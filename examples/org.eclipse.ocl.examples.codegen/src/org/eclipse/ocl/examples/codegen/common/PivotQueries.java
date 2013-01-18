/**
 * <copyright>
 *
 * Copyright (c) 2011 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.ocl.examples.codegen.common;

import java.util.LinkedHashSet;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.pivot.Operation;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.utilities.PivotUtil;

public class PivotQueries
{
	public static @NonNull LinkedHashSet<Operation> getOperations(@NonNull Type type) {
		MetaModelManager metaModelManager = DomainUtil.nonNullState(PivotUtil.findMetaModelManager(type));
		LinkedHashSet<Operation> operations = new LinkedHashSet<Operation>();
		for (Operation operation : metaModelManager.getMemberOperations(type, false)) {
			operations.add(operation);
		}
		for (Operation operation : metaModelManager.getMemberOperations(type, true)) {
			operations.add(operation);
		}
//		if (type.getName().equals("MultiplicityElement")) {
//			System.out.println("here " + operations.size());
//		}
		return operations;
	}
	
	public static @NonNull LinkedHashSet<Property> getProperties(@NonNull Type type) {
		MetaModelManager metaModelManager = DomainUtil.nonNullState(PivotUtil.findMetaModelManager(type));
		LinkedHashSet<Property> properties = new LinkedHashSet<Property>();
		for (Property property : metaModelManager.getMemberProperties(type, false)) {
			properties.add(property);
		}
		for (Property property : metaModelManager.getMemberProperties(type, true)) {
			properties.add(property);
		}
		return properties;
	}
}
