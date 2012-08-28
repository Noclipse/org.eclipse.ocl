/**
 * <copyright>
 *
 * Copyright (c) 2010 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 *
 * $Id: ClassifierContextDeclCSImpl.java,v 1.7 2011/02/08 17:53:05 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.PivotPackage;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.xtext.base.baseCST.PathNameCS;
import org.eclipse.ocl.examples.xtext.base.cs2pivot.CS2Pivot;
import org.eclipse.ocl.examples.xtext.base.util.BaseCSVisitor;
import org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.ClassifierContextDeclCS;
import org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.CompleteOCLCSTPackage;
import org.eclipse.ocl.examples.xtext.completeocl.util.CompleteOCLCSVisitor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Classifier Context Decl CS</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.impl.ClassifierContextDeclCSImpl#getSelfName <em>Self Name</em>}</li>
 *   <li>{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.impl.ClassifierContextDeclCSImpl#getClassifier <em>Classifier</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClassifierContextDeclCSImpl extends ContextDeclCSImpl implements ClassifierContextDeclCS {
	/**
	 * The default value of the '{@link #getSelfName() <em>Self Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelfName()
	 * @generated
	 * @ordered
	 */
	protected static final String SELF_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSelfName() <em>Self Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelfName()
	 * @generated
	 * @ordered
	 */
	protected String selfName = SELF_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassifierContextDeclCSImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompleteOCLCSTPackage.Literals.CLASSIFIER_CONTEXT_DECL_CS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSelfName() {
		return selfName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSelfName(String newSelfName) {
		String oldSelfName = selfName;
		selfName = newSelfName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompleteOCLCSTPackage.CLASSIFIER_CONTEXT_DECL_CS__SELF_NAME, oldSelfName, selfName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case CompleteOCLCSTPackage.CLASSIFIER_CONTEXT_DECL_CS__SELF_NAME:
				return getSelfName();
			case CompleteOCLCSTPackage.CLASSIFIER_CONTEXT_DECL_CS__CLASSIFIER:
				return getClassifier();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case CompleteOCLCSTPackage.CLASSIFIER_CONTEXT_DECL_CS__SELF_NAME:
				setSelfName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID)
		{
			case CompleteOCLCSTPackage.CLASSIFIER_CONTEXT_DECL_CS__SELF_NAME:
				setSelfName(SELF_NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case CompleteOCLCSTPackage.CLASSIFIER_CONTEXT_DECL_CS__SELF_NAME:
				return SELF_NAME_EDEFAULT == null ? selfName != null : !SELF_NAME_EDEFAULT.equals(selfName);
			case CompleteOCLCSTPackage.CLASSIFIER_CONTEXT_DECL_CS__CLASSIFIER:
				return getClassifier() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String toString() {
		return super.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> R accept(@NonNull BaseCSVisitor<R> visitor) {
		return (R) ((CompleteOCLCSVisitor<?>)visitor).visitClassifierContextDeclCS(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Type getClassifier()
	{
		PathNameCS pathName2 = pathName;
		if (pathName2 == null) {
			return null;
		}
		CS2Pivot.setElementType(pathName2, PivotPackage.Literals.TYPE, this, null);
		return (Type) pathName2.getElement();
	}
} //ClassifierContextDeclCSImpl
