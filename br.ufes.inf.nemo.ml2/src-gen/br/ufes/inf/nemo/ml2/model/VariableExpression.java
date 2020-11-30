/**
 * generated by Xtext 2.21.0
 */
package br.ufes.inf.nemo.ml2.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.VariableExpression#getReferringVariable <em>Referring Variable</em>}</li>
 * </ul>
 *
 * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getVariableExpression()
 * @model
 * @generated
 */
public interface VariableExpression extends DotOperation
{
  /**
   * Returns the value of the '<em><b>Referring Variable</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Referring Variable</em>' attribute.
   * @see #setReferringVariable(String)
   * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getVariableExpression_ReferringVariable()
   * @model
   * @generated
   */
  String getReferringVariable();

  /**
   * Sets the value of the '{@link br.ufes.inf.nemo.ml2.model.VariableExpression#getReferringVariable <em>Referring Variable</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Referring Variable</em>' attribute.
   * @see #getReferringVariable()
   * @generated
   */
  void setReferringVariable(String value);

} // VariableExpression