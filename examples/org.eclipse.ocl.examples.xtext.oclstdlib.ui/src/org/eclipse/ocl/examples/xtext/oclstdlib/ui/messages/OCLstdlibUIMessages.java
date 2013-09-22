/**
 * <copyright>
 *
 * Copyright (c) 2012, 2013 E.D.Willink and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */

package org.eclipse.ocl.examples.xtext.oclstdlib.ui.messages;

import org.eclipse.osgi.util.NLS;

/**
 * An accessor class for externalized strings.
 */
public class OCLstdlibUIMessages
{	
	static {
		NLS.initializeMessages(OCLstdlibUIMessages.class.getName(), OCLstdlibUIMessages.class);
	}

	public static String NewWizardPage_defaultFileName;
	public static String NewWizardPage_fileNameLabel;
	public static String NewWizardPage_pageDescription;
	public static String NewWizardPage_pageSummary;
	public static String NewWizardPage_pageTitle;
}
