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
 * $Id: OclAnyOclAsTypeOperation.java,v 1.7 2011/05/07 16:41:47 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.oclany;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainStandardLibrary;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractUntypedBinaryOperation;
import org.eclipse.ocl.examples.domain.messages.EvaluatorMessages;
import org.eclipse.ocl.examples.domain.values.impl.InvalidValueException;

/**
 * OclAnyOclAsTypeOperation realises the OclAny::oclAsType() library operation.
 */
public class OclAnyOclAsTypeOperation extends AbstractUntypedBinaryOperation
{
	public static final @NonNull OclAnyOclAsTypeOperation INSTANCE = new OclAnyOclAsTypeOperation();

	@Override
	@Deprecated
	public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId, @Nullable Object left, @Nullable Object right) {
		return evaluate(evaluator, left, right);
	}

	@Override
	public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @Nullable Object sourceVal, @Nullable Object argVal) {
		if (sourceVal instanceof InvalidValueException) {
			throw (InvalidValueException)sourceVal;
		}
		DomainType argType = asType(argVal);
		DomainType sourceType = evaluator.getIdResolver().getDynamicTypeOf(sourceVal);
		DomainStandardLibrary standardLibrary = evaluator.getStandardLibrary();
		if (sourceType.conformsTo(standardLibrary, argType)) {
			return sourceVal;
		}
		else {
			throw new InvalidValueException(EvaluatorMessages.IncompatibleArgumentType, argType);
		}
	}
}
