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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.ocl.examples.domain.elements.DomainProperty;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.InvalidValueException;
import org.eclipse.ocl.examples.domain.library.AbstractProperty;
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.domain.values.ValueFactory;

/**
 * The static instance of ExplicitCompositionProperty supports evaluation of
 * a property call that navigates a relationship to a container.
 */
public class ExplicitCompositionProperty extends AbstractProperty
{
	public static final ExplicitCompositionProperty INSTANCE = new ExplicitCompositionProperty();

	public Value evaluate(DomainEvaluator evaluator, DomainType returnType, Value sourceValue, DomainProperty property) throws InvalidValueException {
		ValueFactory valueFactory = evaluator.getValueFactory();
		EObject eObject = sourceValue.asNavigableObject(); 
		Object eValue = eObject.eContainer();
		if (eValue == null) {
			return valueFactory.getNull();
		}
		else {
			EReference eContainmentFeature = eObject.eContainmentFeature();
			return valueFactory.valueOf(eValue, eContainmentFeature.getEContainingClass());
		}
		// ??? Type conformance check
	}
}