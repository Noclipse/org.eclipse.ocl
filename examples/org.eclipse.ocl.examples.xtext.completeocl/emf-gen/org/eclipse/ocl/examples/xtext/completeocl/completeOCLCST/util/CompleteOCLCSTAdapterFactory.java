/**
 * <copyright>
 *
 * Copyright (c) 2010 E.D.Willink and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: CompleteOCLCSTAdapterFactory.java,v 1.7 2011/05/20 15:26:51 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.examples.domain.elements.Nameable;
import org.eclipse.ocl.examples.pivot.util.MorePivotable;
import org.eclipse.ocl.examples.pivot.util.Pivotable;
import org.eclipse.ocl.examples.xtext.base.baseCST.ConstraintCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.ElementCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.ModelElementCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.NamedElementCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.NamespaceCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.PackageCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.PivotableElementCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.RootCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.SpecificationCS;
import org.eclipse.ocl.examples.xtext.base.baseCST.TypedElementCS;
import org.eclipse.ocl.examples.xtext.base.util.VisitableCS;
import org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.*;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ExpCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ExpSpecificationCS;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.CompleteOCLCSTPackage
 * @generated
 */
public class CompleteOCLCSTAdapterFactory extends AdapterFactoryImpl
{
  /**
	 * The cached model package.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected static CompleteOCLCSTPackage modelPackage;

  /**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public CompleteOCLCSTAdapterFactory()
  {
		if (modelPackage == null)
		{
			modelPackage = CompleteOCLCSTPackage.eINSTANCE;
		}
	}

  /**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
  @Override
  public boolean isFactoryForType(Object object)
  {
		if (object == modelPackage)
		{
			return true;
		}
		if (object instanceof EObject)
		{
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

  /**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected CompleteOCLCSTSwitch<Adapter> modelSwitch =
    new CompleteOCLCSTSwitch<Adapter>()
		{
			@Override
			public Adapter caseBodyCS(BodyCS object)
			{
				return createBodyCSAdapter();
			}
			@Override
			public Adapter caseClassifierContextDeclCS(ClassifierContextDeclCS object)
			{
				return createClassifierContextDeclCSAdapter();
			}
			@Override
			public Adapter caseCompleteOCLDocumentCS(CompleteOCLDocumentCS object)
			{
				return createCompleteOCLDocumentCSAdapter();
			}
			@Override
			public Adapter caseContextConstraintCS(ContextConstraintCS object)
			{
				return createContextConstraintCSAdapter();
			}
			@Override
			public Adapter caseContextDeclCS(ContextDeclCS object)
			{
				return createContextDeclCSAdapter();
			}
			@Override
			public Adapter caseContextSpecificationCS(ContextSpecificationCS object)
			{
				return createContextSpecificationCSAdapter();
			}
			@Override
			public Adapter caseDefCS(DefCS object)
			{
				return createDefCSAdapter();
			}
			@Override
			public Adapter caseDefFeatureCS(DefFeatureCS object)
			{
				return createDefFeatureCSAdapter();
			}
			@Override
			public Adapter caseDefOperationCS(DefOperationCS object)
			{
				return createDefOperationCSAdapter();
			}
			@Override
			public Adapter caseDefPropertyCS(DefPropertyCS object)
			{
				return createDefPropertyCSAdapter();
			}
			@Override
			public Adapter caseDerCS(DerCS object)
			{
				return createDerCSAdapter();
			}
			@Override
			public Adapter caseFeatureContextDeclCS(FeatureContextDeclCS object)
			{
				return createFeatureContextDeclCSAdapter();
			}
			@Override
			public Adapter caseIncludeCS(IncludeCS object)
			{
				return createIncludeCSAdapter();
			}
			@Override
			public Adapter caseInitCS(InitCS object)
			{
				return createInitCSAdapter();
			}
			@Override
			public Adapter caseInvCS(InvCS object)
			{
				return createInvCSAdapter();
			}
			@Override
			public Adapter caseOCLMessageArgCS(OCLMessageArgCS object)
			{
				return createOCLMessageArgCSAdapter();
			}
			@Override
			public Adapter caseOperationContextDeclCS(OperationContextDeclCS object)
			{
				return createOperationContextDeclCSAdapter();
			}
			@Override
			public Adapter casePackageDeclarationCS(PackageDeclarationCS object)
			{
				return createPackageDeclarationCSAdapter();
			}
			@Override
			public Adapter casePathNameDeclCS(PathNameDeclCS object)
			{
				return createPathNameDeclCSAdapter();
			}
			@Override
			public Adapter casePostCS(PostCS object)
			{
				return createPostCSAdapter();
			}
			@Override
			public Adapter casePreCS(PreCS object)
			{
				return createPreCSAdapter();
			}
			@Override
			public Adapter casePropertyContextDeclCS(PropertyContextDeclCS object)
			{
				return createPropertyContextDeclCSAdapter();
			}
			@Override
			public Adapter caseVisitableCS(VisitableCS object)
			{
				return createVisitableCSAdapter();
			}
			@Override
			public Adapter caseElementCS(ElementCS object)
			{
				return createElementCSAdapter();
			}
			@Override
			public Adapter casePivotable(Pivotable object)
			{
				return createPivotableAdapter();
			}
			@Override
			public Adapter casePivotableElementCS(PivotableElementCS object)
			{
				return createPivotableElementCSAdapter();
			}
			@Override
			public Adapter caseModelElementCS(ModelElementCS object)
			{
				return createModelElementCSAdapter();
			}
			@Override
			public Adapter caseNameable(Nameable object)
			{
				return createNameableAdapter();
			}
			@Override
			public Adapter caseNamedElementCS(NamedElementCS object)
			{
				return createNamedElementCSAdapter();
			}
			@Override
			public Adapter caseConstraintCS(ConstraintCS object)
			{
				return createConstraintCSAdapter();
			}
			@Override
			public Adapter caseMorePivotable(MorePivotable object)
			{
				return createMorePivotableAdapter();
			}
			@Override
			public Adapter caseNamespaceCS(NamespaceCS object)
			{
				return createNamespaceCSAdapter();
			}
			@Override
			public Adapter casePackageCS(PackageCS object)
			{
				return createPackageCSAdapter();
			}
			@Override
			public Adapter caseRootCS(RootCS object)
			{
				return createRootCSAdapter();
			}
			@Override
			public Adapter caseSpecificationCS(SpecificationCS object)
			{
				return createSpecificationCSAdapter();
			}
			@Override
			public Adapter caseExpSpecificationCS(ExpSpecificationCS object)
			{
				return createExpSpecificationCSAdapter();
			}
			@Override
			public Adapter caseTypedElementCS(TypedElementCS object)
			{
				return createTypedElementCSAdapter();
			}
			@Override
			public Adapter caseExpCS(ExpCS object)
			{
				return createExpCSAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object)
			{
				return createEObjectAdapter();
			}
		};

  /**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
  @Override
  public Adapter createAdapter(Notifier target)
  {
		return modelSwitch.doSwitch((EObject)target);
	}


  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.BodyCS <em>Body CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.BodyCS
	 * @generated
	 */
  public Adapter createBodyCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.ClassifierContextDeclCS <em>Classifier Context Decl CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.ClassifierContextDeclCS
	 * @generated
	 */
  public Adapter createClassifierContextDeclCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.CompleteOCLDocumentCS <em>Complete OCL Document CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.CompleteOCLDocumentCS
	 * @generated
	 */
  public Adapter createCompleteOCLDocumentCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.ContextConstraintCS <em>Context Constraint CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.ContextConstraintCS
	 * @generated
	 */
	public Adapter createContextConstraintCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.base.baseCST.ConstraintCS <em>Constraint CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.base.baseCST.ConstraintCS
	 * @generated
	 */
  public Adapter createConstraintCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.pivot.util.MorePivotable <em>More Pivotable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.pivot.util.MorePivotable
	 * @generated
	 */
	public Adapter createMorePivotableAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.ContextDeclCS <em>Context Decl CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.ContextDeclCS
	 * @generated
	 */
  public Adapter createContextDeclCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.ContextSpecificationCS <em>Context Specification CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.ContextSpecificationCS
	 * @generated
	 */
	public Adapter createContextSpecificationCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.DefCS <em>Def CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.DefCS
	 * @generated
	 */
  public Adapter createDefCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.DefFeatureCS <em>Def Feature CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.DefFeatureCS
	 * @generated
	 */
	public Adapter createDefFeatureCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.DefOperationCS <em>Def Operation CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.DefOperationCS
	 * @generated
	 */
	public Adapter createDefOperationCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.DefPropertyCS <em>Def Property CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.DefPropertyCS
	 * @generated
	 */
	public Adapter createDefPropertyCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.DerCS <em>Der CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.DerCS
	 * @generated
	 */
  public Adapter createDerCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.FeatureContextDeclCS <em>Feature Context Decl CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.FeatureContextDeclCS
	 * @generated
	 */
  public Adapter createFeatureContextDeclCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.IncludeCS <em>Include CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.IncludeCS
	 * @generated
	 */
	public Adapter createIncludeCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.InitCS <em>Init CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.InitCS
	 * @generated
	 */
  public Adapter createInitCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.InvCS <em>Inv CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.InvCS
	 * @generated
	 */
  public Adapter createInvCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.OCLMessageArgCS <em>OCL Message Arg CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.OCLMessageArgCS
	 * @generated
	 */
	public Adapter createOCLMessageArgCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.OperationContextDeclCS <em>Operation Context Decl CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.OperationContextDeclCS
	 * @generated
	 */
  public Adapter createOperationContextDeclCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.PackageDeclarationCS <em>Package Declaration CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.PackageDeclarationCS
	 * @generated
	 */
  public Adapter createPackageDeclarationCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.PathNameDeclCS <em>Path Name Decl CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.PathNameDeclCS
	 * @generated
	 */
	public Adapter createPathNameDeclCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.PostCS <em>Post CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.PostCS
	 * @generated
	 */
  public Adapter createPostCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.PreCS <em>Pre CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.PreCS
	 * @generated
	 */
  public Adapter createPreCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.PropertyContextDeclCS <em>Property Context Decl CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.completeocl.completeOCLCST.PropertyContextDeclCS
	 * @generated
	 */
  public Adapter createPropertyContextDeclCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.base.util.VisitableCS <em>Visitable CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.base.util.VisitableCS
	 * @generated
	 */
	public Adapter createVisitableCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.base.baseCST.ElementCS <em>Element CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.base.baseCST.ElementCS
	 * @generated
	 */
  public Adapter createElementCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.pivot.util.Pivotable <em>Pivotable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.pivot.util.Pivotable
	 * @generated
	 */
	public Adapter createPivotableAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.base.baseCST.PivotableElementCS <em>Pivotable Element CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.base.baseCST.PivotableElementCS
	 * @generated
	 */
	public Adapter createPivotableElementCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.base.baseCST.ModelElementCS <em>Model Element CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.base.baseCST.ModelElementCS
	 * @generated
	 */
  public Adapter createModelElementCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.domain.elements.Nameable <em>Nameable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.domain.elements.Nameable
	 * @generated
	 */
	public Adapter createNameableAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.base.baseCST.NamedElementCS <em>Named Element CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.base.baseCST.NamedElementCS
	 * @generated
	 */
  public Adapter createNamedElementCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.base.baseCST.NamespaceCS <em>Namespace CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.base.baseCST.NamespaceCS
	 * @generated
	 */
  public Adapter createNamespaceCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.base.baseCST.PackageCS <em>Package CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.base.baseCST.PackageCS
	 * @generated
	 */
	public Adapter createPackageCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.base.baseCST.RootCS <em>Root CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.base.baseCST.RootCS
	 * @generated
	 */
	public Adapter createRootCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.base.baseCST.SpecificationCS <em>Specification CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.base.baseCST.SpecificationCS
	 * @generated
	 */
	public Adapter createSpecificationCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ExpSpecificationCS <em>Exp Specification CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ExpSpecificationCS
	 * @generated
	 */
	public Adapter createExpSpecificationCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.base.baseCST.TypedElementCS <em>Typed Element CS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.base.baseCST.TypedElementCS
	 * @generated
	 */
	public Adapter createTypedElementCSAdapter()
	{
		return null;
	}

/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ExpCS <em>Exp CS</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.ocl.examples.xtext.essentialocl.essentialOCLCST.ExpCS
	 * @generated
	 */
  public Adapter createExpCSAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
  public Adapter createEObjectAdapter()
  {
		return null;
	}

} //CompleteOCLCSTAdapterFactory
