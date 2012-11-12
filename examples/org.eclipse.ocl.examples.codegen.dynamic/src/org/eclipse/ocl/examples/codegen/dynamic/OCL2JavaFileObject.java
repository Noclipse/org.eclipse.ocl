/**
 * <copyright>
 *
 * Copyright (c) 2012 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.ocl.examples.codegen.dynamic;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.eclipse.ocl.examples.domain.library.LibraryOperation;

public class OCL2JavaFileObject extends SimpleJavaFileObject
{
	private static JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	private static StandardJavaFileManager stdFileManager = compiler
			.getStandardFileManager(null, Locale.getDefault(), null);
	private static List<String> compilationOptions = Arrays.asList("-d", "bin", "-target", "1.5", "-g:{source,lines,vars}");
	
	public static LibraryOperation loadClass(String qualifiedName, String javaCodeSource) throws Exception {
		List<? extends JavaFileObject> compilationUnits = Collections.singletonList(
				new OCL2JavaFileObject(qualifiedName, javaCodeSource));
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		CompilationTask compilerTask = compiler.getTask(null, stdFileManager,
				diagnostics, compilationOptions, null, compilationUnits);
		if (!compilerTask.call()) {
			StringBuilder s = new StringBuilder();
			s.append("Failed to compile " + qualifiedName);
			for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
				s.append("\n" + diagnostic);
			}
			throw new IOException(s.toString());
		}
		stdFileManager.close();		// Close the file manager which re-opens automatically
		Class<?> testClass = Class.forName(qualifiedName);
		Field testField = testClass.getField("INSTANCE");
		return (LibraryOperation) testField.get(null);
	}
	
    private String javaCode ;
 
    /**
     */
    public OCL2JavaFileObject(String qualifiedName, String javaCode) {
        super(java.net.URI.create("string:///" +qualifiedName.replaceAll("\\.", "/") + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
        this.javaCode = javaCode ;
    }
 
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return javaCode ;
    }
}