/**
 * <copyright>
 *
 * Copyright (c) 2013 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.ocl.examples.xtext.essentialocl.cs2as;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.xtext.base.basecs.ElementCS;

/**
 * An Iterator over the implicit source variables (most nested first).
 */
public class ImplicitSourceVariableIterator extends AbstractImplicitSourceNamedElementIterator<Variable>
{
	public ImplicitSourceVariableIterator(@NonNull ElementCS csElement) {
		super(csElement);
	}

	@Override
	protected void setNext(@NonNull Variable asVariable) {
		next = asVariable;
	}
}
