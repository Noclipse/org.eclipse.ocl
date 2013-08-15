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
 * $Id: OclInvalidAllInstancesOperation.java,v 1.2 2011/01/24 19:56:32 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.oclinvalid;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.library.AbstractSimpleUnaryOperation;
import org.eclipse.ocl.examples.domain.messages.EvaluatorMessages;
import org.eclipse.ocl.examples.domain.values.SetValue;
import org.eclipse.ocl.examples.domain.values.impl.InvalidValueException;

/**
 * OclInvalidAllInstancesOperation realises the OclInvalid::allInstances() library operation.
 */
public class OclInvalidAllInstancesOperation extends AbstractSimpleUnaryOperation
{
	public static final @NonNull OclInvalidAllInstancesOperation INSTANCE = new OclInvalidAllInstancesOperation();

	@Override
	public @NonNull SetValue evaluate(@Nullable Object sourceVal) {
		// OclInvalid has a single instance: invalid that cannot be returned in a collection
		throw new InvalidValueException(EvaluatorMessages.InvalidLiteral);
	}
}
