/**
 * <copyright>
 *
 * Copyright (c) 2010,2011 E.D.Willink and others.
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
 * $Id: StringCharactersOperation.java,v 1.3 2011/02/21 08:37:46 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.string;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.ids.CollectionTypeId;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractUnaryOperation;
import org.eclipse.ocl.examples.domain.values.SequenceValue;

/**
 * StringCharactersOperation realises the String::characters() library operation.
 */
public class StringCharactersOperation extends AbstractUnaryOperation
{
	public static final @NonNull StringCharactersOperation INSTANCE = new StringCharactersOperation();

	@Override
	public @NonNull SequenceValue evaluate(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId, @Nullable Object sourceVal) {
		String sourceString = asString(sourceVal);
		List<Object> results = new ArrayList<Object>(sourceString.length());
		for (int i = 0; i < sourceString.length(); i++) {
			@SuppressWarnings("null") @NonNull String s = sourceString.substring(i, i+1);
			results.add(s);
		}
		return createSequenceValue((CollectionTypeId)returnTypeId, results);
	}
}
