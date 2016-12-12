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
	
	private String link = "http://ex.org/";
	private String linkOmdb = "https://www.omdb.org/";
	private String linkMovieLens ="https://movielens.org/";
	
	public RDFConstructor() {
		parser = new DBParser();
		rdfFile = new File("MovieLens.rdf");
	}
	
	public void generateParsing() throws IOException {
		parser.parseMovies();
		parser.parseLinks();
		parser.parseTags();
		// TODO : Optimize because demand more than 1G of bytes
		parser.parseRates();
		parser.parseScores(); 
		parser.parseMetaTags();
	}
	
	public Model generateTagsTriples() {
		Model model = ModelFactory.createDefaultModel();

		for (Iterator<Integer> iter = parser.getTags().keySet().iterator(); iter.hasNext();) {
			// TODO : Use URI for tags
			int sub = iter.next();
			String prop = parser.getTags().get(sub);
			model.createResource(this.link + "/Tags/"+String.valueOf(sub))
			         .addProperty(FOAF.name, prop);
		}

		 return model;
	}
	
	/** TODO : Using JENA **/
	public Model generateScoreTriples() {
		Model model = ModelFactory.createDefaultModel();
		for (CoupleMovieTag cmt : parser.getScores().keySet()) {
			String uri;
			// Directly adding the id movie from wikidata (omdb) if it is possible
			uri = this.linkMovieLens+ "movies/" + cmt.getIdMovie();
		    	model.createResource(this.link + "/tags/"+cmt.getIdTag())
		    		.addProperty(m.getProperty(this.linkMovieLens + "movie"), uri)
		    		.addLiteral(m.getProperty(this.link + "Relevance"), parser.getScores().get(cmt));		    	
		}
		return model;
	}
	
	public Model generateMovies() {
		Model model = ModelFactory.createDefaultModel();
		for (int key : parser.getMovies().keySet()) {
			if (parser.getWikidataId().get(key) != null) {
				model.createResource(this.linkMovieLens + "movies/"+ key)
				.addProperty(FOAF.name, parser.getMovies().get(key))
				.addProperty(m.getProperty(this.linkOmdb+ "movie"), this.linkOmdb + "movie/"+ parser.getWikidataId().get(key));
			} else {
				model.createResource(this.linkMovieLens + "/movies/"+ key)
				.addProperty(FOAF.name, parser.getMovies().get(key));
			}
		}
		return model;
	}
	
	/** TODO : Using JENA **/
	public Model generateTagsOnMovieTriples() {
		Model model = ModelFactory.createDefaultModel();

		for (MetaTag mt : parser.getMetaTags()) {
			// TODO : Use URI for tags
		    	model.createResource(mt.getTagValue())
				       .addLiteral(m.getProperty(this.link + "User"), mt.getUserId())
				       .addLiteral(m.getProperty(this.link + "Movie"), mt.getMovieId());					  		 
		}

		return model;
	}
	
	/** TODO : Using JENA **/
	public void generateRateTriples() {
		return;
	}
	
	/** TODO : Using JENA, obtaining additional data (name, actors, etc) **/
	public void obtainWikiDataTriples() {
		
	}
	
	/** TODO : Call all the jena functions **/
	public void generateAllRDF() {
		try
		{
		    FileWriter fw = new FileWriter (rdfFile);
		    
		    System.out.println("Writing in MovieLens.rdf file...");
		    
		    this.generateMovies().write(fw, "N-TRIPLES");
		    
		    this.generateTagsTriples().write(fw, "N-TRIPLES");
		    
		    this.generateTagsOnMovieTriples().write(fw, "N-TRIPLES");
		    
		    this.generateScoreTriples().write(fw, "N-TRIPLES");
		 
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
		 m.createProperty(this.link + "Relevance");
		 m.createProperty(this.link + "Tag");
		 m.createProperty(this.link + "User");
		 m.createProperty(this.link + "Movie");
		 m.createProperty(this.linkOmdb + "movie");
		 m.createProperty(this.linkMovieLens + "movie");
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
