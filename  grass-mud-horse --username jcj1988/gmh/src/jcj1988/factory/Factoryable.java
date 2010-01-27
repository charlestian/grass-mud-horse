package jcj1988.factory;

import jcj1988.oprate.Operatable;

/**
 * 操作的制作工厂类，在使用工厂函数之前必须先调用init函数，
 * 否则将会出错,初始化所需要的参数可有由一个参数对象传递
 * */
public interface Factoryable{
	public Operatable Factory(String s) ;
}
