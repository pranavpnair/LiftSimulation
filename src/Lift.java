import java.text.DecimalFormat;
import java.util.Random;

public class Lift
{
	int population;
    double f1, f2, f3, f4;
    double avgWaitTime;
    double avgTotalTime;
    double[] arrTime;
    int[] arrFloor;
    int done;
    double clock;
    double[]  totalTime;
    double[]  waitingTime;
    double util;
    int trips;
    Random rand1,rand2;
    
    public static int[] RandomizeArray(int[] array){
		Random rgen = new Random();  // Random number generator			
 
		for (int i=0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    int temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
 
		return array;
	}
    
    public double getNext(Random rand, double lambda) {
        return  Math.log(1-rand.nextDouble())/(-lambda);
    }
    
	public Lift(int users) {
		rand1 = new Random();
		rand2 = new Random();
		population = users;
		f1 = 0.1;  
		f2= 0.17;   
		f3= 0.49; 
		f4=1-f1-f2-f3;
		done = 0; 
		clock = 0; 
		util=0; 
		trips =0;
		arrTime = new double[users];
		totalTime = new double[users];;
		waitingTime = new double[users];
		arrFloor = new int[users];
		for(int i=0;i<users;i++) {
	        if(i==0) 
	        	arrTime[i] = getNext(rand1,1);
	        else arrTime[i] = arrTime[i-1] + getNext(rand1,1);
	        arrFloor[i] = (int) (Math.ceil((int)(i/(f1*users))/(i/(f1*users)+0.1)) + 
	                Math.ceil((int)(i/((f2+f1)*users))/(i/((f2+f1)*users)+0.1))
	             + Math.ceil((int)(i/((1-f4)*users))/(i/((1-f4)*users)+0.1)) +1);
	    }
		for(int i=0;i<users;i++) {
	        waitingTime[i]=arrTime[i];
			totalTime[i]=arrTime[i];
	    }
		RandomizeArray(arrFloor);
	}

	public double liftInAction(int[] floors, int sz,int k){
		double t =0;
	    int d=0, f=0;
	    while(sz!=d) {
	        t+= rand2.nextGaussian()*0.04 + 0.18;    //check it
	        f++;
	        for(int i=0;i<sz;i++) {
	            if(floors[i+k]==f) {
	                d++;                    
	                totalTime[done-sz+i] = clock + t- totalTime[done-sz+i];
	            }
	        }
	    }
	    return t + 0.1*f;                   
	}
	
	public void simulation1() {
		avgWaitTime = avgTotalTime =0;
	    while(done!=population) {
	        int t = done;
	        while(done<population && done-t<6 && arrTime[done]<= clock) done++;
	        if(t-done == 0) {
	            clock = arrTime[done];
	            continue;
	        }
	        for(int i=t;i<done;i++)
	            waitingTime[i] = clock - waitingTime[i];
	        double d = liftInAction(arrFloor, done-t,t);
	        clock+=d;
	        util += d*(done-t)/6;
	        trips ++;
	    }
	    util *= (100.0/clock);
	    for(int i=0;i<population;i++) {
	        avgWaitTime += waitingTime[i];
	        avgTotalTime += totalTime[i];
	    }
	}

	public void simulation2() {
		int t = 0;
	    avgTotalTime = avgWaitTime = 0;
	    boolean lwait = false;
	    while(done!=population) {
	        if(!lwait)
	            t = done;
	        while(done<population && done-t<6 && arrTime[done]<= clock) done++;
	        if(done-t < 6 && done!=population) {
	            clock = arrTime[done];
	            lwait = true;
	            continue;
	        }
	        lwait = false;
	        for(int i=t;i<done;i++)
	            waitingTime[i] = clock - waitingTime[i];
	        double d = liftInAction(arrFloor, done-t,t);
	        clock += d;
	        util += d;
	        trips ++;
	    }
	    
	    util *= (100.0/clock);
	    for(int i=0;i<population;i++) {
	        avgWaitTime += waitingTime[i];
	        avgTotalTime += totalTime[i];
	    }
	}

	public void print() {
		DecimalFormat df2 = new DecimalFormat("###.###");
		System.out.println("Utilization value = " + String.valueOf(df2.format(util)));
		System.out.println("Avg waiting time = "  + String.valueOf(df2.format(avgWaitTime/population)) + " minutes.");
		System.out.println("Avg total time = " + String.valueOf(df2.format(avgTotalTime/population)) + " minutes.");
		System.out.println("No of Trips = " + String.valueOf(trips));
	}

}
