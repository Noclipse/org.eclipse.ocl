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

import org.eclipse.ocl.examples.domain.library.LibraryOperation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CG Library Operation Call Exp</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.codegen.cgmodel.CGLibraryOperationCallExp#getLibraryOperation <em>Library Operation</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.ocl.examples.codegen.cgmodel.CGModelPackage#getCGLibraryOperationCallExp()
 * @model
 * @generated
 */
public interface CGLibraryOperationCallExp extends CGOperationCallExp {
	/**
	 * Returns the value of the '<em><b>Library Operation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Library Operation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library Operation</em>' attribute.
	 * @see #setLibraryOperation(LibraryOperation)
	 * @see org.eclipse.ocl.examples.codegen.cgmodel.CGModelPackage#getCGLibraryOperationCallExp_LibraryOperation()
	 * @model dataType="org.eclipse.ocl.examples.codegen.cgmodel.LibraryOperation" required="true"
	 * @generated
	 */
	LibraryOperation getLibraryOperation();

	/**
	 * Sets the value of the '{@link org.eclipse.ocl.examples.codegen.cgmodel.CGLibraryOperationCallExp#getLibraryOperation <em>Library Operation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Library Operation</em>' attribute.
	 * @see #getLibraryOperation()
	 * @generated
	 */
	void setLibraryOperation(LibraryOperation value);

} // CGLibraryOperationCallExp
