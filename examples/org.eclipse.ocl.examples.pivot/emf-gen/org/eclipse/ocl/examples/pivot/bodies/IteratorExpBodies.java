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

import org.eclipse.ocl.examples.domain.elements.DomainCollectionType;
import org.eclipse.ocl.examples.domain.elements.DomainStandardLibrary;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.evaluation.DomainEvaluator;
import org.eclipse.ocl.examples.domain.evaluation.InvalidValueException;
import org.eclipse.ocl.examples.domain.library.AbstractBinaryOperation;
import org.eclipse.ocl.examples.domain.library.AbstractUnaryOperation;
import org.eclipse.ocl.examples.domain.library.LibraryBinaryOperation;
import org.eclipse.ocl.examples.domain.library.LibraryIteration;
import org.eclipse.ocl.examples.domain.library.LibraryProperty;
import org.eclipse.ocl.examples.domain.library.LibraryUnaryOperation;
import org.eclipse.ocl.examples.domain.values.CollectionValue;
import org.eclipse.ocl.examples.domain.values.IntegerValue;
import org.eclipse.ocl.examples.domain.values.StringValue;
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.domain.values.ValueFactory;
import org.eclipse.ocl.examples.library.executor.ExecutorOperation;
import org.eclipse.ocl.examples.library.executor.ExecutorProperty;
import org.eclipse.ocl.examples.library.executor.ExecutorSingleIterationManager;
import org.eclipse.ocl.examples.library.executor.ExecutorType;
import org.eclipse.ocl.examples.library.oclstdlib.OCLstdlibTables;
import org.eclipse.ocl.examples.pivot.PivotTables;

/**
 * IteratorExpBodies provides the Java implementation bodies of OCL-defined IteratorExp operations and properties.
 */
@SuppressWarnings("nls")
public class IteratorExpBodies
{

	/** 
	 * Implementation of the IteratorExp 'AnyBodyTypeIsBoolean' invariant.
	 */
	public static class _invariant_AnyBodyTypeIsBoolean extends AbstractUnaryOperation
	{
		public static _invariant_AnyBodyTypeIsBoolean INSTANCE = new _invariant_AnyBodyTypeIsBoolean();
	
		/*
		name = 'any' implies body.type = 'Boolean'
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_any = valueFactory.stringValueOf("any");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_LoopExp_body = PivotTables.Properties._LoopExp__body;
			final LibraryProperty IP_LoopExp_body = P_LoopExp_body.getImplementation();
			final StringValue S_Boolean = valueFactory.stringValueOf("Boolean");
			
			Value leftA_symbol_82;
			try {
				
				Value A_symbol_83 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_84 = valueFactory.typeOf(A_symbol_83, S_any);
				LibraryBinaryOperation dynamic_A_symbol_84 = (LibraryBinaryOperation)static_A_symbol_84.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_84 = dynamic_A_symbol_84.evaluate(evaluator, T_Boolean, A_symbol_83, S_any);
				leftA_symbol_82 = A_symbol_84;
			} catch (InvalidValueException e) {
				leftA_symbol_82 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_84 = leftA_symbol_82;
			Value rightA_symbol_82;
			try {
				
				Value A_symbol_85 = IP_LoopExp_body.evaluate(evaluator, T_pivot__OCLExpression, self, P_LoopExp_body);
				
				Value A_symbol_86 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_85, P_TypedElement_type);
				
				DomainType static_A_symbol_87 = valueFactory.typeOf(A_symbol_86, S_Boolean);
				LibraryBinaryOperation dynamic_A_symbol_87 = (LibraryBinaryOperation)static_A_symbol_87.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_87 = dynamic_A_symbol_87.evaluate(evaluator, T_Boolean, A_symbol_86, S_Boolean);
				rightA_symbol_82 = A_symbol_87;
			} catch (InvalidValueException e) {
				rightA_symbol_82 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_87 = rightA_symbol_82;
			DomainType static_A_symbol_82 = valueFactory.typeOf(A_symbol_84);
			LibraryBinaryOperation dynamic_A_symbol_82 = (LibraryBinaryOperation)static_A_symbol_82.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_82 = dynamic_A_symbol_82.evaluate(evaluator, T_Boolean, A_symbol_84, A_symbol_87);
			return A_symbol_82;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'AnyHasOneIterator' invariant.
	 */
	public static class _invariant_AnyHasOneIterator extends AbstractUnaryOperation
	{
		public static _invariant_AnyHasOneIterator INSTANCE = new _invariant_AnyHasOneIterator();
	
		/*
		name = 'any' implies iterator->size() = 1
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_any = valueFactory.stringValueOf("any");
			final ExecutorOperation O_Real__eq_ = OCLstdlibTables.Operations._Real___eq_;
			final ExecutorType T_Integer = OCLstdlibTables.Types._Integer;
			final ExecutorOperation O_Collection_size = OCLstdlibTables.Operations._Collection__size;
			final ExecutorType T_pivot__Variable = PivotTables.Types._Variable;
			final DomainCollectionType T_OrderedSet_pivot__Variable_ = standardLibrary.getOrderedSetType(T_pivot__Variable);
			final ExecutorProperty P_LoopExp_iterator = PivotTables.Properties._LoopExp__iterator;
			final LibraryProperty IP_LoopExp_iterator = P_LoopExp_iterator.getImplementation();
			final IntegerValue I_1 = valueFactory.integerValueOf(1);
			
			Value leftA_symbol_88;
			try {
				
				Value A_symbol_89 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_90 = valueFactory.typeOf(A_symbol_89, S_any);
				LibraryBinaryOperation dynamic_A_symbol_90 = (LibraryBinaryOperation)static_A_symbol_90.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_90 = dynamic_A_symbol_90.evaluate(evaluator, T_Boolean, A_symbol_89, S_any);
				leftA_symbol_88 = A_symbol_90;
			} catch (InvalidValueException e) {
				leftA_symbol_88 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_90 = leftA_symbol_88;
			Value rightA_symbol_88;
			try {
				
				Value A_symbol_91 = IP_LoopExp_iterator.evaluate(evaluator, T_OrderedSet_pivot__Variable_, self, P_LoopExp_iterator);
				
				DomainType static_A_symbol_92 = valueFactory.typeOf(A_symbol_91);
				LibraryUnaryOperation dynamic_A_symbol_92 = (LibraryUnaryOperation)static_A_symbol_92.lookupImplementation(standardLibrary, O_Collection_size);
				Value A_symbol_92 = dynamic_A_symbol_92.evaluate(evaluator, T_Integer, A_symbol_91);
				DomainType static_A_symbol_93 = valueFactory.typeOf(A_symbol_92, I_1);
				LibraryBinaryOperation dynamic_A_symbol_93 = (LibraryBinaryOperation)static_A_symbol_93.lookupImplementation(standardLibrary, O_Real__eq_);
				Value A_symbol_93 = dynamic_A_symbol_93.evaluate(evaluator, T_Boolean, A_symbol_92, I_1);
				rightA_symbol_88 = A_symbol_93;
			} catch (InvalidValueException e) {
				rightA_symbol_88 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_93 = rightA_symbol_88;
			DomainType static_A_symbol_88 = valueFactory.typeOf(A_symbol_90);
			LibraryBinaryOperation dynamic_A_symbol_88 = (LibraryBinaryOperation)static_A_symbol_88.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_88 = dynamic_A_symbol_88.evaluate(evaluator, T_Boolean, A_symbol_90, A_symbol_93);
			return A_symbol_88;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'AnyTypeIsSourceElementType' invariant.
	 */
	public static class _invariant_AnyTypeIsSourceElementType extends AbstractUnaryOperation
	{
		public static _invariant_AnyTypeIsSourceElementType INSTANCE = new _invariant_AnyTypeIsSourceElementType();
	
		/*
		name = 'any' implies type = source.type.oclAsType(CollectionType).elementType
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_any = valueFactory.stringValueOf("any");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorProperty P_CollectionType_elementType = PivotTables.Properties._CollectionType__elementType;
			final LibraryProperty IP_CollectionType_elementType = P_CollectionType_elementType.getImplementation();
			final ExecutorType T_pivot__CollectionType = PivotTables.Types._CollectionType;
			final ExecutorOperation O_OclAny_oclAsType = OCLstdlibTables.Operations._OclAny__oclAsType;
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_CallExp_source = PivotTables.Properties._CallExp__source;
			final LibraryProperty IP_CallExp_source = P_CallExp_source.getImplementation();
			final Value T_ClassClassifier_pivot__CollectionType_ = valueFactory.createTypeValue(PivotTables.Types._CollectionType);
			
			Value leftA_symbol_94;
			try {
				
				Value A_symbol_95 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_96 = valueFactory.typeOf(A_symbol_95, S_any);
				LibraryBinaryOperation dynamic_A_symbol_96 = (LibraryBinaryOperation)static_A_symbol_96.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_96 = dynamic_A_symbol_96.evaluate(evaluator, T_Boolean, A_symbol_95, S_any);
				leftA_symbol_94 = A_symbol_96;
			} catch (InvalidValueException e) {
				leftA_symbol_94 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_96 = leftA_symbol_94;
			Value rightA_symbol_94;
			try {
				
				Value A_symbol_97 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				
				Value A_symbol_98 = IP_CallExp_source.evaluate(evaluator, T_pivot__OCLExpression, self, P_CallExp_source);
				
				Value A_symbol_99 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_98, P_TypedElement_type);
				
				DomainType static_A_symbol_100 = valueFactory.typeOf(A_symbol_99);
				LibraryBinaryOperation dynamic_A_symbol_100 = (LibraryBinaryOperation)static_A_symbol_100.lookupImplementation(standardLibrary, O_OclAny_oclAsType);
				Value A_symbol_100 = dynamic_A_symbol_100.evaluate(evaluator, T_pivot__CollectionType, A_symbol_99, T_ClassClassifier_pivot__CollectionType_);
				Value A_symbol_101 = IP_CollectionType_elementType.evaluate(evaluator, T_Type, A_symbol_100, P_CollectionType_elementType);
				
				DomainType static_A_symbol_102 = valueFactory.typeOf(A_symbol_97, A_symbol_101);
				LibraryBinaryOperation dynamic_A_symbol_102 = (LibraryBinaryOperation)static_A_symbol_102.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_102 = dynamic_A_symbol_102.evaluate(evaluator, T_Boolean, A_symbol_97, A_symbol_101);
				rightA_symbol_94 = A_symbol_102;
			} catch (InvalidValueException e) {
				rightA_symbol_94 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_102 = rightA_symbol_94;
			DomainType static_A_symbol_94 = valueFactory.typeOf(A_symbol_96);
			LibraryBinaryOperation dynamic_A_symbol_94 = (LibraryBinaryOperation)static_A_symbol_94.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_94 = dynamic_A_symbol_94.evaluate(evaluator, T_Boolean, A_symbol_96, A_symbol_102);
			return A_symbol_94;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'ClosureElementTypeIsSourceElementType' invariant.
	 */
	public static class _invariant_ClosureElementTypeIsSourceElementType extends AbstractUnaryOperation
	{
		public static _invariant_ClosureElementTypeIsSourceElementType INSTANCE = new _invariant_ClosureElementTypeIsSourceElementType();
	
