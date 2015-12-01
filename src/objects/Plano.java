package objects;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import core.Color;
import scene.Rayo;

public class Plano extends Objeto{
	
	private Point3d p;
	
	public Plano (Point3d p, Vector3d n, Color kd, double reflex, double iRefrac, double cRefrac) {
		super(kd, n, reflex, iRefrac, cRefrac);
		this.p = p;
		super.setN(n);
	}

	public Point3d interseccion(Rayo r) {
		Vector3d d = r.getD();
		Point3d p = new Point3d(this.p);
		double inf = d.dot(super.getN(p));
		if (inf < 0){
			/*
			 * La normal del plano y el rayo van en sentidos inveros, entonces 
			 * intersectan
			 */
			p.sub(r.getP0());
			Vector3d vec = new Vector3d(p);
			double sup = vec.dot(super.getN(p));
			return r.getPunto(sup/inf);
		} else {
			/*
			 * El rayo no intersecta, se devuelve null ( inf == 0)
			 * o 
			 * El plano esta de espaldas al ojo, suponiendo que solo
			 * se ver por una cara, entonces este no se ve (inf > 0)
			 */
			return null;
		}		
	}
}
