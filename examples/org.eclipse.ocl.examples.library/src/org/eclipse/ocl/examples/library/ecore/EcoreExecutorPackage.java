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
package org.eclipse.ocl.examples.library.ecore;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainPackage;
import org.eclipse.ocl.examples.domain.utilities.ArrayIterable;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.library.executor.ExecutorPackage;
import org.eclipse.ocl.examples.library.executor.ExecutorStandardLibrary;
import org.eclipse.ocl.examples.library.executor.ExecutorType;

public class EcoreExecutorPackage extends ExecutorPackage
{
	protected final EPackage ePackage;
	private ExecutorStandardLibrary standardLibrary = null;
	private ExecutorType[] types = null;
	private @Nullable List<EcoreExecutorPackage> packages = null;

	public EcoreExecutorPackage(/*@NonNull*/ EPackage ePackage) {
		super(DomainUtil.nonNullEMF(ePackage.getName()), ePackage.getNsPrefix(), ePackage.getNsURI());
		this.ePackage = ePackage;		
	}

	public final EPackage getEPackage() {
		return ePackage;
	}

	public Iterable<? extends DomainPackage> getNestedPackage() {
		List<EcoreExecutorPackage> packages2 = packages;
		if (packages2 == null) {
			packages2 = packages = new ArrayList<EcoreExecutorPackage>();
			for (EPackage eSubPackage : ePackage.getESubpackages()) {
				EcoreExecutorPackage subPackage = standardLibrary.getPackage(eSubPackage);
				if (subPackage != null) {
					packages2.add(subPackage);
				}
			}
		}
		return packages2;
	}

	public DomainPackage getNestingPackage() {
		EPackage eSuperPackage = ePackage.getESuperPackage();
		if (eSuperPackage == null) {
			return null;
		}
		return standardLibrary.getPackage(eSuperPackage);
	}

	public Iterable<ExecutorType> getOwnedType() {
		return new ArrayIterable<ExecutorType>(types);
	}

	public ExecutorType getType(String typeName) {
		for (ExecutorType type: getOwnedType()) {
			if (type.getName().equals(typeName)) {
				return type;
			}
		}
		return null;
	}
	
	public void init(ExecutorStandardLibrary standardLibrary, ExecutorType[] types) {
		assert this.standardLibrary == null;
		assert this.types == null;
		this.standardLibrary = standardLibrary;
		this.types = types;
		if (standardLibrary != null) {
			standardLibrary.addPackage(this);
		}
	}
	
	@Deprecated
	public void init(ExecutorType[] types) {
		init(null, types);
	}
	
//	public ExecutorType lookupType(int classIndex) {
//		return types[classIndex];
//	}
}