import java.util.Comparator;

/*
 * On this class we save and calculated every attribute information and property
 */
public class Atributo {
	String nombre;
	Valor[] valores;
	
	public Atributo(String nombre, Valor[] valores) {
		this.nombre = nombre;
		this.valores = valores;
	}

	
	
				//Getters and Setters
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Valor[] getValores() {
		return valores;
	}

	public void setValores(Valor[] valores) {
		this.valores = valores;
	}
	
	
	//Formule to calculated the merito of one attribute
	public double merito() {
		int tamanio= this.valores.length;
		double merito=0;
		double N=0; double Ri[]=new double[tamanio];
		for (int i=0; i<tamanio; i++)
			N += valores[i].getTotalPN();
		System.out.println("N:"+N);
		for (int i=0; i<tamanio; i++) {
			Ri[i]=(valores[i].getTotalPN()) / N;
			System.out.println("valores de "+i+" = "+ valores[i].getTotalPN());
			System.out.println("valores de P "+i+" = "+ valores[i].getP());
			System.out.println("valores de N "+i+" = "+ valores[i].getN());
			System.out.println("Ri: "+ Ri[i]);
		}
		System.out.println("--------------\ncalculo del merito:");
		for (int i=0; i<tamanio; i++) {
			double inforD= infor(i);
			merito +=  Ri[i]*inforD;
			System.out.println("merito+= "+ Ri[i] +" * "+ inforD);
		}
		System.out.println("Merito total:");
		
		return merito;
	}
	
	
	  //Method to calculated the quantity of information which one attribute can have
		public double infor(int i){
			double infor=0; double p,n;
			p= this.valores[i].getP()/(this.valores[i].getTotalPN());
			n= this.valores[i].getN()/(this.valores[i].getTotalPN());
			if ((p==0) &&( n == 0)) return 0;
			else if (p==0) return  -n*(Math.log(n)/Math.log(2));
				else if (n==0) return -p* (Math.log(p)/Math.log(2));	
			infor=(-p* (Math.log(p)/Math.log(2))- n*(Math.log(n)/Math.log(2)));
			System.out.println("i= "+i);
			System.out.println("p= "+ p);
			System.out.println("n= "+ n);
			System.out.println("infor de " +i+" = " + -p* (Math.log(p)/Math.log(2)) +" + "+
			-n*(Math.log(n)/Math.log(2)) +" = " + infor);
		
			return infor;
		}	
}

//Re-implementation of Comparator interface to have orden comparation
class meritoComparator implements Comparator<Atributo>
{
    public int compare(Atributo c1, Atributo c2)
    {
        return Double.compare(c1.merito(), c2.merito());
    }
}
