package jcj1988.manage;

import java.math.BigInteger;
import java.util.ArrayList;

import jcj1988.io.IOable;
import jcj1988.oprate.Operatable;
import jcj1988.oprate.Callable;
import jcj1988.oprate.OperatorArg;
import jcj1988.oprate.OperatorNArg;

public class DebugMng extends Manager {
	private IOable io = null;
	private ArrayList<Callable> opr = new ArrayList<Callable>();
	private int i = 0;

	public DebugMng(IOable io) {
		this.io = io;
		io.println("以下为加载过程：\n");
	}

	@Override
	public void manage(Operatable elem) {
		io.println(elem.getName());
		opr.add(new OperatorNArg(elem));
	}

	@Override
	public void manage(Operatable elem, BigInteger b) {
		io.println(elem.getName() + " " + b);
		if (elem.isMarkOpr()) {
			i=opr.size()-1;
			elem.execute(b);
		} else {
			opr.add(new OperatorArg(elem, b));
		}
	}

	@Override
	public void execute() {
		Callable o=null;
		io.println("\n\n以下为运行过程：\n");
		for (i = 0; i < opr.size(); i++) {
			o=opr.get(i);
			io.print(o.getName()+" ");
			if(o.getName().equals("F_END"))break;
			o.call();
			io.print("\n");
		}
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public ArrayList<Callable> getOpr() {
		return opr;
	}
}
