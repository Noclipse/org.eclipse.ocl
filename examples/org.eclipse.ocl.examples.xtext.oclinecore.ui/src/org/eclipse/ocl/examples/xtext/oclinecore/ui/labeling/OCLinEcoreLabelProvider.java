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
 * $Id: OCLinEcoreLabelProvider.java,v 1.8 2011/03/01 08:47:41 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.oclinecore.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.UMLReflection;
import org.eclipse.ocl.examples.xtext.base.baseCST.AnnotationCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.AttributeCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.ClassCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.DataTypeCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.DetailCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.EnumerationCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.EnumerationLiteralCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.OperationCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.PackageCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.ParameterCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.ReferenceCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.RootPackageCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.TypeParameterCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.TypeRefCS;
import org.eclipse.ocl.examples.xtext.essentialocl.ui.labeling.EssentialOCLLabelProvider;
import org.eclipse.ocl.examples.xtext.oclinecore.oclinEcoreCST.OCLinEcoreConstraintCS;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.inject.Inject;

/**
 * Provides labels for OCLinEcoreCST objects.
 * 
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class OCLinEcoreLabelProvider extends EssentialOCLLabelProvider {

	@Inject
	public OCLinEcoreLabelProvider(@NonNull AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

/*	public String text(AnnotationCS ele) {
		StringBuilder s = new StringBuilder();
//		String idName = ele.getIdSource();
//		if (idName != null) {
//			appendString(s, idName);			
//		}
//		else {
			s.append("\"");
			appendString(s, ele.getName());			
			s.append("\"");
//		}
		return s.toString();
	} */

	protected String image(AnnotationCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EAnnotation.gif";
	}

	protected String image(AttributeCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EAttribute.gif";
	}

	protected String image(ClassCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EClass.gif";
	}

	protected String image(OCLinEcoreConstraintCS ele) {
		String stereotype = ele.getStereotype();
		if (UMLReflection.BODY.equals(stereotype)) {
			return "/org.eclipse.ocl.examples.xtext.oclinecore.ui/icons/full/obj16/DefinitionConstraint.gif";
		}
		else if (UMLReflection.DERIVATION.equals(stereotype)) {
			return "/org.eclipse.ocl.examples.xtext.oclinecore.ui/icons/full/obj16/DerivationConstraint.gif";
		}
		else if (UMLReflection.INITIAL.equals(stereotype)) {
			return "/org.eclipse.ocl.examples.xtext.oclinecore.ui/icons/full/obj16/InitialConstraint.gif";
		}
		else if (UMLReflection.INVARIANT.equals(stereotype)) {
			return "/org.eclipse.ocl.examples.xtext.oclinecore.ui/icons/full/obj16/InvariantConstraint.gif";
		}
		else if (UMLReflection.POSTCONDITION.equals(stereotype)) {
			return "/org.eclipse.ocl.examples.xtext.oclinecore.ui/icons/full/obj16/PostconditionConstraint.gif";
		}
		else if (UMLReflection.PRECONDITION.equals(stereotype)) {
			return "/org.eclipse.ocl.examples.xtext.oclinecore.ui/icons/full/obj16/PreconditionConstraint.gif";
		}
		return "/org.eclipse.ocl.edit/icons/full/obj16/Constraint.gif";
	}

	public String text(OCLinEcoreConstraintCS ele) {
		StringBuilder s = new StringBuilder();
		s.append("<");
		appendString(s, ele.getStereotype());
		s.append("> ");
		appendOptionalName(s, ele);
		return s.toString();
	}

	protected String image(DataTypeCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EDataType.gif";
	}

	protected String image(DetailCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EStringToStringMapEntry.gif";
	}

//	public String text(DocumentationCS ele) {
//		return "documentation";
//	}

	protected String image(EnumerationCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EEnum.gif";
	}

	protected String image(EnumerationLiteralCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EEnumLiteral.gif";
	}

	protected String image(OperationCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EOperation.gif";
	}

	protected String image(PackageCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EPackage.gif";
	}

	protected String image(ParameterCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EParameter.gif";
	}

	protected String image(ReferenceCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EReference.gif";
	}

//	protected String image(ReferenceCSRef ele) {
//		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EReference.gif";
//	}

	public String text(RootPackageCS ele) {
		return "OCL in Ecore document";
	}

	protected String image(TypeParameterCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/ETypeParameter.gif";
	}

	protected String image(TypeRefCS ele) {
		return "/org.eclipse.emf.ecore.edit/icons/full/obj16/EGenericType.gif";
	}

	public String text(TypeRefCS ele) {
		ICompositeNode node = NodeModelUtils.getNode(ele);
		return NodeModelUtils.getTokenText(node);
	}
}
