/**
 * <copyright>
 * 
 * Copyright (c) 2012 E.D.Willink and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 * 
 * </copyright>
 *************************************************************************
 * This code is 100% auto-generated
 * from: pivot
 * using: org.eclipse.ocl.examples.codegen.tables.model2tables.mtl
 *
 * Do not edit it.
 */
package org.eclipse.ocl.examples.pivot.bodies;

import org.eclipse.ocl.examples.domain.elements.DomainStandardLibrary;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.InvalidValueException;
import org.eclipse.ocl.examples.domain.library.AbstractUnaryOperation;
import org.eclipse.ocl.examples.domain.library.LibraryBinaryOperation;
import org.eclipse.ocl.examples.domain.library.LibraryProperty;
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.domain.values.ValueFactory;
import org.eclipse.ocl.examples.library.executor.ExecutorOperation;
import org.eclipse.ocl.examples.library.executor.ExecutorProperty;
import org.eclipse.ocl.examples.library.executor.ExecutorType;
import org.eclipse.ocl.examples.library.oclstdlib.OCLstdlibTables;
import org.eclipse.ocl.examples.pivot.PivotTables;

/**
 * CollectionLiteralExpBodies provides the Java implementation bodies of OCL-defined CollectionLiteralExp operations and properties.
 */
@SuppressWarnings("nls")
public class CollectionLiteralExpBodies
{

	/** 
	 * Implementation of the CollectionLiteralExp 'BagKindIsBag' invariant.
	 */
	public static class _invariant_BagKindIsBag extends AbstractUnaryOperation
	{
		public static _invariant_BagKindIsBag INSTANCE = new _invariant_BagKindIsBag();
	
		/*
		kind = CollectionKind::Bag implies type.oclIsKindOf(BagType)
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Pivot_ecore__pivot__CollectionKind = PivotTables.Types._CollectionKind;
			final ExecutorProperty P_CollectionLiteralExp_kind = PivotTables.Properties._CollectionLiteralExp__kind;
			final LibraryProperty IP_CollectionLiteralExp_kind = P_CollectionLiteralExp_kind.getImplementation();
			final Value A_symbol_388 = valueFactory.createEnumerationLiteralValue(PivotTables.EnumerationLiterals._CollectionKind__Bag);
			final ExecutorOperation O_OclAny_oclIsKindOf = OCLstdlibTables.Operations._OclAny__oclIsKindOf;
			final ExecutorType T_Pivot_ecore__pivot__Type = PivotTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_Pivot_ecore__pivot__BagType_ = valueFactory.createTypeValue(PivotTables.Types._BagType);
			
			Value leftA_symbol_389;
			try {
				
				Value A_symbol_390 = IP_CollectionLiteralExp_kind.evaluate(evaluator, T_Pivot_ecore__pivot__CollectionKind, self, P_CollectionLiteralExp_kind);
				
				
				DomainType static_A_symbol_391 = valueFactory.typeOf(A_symbol_390, A_symbol_388);
				LibraryBinaryOperation dynamic_A_symbol_391 = (LibraryBinaryOperation)static_A_symbol_391.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_391 = dynamic_A_symbol_391.evaluate(evaluator, T_Boolean, A_symbol_390, A_symbol_388);
				leftA_symbol_389 = A_symbol_391;
			} catch (InvalidValueException e) {
				leftA_symbol_389 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_391 = leftA_symbol_389;
			Value rightA_symbol_389;
			try {
				
				Value A_symbol_392 = IP_TypedElement_type.evaluate(evaluator, T_Pivot_ecore__pivot__Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_393 = valueFactory.typeOf(A_symbol_392);
				LibraryBinaryOperation dynamic_A_symbol_393 = (LibraryBinaryOperation)static_A_symbol_393.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
				Value A_symbol_393 = dynamic_A_symbol_393.evaluate(evaluator, T_Boolean, A_symbol_392, T_ClassClassifier_Pivot_ecore__pivot__BagType_);
				rightA_symbol_389 = A_symbol_393;
			} catch (InvalidValueException e) {
				rightA_symbol_389 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_393 = rightA_symbol_389;
			DomainType static_A_symbol_389 = valueFactory.typeOf(A_symbol_391);
			LibraryBinaryOperation dynamic_A_symbol_389 = (LibraryBinaryOperation)static_A_symbol_389.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_389 = dynamic_A_symbol_389.evaluate(evaluator, T_Boolean, A_symbol_391, A_symbol_393);
			return A_symbol_389;
		}
	}

	/** 
	 * Implementation of the CollectionLiteralExp 'CollectionKindIsConcrete' invariant.
	 */
	public static class _invariant_CollectionKindIsConcrete extends AbstractUnaryOperation
	{
		public static _invariant_CollectionKindIsConcrete INSTANCE = new _invariant_CollectionKindIsConcrete();
	
