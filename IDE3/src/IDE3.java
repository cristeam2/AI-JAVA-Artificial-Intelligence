
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Class to analyze the tree structure to find a solution
 */
public class IDE3 {
	 private  static ArrayList<String> listaDeAtributos;
	 private static ArrayList<String>[]  listaDeEjemplos;
	 private static int lineas=0;
	 private static int elementosPorFila=0;
	 private static List<Atributo> meritos;
	 private static ArrayList<String>[]  allLines;
	 
	 //Attribute Matrix array [i] with an ArrayList <> values
	 //Matriz de array Atributo[i] con un ArrayList<> de valores  
	 private static ArrayList<String>[]  listaDeValoresDeCadaAtributo;
	
	 //This array is saved all attributes with all your values with your merits, p, Total ny
	 //Guardo en este array todos los atributos con todos sus valores con su meritos,p,n y total
	 private static Atributo[] atributo;
	 
	 // Array where there are positive, negative and total cases for each value of each attribute
	 // Array donde estan los casos positivos, negativos y totales de cada valor de cada atributo
	 private static  Valor[] valor;

	 
	 
/*
 * Load the array (ArrayList <String> []) for all values of each attribute
 * is an array with ArrayLists <> on each element
 *
 * 
 * Carga la matriz  (ArrayList<String>[]) de todos los valores de cada atributo
 * es un array con Arraylists<> en cada elemento
 */
	@SuppressWarnings("unchecked")
	public static void cargarValoresDeCadaAtributo(){
		//creo el array del tamanio segun la cantidad de atributos que leo del fichero
		listaDeValoresDeCadaAtributo= new ArrayList[listaDeAtributos.size()];
		for (int i =0; i<listaDeAtributos.size();i++) 
			listaDeValoresDeCadaAtributo[i]=new ArrayList<String>();
			//for para leer todas las lineas
			for (int i =0; i<lineas-1;i++) 
				//for para leer cada columna
				for (int j=0; j<elementosPorFila; j++) 
					if (!listaDeValoresDeCadaAtributo[j].contains(listaDeEjemplos[i].get(j)))
						listaDeValoresDeCadaAtributo[j].add(listaDeEjemplos[i].get(j)); 		
	}
	
	
	/*
	 *Method to see the positive, negative and overall values (R)
	 *of each value of each attribute
	 * 
	 * Metodo para saber los valores positivos, negativos y total (R) 
	 * de cada valor de cada atributo
	 */
	public static void dameRepeticionesPyN() {
		//contadores para obtener los valores positivos, negativos y totales
		// counter for positive values, negative and total
		int contR=0,contP=0,contN=0; 
		boolean comparador; 
		int numeroDeAtributos=listaDeAtributos.size();
		atributo=new Atributo[numeroDeAtributos];
		System.out.println("Lista de atributos:");
		for (int k=0; k<numeroDeAtributos;k++){
			int numeroDeValoresDelAtributo= listaDeValoresDeCadaAtributo[k].size();
			valor=new Valor[numeroDeValoresDelAtributo];
		  for (int j =0; j<listaDeValoresDeCadaAtributo[k].size() ;j++){
			  for (int i =0; i<lineas-1;i++) {
				comparador = listaDeValoresDeCadaAtributo[k].get(j).contains(listaDeEjemplos[i].get(k));
				if	(comparador)
					contR++;
				if	(comparador && listaDeEjemplos[i].get(numeroDeAtributos-1).equalsIgnoreCase("si"))
					contP++;
				if	(comparador && listaDeEjemplos[i].get(numeroDeAtributos-1).equalsIgnoreCase("no"))				
					contN++;		
			  }
	   	  System.out.println("Atributo= "+listaDeAtributos.get(k));
	   	  System.out.println("Valor= "+ listaDeValoresDeCadaAtributo[k].get(j));
	   	  String nombreValor=listaDeValoresDeCadaAtributo[k].get(j);
	   	  valor[j]= new Valor(nombreValor,contR,contP,contN);
	   	  System.out.println("N= "+contR);
	   	  System.out.println("p= "+contP);
	   	  System.out.println("n= "+contN);
	   	  contR=0; contN=0; contP=0;
		  }
		atributo[k]=new Atributo(listaDeAtributos.get(k),valor); 
		}
		meritos = Arrays.asList(atributo.clone());
		meritoComparator mC = new meritoComparator();
		meritos.sort(mC);
		System.out.println("---------------------\n"+"calculos para : "+ atributo[0].nombre );
		System.out.println(atributo[0].merito());
	}
	
	
	/*
	 * Load the file play an ArrayList <String> [] called listaDeEjemplos;
	 * 
	 * Carga del fichero juego  un ArrayList<String>[]  llamado listaDeEjemplos;
	 */
	@SuppressWarnings("unchecked")
	public static void readAJuegoTxt() throws IOException {
			DataInputStream in = new DataInputStream(new FileInputStream("Juego.txt"));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine=br.readLine() ;
			String[] argumentos  = strLine.split(",");
			int sizeArgumentos= argumentos.length;

			ArrayList<String> temp = new ArrayList<String>();
			while ( strLine != null) {
				argumentos  = strLine.split(",");
				for (int i = 0; i < argumentos.length; i++) {
	 				temp.add(argumentos[i]);
					}
				lineas++;
				strLine = br.readLine();
			}
			elementosPorFila = (temp.size()-1)/(lineas-1);
			listaDeEjemplos= new ArrayList[lineas-1];
			for (int j=0; j<temp.size(); j++)
					if (j*sizeArgumentos+sizeArgumentos>temp.size());
					else 
						listaDeEjemplos[j]=new ArrayList<String>(temp.subList(j*sizeArgumentos,(j*sizeArgumentos)+sizeArgumentos));	
			in.close(); 
	}
	
	
/*
 * Load File Attributes list of attributes gambling
 * 
 * Carga del fichero Atributos juego la lista de atributos
 */
	public static void readAtributosJuegoTxt() throws IOException {
			listaDeAtributos= new ArrayList<String>();
			FileInputStream fstream = new FileInputStream("AtributosJuego.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = br.readLine();
			String[] argumentos = strLine.split(",");		
			for (int i = 0; i < argumentos.length; i++) {
				listaDeAtributos.add(argumentos[i]);
			}
			in.close();	
	}
	

	/*
	 * Read all the giving structure file to know his properties
	 */
	@SuppressWarnings("unchecked")
	public static void readAll() throws IOException {
		DataInputStream in = new DataInputStream(new FileInputStream("Juego.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine=br.readLine() ;
		String[] argumentos  = strLine.split(",");
		
		int sizeArgumentos= argumentos.length;

		ArrayList<String> temp = new ArrayList<String>();
		while ( strLine != null) {
			argumentos  = strLine.split(",");
			for (int i = 0; i < argumentos.length; i++) {
 				temp.add(argumentos[i]);
				}
			strLine = br.readLine();
		}
		allLines= new ArrayList[lineas+1];
		ArrayList<String> aS = new ArrayList<String>();
		for(int i=0; i< argumentos.length; i++){
			aS.add(argumentos[i]);
		}
		allLines[0] = listaDeAtributos;
		for (int j=0; j<temp.size(); j++)
				if (j*sizeArgumentos+sizeArgumentos>temp.size());
				else 
					allLines[j+1]=new ArrayList<String>(temp.subList(j*sizeArgumentos,(j*sizeArgumentos)+sizeArgumentos));	
		in.close(); 
}
	
				//Getters and Setters
	
	public static ArrayList<String> getListaDeAtributos() {
		return listaDeAtributos;
	}

	public static void setListaDeAtributos(ArrayList<String> listaDeAtributos) {
		IDE3.listaDeAtributos = listaDeAtributos;
	}

	public static ArrayList<String>[] getListaDeEjemplos() {
		return listaDeEjemplos;
	}

	public static void setListaDeEjemplos(ArrayList<String>[] listaDeEjemplos) {
		IDE3.listaDeEjemplos = listaDeEjemplos;
	}

	public static int getLineas() {
		return lineas;
	}

	public static void setLineas(int lineas) {
		IDE3.lineas = lineas;
	}

	public static int getElementosPorFila() {
		return elementosPorFila;
	}

	public static void setElementosPorFila(int elementosPorFila) {
		IDE3.elementosPorFila = elementosPorFila;
	}

	public static ArrayList<String>[] getlistaDeValoresDeCadaAtributo() {
		return listaDeValoresDeCadaAtributo;
	}

	public static void setlistaDeValoresDeCadaAtributo(
			ArrayList<String>[] listaDeValoresDeCadaAtributo) {
		IDE3.listaDeValoresDeCadaAtributo = listaDeValoresDeCadaAtributo;
	}

	public static Atributo[] getAtributo() {
		return atributo;
	}

	public static void setAtributo(Atributo[] atributo) {
		IDE3.atributo = atributo;
	}

	public static Valor[] getValor() {
		return valor;
	}

	public static void setValor(Valor[] valor) {
		IDE3.valor = valor;
	}


	public static List<Atributo> getMeritos() {
		return meritos;
	}


	public static ArrayList<String>[] getAllLines() {
		return allLines;
	}


	public static void setAllLines(ArrayList<String>[] allLines) {
		IDE3.allLines = allLines;
	}


	public void setMeritos(ArrayList<Atributo> meritos) {
		this.meritos = meritos;
	}		
}
