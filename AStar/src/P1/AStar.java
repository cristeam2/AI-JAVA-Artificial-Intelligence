package P1;


import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/*
 * Class with all logic
 * 
 * Clase con toda la logica 
 * 
 */
public class AStar {

	public static final int OBSTACULO   =3;
	static Formula formula = new Formula();	
	static LecturaDatos ld = new LecturaDatos();
	static int [][] matriz= ld.getMatriz();
	private static ArrayList<Nodo> listaCerradaTotal=new ArrayList<Nodo>();

	private static ArrayList<Nodo> listaAbierta=new ArrayList<Nodo>();
	private static ArrayList<Nodo> listaCerrada=new ArrayList<Nodo>();
	private static ArrayList<Nodo> listaObstaculos=new ArrayList<Nodo>();
	public static int contadorDeFinales=0;//***

	/*
	 * Start the inicial matrix with the knowing obstacules
	 * 
	 * Comienzo de la carga de la matriz sabiendo cuales celdas son obstaculos
	 */
	public static void inicio() {
		for ( int i =0; i<Matriz.M;i++ ){
			for ( int j=0; j< Matriz.N; j++){
				{				
					if (matriz[i][j]== OBSTACULO) 
						//listaCerrada.add(new Nodo(i,j));
						listaObstaculos.add(new Nodo(i,j));
				}
			}
		}
		busquedaRecorridoAstar ( ) ;
	}
	
	
	
/*
 * Node with the lowest cost function among all those who are on the list open
 * 
 * Nodo con la menor funcion de coste de entre todos los que estan en la lista abierta
 */
	private static Nodo nodoConMenorF() {
		
		//Iterator<Nodo> iterator = listaCerrada.iterator();
		double x;
		int ind=0;
		if (listaAbierta.isEmpty()) {
			JFrame frame=new JFrame();
			JOptionPane.showMessageDialog(null, "Sorry, there is no solution for this configuration.");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.dispose();
			System.out.println("error nodo no alcanzable");System.exit(1);
		}
		x=listaAbierta.get(0).getF(); 
		
		//compara el actual con el siguiente
		for (int i=0; i<=listaAbierta.size();i++) {
			if ((i+1) <listaAbierta.size() && x > listaAbierta.get(i+1).getF()){
				x=listaAbierta.get(i+1).getF();
				ind=i+1;
			}				
		}
		Nodo tempNodo= listaAbierta.get(ind);
		listaAbierta.remove(ind);
		return tempNodo;
	}
	
	
	
