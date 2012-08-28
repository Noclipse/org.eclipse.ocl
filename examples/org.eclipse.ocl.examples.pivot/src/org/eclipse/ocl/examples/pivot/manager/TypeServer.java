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
package org.eclipse.ocl.examples.pivot.manager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.examples.library.executor.ReflectiveType;
import org.eclipse.ocl.examples.pivot.ClassifierType;
import org.eclipse.ocl.examples.pivot.CollectionType;
import org.eclipse.ocl.examples.pivot.Iteration;
import org.eclipse.ocl.examples.pivot.Operation;
import org.eclipse.ocl.examples.pivot.ParameterableElement;
import org.eclipse.ocl.examples.pivot.PivotFactory;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.TemplateBinding;
import org.eclipse.ocl.examples.pivot.TemplateParameter;
import org.eclipse.ocl.examples.pivot.TemplateParameterSubstitution;
import org.eclipse.ocl.examples.pivot.TemplateSignature;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.TypedElement;
import org.eclipse.ocl.examples.pivot.executor.PivotReflectivePackage;
import org.eclipse.ocl.examples.pivot.utilities.PivotUtil;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

/**
 * A TypeServer serves coordinated behaviour of one or more
 * merged Types as required for Complete OCL type extension.
 * 
 * For specializeable types, a TypeServer keeps track of zero or more specializations
 * using WeakReferences so that the specializations vanish once no longer required.
 */
public class TypeServer
{
	public static Function<TypeTracker, Type> tracker2class = new Function<TypeTracker, Type>()
	{
		public Type apply(TypeTracker typeTracker) {
			return typeTracker.getTarget();
		}
	};

	protected final PackageManager packageManager;
	private Type primaryType;
	
	private final List<TypeTracker> trackers = new ArrayList<TypeTracker>();

	/**
	 * Map from operation name to the overload list of operation or a list of operations to be treated as merged. 
	 */
	private final Map<String, List<List<Operation>>> operation2operations = new HashMap<String, List<List<Operation>>>();

	/**
	 * Map from property name to the list of properties to be treated as merged. 
	 */
	private final Map<String, List<Property>> property2properties = new HashMap<String, List<Property>>();
	
	private PackageServer packageServer;			// Null value assigned by setTarget();
	
	/**
	 * Compiled inheritance relationships used by compiled expressions.
	 */
	private ReflectiveType executorType = null;
	
	/**
	 * Map from first actual type to list of specialized types for further actual types. 
	 */
	private Map<ParameterableElement, List<WeakReference<Type>>> firstActual2specializations = null;
	
	protected TypeServer(PackageManager packageManager) {
		this.packageManager = packageManager;
	}

	void addOperation(Operation pivotOperation) {
		String operationName = pivotOperation.getName();
		List<List<Operation>> overloads = operation2operations.get(operationName);
		if (overloads == null) {
			overloads = new ArrayList<List<Operation>>();
			operation2operations.put(operationName, overloads);
		}
		List<Operation> overload = findOverload(overloads, pivotOperation);
		if (overload == null) {
			overload = new ArrayList<Operation>();
			overloads.add(overload);
		}
		if (!overload.contains(pivotOperation)) {
			overload.add(pivotOperation);
		}
	}

	void addProperty(Property pivotProperty) {
		String propertyName = pivotProperty.getName();
		List<Property> properties = property2properties.get(propertyName);
		if (properties == null) {
			properties = new ArrayList<Property>();
			property2properties.put(propertyName, properties);
		}
		if (!properties.contains(pivotProperty)) {
			properties.add(pivotProperty);
		}
	}
	
	public TypeTracker addType(Type type) {
		TypeTracker typeTracker = (TypeTracker) EcoreUtil.getAdapter(type.eAdapters(), packageManager);
		if (typeTracker == null) {
			typeTracker = new TypeTracker(this, type);
		}
		if (!trackers.contains(typeTracker)) {
			trackers.add(typeTracker);
			if (trackers.size() == 1) {
				MetaModelManager metaModelManager = packageManager.getMetaModelManager();
				primaryType = trackers.get(0).getTarget();
				org.eclipse.ocl.examples.pivot.Package targetPackage = primaryType.getPackage();
				packageServer = metaModelManager.getPackageTracker(targetPackage).getPackageServer();
			}
		}
		return typeTracker;
	}

	void addedOperation(Object object) {
		if (object instanceof Operation) {
			Operation pivotOperation = (Operation)object;
			addOperation(pivotOperation);
		}
	}

