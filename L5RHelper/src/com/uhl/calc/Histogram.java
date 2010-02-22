package com.uhl.calc;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uhl.HomeActivity;
import com.uhl.db.DBHelper;

public class Histogram {

	private Double[] histogram;
	
	public Histogram(byte[] data) {
		LoadHistogram(data);
	}
	
	public Histogram(Roll roll){
		DBHelper db = new DBHelper(new HomeActivity());
		try {
			db.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		db.openDataBase();
		SQLiteDatabase db2 = db.getReadableDatabase();
		Cursor cursor = db2.rawQuery(
				"select histogram from rolls where kept = ? and rolled = ? and gp = ? and luck = ?",
				new String[] { String.valueOf(roll.getRolled()),
						String.valueOf(roll.getKept()),
						String.valueOf(roll.getGp()),
						String.valueOf(roll.getLuck())});
		db.close();
		LoadHistogram(cursor.getBlob(0));		
	}
	
	private void LoadHistogram(byte[] data){
		Double[] result = new Double[0];
		ByteArrayInputStream bIn = new ByteArrayInputStream(data);
    	ObjectInputStream in;
		try {
			in = new ObjectInputStream(bIn);		
			result = (Double[]) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.histogram = result;
	}


	public int getHighestTN(double confidence) {
		int result = 0;
		if(this.histogram.length == 0)
			return result;
		double accum = 0.0;
		//Conservative check. If confidence is 95 and one TN is 94.8 and the other 95.2 it picks 95.2
		while(confidence <= (1.0-accum)){ 
			result++;
			accum += this.histogram[result];			
		}		
		return result-1;
	}
}