	/*
	 * Taking a coordinate  and see if there is a node with these coordinates in the list of closed nodes
	 * 
	 * Recibo una coordenada y veo si hay un nodo con esas coordenadas esta 
	 * en la lista de los nodos cerrados
	 * 
	 */
	private static boolean  buscarListaCerrados(int posX,int posY) {
		int x,y; 
		boolean estaPresente =false;
		
		for (int i=0; i<listaCerrada.size();i++) {
			x =listaCerrada.get(i).getX();
			y= listaCerrada.get(i).getY();
			if ( posX==x && posY==y )
				estaPresente=true;
		}
		return estaPresente;
	} 
	
	
	/*
	 * Taking a coordinate  and see if there is a node with these coordinates in the list 
	 * of closed nodes, or opened or obstacules
	 * 
	 * Recibo una coordenada y veo si hay un nodo con esas coordenadas 
	 * esta en la lista de los cerrados o abiertos u obstaculos
	 */
	private static boolean  buscarListaCerradosAbiertosYObstaculos(int posX,int posY) {
		int x,y; 
		boolean estaPresente =false;
		
		for (int i=0; i<listaCerrada.size();i++) {
			x =listaCerrada.get(i).getX();
			y= listaCerrada.get(i).getY();
			if ( posX==x && posY==y )
				estaPresente=true;
		}
		if (!listaObstaculos.isEmpty()) {
			for (int i=0; i<listaObstaculos.size();i++) {
				x =listaObstaculos.get(i).getX();
				y= listaObstaculos.get(i).getY();
				if ( posX==x && posY==y )
					estaPresente=true;
			}
		}
		if (!listaAbierta.isEmpty()) {
			for (int i=0; i<listaAbierta.size();i++) {
				x =listaAbierta.get(i).getX();
				y= listaAbierta.get(i).getY();
				if ( posX==x && posY==y )
					estaPresente=true;
			}
		}
		return estaPresente;
	} 

	
	/*
	 *Algorithm to walk the 8 squares around a fixed box
	 * 
	 * Algoritmo para recorrer las 8casillas al rededor de una casilla fijada
	 */
	public static boolean chequeoCasillas(int posTempX, int posTempY,int filFija, int colFija ) {
		boolean  continuar=true;
		
		if (posTempX<0 || posTempY<0 || posTempX>=Matriz.M || posTempY>=Matriz.N  || matriz[posTempX][posTempY]==OBSTACULO 
				|| (posTempX==filFija && posTempY == colFija) || buscarListaCerradosAbiertosYObstaculos(posTempX,posTempY))	
			continuar=false;
		return continuar;
	}
	
	
	/*
	 * Does the same as chequeoCasillas but jumping the center square,it means
	 * it will check the nine cells
	 * 
	 * Hace lo mismo que chequeoCasillas pero saltando la casilla central,
	 * es decir revisa a las nueve casillas
	 */
	public static boolean chequeoCasillas2(int posX, int posY,int filF, int colF ) {
		boolean  continuar=true;
		if (posX<0 || posY<0 || posX>=Matriz.M || posY>=Matriz.N  || matriz[posX][posY]==OBSTACULO 
				|| buscarListaCerrados(posX,posY))

			continuar=false;
		return continuar;
	}

	
	
