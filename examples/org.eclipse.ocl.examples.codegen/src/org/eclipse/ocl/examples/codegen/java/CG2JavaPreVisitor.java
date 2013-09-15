/**
 * <copyright>
 * 
 * Copyright (c) 2013 CEA LIST and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   E.D.Willink(CEA LIST) - Initial API and implementation
 * 
 * </copyright>
 */
package org.eclipse.ocl.examples.codegen.java;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.codegen.analyzer.CodeGenAnalyzer;
import org.eclipse.ocl.examples.codegen.cgmodel.CGBoxExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGBuiltInIterationCallExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGCollectionExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGCollectionPart;
import org.eclipse.ocl.examples.codegen.cgmodel.CGConstantExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGConstraint;
import org.eclipse.ocl.examples.codegen.cgmodel.CGConstructorExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGConstructorPart;
import org.eclipse.ocl.examples.codegen.cgmodel.CGElement;
import org.eclipse.ocl.examples.codegen.cgmodel.CGElementId;
import org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorCompositionProperty;
import org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorConstructorPart;
import org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorNavigationProperty;
import org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorOperation;
import org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorOperationCallExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorOppositeProperty;
import org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorProperty;
import org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorPropertyCallExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorType;
import org.eclipse.ocl.examples.codegen.cgmodel.CGIterationCallExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGIterator;
import org.eclipse.ocl.examples.codegen.cgmodel.CGLetExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGLibraryIterateCallExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGLibraryIterationCallExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGLibraryOperationCallExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGLibraryPropertyCallExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGModelFactory;
import org.eclipse.ocl.examples.codegen.cgmodel.CGOperation;
import org.eclipse.ocl.examples.codegen.cgmodel.CGParameter;
import org.eclipse.ocl.examples.codegen.cgmodel.CGProperty;
import org.eclipse.ocl.examples.codegen.cgmodel.CGText;
import org.eclipse.ocl.examples.codegen.cgmodel.CGTypeExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGTypeId;
import org.eclipse.ocl.examples.codegen.cgmodel.CGTypedElement;
import org.eclipse.ocl.examples.codegen.cgmodel.CGUnboxExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGValuedElement;
import org.eclipse.ocl.examples.codegen.cgmodel.util.AbstractExtendingCGModelVisitor;
import org.eclipse.ocl.examples.codegen.generator.GenModelHelper;
import org.eclipse.ocl.examples.codegen.generator.TypeDescriptor;
import org.eclipse.ocl.examples.codegen.java.types.JavaTypeId;
import org.eclipse.ocl.examples.domain.ids.CollectionTypeId;
import org.eclipse.ocl.examples.domain.ids.ElementId;
import org.eclipse.ocl.examples.domain.ids.EnumerationLiteralId;
import org.eclipse.ocl.examples.domain.ids.MetaclassId;
import org.eclipse.ocl.examples.domain.ids.NestedTypeId;
import org.eclipse.ocl.examples.domain.ids.OclVoidTypeId;
import org.eclipse.ocl.examples.domain.ids.PrimitiveTypeId;
import org.eclipse.ocl.examples.domain.ids.TuplePartId;
import org.eclipse.ocl.examples.domain.ids.TupleTypeId;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.library.LibraryOperation;
import org.eclipse.ocl.examples.domain.library.LibrarySimpleOperation;
import org.eclipse.ocl.examples.domain.library.LibraryUntypedOperation;
import org.eclipse.ocl.examples.domain.values.CollectionValue;
import org.eclipse.ocl.examples.pivot.CollectionLiteralExp;
import org.eclipse.ocl.examples.pivot.Property;

/**
 * A CG2JavaPreVisitor prepares for Java code generation by performing a tree traversal
 * to gather all imports and global constants.
 */
public class CG2JavaPreVisitor extends AbstractExtendingCGModelVisitor<Object, JavaGlobalContext>
{
	protected final @NonNull JavaCodeGenerator codeGenerator;
	protected final @NonNull GenModelHelper genModelHelper;
	protected final @NonNull CodeGenAnalyzer analyzer;
	protected JavaLocalContext localContext;

