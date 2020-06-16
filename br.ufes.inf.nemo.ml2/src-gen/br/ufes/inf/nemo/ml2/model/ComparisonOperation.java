/**
 * generated by Xtext 2.21.0
 */
package br.ufes.inf.nemo.ml2.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Comparison Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.ComparisonOperation#getOperator <em>Operator</em>}</li>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.ComparisonOperation#getRight <em>Right</em>}</li>
 * </ul>
 *
 * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getComparisonOperation()
 * @model
 * @generated
 */
public interface ComparisonOperation extends EObject
{
  /**
   * Returns the value of the '<em><b>Operator</b></em>' attribute.
   * The literals are from the enumeration {@link br.ufes.inf.nemo.ml2.model.ComparisonOperator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operator</em>' attribute.
   * @see br.ufes.inf.nemo.ml2.model.ComparisonOperator
   * @see #setOperator(ComparisonOperator)
   * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getComparisonOperation_Operator()
   * @model
   * @generated
   */
  ComparisonOperator getOperator();

  /**
   * Sets the value of the '{@link br.ufes.inf.nemo.ml2.model.ComparisonOperation#getOperator <em>Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operator</em>' attribute.
   * @see br.ufes.inf.nemo.ml2.model.ComparisonOperator
   * @see #getOperator()
   * @generated
   */
  void setOperator(ComparisonOperator value);

  /**
   * Returns the value of the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right</em>' containment reference.
   * @see #setRight(RelationalExpression)
   * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getComparisonOperation_Right()
   * @model containment="true"
   * @generated
   */
  RelationalExpression getRight();

  /**
   * Sets the value of the '{@link br.ufes.inf.nemo.ml2.model.ComparisonOperation#getRight <em>Right</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right</em>' containment reference.
   * @see #getRight()
   * @generated
   */
  void setRight(RelationalExpression value);

} // ComparisonOperation