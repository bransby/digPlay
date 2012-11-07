package com.businessclasses;



public enum Route {
	NO_ROUTE,
	FLY,
	IN,
	OUT,
	SLANT,
	POST,
	SKINNY_POST, 
	FLAG,
	STOP_AND_GO, 
	ARROW,
	WHEEL,
	FADE;

	@Override
	public String toString(){
		if(this.equals(STOP_AND_GO)){
			return "STOP AND GO";
		} else if(this.equals(SKINNY_POST)){
			return "SKINNY POST";
		} else if(this.equals(NO_ROUTE)){
			return "NO ROUTE";
		}
		return super.toString();
	}
}
