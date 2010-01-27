package jcj1988.manage;

import java.math.BigInteger;
import java.util.ArrayList;

import jcj1988.oprate.Operatable;
import jcj1988.oprate.Operator;
import jcj1988.oprate.OperatorArg;
import jcj1988.oprate.OperatorNArg;

/***
 * 
 * 
 * */
public class RunMng extends Manager {
	private ArrayList<Operator> opr = new ArrayList<Operator>();
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
			opr.get(i).execute();
		}
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public ArrayList<Operator> getOpr() {
		return opr;
	}
}
