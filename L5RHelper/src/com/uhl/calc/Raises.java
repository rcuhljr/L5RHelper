package com.uhl.calc;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uhl.HomeActivity;
import com.uhl.db.DBHelper;

public class Raises {

	public static int calculateRaises(Context ctx, int target, Roll roll) {

		return calculateRaises(ctx, target, roll, 95);
	}

	public static int calculateRaises(Context ctx, int target, Roll roll, int confidence) {

		Histogram h = new Histogram(roll, ctx);
		int highest = h.getHighestTN(confidence);
		return (highest - target) / 5;
	}
}
