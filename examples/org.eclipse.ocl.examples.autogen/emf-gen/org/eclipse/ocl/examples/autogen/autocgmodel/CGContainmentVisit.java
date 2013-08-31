/**
 * <copyright>
 * 
 * Copyright (c) 2013 E.D.Willink and others.
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
package org.eclipse.ocl.examples.autogen.autocgmodel;

import java.util.List;

import org.eclipse.ocl.examples.codegen.cgmodel.CGOperation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CG Containment Visit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.autogen.autocgmodel.CGContainmentVisit#getParts <em>Parts</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.ocl.examples.autogen.autocgmodel.AutoCGModelPackage#getCGContainmentVisit()
 * @model
 * @generated
 */
public interface CGContainmentVisit extends CGOperation {
	/**
	 * Returns the value of the '<em><b>Parts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.ocl.examples.autogen.autocgmodel.CGContainmentPart}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.ocl.examples.autogen.autocgmodel.CGContainmentPart#getContainmentVisit <em>Containment Visit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The parameters of this operation, with 'self' as the first parameter.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Parts</em>' containment reference list.
	 * @see org.eclipse.ocl.examples.autogen.autocgmodel.AutoCGModelPackage#getCGContainmentVisit_Parts()
	 * @see org.eclipse.ocl.examples.autogen.autocgmodel.CGContainmentPart#getContainmentVisit
	 * @model opposite="containmentVisit" containment="true" ordered="false"
	 * @generated
	 */
	List<CGContainmentPart> getParts();

} // CGContainmentVisit
