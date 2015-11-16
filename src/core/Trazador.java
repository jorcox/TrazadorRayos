package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
		
		/* Lanzar rayos desde cada pixel (doble jarl recorriendo pantalla) */
	}
	
}
