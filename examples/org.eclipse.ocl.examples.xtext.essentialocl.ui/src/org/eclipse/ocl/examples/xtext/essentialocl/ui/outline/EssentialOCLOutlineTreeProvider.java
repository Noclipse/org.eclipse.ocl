/*******************************************************************************
 * Copyright (c) 2011, 2013 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *******************************************************************************/
package org.eclipse.ocl.examples.xtext.essentialocl.ui.outline;

import org.eclipse.ocl.examples.xtext.base.basecs.ConstraintCS;
import org.eclipse.ocl.examples.xtext.base.basecs.ImportCS;
import org.eclipse.ocl.examples.xtext.base.basecs.TypeRefCS;
import org.eclipse.ocl.examples.xtext.base.ui.outline.BaseOutlineTreeProvider;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.InfixExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.NameExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.NestedExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.OperatorCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.PrefixExpCS;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;

/**
 * customization of the default outline structure
 * 
 */
public class EssentialOCLOutlineTreeProvider extends BaseOutlineTreeProvider
{
	protected void _createChildren(IOutlineNode parentNode, InfixExpCS csOperator) {
		createNode(parentNode, csOperator.getSource());
		createNode(parentNode, csOperator.getArgument());
	}

	protected void _createChildren(IOutlineNode parentNode, ConstraintCS constraint) {
		createChildren(parentNode, constraint.getOwnedSpecification());
	}

/*	protected void _createChildren(IOutlineNode parentNode, InvocationExpCS csExp) {
		// createNode(parentNode, csExp.getNameExp());
		for (NavigatingArgCS csArgument : csExp.getArgument()) {
			createNode(parentNode, csArgument);
		}
	} */

//	protected void _createChildren(IOutlineNode parentNode, NavigationOperatorCS csOperator) {
//		createNode(parentNode, csOperator.getSource());
//		createNode(parentNode, csOperator.getArgument());
//	}

	protected void _createChildren(IOutlineNode parentNode, NestedExpCS csExp) {
		createNode(parentNode, csExp.getSource());
	}

	protected void _createChildren(IOutlineNode parentNode, PrefixExpCS csPrefixExp) {
//		for (UnaryOperatorCS csOperator : csPrefixExp.getOwnedOperators()) {
			createNode(parentNode, csPrefixExp.getSource());
//		}
		createNode(parentNode, csPrefixExp.getOwnedExpression());
	}

//	protected void _createChildren(IOutlineNode parentNode, UnaryOperatorCS csOperator) {
//		createNode(parentNode, csOperator.getSource());
//	}

	/*
	 * protected void _createNode(IOutlineNode parentNode,
	 * CollectionLiteralPartCS collectionLiteralPart) { if
	 * (collectionLiteralPart.getLastExpressionCS() == null) {
	 * createNode(parentNode, collectionLiteralPart.getExpressionCS()); } else {
	 * createChildren(parentNode, collectionLiteralPart); } }
	 */
	
	protected void _createNode(IOutlineNode parentNode, InfixExpCS csInfixExp) {
		//
		// Find the root.
		//
		if (csInfixExp != null) {
			OperatorCS csRoot = csInfixExp;
			for (OperatorCS csParent = csRoot.getParent(); csParent != null; csParent = csParent.getParent()) {
				csRoot = csParent;
			}
			createNode(parentNode, csRoot);
		}
	}
	
/*	protected void _createNode(IOutlineNode parentNode, InfixExpCS csInfixExp) {
		List<BinaryOperatorCS> csOperators = csInfixExp.getOwnedOperator();
		if (csOperators.size() > 0) {
			ExpCS csExp = csOperators.get(0);
			for (; csExp.getParent() != null; csExp = csExp.getParent()) {
			}
			createNode(parentNode, csExp);
		} //
		createChildren(parentNode, templateSignature);
	} */

	// protected void _createNode(IOutlineNode parentNode, TemplateParameterCS
	// templateParameter) {
	// createNode(parentNode, templateParameter.getParameteredElement());
	// }

	// protected void _createNode(IOutlineNode parentNode, TemplateSignatureCS
	// templateSignature) {
	// createChildren(parentNode, templateSignature);
	// }

//	protected boolean _isLeaf(ConstructorExpCS csExp) {
//		return false;
//	}
	
	protected boolean _isLeaf(ImportCS csExp) {
		return true;
	}

//	protected boolean _isLeaf(IndexExpCS csExp) {
//		return false;
//	}

//	protected boolean _isLeaf(InvocationExpCS csExp) {
//		return false;
//	}

	protected boolean _isLeaf(NameExpCS csExp) {
		return true;
	}

	protected boolean _isLeaf(OperatorCS csExp) {
		return false;
	}

	protected boolean _isLeaf(TypeRefCS csExp) {
		return true;
	}
}
