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
package org.eclipse.ocl.examples.codegen.analyzer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.codegen.cgmodel.CGBoxExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGCatchExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGCollectionExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGCollectionPart;
import org.eclipse.ocl.examples.codegen.cgmodel.CGOperationCallExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGPropertyCallExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGString;
import org.eclipse.ocl.examples.codegen.cgmodel.CGThrowExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGTypeId;
import org.eclipse.ocl.examples.codegen.cgmodel.CGUnboxExp;
import org.eclipse.ocl.examples.codegen.cgmodel.CGValuedElement;
import org.eclipse.ocl.examples.domain.elements.DomainType;
import org.eclipse.ocl.examples.domain.elements.Nameable;
import org.eclipse.ocl.examples.domain.ids.ClassId;
import org.eclipse.ocl.examples.domain.ids.CollectionTypeId;
import org.eclipse.ocl.examples.domain.ids.DataTypeId;
import org.eclipse.ocl.examples.domain.ids.ElementId;
import org.eclipse.ocl.examples.domain.ids.EnumerationId;
import org.eclipse.ocl.examples.domain.ids.EnumerationLiteralId;
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
import org.eclipse.ocl.examples.domain.ids.TemplateBinding;
import org.eclipse.ocl.examples.domain.ids.TemplateParameterId;
import org.eclipse.ocl.examples.domain.ids.TemplateableTypeId;
import org.eclipse.ocl.examples.domain.ids.TuplePartId;
import org.eclipse.ocl.examples.domain.ids.TupleTypeId;
import org.eclipse.ocl.examples.domain.ids.TypeId;
import org.eclipse.ocl.examples.domain.ids.UnspecifiedId;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.domain.values.CollectionValue;
import org.eclipse.ocl.examples.domain.values.IntegerRange;
import org.eclipse.ocl.examples.domain.values.IntegerValue;
import org.eclipse.ocl.examples.domain.values.InvalidValue;
import org.eclipse.ocl.examples.domain.values.RealValue;
import org.eclipse.ocl.examples.pivot.BagType;
import org.eclipse.ocl.examples.pivot.CollectionLiteralExp;
import org.eclipse.ocl.examples.pivot.CollectionRange;
import org.eclipse.ocl.examples.pivot.CollectionType;
import org.eclipse.ocl.examples.pivot.ExpressionInOCL;
import org.eclipse.ocl.examples.pivot.IntegerLiteralExp;
import org.eclipse.ocl.examples.pivot.Iteration;
import org.eclipse.ocl.examples.pivot.LiteralExp;
import org.eclipse.ocl.examples.pivot.LoopExp;
import org.eclipse.ocl.examples.pivot.Operation;
import org.eclipse.ocl.examples.pivot.OperationCallExp;
import org.eclipse.ocl.examples.pivot.OrderedSetType;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.PropertyCallExp;
import org.eclipse.ocl.examples.pivot.RealLiteralExp;
import org.eclipse.ocl.examples.pivot.SequenceType;
import org.eclipse.ocl.examples.pivot.SetType;
import org.eclipse.ocl.examples.pivot.StringLiteralExp;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.TypeExp;
import org.eclipse.ocl.examples.pivot.UnlimitedNaturalLiteralExp;
import org.eclipse.ocl.examples.pivot.VariableDeclaration;
import org.eclipse.ocl.examples.pivot.VariableExp;

/**
 * A NameManager provides suggestions for names and maintains caches of used names so that model elements are consistently
 * named without collisions. 
 */
