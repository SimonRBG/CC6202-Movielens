import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RDFConstructor {

	private DBParser parser;
	
	public RDFConstructor() {
		parser = new DBParser();
	}
	
	public void generateParsing() throws IOException {
		//dbp.parseMovies();
		//dbp.parseLinks();
		//dbp.parseTags();
		/** TODO : Optimize because demand more than 1G of bytes
		//parser.parseScores(); 
		parser.parseRates();**/ 
		parser.parseMetaTags();
	}
	
	/** TODO : Using JENA **/
	public void generateTagsTriples() {
		return;
	}
	
	/** TODO : Using JENA **/
	public void generateScoreTriples() {
		return;
	}
	
	/** TODO : Using JENA **/
	public void generateTagsOnMoveieTriples() {
		return;
	}
	
	/** TODO : Using JENA **/
	public void generateRateTriples() {
		return;
	}
	
	/** TODO : Using JENA **/
	public void generateWikiDataTriples() {
		return;
	}
	
	/** TODO : Call all the jena functions **/
	public void generateAllRDF() {
		return;
	}
	
	public static void main (String args[]) {
		RDFConstructor rdfc = new RDFConstructor();
		try {
			rdfc.generateParsing();
			rdfc.generateAllRDF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
