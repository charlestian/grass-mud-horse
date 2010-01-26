package jcj1988.oprate;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * gmh中的栈,操作与gmh中保持一致，具体细节查看gmh说明。由于对于java中各数据结构的效率认识不清，故写此类
 * 一是为逐层分解，减少数据结构对注意力的分散; 二是为了更好的体现层次，使结构清晰;
 * 三是为以后找到效率更高的办法后，可以直接替换此类，避免对于其他代码的牵扯，增加可重用性
 * */
public class Stack {
	/**
	 * 此处暂用ArrayList而不用LinkedList的原因， 因为本人在查阅JDK API的时候在ArrayList类的说明中发现如下说明：
	 * “add 操作以分摊的固定时间 运行，也就是说，添加 n 个元素需要 O(n) 时间。 其他所有操作都以线性时间运行（大体上讲）。 与用于
	 * LinkedList 实现的常数因子相比，此实现的常数因子较低。”
	 * */
	ArrayList<BigInteger> list = new ArrayList<BigInteger>();

	/**
	 * 非gmh操作，为相关操作预留。返回栈的实际大小
	 * */
	int size() {
		return list.size();
	}

	/**
	 * 非gmh操作，为其他操作预留。 查看栈顶对象而不移除它。
	 * */
	BigInteger peek() {
		return list.get(list.size()-1);
	}
	
	/**
	 * 非gmh操作，为其他操作预留。翻墙式查看次栈顶元素
	 * */
	BigInteger deeper() {
		return list.get(list.size()-2);
	}

	/**
	 * 非gmh操作，为其他操作预留。 测试堆栈是否为空。
	 * */
	boolean isEmpty() {
		return (list.size() == 0);
	}

	/**
	 * 非gmh操作，为其他操作预留。 替换栈顶元素为i。
	 * */
	void setTop(BigInteger i) {
		list.set(list.size()-1, i);
	}
	/**
	 * 非gmh操作，为其他操作预留。用i替换栈顶两个元素。
	 * */
	void coverTops(BigInteger i) {
		list.set(list.size()-1, i);
		list.set(list.size()-2, i);
	}

	/**
	 * 压栈
	 * */
	public void push(BigInteger bi) {
		list.add(bi);
	}

	/**
	 * 出栈
	 * */
	public BigInteger pop() {
		return list.remove(list.size()-1);
	}

	/**
	 * 复制栈顶元素到栈顶
	 * */
	public void dup() {
		list.add(list.get(list.size()-1));
	}

	/**
	 * 复制第n个元素到栈顶
	 * */
	public void copy(int n) {
		list.add(list.get(n));
	}

	/**
	 * 交换栈顶两个元素
	 * */
	public void swap() {
		BigInteger tmp1 = list.get(list.size() - 2);
		BigInteger tmp2 = list.get(list.size()-1);
		list.set(list.size()-1, tmp1);
		list.set(list.size() - 2, tmp2);
	}

	/**
	 * 保持栈顶元素，slide n item off the stack 注：此处的理解可能存在歧义，
	 * 对于"slide n item off the stack"本人理解为 将栈顶元素下降n个元素
	 * */
	public void slid(int n) {
		// removeRange为protected,而且时间复杂度大
		// list.removeRange(list.size()-n,list.size());
		for (int i = list.size() - n; i < list.size(); i++) {
			list.remove(i);
		}
	}
}
