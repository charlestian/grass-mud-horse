package jcj1988.oprate;

import java.math.BigInteger;

/**
 * ���β����ࣨOperator which have Argument��
 * */
public class OperatorArg implements Operator{
	Operatable op = null;
	BigInteger arg = null;
	String name=null;

	public OperatorArg(Operatable op, BigInteger arg) {
		this.op = op;
		this.arg = arg;
		this.name=op.getName()+" "+arg;
	}

	@Override
	public void execute() {
		op.execute(arg);
	}

	@Override
	public String getName() {
		return this.name;
	}
}