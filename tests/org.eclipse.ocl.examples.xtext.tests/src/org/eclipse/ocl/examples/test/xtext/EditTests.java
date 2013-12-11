/**
 * <copyright>
 *
 * Copyright (c) 2010, 2013 E.D.Willink and others.
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
 * $Id: EditTests.java,v 1.8 2011/05/20 15:27:16 ewillink Exp $
 */
package org.eclipse.ocl.examples.test.xtext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.common.OCLConstants;
import org.eclipse.ocl.common.internal.options.CommonOptions;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.domain.values.util.ValuesUtil;
import org.eclipse.ocl.examples.pivot.OCL;
import org.eclipse.ocl.examples.pivot.PivotConstants;
import org.eclipse.ocl.examples.pivot.SequenceType;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.context.ModelContext;
import org.eclipse.ocl.examples.pivot.delegate.OCLDelegateDomain;
import org.eclipse.ocl.examples.pivot.ecore.Ecore2Pivot;
import org.eclipse.ocl.examples.pivot.library.StandardLibraryContribution;
import org.eclipse.ocl.examples.pivot.manager.CollectionTypeServer;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManagerResourceAdapter;
import org.eclipse.ocl.examples.pivot.messages.OCLMessages;
import org.eclipse.ocl.examples.pivot.resource.ASResource;
import org.eclipse.ocl.examples.pivot.utilities.PivotEnvironmentFactory;
import org.eclipse.ocl.examples.xtext.base.utilities.BaseCSResource;
import org.eclipse.ocl.examples.xtext.base.utilities.CS2PivotResourceAdapter;
import org.eclipse.ocl.examples.xtext.essentialocl.utilities.EssentialOCLCSResource;
import org.eclipse.ocl.examples.xtext.oclinecore.oclinecorecs.OCLinEcoreCSPackage;
import org.eclipse.ocl.examples.xtext.tests.XtextTestCase;
import org.eclipse.xtext.resource.impl.ListBasedDiagnosticConsumer;

/**
 * Tests that load a model and verify that there are no unresolved proxies as a result.
 */
@SuppressWarnings("null")
public class EditTests extends XtextTestCase
{	

