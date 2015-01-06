package blake.przewodnikturystyczny.baza.model;

public interface IfLocalizable {
	public String getAdres();
	public void setLatitude(double latitude);
	public void setLongitude(double longitude);
	Long save();
}
