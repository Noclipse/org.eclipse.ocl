/**
 * Copyright (c) 2010, 2017 Willink Transformations and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 */
package org.eclipse.ocl.pivot.internal;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.pivot.Behavior;
import org.eclipse.ocl.pivot.Comment;
import org.eclipse.ocl.pivot.Constraint;
import org.eclipse.ocl.pivot.Element;
import org.eclipse.ocl.pivot.ElementExtension;
import org.eclipse.ocl.pivot.MapType;
import org.eclipse.ocl.pivot.Operation;
import org.eclipse.ocl.pivot.PivotPackage;
import org.eclipse.ocl.pivot.Property;
import org.eclipse.ocl.pivot.StereotypeExtender;
import org.eclipse.ocl.pivot.TemplateBinding;
import org.eclipse.ocl.pivot.TemplateParameter;
import org.eclipse.ocl.pivot.TemplateParameterSubstitution;
import org.eclipse.ocl.pivot.TemplateSignature;
import org.eclipse.ocl.pivot.TemplateableElement;
import org.eclipse.ocl.pivot.Type;
import org.eclipse.ocl.pivot.ids.TypeId;
import org.eclipse.ocl.pivot.util.Visitor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.ocl.pivot.internal.MapTypeImpl#getKeyType <em>Key Type</em>}</li>
 *   <li>{@link org.eclipse.ocl.pivot.internal.MapTypeImpl#isKeysAreNullFree <em>Keys Are Null Free</em>}</li>
 *   <li>{@link org.eclipse.ocl.pivot.internal.MapTypeImpl#getValueType <em>Value Type</em>}</li>
 *   <li>{@link org.eclipse.ocl.pivot.internal.MapTypeImpl#isValuesAreNullFree <em>Values Are Null Free</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MapTypeImpl extends IterableTypeImpl implements MapType
{
	/**
	 * The default value of the '{@link #isKeysAreNullFree() <em>Keys Are Null Free</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * @since 1.6
	 * <!-- end-user-doc -->
	 * @see #isKeysAreNullFree()
	 * @generated
	 * @ordered
	 */
	protected static final boolean KEYS_ARE_NULL_FREE_EDEFAULT = true;
	/**
	 * The flag representing the value of the '{@link #isKeysAreNullFree() <em>Keys Are Null Free</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * @since 1.6
	 * <!-- end-user-doc -->
	 * @see #isKeysAreNullFree()
	 * @generated
	 * @ordered
	 */
	protected static final int KEYS_ARE_NULL_FREE_EFLAG = 1 << 12;
	/**
	 * The default value of the '{@link #isValuesAreNullFree() <em>Values Are Null Free</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * @since 1.6
	 * <!-- end-user-doc -->
	 * @see #isValuesAreNullFree()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VALUES_ARE_NULL_FREE_EDEFAULT = true;
	/**
	 * The flag representing the value of the '{@link #isValuesAreNullFree() <em>Values Are Null Free</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * @since 1.6
	 * <!-- end-user-doc -->
	 * @see #isValuesAreNullFree()
	 * @generated
	 * @ordered
	 */
	protected static final int VALUES_ARE_NULL_FREE_EFLAG = 1 << 13;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MapTypeImpl()
	{
		super();
		eFlags |= KEYS_ARE_NULL_FREE_EFLAG;
		eFlags |= VALUES_ARE_NULL_FREE_EFLAG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return PivotPackage.Literals.MAP_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case PivotPackage.MAP_TYPE__ANNOTATING_COMMENTS:
				return getAnnotatingComments();
			case PivotPackage.MAP_TYPE__OWNED_ANNOTATIONS:
				return getOwnedAnnotations();
			case PivotPackage.MAP_TYPE__OWNED_COMMENTS:
				return getOwnedComments();
			case PivotPackage.MAP_TYPE__OWNED_EXTENSIONS:
				return getOwnedExtensions();
			case PivotPackage.MAP_TYPE__NAME:
				return getName();
			case PivotPackage.MAP_TYPE__OWNED_CONSTRAINTS:
				return getOwnedConstraints();
			case PivotPackage.MAP_TYPE__OWNED_BINDINGS:
				return getOwnedBindings();
			case PivotPackage.MAP_TYPE__OWNED_SIGNATURE:
				return getOwnedSignature();
			case PivotPackage.MAP_TYPE__UNSPECIALIZED_ELEMENT:
				return getUnspecializedElement();
			case PivotPackage.MAP_TYPE__EXTENDERS:
				return getExtenders();
			case PivotPackage.MAP_TYPE__INSTANCE_CLASS_NAME:
				return getInstanceClassName();
			case PivotPackage.MAP_TYPE__IS_ABSTRACT:
				return isIsAbstract();
			case PivotPackage.MAP_TYPE__IS_ACTIVE:
				return isIsActive();
			case PivotPackage.MAP_TYPE__IS_INTERFACE:
				return isIsInterface();
			case PivotPackage.MAP_TYPE__OWNED_BEHAVIORS:
				return getOwnedBehaviors();
			case PivotPackage.MAP_TYPE__OWNED_INVARIANTS:
				return getOwnedInvariants();
			case PivotPackage.MAP_TYPE__OWNED_OPERATIONS:
				return getOwnedOperations();
			case PivotPackage.MAP_TYPE__OWNED_PROPERTIES:
				return getOwnedProperties();
			case PivotPackage.MAP_TYPE__OWNING_PACKAGE:
				return getOwningPackage();
			case PivotPackage.MAP_TYPE__SUPER_CLASSES:
				return getSuperClasses();
			case PivotPackage.MAP_TYPE__BEHAVIORAL_CLASS:
				if (resolve) return getBehavioralClass();
				return basicGetBehavioralClass();
			case PivotPackage.MAP_TYPE__IS_SERIALIZABLE:
				return isIsSerializable();
			case PivotPackage.MAP_TYPE__VALUE:
				return getValue();
			case PivotPackage.MAP_TYPE__KEY_TYPE:
				return getKeyType();
			case PivotPackage.MAP_TYPE__KEYS_ARE_NULL_FREE:
				return isKeysAreNullFree();
			case PivotPackage.MAP_TYPE__VALUE_TYPE:
				return getValueType();
			case PivotPackage.MAP_TYPE__VALUES_ARE_NULL_FREE:
				return isValuesAreNullFree();
		}
		return eDynamicGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case PivotPackage.MAP_TYPE__ANNOTATING_COMMENTS:
				getAnnotatingComments().clear();
				getAnnotatingComments().addAll((Collection<? extends Comment>)newValue);
				return;
			case PivotPackage.MAP_TYPE__OWNED_ANNOTATIONS:
				getOwnedAnnotations().clear();
				getOwnedAnnotations().addAll((Collection<? extends Element>)newValue);
				return;
			case PivotPackage.MAP_TYPE__OWNED_COMMENTS:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection<? extends Comment>)newValue);
				return;
			case PivotPackage.MAP_TYPE__OWNED_EXTENSIONS:
				getOwnedExtensions().clear();
				getOwnedExtensions().addAll((Collection<? extends ElementExtension>)newValue);
				return;
			case PivotPackage.MAP_TYPE__NAME:
				setName((String)newValue);
				return;
			case PivotPackage.MAP_TYPE__OWNED_CONSTRAINTS:
				getOwnedConstraints().clear();
				getOwnedConstraints().addAll((Collection<? extends Constraint>)newValue);
				return;
			case PivotPackage.MAP_TYPE__OWNED_BINDINGS:
				getOwnedBindings().clear();
				getOwnedBindings().addAll((Collection<? extends TemplateBinding>)newValue);
				return;
			case PivotPackage.MAP_TYPE__OWNED_SIGNATURE:
				setOwnedSignature((TemplateSignature)newValue);
				return;
			case PivotPackage.MAP_TYPE__UNSPECIALIZED_ELEMENT:
				setUnspecializedElement((TemplateableElement)newValue);
				return;
			case PivotPackage.MAP_TYPE__EXTENDERS:
				getExtenders().clear();
				getExtenders().addAll((Collection<? extends StereotypeExtender>)newValue);
				return;
			case PivotPackage.MAP_TYPE__INSTANCE_CLASS_NAME:
				setInstanceClassName((String)newValue);
				return;
			case PivotPackage.MAP_TYPE__IS_ABSTRACT:
				setIsAbstract((Boolean)newValue);
				return;
			case PivotPackage.MAP_TYPE__IS_ACTIVE:
				setIsActive((Boolean)newValue);
				return;
			case PivotPackage.MAP_TYPE__IS_INTERFACE:
				setIsInterface((Boolean)newValue);
				return;
			case PivotPackage.MAP_TYPE__OWNED_BEHAVIORS:
				getOwnedBehaviors().clear();
				getOwnedBehaviors().addAll((Collection<? extends Behavior>)newValue);
				return;
			case PivotPackage.MAP_TYPE__OWNED_INVARIANTS:
				getOwnedInvariants().clear();
				getOwnedInvariants().addAll((Collection<? extends Constraint>)newValue);
				return;
			case PivotPackage.MAP_TYPE__OWNED_OPERATIONS:
				getOwnedOperations().clear();
				getOwnedOperations().addAll((Collection<? extends Operation>)newValue);
				return;
			case PivotPackage.MAP_TYPE__OWNED_PROPERTIES:
				getOwnedProperties().clear();
				getOwnedProperties().addAll((Collection<? extends Property>)newValue);
				return;
			case PivotPackage.MAP_TYPE__OWNING_PACKAGE:
				setOwningPackage((org.eclipse.ocl.pivot.Package)newValue);
				return;
			case PivotPackage.MAP_TYPE__SUPER_CLASSES:
				getSuperClasses().clear();
				getSuperClasses().addAll((Collection<? extends org.eclipse.ocl.pivot.Class>)newValue);
				return;
			case PivotPackage.MAP_TYPE__BEHAVIORAL_CLASS:
				setBehavioralClass((org.eclipse.ocl.pivot.Class)newValue);
				return;
			case PivotPackage.MAP_TYPE__IS_SERIALIZABLE:
				setIsSerializable((Boolean)newValue);
				return;
			case PivotPackage.MAP_TYPE__KEY_TYPE:
				setKeyType((Type)newValue);
				return;
			case PivotPackage.MAP_TYPE__KEYS_ARE_NULL_FREE:
				setKeysAreNullFree((Boolean)newValue);
				return;
			case PivotPackage.MAP_TYPE__VALUE_TYPE:
				setValueType((Type)newValue);
				return;
			case PivotPackage.MAP_TYPE__VALUES_ARE_NULL_FREE:
				setValuesAreNullFree((Boolean)newValue);
				return;
		}
		eDynamicSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case PivotPackage.MAP_TYPE__ANNOTATING_COMMENTS:
				getAnnotatingComments().clear();
				return;
			case PivotPackage.MAP_TYPE__OWNED_ANNOTATIONS:
				getOwnedAnnotations().clear();
				return;
			case PivotPackage.MAP_TYPE__OWNED_COMMENTS:
				getOwnedComments().clear();
				return;
			case PivotPackage.MAP_TYPE__OWNED_EXTENSIONS:
				getOwnedExtensions().clear();
				return;
			case PivotPackage.MAP_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PivotPackage.MAP_TYPE__OWNED_CONSTRAINTS:
				getOwnedConstraints().clear();
				return;
			case PivotPackage.MAP_TYPE__OWNED_BINDINGS:
				getOwnedBindings().clear();
				return;
			case PivotPackage.MAP_TYPE__OWNED_SIGNATURE:
				setOwnedSignature((TemplateSignature)null);
				return;
			case PivotPackage.MAP_TYPE__UNSPECIALIZED_ELEMENT:
				setUnspecializedElement((TemplateableElement)null);
				return;
			case PivotPackage.MAP_TYPE__EXTENDERS:
				getExtenders().clear();
				return;
			case PivotPackage.MAP_TYPE__INSTANCE_CLASS_NAME:
				setInstanceClassName(INSTANCE_CLASS_NAME_EDEFAULT);
				return;
			case PivotPackage.MAP_TYPE__IS_ABSTRACT:
				setIsAbstract(IS_ABSTRACT_EDEFAULT);
				return;
			case PivotPackage.MAP_TYPE__IS_ACTIVE:
				setIsActive(IS_ACTIVE_EDEFAULT);
				return;
			case PivotPackage.MAP_TYPE__IS_INTERFACE:
				setIsInterface(IS_INTERFACE_EDEFAULT);
				return;
			case PivotPackage.MAP_TYPE__OWNED_BEHAVIORS:
				getOwnedBehaviors().clear();
				return;
			case PivotPackage.MAP_TYPE__OWNED_INVARIANTS:
				getOwnedInvariants().clear();
				return;
			case PivotPackage.MAP_TYPE__OWNED_OPERATIONS:
				getOwnedOperations().clear();
				return;
			case PivotPackage.MAP_TYPE__OWNED_PROPERTIES:
				getOwnedProperties().clear();
				return;
			case PivotPackage.MAP_TYPE__OWNING_PACKAGE:
				setOwningPackage((org.eclipse.ocl.pivot.Package)null);
				return;
			case PivotPackage.MAP_TYPE__SUPER_CLASSES:
				getSuperClasses().clear();
				return;
			case PivotPackage.MAP_TYPE__BEHAVIORAL_CLASS:
				setBehavioralClass((org.eclipse.ocl.pivot.Class)null);
				return;
			case PivotPackage.MAP_TYPE__IS_SERIALIZABLE:
				setIsSerializable(IS_SERIALIZABLE_EDEFAULT);
				return;
			case PivotPackage.MAP_TYPE__KEY_TYPE:
				setKeyType((Type)null);
				return;
			case PivotPackage.MAP_TYPE__KEYS_ARE_NULL_FREE:
				setKeysAreNullFree(KEYS_ARE_NULL_FREE_EDEFAULT);
				return;
			case PivotPackage.MAP_TYPE__VALUE_TYPE:
				setValueType((Type)null);
				return;
			case PivotPackage.MAP_TYPE__VALUES_ARE_NULL_FREE:
				setValuesAreNullFree(VALUES_ARE_NULL_FREE_EDEFAULT);
				return;
		}
		eDynamicUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case PivotPackage.MAP_TYPE__ANNOTATING_COMMENTS:
				return annotatingComments != null && !annotatingComments.isEmpty();
			case PivotPackage.MAP_TYPE__OWNED_ANNOTATIONS:
				return ownedAnnotations != null && !ownedAnnotations.isEmpty();
			case PivotPackage.MAP_TYPE__OWNED_COMMENTS:
				return ownedComments != null && !ownedComments.isEmpty();
			case PivotPackage.MAP_TYPE__OWNED_EXTENSIONS:
				return ownedExtensions != null && !ownedExtensions.isEmpty();
			case PivotPackage.MAP_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PivotPackage.MAP_TYPE__OWNED_CONSTRAINTS:
				return ownedConstraints != null && !ownedConstraints.isEmpty();
			case PivotPackage.MAP_TYPE__OWNED_BINDINGS:
				return ownedBindings != null && !ownedBindings.isEmpty();
			case PivotPackage.MAP_TYPE__OWNED_SIGNATURE:
				return ownedSignature != null;
			case PivotPackage.MAP_TYPE__UNSPECIALIZED_ELEMENT:
				return unspecializedElement != null;
			case PivotPackage.MAP_TYPE__EXTENDERS:
				return extenders != null && !extenders.isEmpty();
			case PivotPackage.MAP_TYPE__INSTANCE_CLASS_NAME:
				return INSTANCE_CLASS_NAME_EDEFAULT == null ? instanceClassName != null : !INSTANCE_CLASS_NAME_EDEFAULT.equals(instanceClassName);
			case PivotPackage.MAP_TYPE__IS_ABSTRACT:
				return ((eFlags & IS_ABSTRACT_EFLAG) != 0) != IS_ABSTRACT_EDEFAULT;
			case PivotPackage.MAP_TYPE__IS_ACTIVE:
				return ((eFlags & IS_ACTIVE_EFLAG) != 0) != IS_ACTIVE_EDEFAULT;
			case PivotPackage.MAP_TYPE__IS_INTERFACE:
				return ((eFlags & IS_INTERFACE_EFLAG) != 0) != IS_INTERFACE_EDEFAULT;
			case PivotPackage.MAP_TYPE__OWNED_BEHAVIORS:
				return ownedBehaviors != null && !ownedBehaviors.isEmpty();
			case PivotPackage.MAP_TYPE__OWNED_INVARIANTS:
				return ownedInvariants != null && !ownedInvariants.isEmpty();
			case PivotPackage.MAP_TYPE__OWNED_OPERATIONS:
				return ownedOperations != null && !ownedOperations.isEmpty();
			case PivotPackage.MAP_TYPE__OWNED_PROPERTIES:
				return ownedProperties != null && !ownedProperties.isEmpty();
			case PivotPackage.MAP_TYPE__OWNING_PACKAGE:
				return getOwningPackage() != null;
			case PivotPackage.MAP_TYPE__SUPER_CLASSES:
				return superClasses != null && !superClasses.isEmpty();
			case PivotPackage.MAP_TYPE__BEHAVIORAL_CLASS:
				return behavioralClass != null;
			case PivotPackage.MAP_TYPE__IS_SERIALIZABLE:
				return ((eFlags & IS_SERIALIZABLE_EFLAG) != 0) != IS_SERIALIZABLE_EDEFAULT;
			case PivotPackage.MAP_TYPE__VALUE:
				return VALUE_EDEFAULT == null ? getValue() != null : !VALUE_EDEFAULT.equals(getValue());
			case PivotPackage.MAP_TYPE__KEY_TYPE:
				return getKeyType() != null;
			case PivotPackage.MAP_TYPE__KEYS_ARE_NULL_FREE:
				return ((eFlags & KEYS_ARE_NULL_FREE_EFLAG) != 0) != KEYS_ARE_NULL_FREE_EDEFAULT;
			case PivotPackage.MAP_TYPE__VALUE_TYPE:
				return getValueType() != null;
			case PivotPackage.MAP_TYPE__VALUES_ARE_NULL_FREE:
				return ((eFlags & VALUES_ARE_NULL_FREE_EFLAG) != 0) != VALUES_ARE_NULL_FREE_EDEFAULT;
		}
		return eDynamicIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (keysAreNullFree: "); //$NON-NLS-1$
		result.append((eFlags & KEYS_ARE_NULL_FREE_EFLAG) != 0);
		result.append(", valuesAreNullFree: "); //$NON-NLS-1$
		result.append((eFlags & VALUES_ARE_NULL_FREE_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 * @generated
	 */
	@Override
	public <R> R accept(@NonNull Visitor<R> visitor) {
		return visitor.visitMapType(this);
	}

	@Deprecated /* @deprecated no longer duplicates template binding */
	protected Type keyType = null;
	@Deprecated /* @deprecated no longer duplicates template binding */
	protected Type valueType = null;

	@Deprecated /* @deprecated no longer duplicates template binding */
	public Type basicGetKeyType() {
		return getKeyType();
	}

	@Deprecated /* @deprecated no longer duplicates template binding */
	public Type basicGetValueType() {
		return getValueType();
	}

	@Override
	public @NonNull TypeId computeId() {
		if (getUnspecializedElement() == null) {
			return TypeId.MAP;
		}
		else {
			return TypeId.MAP.getSpecializedId(getKeyType().getTypeId(), getValueType().getTypeId());
		}
	}

	@Override
	public Type getKeyType() {
		TemplateSignature templateSignature = getOwnedSignature();
		if (templateSignature != null) {
			List<TemplateParameter> templateParameters = templateSignature.getOwnedParameters();
			return templateParameters.get(0);
		}
		else {
			List<TemplateBinding> templateBindings = getOwnedBindings();
			List<TemplateParameterSubstitution> templateParameterSubstitutions = templateBindings.get(0).getOwnedSubstitutions();
			return templateParameterSubstitutions.get(0).getActual();
		}
	}

	@Override
	public Type getValueType() {
		TemplateSignature templateSignature = getOwnedSignature();
		if (templateSignature != null) {
			List<TemplateParameter> templateParameters = templateSignature.getOwnedParameters();
			return templateParameters.get(1);
		}
		else {
			List<TemplateBinding> templateBindings = getOwnedBindings();
			List<TemplateParameterSubstitution> templateParameterSubstitutions = templateBindings.get(0).getOwnedSubstitutions();
			return templateParameterSubstitutions.get(1).getActual();
		}
	}

	@Override
	public void setKeyType(Type newKeyType) {				// FIXME delete me once compatibility not needed
		System.err.println(eClass().getName() + ".setKeyType() is ignored");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isKeysAreNullFree()
	{
		return (eFlags & KEYS_ARE_NULL_FREE_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKeysAreNullFree(boolean newKeysAreNullFree)
	{
		boolean oldKeysAreNullFree = (eFlags & KEYS_ARE_NULL_FREE_EFLAG) != 0;
		if (newKeysAreNullFree) eFlags |= KEYS_ARE_NULL_FREE_EFLAG; else eFlags &= ~KEYS_ARE_NULL_FREE_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PivotPackage.MAP_TYPE__KEYS_ARE_NULL_FREE, oldKeysAreNullFree, newKeysAreNullFree));
	}

	@Override
	public void setValueType(Type newValueType) {			// FIXME delete me once compatibility not needed
		System.err.println(eClass().getName() + ".setValueType() is ignored");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isValuesAreNullFree()
	{
		return (eFlags & VALUES_ARE_NULL_FREE_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValuesAreNullFree(boolean newValuesAreNullFree)
	{
		boolean oldValuesAreNullFree = (eFlags & VALUES_ARE_NULL_FREE_EFLAG) != 0;
		if (newValuesAreNullFree) eFlags |= VALUES_ARE_NULL_FREE_EFLAG; else eFlags &= ~VALUES_ARE_NULL_FREE_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PivotPackage.MAP_TYPE__VALUES_ARE_NULL_FREE, oldValuesAreNullFree, newValuesAreNullFree));
	}
} //MapTypeImpl
