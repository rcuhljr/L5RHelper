package com.uhl;

import java.util.Hashtable;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.uhl.db.DBHelper;

public class LoadProfileView extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  dbHelper = new DBHelper(this);
	  
	  BuildUserTable();
	  String[] names = new String[profiles.keySet().size()];
	  profiles.keySet().toArray(names);

	  setListAdapter(new ArrayAdapter<String>(this, R.layout.load_character_list_item, names));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	          Toast.LENGTH_SHORT).show();
	    }
	  });
	}
	
	private void BuildUserTable() {
		Cursor cursor = dbHelper.GetProfiles();
		if(cursor.getCount() < 1){
			profiles.put("No Profiles", -1);
			return;
		}
		do{
			profiles.put(cursor.getString(1), cursor.getInt(0));
		}while(cursor.moveToNext());		
	}

	private DBHelper dbHelper;
	private Hashtable<String, Integer> profiles = new Hashtable<String, Integer>();
	
}
