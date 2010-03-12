package com.uhl.test.utils;

import org.easymock.IArgumentMatcher;

import com.uhl.db.Profile;

public class ProfileEquals implements IArgumentMatcher {

	private Profile expected;
	
	public ProfileEquals(Profile e){
		expected = e;
	}
	
	@Override
	public void appendTo(StringBuffer buffer) {
		buffer.append("eqProfile(");
		buffer.append(expected.getClass().getName());
		buffer.append(")");
	}

	@Override
	public boolean matches(Object actual) {
		if(!(actual instanceof Profile)){
			return false;
		}
		Profile a = (Profile)actual;
		boolean result = true;
		
		result &= expected.getAgility() == a.getAgility();
		result &= expected.getAirRing() == a.getAirRing();
		result &= expected.getDefaultViewId() == a.getDefaultViewId();
		result &= expected.getEarthRing() == a.getEarthRing();
		result &= expected.getFireRing() == a.getFireRing();
		result &= expected.getGp() == a.getGp();
		result &= expected.getId() == a.getId();
		result &= expected.getLuck() == a.getLuck();
		result &= expected.getName().equals(a.getName());
		result &= expected.getReflexes() == a.getReflexes();
		result &= expected.getVoidRing() == a.getVoidRing();
		result &= expected.getWaterRing() == a.getWaterRing();
		
		return result;
	}

}