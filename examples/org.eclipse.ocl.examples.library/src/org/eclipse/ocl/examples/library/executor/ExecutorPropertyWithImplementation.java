/**
 * <copyright>
 *
 * Copyright (c) 2012 E.D.Willink and others.
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
 * $Id$
 */
package org.eclipse.ocl.examples.library.executor;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.domain.elements.DomainInheritance;
import org.eclipse.ocl.examples.domain.library.LibraryProperty;

public class ExecutorPropertyWithImplementation extends ExecutorProperty
{
	protected final @NonNull LibraryProperty implementation;
	
	public ExecutorPropertyWithImplementation(@NonNull  String name, @NonNull DomainInheritance executorType, int propertyIndex, @NonNull LibraryProperty implementation) {
		super(name, executorType, propertyIndex);
		this.implementation = implementation;
	}

	@Override
	public @NonNull LibraryProperty getImplementation() {
		return implementation;
	}
}