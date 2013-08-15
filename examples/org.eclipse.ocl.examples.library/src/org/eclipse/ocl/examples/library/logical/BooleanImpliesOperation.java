/**
 * <copyright>
 *
 * Copyright (c) 2009, 2013 E.D.Willink and others.
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
 * $Id: BooleanImpliesOperation.java,v 1.3 2011/02/21 08:37:47 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.logical;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractSimpleBinaryOperation;
import org.eclipse.ocl.examples.domain.messages.EvaluatorMessages;
import org.eclipse.ocl.examples.domain.values.impl.InvalidValueException;

/**
 * ImpliesOperation realises the implies() library operation.
 */
public class BooleanImpliesOperation extends AbstractSimpleBinaryOperation
{
	public static final @NonNull BooleanImpliesOperation INSTANCE = new BooleanImpliesOperation();

	@Override
	public @Nullable Boolean evaluate(@Nullable Object left, @Nullable Object right) {
		if ((left == Boolean.FALSE) || (right == Boolean.TRUE)) {
			return TRUE_VALUE;
		}
		else if ((left == Boolean.TRUE) && (right == Boolean.FALSE)) {
			return FALSE_VALUE;
		}
		else if (left instanceof InvalidValueException) {
			throw (InvalidValueException)left;
		}
		else if (right instanceof InvalidValueException) {
			throw (InvalidValueException)right;
		}
		else {
			throw new InvalidValueException(EvaluatorMessages.TypedValueRequired, TypeId.BOOLEAN_NAME, getTypeName(left));
		}
	}
}
