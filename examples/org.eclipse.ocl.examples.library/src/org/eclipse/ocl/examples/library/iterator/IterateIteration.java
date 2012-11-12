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
 * $Id: IterateIteration.java,v 1.5 2011/05/07 16:41:47 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.iterator;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.DomainIterationManager;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractIteration;

/**
 * IterateIteration realizes the Collection::iterate() library iteration.
 */
public class IterateIteration extends AbstractIteration
{
	public static final @NonNull IterateIteration INSTANCE = new IterateIteration();

	public @NonNull Object createAccumulatorValue(@NonNull DomainEvaluator evaluator, @NonNull TypeId accumulatorTypeId, @NonNull TypeId bodyTypeId) {
		throw new UnsupportedOperationException();		// Never used since values are assigned directly as the accumulator
	}

	@Override
	protected @Nullable Object updateAccumulator(@NonNull DomainIterationManager iterationManager) throws Exception {
		Object bodyValue = iterationManager.evaluateBody();
		return iterationManager.updateAccumulator(bodyValue);
	}
}
