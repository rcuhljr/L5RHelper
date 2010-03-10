package com.uhl.db;

import android.database.Cursor;

public class Template {
	
	private int _id = -1;
	private int profileId = -1;
	private String name;	
	private int reflexes =0;
	private int agility =0;
	private int useReflexes = 0;
	private int modifier = 0;
	private int rolled = 0;
	private int kept = 0;
	private int isGp =0;
	private int skillRank = 0;
	private int castingRing = 0;
	
	public Template(int profileId){
		setProfileId(profileId);		
	}
	
	public Template(Cursor cursor){
		setId(cursor.getInt(0));
		setProfileId(cursor.getInt(1));
		setName(cursor.getString(2));		
		setReflexes(cursor.getInt(3));
		setAgility(cursor.getInt(4));
		setUseReflexes(cursor.getInt(5));
		setSkillRank(cursor.getInt(6));
		setisGp(cursor.getInt(7));
		setModifier(cursor.getInt(8));
		setRolled(cursor.getInt(9));
		setKept(cursor.getInt(10));
		setCastingRing(cursor.getInt(11));
	}

	public void setId(int _id) {
		this._id = _id;
	}

	public int getId() {
		return _id;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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

	public void setisGp(int isGp) {
		this.isGp = isGp;
	}

	public int getisGp() {
		return isGp;
	}

	public void setUseReflexes(int useReflexes) {
		this.useReflexes = useReflexes;
	}

	public int getUseReflexes() {
		return useReflexes;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public int getModifier() {
		return modifier;
	}

	public void setRolled(int rolled) {
		this.rolled = rolled;
	}

	public int getRolled() {
		return rolled;
	}

	public void setKept(int kept) {
		this.kept = kept;
	}

	public int getKept() {
		return kept;
	}

	public void setSkillRank(int skillRank) {
		this.skillRank = skillRank;
	}

	public int getSkillRank() {
		return skillRank;
	}

	public void setCastingRing(int castingRing) {
		this.castingRing = castingRing;
	}

	public int getCastingRing() {
		return castingRing;
	}
}