		/*
		name = 'closure' implies
	type.oclAsType(CollectionType).elementType =
	source.type.oclAsType(CollectionType).elementType
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_closure = valueFactory.stringValueOf("closure");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_CollectionType_elementType = PivotTables.Properties._CollectionType__elementType;
			final LibraryProperty IP_CollectionType_elementType = P_CollectionType_elementType.getImplementation();
			final ExecutorType T_pivot__CollectionType = PivotTables.Types._CollectionType;
			final ExecutorOperation O_OclAny_oclAsType = OCLstdlibTables.Operations._OclAny__oclAsType;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_pivot__CollectionType_ = valueFactory.createTypeValue(PivotTables.Types._CollectionType);
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_CallExp_source = PivotTables.Properties._CallExp__source;
			final LibraryProperty IP_CallExp_source = P_CallExp_source.getImplementation();
			
			Value leftA_symbol_103;
			try {
				
				Value A_symbol_104 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_105 = valueFactory.typeOf(A_symbol_104, S_closure);
				LibraryBinaryOperation dynamic_A_symbol_105 = (LibraryBinaryOperation)static_A_symbol_105.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_105 = dynamic_A_symbol_105.evaluate(evaluator, T_Boolean, A_symbol_104, S_closure);
				leftA_symbol_103 = A_symbol_105;
			} catch (InvalidValueException e) {
				leftA_symbol_103 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_105 = leftA_symbol_103;
			Value rightA_symbol_103;
			try {
				
				Value A_symbol_106 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_107 = valueFactory.typeOf(A_symbol_106);
				LibraryBinaryOperation dynamic_A_symbol_107 = (LibraryBinaryOperation)static_A_symbol_107.lookupImplementation(standardLibrary, O_OclAny_oclAsType);
				Value A_symbol_107 = dynamic_A_symbol_107.evaluate(evaluator, T_pivot__CollectionType, A_symbol_106, T_ClassClassifier_pivot__CollectionType_);
				Value A_symbol_108 = IP_CollectionType_elementType.evaluate(evaluator, T_Type, A_symbol_107, P_CollectionType_elementType);
				
				
				Value A_symbol_109 = IP_CallExp_source.evaluate(evaluator, T_pivot__OCLExpression, self, P_CallExp_source);
				
				Value A_symbol_110 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_109, P_TypedElement_type);
				
				DomainType static_A_symbol_111 = valueFactory.typeOf(A_symbol_110);
				LibraryBinaryOperation dynamic_A_symbol_111 = (LibraryBinaryOperation)static_A_symbol_111.lookupImplementation(standardLibrary, O_OclAny_oclAsType);
				Value A_symbol_111 = dynamic_A_symbol_111.evaluate(evaluator, T_pivot__CollectionType, A_symbol_110, T_ClassClassifier_pivot__CollectionType_);
				Value A_symbol_112 = IP_CollectionType_elementType.evaluate(evaluator, T_Type, A_symbol_111, P_CollectionType_elementType);
				
				DomainType static_A_symbol_113 = valueFactory.typeOf(A_symbol_108, A_symbol_112);
				LibraryBinaryOperation dynamic_A_symbol_113 = (LibraryBinaryOperation)static_A_symbol_113.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_113 = dynamic_A_symbol_113.evaluate(evaluator, T_Boolean, A_symbol_108, A_symbol_112);
				rightA_symbol_103 = A_symbol_113;
			} catch (InvalidValueException e) {
				rightA_symbol_103 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_113 = rightA_symbol_103;
			DomainType static_A_symbol_103 = valueFactory.typeOf(A_symbol_105);
			LibraryBinaryOperation dynamic_A_symbol_103 = (LibraryBinaryOperation)static_A_symbol_103.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_103 = dynamic_A_symbol_103.evaluate(evaluator, T_Boolean, A_symbol_105, A_symbol_113);
			return A_symbol_103;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'ClosureHasOneIterator' invariant.
	 */
	public static class _invariant_ClosureHasOneIterator extends AbstractUnaryOperation
	{
		public static _invariant_ClosureHasOneIterator INSTANCE = new _invariant_ClosureHasOneIterator();
	
		/*
		name = 'closure' implies iterator->size() = 1
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_closure = valueFactory.stringValueOf("closure");
			final ExecutorOperation O_Real__eq_ = OCLstdlibTables.Operations._Real___eq_;
			final ExecutorType T_Integer = OCLstdlibTables.Types._Integer;
			final ExecutorOperation O_Collection_size = OCLstdlibTables.Operations._Collection__size;
			final ExecutorType T_pivot__Variable = PivotTables.Types._Variable;
			final DomainCollectionType T_OrderedSet_pivot__Variable_ = standardLibrary.getOrderedSetType(T_pivot__Variable);
			final ExecutorProperty P_LoopExp_iterator = PivotTables.Properties._LoopExp__iterator;
			final LibraryProperty IP_LoopExp_iterator = P_LoopExp_iterator.getImplementation();
			final IntegerValue I_1 = valueFactory.integerValueOf(1);
			
			Value leftA_symbol_114;
			try {
				
				Value A_symbol_115 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_116 = valueFactory.typeOf(A_symbol_115, S_closure);
				LibraryBinaryOperation dynamic_A_symbol_116 = (LibraryBinaryOperation)static_A_symbol_116.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_116 = dynamic_A_symbol_116.evaluate(evaluator, T_Boolean, A_symbol_115, S_closure);
				leftA_symbol_114 = A_symbol_116;
			} catch (InvalidValueException e) {
				leftA_symbol_114 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_116 = leftA_symbol_114;
			Value rightA_symbol_114;
			try {
				
				Value A_symbol_117 = IP_LoopExp_iterator.evaluate(evaluator, T_OrderedSet_pivot__Variable_, self, P_LoopExp_iterator);
				
				DomainType static_A_symbol_118 = valueFactory.typeOf(A_symbol_117);
				LibraryUnaryOperation dynamic_A_symbol_118 = (LibraryUnaryOperation)static_A_symbol_118.lookupImplementation(standardLibrary, O_Collection_size);
				Value A_symbol_118 = dynamic_A_symbol_118.evaluate(evaluator, T_Integer, A_symbol_117);
				DomainType static_A_symbol_119 = valueFactory.typeOf(A_symbol_118, I_1);
				LibraryBinaryOperation dynamic_A_symbol_119 = (LibraryBinaryOperation)static_A_symbol_119.lookupImplementation(standardLibrary, O_Real__eq_);
				Value A_symbol_119 = dynamic_A_symbol_119.evaluate(evaluator, T_Boolean, A_symbol_118, I_1);
				rightA_symbol_114 = A_symbol_119;
			} catch (InvalidValueException e) {
				rightA_symbol_114 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_119 = rightA_symbol_114;
			DomainType static_A_symbol_114 = valueFactory.typeOf(A_symbol_116);
			LibraryBinaryOperation dynamic_A_symbol_114 = (LibraryBinaryOperation)static_A_symbol_114.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_114 = dynamic_A_symbol_114.evaluate(evaluator, T_Boolean, A_symbol_116, A_symbol_119);
			return A_symbol_114;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'ClosureSourceElementTypeIsBodyElementType' invariant.
	 */
	public static class _invariant_ClosureSourceElementTypeIsBodyElementType extends AbstractUnaryOperation
	{
		public static _invariant_ClosureSourceElementTypeIsBodyElementType INSTANCE = new _invariant_ClosureSourceElementTypeIsBodyElementType();
	
		/*
		name = 'closure' implies
	source.type.oclAsType(CollectionType).elementType =
	if body.type.oclIsKindOf(CollectionType)
	then body.type.oclAsType(CollectionType).elementType
	else body.type
	endif
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_closure = valueFactory.stringValueOf("closure");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_CollectionType_elementType = PivotTables.Properties._CollectionType__elementType;
			final LibraryProperty IP_CollectionType_elementType = P_CollectionType_elementType.getImplementation();
			final ExecutorType T_pivot__CollectionType = PivotTables.Types._CollectionType;
			final ExecutorOperation O_OclAny_oclAsType = OCLstdlibTables.Operations._OclAny__oclAsType;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_CallExp_source = PivotTables.Properties._CallExp__source;
			final LibraryProperty IP_CallExp_source = P_CallExp_source.getImplementation();
			final Value T_ClassClassifier_pivot__CollectionType_ = valueFactory.createTypeValue(PivotTables.Types._CollectionType);
			final ExecutorOperation O_OclAny_oclIsKindOf = OCLstdlibTables.Operations._OclAny__oclIsKindOf;
			final ExecutorProperty P_LoopExp_body = PivotTables.Properties._LoopExp__body;
			final LibraryProperty IP_LoopExp_body = P_LoopExp_body.getImplementation();
			
			Value leftA_symbol_120;
			try {
				
				Value A_symbol_121 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_122 = valueFactory.typeOf(A_symbol_121, S_closure);
				LibraryBinaryOperation dynamic_A_symbol_122 = (LibraryBinaryOperation)static_A_symbol_122.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_122 = dynamic_A_symbol_122.evaluate(evaluator, T_Boolean, A_symbol_121, S_closure);
				leftA_symbol_120 = A_symbol_122;
			} catch (InvalidValueException e) {
				leftA_symbol_120 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_122 = leftA_symbol_120;
			Value rightA_symbol_120;
			try {
				
				Value A_symbol_123 = IP_CallExp_source.evaluate(evaluator, T_pivot__OCLExpression, self, P_CallExp_source);
				
				Value A_symbol_124 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_123, P_TypedElement_type);
				
				DomainType static_A_symbol_125 = valueFactory.typeOf(A_symbol_124);
				LibraryBinaryOperation dynamic_A_symbol_125 = (LibraryBinaryOperation)static_A_symbol_125.lookupImplementation(standardLibrary, O_OclAny_oclAsType);
				Value A_symbol_125 = dynamic_A_symbol_125.evaluate(evaluator, T_pivot__CollectionType, A_symbol_124, T_ClassClassifier_pivot__CollectionType_);
				Value A_symbol_126 = IP_CollectionType_elementType.evaluate(evaluator, T_Type, A_symbol_125, P_CollectionType_elementType);
				
					
					Value A_symbol_127 = IP_LoopExp_body.evaluate(evaluator, T_pivot__OCLExpression, self, P_LoopExp_body);
					
					Value A_symbol_128 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_127, P_TypedElement_type);
					
					DomainType static_A_symbol_129 = valueFactory.typeOf(A_symbol_128);
					LibraryBinaryOperation dynamic_A_symbol_129 = (LibraryBinaryOperation)static_A_symbol_129.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
					Value A_symbol_129 = dynamic_A_symbol_129.evaluate(evaluator, T_Boolean, A_symbol_128, T_ClassClassifier_pivot__CollectionType_);
				Value A_symbol_130;
				if (A_symbol_129.isTrue()) {
					
					Value A_symbol_131 = IP_LoopExp_body.evaluate(evaluator, T_pivot__OCLExpression, self, P_LoopExp_body);
					
					Value A_symbol_132 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_131, P_TypedElement_type);
					
					DomainType static_A_symbol_133 = valueFactory.typeOf(A_symbol_132);
					LibraryBinaryOperation dynamic_A_symbol_133 = (LibraryBinaryOperation)static_A_symbol_133.lookupImplementation(standardLibrary, O_OclAny_oclAsType);
					Value A_symbol_133 = dynamic_A_symbol_133.evaluate(evaluator, T_pivot__CollectionType, A_symbol_132, T_ClassClassifier_pivot__CollectionType_);
					Value A_symbol_134 = IP_CollectionType_elementType.evaluate(evaluator, T_Type, A_symbol_133, P_CollectionType_elementType);
					
					A_symbol_130 = A_symbol_134;
				}
				else if (A_symbol_129.isFalse()) {
					
					Value A_symbol_135 = IP_LoopExp_body.evaluate(evaluator, T_pivot__OCLExpression, self, P_LoopExp_body);
					
					Value A_symbol_136 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_135, P_TypedElement_type);
					
					A_symbol_130 = A_symbol_136;
				}
				else if (A_symbol_129.isNull()) {
					A_symbol_130 = valueFactory.throwInvalidValueException("null if condition");
				}
				else {
					A_symbol_130 = valueFactory.throwInvalidValueException("invalid if condition");
				}
				DomainType static_A_symbol_137 = valueFactory.typeOf(A_symbol_126, A_symbol_130);
				LibraryBinaryOperation dynamic_A_symbol_137 = (LibraryBinaryOperation)static_A_symbol_137.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_137 = dynamic_A_symbol_137.evaluate(evaluator, T_Boolean, A_symbol_126, A_symbol_130);
				rightA_symbol_120 = A_symbol_137;
			} catch (InvalidValueException e) {
				rightA_symbol_120 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_137 = rightA_symbol_120;
			DomainType static_A_symbol_120 = valueFactory.typeOf(A_symbol_122);
			LibraryBinaryOperation dynamic_A_symbol_120 = (LibraryBinaryOperation)static_A_symbol_120.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_120 = dynamic_A_symbol_120.evaluate(evaluator, T_Boolean, A_symbol_122, A_symbol_137);
			return A_symbol_120;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'ClosureTypeIsUniqueCollection' invariant.
	 */
	public static class _invariant_ClosureTypeIsUniqueCollection extends AbstractUnaryOperation
	{
		public static _invariant_ClosureTypeIsUniqueCollection INSTANCE = new _invariant_ClosureTypeIsUniqueCollection();
	
