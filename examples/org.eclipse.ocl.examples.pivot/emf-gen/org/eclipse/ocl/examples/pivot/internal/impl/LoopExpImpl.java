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
 * $Id: LoopExpImpl.java,v 1.5 2011/05/19 16:55:39 ewillink Exp $
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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.InvalidValueException;
import org.eclipse.ocl.examples.domain.messages.EvaluatorMessages;
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.domain.values.ValueFactory;
import org.eclipse.ocl.examples.library.ecore.EcoreExecutorManager;
import org.eclipse.ocl.examples.library.executor.ExecutorType;
import org.eclipse.ocl.examples.library.oclstdlib.OCLstdlibTables;
import org.eclipse.ocl.examples.pivot.Annotation;
import org.eclipse.ocl.examples.pivot.AppliedStereotype;
import org.eclipse.ocl.examples.pivot.Comment;
import org.eclipse.ocl.examples.pivot.Constraint;
import org.eclipse.ocl.examples.pivot.Feature;
import org.eclipse.ocl.examples.pivot.Iteration;
import org.eclipse.ocl.examples.pivot.LoopExp;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.PivotPackage;
import org.eclipse.ocl.examples.pivot.PivotTables;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.bodies.LoopExpBodies;
import org.eclipse.ocl.examples.pivot.util.PivotValidator;
import org.eclipse.ocl.examples.pivot.util.Visitor;
import org.eclipse.osgi.util.NLS;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Loop Exp</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.pivot.internal.impl.LoopExpImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.ocl.examples.pivot.internal.impl.LoopExpImpl#getIterator <em>Iterator</em>}</li>
 *   <li>{@link org.eclipse.ocl.examples.pivot.internal.impl.LoopExpImpl#getReferredIteration <em>Referred Iteration</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class LoopExpImpl
		extends CallExpImpl
		implements LoopExp {

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected OCLExpression body;

	/**
	 * The cached value of the '{@link #getIterator() <em>Iterator</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIterator()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> iterator;

	/**
	 * The cached value of the '{@link #getReferredIteration() <em>Referred Iteration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferredIteration()
	 * @generated
	 * @ordered
	 */
	protected Iteration referredIteration;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoopExpImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PivotPackage.Literals.LOOP_EXP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OCLExpression getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBody(OCLExpression newBody,
			NotificationChain msgs) {
		OCLExpression oldBody = body;
		body = newBody;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PivotPackage.LOOP_EXP__BODY, oldBody, newBody);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(OCLExpression newBody) {
		if (newBody != body)
		{
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PivotPackage.LOOP_EXP__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PivotPackage.LOOP_EXP__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PivotPackage.LOOP_EXP__BODY, newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OCLExpression createBody(EClass eClass) {
		OCLExpression newBody = (OCLExpression) create(eClass);
		setBody(newBody);
		return newBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Variable> getIterator()
	{
		if (iterator == null)
		{
			iterator = new EObjectContainmentEList<Variable>(Variable.class, this, PivotPackage.LOOP_EXP__ITERATOR);
		}
		return iterator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable createIterator() {
		Variable newIterator = (Variable) create(PivotPackage.Literals.VARIABLE);
		getIterator().add(newIterator);
		return newIterator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iteration getReferredIteration()
	{
		if (referredIteration != null && ((EObject)referredIteration).eIsProxy())
		{
			InternalEObject oldReferredIteration = (InternalEObject)referredIteration;
			referredIteration = (Iteration)eResolveProxy(oldReferredIteration);
			if (referredIteration != oldReferredIteration)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PivotPackage.LOOP_EXP__REFERRED_ITERATION, oldReferredIteration, referredIteration));
			}
		}
		return referredIteration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iteration basicGetReferredIteration()
	{
		return referredIteration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferredIteration(Iteration newReferredIteration)
	{
		Iteration oldReferredIteration = referredIteration;
		referredIteration = newReferredIteration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PivotPackage.LOOP_EXP__REFERRED_ITERATION, oldReferredIteration, referredIteration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNoInitializers(DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		/*
		self.iterator->forAll(initExpression->isEmpty())
		*/
		try {
			final DomainEvaluator evaluator = new EcoreExecutorManager(this, null, PivotTables.LIBRARY);
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final Value self = valueFactory.valueOf(this);
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			
			final DomainType returnType = T_Boolean;
			final Value result = LoopExpBodies._invariant_NoInitializers.INSTANCE.evaluate(evaluator, returnType, self);
			final boolean resultIsNull = result.isNull();
			if (!resultIsNull && result.asBoolean()) {	// true => true, false/null => dropthrough, invalid => exception
				return true;
			}
			if (diagnostics != null) {
				int severity = resultIsNull ? Diagnostic.ERROR : Diagnostic.WARNING;
				String message = NLS.bind(EvaluatorMessages.ValidationConstraintIsNotSatisfied_ERROR_, new Object[]{"LoopExp", "NoInitializers", EObjectValidator.getObjectLabel(this, context)});
			    diagnostics.add(new BasicDiagnostic(severity, PivotValidator.DIAGNOSTIC_SOURCE, PivotValidator.LOOP_EXP__NO_INITIALIZERS, message, new Object [] { this }));
			}
			return false;
		} catch (InvalidValueException e) {
			String message = NLS.bind(EvaluatorMessages.ValidationEvaluationFailed_ERROR_, new Object[]{"LoopExp", "NoInitializers", EObjectValidator.getObjectLabel(this, context)});
			throw new WrappedException(message, e);
		}
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSourceIsCollection(DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		/*
		source.type.oclIsKindOf(CollectionType)
		*/
		try {
			final DomainEvaluator evaluator = new EcoreExecutorManager(this, null, PivotTables.LIBRARY);
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final Value self = valueFactory.valueOf(this);
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			
			final DomainType returnType = T_Boolean;
			final Value result = LoopExpBodies._invariant_SourceIsCollection.INSTANCE.evaluate(evaluator, returnType, self);
			final boolean resultIsNull = result.isNull();
			if (!resultIsNull && result.asBoolean()) {	// true => true, false/null => dropthrough, invalid => exception
				return true;
			}
			if (diagnostics != null) {
				int severity = resultIsNull ? Diagnostic.ERROR : Diagnostic.WARNING;
				String message = NLS.bind(EvaluatorMessages.ValidationConstraintIsNotSatisfied_ERROR_, new Object[]{"LoopExp", "SourceIsCollection", EObjectValidator.getObjectLabel(this, context)});
			    diagnostics.add(new BasicDiagnostic(severity, PivotValidator.DIAGNOSTIC_SOURCE, PivotValidator.LOOP_EXP__SOURCE_IS_COLLECTION, message, new Object [] { this }));
			}
			return false;
		} catch (InvalidValueException e) {
			String message = NLS.bind(EvaluatorMessages.ValidationEvaluationFailed_ERROR_, new Object[]{"LoopExp", "SourceIsCollection", EObjectValidator.getObjectLabel(this, context)});
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
			case PivotPackage.LOOP_EXP__OWNED_COMMENT:
				return ((InternalEList<?>)getOwnedComment()).basicRemove(otherEnd, msgs);
			case PivotPackage.LOOP_EXP__APPLIED_STEREOTYPE:
				return ((InternalEList<?>)getAppliedStereotype()).basicRemove(otherEnd, msgs);
			case PivotPackage.LOOP_EXP__OWNED_RULE:
				return ((InternalEList<?>)getOwnedRule()).basicRemove(otherEnd, msgs);
			case PivotPackage.LOOP_EXP__OWNED_ANNOTATION:
				return ((InternalEList<?>)getOwnedAnnotation()).basicRemove(otherEnd, msgs);
			case PivotPackage.LOOP_EXP__SOURCE:
				return basicSetSource(null, msgs);
			case PivotPackage.LOOP_EXP__BODY:
				return basicSetBody(null, msgs);
			case PivotPackage.LOOP_EXP__ITERATOR:
				return ((InternalEList<?>)getIterator()).basicRemove(otherEnd, msgs);
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
			case PivotPackage.LOOP_EXP__OWNED_COMMENT:
				return getOwnedComment();
			case PivotPackage.LOOP_EXP__APPLIED_STEREOTYPE:
				return getAppliedStereotype();
			case PivotPackage.LOOP_EXP__NAME:
				return getName();
			case PivotPackage.LOOP_EXP__OWNED_RULE:
				return getOwnedRule();
			case PivotPackage.LOOP_EXP__IS_STATIC:
				return isStatic();
			case PivotPackage.LOOP_EXP__OWNED_ANNOTATION:
				return getOwnedAnnotation();
			case PivotPackage.LOOP_EXP__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case PivotPackage.LOOP_EXP__SOURCE:
				return getSource();
			case PivotPackage.LOOP_EXP__IMPLICIT:
				return isImplicit();
			case PivotPackage.LOOP_EXP__BODY:
				return getBody();
			case PivotPackage.LOOP_EXP__ITERATOR:
				return getIterator();
			case PivotPackage.LOOP_EXP__REFERRED_ITERATION:
				if (resolve) return getReferredIteration();
				return basicGetReferredIteration();
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
			case PivotPackage.LOOP_EXP__OWNED_COMMENT:
				getOwnedComment().clear();
				getOwnedComment().addAll((Collection<? extends Comment>)newValue);
				return;
			case PivotPackage.LOOP_EXP__APPLIED_STEREOTYPE:
				getAppliedStereotype().clear();
				getAppliedStereotype().addAll((Collection<? extends AppliedStereotype>)newValue);
				return;
			case PivotPackage.LOOP_EXP__NAME:
				setName((String)newValue);
				return;
			case PivotPackage.LOOP_EXP__OWNED_RULE:
				getOwnedRule().clear();
				getOwnedRule().addAll((Collection<? extends Constraint>)newValue);
				return;
			case PivotPackage.LOOP_EXP__IS_STATIC:
				setIsStatic((Boolean)newValue);
				return;
			case PivotPackage.LOOP_EXP__OWNED_ANNOTATION:
				getOwnedAnnotation().clear();
				getOwnedAnnotation().addAll((Collection<? extends Annotation>)newValue);
				return;
			case PivotPackage.LOOP_EXP__TYPE:
				setType((Type)newValue);
				return;
			case PivotPackage.LOOP_EXP__SOURCE:
				setSource((OCLExpression)newValue);
				return;
			case PivotPackage.LOOP_EXP__IMPLICIT:
				setImplicit((Boolean)newValue);
				return;
			case PivotPackage.LOOP_EXP__BODY:
				setBody((OCLExpression)newValue);
				return;
			case PivotPackage.LOOP_EXP__ITERATOR:
				getIterator().clear();
				getIterator().addAll((Collection<? extends Variable>)newValue);
				return;
			case PivotPackage.LOOP_EXP__REFERRED_ITERATION:
				setReferredIteration((Iteration)newValue);
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
			case PivotPackage.LOOP_EXP__OWNED_COMMENT:
				getOwnedComment().clear();
				return;
			case PivotPackage.LOOP_EXP__APPLIED_STEREOTYPE:
				getAppliedStereotype().clear();
				return;
			case PivotPackage.LOOP_EXP__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PivotPackage.LOOP_EXP__OWNED_RULE:
				getOwnedRule().clear();
				return;
			case PivotPackage.LOOP_EXP__IS_STATIC:
				setIsStatic(IS_STATIC_EDEFAULT);
				return;
			case PivotPackage.LOOP_EXP__OWNED_ANNOTATION:
				getOwnedAnnotation().clear();
				return;
			case PivotPackage.LOOP_EXP__TYPE:
				setType((Type)null);
				return;
			case PivotPackage.LOOP_EXP__SOURCE:
				setSource((OCLExpression)null);
				return;
			case PivotPackage.LOOP_EXP__IMPLICIT:
				setImplicit(IMPLICIT_EDEFAULT);
				return;
			case PivotPackage.LOOP_EXP__BODY:
				setBody((OCLExpression)null);
				return;
			case PivotPackage.LOOP_EXP__ITERATOR:
				getIterator().clear();
				return;
			case PivotPackage.LOOP_EXP__REFERRED_ITERATION:
				setReferredIteration((Iteration)null);
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
			case PivotPackage.LOOP_EXP__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case PivotPackage.LOOP_EXP__APPLIED_STEREOTYPE:
				return appliedStereotype != null && !appliedStereotype.isEmpty();
			case PivotPackage.LOOP_EXP__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PivotPackage.LOOP_EXP__OWNED_RULE:
				return ownedRule != null && !ownedRule.isEmpty();
			case PivotPackage.LOOP_EXP__IS_STATIC:
				return ((eFlags & IS_STATIC_EFLAG) != 0) != IS_STATIC_EDEFAULT;
			case PivotPackage.LOOP_EXP__OWNED_ANNOTATION:
				return ownedAnnotation != null && !ownedAnnotation.isEmpty();
			case PivotPackage.LOOP_EXP__TYPE:
				return type != null;
			case PivotPackage.LOOP_EXP__SOURCE:
				return source != null;
			case PivotPackage.LOOP_EXP__IMPLICIT:
				return ((eFlags & IMPLICIT_EFLAG) != 0) != IMPLICIT_EDEFAULT;
			case PivotPackage.LOOP_EXP__BODY:
				return body != null;
			case PivotPackage.LOOP_EXP__ITERATOR:
				return iterator != null && !iterator.isEmpty();
			case PivotPackage.LOOP_EXP__REFERRED_ITERATION:
				return referredIteration != null;
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
			case PivotPackage.LOOP_EXP___ALL_OWNED_ELEMENTS:
				return allOwnedElements();
			case PivotPackage.LOOP_EXP___GET_VALUE__TYPE_STRING:
				return getValue((Type)arguments.get(0), (String)arguments.get(1));
			case PivotPackage.LOOP_EXP___VALIDATE_NOT_OWN_SELF__DIAGNOSTICCHAIN_MAP:
				return validateNotOwnSelf((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case PivotPackage.LOOP_EXP___VALIDATE_SOURCE_IS_COLLECTION__DIAGNOSTICCHAIN_MAP:
				return validateSourceIsCollection((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case PivotPackage.LOOP_EXP___VALIDATE_NO_INITIALIZERS__DIAGNOSTICCHAIN_MAP:
				return validateNoInitializers((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
		}
		return eDynamicInvoke(operationID, arguments);
	}

	@Override
	public <R> R accept(Visitor<R> visitor) {
		return visitor.visitLoopExp(this);
	}

	@Override
	public Feature getReferredFeature()
	{
		return getReferredIteration();
	}
} //LoopExpImpl
