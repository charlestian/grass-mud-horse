package jcj1988.oprate;

import java.math.BigInteger;
/**
 * 所有操作类的接口，统一操作类族，以方便建立工厂和统一方式调用
 * */
public interface Operatable extends Operator{

	public void execute(BigInteger b);

	public boolean isNeedArg();
	
	public boolean isMarkOpr();
	
	public String getName();
}