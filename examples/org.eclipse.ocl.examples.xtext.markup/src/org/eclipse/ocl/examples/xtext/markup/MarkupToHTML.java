/**
 * <copyright>
 *
 * Copyright (c) 2011 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.ocl.examples.xtext.markup;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.examples.pivot.ExpressionInOcl;
import org.eclipse.ocl.examples.pivot.OCL;
import org.eclipse.ocl.examples.pivot.ParserException;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.ecore.Ecore2Pivot;
import org.eclipse.ocl.examples.pivot.helper.OCLHelper;
import org.eclipse.ocl.examples.pivot.prettyprint.PrettyPrintExprVisitor;
import org.eclipse.ocl.examples.pivot.prettyprint.PrettyPrintOptions;
import org.eclipse.ocl.examples.pivot.prettyprint.PrettyPrintTypeVisitor;
import org.eclipse.ocl.examples.pivot.utilities.HTMLBuffer;
import org.eclipse.ocl.examples.pivot.utilities.PivotEnvironmentFactory;
import org.eclipse.ocl.examples.pivot.utilities.TypeManager;
import org.eclipse.ocl.examples.pivot.values.StringValue;
import org.eclipse.ocl.examples.pivot.values.Value;
import org.eclipse.ocl.examples.xtext.markup.util.MarkupSwitch;

/**
 * MarkupToHTML gives an HTML presentation of the markup.
 */
public class MarkupToHTML extends MarkupSwitch<HTMLBuffer>
{
	@SuppressWarnings("serial")
	public static class InvalidMarkupException extends RuntimeException
	{
		public InvalidMarkupException(Exception e) {
			super(e);
		}		
	}
	
	public static String toString(TypeManager typeManager, Object context, MarkupElement element) throws Exception {
		MarkupToHTML toString = new MarkupToHTML(typeManager, context);
		try {
			return toString.doSwitch(element).toString();
		} catch (InvalidMarkupException e) {
			throw (Exception)e.getCause();
		}
	}
	
	private OCL ocl = null;
	private TypeManager typeManager;
	protected final Object context;
	protected final HTMLBuffer s = new HTMLBuffer();

	public MarkupToHTML(TypeManager typeManager, Object context) {
		this.typeManager = typeManager;
		this.context = context;
	}	
	
	@Override
	public HTMLBuffer caseCompoundElement(CompoundElement object) {
		for (MarkupElement element : object.getElements()) {
			doSwitch(element);
		}
		return s;
	}

	@Override
	public HTMLBuffer caseFontElement(FontElement object) {
		String font = object.getFont();
		String htmlFont;
		if ("b[".equals(font)) {
			htmlFont = "b";
		}
		else if ("e[".equals(font)) {
			htmlFont = "i";
		}
		else {
			htmlFont = "???";
		}
		s.startFontName(htmlFont);
		caseCompoundElement(object);
		s.endFontName(htmlFont);
		return s;
	}

	@Override
	public HTMLBuffer caseNewLineElement(NewLineElement object) {
		int newLines = MarkupUtils.getNewlineCount(object);
		if (newLines <= 1) {
			s.append("\n");
		}
		else {
			s.append("\n");
			s.endParagraph();
			s.startParagraph();
			s.append("\n");
		}
		return s;
	}

	@Override
	public HTMLBuffer caseNullElement(NullElement object) {
		s.append('[');
		caseCompoundElement(object);
		s.append(']');
		return s;
	}

	@Override
	public HTMLBuffer caseOclCodeElement(OclCodeElement object) {
		s.startFontName("pre");
		String oclString = MarkupToString.toString(object.getElements());		
		try {
			ExpressionInOcl query = createQuery(oclString);
			String text = PrettyPrintExprVisitor.prettyPrint(query);
			s.append(text);
		} catch (ParserException e) {
			throw new InvalidMarkupException(e);
		} finally {
			s.endFontName("pre");
		}
		return s;
	}

	@Override
	public HTMLBuffer caseOclEvalElement(OclEvalElement object) {
		String oclString = MarkupToString.toString(object.getElements());		
		try {
			OCL ocl = getOCL();
			ExpressionInOcl query = createQuery(oclString);
			Value value = ocl.evaluate(context, query);
			if (value instanceof StringValue) {
				s.append(((StringValue)value).stringValue());
			}
			else {
				s.append(String.valueOf(value));
			}
		} catch (ParserException e) {
			throw new InvalidMarkupException(e);
		}
		return s;
	}

	@Override
	public HTMLBuffer caseOclTextElement(OclTextElement object) {
		s.startFontName("tt");
		String oclString = MarkupToString.toString(object.getElements());		
		try {
			ExpressionInOcl query = createQuery(oclString);
			PrettyPrintOptions.Global options = PrettyPrintTypeVisitor.createOptions(null);
			options.setLinelength(Integer.MAX_VALUE);
			String text = PrettyPrintExprVisitor.prettyPrint(query, options);
			s.append(text);
		} catch (ParserException e) {
			throw new InvalidMarkupException(e);
		} finally {
			s.endFontName("tt");
		}
		return s;
	}

	@Override
	public HTMLBuffer caseTextElement(TextElement object) {
		for (String text : object.getText()) {
			int iMax = text.length();
			if (iMax > 0) {
				char c = text.charAt(0);
				if ((c == ' ') || (c == '\t')) {
					s.append(' ');
				}
				else {
					for (int i = 0; i < iMax; ) {
						c  = text.charAt(i++);
						if ((c == '\\') && (i < iMax)) {
							c  = text.charAt(i++);
						}
						s.append(c);
					}
				}
			}
		}
		return s;
	}

	protected ExpressionInOcl createQuery(String oclString) throws ParserException {
		OCL ocl = getOCL();
		OCLHelper helper = ocl.createOCLHelper();
		if (context instanceof EObject) {
			EClass eClass = ((EObject)context).eClass();
			Type pivotType = typeManager.getPivotType(eClass.getName());
			if (pivotType == null) {
				Resource resource = eClass.eResource();
				Ecore2Pivot ecore2Pivot = Ecore2Pivot.getAdapter(resource, typeManager);
				pivotType = ecore2Pivot.getCreated(Type.class, eClass);
			}
			helper.setContext(pivotType);
		}
		return helper.createQuery(oclString);
	}

	@Override
	public HTMLBuffer defaultCase(EObject object) {
		s.append("<Unsupported ");
		s.append(object.eClass().getName());
		s.append(">");
		return s;
	}

	protected OCL getOCL() {
		if (ocl == null) {
			Registry packageRegistry = null; //resourceSet.getPackageRegistry();
			PivotEnvironmentFactory envFactory = new PivotEnvironmentFactory(packageRegistry, typeManager);
			ocl = OCL.newInstance(envFactory);
			if (typeManager == null) {
				typeManager = envFactory.getTypeManager();
			}
		}
		return ocl;
	}

	@Override
	public String toString() {
		return s.toString();
	}
}

