/**
 * <copyright>
 * 
 * Copyright (c) 2011, 2012 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 * 
 * The standalone functionality is heavily influenced by org.eclipse.emf.mwe.utils.StandaloneSetup.
 * 
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.ocl.examples.domain.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.WeakHashMap;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.SingletonAdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.common.utils.TracingOption;
import org.eclipse.ocl.examples.domain.compatibility.EMF_2_9;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * StandaloneProjectMap and {@link ProjectMap} provides facilities to assist in
 * preparing the {@link URIConverter} and the {@link EPackage.Registry} of a
 * {@link ResourceSet} and the global and
 * {@link EcorePlugin#getPlatformResourceMap()} and
 * {@link EcorePlugin#getEPackageNsURIToGenModelLocationMap} to support
 * arbitrary and compatible use of <tt>platform:/plugin</tt>,
 * <tt>platform:/resource</tt> and registered URIs in both plugin and standalone
 * environments.
 * <p>
 * StandaloneProjectMap supports only standalone usage and so is free of
 * dependencies on the Eclipse platform. ProjectMap extends StandaloneProjectMap
 * to provide polymorphic standalone and plugin environments.
 * <p>
 * As a result, when the current file context is my.project/model/MyModel.ecore,
 * and when the classpath contains only the JAR version of Ecore, referencing a
 * resource as any or all of
 * <ul>
 * <li>http://www.eclipse.org/emf/2002/Ecore</li>
 * <li>platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore</li>
 * <li>platform:/resource/org.eclipse.emf.ecore/model/Ecore.ecore</li>
 * <li>../../org.eclipse.emf.ecore/model/Ecore.ecore</li>
 * </ul>
 * results in the same Resource being returned by {@link
 * ResourceSet.getResource()}.
 * <p>
 * If the classpath contains distinct imported project and JAR versions of
 * Ecore, referencing
 * <ul>
 * <li>http://www.eclipse.org/emf/2002/Ecore</li>
 * </ul>
 * returns the JAR plugin version while referencing
 * <ul>
 * <li>platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore</li>
 * <li>platform:/resource/org.eclipse.emf.ecore/model/Ecore.ecore</li>
 * <li>../../org.eclipse.emf.ecore/model/Ecore.ecore</li>
 * </ul>
 * returns the imported project version.
 * <p>
 * A ProjectMap consists of a map from a project or bundle name to a location
 * that is resolvable by the conventional Platform URL stream opening
 * capabilities. Utility methods support export of the map to initialize the
 * URIMap in a {@link URIConverter} and/or the
 * {@link EcorePlugin#getPlatformResourceMap()}.
 * <p>
 * Minimal usage to configure <tt>aResourceSet</tt> is just <br>
 * <tt>new ProjectMap().initializeResourceSet(aResourceSet);</tt> <br>
 * or <tt>ProjectMap.getAdapter(aResourceSet);</tt> <br>
 * Thereafter EMF accesses to projects and bundles should just work.
 * 
 * <h4>Standalone Environment</h4>
 * 
 * A resolvable location is a physical location such as
 * <ul>
 * <li>
 * <tt>archive:file:/C:/Tools/Eclipse/3.7.1/plugins/org.antlr.runtime_3.2.0.v201101311130.jar!/</tt>
 * </li>
 * <li>
 * <tt>file:/C:/GIT/org.eclipse.ocl/examples/org.eclipse.ocl.examples.common/</tt>
 * </li>
 * </ul>
 * <p>
 * {@link #getProjectMap()} returns a map of project names and bundle names to a
 * physical location which is established by searching the classpath for folders
 * and JARs containing .project files. If a manifest is also found, the search
 * has found a bundle and the Bundle-SymbolicName is read from the manifest.
 * <p>
 * {@link #initializePackageRegistry(ResourceSet)} populates a trio of
 * registrations for each <tt>genPackages.ecorePackage</tt> referenced from a
 * <tt>genmodel</tt> referenced from a
 * <tt>org.eclipse.emf.ecore.generated_package</tt> defined in any
 * <tt>plugin.xml</tt> found on the classpath. The three declarations ensure
 * that when appropriate, each of the namespace URI (e.g.
 * <tt>http://www.eclipse.org/emf/2002/Ecore</tt>), the project URI (e.g.
 * <tt>platform:/resource/org.eclipse.emf.ecore/model/Ecore.ecore</tt>) and the
 * plugin URI (e.g.
 * <tt>platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore</tt>) resolve to
 * the same Resource eliminating most opportunities for meta-model
 * schizophrenia.
 * <p>
 * {@link #initializePlatformResourceMap()} populates
 * {@link EcorePlugin#getPlatformResourceMap()} with a <i>project</i> to
 * <tt>platform:/resource/<i>project</i></tt> entry for each project and a
 * <i>bundle</i> to <tt>platform:/plugin/<i>bundle</i></tt> entry for each
 * bundle.
 * <p>
 * {@link #initializeGenModelLocationMap(ResourceSet)} exploits the classpath
 * scan for plugins and projects to identify all plugin.xml files and populate
 * the {@link EcorePlugin#getEPackageNsURIToGenModelLocationMap()} from the
 * <tt>org.eclipse.emf.ecore.generated_package</tt> extension points in the same
 * way as occurs automatically in a plugin environment.
 * <p>
 * {@link #initializeURIMap(URIConverter)} installs a
 * <tt>platform:/plugin/<i>project</i></tt> to
 * <tt>platform:/resource/<i>project</i></tt> URI mapping for each project and a
 * <tt>platform:/resource/<i>bundle</i></tt> to
 * <tt>platform:/plugin/<i>bundle</i></tt> URI mapping for each bundle.
 * 
 * <h4>Static Instances and Re-Use</h4>
 * 
 * No static <tt>INSTANCE</tt> is provided because different class loaders or
 * dynamic class path changes may result in stale content. Standalone
 * applications are strongly advised to create their own static instance in a
 * stable context and so avoid repeating the significant costs of a full class
 * path search.
 * <p>
 * The {@link #getAdapter(ResourceSet)} method may be used to invoke
 * {@link #initializeResourceSet(ResourceSet)} if not already invoked and to
 * install the ProjectMap as a ResourceSet adapter allowing an invocation of
 * {@link #findAdapter(ResourceSet)} to find it for subsequent re-use.
 */
public class StandaloneProjectMap extends SingletonAdapterImpl
{
	private static final String PLUGIN_ID = "org.eclipse.ocl.examples.domain";;

	private static final Logger logger = Logger.getLogger(StandaloneProjectMap.class);

	public static final @NonNull TracingOption PROJECT_MAP_ADD_EPACKAGE = new TracingOption(PLUGIN_ID, "projectMap/addEPackage");
	public static final @NonNull TracingOption PROJECT_MAP_ADD_GEN_MODEL = new TracingOption(PLUGIN_ID, "projectMap/addGenModel");
	public static final @NonNull TracingOption PROJECT_MAP_ADD_GENERATED_PACKAGE = new TracingOption(PLUGIN_ID, "projectMap/addGeneratedPackage");
	public static final @NonNull TracingOption PROJECT_MAP_ADD_URI_MAP = new TracingOption(PLUGIN_ID, "projectMap/addURIMap");
	public static final @NonNull TracingOption PROJECT_MAP_CONFIGURE = new TracingOption(PLUGIN_ID, "projectMap/configure");
	public static final @NonNull TracingOption PROJECT_MAP_INSTALL = new TracingOption(PLUGIN_ID, "projectMap/install");
	public static final @NonNull TracingOption PROJECT_MAP_RESOLVE = new TracingOption(PLUGIN_ID, "projectMap/resolve");
	
	{
//		PROJECT_MAP_ADD_EPACKAGE.setState(true);
//		PROJECT_MAP_ADD_GEN_MODEL.setState(true);
//		PROJECT_MAP_ADD_GENERATED_PACKAGE.setState(true);
//		PROJECT_MAP_ADD_URI_MAP.setState(true);
//		PROJECT_MAP_CONFIGURE.setState(true);
//		PROJECT_MAP_INSTALL.setState(true);
//		PROJECT_MAP_RESOLVE.setState(true);
	}
	
	/**
	 * EPackageDescriptor is an EPackage.Descriptor that loads the EPackage to resolve a Namespace URI reference
	 * from the plugin/project model/code in accordance with the configured ResourceLoadStrategy.
	 */
	protected static class EPackageDescriptor implements EPackage.Descriptor
	{	
		protected final @NonNull IPackageLoadStatus packageLoadStatus;	// The PackageLoadStatus of the required package.

		protected EPackageDescriptor(@NonNull IPackageLoadStatus packageLoadStatus, @NonNull EPackage.Registry packageRegistry) {
			this.packageLoadStatus = packageLoadStatus;
			packageRegistry.put(getURI().toString(), this);
			if (PROJECT_MAP_INSTALL.isActive()) {
				PROJECT_MAP_INSTALL.println(toString());
			}
		}

		public EFactory getEFactory() {
			EPackage ePackage = getEPackage();
			if (ePackage != null) {
				return ePackage.getEFactoryInstance();
			}
			else {
				return null;
			}
		}

		public @Nullable EPackage getEPackage() {
			IResourceLoadStrategy resourceLoadStrategy = packageLoadStatus.getResourceLoadStrategy();
			return resourceLoadStrategy.getEPackage(packageLoadStatus);
		}

		public @NonNull URI getURI() {
			return packageLoadStatus.getPackageDescriptor().getNsURI();
		}

		@Override
		public @NonNull String toString() {
			return getClass().getSimpleName() + ": " + getURI() + " with " + packageLoadStatus.getResourceLoadStrategy();
		}

		public void uninstall(@NonNull Registry packageRegistry) {
			if (PROJECT_MAP_INSTALL.isActive()) {
				PROJECT_MAP_INSTALL.println("" + toString());
			}
			packageRegistry.put(getURI().toString(), null);
		}
	}

	/**
	 * An IResourceLoadStatus maintains the lazy load state of a resource within an EPackage.Registry
	 */
	public static interface IResourceLoadStatus
	{	
		void configureEPackageResource();
		
		void configureEPackageRegistry(@NonNull Resource resource);

		void configureResourceSetURIResourceMap(@NonNull Resource resource);

		/**
		 * Dispose of all facilities used by the PackageLoadStatus, and remove all EPackageDescriptor entries.
		 */
		void dispose();

		/**
		 * Return the EPackage to be used for a platform-resource/plugin URI after a namespace URI has already been loaded.
		 */
		@Nullable EPackage getConflictingModelURI(@NonNull EPackage ePackage);

		/**
		 * Return the EPackage resolved by the first loadEPackageByModelURI/loadEPackageByNsURI.
		 * @return
		 */
		@Nullable EPackage getFirstEPackage();

		/**
		 * Return the package load statusfor a packageDescriptor 
		 */
		@Nullable IPackageLoadStatus getPackageLoadStatus(@NonNull IPackageDescriptor packageDescriptor);

		/**
		 * Return the descriptor for the package.
		 */
		@NonNull IResourceDescriptor getResourceDescriptor();

		/**
		 * Return the current package loading strategy.
		 */
		@NonNull IResourceLoadStrategy getResourceLoadStrategy();
		
		/**
		 * Return the package registry maintained by this package load status
		 */
		@NonNull EPackage.Registry getPackageRegistry();

		@Nullable ResourceSet getResourceSet();

		/**
		 * Load and return the EPackage appropriate to the platform resource or plugin URI.
		 */
		@Nullable Resource loadModel(@NonNull URI nsURI);

		/**
		 * Load and return the EPackage appropriate to the namespace URI.
		 */
		void loadEPackages();

		/**
		 * Define a new conflict handler.
		 */
		void setConflictHandler(@Nullable IConflictHandler conflictHandler);

		void setResource(@NonNull Resource resource);

		/**
		 * Define a new package load strategy.
		 */
		void setResourceLoadStrategy(@NonNull IResourceLoadStrategy resourceLoadStrategy);

		/**
		 * Reset the status following notiofication that the model has been unloaded.
		 */
		void unloadedResource();
	}

	/**
	 * An IPackageLoadState maintains the lazy load state of a package within an EPackage.Registry
	 */
	public static interface IPackageLoadStatus
	{	
		void configureEPackageRegistry(@NonNull ResourceSet resourceSet, @NonNull Resource resource);

		void configureURImap();
		
		/**
		 * Dispose of all facilities used by the PackageLoadStatus, and remove all EPackageDescriptor entries.
		 */
		void dispose();

		/**
		 * Return the EPackage to be used for a namespace URI after a platform-resource/plugin URI has already been loaded.
		 */
		@Nullable EPackage getConflictingNsURI();

		@Nullable EPackage getEPackage();

		@Nullable EPackage getEPackageInstance();

		/**
		 * Return the EPackage resolved by the first loadEPackageByModelURI/loadEPackageByNsURI.
		 * @return
		 */
		@Nullable EPackage getFirstEPackage();

		@Nullable EPackage getModel();

		/**
		 * Return the descriptor for the package.
		 */
		@NonNull IPackageDescriptor getPackageDescriptor();

		/**
		 * Return the current package loading strategy.
		 */
		@NonNull IResourceLoadStrategy getResourceLoadStrategy();

		@NonNull IResourceLoadStatus getResourceLoadStatus();

		/**
		 * Load and return the EPackage appropriate to the namespace URI.
		 */
		@Nullable EPackage loadEPackage();

		void setEPackage(@NonNull EPackage ePackage);

		void setModel(@NonNull EPackage ePackage);

