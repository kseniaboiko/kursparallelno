package stream;

import java.util.stream.IntStream;

public class UnDirect {
	final double a = 5;
	final double A = 2;
	final double h = 0.1;
	final double tau = 0.005;
	final int amountTau = 200;
	final int amountH = 10;
	double valueH = 0;
	double valueTau = 0;
	double w[][] = new double[amountTau][amountH];
	
	static double calculateExact(double valueH, double valueTau) {
		return 1.0 / (1.0 + 2 * Math.pow(Math.exp(1), Math.sqrt(2) * 0.5 * valueH + 0.5 * (2 * 5 - 1) * valueTau));
	}
    
	 double iteration (int k,int i) {
		
		return w[i-1][k]+ tau*((w[i-1][k+1] - 2 * w[i-1][k]+ w[i-1][k-1])/(h*h) - a *  w[i-1][k]
				+  w[i-1][k] *  w[i-1][k] + a *  w[i-1][k] *  w[i-1][k] -  w[i-1][k] *  w[i-1][k] *  w[i-i][k]);
	}	
	 
	
	void calc() {
IntStream.range(0,w[0].length).parallel().forEach(i->w[0][i]=calculateExact(i*h,0));
		
		
		IntStream.range(0,w.length).parallel().forEach(i->{
		w[i][0]=calculateExact(0,i*tau);
		w[i][w[0].length-1]= calculateExact(1,i*tau);
		});	
		
		/*for(int k =1; k< w[0].length-1;k++) {
			int y = k;
			IntStream.range(1,w.length).parallel().forEach((i)->w[i][y]= iteration(y,i));
		}*/
		
		

		for(int k =1; k< w.length;k++) {
			int y = k;
			IntStream.range(1,w[0].length-1).parallel().forEach((i)->w[y][i]= iteration(i,y));
		}
		
		
		for (int i = 0; i < amountTau; ++i) {
			// System.out.println("{");
			for (int j = 0; j < amountH; ++j) {
				System.out.print(w[i][j] + "," + "\t");
			}
			System.out.println();
		}
	}

}
