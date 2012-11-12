/**
 * <copyright>
 * 
 * Copyright (c) 2002,2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   E.D.Willink - Bug 298634
 *
 * </copyright>
 *
 * $Id: AllXtextTests.java,v 1.10 2011/05/20 15:27:16 ewillink Exp $
 */

package org.eclipse.ocl.examples.test.xtext;

import org.eclipse.ocl.examples.pivot.tests.EvaluateBooleanOperationsTest4;
import org.eclipse.ocl.examples.pivot.tests.EvaluateClassifierOperationsTest;
import org.eclipse.ocl.examples.pivot.tests.EvaluateCollectionOperationsTest4;
import org.eclipse.ocl.examples.pivot.tests.EvaluateConstructsTest;
import org.eclipse.ocl.examples.pivot.tests.EvaluateModelOperationsTest;
import org.eclipse.ocl.examples.pivot.tests.EvaluateNameVisibilityTest;
import org.eclipse.ocl.examples.pivot.tests.EvaluateNumericOperationsTest4;
import org.eclipse.ocl.examples.pivot.tests.EvaluateOclAnyOperationsTest;
import org.eclipse.ocl.examples.pivot.tests.EvaluateStringOperationsTest4;
import org.eclipse.ocl.examples.pivot.tests.IteratorsTest4;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Tests for the Pivot Evaluation.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	EvaluateBooleanOperationsTest4.class,
	EvaluateClassifierOperationsTest.class,
	EvaluateCollectionOperationsTest4.class,
	EvaluateConstructsTest.class,
	EvaluateModelOperationsTest.class,
	EvaluateNameVisibilityTest.class,
	EvaluateNumericOperationsTest4.class,
	EvaluateOclAnyOperationsTest.class,
	EvaluateStringOperationsTest4.class,
	IteratorsTest4.class})

public class AllEvaluationTests {}
