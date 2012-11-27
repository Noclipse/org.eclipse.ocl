/**
 * <copyright>
 *
 * Copyright (c) 2011 E.D.Willink and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.ocl.examples.library.ecore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainPackage;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.domain.types.IdResolver;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.library.executor.ExecutorManager;
import org.eclipse.ocl.examples.library.executor.ExecutorStandardLibrary;
import org.eclipse.ocl.examples.library.executor.LazyModelManager;

public class EcoreExecutorManager extends ExecutorManager
{
	private final @Nullable Object contextObject;
	private DomainModelManager modelManager = null;
	private IdResolver idResolver = null;
	
	public EcoreExecutorManager(@Nullable Object contextObject, @NonNull ExecutorStandardLibrary standardLibrary) {
		super(standardLibrary);
		this.contextObject = contextObject;
	}

	@Deprecated
	public EcoreExecutorManager(@Nullable Object contextObject, @Nullable Map<Object, Object> contextMap, @NonNull ExecutorStandardLibrary standardLibrary) {
		this(contextObject, standardLibrary);
	}

	protected @NonNull IdResolver createIdResolver() {
		if (!(contextObject instanceof EObject)) {
			@SuppressWarnings("null")@NonNull List<EObject> emptyList = Collections.<EObject>emptyList();
			return new EcoreIdResolver(emptyList, (ExecutorStandardLibrary) standardLibrary);
		}
		EObject rootContainer = EcoreUtil.getRootContainer((EObject)contextObject);
		Notifier notifier;
		Resource resource = rootContainer.eResource();
		if (resource != null) {
			ResourceSet resourceSet = resource.getResourceSet();
			if (resourceSet != null) {
				notifier = resourceSet;
			}
			else {
				notifier = resource;
			}
		}
		else {
			notifier = rootContainer;
		}
		synchronized (notifier) {
			EList<Adapter> eAdapters = notifier.eAdapters();
			for (Adapter adapter : eAdapters) {
				if (adapter instanceof EcoreIdResolver) {
					return (EcoreIdResolver) adapter;
				}
			}
			List<EObject> roots = null;
			if (resource != null) {
				ResourceSet resourceSet = resource.getResourceSet();
				if (resourceSet != null) {
					roots = new ArrayList<EObject>();
					for (Resource r : resourceSet.getResources()) {
						roots.addAll(r.getContents());
					}
				}
				else {
					roots = resource.getContents();
				}
			}
			DomainPackage root = standardLibrary.getOclAnyType().getPackage();
			if (root instanceof EObject) {
				if (roots == null) {
					roots = new ArrayList<EObject>();
				}
				roots.add((EObject) root);
			}
			if (roots == null) {
				roots = Collections.singletonList(rootContainer);
			}
			assert roots != null;
			EcoreIdResolver adapter = new EcoreIdResolver(roots, (ExecutorStandardLibrary) standardLibrary);
			eAdapters.add(adapter);
			return adapter;
		}
	}

	public @NonNull DomainEvaluator createNestedEvaluator() {
		return new EcoreExecutorManager(contextObject, (ExecutorStandardLibrary) standardLibrary);
	}

	@Override
	public @NonNull DomainType getDynamicTypeOf(@Nullable Object value) {
		IdResolver idResolver2 = idResolver;
		if (idResolver2 == null) {
			idResolver = idResolver2 = createIdResolver();
		}
		return idResolver2.getDynamicTypeOf(value);
	}

	public @NonNull DomainModelManager getModelManager() {
		DomainModelManager modelManager2 = modelManager;
		if (modelManager2 == null) {
			synchronized (this) {
				modelManager2 = modelManager;
				if (modelManager2 == null) {
					if (contextObject instanceof EObject) {
						modelManager2 = new LazyModelManager((EObject)contextObject)
						{
							@Override
							protected boolean isInstance(@NonNull DomainType type, @NonNull EObject element) {
								EClass eClass = DomainUtil.nonNullEMF(element.eClass());
								DomainType elementType = standardLibrary.getType(eClass);
								return elementType.conformsTo(standardLibrary, type);
							}
							
						};
					}
					else {
						modelManager2 = DomainModelManager.NULL;
					}
					modelManager = modelManager2;
				}
			}
		}
		return modelManager2;
	}

	@Override
	public @NonNull IdResolver getIdResolver() {
		IdResolver idResolver2 = idResolver;
		if (idResolver2 == null) {
			idResolver = idResolver2 = /*standardLibrary.getIdResolver(); //*/createIdResolver();
		}
		return idResolver2;
	}

	@Override
	public @NonNull DomainType getStaticTypeOf(@Nullable Object value) {
		IdResolver idResolver2 = idResolver;
		if (idResolver2 == null) {
			idResolver = idResolver2 = createIdResolver();
		}
		return idResolver2.getStaticTypeOf(value);
	}

	@Override
	public @NonNull DomainType getStaticTypeOf(@Nullable Object value, @NonNull Object... values) {
		IdResolver idResolver2 = idResolver;
		if (idResolver2 == null) {
			idResolver = idResolver2 = createIdResolver();
		}
		return idResolver2.getStaticTypeOf(value, values);
	}

	@Override
	public @NonNull DomainType getStaticTypeOf(@Nullable Object value, @NonNull Iterable<?> values) {
		IdResolver idResolver2 = idResolver;
		if (idResolver2 == null) {
			idResolver = idResolver2 = createIdResolver();
		}
		return idResolver2.getStaticTypeOf(value, values);
	}
}