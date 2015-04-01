package P1;


/*
 * Class to load the information from the GUI or from the console
 * 
 * Clase para cargar la informacion desde la interfaz grafica o desde la consola
 * 
 */

public class LecturaDatos {
	private int [][] matriz;

	public int[][] getMatriz() {

		Matriz m = new Matriz();
		this.matriz =m.getM(); 
		return this.matriz;
	}




	/*
	 * An possible console load:	
	 * 
	 *Carga por consola:
	 *
	Scanner entrada = new Scanner(System.in); 
	int fil, col; char c = 'y'; String s;
	System.out.println("Numero de columnas");
	fil = entrada.nextInt(); 
	System.out.println("Numero de columnas");
	col = entrada.nextInt();
	int [][] matriz = new int[fil][col]; 
	for (int i= 0; i<fil; i++)
		for (int j= 0; j<col; j++)
			matriz[i][j]=0;
	System.out.println("Indica la fila y columna de inicio");
	System.out.println("Fila:");
	int filI= entrada.nextInt();
	System.out.println("columna:");
	int colI= entrada.nextInt();
	matriz[filI-1][colI-1] = 1;

	System.out.println("Indica la fila y columna de fin");
	System.out.println("Fila:");
	int filF= entrada.nextInt();
	System.out.println("columna:");
	int colF= entrada.nextInt();
	matriz[filF-1][colF-1] = 1;

	while ( c == 'y' ) { 

		System.out.println("Indica la fila y columna bloqueada");			
		System.out.println("Fila:");
		fil= entrada.nextInt();
		System.out.println("columna:");
		col = entrada.nextInt();
		matriz[fil-1][col-1]=-1;
		System.out.println("continuar? y/n");
		s= entrada.next();
		c=s.charAt(0);	
	}*/

	/*double [][] G = new double[fil][col]; 
	double [][] H = new double[fil][col];
	double [][] F = new double[fil][col];
	char   [][] listaAbierta = new char[fil][col];
	char   [][] listaCerrada = new char[fil][col];
	 */

	/*	int filT=filI, colT= colI; double min; 
	for ( int i=0; i<3; i++)
		colT=colI-i;		
		for ( int j=0; j<3; j++)
			filT=filI-j;
			H[filT][colT]= f.calculaDistancia(colT, colF , filT, filF);	
			G[filT][colT]= f.calculaDistancia(colT, colI , filT, filI);			
			F[filT][colT]=G[filT][colT]+H[filT][colT];
			//min= Math.min (max, F[filT][colT]);
	for ( int i=0; i<3; i++)
		filT=filI-1;
		for ( int j=0; j<3; j++);			
		if (matriz[filT-1][colT-1]>0) {
			//calculo
			colT=filT-1;
			colT=filT+1;

		}*/


}
