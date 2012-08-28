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
 * $Id: PivotDiagnostician.java,v 1.1 2011/02/19 12:00:36 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.base.utilities;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.ResourceValidatorImpl;

/**
 * PivotResourceValidator extends CS Resource validation to the referenced Pivot resources and attempts
 * to indicate Pivot validation problems in the appropriate CS context. 
 */
public class PivotResourceValidator extends ResourceValidatorImpl
{
	public static class ValidationDiagnostic extends BasicDiagnostic implements Resource.Diagnostic
	{
		private ValidationDiagnostic() {
			super(EObjectValidator.DIAGNOSTIC_SOURCE, 0, "", null);
		}

		public int getColumn() {
			return 0;		// This could be computed from the CS
		}

		public int getLine() {
			return 0;		// This could be computed from the CS
		}

		public String getLocation() {
			return null;		// This could be computed from the CS
		}

		public Integer getOffset() {
			return null;		// This could be computed from the CS
		}

		public Integer getLength() {
			return 10;		// This could be computed from the CS
		}
	}

	private static final Logger log = Logger.getLogger(PivotResourceValidator.class);

	protected ValidationDiagnostic createDefaultDiagnostic(Diagnostician diagnostician, EObject pivotObject) {
//		Object objectLabel = diagnostician.getObjectLabel(pivotObject);
//		return new ValidationDiagnostic(EcorePlugin.INSTANCE.getString("_UI_DiagnosticRoot_diagnostic",
//			new Object[] { objectLabel }), new Object[] { pivotObject });
		return new ValidationDiagnostic();
	}

	protected void issueFromDiagnostics(IAcceptor<Issue> acceptor, ValidationDiagnostic diagnostic) {
		for (Diagnostic childDiagnostic : diagnostic.getChildren()) {
//			System.out.println(" issueFromEValidatorDiagnostic " + childDiagnostic);
			issueFromEValidatorDiagnostic(childDiagnostic, acceptor);
		}
	}

	protected void performValidation(IAcceptor<Issue> acceptor, Resource pivotResource, CancelIndicator monitor) {
		Diagnostician diagnostician = getDiagnostician();
		Map<Object, Object> context = diagnostician.createDefaultContext();
		for (Resource pResource : pivotResource.getResourceSet().getResources()) {
//			System.out.println(" performValidation " + pResource.getURI() + " on " + Thread.currentThread().getName());
			removeValidationDiagnostics(pResource.getErrors());
			removeValidationDiagnostics(pResource.getWarnings());
			for (EObject pObject : pResource.getContents()) {
				try {
					if (monitor.isCanceled())
						return;
					ValidationDiagnostic diagnostic = createDefaultDiagnostic(diagnostician, pObject);
				    diagnostician.validate(pObject, diagnostic, context);
					if (!diagnostic.getChildren().isEmpty()) {
						if (diagnostic.getSeverity() == Diagnostic.ERROR) {
							pResource.getErrors().add(diagnostic);
						}
						else if (diagnostic.getSeverity() == Diagnostic.WARNING) {
							pResource.getWarnings().add(diagnostic);
						}
						issueFromDiagnostics(acceptor, diagnostic);
					}
				} catch (RuntimeException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	protected void removeValidationDiagnostics(List<Resource.Diagnostic> diagnostics) {
		for (int i = diagnostics.size()-1; i >= 0; i--) {
			Resource.Diagnostic diagnostic = diagnostics.get(i);
			if (diagnostic instanceof ValidationDiagnostic) {
				diagnostics.remove(i);
			}
		}
	}

	protected void reuseValidation(IAcceptor<Issue> acceptor, Resource pivotResource, CancelIndicator monitor) {
		for (Resource pResource : pivotResource.getResourceSet().getResources()) {
//			System.out.println(" reuseValidation " + pResource.getURI() + " on " + Thread.currentThread().getName());
			for (Resource.Diagnostic diagnostic : pResource.getErrors()) {
				if (diagnostic instanceof ValidationDiagnostic) {
					issueFromDiagnostics(acceptor, (ValidationDiagnostic)diagnostic);
				}
			}
			for (Resource.Diagnostic diagnostic : pResource.getWarnings()) {
				if (diagnostic instanceof ValidationDiagnostic) {
					issueFromDiagnostics(acceptor, (ValidationDiagnostic)diagnostic);
				}
			}
		}
	}

	@Override
	public List<Issue> validate(Resource resource, CheckMode mode, CancelIndicator monitor) {
//		System.out.println(new Date() + " Validate " + mode + " : " + csResource.getURI() + " on " + Thread.currentThread().getName());
		List<Issue> issues = super.validate(resource, mode, monitor);
		if (!monitor.isCanceled() && (resource instanceof BaseCSResource)) {
			BaseCSResource csResource = (BaseCSResource)resource;
			CS2PivotResourceAdapter cs2pivotAdapter = CS2PivotResourceAdapter.findAdapter(csResource);
			if (cs2pivotAdapter != null) {
				Resource pivotResource = cs2pivotAdapter.getPivotResource(csResource);
				if (pivotResource != null) {
					IAcceptor<Issue> acceptor = createAcceptor(issues);
					if (mode.shouldCheck(CheckType.EXPENSIVE)) {
						performValidation(acceptor, pivotResource, monitor);
					}
					else {
						reuseValidation(acceptor, pivotResource, monitor);
					}
					if (monitor.isCanceled())
						return null;
				}
			}
		}
		return issues;
	}
}