	public CG2JavaPreVisitor(@NonNull JavaGlobalContext javaContext) {
		super(javaContext);
		this.codeGenerator = javaContext.getCodeGenerator();
		this.analyzer = codeGenerator.getAnalyzer();
		this.genModelHelper = codeGenerator.getGenModelHelper();
	}

	protected void addOwnedTypeId(@NonNull CGValuedElement cgElement, @NonNull ElementId typeId) {
		if (typeId instanceof OclVoidTypeId) {	// FIXME tuples etc etc
			;
		}
		else if (typeId instanceof PrimitiveTypeId) {
			;
		}
//		else if (typeId instanceof JavaTypeId) {
//			;
//		}
		else {
			CGElementId elementId = analyzer.getElementId(typeId);
			CGElementId cgTypeId = elementId;
			CGConstantExp cgConstantExp = CGModelFactory.eINSTANCE.createCGConstantExp();
			cgConstantExp.setReferredConstant(cgTypeId);
			if (elementId instanceof CGTypeId) {
				cgConstantExp.setTypeId((CGTypeId) elementId);
			}
//			if (cgElement != null) {
				cgElement.getOwns().add(cgConstantExp);		// FIXME suppress/detect duplicates
//			}
			if (typeId instanceof CollectionTypeId) {
				addOwnedTypeId(cgConstantExp, ((CollectionTypeId)typeId).getElementTypeId());
			}
			else if (typeId instanceof MetaclassId) {
				addOwnedTypeId(cgConstantExp, ((MetaclassId)typeId).getElementId());
			}
			else if (typeId instanceof TupleTypeId) {
				for (@SuppressWarnings("null")@NonNull TuplePartId partId : ((TupleTypeId)typeId).getPartIds()) {
					addOwnedTypeId(cgConstantExp, partId);
				}
			}
			else if (typeId instanceof NestedTypeId) {
				addOwnedTypeId(cgConstantExp, ((NestedTypeId)typeId).getParent());			
			}
		}
	}

	protected void doTypedElement(@NonNull CGTypedElement cgTypedElement) {
		CGTypeId cgTypeId = cgTypedElement.getTypeId();
		if (cgTypeId != null) {
			cgTypeId.accept(this);
		}
	}

	protected void doValuedElement(@NonNull CGValuedElement cgValuedElement) {
		CGValuedElement value = cgValuedElement.getNamedValue();
		if (value.isGlobal()) {
			context.addGlobal(value);
		}
		TypeId asTypeId = cgValuedElement.getASTypeId();
		if (asTypeId != null) {
			addOwnedTypeId(cgValuedElement, asTypeId);
		}
		if (cgValuedElement.getNamedValue() == cgValuedElement) {
			if ((localContext != null) && !cgValuedElement.isGlobal()) {
				localContext.getValueName(cgValuedElement);
			}
			else {
				context.getValueName(cgValuedElement);
			}
		}
	}

	public @NonNull JavaCodeGenerator getCodeGenerator() {
		return codeGenerator;
	}

	protected @Nullable CGValuedElement installIdResolverVariable(@NonNull CGValuedElement cgValuedElement) {
		CGValuedElement idResolverVariable = localContext.createIdResolverVariable();
		cgValuedElement.getOwns().add(idResolverVariable);
		return idResolverVariable;
	}

	protected @NonNull CGText installStandardLibraryVariable(@NonNull CGValuedElement cgValuedElement) {
		CGText standardLibraryVariable = localContext.createStandardLibraryVariable();
		cgValuedElement.getOwns().add(standardLibraryVariable);
		installIdResolverVariable(standardLibraryVariable);
		return standardLibraryVariable;
	}

	public @Nullable Object visiting(@NonNull CGElement visitable) {
		throw new UnsupportedOperationException(getClass().getSimpleName() + ": " + visitable.getClass().getSimpleName());
	}

