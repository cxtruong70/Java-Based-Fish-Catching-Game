public class Util {
	public static double random(double low, double high) {
		return low + Math.random() * (high - low);
	}
	
	public static double dist(double x1, double y1, double x2, double y2){
		//we can do this way as well: Math.abs(x1-x2)+Math.abs(y1-y2)-Math.min(Math.abs(x1-x2), Math.abs(y1-y2));	
		double distance = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		return distance;
	}
}
