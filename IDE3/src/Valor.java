
/*
 * Class to help to calcule the value for the entropy formule
 */
public class Valor {
	String nombre;
	double totalPN;
	double p;
	double n;
	
	
	//Constructor
	public Valor(String nombre, int cantidad, double p, double n) {
		this.nombre=nombre;
		this.p = p;
		this.n = n;
		this.totalPN = p+n;
	}

				//Getters and Setters
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getTotalPN() {
		return totalPN;
	}

	

	public double getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public double getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setTotalPN(double totalPN) {
		this.totalPN = totalPN;
	}

	public void setP(double p) {
		this.p = p;
	}

	public void setN(double n) {
		this.n = n;
	}
	
}
