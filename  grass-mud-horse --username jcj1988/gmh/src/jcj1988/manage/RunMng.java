package jcj1988.manage;

import java.math.BigInteger;
import java.util.ArrayList;

import jcj1988.oprate.Operatable;
import jcj1988.oprate.Callable;
import jcj1988.oprate.OperatorArg;
import jcj1988.oprate.OperatorNArg;

/***
 * 
 * 
 * */
public class RunMng extends Manager {
	private ArrayList<Callable> opr = new ArrayList<Callable>();
	private int i = 0;

	@Override
	public void manage(Operatable elem) {
		opr.add(new OperatorNArg(elem));
	}

	@Override
	public void manage(Operatable elem, BigInteger b) {
		if (elem.isMarkOpr()) {
			i = opr.size() - 1;
			elem.execute(b);
		} else {
			opr.add(new OperatorArg(elem, b));
		}
	}

	@Override
	public void execute() {
		for (i = 0; i < opr.size(); i++) {
			opr.get(i).call();
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
