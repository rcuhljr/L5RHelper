package com.uhl;


public class L5RUtilites  {   
    //actually ended up not needing this once I wrote it.
	//Oops, String::equalsIgnoreCase();
	public static boolean caseInsensitive(Object a, Object b){
		return a.toString().toUpperCase().equals(b.toString().toUpperCase());
	}

}
