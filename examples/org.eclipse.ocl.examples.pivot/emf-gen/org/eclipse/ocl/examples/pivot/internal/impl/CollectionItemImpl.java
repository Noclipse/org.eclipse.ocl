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
 * $Id: CollectionItemImpl.java,v 1.4 2011/05/19 16:55:39 ewillink Exp $
 */
package org.eclipse.ocl.examples.pivot.internal.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.InvalidValueException;
import org.eclipse.ocl.examples.domain.messages.EvaluatorMessages;
import org.eclipse.ocl.examples.domain.values.util.ValuesUtil;
import org.eclipse.ocl.examples.library.ecore.EcoreExecutorManager;
import org.eclipse.ocl.examples.library.executor.ExecutorType;
import org.eclipse.ocl.examples.library.oclstdlib.OCLstdlibTables;
import org.eclipse.ocl.examples.pivot.Annotation;
import org.eclipse.ocl.examples.pivot.CollectionItem;
import org.eclipse.ocl.examples.pivot.Comment;
import org.eclipse.ocl.examples.pivot.Constraint;
import org.eclipse.ocl.examples.pivot.ElementExtension;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.PivotPackage;
import org.eclipse.ocl.examples.pivot.PivotTables;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.bodies.CollectionItemBodies;
import org.eclipse.ocl.examples.pivot.util.PivotValidator;
import org.eclipse.ocl.examples.pivot.util.Visitor;
import org.eclipse.osgi.util.NLS;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Collection Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.pivot.internal.impl.CollectionItemImpl#getItem <em>Item</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CollectionItemImpl
		extends CollectionLiteralPartImpl
		implements CollectionItem {

	/**
	 * The cached value of the '{@link #getItem() <em>Item</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItem()
	 * @generated
	 * @ordered
	 */
	protected OCLExpression item;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CollectionItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PivotPackage.Literals.COLLECTION_ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OCLExpression getItem() {
		return item;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetItem(OCLExpression newItem,
			NotificationChain msgs) {
		OCLExpression oldItem = item;
		item = newItem;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PivotPackage.COLLECTION_ITEM__ITEM, oldItem, newItem);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setItem(OCLExpression newItem) {
		if (newItem != item)
		{
			NotificationChain msgs = null;
			if (item != null)
				msgs = ((InternalEObject)item).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PivotPackage.COLLECTION_ITEM__ITEM, null, msgs);
			if (newItem != null)
				msgs = ((InternalEObject)newItem).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PivotPackage.COLLECTION_ITEM__ITEM, null, msgs);
			msgs = basicSetItem(newItem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PivotPackage.COLLECTION_ITEM__ITEM, newItem, newItem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OCLExpression createItem(EClass eClass) {
		OCLExpression newItem = (OCLExpression) create(eClass);
		setItem(newItem);
		return newItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypeIsItemType(DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		/*
		type = item.type
		*/
		try {
			final @NonNull DomainEvaluator evaluator = new EcoreExecutorManager(this, PivotTables.LIBRARY);
			final @NonNull ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			
			final @NonNull DomainType returnType = T_Boolean;
			final @NonNull Object result = CollectionItemBodies._invariant_TypeIsItemType.INSTANCE.evaluate(evaluator, returnType.getTypeId(), this);
			final boolean resultIsNull = ValuesUtil.isNull(result);
			if (!resultIsNull && ValuesUtil.asBoolean(result)) {	// true => true, false/null => dropthrough, invalid => exception
				return true;
			}
			if (diagnostics != null) {
				int severity = resultIsNull ? Diagnostic.ERROR : Diagnostic.WARNING;
				String message = NLS.bind(EvaluatorMessages.ValidationConstraintIsNotSatisfied_ERROR_, new Object[]{"CollectionItem", "TypeIsItemType", EObjectValidator.getObjectLabel(this, context)});
			    diagnostics.add(new BasicDiagnostic(severity, PivotValidator.DIAGNOSTIC_SOURCE, PivotValidator.COLLECTION_ITEM__TYPE_IS_ITEM_TYPE, message, new Object [] { this }));
			}
			return false;
		} catch (InvalidValueException e) {
			String message = NLS.bind(EvaluatorMessages.ValidationEvaluationFailed_ERROR_, new Object[]{"CollectionItem", "TypeIsItemType", EObjectValidator.getObjectLabel(this, context)});
			throw new WrappedException(message, e);
		}
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case PivotPackage.COLLECTION_ITEM__OWNED_COMMENT:
				return ((InternalEList<?>)getOwnedComment()).basicRemove(otherEnd, msgs);
			case PivotPackage.COLLECTION_ITEM__EXTENSION:
				return ((InternalEList<?>)getExtension()).basicRemove(otherEnd, msgs);
			case PivotPackage.COLLECTION_ITEM__OWNED_RULE:
				return ((InternalEList<?>)getOwnedRule()).basicRemove(otherEnd, msgs);
			case PivotPackage.COLLECTION_ITEM__OWNED_ANNOTATION:
				return ((InternalEList<?>)getOwnedAnnotation()).basicRemove(otherEnd, msgs);
			case PivotPackage.COLLECTION_ITEM__ITEM:
				return basicSetItem(null, msgs);
		}
		return eDynamicInverseRemove(otherEnd, featureID, msgs);
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
			case PivotPackage.COLLECTION_ITEM__OWNED_COMMENT:
				return getOwnedComment();
			case PivotPackage.COLLECTION_ITEM__EXTENSION:
				return getExtension();
			case PivotPackage.COLLECTION_ITEM__NAME:
				return getName();
			case PivotPackage.COLLECTION_ITEM__OWNED_RULE:
				return getOwnedRule();
			case PivotPackage.COLLECTION_ITEM__IS_STATIC:
				return isStatic();
			case PivotPackage.COLLECTION_ITEM__OWNED_ANNOTATION:
				return getOwnedAnnotation();
			case PivotPackage.COLLECTION_ITEM__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case PivotPackage.COLLECTION_ITEM__ITEM:
				return getItem();
		}
		return eDynamicGet(featureID, resolve, coreType);
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
			case PivotPackage.COLLECTION_ITEM__OWNED_COMMENT:
				getOwnedComment().clear();
				getOwnedComment().addAll((Collection<? extends Comment>)newValue);
				return;
			case PivotPackage.COLLECTION_ITEM__EXTENSION:
				getExtension().clear();
				getExtension().addAll((Collection<? extends ElementExtension>)newValue);
				return;
			case PivotPackage.COLLECTION_ITEM__NAME:
				setName((String)newValue);
				return;
			case PivotPackage.COLLECTION_ITEM__OWNED_RULE:
				getOwnedRule().clear();
				getOwnedRule().addAll((Collection<? extends Constraint>)newValue);
				return;
			case PivotPackage.COLLECTION_ITEM__IS_STATIC:
				setIsStatic((Boolean)newValue);
				return;
			case PivotPackage.COLLECTION_ITEM__OWNED_ANNOTATION:
				getOwnedAnnotation().clear();
				getOwnedAnnotation().addAll((Collection<? extends Annotation>)newValue);
				return;
			case PivotPackage.COLLECTION_ITEM__TYPE:
				setType((Type)newValue);
				return;
			case PivotPackage.COLLECTION_ITEM__ITEM:
				setItem((OCLExpression)newValue);
				return;
		}
		eDynamicSet(featureID, newValue);
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
			case PivotPackage.COLLECTION_ITEM__OWNED_COMMENT:
				getOwnedComment().clear();
				return;
			case PivotPackage.COLLECTION_ITEM__EXTENSION:
				getExtension().clear();
				return;
			case PivotPackage.COLLECTION_ITEM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PivotPackage.COLLECTION_ITEM__OWNED_RULE:
				getOwnedRule().clear();
				return;
			case PivotPackage.COLLECTION_ITEM__IS_STATIC:
				setIsStatic(IS_STATIC_EDEFAULT);
				return;
			case PivotPackage.COLLECTION_ITEM__OWNED_ANNOTATION:
				getOwnedAnnotation().clear();
				return;
			case PivotPackage.COLLECTION_ITEM__TYPE:
				setType((Type)null);
				return;
			case PivotPackage.COLLECTION_ITEM__ITEM:
				setItem((OCLExpression)null);
				return;
		}
		eDynamicUnset(featureID);
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
			case PivotPackage.COLLECTION_ITEM__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case PivotPackage.COLLECTION_ITEM__EXTENSION:
				return extension != null && !extension.isEmpty();
			case PivotPackage.COLLECTION_ITEM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PivotPackage.COLLECTION_ITEM__OWNED_RULE:
				return ownedRule != null && !ownedRule.isEmpty();
			case PivotPackage.COLLECTION_ITEM__IS_STATIC:
				return ((eFlags & IS_STATIC_EFLAG) != 0) != IS_STATIC_EDEFAULT;
			case PivotPackage.COLLECTION_ITEM__OWNED_ANNOTATION:
				return ownedAnnotation != null && !ownedAnnotation.isEmpty();
			case PivotPackage.COLLECTION_ITEM__TYPE:
				return type != null;
			case PivotPackage.COLLECTION_ITEM__ITEM:
				return item != null;
		}
		return eDynamicIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException
	{
		switch (operationID)
		{
			case PivotPackage.COLLECTION_ITEM___ALL_OWNED_ELEMENTS:
				return allOwnedElements();
			case PivotPackage.COLLECTION_ITEM___GET_VALUE__TYPE_STRING:
				return getValue((Type)arguments.get(0), (String)arguments.get(1));
			case PivotPackage.COLLECTION_ITEM___VALIDATE_NOT_OWN_SELF__DIAGNOSTICCHAIN_MAP:
				return validateNotOwnSelf((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case PivotPackage.COLLECTION_ITEM___VALIDATE_TYPE_IS_ITEM_TYPE__DIAGNOSTICCHAIN_MAP:
				return validateTypeIsItemType((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
		}
		return eDynamicInvoke(operationID, arguments);
	}

	@Override
	public <R> R accept(@NonNull Visitor<R> visitor) {
		return visitor.visitCollectionItem(this);
	}
} //CollectionItemImpl
