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
import org.eclipse.ocl.examples.domain.library.AbstractBinaryOperation;
import org.eclipse.ocl.examples.domain.library.AbstractUnaryOperation;
import org.eclipse.ocl.examples.domain.library.LibraryBinaryOperation;
import org.eclipse.ocl.examples.domain.library.LibraryProperty;
import org.eclipse.ocl.examples.domain.values.ObjectValue;
import org.eclipse.ocl.examples.domain.values.StringValue;
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.domain.values.ValueFactory;
import org.eclipse.ocl.examples.library.executor.ExecutorOperation;
import org.eclipse.ocl.examples.library.executor.ExecutorProperty;
import org.eclipse.ocl.examples.library.executor.ExecutorType;
import org.eclipse.ocl.examples.library.oclstdlib.OCLstdlibTables;
import org.eclipse.ocl.examples.pivot.PivotTables;

/**
 * TypedMultiplicityElementBodies provides the Java implementation bodies of OCL-defined TypedMultiplicityElement operations and properties.
 */
@SuppressWarnings("nls")
public class TypedMultiplicityElementBodies
{

	/** 
	 * Implementation of the TypedMultiplicityElement::CompatibleBody '' <body>.
	 */
	public static class _CompatibleBody_body_ extends AbstractBinaryOperation
	{
		public static _CompatibleBody_body_ INSTANCE = new _CompatibleBody_body_();
	
