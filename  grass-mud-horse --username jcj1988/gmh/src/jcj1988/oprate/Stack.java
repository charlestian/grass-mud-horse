package jcj1988.oprate;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * gmh�е�ջ,������gmh�б���һ�£�����ϸ�ڲ鿴gmh˵�������ڶ���java�и����ݽṹ��Ч����ʶ���壬��д����
 * һ��Ϊ���ֽ⣬�������ݽṹ��ע�����ķ�ɢ; ����Ϊ�˸��õ����ֲ�Σ�ʹ�ṹ����;
 * ����Ϊ�Ժ��ҵ�Ч�ʸ��ߵİ취�󣬿���ֱ���滻���࣬����������������ǣ�������ӿ�������
 * */
public class Stack {
	/**
	 * �˴�����ArrayList������LinkedList��ԭ�� ��Ϊ�����ڲ���JDK API��ʱ����ArrayList���˵���з�������˵����
	 * ��add �����Է�̯�Ĺ̶�ʱ�� ���У�Ҳ����˵����� n ��Ԫ����Ҫ O(n) ʱ�䡣 �������в�����������ʱ�����У������Ͻ����� ������
	 * LinkedList ʵ�ֵĳ���������ȣ���ʵ�ֵĳ������ӽϵ͡���
	 * */
	ArrayList<BigInteger> list = new ArrayList<BigInteger>();

	/**
	 * ��gmh������Ϊ��ز���Ԥ��������ջ��ʵ�ʴ�С
	 * */
	int size() {
		return list.size();
	}

	/**
	 * ��gmh������Ϊ��������Ԥ���� �鿴ջ����������Ƴ�����
	 * */
	BigInteger peek() {
		return list.get(list.size()-1);
	}
	
	/**
	 * ��gmh������Ϊ��������Ԥ������ǽʽ�鿴��ջ��Ԫ��
	 * */
	BigInteger deeper() {
		return list.get(list.size()-2);
	}

	/**
	 * ��gmh������Ϊ��������Ԥ���� ���Զ�ջ�Ƿ�Ϊ�ա�
	 * */
	boolean isEmpty() {
		return (list.size() == 0);
	}

	/**
	 * ��gmh������Ϊ��������Ԥ���� �滻ջ��Ԫ��Ϊi��
	 * */
	void setTop(BigInteger i) {
		list.set(list.size()-1, i);
	}
	/**
	 * ��gmh������Ϊ��������Ԥ������i�滻ջ������Ԫ�ء�
	 * */
	void coverTops(BigInteger i) {
		list.set(list.size()-1, i);
		list.set(list.size()-2, i);
	}

	/**
	 * ѹջ
	 * */
	public void push(BigInteger bi) {
		list.add(bi);
	}

	/**
	 * ��ջ
	 * */
	public BigInteger pop() {
		return list.remove(list.size()-1);
	}

	/**
	 * ����ջ��Ԫ�ص�ջ��
	 * */
	public void dup() {
		list.add(list.get(list.size()-1));
	}

	/**
	 * ���Ƶ�n��Ԫ�ص�ջ��
	 * */
	public void copy(int n) {
		list.add(list.get(n));
	}

	/**
	 * ����ջ������Ԫ��
	 * */
	public void swap() {
		BigInteger tmp1 = list.get(list.size() - 2);
		BigInteger tmp2 = list.get(list.size()-1);
		list.set(list.size()-1, tmp1);
		list.set(list.size() - 2, tmp2);
	}

	/**
	 * ����ջ��Ԫ�أ�slide n item off the stack ע���˴��������ܴ������壬
	 * ����"slide n item off the stack"�������Ϊ ��ջ��Ԫ���½�n��Ԫ��
	 * */
	public void slid(int n) {
		// removeRangeΪprotected,����ʱ�临�Ӷȴ�
		// list.removeRange(list.size()-n,list.size());
		for (int i = list.size() - n; i < list.size(); i++) {
			list.remove(i);
		}
	}
}
