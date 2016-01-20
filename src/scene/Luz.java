package scene;

import javax.vecmath.Point3d;

/**
 * 
 * @author Javier Beltran Jorba
 * @author Jorge Cancer Gil
 * 
 * Una Luz define cada uno de los focos de luz de
 * la escena en un punto y con un brillo.
 * 
 */
public class Luz {
	
	private Point3d punto;
	private double brillo;
	
	/**
	 * Crea una luz en una ubicacion y con un brillo.
	 */
	public Luz(Point3d punto, double brillo) {
		this.punto = punto;
		this.brillo = brillo;
	}

	public Point3d getPunto() {
		return punto;
	}

	public double getBrillo() {
		return brillo;
	}
	
	
}
