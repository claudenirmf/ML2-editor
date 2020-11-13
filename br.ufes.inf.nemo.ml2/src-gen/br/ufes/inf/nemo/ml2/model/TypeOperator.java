/**
 * generated by Xtext 2.21.0
 */
package br.ufes.inf.nemo.ml2.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Type Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getTypeOperator()
 * @model
 * @generated
 */
public enum TypeOperator implements Enumerator
{
  /**
   * The '<em><b>OCL AS TYPE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #OCL_AS_TYPE_VALUE
   * @generated
   * @ordered
   */
  OCL_AS_TYPE(0, "OCL_AS_TYPE", "oclAsType"),

  /**
   * The '<em><b>OCL IS KIND OF</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #OCL_IS_KIND_OF_VALUE
   * @generated
   * @ordered
   */
  OCL_IS_KIND_OF(1, "OCL_IS_KIND_OF", "oclIsKindOf"),

  /**
   * The '<em><b>OCL IS TYPE OF</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #OCL_IS_TYPE_OF_VALUE
   * @generated
   * @ordered
   */
  OCL_IS_TYPE_OF(2, "OCL_IS_TYPE_OF", "oclIsTypeOf");

  /**
   * The '<em><b>OCL AS TYPE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #OCL_AS_TYPE
   * @model literal="oclAsType"
   * @generated
   * @ordered
   */
  public static final int OCL_AS_TYPE_VALUE = 0;

  /**
   * The '<em><b>OCL IS KIND OF</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #OCL_IS_KIND_OF
   * @model literal="oclIsKindOf"
   * @generated
   * @ordered
   */
  public static final int OCL_IS_KIND_OF_VALUE = 1;

  /**
   * The '<em><b>OCL IS TYPE OF</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #OCL_IS_TYPE_OF
   * @model literal="oclIsTypeOf"
   * @generated
   * @ordered
   */
  public static final int OCL_IS_TYPE_OF_VALUE = 2;

  /**
   * An array of all the '<em><b>Type Operator</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final TypeOperator[] VALUES_ARRAY =
    new TypeOperator[]
    {
      OCL_AS_TYPE,
      OCL_IS_KIND_OF,
      OCL_IS_TYPE_OF,
    };

  /**
   * A public read-only list of all the '<em><b>Type Operator</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<TypeOperator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Type Operator</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static TypeOperator get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      TypeOperator result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Type Operator</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static TypeOperator getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      TypeOperator result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Type Operator</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static TypeOperator get(int value)
  {
    switch (value)
    {
      case OCL_AS_TYPE_VALUE: return OCL_AS_TYPE;
      case OCL_IS_KIND_OF_VALUE: return OCL_IS_KIND_OF;
      case OCL_IS_TYPE_OF_VALUE: return OCL_IS_TYPE_OF;
    }
    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final int value;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String name;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String literal;

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private TypeOperator(int value, String name, String literal)
  {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getLiteral()
  {
    return literal;
  }

  /**
   * Returns the literal value of the enumerator, which is its string representation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    return literal;
  }
  
} //TypeOperator
