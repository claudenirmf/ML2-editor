/**
 * generated by Xtext 2.21.0
 */
package br.ufes.inf.nemo.ml2.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.EntityDeclaration#getName <em>Name</em>}</li>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.EntityDeclaration#getClassifiers <em>Classifiers</em>}</li>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.EntityDeclaration#getAssignments <em>Assignments</em>}</li>
 * </ul>
 *
 * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getEntityDeclaration()
 * @model
 * @generated
 */
public interface EntityDeclaration extends ModelElement
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getEntityDeclaration_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link br.ufes.inf.nemo.ml2.model.EntityDeclaration#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Classifiers</b></em>' reference list.
   * The list contents are of type {@link br.ufes.inf.nemo.ml2.model.Class}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Classifiers</em>' reference list.
   * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getEntityDeclaration_Classifiers()
   * @model
   * @generated
   */
  EList<br.ufes.inf.nemo.ml2.model.Class> getClassifiers();

  /**
   * Returns the value of the '<em><b>Assignments</b></em>' containment reference list.
   * The list contents are of type {@link br.ufes.inf.nemo.ml2.model.FeatureAssignment}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assignments</em>' containment reference list.
   * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getEntityDeclaration_Assignments()
   * @model containment="true"
   * @generated
   */
  EList<FeatureAssignment> getAssignments();

} // EntityDeclaration
