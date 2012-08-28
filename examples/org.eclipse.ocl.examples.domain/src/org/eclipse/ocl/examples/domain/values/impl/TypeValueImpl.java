/**
 * <copyright>
 *
 * Copyright (c) 2010,2011 E.D.Willink and others.
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
 * $Id: TypeValueImpl.java,v 1.5 2011/04/25 09:49:14 ewillink Exp $
 */
package org.eclipse.ocl.examples.domain.values.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.examples.domain.elements.DomainClassifierType;
import org.eclipse.ocl.examples.domain.elements.DomainInheritance;
import org.eclipse.ocl.examples.domain.elements.DomainStandardLibrary;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.InvalidValueException;
import org.eclipse.ocl.examples.domain.messages.EvaluatorMessages;
import org.eclipse.ocl.examples.domain.values.TypeValue;
import org.eclipse.ocl.examples.domain.values.ValueFactory;
import org.eclipse.ocl.examples.domain.values.ValuesPackage;

/**
 * @generated NOT
 */
public abstract class TypeValueImpl extends ObjectValueImpl implements TypeValue
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValuesPackage.Literals.TYPE_VALUE;
	}


	private DomainClassifierType classifierType;

	public TypeValueImpl(ValueFactory valueFactory, DomainType object) {
		super(valueFactory, object);
		this.classifierType = null;
	}

	@Override
	public DomainType asElement() {
		return getObject();
	}

	@Override
	public TypeValue asElementValue() {
		return this;
	}

	@Override
	public EObject asNavigableObject()
			throws InvalidValueException {
		DomainType navigableObject = getObject();
		if (navigableObject instanceof EObject) {
			return (EObject) navigableObject;
		} else {
			return (EObject) valueFactory.throwInvalidValueException(
				EvaluatorMessages.TypedValueRequired, "Object", getType());
		}
	}

	@Override
	public TypeValueImpl asTypeValue() {
		return this;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (!(that instanceof TypeValue)) {
			return false;
		}
		DomainStandardLibrary standardLibrary = valueFactory
			.getStandardLibrary();
		DomainInheritance thisInheritance = getObject()
			.getInheritance(standardLibrary);
		DomainInheritance thatInheritance = ((TypeValue) that)
			.getInstanceType().getInheritance(standardLibrary);
		return thisInheritance == thatInheritance;
	}

	public DomainType getElement() {
		return getObject();
	}

	public DomainType getInstanceType() {
		return getObject();
	}

	@Override
	public DomainType getObject() {
		return (DomainType) object;
	}

	public DomainClassifierType getType() {
		if (classifierType == null) {
			classifierType = valueFactory.getStandardLibrary()
				.getClassifierType(getObject());
		}
		return classifierType;
	}
}
