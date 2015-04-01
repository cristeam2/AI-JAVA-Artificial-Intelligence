import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;


/*
 * Class with the Bayes algorithm
 */
public class Bayes {

	static int sizeVectorM=0; 
	static int sizeAll=0;
	static int mitad=0;
	static double[] vectorM1=null;
	private static double[][] vectorC1=null;
	static double[] vectorM2=null;
	private static double[][] vectorC2=null;
	private static double[][] 	inverseVectorC1=null;
	private static double[][] 	inverseVectorC2=null;
	private static ArrayList<String>[] allLines=null;
	private static double det1;
	private static double det2;
	
	//Starting the solution 
	public static void main() throws IOException {
		PrintWriter writer = new PrintWriter("Bayes Centres result", "UTF-8");
		allLines=Main.getAllLines();		
		mitad=(allLines.length / 2);
		sizeVectorM=	Main.getSizeArgumentos()-1;
		sizeAll=allLines.length;
		vectorM1=new double[sizeVectorM]; 
		vectorM2=new double[sizeVectorM]; 
		vectorC1=new double[sizeVectorM][sizeVectorM];
		vectorC2=new double[sizeVectorM][sizeVectorM];
		inverseVectorC1=new double[sizeVectorM][sizeVectorM];
		inverseVectorC2=new double[sizeVectorM][sizeVectorM];
		System.out.println("\nEnd of Kmedias, start of Bayes:\n ");
		System.out.println("Fase de entrenamiento: ");
	  calculateM1();
		calculateM2();
		calculateC1();
		inverseVectorC1=Inverse.main(vectorC1.length, vectorC1,"C1");
		det1=Determinant.main( vectorC1,"C1");
		calculateC2();
		inverseVectorC2=Inverse.main(vectorC2.length, vectorC2,"C2");
		det2=Determinant.main(vectorC2,"C2");
		writer.println("Bayes Centres result: ");
		writer.println(Arrays.toString(vectorM1));
		writer.println(Arrays.toString(vectorM2));
		writer.close();
		readTest("Testiris01.txt");
		readTest("Testiris02.txt");
		readTest("Testiris03.txt");
	}

	
	//Formulas and methods to calculated the covariance vector
	private static void calculateC1() {
		double[][] vectorXmenosM=new double[mitad][sizeVectorM]; 
		double[][] vectorXmenosMTras=new double[sizeVectorM][mitad]; 
		System.out.println("Vector M1:");
		System.out.println(Arrays.toString(vectorM1));
		for(int i=0; i<mitad; i++) 
			for(int j=0; j<sizeVectorM; j++)  
				vectorXmenosM[i][j]=Double.parseDouble(allLines[i].get(j))-vectorM1[j];
		for(int x=0; x<mitad; x++)  
			for(int j=0; j<sizeVectorM; j++)  
				vectorXmenosMTras[j][x]=vectorXmenosM[x][j];
	  System.out.print("\n\n"+"Print vector Xi - M:");
		for(int i=0; i<mitad; i++)  {
			System.out.println(" ");
			for(int j=0; j<sizeVectorM; j++)  
				System.out.print(vectorXmenosM[i][j] +" ");
		}
		System.out.print("\n\nPrint vector Xi - M Trasposte:");
		for(int i=0; i<sizeVectorM; i++)  {
			System.out.println(" ");
		 	for(int j=0; j<mitad; j++)  
				System.out.print(vectorXmenosMTras[i][j] +" ");
		}
		vectorC1=multiply(vectorXmenosMTras,vectorXmenosM);
		 for(int i=0; i<vectorC1.length; i++) 
			for(int j=0; j<vectorC1[i].length; j++) 
				vectorC1[i][j]=	vectorC1[i][j]/(allLines.length/2.0);
		System.out.println("\n\nPrint Vector C1:");
		for(int i=0; i<vectorC1.length; i++) 
			System.out.println(Arrays.toString(vectorC1[i] ));		
	}
	
	
	//Formulas and methods to calculated the another covariance vector
	private static void calculateC2() {
		double[][] vectorXmenosM2=new double[mitad][sizeVectorM]; 
		double[][] vectorXmenosMTras2=new double[sizeVectorM][mitad]; 
		System.out.println("\n"+"----------------------------------------------");
		System.out.println("----------------------------------------------");
		System.out.println("\nVector M2:");
		System.out.println(Arrays.toString(vectorM2));
		for(int i=0; i<mitad; i++) 
			for(int j=0; j<sizeVectorM; j++)  
				vectorXmenosM2[i][j]=Double.parseDouble(allLines[i+mitad].get(j))-vectorM2[j];
		for(int x=0; x<mitad; x++)  
			for(int j=0; j<sizeVectorM; j++)  
				vectorXmenosMTras2[j][x]=vectorXmenosM2[x][j];
	  System.out.print("\n\n"+"Print vector2 Xi - M:");
		for(int i=0; i<mitad; i++)  {
			System.out.println(" ");
			 for(int j=0; j<sizeVectorM; j++)  
				System.out.print(vectorXmenosM2[i][j] +" ");
		}
		System.out.print("\n\nPrint vector2 Xi - M Trasposte:");
		for(int i=0; i<sizeVectorM; i++)  {
			System.out.println(" ");
			for(int j=0; j<allLines.length/2; j++)  
				System.out.print(vectorXmenosMTras2[i][j] +" ");
		}
		vectorC2=multiply(vectorXmenosMTras2,vectorXmenosM2);
		for(int i=0; i<vectorC2.length; i++) 
			for(int j=0; j<vectorC2[i].length; j++) 
				vectorC2[i][j]=	vectorC2[i][j]/(allLines.length/2.0);
		System.out.println("\n\nPrint Vector C2:");
		for(int i=0; i<vectorC2.length; i++) 
			System.out.println(Arrays.toString(vectorC2[i] ));
	}
	
	
	//Methods to calculated the multiplication betweens matrices
	public static double[][] multiply(double[][] a, double[][] b) {
    int rowsInA = a.length;
    int columnsInA = a[0].length;
    int columnsInB = b[0].length;
    double[][] c = new double[rowsInA][columnsInB];
    for (int i = 0; i < rowsInA; i++) 
        for (int j = 0; j < columnsInB; j++) 
            for (int k = 0; k < columnsInA; k++) 
               	c[i][j] = c[i][j] + a[i][k] * b[k][j];
   
    return c;
  }
	
