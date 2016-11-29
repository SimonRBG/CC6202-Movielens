import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class DBParser {
	
	private final static String RESOURCES_PATH = "./ml-20m/";
	private final static String MOVIES_FILE_NAME = "movies.csv";
	private final static String LINKS_FILE_NAME = "links.csv";
	private final static String TAGS_FILE_NAME = "genome-tags.csv";
	private final static String SCORES_FILE_NAME = "genome-scores.csv";
	private final static String SEPARATOR = ",";
	private HashMap <Integer, String> movies;
	private HashMap <Integer, Integer> imdbId;
	private HashMap <Integer, Integer> wikidataId;
	private HashMap <Integer, String> tags;
	private HashMap <CoupleMovieTag, Double> scores;
	
	public DBParser() {
		movies = new HashMap<Integer, String>();
		imdbId = new HashMap<Integer, Integer>();
		wikidataId = new HashMap<Integer, Integer>();
		tags = new HashMap<Integer, String>();
		scores = new HashMap <CoupleMovieTag, Double>();
	}
	
    public void parseMovies() throws IOException {
        InputStream fr = new FileInputStream(RESOURCES_PATH + MOVIES_FILE_NAME);
        
        InputStreamReader ipsr = new InputStreamReader(fr);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		// Consume the first line
		br.readLine();
		while ((ligne=br.readLine()) != null){
			movies.put(Integer.parseInt(ligne.split(SEPARATOR)[0]), ligne.split(SEPARATOR)[1]);
			System.out.println(movies.get(Integer.parseInt(ligne.split(SEPARATOR)[0])));
		}
		br.close(); 
        
    }
    
    public void parseLinks() throws IOException {
        InputStream fr = new FileInputStream(RESOURCES_PATH + LINKS_FILE_NAME);
        
        InputStreamReader ipsr = new InputStreamReader(fr);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		// Consume the first line
		br.readLine();
		while ((ligne=br.readLine()) != null){
			if (ligne.split(SEPARATOR).length >= 2) {
				imdbId.put(Integer.parseInt(ligne.split(SEPARATOR)[0]), Integer.parseInt(ligne.split(SEPARATOR)[1]));
				System.out.println(imdbId.get(Integer.parseInt(ligne.split(SEPARATOR)[0])));
			}
			if (ligne.split(SEPARATOR).length >= 3) {
				wikidataId.put(Integer.parseInt(ligne.split(SEPARATOR)[0]), Integer.parseInt(ligne.split(SEPARATOR)[2]));
				System.out.println(wikidataId.get(Integer.parseInt(ligne.split(SEPARATOR)[0])));
			}
		}
		br.close(); 
        
    }
    
    public void parseTags() throws IOException {
        InputStream fr = new FileInputStream(RESOURCES_PATH + TAGS_FILE_NAME);
        
        InputStreamReader ipsr = new InputStreamReader(fr);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		// Consume the first line
		br.readLine();
		while ((ligne=br.readLine()) != null){
			tags.put(Integer.parseInt(ligne.split(SEPARATOR)[0]), ligne.split(SEPARATOR)[1]);
			System.out.println(tags.get(Integer.parseInt(ligne.split(SEPARATOR)[0])));
		}
		br.close(); 
        
    }
    
    public void parseScores() throws IOException {
        InputStream fr = new FileInputStream(RESOURCES_PATH + SCORES_FILE_NAME);
        
        InputStreamReader ipsr = new InputStreamReader(fr);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		// Consume the first line
		br.readLine();
		while ((ligne=br.readLine()) != null){
			scores.put(new CoupleMovieTag(Integer.parseInt(ligne.split(SEPARATOR)[0]),Integer.parseInt(ligne.split(SEPARATOR)[1])), Double.parseDouble(ligne.split(SEPARATOR)[2]));
			System.out.println(scores.size());
		}
		br.close(); 
        
    }
    
    public static void main(String args[]){
    	DBParser dbp = new DBParser();
    	try {
			dbp.parseMovies();
			dbp.parseLinks();
			dbp.parseTags();
			// TODO : Optimize because demand more than 1G of bytes
			//dbp.parseScores();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
