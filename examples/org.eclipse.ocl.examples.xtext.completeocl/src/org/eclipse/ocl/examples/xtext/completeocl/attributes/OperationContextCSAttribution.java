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
 * $Id: OperationContextAttribution.java,v 1.9 2011/02/08 17:53:05 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.completeocl.attributes;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.scoping.AbstractAttribution;
import org.eclipse.ocl.examples.pivot.scoping.EnvironmentView;
import org.eclipse.ocl.examples.pivot.scoping.ScopeView;
import org.eclipse.ocl.examples.xtext.base.basecs.PathElementCS;
import org.eclipse.ocl.examples.xtext.base.basecs.PathNameCS;
import org.eclipse.ocl.examples.xtext.completeocl.completeoclcs.CompleteOCLCSPackage;
import org.eclipse.ocl.examples.xtext.completeocl.completeoclcs.OperationContextDeclCS;

public class OperationContextCSAttribution extends AbstractAttribution
{
	public static final @NonNull OperationContextCSAttribution INSTANCE = new OperationContextCSAttribution();

	@Override
	public ScopeView computeLookup(@NonNull EObject target, @NonNull EnvironmentView environmentView, @NonNull ScopeView scopeView) {
		OperationContextDeclCS targetElement = (OperationContextDeclCS)target;
		EStructuralFeature containmentFeature = scopeView.getContainmentFeature();
/*		if (containmentFeature == CompleteOCLCSPackage.Literals.OPERATION_CONTEXT_DECL_CS__OPERATION) {
			Filter filter = new OperationContextFilter(targetElement);
			try {
				environmentView.addFilter(filter);
				ScopeView namespaceScope = getNamespaceScope(environmentView, scopeView, targetElement.getNamespace());
				if ("conformsTo".equals(environmentView.getName())) {		// FIXME debugging
					environmentView.computeLookups(namespaceScope);
				}
				else {
					environmentView.computeLookups(namespaceScope);
				}
				return null;
			}
			finally {
				environmentView.removeFilter(filter);
			}
		}
		else if (containmentFeature == CompleteOCLCSPackage.Literals.CONTEXT_DECL_CS__NAMESPACE) {
			return getNextNamespaceScope(environmentView, scopeView, targetElement.getNamespace());
		}
		else*/ if ((containmentFeature == CompleteOCLCSPackage.Literals.OPERATION_CONTEXT_DECL_CS__POSTCONDITIONS)
				|| (containmentFeature == CompleteOCLCSPackage.Literals.OPERATION_CONTEXT_DECL_CS__PRECONDITIONS)
				|| (containmentFeature == CompleteOCLCSPackage.Literals.OPERATION_CONTEXT_DECL_CS__BODIES)) {
//			return getNextNamespaceScope(environmentView, scopeView, target.getNamespace());
//			Operation operation = targetElement.getOperation();
			PathNameCS pathName = targetElement.getPathName();
			if (pathName != null) {
				List<PathElementCS> path = pathName.getPath();
				if (path.size() > 1) {
					Element element = path.get(path.size()-2).getElement();
					if (element instanceof Type) {
						Type type = (Type) element;
//						MetaModelManager metaModelManager = environmentView.getMetaModelManager();
						environmentView.addAllOperations(type, false);
						environmentView.addAllProperties(type, false);
//						if (!environmentView.hasFinalResult()) {
//							Set<Type> alreadyVisitedTypes = new HashSet<Type>();
		//					org.eclipse.ocl.examples.pivot.Class unspecializedTarget = PivotUtil.getUnspecializedTemplateableElement(target);	// FIXME
//							for (Type superClass : metaModelManager.getSuperClasses(type)) {
//								environmentView.addAllContents(type, scopeView, superClass, Boolean.FALSE, alreadyVisitedTypes);
//							}
//						}
					}
				}
			}
		}
		return scopeView.getParent();

	}
}
