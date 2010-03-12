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
	
	public void testCalculateRangeShouldHandleRange0(){
		Context testContext = getContext();
		double[] actual = Raises.CalculateRange(testContext, new Roll(10,5), 0, 20);
		assertEquals(1, actual.length);
		assertEquals(99.91, actual[0]);
	}
	
	public void testCalculateRangeShouldHandleRange1(){
		Context testContext = getContext();
		double[] actual = Raises.CalculateRange(testContext, new Roll(10,5), 1, 20);
		assertEquals(3, actual.length);
		assertEquals(99.91, actual[1]);
	}
	
	public void testCalculateRangeShouldHandleRange3(){
		Context testContext = getContext();
		double[] actual = Raises.CalculateRange(testContext, new Roll(10,5), 3, 20);
		assertEquals(7, actual.length);
		assertEquals(99.91, actual[3]);
	}
	
	public void testCalculateRangeShouldHandleRange3TN10(){
		Context testContext = getContext();
		double[] actual = Raises.CalculateRange(testContext, new Roll(10,5), 3, 10);
		assertEquals(7, actual.length);
		assertEquals(99.91, actual[5]);
	}
	
}