		/*
		name = 'closure' implies
	if
	  source.type.oclIsKindOf(SequenceType) or
	  source.type.oclIsKindOf(OrderedSetType)
	then type.oclIsKindOf(OrderedSetType)
	else type.oclIsKindOf(SetType)
	endif
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_closure = valueFactory.stringValueOf("closure");
			final ExecutorOperation O_Boolean_or = OCLstdlibTables.Operations._Boolean__or;
			final ExecutorOperation O_OclAny_oclIsKindOf = OCLstdlibTables.Operations._OclAny__oclIsKindOf;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_CallExp_source = PivotTables.Properties._CallExp__source;
			final LibraryProperty IP_CallExp_source = P_CallExp_source.getImplementation();
			final Value T_ClassClassifier_pivot__SequenceType_ = valueFactory.createTypeValue(PivotTables.Types._SequenceType);
			final Value T_ClassClassifier_pivot__OrderedSetType_ = valueFactory.createTypeValue(PivotTables.Types._OrderedSetType);
			final Value T_ClassClassifier_pivot__SetType_ = valueFactory.createTypeValue(PivotTables.Types._SetType);
			
			Value leftA_symbol_138;
			try {
				
				Value A_symbol_139 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_140 = valueFactory.typeOf(A_symbol_139, S_closure);
				LibraryBinaryOperation dynamic_A_symbol_140 = (LibraryBinaryOperation)static_A_symbol_140.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_140 = dynamic_A_symbol_140.evaluate(evaluator, T_Boolean, A_symbol_139, S_closure);
				leftA_symbol_138 = A_symbol_140;
			} catch (InvalidValueException e) {
				leftA_symbol_138 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_140 = leftA_symbol_138;
			Value rightA_symbol_138;
			try {
					Value leftA_symbol_141;
					try {
						
						Value A_symbol_142 = IP_CallExp_source.evaluate(evaluator, T_pivot__OCLExpression, self, P_CallExp_source);
						
						Value A_symbol_143 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_142, P_TypedElement_type);
						
						DomainType static_A_symbol_144 = valueFactory.typeOf(A_symbol_143);
						LibraryBinaryOperation dynamic_A_symbol_144 = (LibraryBinaryOperation)static_A_symbol_144.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
						Value A_symbol_144 = dynamic_A_symbol_144.evaluate(evaluator, T_Boolean, A_symbol_143, T_ClassClassifier_pivot__SequenceType_);
						leftA_symbol_141 = A_symbol_144;
					} catch (InvalidValueException e) {
						leftA_symbol_141 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_144 = leftA_symbol_141;
					Value rightA_symbol_141;
					try {
						
						Value A_symbol_145 = IP_CallExp_source.evaluate(evaluator, T_pivot__OCLExpression, self, P_CallExp_source);
						
						Value A_symbol_146 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_145, P_TypedElement_type);
						
						DomainType static_A_symbol_147 = valueFactory.typeOf(A_symbol_146);
						LibraryBinaryOperation dynamic_A_symbol_147 = (LibraryBinaryOperation)static_A_symbol_147.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
						Value A_symbol_147 = dynamic_A_symbol_147.evaluate(evaluator, T_Boolean, A_symbol_146, T_ClassClassifier_pivot__OrderedSetType_);
						rightA_symbol_141 = A_symbol_147;
					} catch (InvalidValueException e) {
						rightA_symbol_141 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_147 = rightA_symbol_141;
					DomainType static_A_symbol_141 = valueFactory.typeOf(A_symbol_144);
					LibraryBinaryOperation dynamic_A_symbol_141 = (LibraryBinaryOperation)static_A_symbol_141.lookupImplementation(standardLibrary, O_Boolean_or);
					Value A_symbol_141 = dynamic_A_symbol_141.evaluate(evaluator, T_Boolean, A_symbol_144, A_symbol_147);
				Value A_symbol_148;
				if (A_symbol_141.isTrue()) {
					
					Value A_symbol_149 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
					
					DomainType static_A_symbol_150 = valueFactory.typeOf(A_symbol_149);
					LibraryBinaryOperation dynamic_A_symbol_150 = (LibraryBinaryOperation)static_A_symbol_150.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
					Value A_symbol_150 = dynamic_A_symbol_150.evaluate(evaluator, T_Boolean, A_symbol_149, T_ClassClassifier_pivot__OrderedSetType_);
					A_symbol_148 = A_symbol_150;
				}
				else if (A_symbol_141.isFalse()) {
					
					Value A_symbol_151 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
					
					DomainType static_A_symbol_152 = valueFactory.typeOf(A_symbol_151);
					LibraryBinaryOperation dynamic_A_symbol_152 = (LibraryBinaryOperation)static_A_symbol_152.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
					Value A_symbol_152 = dynamic_A_symbol_152.evaluate(evaluator, T_Boolean, A_symbol_151, T_ClassClassifier_pivot__SetType_);
					A_symbol_148 = A_symbol_152;
				}
				else if (A_symbol_141.isNull()) {
					A_symbol_148 = valueFactory.throwInvalidValueException("null if condition");
				}
				else {
					A_symbol_148 = valueFactory.throwInvalidValueException("invalid if condition");
				}
				rightA_symbol_138 = A_symbol_148;
			} catch (InvalidValueException e) {
				rightA_symbol_138 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_148 = rightA_symbol_138;
			DomainType static_A_symbol_138 = valueFactory.typeOf(A_symbol_140);
			LibraryBinaryOperation dynamic_A_symbol_138 = (LibraryBinaryOperation)static_A_symbol_138.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_138 = dynamic_A_symbol_138.evaluate(evaluator, T_Boolean, A_symbol_140, A_symbol_148);
			return A_symbol_138;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'CollectElementTypeIsSourceElementType' invariant.
	 */
	public static class _invariant_CollectElementTypeIsSourceElementType extends AbstractUnaryOperation
	{
		public static _invariant_CollectElementTypeIsSourceElementType INSTANCE = new _invariant_CollectElementTypeIsSourceElementType();
	
		/*
		name = 'collect' implies
	type.oclAsType(CollectionType).elementType =
	body.type.oclAsType(CollectionType).elementType
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_collect = valueFactory.stringValueOf("collect");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_CollectionType_elementType = PivotTables.Properties._CollectionType__elementType;
			final LibraryProperty IP_CollectionType_elementType = P_CollectionType_elementType.getImplementation();
			final ExecutorType T_pivot__CollectionType = PivotTables.Types._CollectionType;
			final ExecutorOperation O_OclAny_oclAsType = OCLstdlibTables.Operations._OclAny__oclAsType;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_pivot__CollectionType_ = valueFactory.createTypeValue(PivotTables.Types._CollectionType);
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_LoopExp_body = PivotTables.Properties._LoopExp__body;
			final LibraryProperty IP_LoopExp_body = P_LoopExp_body.getImplementation();
			
			Value leftA_symbol_153;
			try {
				
				Value A_symbol_154 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_155 = valueFactory.typeOf(A_symbol_154, S_collect);
				LibraryBinaryOperation dynamic_A_symbol_155 = (LibraryBinaryOperation)static_A_symbol_155.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_155 = dynamic_A_symbol_155.evaluate(evaluator, T_Boolean, A_symbol_154, S_collect);
				leftA_symbol_153 = A_symbol_155;
			} catch (InvalidValueException e) {
				leftA_symbol_153 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_155 = leftA_symbol_153;
			Value rightA_symbol_153;
			try {
				
				Value A_symbol_156 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_157 = valueFactory.typeOf(A_symbol_156);
				LibraryBinaryOperation dynamic_A_symbol_157 = (LibraryBinaryOperation)static_A_symbol_157.lookupImplementation(standardLibrary, O_OclAny_oclAsType);
				Value A_symbol_157 = dynamic_A_symbol_157.evaluate(evaluator, T_pivot__CollectionType, A_symbol_156, T_ClassClassifier_pivot__CollectionType_);
				Value A_symbol_158 = IP_CollectionType_elementType.evaluate(evaluator, T_Type, A_symbol_157, P_CollectionType_elementType);
				
				
				Value A_symbol_159 = IP_LoopExp_body.evaluate(evaluator, T_pivot__OCLExpression, self, P_LoopExp_body);
				
				Value A_symbol_160 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_159, P_TypedElement_type);
				
				DomainType static_A_symbol_161 = valueFactory.typeOf(A_symbol_160);
				LibraryBinaryOperation dynamic_A_symbol_161 = (LibraryBinaryOperation)static_A_symbol_161.lookupImplementation(standardLibrary, O_OclAny_oclAsType);
				Value A_symbol_161 = dynamic_A_symbol_161.evaluate(evaluator, T_pivot__CollectionType, A_symbol_160, T_ClassClassifier_pivot__CollectionType_);
				Value A_symbol_162 = IP_CollectionType_elementType.evaluate(evaluator, T_Type, A_symbol_161, P_CollectionType_elementType);
				
				DomainType static_A_symbol_163 = valueFactory.typeOf(A_symbol_158, A_symbol_162);
				LibraryBinaryOperation dynamic_A_symbol_163 = (LibraryBinaryOperation)static_A_symbol_163.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_163 = dynamic_A_symbol_163.evaluate(evaluator, T_Boolean, A_symbol_158, A_symbol_162);
				rightA_symbol_153 = A_symbol_163;
			} catch (InvalidValueException e) {
				rightA_symbol_153 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_163 = rightA_symbol_153;
			DomainType static_A_symbol_153 = valueFactory.typeOf(A_symbol_155);
			LibraryBinaryOperation dynamic_A_symbol_153 = (LibraryBinaryOperation)static_A_symbol_153.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_153 = dynamic_A_symbol_153.evaluate(evaluator, T_Boolean, A_symbol_155, A_symbol_163);
			return A_symbol_153;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'CollectHasOneIterator' invariant.
	 */
	public static class _invariant_CollectHasOneIterator extends AbstractUnaryOperation
	{
		public static _invariant_CollectHasOneIterator INSTANCE = new _invariant_CollectHasOneIterator();
	
		/*
		name = 'collect' implies iterator->size() = 1
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_collect = valueFactory.stringValueOf("collect");
			final ExecutorOperation O_Real__eq_ = OCLstdlibTables.Operations._Real___eq_;
			final ExecutorType T_Integer = OCLstdlibTables.Types._Integer;
			final ExecutorOperation O_Collection_size = OCLstdlibTables.Operations._Collection__size;
			final ExecutorType T_pivot__Variable = PivotTables.Types._Variable;
			final DomainCollectionType T_OrderedSet_pivot__Variable_ = standardLibrary.getOrderedSetType(T_pivot__Variable);
			final ExecutorProperty P_LoopExp_iterator = PivotTables.Properties._LoopExp__iterator;
			final LibraryProperty IP_LoopExp_iterator = P_LoopExp_iterator.getImplementation();
			final IntegerValue I_1 = valueFactory.integerValueOf(1);
			
			Value leftA_symbol_164;
			try {
				
				Value A_symbol_165 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_166 = valueFactory.typeOf(A_symbol_165, S_collect);
				LibraryBinaryOperation dynamic_A_symbol_166 = (LibraryBinaryOperation)static_A_symbol_166.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_166 = dynamic_A_symbol_166.evaluate(evaluator, T_Boolean, A_symbol_165, S_collect);
				leftA_symbol_164 = A_symbol_166;
			} catch (InvalidValueException e) {
				leftA_symbol_164 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_166 = leftA_symbol_164;
			Value rightA_symbol_164;
			try {
				
				Value A_symbol_167 = IP_LoopExp_iterator.evaluate(evaluator, T_OrderedSet_pivot__Variable_, self, P_LoopExp_iterator);
				
				DomainType static_A_symbol_168 = valueFactory.typeOf(A_symbol_167);
				LibraryUnaryOperation dynamic_A_symbol_168 = (LibraryUnaryOperation)static_A_symbol_168.lookupImplementation(standardLibrary, O_Collection_size);
				Value A_symbol_168 = dynamic_A_symbol_168.evaluate(evaluator, T_Integer, A_symbol_167);
				DomainType static_A_symbol_169 = valueFactory.typeOf(A_symbol_168, I_1);
				LibraryBinaryOperation dynamic_A_symbol_169 = (LibraryBinaryOperation)static_A_symbol_169.lookupImplementation(standardLibrary, O_Real__eq_);
				Value A_symbol_169 = dynamic_A_symbol_169.evaluate(evaluator, T_Boolean, A_symbol_168, I_1);
				rightA_symbol_164 = A_symbol_169;
			} catch (InvalidValueException e) {
				rightA_symbol_164 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_169 = rightA_symbol_164;
			DomainType static_A_symbol_164 = valueFactory.typeOf(A_symbol_166);
			LibraryBinaryOperation dynamic_A_symbol_164 = (LibraryBinaryOperation)static_A_symbol_164.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_164 = dynamic_A_symbol_164.evaluate(evaluator, T_Boolean, A_symbol_166, A_symbol_169);
			return A_symbol_164;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'CollectNestedHasOneIterator' invariant.
	 */
	public static class _invariant_CollectNestedHasOneIterator extends AbstractUnaryOperation
	{
		public static _invariant_CollectNestedHasOneIterator INSTANCE = new _invariant_CollectNestedHasOneIterator();
	
