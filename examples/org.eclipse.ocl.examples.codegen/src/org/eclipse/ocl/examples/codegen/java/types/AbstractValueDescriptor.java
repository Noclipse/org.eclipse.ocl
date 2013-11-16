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
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.codegen.java.JavaStream;
import org.eclipse.ocl.examples.domain.ids.ElementId;

/**
 * An AbstractValueDescriptor extends an AbstractDescriptor to describe a type for which a Java Class as well as a pivot ElementId is available.
 */
public abstract class AbstractValueDescriptor extends AbstractDescriptor
{
	protected final @NonNull Class<?> javaClass;

	public AbstractValueDescriptor(@NonNull ElementId elementId, @NonNull Class<?> javaClass) {
		super(elementId);
		assert javaClass != Object.class;
		this.javaClass = javaClass;
	}

	public void append(@NonNull JavaStream js) {
		js.appendClassReference(javaClass);
	}

	@SuppressWarnings("null")
	public @NonNull String getClassName() {
		return javaClass.getName();
	}

	public @NonNull Class<?> getJavaClass() {
		return javaClass;
	}

	public @Nullable Class<?> hasJavaClass() {
		return javaClass;
	}

	@Override
	public boolean isAssignableTo(@NonNull Class<?> javaClass) {
		return javaClass.isAssignableFrom(this.javaClass);
	}

	@Override
	public @NonNull String toString() {
		return elementId + " => " + getClassName();
	}
}