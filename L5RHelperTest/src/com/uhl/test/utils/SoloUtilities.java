package com.uhl.test.utils;

import java.util.ArrayList;
import java.util.Hashtable;

import android.widget.Spinner;

import com.jayway.android.robotium.solo.Solo;

public class SoloUtilities {
	
	public static void moveSpinners(Solo solo, ArrayList<Spinner> spinners, Hashtable<Integer, Integer> moves) {		
		for(int i = 0; i < spinners.size(); i++){
			if(moves.containsKey(spinners.get(i).getId())){
				solo.pressSpinnerItem(i, moves.get(spinners.get(i).getId()));				
			}
		}
	}

}
