package blake.przewodnikturystyczny.baza.model.pomocniczy;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="MiejsceWydarzenie")
public class TabMiejsceWydarzenie extends Model {
	
	@Column(name="Miejsce_ID", notNull=true)
	private int miejsce_id;
	@Column(name="Wydarzenie_ID", notNull=true)
	private int wydarzenie_id;
	
	public TabMiejsceWydarzenie() {
		super();
	}
	
	public TabMiejsceWydarzenie(int miejsce_id, int wydarzenie_id) {
		super();
		this.miejsce_id = miejsce_id;
		this.wydarzenie_id = wydarzenie_id;
	}
}