package jcj1988.oprate;

import java.math.BigInteger;

/**
 * gmh�����������࣬��gmh�еĲ���һ�£� ��ջ��������Ԫ������ѧ��������� Ȼ���ü������滻������Ԫ�ء� ����ջ���Ǹ�Ԫ���Ǽ������ֵ��
 * ����ϸ�ڲ鿴gmh˵����
 * */
public class Arith {
	/** ����������õ���ջ */
	Stack s = null;
	/** �����������ֵ */
	BigInteger left = null;
	/** �����������ֵ */
	BigInteger right = null;

	/** ���캯�� */
	public Arith(Stack s) {
		this.s = s;
	}

	/** ��ȡ������ */
	private void getNum() {
		right = s.pop();
		left = s.pop();
		//right = s.peek();
		//left = s.deeper();
	}

	/** �� */
	public void add() {
		getNum();
		s.push(left.add(right));
		// s.dup();
		//s.coverTops(left.add(right));
	}

	/** �� */
	public void sub() {
		getNum();
		s.push(left.subtract(right));
		// s.dup();
		//s.coverTops(left.subtract(right));
	}

	/** �� */
	public void mul() {
		getNum();
		s.push(left.multiply(right));
		// s.dup();
		//s.coverTops(left.multiply(right));
	}

	/** �� */
	public void div() {
		getNum();
		s.push(left.divide(right));
		// s.dup();
		//s.coverTops(left.divide(right));
	}

	/** ȡģ */
	public void mod() {
		getNum();
		s.push(left.mod(right));
		// s.dup();
		//s.coverTops(left.mod(right));
	}
}
