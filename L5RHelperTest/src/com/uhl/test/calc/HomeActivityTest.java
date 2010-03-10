package com.uhl.test.calc;



import java.util.ArrayList;

import org.easymock.EasyMock;
import org.powermock.api.easymock.PowerMock;

import com.jayway.android.robotium.solo.Solo;
import com.uhl.HomeActivity;
import com.uhl.db.DBServiceLocator;
import com.uhl.db.IDBHelper;


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
	
	public void testCreateCharacterWorks(){		 
		EasyMock.expect(mockDb.profileNameExists("A Test Name", -1)).andReturn(true);
		PowerMock.replayAll();
		solo.clickOnButton("Create New Character");
		assertTrue(solo.searchText("Character Name"));
		solo.enterText(solo.getCurrentEditTexts().get(0).getId(), "A Test Name");
		ArrayList<View> views = solo.getViews();
		solo.clickOnScreen(views.get(9));	
		solo.clickOnScreen(solo.getCurrentButtons().get(0));
		assertTrue(solo.searchEditText("Name in use"));
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
