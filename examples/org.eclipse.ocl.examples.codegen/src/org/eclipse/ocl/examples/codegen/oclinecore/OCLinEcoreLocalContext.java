/**
 * <copyright>
 * 
 * Copyright (c) 2013 CEA LIST and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   E.D.Willink(CEA LIST) - Initial API and implementation
 * 
 * </copyright>
 */
package org.eclipse.ocl.examples.codegen.oclinecore;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.codegen.cgmodel.CGConstraint;
import org.eclipse.ocl.examples.codegen.cgmodel.CGElement;
import org.eclipse.ocl.examples.codegen.cgmodel.CGModelFactory;
import org.eclipse.ocl.examples.codegen.cgmodel.CGParameter;
import org.eclipse.ocl.examples.codegen.cgmodel.CGText;
import org.eclipse.ocl.examples.codegen.cgmodel.CGValuedElement;
import org.eclipse.ocl.examples.codegen.java.ImportUtils;
import org.eclipse.ocl.examples.codegen.java.JavaConstants;
import org.eclipse.ocl.examples.codegen.java.JavaGlobalContext;
import org.eclipse.ocl.examples.codegen.java.JavaLocalContext;
import org.eclipse.ocl.examples.library.ecore.EcoreExecutorManager;

/**
 * A JavaLocalContext maintains the Java-specific context for generation of coide from a CGOperation.
 */
public class OCLinEcoreLocalContext extends JavaLocalContext
{
	protected final @Nullable String contextName;
	protected final @Nullable String diagnosticsName;
	protected final @Nullable String messageName;
	protected final @Nullable String severityName;

	public OCLinEcoreLocalContext(@NonNull JavaGlobalContext globalContext, @NonNull CGElement cgScope) {
		super(globalContext, cgScope);
		if (cgScope instanceof CGConstraint) {
			this.contextName = nameManagerContext.getSymbolName(null, "context");
			this.diagnosticsName = nameManagerContext.getSymbolName(null, "diagnostics");
			this.messageName = nameManagerContext.getSymbolName(null, "message");
			this.severityName = nameManagerContext.getSymbolName(null, "severity");
		}
		else {
			this.contextName = null;
			this.diagnosticsName = null;
			this.messageName = null;
			this.severityName = null;
		}
	}

	@Override
	public @Nullable CGParameter createEvaluatorParameter() {
		return null;
	}

/*	@Override */
	public @Nullable CGValuedElement createEvaluatorVariable() {
		CGText evaluator = CGModelFactory.eINSTANCE.createCGText();
		setNames2(evaluator, JavaConstants.EVALUATOR_NAME, JavaConstants.EVALUATOR_TYPE_ID);
		String managerClassName = ImportUtils.getAffixedName(EcoreExecutorManager.class);
		evaluator.setTextValue("new " + managerClassName + "(this, " + getGlobalContext().getTablesClassName() + ".LIBRARY)");
		return evaluator;
	}

	@Override
	public @NonNull CGValuedElement createIdResolverVariable() {
		CGValuedElement evaluator = createEvaluatorVariable();
		CGValuedElement idResolverVariable = super.createIdResolverVariable();
		idResolverVariable.getOwns().add(evaluator);
		return idResolverVariable;
	}

	@Override
	public @Nullable CGParameter createTypeIdParameter() {
		return null;
	}

	public @Nullable String getContextName() {
		return contextName;
	}

	public @Nullable String getDiagnosticsName() {
		return diagnosticsName;
	}

	@Override
	public @NonNull OCLinEcoreGlobalContext getGlobalContext() {
		return (OCLinEcoreGlobalContext) globalContext;
	}

	public @Nullable String getMessageName() {
		return messageName;
	}

	public @Nullable String getSeverityName() {
		return severityName;
	}
}
