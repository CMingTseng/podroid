package com.podling.podroid.group;

import org.the86.model.Group;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.podling.podroid.util.TabListener;

public class GroupActivity extends Activity {

	public static Intent newInstance(Context context, Group group) {
		Intent intent = new Intent(context, GroupActivity.class);
		intent.putExtra("groupSlug", group.getSlug());
		intent.putExtra("groupName", group.getName());
		return intent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();

		String groupName = extras.getString("groupName");
		
		ActionBar actionBar = getActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setTitle(groupName);
		
		Tab tab = actionBar
				.newTab()
				.setText("conversations")
				.setTabListener(
						new TabListener<GroupConversationsFragment>(this,
								"conversations",
								GroupConversationsFragment.class, extras));
		actionBar.addTab(tab);
		
		 tab = actionBar
					.newTab()
					.setText("members")
					.setTabListener(
							new TabListener<GroupMembersFragment>(this,
									"members",
									GroupMembersFragment.class, extras));
			actionBar.addTab(tab);
	}
}
