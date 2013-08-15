/**
 * <copyright>
 *
 * Copyright (c) 2012, 2013 E.D.Willink and others.
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
 * $Id: StringToRealOperation.java,v 1.3 2011/02/21 08:37:47 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.string;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.library.AbstractSimpleUnaryOperation;

/**
 * StringTrimOperation realizes the String::trim() library operation.
 */
public class StringTrimOperation extends AbstractSimpleUnaryOperation
{
	public static final @NonNull StringTrimOperation INSTANCE = new StringTrimOperation();

	@Override
	public @NonNull String evaluate(@Nullable Object sourceVal) {
		String sourceString = asString(sourceVal);
		@SuppressWarnings("null")@NonNull String result = sourceString.trim();
		return result;
	}
}
