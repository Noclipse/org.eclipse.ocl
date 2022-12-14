/**
 * Copyright (c) 2005, 2020 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors: 
 *   IBM - Initial API and implementation
 */
package org.eclipse.ocl.examples.extlibrary;


import org.eclipse.emf.ecore.EObject;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Addressable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.extlibrary.Addressable#getAddress <em>Address</em>}</li>
 * </ul>
 *
 * @see org.eclipse.ocl.examples.extlibrary.EXTLibraryPackage#getAddressable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Addressable extends EObject
{
  /**
	 * Returns the value of the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Address</em>' attribute.
	 * @see #setAddress(String)
	 * @see org.eclipse.ocl.examples.extlibrary.EXTLibraryPackage#getAddressable_Address()
	 * @model
	 * @generated
	 */
  String getAddress();

  /**
	 * Sets the value of the '{@link org.eclipse.ocl.examples.extlibrary.Addressable#getAddress <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Address</em>' attribute.
	 * @see #getAddress()
	 * @generated
	 */
  void setAddress(String value);

} // Addressable
