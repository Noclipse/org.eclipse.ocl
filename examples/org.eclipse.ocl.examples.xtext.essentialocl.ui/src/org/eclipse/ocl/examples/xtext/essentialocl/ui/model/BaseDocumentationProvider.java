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
package org.eclipse.ocl.examples.xtext.essentialocl.ui.model;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.examples.pivot.Comment;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.xtext.documentation.impl.MultiLineCommentDocumentationProvider;

public class BaseDocumentationProvider extends MultiLineCommentDocumentationProvider
{
	@Override
	protected String findComment(EObject o) {
		if (o instanceof Element) {
			List<Comment> comments = ((Element)o).getOwnedComment();
			if (comments.size() > 0) {
				StringBuilder s = new StringBuilder();
				for (Comment comment : comments) {
					if (s.length() > 0) {
						s.append("\n");
					}
					s.append(comment.getBody());
				}
				if (s.length() > 0) {
					return s.toString();
				}
			}
		}
		return super.findComment(o);
	}
}
