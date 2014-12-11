/*******************************************************************************
 * Copyright (c) 2013 CEA LIST and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink (CEA LIST) - initial API and implementation
 *******************************************************************************/
package org.eclipse.ocl.pivot.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.domain.elements.DomainClass;
import org.eclipse.ocl.domain.elements.DomainCollectionType;
import org.eclipse.ocl.domain.elements.DomainEnvironment;
import org.eclipse.ocl.domain.elements.DomainTemplateParameter;
import org.eclipse.ocl.domain.elements.DomainType;
import org.eclipse.ocl.domain.utilities.DomainUtil;
import org.eclipse.ocl.pivot.LambdaType;
import org.eclipse.ocl.pivot.Property;
import org.eclipse.ocl.pivot.TemplateSignature;
import org.eclipse.ocl.pivot.TupleType;

/**
 * A TemplateSpecialisation supports resolution of template parameter within an element referenced from an OCL expression.
 * For instance a PropertyCallExp.referredProperty references the unspecialised Property and consequently the type and owningType
 * of the referredProperty may have unresolved template parameters. These may be resolved by exploiting the bindings of
 * the ProperyCallExp.source.
 * <p>
 * Invocation should first invoke needsSpecialisation() to discover whether the cost of constructing a TemplateSpecialisation
 * can be bypassed. If specialisation is needed a TemplateSpecialisation should be constructed for the prevailing OCL Standard
 * Library, and known type equivalences installed by invoking installEquivalence() for each. getSpecialisation may then be used
 * to resolve the type.
 */
public class TemplateSpecialisation
{
	/**
	 * Return true if a referencedType needs specialisation to resolve a template parameter.
	 */
	public static boolean needsSpecialisation(@Nullable DomainType referencedType)
	{
		if (referencedType == null) {
			return true;
		}
		DomainTemplateParameter templateParameter = referencedType.isTemplateParameter();
		if (templateParameter != null) {
			return true;
		}
		if (referencedType instanceof DomainCollectionType) {
			DomainType elementType = ((DomainCollectionType)referencedType).getElementType();
			return needsSpecialisation(elementType);
		}
		if (referencedType instanceof TupleType) {
			TupleType tupleType = (TupleType)referencedType;
			for (Property tuplePart : tupleType.getOwnedProperties()) {
				DomainType tuplePartType = tuplePart.getType();
				if (needsSpecialisation(tuplePartType)) {
					return true;
				}
			}
			return false;
		}
		if (referencedType instanceof LambdaType) {
			LambdaType lambdaType = (LambdaType)referencedType;
			DomainType contextType = lambdaType.getContextType();
			if (needsSpecialisation(contextType)) {
				return true;
			}
			DomainType resultType = lambdaType.getResultType();
			if (needsSpecialisation(resultType)) {
				return true;
			}
			for (DomainType parameterType : lambdaType.getParameterTypes()) {
				if (needsSpecialisation(parameterType)) {
					return true;
				}
			}
			return false;
		}
		if (referencedType instanceof org.eclipse.ocl.pivot.Class) {
			TemplateSignature templateSignature = ((org.eclipse.ocl.pivot.Class)referencedType).getOwnedTemplateSignature();
			if (templateSignature != null) {
				return true;
			}
		}
		return false;
	}

	protected final @NonNull DomainEnvironment environment;
//	protected final @NonNull DomainStandardLibrary standardLibrary;
	protected /*@LazyNonNull*/ Map<DomainTemplateParameter, DomainType> bindings = null;

	public TemplateSpecialisation(@NonNull DomainEnvironment environment) {
		this.environment = environment;
//		this.standardLibrary = environment.getStandardLibrary();
	}
	
