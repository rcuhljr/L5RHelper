package com.uhl.db;

public enum DefaultViews {
	melee(1), caster(2);
	private int viewId;
	private DefaultViews(int id) {
		this.viewId = id;
	}
	
	public int getId(){
		return this.viewId;
	}
	
}
