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
/** 
 * That class calls the parser.
 * After the parsing, it calls function to construct each triples.
 * Each triples feed a model. 
 * All models are obtained to be written into a file.
 * @author Simon
 *
 */
public class RDFConstructor {

	private DBParser parser;
	
	private File rdfFile;
	
	private Model m;
	
	private String link = "http://ex.org/";
	private String linkOmdb = "https://www.omdb.org/";
	private String linkMovieLens ="https://movielens.org/";
	private String linkImdb ="www.imdb.com/";

	
	public RDFConstructor() {
		parser = new DBParser();
		rdfFile = new File("MovieLens.ttl");
	}
	
	public void generateParsing() throws IOException {
		parser.parseMovies();
		parser.parseLinks();
		parser.parseTags();
		parser.parseRates();
		parser.parseScores(); 
		parser.parseMetaTags();
	}
	
	public Model generateTagsTriples() {
		Model model = ModelFactory.createDefaultModel();

		for (Iterator<Integer> iter = parser.getTags().keySet().iterator(); iter.hasNext();) {
			int sub = iter.next();
			String prop = parser.getTags().get(sub);
			model.createResource(this.link + "Tag/"+String.valueOf(sub))
			         .addProperty(FOAF.name, prop);
		}

		 return model;
	}
	
	public Model generateScoreTriples() {
		Model model = ModelFactory.createDefaultModel();
		for (CoupleMovieTag cmt : parser.getScores().keySet()) {
			String uri;
			// Directly adding the id movie from wikidata (omdb) if it is possible
			uri = this.linkMovieLens+ "movies/" + cmt.getIdMovie();
		    	model.createResource(this.link + "Tag/"+cmt.getIdTag())
		    		.addProperty(m.getProperty(this.linkMovieLens + "movie"), uri)
		    		.addLiteral(m.getProperty(this.link + "Relevance"), parser.getScores().get(cmt));		    	
		}
		return model;
	}
	
	public Model generateMovies() {
		Model model = ModelFactory.createDefaultModel();
		for (int key : parser.getMovies().keySet()) {
			if (parser.getImdbId().get(key) != null) {
				model.createResource(this.linkMovieLens + "movies/"+ key)
				.addProperty(FOAF.name, parser.getMovies().get(key))
				.addProperty(m.getProperty(this.linkImdb+ "movie"), this.linkImdb + "title/tt"+ parser.getImdbId().get(key));

			} else {
				model.createResource(this.linkMovieLens + "movies/"+ key)
				.addProperty(FOAF.name, parser.getMovies().get(key));
			}
		}
		return model;
	}
	
	public Model generateTagsOnMovieTriples() {
		Model model = ModelFactory.createDefaultModel();
		long id = 0;
		for (MetaTag mt : parser.getMetaTags()) {
			id++;
		    	model.createResource(this.link + "MetaTag/" + id)
		    		.addLiteral(FOAF.name, mt.getTagValue())
				    .addLiteral(m.getProperty(this.link + "User"),  mt.getUserId())
				    .addLiteral(m.getProperty(this.link + "Movie"), m.getResource(this.linkMovieLens + "movies/"+ mt.getMovieId()));					  		 
		}

		return model;
	}
	
	public void generateRateTriples(FileWriter fw) {		
		int key = 0;
		for( Rate r : parser.getRatings()){
			key++;
			Model model = ModelFactory.createDefaultModel();
			model.createResource(this.link + "Rating/" + key) //no hay un id para rating
				.addLiteral(m.getProperty(this.link + "User"), r.getUserId())
				.addLiteral( m.getProperty(this.link + "Movie"), m.getResource(this.linkMovieLens + "movies/"+ r.getMovieId()))
				.addLiteral(m.getProperty(this.link + "Rate"), r.getRateValue());
			model.write(fw, "N-TRIPLES");
			if(key%1000==0)
				System.out.println("N� of rates wrote: " + key);
		}
	}
	
	public void generateAllRDF() {
		try
		{
		    FileWriter fw = new FileWriter (rdfFile, true);
		    
		    System.out.println("Writing in MovieLens.ttl file...");
		    
		    System.out.println("Writing Movies triples");
		    this.generateMovies().write(fw, "N-TRIPLES");
		    fw.flush();
		    System.out.println("Writing Tags triples");
		    this.generateTagsTriples().write(fw, "N-TRIPLES");
		    fw.flush();
		    System.out.println("Writing MetaTags triples");
		    this.generateTagsOnMovieTriples().write(fw, "N-TRIPLES");
		    fw.flush();  
		    System.out.println("Writing Scores triples");
		    this.generateScoreTriples().write(fw, "N-TRIPLES");
		    fw.flush();
		    System.out.println("Writing Rates triples");
		    this.generateRateTriples(fw);
		   		    
		   
		    System.out.println("Writing in MovieLens.ttl file complete");
		    
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
		 m.createProperty(this.link + "MetaTag");
		 m.createProperty(this.link + "User");
		 m.createProperty(this.link + "Movie");
		 m.createProperty(this.link + "Rate");
		 m.createProperty(this.linkOmdb + "movie");
		 m.createProperty(this.linkMovieLens + "movie");
		 m.createProperty(this.linkImdb + "movie");
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
