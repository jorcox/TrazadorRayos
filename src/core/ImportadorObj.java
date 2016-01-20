package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.vecmath.Point3d;
import objects.Triangulo;
import scene.Camara;

/**
 * 
 * @author Javier Beltran Jorba
 * @author Jorge Cancer Gil
 * 
 * Clase auxiliar para importar los objetos complejos del fichero
 * a la escena. Un objeto complejo es aquel que se encuentra
 * almacenado en un fichero .obj y definido por triangulos.
 *
 */
public class ImportadorObj {

	/**
	 * Dado un fichero .obj en el que se define un objeto en base a
	 * triangulos, devuelve una lista de objetos Triangulo tal cual
	 * estaban definidos en el fichero.
	 */
	public static ArrayList<Triangulo> leerFigura(String fichero, Camara cam) {
		ArrayList<Triangulo> caras = new ArrayList<Triangulo>();
		ArrayList<Point3d> vertices = new ArrayList<Point3d>();
		vertices.add(null);
		try {
			Scanner obj = new Scanner(new File(fichero));
			while (obj.hasNextLine()) {
				String v = obj.next();
				switch (v) {
				case "v":
					/* Lee un vertice */
					vertices.add(new Point3d(Float.parseFloat(obj.next()), Float.parseFloat(obj.next()),
							Float.parseFloat(obj.next())));
					break;
				case "f":
					/* Crea un triangulo a partir de 3 vertices leidos previamente */
					int v1 = leerV(obj.next());
					int v2 = leerV(obj.next());
					int v3 = leerV(obj.next());
					Point3d p1 = new Point3d(vertices.get(v1));
					Point3d p2 = new Point3d(vertices.get(v2));
					Point3d p3 = new Point3d(vertices.get(v3));
					
					/* Realiza las transformaciones tridimensionales indicadas */
					Transformacion aumento = Transformacion.getMatrizEscala(0.3, 0.3, 0.3);
					Transformacion lejos = Transformacion.getMatrizTraslacion(1, -70, -1700);
					Transformacion camaraMundo = Transformacion.getMatrizCamaraMundo(cam);
					Transformacion giroX = Transformacion.getMatrizGiroX(15);
					p1 = giroX.transformar(p1);
					p2 = giroX.transformar(p2);
					p3 = giroX.transformar(p3);

					p1 = aumento.transformar(p1);
					p2 = aumento.transformar(p2);
					p3 = aumento.transformar(p3);
					
					p1 = lejos.transformar(p1);
					p2 = lejos.transformar(p2);
					p3 = lejos.transformar(p3);

					p1 = camaraMundo.transformar(p1);
					p2 = camaraMundo.transformar(p2);
					p3 = camaraMundo.transformar(p3);
					
					/* Crea un objeto Triangulo */
					Triangulo t = new Triangulo(p1,p2,p3,new Color(200,200,200),0,0,1.3333333333);
					caras.add(t);
					break;
				}
				try {
					obj.nextLine();
				} catch (Exception e) {
				}
			}
			obj.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return caras;
	}

	/**
	 * Metodo auxiliar para leer cada vertice a partir de la
	 * sintaxis del formato .obj.
	 */
	private static int leerV(String v1) {
		String lv1[] = v1.split("/");

		return Integer.parseInt(lv1[0]);

	}
}