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
 * $Id: AbstractOperationFilter.java,v 1.3 2011/04/25 19:39:51 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.essentialocl.attributes;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.pivot.CollectionType;
import org.eclipse.ocl.examples.pivot.Operation;
import org.eclipse.ocl.examples.pivot.ParameterableElement;
import org.eclipse.ocl.examples.pivot.TemplateParameter;
import org.eclipse.ocl.examples.pivot.TemplateSignature;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.scoping.EnvironmentView;
import org.eclipse.ocl.examples.pivot.scoping.ScopeFilter;
import org.eclipse.ocl.examples.pivot.utilities.PivotUtil;

public abstract class AbstractOperationFilter implements ScopeFilter
{
	protected final @NonNull MetaModelManager metaModelManager;
	protected final @NonNull Type sourceType;
	
	public AbstractOperationFilter(@NonNull MetaModelManager metaModelManager, @NonNull Type sourceType) {
		this.metaModelManager = metaModelManager;
		this.sourceType = PivotUtil.getBehavioralType(sourceType);
	}

	public int compareMatches(@NonNull EObject match1, @Nullable Map<TemplateParameter, ParameterableElement> bindings1,
			@NonNull EObject match2, @Nullable Map<TemplateParameter, ParameterableElement> bindings2) {
		int comparison = metaModelManager.compareOperationMatches((Operation)match1, bindings1,
			(Operation)match2, bindings2);
//		if (comparison == 0) {
//			metaModelManager.compareOperationMatches((Operation)match1, bindings1,
//				(Operation)match2, bindings2);	// FIXME Debugging
//		}
		return comparison;
	}

	protected Map<TemplateParameter, ParameterableElement> getOperationBindings(@NonNull Operation candidateOperation) {
		Type sourceType = this.sourceType;
		if (!(sourceType instanceof CollectionType) && (candidateOperation.getOwningType() instanceof CollectionType)) {
			sourceType = metaModelManager.getCollectionType("Set", sourceType);		// Implicit oclAsSet()
		}			
		Map<TemplateParameter, ParameterableElement> bindings = PivotUtil.getAllTemplateParameterSubstitutions(null, sourceType);
//			PivotUtil.getAllTemplateParameterSubstitutions(bindings, candidateOperation);
		TemplateSignature templateSignature = candidateOperation.getOwnedTemplateSignature();
		if (templateSignature != null) {
			for (TemplateParameter templateParameter : templateSignature.getOwnedParameter()) {
				if (bindings == null) {
					bindings = new HashMap<TemplateParameter, ParameterableElement>();
				}
				bindings.put(templateParameter, null);
			}
		}
		return bindings;
	}

	protected void installBindings(@NonNull EnvironmentView environmentView, @NonNull Type forType, @NonNull EObject eObject,
			@Nullable Map<TemplateParameter, ParameterableElement> bindings) {
		environmentView.setBindings(eObject, bindings);
	}
}