package core;

/**
 * 
 * @author Javier Beltran Jorba
 * @author Jorge Cancer Gil
 * 
 * Esta excepcion salta cuando aparece un error en el fichero
 * de entrada de datos (por ejemplo, el usuario ha utilizado
 * una sintaxis equivocada).
 *
 */
public class FicheroDatosException extends Exception {

	private static final long serialVersionUID = 1L;

	public FicheroDatosException(String e) {
		super(e);
	}
}
