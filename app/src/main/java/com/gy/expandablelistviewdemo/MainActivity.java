package com.gy.expandablelistviewdemo;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
	
	private SettingsListAdapter adapter;
	private ExpandableListView categoriesList;
	private ArrayList<Category> categories;
	
	protected Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		categoriesList = (ExpandableListView)findViewById(R.id.categories);
		categories = Category.getCategories();
		adapter = new SettingsListAdapter(this, 
				categories, categoriesList);
        categoriesList.setAdapter(adapter);
        
        categoriesList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				
				CheckedTextView checkbox = (CheckedTextView)v.findViewById(R.id.list_item_text_child);
				/*切换checkBox 状态*/
				checkbox.toggle();
				
				
				// find parent view by tag
				View parentView = categoriesList.findViewWithTag(categories.get(groupPosition).name);
				if(parentView != null) {
					TextView sub = parentView.findViewById(R.id.list_item_text_subscriptions);
					
					if(sub != null) {
						Category category = categories.get(groupPosition);
						if(checkbox.isChecked()) {
							// add child category to parent's selection list
							category.selection.add(checkbox.getText().toString());
							
							// sort list in alphabetical order
							Collections.sort(category.selection, new CustomComparator());
						}
						else {
							// remove child category from parent's selection list
							category.selection.remove(checkbox.getText().toString());
						}		
						
						// display selection list
						sub.setText(category.selection.toString());
					}
				}				
				return true;
			}
		});
	}
	
	public class CustomComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }
    
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
