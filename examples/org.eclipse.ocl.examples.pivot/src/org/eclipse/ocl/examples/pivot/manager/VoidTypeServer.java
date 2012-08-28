/**
 * <copyright>
 *
 * Copyright (c) 2010 E.D.Willink and others.
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
 * $Id: Nameable.java,v 1.2 2011/01/24 20:49:36 ewillink Exp $
 */
package org.eclipse.ocl.examples.pivot.manager;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.domain.elements.DomainInheritance;
import org.eclipse.ocl.examples.pivot.VoidType;

class VoidTypeServer extends ExtensibleTypeServer
{
	public VoidTypeServer(@NonNull PackageServer packageServer, @NonNull VoidType type) {
		super(packageServer, type);
	}

	@Override
	public @NonNull DomainInheritance getCommonInheritance(@NonNull DomainInheritance thatInheritance) {
		return thatInheritance.isUndefined() ? this : thatInheritance;
	}

	@Override
	public boolean isUndefined() {
		return true;
	}
}
