/**
 * generated by Xtext 2.21.0
 */
package br.ufes.inf.nemo.ml2.model.impl;

import br.ufes.inf.nemo.ml2.model.ModelPackage;
import br.ufes.inf.nemo.ml2.model.UserDefinedTypeLiteral;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User Defined Type Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.impl.UserDefinedTypeLiteralImpl#getTypeName <em>Type Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UserDefinedTypeLiteralImpl extends TypeLiteralExpressionImpl implements UserDefinedTypeLiteral
{
  /**
   * The cached value of the '{@link #getTypeName() <em>Type Name</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypeName()
   * @generated
   * @ordered
   */
  protected br.ufes.inf.nemo.ml2.model.Class typeName;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected UserDefinedTypeLiteralImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return ModelPackage.Literals.USER_DEFINED_TYPE_LITERAL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public br.ufes.inf.nemo.ml2.model.Class getTypeName()
  {
    if (typeName != null && typeName.eIsProxy())
    {
      InternalEObject oldTypeName = (InternalEObject)typeName;
      typeName = (br.ufes.inf.nemo.ml2.model.Class)eResolveProxy(oldTypeName);
      if (typeName != oldTypeName)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.USER_DEFINED_TYPE_LITERAL__TYPE_NAME, oldTypeName, typeName));
      }
    }
    return typeName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public br.ufes.inf.nemo.ml2.model.Class basicGetTypeName()
  {
    return typeName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTypeName(br.ufes.inf.nemo.ml2.model.Class newTypeName)
  {
    br.ufes.inf.nemo.ml2.model.Class oldTypeName = typeName;
    typeName = newTypeName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.USER_DEFINED_TYPE_LITERAL__TYPE_NAME, oldTypeName, typeName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case ModelPackage.USER_DEFINED_TYPE_LITERAL__TYPE_NAME:
        if (resolve) return getTypeName();
        return basicGetTypeName();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case ModelPackage.USER_DEFINED_TYPE_LITERAL__TYPE_NAME:
        setTypeName((br.ufes.inf.nemo.ml2.model.Class)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case ModelPackage.USER_DEFINED_TYPE_LITERAL__TYPE_NAME:
        setTypeName((br.ufes.inf.nemo.ml2.model.Class)null);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case ModelPackage.USER_DEFINED_TYPE_LITERAL__TYPE_NAME:
        return typeName != null;
    }
    return super.eIsSet(featureID);
  }

} //UserDefinedTypeLiteralImpl
