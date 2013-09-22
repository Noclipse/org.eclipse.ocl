/**
 * <copyright>
 *
 * Copyright (c) 2013 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Obeo - initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.ocl.examples.xtext.completeocl.ui.wizards;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ocl.examples.domain.values.util.ValuesUtil;
import org.eclipse.ocl.examples.xtext.base.ui.wizards.AbstractFileDialog;
import org.eclipse.ocl.examples.xtext.base.ui.wizards.AbstractFileNewWizard;
import org.eclipse.ocl.examples.xtext.base.ui.wizards.AbstractFileNewWizardPage;
import org.eclipse.ocl.examples.xtext.completeocl.ui.CompleteOCLUiModule;
import org.eclipse.ocl.examples.xtext.completeocl.ui.messages.CompleteOCLUIMessages;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 * Wizard allowing the user to create a new OCL rule file.
 */
public class CompleteOCLFileNewWizard extends AbstractFileNewWizard
{
	@Override
	protected @NonNull CompleteOCLFileDialog createDialog(@NonNull AbstractFileNewWizardPage wizardPage, @Nullable IResource initialSelection) {
		return new CompleteOCLFileDialog(this, wizardPage, initialSelection);
	}

	@Override
	protected String getEditorId() {
		return CompleteOCLUiModule.EDITOR_ID;
	}

	@Override
	public @NonNull String getInitialContentsAsString(@NonNull IFile newFile, @NonNull AbstractFileDialog dialog) {
		String firstPackageName = null;
		String firstTypeName = null;
		String firstPropertyName = null;
		StringBuilder s = new StringBuilder();
		List<URI> uris = dialog.getURIs();
		if (uris.size() > 0) {
			ResourceSet resourceSet = new ResourceSetImpl();
			URI newURI = URI.createPlatformResourceURI(newFile.getFullPath().toString(), true);
			for (URI uri : uris) {
				@SuppressWarnings("null")@NonNull URI deresolvedURI = uri.deresolve(newURI);
				try {
					Resource resource = resourceSet.getResource(uri, true);
					s.append("import '" + ValuesUtil.oclToString(deresolvedURI) + "'\n");
					if (firstPropertyName == null) {
						for (EObject eObject : resource.getContents()) {
							if (eObject instanceof org.eclipse.uml2.uml.Package) {
								org.eclipse.uml2.uml.Package umlPackage = (org.eclipse.uml2.uml.Package)eObject;
								if (firstPackageName == null) {
									firstPackageName = umlPackage.getName();
								}
								for (Type umlType : umlPackage.getOwnedTypes()) {
									if (firstTypeName == null) {
										firstTypeName = umlType.getName();
										firstPackageName = umlPackage.getName();
									}
									if (umlType instanceof org.eclipse.uml2.uml.Class) {
										for (Property umlProperty : ((org.eclipse.uml2.uml.Class)umlType).getOwnedAttributes()) {
											firstPropertyName = umlProperty.getName();
											firstTypeName = umlType.getName();
											firstPackageName = umlPackage.getName();
											break;
										}
									}
									if (firstPropertyName != null) {
										break;
									}
								}
							}
							else if (eObject instanceof EPackage) {
								EPackage ePackage = (EPackage)eObject;
								if (firstPackageName == null) {
									firstPackageName = ePackage.getName();
								}
								for (EClassifier eClassifier : ePackage.getEClassifiers()) {
									if (firstTypeName == null) {
										firstTypeName = eClassifier.getName();
										firstPackageName = ePackage.getName();
									}
									if (eClassifier instanceof EClass) {
										for (EStructuralFeature eStructuralFeature : ((EClass)eClassifier).getEAllStructuralFeatures()) {
											firstPropertyName = eStructuralFeature.getName();
											firstTypeName = eClassifier.getName();
											firstPackageName = ePackage.getName();
											break;
										}
									}
									if (firstPropertyName != null) {
										break;
									}
								}
							}
							if (firstPropertyName != null) {
								break;
							}
						}
					}
				}
				catch (Throwable e) {} 
			}
			s.append("\n");
		}
		if (firstPackageName == null) {
			firstPackageName = "undefined_root_package_name";
		}
		if (firstTypeName == null) {
			firstTypeName = "Example";
		}
		if (firstPropertyName == null) {
			firstPropertyName = "feature";
		}
		s.append("package " + firstPackageName + "\n");
		s.append("\n");
		s.append("context " + firstTypeName + "\n");
		s.append("--\n");
		s.append("-- example invariant with a custom error message to verify that\n");
		s.append("-- the '" + firstPropertyName + "' property of all '" + firstPackageName + "::" + firstTypeName + "' instances is non-null\n");
		s.append("--\n");
		s.append("inv NonNull_" + firstPropertyName + "('The \\'" + firstPropertyName + "\\' property of \"' + self.toString() + '\" is null'):\n");
		s.append("\t" + firstPropertyName + " <> null\n");
		s.append("\n");
		s.append("endpackage\n");
		@SuppressWarnings("null")@NonNull String string = s.toString();
		return string;
	}

	@Override
	public @NonNull String getNewFileExtension() {
		return "ocl";
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull String getNewFileName() {
		return CompleteOCLUIMessages.NewWizardPage_defaultFileName;
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull String getNewFileLabel() {
		return CompleteOCLUIMessages.NewWizardPage_fileNameLabel;
	}
	
	@SuppressWarnings("null")
	@Override
	public @NonNull String getPageDescription() {
		return CompleteOCLUIMessages.NewWizardPage_pageDescription;
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull String getPageSummary() {
		return CompleteOCLUIMessages.NewWizardPage_pageSummary;
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull String getPageTitle() {
		return CompleteOCLUIMessages.NewWizardPage_pageTitle;
	}
}