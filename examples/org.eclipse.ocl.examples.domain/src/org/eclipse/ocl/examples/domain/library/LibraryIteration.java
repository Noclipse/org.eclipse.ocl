/**
 * <copyright>
 *
 * Copyright (c) 2011 E.D.Willink and others.
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
 * $Id: LibraryIteration.java,v 1.3 2011/02/21 08:37:47 ewillink Exp $
 */
package org.eclipse.ocl.examples.domain.library;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.DomainIterationManager;
import org.eclipse.ocl.examples.domain.evaluation.InvalidValueException;
import org.eclipse.ocl.examples.domain.ids.TypeId;

/**
 */
public interface LibraryIteration extends LibraryFeature
{
	/**
	 * Create the value that will accumulate the iteration results.
	 * 
	 * @param evaluator that supervises evaluation
	 * @param type of the accumulator
	 * @return the accumulator
	 * @throws InvalidValueException 
	 */
	@NonNull Object createAccumulatorValue(@NonNull DomainEvaluator evaluator, @NonNull TypeId accumulatorTypeId, @NonNull TypeId bodyTypeId);
	
	/**
	 * Evaluate the iteration under the supervision of an iterationManager.
	 * 
	 * @param iterationManager the iteration space
	 * @return the result
	 * 
	 * @throws InvalidValueException if a body evaluates to invalid
	 */
	@Nullable Object evaluateIteration(@NonNull DomainIterationManager iterationManager);
}
