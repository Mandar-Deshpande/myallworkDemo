package test;


public class Test {

	public static void main(String args[]) {
		double d = 0.000002;
		Double e=Math.ceil(-d);
		float f = -90;
		long temp= e.intValue();
		System.out.println(Math.ceil(d));
		System.out.println(Math.ceil(f));
		System.out.println(Math.ceil(temp));
		
		System.out.println(Math.floor(d));
		System.out.println(Math.floor(f));
		System.out.println(Math.floor(temp));
	}
}
