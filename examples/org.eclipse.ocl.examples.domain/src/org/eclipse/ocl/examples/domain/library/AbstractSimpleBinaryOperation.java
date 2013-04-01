/**
 * <copyright>
 *
 * Copyright (c) 2009,2011 E.D.Willink and others.
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
 * $Id: AbstractBinaryOperation.java,v 1.3 2011/02/21 08:37:47 ewillink Exp $
 */
package org.eclipse.ocl.examples.domain.library;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainCallExp;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.ids.TypeId;

/**
 * AbstractSimpleBinaryOperation defines the default implementation of a binary operation redirecting the
 * invocation to the argument-only form.
 */
public abstract class AbstractSimpleBinaryOperation extends AbstractUntypedBinaryOperation implements LibrarySimpleBinaryOperation
{
	@Override
	public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull DomainCallExp callExp, @Nullable Object sourceValue, Object... argumentValues) {
		return evaluate(sourceValue, argumentValues[0]);
	}

	@Override
	public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull DomainCallExp callExp, @Nullable Object sourceValue, @Nullable Object argumentValue) {
		return evaluate(sourceValue, argumentValue);
	}

	@Override
	public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId, @Nullable Object sourceValue, @Nullable Object argumentValue) {
		return evaluate(sourceValue, argumentValue);
	}

	@Override
	public @Nullable /*@Thrown*/ Object evaluate(@NonNull DomainEvaluator evaluator, @Nullable Object sourceValue, @Nullable Object argumentValue) {
		return evaluate(sourceValue, argumentValue);
	}

	// Redundant declaration avoids @Override dilemma for 1.5/1.6
	public abstract @Nullable /*@Thrown*/ Object evaluate(@Nullable Object sourceValue, @Nullable Object argumentValue);
}