	protected OCL ocl = null;
//	protected MetaModelManager metaModelManager = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ocl = OCL.newInstance(new PivotEnvironmentFactory(EPackage.Registry.INSTANCE, new MetaModelManager()));
	}

	@Override
	protected void tearDown() throws Exception {
		StandardLibraryContribution.REGISTRY.remove(MetaModelManager.DEFAULT_OCL_STDLIB_URI);
		ocl.dispose();		
		ocl = null;		
		super.tearDown();
	}

	protected Resource getEcoreFromCS1(OCL ocl1, String testDocument, URI ecoreURI) throws IOException {
		MetaModelManager metaModelManager1 = ocl1.getMetaModelManager();
		InputStream inputStream = new URIConverter.ReadableInputStream(testDocument, "UTF-8");
		URI xtextURI = URI.createURI("test.oclinecore");
		ResourceSet resourceSet = new ResourceSetImpl();
		EssentialOCLCSResource xtextResource = (EssentialOCLCSResource) resourceSet.createResource(xtextURI, null);
		MetaModelManagerResourceAdapter.getAdapter(xtextResource, metaModelManager1);
		xtextResource.load(inputStream, null);
		assertNoResourceErrors("Loading Xtext", xtextResource);
		MetaModelManagerResourceAdapter adapter = MetaModelManagerResourceAdapter.getAdapter(xtextResource, metaModelManager1);
		Resource asResource = cs2pivot(ocl1, xtextResource, null);
		Resource ecoreResource = pivot2ecore(ocl1, asResource, ecoreURI, true);
		adapter.dispose();
		return ecoreResource;
	}

	protected Resource doRename(EssentialOCLCSResource xtextResource, Resource asResource, String oldString, String newString, String... expectedErrors) throws IOException {
		String contextMessage = "Renaming '" + oldString + "' to '" + newString + "'";
//		System.out.println("-----------------" + contextMessage + "----------------");
		replace(xtextResource, oldString, newString); 
		assertResourceErrors(contextMessage, xtextResource, expectedErrors);
		assertNoResourceErrors(contextMessage, asResource);
		boolean validSave = expectedErrors.length == 0;
		if (validSave) {
			assertNoValidationErrors(contextMessage, asResource);
		}
		Resource ecoreResource = pivot2ecore(ocl, asResource, null, true);
		assertNoResourceErrors(contextMessage, ecoreResource);
		return ecoreResource;
	}	

	protected void replace(EssentialOCLCSResource xtextResource, String oldString, String newString) {
		String xtextContent = xtextResource.getContents().get(0).toString();
		int index = xtextContent.indexOf(oldString);
		xtextResource.update(index, oldString.length(), newString);
	}	

	public void testEdit_Paste_operation_394057() throws Exception {
		OCLDelegateDomain.initialize(null);
		OCLDelegateDomain.initialize(null, OCLConstants.OCL_DELEGATE_URI);
		CommonOptions.DEFAULT_DELEGATION_MODE.setDefaultValue(OCLDelegateDomain.OCL_DELEGATE_URI_PIVOT);
		String testDocument = 
			"package tutorial : tuttut = 'http://www.eclipse.org/mdt/ocl/oclinecore/tutorial'\n" +
			"{\n" +
			"	class Library\n" +
			"	{\n" +
			"		property books#library : Book[*] { composes };\n" +
			"		/*$$*/\n" +
			"	}\n" +
			"	class Book\n" +
			"	{\n" +
			"		attribute name : String;\n" +
			"		property library#books : Library[?];\n" +
			"	}\n" +
			"}\n";
		String pasteText = 
			"operation packageLabels(packages : Book[*] { !unique, ordered }) : String\n" +
			"{\n" +
			"	body: packages->sortedBy(name)->iterate(p; acc : String = '' | acc + ' ' + p.name);\n" +
			"}";
		EssentialOCLCSResource xtextResource;
		Resource asResource;
		{
			URI ecoreURI1 = getProjectFileURI("test1.ecore");
			InputStream inputStream = new URIConverter.ReadableInputStream(testDocument, "UTF-8");
			URI outputURI = getProjectFileURI("test.oclinecore");
			xtextResource = (EssentialOCLCSResource) resourceSet.createResource(outputURI, null);
			MetaModelManagerResourceAdapter.getAdapter(xtextResource, ocl.getMetaModelManager());
			xtextResource.load(inputStream, null);
			asResource = cs2pivot(ocl, xtextResource, null);
			@SuppressWarnings("unused") Resource ecoreResource1 = pivot2ecore(ocl, asResource, ecoreURI1, true);
		}
		//
		//	Change "/*$$*/" to "pasteText".
		//
		{
			replace(xtextResource, "/*$$*/", pasteText);
			assertNoResourceErrors("Pasting operation", xtextResource);
			assertNoValidationErrors("Pasting operation", xtextResource);
			assertNoResourceErrors("Pasting operation", asResource);
			assertNoValidationErrors("Pasting operation", asResource);
			URI ecoreURI2 = getProjectFileURI("test2.ecore");
			@SuppressWarnings("unused") Resource ecoreResource2 = pivot2ecore(ocl, asResource, ecoreURI2, false);
		}
		//
		//	Change "pasteText" back to "/*$$*/".
		//
		{
			replace(xtextResource, pasteText, "/*$$*/");
			assertNoResourceErrors("Unpasting operation", xtextResource);
			assertNoValidationErrors("Unpasting operation", xtextResource);
			assertNoResourceErrors("Unpasting operation", asResource);
			assertNoValidationErrors("Unpasting operation", asResource);
			URI ecoreURI3 = getProjectFileURI("test3.ecore");
			@SuppressWarnings("unused") Resource ecoreResource3 = pivot2ecore(ocl, asResource, ecoreURI3, true);
		}
	}	

	public void testEdit_Reclass_ecore_383285() throws Exception {
		String testDocument_class = 
				"package p1 : p2 = 'p3' {\n" +
				"    class C : 'java.lang.Object';\n" +
				"}\n";
		String testDocument_datatype = 
				"package p1 : p2 = 'p3' {\n" +
				"    datatype C : 'java.lang.Object';\n" +
				"}\n";
		URI ecoreURI_class = getProjectFileURI("test-class.ecore");
		URI ecoreURI_datatype = getProjectFileURI("test-datatype.ecore");
		OCL ocl_class = OCL.newInstance(new PivotEnvironmentFactory());
		OCL ocl_datatype = OCL.newInstance(new PivotEnvironmentFactory());
		MetaModelManager metaModelManager_class = ocl_class.getMetaModelManager();
		MetaModelManager metaModelManager_datatype = ocl_datatype.getMetaModelManager();
		Resource ecoreResource_class = getEcoreFromCS1(ocl_class, testDocument_class, ecoreURI_class);
		Resource ecoreResource_datatype = getEcoreFromCS1(ocl_datatype, testDocument_datatype, ecoreURI_datatype);
		EssentialOCLCSResource xtextResource;
		Resource asResource;
		{
			URI ecoreURI1 = getProjectFileURI("test1.ecore");
			InputStream inputStream = new URIConverter.ReadableInputStream(testDocument_class, "UTF-8");
			URI outputURI = getProjectFileURI("test.oclinecore");
			xtextResource = (EssentialOCLCSResource) resourceSet.createResource(outputURI, null);
			MetaModelManagerResourceAdapter.getAdapter(xtextResource, ocl.getMetaModelManager());
			xtextResource.load(inputStream, null);
			asResource = cs2pivot(ocl, xtextResource, null);
			Resource ecoreResource1 = pivot2ecore(ocl, asResource, ecoreURI1, true);
			assertSameModel(ecoreResource_class, ecoreResource1);
		}
		//
		//	Change "class" to "datatype" and see EClass change to EDataType.
		//
		{
			replace(xtextResource, "class", "datatype");
			assertNoResourceErrors("Reclassing to datatype", xtextResource);
			URI ecoreURI2 = getProjectFileURI("test2.ecore");
			Resource ecoreResource2 = pivot2ecore(ocl, asResource, ecoreURI2, false);
			assertSameModel(ecoreResource_datatype, ecoreResource2);
		}
		//
		//	Change "datatype" back to "class" and see EDataType change back to EClass.
		//
		{
			replace(xtextResource, "datatype", "class");
			assertNoResourceErrors("Reclassing to class", xtextResource);
			URI ecoreURI3 = getProjectFileURI("test3.ecore");
			Resource ecoreResource3 = pivot2ecore(ocl, asResource, ecoreURI3, true);
			assertSameModel(ecoreResource_class, ecoreResource3);
		}
		metaModelManager_class.dispose();
		metaModelManager_datatype.dispose();
	}	

	public void testEdit_Comments() throws Exception {
		String testDocument_uncommented = 
				"package p1 : p2 = 'p3' {\n" +
				"    class C : 'java.lang.Object';\n" +
				"}\n";
		String testDocument_commented = 
				"package p1 : p2 = 'p3' {\n" +
				"    /* a comment */\n" +
				"    class C : 'java.lang.Object';\n" +
				"}\n";
		String testDocument_recommented = 
				"package p1 : p2 = 'p3' {\n" +
				"    /*\n" +
				"	  *	yet \n" +
				"	  *	another \n" +
				"	  *	comment\n" +
				"	  */\n" +
				"    class C : 'java.lang.Object';\n" +
				"}\n";
		URI ecoreURI_uncommented = getProjectFileURI("test-uncommented.ecore");
		URI ecoreURI_commented = getProjectFileURI("test-commented.ecore");
		URI ecoreURI_recommented = getProjectFileURI("test-recommented.ecore");
		OCL ocl_uncommented = OCL.newInstance(new PivotEnvironmentFactory());
		OCL ocl_commented = OCL.newInstance(new PivotEnvironmentFactory());
		OCL ocl_recommented = OCL.newInstance(new PivotEnvironmentFactory());
		MetaModelManager metaModelManager_uncommented = ocl_uncommented.getMetaModelManager();
		MetaModelManager metaModelManager_commented = ocl_commented.getMetaModelManager();
		MetaModelManager metaModelManager_recommented = ocl_recommented.getMetaModelManager();
		Resource ecoreResource_uncommented = getEcoreFromCS1(ocl_uncommented, testDocument_uncommented, ecoreURI_uncommented);
		Resource ecoreResource_commented = getEcoreFromCS1(ocl_commented, testDocument_commented, ecoreURI_commented);
		Resource ecoreResource_recommented = getEcoreFromCS1(ocl_recommented, testDocument_recommented, ecoreURI_recommented);
		assertHasComments(ecoreResource_uncommented, new String[]{});
		assertHasComments(ecoreResource_commented, new String[]{"a comment"});
		assertHasComments(ecoreResource_recommented, new String[]{"yet\nanother\ncomment"});
		EssentialOCLCSResource xtextResource;
		Resource asResource;
		{
			URI ecoreURI1 = getProjectFileURI("test1.ecore");
			InputStream inputStream = new URIConverter.ReadableInputStream(testDocument_uncommented, "UTF-8");
			URI outputURI = getProjectFileURI("test.oclinecore");
			xtextResource = (EssentialOCLCSResource) resourceSet.createResource(outputURI, null);
			MetaModelManagerResourceAdapter.getAdapter(xtextResource, ocl.getMetaModelManager());
			xtextResource.load(inputStream, null);
			asResource = cs2pivot(ocl, xtextResource, null);
			Resource ecoreResource1 = pivot2ecore(ocl, asResource, ecoreURI1, true);
			assertSameModel(ecoreResource_uncommented, ecoreResource1);
		}
		//
		//	Change "class" to "/* a comment */class".
		//
		{
			replace(xtextResource, "class", "/* a comment */class");
			assertNoResourceErrors("Adding comment", xtextResource);
			URI ecoreURI2 = getProjectFileURI("test2.ecore");
			Resource ecoreResource2 = pivot2ecore(ocl, asResource, ecoreURI2, false);
			assertSameModel(ecoreResource_commented, ecoreResource2);
		}
		//
		//	Change "/* a comment */" to "/* yet\n* another\n * comment */".
		//
		{
			replace(xtextResource, "/* a comment */", "/* yet\n* another\n * comment */");
			assertNoResourceErrors("Changing comment", xtextResource);
			URI ecoreURI3 = getProjectFileURI("test3.ecore");
			Resource ecoreResource3 = pivot2ecore(ocl, asResource, ecoreURI3, true);
			assertSameModel(ecoreResource_recommented, ecoreResource3);
		}
		//
		//	Change "/* yet\n* another\n * comment */" back to nothing.
		//
		{
			replace(xtextResource, "/* yet\n* another\n * comment */", "");
			assertNoResourceErrors("Removing comment", xtextResource);
			URI ecoreURI4 = getProjectFileURI("test4.ecore");
			Resource ecoreResource4 = pivot2ecore(ocl, asResource, ecoreURI4, true);
			assertSameModel(ecoreResource_uncommented, ecoreResource4);
		}
		metaModelManager_uncommented.dispose();
		metaModelManager_commented.dispose();
		metaModelManager_recommented.dispose();
	}	

	private void assertHasComments(@NonNull Resource aResource, @NonNull String[] comments) {
		Map<String, Integer> expected = new HashMap<String, Integer>();
		for (String comment : comments) {
			Integer count = expected.get(comment);
			count = (count != null) ? (count + 1) : 1;
			expected.put(comment, count);
		}
		for (TreeIterator<EObject> tit = aResource.getAllContents(); tit.hasNext(); ) {
			EObject eObject = tit.next();
			if (eObject instanceof EModelElement) {
				EAnnotation eAnnotation = ((EModelElement)eObject).getEAnnotation(PivotConstants.DOCUMENTATION_ANNOTATION_SOURCE);
				if (eAnnotation != null) {
					String comment = eAnnotation.getDetails().get(PivotConstants.DOCUMENTATION_ANNOTATION_KEY);
					Integer count = expected.get(comment);
					assertTrue("Expected comment '" + comment + "' exists", (count != null) && (count > 0));
					expected.put(comment, count-1);
				}
			}
		}
		for (String comment : comments) {
			assertEquals("Expected comment '" + comment + "' extra occurences", 0, expected.get(comment).intValue());
		}
	}

	public void testEdit_Refresh_ecore_382230() throws Exception {
		OCLDelegateDomain.initialize(null);
		OCLDelegateDomain.initialize(null, OCLConstants.OCL_DELEGATE_URI);
		CommonOptions.DEFAULT_DELEGATION_MODE.setDefaultValue(OCLDelegateDomain.OCL_DELEGATE_URI_PIVOT);
		OCL ocl0 = OCL.newInstance(new PivotEnvironmentFactory());
		MetaModelManager metaModelManager0 = ocl0.getMetaModelManager();
		String testDocument = 
			"package tutorial : tuttut = 'http://www.eclipse.org/mdt/ocl/oclinecore/tutorial'\n" +
			"{\n" +
			"	class Library\n" +
			"	{\n" +
			"		property books#library : Book[*] { composes };\n" +
			"	}\n" +
			"	class Book\n" +
			"	{\n" +
			"		property library#books : Library[?];\n" +
			"		property name : String;\n" +
			"		invariant NameNotEmpty: name->notEmpty();\n" +
			"	}\n" +
			"}\n";
		URI ecoreURI = createEcoreFile(metaModelManager0, "RefreshTest.ecore", testDocument, true);
		metaModelManager0.dispose();
		//
		//	Load and instrument test document
		//
		OCL ocl1 = OCL.newInstance(new PivotEnvironmentFactory());
		MetaModelManager metaModelManager1 = ocl1.getMetaModelManager();
		Resource ecoreResource = metaModelManager1.getExternalResourceSet().getResource(ecoreURI, true);
		assertNoResourceErrors("Ecore load", ecoreResource);
		assertNoValidationErrors("Ecore load", ecoreResource);
		ASResource asResource = ocl1.ecore2pivot(ecoreResource);
		assertNoResourceErrors("Pivot load", asResource);
		assertNoValidationErrors("Pivot load", asResource);
		Set<EObject> loadPivotContent = new HashSet<EObject>();
		for (TreeIterator<EObject> tit = asResource.getAllContents(); tit.hasNext(); ) {
			EObject eObject = tit.next();
//			System.out.println(DomainUtil.debugSimpleName(eObject));
			loadPivotContent.add(eObject);
		}
		{
			ResourceSet resourceSet = new ResourceSetImpl();
			BaseCSResource xtextResource1 = (BaseCSResource) resourceSet.createResource(ecoreURI.appendFileExtension("oclinecore"), OCLinEcoreCSPackage.eCONTENT_TYPE);
			xtextResource1.setURI(ecoreURI);
			ocl1.pivot2cs(asResource, xtextResource1);
			assertNoResourceErrors("Xtext load", xtextResource1);
			assertNoValidationErrors("Xtext load", xtextResource1);
			CS2PivotResourceAdapter cs2pivotAdapter1 = xtextResource1.getCS2ASAdapter(null);
			ListBasedDiagnosticConsumer diagnosticsConsumer1 = new ListBasedDiagnosticConsumer();
			cs2pivotAdapter1.refreshPivotMappings(diagnosticsConsumer1);
			Set<EObject> parsePivotContent = new HashSet<EObject>();
			for (TreeIterator<EObject> tit = asResource.getAllContents(); tit.hasNext(); ) {
				EObject eObject = tit.next();
//				System.out.println(DomainUtil.debugSimpleName(eObject));
				parsePivotContent.add(eObject);
			}
			assertEquals(loadPivotContent.size(), parsePivotContent.size());
			assertEquals(loadPivotContent, parsePivotContent);
		}
		
		//
		//	Reload and re-instrument test document
		//
		StringWriter writer = new StringWriter();
		OutputStream outputStream = new URIConverter.WriteableOutputStream(writer, "UTF-8");
		ecoreResource.save(outputStream, null);
		ecoreResource.unload();
		InputStream inputStream = new URIConverter.ReadableInputStream(writer.toString().replace("tuttut",  "tut"), "UTF-8");
		ecoreResource.load(inputStream, null);
		assertNoResourceErrors("Ecore reload", ecoreResource);
		assertNoValidationErrors("Ecore reload", ecoreResource);
		Ecore2Pivot ecore2Pivot = Ecore2Pivot.getAdapter(ecoreResource, metaModelManager1);
		ecore2Pivot.update(asResource, ecoreResource.getContents());
		assertNoResourceErrors("Pivot reload", ecoreResource);
		assertNoValidationErrors("Pivot reload", ecoreResource);
		Set<EObject> newPivotContent = new HashSet<EObject>();
		for (TreeIterator<EObject> tit = asResource.getAllContents(); tit.hasNext(); ) {
			EObject eObject = tit.next();
//			System.out.println(PivotUtil.debugSimpleName(eObject));
			newPivotContent.add(eObject);
		}
		assertEquals(loadPivotContent.size(), newPivotContent.size());
		assertEquals(loadPivotContent, newPivotContent);
		{
			ResourceSet resourceSet = new ResourceSetImpl();
			BaseCSResource xtextResource2 = (BaseCSResource) resourceSet.createResource(ecoreURI.appendFileExtension("oclinecore"), OCLinEcoreCSPackage.eCONTENT_TYPE);
			xtextResource2.setURI(ecoreURI);
			ocl1.pivot2cs(asResource, xtextResource2);
			assertNoResourceErrors("Xtext load", xtextResource2);
			assertNoValidationErrors("Xtext load", xtextResource2);
			CS2PivotResourceAdapter cs2pivotAdapter2 = xtextResource2.getCS2ASAdapter(null);
			ListBasedDiagnosticConsumer diagnosticsConsumer2 = new ListBasedDiagnosticConsumer();
			cs2pivotAdapter2.refreshPivotMappings(diagnosticsConsumer2);
			Set<EObject> reparsePivotContent = new HashSet<EObject>();
			for (TreeIterator<EObject> tit = asResource.getAllContents(); tit.hasNext(); ) {
				EObject eObject = tit.next();
//				System.out.println(PivotUtil.debugSimpleName(eObject));
				reparsePivotContent.add(eObject);
			}
			assertEquals(loadPivotContent.size(), reparsePivotContent.size());
			assertEquals(loadPivotContent, reparsePivotContent);
		}
		metaModelManager1.dispose();
	}	

	public void testEdit_Rename_ecore() throws Exception {
		String testDocument = 
			"module m1 \n" +
			"package p1 : p2 = 'p3' {\n" +
			"}\n";
		URI ecoreURI0 = getProjectFileURI("test0.ecore");
		OCL ocl1 = OCL.newInstance(new PivotEnvironmentFactory());
		MetaModelManager metaModelManager1 = ocl1.getMetaModelManager();
		Resource ecoreResource0 = getEcoreFromCS1(ocl1, testDocument, ecoreURI0);
		EssentialOCLCSResource xtextResource;
		Resource asResource;
		{
			URI ecoreURI1 = getProjectFileURI("test1.ecore");
			InputStream inputStream = new URIConverter.ReadableInputStream(testDocument, "UTF-8");
			URI outputURI = getProjectFileURI("test.oclinecore");
			xtextResource = (EssentialOCLCSResource) resourceSet.createResource(outputURI, null);
			MetaModelManagerResourceAdapter.getAdapter(xtextResource, ocl.getMetaModelManager());
			xtextResource.load(inputStream, null);
			asResource = cs2pivot(ocl, xtextResource, null);
			Resource ecoreResource1 = pivot2ecore(ocl, asResource, ecoreURI1, true);
			assertSameModel(ecoreResource0, ecoreResource1);
		}
		//
		//	Inserting a leading space has no Ecore effect.
		//
		{
			xtextResource.update(0, 0, " ");
			assertNoResourceErrors("Adding space", xtextResource);
			URI ecoreURI2 = getProjectFileURI("test2.ecore");
			Resource ecoreResource2 = pivot2ecore(ocl, asResource, ecoreURI2, true);
			assertSameModel(ecoreResource0, ecoreResource2);
		}
		//
		//	Deleting the leading space has no Ecore effect.
		//
		{
			xtextResource.update(0, 1, "");
			assertNoResourceErrors("Deleting space", xtextResource);
			URI ecoreURI3 = getProjectFileURI("test3.ecore");
			Resource ecoreResource3 = pivot2ecore(ocl, asResource, ecoreURI3, true);
			assertSameModel(ecoreResource0, ecoreResource3);
		}
		//
		//	Changing "p1" to "pkg" renames the package.
		//
		{
			replace(xtextResource, "p1", "pkg"); 
			assertNoResourceErrors("Renaming", xtextResource);
			URI ecoreURI4 = getProjectFileURI("test4.ecore");
			Resource ecoreResource4 = pivot2ecore(ocl, asResource, ecoreURI4, true);
			((EPackage)ecoreResource0.getContents().get(0)).setName("pkg");
			assertSameModel(ecoreResource0, ecoreResource4);		
		}
		metaModelManager1.dispose();
	}	

	public void testEdit_Rename_Restore_ecore() throws Exception {
		String testDocument = 
			"package TestPackage : tp = 'TestPackage'\n" +
			"{\n" +
			"	class TestClass1 {\n" +
			"		property testProperty1 : Integer;\n" +
			"		operation testOperation(i : Integer) : Integer;\n" +
			"		invariant testInvariant: 1 = 0;\n" +
			"	}\n" +
			"	class TestClass2 {\n" +
			"		property testProperty2 : TestClass1;\n" +
			"		property testProperty3 : Integer[*];\n" +
			"		invariant testInvariant: testProperty2.testProperty1 = testProperty2.testOperation(123456);\n" +
			"	}\n" +
			"}\n";
		URI ecoreURI0 = getProjectFileURI("test0.ecore");
		OCL ocl1 = OCL.newInstance(new PivotEnvironmentFactory());
		MetaModelManager metaModelManager1 = ocl1.getMetaModelManager();
		Resource ecoreResource0 = getEcoreFromCS1(ocl1, testDocument, ecoreURI0);
		URI ecoreURI1 = getProjectFileURI("test1.ecore");
		InputStream inputStream = new URIConverter.ReadableInputStream(testDocument, "UTF-8");
		URI outputURI = getProjectFileURI("test.oclinecore");
		EssentialOCLCSResource xtextResource = (EssentialOCLCSResource) resourceSet.createResource(outputURI, null);
		MetaModelManagerResourceAdapter adapter = MetaModelManagerResourceAdapter.getAdapter(xtextResource, ocl.getMetaModelManager());
		xtextResource.load(inputStream, null);
		Resource asResource = cs2pivot(ocl, xtextResource, null);
		{
			Resource ecoreResource1 = pivot2ecore(ocl, asResource, ecoreURI1, true);
			assertSameModel(ecoreResource0, ecoreResource1);
		}
		Type pivotTestClass1 = ocl.getMetaModelManager().getPrimaryType("TestPackage", "TestClass1");
		//
		//	Changing "TestClass1" to "Testing" renames a type and breaks the invariant.
		//
		doRename(xtextResource, asResource, "TestClass1", "Testing",
//			DomainUtil.bind(OCLMessages.Unresolved_ERROR_, "Type", pivotTestClass1.getName()),
			DomainUtil.bind(OCLMessages.UnresolvedType_ERROR_, pivotTestClass1.getName()),
			DomainUtil.bind(OCLMessages.UnresolvedOperationCall_ERROR_, "testOperation", "OclInvalid", "UnlimitedNatural"),
			DomainUtil.bind(OCLMessages.UnresolvedProperty_ERROR_, "testProperty1", "OclInvalid"));
		//
		//	Changing "Testing" back to "TestClass1" restores the type and the invariant.
		//
		assertSameModel(ecoreResource0, doRename(xtextResource, asResource, "Testing", "TestClass1"));
		pivotTestClass1 = ocl.getMetaModelManager().getPrimaryType("TestPackage", "TestClass1");
		//
		//	Changing "testProperty1" to "tProperty" renames the property and breaks the invariant.
		//
		doRename(xtextResource, asResource, "testProperty1", "tProperty",
			DomainUtil.bind(OCLMessages.UnresolvedProperty_ERROR_, "testProperty1", pivotTestClass1 + ""));
		//
		//	Changing "tProperty" back to "testProperty" restores the property and the invariant.
		//
		assertSameModel(ecoreResource0, doRename(xtextResource, asResource, "tProperty", "testProperty1"));
		//
		//	Changing "testOperation" to "tOperation" renames the operation and breaks the invariant.
		//
		doRename(xtextResource, asResource, "testOperation", "tOperation",
			DomainUtil.bind(OCLMessages.UnresolvedOperationCall_ERROR_, "testOperation", pivotTestClass1 + "", "UnlimitedNatural"));
		//
		//	Changing "tOperation" back to "testOperation" restores the operation and the invariant.
		//
		assertSameModel(ecoreResource0, doRename(xtextResource, asResource, "tOperation", "testOperation"));
		//
		//	Changing "testOperation(i : Integer)" to "testOperation()" mismatches the operation signature and breaks the invariant.
		//
		doRename(xtextResource, asResource, "testOperation(i : Integer)", "testOperation()",
			DomainUtil.bind(OCLMessages.UnresolvedOperationCall_ERROR_, "testOperation", pivotTestClass1 + "", "UnlimitedNatural"));
		//
		//	Changing "testOperation()" back to "testOperation(i : Integer)" restores the operation and the invariant.
		//
		assertSameModel(ecoreResource0, doRename(xtextResource, asResource, "testOperation()", "testOperation(i : Integer)"));
		//
		//	Changing "testOperation(i : Integer)" to "testOperation(s : String)" mismatches the operation signature and breaks the invariant.
		//
		doRename(xtextResource, asResource, "testOperation(i : Integer)", "testOperation(s : String)",
			DomainUtil.bind(OCLMessages.UnresolvedOperationCall_ERROR_, "testOperation", pivotTestClass1 + "", "UnlimitedNatural"));
		//
		//	Changing "testOperation()" back to "testOperation(i : Integer)" restores the operation and the invariant.
		//
		assertSameModel(ecoreResource0, doRename(xtextResource, asResource, "testOperation(s : String)", "testOperation(i : Integer)"));
		//
		adapter.dispose();
		metaModelManager1.dispose();
	}

	public void testEdit_StaleReference_ecore() throws Exception {
		String testDocument = 
			"package TestPackage : tp = 'TestPackage'\n" +
			"{\n" +
			"	class TestClass1 {\n" +
			"		property testProperty1 : Integer;\n" +
			"		operation testOperation() : Integer;\n" +
			"		invariant testInvariant: 1 = 0;\n" +
			"	}\n" +
			"	class TestClass2 {\n" +
			"		property testProperty2 : TestClass1[*];\n" +
			"		invariant testInvariant: testProperty2->select(testOperation() = testProperty1)->isEmpty();\n" +
			"	}\n" +
			"}\n";
		URI ecoreURI0 = getProjectFileURI("test0.ecore");
//		System.out.println("*************load-reference*********************************************************");
		OCL ocl1 = OCL.newInstance(new PivotEnvironmentFactory());
		MetaModelManager metaModelManager1 = ocl1.getMetaModelManager();
		Resource ecoreResource0 = getEcoreFromCS1(ocl1, testDocument, ecoreURI0);
		URI ecoreURI1 = getProjectFileURI("test1.ecore");
		InputStream inputStream = new URIConverter.ReadableInputStream(testDocument, "UTF-8");
		URI outputURI = getProjectFileURI("test.oclinecore");
		EssentialOCLCSResource xtextResource = (EssentialOCLCSResource) resourceSet.createResource(outputURI, null);
		MetaModelManagerResourceAdapter adapter = MetaModelManagerResourceAdapter.getAdapter(xtextResource, ocl.getMetaModelManager());
//		System.out.println("*************load*********************************************************");
		xtextResource.load(inputStream, null);
		Resource asResource = cs2pivot(ocl, xtextResource, null);
		{
			Resource ecoreResource1 = pivot2ecore(ocl, asResource, ecoreURI1, true);
			assertSameModel(ecoreResource0, ecoreResource1);
		}
		Type pivotTestClass1 = ocl.getMetaModelManager().getPrimaryType("TestPackage", "TestClass1");
		//
		//	Changing "TestClass1" to "Testing" renames a type and breaks the referredProperty/referredOperation.
		//
		doRename(xtextResource, asResource, "TestClass1", "Testing",
			DomainUtil.bind(OCLMessages.UnresolvedType_ERROR_, pivotTestClass1.getName()),
			DomainUtil.bind(OCLMessages.UnresolvedOperationCall_ERROR_, "testOperation", "Set(OclInvalid)", ""),
			DomainUtil.bind(OCLMessages.UnresolvedProperty_ERROR_, "testProperty1", "OclInvalid"));
		//
		//	Changing "Testing" back to "TestClass1" restores the type and the referredProperty/referredOperation.
		//
		assertSameModel(ecoreResource0, doRename(xtextResource, asResource, "Testing", "TestClass1"));
		pivotTestClass1 = ocl.getMetaModelManager().getPrimaryType("TestPackage", "TestClass1");
		//
		//	Changing "TestClass1" to "Testing" renames a type and breaks the referredProperty/referredOperation.
		//
		doRename(xtextResource, asResource, "TestClass1", "Testing",
			DomainUtil.bind(OCLMessages.UnresolvedType_ERROR_, pivotTestClass1.getName()),
			DomainUtil.bind(OCLMessages.UnresolvedOperationCall_ERROR_, "testOperation", "Set(OclInvalid)", ""),
			DomainUtil.bind(OCLMessages.UnresolvedProperty_ERROR_, "testProperty1", "OclInvalid"));
		//
		//	Changing "Testing" back to "TestClass1" restores the type and the referredProperty/referredOperation.
		//
		assertSameModel(ecoreResource0, doRename(xtextResource, asResource, "Testing", "TestClass1"));
		pivotTestClass1 = ocl.getMetaModelManager().getPrimaryType("TestPackage", "TestClass1");
		//
		adapter.dispose();
		metaModelManager1.dispose();
	}

	public void testEdit_StaleSpecialization() throws Exception {
		String testDocument = 
			"import 'http://www.eclipse.org/ocl/3.1.0/OCL.oclstdlib';\n" + 
			"library ocl : ocl = 'http://www.eclipse.org/ocl/3.1.0/OCL.oclstdlib' {\n" +
			"type MyType conformsTo OclAny{\n" +
			"operation testFunction() : Boolean;\n" +
			"}\n" +
			"}\n";
		URI outputURI = getProjectFileURI("test.oclstdlib");
		MetaModelManager metaModelManager = ocl.getMetaModelManager();
		ModelContext modelContext = new ModelContext(metaModelManager, outputURI);
		EssentialOCLCSResource xtextResource = (EssentialOCLCSResource) modelContext.createBaseResource(testDocument);
		Resource asResource = cs2pivot(ocl, xtextResource, null);
		assertResourceErrors("Loading input", xtextResource);
		assertNoResourceErrors("Loading input", asResource);
		//
		Type myType = metaModelManager.getPrimaryType("http://www.eclipse.org/ocl/3.1.0/OCL.oclstdlib", "MyType");
		SequenceType sequenceType = metaModelManager.getSequenceType();
		CollectionTypeServer.TemplateArguments templateArguments = new CollectionTypeServer.TemplateArguments(myType, ValuesUtil.ZERO_VALUE, ValuesUtil.UNLIMITED_VALUE);
		CollectionTypeServer sequenceTypeServer = (CollectionTypeServer) metaModelManager.getTypeServer(sequenceType);
		WeakReference<Type> sequenceMyType = new WeakReference<Type>(sequenceTypeServer.findSpecializedType(templateArguments));
		assertNull(sequenceMyType.get()); 
		//
		doRename(xtextResource, asResource, "Boolean", "Sequence(MyType)");
		sequenceMyType = new WeakReference<Type>(sequenceTypeServer.findSpecializedType(templateArguments));
		assertNotNull(sequenceMyType.get()); 
		//		
		doRename(xtextResource, asResource, "Sequence(MyType)", "Set(MyType)");
		System.gc();
		sequenceMyType = new WeakReference<Type>(sequenceTypeServer.findSpecializedType(templateArguments));
		assertNull(sequenceMyType.get()); 
	}

	public void testEdit_Paste_CompleteOCL() throws Exception {
		OCLDelegateDomain.initialize(null);
		OCLDelegateDomain.initialize(null, OCLConstants.OCL_DELEGATE_URI);
		CommonOptions.DEFAULT_DELEGATION_MODE.setDefaultValue(OCLDelegateDomain.OCL_DELEGATE_URI_PIVOT);
		String testDocument = 
			"package ocl\n" +
			"context IterateExp\n" +
			"inv True: true\n" +
			"/*$$*/\n" +
			"context IteratorExp\n" +
			"def: IsTrue() : Boolean = true\n" +
			"inv True: IsTrue()\n" +
			"endpackage\n";
		String pasteText = 
			"context IterateExp\n" +
			"inv False: true\n";
		EssentialOCLCSResource xtextResource;
		Resource asResource;
		{
//			URI ecoreURI1 = getProjectFileURI("test1.ecore");
			InputStream inputStream = new URIConverter.ReadableInputStream(testDocument, "UTF-8");
			URI outputURI = getProjectFileURI("test.ocl");
			xtextResource = (EssentialOCLCSResource) resourceSet.createResource(outputURI, null);
			MetaModelManagerResourceAdapter.getAdapter(xtextResource, ocl.getMetaModelManager());
			xtextResource.load(inputStream, null);
			asResource = cs2pivot(ocl, xtextResource, null);
			assertNoResourceErrors("Loading", xtextResource);
			assertNoValidationErrors("Loading", xtextResource);
			assertNoResourceErrors("Loading", asResource);
			assertNoValidationErrors("Loading", asResource);
//			@SuppressWarnings("unused") Resource ecoreResource1 = pivot2ecore(ocl, asResource, ecoreURI1, true);
		}
		//
		//	Change "/*$$*/" to "pasteText".
		//
		{
			replace(xtextResource, "/*$$*/", pasteText);
			assertNoResourceErrors("Pasting operation", xtextResource);
			assertNoValidationErrors("Pasting operation", xtextResource);
			assertNoResourceErrors("Pasting operation", asResource);
			assertNoValidationErrors("Pasting operation", asResource);
//			URI ecoreURI2 = getProjectFileURI("test2.ecore");
//			@SuppressWarnings("unused") Resource ecoreResource2 = pivot2ecore(ocl, asResource, ecoreURI2, false);
		}
		//
		//	Change "pasteText" back to "/*$$*/".
		//
		{
//			replace(xtextResource, pasteText, "/*$$*/");
//			assertNoResourceErrors("Unpasting operation", xtextResource);
//			assertNoValidationErrors("Unpasting operation", xtextResource);
//			assertNoResourceErrors("Unpasting operation", asResource);
//			assertNoValidationErrors("Unpasting operation", asResource);
//			URI ecoreURI3 = getProjectFileURI("test3.ecore");
//			@SuppressWarnings("unused") Resource ecoreResource3 = pivot2ecore(ocl, asResource, ecoreURI3, true);
		}
	}	
}
