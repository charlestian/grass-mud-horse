package jcj1988.oprate;

import java.math.BigInteger;

import jcj1988.io.IOable;

/**
 * gmh����������࣬��gmh�еĲ���һ�£�����ϸ�ڲ鿴gmh˵���� �������ݽṹ��ע�����ķ�ɢ,���õ����ֲ�Σ�ʹ�ṹ����
 * */
public class IO {
	/** ��������ӿ� */
	IOable io = null;
	Stack s = null;
	Heap h = null;

	/**
	 * gmhIO�Ĺ��캯��
	 * */
	public IO(IOable io, Stack s, Heap h) {
		this.io = io;
		this.s = s;
		this.h = h;
	}

	public void putChar() {
		io.putChar(s.peek().intValue());
	}

	public void getChar() {
		h.set(s.peek().intValue(), BigInteger.valueOf(io.getChar()));
	}

	public void putInt() {
		io.putInt(s.peek().intValue());
	}

	public void getInt() {
		h.set(s.peek().intValue(), BigInteger.valueOf(io.getInt()));
		;
	}
}
