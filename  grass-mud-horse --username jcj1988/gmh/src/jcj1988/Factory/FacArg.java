package jcj1988.Factory;

import java.io.RandomAccessFile;

import jcj1988.io.IOable;

/**为Factoryable继承对象所写的参数类*/
public class FacArg {
	public int heapMax;
	public IOable stdio;
	public RandomAccessFile raf;
	public FacArg(int heapMax, IOable stdio, RandomAccessFile raf){
		this.heapMax=heapMax;
		this.stdio=stdio;
		this.raf=raf;
	}
}
