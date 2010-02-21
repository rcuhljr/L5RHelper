package com.uhl.calc;

import java.io.IOException;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uhl.HomeActivity;
import com.uhl.db.DBHelper;

public class Raises {

	public static int calculateRaises(int target, Roll roll) {

		return calculateRaises(target, roll, 0.95);
	}

	public static int calculateRaises(int target, Roll roll, double confidence) {

		Histogram h = loadHistogram(roll);
		int highest = h.getHighestTN(confidence);
		return (highest - target) / 5;
	}

	// TODO: make this less hideous
	private static Histogram loadHistogram(Roll roll) {

		DBHelper db = new DBHelper(new HomeActivity());
		try {
			db.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		db.openDataBase();
		SQLiteDatabase db2 = db.getReadableDatabase();
		Cursor cursor = db2.rawQuery(
				"select histogram from rolls where kept = ? and rolled = ?",
				new String[] { String.valueOf(roll.getRolled()),
						String.valueOf(roll.getKept()) });
		return new Histogram(cursor.getBlob(0));
	}
}