public class NameManager
{
	public static final String BAG_NAME_HINT_PREFIX = "BAG";
	public static final String COLLECTION_NAME_HINT_PREFIX = "COL";
	public static final String DEFAULT_NAME_PREFIX = "symbol";
//	public static final String ID_NAME_HINT_PREFIX = "TID";
	public static final String EXPRESSION_IN_OCL_NAME_HINT_PREFIX = "result";
	public static final String INTEGER_NAME_HINT_PREFIX = "INT_";
	public static final String INVALID_NAME_HINT_PREFIX = "IVE_";
	public static final String ITERATION_NAME_HINT_PREFIX = "";
	public static final String OPERATION_NAME_HINT_PREFIX = "OP_";
	public static final String OPERATION_CALL_EXP_NAME_HINT_PREFIX = ""; //"RES_";
	public static final String ORDERED_SET_NAME_HINT_PREFIX = "ORD";
	public static final String PROPERTY_NAME_HINT_PREFIX = "";
	public static final String REAL_NAME_HINT_PREFIX = "REA_";
	public static final String RANGE_NAME_HINT_PREFIX = "RNG";
	public static final String SEQUENCE_NAME_HINT_PREFIX = "SEQ";
	public static final String SET_NAME_HINT_PREFIX = "SET";
	public static final String STRING_NAME_HINT_PREFIX = "STR_";
	public static final String TYPE_NAME_HINT_PREFIX = "TYP_";
	public static final String VARIABLE_DECLARATION_NAME_HINT_PREFIX = "";
	
	/**
	 * Names that will not be allocated to temporary variables.
	 * <p>
	 * This Set is public and unsynchronized. Clients may change it in arbitrary ways at their own risk.
	 * <p>
	 * It is strongly recommended that clients do no more than add additional names.
	 */
	public static final Set<String> reservedJavaNames = new HashSet<String>();
	{
		reservedJavaNames.add("Boolean");
		reservedJavaNames.add("Character");
		reservedJavaNames.add("Class");
		reservedJavaNames.add("Double");
		reservedJavaNames.add("Float");
		reservedJavaNames.add("Integer");
		reservedJavaNames.add("List");
		reservedJavaNames.add("Long");
		reservedJavaNames.add("Map");
		reservedJavaNames.add("Package");
		reservedJavaNames.add("String");
		
		reservedJavaNames.add("boolean");
		reservedJavaNames.add("byte");
		reservedJavaNames.add("char");
		reservedJavaNames.add("double");
		reservedJavaNames.add("float");
		reservedJavaNames.add("int");
		reservedJavaNames.add("long");
		reservedJavaNames.add("short");
		reservedJavaNames.add("void");
		
		reservedJavaNames.add("abstract");		// FIXME Exploit CodeGenUtil.getJavaReservedWords()
		reservedJavaNames.add("assert");
		reservedJavaNames.add("break");
		reservedJavaNames.add("case");
		reservedJavaNames.add("catch");
		reservedJavaNames.add("class");
		reservedJavaNames.add("const");
		reservedJavaNames.add("continue");
		reservedJavaNames.add("default");
		reservedJavaNames.add("do");
		reservedJavaNames.add("else");
		reservedJavaNames.add("enum");
		reservedJavaNames.add("extends");
		reservedJavaNames.add("final");
		reservedJavaNames.add("finally");
		reservedJavaNames.add("for");
		reservedJavaNames.add("goto");
		reservedJavaNames.add("if");
		reservedJavaNames.add("implements");
		reservedJavaNames.add("import");
		reservedJavaNames.add("instanceof");
		reservedJavaNames.add("interface");
		reservedJavaNames.add("native");
		reservedJavaNames.add("new");
		reservedJavaNames.add("package");
		reservedJavaNames.add("private");
		reservedJavaNames.add("protected");
		reservedJavaNames.add("public");
		reservedJavaNames.add("return");
		reservedJavaNames.add("static");
		reservedJavaNames.add("strictfp");
		reservedJavaNames.add("switch");
		reservedJavaNames.add("synchronized");
		reservedJavaNames.add("throw");
		reservedJavaNames.add("throws");
		reservedJavaNames.add("transient");
		reservedJavaNames.add("try");
		reservedJavaNames.add("volatile");
		reservedJavaNames.add("while");

		reservedJavaNames.add("false");
		reservedJavaNames.add("null");
		reservedJavaNames.add("super");
		reservedJavaNames.add("this");
		reservedJavaNames.add("true");
	}

