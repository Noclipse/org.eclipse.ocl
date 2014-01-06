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
 * $Id: OrderedCollectionAppendOperation.java,v 1.3 2011/02/21 08:37:46 ewillink Exp $
 */
package org.eclipse.ocl.examples.library.collection;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.library.AbstractSimpleBinaryOperation;
import org.eclipse.ocl.examples.domain.values.OrderedCollectionValue;

/**
 * OrderedCollectionAppendOperation realises the OrderedCollection::append() library operation.
 */
public class OrderedCollectionAppendOperation extends AbstractSimpleBinaryOperation
{
	public static final @NonNull OrderedCollectionAppendOperation INSTANCE = new OrderedCollectionAppendOperation();

	@Override
	public @NonNull OrderedCollectionValue evaluate(@Nullable Object left, @Nullable Object right) {
		OrderedCollectionValue leftOrderedCollectionValue = asOrderedCollectionValue(left);
		return leftOrderedCollectionValue.append(right);
	}
}