	/**
	 * Return the specialisation of referencedType if distinct from referencedType.
	 * Returns null if specialisation not available or not distinct from referencedType.
	 */
	private @Nullable DomainType getResolution(@Nullable DomainType referencedType) {
		if (referencedType != null) {
			DomainTemplateParameter templateParameter = referencedType.isTemplateParameter();
			if (templateParameter != null) {
				return bindings != null ? bindings.get(templateParameter) : null;
			}
		}
		if (referencedType instanceof DomainCollectionType) {
			DomainCollectionType collectionType = (DomainCollectionType)referencedType;
			DomainType elementType = getResolution(collectionType.getElementType());
			if (elementType == null) {
				elementType = environment.getStandardLibrary().getOclAnyType();
			}
			DomainClass containerType = DomainUtil.nonNullState(collectionType.getContainerType());
			return environment.getCollectionType(containerType, elementType, collectionType.getLowerValue(), collectionType.getUpperValue());
		}
		if (referencedType instanceof TupleType) {
//			DomainTupleType tupleType = (DomainTupleType)referencedType;
			throw new UnsupportedOperationException();
		}
		if (referencedType instanceof LambdaType) {
//			DomainLambdaType lambdaType = (DomainLambdaType)referencedType;
			throw new UnsupportedOperationException();
		}
		return null;
	}

	public DomainClass getSpecialisation(@NonNull DomainType referredType) {
		DomainType specialisation = getResolution(referredType);
		return (DomainClass) (specialisation != null ? specialisation : referredType);	// FIXME cast
	}
	
	public void installEquivalence(@Nullable DomainType resolvedType, @Nullable DomainType referencedType) {
		if (resolvedType == null) {
			return;
		}
		if (referencedType == null) {
			return;
		}
		DomainTemplateParameter templateParameter = referencedType.isTemplateParameter();
		if (templateParameter != null) {
			if (bindings == null) {
				bindings = new HashMap<DomainTemplateParameter, DomainType>();
			}
			if (bindings.put(templateParameter, resolvedType) != null) {
				bindings.put(templateParameter, null);
			}
			return;
		}
		if (referencedType instanceof DomainCollectionType) {
			if (resolvedType instanceof DomainCollectionType) {
				DomainType resolvedElementType = ((DomainCollectionType)resolvedType).getElementType();
				DomainType referencedElementType = ((DomainCollectionType)referencedType).getElementType();
				installEquivalence(resolvedElementType, referencedElementType);
			}
			return;
		}
		if (referencedType instanceof TupleType) {
			if (resolvedType instanceof TupleType) {
				TupleType referencedTupleType = (TupleType)referencedType;
				TupleType resolvedTupleType = (TupleType)resolvedType;
				Iterable<? extends Property> referencedTupleParts = referencedTupleType.getOwnedProperties();
				for (Property resolvedTuplePart : resolvedTupleType.getOwnedProperties()) {
					Property referencedTuplePart = DomainUtil.getNamedElement(referencedTupleParts, resolvedTuplePart.getName());
					if (referencedTuplePart != null) {
						DomainType resolvedTuplePartType = resolvedTuplePart.getType();
						DomainType referencedTuplePartType = referencedTuplePart.getType();
						installEquivalence(resolvedTuplePartType, referencedTuplePartType);
					}
				}
			}
			return;
		}
		if (referencedType instanceof LambdaType) {
			if (resolvedType instanceof LambdaType) {
				LambdaType referencedLambdaType = (LambdaType)referencedType;
				LambdaType resolvedLambdaType = (LambdaType)resolvedType;
				installEquivalence(resolvedLambdaType.getContextType(), referencedLambdaType.getContextType());
				installEquivalence(resolvedLambdaType.getResultType(), referencedLambdaType.getResultType());
				List<? extends DomainType> resolvedParameterTypes = resolvedLambdaType.getParameterTypes();
				List<? extends DomainType> referencedParameterTypes = referencedLambdaType.getParameterTypes();
				for (int i = 0; i < Math.min(resolvedParameterTypes.size(), referencedParameterTypes.size()); i++) {
					DomainType resolvedParameterType = resolvedParameterTypes.get(i);
					DomainType referencedParameterType = referencedParameterTypes.get(i);
					installEquivalence(resolvedParameterType, referencedParameterType);
				}
			}
			return;
		}
		return;
	}
}