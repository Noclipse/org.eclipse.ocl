/**
 * <copyright>
 *
 * Copyright (c) 2010, 2013 E.D.Willink and others.
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
 * $Id: ClassifierContextAttribution.java,v 1.8 2011/02/08 17:53:05 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.completeocl.attributes;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.scoping.AbstractAttribution;
import org.eclipse.ocl.examples.pivot.scoping.EnvironmentView;
import org.eclipse.ocl.examples.pivot.scoping.ScopeView;
import org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.ClassifierContextDeclCS;
import org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.CompleteOCLCSTPackage;

public class ClassifierContextCSAttribution extends AbstractAttribution
{
	public static final @NonNull ClassifierContextCSAttribution INSTANCE = new ClassifierContextCSAttribution();

	@Override
	public ScopeView computeLookup(@NonNull EObject target, @NonNull EnvironmentView environmentView, @NonNull ScopeView scopeView) {
		ClassifierContextDeclCS targetElement = (ClassifierContextDeclCS)target;
		EStructuralFeature containmentFeature = scopeView.getContainmentFeature();
		if (containmentFeature == CompleteOCLCSTPackage.Literals.CLASSIFIER_CONTEXT_DECL_CS__INVARIANTS) {
			Type type = targetElement.getClassifier();
			if (type != null) {
				environmentView.addAllOperations(type, false);
				environmentView.addAllProperties(type, false);
			}
		}
		return scopeView.getParent();
	}
}
