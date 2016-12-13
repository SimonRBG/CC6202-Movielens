/**
 * Class to avoid code repetition in Rate an MetaTag
 * @author Simon
 *
 */
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
}
