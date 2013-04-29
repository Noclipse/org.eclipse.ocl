/**
 * <copyright>
 *
 * Copyright (c) 2012 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.ocl.examples.codegen.inliner;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.codegen.generator.CodeGenerator;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.ids.PropertyId;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.AbstractProperty;
import org.eclipse.ocl.examples.pivot.TemplateableElement;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.ecore.EObjectProperty;
import org.eclipse.ocl.examples.pivot.library.CompositionProperty;
import org.eclipse.ocl.examples.pivot.library.ConstrainedProperty;
import org.eclipse.ocl.examples.pivot.library.ExplicitNavigationProperty;
import org.eclipse.ocl.examples.pivot.library.TuplePartProperty;

public class PropertyInliners
{
	public PropertyInliners(@NonNull CodeGenerator codeGenerator) {
		codeGenerator.addInliner(CompositionProperty.class, new _CompositionProperty());
		codeGenerator.addInliner(ConstrainedProperty.class, new _ConstrainedProperty());
		codeGenerator.addInliner(EObjectProperty.class, new _EObjectProperty());
		codeGenerator.addInliner(ExplicitNavigationProperty.class, new _ExplicitUnboxedNavigationProperty());
		codeGenerator.addInliner(TuplePartProperty.class, new _TuplePartProperty());
	}

	public static abstract class AbstractJavaPropertyInliner extends AbstractInliner implements PropertyInliner
	{
	}
	
	public static class BoxedExplicitNavigationProperty extends AbstractProperty
	{
		protected @NonNull PropertyId propertyId;
//		protected @NonNull DomainProperty property;
		private EStructuralFeature eFeature = null;
		
		public BoxedExplicitNavigationProperty(@NonNull PropertyId propertyId) {
			this.propertyId = propertyId;
			// FIXME static attempt at eFeature
		}
		
		public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId, @Nullable Object sourceValue) {
			assert sourceValue != null;
			EObject eObject = (EObject)sourceValue; 
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
					return evaluator.getIdResolver().boxedValueOf(eValue, eFeature2, returnTypeId);
//					return evaluator.getIdResolver().valueOf(eValue)
				}
				return eValue;
			}
			return null;
		}
	}
	
	public static class UnboxedCompositionProperty extends AbstractProperty
	{
		protected @NonNull String containmentFeatureName;
		
		public UnboxedCompositionProperty(@NonNull String containmentFeatureName) {
			this.containmentFeatureName = containmentFeatureName;
		}
		
		public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId, @Nullable Object sourceValue) {
			assert sourceValue != null;
			EObject eObject = (EObject)sourceValue; 
			EObject eContainer = eObject.eContainer();
			if (eContainer == null) {
				return null;				// No container
			}
			EReference eContainmentFeature = eObject.eContainmentFeature();
			if (!containmentFeatureName.equals(eContainmentFeature.getName())) {
				return null;				// Contained but by some other property
			}
			return eContainer;
		}
	}
	
	public static class UnboxedExplicitNavigationProperty extends AbstractProperty
	{
		protected @NonNull PropertyId propertyId;
//		protected @NonNull DomainProperty property;
		private EStructuralFeature eFeature = null;
		
		public UnboxedExplicitNavigationProperty(@NonNull PropertyId propertyId) {
			this.propertyId = propertyId;
			// FIXME static attempt at eFeature
		}
		
		public @Nullable Object evaluate(@NonNull DomainEvaluator evaluator, @NonNull TypeId returnTypeId, @Nullable Object sourceValue) {
			assert sourceValue != null;
			EObject eObject = (EObject)sourceValue; 
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
//				if (eValue != null) {
//					return valueOf(eValue, eFeature2, returnTypeId);
//				}
				return eValue;
			}
			return null;
		}
	}

	public class _CompositionProperty extends AbstractJavaPropertyInliner
	{
	}

	public class _ConstrainedProperty extends AbstractJavaPropertyInliner
	{
		private final AbstractJavaPropertyInliner compositionInliner;
		private final AbstractJavaPropertyInliner navigationInliner;
		
		public _ConstrainedProperty() {
			compositionInliner = new _CompositionProperty();
			navigationInliner = new _ExplicitBoxedNavigationProperty();
		}	
	}

	public class _EObjectProperty extends AbstractJavaPropertyInliner
	{
		private final AbstractJavaPropertyInliner compositionInliner;
		private final AbstractJavaPropertyInliner navigationInliner;
		
		public _EObjectProperty() {
			compositionInliner = new _CompositionProperty();
			navigationInliner = new _ExplicitUnboxedNavigationProperty();
		}	
	}

	public class _ExplicitBoxedNavigationProperty extends AbstractJavaPropertyInliner
	{
	}

	public class _ExplicitUnboxedNavigationProperty extends AbstractJavaPropertyInliner
	{
	}

	public class _TuplePartProperty extends AbstractJavaPropertyInliner
	{
	}
	
}
