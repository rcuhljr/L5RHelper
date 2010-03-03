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
	
	public void testRolloverShouldReachStatic(){
		Roll aRoll = new Roll(22,6,0,0);
		assertEquals(10,aRoll.getStatMod());
	}
	
	public void testToStringWorksProperlyNoStatic(){
		Roll aRoll = new Roll(12,6,0,0);
		assertEquals("10K7",aRoll.toString());
	}
	
	public void testToStringWorksProperlyWithPositiveStatic(){
		Roll aRoll = new Roll(12,6,11,0,0);
		assertEquals("10K7+11",aRoll.toString());
	}
	
	public void testToStringWorksProperlyWithNegativeStatic(){
		Roll aRoll = new Roll(12,6,-8,0,0);
		assertEquals("10K7-8",aRoll.toString());
	}

}
