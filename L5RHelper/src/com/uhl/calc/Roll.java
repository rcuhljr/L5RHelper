package com.uhl.calc;


public class Roll {

	private int rolled;
	private int kept;
	private int luck;
	private int gp;
	private int statMod;

	public Roll(int rolled, int kept, int luck, int gp){
		this(rolled, kept, 0, luck, gp);
	}
	
	public Roll(int rolled, int kept){
		this(rolled, kept, 0, 0, 0);
	}
	
	public Roll(int rolled, int kept, int statMod, int luck, int gp) {
		setRolled(rolled);
		setKept(kept);
		setLuck(luck);
		setGp(gp);
		setStatMod(statMod);
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
		return Math.min(rolled,10);
	}

	public void setKept(int kept) {
		this.kept = kept;
	}

	public int getKept() {
		int inflow = Math.max(this.rolled - 10,0);
		int val = this.kept + (inflow)/2;		
		return Math.min(val, 10);
	}

	public void setStatMod(int statMod) {
		this.statMod = statMod;
	}

	public int getStatMod() {
		int inflow = Math.max(this.rolled - 10,0);
		int val = this.kept + (inflow)/2;
		val = Math.max(val-10, 0)*5;		
		return val + this.statMod;
	}
	
	public String toString(){
		int mod = this.getStatMod();
		if(mod > 0){		
			return this.getRolled()+"K"+this.getKept()+"+"+this.getStatMod();
		}else if (mod < 0){
			return this.getRolled()+"K"+this.getKept()+this.getStatMod();
		}else{		
			return this.getRolled()+"K"+this.getKept();
		}
	}	
}
