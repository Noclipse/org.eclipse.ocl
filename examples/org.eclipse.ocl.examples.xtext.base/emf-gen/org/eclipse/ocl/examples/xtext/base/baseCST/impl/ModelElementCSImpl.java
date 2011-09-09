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
 * $Id: ModelElementCSImpl.java,v 1.3 2011/02/11 20:59:26 ewillink Exp $
 */

package org.eclipse.ocl.examples.xtext.base.baseCST.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.examples.xtext.base.baseCST.AnnotationElementCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.BaseCSTPackage;
import org.eclipse.ocl.examples.xtext.base.baseCST.ModelElementCS;
import org.eclipse.ocl.examples.xtext.base.util.BaseCSVisitor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Element CS</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.xtext.base.baseCST.impl.ModelElementCSImpl#getOwnedAnnotation <em>Owned Annotation</em>}</li>
 *   <li>{@link org.eclipse.ocl.examples.xtext.base.baseCST.impl.ModelElementCSImpl#getOriginalXmiId <em>Original Xmi Id</em>}</li>
 *   <li>{@link org.eclipse.ocl.examples.xtext.base.baseCST.impl.ModelElementCSImpl#getCsi <em>Csi</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ModelElementCSImpl extends PivotableElementCSImpl implements ModelElementCS {
	/**
	 * The cached value of the '{@link #getOwnedAnnotation() <em>Owned Annotation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedAnnotation()
	 * @generated
	 * @ordered
	 */
	protected EList<AnnotationElementCS> ownedAnnotation;

	/**
	 * The default value of the '{@link #getOriginalXmiId() <em>Original Xmi Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalXmiId()
	 * @generated
	 * @ordered
	 */
	protected static final String ORIGINAL_XMI_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOriginalXmiId() <em>Original Xmi Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalXmiId()
	 * @generated
	 * @ordered
	 */
	protected String originalXmiId = ORIGINAL_XMI_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getCsi() <em>Csi</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCsi()
	 * @generated
	 * @ordered
	 */
	protected static final String CSI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCsi() <em>Csi</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCsi()
	 * @generated
	 * @ordered
	 */
	protected String csi = CSI_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelElementCSImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BaseCSTPackage.Literals.MODEL_ELEMENT_CS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AnnotationElementCS> getOwnedAnnotation()
	{
		if (ownedAnnotation == null)
		{
			ownedAnnotation = new EObjectContainmentEList<AnnotationElementCS>(AnnotationElementCS.class, this, BaseCSTPackage.MODEL_ELEMENT_CS__OWNED_ANNOTATION);
		}
		return ownedAnnotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOriginalXmiId() {
		return originalXmiId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalXmiId(String newOriginalXmiId) {
		String oldOriginalXmiId = originalXmiId;
		originalXmiId = newOriginalXmiId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BaseCSTPackage.MODEL_ELEMENT_CS__ORIGINAL_XMI_ID, oldOriginalXmiId, originalXmiId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCsi()
	{
		return csi;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCsi(String newCsi)
	{
		String oldCsi = csi;
		csi = newCsi;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BaseCSTPackage.MODEL_ELEMENT_CS__CSI, oldCsi, csi));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case BaseCSTPackage.MODEL_ELEMENT_CS__OWNED_ANNOTATION:
				return ((InternalEList<?>)getOwnedAnnotation()).basicRemove(otherEnd, msgs);
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
		switch (featureID)
		{
			case BaseCSTPackage.MODEL_ELEMENT_CS__OWNED_ANNOTATION:
				return getOwnedAnnotation();
			case BaseCSTPackage.MODEL_ELEMENT_CS__ORIGINAL_XMI_ID:
				return getOriginalXmiId();
			case BaseCSTPackage.MODEL_ELEMENT_CS__CSI:
				return getCsi();
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
		switch (featureID)
		{
			case BaseCSTPackage.MODEL_ELEMENT_CS__OWNED_ANNOTATION:
				getOwnedAnnotation().clear();
				getOwnedAnnotation().addAll((Collection<? extends AnnotationElementCS>)newValue);
				return;
			case BaseCSTPackage.MODEL_ELEMENT_CS__ORIGINAL_XMI_ID:
				setOriginalXmiId((String)newValue);
				return;
			case BaseCSTPackage.MODEL_ELEMENT_CS__CSI:
				setCsi((String)newValue);
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
			case BaseCSTPackage.MODEL_ELEMENT_CS__OWNED_ANNOTATION:
				getOwnedAnnotation().clear();
				return;
			case BaseCSTPackage.MODEL_ELEMENT_CS__ORIGINAL_XMI_ID:
				setOriginalXmiId(ORIGINAL_XMI_ID_EDEFAULT);
				return;
			case BaseCSTPackage.MODEL_ELEMENT_CS__CSI:
				setCsi(CSI_EDEFAULT);
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
			case BaseCSTPackage.MODEL_ELEMENT_CS__OWNED_ANNOTATION:
				return ownedAnnotation != null && !ownedAnnotation.isEmpty();
			case BaseCSTPackage.MODEL_ELEMENT_CS__ORIGINAL_XMI_ID:
				return ORIGINAL_XMI_ID_EDEFAULT == null ? originalXmiId != null : !ORIGINAL_XMI_ID_EDEFAULT.equals(originalXmiId);
			case BaseCSTPackage.MODEL_ELEMENT_CS__CSI:
				return CSI_EDEFAULT == null ? csi != null : !CSI_EDEFAULT.equals(csi);
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

	@Override
	public <R, C> R accept(BaseCSVisitor<R, C> visitor) {
		return visitor.visitModelElementCS(this);
	}
} //ModelElementCSImpl
