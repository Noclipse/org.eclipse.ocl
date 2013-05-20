/**
 * <copyright>
 *
 * Copyright (c) 2011, 2013 E.D.Willink and others.
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
 * $Id: ImplicitNonCompositionProperty.java,v 1.1 2011/04/27 06:19:59 ewillink Exp $
 */
package org.eclipse.ocl.examples.pivot.library;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainCollectionType;
import org.eclipse.ocl.examples.domain.elements.DomainProperty;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.domain.ids.CollectionTypeId;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractProperty;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;

/**
 * The static instance of ImplicitNonCompositionProperty supports evaluation of
 * implicit properties for opposites of non-composition relationships.
 */
public class ImplicitNonCompositionProperty extends AbstractProperty
{
	protected @NonNull DomainProperty property;
	
	public ImplicitNonCompositionProperty(@NonNull DomainProperty property) {
		this.property = property;
	}
	
	public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId, @Nullable Object sourceValue) {
		DomainModelManager modelManager = evaluator.getModelManager();
		DomainProperty thatProperty = property.getOpposite();
		DomainType thatType = DomainUtil.nonNullModel(property.getType());
		if (thatType instanceof DomainCollectionType) {
			thatType = ((DomainCollectionType)thatType).getElementType();
		}
		List<Object> results = new ArrayList<Object>();
		if (thatType != null) {
			for (EObject eObject : modelManager.get(thatType)) {	// FIXME Use a cache
				EClass eClass = eObject.eClass();
				EStructuralFeature eFeature = eClass.getEStructuralFeature(thatProperty.getName());
				Object eGet = eObject.eGet(eFeature);
				if (eGet == sourceValue) {
					results.add(eObject);
				}
			}
		}
		return evaluator.getIdResolver().createSetOfAll((CollectionTypeId)returnTypeId, results);
	}
}