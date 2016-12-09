import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.VCARD;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;

public class RDFConstructor {

	private DBParser parser;
	
	private File rdfFile;
	
	private Model m;
	
	private String link = "REPLACELinkThere#";

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
		parser.parseMetaTags();
	}
	
	public Model generateTagsTriples() {
		Model model = ModelFactory.createDefaultModel();

		for (Iterator<Integer> iter = parser.getTags().keySet().iterator(); iter.hasNext();) {
			// TODO : Use URI for tags
			int sub = iter.next();
			String prop = parser.getTags().get(sub);
			  model.createResource(RDFS.getURI() + "/tags/"+String.valueOf(sub))
			         .addProperty(FOAF.name, prop);
		}

		 return model;
	}
	
	/** TODO : Using JENA **/
	public void generateScoreTriples() {
		return;
	}
	
	/** TODO : Using JENA **/
	public Model generateTagsOnMoveieTriples() {
		Model model = ModelFactory.createDefaultModel();

		for (MetaTag mt : parser.getMetaTags()) {
			// TODO : Use URI for tags
			  model.createResource(mt.getTagValue())
			  // TODO : Which RDFS ?????
			  // TODO : Use Movie's id from Wikidata
			         .addProperty(m.getProperty(this.link + "MovieId"), String.valueOf(mt.getMovieId()));
			  model.createResource(mt.getTagValue())
			  		 .addProperty(m.getProperty(this.link + "UserId"), String.valueOf(mt.getUserId()));
		}

		 return model;
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
		    
		    System.out.println("Writing in MovieLens.rdf file...");
		    
		    this.generateTagsTriples().write(fw, "N-TRIPLES");
		    
		    this.generateTagsOnMoveieTriples().write(fw, "N-TRIPLES");
		 
		    System.out.println("Writing in MovieLens.rdf file complete");
		    
		    fw.close();
		}
		catch (IOException exception)
		{
		    System.out.println ("Error while reading: " + exception.getMessage());
		}
	}
	
	public void generateProperties(){
		 m = ModelFactory.createDefaultModel();
		 m.createProperty( this.link + "relevance");
		 m.createProperty( this.link + "TagId");
		 m.createProperty( this.link + "UserId");
		 m.createProperty( this.link + "MovieId");
	}
	
	public static void main (String args[]) {
		RDFConstructor rdfc = new RDFConstructor();
		try {
			rdfc.generateProperties();
			rdfc.generateParsing();
			rdfc.generateAllRDF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
