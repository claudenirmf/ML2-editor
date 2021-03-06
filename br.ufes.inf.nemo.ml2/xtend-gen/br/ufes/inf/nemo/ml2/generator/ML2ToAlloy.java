/**
 * Transformation of an ML2 model to an Alloy model.
 * 
 * @author Fernando Amaral Musso
 */
package br.ufes.inf.nemo.ml2.generator;

import br.ufes.inf.nemo.ml2.generator.OCLToAlloy;
import br.ufes.inf.nemo.ml2.model.Attribute;
import br.ufes.inf.nemo.ml2.model.AttributeAssignment;
import br.ufes.inf.nemo.ml2.model.CategorizationType;
import br.ufes.inf.nemo.ml2.model.Constraint;
import br.ufes.inf.nemo.ml2.model.DataType;
import br.ufes.inf.nemo.ml2.model.EntityDeclaration;
import br.ufes.inf.nemo.ml2.model.Feature;
import br.ufes.inf.nemo.ml2.model.FeatureAssignment;
import br.ufes.inf.nemo.ml2.model.FirstOrderClass;
import br.ufes.inf.nemo.ml2.model.GeneralizationSet;
import br.ufes.inf.nemo.ml2.model.HighOrderClass;
import br.ufes.inf.nemo.ml2.model.HigherOrderClass;
import br.ufes.inf.nemo.ml2.model.Individual;
import br.ufes.inf.nemo.ml2.model.Model;
import br.ufes.inf.nemo.ml2.model.ModelElement;
import br.ufes.inf.nemo.ml2.model.OrderlessClass;
import br.ufes.inf.nemo.ml2.model.PrimitiveType;
import br.ufes.inf.nemo.ml2.model.Reference;
import br.ufes.inf.nemo.ml2.model.ReferenceAssignment;
import br.ufes.inf.nemo.ml2.model.RegularityAttribute;
import br.ufes.inf.nemo.ml2.model.RegularityFeatureType;
import br.ufes.inf.nemo.ml2.model.RegularityReference;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ML2ToAlloy {
  @Inject
  @Extension
  private OCLToAlloy _oCLToAlloy;
  
  /**
   * Generates the file <modelname>.als containing Alloy code, derived from the transformation
   * of the ML2 Model provided by the file <modelname>.ml2.
   * 
   * @param ml2model the ML2 Model to be transformed.
   */
  public CharSequence generateAlloyModel(final Model ml2model) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("module ");
    String _name = ml2model.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("open MLTStar");
    _builder.newLine();
    _builder.append("open utils");
    _builder.newLine();
    _builder.newLine();
    {
      EList<ModelElement> _elements = ml2model.getElements();
      for(final ModelElement element : _elements) {
        CharSequence _generateAlloyElement = this.generateAlloyElement(element);
        _builder.append(_generateAlloyElement);
        _builder.newLineIfNotEmpty();
      }
    }
    CharSequence _generateDisjointIndividualsFact = this.generateDisjointIndividualsFact(ml2model);
    _builder.append(_generateDisjointIndividualsFact);
    _builder.newLineIfNotEmpty();
    CharSequence _generateDisjointDisconnectedHierarchiesFact = this.generateDisjointDisconnectedHierarchiesFact(ml2model);
    _builder.append(_generateDisjointDisconnectedHierarchiesFact);
    _builder.newLineIfNotEmpty();
    CharSequence _generateUnwantedInstantiationsFact = this.generateUnwantedInstantiationsFact(ml2model);
    _builder.append(_generateUnwantedInstantiationsFact);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Generates the file MLTStar.als, containing the Alloy formalization of the MLT* theory
   * developed by Victorio Carvalho, João Paulo Almeida and Claudenir Fonseca.
   * 
   * A new section named NOTABLE CONSTANTS is added, in order to make explicit the use of
   * signatures for the MLT* basic ordered types used in the Alloy model being generated.
   * 
   * @param ml2model the ML2 Model (used to add signatures to the notable constants section).
   */
  public CharSequence generateMLTStarAlloyModel(final Model model) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tMLT*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\t");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tMLT* extends basic MLT. The domain of quantification is a superset of Basic MLT,");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tadmitting types that are not stratified. This opens up the possibility of the types");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\t\"Entity\" (the type of all entities, i.e., the universe), \"Type\" (the type of all");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\ttypes), \"OrderedType\" (which is the type of all types in Basic MLT), etc.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tSome known limitations of this specification are:");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\t  - Static classification only;");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\t  - No support for entity\'s features (attributes and domain relations).");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tAuthors:");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\t  Victorio Carvalho - Basic MLT");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\t  João Paulo Almeida - Contributions to Basic MLT and initial developments of MLT*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\t  Claudenir Fonseca - Revision and refactoring of MLT* and further developments");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("module MLTStar");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/********************************************************************************************");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* UTILS - PREDICATES AND FUNCTIONS");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* The predicates below are used throughout the code improving its readability.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*******************************************************************************************/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("pred iof[x,y: Entity] \t\t\t\t\t{ x in iof.y }");
    _builder.newLine();
    _builder.append("pred specializes[x,y: Entity] \t\t\t{ x in specializes.y }");
    _builder.newLine();
    _builder.append("pred properSpecializes[ x,y: Entity] \t{ x in properSpecializes.y }");
    _builder.newLine();
    _builder.append("pred powertypeOf[x,y: Entity] \t\t\t{ x in powertypeOf.y }");
    _builder.newLine();
    _builder.append("pred categorizes[x,y: Entity] \t\t\t{ x in categorizes.y }");
    _builder.newLine();
    _builder.append("pred compCategorizes[x,y: Entity] \t\t{ x in compCategorizes.y }");
    _builder.newLine();
    _builder.append("pred disjCategorizes[x,y: Entity] \t\t{ x in disjCategorizes.y }");
    _builder.newLine();
    _builder.append("pred partitions[x,y: Entity] \t\t\t{ x in partitions.y }");
    _builder.newLine();
    _builder.append("pred isSubordinatedTo[x,y: Entity] \t\t{ x in isSubordinatedTo.y }");
    _builder.newLine();
    _builder.append("pred disjointExtentions[t,t\': Type] \t{ no iof.t & iof.t\' }");
    _builder.newLine();
    _builder.append("pred disjoint_[x,y: Entity] \t\t\t{ no x & y }");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/********************************************************************************************");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* MODEL SPECIFICATION");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Signatures and Constraints.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*******************************************************************************************/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tEntity");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tRepresents an entity, with all possible relations that may be established between");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tentities according to the theory.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("sig Entity { ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("iof: set Entity,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("directIof: set Entity");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tDirect instantiations are used to simplify the visualization of\tinstantiations.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all e: Entity | e.directIof = (e.iof) - (e.iof).properSpecializes");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tIndividual");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tAn individual is an entity that has no possible instances. The signature Individual");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tdoes not account for the entity Individual, which is going to be specified later");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tthrough the signature Individual_ (with an underscore at the end).");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("sig Individual in Entity {");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact individualDef {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all x: Entity | (x in Individual iff no iof.x)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tType");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tA type is an entity that has instances. Also, types must be in an instantiation");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tchain that eventually leads to some individual. As Individual, Type does not");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\taccount for the entity type, which is specified later as Type_.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("sig Type in Entity {");
    _builder.newLine();
    _builder.append(" \t");
    _builder.append("specializes: set Type,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("properSpecializes: set Type,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("isSubordinatedTo: set Type,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("powertypeOf: set Type,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("categorizes: set Type,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("compCategorizes: set Type,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("disjCategorizes: set Type,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("partitions: set Type,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("directSpecializes: set Entity,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("isDirectSubordinateTo: set Type,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("directCategorizes: set Type");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all e: Entity | e in Type iff some (iof.e)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tTypes are ultimately founded upon individuals.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact typeWellFounded {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t: Type | t in Type iff some (^iof.t & Individual)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tDirect relations are used to improve visualization of runs and checkings.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t: Type | t.directSpecializes = (t.properSpecializes) - (t.properSpecializes).properSpecializes");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t: Type | t.isDirectSubordinateTo = (t.isSubordinatedTo) - (t.isSubordinatedTo).properSpecializes");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t: Type | t.directCategorizes = ((t.categorizes) - (t.categorizes+t.powertypeOf).properSpecializes)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tBasicType");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tBasic types are the highest entities in their specialization chain of any instance");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\torder. That is, for any given type order there is one entity that every ordered");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\ttype specializes. In other words, a basic type is a type at the top of the ");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\thierarchy of specializations of types that are \"stratified\" in Basic MLT.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("sig BasicType in Type {");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all b: Type | b in BasicType iff ((all e: Entity | iof[e,b] iff e in Individual) or (some lot: BasicType | powertypeOf[b,lot]))");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tNot necessary in Basic MLT but saves up to 15 seconds.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact noIofLoopForBasicTypes\t{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all x: BasicType | x not in x.^iof");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tOrderedType");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tA type is a ordered type iff it is a specialization of a basic type.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("sig OrderedType in Type {");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t: Type | t in OrderedType iff (some b: BasicType | specializes[t,b])");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tOrderlessType");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tA type is a orderless type iff it is not an ordered one.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("sig OrderlessType in Type {");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t: Type | t in OrderlessType iff (no b: BasicType | specializes[t,b])");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/********************************************************************************************");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* CONSTANT MODEL ENTITIES");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Signatures to reference the entities which instances are members of the sets defined above.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*******************************************************************************************/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tDefining the \"Individual_\" basic type.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("sig Individual_ in Type {");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all e: Entity | e in Individual_ iff (all e\': Entity | iof[e\',e] iff no iof.e\')");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tDefining the type \"Type\", whose instances are all types.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("sig Type_ in Entity {");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t: Entity | t in Type_ iff (all e: Entity | iof[e,t] iff e in Type)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tDefining the universal type \"Entity\".");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("sig Entity_ in Entity {");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all e: Entity | e in Entity_ iff (all e\': Entity | iof[e\',e] iff e\' in Entity)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tDefining the type \"OrderedType\", whose instances are all types whose instances");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tare at the same order. These are the types in Basic MLT.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("sig OrderedType_ in Entity {");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all e: Entity | e in OrderedType_ iff (all e\': Entity | iof[e\',e] iff e\' in OrderedType)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tDefining the type \"OrderlessType\", whose instances are all types whose instances");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tspan through different orders. These are the types in Basic MLT.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("sig OrderlessType_ in Entity {");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all e: Entity | e in OrderlessType_ iff (e in Type and (all e\': Entity | iof[e\',e] iff e\' in OrderlessType))");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/********************************************************************************************");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* DEFINITIONS");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*******************************************************************************************/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tAxiom A7 - Two types are equal iff the set of all their possible instances is the same");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact typesEquivalence {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t1,t2: Type | (t1 = t2 iff iof.t1 = iof.t2)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tAxiom A8 - Specialization Definition: t1 specializes t2 iff all instances of t1 are also");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tinstances of t2.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact specializationDefinition {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t1,t2: Type | specializes[t1,t2] iff (all e: iof.t1 | iof[e,t2])");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tAxiom A9 - Proper Specialization Definition: t1 proper specializes t2 iff t1 specializes");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tt2 and is different from it.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact properSpecializationDefinition {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t1,t2: Type | properSpecializes[t1,t2] iff (specializes[t1,t2] and t1 != t2)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tAxiom A10 - Subordination Definition: t1 is subordinate to t2 iff every instance of t1");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tspecializes an instance of t2.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact subordinationDefinition {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t1,t2: Type | isSubordinatedTo[t1,t2] iff (all t3: iof.t1 | some (t3.properSpecializes & iof.t2))");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tAxiom A11 - Powertype Definition: a type t1 is powertype of a type t2 iff all instances of");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tt1 are specializations of t2 and all possible specializations of t2 are instances of t1.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact powertypeOfDefinition {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all pwt,t: Type | powertypeOf[pwt,t] iff (all t\': Entity | (iof[t\',pwt] iff specializes[t\',t]))");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tAxiom A12 - Categorization Definition: a type t1 categorizes a type t2 iff all instances");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tof t1 are proper specializations of t2.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact categorizationDefinition {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t1,t2: Type | categorizes[t1,t2] iff iof.t1 in properSpecializes.t2");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tAxiom A13 - Complete Categorization Definition: a type t1 completely categorizes a type t2");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tiff t1 categorizes t2 and for every instance of t2 there is some type that is instantiated");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tby it and is instance of t1.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact completeCategorizationDefinition {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t1,t2: Type | compCategorizes[t1,t2] iff (categorizes[t1,t2] and (all e: iof.t2 | some e.iof & iof.t1))");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tAxiom A14 - Disjoint Categorization Definition: a type t1 disjointly categorizes a type t2");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tiff t1 categorizes t2 and for any pair of different types that are instances of t1 implies");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tthis pair has disjoint extensions.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact disjointCategorizationDefinition {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t1,t2: Type | disjCategorizes[t1,t2] iff (categorizes[t1,t2] and (all t3,t4: iof.t1 | t3 != t4 implies disjoint_[iof.t3,iof.t4]))");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tAxiom A15 - Partitions Definition: a type t1 partitions a type t2 iff t1 disjointly categorizes");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*\tt2 and t1 completely categorizes t2.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("fact partitionsDefinition {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("all t1,t2: Entity | partitions[t1,t2] iff (disjCategorizes[t1,t2] and compCategorizes[t1,t2])");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/********************************************************************************************");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* USEFUL CONSTRAINTS");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Some constraints for cutting out unwanted models.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*******************************************************************************************/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact allEntitiesConnectedByInstantiation {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("let navigableiof = iof + ~iof | all x,y: Entity | (x in y.*navigableiof)");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact someIndividuals {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("some e: Entity | no iof.e");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fact someBasicTypes {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("some BasicType");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/********************************************************************************************");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* NOTABLE CONSTANTS");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Signatures to represent the most used Ordered Types.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*******************************************************************************************/");
    _builder.newLine();
    _builder.newLine();
    CharSequence _generateNotableConstantsSection = this.generateNotableConstantsSection(model);
    _builder.append(_generateNotableConstantsSection);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * Generates the file utils.als, containing useful extra elements for the transformation.
   * 
   * The Boolean enumeration, for instance, is used to map ML2 booleans to Alloy, given that
   * Alloy does not support booleans primitively.
   * 
   * Additional elements may be added to this file during the development of extensions to
   * this model transformation.
   */
  public CharSequence generateUtilsAlloyModel() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("module utils");
    _builder.newLine();
    _builder.newLine();
    _builder.append("enum Boolean {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("true, false");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("pred TRUE {}");
    _builder.newLine();
    _builder.append("pred FALSE { not TRUE }");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fun abs [self: Int] : Int {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("self < 0 implies negate[self] else self");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fun min [self, i: Int] : Int {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("let a = int[self], b = int[i] | a <= b implies a else b");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("fun max [self, i: Int] : Int {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("let a = int[self], b = int[i] | a <= b implies b else a");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Generates the Alloy counterpart of an ML2 Individual element.
   * 
   * @param individual the ML2 Individual element to be transformed.
   */
  protected CharSequence _generateAlloyElement(final Individual individual) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateAlloySingleton = this.generateAlloySingleton(individual);
    _builder.append(_generateAlloySingleton);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * Generates the Alloy counterpart of an ML2 Class element.
   * 
   * @param ml2class the ML2 Class element to be transformed.
   */
  protected CharSequence _generateAlloyElement(final br.ufes.inf.nemo.ml2.model.Class _class) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateAlloySignature = this.generateAlloySignature(_class);
    _builder.append(_generateAlloySignature);
    _builder.newLineIfNotEmpty();
    CharSequence _generateAlloySingleton = this.generateAlloySingleton(_class);
    _builder.append(_generateAlloySingleton);
    _builder.newLineIfNotEmpty();
    _builder.append("fact ");
    String _name = _class.getName();
    _builder.append(_name);
    _builder.append("ReifiedDefinition {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("all e: Entity | e in ");
    String _name_1 = _class.getName();
    _builder.append(_name_1, "\t");
    _builder.append("Reified iff (all e\': Entity | iof[e\',e] iff e\' in ");
    String _name_2 = _class.getName();
    _builder.append(_name_2, "\t");
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    CharSequence _generateProperSpecializationFact = this.generateProperSpecializationFact(_class);
    _builder.append(_generateProperSpecializationFact);
    _builder.newLineIfNotEmpty();
    CharSequence _generatePowertypeFact = this.generatePowertypeFact(_class);
    _builder.append(_generatePowertypeFact);
    _builder.newLineIfNotEmpty();
    CharSequence _generateCategorizationFact = this.generateCategorizationFact(_class);
    _builder.append(_generateCategorizationFact);
    _builder.newLineIfNotEmpty();
    CharSequence _generateSubordinationFact = this.generateSubordinationFact(_class);
    _builder.append(_generateSubordinationFact);
    _builder.newLineIfNotEmpty();
    {
      EList<br.ufes.inf.nemo.ml2.model.Class> _classifiers = _class.getClassifiers();
      for(final br.ufes.inf.nemo.ml2.model.Class classifier : _classifiers) {
        {
          if (((classifier instanceof HigherOrderClass) && (((HigherOrderClass) classifier).getCategorizedClass() != null))) {
            {
              EList<FeatureAssignment> _assignments = _class.getAssignments();
              for(final FeatureAssignment assignment : _assignments) {
                CharSequence _generateRegularityFeatureFact = this.generateRegularityFeatureFact(assignment, _class);
                _builder.append(_generateRegularityFeatureFact);
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  /**
   * Generates the Alloy counterpart of an ML2 Generalization Set element.
   * 
   * @param genset the ML2 Generalization Set element to be transformed.
   */
  protected CharSequence _generateAlloyElement(final GeneralizationSet genset) {
    CharSequence _xifexpression = null;
    boolean _isIsDisjoint = genset.isIsDisjoint();
    if (_isIsDisjoint) {
      CharSequence _xifexpression_1 = null;
      boolean _isIsComplete = genset.isIsComplete();
      if (_isIsComplete) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("fact DisjointCompleteGenSet");
        String _name = genset.getName();
        _builder.append(_name);
        _builder.append(" {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("disjoint[");
        final Function<br.ufes.inf.nemo.ml2.model.Class, String> _function = (br.ufes.inf.nemo.ml2.model.Class it) -> {
          return it.getName();
        };
        String _collect = genset.getSpecifics().stream().<String>map(_function).collect(Collectors.joining(","));
        _builder.append(_collect, "\t");
        _builder.append("]");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _name_1 = genset.getGeneral().getName();
        _builder.append(_name_1, "\t");
        _builder.append(" = ");
        final Function<br.ufes.inf.nemo.ml2.model.Class, String> _function_1 = (br.ufes.inf.nemo.ml2.model.Class it) -> {
          return it.getName();
        };
        String _collect_1 = genset.getSpecifics().stream().<String>map(_function_1).collect(Collectors.joining("+"));
        _builder.append(_collect_1, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
        _xifexpression_1 = _builder;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("fact DisjointGenSet");
        String _name_2 = genset.getName();
        _builder_1.append(_name_2);
        _builder_1.append(" {");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("\t");
        _builder_1.append("disjoint[");
        final Function<br.ufes.inf.nemo.ml2.model.Class, String> _function_2 = (br.ufes.inf.nemo.ml2.model.Class it) -> {
          return it.getName();
        };
        String _collect_2 = genset.getSpecifics().stream().<String>map(_function_2).collect(Collectors.joining(","));
        _builder_1.append(_collect_2, "\t");
        _builder_1.append("]");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("}");
        _builder_1.newLine();
        _builder_1.newLine();
        _xifexpression_1 = _builder_1;
      }
      _xifexpression = _xifexpression_1;
    } else {
      CharSequence _xifexpression_2 = null;
      boolean _isIsComplete_1 = genset.isIsComplete();
      if (_isIsComplete_1) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("fact CompleteGenSet");
        String _name_3 = genset.getName();
        _builder_2.append(_name_3);
        _builder_2.append(" {");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        String _name_4 = genset.getGeneral().getName();
        _builder_2.append(_name_4, "\t");
        _builder_2.append(" = ");
        final Function<br.ufes.inf.nemo.ml2.model.Class, String> _function_3 = (br.ufes.inf.nemo.ml2.model.Class it) -> {
          return it.getName();
        };
        String _collect_3 = genset.getSpecifics().stream().<String>map(_function_3).collect(Collectors.joining("+"));
        _builder_2.append(_collect_3, "\t");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("}");
        _builder_2.newLine();
        _builder_2.newLine();
        _xifexpression_2 = _builder_2;
      }
      _xifexpression = _xifexpression_2;
    }
    return _xifexpression;
  }
  
  /**
   * Generates the Alloy counterpart of an ML2 Constraint element.
   * (The implementation of this method can be found in br.ufes.inf.nemo.ml2.generator.OCLToAlloy)
   * 
   * @param constraint the ML2 Constraint element to be transformed.
   */
  protected CharSequence _generateAlloyElement(final Constraint constraint) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateAlloyConstraint = this._oCLToAlloy.generateAlloyConstraint(constraint);
    _builder.append(_generateAlloyConstraint);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * Generates an Alloy signature related to an ML2 First-Order Class element.
   * 
   * @param foclass the ML2 First-Order Class element to be transformed.
   */
  protected CharSequence _generateAlloySignature(final FirstOrderClass foclass) {
    CharSequence _switchResult = null;
    int _size = foclass.getSuperClasses().size();
    switch (_size) {
      case 0:
        StringConcatenation _builder = new StringConcatenation();
        {
          int _size_1 = foclass.getFeatures().size();
          boolean _equals = (_size_1 == 0);
          if (_equals) {
            _builder.append("sig ");
            String _name = foclass.getName();
            _builder.append(_name);
            _builder.append(" in Individual {}");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("sig ");
            String _name_1 = foclass.getName();
            _builder.append(_name_1);
            _builder.append(" in Individual {");
            _builder.newLineIfNotEmpty();
            {
              EList<Feature> _features = foclass.getFeatures();
              boolean _hasElements = false;
              for(final Feature feature : _features) {
                if (!_hasElements) {
                  _hasElements = true;
                } else {
                  _builder.appendImmediate(",", "\t");
                }
                _builder.append("\t");
                CharSequence _generateAlloySignatureFields = this.generateAlloySignatureFields(feature);
                _builder.append(_generateAlloySignatureFields, "\t");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("}");
            _builder.newLine();
          }
        }
        _builder.newLine();
        _switchResult = _builder;
        break;
      case 1:
        StringConcatenation _builder_1 = new StringConcatenation();
        {
          int _size_2 = foclass.getFeatures().size();
          boolean _equals_1 = (_size_2 == 0);
          if (_equals_1) {
            _builder_1.append("sig ");
            String _name_2 = foclass.getName();
            _builder_1.append(_name_2);
            _builder_1.append(" in ");
            String _name_3 = IterableExtensions.<br.ufes.inf.nemo.ml2.model.Class>head(foclass.getSuperClasses()).getName();
            _builder_1.append(_name_3);
            _builder_1.append(" {}");
            _builder_1.newLineIfNotEmpty();
          } else {
            _builder_1.append("sig ");
            String _name_4 = foclass.getName();
            _builder_1.append(_name_4);
            _builder_1.append(" in ");
            String _name_5 = IterableExtensions.<br.ufes.inf.nemo.ml2.model.Class>head(foclass.getSuperClasses()).getName();
            _builder_1.append(_name_5);
            _builder_1.append(" {");
            _builder_1.newLineIfNotEmpty();
            {
              EList<Feature> _features_1 = foclass.getFeatures();
              boolean _hasElements_1 = false;
              for(final Feature feature_1 : _features_1) {
                if (!_hasElements_1) {
                  _hasElements_1 = true;
                } else {
                  _builder_1.appendImmediate(",", "\t");
                }
                _builder_1.append("\t");
                CharSequence _generateAlloySignatureFields_1 = this.generateAlloySignatureFields(feature_1);
                _builder_1.append(_generateAlloySignatureFields_1, "\t");
                _builder_1.newLineIfNotEmpty();
              }
            }
            _builder_1.append("}");
            _builder_1.newLine();
          }
        }
        _builder_1.newLine();
        _switchResult = _builder_1;
        break;
      default:
        StringConcatenation _builder_2 = new StringConcatenation();
        {
          int _size_3 = foclass.getFeatures().size();
          boolean _equals_2 = (_size_3 == 0);
          if (_equals_2) {
            _builder_2.append("sig ");
            String _name_6 = foclass.getName();
            _builder_2.append(_name_6);
            _builder_2.append(" in Individual {}");
            _builder_2.newLineIfNotEmpty();
          } else {
            _builder_2.append("sig ");
            String _name_7 = foclass.getName();
            _builder_2.append(_name_7);
            _builder_2.append(" in Individual {");
            _builder_2.newLineIfNotEmpty();
            {
              EList<Feature> _features_2 = foclass.getFeatures();
              boolean _hasElements_2 = false;
              for(final Feature feature_2 : _features_2) {
                if (!_hasElements_2) {
                  _hasElements_2 = true;
                } else {
                  _builder_2.appendImmediate(",", "\t");
                }
                _builder_2.append("\t");
                CharSequence _generateAlloySignatureFields_2 = this.generateAlloySignatureFields(feature_2);
                _builder_2.append(_generateAlloySignatureFields_2, "\t");
                _builder_2.newLineIfNotEmpty();
              }
            }
            _builder_2.append("}");
            _builder_2.newLine();
          }
        }
        _builder_2.newLine();
        _builder_2.append("fact ");
        String _name_8 = foclass.getName();
        _builder_2.append(_name_8);
        _builder_2.append("SuperClasses {");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        _builder_2.append("all x: ");
        String _name_9 = foclass.getName();
        _builder_2.append(_name_9, "\t");
        _builder_2.append(" | x in (");
        final Function<br.ufes.inf.nemo.ml2.model.Class, String> _function = (br.ufes.inf.nemo.ml2.model.Class it) -> {
          return it.getName();
        };
        String _collect = foclass.getSuperClasses().stream().<String>map(_function).collect(Collectors.joining(" & "));
        _builder_2.append(_collect, "\t");
        _builder_2.append(")");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("}");
        _builder_2.newLine();
        _builder_2.newLine();
        _switchResult = _builder_2;
        break;
    }
    return _switchResult;
  }
  
  /**
   * Generates an Alloy signature related to an ML2 High-Order Class element.
   * 
   * @param hoclass the ML2 High-Order Class element to be transformed.
   */
  protected CharSequence _generateAlloySignature(final HighOrderClass hoclass) {
    CharSequence _switchResult = null;
    int _size = hoclass.getSuperClasses().size();
    switch (_size) {
      case 0:
        StringConcatenation _builder = new StringConcatenation();
        {
          int _size_1 = hoclass.getFeatures().size();
          boolean _equals = (_size_1 == 0);
          if (_equals) {
            _builder.append("sig ");
            String _name = hoclass.getName();
            _builder.append(_name);
            _builder.append(" in Order");
            int _order = hoclass.getOrder();
            int _minus = (_order - 1);
            _builder.append(_minus);
            _builder.append("Type {}");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("sig ");
            String _name_1 = hoclass.getName();
            _builder.append(_name_1);
            _builder.append(" in Order");
            int _order_1 = hoclass.getOrder();
            int _minus_1 = (_order_1 - 1);
            _builder.append(_minus_1);
            _builder.append("Type {");
            _builder.newLineIfNotEmpty();
            {
              EList<Feature> _features = hoclass.getFeatures();
              boolean _hasElements = false;
              for(final Feature feature : _features) {
                if (!_hasElements) {
                  _hasElements = true;
                } else {
                  _builder.appendImmediate(",", "\t");
                }
                _builder.append("\t");
                CharSequence _generateAlloySignatureFields = this.generateAlloySignatureFields(feature);
                _builder.append(_generateAlloySignatureFields, "\t");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("}");
            _builder.newLine();
          }
        }
        _builder.newLine();
        _switchResult = _builder;
        break;
      case 1:
        StringConcatenation _builder_1 = new StringConcatenation();
        {
          int _size_2 = hoclass.getFeatures().size();
          boolean _equals_1 = (_size_2 == 0);
          if (_equals_1) {
            _builder_1.append("sig ");
            String _name_2 = hoclass.getName();
            _builder_1.append(_name_2);
            _builder_1.append(" in ");
            String _name_3 = IterableExtensions.<br.ufes.inf.nemo.ml2.model.Class>head(hoclass.getSuperClasses()).getName();
            _builder_1.append(_name_3);
            _builder_1.append(" {}");
            _builder_1.newLineIfNotEmpty();
          } else {
            _builder_1.append("sig ");
            String _name_4 = hoclass.getName();
            _builder_1.append(_name_4);
            _builder_1.append(" in ");
            String _name_5 = IterableExtensions.<br.ufes.inf.nemo.ml2.model.Class>head(hoclass.getSuperClasses()).getName();
            _builder_1.append(_name_5);
            _builder_1.append(" {");
            _builder_1.newLineIfNotEmpty();
            {
              EList<Feature> _features_1 = hoclass.getFeatures();
              boolean _hasElements_1 = false;
              for(final Feature feature_1 : _features_1) {
                if (!_hasElements_1) {
                  _hasElements_1 = true;
                } else {
                  _builder_1.appendImmediate(",", "\t");
                }
                _builder_1.append("\t");
                CharSequence _generateAlloySignatureFields_1 = this.generateAlloySignatureFields(feature_1);
                _builder_1.append(_generateAlloySignatureFields_1, "\t");
                _builder_1.newLineIfNotEmpty();
              }
            }
            _builder_1.append("}");
            _builder_1.newLine();
          }
        }
        _builder_1.newLine();
        _switchResult = _builder_1;
        break;
      default:
        StringConcatenation _builder_2 = new StringConcatenation();
        {
          int _size_3 = hoclass.getFeatures().size();
          boolean _equals_2 = (_size_3 == 0);
          if (_equals_2) {
            _builder_2.append("sig ");
            String _name_6 = hoclass.getName();
            _builder_2.append(_name_6);
            _builder_2.append(" in Order");
            int _order_2 = hoclass.getOrder();
            int _minus_2 = (_order_2 - 1);
            _builder_2.append(_minus_2);
            _builder_2.append("Type {}");
            _builder_2.newLineIfNotEmpty();
          } else {
            _builder_2.append("sig ");
            String _name_7 = hoclass.getName();
            _builder_2.append(_name_7);
            _builder_2.append(" in Order");
            int _order_3 = hoclass.getOrder();
            int _minus_3 = (_order_3 - 1);
            _builder_2.append(_minus_3);
            _builder_2.append("Type {");
            _builder_2.newLineIfNotEmpty();
            {
              EList<Feature> _features_2 = hoclass.getFeatures();
              boolean _hasElements_2 = false;
              for(final Feature feature_2 : _features_2) {
                if (!_hasElements_2) {
                  _hasElements_2 = true;
                } else {
                  _builder_2.appendImmediate(",", "\t");
                }
                _builder_2.append("\t");
                CharSequence _generateAlloySignatureFields_2 = this.generateAlloySignatureFields(feature_2);
                _builder_2.append(_generateAlloySignatureFields_2, "\t");
                _builder_2.newLineIfNotEmpty();
              }
            }
            _builder_2.append("}");
            _builder_2.newLine();
          }
        }
        _builder_2.newLine();
        _builder_2.append("fact ");
        String _name_8 = hoclass.getName();
        _builder_2.append(_name_8);
        _builder_2.append("SuperClasses {");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        _builder_2.append("all x: ");
        String _name_9 = hoclass.getName();
        _builder_2.append(_name_9, "\t");
        _builder_2.append(" | x in (");
        final Function<br.ufes.inf.nemo.ml2.model.Class, String> _function = (br.ufes.inf.nemo.ml2.model.Class it) -> {
          return it.getName();
        };
        String _collect = hoclass.getSuperClasses().stream().<String>map(_function).collect(Collectors.joining(" & "));
        _builder_2.append(_collect, "\t");
        _builder_2.append(")");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("}");
        _builder_2.newLine();
        _builder_2.newLine();
        _switchResult = _builder_2;
        break;
    }
    return _switchResult;
  }
  
  /**
   * Generates an Alloy signature related to an ML2 Orderless Class element.
   * 
   * @param olclass the ML2 Orderless Class element to be transformed.
   */
  protected CharSequence _generateAlloySignature(final OrderlessClass olclass) {
    StringConcatenation _builder = new StringConcatenation();
    {
      int _size = olclass.getFeatures().size();
      boolean _equals = (_size == 0);
      if (_equals) {
        _builder.append("sig ");
        String _name = olclass.getName();
        _builder.append(_name);
        _builder.append(" in OrderlessType {}");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("sig ");
        String _name_1 = olclass.getName();
        _builder.append(_name_1);
        _builder.append(" in OrderlessType {");
        _builder.newLineIfNotEmpty();
        {
          EList<Feature> _features = olclass.getFeatures();
          boolean _hasElements = false;
          for(final Feature feature : _features) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "\t");
            }
            _builder.append("\t");
            CharSequence _generateAlloySignatureFields = this.generateAlloySignatureFields(feature);
            _builder.append(_generateAlloySignatureFields, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Generates an Alloy signature field related to an ML2 Attribute element.
   * 
   * @param attribute the ML2 Attribute element to be transformed.
   */
  protected CharSequence _generateAlloySignatureFields(final Attribute attribute) {
    CharSequence _xifexpression = null;
    DataType __type = attribute.get_type();
    boolean _tripleNotEquals = (__type != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder = new StringConcatenation();
      String _name = attribute.getName();
      _builder.append(_name);
      _builder.append(": ");
      String _decideMultiplicityKeyword = this.decideMultiplicityKeyword(attribute);
      _builder.append(_decideMultiplicityKeyword);
      String _name_1 = attribute.get_type().getName();
      _builder.append(_name_1);
      _builder.newLineIfNotEmpty();
      _xifexpression = _builder;
    } else {
      CharSequence _switchResult = null;
      PrimitiveType _primitiveType = attribute.getPrimitiveType();
      if (_primitiveType != null) {
        switch (_primitiveType) {
          case BOOLEAN:
            StringConcatenation _builder_1 = new StringConcatenation();
            String _name_2 = attribute.getName();
            _builder_1.append(_name_2);
            _builder_1.append(": ");
            String _decideMultiplicityKeyword_1 = this.decideMultiplicityKeyword(attribute);
            _builder_1.append(_decideMultiplicityKeyword_1);
            _builder_1.append("Boolean");
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
            break;
          case NUMBER:
            StringConcatenation _builder_2 = new StringConcatenation();
            String _name_3 = attribute.getName();
            _builder_2.append(_name_3);
            _builder_2.append(": ");
            String _decideMultiplicityKeyword_2 = this.decideMultiplicityKeyword(attribute);
            _builder_2.append(_decideMultiplicityKeyword_2);
            _builder_2.append("Int");
            _builder_2.newLineIfNotEmpty();
            _switchResult = _builder_2;
            break;
          case STRING:
            StringConcatenation _builder_3 = new StringConcatenation();
            String _name_4 = attribute.getName();
            _builder_3.append(_name_4);
            _builder_3.append(": ");
            String _decideMultiplicityKeyword_3 = this.decideMultiplicityKeyword(attribute);
            _builder_3.append(_decideMultiplicityKeyword_3);
            _builder_3.append("String");
            _builder_3.newLineIfNotEmpty();
            _switchResult = _builder_3;
            break;
          default:
            break;
        }
      }
      _xifexpression = _switchResult;
    }
    return _xifexpression;
  }
  
  /**
   * Generates an Alloy signature field related to an ML2 Reference element.
   * 
   * @param reference the ML2 Reference element to be transformed.
   */
  protected CharSequence _generateAlloySignatureFields(final Reference reference) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = reference.getName();
    _builder.append(_name);
    _builder.append(": ");
    String _decideMultiplicityKeyword = this.decideMultiplicityKeyword(reference);
    _builder.append(_decideMultiplicityKeyword);
    String _name_1 = reference.get_type().getName();
    _builder.append(_name_1);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * Generates an Alloy singleton related to an ML2 Individual element.
   * 
   * @param individual the ML2 Individual element to be transformed.
   */
  protected CharSequence _generateAlloySingleton(final Individual individual) {
    CharSequence _switchResult = null;
    int _size = individual.getClassifiers().size();
    switch (_size) {
      case 1:
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("one sig ");
        String _name = individual.getName();
        _builder.append(_name);
        _builder.append(" in ");
        String _name_1 = IterableExtensions.<br.ufes.inf.nemo.ml2.model.Class>head(individual.getClassifiers()).getName();
        _builder.append(_name_1);
        _builder.append(" {}");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        CharSequence _generateAlloySingletonAssignmentsFact = this.generateAlloySingletonAssignmentsFact(individual);
        _builder.append(_generateAlloySingletonAssignmentsFact);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
        break;
      default:
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("one sig ");
        String _name_2 = individual.getName();
        _builder_1.append(_name_2);
        _builder_1.append(" in Individual {}");
        _builder_1.newLineIfNotEmpty();
        _builder_1.newLine();
        CharSequence _generateAlloySingletonAssignmentsFact_1 = this.generateAlloySingletonAssignmentsFact(individual);
        _builder_1.append(_generateAlloySingletonAssignmentsFact_1);
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("fact ");
        String _name_3 = individual.getName();
        _builder_1.append(_name_3);
        _builder_1.append("InstantiatedClasses {");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("\t");
        String _name_4 = individual.getName();
        _builder_1.append(_name_4, "\t");
        _builder_1.append(" in (");
        final Function<br.ufes.inf.nemo.ml2.model.Class, String> _function = (br.ufes.inf.nemo.ml2.model.Class it) -> {
          return it.getName();
        };
        String _collect = individual.getClassifiers().stream().<String>map(_function).collect(Collectors.joining(" & "));
        _builder_1.append(_collect, "\t");
        _builder_1.append(")");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("}");
        _builder_1.newLine();
        _builder_1.newLine();
        _switchResult = _builder_1;
        break;
    }
    return _switchResult;
  }
  
  /**
   * Generates an Alloy singleton related to an ML2 First-Order Class element.
   * 
   * @param foclass the ML2 First-Order Class element to be transformed.
   */
  protected CharSequence _generateAlloySingleton(final FirstOrderClass foclass) {
    CharSequence _switchResult = null;
    int _size = foclass.getClassifiers().size();
    switch (_size) {
      case 0:
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("one sig ");
        String _name = foclass.getName();
        _builder.append(_name);
        _builder.append("Reified in Order1Type {}");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        CharSequence _generateAlloySingletonAssignmentsFact = this.generateAlloySingletonAssignmentsFact(foclass);
        _builder.append(_generateAlloySingletonAssignmentsFact);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
        break;
      case 1:
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("one sig ");
        String _name_1 = foclass.getName();
        _builder_1.append(_name_1);
        _builder_1.append("Reified in ");
        String _name_2 = IterableExtensions.<br.ufes.inf.nemo.ml2.model.Class>head(foclass.getClassifiers()).getName();
        _builder_1.append(_name_2);
        _builder_1.append(" {}");
        _builder_1.newLineIfNotEmpty();
        _builder_1.newLine();
        CharSequence _generateAlloySingletonAssignmentsFact_1 = this.generateAlloySingletonAssignmentsFact(foclass);
        _builder_1.append(_generateAlloySingletonAssignmentsFact_1);
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
        break;
      default:
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("one sig ");
        String _name_3 = foclass.getName();
        _builder_2.append(_name_3);
        _builder_2.append("Reified in Order1Type{}");
        _builder_2.newLineIfNotEmpty();
        _builder_2.newLine();
        CharSequence _generateAlloySingletonAssignmentsFact_2 = this.generateAlloySingletonAssignmentsFact(foclass);
        _builder_2.append(_generateAlloySingletonAssignmentsFact_2);
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("fact ");
        String _name_4 = foclass.getName();
        _builder_2.append(_name_4);
        _builder_2.append("InstantiatedClasses {");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        _builder_2.append("all x: ");
        String _name_5 = foclass.getName();
        _builder_2.append(_name_5, "\t");
        _builder_2.append("Reified | x in (");
        final Function<br.ufes.inf.nemo.ml2.model.Class, String> _function = (br.ufes.inf.nemo.ml2.model.Class it) -> {
          return it.getName();
        };
        String _collect = foclass.getClassifiers().stream().<String>map(_function).collect(Collectors.joining(" & "));
        _builder_2.append(_collect, "\t");
        _builder_2.append(")");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("}");
        _builder_2.newLine();
        _builder_2.newLine();
        _switchResult = _builder_2;
        break;
    }
    return _switchResult;
  }
  
  /**
   * Generates an Alloy singleton related to an ML2 High-Order Class element.
   * 
   * @param hoclass the ML2 High-Order Class element to be transformed.
   */
  protected CharSequence _generateAlloySingleton(final HighOrderClass hoclass) {
    CharSequence _switchResult = null;
    int _size = hoclass.getClassifiers().size();
    switch (_size) {
      case 0:
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("one sig ");
        String _name = hoclass.getName();
        _builder.append(_name);
        _builder.append("Reified in Order");
        int _order = hoclass.getOrder();
        _builder.append(_order);
        _builder.append("Type {}");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        CharSequence _generateAlloySingletonAssignmentsFact = this.generateAlloySingletonAssignmentsFact(hoclass);
        _builder.append(_generateAlloySingletonAssignmentsFact);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
        break;
      case 1:
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("one sig ");
        String _name_1 = hoclass.getName();
        _builder_1.append(_name_1);
        _builder_1.append("Reified in ");
        String _name_2 = IterableExtensions.<br.ufes.inf.nemo.ml2.model.Class>head(hoclass.getClassifiers()).getName();
        _builder_1.append(_name_2);
        _builder_1.append(" {}");
        _builder_1.newLineIfNotEmpty();
        _builder_1.newLine();
        CharSequence _generateAlloySingletonAssignmentsFact_1 = this.generateAlloySingletonAssignmentsFact(hoclass);
        _builder_1.append(_generateAlloySingletonAssignmentsFact_1);
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
        break;
      default:
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("one sig ");
        String _name_3 = hoclass.getName();
        _builder_2.append(_name_3);
        _builder_2.append("Reified in Order");
        int _order_1 = hoclass.getOrder();
        _builder_2.append(_order_1);
        _builder_2.append("Type {}");
        _builder_2.newLineIfNotEmpty();
        _builder_2.newLine();
        CharSequence _generateAlloySingletonAssignmentsFact_2 = this.generateAlloySingletonAssignmentsFact(hoclass);
        _builder_2.append(_generateAlloySingletonAssignmentsFact_2);
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("fact ");
        String _name_4 = hoclass.getName();
        _builder_2.append(_name_4);
        _builder_2.append("InstantiatedClasses {");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        _builder_2.append("all x: ");
        String _name_5 = hoclass.getName();
        _builder_2.append(_name_5, "\t");
        _builder_2.append("Reified | x in (");
        final Function<br.ufes.inf.nemo.ml2.model.Class, String> _function = (br.ufes.inf.nemo.ml2.model.Class it) -> {
          return it.getName();
        };
        String _collect = hoclass.getClassifiers().stream().<String>map(_function).collect(Collectors.joining(" & "));
        _builder_2.append(_collect, "\t");
        _builder_2.append(")");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("}");
        _builder_2.newLine();
        _builder_2.newLine();
        _switchResult = _builder_2;
        break;
    }
    return _switchResult;
  }
  
  /**
   * Generates an Alloy singleton related to an ML2 Orderless Class element.
   * 
   * @param olclass the ML2 Orderless Class element to be transformed.
   */
  protected CharSequence _generateAlloySingleton(final OrderlessClass olclass) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("one sig ");
    String _name = olclass.getName();
    _builder.append(_name);
    _builder.append("Reified in OrderlessType {}");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateAlloySingletonAssignmentsFact = this.generateAlloySingletonAssignmentsFact(olclass);
    _builder.append(_generateAlloySingletonAssignmentsFact);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * Generates an Alloy fact related to an ML2 Individual element's assignments.
   * 
   * @param individual the ML2 Individual element to be considered.
   */
  protected CharSequence _generateAlloySingletonAssignmentsFact(final Individual individual) {
    CharSequence _xifexpression = null;
    int _size = individual.getAssignments().size();
    boolean _notEquals = (_size != 0);
    if (_notEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("fact {");
      _builder.newLine();
      {
        EList<FeatureAssignment> _assignments = individual.getAssignments();
        for(final FeatureAssignment assignment : _assignments) {
          _builder.append("\t");
          String _name = individual.getName();
          _builder.append(_name, "\t");
          _builder.append(".");
          CharSequence _generateAlloySingletonAssignment = this.generateAlloySingletonAssignment(assignment);
          _builder.append(_generateAlloySingletonAssignment, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _xifexpression = _builder;
    }
    return _xifexpression;
  }
  
  /**
   * Generates an Alloy fact related to an ML2 Class element's assignments.
   * 
   * @param ml2class the ML2 Class element to be considered.
   */
  protected CharSequence _generateAlloySingletonAssignmentsFact(final br.ufes.inf.nemo.ml2.model.Class ml2class) {
    CharSequence _xifexpression = null;
    int _size = ml2class.getAssignments().size();
    boolean _notEquals = (_size != 0);
    if (_notEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("fact {");
      _builder.newLine();
      {
        EList<FeatureAssignment> _assignments = ml2class.getAssignments();
        for(final FeatureAssignment assignment : _assignments) {
          _builder.append("\t");
          String _name = ml2class.getName();
          _builder.append(_name, "\t");
          _builder.append("Reified.");
          CharSequence _generateAlloySingletonAssignment = this.generateAlloySingletonAssignment(assignment);
          _builder.append(_generateAlloySingletonAssignment, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _xifexpression = _builder;
    }
    return _xifexpression;
  }
  
  /**
   * Generates an Alloy singleton assignment related to an ML2 AttributeAssignment element.
   * 
   * @param attributeAssignment the ML2 AttributeAssignment element to be transformed.
   */
  protected CharSequence _generateAlloySingletonAssignment(final AttributeAssignment attributeAssignment) {
    CharSequence _xifexpression = null;
    int _size = attributeAssignment.getDatatypeValues().size();
    boolean _notEquals = (_size != 0);
    if (_notEquals) {
      StringConcatenation _builder = new StringConcatenation();
      String _name = attributeAssignment.getAttribute().getName();
      _builder.append(_name);
      _builder.append(" = ");
      final Function<Individual, String> _function = (Individual it) -> {
        return it.getName();
      };
      String _collect = attributeAssignment.getDatatypeValues().stream().<String>map(_function).collect(Collectors.joining(" + "));
      _builder.append(_collect);
      _builder.newLineIfNotEmpty();
      _xifexpression = _builder;
    } else {
      CharSequence _switchResult = null;
      PrimitiveType _primitiveType = attributeAssignment.getAttribute().getPrimitiveType();
      if (_primitiveType != null) {
        switch (_primitiveType) {
          case BOOLEAN:
            StringConcatenation _builder_1 = new StringConcatenation();
            String _name_1 = attributeAssignment.getAttribute().getName();
            _builder_1.append(_name_1);
            _builder_1.append(" = ");
            final Function<Boolean, String> _function_1 = (Boolean it) -> {
              return it.toString();
            };
            String _collect_1 = attributeAssignment.getBooleanValues().stream().<String>map(_function_1).collect(Collectors.joining(" + "));
            _builder_1.append(_collect_1);
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
            break;
          case NUMBER:
            StringConcatenation _builder_2 = new StringConcatenation();
            String _name_2 = attributeAssignment.getAttribute().getName();
            _builder_2.append(_name_2);
            _builder_2.append(" = ");
            final Function<Double, String> _function_2 = (Double it) -> {
              return Integer.valueOf(it.intValue()).toString();
            };
            String _collect_2 = attributeAssignment.getNumberValues().stream().<String>map(_function_2).collect(Collectors.joining(" + "));
            _builder_2.append(_collect_2);
            _builder_2.newLineIfNotEmpty();
            _switchResult = _builder_2;
            break;
          case STRING:
            StringConcatenation _builder_3 = new StringConcatenation();
            String _name_3 = attributeAssignment.getAttribute().getName();
            _builder_3.append(_name_3);
            _builder_3.append(" = ");
            final Function<String, String> _function_3 = (String it) -> {
              return (("\"" + it) + "\"");
            };
            String _collect_3 = attributeAssignment.getStringValues().stream().<String>map(_function_3).collect(Collectors.joining(" + "));
            _builder_3.append(_collect_3);
            _builder_3.newLineIfNotEmpty();
            _switchResult = _builder_3;
            break;
          default:
            break;
        }
      }
      _xifexpression = _switchResult;
    }
    return _xifexpression;
  }
  
  /**
   * Generates an Alloy singleton assignment related to an ML2 ReferenceAssignment element.
   * 
   * @param referenceAssignment the ML2 ReferenceAssignment element to be transformed.
   */
  protected CharSequence _generateAlloySingletonAssignment(final ReferenceAssignment referenceAssignment) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = referenceAssignment.getReference().getName();
    _builder.append(_name);
    _builder.append(" = ");
    final Function<EntityDeclaration, String> _function = (EntityDeclaration it) -> {
      return it.getName();
    };
    String _collect = referenceAssignment.getValues().stream().<String>map(_function).collect(Collectors.joining(" + "));
    _builder.append(_collect);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * Generates an Alloy fact related to an ML2 Proper Specialization relation of a First-Order Class element.
   * 
   * @param foclass the ML2 First-Order Class element to be considered.
   */
  protected CharSequence _generateProperSpecializationFact(final FirstOrderClass foclass) {
    CharSequence _xifexpression = null;
    int _size = foclass.getSuperClasses().size();
    boolean _equals = (_size == 0);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("fact ");
      String _name = foclass.getName();
      _builder.append(_name);
      _builder.append("ReifiedProperSpecialization {");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("properSpecializes[");
      String _name_1 = foclass.getName();
      _builder.append(_name_1, "\t");
      _builder.append("Reified,Individual_]");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("fact ");
      String _name_2 = foclass.getName();
      _builder_1.append(_name_2);
      _builder_1.append("ReifiedProperSpecialization {");
      _builder_1.newLineIfNotEmpty();
      {
        EList<br.ufes.inf.nemo.ml2.model.Class> _superClasses = foclass.getSuperClasses();
        for(final br.ufes.inf.nemo.ml2.model.Class superClass : _superClasses) {
          _builder_1.append("\t");
          _builder_1.append("properSpecializes[");
          String _name_3 = foclass.getName();
          _builder_1.append(_name_3, "\t");
          _builder_1.append("Reified,");
          String _name_4 = superClass.getName();
          _builder_1.append(_name_4, "\t");
          _builder_1.append("Reified]");
          _builder_1.newLineIfNotEmpty();
        }
      }
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  /**
   * Generates an Alloy fact related to an ML2 Proper Specialization relation of a High-Order Class element.
   * 
   * @param hoclass the ML2 High-Order Class element to be considered.
   */
  protected CharSequence _generateProperSpecializationFact(final HighOrderClass hoclass) {
    CharSequence _xifexpression = null;
    int _size = hoclass.getSuperClasses().size();
    boolean _equals = (_size == 0);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("fact ");
      String _name = hoclass.getName();
      _builder.append(_name);
      _builder.append("ReifiedProperSpecialization {");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("properSpecializes[");
      String _name_1 = hoclass.getName();
      _builder.append(_name_1, "\t");
      _builder.append("Reified,Order");
      int _order = hoclass.getOrder();
      int _minus = (_order - 1);
      _builder.append(_minus, "\t");
      _builder.append("TypeReified]");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("fact ");
      String _name_2 = hoclass.getName();
      _builder_1.append(_name_2);
      _builder_1.append("ReifiedProperSpecialization {");
      _builder_1.newLineIfNotEmpty();
      {
        EList<br.ufes.inf.nemo.ml2.model.Class> _superClasses = hoclass.getSuperClasses();
        for(final br.ufes.inf.nemo.ml2.model.Class superClass : _superClasses) {
          _builder_1.append("\t");
          _builder_1.append("properSpecializes[");
          String _name_3 = hoclass.getName();
          _builder_1.append(_name_3, "\t");
          _builder_1.append("Reified,");
          String _name_4 = superClass.getName();
          _builder_1.append(_name_4, "\t");
          _builder_1.append("Reified]");
          _builder_1.newLineIfNotEmpty();
        }
      }
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  /**
   * Generates an Alloy fact related to an ML2 Proper Specialization relation of a Orderless Class element.
   * 
   * @param olclass the ML2 Orderless Class element to be considered.
   */
  protected CharSequence _generateProperSpecializationFact(final OrderlessClass olclass) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fact ");
    String _name = olclass.getName();
    _builder.append(_name);
    _builder.append("ReifiedProperSpecialization {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("properSpecializes[");
    String _name_1 = olclass.getName();
    _builder.append(_name_1, "\t");
    _builder.append("Reified,OrderlessType_]");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Generates an Alloy fact related to an ML2 AttributeAssignment element regulated by an ML2 Attribute element.
   * 
   * @param attributeAssignment the ML2 AttributeAssignment element with regulated feature.
   * @param ml2class the ML2 Class element with regulator feature.
   */
  protected CharSequence _generateRegularityFeatureFact(final AttributeAssignment attributeAssignment, final br.ufes.inf.nemo.ml2.model.Class ml2class) {
    CharSequence _xifexpression = null;
    Attribute _attribute = attributeAssignment.getAttribute();
    if ((_attribute instanceof RegularityAttribute)) {
      CharSequence _xblockexpression = null;
      {
        Attribute _attribute_1 = attributeAssignment.getAttribute();
        final RegularityAttribute regAtt = ((RegularityAttribute) _attribute_1);
        CharSequence _switchResult = null;
        RegularityFeatureType _regularityType = regAtt.getRegularityType();
        if (_regularityType != null) {
          switch (_regularityType) {
            case DETERMINES_VALUE:
              StringConcatenation _builder = new StringConcatenation();
              _builder.append("fact ");
              String _name = regAtt.getName();
              _builder.append(_name);
              _builder.append("Regulates");
              String _name_1 = regAtt.getRegulates().getName();
              _builder.append(_name_1);
              _builder.append(" {");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("all x: ");
              String _name_2 = ml2class.getName();
              _builder.append(_name_2, "\t");
              _builder.append(" | x.");
              String _name_3 = regAtt.getRegulates().getName();
              _builder.append(_name_3, "\t");
              _builder.append(" = ");
              String _name_4 = ml2class.getName();
              _builder.append(_name_4, "\t");
              _builder.append("Reified.");
              String _name_5 = attributeAssignment.getAttribute().getName();
              _builder.append(_name_5, "\t");
              _builder.newLineIfNotEmpty();
              _builder.append("}");
              _builder.newLine();
              _builder.newLine();
              _switchResult = _builder;
              break;
            case DETERMINES_MIN_VALUE:
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append("fact ");
              String _name_6 = regAtt.getName();
              _builder_1.append(_name_6);
              _builder_1.append("Regulates");
              String _name_7 = regAtt.getRegulates().getName();
              _builder_1.append(_name_7);
              _builder_1.append(" {");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("\t");
              _builder_1.append("all x: ");
              String _name_8 = ml2class.getName();
              _builder_1.append(_name_8, "\t");
              _builder_1.append(" | x.");
              String _name_9 = regAtt.getRegulates().getName();
              _builder_1.append(_name_9, "\t");
              _builder_1.append(" >= ");
              String _name_10 = ml2class.getName();
              _builder_1.append(_name_10, "\t");
              _builder_1.append("Reified.");
              String _name_11 = attributeAssignment.getAttribute().getName();
              _builder_1.append(_name_11, "\t");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("}");
              _builder_1.newLine();
              _builder_1.newLine();
              _switchResult = _builder_1;
              break;
            case DETERMINES_MAX_VALUE:
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("fact ");
              String _name_12 = regAtt.getName();
              _builder_2.append(_name_12);
              _builder_2.append("Regulates");
              String _name_13 = regAtt.getRegulates().getName();
              _builder_2.append(_name_13);
              _builder_2.append(" {");
              _builder_2.newLineIfNotEmpty();
              _builder_2.append("\t");
              _builder_2.append("all x: ");
              String _name_14 = ml2class.getName();
              _builder_2.append(_name_14, "\t");
              _builder_2.append(" | x.");
              String _name_15 = regAtt.getRegulates().getName();
              _builder_2.append(_name_15, "\t");
              _builder_2.append(" <= ");
              String _name_16 = ml2class.getName();
              _builder_2.append(_name_16, "\t");
              _builder_2.append("Reified.");
              String _name_17 = attributeAssignment.getAttribute().getName();
              _builder_2.append(_name_17, "\t");
              _builder_2.newLineIfNotEmpty();
              _builder_2.append("}");
              _builder_2.newLine();
              _builder_2.newLine();
              _switchResult = _builder_2;
              break;
            default:
              StringConcatenation _builder_3 = new StringConcatenation();
              _switchResult = _builder_3;
              break;
          }
        } else {
          StringConcatenation _builder_3 = new StringConcatenation();
          _switchResult = _builder_3;
        }
        _xblockexpression = _switchResult;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  /**
   * Generates an Alloy fact related to an ML2 ReferenceAssignment element regulated by an ML2 Reference element.
   * 
   * @param referenceAssignment the ML2 ReferenceAssignment element with regulated feature.
   * @param ml2class the ML2 Class element with regulator feature.
   */
  protected CharSequence _generateRegularityFeatureFact(final ReferenceAssignment referenceAssignment, final br.ufes.inf.nemo.ml2.model.Class ml2class) {
    CharSequence _xifexpression = null;
    Reference _reference = referenceAssignment.getReference();
    Reference _regulates = ((RegularityReference) _reference).getRegulates();
    boolean _tripleNotEquals = (_regulates != null);
    if (_tripleNotEquals) {
      CharSequence _xblockexpression = null;
      {
        Reference _reference_1 = referenceAssignment.getReference();
        final RegularityReference regRef = ((RegularityReference) _reference_1);
        CharSequence _switchResult = null;
        RegularityFeatureType _regularityType = regRef.getRegularityType();
        if (_regularityType != null) {
          switch (_regularityType) {
            case DETERMINES_TYPE:
              StringConcatenation _builder = new StringConcatenation();
              _builder.append("fact ");
              String _name = regRef.getName();
              _builder.append(_name);
              _builder.append("Regulates");
              String _name_1 = regRef.getRegulates().getName();
              _builder.append(_name_1);
              _builder.append(" {");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("all x: ");
              String _name_2 = ml2class.getName();
              _builder.append(_name_2, "\t");
              _builder.append(" | x.");
              String _name_3 = regRef.getRegulates().getName();
              _builder.append(_name_3, "\t");
              _builder.append(" = ");
              String _name_4 = ml2class.getName();
              _builder.append(_name_4, "\t");
              _builder.append("Reified.");
              String _name_5 = regRef.getName();
              _builder.append(_name_5, "\t");
              _builder.newLineIfNotEmpty();
              _builder.append("}");
              _builder.newLine();
              _builder.newLine();
              _switchResult = _builder;
              break;
            default:
              StringConcatenation _builder_1 = new StringConcatenation();
              _switchResult = _builder_1;
              break;
          }
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _switchResult = _builder_1;
        }
        _xblockexpression = _switchResult;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  /**
   * Generates an Alloy fact related to an ML2 Powertype cross-level relation.
   * 
   * @param ml2class the ML2 Class element to be considered.
   */
  public CharSequence generatePowertypeFact(final br.ufes.inf.nemo.ml2.model.Class _class) {
    CharSequence _xifexpression = null;
    if ((_class instanceof HigherOrderClass)) {
      CharSequence _xifexpression_1 = null;
      br.ufes.inf.nemo.ml2.model.Class _powertypeOf = ((HigherOrderClass)_class).getPowertypeOf();
      boolean _tripleNotEquals = (_powertypeOf != null);
      if (_tripleNotEquals) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("fact ");
        String _name = ((HigherOrderClass)_class).getName();
        _builder.append(_name);
        _builder.append("Powertype {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("powertypeOf[");
        String _name_1 = ((HigherOrderClass)_class).getName();
        _builder.append(_name_1, "\t");
        _builder.append("Reified,");
        String _name_2 = ((HigherOrderClass)_class).getPowertypeOf().getName();
        _builder.append(_name_2, "\t");
        _builder.append("Reified]");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
        _xifexpression_1 = _builder;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  /**
   * Generates an Alloy fact related to an ML2 Subordination cross-level relation.
   * 
   * @param ml2class the ML2 Class element to be considered.
   */
  public CharSequence generateSubordinationFact(final br.ufes.inf.nemo.ml2.model.Class _class) {
    CharSequence _xifexpression = null;
    if ((_class instanceof HigherOrderClass)) {
      CharSequence _xifexpression_1 = null;
      int _size = ((HigherOrderClass)_class).getSubordinators().size();
      boolean _notEquals = (_size != 0);
      if (_notEquals) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("fact ");
        String _name = ((HigherOrderClass)_class).getName();
        _builder.append(_name);
        _builder.append("Subordination {");
        _builder.newLineIfNotEmpty();
        {
          EList<HigherOrderClass> _subordinators = ((HigherOrderClass)_class).getSubordinators();
          for(final HigherOrderClass subordinator : _subordinators) {
            _builder.append("\t");
            _builder.append("isSubordinatedTo[");
            String _name_1 = ((HigherOrderClass)_class).getName();
            _builder.append(_name_1, "\t");
            _builder.append("Reified,");
            String _name_2 = subordinator.getName();
            _builder.append(_name_2, "\t");
            _builder.append("Reified]");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
        _xifexpression_1 = _builder;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  /**
   * Generates an Alloy fact related to an ML2 Categorization cross-level relation.
   * 
   * @param ml2class the ML2 Class element to be considered.
   */
  public CharSequence generateCategorizationFact(final br.ufes.inf.nemo.ml2.model.Class _class) {
    CharSequence _xifexpression = null;
    if ((_class instanceof HigherOrderClass)) {
      CharSequence _xifexpression_1 = null;
      br.ufes.inf.nemo.ml2.model.Class _categorizedClass = ((HigherOrderClass)_class).getCategorizedClass();
      boolean _tripleNotEquals = (_categorizedClass != null);
      if (_tripleNotEquals) {
        CharSequence _switchResult = null;
        CategorizationType _categorizationType = ((HigherOrderClass)_class).getCategorizationType();
        if (_categorizationType != null) {
          switch (_categorizationType) {
            case CATEGORIZER:
              StringConcatenation _builder = new StringConcatenation();
              _builder.append("fact ");
              String _name = ((HigherOrderClass)_class).getName();
              _builder.append(_name);
              _builder.append("Categorization {");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("categorizes[");
              String _name_1 = ((HigherOrderClass)_class).getName();
              _builder.append(_name_1, "\t");
              _builder.append("Reified,");
              String _name_2 = ((HigherOrderClass)_class).getCategorizedClass().getName();
              _builder.append(_name_2, "\t");
              _builder.append("Reified]");
              _builder.newLineIfNotEmpty();
              _builder.append("}");
              _builder.newLine();
              _builder.newLine();
              _switchResult = _builder;
              break;
            case COMPLETE_CATEGORIZER:
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append("fact ");
              String _name_3 = ((HigherOrderClass)_class).getName();
              _builder_1.append(_name_3);
              _builder_1.append("CompleteCategorization {");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("\t");
              _builder_1.append("compCategorizes[");
              String _name_4 = ((HigherOrderClass)_class).getName();
              _builder_1.append(_name_4, "\t");
              _builder_1.append("Reified,");
              String _name_5 = ((HigherOrderClass)_class).getCategorizedClass().getName();
              _builder_1.append(_name_5, "\t");
              _builder_1.append("Reified]");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("}");
              _builder_1.newLine();
              _builder_1.newLine();
              _switchResult = _builder_1;
              break;
            case DISJOINT_CATEGORIZER:
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("fact ");
              String _name_6 = ((HigherOrderClass)_class).getName();
              _builder_2.append(_name_6);
              _builder_2.append("DisjointCategorization {");
              _builder_2.newLineIfNotEmpty();
              _builder_2.append("\t");
              _builder_2.append("disjCategorizes[");
              String _name_7 = ((HigherOrderClass)_class).getName();
              _builder_2.append(_name_7, "\t");
              _builder_2.append("Reified,");
              String _name_8 = ((HigherOrderClass)_class).getCategorizedClass().getName();
              _builder_2.append(_name_8, "\t");
              _builder_2.append("Reified]");
              _builder_2.newLineIfNotEmpty();
              _builder_2.append("}");
              _builder_2.newLine();
              _builder_2.newLine();
              _switchResult = _builder_2;
              break;
            case PARTITIONER:
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append("fact ");
              String _name_9 = ((HigherOrderClass)_class).getName();
              _builder_3.append(_name_9);
              _builder_3.append("Partition {");
              _builder_3.newLineIfNotEmpty();
              _builder_3.append("\t");
              _builder_3.append("partitions[");
              String _name_10 = ((HigherOrderClass)_class).getName();
              _builder_3.append(_name_10, "\t");
              _builder_3.append("Reified,");
              String _name_11 = ((HigherOrderClass)_class).getCategorizedClass().getName();
              _builder_3.append(_name_11, "\t");
              _builder_3.append("Reified]");
              _builder_3.newLineIfNotEmpty();
              _builder_3.append("}");
              _builder_3.newLine();
              _builder_3.newLine();
              _switchResult = _builder_3;
              break;
            default:
              break;
          }
        }
        _xifexpression_1 = _switchResult;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  /**
   * Generates an Alloy fact to ensure the disjointness of all individuals.
   * 
   * @param ml2model the ML2 Model to be considered.
   */
  public CharSequence generateDisjointIndividualsFact(final Model ml2model) {
    CharSequence _xblockexpression = null;
    {
      ArrayList<Individual> individualsToInclude = new ArrayList<Individual>();
      Iterable<Individual> _filter = Iterables.<Individual>filter(ml2model.getElements(), Individual.class);
      for (final Individual individual : _filter) {
        individualsToInclude.add(individual);
      }
      CharSequence _xifexpression = null;
      int _size = individualsToInclude.size();
      boolean _greaterThan = (_size > 1);
      if (_greaterThan) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("fact DisjointIndividuals {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("disjoint[");
        {
          boolean _hasElements = false;
          for(final Individual i : individualsToInclude) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "\t");
            }
            String _name = i.getName();
            _builder.append(_name, "\t");
          }
        }
        _builder.append("]");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * Generates an Alloy fact to ensure the disjointness of disconnected hierarchies.
   * 
   * @param ml2model the ML2 Model to be considered.
   */
  public CharSequence generateDisjointDisconnectedHierarchiesFact(final Model ml2model) {
    CharSequence _xblockexpression = null;
    {
      ArrayList<HashSet<br.ufes.inf.nemo.ml2.model.Class>> disconnectedHierarchies = new ArrayList<HashSet<br.ufes.inf.nemo.ml2.model.Class>>();
      Iterable<br.ufes.inf.nemo.ml2.model.Class> _filter = Iterables.<br.ufes.inf.nemo.ml2.model.Class>filter(ml2model.getElements(), br.ufes.inf.nemo.ml2.model.Class.class);
      for (final br.ufes.inf.nemo.ml2.model.Class ml2class : _filter) {
        {
          ArrayList<br.ufes.inf.nemo.ml2.model.Class> ml2classesArray = new ArrayList<br.ufes.inf.nemo.ml2.model.Class>();
          HashSet<br.ufes.inf.nemo.ml2.model.Class> hierarchy = new HashSet<br.ufes.inf.nemo.ml2.model.Class>();
          ml2classesArray.add(ml2class);
          while ((ml2classesArray.size() != 0)) {
            {
              br.ufes.inf.nemo.ml2.model.Class aux = ml2classesArray.remove(0);
              hierarchy.add(aux);
              EList<br.ufes.inf.nemo.ml2.model.Class> _superClasses = aux.getSuperClasses();
              for (final br.ufes.inf.nemo.ml2.model.Class superClass : _superClasses) {
                ml2classesArray.add(superClass);
              }
            }
          }
          ArrayList<HashSet<br.ufes.inf.nemo.ml2.model.Class>> hierarchiesToRemove = new ArrayList<HashSet<br.ufes.inf.nemo.ml2.model.Class>>();
          for (final HashSet<br.ufes.inf.nemo.ml2.model.Class> h : disconnectedHierarchies) {
            {
              HashSet<br.ufes.inf.nemo.ml2.model.Class> intersection = new HashSet<br.ufes.inf.nemo.ml2.model.Class>(hierarchy);
              intersection.retainAll(h);
              int _size = intersection.size();
              boolean _notEquals = (_size != 0);
              if (_notEquals) {
                hierarchy.addAll(h);
                hierarchiesToRemove.add(h);
              }
            }
          }
          disconnectedHierarchies.add(hierarchy);
          for (final HashSet<br.ufes.inf.nemo.ml2.model.Class> h_1 : hierarchiesToRemove) {
            disconnectedHierarchies.remove(h_1);
          }
        }
      }
      CharSequence _xifexpression = null;
      int _size = disconnectedHierarchies.size();
      boolean _greaterThan = (_size > 1);
      if (_greaterThan) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("fact DisjointDisconnectedHierarchies {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("disjoint[");
        {
          boolean _hasElements = false;
          for(final HashSet<br.ufes.inf.nemo.ml2.model.Class> h : disconnectedHierarchies) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "\t");
            }
            {
              int _size_1 = h.size();
              boolean _equals = (_size_1 == 1);
              if (_equals) {
                {
                  for(final br.ufes.inf.nemo.ml2.model.Class c : h) {
                    String _name = c.getName();
                    _builder.append(_name, "\t");
                  }
                }
              } else {
                {
                  boolean _hasElements_1 = false;
                  for(final br.ufes.inf.nemo.ml2.model.Class c_1 : h) {
                    if (!_hasElements_1) {
                      _hasElements_1 = true;
                      _builder.append("(", "\t");
                    } else {
                      _builder.appendImmediate("+", "\t");
                    }
                    String _name_1 = c_1.getName();
                    _builder.append(_name_1, "\t");
                  }
                  if (_hasElements_1) {
                    _builder.append(")", "\t");
                  }
                }
              }
            }
          }
        }
        _builder.append("]");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * Generates an Alloy fact to ensure that some classes are not instances of other classes,
   * if the instantiation was not explicitly defined.
   * 
   * @param ml2model the ML2 Model to be considered.
   */
  public CharSequence generateUnwantedInstantiationsFact(final Model ml2model) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fact UnwantedInstantiations {");
    _builder.newLine();
    {
      Iterable<FirstOrderClass> _filter = Iterables.<FirstOrderClass>filter(ml2model.getElements(), FirstOrderClass.class);
      for(final FirstOrderClass foclass : _filter) {
        {
          final Function1<HighOrderClass, Boolean> _function = (HighOrderClass it) -> {
            int _order = it.getOrder();
            return Boolean.valueOf((_order == 2));
          };
          Iterable<HighOrderClass> _filter_1 = IterableExtensions.<HighOrderClass>filter(Iterables.<HighOrderClass>filter(ml2model.getElements(), HighOrderClass.class), _function);
          for(final HighOrderClass hoclass : _filter_1) {
            {
              boolean _contains = foclass.getClassifiers().contains(hoclass);
              boolean _not = (!_contains);
              if (_not) {
                _builder.append("\t");
                _builder.append("not iof[");
                String _name = foclass.getName();
                _builder.append(_name, "\t");
                _builder.append("Reified,");
                String _name_1 = hoclass.getName();
                _builder.append(_name_1, "\t");
                _builder.append("Reified]");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    {
      Iterable<HighOrderClass> _filter_2 = Iterables.<HighOrderClass>filter(ml2model.getElements(), HighOrderClass.class);
      for(final HighOrderClass hoclass_1 : _filter_2) {
        {
          final Function1<HighOrderClass, Boolean> _function_1 = (HighOrderClass it) -> {
            int _order = it.getOrder();
            int _order_1 = hoclass_1.getOrder();
            int _plus = (_order_1 + 1);
            return Boolean.valueOf((_order == _plus));
          };
          Iterable<HighOrderClass> _filter_3 = IterableExtensions.<HighOrderClass>filter(Iterables.<HighOrderClass>filter(ml2model.getElements(), HighOrderClass.class), _function_1);
          for(final HighOrderClass hoclass2 : _filter_3) {
            {
              boolean _contains_1 = hoclass_1.getClassifiers().contains(hoclass2);
              boolean _not_1 = (!_contains_1);
              if (_not_1) {
                _builder.append("\t");
                _builder.append("not iof[");
                String _name_2 = hoclass_1.getName();
                _builder.append(_name_2, "\t");
                _builder.append("Reified,");
                String _name_3 = hoclass2.getName();
                _builder.append(_name_3, "\t");
                _builder.append("Reified]");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Generates Alloy signatures and singletons for each MLT* basic ordered type used in the Alloy
   * model being generated. The number of types considered is given by the order of the class with
   * the highest order in the ML2 Model.
   * 
   * @param ml2class the ML2 Model to be considered.
   */
  public CharSequence generateNotableConstantsSection(final Model model) {
    StringConcatenation _builder = new StringConcatenation();
    {
      int _highestOrder = this.highestOrder(model);
      IntegerRange _upTo = new IntegerRange(1, _highestOrder);
      for(final Integer order : _upTo) {
        _builder.append("sig Order");
        _builder.append(order);
        _builder.append("Type in OrderedType {");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
        _builder.append("fact Order");
        _builder.append(order);
        _builder.append("TypeDefinition {");
        _builder.newLineIfNotEmpty();
        {
          if (((order).intValue() == 1)) {
            _builder.append("\t");
            _builder.append("all e: Entity | (e in Order");
            _builder.append(order, "\t");
            _builder.append("Type iff (some b: BasicType,f: Individual_ | iof[e,b] and powertypeOf[b,f]))");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t");
            _builder.append("all e: Entity | (e in Order");
            _builder.append(order, "\t");
            _builder.append("Type iff (some b: BasicType,f: Order");
            _builder.append(((order).intValue() - 1), "\t");
            _builder.append("TypeReified | iof[e,b] and powertypeOf[b,f]))");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
        _builder.append("one sig Order");
        _builder.append(order);
        _builder.append("TypeReified in OrderedType {");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
        _builder.append("fact Order");
        _builder.append(order);
        _builder.append("TypeReifiedDefinition {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("all e: Entity | e in Order");
        _builder.append(order, "\t");
        _builder.append("TypeReified iff (all e\': Entity | iof[e\',e] iff e\' in Order");
        _builder.append(order, "\t");
        _builder.append("Type)");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  /**
   * Decides the Alloy multiplicity keyword to be used in ML2 Feature declarations.
   * 
   * @param feature the ML2 Feature to be considered.
   */
  private String decideMultiplicityKeyword(final Feature feature) {
    int _xifexpression = (int) 0;
    if ((feature instanceof Attribute)) {
      _xifexpression = ((Attribute)feature).getLowerBound();
    } else {
      _xifexpression = ((Reference) feature).getLowerBound();
    }
    final int lowerBound = _xifexpression;
    int _xifexpression_1 = (int) 0;
    if ((feature instanceof Attribute)) {
      _xifexpression_1 = ((Attribute)feature).getUpperBound();
    } else {
      _xifexpression_1 = ((Reference) feature).getUpperBound();
    }
    final int upperBound = _xifexpression_1;
    if ((lowerBound == 0)) {
      if ((upperBound == 1)) {
        return "lone ";
      } else {
        return "set ";
      }
    } else {
      if ((upperBound == 1)) {
        return "one ";
      } else {
        return "some ";
      }
    }
  }
  
  /**
   * Determines the highest order of an ML2 model.
   * 
   * @param model the ML2 Model to be considered.
   */
  private int highestOrder(final Model model) {
    int order = 1;
    EList<ModelElement> _elements = model.getElements();
    for (final ModelElement element : _elements) {
      if ((element instanceof HighOrderClass)) {
        int _order = ((HighOrderClass)element).getOrder();
        boolean _greaterThan = (_order > order);
        if (_greaterThan) {
          order = ((HighOrderClass)element).getOrder();
        }
      }
    }
    return order;
  }
  
  public CharSequence generateAlloyElement(final ModelElement _class) {
    if (_class instanceof br.ufes.inf.nemo.ml2.model.Class) {
      return _generateAlloyElement((br.ufes.inf.nemo.ml2.model.Class)_class);
    } else if (_class instanceof Individual) {
      return _generateAlloyElement((Individual)_class);
    } else if (_class instanceof Constraint) {
      return _generateAlloyElement((Constraint)_class);
    } else if (_class instanceof GeneralizationSet) {
      return _generateAlloyElement((GeneralizationSet)_class);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(_class).toString());
    }
  }
  
  public CharSequence generateAlloySignature(final br.ufes.inf.nemo.ml2.model.Class foclass) {
    if (foclass instanceof FirstOrderClass) {
      return _generateAlloySignature((FirstOrderClass)foclass);
    } else if (foclass instanceof HighOrderClass) {
      return _generateAlloySignature((HighOrderClass)foclass);
    } else if (foclass instanceof OrderlessClass) {
      return _generateAlloySignature((OrderlessClass)foclass);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(foclass).toString());
    }
  }
  
  public CharSequence generateAlloySignatureFields(final Feature attribute) {
    if (attribute instanceof Attribute) {
      return _generateAlloySignatureFields((Attribute)attribute);
    } else if (attribute instanceof Reference) {
      return _generateAlloySignatureFields((Reference)attribute);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(attribute).toString());
    }
  }
  
  public CharSequence generateAlloySingleton(final EntityDeclaration foclass) {
    if (foclass instanceof FirstOrderClass) {
      return _generateAlloySingleton((FirstOrderClass)foclass);
    } else if (foclass instanceof HighOrderClass) {
      return _generateAlloySingleton((HighOrderClass)foclass);
    } else if (foclass instanceof OrderlessClass) {
      return _generateAlloySingleton((OrderlessClass)foclass);
    } else if (foclass instanceof Individual) {
      return _generateAlloySingleton((Individual)foclass);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(foclass).toString());
    }
  }
  
  public CharSequence generateAlloySingletonAssignmentsFact(final EntityDeclaration ml2class) {
    if (ml2class instanceof br.ufes.inf.nemo.ml2.model.Class) {
      return _generateAlloySingletonAssignmentsFact((br.ufes.inf.nemo.ml2.model.Class)ml2class);
    } else if (ml2class instanceof Individual) {
      return _generateAlloySingletonAssignmentsFact((Individual)ml2class);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(ml2class).toString());
    }
  }
  
  public CharSequence generateAlloySingletonAssignment(final FeatureAssignment attributeAssignment) {
    if (attributeAssignment instanceof AttributeAssignment) {
      return _generateAlloySingletonAssignment((AttributeAssignment)attributeAssignment);
    } else if (attributeAssignment instanceof ReferenceAssignment) {
      return _generateAlloySingletonAssignment((ReferenceAssignment)attributeAssignment);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(attributeAssignment).toString());
    }
  }
  
  public CharSequence generateProperSpecializationFact(final br.ufes.inf.nemo.ml2.model.Class foclass) {
    if (foclass instanceof FirstOrderClass) {
      return _generateProperSpecializationFact((FirstOrderClass)foclass);
    } else if (foclass instanceof HighOrderClass) {
      return _generateProperSpecializationFact((HighOrderClass)foclass);
    } else if (foclass instanceof OrderlessClass) {
      return _generateProperSpecializationFact((OrderlessClass)foclass);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(foclass).toString());
    }
  }
  
  public CharSequence generateRegularityFeatureFact(final FeatureAssignment attributeAssignment, final br.ufes.inf.nemo.ml2.model.Class ml2class) {
    if (attributeAssignment instanceof AttributeAssignment) {
      return _generateRegularityFeatureFact((AttributeAssignment)attributeAssignment, ml2class);
    } else if (attributeAssignment instanceof ReferenceAssignment) {
      return _generateRegularityFeatureFact((ReferenceAssignment)attributeAssignment, ml2class);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(attributeAssignment, ml2class).toString());
    }
  }
}
