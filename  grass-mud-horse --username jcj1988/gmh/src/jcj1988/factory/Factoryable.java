package jcj1988.factory;

import jcj1988.oprate.Operatable;

/**
 * ���������������࣬��ʹ�ù�������֮ǰ�����ȵ���init������
 * ���򽫻����,��ʼ������Ҫ�Ĳ���������һ���������󴫵�
 * */
public interface Factoryable{
	public Operatable Factory(String s) ;
}
