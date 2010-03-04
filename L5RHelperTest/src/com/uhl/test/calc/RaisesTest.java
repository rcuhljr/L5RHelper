package com.uhl.test.calc;

import android.content.Context;
import android.test.AndroidTestCase;

import com.uhl.calc.Raises;
import com.uhl.calc.Roll;

public class RaisesTest extends AndroidTestCase {

	public void testCalculateRaisesShouldHandle0Raises() {

		Context testContext = getContext();
		int raises = Raises.calculateRaises(testContext, Integer.MAX_VALUE,
				new Roll(10, 10, 0, 0));
		assertEquals(-1, raises);
	}

	public void testCalculateRaisesShouldHandleTN10() {
		Context testContext = getContext();
		int raises = Raises.calculateRaises(testContext, 10, new Roll(10, 10,
				0, 0));
		assertEquals(6, raises);
	}

	public void testCalculateRaisesShouldHandleGP() {
		Context testContext = getContext();
		int raises = Raises.calculateRaises(testContext, 10, new Roll(10, 10,
				0, 1));
		assertEquals(6, raises);
	}

	public void testCalculateRaisesShouldUseCorrectConfidence() {
		Context testContext = getContext();
		int raises = Raises.calculateRaises(testContext, 10, new Roll(10, 10,
				0, 0), 50);
		assertEquals(10, raises);
	}
}
