package com.atm.model;

public class Atm {

	private int totalCash;

	private int numOfFiftys;

	private int numOfTwentys;

	private int numOfTens;

	private int numOfFives;

	public Atm(int totalCash, int numOfFiftys, int numOfTwentys, int numOfTens, int numOfFives) {
		this.totalCash = totalCash;
		this.numOfFiftys = numOfFiftys;
		this.numOfTwentys = numOfTwentys;
		this.numOfTens = numOfTens;
		this.numOfFives = numOfFives;
	}

	public int getTotalCash() {
		return this.totalCash;
	}

	public void setTotalCash(int tc) {
		this.totalCash = tc;
	}

	public int getNumOfFiftys() {
		return this.numOfFiftys;
	}

	public void setNumOfFiftys(int numFifty) {
		this.numOfFiftys = numFifty;
	}

	public int getNumOfTwentys() {
		return this.numOfTwentys;
	}

	public void setNumOfTwentys(int numTwenty) {
		this.numOfTwentys = numTwenty;
	}

	public int getNumOfTens() {
		return this.numOfTens;
	}

	public void setNumOfTens(int numTen) {
		this.numOfTens = numTen;
	}

	public int getNumOfFives() {
		return this.numOfFives;
	}

	public void setNumOfFives(int numFive) {
		this.numOfFives = numFive;
	}

}
