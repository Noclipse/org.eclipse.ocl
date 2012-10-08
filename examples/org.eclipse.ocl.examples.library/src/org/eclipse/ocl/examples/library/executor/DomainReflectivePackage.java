/**
 * <copyright>
 *
 * Copyright (c) 2011,2012 E.D.Willink and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.ocl.examples.library.executor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.domain.elements.DomainPackage;
import org.eclipse.ocl.examples.domain.elements.DomainStandardLibrary;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.typeids.Typeid;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;

/**
 * DomainExecutorPackage uses the limited Domain interfaces to construct a package description for use
 * in contexts where no explicit ExecutorPackage is available.
 * 
 * This typically occurs when a dynamic Ecore model is used but no MetaModelManager is accessible.
 */
public class DomainReflectivePackage extends ReflectivePackage
{
	protected final @NonNull DomainStandardLibrary standardLibrary;
	protected final @NonNull DomainPackage domainPackage;

	public DomainReflectivePackage(@NonNull DomainStandardLibrary standardLibrary, @NonNull DomainPackage domainPackage) {
		super(DomainUtil.nonNullPivot(domainPackage.getName()), domainPackage.getNsPrefix(), domainPackage.getNsURI(), domainPackage.getTypeid());
		this.standardLibrary = standardLibrary;
		this.domainPackage = domainPackage;
	}

	@Override
	protected @NonNull ReflectiveType createExecutorType(@NonNull DomainType domainType) {
		return new DomainReflectiveType(this, domainType);
	}

	@Override
	protected @NonNull Iterable<? extends DomainType> getDomainTypes() {
		return DomainUtil.nonNullPivot(domainPackage.getOwnedType());
	}

	public Iterable<? extends DomainPackage> getNestedPackage() {
		return domainPackage.getNestedPackage();			// FIXME Is this recursive??
	}

	public DomainPackage getNestingPackage() {
		return domainPackage.getNestingPackage();			// FIXME Is this recursive??
	}
	
	@Override
	protected @NonNull DomainStandardLibrary getStandardLibrary() {
		return standardLibrary;
	}

	@Override
	public @NonNull Typeid getTypeid() {
		return domainPackage.getTypeid();
	}
}