		/*
		name = 'collectNested' implies iterator->size() = 1
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_collectN___ = valueFactory.stringValueOf("collectNested");
			final ExecutorOperation O_Real__eq_ = OCLstdlibTables.Operations._Real___eq_;
			final ExecutorType T_Integer = OCLstdlibTables.Types._Integer;
			final ExecutorOperation O_Collection_size = OCLstdlibTables.Operations._Collection__size;
			final ExecutorType T_pivot__Variable = PivotTables.Types._Variable;
			final DomainCollectionType T_OrderedSet_pivot__Variable_ = standardLibrary.getOrderedSetType(T_pivot__Variable);
			final ExecutorProperty P_LoopExp_iterator = PivotTables.Properties._LoopExp__iterator;
			final LibraryProperty IP_LoopExp_iterator = P_LoopExp_iterator.getImplementation();
			final IntegerValue I_1 = valueFactory.integerValueOf(1);
			
			Value leftA_symbol_170;
			try {
				
				Value A_symbol_171 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_172 = valueFactory.typeOf(A_symbol_171, S_collectN___);
				LibraryBinaryOperation dynamic_A_symbol_172 = (LibraryBinaryOperation)static_A_symbol_172.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_172 = dynamic_A_symbol_172.evaluate(evaluator, T_Boolean, A_symbol_171, S_collectN___);
				leftA_symbol_170 = A_symbol_172;
			} catch (InvalidValueException e) {
				leftA_symbol_170 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_172 = leftA_symbol_170;
			Value rightA_symbol_170;
			try {
				
				Value A_symbol_173 = IP_LoopExp_iterator.evaluate(evaluator, T_OrderedSet_pivot__Variable_, self, P_LoopExp_iterator);
				
				DomainType static_A_symbol_174 = valueFactory.typeOf(A_symbol_173);
				LibraryUnaryOperation dynamic_A_symbol_174 = (LibraryUnaryOperation)static_A_symbol_174.lookupImplementation(standardLibrary, O_Collection_size);
				Value A_symbol_174 = dynamic_A_symbol_174.evaluate(evaluator, T_Integer, A_symbol_173);
				DomainType static_A_symbol_175 = valueFactory.typeOf(A_symbol_174, I_1);
				LibraryBinaryOperation dynamic_A_symbol_175 = (LibraryBinaryOperation)static_A_symbol_175.lookupImplementation(standardLibrary, O_Real__eq_);
				Value A_symbol_175 = dynamic_A_symbol_175.evaluate(evaluator, T_Boolean, A_symbol_174, I_1);
				rightA_symbol_170 = A_symbol_175;
			} catch (InvalidValueException e) {
				rightA_symbol_170 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_175 = rightA_symbol_170;
			DomainType static_A_symbol_170 = valueFactory.typeOf(A_symbol_172);
			LibraryBinaryOperation dynamic_A_symbol_170 = (LibraryBinaryOperation)static_A_symbol_170.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_170 = dynamic_A_symbol_170.evaluate(evaluator, T_Boolean, A_symbol_172, A_symbol_175);
			return A_symbol_170;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'CollectNestedTypeIsBag' invariant.
	 */
	public static class _invariant_CollectNestedTypeIsBag extends AbstractUnaryOperation
	{
		public static _invariant_CollectNestedTypeIsBag INSTANCE = new _invariant_CollectNestedTypeIsBag();
	
		/*
		name = 'collectNested' implies type.oclIsKindOf(BagType)
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_collectN___ = valueFactory.stringValueOf("collectNested");
			final ExecutorOperation O_OclAny_oclIsKindOf = OCLstdlibTables.Operations._OclAny__oclIsKindOf;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_pivot__BagType_ = valueFactory.createTypeValue(PivotTables.Types._BagType);
			
			Value leftA_symbol_176;
			try {
				
				Value A_symbol_177 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_178 = valueFactory.typeOf(A_symbol_177, S_collectN___);
				LibraryBinaryOperation dynamic_A_symbol_178 = (LibraryBinaryOperation)static_A_symbol_178.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_178 = dynamic_A_symbol_178.evaluate(evaluator, T_Boolean, A_symbol_177, S_collectN___);
				leftA_symbol_176 = A_symbol_178;
			} catch (InvalidValueException e) {
				leftA_symbol_176 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_178 = leftA_symbol_176;
			Value rightA_symbol_176;
			try {
				
				Value A_symbol_179 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_180 = valueFactory.typeOf(A_symbol_179);
				LibraryBinaryOperation dynamic_A_symbol_180 = (LibraryBinaryOperation)static_A_symbol_180.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
				Value A_symbol_180 = dynamic_A_symbol_180.evaluate(evaluator, T_Boolean, A_symbol_179, T_ClassClassifier_pivot__BagType_);
				rightA_symbol_176 = A_symbol_180;
			} catch (InvalidValueException e) {
				rightA_symbol_176 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_180 = rightA_symbol_176;
			DomainType static_A_symbol_176 = valueFactory.typeOf(A_symbol_178);
			LibraryBinaryOperation dynamic_A_symbol_176 = (LibraryBinaryOperation)static_A_symbol_176.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_176 = dynamic_A_symbol_176.evaluate(evaluator, T_Boolean, A_symbol_178, A_symbol_180);
			return A_symbol_176;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'CollectNestedTypeIsBodyType' invariant.
	 */
	public static class _invariant_CollectNestedTypeIsBodyType extends AbstractUnaryOperation
	{
		public static _invariant_CollectNestedTypeIsBodyType INSTANCE = new _invariant_CollectNestedTypeIsBodyType();
	
		/*
		name = 'collectNested' implies type = body.type
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_collectN___ = valueFactory.stringValueOf("collectNested");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_LoopExp_body = PivotTables.Properties._LoopExp__body;
			final LibraryProperty IP_LoopExp_body = P_LoopExp_body.getImplementation();
			
			Value leftA_symbol_181;
			try {
				
				Value A_symbol_182 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_183 = valueFactory.typeOf(A_symbol_182, S_collectN___);
				LibraryBinaryOperation dynamic_A_symbol_183 = (LibraryBinaryOperation)static_A_symbol_183.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_183 = dynamic_A_symbol_183.evaluate(evaluator, T_Boolean, A_symbol_182, S_collectN___);
				leftA_symbol_181 = A_symbol_183;
			} catch (InvalidValueException e) {
				leftA_symbol_181 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_183 = leftA_symbol_181;
			Value rightA_symbol_181;
			try {
				
				Value A_symbol_184 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				
				Value A_symbol_185 = IP_LoopExp_body.evaluate(evaluator, T_pivot__OCLExpression, self, P_LoopExp_body);
				
				Value A_symbol_186 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_185, P_TypedElement_type);
				
				DomainType static_A_symbol_187 = valueFactory.typeOf(A_symbol_184, A_symbol_186);
				LibraryBinaryOperation dynamic_A_symbol_187 = (LibraryBinaryOperation)static_A_symbol_187.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_187 = dynamic_A_symbol_187.evaluate(evaluator, T_Boolean, A_symbol_184, A_symbol_186);
				rightA_symbol_181 = A_symbol_187;
			} catch (InvalidValueException e) {
				rightA_symbol_181 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_187 = rightA_symbol_181;
			DomainType static_A_symbol_181 = valueFactory.typeOf(A_symbol_183);
			LibraryBinaryOperation dynamic_A_symbol_181 = (LibraryBinaryOperation)static_A_symbol_181.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_181 = dynamic_A_symbol_181.evaluate(evaluator, T_Boolean, A_symbol_183, A_symbol_187);
			return A_symbol_181;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'CollectTypeIsUnordered' invariant.
	 */
	public static class _invariant_CollectTypeIsUnordered extends AbstractUnaryOperation
	{
		public static _invariant_CollectTypeIsUnordered INSTANCE = new _invariant_CollectTypeIsUnordered();
	
		/*
		name = 'collect' implies
	if
	  source.type.oclIsKindOf(SequenceType) or
	  source.type.oclIsKindOf(OrderedSetType)
	then type.oclIsKindOf(SequenceType)
	else type.oclIsKindOf(BagType)
	endif
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_collect = valueFactory.stringValueOf("collect");
			final ExecutorOperation O_Boolean_or = OCLstdlibTables.Operations._Boolean__or;
			final ExecutorOperation O_OclAny_oclIsKindOf = OCLstdlibTables.Operations._OclAny__oclIsKindOf;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_CallExp_source = PivotTables.Properties._CallExp__source;
			final LibraryProperty IP_CallExp_source = P_CallExp_source.getImplementation();
			final Value T_ClassClassifier_pivot__SequenceType_ = valueFactory.createTypeValue(PivotTables.Types._SequenceType);
			final Value T_ClassClassifier_pivot__OrderedSetType_ = valueFactory.createTypeValue(PivotTables.Types._OrderedSetType);
			final Value T_ClassClassifier_pivot__BagType_ = valueFactory.createTypeValue(PivotTables.Types._BagType);
			
			Value leftA_symbol_188;
			try {
				
				Value A_symbol_189 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_190 = valueFactory.typeOf(A_symbol_189, S_collect);
				LibraryBinaryOperation dynamic_A_symbol_190 = (LibraryBinaryOperation)static_A_symbol_190.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_190 = dynamic_A_symbol_190.evaluate(evaluator, T_Boolean, A_symbol_189, S_collect);
				leftA_symbol_188 = A_symbol_190;
			} catch (InvalidValueException e) {
				leftA_symbol_188 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_190 = leftA_symbol_188;
			Value rightA_symbol_188;
			try {
					Value leftA_symbol_191;
					try {
						
						Value A_symbol_192 = IP_CallExp_source.evaluate(evaluator, T_pivot__OCLExpression, self, P_CallExp_source);
						
						Value A_symbol_193 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_192, P_TypedElement_type);
						
						DomainType static_A_symbol_194 = valueFactory.typeOf(A_symbol_193);
						LibraryBinaryOperation dynamic_A_symbol_194 = (LibraryBinaryOperation)static_A_symbol_194.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
						Value A_symbol_194 = dynamic_A_symbol_194.evaluate(evaluator, T_Boolean, A_symbol_193, T_ClassClassifier_pivot__SequenceType_);
						leftA_symbol_191 = A_symbol_194;
					} catch (InvalidValueException e) {
						leftA_symbol_191 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_194 = leftA_symbol_191;
					Value rightA_symbol_191;
					try {
						
						Value A_symbol_195 = IP_CallExp_source.evaluate(evaluator, T_pivot__OCLExpression, self, P_CallExp_source);
						
						Value A_symbol_196 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_195, P_TypedElement_type);
						
						DomainType static_A_symbol_197 = valueFactory.typeOf(A_symbol_196);
						LibraryBinaryOperation dynamic_A_symbol_197 = (LibraryBinaryOperation)static_A_symbol_197.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
						Value A_symbol_197 = dynamic_A_symbol_197.evaluate(evaluator, T_Boolean, A_symbol_196, T_ClassClassifier_pivot__OrderedSetType_);
						rightA_symbol_191 = A_symbol_197;
					} catch (InvalidValueException e) {
						rightA_symbol_191 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_197 = rightA_symbol_191;
					DomainType static_A_symbol_191 = valueFactory.typeOf(A_symbol_194);
					LibraryBinaryOperation dynamic_A_symbol_191 = (LibraryBinaryOperation)static_A_symbol_191.lookupImplementation(standardLibrary, O_Boolean_or);
					Value A_symbol_191 = dynamic_A_symbol_191.evaluate(evaluator, T_Boolean, A_symbol_194, A_symbol_197);
				Value A_symbol_198;
				if (A_symbol_191.isTrue()) {
					
					Value A_symbol_199 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
					
					DomainType static_A_symbol_200 = valueFactory.typeOf(A_symbol_199);
					LibraryBinaryOperation dynamic_A_symbol_200 = (LibraryBinaryOperation)static_A_symbol_200.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
					Value A_symbol_200 = dynamic_A_symbol_200.evaluate(evaluator, T_Boolean, A_symbol_199, T_ClassClassifier_pivot__SequenceType_);
					A_symbol_198 = A_symbol_200;
				}
				else if (A_symbol_191.isFalse()) {
					
					Value A_symbol_201 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
					
					DomainType static_A_symbol_202 = valueFactory.typeOf(A_symbol_201);
					LibraryBinaryOperation dynamic_A_symbol_202 = (LibraryBinaryOperation)static_A_symbol_202.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
					Value A_symbol_202 = dynamic_A_symbol_202.evaluate(evaluator, T_Boolean, A_symbol_201, T_ClassClassifier_pivot__BagType_);
					A_symbol_198 = A_symbol_202;
				}
				else if (A_symbol_191.isNull()) {
					A_symbol_198 = valueFactory.throwInvalidValueException("null if condition");
				}
				else {
					A_symbol_198 = valueFactory.throwInvalidValueException("invalid if condition");
				}
				rightA_symbol_188 = A_symbol_198;
			} catch (InvalidValueException e) {
				rightA_symbol_188 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_198 = rightA_symbol_188;
			DomainType static_A_symbol_188 = valueFactory.typeOf(A_symbol_190);
			LibraryBinaryOperation dynamic_A_symbol_188 = (LibraryBinaryOperation)static_A_symbol_188.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_188 = dynamic_A_symbol_188.evaluate(evaluator, T_Boolean, A_symbol_190, A_symbol_198);
			return A_symbol_188;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'ExistsBodyTypeIsBoolean' invariant.
	 */
	public static class _invariant_ExistsBodyTypeIsBoolean extends AbstractUnaryOperation
	{
		public static _invariant_ExistsBodyTypeIsBoolean INSTANCE = new _invariant_ExistsBodyTypeIsBoolean();
	
