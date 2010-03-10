package com.uhl.db;

import android.content.Context;

public class DBServiceLocator {
	
	private DBServiceLocator(){}
	
	static private IDBHelper _instance = null;
	
	static public IDBHelper getDBHelper(Context ctx){
		if(_instance == null) {
			_instance = new DBHelper(ctx);
		}
		return _instance;		
	}
	
	static public void setDBHelper(IDBHelper helper){
		_instance = helper;
	}

}
