package jcj1988.Factory;

import jcj1988.oprate.Oprateable;

/**
 * 操作的制作工厂类，在使用工厂函数之前必须先调用init函数，
 * 否则将会出错,初始化所需要的参数可有由一个参数对象传递
 * */
public interface Factoryable <argT>{
	public void init(argT o);
	public Oprateable Factory(String s) ;
}
