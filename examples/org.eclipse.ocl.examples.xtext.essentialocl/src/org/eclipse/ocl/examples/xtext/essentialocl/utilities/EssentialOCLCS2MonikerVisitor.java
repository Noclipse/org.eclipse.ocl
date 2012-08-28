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
 * $Id: EssentialOCLCS2MonikerVisitor.java,v 1.9 2011/05/02 09:31:32 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.essentialocl.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.PivotConstants;
import org.eclipse.ocl.examples.pivot.PivotPackage;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.utilities.PivotUtil;
import org.eclipse.ocl.examples.xtext.base.baseCST.BaseCSTPackage;
import org.eclipse.ocl.examples.xtext.base.baseCST.ConstraintCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.ElementCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.ModelElementCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.PathElementCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.PathNameCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.TypeRefCS;
import org.eclipse.ocl.examples.xtext.base.util.BaseCSVisitor;
import org.eclipse.ocl.examples.xtext.base.utilities.BaseCS2MonikerVisitor;
import org.eclipse.ocl.examples.xtext.base.utilities.CS2Moniker;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.BooleanLiteralExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.CollectionLiteralExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.CollectionLiteralPartCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.CollectionTypeCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ConstructorExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ContextCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.EssentialOCLCSTPackage;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.IfExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.InfixExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.InvalidLiteralExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.InvocationExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.LetExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.LetVariableCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.NameExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.NavigatingArgCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.NavigationOperatorCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.NavigationRole;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.NestedExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.NullLiteralExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.NumberLiteralExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.OperatorCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.PrefixExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.SelfExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.StringLiteralExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.TupleLiteralExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.TupleLiteralPartCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.TypeLiteralExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.TypeNameExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.UnlimitedNaturalLiteralExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.util.AbstractExtendingDelegatingEssentialOCLCSVisitor;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

