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
 * $Id: Ecore2PivotReferenceSwitch.java,v 1.9 2011/05/12 06:07:29 ewillink Exp $
 */
package org.eclipse.ocl.examples.pivot.ecore;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.util.EcoreSwitch;
import org.eclipse.emf.ecore.xmi.impl.EMOFExtendedMetaData;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.library.LibraryConstants;
import org.eclipse.ocl.examples.pivot.Annotation;
import org.eclipse.ocl.examples.pivot.DataType;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.Operation;
import org.eclipse.ocl.examples.pivot.Parameter;
import org.eclipse.ocl.examples.pivot.PivotFactory;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.TypeTemplateParameter;
import org.eclipse.ocl.examples.pivot.TypedElement;
import org.eclipse.ocl.examples.pivot.library.JavaCompareToOperation;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.utilities.PivotUtil;

public class Ecore2PivotReferenceSwitch extends EcoreSwitch<Object>
{				
    /**
     * The key that identifies opposite role names in an annotation
     */
    public static final String PROPERTY_OPPOSITE_ROLE_NAME_KEY = "Property.oppositeRoleName"; //$NON-NLS-1$
    public static final Object PROPERTY_OPPOSITE_ROLE_UNIQUE_KEY = "Property.oppositeUnique"; //$NON-NLS-1$
    public static final Object PROPERTY_OPPOSITE_ROLE_ORDERED_KEY = "Property.oppositeOrdered"; //$NON-NLS-1$
    public static final Object PROPERTY_OPPOSITE_ROLE_LOWER_KEY = "Property.oppositeLower"; //$NON-NLS-1$
    public static final Object PROPERTY_OPPOSITE_ROLE_UPPER_KEY = "Property.oppositeUpper"; //$NON-NLS-1$

    protected final Ecore2Pivot converter;
	
	public Ecore2PivotReferenceSwitch(Ecore2Pivot converter) {
		this.converter = converter;
	}
	
	@Override
	public Object caseEAnnotation(EAnnotation eObject) {
		Annotation pivotElement = converter.getCreated(Annotation.class, DomainUtil.nonNullEMF(eObject));
		doSwitchAll(Element.class, pivotElement.getReference(), eObject.getReferences());
		return null;
	}

	@Override
	public Object caseEClass(EClass eObject) {
		org.eclipse.ocl.examples.pivot.Class pivotElement = converter.getCreated(org.eclipse.ocl.examples.pivot.Class.class, DomainUtil.nonNullEMF(eObject));
		doSwitchAll(Type.class, pivotElement.getSuperClass(), eObject.getEGenericSuperTypes());
		if (pivotElement.getSuperClass().isEmpty()) {
			org.eclipse.ocl.examples.pivot.Class oclElementType = converter.getMetaModelManager().getOclElementType();
			pivotElement.getSuperClass().add(oclElementType);
		}
		return null;
	}

	@Override
	public Object caseEDataType(EDataType eObject) {
		super.caseEDataType(eObject);
		Class<?> instanceClass = eObject.getInstanceClass();
		if (instanceClass != null) {
			DataType pivotElement = converter.getCreated(DataType.class, eObject);
			try {
				MetaModelManager metaModelManager = converter.getMetaModelManager();
				Method declaredMethod = instanceClass.getDeclaredMethod("compareTo", instanceClass);
				Operation operation = PivotFactory.eINSTANCE.createOperation();
				operation.setName(LibraryConstants.COMPARE_TO);
				operation.setImplementation(new JavaCompareToOperation(declaredMethod));
				Parameter parameter = PivotFactory.eINSTANCE.createParameter();
				parameter.setName("that");
				parameter.setType(metaModelManager.getOclSelfType());
				operation.getOwnedParameter().add(parameter);
				operation.setType(metaModelManager.getIntegerType());
				pivotElement.getOwnedOperation().add(operation);
				pivotElement.getSuperClass().add(metaModelManager.getOclComparableType());
			} catch (Exception e) {
			}
		}
		return null;
	}

	@Override
	public Object caseEOperation(EOperation eObject) {
		Operation pivotElement = converter.getCreated(Operation.class, DomainUtil.nonNullEMF(eObject));
		doSwitchAll(Type.class, pivotElement.getRaisedException(), eObject.getEGenericExceptions());
		return null;
	}

