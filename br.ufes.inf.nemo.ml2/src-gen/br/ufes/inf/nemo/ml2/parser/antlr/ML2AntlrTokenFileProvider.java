/*
 * generated by Xtext 2.21.0
 */
package br.ufes.inf.nemo.ml2.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class ML2AntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("br/ufes/inf/nemo/ml2/parser/antlr/internal/InternalML2.tokens");
	}
}
