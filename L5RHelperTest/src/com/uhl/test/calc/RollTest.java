package com.uhl.test.calc;

import com.uhl.calc.Roll;

import android.test.AndroidTestCase;

public class RollTest extends AndroidTestCase {
	
	public void testRolledShouldHandleMoreThenTenDice(){
		Roll aRoll = new Roll(12,4,0,0);
		assertEquals(10,aRoll.getRolled());
	}
	
	public void testkeptShouldHandleMoreThenTenRolledDice(){
		Roll aRoll = new Roll(12,4,0,0);
		assertEquals(5,aRoll.getKept());
	}

}
