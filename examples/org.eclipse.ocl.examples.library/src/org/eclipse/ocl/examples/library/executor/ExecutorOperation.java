/**
 * <copyright>
 *
 * Copyright (c) 2011, 2013 E.D.Willink and others.
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

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainConstraint;
import org.eclipse.ocl.examples.domain.elements.DomainExpression;
import org.eclipse.ocl.examples.domain.elements.DomainInheritance;
import org.eclipse.ocl.examples.domain.elements.DomainOperation;
import org.eclipse.ocl.examples.domain.elements.DomainParameterTypes;
import org.eclipse.ocl.examples.domain.elements.DomainStandardLibrary;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.elements.DomainTypeParameters;
import org.eclipse.ocl.examples.domain.elements.DomainTypedElement;
import org.eclipse.ocl.examples.domain.ids.OperationId;
import org.eclipse.ocl.examples.domain.ids.ParametersId;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.LibraryFeature;
import org.eclipse.ocl.examples.library.oclany.OclAnyUnsupportedOperation;

public class ExecutorOperation implements DomainOperation
{
	protected final @NonNull String name;
	protected final @NonNull DomainParameterTypes parameterTypes;
	protected final @NonNull DomainInheritance inheritance;
	protected final int index;
	protected final @NonNull LibraryFeature implementation;
	protected final @NonNull DomainTypeParameters typeParameters;
	
	public ExecutorOperation(@NonNull String name, @NonNull DomainParameterTypes parameterTypes, @NonNull DomainInheritance inheritance, int index, @NonNull DomainTypeParameters typeParameters, @Nullable LibraryFeature implementation) {
		this.name = name;
		this.parameterTypes = parameterTypes;
		this.inheritance = inheritance;
		this.index = index;
		this.implementation = implementation != null ? implementation : OclAnyUnsupportedOperation.INSTANCE;		// FIXME
		this.typeParameters = typeParameters;
	}

	public @NonNull LibraryFeature getImplementation() {
		return implementation;
	}

	public DomainExpression getBodyExpression() {
		throw new UnsupportedOperationException(); 			// FIXME
	}

	public final int getIndex() {
		return index;
	}

	public final @NonNull DomainInheritance getInheritance(@NonNull DomainStandardLibrary standardLibrary) {
		return inheritance;
	}

	public final String getName() {
		return name;
	}

	public @NonNull OperationId getOperationId() {
		throw new UnsupportedOperationException();			// FIXME
	}
	
	public @NonNull List<? extends DomainTypedElement> getOwnedParameter() {
		return getParameterTypes().getParameters();
	}

	public @NonNull List<? extends DomainConstraint> getOwnedRule() {
		throw new UnsupportedOperationException();			// FIXME
	}

	public @NonNull DomainType getOwningType() {
		throw new UnsupportedOperationException();			// FIXME
	}

	public @NonNull ParametersId getParametersId() {
		return parameterTypes.getParametersId();
	}
	
	public @NonNull DomainParameterTypes getParameterTypes() {
		return parameterTypes;
	}

	
	public @NonNull List<? extends DomainConstraint> getPostcondition() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); // WIP FIXME
	}

	public @NonNull List<? extends DomainConstraint> getPrecondition() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); // WIP FIXME
	}

	public @NonNull DomainType getType() {
//		return executorType;
		throw new UnsupportedOperationException(); // WIP FIXME
	}

	public @NonNull TypeId getTypeId() {
		DomainType type2 = getType();
		return type2.getTypeId();
	}

	public @NonNull DomainTypeParameters getTypeParameters() {
		return typeParameters;
	}

	public boolean isStatic() {
		return false;								// WIP FIXME
	}

	@Override
	public String toString() {
		return inheritance.toString() + "::" + name; //$NON-NLS-1$
	}
}