	void addedProperty(Object object) {
		if (object instanceof Property) {
			Property pivotProperty = (Property)object;
			addProperty(pivotProperty);
		}
	}

	void changedInheritance() {
		if (executorType != null) {
			executorType.uninstall();
			executorType = null;
		}
	}
	
	protected Type createSpecialization(List<? extends ParameterableElement> templateArguments) {
		Type unspecializedType = primaryType;
		String typeName = unspecializedType.getName();
		TemplateSignature templateSignature = unspecializedType.getOwnedTemplateSignature();
		List<TemplateParameter> templateParameters = templateSignature.getOwnedParameter();
		EClass eClass = unspecializedType.eClass();
		EFactory eFactoryInstance = eClass.getEPackage().getEFactoryInstance();
		Type specializedType = (Type) eFactoryInstance.create(eClass);		
		specializedType.setName(typeName);
		TemplateBinding templateBinding = PivotFactory.eINSTANCE.createTemplateBinding();
		templateBinding.setSignature(templateSignature);
		Map<TemplateParameter, ParameterableElement> allBindings = new HashMap<TemplateParameter, ParameterableElement>();
		for (int i = 0; i < templateParameters.size(); i++) {
			TemplateParameter formalParameter = templateParameters.get(i);
			ParameterableElement actualType = templateArguments.get(i);
			allBindings.put(formalParameter, templateArguments.get(i));
			TemplateParameterSubstitution templateParameterSubstitution = PivotFactory.eINSTANCE.createTemplateParameterSubstitution();
			templateParameterSubstitution.setFormal(formalParameter);
			if ((actualType != null) && (actualType.eResource() == null)) {
				templateParameterSubstitution.setOwnedActual(actualType);
			}
			else {
				templateParameterSubstitution.setActual(actualType);
			}
			templateBinding.getParameterSubstitution().add(templateParameterSubstitution);
		}
		specializedType.getTemplateBinding().add(templateBinding);
		resolveSuperClasses(specializedType, unspecializedType, allBindings);
		if (specializedType instanceof CollectionType) {
			ParameterableElement templateArgument = templateArguments.get(0);
			CollectionType specializedCollectionType = (CollectionType)specializedType;
			specializedCollectionType.setElementType((Type) templateArgument);
		}
		else if (specializedType instanceof ClassifierType) {
			ParameterableElement templateArgument = templateArguments.get(0);
			ClassifierType specializedClassifierType = (ClassifierType)specializedType;
			specializedClassifierType.setInstanceType((Type)templateArgument);
		}
		specializedType.setUnspecializedElement(unspecializedType);
		MetaModelManager metaModelManager = packageManager.getMetaModelManager();
		Orphanage.getOrphanage(metaModelManager.getPivotResourceSet()).add(specializedType);
//		specializeableClassServer.metaModelManager.addOrphanClass(specializedType);
		return specializedType;
	}

	public void dispose() {
		if (!trackers.isEmpty()) {
			Collection<TypeTracker> savedTypeTrackers = new ArrayList<TypeTracker>(trackers);
			trackers.clear();
			for (TypeTracker typeTracker : savedTypeTrackers) {
				typeTracker.dispose();
			}
		}
		property2properties.clear();
		operation2operations.clear();
		if (executorType != null) {
			executorType.dispose();
			executorType = null;
		}
	}
	
	private List<Operation> findOverload(List<List<Operation>> overloads, Operation requiredOperation) {
		List<? extends TypedElement> requiredParameters;
		if (requiredOperation instanceof Iteration) {
			requiredParameters = ((Iteration)requiredOperation).getOwnedIterator();
		}
		else {
			requiredParameters = requiredOperation.getOwnedParameter();
		}
		int requiredSize = requiredParameters.size();
		for (List<Operation> overload : overloads) {
			if (overload.size() > 0) {
				Operation operation = overload.get(0);
				List<? extends TypedElement> actualParameters;
				if (operation instanceof Iteration) {
					actualParameters = ((Iteration)operation).getOwnedIterator();
				}
				else {
					actualParameters = operation.getOwnedParameter();
				}
				if (requiredSize == actualParameters.size()) {
					boolean gotIt = true;
					for (int i = 0; i < requiredSize; i++) {
						TypedElement requiredParameter = requiredParameters.get(i);
						TypedElement actualParameter = actualParameters.get(i);
						Type requiredType = requiredParameter.getType();
						Type actualType = actualParameter.getType();
						if (requiredType != actualType) {
							gotIt = false;
							break;
						}
					}
					if (gotIt) {
						return overload;
					}
				}
			}
		}			
		return null;
	}