	//Methods to calculated one central vector
	private static void calculateM1(){
		for(int j=0; j<(allLines.length/2); j++) 
			for(int i=0; i<Main.getSizeArgumentos()-1; i++) 
				vectorM1[i]+=Double.parseDouble(allLines[j].get(i));
		for(int i=0; i<sizeVectorM; i++)
			vectorM1[i]=vectorM1[i]/mitad;
	}
	
	
	//Methods to calculated the another central vector
	private static void calculateM2(){
		for(int j=mitad; j<allLines.length; j++) 
			for(int i=0; i<Main.getSizeArgumentos()-1; i++) 
				vectorM2[i]+=Double.parseDouble(allLines[j].get(i));
		for(int i=0; i<sizeVectorM; i++)
			vectorM2[i]=vectorM2[i]/mitad;	
	}
	
	
	//Reading the test file and showing the solutions
	public static void readTest(String nombre) throws IOException{
		double[][] vectorXmenosM1=new double[1][sizeVectorM]; 
		double[][] vectorXmenosMTras1=new double[sizeVectorM][1]; 
		double[][] vectorXmenosM2=new double[1][sizeVectorM]; 
		double[][] vectorXmenosMTras2=new double[sizeVectorM][1]; 
		double resultD1;		double resultD2;
		double d; 		double resultP1;		double resultP2;
		double[] vectorXi;
	
		System.out.println("\nBayes Test: "+nombre+ "\n");
		PrintWriter writer = new PrintWriter("Bayes result of "+nombre, "UTF-8");
		DataInputStream in = new DataInputStream(new FileInputStream(nombre));
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine=br.readLine() ;
		String[] argumentos  = strLine.split(",");
		in.close();
		
		vectorXi= new double[argumentos.length-1];
		for (int i =0 ; i <argumentos.length-1;i ++)
				vectorXi[i]=Double.parseDouble(argumentos[i]);
		for(int i=0; i<sizeVectorM; i++){  
				vectorXmenosM1[0][i]=vectorXi[i]-vectorM1[i];
				vectorXmenosM2[0][i]=vectorXi[i]-vectorM2[i];
		}
		System.out.println("Vector Xi de " + nombre);
		System.out.println(Arrays.toString(vectorXi));
		System.out.println("Vector M1: ");
		System.out.println(Arrays.toString(vectorM1));
		System.out.println("Vector M2: ");
		System.out.println(Arrays.toString(vectorM2));
		System.out.println("Vector Xi menos M1: ");
		System.out.println(Arrays.toString(vectorXmenosM1[0]));
		System.out.println("Vector Xi menos M2: ");
		System.out.println(Arrays.toString(vectorXmenosM2[0]));
		for(int j=0; j<sizeVectorM; j++)  {
					vectorXmenosMTras1[j][0]=vectorXmenosM1[0][j];
					vectorXmenosMTras2[j][0]=vectorXmenosM2[0][j];
		}
		System.out.println("Multiplication (Xi-Mi) * c Inverse * (Xi-Mi) Traspuesta");
		System.out.print(vectorXmenosM1.length+ " x " +vectorXmenosM1[0].length );
		System.out.print("   " + inverseVectorC1.length+ " x " +inverseVectorC1[0].length );
		System.out.println("   " + vectorXmenosMTras1.length+ " x " +vectorXmenosMTras1[0].length);
		d=sizeVectorM; 

		resultD1=multiply(multiply( vectorXmenosM1,inverseVectorC1),vectorXmenosMTras1)[0][0];
		resultD2=multiply(multiply( vectorXmenosM2,inverseVectorC2),vectorXmenosMTras2)[0][0];
		resultP1=	(1.0/ (Math.pow((2.0*Math.PI),d/2.0) * Math.pow(det1, 0.5))) *Math.exp(-(1/2.0)*resultD1);
		resultP2=	(1.0/ (Math.pow((2.0*Math.PI),d/2.0) * Math.pow(det2, 0.5))) *Math.exp(-(1/2.0)*resultD2);
	
		System.out.println("Porcentaje de pertenencia a la clase 1: " + (resultP1/(resultP1+resultP2))*100+"%");
		writer.println	("Porcentaje de pertenencia a la clase 1: " + (resultP1/(resultP1+resultP2))*100+"%");
		System.out.println("Porcentaje de pertenencia a la clase 2: " + (resultP2/(resultP1+resultP2))*100+"%");
		writer.println("Porcentaje de pertenencia a la clase 2: " + (resultP2/(resultP1+resultP2))*100+"%");
	
		System.out.println("Distance D1= " + resultD1);		
		writer.println("Distance D1= " + resultD1);
	
		System.out.println("Distance D2= " + resultD2);
		writer.println("Distance D2= " + resultD2);	
		if (resultD1 < resultD2) {
				System.out.println("Because D1 < D2 It's belong to first class,which is: " +allLines[0].get(Main.getSizeArgumentos()-1));
				writer.println("Because D1 < D2 It's belong to first class,which is: " +allLines[0].get(Main.getSizeArgumentos()-1));
		}	
		else {
				System.out.println("Because D2 < D1  It's belong to sencond class,which is: " +allLines[sizeAll-1].get(Main.getSizeArgumentos()-1));
				writer.println	("Because D2 < D1 It's belong to second class,which is: " +allLines[sizeAll-1].get(Main.getSizeArgumentos()-1));
		}
		if (resultP1 < 0.0001) resultP1=0; 
		if (resultP2 < 0.0001) resultP2=0;

		writer.close();	
	}
}
