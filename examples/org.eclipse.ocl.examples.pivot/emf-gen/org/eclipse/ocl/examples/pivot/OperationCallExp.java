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
 * $Id: OperationCallExp.java,v 1.4 2011/05/19 16:55:39 ewillink Exp $
 */
package org.eclipse.ocl.examples.pivot;

import java.util.Map;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation Call Exp</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.pivot.OperationCallExp#getArgument <em>Argument</em>}</li>
 *   <li>{@link org.eclipse.ocl.examples.pivot.OperationCallExp#getReferredOperation <em>Referred Operation</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.ocl.examples.pivot.PivotPackage#getOperationCallExp()
 * @model
 * @generated
 */
public interface OperationCallExp
		extends FeatureCallExp, ReferringElement {

	/**
	 * Returns the value of the '<em><b>Argument</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.ocl.examples.pivot.OCLExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Argument</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Argument</em>' containment reference list.
	 * @see org.eclipse.ocl.examples.pivot.PivotPackage#getOperationCallExp_Argument()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://www.eclipse.org/ocl/3.1.0/Pivot!OperationCallExp!argument'"
	 * @generated
	 */
	EList<OCLExpression> getArgument();

	/**
	 * Creates a new {@link org.eclipse.ocl.examples.pivot.OCLExpression} and appends it to the '<em><b>Argument</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.ocl.examples.pivot.OCLExpression} to create.
	 * @return The new {@link org.eclipse.ocl.examples.pivot.OCLExpression}.
	 * @see #getArgument()
	 * @generated
	 */
	OCLExpression createArgument(EClass eClass);

	/**
	 * Returns the value of the '<em><b>Referred Operation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referred Operation</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referred Operation</em>' reference.
	 * @see #setReferredOperation(Operation)
	 * @see org.eclipse.ocl.examples.pivot.PivotPackage#getOperationCallExp_ReferredOperation()
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://www.eclipse.org/ocl/3.1.0/Pivot!OperationCallExp!referredOperation'"
	 * @generated
	 */
	Operation getReferredOperation();

	/**
	 * Sets the value of the '{@link org.eclipse.ocl.examples.pivot.OperationCallExp#getReferredOperation <em>Referred Operation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Referred Operation</em>' reference.
	 * @see #getReferredOperation()
	 * @generated
	 */
	void setReferredOperation(Operation value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * There must be exactly as many arguments as the referred operation has parameters.
	 * <!-- end-model-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='/*\nargument->size() = referredOperation.ownedParameter->size()\n\052/\nfinal @<%org.eclipse.jdt.annotation.NonNull%> <%org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator%> evaluator = new <%org.eclipse.ocl.examples.library.ecore.EcoreExecutorManager%>(this, <%org.eclipse.ocl.examples.pivot.PivotTables%>.LIBRARY);\nfinal @NonNull <%org.eclipse.ocl.examples.domain.ids.PrimitiveTypeId%> T_Boolean = <%org.eclipse.ocl.examples.domain.ids.TypeId%>.BOOLEAN;\n\nfinal @NonNull Object result = <%org.eclipse.ocl.examples.pivot.bodies.OperationCallExpBodies%>._invariant_ArgumentCount.INSTANCE.evaluate(evaluator, T_Boolean, this);\nfinal boolean resultIsNull = <%org.eclipse.ocl.examples.domain.values.util.ValuesUtil%>.isNull(result);\nif (!resultIsNull && <%org.eclipse.ocl.examples.domain.values.util.ValuesUtil%>.asBoolean(result)) {\t// true => true, false/null => dropthrough, invalid => exception\n\treturn true;\n}\nif (diagnostics != null) {\n\tint severity = resultIsNull ? <%org.eclipse.emf.common.util.Diagnostic%>.ERROR : <%org.eclipse.emf.common.util.Diagnostic%>.WARNING;\n\tString message = <%org.eclipse.osgi.util.NLS%>.bind(<%org.eclipse.ocl.examples.domain.messages.EvaluatorMessages%>.ValidationConstraintIsNotSatisfied_ERROR_, new Object[]{\"OperationCallExp\", \"ArgumentCount\", <%org.eclipse.emf.ecore.util.EObjectValidator%>.getObjectLabel(this, context)});\n    diagnostics.add(new <%org.eclipse.emf.common.util.BasicDiagnostic%>(severity, <%org.eclipse.ocl.examples.pivot.util.PivotValidator%>.DIAGNOSTIC_SOURCE, <%org.eclipse.ocl.examples.pivot.util.PivotValidator%>.OPERATION_CALL_EXP__ARGUMENT_COUNT, message, new Object [] { this }));\n}\nreturn false;\n\n'"
	 * @generated
	 */
	boolean validateArgumentCount(DiagnosticChain diagnostics, Map<Object, Object> context);

} // OperationCallExp
