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
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.domain.values.ValueFactory;
import org.eclipse.ocl.examples.library.executor.ExecutorOperation;
import org.eclipse.ocl.examples.library.executor.ExecutorProperty;
import org.eclipse.ocl.examples.library.executor.ExecutorSingleIterationManager;
import org.eclipse.ocl.examples.library.executor.ExecutorType;
import org.eclipse.ocl.examples.library.oclstdlib.OCLstdlibTables;
import org.eclipse.ocl.examples.pivot.PivotTables;

/**
 * ConstraintBodies provides the Java implementation bodies of OCL-defined Constraint operations and properties.
 */
@SuppressWarnings("nls")
public class ConstraintBodies
{

	/** 
	 * Implementation of the Constraint 'UniqueName' invariant.
	 */
	public static class _invariant_UniqueName extends AbstractUnaryOperation
	{
		public static @NonNull _invariant_UniqueName INSTANCE = new _invariant_UniqueName();
	
		/*
		context.ownedRule->excluding(self)
	->forAll(name <> self.name or stereotype <> self.stereotype)
		*/
		public @NonNull Value evaluate(@NonNull DomainEvaluator evaluator, @NonNull DomainType returnType, final @NonNull Value self) throws InvalidValueException {
			final @NonNull ValueFactory valueFactory = evaluator.getValueFactory();
			final @NonNull DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final @NonNull ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final @NonNull ExecutorOperation O_Collection_forAll = OCLstdlibTables.Operations._Collection__1_forAll;
			final @NonNull ExecutorType T_pivot__Constraint = PivotTables.Types._Constraint;
			final @NonNull DomainCollectionType T_OrderedSet_pivot__Constraint_ = standardLibrary.getOrderedSetType(T_pivot__Constraint, null, null);
			final @NonNull ExecutorOperation O_OrderedSet_excluding = OCLstdlibTables.Operations._OrderedSet__excluding;
			final @NonNull ExecutorProperty P_NamedElement_ownedRule = PivotTables.Properties._NamedElement__ownedRule;
			final @NonNull LibraryProperty IP_NamedElement_ownedRule = P_NamedElement_ownedRule.getImplementation();
			final @NonNull ExecutorType T_pivot__NamedElement = PivotTables.Types._NamedElement;
			final @NonNull ExecutorProperty P_Constraint_context = PivotTables.Properties._Constraint__context;
			final @NonNull LibraryProperty IP_Constraint_context = P_Constraint_context.getImplementation();
			final @NonNull ExecutorOperation O_Boolean_or = OCLstdlibTables.Operations._Boolean__or;
			final @NonNull ExecutorOperation O_String__lt__gt_ = OCLstdlibTables.Operations._String___lt__gt_;
			final @NonNull ExecutorType T_String = OCLstdlibTables.Types._String;
			final @NonNull ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final @NonNull LibraryProperty IP_NamedElement_name = P_NamedElement_name.getImplementation();
			final @NonNull ExecutorProperty P_Constraint_stereotype = PivotTables.Properties._Constraint__stereotype;
			final @NonNull LibraryProperty IP_Constraint_stereotype = P_Constraint_stereotype.getImplementation();
			
			
			Value A_symbol_2 = IP_Constraint_context.evaluate(evaluator, T_pivot__NamedElement, self, P_Constraint_context);
			
			Value A_symbol_3 = IP_NamedElement_ownedRule.evaluate(evaluator, T_OrderedSet_pivot__Constraint_, A_symbol_2, P_NamedElement_ownedRule);
			
			
			DomainType static_A_symbol_0 = valueFactory.typeOf(A_symbol_3);
			LibraryBinaryOperation dynamic_A_symbol_0 = (LibraryBinaryOperation)static_A_symbol_0.lookupImplementation(standardLibrary, O_OrderedSet_excluding);
			Value A_symbol_0 = dynamic_A_symbol_0.evaluate(evaluator, T_OrderedSet_pivot__Constraint_, A_symbol_3, self);
			
			/** 
			 * Implementation of the iterator body.
			 */
			AbstractBinaryOperation body_A_symbol_1 = new AbstractBinaryOperation()
			{
			/*
			name <> self.name or stereotype <> self.stereotype
			*/
				public @NonNull Value evaluate(@NonNull DomainEvaluator evaluator, @NonNull DomainType returnType, @NonNull Value sourceValue, @NonNull Value iterator1) throws InvalidValueException {
					final @NonNull Value V_1_ = iterator1;	// iterator: 1_
					Value leftA_symbol_4;
					try {
						
						Value A_symbol_5 = IP_NamedElement_name.evaluate(evaluator, T_String, V_1_, P_NamedElement_name);
						
						
						Value A_symbol_6 = IP_NamedElement_name.evaluate(evaluator, T_String, self, P_NamedElement_name);
						
						DomainType static_A_symbol_7 = valueFactory.typeOf(A_symbol_5, A_symbol_6);
						LibraryBinaryOperation dynamic_A_symbol_7 = (LibraryBinaryOperation)static_A_symbol_7.lookupImplementation(standardLibrary, O_String__lt__gt_);
						Value A_symbol_7 = dynamic_A_symbol_7.evaluate(evaluator, T_Boolean, A_symbol_5, A_symbol_6);
						leftA_symbol_4 = A_symbol_7;
					} catch (InvalidValueException e) {
						leftA_symbol_4 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_7 = leftA_symbol_4;
					Value rightA_symbol_4;
					try {
						
						Value A_symbol_8 = IP_Constraint_stereotype.evaluate(evaluator, T_String, V_1_, P_Constraint_stereotype);
						
						
						Value A_symbol_9 = IP_Constraint_stereotype.evaluate(evaluator, T_String, self, P_Constraint_stereotype);
						
						DomainType static_A_symbol_10 = valueFactory.typeOf(A_symbol_8, A_symbol_9);
						LibraryBinaryOperation dynamic_A_symbol_10 = (LibraryBinaryOperation)static_A_symbol_10.lookupImplementation(standardLibrary, O_String__lt__gt_);
						Value A_symbol_10 = dynamic_A_symbol_10.evaluate(evaluator, T_Boolean, A_symbol_8, A_symbol_9);
						rightA_symbol_4 = A_symbol_10;
					} catch (InvalidValueException e) {
						rightA_symbol_4 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_10 = rightA_symbol_4;
					DomainType static_A_symbol_4 = valueFactory.typeOf(A_symbol_7);
					LibraryBinaryOperation dynamic_A_symbol_4 = (LibraryBinaryOperation)static_A_symbol_4.lookupImplementation(standardLibrary, O_Boolean_or);
					Value A_symbol_4 = dynamic_A_symbol_4.evaluate(evaluator, T_Boolean, A_symbol_7, A_symbol_10);
					return A_symbol_4;
				}
			};
			DomainType static_A_symbol_1 = A_symbol_0.getType();
			LibraryIteration dynamic_A_symbol_1 = (LibraryIteration)static_A_symbol_1.lookupImplementation(standardLibrary, O_Collection_forAll);
			Value acc_A_symbol_1 = dynamic_A_symbol_1.createAccumulatorValue(evaluator, T_Boolean, T_Boolean);
			ExecutorSingleIterationManager manager_A_symbol_1 = new ExecutorSingleIterationManager(evaluator, T_Boolean, body_A_symbol_1, (CollectionValue)A_symbol_0, acc_A_symbol_1);
			Value A_symbol_1 = dynamic_A_symbol_1.evaluateIteration(manager_A_symbol_1);
			return A_symbol_1;
		}
	}





}

