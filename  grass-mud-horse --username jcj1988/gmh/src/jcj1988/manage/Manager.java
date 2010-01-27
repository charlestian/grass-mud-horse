package jcj1988.manage;

import java.math.BigInteger;

import jcj1988.oprate.Executable;
import jcj1988.oprate.Operatable;

public abstract class Manager implements Executable {
	public abstract void manage(Operatable elem);

	public abstract void manage(Operatable elem, BigInteger b);

	@Override
	public void execute() {
		// do nothing
	}
}
