/**
 * <copyright>
 *
 * Copyright (c) 2011 E.D.Willink and others.
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
 * $Id$
 */
package org.eclipse.ocl.examples.library.executor;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.elements.DomainCollectionType;
import org.eclipse.ocl.examples.domain.elements.DomainElement;
import org.eclipse.ocl.examples.domain.elements.DomainMetaclass;
import org.eclipse.ocl.examples.domain.elements.DomainTupleType;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.ids.TemplateParameterId;
import org.eclipse.ocl.examples.domain.ids.TupleTypeId;
import org.eclipse.ocl.examples.domain.types.AbstractCollectionType;
import org.eclipse.ocl.examples.domain.types.AbstractStandardLibrary;
import org.eclipse.ocl.examples.domain.types.AbstractTupleType;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.domain.values.IntegerValue;
import org.eclipse.ocl.examples.library.oclstdlib.OCLstdlibTables;

public abstract class ExecutableStandardLibrary extends AbstractStandardLibrary
{
	/**
	 * Shared cache of the lazily created lazily deleted Classifier types of each type. 
	 */
	private @NonNull Map<DomainType, WeakReference<DomainMetaclass>> classifiers = new WeakHashMap<DomainType, WeakReference<DomainMetaclass>>();
	
	/**
	 * Shared cache of the lazily created lazily deleted specializations of each type. 
	 */
	private @NonNull Map<DomainType, Map<DomainType, WeakReference<AbstractCollectionType>>> specializations = new WeakHashMap<DomainType, Map<DomainType, WeakReference<AbstractCollectionType>>>();
	
	/**
	 * Shared cache of the lazily created lazily deleted tuples. 
	 */
	private @NonNull Map<TupleTypeId, WeakReference<DomainTupleType>> tupleTypeMap = new WeakHashMap<TupleTypeId, WeakReference<DomainTupleType>>();
	
//	public abstract @NonNull DomainEvaluator createEvaluator(@NonNull EObject contextObject, @Nullable Map<Object, Object> contextMap);

//	@Override
//	protected @NonNull IdResolver createIdResolver() {
//		@SuppressWarnings("null")@NonNull List<EObject> emptyList = Collections.<EObject>emptyList();
//		return new EcoreIdResolver(emptyList, this);
//	}

	protected abstract @NonNull DomainMetaclass createMetaclass(@NonNull DomainType classType);
	
	public @NonNull DomainType getBagType() {
		return OCLstdlibTables.Types._Bag;
	}

	public @NonNull DomainCollectionType getBagType(@NonNull DomainType elementType, @Nullable IntegerValue lower, @Nullable IntegerValue upper) {
		return getCollectionType(getBagType(), elementType, lower, upper);
	}

	public @NonNull DomainType getBooleanType() {
		return OCLstdlibTables.Types._Boolean;
	}

	public @NonNull DomainType getCollectionType() {
		return OCLstdlibTables.Types._Collection;
	}

	@Override
	public synchronized @NonNull DomainCollectionType getCollectionType(@NonNull DomainType genericType, @NonNull DomainType elementType, @Nullable IntegerValue lower, @Nullable IntegerValue upper) {
		AbstractCollectionType specializedType = null;
		Map<DomainType, WeakReference<AbstractCollectionType>> map = specializations.get(genericType);
		if (map == null) {
			map = new WeakHashMap<DomainType, WeakReference<AbstractCollectionType>>();
			specializations.put(genericType, map);
		}
		else {
			specializedType = weakGet(map, elementType);
		}
		if (specializedType == null) {
			specializedType = new AbstractCollectionType(this, DomainUtil.nonNullModel(genericType.getName()), genericType, elementType, lower, upper);
			map.put(elementType, new WeakReference<AbstractCollectionType>(specializedType));
		}
		return specializedType;
	}

	public @NonNull DomainType getIntegerType() {
		return OCLstdlibTables.Types._Integer;
	}

	public synchronized @NonNull DomainMetaclass getMetaclass(@NonNull DomainType classType) {
		DomainMetaclass metaclass = weakGet(classifiers, classType);
		if (metaclass == null) {
			metaclass = createMetaclass(classType);
			classifiers.put(classType, new WeakReference<DomainMetaclass>(metaclass));
		}
		return metaclass;
	}

	public @NonNull DomainType getOclAnyType() {
		return OCLstdlibTables.Types._OclAny;
	}

	public @NonNull DomainType getOclComparableType() {
		return OCLstdlibTables.Types._OclComparable;
	}