		/**
		 * Reset the status following notiofication that the model has been unloaded.
		 */
		void unloadedResource();
	}

	/**
	 * An IResourceLoadStrategy determines how each of the possible forms of URI reference to an EPackage should loaded.
	 */
	public static interface IResourceLoadStrategy
	{
		/**
		 * Respond to the explicit addition of a Java EPackage in the user's ResourceSet.
		 */
		void addedEPackage(@NonNull IPackageLoadStatus packageLoadStatus, @NonNull EPackage ePackage);

		/**
		 * Respond to the explicit addition of an Ecore model in the user's ResourceSet.
		 */
		void addedModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull Resource resource);

		void configure(@NonNull IResourceLoadStatus resourceLoadStatus, @Nullable IConflictHandler conflictHandler);

		/**
		 * Load and return the EPackage appropriate to the namespace URI.
		 */
		@Nullable EPackage getEPackage(@NonNull IPackageLoadStatus packageLoadStatus);

		void handleConflictingModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull EPackage ePackage);

		void loadedModel(@NonNull IResourceLoadStatus packageLoadStatus, @NonNull Resource resource);

		/**
		 * Respond to the notification that the resource has been unloaded.
		 */
		void unloadedResource(@NonNull IResourceLoadStatus resourceLoadStatus);
	}

	protected static abstract class AbstractResourceLoadStrategy implements IResourceLoadStrategy
	{			
		public void addedEPackage(@NonNull IPackageLoadStatus packageLoadStatus, @NonNull EPackage ePackage) {
			throw new UnsupportedOperationException();
		}

		public void addedModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull Resource resource) {
			throw new UnsupportedOperationException();
		}

		public void configure(@NonNull IResourceLoadStatus resourceLoadStatus, @Nullable IConflictHandler conflictHandler) {
			resourceLoadStatus.setConflictHandler(conflictHandler);
			resourceLoadStatus.setResourceLoadStrategy(this);
		}

		public void handleConflictingModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull EPackage ePackage) {
			throw new UnsupportedOperationException();
		}

		protected @Nullable EPackage loadEPackage(@NonNull IPackageLoadStatus packageLoadStatus, boolean configureURImap) {
			IResourceLoadStatus resourceLoadStatus = packageLoadStatus.getResourceLoadStatus();
			resourceLoadStatus.loadEPackages();
			EPackage ePackage = packageLoadStatus.getEPackage();
			if (ePackage == null) {
				ePackage = packageLoadStatus.getEPackageInstance();
			}
			if (configureURImap) {
				resourceLoadStatus.configureEPackageResource();//ResourceSetURIResourceMap(ePackage.eResource());
			}
			return returnEPackage(packageLoadStatus, ePackage);
		}

		protected @Nullable EPackage loadModel(@NonNull IPackageLoadStatus packageLoadStatus) {
			packageLoadStatus.getResourceLoadStatus().loadModel(packageLoadStatus.getPackageDescriptor().getNsURI());
			EPackage ePackage = packageLoadStatus.getModel();
			if (ePackage == null) {
				ePackage = packageLoadStatus.getEPackageInstance();
			}
			return returnEPackage(packageLoadStatus, ePackage);
		}

		public void loadedModel(@NonNull IResourceLoadStatus packageLoadStatus, @NonNull Resource resource) {}
		
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}

		protected @Nullable EPackage returnEPackage(@NonNull IPackageLoadStatus packageLoadStatus, @Nullable EPackage ePackage) {
			if (ePackage != null) {
				if (PROJECT_MAP_RESOLVE.isActive()) {
					URI uri = EcoreUtil.getURI(ePackage);
					PROJECT_MAP_RESOLVE.println("EPackage.Registry[" + packageLoadStatus.getPackageDescriptor().getNsURI() + "] => " + uri);
				}
			}
			return ePackage;
		}
	}
	
	/**
	 * The LoadedStrategy re-uses the already loaded EPackage.
	 */
	private static final class LoadedStrategy extends AbstractResourceLoadStrategy
	{		
		@Override
		public void addedModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull Resource resource) {
			resourceLoadStatus.setResource(resource);
			resourceLoadStatus.setResourceLoadStrategy(LoadedStrategy.INSTANCE);
		}

		public static final @NonNull IResourceLoadStrategy INSTANCE = new LoadedStrategy();
		
		public @Nullable EPackage getEPackage(@NonNull IPackageLoadStatus packageLoadStatus) {
			return packageLoadStatus.getFirstEPackage();
		}

		public void unloadedResource(@NonNull IResourceLoadStatus packageLoadStatus) {}
	}
	
	/**
	 * The LoadedAsEPackageStrategy re-uses the already loaded EPackage for namespace URI accesses, and invokes the conflict handler for
	 * platform URI accesses.
	 */
	private static final class LoadedAsEPackageStrategy extends AbstractResourceLoadStrategy
	{
		public static final @NonNull IResourceLoadStrategy INSTANCE = new LoadedAsEPackageStrategy();
		
		public @Nullable EPackage getEPackage(@NonNull IPackageLoadStatus packageLoadStatus) {
			EPackage ePackage = packageLoadStatus.getFirstEPackage();
			return returnEPackage(packageLoadStatus, ePackage);
		}

		public void unloadedResource(@NonNull IResourceLoadStatus packageLoadStatus) {}
	}
	
	/**
	 * The LoadedAsModelStrategy re-uses the already loaded EPackage for platform URI accesses, and invokes the conflict handler for
	 * namespace URI accesses.
	 */
	private static final class LoadedFirstAsModelStrategy extends AbstractResourceLoadStrategy
	{
		public static final @NonNull IResourceLoadStrategy INSTANCE = new LoadedFirstAsModelStrategy();

		@Override
		public void addedModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull Resource resource) {}
		
		public @Nullable EPackage getEPackage(@NonNull IPackageLoadStatus packageLoadStatus) {
			EPackage ePackage = packageLoadStatus.getConflictingNsURI();
			return returnEPackage(packageLoadStatus, ePackage);
		}
		
		@Override
		public void loadedModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull Resource resource) {}

		public void unloadedResource(@NonNull IResourceLoadStatus packageLoadStatus) {
			packageLoadStatus.unloadedResource();
			packageLoadStatus.setResourceLoadStrategy(LoadFirstStrategy.INSTANCE);
		}
	}

	/**
	 * The LoadBothStrategy permits metamodel schizophrenia and so access to the namespace URI resolves to an installed
	 * resource while access to the platform plugin or resource URI resolve to a dynmically loaded resource.
	 */
	public static final class LoadBothStrategy extends AbstractResourceLoadStrategy
	{
		public static final @NonNull IResourceLoadStrategy INSTANCE = new LoadBothStrategy();

		@Override
		public void addedEPackage(@NonNull IPackageLoadStatus packageLoadStatus, @NonNull EPackage ePackage) {
			IResourceLoadStatus resourceLoadStatus = packageLoadStatus.getResourceLoadStatus();
			Resource eResource = ePackage.eResource();
			if (eResource != null) {
				resourceLoadStatus.configureResourceSetURIResourceMap(eResource);
			}
			resourceLoadStatus.setResourceLoadStrategy(LoadedStrategy.INSTANCE);
		}
		
		@Override
		public void addedModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull Resource resource) {
			resourceLoadStatus.setResource(resource);
			resourceLoadStatus.configureResourceSetURIResourceMap(resource);
			resourceLoadStatus.setResourceLoadStrategy(LoadingBothLoadedModelStrategy.INSTANCE);
		}

		public @Nullable EPackage getEPackage(@NonNull IPackageLoadStatus packageLoadStatus) {
			return loadEPackage(packageLoadStatus, false);
		}

		public void unloadedResource(@NonNull IResourceLoadStatus packageLoadStatus) {
			packageLoadStatus.unloadedResource();
		}
	}

	/**
	 * The LoadBothStrategy permits metamodel schizophrenia and so access to the namespace URI resolves to an installed
	 * resource while access to the platform plugin or resource URI resolve to a dynmically loaded resource.
	 */
	public static final class LoadingBothLoadedModelStrategy extends AbstractResourceLoadStrategy
	{
		public static final @NonNull IResourceLoadStrategy INSTANCE = new LoadingBothLoadedModelStrategy();

		@Override
		public void addedEPackage(@NonNull IPackageLoadStatus packageLoadStatus, @NonNull EPackage ePackage) {
			IResourceLoadStatus resourceLoadStatus = packageLoadStatus.getResourceLoadStatus();
			Resource eResource = ePackage.eResource();
			if (eResource != null) {
				resourceLoadStatus.configureResourceSetURIResourceMap(eResource);
			}
			resourceLoadStatus.setResourceLoadStrategy(LoadedStrategy.INSTANCE);
		}

		public @Nullable EPackage getEPackage(@NonNull IPackageLoadStatus packageLoadStatus) {
			return loadEPackage(packageLoadStatus, false);
		}

		public void unloadedResource(@NonNull IResourceLoadStatus packageLoadStatus) {
			packageLoadStatus.unloadedResource();
		}
	}

	/**
	 * The LoadEPackageStrategy uses the EPackage referenced by the namespace URI for all kinds of access, and then changes the
	 * strategy to LOADED for all further accesses.
	 */
	public static final class LoadEPackageStrategy extends AbstractResourceLoadStrategy
	{
		public static final @NonNull IResourceLoadStrategy INSTANCE = new LoadEPackageStrategy();

		@Override
		public void addedEPackage(@NonNull IPackageLoadStatus packageLoadStatus, @NonNull EPackage ePackage) {
			IResourceLoadStatus resourceLoadStatus = packageLoadStatus.getResourceLoadStatus();
			Resource eResource = ePackage.eResource();
			if (eResource != null) {
				resourceLoadStatus.configureResourceSetURIResourceMap(eResource);
			}
			resourceLoadStatus.setResourceLoadStrategy(LoadedStrategy.INSTANCE);
		}

		@Override
		public void configure(@NonNull IResourceLoadStatus resourceLoadStatus, @Nullable IConflictHandler conflictHandler) {
			super.configure(resourceLoadStatus, conflictHandler);
			resourceLoadStatus.configureEPackageResource();
		}

		@Override
		public void handleConflictingModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull EPackage ePackage) {
			resourceLoadStatus.getConflictingModelURI(ePackage);
		}
		
		public @Nullable EPackage getEPackage(@NonNull IPackageLoadStatus packageLoadStatus) {
			return loadEPackage(packageLoadStatus, false);
		}

		public void unloadedResource(@NonNull IResourceLoadStatus packageLoadStatus) {}
	}

	/**
	 * The LoadFirstStrategy use the EPackage corresponding to the first access as either a namespace URI or platform plugin.resource URI.
	 * Thereafter accesses to the same URI use the first loaded EPackage. Accesses to the other form of URI are arbitrated by the IConflictHandler
	 * in the IPackageLoadStatus.
	 */
	public static final class LoadFirstStrategy extends AbstractResourceLoadStrategy
	{
		public static final @NonNull IResourceLoadStrategy INSTANCE = new LoadFirstStrategy();
		
		@Override
		public void addedEPackage(@NonNull IPackageLoadStatus packageLoadStatus, @NonNull EPackage ePackage) {
			IResourceLoadStatus resourceLoadStatus = packageLoadStatus.getResourceLoadStatus();
			Resource eResource = ePackage.eResource();
			if (eResource != null) {
				resourceLoadStatus.configureResourceSetURIResourceMap(eResource);
			}
			resourceLoadStatus.setResourceLoadStrategy(LoadedAsEPackageStrategy.INSTANCE);
		}
		
		@Override
		public void addedModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull Resource resource) {
			resourceLoadStatus.configureResourceSetURIResourceMap(resource);
			resourceLoadStatus.setResourceLoadStrategy(LoadedFirstAsModelStrategy.INSTANCE);
			resourceLoadStatus.setResource(resource);
		}

		public @Nullable EPackage getEPackage(@NonNull IPackageLoadStatus packageLoadStatus) {
			return loadEPackage(packageLoadStatus, true);
		}

		@Override
		public void handleConflictingModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull EPackage ePackage) {
			resourceLoadStatus.getConflictingModelURI(ePackage);
		}

		public void unloadedResource(@NonNull IResourceLoadStatus packageLoadStatus) {}
	}

	/**
	 * The LoadModelStrategy uses the EPackage referenced by the platform resopurce/plugin URI for all kinds of access, and then changes the
	 * strategy to LOADED for all further accesses.
	 */
	public static final class LoadModelStrategy extends AbstractResourceLoadStrategy
	{
		public static final @NonNull IResourceLoadStrategy INSTANCE = new LoadModelStrategy();
		
		@Override
		public void addedModel(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull Resource resource) {
			resourceLoadStatus.setResource(resource);
			resourceLoadStatus.configureResourceSetURIResourceMap(resource);
			resourceLoadStatus.configureEPackageRegistry(resource);
			resourceLoadStatus.setResourceLoadStrategy(LoadedStrategy.INSTANCE);
		}
		
		public @Nullable EPackage getEPackage(@NonNull IPackageLoadStatus packageLoadStatus) {
			return loadModel(packageLoadStatus);
		}

		public void unloadedResource(@NonNull IResourceLoadStatus packageLoadStatus) {}
	}
	
	/**
	 * An IResourceDescriptor describes the modeling capabilities of one or more known
	 * model packages in a genmodel.
	 */
	public static interface IResourceDescriptor
	{
		void addedEPackage(@NonNull ResourceSet resourceSet, @NonNull EPackage ePackage);
		void addedModel(@NonNull ResourceSet resourceSet, @NonNull Resource resource);

		void configure(@Nullable ResourceSet resourceSet, @NonNull IResourceLoadStrategy resourceLoadStrategy, @Nullable IConflictHandler conflictHandler);

		void configureResourceSetURIResourceMap(@NonNull ResourceSet resourceSet, @NonNull Resource resource);

		/**
		 * Return the project relative Gen Model URI.
		 */
		@NonNull URI getGenModelURI();

		/**
		 * Return the external filespace form of the model URI containing the package.
		 * @Throws IllegalStateException if there is no Ecore model.
		 */
		@NonNull URI getLocationURI();

		Iterable<? extends IPackageDescriptor> getPackageDescriptors();

		/**
		 * Return the platform:/resource form of the model URI containing the package
		 * @Throws IllegalStateException if there is no Ecore model.
		 */
		@NonNull URI getPlatformResourceURI();

		/**
		 * Return the platform:/plugin form of the model URI containing the package
		 * @Throws IllegalStateException if there is no Ecore model.
		 */
		@NonNull URI getPlatformPluginURI();

		/**
		 * Return the Project Descriptor containing this package.
		 */
		@NonNull IProjectDescriptor getProjectDescriptor();

		IPackageDescriptor getFirstPackageDescriptor();
		
		@NonNull IResourceLoadStatus getResourceLoadStatus(@Nullable ResourceSet resourceSet);

		boolean hasEcoreModel();

		void setEcore(@NonNull List<String> ecorePackages, @NonNull Map<String, IPackageDescriptor> nsURI2packageDescriptor);

		@NonNull URI setResourceURI(@NonNull String ecorePackage);

		/**
		 * Unload the package registry to force a reload.
		 */
		void unload(@NonNull ResourceSet resourceSet);
	}
	
	/**
	 * An IPackageDescriptor describes the modeling capabilities of a known
	 * model package and may be installed under a variety of synonyms in an
	 * EPackage.Registry to map multiple URIs to a single EPackage.
	 */
	public static interface IPackageDescriptor
	{
		void configure(@NonNull ResourceSet resourceSet, @NonNull IResourceLoadStrategy instance, @Nullable IConflictHandler conflictHandler);
		
		@Nullable String getClassName();

		/**
		 * Return the project relative Ecore Model URI, which may be null if there is no corresponding Ecore model.
		 */
		@Nullable URI getEcoreModelURI();

		@Nullable URI getEcorePackageURI();

		/**
		 * Return the Package NS URI.
		 */
		@NonNull URI getNsURI();

		@NonNull IResourceDescriptor getResourceDescriptor();

		void setEcorePackage(@NonNull String ecorePackage);
	}

	/**
	 * An IProjectDescriptor describes the capabilities of a project.
	 */
	public static interface IProjectDescriptor
	{
		void addPackageDescriptor(@NonNull IPackageDescriptor packageDescriptor);

		void addResourceDescriptor(@NonNull IResourceDescriptor resourceDescriptor);

		void configure(@Nullable ResourceSet resourceSet, @NonNull IResourceLoadStrategy resourceLoadStrategy, @Nullable IConflictHandler conflictHandler);

		@NonNull IResourceDescriptor createResourceDescriptor(@NonNull String genModel, @NonNull Map<URI, String> nsURI2className);

		/**
		 * Return the physical location of this project.
		 */
		@NonNull URI getLocationURI();

		/**
		 * Return the physical location of a projectRelativeFileName as a URI.
		 */
		@NonNull URI getLocationURI(@NonNull String projectRelativeFileName);

		/**
		 * Return the physical location of a projectRelativeFileName as a File.
		 */
		@NonNull File getLocationFile(@NonNull String projectRelativeFileName);

		/**
		 * Return project name.
		 */
		@NonNull String getName();

		/**
		 * Return the location of this project as a platform:/plugin URI.
		 */
		@NonNull URI getPlatformPluginURI();

		/**
		 * Return the location of a projectRelativeFileName as a
		 * platform:/resource URI.
		 */
		@NonNull URI getPlatformPluginURI(@NonNull String projectRelativeFileName);

		/**
		 * Return the location of this project as a platform:/resource URI.
		 */
		@NonNull URI getPlatformResourceURI();

		/**
		 * Return the location of a projectRelativeFileName as a
		 * platform:/resource URI.
		 */
		@NonNull URI getPlatformResourceURI(@NonNull String projectRelativeFileName);

		/**
		 * Return the package descriptor for the package with a given nsURI or
		 * null if none known in the project.
		 */
		IPackageDescriptor getPackageDescriptor(@NonNull URI nsURI);

		/**
		 * Return the overall ProjectMap.
		 */
		@NonNull StandaloneProjectMap getProjectMap();

		/**
		 * Return all packages descriptors in the project.
		 */
		@Nullable Collection<IResourceDescriptor> getResourceDescriptors();

		void initializeGenModelLocationMap(@NonNull Map<URI, IPackageDescriptor> nsURI2package);

		void initializePlatformResourceMap();

		void initializeURIMap(@NonNull Map<URI, URI> uriMap);
	}
	
	public static abstract class AbstractResourceLoadStatus implements IResourceLoadStatus, Adapter
	{
		protected final @NonNull IResourceDescriptor resourceDescriptor;
		protected @Nullable ResourceSet resourceSet;
		private final @NonNull Map<URI, PackageLoadStatus> nsURI2packageLoadStatus = new HashMap<URI, PackageLoadStatus>();
		protected final @NonNull EPackage.Registry packageRegistry;
		
		/**
		 * The optional handler for namespace/platform or platform/namespace metamodel schizophrenia.
		 */
		protected @Nullable IConflictHandler conflictHandler = MapToFirstConflictHandlerWithLog.INSTANCE;
		
		/**
		 * The strategy to be used to resolve further URI to EPackage mappings.
		 */
		protected @NonNull IResourceLoadStrategy resourceLoadStrategy = LoadFirstStrategy.INSTANCE;

		/**
		 * Target of unload watching Adapter.
		 */
		private @Nullable Notifier target = null;

		/**
		 * The dynamically loaded model (e.g. platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore#/).
		 */
		protected @Nullable Resource eModel = null;

		/**
		 * Re-entrancy detector for self-referential models such as Ecore.ecore.
		 */
		protected boolean modelLoadInProgress = false;

		protected AbstractResourceLoadStatus(@NonNull IResourceDescriptor resourceDescriptor, @Nullable ResourceSet resourceSet) {
			this.resourceDescriptor = resourceDescriptor;
			this.resourceSet = resourceSet;
			this.packageRegistry = StandaloneProjectMap.getPackageRegistry(resourceSet);
			for (IPackageDescriptor packageDescriptor : resourceDescriptor.getPackageDescriptors()) {
				nsURI2packageLoadStatus.put(packageDescriptor.getNsURI(), new PackageLoadStatus(this, packageDescriptor));
			}
		}
		
		public void configureEPackageRegistry(@NonNull Resource resource) {
			ResourceSet resourceSet2 = resourceSet;
			if (resourceSet2 != null) {
				for (IPackageLoadStatus packageLoadStatus : nsURI2packageLoadStatus.values()) {
					EPackage ePackage = packageLoadStatus.getEPackage();
					if (ePackage != null) {
						packageLoadStatus.configureEPackageRegistry(resourceSet2, resource);
					}
				}
			}
		}

		public void configureEPackageResource() {
			ResourceSet resourceSet2 = resourceSet;
			if (resourceSet2 != null) {
				Collection<PackageLoadStatus> packageLoadStatuses = nsURI2packageLoadStatus.values();
				@SuppressWarnings("null")@NonNull URI uri = resourceDescriptor.getGenModelURI().appendFileExtension("ecore");
				MultiplePackageResource resource = new MultiplePackageResource(uri, this);
				for (IPackageLoadStatus packageLoadStatus : packageLoadStatuses) {
					EPackage ePackage = packageLoadStatus.loadEPackage();
					if (ePackage != null) {
						resource.put(ePackage);
					}
				}
				resource.setLoaded(true);
				resourceDescriptor.configureResourceSetURIResourceMap(resourceSet2, resource);
			}
		}

		public void configureResourceSetURIResourceMap(@NonNull Resource resource) {
			ResourceSet resourceSet2 = resourceSet;
			if (resourceSet2 != null) {
				resourceDescriptor.configureResourceSetURIResourceMap(resourceSet2, resource);
			}
		}
		
		public void dispose() {
			resourceSet = null;
			if (target != null) {
				target.eAdapters().remove(this);
				target = null;
			}
		}

		public @Nullable EPackage getConflictingModelURI(@NonNull EPackage ePackage) {
			if (conflictHandler != null) {
				return conflictHandler.handleConflictingModelURI(this, ePackage);
			}
			else {
				return null;
			}
		}

		public @Nullable EPackage getFirstEPackage() {
			throw new UnsupportedOperationException();
		}

		public @Nullable IPackageLoadStatus getPackageLoadStatus(@NonNull IPackageDescriptor packageDescriptor) {
			return nsURI2packageLoadStatus.get(packageDescriptor.getNsURI());
		}

		public @NonNull IResourceLoadStrategy getResourceLoadStrategy() {
			return resourceLoadStrategy;
		}

		public @NonNull EPackage.Registry getPackageRegistry() {
			return packageRegistry;
		}

		public @NonNull IResourceDescriptor getResourceDescriptor() {
			return resourceDescriptor;
		}

		public @Nullable Resource getResource() {
			Resource eModel2 = eModel;
			if (eModel2 == null) {
				try {
					modelLoadInProgress = true;
					IResourceDescriptor resourceDescriptor2 = getResourceDescriptor();
					if (resourceDescriptor2.hasEcoreModel()) {
						URI platformResourceURI = resourceDescriptor2.getPlatformResourceURI();
						ResourceSet resourceSet = this.resourceSet != null ? this.resourceSet : new ResourceSetImpl();
						resourceSet.createResource(platformResourceURI);  // Calls back to addedResource()
					}
				} finally {
					modelLoadInProgress = false;
				}
			}
			return eModel;
		}

		public @Nullable ResourceSet getResourceSet() {
			return resourceSet;
		}

		public @Nullable Notifier getTarget() {
			return target;
		}

		public @Nullable EPackage handleConflictingNsURI(@NonNull IPackageLoadStatus packageLoadStatus) {
			if (conflictHandler != null) {
				return conflictHandler.handleConflictingNsURI(packageLoadStatus);
			}
			else {
				EPackage ePackage = packageLoadStatus.getEPackage();
				if (ePackage == null) {
					ePackage = packageLoadStatus.getEPackageInstance();
				}
				return ePackage;
			}
		}

		protected void handleLoadException(Resource resource, final @NonNull String location, Exception exception) throws RuntimeException {
			class DiagnosticWrappedException extends WrappedException implements Resource.Diagnostic
			{
				private static final long serialVersionUID = 1L;

				public DiagnosticWrappedException(Exception exception) {
					super(exception);
				}

				public String getLocation() {
					return location;
				}

				public int getColumn() {
					return 0;
				}

				public int getLine() {
					return 0;
				}
			}
			Exception cause = exception instanceof Resource.IOWrappedException ? (Exception) exception.getCause() : exception;
			DiagnosticWrappedException wrappedException = new DiagnosticWrappedException(cause);
			if ((resource != null) && resource.getErrors().isEmpty()) {
				resource.getErrors().add(exception instanceof Resource.Diagnostic ? (Resource.Diagnostic) exception : wrappedException);
			}
			throw wrappedException;
		}

		protected void install() {}

		public boolean isAdapterForType(Object type) {
			return false;
		}

		public void loadEPackages() {
			for (IPackageLoadStatus packageLoadStatus : nsURI2packageLoadStatus.values()) {
				packageLoadStatus.loadEPackage();
			}
		}

		public synchronized @Nullable Resource loadModel(@NonNull URI nsURI) {
			if (modelLoadInProgress) {					// Recursive load
				logger.error("Attempt to load self-referential '" + nsURI + "' as model replaced by registered EPackage");
				return eModel;
			}
			return getResource();
		}

		private void loadedModel(@NonNull ResourceSet resourceSet, @NonNull EPackage ePackage) {
			String nsURI = ePackage.getNsURI();
			if (nsURI != null) {
				StandaloneProjectMap projectMap = resourceDescriptor.getProjectDescriptor().getProjectMap();
				@SuppressWarnings("null")@NonNull URI uri = URI.createURI(nsURI);
				IPackageDescriptor packageDescriptor = projectMap.getPackageDescriptor(uri);
				if (packageDescriptor != null) {
					IPackageLoadStatus packageLoadStatus = getPackageLoadStatus(packageDescriptor);
					if (packageLoadStatus != null) {
						packageLoadStatus.setModel(ePackage);
						if (PROJECT_MAP_RESOLVE.isActive()) {
							PROJECT_MAP_RESOLVE.println(ePackage.getNsURI() + " => " + ePackage.eResource().getURI() + " : " + DomainUtil.debugSimpleName(ePackage));
						}
					}
				}
			}
			for (EPackage eSubPackage : ePackage.getESubpackages()) {
				if (eSubPackage != null) {
					loadedModel(resourceSet, eSubPackage);
				}
			}
		}

		private void loadedResource(@NonNull Resource newResource) {
			ResourceSet resourceSet = newResource.getResourceSet();
			if (resourceSet != null) {
				for (EObject eObject : newResource.getContents()) {
					if (eObject instanceof EPackage) {
						EPackage ePackage = (EPackage) eObject;
						loadedModel(resourceSet, ePackage);
					}
				}
			}
			resourceLoadStrategy.loadedModel(this, newResource);
		}
		
		public void notifyChanged(Notification notification) {
			if (notification.getNotifier() == target) {
				int id = notification.getFeatureID(Resource.class);
				if (id == Resource.RESOURCE__IS_LOADED) {
					int eventType = notification.getEventType();
					if (eventType == Notification.SET) {
						boolean wasLoaded = notification.getOldBooleanValue();
						boolean isLoaded = notification.getNewBooleanValue();
						if (isLoaded && !wasLoaded) {
							if (target instanceof Resource) {
								loadedResource((Resource) target);
							}
						}
						else if (!isLoaded && wasLoaded) {
							resourceLoadStrategy.unloadedResource(this);
						}
					}
				}
			}
		}

		public void setConflictHandler(@Nullable IConflictHandler conflictHandler) {
			this.conflictHandler = conflictHandler;
		}

		public void setResource(@NonNull Resource resource) {
			assert eModel == null;
			eModel = resource;
			if (!resource.isLoaded()) {
				try {
					InputStream inputStream = resource.getResourceSet().getURIConverter().createInputStream(resource.getURI());
					List<Adapter> eAdapters = resource.eAdapters();
					if (!eAdapters.contains(this)) {
						eAdapters.add(this);
					}
					resource.load(inputStream, null);
				} catch (Exception exception) {
					handleLoadException(resource, DomainUtil.nonNullEMF(resource.getURI().toString()), exception);
				} 
			}
		}

		public void setResourceLoadStrategy(@NonNull IResourceLoadStrategy resourceLoadStrategy) {
			this.resourceLoadStrategy = resourceLoadStrategy;
			if (PROJECT_MAP_CONFIGURE.isActive()) {
				PROJECT_MAP_CONFIGURE.println(this.toString());
			}
		}

		public void setTarget(Notifier newTarget) {
			this.target = newTarget;
		}

		@Override
		public @NonNull String toString() {
			StringBuilder s = new StringBuilder();
			s.append(resourceLoadStrategy +  " for " + resourceDescriptor.getGenModelURI());
			if (packageRegistry == EPackage.Registry.INSTANCE) {
				s.append(" in global ");
			}
			else {
				s.append(" in ");
			}
			s.append(DomainUtil.debugSimpleName(packageRegistry));
			@SuppressWarnings("null")@NonNull String string = s.toString();
			return string;
		}

		public void unloadedResource() {
			for (IPackageLoadStatus packageLoadStatus : nsURI2packageLoadStatus.values()) {
				packageLoadStatus.unloadedResource();
			}
		}
	}
	
	public static final class SinglePackageResourceLoadStatus extends AbstractResourceLoadStatus
	{
		public SinglePackageResourceLoadStatus(@NonNull SinglePackageResourceDescriptor packageDescriptor, @Nullable ResourceSet resourceSet) {
			super(packageDescriptor, resourceSet);
			install();
		}
	}
	
	/**
	 * A MultiplePackageResource may bwe installed in a ResourceSet.uriResourceMap for resolution as the
	 * as *.ecore URI and subsequent delegation of the fragment getEObject to the appropraite Java EPackageImpl.
	 * <p>
	 * This Resource should never be used for any other purpose.
	 */
	public static final class MultiplePackageResource extends ResourceImpl
	{
		private final @NonNull Map<String, EPackage> fragment2ePackage = new HashMap<String, EPackage>();
		private final @NonNull IResourceLoadStatus resourceLoadStatus;

		public MultiplePackageResource(@NonNull URI uri, @NonNull IResourceLoadStatus resourceLoadStatus) {
			super(uri);
			this.resourceLoadStatus = resourceLoadStatus;
		}

		@Override
		public EObject getEObject(String uriFragment) {
			EPackage ePackage = fragment2ePackage.get(uriFragment);
			if (ePackage != null) {
				resourceLoadStatus.getResourceLoadStrategy().handleConflictingModel(resourceLoadStatus, ePackage);
			}
			return ePackage;
		}

		public void put(@NonNull EPackage ePackage) {
			StringBuilder s = new StringBuilder();
			computeFragment(s, ePackage);
			fragment2ePackage.put(s.toString(), ePackage);
		}

		private void computeFragment(@NonNull StringBuilder s, @NonNull EPackage ePackage) {
			EPackage eSuperPackage = ePackage.getESuperPackage();
			if (eSuperPackage == null) {
				s.append("/");
			}
			else {
				computeFragment(s, eSuperPackage);
				s.append("/");
				s.append(ePackage.getName());
			}
		}
		
		@Override
		public Notification setLoaded(boolean isLoaded) {
			return super.setLoaded(isLoaded);
		}
	}
	
	public static final class MultiplePackageResourceLoadStatus extends AbstractResourceLoadStatus
	{
		public MultiplePackageResourceLoadStatus(@NonNull MultiplePackageResourceDescriptor resourceDescriptor, @Nullable ResourceSet resourceSet) {
			super(resourceDescriptor, resourceSet);
			install();
		}
	}
	
	public static final class PackageLoadStatus implements IPackageLoadStatus
	{
		protected final @NonNull AbstractResourceLoadStatus resourceLoadStatus;
		protected final @NonNull IPackageDescriptor packageDescriptor;
		
		protected final @NonNull EPackageDescriptor namespaceURIDescriptor;
		
		/**
		 * The EPackage resulting from the first loadEPackageByModelURI/loadEPackageByNsURI
		 */
		private @Nullable EPackage firstEPackage;

		/**
		 * The resolved compiled EPackage (e.g. org.eclipse.emf.ecore.EcorePackage.eINSTANCE).
		 */
		private @Nullable EPackage ePackage = null;

		/**
		 * The resolved EPackage from a dynamically loaded model (e.g. platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore#/).
		 */
		private @Nullable EPackage eModel = null;

		public PackageLoadStatus(@NonNull AbstractResourceLoadStatus resourceLoadStatus, @NonNull IPackageDescriptor packageDescriptor) {
			this.resourceLoadStatus = resourceLoadStatus;
			this.packageDescriptor = packageDescriptor;
			this.namespaceURIDescriptor = new EPackageDescriptor(this, resourceLoadStatus.getPackageRegistry());
		}
		
		public void configureEPackageRegistry(@NonNull ResourceSet resourceSet, @NonNull Resource resource) {
			URI nsURI = packageDescriptor.getNsURI();
			IPackageLoadStatus packageLoadStatus = resourceLoadStatus.getPackageLoadStatus(packageDescriptor);
			if (packageLoadStatus != null) {
				EPackage modelEPackage = packageLoadStatus.getModel();
				resourceSet.getPackageRegistry().put(nsURI.toString(), modelEPackage);
				if (PROJECT_MAP_RESOLVE.isActive()) {
					PROJECT_MAP_RESOLVE.println("EPackage.Registry[" + nsURI + "] => " + EcoreUtil.getURI(modelEPackage));
				}
			}
		}

		public void configureURImap() {
			EPackage ePackage = getEPackage();
			if (ePackage != null) {
				ResourceSet resourceSet = resourceLoadStatus.getResourceSet();
				if (resourceSet != null) {
					resourceLoadStatus.getResourceDescriptor().addedEPackage(resourceSet, ePackage);
				}
			}
		}
		
		public void dispose() {
			namespaceURIDescriptor.uninstall(resourceLoadStatus.getPackageRegistry());
			firstEPackage = null;
			ePackage = null;
			eModel = null;
		}

		public @Nullable EPackage getConflictingNsURI() {
			return resourceLoadStatus.handleConflictingNsURI(this);
		}

		public @Nullable EPackage getEPackage() {
			if (ePackage == null) {
				ePackage = getEPackageInstance();
			}
			return ePackage;
		}

		public @Nullable EPackage getEPackageInstance() {
			String className = packageDescriptor.getClassName();
			if (className != null) {
				try {
					Class<?> javaClass = Class.forName(className);
					Field field = javaClass.getField("eINSTANCE");
					return (EPackage) field.get(null);
				} catch (ClassNotFoundException e) {
					throw new WrappedException(e);
				} catch (IllegalAccessException e) {
					throw new WrappedException(e);
				} catch (NoSuchFieldException e) {
					throw new WrappedException(e);
				}
			} else {
				Object object = EPackage.Registry.INSTANCE.get(packageDescriptor.getNsURI().toString());
				if (object instanceof EPackage) {
					return  (EPackage) object;
				} else if (object instanceof EPackage.Descriptor) {
					return  ((EPackage.Descriptor) object).getEPackage();
				}
			}
			return null;
		}

		public @Nullable EPackage getFirstEPackage() {
			if (firstEPackage != null) {
				return firstEPackage;
			}
			else {
				return getEPackageInstance();
			}
		}

		public @Nullable EPackage getModel() {
			return eModel;
		}

		public @NonNull IPackageDescriptor getPackageDescriptor() {
			return packageDescriptor;
		}

		public @NonNull IResourceLoadStrategy getResourceLoadStrategy() {
			return resourceLoadStatus.getResourceLoadStrategy();
		}

		public @NonNull IResourceLoadStatus getResourceLoadStatus() {
			return resourceLoadStatus;
		}

		public @Nullable EPackage loadEPackage() {
			if (ePackage == null) {
				ePackage = getEPackageInstance();
			}
			if (firstEPackage == null) {
				firstEPackage = ePackage;
			}
			return ePackage;
		}

		public void setEPackage(@NonNull EPackage ePackage) {
			assert this.ePackage == null;
			if (firstEPackage == null) {
				firstEPackage = ePackage;
			}
			this.ePackage = ePackage;
		}

		public void setModel(@NonNull EPackage ePackage) {
			assert this.eModel == null;
			if (firstEPackage == null) {
				firstEPackage = ePackage;
			}
			this.eModel = ePackage;
		}

		@Override
		public String toString() {
			return packageDescriptor.toString();
		}

		public void unloadedResource() {
			eModel = null;
		}
	}

	/**
	 * PackageDescriptor supports lazy class loading and initialization of a
	 * compiled Ecore package. Class loading occurs in the context of the
	 * ProjectMap, which performs classpath scans, so it is assumed that
	 * everything is visible. Re-use in a larger context may require a new
	 * ProjectMap to be created.
	 * 
	 * If a PackageDescriptor is installed under multiple URIs, the resource
	 * created by the first load is shared by all subsequent resolutions.
	 * 
	 * If a PackageDescriptor is set to useModel, the *.ecore file is loaded to
	 * provide the EPackage, rather than the Java className.
	 * 
	 * A PackageDescriptor maintains the declared context of a package which may be shared by
	 * many ResourceSets. In contrast a PackageStatus maintains the actual state of a package
	 * for a particular EPackage.Registry, each of which may have a distinct ResourceLoadStrategy
	 * and consequently may not load the same EPackage.
	 */
	public static abstract class AbstractResourceDescriptor implements IResourceDescriptor
	{
		/**
		 * The bundle/project in which this package is defined (e.g. for org.eclipse.emf.ecore). 
		 */
		protected final @NonNull IProjectDescriptor projectDescriptor;

		/**
		 * The project-relative URI of the GenModel for the EPackage (e.g. model/Ecore.genmodel). 
		 */
		protected final @NonNull URI genModelURI;
		
		/**
		 * The filespace URI of the EPackage (e.g. file:/C:/Eclipse/plugins/org.eclipse.emf.ecore/model/Ecore.ecore). 
		 */
		private @Nullable  URI locationURI = null;
		
		/**
		 * The platform resource URI of the EPackage (e.g. platform:/resource/org.eclipse.emf.ecore/model/Ecore.ecore). 
		 */
		private @Nullable  URI platformResourceURI = null;
		
		/**
		 * The platform plugin URI of the EPackage (e.g. platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore). 
		 */
		private @Nullable  URI platformPluginURI = null;

		/**
		 * The package descriptors for each of the multiple packages in the genmodel. 
		 */
		protected final @NonNull List<IPackageDescriptor> packageDescriptors = new ArrayList<IPackageDescriptor>();
		
		private boolean hasEcoreModel = false;
		
		/**
		 * The IResourceLoadStatus for each ResourceSet (null is the global 'ResourceSet' 'containing' all Java'd packages).
		 */
		private final @NonNull WeakHashMap<ResourceSet, IResourceLoadStatus> resourceSet2resourceLoadStatus = new WeakHashMap<ResourceSet, IResourceLoadStatus>();
		
		protected AbstractResourceDescriptor(@NonNull IProjectDescriptor projectDescriptor, @NonNull URI genModelURI, @NonNull Map<URI, String> nsURI2className) {
			this.projectDescriptor = projectDescriptor;
			this.genModelURI = genModelURI;
			for (@SuppressWarnings("null")@NonNull URI nsURI : nsURI2className.keySet()) {
				@SuppressWarnings("null")@NonNull String className = nsURI2className.get(nsURI);
				IPackageDescriptor packageDescriptor = projectDescriptor.getPackageDescriptor(nsURI);
				if (packageDescriptor == null) {
					if (PROJECT_MAP_ADD_GENERATED_PACKAGE.isActive()) {
						PROJECT_MAP_ADD_GENERATED_PACKAGE.println(nsURI + " : " + genModelURI + " : " + className);
					}
					packageDescriptor = new PackageDescriptor(this, nsURI, className);
					projectDescriptor.addPackageDescriptor(packageDescriptor);
					packageDescriptors.add(packageDescriptor);
				}
			}
			projectDescriptor.addResourceDescriptor(this);
		}
		
		public void addedEPackage(@NonNull ResourceSet resourceSet, @NonNull EPackage ePackage) {
			IResourceLoadStatus resourceLoadStatus = resourceSet2resourceLoadStatus.get(resourceSet);
			@SuppressWarnings("null")@NonNull URI uri = URI.createURI(ePackage.getNsURI());
			IPackageDescriptor packageDescriptor = getProjectDescriptor().getPackageDescriptor(uri);
			if (packageDescriptor != null) {
				IPackageLoadStatus packageLoadStatus = resourceLoadStatus.getPackageLoadStatus(packageDescriptor);
				if (packageLoadStatus != null) {
					resourceLoadStatus.getResourceLoadStrategy().addedEPackage(packageLoadStatus, ePackage);
				}
			}
		}
		
		public void addedModel(@NonNull ResourceSet resourceSet, @NonNull Resource resource) {
			IResourceLoadStatus resourceLoadStatus = resourceSet2resourceLoadStatus.get(resourceSet);
			if (resourceLoadStatus != null) {
				resourceLoadStatus.getResourceLoadStrategy().addedModel(resourceLoadStatus, resource);
			}
		}

		public void configure(@Nullable ResourceSet resourceSet, @NonNull IResourceLoadStrategy resourceLoadStrategy, @Nullable IConflictHandler conflictHandler) {
			if (hasEcoreModel) {
				IResourceLoadStatus resourceLoadStatus = getResourceLoadStatus(resourceSet);
				resourceLoadStrategy.configure(resourceLoadStatus, conflictHandler);
			}
		}
		
		public void configureResourceSetURIResourceMap(@NonNull ResourceSet resourceSet, @NonNull Resource resource) {
			Map<URI, Resource> uriResourceMap;
			synchronized (resourceSet) {
				uriResourceMap = ((ResourceSetImpl)resourceSet).getURIResourceMap();
				if (uriResourceMap == null) {
					uriResourceMap = new HashMap<URI, Resource>();
					((ResourceSetImpl)resourceSet).setURIResourceMap(uriResourceMap);
				}
			}
			URI platformPluginURI = getPlatformPluginURI();
			URI platformResourceURI = getPlatformResourceURI();
			synchronized (uriResourceMap) {
				uriResourceMap.put(platformPluginURI, resource);
				uriResourceMap.put(platformResourceURI, resource);
			}
			if (PROJECT_MAP_RESOLVE.isActive()) {
				URI uri = resource.getURI();
				PROJECT_MAP_RESOLVE.println("ResourceSet.uriResourceMap[" + platformPluginURI + "] => " + uri);
				PROJECT_MAP_RESOLVE.println("ResourceSet.uriResourceMap[" + platformResourceURI + "] => " + uri);
			}
		}

		protected abstract @NonNull IResourceLoadStatus createResourceLoadStatus(@Nullable ResourceSet resourceSet);

		@Deprecated
		public IPackageDescriptor getFirstPackageDescriptor() {
			return packageDescriptors.iterator().next();
		}

		public @NonNull URI getGenModelURI() {
			return genModelURI;
		}

		public @NonNull URI getLocationURI() {
			return DomainUtil.nonNullState(locationURI);
		}
		
		public @NonNull List<? extends IPackageDescriptor> getPackageDescriptors() {
			return packageDescriptors;
		}
		
		public @NonNull URI getPlatformPluginURI() {
			return DomainUtil.nonNullState(platformPluginURI);
		}

		public @NonNull URI getPlatformResourceURI() {
			return DomainUtil.nonNullState(platformResourceURI);
		}

		public @NonNull IProjectDescriptor getProjectDescriptor() {
			return projectDescriptor;
		}

		public @NonNull IResourceLoadStatus getResourceLoadStatus(@Nullable ResourceSet resourceSet) {
			assert hasEcoreModel;
			IResourceLoadStatus resourceLoadStatus = resourceSet2resourceLoadStatus.get(resourceSet);
			if (resourceLoadStatus == null) {
				synchronized (resourceSet2resourceLoadStatus) {
					resourceLoadStatus = resourceSet2resourceLoadStatus.get(resourceSet);
					if (resourceLoadStatus == null) {
						resourceLoadStatus = createResourceLoadStatus(resourceSet);
						resourceSet2resourceLoadStatus.put(resourceSet, resourceLoadStatus);
					}
				}
			}
			return resourceLoadStatus;
		}

		public boolean hasEcoreModel() {
			return hasEcoreModel;
		}

		public void setEcore(@NonNull List<String> ecorePackages, @NonNull Map<String, IPackageDescriptor> nsURI2packageDescriptor) {
			String anEcorePackage = DomainUtil.nonNullState(ecorePackages.get(0));
			setResourceURI(anEcorePackage);
			URI projectLocationURI = projectDescriptor.getLocationURI();
			URI absoluteGenModelURI = genModelURI.resolve(projectLocationURI);
			List<IPackageDescriptor> packageDescriptors = new ArrayList<IPackageDescriptor>();
			ResourceSet resourceSet = new ResourceSetImpl();		// FIXME maybe this can be lazy
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
			for (@SuppressWarnings("null")@NonNull String ecorePackage : ecorePackages) {
				URI ecorePackageURI = URI.createURI(ecorePackage);
				URI absoluteEcoreURI = ecorePackageURI.resolve(absoluteGenModelURI);
				EObject eObject = resourceSet.getEObject(absoluteEcoreURI, true);
				if (eObject instanceof EPackage) {
					EPackage ePackage = (EPackage)eObject;
					String nsURI = ePackage.getNsURI();
					if (nsURI != null) {										// EPackage should have an nsURI to match plugin.xml, but ignore if not
						IPackageDescriptor packageDescriptor = nsURI2packageDescriptor.get(nsURI);
						if (packageDescriptor != null) {						// Not all EPackages have plugin.xml registrations
							packageDescriptor.setEcorePackage(ecorePackage);
							packageDescriptors.add(packageDescriptor);
						}
					}
				}
			}
			hasEcoreModel = true;
		}

		public @NonNull URI setResourceURI(@NonNull String ecorePackage) {
			URI uri = URI.createURI(ecorePackage);
			URI projectLocationURI = projectDescriptor.getLocationURI();
			URI absoluteGenModelURI = genModelURI.resolve(projectLocationURI);
			URI absolutePackageURI = uri.resolve(absoluteGenModelURI);
			@SuppressWarnings("null")@NonNull URI ecorePackageURI = absolutePackageURI.deresolve(projectLocationURI, true, true, true);
			@SuppressWarnings("null")@NonNull URI ecoreURI = ecorePackageURI.trimFragment();
			URI resourceURI = projectDescriptor.getPlatformResourceURI();
			URI pluginURI = projectDescriptor.getPlatformPluginURI();
			platformResourceURI = ecoreURI.resolve(resourceURI);
			platformPluginURI = ecoreURI.resolve(pluginURI);
			locationURI = ecoreURI.resolve(projectDescriptor.getLocationURI());
			projectDescriptor.getProjectMap().addResourceDescriptor(this);
			return ecorePackageURI;
		}

		public void unload() {
			synchronized (resourceSet2resourceLoadStatus) {
				for (IResourceLoadStatus resourceLoadStatus : resourceSet2resourceLoadStatus.values()) {
					resourceLoadStatus.dispose();
				}
				resourceSet2resourceLoadStatus.clear();
			}
		}

		public void unload(@NonNull ResourceSet resourceSet) {
			if (hasEcoreModel()) {
				synchronized (resourceSet2resourceLoadStatus) {
					IResourceLoadStatus resourceLoadStatus = resourceSet2resourceLoadStatus.remove(resourceSet);
					if (resourceLoadStatus != null) {
						resourceLoadStatus.dispose();
					}
				}
			}
		}
	}

	/**
	 * PackageDescriptor supports lazy class loading and initialization of a
	 * compiled Ecore package. Class loading occurs in the context of the
	 * ProjectMap, which performs classpath scans, so it is assumed that
	 * everything is visible. Re-use in a larger context may require a new
	 * ProjectMap to be created.
	 * 
	 * If a PackageDescriptor is installed under multiple URIs, the resource
	 * created by the first load is shared by all subsequent resolutions.
	 * 
	 * If a PackageDescriptor is set to useModel, the *.ecore file is loaded to
	 * provide the EPackage, rather than the Java className.
	 * 
	 * A PackageDescriptor maintains the declared context of a package which may be shared by
	 * many ResourceSets. In contrast a PackageStatus maintains the actual state of a package
	 * for a particular EPackage.Registry, each of which may have a distinct ResourceLoadStrategy
	 * and consequently may not load the same EPackage.
	 */
	public static final class PackageDescriptor  implements IPackageDescriptor
	{
		/**
		 * The MultiplePackageResourceDescriptor if this PackageDescriptor is part of a multi-package genmodel.
		 */
		protected final @NonNull IResourceDescriptor resourceDescriptor;

		/**
		 * The namespace URI of the EPackage (e.g. http://www.eclipse.org/emf/2002/Ecore). 
		 */
		protected final @NonNull URI namespaceURI;

		/**
		 * The Java class name of the compiled EPackage (e.g. org.eclipse.emf.ecore.EcorePackage). 
		 */
		protected final @Nullable String className;

		/**
		 * The project-relative URI of the model for the EPackage (e.g. model/Ecore.ecore#/). 
		 */
		private @Nullable URI ecorePackageURI = null;
		
		public PackageDescriptor(@NonNull IResourceDescriptor resourceDescriptor, @NonNull URI nsURI, @Nullable String className) {
			this.resourceDescriptor = resourceDescriptor;
			this.namespaceURI = nsURI;
			this.className = className;
		}

		public void configure(@Nullable ResourceSet resourceSet, @NonNull IResourceLoadStrategy resourceLoadStrategy, @Nullable IConflictHandler conflictHandler) {
			resourceDescriptor.configure(resourceSet, resourceLoadStrategy, conflictHandler);
		}

		public @Nullable String getClassName() {
			return className;
		}

		public @Nullable EFactory getEFactory() {
			return null;
		}

		public @Nullable URI getEcoreModelURI() {
			return ecorePackageURI != null ? ecorePackageURI.trimFragment() : null;
		}

		public @Nullable URI getEcorePackageURI() {
			return ecorePackageURI;
		}

		public IPackageDescriptor getFirstPackageDescriptor() {
			return this;
		}

		public @NonNull URI getNsURI() {
			return namespaceURI;
		}
		
		@SuppressWarnings("null")
		public @NonNull List<? extends IPackageDescriptor> getPackageDescriptors() {
			return Collections.singletonList(this);
		}

		public @NonNull IResourceDescriptor getResourceDescriptor() {
			return resourceDescriptor;
		}

		public boolean hasEcoreModel() {
			return ecorePackageURI != null;
		}

		public void setEcorePackage(@NonNull String ecorePackage) {
			ecorePackageURI = resourceDescriptor.setResourceURI(ecorePackage);
			if (PROJECT_MAP_ADD_EPACKAGE.isActive()) {
				PROJECT_MAP_ADD_EPACKAGE.println(namespaceURI + " => " + ecorePackage + " : " + className);
			}
		}

		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append(namespaceURI);
			s.append(" => ");
			s.append(className);
//			s.append(", ");
//			s.append(genModelURI);
			if (ecorePackageURI != null) {
				s.append(", ");
				s.append(ecorePackageURI);
			}
			return s.toString();
		}
	}

	/**
	 * PackageDescriptor supports lazy class loading and initialization of a
	 * compiled Ecore package. Class loading occurs in the context of the
	 * ProjectMap, which performs classpath scans, so it is assumed that
	 * everything is visible. Re-use in a larger context may require a new
	 * ProjectMap to be created.
	 * 
	 * If a PackageDescriptor is installed under multiple URIs, the resource
	 * created by the first load is shared by all subsequent resolutions.
	 * 
	 * If a PackageDescriptor is set to useModel, the *.ecore file is loaded to
	 * provide the EPackage, rather than the Java className.
	 * 
	 * A PackageDescriptor maintains the declared context of a package which may be shared by
	 * many ResourceSets. In contrast a PackageStatus maintains the actual state of a package
	 * for a particular EPackage.Registry, each of which may have a distinct ResourceLoadStrategy
	 * and consequently may not load the same EPackage.
	 */
	public static final class SinglePackageResourceDescriptor extends AbstractResourceDescriptor
	{
		public SinglePackageResourceDescriptor(@NonNull IProjectDescriptor projectDescriptor, @NonNull URI genModelURI, @NonNull Map<URI, String> nsURI2className) {
			super(projectDescriptor, genModelURI, nsURI2className);
		}

		@Override
		protected @NonNull IResourceLoadStatus createResourceLoadStatus(@Nullable ResourceSet resourceSet) {
			return new SinglePackageResourceLoadStatus(this, resourceSet);
		}

		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
//			s.append(namespaceURI);
//			s.append(" => ");
//			s.append(className);
//			s.append(", ");
			s.append(genModelURI);
//			if (ecorePackageURI != null) {
//				s.append(", ");
//				s.append(ecorePackageURI);
//			}
			return s.toString();
		}
	}

	/**
	 * PackageDescriptor supports lazy class loading and initialization of a
	 * compiled Ecore package. Class loading occurs in the context of the
	 * ProjectMap, which performs classpath scans, so it is assumed that
	 * everything is visible. Re-use in a larger context may require a new
	 * ProjectMap to be created.
	 * 
	 * If a PackageDescriptor is installed under multiple URIs, the resource
	 * created by the first load is shared by all subsequent resolutions.
	 * 
	 * If a PackageDescriptor is set to useModel, the *.ecore file is loaded to
	 * provide the EPackage, rather than the Java className.
	 * 
	 * A PackageDescriptor maintains the declared context of a package which may be shared by
	 * many ResourceSets. In contrast a PackageStatus maintains the actual state of a package
	 * for a particular EPackage.Registry, each of which may have a distinct ResourceLoadStrategy
	 * and consequently may not load the same EPackage.
	 */
	public static final class MultiplePackageResourceDescriptor extends AbstractResourceDescriptor
	{
		
		public MultiplePackageResourceDescriptor(@NonNull ProjectDescriptor projectDescriptor, @NonNull URI genModelURI, @NonNull Map<URI, String> nsURI2className) {
			super(projectDescriptor, genModelURI, nsURI2className);
		}

		@Override
		protected @NonNull IResourceLoadStatus createResourceLoadStatus(@Nullable ResourceSet resourceSet) {
			return new MultiplePackageResourceLoadStatus(this, resourceSet);
		}

		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append("{");
			boolean isFirst = true;
			for (IPackageDescriptor packageDescriptor : packageDescriptors) {
				if (!isFirst) {
					s.append(",");
				}
				s.append(packageDescriptor.getNsURI());
				isFirst = false;
			}
			s.append("} => ");
//			s.append(className);
//			s.append(", ");
			s.append(genModelURI);
//			if (ecorePackageURI != null) {
//				s.append(", ");
//				s.append(ecorePackageURI);
//			}
			return s.toString();
		}
	}

	/**
	 * PluginReader provides the SAX callbacks to support reading the
	 * org.eclipse.emf.ecore.generated_package extension point in a plugin.xml
	 * file and activating the GenModelReader to process the
	 * ecorePackage locations and invoking {@link addGenModel()} for each
	 * encounter.
	 */
	protected static class PluginReader extends DefaultHandler
	{
		public static final @NonNull String pluginTag = "plugin";
		public static final @NonNull String extensionTag = "extension";
		public static final @NonNull String pointTag = "point";
		public static final @NonNull String packageTag = "package";
		public static final @NonNull String extensionPointAttribute = "org.eclipse.emf.ecore.generated_package";
		public static final @NonNull String uriAttribute = "uri";
		public static final @NonNull String classAttribute = "class";
		public static final @NonNull String genModelAttribute = "genModel";

		protected final JarFile jarFile;
		protected final IProjectDescriptor projectDescriptor;
		private int pluginCount = 0;
		private int extensionCount = 0;
		private boolean inPoint = false;
		private int packageCount = 0;
		private @NonNull Map<String, GenModelReader> genModelReaders = new HashMap<String, GenModelReader>();
		private @Nullable Map<String, Map<URI, String>> genModelURI2nsURI2className = null;

		private PluginReader(@Nullable IProjectDescriptor projectDescriptor) {
			this.jarFile = null;
			this.projectDescriptor = projectDescriptor;
		}

		public PluginReader(@NonNull JarFile jarFile, @NonNull IProjectDescriptor projectDescriptor) {
			this.jarFile = jarFile;
			this.projectDescriptor = projectDescriptor;
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
			Map<String, Map<URI, String>> genModelURI2nsURI2className2 = genModelURI2nsURI2className;
			if (genModelURI2nsURI2className2 != null) {
				for (@SuppressWarnings("null")@NonNull String genModel : genModelURI2nsURI2className2.keySet()) {
					@SuppressWarnings("null")@NonNull Map<URI, String> nsURI2className = genModelURI2nsURI2className2.get(genModel);
					IResourceDescriptor resourceDescriptor = projectDescriptor.createResourceDescriptor(genModel, nsURI2className);
					GenModelReader genModelReader = new GenModelReader(resourceDescriptor);
					genModelReaders.put(genModel, genModelReader);
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (pluginCount == 1) {
				if (pluginTag.equals(qName)) {
					pluginCount--;
				}
				if (extensionCount == 1) {
					if (extensionTag.equals(qName)) {
						extensionCount--;
					}
					if (packageCount == 1) {
						if (packageTag.equals(qName)) {
							packageCount--;
						}
					}
				}
			}
		}

		public void scanContents(SAXParser saxParser) throws SAXParseException {
			for (String genModel : genModelReaders.keySet()) {
				GenModelReader genModelReader = genModelReaders.get(genModel);
				URI locationURI = projectDescriptor.getLocationURI();
				URI genModelURI = URI.createURI(genModel).resolve(locationURI);
				InputStream inputStream = null;
				try {
					if (jarFile != null) {
						ZipEntry entry = jarFile.getEntry(genModel);
						if (entry != null) {
							inputStream = jarFile.getInputStream(entry);
						}
					} else {
						inputStream = new FileInputStream(genModelURI.toString().substring(5)); // Lose file:
					}
					if (inputStream != null) {
						saxParser.parse(inputStream, genModelReader);
					}
				} catch (Exception e) {
					throw new SAXParseException("Failed to parse " + locationURI, null, e);
				} finally {
					try {
						if (inputStream != null) {
							inputStream.close();
						}
					} catch (IOException e) {
					}
				}
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			if (pluginCount == 0) {
				if (pluginTag.equals(qName)) {
					pluginCount++;
				}
			} else if (pluginCount == 1) {
				if ((extensionCount == 0) && extensionTag.equals(qName)) {
					extensionCount++;
					inPoint = extensionPointAttribute.equals(attributes.getValue(pointTag));
				} else if ((extensionCount == 1) && inPoint) {
					if ((packageCount == 0) && packageTag.equals(qName)) {
						packageCount++;
						String className = attributes.getValue(classAttribute);
						@SuppressWarnings("null")@NonNull URI nsURI = URI.createURI(attributes.getValue(uriAttribute));
						String genModel = attributes.getValue(genModelAttribute);
						if ((genModel != null) && (className != null)) {
							Map<String, Map<URI, String>> genModelURI2nsURI2className2 = genModelURI2nsURI2className;
							if (genModelURI2nsURI2className2 == null) {
								genModelURI2nsURI2className = genModelURI2nsURI2className2 = new HashMap<String, Map<URI, String>>();
							}
							Map<URI, String> nsURI2className = genModelURI2nsURI2className2.get(genModel);
							if (nsURI2className == null) {
								nsURI2className = new HashMap<URI, String>();
								genModelURI2nsURI2className2.put(genModel, nsURI2className);
							}
							nsURI2className.put(nsURI, className);
						}
					}
				}
			}
		}
	}

	/**
	 * GenModelReader provides the SAX callbacks to support reading
	 * the genPackages element in a genmodel file and invoking {@link
	 * addEcorePackage()} for each encounter.
	 */
	protected static class GenModelReader extends DefaultHandler
	{
		public static final @NonNull String genmodelTag = "genmodel:GenModel";
		public static final @NonNull String genPackagesTag = "genPackages";
		public static final @NonNull String nestedGenPackagesTag = "nestedGenPackages";
		public static final @NonNull String ecorePackageTag = "ecorePackage";
		public static final @NonNull String ecorePackageAttribute = "ecorePackage";
		public static final @NonNull String hrefAttribute = "href";

		protected final @NonNull IResourceDescriptor resourceDescriptor;
		protected final @NonNull IProjectDescriptor projectDescriptor;
		protected final @NonNull Map<String, IPackageDescriptor> nsURI2packageDescriptor = new HashMap<String, IPackageDescriptor>();
		protected final @NonNull URI genModelURI;
		protected final @NonNull List<String> ecorePackages = new ArrayList<String>();

		private @NonNull Stack<String> elements = new Stack<String>();

		/**
		 * A simple public static method that may be used to force class
		 * initialization.
		 */
		public static void initStatics() {}

		public GenModelReader(@NonNull IResourceDescriptor resourceDescriptor) {
			this.resourceDescriptor = resourceDescriptor;
			this.projectDescriptor = resourceDescriptor.getProjectDescriptor();
			this.genModelURI = resourceDescriptor.getGenModelURI();
			for (@SuppressWarnings("null")@NonNull IPackageDescriptor packageDescriptor : resourceDescriptor.getPackageDescriptors()) {
				add(packageDescriptor);
			}
		}

		private void add(@NonNull IPackageDescriptor packageDescriptor) {
			nsURI2packageDescriptor.put(packageDescriptor.getNsURI().toString(), packageDescriptor);
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
			try {
				resourceDescriptor.setEcore(ecorePackages, nsURI2packageDescriptor);
			}
			catch (Exception e) {
				logger.warn("Failed lo read " + genModelURI, e);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			elements.pop();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			 int size = elements.size();
			 if (genPackagesTag.equals(qName)) {
				if ((size == 1) && genmodelTag.equals(elements.elementAt(0))) {
					String ecorePackage = attributes.getValue(ecorePackageAttribute);
					if (ecorePackage != null) {
						ecorePackages.add(ecorePackage);
					}
				}
			}
			else if (nestedGenPackagesTag.equals(qName)) {
				if ((size > 1) && genPackagesTag.equals(elements.elementAt(1)) && genmodelTag.equals(elements.elementAt(0))) {
					String ecorePackage = attributes.getValue(ecorePackageAttribute);
					if (ecorePackage != null) {
						ecorePackages.add(ecorePackage);
					}
				}
			}
			else if (ecorePackageTag.equals(qName)) {
				if ((size >= 2) && genPackagesTag.equals(elements.elementAt(1)) && genmodelTag.equals(elements.elementAt(0))) {
					String topElement = elements.elementAt(size-1);
					if (genPackagesTag.equals(topElement) || nestedGenPackagesTag.equals(topElement)) {
						String ecorePackage = attributes.getValue(hrefAttribute);
						if (ecorePackage != null) {
							ecorePackages.add(ecorePackage);
						}
					}
				}
			}
			elements.push(qName);
		}
	}

	public static class ProjectDescriptor implements IProjectDescriptor
	{
		/**
		 * The overall ProjectMap
		 */
		protected final @NonNull StandaloneProjectMap projectMap;

		/**
		 * The project/bundle/plugin name; e.g. "org.eclipse.emf.ecore"
		 */
		protected final @NonNull String name;

		/**
		 * The resolveable location.
		 */
		protected final @NonNull URI locationURI;

		/**
		 * Map from local Model URI to lazy EPackageDescriptor. e.g. from "model/Ecore.ecore".
		 */
		private @Nullable Map<URI, IPackageDescriptor> nsURI2packageDescriptor = null;
		private @Nullable Map<URI, IResourceDescriptor> genModelURI2packageDescriptor = null;

		public ProjectDescriptor(@NonNull StandaloneProjectMap projectMap, @NonNull String name, @NonNull URI locationURI) {
			this.projectMap =  projectMap;
			this.name = name;
			this.locationURI = locationURI;
		}

		public void addPackageDescriptor(@NonNull IPackageDescriptor packageDescriptor) {
			Map<URI, IPackageDescriptor> nsURI2packageDescriptor2 = nsURI2packageDescriptor;
			if (nsURI2packageDescriptor2 == null) {
				nsURI2packageDescriptor = nsURI2packageDescriptor2 = new HashMap<URI, IPackageDescriptor>();
			}
			nsURI2packageDescriptor2.put(packageDescriptor.getNsURI(), packageDescriptor);
		}

		public void addResourceDescriptor(@NonNull IResourceDescriptor resourceDescriptor) {
			Map<URI, IResourceDescriptor> genModelURI2packageDescriptor2 = genModelURI2packageDescriptor;
			if (genModelURI2packageDescriptor2 == null) {
				genModelURI2packageDescriptor = genModelURI2packageDescriptor2 = new HashMap<URI, IResourceDescriptor>();
			}
			genModelURI2packageDescriptor2.put(resourceDescriptor.getGenModelURI(), resourceDescriptor);
		}

		public void configure(@Nullable ResourceSet resourceSet, @NonNull IResourceLoadStrategy resourceLoadStrategy, @Nullable IConflictHandler conflictHandler) {
			if (genModelURI2packageDescriptor != null) {
				for (IResourceDescriptor resourceDescriptor : genModelURI2packageDescriptor.values()) {
					resourceDescriptor.configure(resourceSet, resourceLoadStrategy, conflictHandler);
				}
			}
		}
		
		public @NonNull IResourceDescriptor createResourceDescriptor(@NonNull String genModel, @NonNull Map<URI, String> nsURI2className) {
			URI absoluteGenModelURI = URI.createURI(genModel).resolve(locationURI);
			@SuppressWarnings("null")@NonNull URI projectGenModelURI = absoluteGenModelURI.deresolve(locationURI, true, true, true);
			if (nsURI2className.size() <= 1) {
				return new SinglePackageResourceDescriptor(this, projectGenModelURI, nsURI2className);
			}
			else {
				return new MultiplePackageResourceDescriptor(this, projectGenModelURI, nsURI2className);
			}
		}

		public @NonNull URI getLocationURI() {
			return locationURI;
		}

		@SuppressWarnings("null")
		public @NonNull URI getLocationURI(@NonNull String projectRelativeFileName) {
			return URI.createURI(projectRelativeFileName).resolve(locationURI);
		}

		public @NonNull File getLocationFile(@NonNull String projectRelativeFileName) {
			return new File(getLocationURI(projectRelativeFileName).toFileString());
		}

		public @NonNull String getName() {
			return name;
		}

		public @Nullable IPackageDescriptor getPackageDescriptor(@NonNull URI nsURI) {
			return nsURI2packageDescriptor != null ? nsURI2packageDescriptor.get(nsURI) : null;
		}

		public @Nullable Collection<IResourceDescriptor> getResourceDescriptors() {
			return genModelURI2packageDescriptor != null ? genModelURI2packageDescriptor.values() : null;
		}

		@SuppressWarnings("null")
		public @NonNull URI getPlatformPluginURI() {
			return URI.createPlatformPluginURI("/" + name + "/", true);
		}

		@SuppressWarnings("null")
		public @NonNull URI getPlatformPluginURI(@NonNull String projectRelativeFileName) {
			return URI.createURI(projectRelativeFileName).resolve(getPlatformPluginURI());
		}

		@SuppressWarnings("null")
		public @NonNull URI getPlatformResourceURI() {
			return URI.createPlatformResourceURI("/" + name + "/", true);
		}

		@SuppressWarnings("null")
		public @NonNull URI getPlatformResourceURI(@NonNull String projectRelativeFileName) {
			return URI.createURI(projectRelativeFileName).resolve(getPlatformResourceURI());
		}

		public @NonNull StandaloneProjectMap getProjectMap() {
			return projectMap;
		}

		public void initializeGenModelLocationMap(@NonNull Map<URI, IPackageDescriptor> nsURI2package) {
			Collection<IResourceDescriptor> resourceDescriptors = getResourceDescriptors();
			if (resourceDescriptors != null) {
				Map<String, URI> ePackageNsURIToGenModelLocationMap = EMF_2_9.EcorePlugin.getEPackageNsURIToGenModelLocationMap(false);
				for (IResourceDescriptor resourceDescriptor : resourceDescriptors) {
					URI genModelURI = resourceDescriptor.getGenModelURI();
					URI resolvedURI = genModelURI.resolve(locationURI);
					for (IPackageDescriptor packageDescriptor : resourceDescriptor.getPackageDescriptors()) {
						URI nsURI = packageDescriptor.getNsURI();
						String nsURIstring = nsURI.toString();
						ePackageNsURIToGenModelLocationMap.put(nsURIstring, resolvedURI);
						nsURI2package.put(nsURI, packageDescriptor);
						if (PROJECT_MAP_ADD_GEN_MODEL.isActive()) {
							PROJECT_MAP_ADD_GEN_MODEL.println(nsURI + " => " + resolvedURI);
						}
					}
				}
			}
		}

		public void initializePlatformResourceMap() {
			Map<String, URI> platformResourceMap = EcorePlugin.getPlatformResourceMap();
			platformResourceMap.put(name, locationURI);
		}

		public void initializeURIMap(@NonNull Map<URI, URI> uriMap) {
			URI resourceURI = getPlatformResourceURI();
			URI pluginURI = getPlatformPluginURI();
			uriMap.put(resourceURI, locationURI);
			uriMap.put(pluginURI, locationURI);
			if (PROJECT_MAP_ADD_URI_MAP.isActive()) {
				PROJECT_MAP_ADD_URI_MAP.println(resourceURI + " => " + locationURI);
				PROJECT_MAP_ADD_URI_MAP.println(pluginURI + " => " + locationURI);
			}
		}

		@Override
		public String toString() {
			return name + " => " + locationURI.toString();
		}
	}
	
	public static interface IConflictHandler
	{
		/**
		 * Return the EPackage to be used for a namespace URI reference after the model EPackage has already been used.
		 */
		@Nullable EPackage handleConflictingNsURI(@NonNull IPackageLoadStatus packageLoadStatus);

		/**
		 * Return the EPackage to be used for a model URI reference after the namespace EPackage has already been used.
		 */
		@Nullable EPackage handleConflictingModelURI(@NonNull IResourceLoadStatus packageLoadStatus, @NonNull EPackage ePackage);
	}
	
	/**
	 * MapToFirstConflictHandler resolves conflicts by returning the first loaded EPackage.
	 */
	public static class MapToFirstConflictHandler implements IConflictHandler
	{
		public static final @NonNull IConflictHandler INSTANCE = new MapToFirstConflictHandler();
		
		public @Nullable EPackage handleConflictingNsURI(@NonNull IPackageLoadStatus packageLoadStatus) {
			return packageLoadStatus.getFirstEPackage();
		}

		public @Nullable EPackage handleConflictingModelURI(@NonNull IResourceLoadStatus packageLoadStatus, @NonNull EPackage ePackage) {
			return ePackage;
		}
	}
	
	/**
	 * MapToFirstConflictHandler resolves conflicts by returning the first loaded EPackage.
	 */
	public static class MapToFirstConflictHandlerWithLog implements IConflictHandler
	{
		public static final @NonNull IConflictHandler INSTANCE = new MapToFirstConflictHandlerWithLog();
		
		public @Nullable EPackage handleConflictingNsURI(@NonNull IPackageLoadStatus packageLoadStatus) {
			EPackage firstEPackage = packageLoadStatus.getFirstEPackage();
			IPackageDescriptor packageDescriptor = packageLoadStatus.getPackageDescriptor();
			logger.error("Conflicting access to '" + packageDescriptor.getNsURI() + "' already accessed as '" + packageDescriptor.getResourceDescriptor().getPlatformResourceURI() + "' or '" + packageDescriptor.getResourceDescriptor().getPlatformPluginURI() + "'");
			packageLoadStatus.getResourceLoadStatus().setConflictHandler(MapToFirstConflictHandler.INSTANCE);
			return firstEPackage;
		}

		public @Nullable EPackage handleConflictingModelURI(@NonNull IResourceLoadStatus resourceLoadStatus, @NonNull EPackage ePackage) {
			IResourceDescriptor resourceDescriptor = resourceLoadStatus.getResourceDescriptor();
			logger.error("Conflicting access to '" + resourceDescriptor.getPlatformResourceURI() +
				"' or '" + resourceDescriptor.getPlatformPluginURI() +
				"' already accessed as '" + ePackage.getNsURI() + "'");
			resourceLoadStatus.setConflictHandler(MapToFirstConflictHandler.INSTANCE);
			return ePackage;
		}
	}
	
	/**
	 * Return any StandaloneProjectMap already installed as an adapter on a
	 * <tt>resourceSet</tt>. Returns null if there is no such adapter.
	 */
	public static @Nullable StandaloneProjectMap findAdapter(@NonNull ResourceSet resourceSet) {
		return (StandaloneProjectMap) EcoreUtil.getAdapter(
			resourceSet.eAdapters(), StandaloneProjectMap.class);
	}

	/**
	 * Return the StandaloneProjectMap already installed as an adapter on a
	 * <tt>resourceSet</tt> if one exists, else creates, installs, initializes
	 * and returns a new StandaloneProjectMap.
	 */
	public static @NonNull StandaloneProjectMap getAdapter(@NonNull ResourceSet resourceSet) {
		StandaloneProjectMap adapter = findAdapter(resourceSet);
		if (adapter == null) {
			adapter = new StandaloneProjectMap();
//			resourceSet.eAdapters().add(adapter);
			adapter.initializeResourceSet(resourceSet);
		}
		return adapter;
	}

	public static void dispose(@NonNull ResourceSet resourceSet) {
		StandaloneProjectMap projectMap = findAdapter(resourceSet);
		if (projectMap != null) {
			projectMap.unload(resourceSet);
		}
	}

	/**
	 * Return the EPackage.Registry for a resourceSet or the Global
	 * {@link EPackage.Registry.INSTANCE} if resourceSet is null.
	 */
	public static @NonNull EPackage.Registry getPackageRegistry(@Nullable ResourceSet resourceSet) {
		if (resourceSet == null) {
			@SuppressWarnings("null")
			@NonNull
			Registry globalRegistry = EPackage.Registry.INSTANCE;
			return globalRegistry;
		} else {
			@SuppressWarnings("null")
			@NonNull
			Registry packageRegistry = resourceSet.getPackageRegistry();
			return packageRegistry;
		}
	}

	/**
	 * Return the Resource.Factory.Registry for a resourceSet or the Global
	 * {@link Resource.Factory.Registry.INSTANCE} if resourceSet is null.
	 */
	public static Resource.Factory.Registry getResourceFactoryRegistry(
			@Nullable ResourceSet resourceSet) {
		return resourceSet != null
			? resourceSet.getResourceFactoryRegistry()
			: Resource.Factory.Registry.INSTANCE;
	}

	/**
	 * Return the URIConverter for a resourceSet or the Global
	 * {@link URIConverter.INSTANCE} if resourceSet is null.
	 */
	@SuppressWarnings("null")
	public static @NonNull URIConverter getURIConverter(@Nullable ResourceSet resourceSet) {
		return resourceSet != null ? resourceSet.getURIConverter() : URIConverter.INSTANCE;
	}

	/**
	 * Return the URI Map for a resourceSet or the Global
	 * {@link URIConverter.URI_MAP} if resourceSet is null.
	 */
	@SuppressWarnings("null")
	public static @NonNull Map<URI, URI> getURIMap(@Nullable ResourceSet resourceSet) {
		return resourceSet != null ? resourceSet.getURIConverter().getURIMap() : URIConverter.URI_MAP;
	}

	/**
	 * A simple public static method that may be used to force class
	 * initialization.
	 */
	public static void initStatics() {
		GenModelReader.initStatics();
		new PluginReader(null);
	}

	/**
	 * Activate any ResourceSetImpl.uriResourceMap so that repeated lookups use
	 * a hash rather than linear search.
	 */
	public static void initializeURIResourceMap(@Nullable ResourceSet resourceSet) {
		if (resourceSet instanceof ResourceSetImpl) {
			ResourceSetImpl resourceSetImpl = (ResourceSetImpl) resourceSet;
			Map<URI, Resource> uriResourceMap = resourceSetImpl.getURIResourceMap();
			if (uriResourceMap == null) {
				resourceSetImpl.setURIResourceMap(new HashMap<URI, Resource>());
			}
		}
	}

	public StandaloneProjectMap() {
		super();
	}

	/**
	 * Exceptions encountered during processing as a map from File to Exception.
	 */
	private @Nullable Map<File, Exception> exceptionMap = null;

	/**
	 * The map of bundle/project name to project descriptor.
	 */
	private Map<String, IProjectDescriptor> project2descriptor = null;

	protected boolean initializedPlatformResourceMap = false;

	/**
	 * The map of package nsURI to package descriptor.
	 */
	protected @Nullable Map<URI, IPackageDescriptor> nsURI2package = null;

	/**
	 * The map of document URI to resource descriptor.
	 */
	protected @Nullable Map<URI, IResourceDescriptor> uri2resource = null;

	public void addResourceDescriptor(@NonNull IResourceDescriptor resourceDescriptor) {
		Map<URI, IResourceDescriptor> uri2resource2 = uri2resource;
		if (uri2resource2 == null) {
			uri2resource = uri2resource2 = new HashMap<URI, IResourceDescriptor>();
		}
		uri2resource2.put(resourceDescriptor.getPlatformPluginURI(), resourceDescriptor);
		uri2resource2.put(resourceDescriptor.getPlatformResourceURI(), resourceDescriptor);
	}

	/**
	 * Configure the PackageRegistry associated with ResourceSet to use a resourceLoadStrategy and conflictHandler when
	 * resolving namespace ansd platform URIs.
	 */
	public void configure(@Nullable ResourceSet resourceSet, @NonNull IResourceLoadStrategy resourceLoadStrategy, @Nullable IConflictHandler conflictHandler) {
		Map<String, IProjectDescriptor> projectDescriptors = getProjectDescriptors();
		if (projectDescriptors != null) {
			for (IProjectDescriptor projectDescriptor : projectDescriptors.values()) {
				projectDescriptor.configure(resourceSet, resourceLoadStrategy, conflictHandler);
			}
		}
	}

	protected @NonNull IProjectDescriptor createProjectDescriptor(@NonNull String projectName, @NonNull URI locationURI) {
		return new ProjectDescriptor(this, projectName, locationURI);
	}

	/**
	 * Return the IPackageDescriptor for a given nsURI.
	 */
	public @Nullable IPackageDescriptor getPackageDescriptor(@NonNull URI nsURI) {
		return nsURI2package != null ? nsURI2package.get(nsURI) : null;
	}

	/**
	 * Return the IProjectDescriptor for a given project or bundle name.
	 */
	public @Nullable IProjectDescriptor getProjectDescriptor(@NonNull String projectName) {
		Map<String, IProjectDescriptor> projectDescriptors = getProjectDescriptors();
		if (projectDescriptors == null) {
			return null;
		}
		return projectDescriptors.get(projectName);
	}

	protected @NonNull IProjectDescriptor getProjectDescriptorInternal(@NonNull URI platformURI) {
		@SuppressWarnings("null")@NonNull String projectName = platformURI.segment(1);
		getProjectDescriptors();
		IProjectDescriptor projectDescriptor = project2descriptor.get(projectName);
		if (projectDescriptor == null) {
			@SuppressWarnings("null")@NonNull URI locationURI = platformURI.trimSegments(platformURI.segmentCount() - 2).appendSegment("");
			projectDescriptor = createProjectDescriptor(projectName, locationURI);
			project2descriptor.put(projectName, projectDescriptor);
		}
		return projectDescriptor;
	}

	/**
	 * Return the mapping of problem files to exceptions, or null if not yet
	 * computed or if no exceptions thrown.
	 */
	public @Nullable Map<File, Exception> getExceptionMap() {
		return exceptionMap;
	}

	/**
	 * Return the resolveable URI for a given project or bundle name.
	 */
	public @Nullable URI getLocation(@NonNull String projectName) {
		Map<String, IProjectDescriptor> projectDescriptors = getProjectDescriptors();
		if (projectDescriptors == null) {
			return null;
		}
		IProjectDescriptor projectDescriptor = projectDescriptors.get(projectName);
		if (projectDescriptor == null) {
			return null;
		}
		return projectDescriptor.getLocationURI();
	}

	/**
	 * Return the mapping of project name or bundle name, as defined in a
	 * manifest file to the location of that project as determined by scanning
	 * the classpath.
	 * <p>
	 * e.g. entries such as <br>
	 * org.antlr.runtime =>
	 * archive:file:/C:/Tools/Eclipse/3.7.1/plugins/org.antlr
	 * .runtime_3.2.0.v201101311130.jar!/ <br>
	 * org.eclipse.ocl.examples.common =>
	 * file:/C:/GIT/org.eclipse.ocl/examples/org.eclipse.ocl.examples.common/
	 * <p>
	 * Any problems arising while creating the project map are gathered into the
	 * exception map accessible using {@link #getExceptionMap()}. An overall
	 * problem may be attributed to the null file.
	 */
	protected synchronized @Nullable Map<String, IProjectDescriptor> getProjectDescriptors() {
		Map<String, IProjectDescriptor> project2descriptor2 = project2descriptor;
		if (project2descriptor2 == null) {
			project2descriptor = project2descriptor2 = new HashMap<String, IProjectDescriptor>();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			try {
				SAXParser saxParser = factory.newSAXParser();
				if (saxParser != null) {
					scanClassPath(project2descriptor2, saxParser);
				}
			} catch (Exception e) {
				logException(null, e);
				return null;
			}
		}
		return project2descriptor2;
	}

	/**
	 * Initialize the
	 * {@link EcorePlugin#getEPackageNsURIToGenModelLocationMap()} so that in a
	 * standalone environment the locations of all genmodels are available.
	 * <p>
	 * Initialization is only necessary once and for a standalone environment.
	 * If <tt>force</tt> is true a re-initialization or plugin initialization
	 * may be forced.
	 */
	public synchronized void initializeGenModelLocationMap(boolean force) {
		if (force || (nsURI2package == null)) {
			Map<URI, IPackageDescriptor> nsURI2package2 = new HashMap<URI, IPackageDescriptor> ();
			nsURI2package = nsURI2package2;
			Map<String, IProjectDescriptor> projectDescriptors = getProjectDescriptors();
			if (projectDescriptors != null) {
				for (IProjectDescriptor projectDescriptor : projectDescriptors.values()) {
					projectDescriptor.initializeGenModelLocationMap(nsURI2package2);
				}
			}
		}
	}

	/**
	 * Install lazy EPackageDescriptors in the EPackage.Registry for all
	 * registered packages and their platform:/plugin and platform:/resource
	 * synonyms, which are determined by examining the genPackages.ecorePackage
	 * attribute in all genModels.
	 */
	public synchronized void initializePackageRegistry(@Nullable ResourceSet resourceSet) {
		getProjectDescriptors();
		for (IProjectDescriptor projectDescriptor : project2descriptor.values()) {
			Collection<IResourceDescriptor> resourceDescriptors = projectDescriptor.getResourceDescriptors();
			if (resourceDescriptors != null) {
				for (IResourceDescriptor resourceDescriptor : resourceDescriptors) {
					assert resourceDescriptor != null;
					if (resourceDescriptor.hasEcoreModel()) {
						IResourceLoadStatus resourceLoadStatus = resourceDescriptor.getResourceLoadStatus(resourceSet);
						resourceLoadStatus.setConflictHandler(MapToFirstConflictHandlerWithLog.INSTANCE);
					}
				}
			}
		}
	}

	/**
	 * Initialize the {@link EcorePlugin#getPlatformResourceMap()} so that in a
	 * standalone environment and in conjunction with {@link
	 * initializeURIMap(URIConverter)} URIs such as
	 * <tt>platform:/resource/<i>project</i></tt> and
	 * <tt>platform:/plugin/<i>project</i></tt> are useable.
	 * <p>
	 * Initialization is only necessary once and for a standalone environment.
	 * If <tt>force</tt> is true a re-initialization or plugin initialization
	 * may be forced.
	 */
	public synchronized void initializePlatformResourceMap(boolean force) {
		if (force || !initializedPlatformResourceMap) {
			initializedPlatformResourceMap = true;
			getProjectDescriptors();
			for (IProjectDescriptor projectDescriptor : project2descriptor.values()) {
				projectDescriptor.initializePlatformResourceMap();
			}
		}
	}

	/**
	 * Ensure that both the {@link EcorePlugin#getPlatformResourceMap()} and
	 * {@link ResourceSet#getURIConverter()} are initialized so that
	 * <tt>platform:/resource/<i>project</i></tt> and
	 * <tt>platform:/plugin/<i>project</i></tt> are useable..
	 * 
	 * A null ResourceSet may be used to provoke initialization of the global
	 * EPackage.Registry.INSTANCE and URIConverter.URI_MAP.
	 */
	public void initializeResourceSet(@Nullable ResourceSet resourceSet) {
		initializeURIResourceMap(resourceSet);
		initializePlatformResourceMap(false);
		initializeURIMap(resourceSet);
		initializeGenModelLocationMap(false);
		initializePackageRegistry(resourceSet);
		if (resourceSet != null) {
			resourceSet.eAdapters().add(this);
		}
	}

	/**
	 * Initialize the uriMap of a uriConverter so that each of
	 * <tt>platform:/resource/<i>project</i></tt> and
	 * <tt>platform:/plugin/<i>project</i></tt> resolve the workspace project
	 * resource else the plugin bundle for use in either standalone or plugin
	 * environment.
	 * <p>
	 * Note that in a plugin environment, a single <tt>platform:/resource/</tt>
	 * to <tt>platform:/plugin/</tt> mapping is sufficient since
	 * <tt>platform:/plugin/</tt> is directly resolveable by the Eclipse
	 * Platform.
	 */
	public synchronized void initializeURIMap(@Nullable ResourceSet resourceSet) {
		getProjectDescriptors();
		Map<URI, URI> uriMap = getURIMap(resourceSet);
		for (String project : project2descriptor.keySet()) {
			IProjectDescriptor projectDescriptor = project2descriptor.get(project);
			projectDescriptor.initializeURIMap(uriMap);
		}
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return (type instanceof Class<?>)
			&& ((Class<?>) type).isAssignableFrom(StandaloneProjectMap.class);
	}

	protected void logException(@Nullable File file, @NonNull Exception e) {
		Map<File, Exception> exceptionMap2 = exceptionMap;
		if (exceptionMap2 == null) {
			exceptionMap = exceptionMap2 = new HashMap<File, Exception>();
		}
		exceptionMap2.put(file, e);
	}

	/**
	 * Does nothing; clients may override so that it does something.
	 */
	@Override
	public void notifyChanged(Notification notification)
	{
		Object notifier = notification.getNotifier();
		if (notifier instanceof ResourceSet) {
			int eventType = notification.getEventType();
			int featureID = notification.getFeatureID(ResourceSet.class);
			if (featureID == ResourceSet.RESOURCE_SET__RESOURCES) {
				if (eventType == Notification.ADD) {
					Object newValue = notification.getNewValue();
					if (newValue instanceof Resource) {
						notifyAddedResource((ResourceSet)notifier, (Resource)newValue);
					}
				}
				else if (eventType == Notification.ADD_MANY) {
					Object newValues =  notification.getNewValue();
					if (newValues instanceof Iterable<?>) {
						for (Object newValue : (Iterable<?>)newValues){
							if (newValue instanceof Resource) {
								notifyAddedResource((ResourceSet)notifier, (Resource)newValue);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * When a new Resource is added to a watched ResourceSet notify the resourceDescriptor that there is a new
	 * (ResourceSet, Resource) pair so that it install both platform:/plugin and platform:/resource
	 * entries in the ResourceSet's uriResourceMap and install a listener to detect when the Resource is loaded.
	 */
	protected void notifyAddedResource(@NonNull ResourceSet resourceSet, @NonNull Resource resource) {
//		resource.eAdapters().add(this);
		if (resourceSet instanceof ResourceSetImpl) {
			Map<URI, IResourceDescriptor> uri2resource2 = uri2resource;
			if (uri2resource2 != null) {
				URI uri = resource.getURI();
				IResourceDescriptor resourceDescriptor = uri2resource2.get(uri);
				if (resourceDescriptor != null) {
					resourceDescriptor.addedModel(resourceSet, resource);
				}
			}
		}
	}

	protected @Nullable IProjectDescriptor registerBundle(@NonNull File file, @NonNull SAXParser saxParser) {
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(file);
			Manifest manifest = jarFile.getManifest();
			if (manifest == null) {
				return null;
			}
			String project = manifest.getMainAttributes().getValue("Bundle-SymbolicName");
			if (project != null) {
				final int indexOf = project.indexOf(';');
				if (indexOf > 0) {
					project = project.substring(0, indexOf);
				}
				IProjectDescriptor projectDescriptor = project2descriptor.get(project);
				if (projectDescriptor != null)
					return projectDescriptor;
				String path = "archive:" + file.toURI() + "!/";
				@SuppressWarnings("null")@NonNull URI locationURI = URI.createURI(path);
				assert project != null;
				projectDescriptor = createProjectDescriptor(project, locationURI);
				project2descriptor.put(project, projectDescriptor);
				ZipEntry entry = jarFile.getEntry("plugin.xml");
				if (entry != null) {
					InputStream inputStream = jarFile.getInputStream(entry);
					try {
						PluginReader pluginReader = new PluginReader(jarFile, projectDescriptor);
						saxParser.parse(inputStream, pluginReader);
						pluginReader.scanContents(saxParser);
					} finally {
						inputStream.close();
					}
				}
				return projectDescriptor;
			}
		} catch (ZipException e) {
			logException(file, new WrappedException("Could not open Jar file " + file.getAbsolutePath() + ".", e));
		} catch (Exception e) {
			logException(file, e);
		} finally{
			if (jarFile != null) {
				try {
					jarFile.close();
				} catch (IOException e) {}
			}
		}
		return null;
	}

	protected @Nullable IProjectDescriptor registerProject(@NonNull File file) {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
			String project = document.getDocumentElement().getElementsByTagName("name").item(0).getTextContent();
			if (project != null) {
				@SuppressWarnings("null")@NonNull URI locationURI = URI.createFileURI(file.getParentFile().getCanonicalPath() + File.separator);
				IProjectDescriptor projectDescriptor = createProjectDescriptor(project, locationURI);
				project2descriptor.put(project, projectDescriptor);
				return projectDescriptor;
			}
		} catch (Exception e) {
			logException(file, new WrappedException("Couldn't read " + file, e));
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	protected void scanClassPath(@NonNull Map<String, IProjectDescriptor> projectDescriptors, @NonNull SAXParser saxParser) {
		String property = System.getProperty("java.class.path");
		String separator = System.getProperty("path.separator");
		if (property != null) {
			String[] entries = property.split(separator);
			for (String entry : entries) {
				File fileEntry = new File(entry);
				try {
					File f = fileEntry.getCanonicalFile();
					if (f.getPath().endsWith(".jar")) {
						registerBundle(f, saxParser);
					} else if (!scanFolder(f, saxParser, new HashSet<String>(), 0)) {
						// eclipse bin folder?
						File parentFile = f.getParentFile();
						File dotProject = new File(parentFile, ".project");
						if (dotProject.exists()) {
							IProjectDescriptor projectDescriptor = registerProject(dotProject);
							if (projectDescriptor != null) {
								File plugIn = new File(parentFile, "plugin.xml");
								if (plugIn.exists()) {
									PluginReader pluginReader = new PluginReader(projectDescriptor);
									saxParser.parse(plugIn, pluginReader);
									pluginReader.scanContents(saxParser);
								}
							}
						}
					}
				} catch (Exception e) {
					logException(fileEntry, e);
				}
			}
		}
	}

	protected boolean scanFolder(@NonNull File f, @NonNull SAXParser saxParser, @NonNull Set<String> alreadyVisited, int depth) {
		try {
			if (!alreadyVisited.add(f.getCanonicalPath()))
				return true;
		} catch (Exception e) {
			logException(f, e);
			return true;
		}
		File[] files = f.listFiles();
		boolean containsProject = false;
		File dotProject = null;
		if (files != null) {
			for (File file : files) {
				if (file.exists() && file.isDirectory() && (depth < 2) && !file.getName().startsWith(".")) {
					containsProject |= scanFolder(file, saxParser, alreadyVisited, depth + 1);
				} else if (".project".equals(file.getName())) {
					dotProject = file;
				} else if (file.getName().endsWith(".jar")) {
					registerBundle(file, saxParser);
				}
			}
		}
		if (!containsProject && dotProject != null)
			registerProject(dotProject);
		return containsProject || dotProject != null;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (project2descriptor != null) {
			List<String> projectNames = new ArrayList<String>(project2descriptor.keySet());
			Collections.sort(projectNames);
			for (String projectName : projectNames) {
				if (s.length() > 0) {
					s.append("\n");
				}
				s.append(projectName);
				s.append(" => ");
				s.append(project2descriptor.get(projectName).getLocationURI());
			}
		}
		return s.toString();
	}

	public void unload(@NonNull ResourceSet resourceSet) {
		if (project2descriptor != null) {
			for (IProjectDescriptor projectDescriptor : project2descriptor.values()) {
				Collection<IResourceDescriptor> resourceDescriptors = projectDescriptor.getResourceDescriptors();
				if (resourceDescriptors != null) {
					for (IResourceDescriptor resourceDescriptor : resourceDescriptors) {
						assert resourceDescriptor != null;
						resourceDescriptor.unload(resourceSet);
					}
				}
			}
		}
	}
}
