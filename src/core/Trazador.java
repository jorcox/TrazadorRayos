package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Trazador {
	
	public static void main(String[] args) {
		/* Procesar fichero y crear objetos */
		if (args[0]!=null) {
			try {
				File fichero = new File(args[0]);
				BufferedReader reader = new BufferedReader(new FileReader(fichero));
				String linea = reader.readLine();
				while (linea!= null) {
					
				}
			} catch (FileNotFoundException e) {
				System.out.println("Error: fichero de datos no encontrado");
				System.exit(1);
			} catch (Exception e) {
				System.out.println("Error: problema con el fichero de datos");
				System.exit(1);
			}
		} else {
			System.out.println("Error: no ha introducido un fichero de datos");
			System.exit(1);
		}
		/* Crear componentes (pantalla, etc) */
		Pantalla p = new Pantalla(300, 300, 20, 1920,1080);
		Point3d g = new Point3d(1,1,1);
		Vector3d v = new Vector3d(-1,-3,2);
		Camara c = new Camara(g,v);
		p.calcularCoordenadasCamaraYMundo(c);
		
		/* Lanzar rayos desde cada pixel (doble jarl recorriendo pantalla) */
		for (int i = 0; i < p.getnC(); ++i) {
			for (int j = 0; j < p.getnR(); ++j) {
				// Creamos el rayo primario del pixel i,j
				Rayo rayoPrimario = new Rayo(c.getE(),p.coordMundo[i][j]);
				// Disparamos el rayo primario a la escena y se comprueba si intersecta
			 
			}
		} 
	}
}
