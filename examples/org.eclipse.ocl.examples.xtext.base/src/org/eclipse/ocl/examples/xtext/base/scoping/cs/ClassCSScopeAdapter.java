/**
 * <copyright>
 *
 * Copyright (c) 2010,2011 E.D.Willink and others.
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
 * $Id: ClassCSScopeAdapter.java,v 1.7 2011/04/25 09:50:02 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.base.scoping.cs;

import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ocl.examples.pivot.ClassifierType;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.utilities.PivotUtil;
import org.eclipse.ocl.examples.xtext.base.baseCST.BaseCSTPackage;
import org.eclipse.ocl.examples.xtext.base.baseCST.ClassCS;
import org.eclipse.ocl.examples.xtext.base.scope.EnvironmentView;
import org.eclipse.ocl.examples.xtext.base.scope.ScopeView;

public class ClassCSScopeAdapter extends BaseCSScopeAdapter<ClassCS, org.eclipse.ocl.examples.pivot.Class>
{
	public ClassCSScopeAdapter(MetaModelManager metaModelManager, ClassCS csElement) {
		super(metaModelManager, csElement, org.eclipse.ocl.examples.pivot.Class.class);
	}

	public void addInheritedContents(EnvironmentView environmentView, org.eclipse.ocl.examples.pivot.Class target, ScopeView scopeView) {
		List<org.eclipse.ocl.examples.pivot.Class> superClasses = target.getSuperClasses();
		if (superClasses.size() > 0) {
			for (org.eclipse.ocl.examples.pivot.Class superClass : superClasses) {
					environmentView.addElementsOfScope(superClass, scopeView);
			}
		}
		else {
			Type libType = metaModelManager.getClassifierType();
			environmentView.addLibContents(libType, scopeView);
		}
	}

	@Override
	public ScopeView computeLookup(EnvironmentView environmentView, ScopeView scopeView) {
		EStructuralFeature containmentFeature = scopeView.getContainmentFeature();
//		TypeBindingsCS bindings = scopeView.getBindings();
		org.eclipse.ocl.examples.pivot.Class pivot = getPivot();
		if (pivot != null) {
			if (containmentFeature == BaseCSTPackage.Literals.CLASS_CS__OWNED_SUPER_TYPE) {
				environmentView.addElements(PivotUtil.getTypeTemplateParameterables(pivot));
			}
			else if (containmentFeature == BaseCSTPackage.Literals.CLASS_CS__OWNED_META_TYPE) {
//				environmentView.addNamedElement(metaModelManager.getBagTypeType());				
//				environmentView.addNamedElement(metaModelManager.getCollectionTypeType());
//				environmentView.addNamedElement(metaModelManager.getOrderedSetTypeType());
//				environmentView.addNamedElement(metaModelManager.getSequenceTypeType());
//				environmentView.addNamedElement(metaModelManager.getSetTypeType());
//				environmentView.addNamedElement(metaModelManager.getPrimitiveTypeType());
				//				environmentView.addElements(PivotPackage.Literals.TYPE, PivotUtil.getTypeTemplateParameterables(pivot));
			}
			else {
				if (pivot instanceof ClassifierType) {
					Type instanceType = ((ClassifierType)pivot).getInstanceType();
					environmentView.addNamedElements(metaModelManager.getLocalOperations(instanceType, true));
					environmentView.addNamedElements(metaModelManager.getLocalProperties(instanceType, true));
				}
				environmentView.addNamedElements(metaModelManager.getLocalOperations(pivot, false));
				environmentView.addNamedElements(metaModelManager.getLocalProperties(pivot, false));
				environmentView.addElements(PivotUtil.getTypeTemplateParameterables(pivot));
				if (!environmentView.hasFinalResult()) {
//					if (environmentView.getRequiredType() != BaseCSTPackage.Literals.TYPE_CS) { // Avoid creating bindings for nested type parameters
					addInheritedContents(environmentView, pivot, scopeView);
//				if ((environmentView.getSize() == 0) && (environmentView.getRequiredType() != BaseCSTPackage.Literals.TYPE_CS)) { // Avoid creating bindings for nested type parameters
//					for (org.eclipse.ocl.examples.pivot.Class superClass : pivot.getSuperClasses()) {
//						ScopeView nestedScopeView = scopeView;
//	//					if (csType instanceof BoundClassifierCS) {
//	//						nestedScopeView = new BaseScopeView(scopeView, ((BoundClassifierCS)csType).getBindings());
//	//					}
//						environmentView.addElementsOfScope(superClass, nestedScopeView);
//					}
				}
			}
		}
		return scopeView.getOuterScope();
	}
}
