/**
 * <copyright>
 *
 * Copyright (c) 2011,2012 E.D.Willink and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.ocl.common.preferences;

import org.eclipse.ocl.common.options.CommonOptions;

/**
 * Class used to initialize default preference values.
 */
public class CommonPreferenceInitializer extends AnnotatedPreferenceInitializer
{
	@Override
	public void initializeDefaultPreferences() {
		putPreference(CommonOptions.CODE_GENERATION_MODE);
		putPreference(CommonOptions.DEFAULT_DELEGATION_MODE);
	}
}
