/**
 * <copyright>
 *
 * Copyright (c) 2012 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.ocl.examples.build.utilities;

import org.apache.log4j.Logger;
import org.eclipse.xtext.XtextStandaloneSetup;

/**
 * Initializes Xtext support.
 */
public class XtextStandaloneSetupBean extends XtextStandaloneSetup
{
	private static final Logger logger = Logger.getLogger(XtextStandaloneSetupBean.class);
	
	public XtextStandaloneSetupBean() {
		logger.info("Configuring 'xtext'");
		doSetup();
	}
}