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
import org.eclipse.ocl.examples.domain.elements.DomainClassifierType;
import org.eclipse.ocl.examples.domain.values.ClassifierTypeValue;
import org.eclipse.ocl.examples.domain.values.ValueFactory;
import org.eclipse.ocl.examples.domain.values.ValuesPackage;

/**
 * @generated NOT
 */
public class ClassifierTypeValueImpl extends TypeValueImpl implements ClassifierTypeValue
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValuesPackage.Literals.CLASSIFIER_TYPE_VALUE;
	}

	public ClassifierTypeValueImpl(ValueFactory valueFactory, DomainClassifierType object) {
		super(valueFactory, object);
	}
	
	@Override
	public boolean equals(Object that) {
		if (!super.equals(that)) {
			return false;
		}
		if (!(that instanceof ClassifierTypeValue)) {
			return false;
		}
		return object.equals(((ClassifierTypeValue)that).getInstanceType());
//		DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
//		DomainInheritance thisInheritance = object.getInstanceType().getInheritance(standardLibrary); 
//		DomainInheritance thatInheritance = ((ClassifierTypeValue)that).getInstanceType().getInheritance(standardLibrary); 
//		return thisInheritance == thatInheritance;
	}
}
