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
	public static ArrayList<Triangulo> leerFigura(String[] orden, Camara cam) {
		String fichero = orden[1];
		ArrayList<Transformacion> transformaciones = new ArrayList<Transformacion>();
		int red = 0;
		int green = 0;
		int blue = 0;
		double iRefl = 0;
		double iRefr = 0;
		double cRefr = 0;
		
		for (int i=2; i<orden.length; i++) {
			String[] partes = orden[i].split(":");
			String clave = partes[0];
			
			switch(clave) {
			case "escala":
				Double xEsc = Double.parseDouble(partes[1]);
				Double yEsc = Double.parseDouble(partes[2]);
				Double zEsc = Double.parseDouble(partes[3]);
				
				transformaciones.add(Transformacion.getMatrizEscala(xEsc, yEsc, zEsc));
				break;
			case "traslacion":
				Double xTras = Double.parseDouble(partes[1]);
				Double yTras = Double.parseDouble(partes[2]);
				Double zTras = Double.parseDouble(partes[3]);
				
				transformaciones.add(Transformacion.getMatrizTraslacion(xTras, yTras, zTras));
				break;
			case "giroX":
				Double ii = Double.parseDouble(partes[1]);
				
				transformaciones.add(Transformacion.getMatrizGiroX(ii));
				break;
			case "giroY":
				Double j = Double.parseDouble(partes[1]);
				
				transformaciones.add(Transformacion.getMatrizGiroY(j));
				break;
			case "giroZ":
				Double k = Double.parseDouble(partes[1]);
				
				transformaciones.add(Transformacion.getMatrizGiroZ(k));
				break;
			case "cR":
				red = Integer.parseInt(partes[1]);
				break;
			case "cG":
				green = Integer.parseInt(partes[1]);
				break;
			case "cB":
				blue = Integer.parseInt(partes[1]);
				break;
			case "iRefl":
				iRefl = Double.parseDouble(partes[1]);
				break;
			case "iRefr":
				iRefr = Double.parseDouble(partes[1]);
				break;
			case "cRefr":
				cRefr = Double.parseDouble(partes[1]);
				break;
			}
		}
		transformaciones.add(Transformacion.getMatrizCamaraMundo(cam));
		
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
					for (int i=0; i<transformaciones.size(); i++) {
						p1 = transformaciones.get(i).transformar(p1);
						p2 = transformaciones.get(i).transformar(p2);
						p3 = transformaciones.get(i).transformar(p3);
					}
					
					/* Crea un objeto Triangulo */
					Triangulo t = new Triangulo(p1,p2,p3,new Color(red,green,blue),iRefl,iRefr,cRefr);
					t.AD = true;
					t.AE = true;
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