		/*
		name = 'exists' implies body.type = Boolean
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_exists = valueFactory.stringValueOf("exists");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_LoopExp_body = PivotTables.Properties._LoopExp__body;
			final LibraryProperty IP_LoopExp_body = P_LoopExp_body.getImplementation();
			final Value T_ClassClassifier_Boolean_ = valueFactory.createTypeValue(OCLstdlibTables.Types._Boolean);
			
			Value leftA_symbol_203;
			try {
				
				Value A_symbol_204 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_205 = valueFactory.typeOf(A_symbol_204, S_exists);
				LibraryBinaryOperation dynamic_A_symbol_205 = (LibraryBinaryOperation)static_A_symbol_205.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_205 = dynamic_A_symbol_205.evaluate(evaluator, T_Boolean, A_symbol_204, S_exists);
				leftA_symbol_203 = A_symbol_205;
			} catch (InvalidValueException e) {
				leftA_symbol_203 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_205 = leftA_symbol_203;
			Value rightA_symbol_203;
			try {
				
				Value A_symbol_206 = IP_LoopExp_body.evaluate(evaluator, T_pivot__OCLExpression, self, P_LoopExp_body);
				
				Value A_symbol_207 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_206, P_TypedElement_type);
				
				DomainType static_A_symbol_208 = valueFactory.typeOf(A_symbol_207, T_ClassClassifier_Boolean_);
				LibraryBinaryOperation dynamic_A_symbol_208 = (LibraryBinaryOperation)static_A_symbol_208.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_208 = dynamic_A_symbol_208.evaluate(evaluator, T_Boolean, A_symbol_207, T_ClassClassifier_Boolean_);
				rightA_symbol_203 = A_symbol_208;
			} catch (InvalidValueException e) {
				rightA_symbol_203 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_208 = rightA_symbol_203;
			DomainType static_A_symbol_203 = valueFactory.typeOf(A_symbol_205);
			LibraryBinaryOperation dynamic_A_symbol_203 = (LibraryBinaryOperation)static_A_symbol_203.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_203 = dynamic_A_symbol_203.evaluate(evaluator, T_Boolean, A_symbol_205, A_symbol_208);
			return A_symbol_203;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'ExistsTypeIsBoolean' invariant.
	 */
	public static class _invariant_ExistsTypeIsBoolean extends AbstractUnaryOperation
	{
		public static _invariant_ExistsTypeIsBoolean INSTANCE = new _invariant_ExistsTypeIsBoolean();
	
		/*
		name = 'exists' implies type = Boolean
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_exists = valueFactory.stringValueOf("exists");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_Boolean_ = valueFactory.createTypeValue(OCLstdlibTables.Types._Boolean);
			
			Value leftA_symbol_209;
			try {
				
				Value A_symbol_210 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_211 = valueFactory.typeOf(A_symbol_210, S_exists);
				LibraryBinaryOperation dynamic_A_symbol_211 = (LibraryBinaryOperation)static_A_symbol_211.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_211 = dynamic_A_symbol_211.evaluate(evaluator, T_Boolean, A_symbol_210, S_exists);
				leftA_symbol_209 = A_symbol_211;
			} catch (InvalidValueException e) {
				leftA_symbol_209 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_211 = leftA_symbol_209;
			Value rightA_symbol_209;
			try {
				
				Value A_symbol_212 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_213 = valueFactory.typeOf(A_symbol_212, T_ClassClassifier_Boolean_);
				LibraryBinaryOperation dynamic_A_symbol_213 = (LibraryBinaryOperation)static_A_symbol_213.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_213 = dynamic_A_symbol_213.evaluate(evaluator, T_Boolean, A_symbol_212, T_ClassClassifier_Boolean_);
				rightA_symbol_209 = A_symbol_213;
			} catch (InvalidValueException e) {
				rightA_symbol_209 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_213 = rightA_symbol_209;
			DomainType static_A_symbol_209 = valueFactory.typeOf(A_symbol_211);
			LibraryBinaryOperation dynamic_A_symbol_209 = (LibraryBinaryOperation)static_A_symbol_209.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_209 = dynamic_A_symbol_209.evaluate(evaluator, T_Boolean, A_symbol_211, A_symbol_213);
			return A_symbol_209;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'ForAllBodyTypeIsBoolean' invariant.
	 */
	public static class _invariant_ForAllBodyTypeIsBoolean extends AbstractUnaryOperation
	{
		public static _invariant_ForAllBodyTypeIsBoolean INSTANCE = new _invariant_ForAllBodyTypeIsBoolean();
	
		/*
		name = 'forAll' implies body.type = Boolean
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_forAll = valueFactory.stringValueOf("forAll");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_LoopExp_body = PivotTables.Properties._LoopExp__body;
			final LibraryProperty IP_LoopExp_body = P_LoopExp_body.getImplementation();
			final Value T_ClassClassifier_Boolean_ = valueFactory.createTypeValue(OCLstdlibTables.Types._Boolean);
			
			Value leftA_symbol_214;
			try {
				
				Value A_symbol_215 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_216 = valueFactory.typeOf(A_symbol_215, S_forAll);
				LibraryBinaryOperation dynamic_A_symbol_216 = (LibraryBinaryOperation)static_A_symbol_216.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_216 = dynamic_A_symbol_216.evaluate(evaluator, T_Boolean, A_symbol_215, S_forAll);
				leftA_symbol_214 = A_symbol_216;
			} catch (InvalidValueException e) {
				leftA_symbol_214 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_216 = leftA_symbol_214;
			Value rightA_symbol_214;
			try {
				
				Value A_symbol_217 = IP_LoopExp_body.evaluate(evaluator, T_pivot__OCLExpression, self, P_LoopExp_body);
				
				Value A_symbol_218 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_217, P_TypedElement_type);
				
				DomainType static_A_symbol_219 = valueFactory.typeOf(A_symbol_218, T_ClassClassifier_Boolean_);
				LibraryBinaryOperation dynamic_A_symbol_219 = (LibraryBinaryOperation)static_A_symbol_219.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_219 = dynamic_A_symbol_219.evaluate(evaluator, T_Boolean, A_symbol_218, T_ClassClassifier_Boolean_);
				rightA_symbol_214 = A_symbol_219;
			} catch (InvalidValueException e) {
				rightA_symbol_214 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_219 = rightA_symbol_214;
			DomainType static_A_symbol_214 = valueFactory.typeOf(A_symbol_216);
			LibraryBinaryOperation dynamic_A_symbol_214 = (LibraryBinaryOperation)static_A_symbol_214.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_214 = dynamic_A_symbol_214.evaluate(evaluator, T_Boolean, A_symbol_216, A_symbol_219);
			return A_symbol_214;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'ForAllTypeIsBoolean' invariant.
	 */
	public static class _invariant_ForAllTypeIsBoolean extends AbstractUnaryOperation
	{
		public static _invariant_ForAllTypeIsBoolean INSTANCE = new _invariant_ForAllTypeIsBoolean();
	
		/*
		name = 'forAll' implies type = Boolean
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_forAll = valueFactory.stringValueOf("forAll");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_Boolean_ = valueFactory.createTypeValue(OCLstdlibTables.Types._Boolean);
			
			Value leftA_symbol_220;
			try {
				
				Value A_symbol_221 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_222 = valueFactory.typeOf(A_symbol_221, S_forAll);
				LibraryBinaryOperation dynamic_A_symbol_222 = (LibraryBinaryOperation)static_A_symbol_222.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_222 = dynamic_A_symbol_222.evaluate(evaluator, T_Boolean, A_symbol_221, S_forAll);
				leftA_symbol_220 = A_symbol_222;
			} catch (InvalidValueException e) {
				leftA_symbol_220 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_222 = leftA_symbol_220;
			Value rightA_symbol_220;
			try {
				
				Value A_symbol_223 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_224 = valueFactory.typeOf(A_symbol_223, T_ClassClassifier_Boolean_);
				LibraryBinaryOperation dynamic_A_symbol_224 = (LibraryBinaryOperation)static_A_symbol_224.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_224 = dynamic_A_symbol_224.evaluate(evaluator, T_Boolean, A_symbol_223, T_ClassClassifier_Boolean_);
				rightA_symbol_220 = A_symbol_224;
			} catch (InvalidValueException e) {
				rightA_symbol_220 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_224 = rightA_symbol_220;
			DomainType static_A_symbol_220 = valueFactory.typeOf(A_symbol_222);
			LibraryBinaryOperation dynamic_A_symbol_220 = (LibraryBinaryOperation)static_A_symbol_220.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_220 = dynamic_A_symbol_220.evaluate(evaluator, T_Boolean, A_symbol_222, A_symbol_224);
			return A_symbol_220;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'IsUniqueHasOneIterator' invariant.
	 */
	public static class _invariant_IsUniqueHasOneIterator extends AbstractUnaryOperation
	{
		public static _invariant_IsUniqueHasOneIterator INSTANCE = new _invariant_IsUniqueHasOneIterator();
	
		/*
		name = 'isUnique' implies iterator->size() = 1
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_isUnique = valueFactory.stringValueOf("isUnique");
			final ExecutorOperation O_Real__eq_ = OCLstdlibTables.Operations._Real___eq_;
			final ExecutorType T_Integer = OCLstdlibTables.Types._Integer;
			final ExecutorOperation O_Collection_size = OCLstdlibTables.Operations._Collection__size;
			final ExecutorType T_pivot__Variable = PivotTables.Types._Variable;
			final DomainCollectionType T_OrderedSet_pivot__Variable_ = standardLibrary.getOrderedSetType(T_pivot__Variable);
			final ExecutorProperty P_LoopExp_iterator = PivotTables.Properties._LoopExp__iterator;
			final LibraryProperty IP_LoopExp_iterator = P_LoopExp_iterator.getImplementation();
			final IntegerValue I_1 = valueFactory.integerValueOf(1);
			
			Value leftA_symbol_225;
			try {
				
				Value A_symbol_226 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_227 = valueFactory.typeOf(A_symbol_226, S_isUnique);
				LibraryBinaryOperation dynamic_A_symbol_227 = (LibraryBinaryOperation)static_A_symbol_227.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_227 = dynamic_A_symbol_227.evaluate(evaluator, T_Boolean, A_symbol_226, S_isUnique);
				leftA_symbol_225 = A_symbol_227;
			} catch (InvalidValueException e) {
				leftA_symbol_225 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_227 = leftA_symbol_225;
			Value rightA_symbol_225;
			try {
				
				Value A_symbol_228 = IP_LoopExp_iterator.evaluate(evaluator, T_OrderedSet_pivot__Variable_, self, P_LoopExp_iterator);
				
				DomainType static_A_symbol_229 = valueFactory.typeOf(A_symbol_228);
				LibraryUnaryOperation dynamic_A_symbol_229 = (LibraryUnaryOperation)static_A_symbol_229.lookupImplementation(standardLibrary, O_Collection_size);
				Value A_symbol_229 = dynamic_A_symbol_229.evaluate(evaluator, T_Integer, A_symbol_228);
				DomainType static_A_symbol_230 = valueFactory.typeOf(A_symbol_229, I_1);
				LibraryBinaryOperation dynamic_A_symbol_230 = (LibraryBinaryOperation)static_A_symbol_230.lookupImplementation(standardLibrary, O_Real__eq_);
				Value A_symbol_230 = dynamic_A_symbol_230.evaluate(evaluator, T_Boolean, A_symbol_229, I_1);
				rightA_symbol_225 = A_symbol_230;
			} catch (InvalidValueException e) {
				rightA_symbol_225 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_230 = rightA_symbol_225;
			DomainType static_A_symbol_225 = valueFactory.typeOf(A_symbol_227);
			LibraryBinaryOperation dynamic_A_symbol_225 = (LibraryBinaryOperation)static_A_symbol_225.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_225 = dynamic_A_symbol_225.evaluate(evaluator, T_Boolean, A_symbol_227, A_symbol_230);
			return A_symbol_225;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'IsUniqueTypeIsBoolean' invariant.
	 */
	public static class _invariant_IsUniqueTypeIsBoolean extends AbstractUnaryOperation
	{
		public static _invariant_IsUniqueTypeIsBoolean INSTANCE = new _invariant_IsUniqueTypeIsBoolean();
	
		/*
		name = 'isUnique' implies type = Boolean
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_isUnique = valueFactory.stringValueOf("isUnique");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_Boolean_ = valueFactory.createTypeValue(OCLstdlibTables.Types._Boolean);
			
			Value leftA_symbol_231;
			try {
				
				Value A_symbol_232 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_233 = valueFactory.typeOf(A_symbol_232, S_isUnique);
				LibraryBinaryOperation dynamic_A_symbol_233 = (LibraryBinaryOperation)static_A_symbol_233.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_233 = dynamic_A_symbol_233.evaluate(evaluator, T_Boolean, A_symbol_232, S_isUnique);
				leftA_symbol_231 = A_symbol_233;
			} catch (InvalidValueException e) {
				leftA_symbol_231 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_233 = leftA_symbol_231;
			Value rightA_symbol_231;
			try {
				
				Value A_symbol_234 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_235 = valueFactory.typeOf(A_symbol_234, T_ClassClassifier_Boolean_);
				LibraryBinaryOperation dynamic_A_symbol_235 = (LibraryBinaryOperation)static_A_symbol_235.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_235 = dynamic_A_symbol_235.evaluate(evaluator, T_Boolean, A_symbol_234, T_ClassClassifier_Boolean_);
				rightA_symbol_231 = A_symbol_235;
			} catch (InvalidValueException e) {
				rightA_symbol_231 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_235 = rightA_symbol_231;
			DomainType static_A_symbol_231 = valueFactory.typeOf(A_symbol_233);
			LibraryBinaryOperation dynamic_A_symbol_231 = (LibraryBinaryOperation)static_A_symbol_231.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_231 = dynamic_A_symbol_231.evaluate(evaluator, T_Boolean, A_symbol_233, A_symbol_235);
			return A_symbol_231;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'IteratorTypeIsSourceElementType' invariant.
	 */
	public static class _invariant_IteratorTypeIsSourceElementType extends AbstractUnaryOperation
	{
		public static _invariant_IteratorTypeIsSourceElementType INSTANCE = new _invariant_IteratorTypeIsSourceElementType();
	
