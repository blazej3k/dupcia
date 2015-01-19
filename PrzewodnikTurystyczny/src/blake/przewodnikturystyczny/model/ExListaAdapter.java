package blake.przewodnikturystyczny.model;

import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import blake.przewodnikturystyczny.R;

public class ExListaAdapter extends BaseExpandableListAdapter {

	private List<ExListaGrupa> groups;
	public LayoutInflater inflater;
	public Activity activity;

	public ExListaAdapter(DialogFragment act, List<ExListaGrupa> groups) {
		activity = act.getActivity();
		this.groups = groups;
		inflater = activity.getLayoutInflater();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).elementy.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		String children = (String) getChild(groupPosition, childPosition);
		TextView text = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cust_row_ex_lista, null);
		}
		convertView.setClickable(false);
		text = (TextView) convertView.findViewById(R.id.tv_row_obiekt);
		text.setText(children);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).elementy.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,	View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cust_grouprow_ex_lista, null);
		}
		convertView.setClickable(false);
		ExListaGrupa group = (ExListaGrupa) getGroup(groupPosition);
		((TextView) convertView).setText(group.nazwa);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
} 