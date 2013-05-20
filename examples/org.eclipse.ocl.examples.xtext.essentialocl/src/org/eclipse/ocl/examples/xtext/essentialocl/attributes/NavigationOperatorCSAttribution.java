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
 * $Id: NavigationOperatorCSAttribution.java,v 1.13 2011/05/05 17:52:54 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.essentialocl.attributes;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.PivotPackage;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.scoping.AbstractAttribution;
import org.eclipse.ocl.examples.pivot.scoping.EnvironmentView;
import org.eclipse.ocl.examples.pivot.scoping.ScopeView;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.InvocationExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.NavigationOperatorCS;

public class NavigationOperatorCSAttribution extends AbstractAttribution
{
	public static final @NonNull NavigationOperatorCSAttribution INSTANCE = new NavigationOperatorCSAttribution();

	@Override
	public ScopeView computeLookup(@NonNull EObject target, @NonNull EnvironmentView environmentView, @NonNull ScopeView scopeView) {
		if (environmentView.isQualifier()) {
			return scopeView.getParent();
		}
		assert scopeView.getContainmentFeature() != PivotPackage.Literals.OPERATION_CALL_EXP__ARGUMENT;		// Arguments must leapfrog to parent.
		NavigationOperatorCS targetElement = (NavigationOperatorCS)target;
		EObject child = scopeView.getChild();
//		assert !(child instanceof InvocationExpCS);			// FIXME invocations should leapfrog over me
		if (!(child instanceof InvocationExpCS)) {
			if (child == targetElement.getArgument()) {
				Type type = NavigationUtil.getNavigationSourceType(environmentView.getMetaModelManager(), targetElement);
				if (type != null) {
					environmentView.addElementsOfScope(type, scopeView);
					return null;											// Explicit navigation must be resolved in source
				}
			}
		}
		return scopeView.getParent();
	}
}