	protected Type findSpecialization(List<WeakReference<Type>> partialSpecializations, List<? extends ParameterableElement> templateArguments) {
		for (int j = partialSpecializations.size(); --j >= 0; ) {
			Type specializedType = partialSpecializations.get(j).get();
			if (specializedType == null) {
				partialSpecializations.remove(j);
			}
			else {
				int i = 0;
				boolean gotIt = true;
				for (TemplateBinding templateBinding : specializedType.getTemplateBinding()) {
					for (TemplateParameterSubstitution parameterSubstitution : templateBinding.getParameterSubstitution()) {
						if (i > 0) {
							ParameterableElement requiredTemplateArgument = templateArguments.get(i);
							ParameterableElement actualTemplateArgument = parameterSubstitution.getActual();
							if (requiredTemplateArgument != actualTemplateArgument) {			// WIP isEquals ???
								gotIt = false;
								break;
							}
						}
						i++;
					}
					if (!gotIt) {
						break;
					}
				}
				if (gotIt) {
					return specializedType;
				}
			}
		}
		return null;
	}

	public synchronized Type findSpecializedType(List<? extends ParameterableElement> templateArguments) {
		TemplateSignature templateSignature = primaryType.getOwnedTemplateSignature();
		List<TemplateParameter> templateParameters = templateSignature.getParameter();
		int iMax = templateParameters.size();
		if (templateArguments.size() != iMax) {
			return null;
		}
		if (firstActual2specializations == null) {
			return null;
		}
		ParameterableElement firstTemplateArgument = templateArguments.get(0);
		List<WeakReference<Type>> partialSpecializations = firstActual2specializations.get(firstTemplateArgument);
		if (partialSpecializations == null) {
			return null;
		}
		return findSpecialization(partialSpecializations, templateArguments);
	}

//	protected PivotReflectivePackage getExecutorPackage() {
//		return packageServer.getExecutorPackage();
//	}

	public ReflectiveType getExecutorType() {
		if (executorType == null) {
			PivotReflectivePackage executorPackage = packageServer.getExecutorPackage();
			executorType = executorPackage.getInheritance(primaryType);
		}
		return executorType;
	}


	public final PackageManager getPackageManager() {
		return packageManager;
	}

	public Operation getOperation(Operation pivotOperation) {
		String operationName = pivotOperation.getName();
		List<List<Operation>> overloads = operation2operations.get(operationName);
		if (overloads == null) {
			return null;
		}
		List<Operation> overload = findOverload(overloads, pivotOperation);
		if (overload == null) {
			return null;
		}
		return overload.isEmpty() ? null : overload.get(0);
	}

	public Iterable<Operation> getOperations(Operation pivotOperation) {
		String operationName = pivotOperation.getName();
		List<List<Operation>> overloads = operation2operations.get(operationName);
		if (overloads == null) {
			return null;
		}
		return findOverload(overloads, pivotOperation);
	}

	public Type getPrimaryType() {
		return primaryType;
	}

	public Iterable<Property> getProperties(Property pivotProperty) {
		String propertyName = pivotProperty.getName();
		return property2properties.get(propertyName);
	}

	public Property getProperty(String propertyName) {
		List<Property> properties = property2properties.get(propertyName);
		if (properties == null) {
			return null;
		}
		return properties.isEmpty() ? null : properties.get(0);
	}

	public synchronized Type getSpecializedType(List<? extends ParameterableElement> templateArguments) {
		TemplateSignature templateSignature = primaryType.getOwnedTemplateSignature();
		List<TemplateParameter> templateParameters = templateSignature.getParameter();
		int iMax = templateParameters.size();
		if (templateArguments.size() != iMax) {
			return null;
		}
		if (firstActual2specializations == null) {
			firstActual2specializations = new HashMap<ParameterableElement, List<WeakReference<Type>>>();
		}
		ParameterableElement firstTemplateArgument = templateArguments.get(0);
		List<WeakReference<Type>> partialSpecializations = firstActual2specializations.get(firstTemplateArgument);
		if (partialSpecializations == null) {
			partialSpecializations = new ArrayList<WeakReference<Type>>();
			firstActual2specializations.put(firstTemplateArgument, partialSpecializations);
		}
		Type specializedType = findSpecialization(partialSpecializations, templateArguments);
		if (specializedType == null) {
			specializedType = createSpecialization(templateArguments);
			partialSpecializations.add(new WeakReference<Type>(specializedType));
		}
		return specializedType;
	}

