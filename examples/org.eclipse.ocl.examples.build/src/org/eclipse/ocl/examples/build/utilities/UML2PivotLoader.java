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
package org.eclipse.ocl.examples.build.utilities;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.WorkflowComponentWithModelSlot;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.ocl.examples.pivot.Root;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.model.OCLstdlib;
import org.eclipse.ocl.examples.pivot.uml.UML2Pivot;

/**
 * Converts a UML resource to its Pivot form.
 */
public class UML2PivotLoader extends WorkflowComponentWithModelSlot
{
	private Logger log = Logger.getLogger(getClass());

	public void invokeInternal(WorkflowContext ctx, ProgressMonitor arg1, Issues arg2) {
		OCLstdlib.install();
		Resource resource = (Resource) ctx.get(getUmlSlot());
		log.info("Pivoting '" + resource.getURI() + "'");
		MetaModelManager metaModelManager = MetaModelManager.getAdapter(resource.getResourceSet());
		UML2Pivot uml2pivot = UML2Pivot.getAdapter(resource, metaModelManager);
		Root root = uml2pivot.getPivotRoot();
		Resource resource2 = root.eResource();
		ctx.set(getModelSlot(), resource2);
	}

	private String umlSlot;

	/**
	 * Sets the name of the UML slot.
	 * 
	 * @param slot
	 *            name of slot
	 */
	public void setUmlSlot(final String slot) {
		umlSlot = slot;
	}

	protected String getUmlSlot() {
		return umlSlot;
	}
}
