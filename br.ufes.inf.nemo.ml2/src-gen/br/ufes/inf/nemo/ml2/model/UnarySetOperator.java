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
 * A representation of the literals of the enumeration '<em><b>Unary Set Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getUnarySetOperator()
 * @model
 * @generated
 */
public enum UnarySetOperator implements Enumerator
{
  /**
   * The '<em><b>SIZE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SIZE_VALUE
   * @generated
   * @ordered
   */
  SIZE(0, "SIZE", "size"),

  /**
   * The '<em><b>IS EMPTY</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #IS_EMPTY_VALUE
   * @generated
   * @ordered
   */
  IS_EMPTY(1, "IS_EMPTY", "isEmpty"),

  /**
   * The '<em><b>NOT EMPTY</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NOT_EMPTY_VALUE
   * @generated
   * @ordered
   */
  NOT_EMPTY(2, "NOT_EMPTY", "notEmpty"),

  /**
   * The '<em><b>SUM</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SUM_VALUE
   * @generated
   * @ordered
   */
  SUM(3, "SUM", "sum"),

  /**
   * The '<em><b>MIN</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #MIN_VALUE
   * @generated
   * @ordered
   */
  MIN(4, "MIN", "min"),

  /**
   * The '<em><b>MAX</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #MAX_VALUE
   * @generated
   * @ordered
   */
  MAX(5, "MAX", "max"),

  /**
   * The '<em><b>AS SET</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #AS_SET_VALUE
   * @generated
   * @ordered
   */
  AS_SET(6, "AS_SET", "asSet"),

  /**
   * The '<em><b>FLATTEN</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #FLATTEN_VALUE
   * @generated
   * @ordered
   */
  FLATTEN(7, "FLATTEN", "flatten");

  /**
   * The '<em><b>SIZE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SIZE
   * @model literal="size"
   * @generated
   * @ordered
   */
  public static final int SIZE_VALUE = 0;

  /**
   * The '<em><b>IS EMPTY</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #IS_EMPTY
   * @model literal="isEmpty"
   * @generated
   * @ordered
   */
  public static final int IS_EMPTY_VALUE = 1;

  /**
   * The '<em><b>NOT EMPTY</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NOT_EMPTY
   * @model literal="notEmpty"
   * @generated
   * @ordered
   */
  public static final int NOT_EMPTY_VALUE = 2;

  /**
   * The '<em><b>SUM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SUM
   * @model literal="sum"
   * @generated
   * @ordered
   */
  public static final int SUM_VALUE = 3;

  /**
   * The '<em><b>MIN</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #MIN
   * @model literal="min"
   * @generated
   * @ordered
   */
  public static final int MIN_VALUE = 4;

  /**
   * The '<em><b>MAX</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #MAX
   * @model literal="max"
   * @generated
   * @ordered
   */
  public static final int MAX_VALUE = 5;

  /**
   * The '<em><b>AS SET</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #AS_SET
   * @model literal="asSet"
   * @generated
   * @ordered
   */
  public static final int AS_SET_VALUE = 6;

  /**
   * The '<em><b>FLATTEN</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #FLATTEN
   * @model literal="flatten"
   * @generated
   * @ordered
   */
  public static final int FLATTEN_VALUE = 7;

  /**
   * An array of all the '<em><b>Unary Set Operator</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final UnarySetOperator[] VALUES_ARRAY =
    new UnarySetOperator[]
    {
      SIZE,
      IS_EMPTY,
      NOT_EMPTY,
      SUM,
      MIN,
      MAX,
      AS_SET,
      FLATTEN,
    };

  /**
   * A public read-only list of all the '<em><b>Unary Set Operator</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<UnarySetOperator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Unary Set Operator</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static UnarySetOperator get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      UnarySetOperator result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Unary Set Operator</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static UnarySetOperator getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      UnarySetOperator result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Unary Set Operator</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static UnarySetOperator get(int value)
  {
    switch (value)
    {
      case SIZE_VALUE: return SIZE;
      case IS_EMPTY_VALUE: return IS_EMPTY;
      case NOT_EMPTY_VALUE: return NOT_EMPTY;
      case SUM_VALUE: return SUM;
      case MIN_VALUE: return MIN;
      case MAX_VALUE: return MAX;
      case AS_SET_VALUE: return AS_SET;
      case FLATTEN_VALUE: return FLATTEN;
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
  private UnarySetOperator(int value, String name, String literal)
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
  
} //UnarySetOperator
