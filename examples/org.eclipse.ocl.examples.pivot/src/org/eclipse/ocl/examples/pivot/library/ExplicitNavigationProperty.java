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
 * $Id: ExplicitNavigationProperty.java,v 1.2 2011/05/07 16:41:20 ewillink Exp $
 */
package org.eclipse.ocl.examples.pivot.library;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.ids.PropertyId;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractProperty;
import org.eclipse.ocl.examples.pivot.TemplateableElement;
import org.eclipse.ocl.examples.pivot.Type;

/**
 * The static instance of ExplicitNavigationProperty supports evaluation of
 * a property call that navigates a relationship.
 */
public class ExplicitNavigationProperty extends AbstractProperty
{
	protected @NonNull PropertyId propertyId;
//	protected @NonNull DomainProperty property;
	private EStructuralFeature eFeature = null;
	
	public ExplicitNavigationProperty(@NonNull PropertyId propertyId) {
		this.propertyId = propertyId;
	}
	
//	public ExplicitNavigationProperty(@NonNull DomainProperty property) {
//		this.property = property;
//	}
	
	public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId, @Nullable Object sourceValue) {
		EObject eObject = asNavigableObject(sourceValue); 
		EStructuralFeature eFeature2 = eFeature;
		if (eFeature2 == null) {
			EClass eClass = eObject.eClass();
			eFeature = eFeature2 = eClass.getEStructuralFeature(propertyId.getName());
		}
		// A specialized property such as CollectionType.elementType is returned from the specialized type
		// An unspecialized property such as CollectionType.ownedOperation is returned from the unspecialized type
		if ((eObject instanceof Type) && !eObject.eIsSet(eFeature2)) {
			TemplateableElement rawType = ((Type)eObject).getUnspecializedElement();
			if (rawType != null) {
				eObject = rawType;
			}
		}
		if (eFeature2 != null) {
			Object eValue = eObject.eGet(eFeature2);
			if (eValue != null) {
				return valueOf(eValue, eFeature2, returnTypeId);
			}
			
		}
		return null;
	}
}