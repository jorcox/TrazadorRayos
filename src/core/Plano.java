package core;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Plano extends Objeto{
	
	private Point3d p;
	private Vector3d n;
	
	public Plano (Point3d p, Vector3d n, Color color) {
		super(color);
		this.p = p;
		this.n = n;
	}
	
	public Vector3d getNormal() {
		return n;
	}

	public Point3d interseccion(Rayo r) {
		Vector3d d = r.getD();
		Point3d p = this.p;
		double inf = d.dot(this.n);
		if (inf == 0) {
			/*
			 * El rayo no intersecta, se devuelve null
			 */
			return null;
		} else if (inf > 0) {
			/*
			 * El plano esta de espaldas al ojo, suponiendo que solo
			 * se ver por una cara, entonces este no se ve
			 */
			return null;
		} else {
			/*
			 * La normal del plano y el rayo van en sentidos inveros, entonces 
			 * intersectan
			 */
			p.sub(r.getP0());
			Vector3d vec = new Vector3d(p);
			double sup = vec.dot(n);
			return r.getPunto(sup/inf);
		}
	}
}
