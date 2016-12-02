import java.sql.Time;

public class MetaTag extends AssociatedUMT {
	private String tagValue;
	
	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public MetaTag(int movieId, int userId, String tagValue, Time time) {
		super.movieId = movieId;
		super.userId = userId;
		this.tagValue = tagValue;
//		super.time = time;
	}
}