	@Override
	public @Nullable Object visitCGBoxExp(@NonNull CGBoxExp cgBoxExp) {
		CGValuedElement unboxedValue = cgBoxExp.getSource();
		if (unboxedValue != null) {
			TypeDescriptor unboxedTypeDescriptor = codeGenerator.getTypeDescriptor(unboxedValue);
			if (unboxedTypeDescriptor.isAssignableTo(Iterable.class)) {
				installIdResolverVariable(cgBoxExp);
			}
		}
		return super.visitCGBoxExp(cgBoxExp);
	}

	@Override
	public @Nullable Object visitCGBuiltInIterationCallExp(@NonNull CGBuiltInIterationCallExp cgBuiltInIterationCallExp) {
		TypeId asTypeId = cgBuiltInIterationCallExp.getASTypeId();
		if (asTypeId != null) {
			addOwnedTypeId(cgBuiltInIterationCallExp, asTypeId);
		}
		return super.visitCGBuiltInIterationCallExp(cgBuiltInIterationCallExp);
	}

	@Override
	public @Nullable Object visitCGCollectionExp(@NonNull CGCollectionExp cgCollectionExp) {
		CollectionLiteralExp collectionExp = (CollectionLiteralExp)cgCollectionExp.getAst();
		if (collectionExp != null) {
			TypeId asTypeId = cgCollectionExp.getASTypeId();
			if (asTypeId != null) {
				addOwnedTypeId(cgCollectionExp, asTypeId);
			}
		}
		return super.visitCGCollectionExp(cgCollectionExp);
	}

	@Override
	public @Nullable Object visitCGCollectionPart(@NonNull CGCollectionPart cgCollectionPart) {
		boolean isRange = cgCollectionPart.isRange();
		if (cgCollectionPart.isConstant() && isRange) {
//			context.addGlobal(cgCollectionPart);
		}
		if (isRange) {
//			context.getFinalVariable(cgCollectionPart);
		}
		return super.visitCGCollectionPart(cgCollectionPart);
	}

	@Override
	public @Nullable Object visitCGConstantExp(@NonNull CGConstantExp cgConstantExp) {
		super.visitCGConstantExp(cgConstantExp);
		CGValuedElement referredConstant = cgConstantExp.getReferredConstant();
		if (referredConstant != null) {
			referredConstant.accept(this);
		}
		return null;
	}

	@Override
	public @Nullable Object visitCGConstraint(@NonNull CGConstraint cgConstraint) {
		localContext = context.getLocalContext(cgConstraint);
		try {
			return super.visitCGConstraint(cgConstraint);
		}
		finally {
			localContext = null;
		}
	}

	@Override
	public @Nullable Object visitCGConstructorExp(@NonNull CGConstructorExp cgConstructorExp) {
		CGExecutorType cgType = cgConstructorExp.getExecutorType();
		if (cgType != null) {
			cgType.accept(this);
		}
		return super.visitCGConstructorExp(cgConstructorExp);
	}

	@Override
	public @Nullable Object visitCGConstructorPart(@NonNull CGConstructorPart cgConstructorPart) {
		CGExecutorConstructorPart cgExecutorConstructorPart = cgConstructorPart.getExecutorPart();
		cgExecutorConstructorPart.accept(this);
		JavaTypeId javaPropertyTypeId = JavaConstants.DOMAIN_PROPERTY_TYPE_ID;
		cgExecutorConstructorPart.setTypeId(analyzer.getTypeId(javaPropertyTypeId));
//		localContext.addLocalVariable(cgExecutorConstructorPart);
		installIdResolverVariable(cgExecutorConstructorPart);
		cgConstructorPart.getOwns().add(cgExecutorConstructorPart);
		cgConstructorPart.getDependsOn().add(cgExecutorConstructorPart);
//		cgConstructorPart.getDependsOn().add(cgConstructorPart.getConstructorExp());
		return super.visitCGConstructorPart(cgConstructorPart);
	}