	public @NonNull DomainType getOclElementType() {
		return OCLstdlibTables.Types._OclElement;
	}

	public @NonNull DomainType getOclInvalidType() {
		return OCLstdlibTables.Types._OclInvalid;
	}

	public DomainType getOclMessageType() {
		return OCLstdlibTables.Types._OclMessage;
	}

	public @NonNull DomainType getOclSelfType() {
		return OCLstdlibTables.Types._OclSelf;
	}

	public @NonNull DomainType getOclSummableType() {
		return OCLstdlibTables.Types._OclSummable;
	}

	public @NonNull DomainType getOclTupleType() {
		return OCLstdlibTables.Types._OclTuple;
	}

	public @NonNull DomainType getOclVoidType() {
		return OCLstdlibTables.Types._OclVoid;
	}

	public @NonNull DomainType getOrderedSetType() {
		return OCLstdlibTables.Types._OrderedSet;
	}

	public @NonNull DomainCollectionType getOrderedSetType(@NonNull DomainType elementType, @Nullable IntegerValue lower, @Nullable IntegerValue upper) {
		return getCollectionType(getOrderedSetType(), elementType, lower, upper);
	}

	public @NonNull DomainType getRealType() {
		return OCLstdlibTables.Types._Real;
	}

	public @NonNull DomainType getSequenceType() {
		return OCLstdlibTables.Types._Sequence;
	}

	public @NonNull DomainCollectionType getSequenceType(@NonNull DomainType elementType, @Nullable IntegerValue lower, @Nullable IntegerValue upper) {
		return getCollectionType(getSequenceType(), elementType, lower, upper);
	}

	public @NonNull DomainType getSetType() {
		return OCLstdlibTables.Types._Set;
	}

	public @NonNull DomainCollectionType getSetType(@NonNull DomainType elementType, @Nullable IntegerValue lower, @Nullable IntegerValue upper) {
		return getCollectionType(getSetType(), elementType, lower, upper);
	}

	public @NonNull DomainType getStringType() {
		return OCLstdlibTables.Types._String;
	}

/*	public @NonNull DomainTupleType getTupleType(@NonNull List<? extends DomainTypedElement> parts) {
		StringBuilder s = new StringBuilder();
		for (DomainTypedElement part : parts) {
			s.append(part.getName());
			s.append("\n"); //$NON-NLS-1$
		}
		String key = s.toString();
		synchronized (this) {
			List<WeakReference<DomainTupleType>> tupleTypes = tupleTypeMap.get(key);
			if (tupleTypes != null) {
				for (int j = tupleTypes.size(); --j >= 0; ) {
					WeakReference<DomainTupleType> tupleTypeRef = tupleTypes.get(j);
					DomainTupleType tupleType = tupleTypeRef.get();
					if (tupleType == null) {
						tupleTypes.remove(j);		// Trim stale list entry.
					}
					else {
						int i = 0;
						for (; i < parts.size(); i++) {
							List<? extends DomainTypedElement> ownedAttributes = tupleType.getOwnedAttribute();
							if (ownedAttributes.get(i).getType() != parts.get(i).getType()) {
								break;
							}
						}
						if (i >= parts.size()) {
							return tupleType;
						}
					}
				}
			}
			else {
				tupleTypes = new ArrayList<WeakReference<DomainTupleType>>();
				tupleTypeMap.put(key, tupleTypes);
			}
			DomainTupleType tupleType = new AbstractTupleType(this, parts);
			tupleTypes.add(new WeakReference<DomainTupleType>(tupleType));
			return tupleType;
		}
	} */

	public @NonNull DomainElement getTemplateParameter(@NonNull TemplateParameterId id, DomainElement context) {
		throw new UnsupportedOperationException();
	}

	public synchronized @NonNull DomainTupleType getTupleType(@NonNull TupleTypeId typeId) {
		WeakReference<DomainTupleType> ref = tupleTypeMap.get(typeId);
		if (ref != null) {
			DomainTupleType domainTupleType = ref.get();
			if (domainTupleType != null) {
				return domainTupleType;
			}
		}
		DomainTupleType domainTupleType = new AbstractTupleType(this, typeId);
		tupleTypeMap.put(typeId, new WeakReference<DomainTupleType>(domainTupleType));
		return domainTupleType;
	}

	public @NonNull DomainType getUniqueCollectionType() {
		return OCLstdlibTables.Types._UniqueCollection;
	}

	public @NonNull DomainType getUnlimitedNaturalType() {
		return OCLstdlibTables.Types._UnlimitedNatural;
	}
}
