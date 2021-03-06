package br.ufes.inf.nemo.ml2.validation;

import br.ufes.inf.nemo.ml2.validation.ValidationIssue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

@SuppressWarnings("all")
public class ValidationWarning extends ValidationIssue {
  public ValidationWarning() {
    super();
  }
  
  public ValidationWarning(final String message, final EStructuralFeature feature, final int index, final String code, final String[] issueData, final EObject source) {
    super(message, feature, index, code, issueData, source);
  }
}