	@Override
	public @Nullable Object visitCGElement(@NonNull CGElement cgElement) {
		List<?> owns = cgElement instanceof CGValuedElement ? ((CGValuedElement)cgElement).getOwns() : null;
		for (CGElement cgChild : cgElement.getChildren()) {
			if ((owns == null) || !owns.contains(cgChild)) {
				cgChild.accept(this);
			}
		}
		return null;
	}

	@Override
	public @Nullable Object visitCGExecutorCompositionProperty(@NonNull CGExecutorCompositionProperty cgExecutorProperty) {
		Property asProperty = (Property) cgExecutorProperty.getAst();
		Property asOppositeProperty = asProperty.getOpposite();
		if ((asOppositeProperty != null) && asOppositeProperty.isComposite()) {
			JavaTypeId javaPropertyTypeId = JavaConstants.UNBOXED_COMPOSITION_PROPERTY_TYPE_ID;
			cgExecutorProperty.setTypeId(analyzer.getTypeId(javaPropertyTypeId));
		}
		return super.visitCGExecutorCompositionProperty(cgExecutorProperty);
	}

	@Override
	public @Nullable Object visitCGExecutorNavigationProperty(@NonNull CGExecutorNavigationProperty cgExecutorProperty) {
		JavaTypeId javaPropertyTypeId = JavaConstants.UNBOXED_EXPLICIT_NAVIGATION_PROPERTY_TYPE_ID;
		cgExecutorProperty.setTypeId(analyzer.getTypeId(javaPropertyTypeId));
		return super.visitCGExecutorNavigationProperty(cgExecutorProperty);
	}

	@Override
	public @Nullable Object visitCGExecutorOperation(@NonNull CGExecutorOperation cgExecutorOperation) {
		installIdResolverVariable(cgExecutorOperation);
		CGElementId cgOperationId = cgExecutorOperation.getUnderlyingOperationId();
		if (cgOperationId != null) {
			cgOperationId.accept(this);
		}
		return super.visitCGExecutorOperation(cgExecutorOperation);
	}

	@Override
	public @Nullable Object visitCGExecutorOperationCallExp(@NonNull CGExecutorOperationCallExp cgExecutorOperationCallExp) {
//		Operation referredOperation = cgExecutorOperationCallExp.getReferredOperation();
//		OperationId operationId = referredOperation.getOperationId();
//		CGExecutorOperation cgExecutorOperation = analyzer.getExecutorOperation(operationId);
//		cgExecutorOperation.accept(this);
//		localContext.getOuterContext().addLocalVariable(cgExecutorOperation);
		return super.visitCGExecutorOperationCallExp(cgExecutorOperationCallExp);
	}

	@Override
	public @Nullable Object visitCGExecutorOppositeProperty(@NonNull CGExecutorOppositeProperty cgExecutorProperty) {
		JavaTypeId javaPropertyTypeId = JavaConstants.UNBOXED_OPPOSITE_NAVIGATION_PROPERTY_TYPE_ID;
		cgExecutorProperty.setTypeId(analyzer.getTypeId(javaPropertyTypeId));
		return super.visitCGExecutorOppositeProperty(cgExecutorProperty);
	}

	@Override
	public @Nullable Object visitCGExecutorProperty(@NonNull CGExecutorProperty cgExecutorProperty) {
		CGElementId cgPropertyId = cgExecutorProperty.getUnderlyingPropertyId();
		if (cgPropertyId != null) {
			cgPropertyId.accept(this);
		}
		return super.visitCGExecutorProperty(cgExecutorProperty);
	}

