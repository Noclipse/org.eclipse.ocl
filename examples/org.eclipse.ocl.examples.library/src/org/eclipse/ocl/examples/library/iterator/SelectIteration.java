/*******************************************************************************
 * Copyright (c) 2009, 2013 E.D.Willink and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 *******************************************************************************/
package org.eclipse.ocl.examples.library.iterator;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.DomainIterationManager;
import org.eclipse.ocl.examples.domain.ids.CollectionTypeId;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractIteration;
import org.eclipse.ocl.examples.domain.messages.EvaluatorMessages;
import org.eclipse.ocl.examples.domain.values.CollectionValue;
import org.eclipse.ocl.examples.domain.values.CollectionValue.Accumulator;
import org.eclipse.ocl.examples.domain.values.impl.InvalidValueException;

/**
 * SelectIteration realizes the Collection::select() library iteration.
 */
public class SelectIteration extends AbstractIteration
{
	public static final @NonNull SelectIteration INSTANCE = new SelectIteration();

	public @NonNull CollectionValue.Accumulator createAccumulatorValue(@NonNull DomainEvaluator evaluator, @NonNull TypeId accumulatorTypeId, @NonNull TypeId bodyTypeId) {
		return createCollectionAccumulatorValue((CollectionTypeId)accumulatorTypeId);
	}
	
	@Override
    protected @Nullable Object updateAccumulator(@NonNull DomainIterationManager iterationManager) {
		Object bodyVal = iterationManager.evaluateBody();		
		assert !(bodyVal instanceof InvalidValueException);
		if (bodyVal == null) {
			throw new InvalidValueException(EvaluatorMessages.UndefinedBody, "select"); 	// Null body is invalid //$NON-NLS-1$
		}
		else if (bodyVal == TRUE_VALUE) {
			Object value = iterationManager.get();		
			Accumulator accumulatorValue = (CollectionValue.Accumulator)iterationManager.getAccumulatorValue();
			assert accumulatorValue != null;												// createAccumulatorValue is @NonNull
			accumulatorValue.add(value);
		}
		else if (bodyVal != Boolean.FALSE) {
			throw new InvalidValueException(EvaluatorMessages.NonBooleanBody, "select"); 	// Non boolean body is invalid //$NON-NLS-1$
		}
		return CARRY_ON;
	}
}
