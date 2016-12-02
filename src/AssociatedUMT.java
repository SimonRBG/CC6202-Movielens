//import java.sql.Time;

public abstract class AssociatedUMT {
	protected int movieId;
	protected int userId;
	//protected Time time;
	
	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

/*	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}
*/
}
