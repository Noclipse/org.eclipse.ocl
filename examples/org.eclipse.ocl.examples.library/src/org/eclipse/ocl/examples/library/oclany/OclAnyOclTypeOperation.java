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
 * $Id: OclAnyOclTypeOperation.java,v 1.5 2011/04/25 09:48:57 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.oclany;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.library.AbstractUntypedUnaryOperation;

/**
 * OclAnyOclTypeOperation realises the OclAny::oclType() library operation.
 */
public class OclAnyOclTypeOperation extends AbstractUntypedUnaryOperation
{
	public static final @NonNull OclAnyOclTypeOperation INSTANCE = new OclAnyOclTypeOperation();

	@Override
	public @NonNull DomainType evaluate(@NonNull DomainEvaluator evaluator, @Nullable Object sourceVal) {
		return evaluator.getStaticTypeOf(sourceVal);
	}
}
