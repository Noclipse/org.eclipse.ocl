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
 * $Id: IsUniqueIteration.java,v 1.3 2011/02/21 08:37:47 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.iterator;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.DomainIterationManager;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractIteration;
import org.eclipse.ocl.examples.domain.values.CollectionValue;
import org.eclipse.ocl.examples.domain.values.impl.InvalidValueException;
import org.eclipse.ocl.examples.domain.values.impl.SetValueImpl;
import org.eclipse.ocl.examples.domain.values.util.ValuesUtil;

/**
 * IsUniqueIteration realizes the Collection::isUnique() library iteration.
 */
public class IsUniqueIteration extends AbstractIteration
{
	public static final @NonNull IsUniqueIteration INSTANCE = new IsUniqueIteration();

	public @NonNull SetValueImpl.Accumulator createAccumulatorValue(@NonNull DomainEvaluator evaluator, @NonNull TypeId accumulatorTypeId, @NonNull TypeId bodyTypeId) {
		return new SetValueImpl.Accumulator(TypeId.SET.getSpecializedId(accumulatorTypeId));
	}
	
	@Override
	protected @NonNull Object resolveTerminalValue(@NonNull DomainIterationManager iterationManager) {
		return true;
	}
	
	@Override
    protected @Nullable Object updateAccumulator(@NonNull DomainIterationManager iterationManager) throws Exception {
		CollectionValue.Accumulator accumulatorValue = (CollectionValue.Accumulator)iterationManager.getAccumulatorValue();
		assert accumulatorValue != null;
		Object bodyVal = iterationManager.evaluateBody();		
		assert !(bodyVal instanceof InvalidValueException);
		if (accumulatorValue.includes(bodyVal) == TRUE_VALUE) {
			return false;						// Abort after second find
		}
		else {
			accumulatorValue.add(bodyVal);
			return null;						// Carry on after first find
		}
	}
}
