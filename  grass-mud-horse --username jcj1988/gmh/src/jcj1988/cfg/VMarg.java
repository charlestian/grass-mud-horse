package jcj1988.cfg;

public class VMarg {
	private int heapMax; 
	private int S;
	private int T;
	private int L;
	
	public VMarg(int h,int s,int t,int l){
		heapMax=h;
		S=s;
		T=t;
		L=l;
	}

	public int getHeapMax() {
		return heapMax;
	}

	public int getS() {
		return S;
	}

	public int getT() {
		return T;
	}

	public int getL() {
		return L;
	}
}
