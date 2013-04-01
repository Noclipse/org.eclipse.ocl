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
 * $Id: LibraryTernaryOperation.java,v 1.3 2011/02/21 08:37:47 ewillink Exp $
 */
package org.eclipse.ocl.examples.domain.library;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainCallExp;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.ids.TypeId;

/**
 * LibraryTernaryOperation defines the invocation API of a ternary operation using
 * <br>
 * either an evaluator call expression, source and argument array
 * <br>
 * or an evaluator return type id and arguments.
 */
public interface LibraryTernaryOperation extends LibraryOperation {
	/**
	 * Return the result of evaluating the operation on source with arg1 and arg2.
	 * An invalid return may be indicated by throwing an exception returning Java null or OCL invalid.
	 *
	 * @param source source argument
	 * @param arg1 first argument
	 * @param arg2 second argument
	 * @return the evaluated value
	 * @throws Exception 
	 */
	@Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId, @Nullable Object sourceValue, @Nullable Object firstArgumentValue, @Nullable Object secondArgumentValue);
	@Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull DomainCallExp callExp, @Nullable Object sourceValue, @Nullable Object firstArgumentValue, @Nullable Object secondArgumentValue);
}
