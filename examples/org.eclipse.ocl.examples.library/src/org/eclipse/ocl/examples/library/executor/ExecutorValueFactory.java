/**
 * <copyright>
 *
 * Copyright (c) 2011 E.D.Willink and others.
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
import org.eclipse.ocl.examples.domain.elements.DomainStandardLibrary;
import org.eclipse.ocl.examples.domain.values.impl.ValueFactoryImpl;

public class ExecutorValueFactory extends ValueFactoryImpl
{
	public ExecutorValueFactory(@NonNull DomainStandardLibrary standardLibrary) {
		super(standardLibrary);
	}

/*	@Override
	public Value valueOf(Object object) {
		Value value = super.valueOf(object);
		if (value != null) {
			return value;
		}
		if (object instanceof EObject) {
			return new EObjectValueImpl(this, (EObject) object);
		}
		return new ObjectValueImpl(this, getStandardLibrary().getAnyClassifierType(), object);	// WIP A better type
	} */
}