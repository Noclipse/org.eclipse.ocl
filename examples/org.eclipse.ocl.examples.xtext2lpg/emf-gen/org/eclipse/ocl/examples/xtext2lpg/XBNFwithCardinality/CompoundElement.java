/**
 */
package org.eclipse.ocl.examples.xtext2lpg.XBNFwithCardinality;

import org.eclipse.emf.common.util.EList;

import org.eclipse.ocl.examples.xtext2lpg.XBNF.AbstractElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compound Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.ocl.examples.xtext2lpg.XBNFwithCardinality.CompoundElement#getElements <em>Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.ocl.examples.xtext2lpg.XBNFwithCardinality.XBNFwithCardinalityPackage#getCompoundElement()
 * @model abstract="true"
 * @generated
 */
public interface CompoundElement extends AbstractElement {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.ocl.examples.xtext2lpg.XBNF.AbstractElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see org.eclipse.ocl.examples.xtext2lpg.XBNFwithCardinality.XBNFwithCardinalityPackage#getCompoundElement_Elements()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<AbstractElement> getElements();

} // CompoundElement
