package com.uhl.calc;


public class Roll {

	private int rolled;
	private int kept;
	private int luck;
	private int gp;

	public Roll(int rolled, int kept, int luck, int gp) {
		setRolled(rolled);
		setKept(kept);
		setLuck(luck);
		setGp(gp);
	}

	public void setGp(int gp) {
		this.gp = gp;
		
	}
	
	public int getGp(){
		return gp;
	}	

	public void setLuck(int luck) {
		this.luck = luck;
	}
	
	public int getLuck(){
		return luck;
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
}
