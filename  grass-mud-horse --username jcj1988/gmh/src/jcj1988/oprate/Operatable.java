package jcj1988.oprate;

import java.math.BigInteger;
/**
 * ���в�����Ľӿڣ�ͳһ�������壬�Է��㽨��������ͳһ��ʽ����
 * */
public interface Operatable extends Operator{

	public void execute(BigInteger b);

	public boolean isNeedArg();
	
	public boolean isMarkOpr();
	
	public String getName();
}