		/*
		self.iterator->forAll(type =
	  source.type.oclAsType(CollectionType).elementType)
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Collection_forAll = OCLstdlibTables.Operations._Collection__1_forAll;
			final ExecutorType T_pivot__Variable = PivotTables.Types._Variable;
			final DomainCollectionType T_OrderedSet_pivot__Variable_ = standardLibrary.getOrderedSetType(T_pivot__Variable);
			final ExecutorProperty P_LoopExp_iterator = PivotTables.Properties._LoopExp__iterator;
			final LibraryProperty IP_LoopExp_iterator = P_LoopExp_iterator.getImplementation();
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorProperty P_CollectionType_elementType = PivotTables.Properties._CollectionType__elementType;
			final LibraryProperty IP_CollectionType_elementType = P_CollectionType_elementType.getImplementation();
			final ExecutorType T_pivot__CollectionType = PivotTables.Types._CollectionType;
			final ExecutorOperation O_OclAny_oclAsType = OCLstdlibTables.Operations._OclAny__oclAsType;
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_CallExp_source = PivotTables.Properties._CallExp__source;
			final LibraryProperty IP_CallExp_source = P_CallExp_source.getImplementation();
			final Value T_ClassClassifier_pivot__CollectionType_ = valueFactory.createTypeValue(PivotTables.Types._CollectionType);
			
			
			Value A_symbol_236 = IP_LoopExp_iterator.evaluate(evaluator, T_OrderedSet_pivot__Variable_, self, P_LoopExp_iterator);
			
			
			/** 
			 * Implementation of the iterator body.
			 */
			AbstractBinaryOperation body_A_symbol_237 = new AbstractBinaryOperation()
			{
			/*
			type = source.type.oclAsType(CollectionType).elementType
			*/
				public Value evaluate(DomainEvaluator evaluator, DomainType returnType, Value sourceValue, Value iterator1) throws InvalidValueException {
					final Value V_1_ = iterator1;	// iterator: 1_
					
					Value A_symbol_238 = IP_TypedElement_type.evaluate(evaluator, T_Type, V_1_, P_TypedElement_type);
					
					
					Value A_symbol_239 = IP_CallExp_source.evaluate(evaluator, T_pivot__OCLExpression, self, P_CallExp_source);
					
					Value A_symbol_240 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_239, P_TypedElement_type);
					
					DomainType static_A_symbol_241 = valueFactory.typeOf(A_symbol_240);
					LibraryBinaryOperation dynamic_A_symbol_241 = (LibraryBinaryOperation)static_A_symbol_241.lookupImplementation(standardLibrary, O_OclAny_oclAsType);
					Value A_symbol_241 = dynamic_A_symbol_241.evaluate(evaluator, T_pivot__CollectionType, A_symbol_240, T_ClassClassifier_pivot__CollectionType_);
					Value A_symbol_242 = IP_CollectionType_elementType.evaluate(evaluator, T_Type, A_symbol_241, P_CollectionType_elementType);
					
					DomainType static_A_symbol_243 = valueFactory.typeOf(A_symbol_238, A_symbol_242);
					LibraryBinaryOperation dynamic_A_symbol_243 = (LibraryBinaryOperation)static_A_symbol_243.lookupImplementation(standardLibrary, O_OclAny__eq_);
					Value A_symbol_243 = dynamic_A_symbol_243.evaluate(evaluator, T_Boolean, A_symbol_238, A_symbol_242);
					return A_symbol_243;
				}
			};
			DomainType static_A_symbol_237 = A_symbol_236.getType();
			LibraryIteration dynamic_A_symbol_237 = (LibraryIteration)static_A_symbol_237.lookupImplementation(standardLibrary, O_Collection_forAll);
			Value acc_A_symbol_237 = dynamic_A_symbol_237.createAccumulatorValue(evaluator, T_Boolean, T_Boolean);
			ExecutorSingleIterationManager manager_A_symbol_237 = new ExecutorSingleIterationManager(evaluator, T_Boolean, body_A_symbol_237, (CollectionValue)A_symbol_236, acc_A_symbol_237);
			Value A_symbol_237 = dynamic_A_symbol_237.evaluateIteration(manager_A_symbol_237);
			return A_symbol_237;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'OneBodyTypeIsBoolean' invariant.
	 */
	public static class _invariant_OneBodyTypeIsBoolean extends AbstractUnaryOperation
	{
		public static _invariant_OneBodyTypeIsBoolean INSTANCE = new _invariant_OneBodyTypeIsBoolean();
	
		/*
		name = 'one' implies body.type = Boolean
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_one = valueFactory.stringValueOf("one");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_LoopExp_body = PivotTables.Properties._LoopExp__body;
			final LibraryProperty IP_LoopExp_body = P_LoopExp_body.getImplementation();
			final Value T_ClassClassifier_Boolean_ = valueFactory.createTypeValue(OCLstdlibTables.Types._Boolean);
			
			Value leftA_symbol_244;
			try {
				
				Value A_symbol_245 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_246 = valueFactory.typeOf(A_symbol_245, S_one);
				LibraryBinaryOperation dynamic_A_symbol_246 = (LibraryBinaryOperation)static_A_symbol_246.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_246 = dynamic_A_symbol_246.evaluate(evaluator, T_Boolean, A_symbol_245, S_one);
				leftA_symbol_244 = A_symbol_246;
			} catch (InvalidValueException e) {
				leftA_symbol_244 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_246 = leftA_symbol_244;
			Value rightA_symbol_244;
			try {
				
				Value A_symbol_247 = IP_LoopExp_body.evaluate(evaluator, T_pivot__OCLExpression, self, P_LoopExp_body);
				
				Value A_symbol_248 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_247, P_TypedElement_type);
				
				DomainType static_A_symbol_249 = valueFactory.typeOf(A_symbol_248, T_ClassClassifier_Boolean_);
				LibraryBinaryOperation dynamic_A_symbol_249 = (LibraryBinaryOperation)static_A_symbol_249.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_249 = dynamic_A_symbol_249.evaluate(evaluator, T_Boolean, A_symbol_248, T_ClassClassifier_Boolean_);
				rightA_symbol_244 = A_symbol_249;
			} catch (InvalidValueException e) {
				rightA_symbol_244 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_249 = rightA_symbol_244;
			DomainType static_A_symbol_244 = valueFactory.typeOf(A_symbol_246);
			LibraryBinaryOperation dynamic_A_symbol_244 = (LibraryBinaryOperation)static_A_symbol_244.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_244 = dynamic_A_symbol_244.evaluate(evaluator, T_Boolean, A_symbol_246, A_symbol_249);
			return A_symbol_244;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'OneHasOneIterator' invariant.
	 */
	public static class _invariant_OneHasOneIterator extends AbstractUnaryOperation
	{
		public static _invariant_OneHasOneIterator INSTANCE = new _invariant_OneHasOneIterator();
	
		/*
		name = 'one' implies iterator->size() = 1
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_one = valueFactory.stringValueOf("one");
			final ExecutorOperation O_Real__eq_ = OCLstdlibTables.Operations._Real___eq_;
			final ExecutorType T_Integer = OCLstdlibTables.Types._Integer;
			final ExecutorOperation O_Collection_size = OCLstdlibTables.Operations._Collection__size;
			final ExecutorType T_pivot__Variable = PivotTables.Types._Variable;
			final DomainCollectionType T_OrderedSet_pivot__Variable_ = standardLibrary.getOrderedSetType(T_pivot__Variable);
			final ExecutorProperty P_LoopExp_iterator = PivotTables.Properties._LoopExp__iterator;
			final LibraryProperty IP_LoopExp_iterator = P_LoopExp_iterator.getImplementation();
			final IntegerValue I_1 = valueFactory.integerValueOf(1);
			
			Value leftA_symbol_250;
			try {
				
				Value A_symbol_251 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_252 = valueFactory.typeOf(A_symbol_251, S_one);
				LibraryBinaryOperation dynamic_A_symbol_252 = (LibraryBinaryOperation)static_A_symbol_252.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_252 = dynamic_A_symbol_252.evaluate(evaluator, T_Boolean, A_symbol_251, S_one);
				leftA_symbol_250 = A_symbol_252;
			} catch (InvalidValueException e) {
				leftA_symbol_250 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_252 = leftA_symbol_250;
			Value rightA_symbol_250;
			try {
				
				Value A_symbol_253 = IP_LoopExp_iterator.evaluate(evaluator, T_OrderedSet_pivot__Variable_, self, P_LoopExp_iterator);
				
				DomainType static_A_symbol_254 = valueFactory.typeOf(A_symbol_253);
				LibraryUnaryOperation dynamic_A_symbol_254 = (LibraryUnaryOperation)static_A_symbol_254.lookupImplementation(standardLibrary, O_Collection_size);
				Value A_symbol_254 = dynamic_A_symbol_254.evaluate(evaluator, T_Integer, A_symbol_253);
				DomainType static_A_symbol_255 = valueFactory.typeOf(A_symbol_254, I_1);
				LibraryBinaryOperation dynamic_A_symbol_255 = (LibraryBinaryOperation)static_A_symbol_255.lookupImplementation(standardLibrary, O_Real__eq_);
				Value A_symbol_255 = dynamic_A_symbol_255.evaluate(evaluator, T_Boolean, A_symbol_254, I_1);
				rightA_symbol_250 = A_symbol_255;
			} catch (InvalidValueException e) {
				rightA_symbol_250 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_255 = rightA_symbol_250;
			DomainType static_A_symbol_250 = valueFactory.typeOf(A_symbol_252);
			LibraryBinaryOperation dynamic_A_symbol_250 = (LibraryBinaryOperation)static_A_symbol_250.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_250 = dynamic_A_symbol_250.evaluate(evaluator, T_Boolean, A_symbol_252, A_symbol_255);
			return A_symbol_250;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'OneTypeIsBoolean' invariant.
	 */
	public static class _invariant_OneTypeIsBoolean extends AbstractUnaryOperation
	{
		public static _invariant_OneTypeIsBoolean INSTANCE = new _invariant_OneTypeIsBoolean();
	
		/*
		name = 'one' implies type = Boolean
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_one = valueFactory.stringValueOf("one");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_Boolean_ = valueFactory.createTypeValue(OCLstdlibTables.Types._Boolean);
			
			Value leftA_symbol_256;
			try {
				
				Value A_symbol_257 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_258 = valueFactory.typeOf(A_symbol_257, S_one);
				LibraryBinaryOperation dynamic_A_symbol_258 = (LibraryBinaryOperation)static_A_symbol_258.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_258 = dynamic_A_symbol_258.evaluate(evaluator, T_Boolean, A_symbol_257, S_one);
				leftA_symbol_256 = A_symbol_258;
			} catch (InvalidValueException e) {
				leftA_symbol_256 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_258 = leftA_symbol_256;
			Value rightA_symbol_256;
			try {
				
				Value A_symbol_259 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_260 = valueFactory.typeOf(A_symbol_259, T_ClassClassifier_Boolean_);
				LibraryBinaryOperation dynamic_A_symbol_260 = (LibraryBinaryOperation)static_A_symbol_260.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_260 = dynamic_A_symbol_260.evaluate(evaluator, T_Boolean, A_symbol_259, T_ClassClassifier_Boolean_);
				rightA_symbol_256 = A_symbol_260;
			} catch (InvalidValueException e) {
				rightA_symbol_256 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_260 = rightA_symbol_256;
			DomainType static_A_symbol_256 = valueFactory.typeOf(A_symbol_258);
			LibraryBinaryOperation dynamic_A_symbol_256 = (LibraryBinaryOperation)static_A_symbol_256.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_256 = dynamic_A_symbol_256.evaluate(evaluator, T_Boolean, A_symbol_258, A_symbol_260);
			return A_symbol_256;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'RejectOrSelectHasOneIterator' invariant.
	 */
	public static class _invariant_RejectOrSelectHasOneIterator extends AbstractUnaryOperation
	{
		public static _invariant_RejectOrSelectHasOneIterator INSTANCE = new _invariant_RejectOrSelectHasOneIterator();
	
