/*******************************************************************************
 * Copyright (c) 2015, 2020 Willink Transformations and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *******************************************************************************/
/*
 * generated by Xtext
 */
package org.eclipse.ocl.xtext.base.formatting;

import org.eclipse.xtext.formatting.impl.FormattingConfig;

/**
 * This class contains custom formatting description.
 *
 * see : http://www.eclipse.org/Xtext/documentation.html#formatting
 * on how and when to use it
 */
public class BaseFormatter extends AbstractBaseFormatter
{
//	@Inject
//	private BaseGrammarAccess grammarAccess;

	@Override
	protected void configureFormatting(FormattingConfig c) {
// It's usually a good idea to activate the following three statements.
// They will add and preserve newlines around comments
//		c.setLinewrap(0, 1, 2).before(grammarAccess.getSL_COMMENTRule());
//		c.setLinewrap(0, 1, 2).before(grammarAccess.getML_COMMENTRule());
//		c.setLinewrap(0, 1, 1).after(grammarAccess.getML_COMMENTRule());
	}
}
