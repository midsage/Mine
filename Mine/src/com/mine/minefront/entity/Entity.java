package com.mine.minefront.entity;

public class Entity {
	
	public double x, z;
	protected boolean removed = false;
	
	protected Entity() {
		
		
	}
	
	public void removed() {
		removed = true;
	}
	
	public void tick() {
		
	}
	
	
	
	
	
	
}