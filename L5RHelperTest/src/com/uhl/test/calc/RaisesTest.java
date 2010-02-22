package com.uhl.test.calc;

import junit.framework.TestCase;

import com.uhl.calc.Raises;
import com.uhl.calc.Roll;

public class RaisesTest extends TestCase {

	public void testCalculateRaisesShouldHandle0Raises() {
		int raises = Raises
				.calculateRaises(Integer.MAX_VALUE, new Roll(10, 10, 0, 0));
		assertEquals(0, raises);
	}
}