	@Override
	public Object caseEReference(EReference eObject) {
//		Property pivotElement = converter.getCreated(Property.class, eObject);		
		Property pivotElement = (Property) caseETypedElement(eObject);
		doSwitchAll(Property.class, pivotElement.getKeys(), eObject.getEKeys());
		Property oppositeProperty = null;
		EReference eOpposite = eObject.getEOpposite();
		if (eOpposite != null) {
			oppositeProperty = converter.getCreated(Property.class, eOpposite);
		}
		else {
			EAnnotation oppositeRole = eObject.getEAnnotation(EMOFExtendedMetaData.EMOF_PACKAGE_NS_URI_2_0);
			if (oppositeRole != null) {
				EMap<String, String> details = oppositeRole.getDetails();
				String oppositeName = details.get(PROPERTY_OPPOSITE_ROLE_NAME_KEY);
				if (oppositeName != null) {
					oppositeProperty = PivotFactory.eINSTANCE.createProperty();
					oppositeProperty.setName(oppositeName);
					oppositeProperty.setImplicit(true);
					Type remoteType = pivotElement.getType();
					Type localType = PivotUtil.getOwningType(pivotElement);
					oppositeProperty.setType(localType);
					String uniqueValue = details.get(PROPERTY_OPPOSITE_ROLE_UNIQUE_KEY);
					if (uniqueValue != null) {
						oppositeProperty.setIsUnique(Boolean.valueOf(uniqueValue));
					}
					String orderedValue = details.get(PROPERTY_OPPOSITE_ROLE_ORDERED_KEY);
					if (orderedValue != null) {
						oppositeProperty.setIsOrdered(Boolean.valueOf(orderedValue));
					}
					String lowerValue = details.get(PROPERTY_OPPOSITE_ROLE_LOWER_KEY);
					if (lowerValue != null) {
						oppositeProperty.setLower(new BigInteger(lowerValue));
					}
					String upperValue = details.get(PROPERTY_OPPOSITE_ROLE_UPPER_KEY);
					if (upperValue != null) {
						oppositeProperty.setUpper(new BigInteger(upperValue));
					}
					remoteType.getOwnedAttribute().add(oppositeProperty);
					oppositeProperty.setOpposite(pivotElement);
				}
			}
			else {
				oppositeRole = eObject.getEAnnotation(EMOFExtendedMetaData.EMOF_PROPERTY_OPPOSITE_ROLE_NAME_ANNOTATION_SOURCE);
				if (oppositeRole != null) {
					EMap<String, String> details = oppositeRole.getDetails();
					String oppositeName = details.get(EMOFExtendedMetaData.EMOF_COMMENT_BODY);
					if (oppositeName != null) {
						oppositeProperty = PivotFactory.eINSTANCE.createProperty();
						oppositeProperty.setName(oppositeName);
						oppositeProperty.setImplicit(true);
						Type remoteType = pivotElement.getType();
						Type localType = PivotUtil.getOwningType(pivotElement);
						oppositeProperty.setType(localType);
						remoteType.getOwnedAttribute().add(oppositeProperty);
						oppositeProperty.setOpposite(pivotElement);
					}
				}
			}
		}
		if (oppositeProperty != null) {
			pivotElement.setOpposite(oppositeProperty);
		}
//		else if (eObject.eContainer() instanceof EClass) {		// Skip annotation references
//			converter.getMetaModelManager().installPropertyDeclaration(pivotElement);
//		}
		return pivotElement;
	}

	@Override
	public TypedElement caseETypedElement(ETypedElement eObject) {
		TypedElement pivotElement = converter.getCreated(TypedElement.class, DomainUtil.nonNullEMF(eObject));
		EGenericType eType = eObject.getEGenericType();
		if (eType != null) {
			Type pivotType = converter.getPivotType(eType);
			pivotElement.setType(pivotType);
		}
		else {
			// FIXME Void ???
		}
/*		EClassifier eClassifier = eGenericType.getEClassifier();
			if (eClassifier != null) {
				allEClassifiers.add(eClassifier);
				ClassifierCS csClassifier = getCS(eClassifier, ClassifierCS.class);
				csTypeRef.setType(csClassifier);
			}
			else {
				ETypeParameter eTypeParameter = eGenericType.getETypeParameter();
				if (eTypeParameter != null) {
					TypeParameterCS csTypeParameter = (TypeParameterCS) createMap.get(eTypeParameter);
					csTypeRef.setType(csTypeParameter);
				}
//				else {
//					error("Unresolved " + eGenericType + " in pass2");
//				}
			} */
		return pivotElement;
	}

	@Override
	public Object caseETypeParameter(ETypeParameter eObject) {
		org.eclipse.ocl.examples.pivot.Class pivotElement = converter.getCreated(org.eclipse.ocl.examples.pivot.Class.class, DomainUtil.nonNullEMF(eObject));
		TypeTemplateParameter typeTemplateParameter = (TypeTemplateParameter) pivotElement.getTemplateParameter();
		doSwitchAll(Type.class, typeTemplateParameter.getConstrainingType(), eObject.getEBounds());
		return null;
	}

	public Object doInPackageSwitch(EObject eObject) {
		int classifierID = eObject.eClass().getClassifierID();
		return doSwitch(classifierID, eObject);
	}

	public <T extends Element> void doSwitchAll(Class<T> pivotClass, Collection<T> pivotElements, List<? extends EObject> eObjects) {
		Class<T> nonNullPivotClass = DomainUtil.nonNullEMF(pivotClass);
		for (EObject eObject : eObjects) {
			T pivotElement = converter.getPivotElement(nonNullPivotClass, DomainUtil.nonNullEntry(eObject));
			if (pivotElement != null) {
				pivotElements.add(pivotElement);
			}
		}
	}
}