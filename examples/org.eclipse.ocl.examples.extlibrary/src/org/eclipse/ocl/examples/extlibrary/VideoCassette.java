/**
 * Copyright (c) 2005-2020 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors: 
 *   IBM - Initial API and implementation
 */
package org.eclipse.ocl.examples.extlibrary;


import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Video Cassette</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.extlibrary.VideoCassette#getCast <em>Cast</em>}</li>
 * </ul>
 *
 * @see org.eclipse.ocl.examples.extlibrary.EXTLibraryPackage#getVideoCassette()
 * @model
 * @generated
 */
public interface VideoCassette extends AudioVisualItem
{
  /**
	 * Returns the value of the '<em><b>Cast</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.ocl.examples.extlibrary.Person}.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Cast</em>' reference list.
	 * @see org.eclipse.ocl.examples.extlibrary.EXTLibraryPackage#getVideoCassette_Cast()
	 * @model
	 * @generated
	 */
  EList<Person> getCast();

} // VideoCassette
