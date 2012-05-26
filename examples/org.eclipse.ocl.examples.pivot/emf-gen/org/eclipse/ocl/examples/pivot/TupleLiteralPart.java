/**
 * <copyright>
 *
 * Copyright (c) 2010 E.D.Willink and others.
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
 * $Id: TupleLiteralPart.java,v 1.4 2011/05/19 16:55:39 ewillink Exp $
 */
package org.eclipse.ocl.examples.pivot;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tuple Literal Part</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.pivot.TupleLiteralPart#getInitExpression <em>Init Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.ocl.examples.pivot.PivotPackage#getTupleLiteralPart()
 * @model
 * @generated
 */
public interface TupleLiteralPart
		extends VariableDeclaration {

	/**
	 * Returns the value of the '<em><b>Init Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Expression</em>' containment reference.
	 * @see #setInitExpression(OCLExpression)
	 * @see org.eclipse.ocl.examples.pivot.PivotPackage#getTupleLiteralPart_InitExpression()
	 * @model containment="true" ordered="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://www.eclipse.org/ocl/3.1.0/Pivot!TupleLiteralPart!initExpression'"
	 * @generated
	 */
	OCLExpression getInitExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.ocl.examples.pivot.TupleLiteralPart#getInitExpression <em>Init Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Init Expression</em>' containment reference.
	 * @see #getInitExpression()
	 * @generated
	 */
	void setInitExpression(OCLExpression value);

	/**
	 * Creates a new {@link org.eclipse.ocl.examples.pivot.OCLExpression} and sets the '<em><b>Init Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.ocl.examples.pivot.OCLExpression} to create.
	 * @return The new {@link org.eclipse.ocl.examples.pivot.OCLExpression}.
	 * @see #getInitExpression()
	 * @generated
	 */
	OCLExpression createInitExpression(EClass eClass);

} // TupleLiteralPart
