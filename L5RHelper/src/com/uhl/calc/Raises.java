package com.uhl.calc;

import java.io.IOException;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uhl.HomeActivity;
import com.uhl.db.DBHelper;

public class Raises {

	public static int calculateRaises(int target, Roll roll) {

		return calculateRaises(target, roll, 95);
	}

	public static int calculateRaises(int target, Roll roll, int confidence) {

		Histogram h = new Histogram(roll);
		int highest = h.getHighestTN(confidence);
		return (highest - target) / 5;
	}
}
