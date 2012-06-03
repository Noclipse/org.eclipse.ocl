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
 *
 * $Id: CompleteOCLEObjectValidator.java,v 1.4 2011/05/20 15:26:51 ewillink Exp $
 */
package org.eclipse.ocl.examples.xtext.completeocl.validation;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.domain.evaluation.InvalidEvaluationException;
import org.eclipse.ocl.examples.domain.evaluation.InvalidValueException;
import org.eclipse.ocl.examples.domain.messages.EvaluatorMessages;
import org.eclipse.ocl.examples.domain.utilities.DomainUtil;
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.domain.values.ValueFactory;
import org.eclipse.ocl.examples.pivot.Constraint;
import org.eclipse.ocl.examples.pivot.Environment;
import org.eclipse.ocl.examples.pivot.EnvironmentFactory;
import org.eclipse.ocl.examples.pivot.ExpressionInOCL;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.UMLReflection;
import org.eclipse.ocl.examples.pivot.ValueSpecification;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.messages.OCLMessages;
import org.eclipse.ocl.examples.pivot.utilities.PivotEnvironmentFactory;
import org.eclipse.ocl.examples.pivot.utilities.PivotUtil;

/**
 * A BasicCompleteOCLEObjectValidator validates CompleteOCL invariants during an EMF validation, for each
 * EPackage for which a/the BasicCompleteOCLEObjectValidator instance has been registered as a validator
 * in the EValidator.Registry.
 */
public class BasicCompleteOCLEObjectValidator extends EObjectValidator
{
	private static class ValidationAdapter extends AdapterImpl
	{
		public static ValidationAdapter findAdapter(ResourceSet resourceSet) {
			if (resourceSet != null) {
				for (Adapter adapter : resourceSet.eAdapters()) {
					if (adapter instanceof ValidationAdapter) {
						return (ValidationAdapter)adapter;
					}
				}
			}
			return null;
		}

		protected final MetaModelManager metaModelManager;
		protected final EnvironmentFactory environmentFactory;
		protected final Environment rootEnvironment;
		
		public ValidationAdapter(MetaModelManager metaModelManager) {
			this.metaModelManager = metaModelManager;
			this.environmentFactory = new PivotEnvironmentFactory(null, metaModelManager);
			this.rootEnvironment = environmentFactory.createEnvironment();
		}

		public MetaModelManager getMetaModelManager() {
			return metaModelManager;
		}

		public boolean validate(EClassifier eClassifier, Object object, DiagnosticChain diagnostics, Map<Object, Object> context) {
			boolean allOk = true;
			Type type = metaModelManager.getPivotOfEcore(Type.class, eClassifier);
			for (Constraint constraint : metaModelManager.getAllConstraints(type)) {
				if (UMLReflection.INVARIANT.equals(constraint.getStereotype())) {
					String constraintName = constraint.getName();
					ValueSpecification specification = constraint.getSpecification();
					if (specification instanceof ExpressionInOCL) {			// Ignore OpaqueExpression -- probably from EAnnotations
						ExpressionInOCL query = (ExpressionInOCL)specification;
						EvaluationEnvironment evaluationEnvironment = environmentFactory.createEvaluationEnvironment();
						ValueFactory valueFactory = metaModelManager.getValueFactory();
						Value value = valueFactory.valueOf(object);
						evaluationEnvironment.add(query.getContextVariable(), value);
						DomainModelManager extents = evaluationEnvironment.createModelManager(object);
						EvaluationVisitor evaluationVisitor = environmentFactory.createEvaluationVisitor(rootEnvironment, evaluationEnvironment, extents);
						int severity = Diagnostic.ERROR;
						String message = null;
						if (query.getType() != evaluationVisitor.getMetaModelManager().getBooleanType()) {
							String objectLabel = DomainUtil.getLabel(eClassifier, object, context);
							message = DomainUtil.bind(OCLMessages.ValidationConstraintIsNotBoolean_ERROR_,
								PivotUtil.getConstraintTypeName(constraint), constraintName, objectLabel);
						}
						try {
							Value expressionResult = query.accept(evaluationVisitor);
							boolean isOk = false;
							if (!expressionResult.isNull()) {
								isOk = expressionResult.asBoolean();
								severity = Diagnostic.WARNING;
							}
							if (!isOk) {
								String objectLabel = DomainUtil.getLabel(eClassifier, object, context);
								OCLExpression messageExpression = query.getMessageExpression();
								if (messageExpression != null) {
									try {
										Value messageResult = messageExpression.accept(evaluationVisitor);
										if (!messageResult.isNull()) {
											message = messageResult.asString();
										}
									} catch (InvalidValueException e) {
										message = DomainUtil.bind(OCLMessages.ValidationMessageIsNotString_ERROR_,
											PivotUtil.getConstraintTypeName(constraint), constraintName, objectLabel);
										severity = Diagnostic.ERROR;
									}
									catch (Exception e) {
										message = DomainUtil.bind(OCLMessages.ValidationMessageException_ERROR_,
											PivotUtil.getConstraintTypeName(constraint), constraintName, objectLabel, e.getMessage());
										severity = Diagnostic.ERROR;
									}
								}
								if (message == null) {
									message = DomainUtil.bind(EvaluatorMessages.ValidationConstraintIsNotSatisfied_ERROR_,
										PivotUtil.getConstraintTypeName(constraint), constraintName, objectLabel);
								}
							}
						} catch (InvalidValueException e) {
							String objectLabel = DomainUtil.getLabel(eClassifier, object, context);
							message = DomainUtil.bind(OCLMessages.ValidationResultIsNotBoolean_ERROR_,
								PivotUtil.getConstraintTypeName(constraint), constraintName, objectLabel);
						} catch (InvalidEvaluationException e) {
							String objectLabel = DomainUtil.getLabel(eClassifier, object, context);
							message = DomainUtil.bind(OCLMessages.ValidationResultIsInvalid_ERROR_,
								PivotUtil.getConstraintTypeName(constraint), constraintName, objectLabel);
						} catch (Throwable e) {
							String objectLabel = DomainUtil.getLabel(eClassifier, object, context);
							message = DomainUtil.bind(OCLMessages.ValidationConstraintException_ERROR_,
								PivotUtil.getConstraintTypeName(constraint), constraintName, objectLabel, e.getMessage());
						}
						if (message != null) {
							diagnostics.add(new BasicDiagnostic(severity, DIAGNOSTIC_SOURCE, 0, message, new Object [] { object }));
						    allOk = false;
						    if (severity == Diagnostic.ERROR) {
						    	break;		// Generate many warnings but only one error
						    }
						}
					}
				}
			}
			return allOk;
		}
	}

