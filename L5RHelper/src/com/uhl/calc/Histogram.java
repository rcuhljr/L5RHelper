package com.uhl.calc;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uhl.HomeActivity;
import com.uhl.db.DBHelper;

public class Histogram {

	private int[] histogram;
	private static int scalingFactor = 10000;
	
	public Histogram(byte[] data) {
		LoadHistogram(data);
	}
	
	public Histogram(Roll roll, Context ctx){
		DBHelper db = new DBHelper(ctx);
		try {
			db.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		db.openDataBase();
		SQLiteDatabase db2 = db.getReadableDatabase();
		Cursor cursor = db2.rawQuery(
				"select histogram from rolls where rolled = ? and kept = ? and gp = ? and luck = ?",
				new String[] { String.valueOf(roll.getRolled()),
						String.valueOf(roll.getKept()),
						String.valueOf(roll.getGp()),
						String.valueOf(roll.getLuck())});
		cursor.moveToFirst();
		LoadHistogram(cursor.getBlob(0));		
		db.close();
	}
	
	private void LoadHistogram(byte[] data){
		int[] result = new int[0];
		ByteArrayInputStream bIn = new ByteArrayInputStream(data);
    	ObjectInputStream in;
		try {
			in = new ObjectInputStream(bIn);		
			result = (int[]) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.histogram = result;
	}


	public int getHighestTN(int confidence) {
		int result = 0;
		int targetValue = (this.scalingFactor - confidence*(this.scalingFactor/100)); 
		if(this.histogram.length == 0)
			return result;
		int accum = 0;
		while(targetValue > accum){ 
			result++;
			accum += this.histogram[result];			
		}		
		return result-1;
	}
}
