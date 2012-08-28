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
 * $Id: PivotConstants.java,v 1.11 2011/05/21 14:56:20 ewillink Exp $
 */
package org.eclipse.ocl.examples.library;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.annotation.NonNull;

public interface LibraryConstants
{
	@SuppressWarnings("null")
	static final @NonNull String PLUGIN_ID = LibraryConstants.class.getPackage().getName();
	@SuppressWarnings("null")
	static final @NonNull URI GEN_MODEL_URI = URI.createPlatformPluginURI("/" + PLUGIN_ID + "/model/oclstdlib.genmodel", true); //$NON-NLS-1$ //$NON-NLS-2$

	static final @NonNull String COMPARE_TO = "compareTo"; //$NON-NLS-1$
}
