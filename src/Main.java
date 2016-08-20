import java.util.Scanner;


public class Main {
	public static void main(String[] args){
		int users;
		Stats s = new Stats();
		double[] wait1 = new double[2000];
		double[] total1 = new double[2000]; 
		double[] wait2 = new double[2000];
		double[] total2 = new double[2000]; 
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of lift users.");
		users = in.nextInt();
		Lift lift1 = new Lift(users);
		System.out.println("Enter the simulation mode 1 or 2");
		int mode = in.nextInt();
		in.close();
		if(mode == 1)
			lift1.simulation1();
		else
			lift1.simulation2();
		lift1.print();
		for(int i=1;i<2000;i++){
			Lift lift = new Lift(users);
			lift.simulation1();
			wait1[i-1] = lift.avgWaitTime/lift.population;
			total1[i-1] = lift.avgTotalTime/lift.population;
		}
		for(int i=1;i<2000;i++){
			Lift lift = new Lift(users);
			lift.simulation2();
			wait2[i-1] = lift.avgWaitTime/lift.population;
			total2[i-1] = lift.avgTotalTime/lift.population;
		}
		System.out.println("For 2000 simulations: ");
		System.out.println("Mean waiting time 1: " + s.mean(wait1)  + " Variance: "+ (s.variance(wait1)));
		System.out.println("Mean total time 1: " + s.mean(total1)  + " Variance: "+ (s.variance(total1)));
		System.out.println("Mean waiting time 2: " + s.mean(wait2)  + " Variance: "+ (s.variance(wait2)));
		System.out.println("Mean total time 2: " + s.mean(total2)  + " Variance: "+ (s.variance(total2)));

	}
}
