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
		buffer.append("eqProfile(");
		buffer.append(expected.getName());
		buffer.append(",");
		buffer.append(expected.getAgility());
		buffer.append(",");
		buffer.append(expected.getAirRing());
		buffer.append(",");
		buffer.append(expected.getDefaultViewId());
		buffer.append(",");
		buffer.append(expected.getEarthRing());
		buffer.append(",");
		buffer.append(expected.getFireRing());
		buffer.append(",");
		buffer.append(expected.getGp());
		buffer.append(",");
		buffer.append(expected.getId());
		buffer.append(",");
		buffer.append(expected.getLuck());
		buffer.append(",");
		buffer.append(expected.getReflexes());
		buffer.append(",");
		buffer.append(expected.getVoidRing());
		buffer.append(",");
		buffer.append(expected.getWaterRing());
		buffer.append(",");
		buffer.append(a.getName());
		buffer.append(a.getAgility());
		buffer.append(",");
		buffer.append(a.getAirRing());
		buffer.append(",");
		buffer.append(a.getDefaultViewId());
		buffer.append(",");
		buffer.append(a.getEarthRing());
		buffer.append(",");
		buffer.append(a.getFireRing());
		buffer.append(",");
		buffer.append(a.getGp());
		buffer.append(",");
		buffer.append(a.getId());
		buffer.append(",");
		buffer.append(a.getLuck());
		buffer.append(",");
		buffer.append(a.getReflexes());
		buffer.append(",");
		buffer.append(a.getVoidRing());
		buffer.append(",");
		buffer.append(a.getWaterRing());
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
