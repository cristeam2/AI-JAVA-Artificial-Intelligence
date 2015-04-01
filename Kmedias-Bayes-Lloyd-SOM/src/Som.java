import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Class with the Som algorithm
 */
public class Som {
	
	private static double alfaInicial =0.1 ;
	private static double alfaFinal = 0.01;
	private static double GAMMA=0.1;
	private static double EPSILON=Math.pow(10, -6); //tolerancia
	private static int  KMax=1000;//kMax
	private static double  K=0;
	private static double  alfaK=0;
	private static  ArrayList<String>[] allLines= 	Main.getAllLines();
	private static double [] centro1=null;
	private static double [] centro2=null;
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("Inicializamos SOM");
		PrintWriter writer = new PrintWriter("SOM Centres result", "UTF-8");
		centro1	= Bayes.vectorM1;
		centro2= Bayes.vectorM2;
		
		Double distanceV1,distanceV2;
		int k=0; boolean continuar =true;
		do {
			k++;
			alfaK=  (Math.pow((alfaFinal/alfaInicial),(k/KMax)) )* alfaInicial ;
			for (int i= 0; i <allLines.length; i++){	
				distanceV1 = (Math.pow(centro1[0] - Double.parseDouble(allLines[i].get(0)) ,2)+Math.pow(centro1[1] - Double.parseDouble(allLines[i].get(1)),2)+Math.pow(centro1[2] - Double.parseDouble(allLines[i].get(2)),2)+Math.pow(centro1[3] - Double.parseDouble(allLines[i].get(3)),2));
				distanceV2 = (Math.pow(centro2[0] - Double.parseDouble(allLines[i].get(0)) ,2)+Math.pow(centro2[1] - Double.parseDouble(allLines[i].get(1)),2)+Math.pow(centro2[2] - Double.parseDouble(allLines[i].get(2)),2)+Math.pow(centro2[3] - Double.parseDouble(allLines[i].get(3)),2));
				K= Math.exp(-1*((distanceV1 /(2*(alfaK*alfaK)))));
				if (K> EPSILON) {
					System.arraycopy(modificarCentro(centro1,i,K), 0, centro1, 0, centro1.length);
				}
				K= Math.exp(-1*((distanceV2 /(2*(alfaK*alfaK)))));
				if (K> EPSILON) {
					System.arraycopy(modificarCentro(centro2,i,K), 0, centro2, 0, centro2.length);
				}
			}
			continuar=false;
		} while(k <= KMax && continuar);

		System.out.println("SOM Centres result: ");
		System.out.println(Arrays.toString(centro1));
		System.out.println(Arrays.toString(centro2));

		writer.println("SOM result: ");
		writer.println(Arrays.toString(centro1));
		writer.println(Arrays.toString(centro2));
		writer.close();
		readTest("Testiris01.txt");
		readTest("Testiris02.txt");
		readTest("Testiris03.txt");
	}
	
	
	//Applying Som formulas
	private static double[] modificarCentro(double[] centro,int i, double K){
		double[] vectorAuxGamma=new double[4]; 
		vectorAuxGamma[0]=(Double.parseDouble(allLines[i].get(0))-centro[0]) * GAMMA *K;
		vectorAuxGamma[1]=(Double.parseDouble(allLines[i].get(1))-centro[1] )* GAMMA *K;
		vectorAuxGamma[2]=(Double.parseDouble(allLines[i].get(2))-centro[2] )* GAMMA *K;
		vectorAuxGamma[3]=(Double.parseDouble(allLines[i].get(3))-centro[3]) * GAMMA *K;
		
		centro[0]=vectorAuxGamma[0]+ centro[0];
		centro[1]=vectorAuxGamma[1]+ centro[1];
		centro[2]=vectorAuxGamma[2]+ centro[2];
		centro[3]=vectorAuxGamma[3]+ centro[3];
		
		return centro;
	} 
	
	
	//Reading the tests and showing the solutions
	public static void readTest(String nombre) throws IOException{
		System.out.println("\nSOM Test: "+nombre+ "\n");
		PrintWriter writer = new PrintWriter("SOM result of "+nombre, "UTF-8");
		DataInputStream in = new DataInputStream(new FileInputStream(nombre));
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine=br.readLine() ;
		String[] argumentos  = strLine.split(",");
		in.close();
		double[] V= new double[argumentos.length-1];
		for (int i =0 ; i <argumentos.length-1;i ++)
				V[i]=Double.parseDouble(argumentos[i]);
		Double distanceV1 = (Math.pow(V[0] - centro1[0],2)+Math.pow(V[1] - centro1[1],2)+Math.pow(V[2] - centro1[2],2)+Math.pow(V[3] - centro1[3],2));
		Double distanceV2 = (Math.pow(V[0] - centro2[0],2)+Math.pow(V[1] - centro2[1],2)+Math.pow(V[2] - centro2[2],2)+Math.pow(V[3] - centro2[3],2));
		System.out.println("Distance 1: "+distanceV1);
		System.out.println("Distance 2: "+distanceV2);
		writer.println("Distance 1: "+distanceV1);
		writer.println("Distance 2: "+distanceV2);
		if (distanceV1 < distanceV2) {
			System.out.println("Because D1 < D2  It's belong to first class,which is: " +allLines[0].get(Main.getSizeArgumentos()-1));
			writer.println("Because D1 < D2 It's belong to first class,which is: " +allLines[0].get(Main.getSizeArgumentos()-1));
	}	
	else { 
			System.out.println("Because D2 < D1 It's belong to sencond class,which is: " +allLines[allLines.length-1].get(Main.getSizeArgumentos()-1));
			writer.println	("Because D2 < D1 It's belong to second class,which is: " +allLines[allLines.length-1].get(Main.getSizeArgumentos()-1));
	}
		
		writer.close();
 }	
}


