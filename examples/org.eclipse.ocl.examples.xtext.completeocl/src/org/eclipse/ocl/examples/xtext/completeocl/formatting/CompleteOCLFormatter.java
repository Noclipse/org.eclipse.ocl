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
 * $Id: CompleteOCLFormatter.java,v 1.9 2011/05/21 14:55:55 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.completeocl.formatting;

import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.BodyCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.ClassifierContextDeclCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.CompleteOCLNavigationOperatorCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.DefCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.DefOperationCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.DefPropertyCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.DerCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.ImportCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.InitCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.InvCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.OperationContextDeclCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.PackageDeclarationCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.PostCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.PreCSElements;
import org.eclipse.ocl.examples.xtext.completeocl.services.CompleteOCLGrammarAccess.PropertyContextDeclCSElements;
import org.eclipse.ocl.examples.xtext.essentialocl.formatting.AbstractEssentialOCLFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;

/**
 * This class contains custom formatting description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting
 * on how and when to use it 
 * 
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an example
 */
public class CompleteOCLFormatter extends AbstractEssentialOCLFormatter
{
	@Override
	protected void configureFormatting(FormattingConfig c) {

	    c.setAutoLinewrap(120);

		CompleteOCLGrammarAccess f = (CompleteOCLGrammarAccess) getGrammarAccess();
	    
		configureCollectionLiteralExpCS(c, f.getCollectionLiteralExpCSAccess());
		configureCollectionTypeCS(c, f.getCollectionTypeCSAccess());
	    configureEssentialOCLNavigationOperatorCS(c, f.getEssentialOCLNavigationOperatorCSAccess());
		configureIfExpCS(c, f.getIfExpCSAccess());
		configureLetExpCS(c, f.getLetExpCSAccess());
		configureMultiplicityBoundsCS(c, f.getMultiplicityBoundsCSAccess());
		configureMultiplicityCS(c, f.getMultiplicityCSAccess());
		configureMultiplicityStringCS(c, f.getMultiplicityStringCSAccess());
	    configureNavigatingCommaArgCS(c, f.getNavigatingCommaArgCSAccess());
	    configureNavigatingSemiArgCS(c, f.getNavigatingSemiArgCSAccess());
	    configureNestedExpCS(c, f.getNestedExpCSAccess());
	    configurePathNameCS(c, f.getPathNameCSAccess());
	    configurePrimaryExpCS(c, f.getPrimaryExpCSAccess());
	    configureTupleLiteralExpCS(c, f.getTupleLiteralExpCSAccess());
	    configureTupleTypeCS(c, f.getTupleTypeCSAccess());

	    c.setLinewrap(2).before(f.getML_COMMENTRule());
	    c.setLinewrap(1).after(f.getML_COMMENTRule());

	    {
			BodyCSElements a = f.getBodyCSAccess();
//		    c.setLinewrap(2).before(a.getStereotypeBodyKeyword_0_0());
			setNoSpaceLineWrap(c, a.getColonKeyword_2());
		    c.setLinewrap(2).after(a.getSpecificationAssignment_3());
//		    c.setIndentation(a.getColonKeyword_2(), a.getWSTerminalRuleCall_4());
	    }
	    {
			ClassifierContextDeclCSElements a = f.getClassifierContextDeclCSAccess();
		    c.setLinewrap(2).before(a.getContextKeyword_0());
	    }
	    {
			CompleteOCLNavigationOperatorCSElements a = f.getCompleteOCLNavigationOperatorCSAccess();
			c.setNoSpace().before(a.getNameCircumflexAccentKeyword_0_0());
			c.setNoSpace().after(a.getNameCircumflexAccentKeyword_0_0());
			c.setLinewrap().before(a.getNameCircumflexAccentCircumflexAccentKeyword_0_1());
			c.setNoSpace().after(a.getNameCircumflexAccentCircumflexAccentKeyword_0_1());
		}
	    {
			DefCSElements a = f.getDefCSAccess();
		    c.setLinewrap(2).before(a.getStereotypeDefKeyword_1_0());
		    c.setNoSpace().before(a.getColonKeyword_3());
//		    c.setIndentation(a.getExpressionAssignment_9(), a.getExpressionAssignment_9());
		    c.setLinewrap(2).after(a.getSpecificationAssignment_6());
//		    c.setIndentation(a.getColonKeyword_3(), a.getWSTerminalRuleCall_7());
	    }
	    {
			DefOperationCSElements a = f.getDefOperationCSAccess();
		    c.setNoSpace().around(a.getLeftParenthesisKeyword_1());
		    c.setNoSpace().before(a.getCommaKeyword_2_1_0());
		    c.setNoSpace().before(a.getRightParenthesisKeyword_3());
			setNoSpaceLineWrap(c, a.getColonKeyword_4());
	    }
	    {
			DefPropertyCSElements a = f.getDefPropertyCSAccess();
			setNoSpaceLineWrap(c, a.getColonKeyword_1());
	    }
	    {
			DerCSElements a = f.getDerCSAccess();
//		    c.setLinewrap(2).before(a.getDeriveKeyword_0());
			setNoSpaceLineWrap(c, a.getColonKeyword_1());
//		    c.setIndentation(a.getExpressionAssignment_2(), a.getExpressionAssignment_2());
		    c.setLinewrap(2).after(a.getSpecificationAssignment_2());
//		    c.setIndentation(a.getColonKeyword_1(), a.getWSTerminalRuleCall_3());
	    }
	    {
			ImportCSElements a = f.getImportCSAccess();
		    c.setLinewrap().before(a.getImportKeyword_0());
			c.setNoSpace().around(a.getAllColonColonKeyword_3_0_0());	
			c.setNoSpace().around(a.getAsteriskKeyword_3_1());	
		    c.setLinewrap().after(a.getAllAssignment_3_0());
	    }
	    {
			InitCSElements a = f.getInitCSAccess();
//		    c.setLinewrap(2).before(a.getInitKeyword_0());
			setNoSpaceLineWrap(c, a.getColonKeyword_1());
//		    c.setIndentation(a.getExpressionAssignment_2(), a.getExpressionAssignment_2());
		    c.setLinewrap(2).after(a.getSpecificationAssignment_2());
//		    c.setIndentation(a.getColonKeyword_1(), a.getWSTerminalRuleCall_3());
	    }
	    {
			InvCSElements a = f.getInvCSAccess();
		    c.setLinewrap(2).before(a.getStereotypeInvKeyword_0_0());
			setNoSpaceLineWrap(c, a.getColonKeyword_2());
		    c.setLinewrap(2).after(a.getSpecificationAssignment_3());
//		    c.setIndentation(a.getColonKeyword_2(), a.getWSTerminalRuleCall_4());
	    }
	    {
			OperationContextDeclCSElements a = f.getOperationContextDeclCSAccess();
		    c.setLinewrap(2).before(a.getContextKeyword_0());
			c.setNoSpace().around(a.getLeftParenthesisKeyword_2());
			c.setNoSpace().before(a.getCommaKeyword_3_1_0());
			c.setNoSpace().before(a.getRightParenthesisKeyword_4());
		    c.setLinewrap(1).after(a.getOwnedTypeAssignment_6());
		    c.setIndentation(a.getLeftParenthesisKeyword_2(), a.getRightParenthesisKeyword_4());
	    }
	    {
	    	PackageDeclarationCSElements a = f.getPackageDeclarationCSAccess();
		    c.setLinewrap(2).before(a.getPackageKeyword_0());
		    c.setLinewrap(2).before(a.getEndpackageKeyword_3());
		    c.setLinewrap(2).after(a.getEndpackageKeyword_3());
			c.setIndentation(a.getPackageKeyword_0(), a.getEndpackageKeyword_3());
	    }
	    {
			PostCSElements a = f.getPostCSAccess();
//		    c.setLinewrap(2).before(a.getPostKeyword_0());
			setNoSpaceLineWrap(c, a.getColonKeyword_2());
//		    c.setIndentation(a.getColonKeyword_2(), a.getExpressionAssignment_3());
		    c.setLinewrap(2).after(a.getSpecificationAssignment_3());
//		    c.setIndentation(a.getColonKeyword_2(), a.getWSTerminalRuleCall_4());
	    }
	    {
			PreCSElements a = f.getPreCSAccess();
//		    c.setLinewrap(2).before(a.getPreKeyword_0());
			setNoSpaceLineWrap(c, a.getColonKeyword_2());
//		    c.setIndentation(a.getColonKeyword_2(), a.getExpressionAssignment_3());
		    c.setLinewrap(2).after(a.getSpecificationAssignment_3());
//		    c.setIndentation(a.getColonKeyword_2(), a.getWSTerminalRuleCall_4());
	    }
	    {
	    	PropertyContextDeclCSElements a = f.getPropertyContextDeclCSAccess();
		    c.setLinewrap(2).before(a.getContextKeyword_0());
		    c.setLinewrap(1).after(a.getOwnedTypeAssignment_3());
	    }
	    {	// comments
	    	c.setNoLinewrap().before(f.getSL_COMMENTRule());
	    }
	}
}
