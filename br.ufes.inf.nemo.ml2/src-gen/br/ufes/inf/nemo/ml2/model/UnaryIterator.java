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
 * A representation of the literals of the enumeration '<em><b>Unary Iterator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see br.ufes.inf.nemo.ml2.model.ModelPackage#getUnaryIterator()
 * @model
 * @generated
 */
public enum UnaryIterator implements Enumerator
{
  /**
   * The '<em><b>SELECT</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SELECT_VALUE
   * @generated
   * @ordered
   */
  SELECT(0, "SELECT", "select"),

  /**
   * The '<em><b>REJECT</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #REJECT_VALUE
   * @generated
   * @ordered
   */
  REJECT(1, "REJECT", "reject"),

  /**
   * The '<em><b>COLLECT</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #COLLECT_VALUE
   * @generated
   * @ordered
   */
  COLLECT(2, "COLLECT", "collect"),

  /**
   * The '<em><b>ANY</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ANY_VALUE
   * @generated
   * @ordered
   */
  ANY(3, "ANY", "any"),

  /**
   * The '<em><b>ONE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ONE_VALUE
   * @generated
   * @ordered
   */
  ONE(4, "ONE", "one"),

  /**
   * The '<em><b>IS UNIQUE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #IS_UNIQUE_VALUE
   * @generated
   * @ordered
   */
  IS_UNIQUE(5, "IS_UNIQUE", "isUnique"),

  /**
   * The '<em><b>CLOSURE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CLOSURE_VALUE
   * @generated
   * @ordered
   */
  CLOSURE(6, "CLOSURE", "closure");

  /**
   * The '<em><b>SELECT</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SELECT
   * @model literal="select"
   * @generated
   * @ordered
   */
  public static final int SELECT_VALUE = 0;

  /**
   * The '<em><b>REJECT</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #REJECT
   * @model literal="reject"
   * @generated
   * @ordered
   */
  public static final int REJECT_VALUE = 1;

  /**
   * The '<em><b>COLLECT</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #COLLECT
   * @model literal="collect"
   * @generated
   * @ordered
   */
  public static final int COLLECT_VALUE = 2;

  /**
   * The '<em><b>ANY</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ANY
   * @model literal="any"
   * @generated
   * @ordered
   */
  public static final int ANY_VALUE = 3;

  /**
   * The '<em><b>ONE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ONE
   * @model literal="one"
   * @generated
   * @ordered
   */
  public static final int ONE_VALUE = 4;

  /**
   * The '<em><b>IS UNIQUE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #IS_UNIQUE
   * @model literal="isUnique"
   * @generated
   * @ordered
   */
  public static final int IS_UNIQUE_VALUE = 5;

  /**
   * The '<em><b>CLOSURE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CLOSURE
   * @model literal="closure"
   * @generated
   * @ordered
   */
  public static final int CLOSURE_VALUE = 6;

  /**
   * An array of all the '<em><b>Unary Iterator</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final UnaryIterator[] VALUES_ARRAY =
    new UnaryIterator[]
    {
      SELECT,
      REJECT,
      COLLECT,
      ANY,
      ONE,
      IS_UNIQUE,
      CLOSURE,
    };

  /**
   * A public read-only list of all the '<em><b>Unary Iterator</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<UnaryIterator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Unary Iterator</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static UnaryIterator get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      UnaryIterator result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Unary Iterator</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static UnaryIterator getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      UnaryIterator result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Unary Iterator</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static UnaryIterator get(int value)
  {
    switch (value)
    {
      case SELECT_VALUE: return SELECT;
      case REJECT_VALUE: return REJECT;
      case COLLECT_VALUE: return COLLECT;
      case ANY_VALUE: return ANY;
      case ONE_VALUE: return ONE;
      case IS_UNIQUE_VALUE: return IS_UNIQUE;
      case CLOSURE_VALUE: return CLOSURE;
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
  private UnaryIterator(int value, String name, String literal)
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
  
} //UnaryIterator
