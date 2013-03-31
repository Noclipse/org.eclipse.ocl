/**
 * <copyright>
 *
 * Copyright (c) 2009,2010 E.D.Willink and others.
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
 * $Id: NumericGreaterThanEqualOperation.java,v 1.2 2011/01/24 19:56:31 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.oclany;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainInheritance;
import org.eclipse.ocl.examples.domain.elements.DomainOperation;
import org.eclipse.ocl.examples.domain.elements.DomainStandardLibrary;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractUntypedBinaryOperation;
import org.eclipse.ocl.examples.domain.library.LibraryBinaryOperation;
import org.eclipse.ocl.examples.domain.values.impl.InvalidValueException;
import org.eclipse.ocl.examples.domain.values.util.ValuesUtil;
import org.eclipse.ocl.examples.library.LibraryConstants;

/**
 * OclComparableComparisonOperation provides the abstract support for a comparison operation.
 */
public abstract class OclComparableComparisonOperation extends AbstractUntypedBinaryOperation
{
	@Override
	@Deprecated
	public @NonNull Boolean evaluate(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId, @Nullable Object left, @Nullable Object right) throws Exception {
		return evaluate(evaluator, left, right);
	}

	@Override
	public @NonNull Boolean evaluate(@NonNull DomainEvaluator evaluator, @Nullable Object left, @Nullable Object right) throws Exception {
		DomainStandardLibrary standardLibrary = evaluator.getStandardLibrary();
		DomainInheritance leftType = evaluator.getStaticTypeOf(left).getInheritance(standardLibrary);
		DomainInheritance rightType = evaluator.getStaticTypeOf(right).getInheritance(standardLibrary);
		DomainInheritance commonType = leftType.getCommonInheritance(rightType);
		DomainInheritance comparableType = standardLibrary.getOclComparableType().getInheritance(standardLibrary);
		DomainInheritance selfType = standardLibrary.getOclSelfType().getInheritance(standardLibrary);
		DomainOperation staticOperation = comparableType.lookupLocalOperation(standardLibrary, LibraryConstants.COMPARE_TO, selfType);
		int intComparison;
		LibraryBinaryOperation implementation = null;
		try {
			if (staticOperation != null) {
				implementation = (LibraryBinaryOperation) commonType.lookupImplementation(standardLibrary, staticOperation);
			}
		} catch (Exception e) {
			throw new InvalidValueException(e, "No 'compareTo' implementation"); //$NON-NLS-1$
		}
		if (implementation != null) {
			Object comparison = implementation.evaluate(evaluator, TypeId.INTEGER, left, right);
			intComparison = ValuesUtil.asInteger(comparison);
			return getResultValue(intComparison) != false;			// FIXME redundant test to suppress warning
		}
		else {
			throw new InvalidValueException("Unsupported compareTo for ''{0}''", left != null ? left.getClass().getName() : NULL_STRING); //$NON-NLS-1$
		}
	}

	protected abstract boolean getResultValue(Integer comparison);
}
