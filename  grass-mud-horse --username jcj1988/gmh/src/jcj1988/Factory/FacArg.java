package jcj1988.Factory;

import java.io.RandomAccessFile;

import jcj1988.io.IOable;

/**ΪFactoryable�̳ж�����д�Ĳ�����*/
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
