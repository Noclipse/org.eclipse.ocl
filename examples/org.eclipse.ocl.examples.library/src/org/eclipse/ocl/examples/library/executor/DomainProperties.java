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
 */
package org.eclipse.ocl.examples.library.executor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainFragment;
import org.eclipse.ocl.examples.domain.elements.DomainInheritance;
import org.eclipse.ocl.examples.domain.elements.DomainProperty;
import org.eclipse.ocl.examples.domain.types.AbstractInheritance;

import com.google.common.collect.Iterables;


public class DomainProperties
{
	protected final @NonNull DomainInheritance inheritance;
	protected final @NonNull Map<String, DomainProperty> name2property = new HashMap<String, DomainProperty>();

	public DomainProperties(@NonNull DomainInheritance inheritance) {
		this.inheritance = inheritance;
		init(inheritance.getAllSuperFragments());
	}

	protected void init(@NonNull Iterable<DomainFragment> allSuperFragments) {
		for (DomainFragment fragment : allSuperFragments) {
			for (DomainProperty property : fragment.getLocalProperties()) {
				name2property.put(property.getName(), property);
			}		
		}		
	}

	public @NonNull Iterable<? extends DomainProperty> getAllProperties(boolean selectStatic) {
		@SuppressWarnings("null")
		@NonNull Iterable<DomainProperty> subItOps = Iterables.filter(name2property.values(), selectStatic ? AbstractInheritance.SELECT_STATIC_PROPERTY : AbstractInheritance.REJECT_STATIC_PROPERTY);
		return subItOps;
	}

	public @Nullable DomainProperty getMemberProperty(@NonNull String name) {
		return name2property.get(name);
	}
}