		/*
		name = 'reject' or name = 'select' implies iterator->size() = 1
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_Boolean_or = OCLstdlibTables.Operations._Boolean__or;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_reject = valueFactory.stringValueOf("reject");
			final StringValue S_select = valueFactory.stringValueOf("select");
			final ExecutorOperation O_Real__eq_ = OCLstdlibTables.Operations._Real___eq_;
			final ExecutorType T_Integer = OCLstdlibTables.Types._Integer;
			final ExecutorOperation O_Collection_size = OCLstdlibTables.Operations._Collection__size;
			final ExecutorType T_pivot__Variable = PivotTables.Types._Variable;
			final DomainCollectionType T_OrderedSet_pivot__Variable_ = standardLibrary.getOrderedSetType(T_pivot__Variable);
			final ExecutorProperty P_LoopExp_iterator = PivotTables.Properties._LoopExp__iterator;
			final LibraryProperty IP_LoopExp_iterator = P_LoopExp_iterator.getImplementation();
			final IntegerValue I_1 = valueFactory.integerValueOf(1);
			
			Value leftA_symbol_261;
			try {
				Value leftA_symbol_262;
				try {
					
					Value A_symbol_263 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
					
					DomainType static_A_symbol_264 = valueFactory.typeOf(A_symbol_263, S_reject);
					LibraryBinaryOperation dynamic_A_symbol_264 = (LibraryBinaryOperation)static_A_symbol_264.lookupImplementation(standardLibrary, O_String__eq_);
					Value A_symbol_264 = dynamic_A_symbol_264.evaluate(evaluator, T_Boolean, A_symbol_263, S_reject);
					leftA_symbol_262 = A_symbol_264;
				} catch (InvalidValueException e) {
					leftA_symbol_262 = valueFactory.createInvalidValue(e);
				}
				Value A_symbol_264 = leftA_symbol_262;
				Value rightA_symbol_262;
				try {
					
					Value A_symbol_265 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
					
					DomainType static_A_symbol_266 = valueFactory.typeOf(A_symbol_265, S_select);
					LibraryBinaryOperation dynamic_A_symbol_266 = (LibraryBinaryOperation)static_A_symbol_266.lookupImplementation(standardLibrary, O_String__eq_);
					Value A_symbol_266 = dynamic_A_symbol_266.evaluate(evaluator, T_Boolean, A_symbol_265, S_select);
					rightA_symbol_262 = A_symbol_266;
				} catch (InvalidValueException e) {
					rightA_symbol_262 = valueFactory.createInvalidValue(e);
				}
				Value A_symbol_266 = rightA_symbol_262;
				DomainType static_A_symbol_262 = valueFactory.typeOf(A_symbol_264);
				LibraryBinaryOperation dynamic_A_symbol_262 = (LibraryBinaryOperation)static_A_symbol_262.lookupImplementation(standardLibrary, O_Boolean_or);
				Value A_symbol_262 = dynamic_A_symbol_262.evaluate(evaluator, T_Boolean, A_symbol_264, A_symbol_266);
				leftA_symbol_261 = A_symbol_262;
			} catch (InvalidValueException e) {
				leftA_symbol_261 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_262 = leftA_symbol_261;
			Value rightA_symbol_261;
			try {
				
				Value A_symbol_267 = IP_LoopExp_iterator.evaluate(evaluator, T_OrderedSet_pivot__Variable_, self, P_LoopExp_iterator);
				
				DomainType static_A_symbol_268 = valueFactory.typeOf(A_symbol_267);
				LibraryUnaryOperation dynamic_A_symbol_268 = (LibraryUnaryOperation)static_A_symbol_268.lookupImplementation(standardLibrary, O_Collection_size);
				Value A_symbol_268 = dynamic_A_symbol_268.evaluate(evaluator, T_Integer, A_symbol_267);
				DomainType static_A_symbol_269 = valueFactory.typeOf(A_symbol_268, I_1);
				LibraryBinaryOperation dynamic_A_symbol_269 = (LibraryBinaryOperation)static_A_symbol_269.lookupImplementation(standardLibrary, O_Real__eq_);
				Value A_symbol_269 = dynamic_A_symbol_269.evaluate(evaluator, T_Boolean, A_symbol_268, I_1);
				rightA_symbol_261 = A_symbol_269;
			} catch (InvalidValueException e) {
				rightA_symbol_261 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_269 = rightA_symbol_261;
			DomainType static_A_symbol_261 = valueFactory.typeOf(A_symbol_262);
			LibraryBinaryOperation dynamic_A_symbol_261 = (LibraryBinaryOperation)static_A_symbol_261.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_261 = dynamic_A_symbol_261.evaluate(evaluator, T_Boolean, A_symbol_262, A_symbol_269);
			return A_symbol_261;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'RejectOrSelectTypeIsBoolean' invariant.
	 */
	public static class _invariant_RejectOrSelectTypeIsBoolean extends AbstractUnaryOperation
	{
		public static _invariant_RejectOrSelectTypeIsBoolean INSTANCE = new _invariant_RejectOrSelectTypeIsBoolean();
	
		/*
		name = 'reject' or name = 'select' implies type = Boolean
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_Boolean_or = OCLstdlibTables.Operations._Boolean__or;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_reject = valueFactory.stringValueOf("reject");
			final StringValue S_select = valueFactory.stringValueOf("select");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_Boolean_ = valueFactory.createTypeValue(OCLstdlibTables.Types._Boolean);
			
			Value leftA_symbol_270;
			try {
				Value leftA_symbol_271;
				try {
					
					Value A_symbol_272 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
					
					DomainType static_A_symbol_273 = valueFactory.typeOf(A_symbol_272, S_reject);
					LibraryBinaryOperation dynamic_A_symbol_273 = (LibraryBinaryOperation)static_A_symbol_273.lookupImplementation(standardLibrary, O_String__eq_);
					Value A_symbol_273 = dynamic_A_symbol_273.evaluate(evaluator, T_Boolean, A_symbol_272, S_reject);
					leftA_symbol_271 = A_symbol_273;
				} catch (InvalidValueException e) {
					leftA_symbol_271 = valueFactory.createInvalidValue(e);
				}
				Value A_symbol_273 = leftA_symbol_271;
				Value rightA_symbol_271;
				try {
					
					Value A_symbol_274 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
					
					DomainType static_A_symbol_275 = valueFactory.typeOf(A_symbol_274, S_select);
					LibraryBinaryOperation dynamic_A_symbol_275 = (LibraryBinaryOperation)static_A_symbol_275.lookupImplementation(standardLibrary, O_String__eq_);
					Value A_symbol_275 = dynamic_A_symbol_275.evaluate(evaluator, T_Boolean, A_symbol_274, S_select);
					rightA_symbol_271 = A_symbol_275;
				} catch (InvalidValueException e) {
					rightA_symbol_271 = valueFactory.createInvalidValue(e);
				}
				Value A_symbol_275 = rightA_symbol_271;
				DomainType static_A_symbol_271 = valueFactory.typeOf(A_symbol_273);
				LibraryBinaryOperation dynamic_A_symbol_271 = (LibraryBinaryOperation)static_A_symbol_271.lookupImplementation(standardLibrary, O_Boolean_or);
				Value A_symbol_271 = dynamic_A_symbol_271.evaluate(evaluator, T_Boolean, A_symbol_273, A_symbol_275);
				leftA_symbol_270 = A_symbol_271;
			} catch (InvalidValueException e) {
				leftA_symbol_270 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_271 = leftA_symbol_270;
			Value rightA_symbol_270;
			try {
				
				Value A_symbol_276 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_277 = valueFactory.typeOf(A_symbol_276, T_ClassClassifier_Boolean_);
				LibraryBinaryOperation dynamic_A_symbol_277 = (LibraryBinaryOperation)static_A_symbol_277.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_277 = dynamic_A_symbol_277.evaluate(evaluator, T_Boolean, A_symbol_276, T_ClassClassifier_Boolean_);
				rightA_symbol_270 = A_symbol_277;
			} catch (InvalidValueException e) {
				rightA_symbol_270 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_277 = rightA_symbol_270;
			DomainType static_A_symbol_270 = valueFactory.typeOf(A_symbol_271);
			LibraryBinaryOperation dynamic_A_symbol_270 = (LibraryBinaryOperation)static_A_symbol_270.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_270 = dynamic_A_symbol_270.evaluate(evaluator, T_Boolean, A_symbol_271, A_symbol_277);
			return A_symbol_270;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'RejectOrSelectTypeIsSourceType' invariant.
	 */
	public static class _invariant_RejectOrSelectTypeIsSourceType extends AbstractUnaryOperation
	{
		public static _invariant_RejectOrSelectTypeIsSourceType INSTANCE = new _invariant_RejectOrSelectTypeIsSourceType();
	
		/*
		name = 'reject' or name = 'select' implies type = source.type
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_Boolean_or = OCLstdlibTables.Operations._Boolean__or;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_reject = valueFactory.stringValueOf("reject");
			final StringValue S_select = valueFactory.stringValueOf("select");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_CallExp_source = PivotTables.Properties._CallExp__source;
			final LibraryProperty IP_CallExp_source = P_CallExp_source.getImplementation();
			
			Value leftA_symbol_278;
			try {
				Value leftA_symbol_279;
				try {
					
					Value A_symbol_280 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
					
					DomainType static_A_symbol_281 = valueFactory.typeOf(A_symbol_280, S_reject);
					LibraryBinaryOperation dynamic_A_symbol_281 = (LibraryBinaryOperation)static_A_symbol_281.lookupImplementation(standardLibrary, O_String__eq_);
					Value A_symbol_281 = dynamic_A_symbol_281.evaluate(evaluator, T_Boolean, A_symbol_280, S_reject);
					leftA_symbol_279 = A_symbol_281;
				} catch (InvalidValueException e) {
					leftA_symbol_279 = valueFactory.createInvalidValue(e);
				}
				Value A_symbol_281 = leftA_symbol_279;
				Value rightA_symbol_279;
				try {
					
					Value A_symbol_282 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
					
					DomainType static_A_symbol_283 = valueFactory.typeOf(A_symbol_282, S_select);
					LibraryBinaryOperation dynamic_A_symbol_283 = (LibraryBinaryOperation)static_A_symbol_283.lookupImplementation(standardLibrary, O_String__eq_);
					Value A_symbol_283 = dynamic_A_symbol_283.evaluate(evaluator, T_Boolean, A_symbol_282, S_select);
					rightA_symbol_279 = A_symbol_283;
				} catch (InvalidValueException e) {
					rightA_symbol_279 = valueFactory.createInvalidValue(e);
				}
				Value A_symbol_283 = rightA_symbol_279;
				DomainType static_A_symbol_279 = valueFactory.typeOf(A_symbol_281);
				LibraryBinaryOperation dynamic_A_symbol_279 = (LibraryBinaryOperation)static_A_symbol_279.lookupImplementation(standardLibrary, O_Boolean_or);
				Value A_symbol_279 = dynamic_A_symbol_279.evaluate(evaluator, T_Boolean, A_symbol_281, A_symbol_283);
				leftA_symbol_278 = A_symbol_279;
			} catch (InvalidValueException e) {
				leftA_symbol_278 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_279 = leftA_symbol_278;
			Value rightA_symbol_278;
			try {
				
				Value A_symbol_284 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				
				Value A_symbol_285 = IP_CallExp_source.evaluate(evaluator, T_pivot__OCLExpression, self, P_CallExp_source);
				
				Value A_symbol_286 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_285, P_TypedElement_type);
				
				DomainType static_A_symbol_287 = valueFactory.typeOf(A_symbol_284, A_symbol_286);
				LibraryBinaryOperation dynamic_A_symbol_287 = (LibraryBinaryOperation)static_A_symbol_287.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_287 = dynamic_A_symbol_287.evaluate(evaluator, T_Boolean, A_symbol_284, A_symbol_286);
				rightA_symbol_278 = A_symbol_287;
			} catch (InvalidValueException e) {
				rightA_symbol_278 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_287 = rightA_symbol_278;
			DomainType static_A_symbol_278 = valueFactory.typeOf(A_symbol_279);
			LibraryBinaryOperation dynamic_A_symbol_278 = (LibraryBinaryOperation)static_A_symbol_278.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_278 = dynamic_A_symbol_278.evaluate(evaluator, T_Boolean, A_symbol_279, A_symbol_287);
			return A_symbol_278;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'SortedByElementTypeIsSourceElementType' invariant.
	 */
	public static class _invariant_SortedByElementTypeIsSourceElementType extends AbstractUnaryOperation
	{
		public static _invariant_SortedByElementTypeIsSourceElementType INSTANCE = new _invariant_SortedByElementTypeIsSourceElementType();
	