	@Override
	public @Nullable Object visitCGExecutorPropertyCallExp(@NonNull CGExecutorPropertyCallExp cgExecutorPropertyCallExp) {
		CGExecutorProperty cgExecutorProperty = cgExecutorPropertyCallExp.getExecutorProperty();
		if (cgExecutorProperty != null) {
			cgExecutorProperty.accept(this);
		}
//		Property referredProperty = cgExecutorPropertyCallExp.getReferredProperty();
//		PropertyId propertyId = referredProperty.getPropertyId();
//		CGExecutorProperty cgExecutorProperty = cgExecutorPropertyCallExp.getExecutorProperty();
//		CGExecutorProperty cgExecutorProperty = analyzer.createExecutorProperty(referredProperty);
//		cgExecutorPropertyCallExp.getUses().add(cgExecutorProperty);
//		cgExecutorProperty.accept(this);
//		localContext.getOuterContext().addLocalVariable(cgExecutorProperty);
//		cgExecutorProperty.getDependsOn().add(installIdResolverVariable());
		return super.visitCGExecutorPropertyCallExp(cgExecutorPropertyCallExp);
	}

	@Override
	public @Nullable Object visitCGExecutorType(@NonNull CGExecutorType cgExecutorType) {
		installIdResolverVariable(cgExecutorType);
		CGTypeId cgTypeId = cgExecutorType.getUnderlyingTypeId();
		if (cgTypeId != null) {
			cgTypeId.accept(this);
		}
		cgExecutorType.setTypeId(analyzer.getTypeId(JavaConstants.DOMAIN_TYPE_TYPE_ID));		// FIXME
		return super.visitCGExecutorType(cgExecutorType);
	}

	@Override
	public @Nullable Object visitCGIterationCallExp(@NonNull CGIterationCallExp cgIterationCallExp) {
		doValuedElement(cgIterationCallExp);				// Resolve name in outer context
		doTypedElement(cgIterationCallExp);
		CGValuedElement cgSource = cgIterationCallExp.getSource();
		if (cgSource != null) {
			cgSource.accept(this);
		}
		for (CGIterator cgIterator : cgIterationCallExp.getIterators()) {
			CGValuedElement cgInit = cgIterator.getInit();
			if (cgInit != null) {
				cgInit.accept(this);
			}
		}
		JavaLocalContext savedLocalContext = localContext;
		localContext = context.getLocalContext(cgIterationCallExp);
		try {
			CGValuedElement cgBody = cgIterationCallExp.getBody();
			if (cgBody != null) {
				cgBody.accept(this);
			}
			for (CGIterator cgIterator : cgIterationCallExp.getIterators()) {
				cgIterator.accept(this);
			}
			return null;
		}
		finally {
			localContext = savedLocalContext;
		}
	}

	@Override
	public @Nullable Object visitCGLetExp(@NonNull CGLetExp cgLetExp) {
		CGValuedElement in = cgLetExp.getIn();
		if (in != null) {
//			context.getFinalVariable(in);
		}
		return super.visitCGLetExp(cgLetExp);
	}

	@Override
	public @Nullable Object visitCGLibraryIterateCallExp(@NonNull CGLibraryIterateCallExp cgLibraryIterateCallExp) {
		installStandardLibraryVariable(cgLibraryIterateCallExp);
		return super.visitCGLibraryIterateCallExp(cgLibraryIterateCallExp);
	}

	@Override
	public @Nullable Object visitCGLibraryIterationCallExp(@NonNull CGLibraryIterationCallExp cgLibraryIterationCallExp) {
		installStandardLibraryVariable(cgLibraryIterationCallExp);
//		TypeId asTypeId = cgLibraryIterationCallExp.getASTypeId();
//		if (asTypeId != null) {
//			addOwnedTypeId(cgLibraryIterationCallExp, asTypeId);
//		}
		return super.visitCGLibraryIterationCallExp(cgLibraryIterationCallExp);
	}

	@Override
	public @Nullable Object visitCGLibraryOperationCallExp(@NonNull CGLibraryOperationCallExp cgOperationCallExp) {
		LibraryOperation libraryOperation = cgOperationCallExp.getLibraryOperation();
		if (!(libraryOperation instanceof LibrarySimpleOperation)) {
			if (!(libraryOperation instanceof LibraryUntypedOperation)) {
				TypeId asTypeId = cgOperationCallExp.getASTypeId();
				if (asTypeId != null) {
					addOwnedTypeId(cgOperationCallExp, asTypeId);
				}
			}
		}
		return super.visitCGLibraryOperationCallExp(cgOperationCallExp);
	}

