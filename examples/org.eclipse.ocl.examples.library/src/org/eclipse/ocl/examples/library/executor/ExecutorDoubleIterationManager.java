/**
 * <copyright>
 *
 * Copyright (c) 2011, 2013 E.D.Willink and others.
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
 * $Id: IterationManager.java,v 1.5 2011/05/07 16:41:47 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.executor;

import java.util.Iterator;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractIterationManager;
import org.eclipse.ocl.examples.domain.library.LibraryTernaryOperation;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.domain.values.CollectionValue;
import org.eclipse.ocl.examples.domain.values.util.ValuesUtil;

public class ExecutorDoubleIterationManager extends AbstractIterationManager
{	
	protected final @NonNull TypeId returnTypeId;
	protected final @NonNull LibraryTernaryOperation body;
	private @Nullable Object accumulatorValue;
	protected final @NonNull CollectionValue collectionValue;
	private @NonNull Iterator<? extends Object> iteratorValue1;
	private final @NonNull Iterator<? extends Object> iteratorValue2;
	private Object currentValue1;
	private Object currentValue2;
	
	public ExecutorDoubleIterationManager(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId, @NonNull LibraryTernaryOperation body,
			@Nullable CollectionValue collectionValue, @Nullable Object accumulatorValue) {
		super(evaluator);
		this.returnTypeId = returnTypeId;
		this.body = body;
		this.accumulatorValue = accumulatorValue;
		this.collectionValue = ValuesUtil.asCollectionValue(collectionValue);
		this.iteratorValue1 = this.collectionValue.iterator();
		this.iteratorValue2 = this.collectionValue.iterator();
		currentValue1 = iteratorValue1.hasNext() ? iteratorValue1.next() : null;
		currentValue2 = iteratorValue2.hasNext() ? iteratorValue2.next() : null;
	}
	
	public boolean advanceIterators() {
		if (iteratorValue1.hasNext()) {
			currentValue1 = iteratorValue1.next();
		}
		else if (iteratorValue2.hasNext()) {
			currentValue2 = iteratorValue2.next();
			iteratorValue1 = collectionValue.iterator();
			currentValue1 = iteratorValue1.hasNext() ? iteratorValue1.next() : null;
		}
		else {
			currentValue1 = null;
			currentValue2 = null;
		}
		return currentValue1 != null;
	}

	public @Nullable Object evaluateBody() {
		return body.evaluate(evaluator, returnTypeId, accumulatorValue,
			DomainUtil.nonNullState(currentValue1), DomainUtil.nonNullState(currentValue2));
	}

	public @Nullable Object getAccumulatorValue() {
		return accumulatorValue;
	}

	public @NonNull CollectionValue getSourceCollection() {
		return collectionValue;
	}
	
	public boolean hasCurrent() {
		return currentValue1 != null;
	}

	public @Nullable Object updateAccumulator(Object newValue) {
		this.accumulatorValue = newValue;
		return null;					// carry on
	}
}