	public static @NonNull IdVisitor<String> idVisitor = new IdVisitor<String>()
	{
		public @NonNull String visitClassId(@NonNull ClassId id) {
			return "CLSSid_" + id.getName();
		}

		public @NonNull String visitCollectionTypeId(@NonNull CollectionTypeId id) {
			CollectionTypeId generalizedId = id.getGeneralizedId();
			String idPrefix;
			if (generalizedId == TypeId.BAG) {
				idPrefix = "BAG_";
			}
			else if (generalizedId == TypeId.ORDERED_SET) {
				idPrefix = "ORD_";
			}
			else if (generalizedId == TypeId.SEQUENCE) {
				idPrefix = "SEQ_";
			}
			else if (generalizedId == TypeId.SET) {
				idPrefix = "SET_";
			}
			else {
				idPrefix = "COL_";
			}
			if (generalizedId == id) {
				return idPrefix;
			}
			else {
				return idPrefix + id.getElementTypeId().accept(this);
			}
		}

		public @NonNull String visitDataTypeId(@NonNull DataTypeId id) {
			return "DATAid_" + id.getName();
		}

		public @NonNull String visitEnumerationId(@NonNull EnumerationId id) {
			return "ENUMid_" + id.getName();
		}

		public @NonNull String visitEnumerationLiteralId(@NonNull EnumerationLiteralId id) {
			return "ELITid_" + id.getName();
		}

		public @NonNull String visitInvalidId(@NonNull OclInvalidTypeId id) {
			return "INVid";
		}

		public @NonNull String visitLambdaTypeId(@NonNull LambdaTypeId id) {
			return "LAMBid_" + id.getName();
		}

		public @NonNull String visitMetaclassId(@NonNull MetaclassId id) {
			return "METAid_" + id.getName();
		}

		public @NonNull String visitNestedPackageId(@NonNull NestedPackageId id) {
			return "PACKid_" + id.getName();
		}

		public @NonNull String visitNsURIPackageId(@NonNull NsURIPackageId id) {
			return "PACKid_" + id.getNsURI();
		}

		public @NonNull String visitNullId(@NonNull OclVoidTypeId id) {
			return "NULLid";
		}

		public @NonNull String visitOperationId(@NonNull OperationId id) {
			return "OPid_" + id.getName();
		}

		public @NonNull String visitPrimitiveTypeId(@NonNull PrimitiveTypeId id) {
			return "PRIMid_" + id.getName();
		}

		public @NonNull String visitPropertyId(@NonNull PropertyId id) {
			return "PROPid_" + id.getName();
		}

		public @NonNull String visitRootPackageId(@NonNull RootPackageId id) {
			return "PACKid_" + id.getName();
		}

		public @NonNull String visitTemplateBinding(@NonNull TemplateBinding id) {
			return "BINDid_";
		}

		public @NonNull String visitTemplateParameterId(@NonNull TemplateParameterId id) {
			return "TMPLid_";
		}

		public @NonNull String visitTemplateableTypeId(@NonNull TemplateableTypeId id) {
			return "TYPEid_";
		}

		public @NonNull String visitTuplePartId(@NonNull TuplePartId id) {
			return "PARTid_";
		}

		public @NonNull String visitTupleTypeId(@NonNull TupleTypeId id) {
			return "TUPLid_";
		}

		public @NonNull String visitUnspecifiedId(@NonNull UnspecifiedId id) {
			return "UNSPid_";
		}
	};
	
