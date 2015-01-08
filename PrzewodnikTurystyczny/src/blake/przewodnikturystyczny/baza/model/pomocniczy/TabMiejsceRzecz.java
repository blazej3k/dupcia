package blake.przewodnikturystyczny.baza.model.pomocniczy;

import blake.przewodnikturystyczny.baza.model.IfSelectable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column.ConflictAction;

@Table(name="MiejsceRzecz")
public class TabMiejsceRzecz extends Model implements IfSelectable {
	@Column(name="Miejsce_ID", notNull=true, uniqueGroups="klucz_zlozony", onUniqueConflicts=ConflictAction.FAIL)
	Long miejsce_id;
	@Column(name="Rzecz_ID", notNull=true, uniqueGroups="klucz_zlozony", onUniqueConflicts=ConflictAction.FAIL)
	Long rzecz_id;
	
	public TabMiejsceRzecz() {
		super();
	}
	
	public TabMiejsceRzecz(Long miejsce_id, Long rzecz_id) {
		super();
		this.miejsce_id = miejsce_id;
		this.rzecz_id = rzecz_id;
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
	 * @return the rzecz_id
	 */
	public Long getRzecz_id() {
		return rzecz_id;
	}

	/**
	 * @param rzecz_id the rzecz_id to set
	 */
	public void setRzecz_id(Long rzecz_id) {
		this.rzecz_id = rzecz_id;
	}
	
	@Override
	public String getNazwa() {
		String nazwa = "Relacja ID="+getId()+", Miejsce_ID="+getMiejsce_id()+", Rzecz_ID="+getRzecz_id();
		
		return nazwa;
	}
}
