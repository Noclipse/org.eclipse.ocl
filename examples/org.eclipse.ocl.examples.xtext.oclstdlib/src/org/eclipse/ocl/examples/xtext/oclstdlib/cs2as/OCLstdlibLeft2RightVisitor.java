/**
 * <copyright>
 *
 * Copyright (c) 2010, 2013 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   E.D.Willink - initial API and implementation
 * 	 E.D.Willink (Obeo) - Bug 416287 - tuple-valued constraints
 *
 * </copyright>
 */
package org.eclipse.ocl.examples.xtext.oclstdlib.cs2as;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.xtext.base.cs2as.CS2PivotConversion;
import org.eclipse.ocl.examples.xtext.oclstdlib.oclstdlibcs.PrecedenceCS;
import org.eclipse.ocl.examples.xtext.oclstdlib.util.AbstractOCLstdlibCSLeft2RightVisitor;

public class OCLstdlibLeft2RightVisitor extends AbstractOCLstdlibCSLeft2RightVisitor
{
	public OCLstdlibLeft2RightVisitor(@NonNull CS2PivotConversion context) {
		super(context);
	}

	@Override
	public Element visitPrecedenceCS(@NonNull PrecedenceCS object) {
		return null;
	}
}