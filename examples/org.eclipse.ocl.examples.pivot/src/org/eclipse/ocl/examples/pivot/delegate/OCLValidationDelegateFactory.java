/**
 * <copyright>
 *
 * Copyright (c) 2010,2011 E.D.Willink and others.
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
 * $Id: OCLValidationDelegateFactory.java,v 1.5 2011/05/30 16:09:57 ewillink Exp $
 */
package org.eclipse.ocl.examples.pivot.delegate;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.common.delegate.DelegateResourceSetAdapter;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;

/**
 * Factory for OCL derived-classifier validation delegates.
 */
public class OCLValidationDelegateFactory extends AbstractOCLDelegateFactory
		implements ValidationDelegate.Factory, ValidationDelegate
{
	public OCLValidationDelegateFactory(@NonNull String delegateURI) {
		super(delegateURI);
	}

	public @Nullable ValidationDelegate createValidationDelegate(@NonNull EClassifier classifier) {
		EPackage ePackage = DomainUtil.nonNullEMF(classifier.getEPackage());
		return new OCLValidationDelegate(getDelegateDomain(ePackage), classifier);
	}

	protected @Nullable ValidationDelegate getValidationDelegate(@NonNull EClassifier eClassifier) {
		DelegateEClassifierAdapter ecAdapter = DelegateEClassifierAdapter.getAdapter(eClassifier);
		ValidationDelegate validationDelegate = ecAdapter.getValidationDelegate(delegateURI);
		return validationDelegate;
	}

	public boolean validate(EClass eClass, EObject eObject,
			Map<Object, Object> context, EOperation invariant, String expression) {
		if (eClass == null) {
			throw new NullPointerException("Null EClass");
		}
		ValidationDelegate validationDelegate = getValidationDelegate(eClass);
		if (validationDelegate == null) {
			return true;		// FIXME
		}
		return validationDelegate.validate(eClass, eObject, context, invariant, expression);
	}

	public boolean validate(EClass eClass, EObject eObject,
			Map<Object, Object> context, String constraint, String expression) {
		if (eClass == null) {
			throw new NullPointerException("Null EClass");
		}
		ValidationDelegate validationDelegate = getValidationDelegate(eClass);
		if (validationDelegate == null) {
			return true;		// FIXME
		}
		return validationDelegate.validate(eClass, eObject, context, constraint, expression);
	}

	public boolean validate(EDataType eDataType, Object value,
			Map<Object, Object> context, String constraint, String expression) {
		if (eDataType == null) {
			throw new NullPointerException("Null EDataType");
		}
		ValidationDelegate validationDelegate = getValidationDelegate(eDataType);
		if (validationDelegate == null) {
			return true;		// FIXME
		}
		return validationDelegate.validate(eDataType, value, context, constraint, expression);
	}

//	public boolean validate(EClass eClass, EObject eObject,
//			DiagnosticChain diagnostics, Map<Object, Object> context,
//			EOperation invariant, String expression, int severity, String source, int code) {
//		ValidationDelegate validationDelegate = getValidationDelegate(eClass);
//		return validationDelegate.validate(eClass, eObject, diagnostics, context, invariant, expression, severity, source, code);
//	}

	public boolean validate(@NonNull EClass eClass, @NonNull EObject eObject,
			@Nullable DiagnosticChain diagnostics, Map<Object, Object> context,
			@NonNull String constraint, String expression, int severity, String source, int code) {
		ValidationDelegate validationDelegate = getValidationDelegate(eClass);
		if (validationDelegate == null) {
			return true;		// FIXME
		}
		return validationDelegate.validate(eClass, eObject, diagnostics, context, constraint, expression, severity, source, code);
	}

	public boolean validate(@NonNull EDataType eDataType, @NonNull Object value,
			@Nullable DiagnosticChain diagnostics, Map<Object, Object> context,
			@NonNull String constraint, String expression, int severity, String source, int code) {
		ValidationDelegate validationDelegate = getValidationDelegate(eDataType);
		if (validationDelegate == null) {
			return true;		// FIXME
		}
		return validationDelegate.validate(eDataType, value, diagnostics, context, constraint, expression, severity, source, code);
	}

	/**
	 * The Global variant of the Factory delegates to a local ResourceSet factory if one
	 * can be located at the EOperation.Internal.InvocationDelegate.Factory.Registry
	 * by the DelegateResourceSetAdapter.
	 */
	public static class Global extends OCLValidationDelegateFactory
	{
		public Global() {
			super(OCLDelegateDomain.OCL_DELEGATE_URI_PIVOT);
		}

		@Override
		public @Nullable ValidationDelegate createValidationDelegate(@NonNull EClassifier classifier) {
			ValidationDelegate.Factory.Registry localRegistry = DelegateResourceSetAdapter.getRegistry(
				classifier, ValidationDelegate.Factory.Registry.class, null);
			if (localRegistry != null) {
				ValidationDelegate.Factory factory = localRegistry.getValidationDelegate(delegateURI);
				if (factory != null) {
					return factory.createValidationDelegate(classifier);
				}
			}
			return super.createValidationDelegate(classifier);
		}	
	}
}
