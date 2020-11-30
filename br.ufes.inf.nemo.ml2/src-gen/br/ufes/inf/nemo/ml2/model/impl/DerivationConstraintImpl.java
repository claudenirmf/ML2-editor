/**
 * generated by Xtext 2.21.0
 */
package br.ufes.inf.nemo.ml2.model.impl;

import br.ufes.inf.nemo.ml2.model.DerivationConstraint;
import br.ufes.inf.nemo.ml2.model.ModelPackage;
import br.ufes.inf.nemo.ml2.model.TypeLiteralExpression;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Derivation Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.impl.DerivationConstraintImpl#getFeatureContext <em>Feature Context</em>}</li>
 *   <li>{@link br.ufes.inf.nemo.ml2.model.impl.DerivationConstraintImpl#getContextType <em>Context Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DerivationConstraintImpl extends ConstraintImpl implements DerivationConstraint
{
  /**
   * The cached value of the '{@link #getFeatureContext() <em>Feature Context</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFeatureContext()
   * @generated
   * @ordered
   */
  protected EList<String> featureContext;

  /**
   * The cached value of the '{@link #getContextType() <em>Context Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getContextType()
   * @generated
   * @ordered
   */
  protected TypeLiteralExpression contextType;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DerivationConstraintImpl()
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
    return ModelPackage.Literals.DERIVATION_CONSTRAINT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getFeatureContext()
  {
    if (featureContext == null)
    {
      featureContext = new EDataTypeEList<String>(String.class, this, ModelPackage.DERIVATION_CONSTRAINT__FEATURE_CONTEXT);
    }
    return featureContext;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TypeLiteralExpression getContextType()
  {
    return contextType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetContextType(TypeLiteralExpression newContextType, NotificationChain msgs)
  {
    TypeLiteralExpression oldContextType = contextType;
    contextType = newContextType;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.DERIVATION_CONSTRAINT__CONTEXT_TYPE, oldContextType, newContextType);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setContextType(TypeLiteralExpression newContextType)
  {
    if (newContextType != contextType)
    {
      NotificationChain msgs = null;
      if (contextType != null)
        msgs = ((InternalEObject)contextType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.DERIVATION_CONSTRAINT__CONTEXT_TYPE, null, msgs);
      if (newContextType != null)
        msgs = ((InternalEObject)newContextType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.DERIVATION_CONSTRAINT__CONTEXT_TYPE, null, msgs);
      msgs = basicSetContextType(newContextType, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DERIVATION_CONSTRAINT__CONTEXT_TYPE, newContextType, newContextType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case ModelPackage.DERIVATION_CONSTRAINT__CONTEXT_TYPE:
        return basicSetContextType(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case ModelPackage.DERIVATION_CONSTRAINT__FEATURE_CONTEXT:
        return getFeatureContext();
      case ModelPackage.DERIVATION_CONSTRAINT__CONTEXT_TYPE:
        return getContextType();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case ModelPackage.DERIVATION_CONSTRAINT__FEATURE_CONTEXT:
        getFeatureContext().clear();
        getFeatureContext().addAll((Collection<? extends String>)newValue);
        return;
      case ModelPackage.DERIVATION_CONSTRAINT__CONTEXT_TYPE:
        setContextType((TypeLiteralExpression)newValue);
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
      case ModelPackage.DERIVATION_CONSTRAINT__FEATURE_CONTEXT:
        getFeatureContext().clear();
        return;
      case ModelPackage.DERIVATION_CONSTRAINT__CONTEXT_TYPE:
        setContextType((TypeLiteralExpression)null);
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
      case ModelPackage.DERIVATION_CONSTRAINT__FEATURE_CONTEXT:
        return featureContext != null && !featureContext.isEmpty();
      case ModelPackage.DERIVATION_CONSTRAINT__CONTEXT_TYPE:
        return contextType != null;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (featureContext: ");
    result.append(featureContext);
    result.append(')');
    return result.toString();
  }

} //DerivationConstraintImpl