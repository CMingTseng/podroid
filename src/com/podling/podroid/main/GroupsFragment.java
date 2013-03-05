package com.podling.podroid.main;

import java.util.List;

import org.the86.The86;
import org.the86.The86Impl;
import org.the86.exception.The86Exception;
import org.the86.model.Group;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.podling.podroid.PodroidApplication;
import com.podling.podroid.R;
import com.podling.podroid.adapter.GroupAdapter;
import com.podling.podroid.group.GroupActivity;

public class GroupsFragment extends ListFragment {
	// private The86 the86;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new RetrieveGroupsTask(getActivity()).execute();
		// the86 = ((PodroidApplication)
		// getActivity().getApplicationContext()).getThe86();
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Group group = (Group) getListAdapter().getItem(position);
		startActivity(GroupActivity.newInstance(getActivity(), group));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.groups, container, false);
		return v;
	}

	private void populate(List<Group> groups) {
		setListAdapter(new GroupAdapter(getActivity(), groups));
	}

	class RetrieveGroupsTask extends AsyncTask<Void, Void, List<Group>> {
		protected ProgressDialog dialog;

		public RetrieveGroupsTask(Context context) {
			// create a progress dialog
			dialog = new ProgressDialog(context);
			dialog.setMessage("loading groups");
			dialog.setCancelable(false);
		}

		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}

		protected List<Group> doInBackground(Void... params) {
			try {
				((PodroidApplication) getActivity().getApplicationContext())
						.setThe86(the86);
				return the86.getGroups();
			} catch (The86Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(List<Group> groups) {
			populate(groups);
			dialog.dismiss();
		}
	}
}