import java.sql.Time;

public class Rate extends AssociatedUMT{
	private float rateValue;
	
	public float getRateValue() {
		return rateValue;
	}

	public void setRateValue(float rateValue) {
		this.rateValue = rateValue;
	}

	public Rate(int movieId, int userId, float rateValue){//, Time time) {
		super.movieId = movieId;
		super.userId = userId;
		this.rateValue = rateValue;
//		super.time = time;
	}
	
}
