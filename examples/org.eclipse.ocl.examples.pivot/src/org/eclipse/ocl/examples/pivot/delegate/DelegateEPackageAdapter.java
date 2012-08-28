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
 *   E.D.Willink - Bug 353171
 *
 * </copyright>
 *
 * $Id: DelegateEPackageAdapter.java,v 1.3 2011/05/07 05:53:44 ewillink Exp $
 */
package org.eclipse.ocl.examples.pivot.delegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.common.delegate.DelegateResourceSetAdapter;
import org.eclipse.ocl.common.delegate.VirtualDelegateMapping;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;

/**
 * DelegateEPackageAdapter extends an EPackage to cache its DelegateDomain
 * that supervises installation of OCL annotations from an OCL document.
 */
public class DelegateEPackageAdapter extends AdapterImpl
{
	/**
	 *	Return the DelegateEPackageAdapter for ePackage, if there is one, or null if none.
	 */
	public static @Nullable DelegateEPackageAdapter findAdapter(@NonNull EPackage ePackage) {
		return (DelegateEPackageAdapter) EcoreUtil.getAdapter(ePackage.eAdapters(), DelegateEPackageAdapter.class);
	}

	/**
	 *	Return the DelegateEPackageAdapter for ePackage, creating
	 *	one if necessary.
	 */
	public static @NonNull DelegateEPackageAdapter getAdapter(@NonNull EPackage ePackage) {
		DelegateEPackageAdapter adapter = (DelegateEPackageAdapter) EcoreUtil.getAdapter(ePackage.eAdapters(), DelegateEPackageAdapter.class);
		if (adapter == null) {
			adapter = new DelegateEPackageAdapter();
			ePackage.eAdapters().add(adapter);
		}
		return adapter;
	}

	/**
	 * The map from delegateURI to known DelegateDomain. Mappings are established
	 * lazily by {@link #getDelegateDomain}.
	 */
	protected Map<String, DelegateDomain> delegateDomainMap = null;

	/**
	 * The map from behavior name to corresponding DelegateDomain. This is
	 * defined by an http://www.eclipse.org/emf/2002/Ecore EPackage annotation
	 * with the behavior name as a key and the delegateURIs as a comma
	 * separated list.
	 */
	protected Map<String, List<DelegateDomain>> delegatedBehaviorMap = null;

	protected @NonNull DelegateDomain createDelegateDomain(@NonNull String delegateURI) {
		EPackage ePackage = DomainUtil.nonNullState(getTarget());
		DelegateDomain.Factory.Registry registry = DelegateResourceSetAdapter.getRegistry(
			ePackage, DelegateDomain.Factory.Registry.class, DelegateDomain.Factory.Registry.INSTANCE);
		DelegateDomain.Factory factory = registry != null ? registry.getFactory(delegateURI) : null;
		if (factory == null) {
			factory = OCLDelegateDomainFactory.INSTANCE;
		}
		return factory.createDelegateDomain(delegateURI, ePackage);
	}

	/**
	 * Return the DelegateDomain for this package and for delegateURI, returning null it does not exist. 
	 */
	public @Nullable DelegateDomain getDelegateDomain(@NonNull String delegateURI) {
		if (delegateDomainMap == null) {
			getDelegateDomains();
		}
		return delegateDomainMap.get(delegateURI);
	}

	public @NonNull Map<String, DelegateDomain> getDelegateDomains() {
		if (delegateDomainMap == null) {
			delegatedBehaviorMap = new HashMap<String, List<DelegateDomain>>();
			delegateDomainMap = new HashMap<String, DelegateDomain>();
			EPackage ePackage = getTarget();
			EAnnotation eAnnotation = ePackage.getEAnnotation(EcorePackage.eNS_URI);
			if (eAnnotation != null) {
				VirtualDelegateMapping registry = VirtualDelegateMapping.getRegistry(ePackage);
				EMap<String, String> details = eAnnotation.getDetails();
				for (DelegatedBehavior<?, ?, ?> delegatedBehavior : AbstractDelegatedBehavior.getDelegatedBehaviors()) {
					String behaviorName = delegatedBehavior.getName();
					String delegateURIs = details.get(behaviorName);
					if (delegateURIs != null) {
						for (StringTokenizer stringTokenizer = new StringTokenizer(delegateURIs); stringTokenizer.hasMoreTokens();) {
							String delegateURI = DomainUtil.nonNullJava(stringTokenizer.nextToken());
							String resolvedURI = registry.resolve(delegateURI);
							initializeDelegatedBehavior(resolvedURI, delegatedBehavior);
						}
					}
				}
			}
		}
		return DomainUtil.nonNullJDT(delegateDomainMap);
	}

	public @NonNull List<DelegateDomain> getDelegateDomains(@NonNull DelegatedBehavior<?, ?, ?> delegatedBehavior) {
		if (delegatedBehaviorMap == null) {
			getDelegateDomains();
		}
		List<DelegateDomain> list = delegatedBehaviorMap.get(delegatedBehavior.getName());
		if (list != null) {
			return list;
		} else {
			return DomainUtil.nonNullJava(Collections.<DelegateDomain>emptyList());
		}
	}

	@Override
	public EPackage getTarget() {
		return (EPackage) super.getTarget();
	}

	private void initializeDelegatedBehavior(@NonNull String delegateURI, DelegatedBehavior<?, ?, ?> delegatedBehavior) {
		String behaviorName = delegatedBehavior.getName();
		synchronized (delegateDomainMap) {
			DelegateDomain delegateDomain = delegateDomainMap.get(delegateURI);
			if (delegateDomain == null) {
				delegateDomain = createDelegateDomain(delegateURI);
				delegateDomainMap.put(delegateURI, delegateDomain);
			}
			List<DelegateDomain> delegateBehaviorList = delegatedBehaviorMap.get(behaviorName);
			if (delegateBehaviorList == null) {
				delegateBehaviorList = new ArrayList<DelegateDomain>();
				delegatedBehaviorMap.put(behaviorName, delegateBehaviorList);
			}
			if (!delegateBehaviorList.contains(delegateDomain)) {
				delegateBehaviorList.add(delegateDomain);
			}
		}
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == DelegateEPackageAdapter.class;
	}

	/**
	 * Return the DelegateDomain for this package and for delegateURI, creating one if it does not already exist. 
	 *
	 * @since 3.2
	 */
	public @NonNull DelegateDomain loadDelegateDomain(@NonNull String delegateURI) {
		if (delegateDomainMap == null) {
			getDelegateDomains();
		}
		DelegateDomain delegateDomain = delegateDomainMap.get(delegateURI);
		if (delegateDomain == null) {
			delegateDomain = createDelegateDomain(delegateURI);
			delegateDomainMap.put(delegateURI, delegateDomain);
		}
		return delegateDomain;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		EPackage resourceSet = (EPackage) newTarget;
		super.setTarget(resourceSet);
	}

	public void unloadDelegates() {
		if (delegateDomainMap != null) {
			List<DelegateDomain> delegateDomains;
			synchronized (delegateDomainMap) {
				delegateDomains = new ArrayList<DelegateDomain>(delegateDomainMap.values());
			}
			for (DelegateDomain delegateDomain : delegateDomains) {
				delegateDomain.reset();
			}
		}
	}
}
