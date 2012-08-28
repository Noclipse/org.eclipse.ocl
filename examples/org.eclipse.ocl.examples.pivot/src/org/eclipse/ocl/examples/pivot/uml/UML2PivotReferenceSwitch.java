/**
 * <copyright>
 *
 * Copyright (c) 2010 E.D.Willink and others.
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
 * $Id: UML2PivotReferenceSwitch.java,v 1.3 2011/01/27 07:02:06 ewillink Exp $
 */
package org.eclipse.ocl.examples.pivot.uml;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.examples.pivot.Constraint;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.Operation;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.TypedElement;
import org.eclipse.uml2.uml.util.UMLSwitch;

public class UML2PivotReferenceSwitch extends UMLSwitch<Object>
{				
	protected final UML2Pivot converter;
	
	public UML2PivotReferenceSwitch(UML2Pivot converter) {
		this.converter = converter;
	}
	
//	@Override
//	public Object caseEAnnotation(EAnnotation eObject) {
//		Annotation pivotElement = converter.getCreated(Annotation.class, eObject);
//		doSwitchAll(Element.class, pivotElement.getReference(), eObject.getReferences());
//		return null;
//	}
	
	@Override
	public Object caseAssociation(org.eclipse.uml2.uml.Association umlAssociation) {
		List<org.eclipse.uml2.uml.Property> umlMemberEnds = umlAssociation.getMemberEnds();
		for (org.eclipse.uml2.uml.Property umlProperty : umlAssociation.getOwnedEnds()) {
			Property pivotElement = converter.getCreated(Property.class, umlProperty);
			converter.copyProperty(pivotElement, umlProperty, null);
			pivotElement.setImplicit(true);
			org.eclipse.uml2.uml.Type umlReferredType = umlProperty.getType();
			if (umlReferredType != null) {
				Type pivotReferredType = converter.getPivotType(umlReferredType);
				pivotElement.setType(pivotReferredType);
			}
			org.eclipse.uml2.uml.Property umlOpposite = getOtherEnd(umlMemberEnds, umlProperty);
			if (umlOpposite != null) {
				Property pivotOpposite = converter.getCreated(Property.class, umlOpposite);
				org.eclipse.uml2.uml.Type umlContainerType = umlOpposite.getType();
				if (umlContainerType != null) {
					Type pivotContainerType = converter.getPivotType(umlContainerType);
					pivotContainerType.getOwnedAttribute().add(pivotElement);
				}
				pivotOpposite.setOpposite(pivotElement);
			}
		}
		return null;
	}

	@Override
	public org.eclipse.ocl.examples.pivot.Class caseClass(org.eclipse.uml2.uml.Class umlClass) {
		org.eclipse.ocl.examples.pivot.Class pivotElement = converter.getCreated(org.eclipse.ocl.examples.pivot.Class.class, umlClass);
		doSwitchAll(Type.class, pivotElement.getSuperClass(), umlClass.getSuperClasses());
		return null;
	}

	@Override
	public Constraint caseConstraint(org.eclipse.uml2.uml.Constraint umlConstraint) {
		Constraint pivotElement = converter.getCreated(Constraint.class, umlConstraint);
		doSwitchAll(Element.class, pivotElement.getConstrainedElement(), umlConstraint.getConstrainedElements());
		return null;
	}

	@Override
	public Operation caseOperation(org.eclipse.uml2.uml.Operation umlOperation) {
		Operation pivotElement = converter.getCreated(Operation.class, umlOperation);
		doSwitchAll(Type.class, pivotElement.getRaisedException(), umlOperation.getRaisedExceptions());
		return null;
	}

	@Override
	public org.eclipse.ocl.examples.pivot.Package casePackage(org.eclipse.uml2.uml.Package umlPackage) {
		org.eclipse.ocl.examples.pivot.Package pivotElement = converter.getCreated(org.eclipse.ocl.examples.pivot.Package.class, umlPackage);
		doSwitchAll(org.eclipse.ocl.examples.pivot.Package.class, pivotElement.getImportedPackage(), umlPackage.getImportedPackages());
		return null;
	}

	@Override
	public EObject caseTypedElement(org.eclipse.uml2.uml.TypedElement umlTypedElement) {
		TypedElement pivotElement = converter.getCreated(TypedElement.class, umlTypedElement);
		org.eclipse.uml2.uml.Type umlType = umlTypedElement.getType();
		if (umlType != null) {
			Type pivotType = converter.resolveType(umlType);
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
		return null;
	}

//	@Override
//	public Object caseETypeParameter(ETypeParameter eObject) {
//		org.eclipse.ocl.examples.pivot.Class pivotElement = converter.getCreated(org.eclipse.ocl.examples.pivot.Class.class, eObject);
//		TypeTemplateParameter typeTemplateParameter = (TypeTemplateParameter) pivotElement.getTemplateParameter();
//		doSwitchAll(Type.class, typeTemplateParameter.getConstrainingType(), eObject.getEBounds());
//		return null;
//	}

	public Object doInPackageSwitch(EObject eObject) {
		int classifierID = eObject.eClass().getClassifierID();
		return doSwitch(classifierID, eObject);
	}

	public <T extends Element> void doSwitchAll(Class<T> pivotClass, Collection<T> pivotElements, List<? extends EObject> eObjects) {
		for (EObject eObject : eObjects) {
			T pivotElement = converter.getCreated(pivotClass, eObject);
			pivotElements.add(pivotElement);
		}
	}

	public org.eclipse.uml2.uml.Property getOtherEnd(List<org.eclipse.uml2.uml.Property> umlMemberEnds, org.eclipse.uml2.uml.Property umlProperty) {
		for (org.eclipse.uml2.uml.Property umlMemberEnd : umlMemberEnds) {
			if (umlMemberEnd != umlProperty) {
				return umlMemberEnd;
			}
		}
		return null;
	}
}