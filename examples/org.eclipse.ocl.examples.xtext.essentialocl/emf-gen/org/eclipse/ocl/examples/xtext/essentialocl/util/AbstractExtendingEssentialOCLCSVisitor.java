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
 * This code is auto-generated
 * from: org.eclipse.ocl.examples.xtext.essentialocl/model/EssentialOCLCS.genmodel
 *
 * Do not edit it.
 */
package	org.eclipse.ocl.examples.xtext.essentialocl.util;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * An AbstractExtendingEssentialOCLCSVisitor provides a default implementation for each
 * visitXxx method that delegates to the visitYyy method of the first
 * super class, (or transitively its first super class' first super class
 * until a non-interface super-class is found). In the absence of any
 * suitable first super class, the method delegates to visiting().
 */
public abstract class AbstractExtendingEssentialOCLCSVisitor<R, C>
	extends org.eclipse.ocl.examples.xtext.base.util.AbstractExtendingBaseCSVisitor<R, C>
	implements EssentialOCLCSVisitor<R>
{
	/**
	 * Initializes me with an initial value for my result.
	 * 
	 * @param context my initial result value
	 */
	protected AbstractExtendingEssentialOCLCSVisitor(@NonNull C context) {
		super(context);
	}	

	public @Nullable R visitBinaryOperatorCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.BinaryOperatorCS object) {
		return visitOperatorCS(object);
	}

	public @Nullable R visitBooleanLiteralExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.BooleanLiteralExpCS object) {
		return visitPrimitiveLiteralExpCS(object);
	}

	public @Nullable R visitCollectionLiteralExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.CollectionLiteralExpCS object) {
		return visitLiteralExpCS(object);
	}

	public @Nullable R visitCollectionLiteralPartCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.CollectionLiteralPartCS object) {
		return visitModelElementCS(object);
	}

	public @Nullable R visitCollectionTypeCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.CollectionTypeCS object) {
		return visitTypedRefCS(object);
	}

	public @Nullable R visitConstructorExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.ConstructorExpCS object) {
		return visitNamedExpCS(object);
	}

	public @Nullable R visitConstructorPartCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.ConstructorPartCS object) {
		return visitModelElementCS(object);
	}

	public @Nullable R visitContextCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.ContextCS object) {
		return visitNamedElementCS(object);
	}

	public @Nullable R visitExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.ExpCS object) {
		return visitModelElementCS(object);
	}

	public @Nullable R visitExpSpecificationCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.ExpSpecificationCS object) {
		return visitSpecificationCS(object);
	}

	public @Nullable R visitIfExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.IfExpCS object) {
		return visitExpCS(object);
	}

	public @Nullable R visitIndexExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.IndexExpCS object) {
		return visitNamedExpCS(object);
	}

	public @Nullable R visitInfixExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.InfixExpCS object) {
		return visitExpCS(object);
	}

	public @Nullable R visitInvalidLiteralExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.InvalidLiteralExpCS object) {
		return visitPrimitiveLiteralExpCS(object);
	}

	public @Nullable R visitInvocationExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.InvocationExpCS object) {
		return visitNamedExpCS(object);
	}

	public @Nullable R visitLetExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.LetExpCS object) {
		return visitExpCS(object);
	}

	public @Nullable R visitLetVariableCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.LetVariableCS object) {
		return visitVariableCS(object);
	}

	public @Nullable R visitLiteralExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.LiteralExpCS object) {
		return visitExpCS(object);
	}

	public @Nullable R visitNameExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.NameExpCS object) {
		return visitExpCS(object);
	}

	public @Nullable R visitNamedExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.NamedExpCS object) {
		return visitExpCS(object);
	}

	public @Nullable R visitNavigatingArgCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.NavigatingArgCS object) {
		return visitModelElementCS(object);
	}

	public @Nullable R visitNavigationOperatorCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.NavigationOperatorCS object) {
		return visitBinaryOperatorCS(object);
	}

	public @Nullable R visitNestedExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.NestedExpCS object) {
		return visitExpCS(object);
	}

	public @Nullable R visitNullLiteralExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.NullLiteralExpCS object) {
		return visitPrimitiveLiteralExpCS(object);
	}

	public @Nullable R visitNumberLiteralExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.NumberLiteralExpCS object) {
		return visitPrimitiveLiteralExpCS(object);
	}

	public @Nullable R visitOperatorCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.OperatorCS object) {
		return visitNamedElementCS(object);
	}

	public @Nullable R visitPrefixExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.PrefixExpCS object) {
		return visitExpCS(object);
	}

	public @Nullable R visitPrimitiveLiteralExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.PrimitiveLiteralExpCS object) {
		return visitLiteralExpCS(object);
	}

	public @Nullable R visitSelfExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.SelfExpCS object) {
		return visitExpCS(object);
	}

	public @Nullable R visitStringLiteralExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.StringLiteralExpCS object) {
		return visitPrimitiveLiteralExpCS(object);
	}

	public @Nullable R visitTupleLiteralExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.TupleLiteralExpCS object) {
		return visitLiteralExpCS(object);
	}

	public @Nullable R visitTupleLiteralPartCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.TupleLiteralPartCS object) {
		return visitVariableCS(object);
	}

	public @Nullable R visitTypeLiteralExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.TypeLiteralExpCS object) {
		return visitLiteralExpCS(object);
	}

	public @Nullable R visitTypeNameExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.TypeNameExpCS object) {
		return visitTypedRefCS(object);
	}

	public @Nullable R visitUnaryOperatorCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.UnaryOperatorCS object) {
		return visitOperatorCS(object);
	}

	public @Nullable R visitUnlimitedNaturalLiteralExpCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.UnlimitedNaturalLiteralExpCS object) {
		return visitPrimitiveLiteralExpCS(object);
	}

	public @Nullable R visitVariableCS(@NonNull org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.VariableCS object) {
		return visitNamedElementCS(object);
	}
}
