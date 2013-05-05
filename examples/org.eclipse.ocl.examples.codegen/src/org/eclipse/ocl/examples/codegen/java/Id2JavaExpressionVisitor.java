/**
 * <copyright>
 * 
 * Copyright (c) 2012,2013 CEA LIST and others.
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

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.ids.BindingsId;
import org.eclipse.ocl.examples.domain.ids.ClassId;
import org.eclipse.ocl.examples.domain.ids.CollectionTypeId;
import org.eclipse.ocl.examples.domain.ids.DataTypeId;
import org.eclipse.ocl.examples.domain.ids.ElementId;
import org.eclipse.ocl.examples.domain.ids.EnumerationId;
import org.eclipse.ocl.examples.domain.ids.EnumerationLiteralId;
import org.eclipse.ocl.examples.domain.ids.IdManager;
import org.eclipse.ocl.examples.domain.ids.IdVisitor;
import org.eclipse.ocl.examples.domain.ids.LambdaTypeId;
import org.eclipse.ocl.examples.domain.ids.MetaclassId;
import org.eclipse.ocl.examples.domain.ids.NestedPackageId;
import org.eclipse.ocl.examples.domain.ids.NsURIPackageId;
import org.eclipse.ocl.examples.domain.ids.OclInvalidTypeId;
import org.eclipse.ocl.examples.domain.ids.OclVoidTypeId;
import org.eclipse.ocl.examples.domain.ids.OperationId;
import org.eclipse.ocl.examples.domain.ids.PrimitiveTypeId;
import org.eclipse.ocl.examples.domain.ids.PropertyId;
import org.eclipse.ocl.examples.domain.ids.RootPackageId;
import org.eclipse.ocl.examples.domain.ids.SpecializedId;
import org.eclipse.ocl.examples.domain.ids.TemplateBinding;
import org.eclipse.ocl.examples.domain.ids.TemplateParameterId;
import org.eclipse.ocl.examples.domain.ids.TemplateableTypeId;
import org.eclipse.ocl.examples.domain.ids.TuplePartId;
import org.eclipse.ocl.examples.domain.ids.TupleTypeId;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.ids.UnspecifiedId;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;

/**
 * An Id2JavaExpressionVisitor appends the expression body of an Id declaration.
 */
public class Id2JavaExpressionVisitor implements IdVisitor<Object>
{
	protected final @NonNull JavaStream js;
	protected final @NonNull MetaModelManager metaModelManager;

	public Id2JavaExpressionVisitor(@NonNull JavaStream js) {
		this.js = js;
		this.metaModelManager = js.getCodeGenerator().getMetaModelManager();
	}

	public @Nullable Object visitClassId(@NonNull ClassId id) {
		js.appendIdReference(id.getParent());
		js.append(".getClassId(");
		js.appendString(id.getName());
		js.append(", " + id.getTemplateParameters() + ")");
		return null;
	}
	
	public @Nullable Object visitCollectionTypeId(@NonNull CollectionTypeId id) {
		js.appendClassReference(TypeId.class);
		CollectionTypeId generalizedId = id.getGeneralizedId();
		String idName = generalizedId.getLiteralName();
		if (idName == null) {
			idName = "COLLECTION";
		}
		js.append("." + idName);
		if (id instanceof SpecializedId) {
			js.append(".getSpecializedId(");
			BindingsId templateBindings = ((SpecializedId)id).getTemplateBindings();
			for (int i = 0; i < templateBindings.size(); i++) {
				if (i > 0) {
					js.append(", ");
				}
				ElementId elementId = DomainUtil.nonNullModel(templateBindings.get(i));
				js.appendIdReference(elementId);
			}
			js.append(")");
		}
		return null;
	}

	public @Nullable Object visitDataTypeId(@NonNull DataTypeId id) {
		js.appendIdReference(id.getParent());
		js.append(".getDataTypeId(");
		js.appendString(id.getName());
		js.append(", " + id.getTemplateParameters() + ")");
		return null;
	}

	public @Nullable Object visitEnumerationId(@NonNull EnumerationId id) {
		js.appendIdReference(id.getParent());
		js.append(".getEnumerationId(");
		js.appendString(id.getName());
		js.append(")");
		return null;
	}

	public @Nullable Object visitEnumerationLiteralId(@NonNull EnumerationLiteralId id) {
		js.appendIdReference(id.getParentId());
		js.append(".getEnumerationLiteralId(");
		js.appendString(id.getName());
		js.append(")");
		return null;
	}