		/*
		kind <> CollectionKind::Collection
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_OclAny__lt__gt_ = OCLstdlibTables.Operations._OclAny___lt__gt_;
			final ExecutorType T_Pivot_ecore__pivot__CollectionKind = PivotTables.Types._CollectionKind;
			final ExecutorProperty P_CollectionLiteralExp_kind = PivotTables.Properties._CollectionLiteralExp__kind;
			final LibraryProperty IP_CollectionLiteralExp_kind = P_CollectionLiteralExp_kind.getImplementation();
			final Value A_symbol_394 = valueFactory.createEnumerationLiteralValue(PivotTables.EnumerationLiterals._CollectionKind__Collection);
			
			
			Value A_symbol_395 = IP_CollectionLiteralExp_kind.evaluate(evaluator, T_Pivot_ecore__pivot__CollectionKind, self, P_CollectionLiteralExp_kind);
			
			
			DomainType static_A_symbol_396 = valueFactory.typeOf(A_symbol_395, A_symbol_394);
			LibraryBinaryOperation dynamic_A_symbol_396 = (LibraryBinaryOperation)static_A_symbol_396.lookupImplementation(standardLibrary, O_OclAny__lt__gt_);
			Value A_symbol_396 = dynamic_A_symbol_396.evaluate(evaluator, T_Boolean, A_symbol_395, A_symbol_394);
			return A_symbol_396;
		}
	}

	/** 
	 * Implementation of the CollectionLiteralExp 'OrderedSetKindIsOrderedSet' invariant.
	 */
	public static class _invariant_OrderedSetKindIsOrderedSet extends AbstractUnaryOperation
	{
		public static _invariant_OrderedSetKindIsOrderedSet INSTANCE = new _invariant_OrderedSetKindIsOrderedSet();
	
