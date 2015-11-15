package core;

public class Rayo {
	Punto p0;
	Punto p1;
	
	public Rayo (Punto p0, Punto p1) {
		
	}
	
	public Punto ecuacionParametrica (double lambda) {
		Punto d = p1.resta(p0); 
		p0.suma(d.multiplicacionEscalar(lambda));
		return p0.suma(d.multiplicacionEscalar(lambda));
	}
}