	public TypeTracker getTypeTracker(Type pivotType) {
		for (TypeTracker typeTracker : trackers) {
			if (typeTracker.getTarget() == pivotType) {
				return typeTracker;
			}
		}
		return addType(pivotType);
	}

	public List<TypeTracker> getTypeTrackers() {
		return trackers;
	}

	public Iterable<Type> getTypes() {
		return Iterables.transform(trackers, tracker2class);
	}
	
	void removedTracker(TypeTracker typeTracker) {
		trackers.remove(typeTracker);
	}

	void removedOperation(Object object) {
		if (object instanceof Operation) {
			Operation pivotOperation = (Operation)object;
			String operationName = pivotOperation.getName();
			List<List<Operation>> overloads = operation2operations.get(operationName);
			if (overloads == null) {
				overloads = new ArrayList<List<Operation>>();
				operation2operations.put(operationName, overloads);
			}
			List<Operation> overload = findOverload(overloads, pivotOperation);
			if (overload != null) {
				overload.remove(pivotOperation);
				if (overload.isEmpty()) {
					overloads.remove(overload);
					if (overloads.isEmpty()) {
						operation2operations.remove(operationName);
					}
				}
			}
//			removeOrphan(pivotOperation);
		}
	}

	void removedProperty(Object object) {
		if (object instanceof Property) {
			Property pivotProperty = (Property)object;
			String propertyName = pivotProperty.getName();
			List<Property> properties = property2properties.get(propertyName);
			if (properties != null) {
				properties.remove(propertyName);
				if (properties.isEmpty()) {
					property2properties.remove(propertyName);
				}
			}
//			removeOrphan(pivotProperty);
		}
	}

	void removedType(Type pivotType) {
		TypeTracker typeTracker = packageManager.findTypeTracker(pivotType);
		trackers.remove(typeTracker);
		if (trackers.size() <= 0) {
			dispose();
		}
		else {	
			primaryType = null;
			packageServer = null;
		}
	}

	protected void resolveSuperClasses(Type specializedClass, Type libraryClass, Map<TemplateParameter, ParameterableElement> allBindings) {
		MetaModelManager metaModelManager = packageManager.getMetaModelManager();
		for (Type superType : libraryClass.getSuperClass()) {
			List<TemplateBinding> superTemplateBindings = superType.getTemplateBinding();
			if (superTemplateBindings.size() > 0) {
//				Map<TemplateParameter, ParameterableElement> superTemplateArgumentMap = new HashMap<TemplateParameter, ParameterableElement>();
				List<ParameterableElement> superTemplateArgumentList = new ArrayList<ParameterableElement>();
				for (TemplateBinding superTemplateBinding : superTemplateBindings) {
					for (TemplateParameterSubstitution superParameterSubstitution : superTemplateBinding.getParameterSubstitution()) {
						ParameterableElement superActual = superParameterSubstitution.getActual();
//						TemplateParameter superFormal = superParameterSubstitution.getFormal();
						TemplateParameter superTemplateParameter = superActual.getTemplateParameter();
						ParameterableElement actualActual = allBindings.get(superTemplateParameter);
//						superTemplateArgumentMap.put(superFormal, actualActual);
						superTemplateArgumentList.add(actualActual);
					}
				}
				Type unspecializedSuperType = PivotUtil.getUnspecializedTemplateableElement(superType);
				TypeServer superTypeServer = metaModelManager.getTypeServer(unspecializedSuperType);
/*				List<ParameterableElement> superTemplateArgumentList = new ArrayList<ParameterableElement>();
				for (TemplateBinding templateBinding : superTemplateBindings) {
					for (TemplateParameterSubstitution parameterSubstitution : templateBinding.getParameterSubstitution()) {
						ParameterableElement templateArgument = parameterSubstitution.getActual();
						superTemplateArgumentList.add(templateArgument);
					}
				} */
				Type specializedSuperType = superTypeServer.getSpecializedType(superTemplateArgumentList);
				specializedClass.getSuperClass().add(specializedSuperType);
			}
			else {
				specializedClass.getSuperClass().add(superType);
			}
		}
	}

//	@Override
//	public void setTarget(Notifier newTarget) {
//		super.setTarget(newTarget);
//		MetaModelManager metaModelManager = getMetaModelManager();
//		org.eclipse.ocl.examples.pivot.Package targetPackage = ((Type)newTarget).getPackage();
//		packageServer = metaModelManager.getPackageTracker(targetPackage).getPackageServer();
//		assert packageServer != null;
//	}

//	@Override
//	public void unsetTarget(Notifier oldTarget) {
//		packageServer = null;
//		super.unsetTarget(oldTarget);
//	}
}