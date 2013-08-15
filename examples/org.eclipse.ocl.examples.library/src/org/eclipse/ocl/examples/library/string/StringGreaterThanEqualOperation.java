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
 * $Id: StringGreaterThanEqualOperation.java,v 1.3 2011/02/21 08:37:46 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.string;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.library.AbstractSimpleBinaryOperation;

/**
 * StringGreaterThanEqualOperation realises the String::>=() library operation.
 */
public class StringGreaterThanEqualOperation extends AbstractSimpleBinaryOperation
{
	public static final @NonNull StringGreaterThanEqualOperation INSTANCE = new StringGreaterThanEqualOperation();

	@Override
	public @NonNull Boolean evaluate(@Nullable Object left, @Nullable Object right) {
		String leftString = asString(left);
		String rightString = asString(right);
		return leftString.compareTo(rightString) >= 0;
	}
}
