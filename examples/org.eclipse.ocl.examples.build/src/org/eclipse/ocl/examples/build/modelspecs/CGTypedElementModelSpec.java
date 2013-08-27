/**
 * <copyright>
 *
 * Copyright (c) 2013 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *	 E.D.Willink - initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.ocl.examples.build.modelspecs;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.codegen.cgmodel.CGExecutorType;
import org.eclipse.ocl.examples.codegen.cgmodel.CGText;
import org.eclipse.ocl.examples.codegen.cgmodel.CGTypeId;
import org.eclipse.ocl.examples.codegen.cgmodel.CGTypedElement;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.elements.DomainTypedElement;
import org.eclipse.ocl.examples.domain.ids.TypeId;

/**
 * CGTypedElementModelSpec supports generation of the CGTypedElement.getASTypeId() method hierarchy.
 */
public class CGTypedElementModelSpec extends ModelSpec
{
	protected abstract static class MyMethodSpec extends MethodSpec
	{
		public MyMethodSpec(@NonNull Class<?> rootClass, @NonNull String interfaceDecl, @Nullable String variableDecl, @Nullable String comment) {
			super(rootClass, interfaceDecl, variableDecl, comment);
		}

		@Override
		protected final @Nullable String getBody(@NonNull ModelSpec modelSpec) {
			if (modelSpec instanceof CGTypedElementModelSpec) {
				return getBody((CGTypedElementModelSpec)modelSpec);
			}
			else {
				return null;
			}
		}

		protected abstract @Nullable String getBody(@NonNull CGTypedElementModelSpec cgModelSpec);		
	}

	/**
	 * The algorithm options for getASTypeId()
	 */
	public interface Ati {
		@NonNull String generate();
	}
	
	public static final @NonNull Ati ATI_ROOT = new Ati() { @Override public @NonNull String generate() {
		return "return ast instanceof " + classRef(DomainTypedElement.class) + " ? ((" + classRef(DomainTypedElement.class) + ") ast).getTypeId() : null;";
	}};
	public static final @NonNull Ati ATI_TEXT = new Ati() { @Override public @NonNull String generate() {
		return "return (" + classRef(TypeId.class) + ") getTypeId().getElementId();		// FIXME Why irregular?";
	}};
	public static final @NonNull Ati ATI_TYPE = new Ati() { @Override public @NonNull String generate() {
		return "return ast instanceof " + classRef(DomainType.class) + " ? ((" + classRef(DomainType.class) + ") ast).getTypeId() : null;";
	}};
	public static final @NonNull Ati ATI_T_ID = new Ati() { @Override public @NonNull String generate() {
		return "return (" + classRef(TypeId.class) + ")elementId;";
	}};
		
	protected static MethodSpec getASTypeId = new MyMethodSpec(CGTypedElement.class, "@Nullable " + classRef(TypeId.class) + " getASTypeId()", null,
		"Return the TypeId of the AS element.")
		{
			@Override
			protected @Nullable String getBody(@NonNull CGTypedElementModelSpec modelSpec) {
				Ati ati = modelSpec.ati;
				return ati != null ? ati.generate() : null;
			}
		};

	public static void register() {
		new CGTypedElementModelSpec(CGTypedElement.class, ATI_ROOT );
		new CGTypedElementModelSpec(CGExecutorType.class, ATI_TYPE );
		new CGTypedElementModelSpec(CGText.class, ATI_TEXT );
		new CGTypedElementModelSpec(CGTypeId.class, ATI_T_ID );
	}

	protected final @Nullable Ati ati;
	
	protected CGTypedElementModelSpec(@NonNull Class<?> cgClass, @Nullable Ati ati) {
		super(cgClass);
		this.ati = ati;
	}
	
	@Override
	public void generate(@NonNull StringBuilder s, @NonNull GenModel genModel, boolean isImplementation) {
		getASTypeId.generate(s, this, genModel, isImplementation);
	}
}
