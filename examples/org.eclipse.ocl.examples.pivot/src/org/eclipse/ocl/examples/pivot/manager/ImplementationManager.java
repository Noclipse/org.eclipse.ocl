/**
 * <copyright>
 *
 * Copyright (c) 2011 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.ocl.examples.pivot.manager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ocl.examples.domain.library.LibraryFeature;
import org.eclipse.ocl.examples.pivot.Constraint;
import org.eclipse.ocl.examples.pivot.ExpressionInOcl;
import org.eclipse.ocl.examples.pivot.Feature;
import org.eclipse.ocl.examples.pivot.Operation;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.TupleType;
import org.eclipse.ocl.examples.pivot.UMLReflection;
import org.eclipse.ocl.examples.pivot.ValueSpecification;
import org.eclipse.ocl.examples.pivot.library.CompositionProperty;
import org.eclipse.ocl.examples.pivot.library.ConstrainedOperation;
import org.eclipse.ocl.examples.pivot.library.ConstrainedProperty;
import org.eclipse.ocl.examples.pivot.library.ExplicitNavigationProperty;
import org.eclipse.ocl.examples.pivot.library.ImplicitNonCompositionProperty;
import org.eclipse.ocl.examples.pivot.library.TuplePartProperty;
import org.eclipse.ocl.examples.pivot.library.UnimplementedOperation;

/**
 * ImplementationManager encapsulates the knowledge about known feature implementations.
 */
public class ImplementationManager
{		
	protected final MetaModelManager metaModelManager;

	/**
	 * ClassLoaders that may be able to load a library implementation.
	 */
	private List<ClassLoader> classLoaders = null;
	
	protected ImplementationManager(MetaModelManager metaModelManager) {
		this.metaModelManager = metaModelManager;
	}

	public void addClassLoader(ClassLoader classLoader) {
		List<ClassLoader> classLoaders = getClassLoaders();
		if (!classLoaders.contains(classLoader)) {
			classLoaders.add(classLoader);
		}
	}

	public List<ClassLoader> getClassLoaders() {
		if (classLoaders == null) {
			classLoaders = new ArrayList<ClassLoader>();
			classLoaders.add(metaModelManager.getClass().getClassLoader());
		}
		return classLoaders;
	}

	protected LibraryFeature getOperationImplementation(Operation operation) throws ClassNotFoundException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		LibraryFeature implementation = operation.getImplementation();
		String implementationClassName = operation.getImplementationClass();
		if (implementationClassName != null) {
			if ((implementation == null) || !implementation.getClass().getName().equals(implementationClassName)) {
				return loadImplementation(operation);
			}
		}
		for (Constraint constraint : metaModelManager.getLocalConstraints(operation)) {
			if (UMLReflection.BODY.equals(constraint.getStereotype())) {
				ValueSpecification specification = constraint.getSpecification();
				if (specification instanceof ExpressionInOcl) {
					return new ConstrainedOperation((ExpressionInOcl) specification);
				}
			}
		}
		return UnimplementedOperation.INSTANCE;
	}

	protected LibraryFeature getPropertyImplementation(Property property) throws ClassNotFoundException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		LibraryFeature implementation = property.getImplementation();
		String implementationClassName = property.getImplementationClass();
		if (implementationClassName != null) {
			if ((implementation == null) || !implementation.getClass().getName().equals(implementationClassName)) {
				return loadImplementation(property);
			}
		}
		//
		// An initial 'OCL expression is evaluated at the creation time of the instance' so
		// if there is an initial constraint do lazy creation evaluation.
		//
		for (Constraint constraint : metaModelManager.getLocalConstraints(property)) {
			if (UMLReflection.INITIAL.equals(constraint.getStereotype())) {
				ValueSpecification specification = constraint.getSpecification();
				if (specification instanceof ExpressionInOcl) {
					return new ConstrainedProperty((ExpressionInOcl) specification);
				}
			}
		}
		//
		// 'A derived value expression is an invariant' so perhaps we'd better evaluate
		// it to support the lazy practice of using derivation as initial.
		//
		for (Constraint constraint : metaModelManager.getLocalConstraints(property)) {
			if (UMLReflection.DERIVATION.equals(constraint.getStereotype())) {
				ValueSpecification specification = constraint.getSpecification();
				if (specification instanceof ExpressionInOcl) {
					return new ConstrainedProperty((ExpressionInOcl) specification);
				}
			}
		}
		Property opposite = property.getOpposite();
		if ((opposite != null) && opposite.isComposite()) {
			return CompositionProperty.INSTANCE;
		}
		if (property.isImplicit()) {
			return ImplicitNonCompositionProperty.INSTANCE;
		}
		else if (property.getOwningType() instanceof TupleType) {
			return TuplePartProperty.INSTANCE;
		}
		else {
			return ExplicitNavigationProperty.INSTANCE;
		}
	}
	
	public void dispose() {
		classLoaders = null;
	}

	/**
	 * Return the implementation of a feature.
	 * 
	 * @param feature to be implemented.
	 * @return the implementation, or null if the feature has no implementation
	 * as is the case for a normal model feature
	 * @throws ClassNotFoundException if the implementation class realising
	 * the implementation is not loadable 
	 * @throws NoSuchFieldException if the implementation class realising
	 * the implementation does not provide a static INSTANCE field
	 * @throws SecurityException if the implementation class is not accessible
	 * @throws IllegalAccessException if the implementation class is not accessible
	 * @throws IllegalArgumentException if the implementation class is not accessible
	 */
	public LibraryFeature loadImplementation(Feature feature) throws ClassNotFoundException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		LibraryFeature implementation = feature.getImplementation();
		if (implementation == null) {
			String implementationClassName = feature.getImplementationClass();
			if (implementationClassName != null) {
				Class<?> theClass = null;
				addClassLoader(feature.getClass().getClassLoader());
				ClassNotFoundException e = null;
				for (ClassLoader classLoader : getClassLoaders()) {
					try {
						theClass = classLoader.loadClass(implementationClassName);
						e = null;
						break;
					} catch (ClassNotFoundException e1) {
						if (e == null) {
							e = e1;
						}
					}						
				}
				if (e != null) {
					throw e;
				}
				@SuppressWarnings("null")
				Field field = theClass.getField("INSTANCE");
				implementation = (LibraryFeature) field.get(null);
			}
		}
		return implementation;
	}
}