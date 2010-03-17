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

public class CreateAndLoadProfileTest extends ActivityInstrumentationTestCase2<HomeActivity> {

	private Solo solo;	
	private IDBHelper mockDb;
	
	public CreateAndLoadProfileTest() {
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
		solo.clickOnView(views.get(9));	
		solo.clickOnButton("Create Character");
		assertTrue(solo.searchText("Name in use"));
		PowerMock.verifyAll();
	}
	
	public void testCreateCharacterShouldDetectEmptyName(){
		solo.clickOnButton("Create New Character");		
		ArrayList<View> views = solo.getViews();
		solo.clickOnView(views.get(9));	
		solo.clickOnButton("Create Character");
		assertTrue(solo.searchText("Name Cannot Be Blank"));
	}
	
	public void testCreateMeleeCharacterShouldWorkWithValidName(){		 
		Profile testProfile = new Profile("Tester", 1);		
		testProfile.setId(-1);	
		testProfile.SetStats(3, 3, 3, 3, 3, 3, 3, 0, 0);		
		EasyMock.expect(mockDb.profileNameExists("Tester", -1)).andReturn(false);		
		EasyMock.expect(mockDb.saveProfile(ProfileEquals.eqProfile(testProfile))).andReturn(true);
		PowerMock.replayAll();
		solo.clickOnButton("Create New Character");
		assertTrue(solo.searchText("Character Name"));
		solo.enterText(solo.getCurrentEditTexts().get(0).getId(), "Tester");
		ArrayList<View> views = solo.getViews();
		solo.clickOnView(views.get(9));	
		solo.clickOnButton("Create Character");
		solo.clickOnButton("Submit");
		assertTrue(solo.searchButton("Create New Character"));
		PowerMock.verifyAll();
	}
	
	public void testCreateCasterCharacterShouldWorkWithValidName(){		 
		Profile testProfile = new Profile("Tester", 2);		
		testProfile.setId(-1);
		testProfile.SetStats(3, 3, 3, 3, 3, 3, 3, 0, 0);		
		EasyMock.expect(mockDb.profileNameExists("Tester", -1)).andReturn(false);		
		EasyMock.expect(mockDb.saveProfile(ProfileEquals.eqProfile(testProfile))).andReturn(true);
		PowerMock.replayAll();
		solo.clickOnButton("Create New Character");
		assertTrue(solo.searchText("Character Name"));
		solo.enterText(solo.getCurrentEditTexts().get(0).getId(), "Tester");
		ArrayList<View> views = solo.getViews();
		solo.clickOnView(views.get(10));	
		solo.clickOnButton("Create Character");
		solo.clickOnButton("Submit");
		assertTrue(solo.searchButton("Create New Character"));
		PowerMock.verifyAll();
	}
	
	public void testLoadExistingCharactersShouldShowNoProfiles(){
		EasyMock.expect(mockDb.getProfiles()).andReturn(new Profile[0]);
		PowerMock.replayAll();
		solo.clickOnButton("Load Existing Character");
		assertTrue(solo.searchText("No Profiles"));
		PowerMock.verifyAll();
	}
	
	public void testLoadExistingCharactersShouldShowProfiles(){
		EasyMock.expect(mockDb.getProfiles()).andReturn(new Profile[]{new Profile("Tester", 1)});
		PowerMock.replayAll();
		solo.clickOnButton("Load Existing Character");
		assertTrue(solo.searchText("Tester"));
		PowerMock.verifyAll();
	}
	
    
    public void tearDown() throws Exception {

        try {
                solo.finalize();
        } catch (Throwable e) {

                e.printStackTrace();
        }
        getActivity().finish();
        mockDb = null;
        super.tearDown();
   }


}
