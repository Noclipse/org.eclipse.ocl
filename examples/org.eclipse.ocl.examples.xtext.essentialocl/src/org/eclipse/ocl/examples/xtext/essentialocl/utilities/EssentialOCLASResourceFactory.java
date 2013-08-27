/**
 * <copyright>
 *
 * Copyright (c) 2013 E.D.Willink and others.
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
package org.eclipse.ocl.examples.xtext.essentialocl.utilities;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.resource.ASResource;
import org.eclipse.ocl.examples.pivot.resource.AbstractASResourceFactory;

public class EssentialOCLASResourceFactory extends AbstractASResourceFactory
{
	public static final @NonNull EssentialOCLASResourceFactory INSTANCE = new EssentialOCLASResourceFactory();
	
	protected EssentialOCLASResourceFactory() {
		super(ASResource.ESSENTIALOCL_CONTENT_TYPE, null);
	}
}