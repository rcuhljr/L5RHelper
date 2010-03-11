package com.uhl.test;



import java.util.ArrayList;

import org.easymock.EasyMock;
import org.powermock.api.easymock.PowerMock;

import com.jayway.android.robotium.solo.Solo;
import com.uhl.HomeActivity;
import com.uhl.db.DBServiceLocator;
import com.uhl.db.IDBHelper;
import com.uhl.db.Profile;
import com.uhl.test.utils.ProfileEquals;


import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {

	private Solo solo;	
	private IDBHelper mockDb;
	
	public HomeActivityTest() {
		super("com.uhl", HomeActivity.class);		
	}
		
	public void setUp(){						
		solo = new Solo(getInstrumentation(), getActivity());
		mockDb = PowerMock.createMock(IDBHelper.class);
		DBServiceLocator.setDBHelper(mockDb);
	}
	
	public void testCreateCharacterShouldDetectExistingName(){		 
		EasyMock.expect(mockDb.profileNameExists("A Test Name", -1)).andReturn(true);
		PowerMock.replayAll();
		solo.clickOnButton("Create New Character");		
		solo.enterText(solo.getCurrentEditTexts().get(0).getId(), "A Test Name");
		ArrayList<View> views = solo.getViews();
		solo.clickOnScreen(views.get(9));	
		solo.clickOnScreen(solo.getCurrentButtons().get(0));
		assertTrue(solo.searchText("Name in use"));
		PowerMock.verifyAll();
	}
	
	public void testCreateCharacterShouldDetectEmptyName(){
		solo.clickOnButton("Create New Character");		
		ArrayList<View> views = solo.getViews();
		solo.clickOnScreen(views.get(9));	
		solo.clickOnScreen(solo.getCurrentButtons().get(0));
		assertTrue(solo.searchText("Name Cannot Be Blank"));
	}
	
	public static Profile eqProfile(Profile in){
		EasyMock.reportMatcher(new ProfileEquals(in));
		return null;
	}
	
	public void testCreateMeleeCharacterShouldWorkWithValidName(){		 
		Profile testProfile = new Profile("Tester", -1);		
		testProfile.setDefaultViewId(1);		
		testProfile.SetStats(3, 3, 3, 3, 3, 3, 3, 0, 0);		
		EasyMock.expect(mockDb.profileNameExists("Tester", -1)).andReturn(false);		
		EasyMock.expect(mockDb.saveProfile(eqProfile(testProfile))).andReturn(true);
		PowerMock.replayAll();
		solo.clickOnButton("Create New Character");
		assertTrue(solo.searchText("Character Name"));
		solo.enterText(solo.getCurrentEditTexts().get(0).getId(), "Tester");
		ArrayList<View> views = solo.getViews();
		solo.clickOnScreen(views.get(9));	
		solo.clickOnScreen(solo.getCurrentButtons().get(0));
		solo.clickOnScreen(solo.getCurrentButtons().get(0));
		assertTrue(solo.searchButton("Create New Character"));
		PowerMock.verifyAll();
	}
	
	public void testCreateCasterCharacterShouldWorkWithValidName(){		 
		Profile testProfile = new Profile("Tester", -1);		
		testProfile.setDefaultViewId(2);		
		testProfile.SetStats(3, 3, 3, 3, 3, 3, 3, 0, 0);		
		EasyMock.expect(mockDb.profileNameExists("Tester", -1)).andReturn(false);		
		EasyMock.expect(mockDb.saveProfile(eqProfile(testProfile))).andReturn(true);
		PowerMock.replayAll();
		solo.clickOnButton("Create New Character");
		assertTrue(solo.searchText("Character Name"));
		solo.enterText(solo.getCurrentEditTexts().get(0).getId(), "Tester");
		ArrayList<View> views = solo.getViews();
		solo.clickOnScreen(views.get(10));	
		solo.clickOnScreen(solo.getCurrentButtons().get(0));
		solo.clickOnScreen(solo.getCurrentButtons().get(0));
		assertTrue(solo.searchButton("Create New Character"));
		PowerMock.verifyAll();
	}
    
    public void tearDown() throws Exception {

        try {
                solo.finalize();
        } catch (Throwable e) {

                e.printStackTrace();
        }
        getActivity().finish();
        super.tearDown();
   }


}
