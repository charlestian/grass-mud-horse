package jcj1988.Factory;

import jcj1988.oprate.Oprateable;

/**
 * ���������������࣬��ʹ�ù�������֮ǰ�����ȵ���init������
 * ���򽫻����,��ʼ������Ҫ�Ĳ���������һ���������󴫵�
 * */
public interface Factoryable <argT>{
	public void init(argT o);
	public Oprateable Factory(String s) ;
}
