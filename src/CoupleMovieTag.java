
public class CoupleMovieTag {
	private int idMovie;
	private int idTag;
	
	public int getIdMovie() {
		return idMovie;
	}

	public void setIdMovie(int idMovie) {
		this.idMovie = idMovie;
	}

	public int getIdTag() {
		return idTag;
	}

	public void setIdTag(int idTag) {
		this.idTag = idTag;
	}

	public CoupleMovieTag(int idMovie, int idTag){
		this.idMovie = idMovie;
		this.idTag = idTag;
	}
}
