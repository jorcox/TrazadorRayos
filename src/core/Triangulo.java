package core;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Triangulo extends Objeto{
	private Point3d p1;
	private Point3d p2;
	private Point3d p3;
	private Vector3d n;
	

	public Triangulo(Point3d p1, Point3d p2, Point3d p3, Color kd) {
		super(kd);
		this.p1 = new Point3d(p1);
		this.p2 = new Point3d(p2);
		this.p3 = new Point3d(p3);
		/*
		 * Calculo de la normal n
		 */
		Point3d aux2 = new Point3d(this.p2);
		Point3d aux3 = new Point3d(this.p3);
		
		aux2.sub(p1);
		aux3.sub(p1);
		Vector3d vec12 = new Vector3d(aux2);
		Vector3d vec13 = new Vector3d(aux3);
		n = new Vector3d(vec12);
		n.cross(vec12, vec13);
		super.setN(n);
	}
	
	@Override
	public Point3d interseccion(Rayo r) {
		Vector3d d = new Vector3d(r.getD());
		Point3d p = new Point3d(this.p1);
		double inf = d.dot(this.n);
		if (inf < 0){
			/*
			 * La normal del triangulo y el rayo van en sentidos inveros, entonces 
			 * intersectan
			 */
			p.sub(r.getP0());
			Vector3d vec = new Vector3d(p);
			double sup = vec.dot(n);
			p = new Point3d(r.getPunto(sup/inf));
			Point3d p1 = new Point3d(this.p1);
			Point3d p2 = new Point3d(this.p2);
			Point3d p3 = new Point3d(this.p3);
			p2.sub(p1);
			p.sub(p1);
			Vector3d a = new Vector3d(p2);
			Vector3d b = new Vector3d(p);
			Vector3d mPN = new Vector3d();
			mPN.cross(a, b);
			double s1 = mPN.dot(n);
			p1 = new Point3d(this.p1);
			p2 = new Point3d(this.p2);
			p3 = new Point3d(this.p3);
			p = new Point3d(r.getPunto(sup/inf));
			p3.sub(p2);
			p.sub(p2);
			a = new Vector3d(p3);
			b = new Vector3d(p);
			mPN = new Vector3d();
			mPN.cross(a, b);
			double s2 = mPN.dot(n);
			p1 = new Point3d(this.p1);
			p2 = new Point3d(this.p2);
			p3 = new Point3d(this.p3);
			p = new Point3d(r.getPunto(sup/inf));
			p1.sub(p3);
			p.sub(p3);
			a = new Vector3d(p1);
			b = new Vector3d(p);
			mPN = new Vector3d();
			mPN.cross(a, b);
			double s3 = mPN.dot(n);
			if ((s1>0&&s2>0&&s3>0)||(s1<0&&s2<0&&s3<0))
				return p = new Point3d(r.getPunto(sup/inf));
			else
				return null;
		} else {
			/*
			 * El rayo no intersecta, se devuelve null ( inf == 0)
			 * o 
			 * El triangulo esta de espaldas al ojo, suponiendo que solo
			 * se ver por una cara, entonces este no se ve (inf > 0)
			 */
			return null;
		}
	}
	
	public Vector3d getN(){
		return this.n;
	}

}
