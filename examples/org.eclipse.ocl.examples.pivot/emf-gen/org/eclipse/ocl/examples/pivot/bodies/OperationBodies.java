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

import org.eclipse.jdt.annotation.NonNull;
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
import org.eclipse.ocl.examples.domain.values.CollectionValue;
import org.eclipse.ocl.examples.domain.values.NullValue;
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
 * OperationBodies provides the Java implementation bodies of OCL-defined Operation operations and properties.
 */
@SuppressWarnings("nls")
public class OperationBodies
{

	/** 
	 * Implementation of the Operation 'CompatibleReturn' invariant.
	 */
	public static class _invariant_CompatibleReturn extends AbstractUnaryOperation
	{
		public static @NonNull _invariant_CompatibleReturn INSTANCE = new _invariant_CompatibleReturn();
	
		/*
		let
	  bodyConstraint : Constraint = ownedRule->any(stereotype = 'body')
	in bodyConstraint <> null implies
	  let bodySpecification : ValueSpecification = bodyConstraint.specification
	  in bodySpecification <> null and
	    bodySpecification.oclIsKindOf(ExpressionInOCL) implies
	    CompatibleBody(bodySpecification)
		*/
		public @NonNull Value evaluate(@NonNull DomainEvaluator evaluator, @NonNull DomainType returnType, final @NonNull Value self) throws InvalidValueException {
			final @NonNull ValueFactory valueFactory = evaluator.getValueFactory();
			final @NonNull DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final @NonNull ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final @NonNull ExecutorOperation O_Boolean_implies = OCLstdlibTables.Operations._Boolean__implies;
			final @NonNull ExecutorOperation O_OclAny__lt__gt_ = OCLstdlibTables.Operations._OclAny___lt__gt_;
			final @NonNull NullValue Null = valueFactory.getNull();
			final @NonNull ExecutorOperation O_Boolean_and = OCLstdlibTables.Operations._Boolean__and;
			final @NonNull ExecutorOperation O_OclAny_oclIsKindOf = OCLstdlibTables.Operations._OclAny__oclIsKindOf;
			final @NonNull Value T_ClassClassifier_pivot__ExpressionInOCL_ = valueFactory.createTypeValue(PivotTables.Types._ExpressionInOCL);
			final @NonNull ExecutorOperation O_TypedMultiplicityElement_CompatibleBody = PivotTables.Operations._TypedMultiplicityElement__CompatibleBody;
			final @NonNull ExecutorType T_pivot__ValueSpecification = PivotTables.Types._ValueSpecification;
			final @NonNull ExecutorProperty P_Constraint_specification = PivotTables.Properties._Constraint__specification;
			final @NonNull LibraryProperty IP_Constraint_specification = P_Constraint_specification.getImplementation();
			final @NonNull ExecutorType T_pivot__Constraint = PivotTables.Types._Constraint;
			final @NonNull ExecutorOperation O_Collection_any = OCLstdlibTables.Operations._Collection__any;
			final @NonNull DomainCollectionType T_OrderedSet_pivot__Constraint_ = standardLibrary.getOrderedSetType(T_pivot__Constraint);
			final @NonNull ExecutorProperty P_NamedElement_ownedRule = PivotTables.Properties._NamedElement__ownedRule;
			final @NonNull LibraryProperty IP_NamedElement_ownedRule = P_NamedElement_ownedRule.getImplementation();
			final @NonNull ExecutorOperation O_String__eq_ = OCLstdlibTables.Operations._String___eq_;
			final @NonNull ExecutorType T_String = OCLstdlibTables.Types._String;
			final @NonNull ExecutorProperty P_Constraint_stereotype = PivotTables.Properties._Constraint__stereotype;
			final @NonNull LibraryProperty IP_Constraint_stereotype = P_Constraint_stereotype.getImplementation();
			final @NonNull StringValue S_body = valueFactory.stringValueOf("body");
			
			
			Value A_symbol_351 = IP_NamedElement_ownedRule.evaluate(evaluator, T_OrderedSet_pivot__Constraint_, self, P_NamedElement_ownedRule);
			
			
			/** 
			 * Implementation of the iterator body.
			 */
			AbstractBinaryOperation body_A_symbol_352 = new AbstractBinaryOperation()
			{
			/*
			stereotype = 'body'
			*/
				public @NonNull Value evaluate(@NonNull DomainEvaluator evaluator, @NonNull DomainType returnType, @NonNull Value sourceValue, @NonNull Value iterator1) throws InvalidValueException {
					final @NonNull Value V_1_ = iterator1;	// iterator: 1_
					
					Value A_symbol_353 = IP_Constraint_stereotype.evaluate(evaluator, T_String, V_1_, P_Constraint_stereotype);
					
					DomainType static_A_symbol_354 = valueFactory.typeOf(A_symbol_353, S_body);
					LibraryBinaryOperation dynamic_A_symbol_354 = (LibraryBinaryOperation)static_A_symbol_354.lookupImplementation(standardLibrary, O_String__eq_);
					Value A_symbol_354 = dynamic_A_symbol_354.evaluate(evaluator, T_Boolean, A_symbol_353, S_body);
					return A_symbol_354;
				}
			};
			DomainType static_A_symbol_352 = A_symbol_351.getType();
			LibraryIteration dynamic_A_symbol_352 = (LibraryIteration)static_A_symbol_352.lookupImplementation(standardLibrary, O_Collection_any);
			Value acc_A_symbol_352 = dynamic_A_symbol_352.createAccumulatorValue(evaluator, T_pivot__Constraint, T_Boolean);
			ExecutorSingleIterationManager manager_A_symbol_352 = new ExecutorSingleIterationManager(evaluator, T_pivot__Constraint, body_A_symbol_352, (CollectionValue)A_symbol_351, acc_A_symbol_352);
			Value A_symbol_352 = dynamic_A_symbol_352.evaluateIteration(manager_A_symbol_352);
			final @NonNull Value V_bodyConstraint = A_symbol_352;
			Value leftA_symbol_355;
			try {
				
				DomainType static_A_symbol_356 = valueFactory.typeOf(V_bodyConstraint, Null);
				LibraryBinaryOperation dynamic_A_symbol_356 = (LibraryBinaryOperation)static_A_symbol_356.lookupImplementation(standardLibrary, O_OclAny__lt__gt_);
				Value A_symbol_356 = dynamic_A_symbol_356.evaluate(evaluator, T_Boolean, V_bodyConstraint, Null);
				leftA_symbol_355 = A_symbol_356;
			} catch (InvalidValueException e) {
				leftA_symbol_355 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_356 = leftA_symbol_355;
			Value rightA_symbol_355;
			try {
				
				Value A_symbol_357 = IP_Constraint_specification.evaluate(evaluator, T_pivot__ValueSpecification, V_bodyConstraint, P_Constraint_specification);
				
				final @NonNull Value V_bodySpecification = A_symbol_357;
				Value leftA_symbol_358;
				try {
					Value leftA_symbol_359;
					try {
						
						DomainType static_A_symbol_360 = valueFactory.typeOf(V_bodySpecification, Null);
						LibraryBinaryOperation dynamic_A_symbol_360 = (LibraryBinaryOperation)static_A_symbol_360.lookupImplementation(standardLibrary, O_OclAny__lt__gt_);
						Value A_symbol_360 = dynamic_A_symbol_360.evaluate(evaluator, T_Boolean, V_bodySpecification, Null);
						leftA_symbol_359 = A_symbol_360;
					} catch (InvalidValueException e) {
						leftA_symbol_359 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_360 = leftA_symbol_359;
					Value rightA_symbol_359;
					try {
						
						DomainType static_A_symbol_361 = valueFactory.typeOf(V_bodySpecification);
						LibraryBinaryOperation dynamic_A_symbol_361 = (LibraryBinaryOperation)static_A_symbol_361.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
						Value A_symbol_361 = dynamic_A_symbol_361.evaluate(evaluator, T_Boolean, V_bodySpecification, T_ClassClassifier_pivot__ExpressionInOCL_);
						rightA_symbol_359 = A_symbol_361;
					} catch (InvalidValueException e) {
						rightA_symbol_359 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_361 = rightA_symbol_359;
					DomainType static_A_symbol_359 = valueFactory.typeOf(A_symbol_360);
					LibraryBinaryOperation dynamic_A_symbol_359 = (LibraryBinaryOperation)static_A_symbol_359.lookupImplementation(standardLibrary, O_Boolean_and);
					Value A_symbol_359 = dynamic_A_symbol_359.evaluate(evaluator, T_Boolean, A_symbol_360, A_symbol_361);
					leftA_symbol_358 = A_symbol_359;
				} catch (InvalidValueException e) {
					leftA_symbol_358 = valueFactory.createInvalidValue(e);
				}
				Value A_symbol_359 = leftA_symbol_358;
				Value rightA_symbol_358;
				try {
					
					
					DomainType static_A_symbol_362 = valueFactory.typeOf(self);
					LibraryBinaryOperation dynamic_A_symbol_362 = (LibraryBinaryOperation)static_A_symbol_362.lookupImplementation(standardLibrary, O_TypedMultiplicityElement_CompatibleBody);
					Value A_symbol_362 = dynamic_A_symbol_362.evaluate(evaluator, T_Boolean, self, V_bodySpecification);
					rightA_symbol_358 = A_symbol_362;
				} catch (InvalidValueException e) {
					rightA_symbol_358 = valueFactory.createInvalidValue(e);
				}
				Value A_symbol_362 = rightA_symbol_358;
				DomainType static_A_symbol_358 = valueFactory.typeOf(A_symbol_359);
				LibraryBinaryOperation dynamic_A_symbol_358 = (LibraryBinaryOperation)static_A_symbol_358.lookupImplementation(standardLibrary, O_Boolean_implies);
				Value A_symbol_358 = dynamic_A_symbol_358.evaluate(evaluator, T_Boolean, A_symbol_359, A_symbol_362);
				final @NonNull Value A_symbol_363 = A_symbol_358;
				rightA_symbol_355 = A_symbol_363;
			} catch (InvalidValueException e) {
				rightA_symbol_355 = valueFactory.createInvalidValue(e);
			}
			Value A_symbol_363 = rightA_symbol_355;
			DomainType static_A_symbol_355 = valueFactory.typeOf(A_symbol_356);
			LibraryBinaryOperation dynamic_A_symbol_355 = (LibraryBinaryOperation)static_A_symbol_355.lookupImplementation(standardLibrary, O_Boolean_implies);
			Value A_symbol_355 = dynamic_A_symbol_355.evaluate(evaluator, T_Boolean, A_symbol_356, A_symbol_363);
			final @NonNull Value A_symbol_364 = A_symbol_355;
			return A_symbol_364;
		}
	}










}

