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
 * $Id: OclAnyOclTypeOperation.java,v 1.5 2011/04/25 09:48:57 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.oclany;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainCallExp;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.DomainLogger;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractPolyOperation;

/**
 * OclAnyOclLogOperation realises the OclAny::oclLog() library operation.
 */
public class OclAnyOclLogOperation extends AbstractPolyOperation
{
	public static final @NonNull OclAnyOclLogOperation INSTANCE = new OclAnyOclLogOperation();


	public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator,
			@NonNull TypeId returnTypeId, @Nullable Object sourceVal)
			throws Exception {
		try {
			DomainLogger log = evaluator.getLogger();
			if (log != null) {
				log.append(getSourceText(sourceVal));
			}
		} catch (Exception e) {}
		return sourceVal;
	}

	public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId,
			@Nullable Object sourceVal, @Nullable Object argVal) {
		try {
			String message = asString(argVal);
			DomainLogger log = evaluator.getLogger();
			if (log != null) {
				log.append(message + getSourceText(sourceVal));
			}
		} catch (Exception e) {}
		return sourceVal;
	}

	public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator,
			@NonNull TypeId returnTypeId, @Nullable Object sourceValue,
			@Nullable Object firstArgumentValue,
			@Nullable Object secondArgumentValue)
			throws Exception {
		throw new UnsupportedOperationException();
	}

	public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator,
			@NonNull DomainCallExp callExp, @Nullable Object sourceValue,
			Object... argumentValues)
			throws Exception {
		throw new UnsupportedOperationException();
	}

	protected @NonNull String getSourceText(@Nullable Object sourceVal) {
		if (sourceVal == null) {
			return "null"; //$NON-NLS-1$
		}
		String string = sourceVal.toString();
		if (string == null) {
			return "null"; //$NON-NLS-1$
		}
		return string;
	}
}