		/*
		kind = CollectionKind::OrderedSet implies
	type.oclIsKindOf(OrderedSetType)
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Pivot_ecore__pivot__CollectionKind = PivotTables.Types._CollectionKind;
			final ExecutorProperty P_CollectionLiteralExp_kind = PivotTables.Properties._CollectionLiteralExp__kind;
			final LibraryProperty IP_CollectionLiteralExp_kind = P_CollectionLiteralExp_kind.getImplementation();
			final Value A_symbol_397 = valueFactory.createEnumerationLiteralValue(PivotTables.EnumerationLiterals._CollectionKind__OrderedSet);
			final ExecutorOperation O_OclAny_oclIsKindOf = OCLstdlibTables.Operations._OclAny__oclIsKindOf;
			final ExecutorType T_Pivot_ecore__pivot__Type = PivotTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_Pivot_ecore__pivot__OrderedSetType_ = valueFactory.createTypeValue(PivotTables.Types._OrderedSetType);
			
			Value leftA_symbol_398;
			try {
				
				Value A_symbol_399 = IP_CollectionLiteralExp_kind.evaluate(evaluator, T_Pivot_ecore__pivot__CollectionKind, self, P_CollectionLiteralExp_kind);
				
				
				DomainType static_A_symbol_400 = valueFactory.typeOf(A_symbol_399, A_symbol_397);
				LibraryBinaryOperation dynamic_A_symbol_400 = (LibraryBinaryOperation)static_A_symbol_400.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_400 = dynamic_A_symbol_400.evaluate(evaluator, T_Boolean, A_symbol_399, A_symbol_397);
				leftA_symbol_398 = A_symbol_400;
			} catch (InvalidValueException e) {
				leftA_symbol_398 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_400 = leftA_symbol_398;
			Value rightA_symbol_398;
			try {
				
				Value A_symbol_401 = IP_TypedElement_type.evaluate(evaluator, T_Pivot_ecore__pivot__Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_402 = valueFactory.typeOf(A_symbol_401);
				LibraryBinaryOperation dynamic_A_symbol_402 = (LibraryBinaryOperation)static_A_symbol_402.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
				Value A_symbol_402 = dynamic_A_symbol_402.evaluate(evaluator, T_Boolean, A_symbol_401, T_ClassClassifier_Pivot_ecore__pivot__OrderedSetType_);
				rightA_symbol_398 = A_symbol_402;
			} catch (InvalidValueException e) {
				rightA_symbol_398 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_402 = rightA_symbol_398;
			DomainType static_A_symbol_398 = valueFactory.typeOf(A_symbol_400);
			LibraryBinaryOperation dynamic_A_symbol_398 = (LibraryBinaryOperation)static_A_symbol_398.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_398 = dynamic_A_symbol_398.evaluate(evaluator, T_Boolean, A_symbol_400, A_symbol_402);
			return A_symbol_398;
		}
	}

	/** 
	 * Implementation of the CollectionLiteralExp 'SequenceKindIsSequence' invariant.
	 */
	public static class _invariant_SequenceKindIsSequence extends AbstractUnaryOperation
	{
		public static _invariant_SequenceKindIsSequence INSTANCE = new _invariant_SequenceKindIsSequence();
	
		/*
		kind = CollectionKind::Sequence implies
	type.oclIsKindOf(SequenceType)
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Pivot_ecore__pivot__CollectionKind = PivotTables.Types._CollectionKind;
			final ExecutorProperty P_CollectionLiteralExp_kind = PivotTables.Properties._CollectionLiteralExp__kind;
			final LibraryProperty IP_CollectionLiteralExp_kind = P_CollectionLiteralExp_kind.getImplementation();
			final Value A_symbol_403 = valueFactory.createEnumerationLiteralValue(PivotTables.EnumerationLiterals._CollectionKind__Sequence);
			final ExecutorOperation O_OclAny_oclIsKindOf = OCLstdlibTables.Operations._OclAny__oclIsKindOf;
			final ExecutorType T_Pivot_ecore__pivot__Type = PivotTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_Pivot_ecore__pivot__SequenceType_ = valueFactory.createTypeValue(PivotTables.Types._SequenceType);
			
			Value leftA_symbol_404;
			try {
				
				Value A_symbol_405 = IP_CollectionLiteralExp_kind.evaluate(evaluator, T_Pivot_ecore__pivot__CollectionKind, self, P_CollectionLiteralExp_kind);
				
				
				DomainType static_A_symbol_406 = valueFactory.typeOf(A_symbol_405, A_symbol_403);
				LibraryBinaryOperation dynamic_A_symbol_406 = (LibraryBinaryOperation)static_A_symbol_406.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_406 = dynamic_A_symbol_406.evaluate(evaluator, T_Boolean, A_symbol_405, A_symbol_403);
				leftA_symbol_404 = A_symbol_406;
			} catch (InvalidValueException e) {
				leftA_symbol_404 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_406 = leftA_symbol_404;
			Value rightA_symbol_404;
			try {
				
				Value A_symbol_407 = IP_TypedElement_type.evaluate(evaluator, T_Pivot_ecore__pivot__Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_408 = valueFactory.typeOf(A_symbol_407);
				LibraryBinaryOperation dynamic_A_symbol_408 = (LibraryBinaryOperation)static_A_symbol_408.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
				Value A_symbol_408 = dynamic_A_symbol_408.evaluate(evaluator, T_Boolean, A_symbol_407, T_ClassClassifier_Pivot_ecore__pivot__SequenceType_);
				rightA_symbol_404 = A_symbol_408;
			} catch (InvalidValueException e) {
				rightA_symbol_404 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_408 = rightA_symbol_404;
			DomainType static_A_symbol_404 = valueFactory.typeOf(A_symbol_406);
			LibraryBinaryOperation dynamic_A_symbol_404 = (LibraryBinaryOperation)static_A_symbol_404.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_404 = dynamic_A_symbol_404.evaluate(evaluator, T_Boolean, A_symbol_406, A_symbol_408);
			return A_symbol_404;
		}
	}

	/** 
	 * Implementation of the CollectionLiteralExp 'SetKindIsSet' invariant.
	 */
	public static class _invariant_SetKindIsSet extends AbstractUnaryOperation
	{
		public static _invariant_SetKindIsSet INSTANCE = new _invariant_SetKindIsSet();
	
