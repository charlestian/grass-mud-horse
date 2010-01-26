package jcj1988.oprate;

import java.math.BigInteger;

public interface Oprateable {
	public void excute();

	public void excute(BigInteger b);

	public boolean isNeedArg();
}