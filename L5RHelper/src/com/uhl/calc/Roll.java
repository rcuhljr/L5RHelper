package com.uhl.calc;


public class Roll {

	private int rolled;
	private int kept;

	public Roll(int rolled, int kept) {
		setRolled(rolled);
		setKept(kept);
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
