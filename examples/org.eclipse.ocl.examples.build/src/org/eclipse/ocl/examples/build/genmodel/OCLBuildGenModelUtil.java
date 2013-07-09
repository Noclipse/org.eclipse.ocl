/**
 * <copyright>
 *
 * Copyright (c) 2013 E.D.Willink and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.ocl.examples.build.genmodel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.emf.codegen.ecore.genmodel.GenAnnotation;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.util.GenModelUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.codegen.java.ImportUtils;


public class OCLBuildGenModelUtil
{
	public static @NonNull String atNonNull(@NonNull GenModel genModel) {
		GenAnnotation genAnnotation = genModel.getGenAnnotation("http://www.eclipse.org/OCL/GenModel");
		if (genAnnotation != null) {
			String value = genAnnotation.getDetails().get("Use Null Annotations");
			if (value != null) {
			    boolean useAtNonNull = Boolean.valueOf(value);
			    if (useAtNonNull) {
			    	return "@" + genModel.getImportedName(NonNull.class.getName()) + " ";
			    }
			}
		}
		return "";
	}
	
	public static @NonNull String atNullable(@NonNull GenModel genModel) {
		GenAnnotation genAnnotation = genModel.getGenAnnotation("http://www.eclipse.org/OCL/GenModel");
		if (genAnnotation != null) {
			String value = genAnnotation.getDetails().get("Use Null Annotations");
			if (value != null) {
			    boolean useAtNonNull = Boolean.valueOf(value);
			    if (useAtNonNull) {
			    	return "@" + genModel.getImportedName(Nullable.class.getName()) + " ";
			    }
			}
		}
		return "";
	}

	public static boolean isRootVisitableClass(@NonNull GenClass genClass) {
		String interfaceName = genClass.getQualifiedInterfaceName();
		String visitableClasses = GenModelUtil.getAnnotation(genClass.getGenModel(), "http://www.eclipse.org/OCL/GenModel/Visitor", "Visitable Classes");
		return (visitableClasses != null) && visitableClasses.contains(interfaceName);
	}

	public static Object copyAndPaste(@NonNull GenClass genClass) {
		String interfaceName = genClass.getQualifiedInterfaceName();
		GenModel genModel = genClass.getGenModel();
		String javaCopyFile = GenModelUtil.getAnnotation(genModel, "http://www.eclipse.org/OCL/GenModel/CopyAndPaste", interfaceName);
		if (javaCopyFile == null) {
			return "";
		}
		URI relativeURI = URI.createURI(javaCopyFile, true);
		URI baseURI = URI.createPlatformResourceURI("/" + genModel.getModelPluginID() + "/", true);
		URI uri = relativeURI.resolve(baseURI);
		StringBuilder s = new StringBuilder();
		s.append("	/**\n");
		s.append("	 * Start of copy from " + uri + " \n");
		s.append("	 */\n");
		s.append("	@SuppressWarnings(\"unused\") private static int _START_OF_COPY_ = 0;\n");
		char[] buf = new char[4096];
		try {
			InputStream iStream = URIConverter.INSTANCE.createInputStream(uri);
			Reader reader = new InputStreamReader(iStream);
			int len = 0;
			while ((len = reader.read(buf)) > 0) {
				s.append(buf, 0, len);
			}
			s.append("	/**\n");
			s.append("	 * End of copy from " + uri + " \n");
			s.append("	 */\n");
			s.append("	@SuppressWarnings(\"unused\") private static int _END_OF_COPY_ = 0;\n");
			iStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resolveImports(genModel, s.toString());
	}

	public static @NonNull String resolveImports(GenModel genModel, String source)
	{
		int iMax = source.length();
		int iStart = 0;
		StringBuilder s = new StringBuilder();
		while (true) {
			int iPrefix = source.indexOf(ImportUtils.IMPORTS_PREFIX, iStart);
			if (iPrefix < 0) {
				break;
			}
			int iSuffix = source.indexOf(ImportUtils.IMPORTS_SUFFIX, iPrefix);
			if (iSuffix < 0) {
				break;
			}
			s.append(source, iStart, iPrefix);
			String longName = source.substring(iPrefix+ImportUtils.IMPORTS_PREFIX.length(), iSuffix);
			s.append(genModel.getImportedName(longName));
			iStart = iSuffix + ImportUtils.IMPORTS_SUFFIX.length();
		}
		s.append(source, iStart, iMax);
		@SuppressWarnings("null")@NonNull String string = s.toString();
		return string;
	}
}