		/*
		kind = CollectionKind::Set implies type.oclIsKindOf(SetType)
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Pivot_ecore__pivot__CollectionKind = PivotTables.Types._CollectionKind;
			final ExecutorProperty P_CollectionLiteralExp_kind = PivotTables.Properties._CollectionLiteralExp__kind;
			final LibraryProperty IP_CollectionLiteralExp_kind = P_CollectionLiteralExp_kind.getImplementation();
			final Value A_symbol_409 = valueFactory.createEnumerationLiteralValue(PivotTables.EnumerationLiterals._CollectionKind__Set);
			final ExecutorOperation O_OclAny_oclIsKindOf = OCLstdlibTables.Operations._OclAny__oclIsKindOf;
			final ExecutorType T_Pivot_ecore__pivot__Type = PivotTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_Pivot_ecore__pivot__SetType_ = valueFactory.createTypeValue(PivotTables.Types._SetType);
			
			Value leftA_symbol_410;
			try {
				
				Value A_symbol_411 = IP_CollectionLiteralExp_kind.evaluate(evaluator, T_Pivot_ecore__pivot__CollectionKind, self, P_CollectionLiteralExp_kind);
				
				
				DomainType static_A_symbol_412 = valueFactory.typeOf(A_symbol_411, A_symbol_409);
				LibraryBinaryOperation dynamic_A_symbol_412 = (LibraryBinaryOperation)static_A_symbol_412.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_412 = dynamic_A_symbol_412.evaluate(evaluator, T_Boolean, A_symbol_411, A_symbol_409);
				leftA_symbol_410 = A_symbol_412;
			} catch (InvalidValueException e) {
				leftA_symbol_410 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_412 = leftA_symbol_410;
			Value rightA_symbol_410;
			try {
				
				Value A_symbol_413 = IP_TypedElement_type.evaluate(evaluator, T_Pivot_ecore__pivot__Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_414 = valueFactory.typeOf(A_symbol_413);
				LibraryBinaryOperation dynamic_A_symbol_414 = (LibraryBinaryOperation)static_A_symbol_414.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
				Value A_symbol_414 = dynamic_A_symbol_414.evaluate(evaluator, T_Boolean, A_symbol_413, T_ClassClassifier_Pivot_ecore__pivot__SetType_);
				rightA_symbol_410 = A_symbol_414;
			} catch (InvalidValueException e) {
				rightA_symbol_410 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_414 = rightA_symbol_410;
			DomainType static_A_symbol_410 = valueFactory.typeOf(A_symbol_412);
			LibraryBinaryOperation dynamic_A_symbol_410 = (LibraryBinaryOperation)static_A_symbol_410.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_410 = dynamic_A_symbol_410.evaluate(evaluator, T_Boolean, A_symbol_412, A_symbol_414);
			return A_symbol_410;
		}
	}


}