	protected static void appendJavaCharacters(StringBuilder s, String string) {
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if (Character.isJavaIdentifierPart(c)) {
				s.append(c);
			}
			else {
				s.append('_');
			}
		}
	}

	protected static void appendJavaCharacters(StringBuilder s, String string, int iMax) {
		for (int i = 0; i < Math.min(iMax, string.length()); i++) {
			char c = string.charAt(i);
			if (Character.isJavaIdentifierPart(c)) {
				s.append(c);
			}
			else {
				s.append('_');
			}
		}
	}

	/**
	 * Return a valid Java identifier based on nameHint. hasPrefix may be true to indicate that the
	 * caller will supply an additional valid prefix relieving this routine of the need to avoid
	 * leading numeric characters.
	 * <p>
	 * This is not intended to be a reversible algorithm; just to provide something reasonably readable.
	 */
	private static @NonNull String getValidJavaIdentifier(@NonNull String nameHint, boolean hasPrefix, @Nullable Object anObject) {
		if (nameHint.equals("<")) {
			return("lt");
		}
		else if (nameHint.equals("<=")) {
			return("le");
		}
		else if (nameHint.equals("=")) {
			return("eq");
		}
		else if (nameHint.equals("<>")) {
			return("ne");
		}
		else if (nameHint.equals(">=")) {
			return("ge");
		}
		else if (nameHint.equals(">")) {
			return("gt");
		}
		else if (nameHint.equals("+")) {
			return("sum");
		}
		else if (nameHint.equals("-")) {
			return((anObject instanceof Operation) && ((Operation)anObject).getOwnedParameter().size() <= 0 ? "neg" : "diff");
		}
		else if (nameHint.equals("*")) {
			return("prod");
		}
		else if (nameHint.equals("/")) {
			return("quot");
		}
		else if (nameHint.equals("1_")) {
			return("_1");
		}
		else if (nameHint.equals("2_")) {
			return("_2");
		}
		StringBuilder s = new StringBuilder();
		Character prefix = null;
		int length = nameHint.length();
		for (int i = 0; i < length; i++) {
			char c = nameHint.charAt(i);
			if (((i == 0) && !hasPrefix) ? Character.isJavaIdentifierStart(c) : Character.isJavaIdentifierPart(c)) {
				if (prefix != null) {
					s.append(prefix);
					prefix = null;
				}
				s.append(c);
			}
			else {
				if (c == '*') {
					s.append("_a");
				}
				else if (c == ':') {
					s.append("_c");
				}
				else if (c == '.') {
					if (prefix != null) {
						s.append(prefix);
						prefix = null;
					}
				}
				else if (c == ')') {
					s.append("_e");
				}
				else if (c == '>') {
					s.append("_g");
				}
				else if (c == '<') {
					s.append("_l");
				}
				else if (c == '-') {
					s.append("_m");
				}
				else if (c == '(') {
					s.append("_o");
				}
				else if (c == '+') {
					s.append("_p");
				}
				else if (c == '=') {
					s.append("_q");
				}
				else if (c == '/') {
					s.append("_s");
				}
				else {
					s.append('_' + Integer.toString(c));
				}
				prefix = '_';
			}
		}
		@SuppressWarnings("null") @NonNull String string = s.toString();
		return string;
	}
	
	public class Context {
		private final @Nullable Context context;					// Pushed context
		private final @NonNull Map<String, Object> name2object;		// User of each name, null if name ambiguous
		private final @NonNull Map<Object, String> object2name;		// Unambiguous name for each object, null if not determined
		private Map<String, Integer> name2counter;					// Auto-generation counter for each colliding name
//		private boolean frozen = false;								// Set true once pushed

		public Context() {
			this.context = null;
			this.name2object = new HashMap<String, Object>();
			this.object2name = new HashMap<Object, String>();
			this.name2counter = null;
		}

		public Context(@NonNull Context context) {
			this.context = context;
			this.name2object = new HashMap<String, Object>(context.name2object);
			this.object2name = new HashMap<Object, String>(context.object2name);
			this.name2counter = context.name2counter != null ? new HashMap<String, Integer>(context.name2counter) : null;
//			context.frozen = true;
		}

		public @NonNull Context createNestedContext() {
			return new Context(this);
		}

		public @NonNull Context getContext() {
			return DomainUtil.nonNullState(context);
		}
		
		protected @NonNull String getGlobalUniqueName(@Nullable Object anObject, @Nullable String... nameHints) {
			if (context != null) {
				return context.getGlobalUniqueName(anObject, nameHints);
			}
			else {
				return getUniqueName(anObject, nameHints);
			}
		}

		public @NonNull String getSymbolName(@Nullable Object anObject, @Nullable String... nameHints) {
			if ((nameHints != null) && (nameHints.length > 0) && (nameHints[0] != null)) {
				return getUniqueName(anObject, nameHints);
			}
			else {
				return getUniqueName(anObject, anObject != null ? getNameHint(anObject) : null);
			}
		}

		/**
		 * Return a unique name using some nameHints to suggest preferred names and allocate that name to anObject.
		 * <p>
		 * If anObject is non-null, any already allocated name is returned rather than allocating another name.
		 * <p>
		 * If anObject is null, the returned name is allocated to no object; not to the null value.
		 * <p>
		 * If nameHints is null a default name is generated.
		 * <p>
		 */
		public @NonNull String getUniqueName(@Nullable Object anObject, @Nullable String... nameHints) {
			if ((anObject instanceof RealValue) && !(anObject instanceof InvalidValue)) {
				anObject = ((RealValue)anObject).asNumber();
			}
			if (anObject != null) {
				String knownName = object2name.get(anObject);
				if (knownName != null) {
					return knownName;
				}
			}
			String lastResort = null;
			if (nameHints != null) {
				for (String nameHint : nameHints) {
					if (nameHint != null)  {
						String validHint = getValidJavaIdentifier(nameHint, false, anObject);
						if (!reservedJavaNames.contains(validHint)) {
							if (anObject != null) {
								Object oldElement = name2object.get(validHint);
								if (oldElement == anObject) {
									return validHint;
								}
								if ((oldElement == null) && !name2object.containsKey(validHint)) {
									install(validHint, anObject);
									return validHint;
								}
							}
							else {
								if (!name2object.containsKey(validHint)) {
									install(validHint, anObject);
									return validHint;
								}
							}
							if (lastResort == null) {
								lastResort = validHint;
							}
						}
					}
				}
			}
			if (lastResort == null) {
				lastResort = DEFAULT_NAME_PREFIX;
			}
			if (name2counter == null) {
				name2counter = new HashMap<String, Integer>();
			}
			Integer counter = name2counter.get(lastResort);
			int count = counter != null ? counter : 0;			
			for ( ; true; count++) {
				String attempt = lastResort + "_" + Integer.toString(count);
				if (!name2object.containsKey(attempt)) {		// Assumes that reserved names do not end in _ count
					install(attempt, anObject);
					name2counter.put(lastResort, ++count);
					return attempt;
				}
			}
		}
		
		private void install(@NonNull String name, @Nullable Object anObject) {
//FIXME			assert !frozen;
			assert !(anObject instanceof RealValue) || (anObject instanceof InvalidValue);
			name2object.put(name, anObject);
			if (anObject != null) {
				object2name.put(anObject, name);
			}
		}
		
		/**
		 * Reserve name for use by anObject. If anObject is null, the reservation is for an unspecified object not for the null value.
		 *
		public @NonNull String reserveName(@NonNull String name, @Nullable Object anObject) {
			assert !frozen;
			assert !(anObject instanceof RealValue);
			String validJavaIdentifier = getUniqueName(anObject, getValidJavaIdentifier(name, true, anObject));
			Object oldElement = name2object.put(validJavaIdentifier, anObject);		// FIXME redundant
			assert (oldElement == null) || (oldElement == anObject);
			return validJavaIdentifier;
		} */
	}

	private @NonNull Context context = new Context();

	public NameManager() {}

	public @NonNull Context createNestedContext() {
		return new Context(context);
	}

	public @NonNull String getExplicitName(@Nullable Object anObject) {
		if (anObject == null) {
			return "null";
		}
		else if (anObject instanceof Boolean) {
			return ((Boolean)anObject).booleanValue() ? "true" : "false";
		}
		else {
			return "<null-" + anObject.getClass().getSimpleName() + ">";
		}
	}

	public @NonNull String getGlobalSymbolName(@Nullable Object anObject, @Nullable String... nameHints) {
		if ((nameHints != null) && (nameHints.length > 0)) {
			return getGlobalUniqueName(anObject, nameHints);
		}
		else {
			return getGlobalUniqueName(anObject, anObject != null ? getNameHint(anObject) : null);
		}
	}
	protected @NonNull String getGlobalUniqueName(@Nullable Object anObject, @Nullable String... nameHints) {
		return context.getGlobalUniqueName(anObject, nameHints);
	}

	protected String getIterationNameHint(@NonNull Iteration anIteration) {
		@SuppressWarnings("null") @NonNull String string = anIteration.getName();
		return ITERATION_NAME_HINT_PREFIX + getValidJavaIdentifier(string, ITERATION_NAME_HINT_PREFIX.length() > 0, anIteration);
	}

	protected String getKindHint(@NonNull String kind) {
		if (TypeId.BAG_NAME.equals(kind)) {
			return BAG_NAME_HINT_PREFIX;
		}
		else if (TypeId.ORDERED_SET_NAME.equals(kind)) {
			return ORDERED_SET_NAME_HINT_PREFIX;
		}
		else if (TypeId.SEQUENCE_NAME.equals(kind)) {
			return SEQUENCE_NAME_HINT_PREFIX;
		}
		else if (TypeId.SET_NAME.equals(kind)) {
			return SET_NAME_HINT_PREFIX;
		}
		else {
			return COLLECTION_NAME_HINT_PREFIX;
		}
	}

	/**
	 * Return a suggestion for the name of anObject.
	 * <p>
	 * The returned name is not guaranteed to be unique. Uniqueness is enforced when the hint is passed to getSymbolName(). 
	 */
	public @Nullable String getNameHint(@NonNull Object anObject) {
		if (anObject instanceof CGValuedElement) {
			anObject = ((CGValuedElement)anObject).getValue();
		}
		if (anObject instanceof CollectionLiteralExp) {
			Type type = ((CollectionLiteralExp)anObject).getType();
			return type != null ? getTypeNameHint(type) : null;
		}
		else if (anObject instanceof CollectionRange) {
			return RANGE_NAME_HINT_PREFIX;
		}
		else if (anObject instanceof CGCollectionPart) {
			return RANGE_NAME_HINT_PREFIX;
		}
		else if (anObject instanceof InvalidValue) {
			return INVALID_NAME_HINT_PREFIX;
		}
		else if (anObject instanceof CollectionValue) {
			String kind = ((CollectionValue)anObject).getKind();
			return kind != null ? getKindHint(kind) : null;
		}
		else if (anObject instanceof CGCollectionExp) {
			String kind = ((CGCollectionExp)anObject).getName();
			return kind != null ? getKindHint(kind) : null;
		}
		else if (anObject instanceof ElementId) {
			String nameHint = ((ElementId)anObject).accept(idVisitor);
			return nameHint;
		}
		else if (anObject instanceof ExpressionInOCL) {
			return EXPRESSION_IN_OCL_NAME_HINT_PREFIX;
		}
		else if (anObject instanceof IntegerLiteralExp) {
			Number numberSymbol = ((IntegerLiteralExp)anObject).getIntegerSymbol();
			return numberSymbol != null ? getNumericNameHint(numberSymbol) : null;
		}
		else if (anObject instanceof IntegerRange) {
			return RANGE_NAME_HINT_PREFIX;
		}
		else if (anObject instanceof IntegerValue) {
			Number numberSymbol = ((IntegerValue)anObject).asNumber();
			return getNumericNameHint(numberSymbol);
		}
		else if (anObject instanceof LoopExp) {
			Iteration referredIteration = ((LoopExp)anObject).getReferredIteration();
			return referredIteration != null ? getIterationNameHint(referredIteration) : null;
		}
		else if (anObject instanceof Number) {
			return getNumericNameHint((Number)anObject);
		}
		else if (anObject instanceof Operation) {
			return getOperationNameHint((Operation)anObject);
		}
		else if (anObject instanceof OperationCallExp) {
			Operation referredOperation = ((OperationCallExp)anObject).getReferredOperation();
			return referredOperation != null ? getOperationCallExpNameHint(referredOperation) : null;
		}
		else if (anObject instanceof CGOperationCallExp) {
			if (anObject instanceof CGBoxExp) {
				return "box";
			}
			else if (anObject instanceof CGUnboxExp) {
				return "unbox";
			}
			else if (anObject instanceof CGCatchExp) {
				return "catch";
			}
			else if (anObject instanceof CGThrowExp) {
				return "throw";
			}
			else {
				Operation referredOperation = ((OperationCallExp)((CGOperationCallExp)anObject).getPivot()).getReferredOperation();
				return referredOperation != null ? getOperationCallExpNameHint(referredOperation) : null;
			}
		}
		else if (anObject instanceof PropertyCallExp) {
			Property referredProperty = ((PropertyCallExp)anObject).getReferredProperty();
			return referredProperty != null ? getPropertyNameHint(referredProperty) : null;
		}
		else if (anObject instanceof CGPropertyCallExp) {
			Property referredProperty = ((PropertyCallExp)((CGPropertyCallExp)anObject).getPivot()).getReferredProperty();
			return referredProperty != null ? getPropertyNameHint(referredProperty) : null;
		}
		else if (anObject instanceof RealLiteralExp) {
			Number numberSymbol = ((RealLiteralExp)anObject).getRealSymbol();
			return numberSymbol != null ? getNumericNameHint(numberSymbol) : null;
		}
		else if (anObject instanceof RealValue) {
			Number numberSymbol = ((RealValue)anObject).asNumber();
			return getNumericNameHint(numberSymbol);
		}
		else if (anObject instanceof String) {
			return getStringNameHint((String)anObject);
		}
		else if (anObject instanceof CGString) {
			String stringValue = ((CGString)anObject).getStringValue();
			return stringValue != null ? getStringNameHint(stringValue) : null;
		}
		else if (anObject instanceof StringLiteralExp) {
			String stringSymbol = ((StringLiteralExp)anObject).getStringSymbol();
			return stringSymbol != null ? getStringNameHint(stringSymbol) : null;
		}
		else if (anObject instanceof DomainType) {
			return getTypeNameHint((DomainType)anObject);
		}
		else if (anObject instanceof CGTypeId) {
			return getTypeNameHint((DomainType) ((CGTypeId)anObject).getPivot());
		}
		else if (anObject instanceof TypeExp) {
			Type referredType = ((TypeExp)anObject).getType();
			return referredType != null ? getTypeNameHint(referredType) : null;
		}
//		else if (anObject instanceof TypeValue) {
//			DomainType referredType = ((TypeValue)anObject).getInstanceType();
//			return getTypeNameHint(referredType);
//		}
		else if (anObject instanceof UnlimitedNaturalLiteralExp) {
			Number numberSymbol = ((UnlimitedNaturalLiteralExp)anObject).getUnlimitedNaturalSymbol();
			return numberSymbol != null ? getNumericNameHint(numberSymbol) : null;
		}
		else if (anObject instanceof VariableExp) {
			VariableDeclaration referredVariable = ((VariableExp)anObject).getReferredVariable();
			return referredVariable != null ? getVariableDeclarationNameHint(referredVariable) : null;
		}
		else if (anObject instanceof LiteralExp) {
			return "literal";
		}
		else if (anObject instanceof Nameable) {
			String name = ((Nameable)anObject).getName();
			return name != null ? getValidJavaIdentifier(name, false, anObject) : null;
		}
		else {
			return null;
		}
	}

	protected String getNumericNameHint(@NonNull Number aNumber) {
		@SuppressWarnings("null") @NonNull String string = aNumber.toString();
		if ((aNumber instanceof BigInteger) || (aNumber instanceof Long) || (aNumber instanceof Integer) || (aNumber instanceof Short)) {
			return INTEGER_NAME_HINT_PREFIX + string;
		}
		else if ((aNumber instanceof BigDecimal) || (aNumber instanceof Double) || (aNumber instanceof Float)) {
			return REAL_NAME_HINT_PREFIX + getValidJavaIdentifier(string, REAL_NAME_HINT_PREFIX.length() > 0, aNumber);
		}
		else {
			return null;
		}
	}

	protected String getOperationNameHint(@NonNull Operation anOperation) {
		@SuppressWarnings("null") @NonNull String string = anOperation.toString();
		return OPERATION_NAME_HINT_PREFIX + getValidJavaIdentifier(string, OPERATION_NAME_HINT_PREFIX.length() > 0, anOperation);
	}

	protected String getOperationCallExpNameHint(@NonNull Operation anOperation) {
		@SuppressWarnings("null") @NonNull String string = anOperation.getName();
		return OPERATION_CALL_EXP_NAME_HINT_PREFIX + getValidJavaIdentifier(string, OPERATION_CALL_EXP_NAME_HINT_PREFIX.length() > 0, anOperation);
	}

	protected String getPropertyNameHint(@NonNull Property aProperty) {
		@SuppressWarnings("null") @NonNull String string = aProperty.getName();
		return PROPERTY_NAME_HINT_PREFIX + getValidJavaIdentifier(string, PROPERTY_NAME_HINT_PREFIX.length() > 0, aProperty);
	}

	protected String getStringNameHint(@NonNull String aString) {
		@SuppressWarnings("null") @NonNull String string = aString.length() > 20 ? aString.substring(0, 20) : aString;
		return STRING_NAME_HINT_PREFIX + getValidJavaIdentifier(string, STRING_NAME_HINT_PREFIX.length() > 0, aString);
	}

	protected String getTypeNameHint(@NonNull DomainType aType) {
		if (aType instanceof CollectionType) {
			if (aType instanceof OrderedSetType) {
				return ORDERED_SET_NAME_HINT_PREFIX;
			}
			else if (aType instanceof SetType) {
				return SET_NAME_HINT_PREFIX;
			}
			else if (aType instanceof SequenceType) {
				return SEQUENCE_NAME_HINT_PREFIX;
			}
			else if (aType instanceof BagType) {
				return BAG_NAME_HINT_PREFIX;
			}
			else {
				return COLLECTION_NAME_HINT_PREFIX;
			}
		}
		@SuppressWarnings("null") @NonNull String string = aType.toString();
		return TYPE_NAME_HINT_PREFIX + getValidJavaIdentifier(string, TYPE_NAME_HINT_PREFIX.length() > 0, aType);
	}

	protected String getVariableDeclarationNameHint(@NonNull VariableDeclaration aVariableDeclaration) {
		String string = DomainUtil.nonNullModel(aVariableDeclaration.getName());
		return VARIABLE_DECLARATION_NAME_HINT_PREFIX + getValidJavaIdentifier(string, VARIABLE_DECLARATION_NAME_HINT_PREFIX.length() > 0, aVariableDeclaration);
	}
	
	/**
	 * Reserve name for use by anObject. If anObject is null, the reservation is for an unspecified object not for the null value.
	 */
	public @NonNull String reserveName(@NonNull String name, @Nullable Object anObject) {
		return context.getSymbolName(anObject, name);
	}
}
