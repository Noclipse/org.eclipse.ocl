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
 * $Id: ImplicitCollectFilter.java,v 1.2 2011/04/25 19:39:51 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.essentialocl.attributes;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.domain.elements.DomainElement;
import org.eclipse.ocl.examples.pivot.CollectionType;
import org.eclipse.ocl.examples.pivot.Iteration;
import org.eclipse.ocl.examples.pivot.ParameterableElement;
import org.eclipse.ocl.examples.pivot.TemplateParameter;
import org.eclipse.ocl.examples.pivot.TemplateSignature;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.scoping.EnvironmentView;
import org.eclipse.ocl.examples.pivot.utilities.PivotUtil;

public class ImplicitCollectFilter extends AbstractOperationFilter
{
	protected final @NonNull Type iteratorType;

	/**
	 * Configure an OperationFilter for an implicit collect.
	 */
	public ImplicitCollectFilter(@NonNull MetaModelManager metaModelManager, @NonNull CollectionType sourceType, @NonNull Type iteratorType) {
		super(metaModelManager, sourceType);
		this.iteratorType = iteratorType;
	}

	public boolean matches(@NonNull EnvironmentView environmentView, @NonNull DomainElement eObject) {
		if (!(eObject instanceof Iteration)) {
			return false;
		}
		Iteration candidateIteration = (Iteration)eObject;
		int iteratorCount = candidateIteration.getOwnedIterator().size();
		if (iteratorCount != 1) {
			return false;
		}
		int accumulatorCount = candidateIteration.getOwnedAccumulator().size();
		if (accumulatorCount != 0) {
			return false;
		}
		TemplateSignature templateSignature = candidateIteration.getOwningType().getOwnedTemplateSignature();
		if (templateSignature == null) {
			return false;
		}
		List<TemplateParameter> templateParameters = templateSignature.getOwnedParameter();
		if (templateParameters.size() != 1) {
			return false;
		}
		Map<TemplateParameter, ParameterableElement> bindings = PivotUtil.getAllTemplateParameterSubstitutions(null, sourceType);
		TemplateParameter iteratorParameter = templateParameters.get(0);
		if (bindings != null) {
			bindings.put(iteratorParameter, iteratorType);
		}
		installBindings(environmentView, eObject, bindings);
		return true;
	}
}