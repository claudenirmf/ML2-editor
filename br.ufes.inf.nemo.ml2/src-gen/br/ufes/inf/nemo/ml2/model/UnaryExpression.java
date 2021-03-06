/**
 * generated by Xtext 2.21.0
 */
package br.ufes.inf.nemo.ml2.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.UnaryExpression#getOperator <em>Operator</em>}</li>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.UnaryExpression#getRight <em>Right</em>}</li>
 * </ul>
 *
 * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getUnaryExpression()
 * @model
 * @generated
 */
public interface UnaryExpression extends EObject
{
  /**
   * Returns the value of the '<em><b>Operator</b></em>' attribute.
   * The literals are from the enumeration {@link br.ufes.inf.nemo.ml2.model.UnaryOperator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operator</em>' attribute.
   * @see br.ufes.inf.nemo.ml2.model.UnaryOperator
   * @see #setOperator(UnaryOperator)
   * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getUnaryExpression_Operator()
   * @model
   * @generated
   */
  UnaryOperator getOperator();

  /**
   * Sets the value of the '{@link br.ufes.inf.nemo.ml2.model.UnaryExpression#getOperator <em>Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operator</em>' attribute.
   * @see br.ufes.inf.nemo.ml2.model.UnaryOperator
   * @see #getOperator()
   * @generated
   */
  void setOperator(UnaryOperator value);

  /**
   * Returns the value of the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right</em>' containment reference.
   * @see #setRight(TermExpression)
   * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getUnaryExpression_Right()
   * @model containment="true"
   * @generated
   */
  TermExpression getRight();

  /**
   * Sets the value of the '{@link br.ufes.inf.nemo.ml2.model.UnaryExpression#getRight <em>Right</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right</em>' containment reference.
   * @see #getRight()
   * @generated
   */
  void setRight(TermExpression value);

} // UnaryExpression
