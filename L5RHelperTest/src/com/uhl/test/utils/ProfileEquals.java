package com.uhl.test.utils;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;

import com.uhl.db.Profile;

public class ProfileEquals implements IArgumentMatcher {

	private Profile expected;
	private Profile a;
	
	public ProfileEquals(Profile e){
		expected = e;
	}
	
	
	public static Profile eqProfile(Profile in){
		EasyMock.reportMatcher(new ProfileEquals(in));
		return null;
	}
	
	@Override
	public void appendTo(StringBuffer buffer) {
		buffer.append("eqProfile(expected:");
		if(expected != null){
			buffer.append(expected.toString());
		}
		buffer.append("actual:");
		if(a != null){
			buffer.append(a.toString());
		}
		buffer.append(")");
	}

	@Override
	public boolean matches(Object actual) {
		if(!(actual instanceof Profile)){
			return false;
		}
		a = (Profile)actual;
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
