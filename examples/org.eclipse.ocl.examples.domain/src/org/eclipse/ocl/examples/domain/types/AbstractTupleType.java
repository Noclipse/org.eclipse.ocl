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
 *
 * $Id$
 */
package org.eclipse.ocl.examples.domain.types;

import java.util.List;

import org.eclipse.ocl.examples.domain.elements.DomainOperation;
import org.eclipse.ocl.examples.domain.elements.DomainTypedElement;
import org.eclipse.ocl.examples.domain.evaluation.InvalidValueException;
import org.eclipse.ocl.examples.domain.library.LibraryFeature;

public class AbstractTupleType extends AbstractType implements DomainTupleType
{
	private List<? extends DomainTypedElement> parts;
	
	public AbstractTupleType(String name, List<? extends DomainTypedElement> parts) {
		super(name);
		this.parts = parts;
	}

	public boolean conformsTo(DomainType type, DomainStandardLibrary standardLibrary) {
		if (this == type) {
			return true;
		}
		if (!(type instanceof DomainTupleType)) {
			return false;
		}
		return standardLibrary.conformsToTupleType(this, (DomainTupleType)type);
	}

	@Override
	public DomainType getCommonType(DomainType type, DomainStandardLibrary standardLibrary) {
		if (type != this) {
			return standardLibrary.getOclAnyType();
		}
		return this;
	}

	public List<? extends DomainTypedElement> getOwnedAttributes() {
		return parts;
	}

	public boolean isEqualTo(DomainType type, DomainStandardLibrary standardLibrary) {
		if (this == type) {
			return true;
		}
		if (!(type instanceof DomainTupleType)) {
			return false;
		}
		return standardLibrary.isEqualToTupleType(this, (DomainTupleType)type);
	}

	public LibraryFeature lookupImplementation(DomainStandardLibrary standardLibrary, DomainOperation staticOperation) throws InvalidValueException {
		return standardLibrary.getOclTupleType().lookupImplementation(standardLibrary, staticOperation);
	}

	public DomainOperation lookupOperation(DomainStandardLibrary standardLibrary, String operationName, DomainType... argumentTypes) {
		return standardLibrary.getOclTupleType().lookupOperation(standardLibrary, operationName, argumentTypes);
	}
}