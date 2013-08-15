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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.codegen.cgmodel.CGClass;
import org.eclipse.ocl.examples.codegen.cgmodel.CGConstraint;
import org.eclipse.ocl.examples.codegen.cgmodel.CGModelPackage;
import org.eclipse.ocl.examples.codegen.cgmodel.CGOperation;
import org.eclipse.ocl.examples.codegen.cgmodel.CGPackage;
import org.eclipse.ocl.examples.codegen.cgmodel.CGProperty;
import org.eclipse.ocl.examples.codegen.cgmodel.util.CGModelVisitor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CG Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.codegen.cgmodel.impl.CGClassImpl#getContainingPackage <em>Containing Package</em>}</li>
 *   <li>{@link org.eclipse.ocl.examples.codegen.cgmodel.impl.CGClassImpl#getOperations <em>Operations</em>}</li>
 *   <li>{@link org.eclipse.ocl.examples.codegen.cgmodel.impl.CGClassImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.ocl.examples.codegen.cgmodel.impl.CGClassImpl#getInvariants <em>Invariants</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CGClassImpl extends CGNamedElementImpl implements CGClass {
	/**
	 * The cached value of the '{@link #getOperations() <em>Operations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperations()
	 * @generated
	 * @ordered
	 */
	protected EList<CGOperation> operations;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<CGProperty> properties;

	/**
	 * The cached value of the '{@link #getInvariants() <em>Invariants</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvariants()
	 * @generated
	 * @ordered
	 */
	protected EList<CGConstraint> invariants;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CGClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CGModelPackage.Literals.CG_CLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CGPackage getContainingPackage() {
		if (eContainerFeatureID() != CGModelPackage.CG_CLASS__CONTAINING_PACKAGE) return null;
		return (CGPackage)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContainingPackage(CGPackage newContainingPackage, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newContainingPackage, CGModelPackage.CG_CLASS__CONTAINING_PACKAGE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainingPackage(CGPackage newContainingPackage) {
		if (newContainingPackage != eInternalContainer() || (eContainerFeatureID() != CGModelPackage.CG_CLASS__CONTAINING_PACKAGE && newContainingPackage != null)) {
			if (EcoreUtil.isAncestor(this, newContainingPackage))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newContainingPackage != null)
				msgs = ((InternalEObject)newContainingPackage).eInverseAdd(this, CGModelPackage.CG_PACKAGE__CLASSES, CGPackage.class, msgs);
			msgs = basicSetContainingPackage(newContainingPackage, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CGModelPackage.CG_CLASS__CONTAINING_PACKAGE, newContainingPackage, newContainingPackage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("null")
	public @NonNull List<CGOperation> getOperations() {
		if (operations == null) {
			operations = new EObjectContainmentWithInverseEList<CGOperation>(CGOperation.class, this, CGModelPackage.CG_CLASS__OPERATIONS, CGModelPackage.CG_OPERATION__CONTAINING_CLASS);
		}
		return operations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("null")
	public @NonNull List<CGProperty> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentWithInverseEList<CGProperty>(CGProperty.class, this, CGModelPackage.CG_CLASS__PROPERTIES, CGModelPackage.CG_PROPERTY__CONTAINING_CLASS);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<CGConstraint> getInvariants() {
		if (invariants == null) {
			invariants = new EObjectContainmentEList<CGConstraint>(CGConstraint.class, this, CGModelPackage.CG_CLASS__INVARIANTS);
		}
		return invariants;
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
			case CGModelPackage.CG_CLASS__CONTAINING_PACKAGE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetContainingPackage((CGPackage)otherEnd, msgs);
			case CGModelPackage.CG_CLASS__OPERATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOperations()).basicAdd(otherEnd, msgs);
			case CGModelPackage.CG_CLASS__PROPERTIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getProperties()).basicAdd(otherEnd, msgs);
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
			case CGModelPackage.CG_CLASS__CONTAINING_PACKAGE:
				return basicSetContainingPackage(null, msgs);
			case CGModelPackage.CG_CLASS__OPERATIONS:
				return ((InternalEList<?>)getOperations()).basicRemove(otherEnd, msgs);
			case CGModelPackage.CG_CLASS__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case CGModelPackage.CG_CLASS__INVARIANTS:
				return ((InternalEList<?>)getInvariants()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case CGModelPackage.CG_CLASS__CONTAINING_PACKAGE:
				return eInternalContainer().eInverseRemove(this, CGModelPackage.CG_PACKAGE__CLASSES, CGPackage.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CGModelPackage.CG_CLASS__CONTAINING_PACKAGE:
				return getContainingPackage();
			case CGModelPackage.CG_CLASS__OPERATIONS:
				return getOperations();
			case CGModelPackage.CG_CLASS__PROPERTIES:
				return getProperties();
			case CGModelPackage.CG_CLASS__INVARIANTS:
				return getInvariants();
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
			case CGModelPackage.CG_CLASS__CONTAINING_PACKAGE:
				setContainingPackage((CGPackage)newValue);
				return;
			case CGModelPackage.CG_CLASS__OPERATIONS:
				getOperations().clear();
				getOperations().addAll((Collection<? extends CGOperation>)newValue);
				return;
			case CGModelPackage.CG_CLASS__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends CGProperty>)newValue);
				return;
			case CGModelPackage.CG_CLASS__INVARIANTS:
				getInvariants().clear();
				getInvariants().addAll((Collection<? extends CGConstraint>)newValue);
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
			case CGModelPackage.CG_CLASS__CONTAINING_PACKAGE:
				setContainingPackage((CGPackage)null);
				return;
			case CGModelPackage.CG_CLASS__OPERATIONS:
				getOperations().clear();
				return;
			case CGModelPackage.CG_CLASS__PROPERTIES:
				getProperties().clear();
				return;
			case CGModelPackage.CG_CLASS__INVARIANTS:
				getInvariants().clear();
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
			case CGModelPackage.CG_CLASS__CONTAINING_PACKAGE:
				return getContainingPackage() != null;
			case CGModelPackage.CG_CLASS__OPERATIONS:
				return operations != null && !operations.isEmpty();
			case CGModelPackage.CG_CLASS__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case CGModelPackage.CG_CLASS__INVARIANTS:
				return invariants != null && !invariants.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public @Nullable <R> R accept(@NonNull CGModelVisitor<R> visitor) {
		return visitor.visitCGClass(this);
	}

} //CGClassImpl
