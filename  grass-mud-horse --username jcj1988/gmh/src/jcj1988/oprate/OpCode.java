package jcj1988.oprate;

import java.math.BigInteger;

/**
 * 操作类工厂类Opraters中操作对象的根类
 * **/
public class OpCode implements Operatable {
	private boolean needArg;
	private String name;

	public OpCode(String name,boolean needArg) {
		this.needArg = needArg;
		this.name=name;
	}

	@Override
	public boolean isNeedArg() {
		return needArg;
	}

	@Override
	public boolean isMarkOpr() {
		return false;
	}

	@Override
	public void execute() {
	}

	@Override
	public void execute(BigInteger b) {
	}

	public String getName() {
		return name;
	}
}
