package blake.przewodnikturystyczny.baza.model.pomocniczy;

import blake.przewodnikturystyczny.baza.model.IfSelectable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;

@Table(name="MiejsceWydarzenie")
public class TabMiejsceWydarzenie extends Model implements IfSelectable {
	
	@Column(name="Miejsce_ID", notNull=true, uniqueGroups="klucz_zlozony", onUniqueConflicts=ConflictAction.FAIL)
	Long miejsce_id;
	@Column(name="Wydarzenie_ID", notNull=true, uniqueGroups="klucz_zlozony", onUniqueConflicts=ConflictAction.FAIL)
	Long wydarzenie_id;
	
	public TabMiejsceWydarzenie() {
		super();
	}
	
	public TabMiejsceWydarzenie(Long miejsce_id, Long wydarzenie_id) {
		super();
		this.miejsce_id = miejsce_id;
		this.wydarzenie_id = wydarzenie_id;
	}

	/**
	 * @return the miejsce_id
	 */
	public Long getMiejsce_id() {
		return miejsce_id;
	}

	/**
	 * @param miejsce_id the miejsce_id to set
	 */
	public void setMiejsce_id(Long miejsce_id) {
		this.miejsce_id = miejsce_id;
	}

	/**
	 * @return the wydarzenie_id
	 */
	public Long getWydarzenie_id() {
		return wydarzenie_id;
	}

	/**
	 * @param wydarzenie_id the wydarzenie_id to set
	 */
	public void setWydarzenie_id(Long wydarzenie_id) {
		this.wydarzenie_id = wydarzenie_id;
	}

	@Override
	public String getNazwa() {
		String nazwa = "Relacja ID="+getId()+", Miejsce_ID="+getMiejsce_id()+", Wydarzenie_ID="+getWydarzenie_id();
		
		return nazwa;
	}
}