public class EssentialOCLCS2MonikerVisitor
	extends AbstractExtendingDelegatingEssentialOCLCSVisitor<Boolean, CS2Moniker, BaseCSVisitor<Boolean>>
	implements PivotConstants {

	private static final class Factory implements CS2Moniker.Factory
	{
		private Factory() {
			BaseCS2MonikerVisitor.FACTORY.getClass();
			CS2Moniker.addFactory(EssentialOCLCSTPackage.eINSTANCE, this);
			roleNames.put(EssentialOCLCSTPackage.Literals.COLLECTION_LITERAL_PART_CS__EXPRESSION_CS, "first");
			roleNames.put(EssentialOCLCSTPackage.Literals.COLLECTION_LITERAL_PART_CS__LAST_EXPRESSION_CS, "last");
			roleNames.put(EssentialOCLCSTPackage.Literals.CONTEXT_CS__OWNED_EXPRESSION, PivotPackage.Literals.EXPRESSION_IN_OCL__BODY_EXPRESSION.getName());
			roleNames.put(EssentialOCLCSTPackage.Literals.INFIX_EXP_CS__OWNED_EXPRESSION, "source");
//			roleNames.put(EssentialOCLCSTPackage.Literals.NAVIGATING_EXP_CS__ARGUMENT, "argument");
		}

		public @NonNull BaseCSVisitor<?> create(@NonNull CS2Moniker context) {
			return new EssentialOCLCS2MonikerVisitor(context);
		}
	}

	public static @NonNull CS2Moniker.Factory FACTORY = new Factory();

	@SuppressWarnings("unchecked")
	public EssentialOCLCS2MonikerVisitor(CS2Moniker context) {
		super((BaseCSVisitor<Boolean>) context.getVisitor(BaseCSTPackage.eINSTANCE), context);
	}

	protected void appendExpPrefix(ElementCS object) {
		if (context.toString().length() >= MONIKER_OVERFLOW_LIMIT) {
			context.append(OVERFLOW_MARKER);
			return;
		}
		ModelElementCS pivotedChild = EssentialOCLUtils.getPivotedCS(object);
		ElementCS pivotingChild = EssentialOCLUtils.getPivotingChildCS(pivotedChild);
		ModelElementCS pivotingParent = EssentialOCLUtils.getPivotingParentCS(pivotingChild);
		EReference pivotingFeature = EssentialOCLUtils.getPivotingFeature(pivotingChild, pivotingParent);
		if (pivotingFeature == null) {
			context.append(NULL_MARKER);
			return;
		}
		assert pivotingFeature.getEContainingClass().isInstance(pivotingParent);
		assert pivotingFeature.getEReferenceType().isInstance(pivotingChild);
		ModelElementCS pivotedCS = EssentialOCLUtils.getPivotedCS(pivotingParent);
		if (pivotedCS != null) {
			context.append(CS2Moniker.toString(pivotedCS));
		}
		context.append(MONIKER_SCOPE_SEPARATOR);
		if (pivotingFeature == EssentialOCLCSTPackage.Literals.LET_EXP_CS__IN) {
			int iMax = ((LetExpCS) pivotingParent).getVariable().size();
			for (int i = 1; i < iMax; i++) {
				context.append(EssentialOCLCSTPackage.Literals.LET_EXP_CS__IN.getName());
				context.append(MONIKER_OPERATOR_SEPARATOR);
				context.append(MONIKER_LET_EXP);
				context.append(MONIKER_SCOPE_SEPARATOR);
			}
		}
		else if (pivotingFeature == EssentialOCLCSTPackage.Literals.EXP_SPECIFICATION_CS__OWNED_EXPRESSION) {
			EObject pivotingParentParent = pivotingParent.eContainer();
			if (pivotingParentParent instanceof ConstraintCS) {
				ConstraintCS csConstraint = (ConstraintCS)pivotingParentParent;
				if (pivotingParent == csConstraint.getSpecification()) {
					pivotingFeature = PivotPackage.Literals.EXPRESSION_IN_OCL__BODY_EXPRESSION;
				}
				else if (pivotingParent == csConstraint.getMessageSpecification()) {
					pivotingFeature = PivotPackage.Literals.EXPRESSION_IN_OCL__MESSAGE_EXPRESSION;
				}
			}
		}
		int index = 0;
		if (pivotingFeature.isMany()) {
			index = ((List<?>)pivotingParent.eGet(pivotingFeature)).indexOf(pivotingChild);
			if (pivotingFeature == EssentialOCLCSTPackage.Literals.INVOCATION_EXP_CS__ARGUMENT) {
				InvocationExpCS csNavigatingExp = (InvocationExpCS)pivotingParent;
				NavigatingArgCS csNavigatingArg = csNavigatingExp.getArgument().get(index);
				switch (csNavigatingArg.getRole()) {
					case ITERATOR: pivotingFeature = PivotPackage.Literals.LOOP_EXP__ITERATOR; break;
					case ACCUMULATOR: pivotingFeature = PivotPackage.Literals.ITERATE_EXP__RESULT; break;
					case EXPRESSION: pivotingFeature = PivotPackage.Literals.LOOP_EXP__BODY; break;
				}
				int roleIndex = 0;
				for ( ; roleIndex < index; roleIndex++) {
					if (csNavigatingExp.getArgument().get(index - (roleIndex+1)).getRole() != csNavigatingArg.getRole()) {
						break;
					}
				}
				index = roleIndex;
			}
		}
		else if (pivotingFeature == EssentialOCLCSTPackage.Literals.COLLECTION_LITERAL_PART_CS__EXPRESSION_CS) {
			if (((CollectionLiteralPartCS)pivotingParent).getLastExpressionCS() != null) {
				pivotingFeature = PivotPackage.Literals.COLLECTION_RANGE__FIRST;
			}
			else {
				pivotingFeature = PivotPackage.Literals.COLLECTION_ITEM__ITEM;
			}
		}
		context.appendRoleCS(pivotingFeature);
		if (index != 0) {
			assert index > 0;
			context.append(index);
		}
		context.append(MONIKER_OPERATOR_SEPARATOR);
		if ((pivotingChild instanceof NavigatingArgCS)
		 && (((NavigatingArgCS)pivotingChild).getRole() == NavigationRole.ACCUMULATOR)) {
			if ((object != pivotingChild) && (object != ((NavigatingArgCS)pivotingChild).getName())) {
				NameExpCS csNameExp = (NameExpCS) ((NavigatingArgCS)pivotingChild).getName();
				appendPathNameCS(csNameExp.getPathName());
				context.append(MONIKER_SCOPE_SEPARATOR);
				context.append(PivotPackage.Literals.VARIABLE__INIT_EXPRESSION.getName());
				context.append(MONIKER_OPERATOR_SEPARATOR);
			}
		}
	}

	protected void appendPathNameCS(PathNameCS pathName) {
		Element element = null;
		if (pathName != null) {
			List<PathElementCS> path = pathName.getPath();
			if (path != null) {
				int iMax = path.size();
				if (iMax > 0) {
					boolean scopeIsResolved = true;
					for (int i = 0; i < iMax-1; i++) {
						Element scopeElement = path.get(i).basicGetElement();
						if ((scopeElement == null) || scopeElement.eIsProxy()) {
							scopeIsResolved = false;
							break;
						}
					}
					if (scopeIsResolved) {
						element = path.get(iMax-1).basicGetElement();
					}
				}
			}
		}
		if ((element != null) && !element.eIsProxy()) {
			context.appendName(element);
		}
		else {
			ICompositeNode node = NodeModelUtils.getNode(pathName);
			if (node != null) {
				String text = node.getText().trim();
				int length = text.length();
				if (text.startsWith("_'") && text.endsWith("'") && (length >= 3)) {
					text = text.substring(2, length-1);
				}
				context.append(text);
			}
		}
	}

	@Override
	public String toString() {
		return context.toString();
	}

	@Override
	public Boolean visitBooleanLiteralExpCS(@NonNull BooleanLiteralExpCS object) {
		appendExpPrefix(object);
		context.append(object.getName());
		return true;
	}

	@Override
	public Boolean visitCollectionLiteralExpCS(@NonNull CollectionLiteralExpCS object) {
		appendExpPrefix(object);
		context.appendNameCS(object.getOwnedType());
		return true;
	}

	@Override
	public Boolean visitCollectionLiteralPartCS(@NonNull CollectionLiteralPartCS object) {
		context.appendParentCS(object, MONIKER_PART_SEPARATOR);
		context.appendIndex(object);
		return true;
	}

	@Override
	public Boolean visitCollectionTypeCS(@NonNull CollectionTypeCS object) {
		Type specializedPivotType = PivotUtil.getPivot(Type.class, object);
		Type unspecializedPivotType = specializedPivotType != null ? PivotUtil.getUnspecializedTemplateableElement(specializedPivotType) : null;
		context.appendParent(unspecializedPivotType, MONIKER_SCOPE_SEPARATOR);
		context.appendNameCS(object);
		TypeRefCS type = object.getOwnedType();
		if (type != null) {
			context.append(TEMPLATE_BINDING_PREFIX);
			context.appendElementCS(type);
			context.append(TEMPLATE_BINDING_SUFFIX);
		}
		return true;
	}

	@Override
	public Boolean visitConstructorExpCS(@NonNull ConstructorExpCS object) {
		appendExpPrefix(object);
		appendPathNameCS(object.getPathName());
//		List<TupleLiteralPartCS> parts = new ArrayList<TupleLiteralPartCS>(object.getOwnedParts());
//		Collections.sort(parts, new Comparator<TupleLiteralPartCS>()
//		{
//			public int compare(TupleLiteralPartCS o1, TupleLiteralPartCS o2) {
//				return o1.getName().compareTo(o2.getName());
//			}
//		});
//		context.append(MONIKER_TUPLE_LITERAL_EXP);		
		return true;
	}

	@Override
	public Boolean visitContextCS(@NonNull ContextCS object) {
		context.append(MONIKER_ROOT_EXP);
		return true;
	}

	@Override
	public Boolean visitIfExpCS(@NonNull IfExpCS object) {
		appendExpPrefix(object);
		context.append(MONIKER_IF_EXP);
		return true;
	}

	@Override
	public Boolean visitInfixExpCS(@NonNull InfixExpCS object) {
		if (object.getOwnedOperator().get(0).getSource() == null) {
			context.append("%%tree-less%%");
			return true;
		}
		appendExpPrefix(object);
		context.append("infix");
		return true;
	}

	@Override
	public Boolean visitInvalidLiteralExpCS(@NonNull InvalidLiteralExpCS object) {
		appendExpPrefix(object);
		context.append(MONIKER_INVALID_LITERAL_EXP);
		return true;
	}

	@Override
	public Boolean visitInvocationExpCS(@NonNull InvocationExpCS object) {
		appendExpPrefix(object);
		context.append("navexp");
		return true;
	}

	@Override
	public Boolean visitLetExpCS(@NonNull LetExpCS object) {
		appendExpPrefix(object);
		context.append(MONIKER_LET_EXP);
		return true;
	}

	@Override
	public Boolean visitLetVariableCS(@NonNull LetVariableCS object) {
		context.appendParentCS(object, MONIKER_SCOPE_SEPARATOR);
		LetExpCS csLetExp = object.getLetExpression();
		for (LetVariableCS csVariable : csLetExp.getVariable()) {
			if (csVariable == object) {
				break;
			}
			context.append(EssentialOCLCSTPackage.Literals.LET_EXP_CS__IN.getName());
			context.append(MONIKER_OPERATOR_SEPARATOR);
			context.append(MONIKER_LET_EXP);
			context.append(MONIKER_SCOPE_SEPARATOR);
		}
//		context.append(MONIKER_LET_VARIABLE_PREFIX);
		context.append("variable");
		context.append(MONIKER_OPERATOR_SEPARATOR);
		context.appendNameCS(object);
		return true;
	}

	@Override
	public Boolean visitNameExpCS(@NonNull NameExpCS object) {
		appendExpPrefix(object);
		appendPathNameCS(object.getPathName());
		return true;
	}

	@Override
	public Boolean visitNavigatingArgCS(@NonNull NavigatingArgCS object) {
		appendExpPrefix(object);
		context.append("navarg");		
		return true;
	}

	@Override
	public Boolean visitNavigationOperatorCS(@NonNull NavigationOperatorCS object) {
		appendExpPrefix(object);
		context.append("navop");		
		return true;
	}

	@Override
	public Boolean visitNestedExpCS(@NonNull NestedExpCS object) {
		appendExpPrefix(object);
		context.append("nested");		
		return true;
	}

	@Override
	public Boolean visitNullLiteralExpCS(@NonNull NullLiteralExpCS object) {
		appendExpPrefix(object);
		context.append(MONIKER_NULL_LITERAL_EXP);
		return true;
	}

	@Override
	public Boolean visitNumberLiteralExpCS(@NonNull NumberLiteralExpCS object) {
		appendExpPrefix(object);
		context.append(object.getName().toString());
		return true;
	}

	@Override
	public Boolean visitOperatorCS(@NonNull OperatorCS object) {
		appendExpPrefix(object);
		context.appendNameCS(object);
		return true;
	}

	@Override
	public Boolean visitPrefixExpCS(@NonNull PrefixExpCS object) {
		appendExpPrefix(object);
		context.append("prefix");
		return true;
	}

	@Override
	public Boolean visitSelfExpCS(@NonNull SelfExpCS object) {
		appendExpPrefix(object);
		context.append(Environment.SELF_VARIABLE_NAME);
		return true;
	}

	@Override
	public Boolean visitStringLiteralExpCS(@NonNull StringLiteralExpCS object) {
		appendExpPrefix(object);
		context.append(MONIKER_STRING_LITERAL_EXP);
		return true;
	}

	@Override
	public Boolean visitTupleLiteralExpCS(@NonNull TupleLiteralExpCS object) {
		appendExpPrefix(object);
		List<TupleLiteralPartCS> parts = new ArrayList<TupleLiteralPartCS>(object.getOwnedParts());
		Collections.sort(parts, new Comparator<TupleLiteralPartCS>()
		{
			public int compare(TupleLiteralPartCS o1, TupleLiteralPartCS o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		context.append(MONIKER_TUPLE_LITERAL_EXP);		
		return true;
	}

	@Override
	public Boolean visitTupleLiteralPartCS(@NonNull TupleLiteralPartCS object) {
		context.appendParentCS(object, MONIKER_SCOPE_SEPARATOR);
		context.appendNameCS(object);
		return true;
	}

	@Override
	public Boolean visitTypeLiteralExpCS(@NonNull TypeLiteralExpCS object) {
		appendExpPrefix(object);
		context.append(((Type) object.getOwnedType().getPivot()).getName());
		return true;
	}

	@Override
	public Boolean visitTypeNameExpCS(@NonNull TypeNameExpCS object) {
		context.appendElement(object.getPivot());
		return true;
	}

	@Override
	public Boolean visitUnlimitedNaturalLiteralExpCS(@NonNull UnlimitedNaturalLiteralExpCS object) {
		appendExpPrefix(object);
		context.append(MONIKER_UNLIMITED_NATURAL_LITERAL_EXP);
		return true;
	}
}
