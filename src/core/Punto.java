package core;

public class Punto {
	private double componenteX;
	private double componenteY;
	private double componenteZ;
	
	public Punto(double x, double y, double z){
		this.componenteX = x;
		this.componenteY = y;
		this.componenteZ = z;
	}
	
	public Punto(Punto nuevo){
		
	}

	/**
	 * @return the componenteX
	 */
	public double getComponenteX() {
		return componenteX;
	}

	/**
	 * @param componenteX the componenteX to set
	 */
	public void setComponenteX(double componenteX) {
		this.componenteX = componenteX;
	}

	/**
	 * @return the componenteY
	 */
	public double getComponenteY() {
		return componenteY;
	}

	/**
	 * @param componenteY the componenteY to set
	 */
	public void setComponenteY(double componenteY) {
		this.componenteY = componenteY;
	}

	/**
	 * @return the componenteZ
	 */
	public double getComponenteZ() {
		return componenteZ;
	}

	/**
	 * @param componenteZ the componenteZ to set
	 */
	public void setComponenteZ(double componenteZ) {
		this.componenteZ = componenteZ;
	}
	
	/**
	 * @return el resultado de restar a este vector el 
	 * vector del @param otro
	 */
	public Punto resta(Punto otro) {
		return new Punto(this.componenteX - otro.getComponenteX(),
						 this.componenteY - otro.getComponenteY(),
						 this.componenteZ - otro.getComponenteZ());
	}
	
	/**
	 * @return el resultado de sumar a este vector el 
	 * vector del @param otro
	 */
	public Punto suma(Punto otro) {
		return new Punto(this.componenteX + otro.getComponenteX(),
						 this.componenteY + otro.getComponenteY(),
						 this.componenteZ + otro.getComponenteZ());
	}
	
	/**
	 * @return el resultado de restar a este vector el 
	 * vector del @param otro
	 */
	public Punto multiplicacionEscalar(double escalar) {
		return new Punto(this.componenteX * escalar,
						 this.componenteY * escalar,
						 this.componenteZ * escalar);
	}
}