	public @Nullable Object visitInvalidId(@NonNull OclInvalidTypeId id) {
		js.appendClassReference(TypeId.class);
		js.append(".");
		js.append(id.getLiteralName());
		return null;
	}

	public @Nullable Object visitLambdaTypeId(@NonNull LambdaTypeId id) {
		// TODO Auto-generated method stub
		return visiting(id);
	}
	
	public @Nullable Object visitMetaclassId(@NonNull MetaclassId id) {
		js.appendClassReference(TypeId.class);
		js.append(".METACLASS");
		if (id != TypeId.METACLASS) {
			js.append(".getSpecializedId(");
			js.appendIdReference(id.getElementId());
			js.append(")");
		}
		return null;
	}

	public @Nullable Object visitNestedPackageId(@NonNull NestedPackageId id) {
		js.appendIdReference(id.getParent());
		js.append(".getNestedPackageId(");
		js.appendString(id.getName());
		js.append(")");
		return null;
	}

	public @Nullable Object visitNsURIPackageId(@NonNull NsURIPackageId id) {
		String nsURI = id.getNsURI();
		GenPackage genPackage = metaModelManager.getGenPackage(nsURI);
		js.appendClassReference(IdManager.class);
		js.append(".getNsURIPackageId(");
		js.appendString(nsURI);
		js.append(", ");
		if (genPackage != null) {
			js.appendClassReference(genPackage.getQualifiedPackageInterfaceName());
			js.append(".eINSTANCE");
		}
		else {
			js.append("null");
		}
		js.append(")");
		return null;
	}

	public @Nullable Object visitNullId(@NonNull OclVoidTypeId id) {
		js.appendClassReference(TypeId.class);
		js.append(".");
		js.append(id.getLiteralName());
		return null;
	}

	public @Nullable Object visitOperationId(@NonNull OperationId id) {
		js.appendIdReference(id.getParent());
		js.append(".getOperationId(" + id.getTemplateParameters() + ", ");
		js.appendString(id.getName());
		js.append(", ");
		js.appendClassReference(IdManager.class);
		js.append(".getParametersId(");
		boolean isFirst = true;
		for (@SuppressWarnings("null")@NonNull TypeId parameterId : id.getParametersId()) {
			if (!isFirst) {
				js.append(", ");
			}
			js.appendIdReference(parameterId);
			isFirst = false;
		}
		js.append("))");
		return null;
	}

	public @Nullable Object visitPrimitiveTypeId(@NonNull PrimitiveTypeId id) {
		js.appendClassReference(TypeId.class);
		js.append(".");
		js.append(id.getLiteralName());
		return null;
	}

	public @Nullable Object visitPropertyId(@NonNull PropertyId id) {
		js.appendIdReference(id.getParent());
		js.append(".getPropertyId(");
		js.appendString(id.getName());
		js.append(")");
		return null;
	}

	public @Nullable Object visitRootPackageId(@NonNull RootPackageId id) {
		js.appendClassReference(IdManager.class);
		js.append(".getRootPackageId(");
		js.appendString(id.getName());
		js.append(")");
		return null;
	}

	public @Nullable Object visitTemplateBinding(@NonNull TemplateBinding id) {
		// TODO Auto-generated method stub
		return visiting(id);
	}

	public @Nullable Object visitTemplateParameterId(@NonNull TemplateParameterId id) {
		js.append("visitTemplateParameterId");
		return null;
	}

	public @Nullable Object visitTemplateableTypeId(@NonNull TemplateableTypeId id) {
		// TODO Auto-generated method stub
		return visiting(id);
	}

	public @Nullable Object visitTuplePartId(@NonNull TuplePartId id) {
		js.appendClassReference(IdManager.class);
		js.append(".getTuplePartId(" + id.getIndex() + ", ");
		js.appendString(id.getName());
		js.append(", ");
		js.appendIdReference(id.getTypeId());
		js.append(")");
		return null;
	}

	public @Nullable Object visitTupleTypeId(@NonNull TupleTypeId id) {
		js.appendClassReference(IdManager.class);
		js.append(".getTupleTypeId(");
		js.appendString(id.getName());
		for (@SuppressWarnings("null")@NonNull TuplePartId partId : id.getPartIds()) {
			js.append(", ");
			js.appendIdReference(partId);
		}
		js.append(")");
		return null;
	}

	public @Nullable Object visitUnspecifiedId(@NonNull UnspecifiedId id) {
		// TODO Auto-generated method stub
		return visiting(id);
	}
	
	public @Nullable Object visiting(@NonNull ElementId id) {
		throw new UnsupportedOperationException(getClass().getSimpleName() + ": " + id.getClass().getName());
	}
}
