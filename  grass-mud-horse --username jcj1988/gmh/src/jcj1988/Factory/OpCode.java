package jcj1988.Factory;

import java.math.BigInteger;

import jcj1988.oprate.Oprateable;

public class OpCode implements Oprateable{
	private boolean needArg;

	public OpCode(boolean needArg) {
		this.needArg = needArg;
	}

	public boolean isNeedArg() {
		return needArg;
	}

	public void excute() {
	}

	public void excute(BigInteger b) {
	}
}
