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

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.pivot.ExpressionInOCL;
import org.eclipse.ocl.examples.pivot.OCL;
import org.eclipse.ocl.examples.pivot.ParserException;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.ecore.Ecore2Pivot;
import org.eclipse.ocl.examples.pivot.helper.OCLHelper;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.prettyprint.PrettyPrintOptions;
import org.eclipse.ocl.examples.pivot.prettyprint.PrettyPrinter;
import org.eclipse.ocl.examples.pivot.utilities.HTMLBuffer;
import org.eclipse.ocl.examples.pivot.utilities.PivotEnvironmentFactory;
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
	
	public static String toString(@NonNull MetaModelManager metaModelManager, @Nullable Object context, @NonNull MarkupElement element) throws Exception {
		MarkupToHTML toString = new MarkupToHTML(metaModelManager, context);
		try {
			return toString.doSwitch(element).toString();
		} catch (InvalidMarkupException e) {
			throw (Exception)e.getCause();
		}
	}
	
	private @Nullable OCL ocl = null;
	private @NonNull MetaModelManager metaModelManager;
	protected final @Nullable Object context;
	protected final @NonNull HTMLBuffer s = new HTMLBuffer();

	public MarkupToHTML(@NonNull MetaModelManager metaModelManager, @Nullable Object context) {
		this.metaModelManager = metaModelManager;
		this.context = context;
	}	

	@Override
	public HTMLBuffer caseBulletElement(BulletElement object) {
		Integer level = Integer.valueOf(object.getLevel());
		s.startBulletLevel(level);
		caseCompoundElement(object);
		s.endBulletLevel(level);
		return s;
	}
	
	@Override
	public HTMLBuffer caseCompoundElement(CompoundElement object) {
		for (MarkupElement element : object.getElements()) {
			doSwitch(element);
		}
		return s;
	}

	@Override
	public HTMLBuffer caseFigureElement(FigureElement object) {
		if (object.getDef() != null) {
			s.appendLabelDef(object.getDef());
		}
		s.appendFigure(object.getSrc(), object.getAlt(), object.getRequiredWidth(), object.getRequiredHeight());
		return s;
	}

	@Override
	public HTMLBuffer caseFigureRefElement(FigureRefElement object) {
		FigureElement ref = object.getRef();
		if (ref.eIsProxy()) {
			String message = "Unresolved proxy '" + ((InternalEObject)ref).eProxyURI() + "'";
			throw new InvalidMarkupException(new IllegalStateException(message));
		}
		s.appendLabelRef(ref.getDef());
		return s;
	}

	@Override
	public HTMLBuffer caseFontElement(FontElement object) {
		String font = object.getFont();
		String htmlFont;
		if ("b".equals(font)) {
			htmlFont = "b";
		}
		else if ("e".equals(font)) {
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
	public HTMLBuffer caseFootnoteElement(FootnoteElement object) {
		s.startFootnote();
		caseCompoundElement(object);
		s.endFootnote();
		return s;
	}

	@Override
	public HTMLBuffer caseHeadingElement(HeadingElement object) {
		String level = object.getLevel();
		if (level == null) {
			level = "1";
		}
		s.startHeadingLevel(level);
		caseCompoundElement(object);
		s.endHeadingLevel(level);
		return s;
	}

	@Override
	public HTMLBuffer caseNewLineElement(NewLineElement object) {
		assert object != null;
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
	public HTMLBuffer caseOCLCodeElement(OCLCodeElement object) {
		s.startFontName("pre");
		List<MarkupElement> elements = object.getElements();
		assert elements != null;
		String oclString = MarkupToString.toString(elements);		
		try {
			ExpressionInOCL query = createQuery(oclString);
			String text = PrettyPrinter.print(query);
			s.append(text);
		} catch (ParserException e) {
			throw new InvalidMarkupException(e);
		} finally {
			s.endFontName("pre");
		}
		return s;
	}

	@Override
	public HTMLBuffer caseOCLEvalElement(OCLEvalElement object) {
		List<MarkupElement> elements = object.getElements();
		assert elements != null;
		String oclString = MarkupToString.toString(elements);		
		try {
			OCL ocl = getOCL();
			ExpressionInOCL query = createQuery(oclString);
			Object value = ocl.evaluate(context, query);
			s.append(String.valueOf(value));
		} catch (ParserException e) {
			throw new InvalidMarkupException(e);
		}
		return s;
	}

	@Override
	public HTMLBuffer caseOCLTextElement(OCLTextElement object) {
		s.startFontName("tt");
		List<MarkupElement> elements = object.getElements();
		assert elements != null;
		String oclString = MarkupToString.toString(elements);		
		try {
			ExpressionInOCL query = createQuery(oclString);
			PrettyPrintOptions.Global options = PrettyPrinter.createOptions(null);
			options.setLinelength(Integer.MAX_VALUE);
			String text = PrettyPrinter.print(query, options);
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

	protected @NonNull ExpressionInOCL createQuery(@NonNull String oclString) throws ParserException {
		OCL ocl = getOCL();
		OCLHelper helper = ocl.createOCLHelper();
		if (context instanceof EObject) {
			EClass eClass = ((EObject)context).eClass();
			String name = eClass.getName();
			assert name != null;
			Type pivotType = metaModelManager.getPivotType(name);
			if (pivotType == null) {
				Resource resource = eClass.eResource();
				if (resource != null) {
					Ecore2Pivot ecore2Pivot = Ecore2Pivot.getAdapter(resource, metaModelManager);
					pivotType = ecore2Pivot.getCreated(Type.class, eClass);
				}
			}
			if (pivotType != null) {
				helper.setContext(pivotType);
			}
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

	protected @NonNull OCL getOCL() {
		OCL ocl2 = ocl;
		if (ocl2 == null) {
			Registry packageRegistry = null; //resourceSet.getPackageRegistry();
			PivotEnvironmentFactory envFactory = new PivotEnvironmentFactory(packageRegistry, metaModelManager);
			ocl2 = ocl = OCL.newInstance(envFactory);
		}
		return ocl2;
	}

	@Override
	public String toString() {
		return s.toString();
	}
}

