package jcj1988.oprate;

/**
 * 无参数运算类（operator which Need Not Argument）
 * */
public class OperatorNArg implements Operator {
	Operatable op = null;
	String name=null;

	public OperatorNArg(Operatable op) {
		this.op = op;
		this.name=op.getName();
	}

	@Override
	public void execute() {
		op.execute();
	}
	
	@Override
	public String getName() {
		return this.name;
	}
}