	private static final BasicCompleteOCLEObjectValidator INSTANCE = new BasicCompleteOCLEObjectValidator();
	private static final Map<EPackage, EValidator> eValidators = new HashMap<EPackage, EValidator>();

	/**
	 * Install Complete OCL validation support in resourceSet for metaModelManager.
	 */
	public static void install(ResourceSet resourceSet, MetaModelManager metaModelManager) {
		if (resourceSet != null) {
			ValidationAdapter validationAdapter = ValidationAdapter.findAdapter(resourceSet);
			if (validationAdapter != null) {
				if (validationAdapter.getMetaModelManager() != metaModelManager) {
					throw new IllegalArgumentException("Inconsistent metaModelManager");
				}
			}
			else {
				validationAdapter = new ValidationAdapter(metaModelManager);
				resourceSet.eAdapters().add(validationAdapter);
			}
		}
	}
	
	/**
	 * Install Complete OCL validation support for all ePackage.
	 */
	public static void install(EPackage ePackage) {
		if (!eValidators.containsKey(ePackage)) {
			Object oldEntry = EValidator.Registry.INSTANCE.put(ePackage, INSTANCE);
			if (oldEntry instanceof EValidator.Descriptor) {
				oldEntry = ((EValidator.Descriptor)oldEntry).getEValidator();
			}
			EValidator eValidator = (EValidator) oldEntry;
			eValidators.put(ePackage, eValidator);
		}
	}

	/**
	 * Return the user's ResourceSet, preferably as a data element of the diagnostics, corresponding to
	 * the original validation context, else from the object else from the eClassifier.
	 */
	protected ResourceSet getResourceSet(EClassifier eClassifier, Object object, DiagnosticChain diagnostics) {
		ResourceSet resourceSet = null;
		if (diagnostics instanceof BasicDiagnostic) {
			for (Object dataObject : ((BasicDiagnostic)diagnostics).getData()) {
				if (dataObject instanceof EObject) {
					Resource resource = EcoreUtil.getRootContainer((EObject) dataObject).eResource();
					if (resource != null) {
						resourceSet = resource.getResourceSet();
						if (resourceSet != null) {
							break;
						}
					}
				}
			}
		}
		if (resourceSet == null) {
			if (object instanceof EObject) {
				Resource resource = EcoreUtil.getRootContainer((EObject) object).eResource();
				if (resource != null) {
					resourceSet = resource.getResourceSet();
				}
			}
			if (resourceSet == null) {
				Resource resource = EcoreUtil.getRootContainer(eClassifier).eResource();
				if (resource != null) {
					resourceSet = resource.getResourceSet();
				}
			}
		}
		return resourceSet;
	}

	@Override
	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean allOk = true;
		EPackage ePackage = eClass.getEPackage();
		EValidator eValidator = eValidators.get(ePackage);
		if (eValidator != null) {
			allOk &= eValidator.validate(eClass, eObject, diagnostics, context);
		}
		if ((allOk || (diagnostics != null)) && !eObject.eIsProxy()) {
			allOk &= validatePivot(eClass, eObject, diagnostics, context);
		}
		return allOk;
	}

	@Override
	public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean allOk = true;
		EPackage ePackage = eDataType.getEPackage();
		EValidator eValidator = eValidators.get(ePackage);
		if (eValidator != null) {
			allOk &= eValidator.validate(eDataType, value, diagnostics, context);
		}
		if ((allOk || (diagnostics != null)) && eDataType.isInstance(value)) {
			allOk &= validatePivot(eDataType, value, diagnostics, context);
		}
		return allOk;
	}

	protected boolean validatePivot(EClassifier eClassifier, Object object, DiagnosticChain diagnostics, Map<Object, Object> context) {
		ResourceSet resourceSet = getResourceSet(eClassifier, object, diagnostics);
		if (resourceSet != null) {
			ValidationAdapter validationAdapter = ValidationAdapter.findAdapter(resourceSet);
			if (validationAdapter != null) {
				boolean allOk = validationAdapter.validate(eClassifier, object, diagnostics, context);
				return allOk || (diagnostics != null);
			}
		}
		return true;
	}
}
