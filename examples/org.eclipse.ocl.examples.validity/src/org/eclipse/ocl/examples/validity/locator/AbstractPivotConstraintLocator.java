/*******************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *	E.D.Willink (CEA LIST) - initial API and implementation
 *******************************************************************************/
package org.eclipse.ocl.examples.validity.locator;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.emf.validation.validity.LeafConstrainingNode;
import org.eclipse.ocl.examples.emf.validation.validity.locator.AbstractConstraintLocator;
import org.eclipse.ocl.pivot.Constraint;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.LanguageExpression;
import org.eclipse.ocl.pivot.ParserException;
import org.eclipse.ocl.pivot.internal.EnvironmentInternal;
import org.eclipse.ocl.pivot.internal.evaluation.EvaluationVisitor;
import org.eclipse.ocl.pivot.internal.manager.MetamodelManager;
import org.eclipse.ocl.pivot.internal.messages.PivotMessagesInternal;
import org.eclipse.ocl.pivot.internal.utilities.ConstraintEvaluator;
import org.eclipse.ocl.pivot.internal.utilities.PivotEnvironmentFactory;
import org.eclipse.ocl.pivot.utilities.StringUtil;
import org.eclipse.ocl.pivot.values.InvalidValueException;

public abstract class AbstractPivotConstraintLocator extends AbstractConstraintLocator
{
	protected static abstract class AbstractConstraintLocator extends ConstraintEvaluator<Diagnostic>
	{
		protected final @NonNull MetamodelManager metamodelManager;
		protected final @Nullable Object object;

		protected AbstractConstraintLocator(@NonNull MetamodelManager metamodelManager, @NonNull ExpressionInOCL expression, @Nullable Object object) {
			super(expression);
			this.metamodelManager = metamodelManager;
			this.object = object;
		}

		@Override
		protected Diagnostic handleExceptionResult(@NonNull Throwable e) {
			String message = StringUtil.bind(PivotMessagesInternal.ValidationConstraintException_ERROR_,
				getConstraintTypeName(), getConstraintName(), getObjectLabel(), e);
			return new BasicDiagnostic(Diagnostic.ERROR, EObjectValidator.DIAGNOSTIC_SOURCE, 0, message, new Object [] { object });
		}

		@Override
		protected Diagnostic handleFailureResult(@Nullable Object result) {
			String message = getConstraintResultMessage(result);
			int severity = getConstraintResultSeverity(result);
			return new BasicDiagnostic(severity, EObjectValidator.DIAGNOSTIC_SOURCE, 0, message, new Object [] { object });
		}

		@Override
		protected Diagnostic handleInvalidExpression(@NonNull String message) {
			return new BasicDiagnostic(Diagnostic.ERROR, EObjectValidator.DIAGNOSTIC_SOURCE, 0, message, new Object [] { object });
		}

		@Override
		protected Diagnostic handleInvalidResult(@NonNull InvalidValueException e) {
			String message = StringUtil.bind(PivotMessagesInternal.ValidationResultIsInvalid_ERROR_,
				getConstraintTypeName(), getConstraintName(), getObjectLabel(), e.getLocalizedMessage());
			return new BasicDiagnostic(Diagnostic.ERROR, EObjectValidator.DIAGNOSTIC_SOURCE, 0, message, new Object [] { object });
		}

		@Override
		protected Diagnostic handleSuccessResult() {
			return null;
		}
	}

	protected @NonNull EvaluationVisitor createEvaluationVisitor(@NonNull MetamodelManager metamodelManager,
			@NonNull ExpressionInOCL query, @Nullable Object contextObject, @Nullable Monitor monitor) {
		PivotEnvironmentFactory environmentFactory = new PivotEnvironmentFactory(null, metamodelManager);
		EnvironmentInternal rootEnvironment = environmentFactory.createEnvironment();
		EvaluationVisitor evaluationVisitor = environmentFactory.createEvaluationVisitor(rootEnvironment, contextObject, query, null);
		evaluationVisitor.setMonitor(monitor);
		return evaluationVisitor;
	}

	protected @NonNull ExpressionInOCL getQuery(@NonNull MetamodelManager metamodelManager, @NonNull Constraint constraint) throws ParserException {
		LanguageExpression specification = constraint.getOwnedSpecification();
		assert specification != null;
		return metamodelManager.getQueryOrThrow(specification);
	}

	@Override
	public @Nullable Resource getSourceResource(@NonNull LeafConstrainingNode node) {
		Object constrainingObject = node.getConstrainingObject();
		if (!(constrainingObject instanceof EObject)) {
			return null;
		}
		return ((EObject)constrainingObject).eResource();
	}
}