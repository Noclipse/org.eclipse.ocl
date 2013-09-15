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
package org.eclipse.ocl.examples.codegen.cgmodel.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.codegen.cgmodel.CGConstructorExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGConstructorPart;
import org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorType;
import org.eclipse.ocl.examples.codegen.cgmodel.CGInvalid;
import org.eclipse.ocl.examples.codegen.cgmodel.CGModelPackage;
import org.eclipse.ocl.examples.codegen.cgmodel.CGValuedElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CG Constructor Exp</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.codegen.cgmodel.impl.CGConstructorExpImpl#getParts <em>Parts</em>}</li>
 *   <li>{@link org.eclipse.ocl.examples.codegen.cgmodel.impl.CGConstructorExpImpl#getExecutorType <em>Executor Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class CGConstructorExpImpl extends CGValuedElementImpl implements CGConstructorExp {
	/**
	 * The cached value of the '{@link #getParts() <em>Parts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParts()
	 * @generated
	 * @ordered
	 */
	protected EList<CGConstructorPart> parts;

	/**
	 * The cached value of the '{@link #getExecutorType() <em>Executor Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecutorType()
	 * @generated
	 * @ordered
	 */
	protected CGExecutorType executorType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CGConstructorExpImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CGModelPackage.Literals.CG_CONSTRUCTOR_EXP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("null")
	public @NonNull List<CGConstructorPart> getParts() {
		if (parts == null) {
			parts = new EObjectContainmentWithInverseEList<CGConstructorPart>(CGConstructorPart.class, this, CGModelPackage.CG_CONSTRUCTOR_EXP__PARTS, CGModelPackage.CG_CONSTRUCTOR_PART__CONSTRUCTOR_EXP);
		}
		return parts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CGExecutorType getExecutorType() {
		return executorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecutorType(CGExecutorType newExecutorType) {
		CGExecutorType oldExecutorType = executorType;
		executorType = newExecutorType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CGModelPackage.CG_CONSTRUCTOR_EXP__EXECUTOR_TYPE, oldExecutorType, executorType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CGModelPackage.CG_CONSTRUCTOR_EXP__PARTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getParts()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CGModelPackage.CG_CONSTRUCTOR_EXP__PARTS:
				return ((InternalEList<?>)getParts()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CGModelPackage.CG_CONSTRUCTOR_EXP__PARTS:
				return getParts();
			case CGModelPackage.CG_CONSTRUCTOR_EXP__EXECUTOR_TYPE:
				return getExecutorType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CGModelPackage.CG_CONSTRUCTOR_EXP__PARTS:
				getParts().clear();
				getParts().addAll((Collection<? extends CGConstructorPart>)newValue);
				return;
			case CGModelPackage.CG_CONSTRUCTOR_EXP__EXECUTOR_TYPE:
				setExecutorType((CGExecutorType)newValue);
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
		switch (featureID) {
			case CGModelPackage.CG_CONSTRUCTOR_EXP__PARTS:
				getParts().clear();
				return;
			case CGModelPackage.CG_CONSTRUCTOR_EXP__EXECUTOR_TYPE:
				setExecutorType((CGExecutorType)null);
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
		switch (featureID) {
			case CGModelPackage.CG_CONSTRUCTOR_EXP__PARTS:
				return parts != null && !parts.isEmpty();
			case CGModelPackage.CG_CONSTRUCTOR_EXP__EXECUTOR_TYPE:
				return executorType != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public @Nullable CGInvalid getInvalidValue() {
		for (CGConstructorPart cgPart : getParts()) {
			CGInvalid invalidValue = cgPart.getInvalidValue();
			if (invalidValue != null) {
				return invalidValue;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public @NonNull CGValuedElement getThisValue() {
		CGInvalid invalidValue = getInvalidValue();
		if (invalidValue != null) {
			return invalidValue;
		}
		else {
			return this;
		}
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public boolean isBoxed() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public boolean isCommonable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public boolean isConstant() {
		for (CGConstructorPart cgPart : getParts()) {
			if (!cgPart.isConstant()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public boolean isGlobal() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public boolean isNonInvalid() {
		for (CGConstructorPart cgPart : getParts()) {
			if (!cgPart.isNonInvalid()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public boolean isNonNull() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public boolean isNull() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public boolean isUnboxed() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public boolean rewriteAs(@NonNull CGValuedElement oldValue, @NonNull CGValuedElement newValue) {
		if (oldValue == executorType) {
			setExecutorType((CGExecutorType)newValue);
			return true;
		}
		return false;
	}

} //CGConstructorExpImpl
