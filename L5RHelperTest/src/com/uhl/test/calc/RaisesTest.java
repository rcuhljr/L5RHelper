package com.uhl.test.calc;

import junit.framework.TestCase;

import android.content.Context;
import android.test.AndroidTestCase;

import com.uhl.calc.Raises;
import com.uhl.calc.Roll;

public class RaisesTest extends AndroidTestCase {

	public void testCalculateRaisesShouldHandle0Raises() {
		
		Context testContext = getContext();
		int raises = Raises
				.calculateRaises(testContext, Integer.MAX_VALUE, new Roll(10, 10, 0, 0));
		assertEquals(0, raises);
	}
}
