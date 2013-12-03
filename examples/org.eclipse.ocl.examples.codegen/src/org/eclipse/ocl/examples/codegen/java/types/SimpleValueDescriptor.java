/**
 * <copyright>
 * 
 * Copyright (c) 2013 CEA LIST and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   E.D.Willink(CEA LIST) - Initial API and implementation
 * 
 * </copyright>
 */
package org.eclipse.ocl.examples.codegen.java.types;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.codegen.generator.TypeDescriptor;
import org.eclipse.ocl.examples.domain.ids.ElementId;

/**
 * A SimpleValueDescriptor describes a type whose boxed and unboxed representations are the same. It has a pivot ElementId and a Java class.
 * <p>
 * Thus a StringValue is a TypeId.STRING and a java.lang.String.
 */
public class SimpleValueDescriptor extends AbstractValueDescriptor implements SimpleDescriptor
{
	public SimpleValueDescriptor(@NonNull ElementId elementId, @NonNull Class<?> javaClass) {
		super(elementId, javaClass);
	}

	@Override
	public final boolean isAssignableFrom(@NonNull TypeDescriptor typeDescriptor) {
		if (!(typeDescriptor instanceof SimpleValueDescriptor)) {
			return false;
		}
		return javaClass.isAssignableFrom(typeDescriptor.getJavaClass());
	}
}