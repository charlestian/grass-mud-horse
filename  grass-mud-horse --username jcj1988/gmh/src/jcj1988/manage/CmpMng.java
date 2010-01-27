package jcj1988.manage;

import java.math.BigInteger;

import jcj1988.io.IOable;
import jcj1988.oprate.Operatable;

public class CmpMng extends Manager {
	private IOable io=null;

	public CmpMng(IOable io) {
		this.io=io;
	}

	@Override
	public void manage(Operatable elem) {
		io.println(elem.getName());
	}

	@Override
	public void manage(Operatable elem, BigInteger b) {
		io.println(elem.getName()+" "+b);
	}

	@Override
	public void execute() {
		// do nothing
	}

}
