package core;

import javax.vecmath.Point3d;

public class Pixel {
	private Point3d centro;
	private Color color;
	
	public Pixel(Point3d centro) {
		this.centro = centro;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public Point3d getPunto(){
		return centro;
	}
}