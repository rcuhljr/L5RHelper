package com.uhl.test;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.powermock.api.easymock.PowerMock;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;
import com.jayway.android.robotium.solo.Solo;
import com.uhl.EditCharacterActivity;
import com.uhl.db.DBServiceLocator;
import com.uhl.db.IDBHelper;
import com.uhl.db.Profile;
import com.uhl.test.utils.ProfileEquals;


public class EditExistingProfileTest extends ActivityInstrumentationTestCase2<EditCharacterActivity>{
	
	private Solo solo;	
	private IDBHelper mockDb;
	private Profile testProfile;

	public EditExistingProfileTest() {
		super("com.uhl", EditCharacterActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	public void setUp(){
		String testName = getName();
		int id = 1;
		String profileId = null;
		if(testName.matches("(?i).*Profile[0-9]+")){
			profileId = testName.substring(testName.lastIndexOf("e")+1);
		}else if (testName.matches("(?i).*ProfileNeg[0-9]+")){
			profileId = testName.substring(testName.lastIndexOf("g")+1);
			id = -1;
		}
		if(profileId != null){
			try{
				id *= Integer.parseInt(profileId);
			}catch(Exception e){}
		}
		Intent anIntent = new Intent();		
		anIntent.putExtra("ID", id);
		anIntent.setClassName("uhl.com", "EditCharacterActivity");
		setActivityIntent(anIntent);		
		mockDb = PowerMock.createMock(IDBHelper.class);
		DBServiceLocator.setDBHelper(mockDb);		
			
		if(id < 0){
			testProfile = new Profile("Tester", 2);
			testProfile.setId(id);			
			testProfile.SetStats(2, 2, 2, 2, 2, 2, 2, 1, 1);
			EasyMock.expect(mockDb.loadProfile(id)).andReturn(testProfile.clone());
			PowerMock.replayAll();
		}else if(id  > 0){
			testProfile = new Profile("Tester", 1);		
			testProfile.setId(id);		
			testProfile.SetStats(2, 2, 2, 2, 2, 2, 2, 1, 1);
			EasyMock.expect(mockDb.loadProfile(id)).andReturn(testProfile.clone());
			PowerMock.replayAll();
		}else{
			PowerMock.replayAll();
		}
		
		solo = new Solo(getInstrumentation(), getActivity());
		
		PowerMock.reset(mockDb);
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
	
	
	public void testEditCharacterShouldWorkWithProfile1(){
		
		EasyMock.expect(mockDb.profileNameExists("Tester", 1)).andReturn(false);
		EasyMock.expect(mockDb.saveProfile(ProfileEquals.eqProfile(testProfile))).andReturn(true);
		PowerMock.replayAll();
		assertTrue(solo.searchEditText("Tester"));
		solo.clickOnScreen(solo.getCurrentButtons().get(2));
		assertTrue(solo.searchButton("Submit"));
		ArrayList<Spinner> spinners = solo.getCurrentSpinners();
		for(Spinner spinner : spinners){
			if(spinner != null){
				assertTrue(spinner.getSelectedItemPosition() == 1);
			}
		}		
		assertFalse(solo.getCurrentActivity().isFinishing());
		solo.clickOnButton("Submit");
		assertTrue(solo.getCurrentActivity().isFinishing());
		
		PowerMock.verifyAll();
	}
	
	public void testEditCharacterShouldWorkWithProfileNeg1(){
		
		EasyMock.expect(mockDb.profileNameExists("Tester", -1)).andReturn(false);
		EasyMock.expect(mockDb.saveProfile(ProfileEquals.eqProfile(testProfile))).andReturn(true);
		PowerMock.replayAll();
		assertTrue(solo.searchEditText("Tester"));
		solo.clickOnScreen(solo.getCurrentButtons().get(2));
		assertTrue(solo.searchButton("Submit"));
		ArrayList<Spinner> spinners = solo.getCurrentSpinners();
		for(Spinner spinner : spinners){
			if(spinner != null){
				assertTrue(spinner.getSelectedItemPosition() == 1);
			}
		}		
		assertFalse(solo.getCurrentActivity().isFinishing());
		solo.clickOnButton("Submit");
		assertTrue(solo.getCurrentActivity().isFinishing());
		
		PowerMock.verifyAll();
	}
	
public void testEditCharacterShouldPersistChangesWithProfile1(){
		
		EasyMock.expect(mockDb.profileNameExists("Tester", 1)).andReturn(false);
		testProfile.setAgility(4);
		testProfile.setLuck(0);
		EasyMock.expect(mockDb.saveProfile(ProfileEquals.eqProfile(testProfile))).andReturn(true);
		PowerMock.replayAll();
		assertTrue(solo.searchEditText("Tester"));
		solo.clickOnScreen(solo.getCurrentButtons().get(2));
		assertTrue(solo.searchButton("Submit"));
		ArrayList<Spinner> spinners = solo.getCurrentSpinners();
		for(Spinner spinner : spinners){
			if(spinner != null){
				assertTrue(spinner.getSelectedItemPosition() == 1);
			}
		}		
		for(int i = 0; i < spinners.size(); i++){
			if(spinners.get(i).getId() == com.uhl.R.id.spin_agility){
				solo.pressSpinnerItem(i, 0);
			}else if(spinners.get(i).getId() == com.uhl.R.id.spin_luck){
				solo.pressSpinnerItem(i, -1);
			}
		}
		assertFalse(solo.getCurrentActivity().isFinishing());
		solo.clickOnButton("Submit");
		assertTrue(solo.getCurrentActivity().isFinishing());
		
		PowerMock.verifyAll();
	}

}
