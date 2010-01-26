package jcj1988.oprate;

import java.io.RandomAccessFile;
import java.math.BigInteger;

/**
 * gmh中的流控制，与gmh中的操作一致，具体细节查看gmh说明。 暂时还未实现。。。
 * */
public class Flow {
	RandomAccessFile raf=null;
	
	public Flow(RandomAccessFile raf){
		this.raf=raf;
	}
	
	
	public void mark(BigInteger b) {

	}

	public void call(BigInteger b) {

	}

	public void jmp(BigInteger b) {

	}

	public void jz(BigInteger b) {

	}

	public void jneg(BigInteger b) {

	}

	public void ret() {

	}

	public void end() {

	}
}
