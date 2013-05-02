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
 * $Id: SettingBehavior.java,v 1.3 2011/02/11 20:00:29 ewillink Exp $
 */
package org.eclipse.ocl.examples.pivot.delegate;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Internal.SettingDelegate;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.common.delegate.DelegateResourceSetAdapter;
import org.eclipse.ocl.common.internal.delegate.OCLDelegateException;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.pivot.Constraint;
import org.eclipse.ocl.examples.pivot.ExpressionInOCL;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.UMLReflection;
import org.eclipse.ocl.examples.pivot.ValueSpecification;
import org.eclipse.ocl.examples.pivot.context.PropertyContext;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.messages.OCLMessages;
import org.eclipse.osgi.util.NLS;

/**
 */
public class SettingBehavior extends AbstractDelegatedBehavior<EStructuralFeature, SettingDelegate.Factory.Registry, SettingDelegate.Factory>
{
	public static final @NonNull SettingBehavior INSTANCE = new SettingBehavior();
	public static final @NonNull String DERIVATION_CONSTRAINT_KEY = "derivation"; //$NON-NLS-1$
	public static final @NonNull String INITIAL_CONSTRAINT_KEY = "initial"; //$NON-NLS-1$
	public static final @NonNull String NAME = "settingDelegates"; //$NON-NLS-1$

	public @Nullable SettingDelegate.Factory getDefaultFactory() {
		return SettingDelegate.Factory.Registry.INSTANCE.getFactory(getName());
	}

	public @NonNull SettingDelegate.Factory.Registry getDefaultRegistry() {
		return DomainUtil.nonNullEMF(SettingDelegate.Factory.Registry.INSTANCE);
	}

	public @NonNull EPackage getEPackage(@NonNull EStructuralFeature eStructuralFeature) {
		return DomainUtil.nonNullEMF(eStructuralFeature.getEContainingClass().getEPackage());
	}

	/**
	 * Return the feature body associated with structuralFeature, if necessary using ocl to
	 * create the relevant parsing environment for a textual definition..
	 * @throws OCLDelegateException 
	 */
	public @NonNull ExpressionInOCL getExpressionInOCL(@NonNull MetaModelManager metaModelManager, @NonNull Property property) throws OCLDelegateException {
		Constraint constraint = property.getDerivationExpression();
		if (constraint != null) {
			ValueSpecification valueSpecification = constraint.getSpecification();
			if (valueSpecification instanceof ExpressionInOCL) {
				return (ExpressionInOCL) valueSpecification;
			}
			PropertyContext propertyContext = new PropertyContext(metaModelManager, null, property);
			ExpressionInOCL expressionInOCL = getExpressionInOCL(propertyContext, constraint);
			if (expressionInOCL != null) {
				return expressionInOCL;
			}
		}
		String message = NLS.bind(OCLMessages.MissingDerivationForSettingDelegate_ERROR_, property);
		throw new OCLDelegateException(message);
	}

	@Override
	public @Nullable SettingDelegate.Factory getFactory(@NonNull DelegateDomain delegateDomain, @NonNull EStructuralFeature eStructuralFeature) {
		SettingDelegate.Factory.Registry registry = DelegateResourceSetAdapter.getRegistry(
			eStructuralFeature, getRegistryClass(), getDefaultRegistry());
	    return registry != null ? registry.getFactory(delegateDomain.getURI()) : null;
	}	

	public @NonNull Class<SettingDelegate.Factory> getFactoryClass() {
		return SettingDelegate.Factory.class;
	}
	
	public @NonNull String getName() {
		return NAME;
	}

	public @NonNull Class<SettingDelegate.Factory.Registry> getRegistryClass() {
		return SettingDelegate.Factory.Registry.class;
	}
}