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
 * $Id$
 */
package org.eclipse.ocl.examples.xtext.essentialocl.serializer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.examples.pivot.Comment;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.xtext.base.baseCST.ModelElementCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.RootCS;
import org.eclipse.ocl.examples.xtext.essentialocl.services.EssentialOCLGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.serializer.sequencer.HiddenTokenSequencer;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class EssentialOCLHiddenTokenSequencer extends HiddenTokenSequencer
{
	@Inject
 	private EssentialOCLGrammarAccess grammarAccess;

	protected void emitComments(EObject semanticChild) {
		if (semanticChild instanceof ModelElementCS) {
			Element pivot = ((ModelElementCS)semanticChild).getPivot();
			if (pivot != null) {
				List<Comment> ownedComment = pivot.getOwnedComment();
				if (ownedComment.size() > 0) {
					String commentIndent = getCommentIndent(semanticChild);
					TerminalRule ml_COMMENTRule = grammarAccess.getML_COMMENTRule();
					for (Comment comment : ownedComment) {
						String indentedBody = comment.getBody().replaceAll("\\n", "\n" + commentIndent + " * ");
						String body = "/**\n" + commentIndent + " * " + indentedBody + "\n" + commentIndent + " */";
						delegate.acceptComment(ml_COMMENTRule, body, null);
					}
				}
			}
		}
	}

	@Override
	public boolean enterAssignedAction(Action action, EObject semanticChild, ICompositeNode node) {
		emitComments(semanticChild);
		return super.enterAssignedAction(action, semanticChild, node);
	}

	@Override
	public boolean enterAssignedParserRuleCall(RuleCall rc, EObject semanticChild, ICompositeNode node) {
		if (!super.enterAssignedParserRuleCall(rc, semanticChild, node)) {
			return false;
		}
		emitComments(semanticChild);
		return true;
	}

	private static List<String> indents = new ArrayList<String>();
	
	protected String getCommentIndent(EObject semanticChild) {
		int i = 0;
		EObject eObject = semanticChild;
		while (eObject != null) {
			EObject eContainer = eObject.eContainer();
			if (eContainer instanceof RootCS) {
				break;
			}
			eObject = eContainer;
			i++;
		}
		if (i >= indents.size()) {
			for (int j = indents.size(); j <= i; j++) {
				if (j == 0) {
					indents.add("");
				}
				else {
					indents.add(indents.get(j-1) + "\t");
				}
			}
		}
		return indents.get(i);
	}
}
