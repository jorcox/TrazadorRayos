package core;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Triangulo extends Objeto{
	private Point3d p1;
	private Point3d p2;
	private Point3d p3;
	private Vector3d n;
	

	public Triangulo(Point3d p1, Point3d p2, Point3d p3, Color color) {
		super(color);
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		/*
		 * Calculo de la normal n
		 */
		Point3d aux2 = p2;
		Point3d aux3 = p3;
		aux2.sub(p1);
		aux3.sub(p1);
		Vector3d vec12 = new Vector3d(aux2);
		Vector3d vec13 = new Vector3d(aux3);
		n.cross(vec12, vec13);
	}
	
	@Override
	public Point3d interseccion(Rayo r) {
		Vector3d d = r.getD();
		Point3d p = this.p1;
		double inf = d.dot(this.n);
		if (inf == 0) {
			/*
			 * El rayo no intersecta, se devuelve null
			 */
			return null;
		} else if (inf > 0) {
			/*
			 * El triangulo esta de espaldas al ojo, suponiendo que solo
			 * se ver por una cara, entonces este no se ve
			 */
			return null;
		} else {
			/*
			 * La normal del triangulo y el rayo van en sentidos inveros, entonces 
			 * intersectan
			 */
			p.sub(r.getP0());
			Vector3d vec = new Vector3d(p);
			double sup = vec.dot(n);
			return r.getPunto(sup/inf);
		}
	}

}