	/*
	 * Starts search using the a star  algorithm
	 * commented the while, you can make the search Recursive
	 * 
	 * Inicia la busqueda empleando el algoritmo a de estrella
	 * comentando el while,puede hacer la busqueda en recursivo
	 */
	private static void busquedaRecorridoAstar (  ) {
		boolean seguir=true;
		int colT, filT, colF,filF; 
		double h1=formula.calculaDistancia(Matriz.casillaInicioX,Matriz.casillaFinalX,Matriz.casillaInicioY,Matriz.casillaFinalY);
		
		listaCerrada.add(new Nodo(Matriz.casillaInicioX, Matriz.casillaInicioY,Matriz.casillaInicioX,Matriz.casillaInicioY,0,h1,h1));
		Nodo nodoInicio=listaCerrada.get(listaCerrada.size()-1);
		double gTotal=nodoInicio.getG();
		Nodo nodoMenor = null; 
		while (seguir){
			colF = nodoInicio.getY(); 
			filF = nodoInicio.getX();
			
			//Searching on the 8 squares around one			
			//Busqueda sobre las 8 casillas al rededor de una
			for (int i = 0; i < 3; i++) {
				colT = colF + 1 - i;
				for (int j = 0; j < 3; j++) {
					filT = filF + 1 - j;
					if (chequeoCasillas(filT, colT,filF,colF) ){
						Nodo nuevoNodo = new Nodo(filT, colT);
						nuevoNodo.setxPadre(nodoInicio.getX());
						nuevoNodo.setyPadre(nodoInicio.getY());
						nuevoNodo.setG(formula.calculaDistancia(filT,filF, colT,colF));
						nuevoNodo.setH(formula.calculaDistancia(filT,Matriz.casillaFinalX, colT, Matriz.casillaFinalY));
						double f = nuevoNodo.getG() + nuevoNodo.getH();
						if (IBoard.board[filT][colT]==4) 
								f+=5;		
						nuevoNodo.setF(f);
						listaAbierta.add(nuevoNodo);					
					}	
				}

			}
			
			//Only for information, debugging
			//Solo para informacion, debug
			System.out.println("Actual lista abierta: ");
			for (int i = 0; i< listaAbierta.size(); i++)
				System.out.println(listaAbierta.get(i).getX()+" "+listaAbierta.get(i).getY()
						+" H: "+listaAbierta.get(i).getH()+" G: "+listaAbierta.get(i).getG()+" F: "+listaAbierta.get(i).getF());

			//Seeking the node with lower F and add it to the list of closed
			//Busco el nodo que tenga menor F y lo anado a la lista de  cerradas
			nodoMenor = nodoConMenorF();
			gTotal+=nodoMenor.getG();
			nodoMenor.setG(gTotal);
			nodoMenor.setF(gTotal+ nodoMenor.getG());
			System.out.println("Nodo menor antes de actualizar: "+nodoMenor.getX()+" "+nodoMenor.getY()+"  G:" +nodoMenor.getG());	
			System.out.println("------------------------------------------------------------------------");

			//Here we update the value of G as we go through the boxes around
			//aqui actualizamos el valor de G a medida que avanzamos por las casillas de alrededor
			colF = nodoInicio.getY(); 
			filF = nodoInicio.getX();
			System.out.println("Nodos a actualizar:");
			for (int i = 0; i < 3; i++) {
				colT = colF + 1 - i;
				for (int j = 0; j < 3; j++) {
					filT = filF + 1 - j;
					if (chequeoCasillas2(filT, colT,filF,colF) ){
						for (int k=0; k<listaAbierta.size(); k++){
							if (listaAbierta.get(k).getX()==filT && listaAbierta.get(k).getY()==colT){
								listaAbierta.get(k).setG((nodoMenor.getG()+listaAbierta.get(k).getG()));
								listaAbierta.get(k).setF(listaAbierta.get(k).getH()+listaAbierta.get(k).getG());
								System.out.println(filT+" " +colT+ " "+" G: "+ listaAbierta.get(k).getG()+" F: "+listaAbierta.get(k).getF());
							}
						}
					}
				}
			} 	
			System.out.println("------------------------------------------------------------------------");
			System.out.println("Valor del nodo menor despues de actulizar: "+nodoMenor.getX()+" "+nodoMenor.getY()+" G: " +nodoMenor.getG());	
			listaCerrada.add(nodoMenor);
			nodoInicio=nodoMenor;
			if ((nodoMenor.getX() == Matriz.casillaFinalX) && (nodoMenor.getY() ==Matriz.casillaFinalY)) 
				{
					contadorDeFinales++;
					seguir=false;
				}
			}
		if ((contadorDeFinales == (IBoard.listaSalidas.size()) )){
			listaCerradaTotal.addAll(listaCerrada);
			System.out.println("encontrado el final, salida por pantalla");
			imprimirSalida(); 
			System.out.println("FIN");			
			seguir=false;
			JOptionPane.showMessageDialog(null,"Solucion encontrada");
			contadorDeFinales=0;
		}
		else {
			Matriz.setCasillaInicioX(Matriz.casillaFinalX);
			Matriz.setCasillaInicioY(Matriz.casillaFinalY);
			Matriz.setCasillaFinalX(IBoard.listaSalidas.get(contadorDeFinales).getX());
			Matriz.setCasillaFinalY(IBoard.listaSalidas.get(contadorDeFinales).getY());
			listaCerradaTotal.addAll(listaCerrada);
			listaAbierta.clear();
			listaCerrada.clear();
			busquedaRecorridoAstar();
		}
	} 


/*
 * Simple method to show the result
 * 
 * Metodo simple para mostrar el resultado
 */
	private static void imprimirSalida() {
		//Iterator<Nodo> iterator = listaCerrada.iterator();
		int x,y;
		for (int i=0; i<listaCerradaTotal.size();i++) {
			x =listaCerradaTotal.get(i).getX();
			y= listaCerradaTotal.get(i).getY();
			if ( matriz[x][y] != OBSTACULO)
				System.out.println(x+" "+y); 
		}
	}

			//Getters and Setters

	public static ArrayList<Nodo> getListaCerradaTotal() {
		return listaCerradaTotal;
	}
	public static ArrayList<Nodo> getListaCerrada() {
		return listaCerrada;
	}
	public static ArrayList<Nodo> getListaAbierta() {
		return listaAbierta;
	}
}


