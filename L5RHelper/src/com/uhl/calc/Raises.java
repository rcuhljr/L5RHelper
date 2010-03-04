package com.uhl.calc;

import android.content.Context;

public class Raises {

	public static int calculateRaises(Context ctx, int target, Roll roll) {

		return calculateRaises(ctx, target, roll, 95);
	}

	public static int calculateRaises(Context ctx, int target, Roll roll, int confidence) {

		Histogram h = new Histogram(roll, ctx);
		int highest = h.getHighestTN(confidence)+roll.getStatMod();
		if(highest < target)
			return -1;
		return (highest - target) / 5;
	}
}