		/*
		name = 'sortedBy' implies
	type.oclAsType(CollectionType).elementType =
	body.type.oclAsType(CollectionType).elementType
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_sortedBy = valueFactory.stringValueOf("sortedBy");
			final ExecutorOperation O_OclAny__eq_ = OCLstdlibTables.Operations._OclAny___eq_;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_CollectionType_elementType = PivotTables.Properties._CollectionType__elementType;
			final LibraryProperty IP_CollectionType_elementType = P_CollectionType_elementType.getImplementation();
			final ExecutorType T_pivot__CollectionType = PivotTables.Types._CollectionType;
			final ExecutorOperation O_OclAny_oclAsType = OCLstdlibTables.Operations._OclAny__oclAsType;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final Value T_ClassClassifier_pivot__CollectionType_ = valueFactory.createTypeValue(PivotTables.Types._CollectionType);
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_LoopExp_body = PivotTables.Properties._LoopExp__body;
			final LibraryProperty IP_LoopExp_body = P_LoopExp_body.getImplementation();
			
			Value leftA_symbol_288;
			try {
				
				Value A_symbol_289 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_290 = valueFactory.typeOf(A_symbol_289, S_sortedBy);
				LibraryBinaryOperation dynamic_A_symbol_290 = (LibraryBinaryOperation)static_A_symbol_290.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_290 = dynamic_A_symbol_290.evaluate(evaluator, T_Boolean, A_symbol_289, S_sortedBy);
				leftA_symbol_288 = A_symbol_290;
			} catch (InvalidValueException e) {
				leftA_symbol_288 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_290 = leftA_symbol_288;
			Value rightA_symbol_288;
			try {
				
				Value A_symbol_291 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_292 = valueFactory.typeOf(A_symbol_291);
				LibraryBinaryOperation dynamic_A_symbol_292 = (LibraryBinaryOperation)static_A_symbol_292.lookupImplementation(standardLibrary, O_OclAny_oclAsType);
				Value A_symbol_292 = dynamic_A_symbol_292.evaluate(evaluator, T_pivot__CollectionType, A_symbol_291, T_ClassClassifier_pivot__CollectionType_);
				Value A_symbol_293 = IP_CollectionType_elementType.evaluate(evaluator, T_Type, A_symbol_292, P_CollectionType_elementType);
				
				
				Value A_symbol_294 = IP_LoopExp_body.evaluate(evaluator, T_pivot__OCLExpression, self, P_LoopExp_body);
				
				Value A_symbol_295 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_294, P_TypedElement_type);
				
				DomainType static_A_symbol_296 = valueFactory.typeOf(A_symbol_295);
				LibraryBinaryOperation dynamic_A_symbol_296 = (LibraryBinaryOperation)static_A_symbol_296.lookupImplementation(standardLibrary, O_OclAny_oclAsType);
				Value A_symbol_296 = dynamic_A_symbol_296.evaluate(evaluator, T_pivot__CollectionType, A_symbol_295, T_ClassClassifier_pivot__CollectionType_);
				Value A_symbol_297 = IP_CollectionType_elementType.evaluate(evaluator, T_Type, A_symbol_296, P_CollectionType_elementType);
				
				DomainType static_A_symbol_298 = valueFactory.typeOf(A_symbol_293, A_symbol_297);
				LibraryBinaryOperation dynamic_A_symbol_298 = (LibraryBinaryOperation)static_A_symbol_298.lookupImplementation(standardLibrary, O_OclAny__eq_);
				Value A_symbol_298 = dynamic_A_symbol_298.evaluate(evaluator, T_Boolean, A_symbol_293, A_symbol_297);
				rightA_symbol_288 = A_symbol_298;
			} catch (InvalidValueException e) {
				rightA_symbol_288 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_298 = rightA_symbol_288;
			DomainType static_A_symbol_288 = valueFactory.typeOf(A_symbol_290);
			LibraryBinaryOperation dynamic_A_symbol_288 = (LibraryBinaryOperation)static_A_symbol_288.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_288 = dynamic_A_symbol_288.evaluate(evaluator, T_Boolean, A_symbol_290, A_symbol_298);
			return A_symbol_288;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'SortedByHasOneIterator' invariant.
	 */
	public static class _invariant_SortedByHasOneIterator extends AbstractUnaryOperation
	{
		public static _invariant_SortedByHasOneIterator INSTANCE = new _invariant_SortedByHasOneIterator();
	
		/*
		name = 'sortedBy' implies iterator->size() = 1
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_sortedBy = valueFactory.stringValueOf("sortedBy");
			final ExecutorOperation O_Real__eq_ = OCLstdlibTables.Operations._Real___eq_;
			final ExecutorType T_Integer = OCLstdlibTables.Types._Integer;
			final ExecutorOperation O_Collection_size = OCLstdlibTables.Operations._Collection__size;
			final ExecutorType T_pivot__Variable = PivotTables.Types._Variable;
			final DomainCollectionType T_OrderedSet_pivot__Variable_ = standardLibrary.getOrderedSetType(T_pivot__Variable);
			final ExecutorProperty P_LoopExp_iterator = PivotTables.Properties._LoopExp__iterator;
			final LibraryProperty IP_LoopExp_iterator = P_LoopExp_iterator.getImplementation();
			final IntegerValue I_1 = valueFactory.integerValueOf(1);
			
			Value leftA_symbol_299;
			try {
				
				Value A_symbol_300 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_301 = valueFactory.typeOf(A_symbol_300, S_sortedBy);
				LibraryBinaryOperation dynamic_A_symbol_301 = (LibraryBinaryOperation)static_A_symbol_301.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_301 = dynamic_A_symbol_301.evaluate(evaluator, T_Boolean, A_symbol_300, S_sortedBy);
				leftA_symbol_299 = A_symbol_301;
			} catch (InvalidValueException e) {
				leftA_symbol_299 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_301 = leftA_symbol_299;
			Value rightA_symbol_299;
			try {
				
				Value A_symbol_302 = IP_LoopExp_iterator.evaluate(evaluator, T_OrderedSet_pivot__Variable_, self, P_LoopExp_iterator);
				
				DomainType static_A_symbol_303 = valueFactory.typeOf(A_symbol_302);
				LibraryUnaryOperation dynamic_A_symbol_303 = (LibraryUnaryOperation)static_A_symbol_303.lookupImplementation(standardLibrary, O_Collection_size);
				Value A_symbol_303 = dynamic_A_symbol_303.evaluate(evaluator, T_Integer, A_symbol_302);
				DomainType static_A_symbol_304 = valueFactory.typeOf(A_symbol_303, I_1);
				LibraryBinaryOperation dynamic_A_symbol_304 = (LibraryBinaryOperation)static_A_symbol_304.lookupImplementation(standardLibrary, O_Real__eq_);
				Value A_symbol_304 = dynamic_A_symbol_304.evaluate(evaluator, T_Boolean, A_symbol_303, I_1);
				rightA_symbol_299 = A_symbol_304;
			} catch (InvalidValueException e) {
				rightA_symbol_299 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_304 = rightA_symbol_299;
			DomainType static_A_symbol_299 = valueFactory.typeOf(A_symbol_301);
			LibraryBinaryOperation dynamic_A_symbol_299 = (LibraryBinaryOperation)static_A_symbol_299.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_299 = dynamic_A_symbol_299.evaluate(evaluator, T_Boolean, A_symbol_301, A_symbol_304);
			return A_symbol_299;
		}
	}

	/** 
	 * Implementation of the IteratorExp 'SortedByIsOrderedIfSourceIsOrdered' invariant.
	 */
	public static class _invariant_SortedByIsOrderedIfSourceIsOrdered extends AbstractUnaryOperation
	{
		public static _invariant_SortedByIsOrderedIfSourceIsOrdered INSTANCE = new _invariant_SortedByIsOrderedIfSourceIsOrdered();
	
		/*
		name = 'sortedBy' implies
	if
	  source.type.oclIsKindOf(SequenceType) or
	  source.type.oclIsKindOf(BagType)
	then type.oclIsKindOf(SequenceType)
	else type.oclIsKindOf(OrderedSetType)
	endif
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final ExecutorType T_String = OCLstdlibTables.Types._String;
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final StringValue S_sortedBy = valueFactory.stringValueOf("sortedBy");
			final ExecutorOperation O_Boolean_or = OCLstdlibTables.Operations._Boolean__or;
			final ExecutorOperation O_OclAny_oclIsKindOf = OCLstdlibTables.Operations._OclAny__oclIsKindOf;
			final ExecutorType T_Type = OCLstdlibTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorType T_pivot__OCLExpression = PivotTables.Types._OCLExpression;
			final ExecutorProperty P_CallExp_source = PivotTables.Properties._CallExp__source;
			final LibraryProperty IP_CallExp_source = P_CallExp_source.getImplementation();
			final Value T_ClassClassifier_pivot__SequenceType_ = valueFactory.createTypeValue(PivotTables.Types._SequenceType);
			final Value T_ClassClassifier_pivot__BagType_ = valueFactory.createTypeValue(PivotTables.Types._BagType);
			final Value T_ClassClassifier_pivot__OrderedSetType_ = valueFactory.createTypeValue(PivotTables.Types._OrderedSetType);
			
			Value leftA_symbol_305;
			try {
				
				Value A_symbol_306 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
				
				DomainType static_A_symbol_307 = valueFactory.typeOf(A_symbol_306, S_sortedBy);
				LibraryBinaryOperation dynamic_A_symbol_307 = (LibraryBinaryOperation)static_A_symbol_307.lookupImplementation(standardLibrary, O_String__eq_);
				Value A_symbol_307 = dynamic_A_symbol_307.evaluate(evaluator, T_Boolean, A_symbol_306, S_sortedBy);
				leftA_symbol_305 = A_symbol_307;
			} catch (InvalidValueException e) {
				leftA_symbol_305 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_307 = leftA_symbol_305;
			Value rightA_symbol_305;
			try {
					Value leftA_symbol_308;
					try {
						
						Value A_symbol_309 = IP_CallExp_source.evaluate(evaluator, T_pivot__OCLExpression, self, P_CallExp_source);
						
						Value A_symbol_310 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_309, P_TypedElement_type);
						
						DomainType static_A_symbol_311 = valueFactory.typeOf(A_symbol_310);
						LibraryBinaryOperation dynamic_A_symbol_311 = (LibraryBinaryOperation)static_A_symbol_311.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
						Value A_symbol_311 = dynamic_A_symbol_311.evaluate(evaluator, T_Boolean, A_symbol_310, T_ClassClassifier_pivot__SequenceType_);
						leftA_symbol_308 = A_symbol_311;
					} catch (InvalidValueException e) {
						leftA_symbol_308 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_311 = leftA_symbol_308;
					Value rightA_symbol_308;
					try {
						
						Value A_symbol_312 = IP_CallExp_source.evaluate(evaluator, T_pivot__OCLExpression, self, P_CallExp_source);
						
						Value A_symbol_313 = IP_TypedElement_type.evaluate(evaluator, T_Type, A_symbol_312, P_TypedElement_type);
						
						DomainType static_A_symbol_314 = valueFactory.typeOf(A_symbol_313);
						LibraryBinaryOperation dynamic_A_symbol_314 = (LibraryBinaryOperation)static_A_symbol_314.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
						Value A_symbol_314 = dynamic_A_symbol_314.evaluate(evaluator, T_Boolean, A_symbol_313, T_ClassClassifier_pivot__BagType_);
						rightA_symbol_308 = A_symbol_314;
					} catch (InvalidValueException e) {
						rightA_symbol_308 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_314 = rightA_symbol_308;
					DomainType static_A_symbol_308 = valueFactory.typeOf(A_symbol_311);
					LibraryBinaryOperation dynamic_A_symbol_308 = (LibraryBinaryOperation)static_A_symbol_308.lookupImplementation(standardLibrary, O_Boolean_or);
					Value A_symbol_308 = dynamic_A_symbol_308.evaluate(evaluator, T_Boolean, A_symbol_311, A_symbol_314);
				Value A_symbol_315;
				if (A_symbol_308.isTrue()) {
					
					Value A_symbol_316 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
					
					DomainType static_A_symbol_317 = valueFactory.typeOf(A_symbol_316);
					LibraryBinaryOperation dynamic_A_symbol_317 = (LibraryBinaryOperation)static_A_symbol_317.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
					Value A_symbol_317 = dynamic_A_symbol_317.evaluate(evaluator, T_Boolean, A_symbol_316, T_ClassClassifier_pivot__SequenceType_);
					A_symbol_315 = A_symbol_317;
				}
				else if (A_symbol_308.isFalse()) {
					
					Value A_symbol_318 = IP_TypedElement_type.evaluate(evaluator, T_Type, self, P_TypedElement_type);
					
					DomainType static_A_symbol_319 = valueFactory.typeOf(A_symbol_318);
					LibraryBinaryOperation dynamic_A_symbol_319 = (LibraryBinaryOperation)static_A_symbol_319.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
					Value A_symbol_319 = dynamic_A_symbol_319.evaluate(evaluator, T_Boolean, A_symbol_318, T_ClassClassifier_pivot__OrderedSetType_);
					A_symbol_315 = A_symbol_319;
				}
				else if (A_symbol_308.isNull()) {
					A_symbol_315 = valueFactory.throwInvalidValueException("null if condition");
				}
				else {
					A_symbol_315 = valueFactory.throwInvalidValueException("invalid if condition");
				}
				rightA_symbol_305 = A_symbol_315;
			} catch (InvalidValueException e) {
				rightA_symbol_305 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_315 = rightA_symbol_305;
			DomainType static_A_symbol_305 = valueFactory.typeOf(A_symbol_307);
			LibraryBinaryOperation dynamic_A_symbol_305 = (LibraryBinaryOperation)static_A_symbol_305.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_305 = dynamic_A_symbol_305.evaluate(evaluator, T_Boolean, A_symbol_307, A_symbol_315);
			return A_symbol_305;
		}
	}
}