	@Override
	public @Nullable Object visitCGLibraryPropertyCallExp(@NonNull CGLibraryPropertyCallExp cgPropertyCallExp) {
//		LibraryProperty libraryProperty = cgPropertyCallExp.getLibraryProperty();
		try {
			return super.visitCGLibraryPropertyCallExp(cgPropertyCallExp);
		}
		finally {
//			installEvaluatorParameter(cgPropertyCallExp);
//			if (!(libraryOperation instanceof LibraryUntypedOperation)) {
				CGTypeId cgTypeId = cgPropertyCallExp.getTypeId();
				if (cgTypeId != null) {
//					context.addGlobal(cgTypeId);
				}
				TypeId asTypeId = cgPropertyCallExp.getASTypeId();
				if (asTypeId != null) {
					addOwnedTypeId(cgPropertyCallExp, asTypeId);
				}
//			}
		}
	}

	@Override
	public @Nullable Object visitCGOperation(@NonNull CGOperation cgOperation) {
		localContext = context.getLocalContext(cgOperation);
		try {
			CGParameter typeIdParameter = localContext.createTypeIdParameter();
			if (typeIdParameter != null) {
				cgOperation.getParameters().add(0, typeIdParameter);
			}
			CGParameter evaluatorParameter = localContext.createEvaluatorParameter();
			if (evaluatorParameter != null) {
				cgOperation.getParameters().add(0, evaluatorParameter);
			}
//			CGTypeId type = idResolverVariable.getTypeId();
//			type.accept(this);
//			return evaluatorParameter;
			return super.visitCGOperation(cgOperation);
		}
		finally {
			localContext = null;
		}
	}

	@Override
	public @Nullable Object visitCGProperty(@NonNull CGProperty cgProperty) {
		localContext = context.getLocalContext(cgProperty);
		try {
			return super.visitCGProperty(cgProperty);
		}
		finally {
			localContext = null;
		}
	}

	@Override
	public @Nullable Object visitCGTypeExp(@NonNull CGTypeExp cgTypeExp) {
		TypeId asTypeId = cgTypeExp.getASTypeId();
		if (asTypeId != null) {
			addOwnedTypeId(cgTypeExp, asTypeId);
		}
		CGExecutorType cgType = cgTypeExp.getExecutorType();
		if (cgType != null) {
			cgType.accept(this);
		}
//		CGExecutorType cgType = cgTypeExp.getExecutorType();
//		String name = cgType.getValueName();
//		if (name == null) {		
//			name = localContext.getNameManagerContext().getSymbolName(cgType);
//			cgType.setValueName(name);
//		}
		return super.visitCGTypeExp(cgTypeExp);
	}

	@Override
	public @Nullable Object visitCGTypedElement(@NonNull CGTypedElement cgTypedElement) {
		super.visitCGTypedElement(cgTypedElement);
		doTypedElement(cgTypedElement);
		return null;
	}

	@Override
	public @Nullable Object visitCGUnboxExp(@NonNull CGUnboxExp cgUnboxExp) {
		CGValuedElement source = analyzer.getExpression(cgUnboxExp.getSource());
		TypeDescriptor boxedTypeDescriptor = codeGenerator.getTypeDescriptor(source);
		if (boxedTypeDescriptor.isAssignableTo(CollectionValue.class)
		 || boxedTypeDescriptor.isAssignableTo(EnumerationLiteralId.class)) {
			installIdResolverVariable(cgUnboxExp);
		}
		return super.visitCGUnboxExp(cgUnboxExp);
	}

	@Override
	public @Nullable Object visitCGValuedElement(@NonNull CGValuedElement cgValuedElement) {
		super.visitCGValuedElement(cgValuedElement);
		doValuedElement(cgValuedElement);
		return null;
	}
}
