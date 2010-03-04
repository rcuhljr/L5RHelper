package com.uhl.db;

import android.database.Cursor;

public class Profile {
	
	private int _id = -1;
	private String name;
	private int defaultViewId =1;
	private int earthRing =3;
	private int waterRing =3;
	private int fireRing =3;
	private int airRing =3;
	private int voidRing =3;
	private int reflexes =3;
	private int agility =3;
	private int luck =0;
	private int gp =0;
	
	public Profile(String name, int defaultView){
		this.setName(name);
		this.setDefaultViewId(defaultView);
	}
	
	public Profile(Cursor cursor){
		setId(cursor.getInt(0));
		setName(cursor.getString(1));
		setDefaultViewId(cursor.getInt(2));
		setEarthRing(cursor.getInt(3));
		setWaterRing(cursor.getInt(4));
		setFireRing(cursor.getInt(5));
		setAirRing(cursor.getInt(6));
		setVoidRing(cursor.getInt(7));
		setReflexes(cursor.getInt(8));
		setAgility(cursor.getInt(9));
		setLuck(cursor.getInt(10));
		setGp(cursor.getInt(11));
		cursor.close();
	}
	
	public void SetStats(int eRing, int wRing, int fRing, int aRing, int vRing, int ref, int agi, int luck, int gp){
		this.setEarthRing(eRing);
		this.setWaterRing(wRing);
		this.setFireRing(fRing);
		this.setAirRing(aRing);
		this.setVoidRing(vRing);
		this.setReflexes(ref);
		this.setAgility(agi);
		this.setLuck(luck);
		this.setGp(gp);
	}

	public void setId(int _id) {
		this._id = _id;
	}

	public int getId() {
		return _id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDefaultViewId(int defaultViewId) {
		this.defaultViewId = defaultViewId;
	}

	public int getDefaultViewId() {
		return defaultViewId;
	}

	public void setEarthRing(int earthRing) {
		this.earthRing = earthRing;
	}

	public int getEarthRing() {
		return earthRing;
	}

	public void setWaterRing(int waterRing) {
		this.waterRing = waterRing;
	}

	public int getWaterRing() {
		return waterRing;
	}

	public void setFireRing(int fireRing) {
		this.fireRing = fireRing;
	}

	public int getFireRing() {
		return fireRing;
	}

	public void setAirRing(int airRing) {
		this.airRing = airRing;
	}

	public int getAirRing() {
		return airRing;
	}

	public void setVoidRing(int voidRing) {
		this.voidRing = voidRing;
	}

	public int getVoidRing() {
		return voidRing;
	}

	public void setReflexes(int reflexes) {
		this.reflexes = reflexes;
	}

	public int getReflexes() {
		return reflexes;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getAgility() {
		return agility;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public int getLuck() {
		return luck;
	}

	public void setGp(int gp) {
		this.gp = gp;
	}

	public int getGp() {
		return gp;
	}
}
