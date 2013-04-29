/**
 * <copyright>
 * 
 * Copyright (c) 2013 CEA LIST and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   E.D.Willink(CEA LIST) - Initial API and implementation
 * 
 * </copyright>
 */
package org.eclipse.ocl.examples.codegen.cgmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CG Executor Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A CGTypeWithReflection defines a Static Single Assignment variable whose value is a DomainType.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorProperty#getUnderlyingPropertyId <em>Underlying Property Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.ocl.examples.codegen.cgmodel.CGModelPackage#getCGExecutorProperty()
 * @model abstract="true"
 * @generated
 */
public interface CGExecutorProperty extends CGValuedElement {
	/**
	 * Returns the value of the '<em><b>Underlying Property Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Underlying Property Id</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Underlying Property Id</em>' reference.
	 * @see #setUnderlyingPropertyId(CGElementId)
	 * @see org.eclipse.ocl.examples.codegen.cgmodel.CGModelPackage#getCGExecutorProperty_UnderlyingPropertyId()
	 * @model resolveProxies="false"
	 * @generated
	 */
	CGElementId getUnderlyingPropertyId();

	/**
	 * Sets the value of the '{@link org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorProperty#getUnderlyingPropertyId <em>Underlying Property Id</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Underlying Property Id</em>' reference.
	 * @see #getUnderlyingPropertyId()
	 * @generated
	 */
	void setUnderlyingPropertyId(CGElementId value);

} // CGExecutorProperty
