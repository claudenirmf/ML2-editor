/**
 * generated by Xtext 2.21.0
 */
package br.ufes.inf.nemo.ml2.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Primitive Type Name</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.PrimitiveTypeName#getTypename <em>Typename</em>}</li>
 * </ul>
 *
 * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getPrimitiveTypeName()
 * @model
 * @generated
 */
public interface PrimitiveTypeName extends TypeLiteralExpression
{
  /**
   * Returns the value of the '<em><b>Typename</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Typename</em>' attribute.
   * @see #setTypename(String)
   * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getPrimitiveTypeName_Typename()
   * @model
   * @generated
   */
  String getTypename();

  /**
   * Sets the value of the '{@link br.ufes.inf.nemo.ml2.model.PrimitiveTypeName#getTypename <em>Typename</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Typename</em>' attribute.
   * @see #getTypename()
   * @generated
   */
  void setTypename(String value);

} // PrimitiveTypeName