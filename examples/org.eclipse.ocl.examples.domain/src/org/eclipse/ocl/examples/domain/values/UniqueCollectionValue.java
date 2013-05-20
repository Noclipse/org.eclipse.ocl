/**
 * <copyright>
 *
 * Copyright (c) 2010, 2012 E.D.Willink and others.
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
 * $Id: UniqueCollectionValue.java,v 1.3 2011/02/21 08:37:52 ewillink Exp $
 */
package org.eclipse.ocl.examples.domain.values;

import org.eclipse.jdt.annotation.NonNull;

public interface UniqueCollectionValue extends CollectionValue
{
	/**
	 * @generated NOT
	 */
	@NonNull UniqueCollectionValue minus(@NonNull UniqueCollectionValue set);

	/**
	 * @generated NOT
	 */
	@NonNull UniqueCollectionValue symmetricDifference(@NonNull UniqueCollectionValue set);       
}
