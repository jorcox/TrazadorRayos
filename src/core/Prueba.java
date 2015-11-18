package core;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Prueba {

	public static void main(String[] args) {
//		Pantalla p = new Pantalla(200, 200, 20, 1920,1080);
//		Point3d g = new Point3d(1,1,1);
//		Vector3d v = new Vector3d(-1,-3,2);
//		Camara c = new Camara(g,v);
//		p.calcularCoordenadasCamaraYMundo(c);
//		System.out.println("melon");
		
		Esfera esf = new Esfera(2, new Point3d(0,0,0), new Color(255,0,0), 1);
		Rayo r = new Rayo(new Point3d(1,1,-5), new Point3d(-1,-1,5));
		Point3d inter = esf.interseccion(r);
		System.out.println(inter);
	}
}
