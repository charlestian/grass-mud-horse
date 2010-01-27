package jcj1988.oprate;

import java.math.BigInteger;

/**
 * º¬²Î²Ù×÷Àà£¨Operator which have Argument£©
 * */
public class OperatorArg implements Callable{
	Operatable op = null;
	BigInteger arg = null;
	String name=null;

	public OperatorArg(Operatable op, BigInteger arg) {
		this.op = op;
		this.arg = arg;
		this.name=op.getName()+" "+arg;
	}

	@Override
	public void call() {
		op.execute(arg);
	}

	@Override
	public String getName() {
		return this.name;
	}
}