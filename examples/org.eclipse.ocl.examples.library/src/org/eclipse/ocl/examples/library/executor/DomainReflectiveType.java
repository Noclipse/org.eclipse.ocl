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

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainInheritance;
import org.eclipse.ocl.examples.domain.elements.DomainOperation;
import org.eclipse.ocl.examples.domain.elements.DomainProperty;
import org.eclipse.ocl.examples.domain.elements.DomainStandardLibrary;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.elements.DomainTypeParameters;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.types.AbstractFragment;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;

public class DomainReflectiveType extends ReflectiveType
{
	protected final @NonNull DomainStandardLibrary standardLibrary;
	protected final @NonNull DomainType domainType;
	private /*@LazyNonNull*/ DomainProperties allProperties;

	public DomainReflectiveType(@NonNull DomainReflectivePackage evaluationPackage, @NonNull DomainType domainType) {
		super(DomainUtil.nonNullModel(domainType.getName()), evaluationPackage, computeFlags(domainType));
		this.standardLibrary = evaluationPackage.getStandardLibrary();
		this.domainType = domainType;
	}

	@Override
	protected @NonNull AbstractFragment createFragment(@NonNull DomainInheritance baseInheritance) {
		return new DomainReflectiveFragment(this, baseInheritance);
	}

	@NonNull
	public Iterable<? extends DomainOperation> getAllOperations(boolean selectStatic) {
		throw new UnsupportedOperationException();
	}

	public @NonNull Iterable<? extends DomainProperty> getAllProperties(boolean selectStatic) {
		DomainProperties allProperties2 = allProperties;
		if (allProperties2 == null) {
			allProperties = allProperties2 = new DomainProperties(this);
		}
		return allProperties2.getAllProperties(selectStatic);
	}

	@Override
	public @NonNull Iterable<? extends DomainInheritance> getInitialSuperInheritances() {
		final Iterator<? extends DomainType> iterator = domainType.getLocalSuperTypes().iterator();
		return new Iterable<DomainInheritance>()
		{
			public Iterator<DomainInheritance> iterator() {
				return new Iterator<DomainInheritance>()
				{
					public boolean hasNext() {
						return iterator.hasNext();
					}

					public DomainInheritance next() {
						return iterator.next().getInheritance(standardLibrary);
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}					
				};
			}			
		};
	}

	public @NonNull List<? extends DomainOperation> getLocalOperations() {
		return domainType.getLocalOperations();
	}

	public @NonNull List<? extends DomainProperty> getLocalProperties() {
		return domainType.getLocalProperties();
	}

	public @NonNull List<? extends DomainType> getLocalSuperTypes() {
		return domainType.getLocalSuperTypes();
	}

	public @Nullable DomainProperty getMemberProperty(@NonNull String name) {
		DomainProperties allProperties2 = allProperties;
		if (allProperties2 == null) {
			allProperties = allProperties2 = new DomainProperties(this);
		}
		return allProperties2.getMemberProperty(name);
	}

	public @NonNull String getMetaTypeName() {
		return domainType.getMetaTypeName();
	}

	public @NonNull List<? extends DomainProperty> getOwnedAttribute() {
		return domainType.getLocalProperties();
	}

	public @NonNull List<? extends DomainOperation> getOwnedOperation() {
		return domainType.getLocalOperations();
	}

	public @NonNull DomainStandardLibrary getStandardLibrary() {
		return standardLibrary;
	}

	@Override
	public @NonNull TypeId getTypeId() {
		return domainType.getTypeId();
	}

	public @NonNull DomainTypeParameters getTypeParameters() {
		return domainType.getTypeParameters();
	}
}