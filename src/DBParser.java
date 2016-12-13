import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class DBParser {
	
	private final static String RESOURCES_PATH = "./ml-20m/";
	private final static String MOVIES_FILE_NAME = "movies.csv";
	private final static String LINKS_FILE_NAME = "links.csv";
	private final static String TAGS_FILE_NAME = "genome-tags.csv";
	private final static String SCORES_FILE_NAME = "genome-scores.csv";
	private final static String RATES_FILE_NAME = "ratings.csv";
	private final static String METATAGS_FILE_NAME = "tags.csv";
	private final static String SEPARATOR = ",";
	private HashMap <Integer, String> movies;
	private HashMap <Integer, String> moviesGenre;
	private HashMap <Integer, Integer> imdbId;
	private HashMap <Integer, Integer> wikidataId;
	private HashMap <Integer, String> tags;
	private HashMap <CoupleMovieTag, Double> scores;
	private ArrayList <Rate> ratings;
	private ArrayList <MetaTag> metaTags;
	
	public HashMap<Integer, String> getMovies() {
		return movies;
	}

	public HashMap<Integer, String> getMoviesGenre() {
		return moviesGenre;
	}

	public HashMap<Integer, Integer> getImdbId() {
		return imdbId;
	}

	public HashMap<Integer, Integer> getWikidataId() {
		return wikidataId;
	}

	public HashMap<Integer, String> getTags() {
		return tags;
	}

	public HashMap<CoupleMovieTag, Double> getScores() {
		return scores;
	}

	public ArrayList<Rate> getRatings() {
		return ratings;
	}

	public ArrayList<MetaTag> getMetaTags() {
		return metaTags;
	}

	public DBParser() {
		movies = new HashMap<Integer, String>();
		moviesGenre = new HashMap<Integer, String>();
		imdbId = new HashMap<Integer, Integer>();
		wikidataId = new HashMap<Integer, Integer>();
		tags = new HashMap<Integer, String>();
		scores = new HashMap <CoupleMovieTag, Double>();
		ratings = new ArrayList <Rate>();
		metaTags = new ArrayList <MetaTag>();
	}
	
    public void parseMovies() throws IOException {
        InputStream fr = new FileInputStream(RESOURCES_PATH + MOVIES_FILE_NAME);
        System.out.println("Parsing movies_file...");
        InputStreamReader ipsr = new InputStreamReader(fr);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		// Consume the unuseful first line
		br.readLine();
		while ((ligne=br.readLine()) != null){
			movies.put(Integer.parseInt(ligne.split(SEPARATOR)[0]), ligne.split(SEPARATOR)[1]);
			moviesGenre.put(Integer.parseInt(ligne.split(SEPARATOR)[0]), ligne.split(SEPARATOR)[2]);
		}
		br.close(); 
        
    }
    
    public void parseLinks() throws IOException {
        InputStream fr = new FileInputStream(RESOURCES_PATH + LINKS_FILE_NAME);
        System.out.println("Parsing links_file...");
        InputStreamReader ipsr = new InputStreamReader(fr);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		// Consume the unuseful first line
		br.readLine();
		while ((ligne=br.readLine()) != null){
			if (ligne.split(SEPARATOR).length >= 2) {
				imdbId.put(Integer.parseInt(ligne.split(SEPARATOR)[0]), Integer.parseInt(ligne.split(SEPARATOR)[1]));
			}
			if (ligne.split(SEPARATOR).length >= 3) {
				wikidataId.put(Integer.parseInt(ligne.split(SEPARATOR)[0]), Integer.parseInt(ligne.split(SEPARATOR)[2]));
			}
		}
		br.close();         
    }
    
    public void parseTags() throws IOException {
    	System.out.println("Parsing genome-tags_file...");
        InputStream fr = new FileInputStream(RESOURCES_PATH + TAGS_FILE_NAME);
        
        InputStreamReader ipsr = new InputStreamReader(fr);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		// Consume the unuseful first line
		br.readLine();
		while ((ligne=br.readLine()) != null){
			tags.put(Integer.parseInt(ligne.split(SEPARATOR)[0]), ligne.split(SEPARATOR)[1]);						
		}
		br.close(); 
        
    }
    
    public void parseScores() throws IOException {
    	System.out.println("Parsing scores_file...");
    	InputStream fr = new FileInputStream(RESOURCES_PATH + SCORES_FILE_NAME);
        
        InputStreamReader ipsr = new InputStreamReader(fr);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		// Consume the first line
		br.readLine();
		while ((ligne=br.readLine()) != null){
			scores.put(new CoupleMovieTag(Integer.parseInt(ligne.split(SEPARATOR)[0]),Integer.parseInt(ligne.split(SEPARATOR)[1])), Double.parseDouble(ligne.split(SEPARATOR)[2]));
		}
		br.close(); 
        
    }
    
    public void parseRates() throws IOException {
        InputStream fr = new FileInputStream(RESOURCES_PATH + RATES_FILE_NAME);
        System.out.println("Parsing rates_file...");
        InputStreamReader ipsr = new InputStreamReader(fr);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		// Consume the first line
		br.readLine();
		while ((ligne=br.readLine()) != null){
			ratings.add(new Rate(Integer.parseInt(ligne.split(SEPARATOR)[1]),Integer.parseInt(ligne.split(SEPARATOR)[0]), Float.parseFloat(ligne.split(SEPARATOR)[2])));
		}
		br.close(); 
        
    }
    
    public void parseMetaTags() throws IOException {
        InputStream fr = new FileInputStream(RESOURCES_PATH + METATAGS_FILE_NAME);
        System.out.println("Parsing tags_file...");
        String tTag;
        int cpt=0;
        InputStreamReader ipsr = new InputStreamReader(fr);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		// Consume the first line
		br.readLine();
		while ((ligne=br.readLine()) != null){
			tTag="";
			cpt = 2;
			//this while is for avoid tags with comas??
			while (cpt < ligne.split(SEPARATOR).length-1){
		        tTag += ligne.split(SEPARATOR)[cpt];
		        cpt++;
		    }			
			metaTags.add(new MetaTag(Integer.parseInt(
					ligne.split(SEPARATOR)[0]),
					Integer.parseInt(ligne.split(SEPARATOR)[1]),
					tTag));
		}
		br.close(); 
        
    }
    
}
