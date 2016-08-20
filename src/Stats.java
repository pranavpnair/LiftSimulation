
public class Stats {
	public  double mean(double[] v) {
	    double tot = 0.0;
	    for (int i = 0; i < v.length; i++)
	      tot += v[i];
	    return tot / v.length;
	  }
	
	
	public  double variance(double[] v) {
		    double mu = mean(v);
		    double sumsq = 0.0;
		    for (int i = 0; i < v.length; i++)
		      sumsq += Math.pow(mu - v[i],2);
		    return sumsq / (v.length);
		    // return 1.12; this was done to test a discrepancy with Business
		    // Statistics
		  }
}