		/*
		let bodyType : Type = bodySpecification.type
	in
	  if bodyType.oclIsKindOf(CollectionType)
	  then
	    let
	      bodyCollectionType : CollectionType = bodyType.oclAsType(CollectionType)
	    in
	      let bodyElementType : Type = bodyCollectionType.elementType
	      in
	        bodyElementType.conformsTo(self.type) and self.isOrdered =
	        (
	          bodyCollectionType.conformsTo(OrderedSet(OclAny)) or
	          bodyCollectionType.conformsTo(Sequence(OclAny))
	        ) and self.isUnique =
	        (
	          bodyCollectionType.conformsTo(OrderedSet(OclAny)) or
	          bodyCollectionType.conformsTo(Set(OclAny))
	        )
	  else bodyType.conformsTo(self.type)
	  endif
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self, final Value bodySpecification) throws InvalidValueException {
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final DomainStandardLibrary standardLibrary = valueFactory.getStandardLibrary();
			final ExecutorType T_Boolean = OCLstdlibTables.Types._Boolean;
			final ExecutorOperation O_OclAny_oclIsKindOf = OCLstdlibTables.Operations._OclAny__oclIsKindOf;
			final Value T_ClassClassifier_Pivot_ecore__pivot__CollectionType_ = valueFactory.createTypeValue(PivotTables.Types._CollectionType);
			final ExecutorOperation O_Boolean_and = OCLstdlibTables.Operations._Boolean__and;
			final ExecutorOperation O_OclType_conformsTo = OCLstdlibTables.Operations._OclType__conformsTo;
			final ExecutorType T_Pivot_ecore__pivot__Type = PivotTables.Types._Type;
			final ExecutorProperty P_TypedElement_type = PivotTables.Properties._TypedElement__type;
			final LibraryProperty IP_TypedElement_type = P_TypedElement_type.getImplementation();
			final ExecutorOperation O_Boolean__eq_ = OCLstdlibTables.Operations._Boolean___eq_;
			final ExecutorProperty P_MultiplicityElement_isOrdered = PivotTables.Properties._MultiplicityElement__isOrdered;
			final LibraryProperty IP_MultiplicityElement_isOrdered = P_MultiplicityElement_isOrdered.getImplementation();
			final ExecutorOperation O_Boolean_or = OCLstdlibTables.Operations._Boolean__or;
			final Value T_CollectionClassifier_OrderedSet_OclAny__OclAny_ = valueFactory.createTypeValue(OCLstdlibTables.Types._OrderedSet);
			final Value T_CollectionClassifier_Sequence_OclAny__OclAny_ = valueFactory.createTypeValue(OCLstdlibTables.Types._Sequence);
			final ExecutorProperty P_MultiplicityElement_isUnique = PivotTables.Properties._MultiplicityElement__isUnique;
			final LibraryProperty IP_MultiplicityElement_isUnique = P_MultiplicityElement_isUnique.getImplementation();
			final Value T_CollectionClassifier_Set_OclAny__OclAny_ = valueFactory.createTypeValue(OCLstdlibTables.Types._Set);
			final ExecutorProperty P_CollectionType_elementType = PivotTables.Properties._CollectionType__elementType;
			final LibraryProperty IP_CollectionType_elementType = P_CollectionType_elementType.getImplementation();
			final ExecutorType T_Pivot_ecore__pivot__CollectionType = PivotTables.Types._CollectionType;
			final ExecutorOperation O_OclAny_oclAsType = OCLstdlibTables.Operations._OclAny__oclAsType;
			
			
			Value A_symbol_311 = IP_TypedElement_type.evaluate(evaluator, T_Pivot_ecore__pivot__Type, bodySpecification, P_TypedElement_type);
			
			final Value V_bodyType = A_symbol_311;
				
				DomainType static_A_symbol_312 = valueFactory.typeOf(V_bodyType);
				LibraryBinaryOperation dynamic_A_symbol_312 = (LibraryBinaryOperation)static_A_symbol_312.lookupImplementation(standardLibrary, O_OclAny_oclIsKindOf);
				Value A_symbol_312 = dynamic_A_symbol_312.evaluate(evaluator, T_Boolean, V_bodyType, T_ClassClassifier_Pivot_ecore__pivot__CollectionType_);
			Value A_symbol_313;
			if (A_symbol_312.isTrue()) {
				
				DomainType static_A_symbol_314 = valueFactory.typeOf(V_bodyType);
				LibraryBinaryOperation dynamic_A_symbol_314 = (LibraryBinaryOperation)static_A_symbol_314.lookupImplementation(standardLibrary, O_OclAny_oclAsType);
				Value A_symbol_314 = dynamic_A_symbol_314.evaluate(evaluator, T_Pivot_ecore__pivot__CollectionType, V_bodyType, T_ClassClassifier_Pivot_ecore__pivot__CollectionType_);
				final Value V_bodyCollectionType = A_symbol_314;
				
				Value A_symbol_315 = IP_CollectionType_elementType.evaluate(evaluator, T_Pivot_ecore__pivot__Type, V_bodyCollectionType, P_CollectionType_elementType);
				
				final Value V_bodyElementType = A_symbol_315;
				Value leftA_symbol_316;
				try {
					Value leftA_symbol_317;
					try {
						
						
						Value A_symbol_318 = IP_TypedElement_type.evaluate(evaluator, T_Pivot_ecore__pivot__Type, self, P_TypedElement_type);
						
						DomainType static_A_symbol_319 = valueFactory.typeOf(V_bodyElementType);
						LibraryBinaryOperation dynamic_A_symbol_319 = (LibraryBinaryOperation)static_A_symbol_319.lookupImplementation(standardLibrary, O_OclType_conformsTo);
						Value A_symbol_319 = dynamic_A_symbol_319.evaluate(evaluator, T_Boolean, V_bodyElementType, A_symbol_318);
						leftA_symbol_317 = A_symbol_319;
					} catch (InvalidValueException e) {
						leftA_symbol_317 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_319 = leftA_symbol_317;
					Value rightA_symbol_317;
					try {
						Value leftA_symbol_320;
						try {
							
							Value A_symbol_321 = IP_MultiplicityElement_isOrdered.evaluate(evaluator, T_Boolean, self, P_MultiplicityElement_isOrdered);
							
							leftA_symbol_320 = A_symbol_321;
						} catch (InvalidValueException e) {
							leftA_symbol_320 = valueFactory.createInvalidValue(e);
						}
						Value A_symbol_321 = leftA_symbol_320;
						Value rightA_symbol_320;
						try {
							Value leftA_symbol_322;
							try {
								
								DomainType static_A_symbol_323 = valueFactory.typeOf(V_bodyCollectionType);
								LibraryBinaryOperation dynamic_A_symbol_323 = (LibraryBinaryOperation)static_A_symbol_323.lookupImplementation(standardLibrary, O_OclType_conformsTo);
								Value A_symbol_323 = dynamic_A_symbol_323.evaluate(evaluator, T_Boolean, V_bodyCollectionType, T_CollectionClassifier_OrderedSet_OclAny__OclAny_);
								leftA_symbol_322 = A_symbol_323;
							} catch (InvalidValueException e) {
								leftA_symbol_322 = valueFactory.createInvalidValue(e);
							}
							Value A_symbol_323 = leftA_symbol_322;
							Value rightA_symbol_322;
							try {
								
								DomainType static_A_symbol_324 = valueFactory.typeOf(V_bodyCollectionType);
								LibraryBinaryOperation dynamic_A_symbol_324 = (LibraryBinaryOperation)static_A_symbol_324.lookupImplementation(standardLibrary, O_OclType_conformsTo);
								Value A_symbol_324 = dynamic_A_symbol_324.evaluate(evaluator, T_Boolean, V_bodyCollectionType, T_CollectionClassifier_Sequence_OclAny__OclAny_);
								rightA_symbol_322 = A_symbol_324;
							} catch (InvalidValueException e) {
								rightA_symbol_322 = valueFactory.createInvalidValue(e);
							}
							Value A_symbol_324 = rightA_symbol_322;
							DomainType static_A_symbol_322 = valueFactory.typeOf(A_symbol_323);
							LibraryBinaryOperation dynamic_A_symbol_322 = (LibraryBinaryOperation)static_A_symbol_322.lookupImplementation(standardLibrary, O_Boolean_or);
							Value A_symbol_322 = dynamic_A_symbol_322.evaluate(evaluator, T_Boolean, A_symbol_323, A_symbol_324);
							rightA_symbol_320 = A_symbol_322;
						} catch (InvalidValueException e) {
							rightA_symbol_320 = valueFactory.createInvalidValue(e);
						}
						Value A_symbol_322 = rightA_symbol_320;
						DomainType static_A_symbol_320 = valueFactory.typeOf(A_symbol_321, A_symbol_322);
						LibraryBinaryOperation dynamic_A_symbol_320 = (LibraryBinaryOperation)static_A_symbol_320.lookupImplementation(standardLibrary, O_Boolean__eq_);
						Value A_symbol_320 = dynamic_A_symbol_320.evaluate(evaluator, T_Boolean, A_symbol_321, A_symbol_322);
						rightA_symbol_317 = A_symbol_320;
					} catch (InvalidValueException e) {
						rightA_symbol_317 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_320 = rightA_symbol_317;
					DomainType static_A_symbol_317 = valueFactory.typeOf(A_symbol_319);
					LibraryBinaryOperation dynamic_A_symbol_317 = (LibraryBinaryOperation)static_A_symbol_317.lookupImplementation(standardLibrary, O_Boolean_and);
					Value A_symbol_317 = dynamic_A_symbol_317.evaluate(evaluator, T_Boolean, A_symbol_319, A_symbol_320);
					leftA_symbol_316 = A_symbol_317;
				} catch (InvalidValueException e) {
					leftA_symbol_316 = valueFactory.createInvalidValue(e);
				}
				Value A_symbol_317 = leftA_symbol_316;
				Value rightA_symbol_316;
				try {
					Value leftA_symbol_325;
					try {
						
						Value A_symbol_326 = IP_MultiplicityElement_isUnique.evaluate(evaluator, T_Boolean, self, P_MultiplicityElement_isUnique);
						
						leftA_symbol_325 = A_symbol_326;
					} catch (InvalidValueException e) {
						leftA_symbol_325 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_326 = leftA_symbol_325;
					Value rightA_symbol_325;
					try {
						Value leftA_symbol_327;
						try {
							
							DomainType static_A_symbol_328 = valueFactory.typeOf(V_bodyCollectionType);
							LibraryBinaryOperation dynamic_A_symbol_328 = (LibraryBinaryOperation)static_A_symbol_328.lookupImplementation(standardLibrary, O_OclType_conformsTo);
							Value A_symbol_328 = dynamic_A_symbol_328.evaluate(evaluator, T_Boolean, V_bodyCollectionType, T_CollectionClassifier_OrderedSet_OclAny__OclAny_);
							leftA_symbol_327 = A_symbol_328;
						} catch (InvalidValueException e) {
							leftA_symbol_327 = valueFactory.createInvalidValue(e);
						}
						Value A_symbol_328 = leftA_symbol_327;
						Value rightA_symbol_327;
						try {
							
							DomainType static_A_symbol_329 = valueFactory.typeOf(V_bodyCollectionType);
							LibraryBinaryOperation dynamic_A_symbol_329 = (LibraryBinaryOperation)static_A_symbol_329.lookupImplementation(standardLibrary, O_OclType_conformsTo);
							Value A_symbol_329 = dynamic_A_symbol_329.evaluate(evaluator, T_Boolean, V_bodyCollectionType, T_CollectionClassifier_Set_OclAny__OclAny_);
							rightA_symbol_327 = A_symbol_329;
						} catch (InvalidValueException e) {
							rightA_symbol_327 = valueFactory.createInvalidValue(e);
						}
						Value A_symbol_329 = rightA_symbol_327;
						DomainType static_A_symbol_327 = valueFactory.typeOf(A_symbol_328);
						LibraryBinaryOperation dynamic_A_symbol_327 = (LibraryBinaryOperation)static_A_symbol_327.lookupImplementation(standardLibrary, O_Boolean_or);
						Value A_symbol_327 = dynamic_A_symbol_327.evaluate(evaluator, T_Boolean, A_symbol_328, A_symbol_329);
						rightA_symbol_325 = A_symbol_327;
					} catch (InvalidValueException e) {
						rightA_symbol_325 = valueFactory.createInvalidValue(e);
					}
					Value A_symbol_327 = rightA_symbol_325;
					DomainType static_A_symbol_325 = valueFactory.typeOf(A_symbol_326, A_symbol_327);
					LibraryBinaryOperation dynamic_A_symbol_325 = (LibraryBinaryOperation)static_A_symbol_325.lookupImplementation(standardLibrary, O_Boolean__eq_);
					Value A_symbol_325 = dynamic_A_symbol_325.evaluate(evaluator, T_Boolean, A_symbol_326, A_symbol_327);
					rightA_symbol_316 = A_symbol_325;
				} catch (InvalidValueException e) {
					rightA_symbol_316 = valueFactory.createInvalidValue(e);
				}
				Value A_symbol_325 = rightA_symbol_316;
				DomainType static_A_symbol_316 = valueFactory.typeOf(A_symbol_317);
				LibraryBinaryOperation dynamic_A_symbol_316 = (LibraryBinaryOperation)static_A_symbol_316.lookupImplementation(standardLibrary, O_Boolean_and);
				Value A_symbol_316 = dynamic_A_symbol_316.evaluate(evaluator, T_Boolean, A_symbol_317, A_symbol_325);
				final Value A_symbol_330 = A_symbol_316;
				final Value A_symbol_331 = A_symbol_330;
				A_symbol_313 = A_symbol_331;
			}
			else if (A_symbol_312.isFalse()) {
				
				
				Value A_symbol_332 = IP_TypedElement_type.evaluate(evaluator, T_Pivot_ecore__pivot__Type, self, P_TypedElement_type);
				
				DomainType static_A_symbol_333 = valueFactory.typeOf(V_bodyType);
				LibraryBinaryOperation dynamic_A_symbol_333 = (LibraryBinaryOperation)static_A_symbol_333.lookupImplementation(standardLibrary, O_OclType_conformsTo);
				Value A_symbol_333 = dynamic_A_symbol_333.evaluate(evaluator, T_Boolean, V_bodyType, A_symbol_332);
				A_symbol_313 = A_symbol_333;
			}
			else if (A_symbol_312.isNull()) {
				A_symbol_313 = valueFactory.throwInvalidValueException("null if condition");
			}
			else {
				A_symbol_313 = valueFactory.throwInvalidValueException("invalid if condition");
			}
			final Value A_symbol_334 = A_symbol_313;
			return A_symbol_334;
		}
	}

	/** 
	 * Implementation of the TypedMultiplicityElement::makeParameter '' <body>.
	 */
	public static class _makeParameter_body_ extends AbstractUnaryOperation
	{
		public static _makeParameter_body_ INSTANCE = new _makeParameter_body_();
	
		/*
		Parameter{name = 'name'}
		*/
		public Value evaluate(DomainEvaluator evaluator, DomainType returnType, final Value self) throws InvalidValueException {
			final ExecutorProperty P_NamedElement_name = PivotTables.Properties._NamedElement__name;
			final ValueFactory valueFactory = evaluator.getValueFactory();
			final StringValue S_name = valueFactory.stringValueOf("name");
			
			ObjectValue A_symbol_335 = PivotTables.Types._Parameter.createInstance(valueFactory);
			
			P_NamedElement_name.setValue(A_symbol_335, S_name);
			
			return A_symbol_335;
		}
	}
}

