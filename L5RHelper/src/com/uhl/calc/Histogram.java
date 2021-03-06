package com.uhl.calc;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import android.content.Context;
import android.database.Cursor;

import com.uhl.db.DBHelper;

public class Histogram {

	private int[] histogram;
	private static final int SCALING_FACTOR = 10000;

	public Histogram(byte[] data) {
		LoadHistogram(data);
	}

	public Histogram(Roll roll, Context ctx) {
		DBHelper db = new DBHelper(ctx);
		Cursor cursor = db.getHistogram(roll);
		LoadHistogram(cursor.getBlob(0));
		db.close();
		cursor.close();
	}

	private void LoadHistogram(byte[] data) {
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
		int targetValue = (Histogram.SCALING_FACTOR - confidence
				* (Histogram.SCALING_FACTOR / 100));
		if (this.histogram.length == 0)
			return result;
		int accum = 0;
		while (targetValue > accum) {
			result++;
			accum += this.histogram[result];
		}
		return result;
	}

	public double[] getRangeRaises(int range, int tn) {
		double[] result = new double[1+range*2];
		if (this.histogram.length == 0)
			return null;
		int count = 0;
		int accum = 0;
		int tnval = 0;
		while (count < result.length) {			
			accum += this.histogram[tnval];
			while(tnval >= (count*5+(tn-range*5))){
				result[count] = 100.0-((double)accum)/(double)Histogram.SCALING_FACTOR*100.0;
				count++;
			}			
			tnval++;		
		}
		return result;
		
	}
}
