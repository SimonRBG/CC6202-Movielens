import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class RDFConstructor {

	private DBParser parser;
	
	private File rdfFile;
	
	public RDFConstructor() {
		parser = new DBParser();
		rdfFile = new File("MovieLens.rdf");
	}
	
	public void generateParsing() throws IOException {
		//parse.parseMovies();
		//parser.parseLinks();
		parser.parseTags();
		/** TODO : Optimize because demand more than 1G of bytes
		//parser.parseScores(); 
		parser.parseRates();**/ 
		//parser.parseMetaTags();
	}
	
	public Model generateTagsTriples() {
		Model model = ModelFactory.createDefaultModel();

		for (Iterator<Integer> iter = parser.getTags().keySet().iterator(); iter.hasNext();) {
			// TODO : Use URI for tags
			int sub = iter.next();
			String prop = parser.getTags().get(sub);
			System.out.println(prop);
			  model.createResource(String.valueOf(sub))
			         .addProperty(VCARD.FN, prop);
		}

		 return model;
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
		try
		{
		    FileWriter fw = new FileWriter (rdfFile);
		    
		    fw.write(this.generateTagsTriples().toString());
		 
		    fw.close();
		}
		catch (IOException exception)
		{
		    System.out.println ("Error while reading: " + exception.getMessage());
		}
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
