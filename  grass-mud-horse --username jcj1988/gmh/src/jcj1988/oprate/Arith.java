package jcj1988.oprate;

import java.math.BigInteger;

/**
 * gmh的算术运算类，与gmh中的操作一致， 对栈顶的两个元素做数学计算操作， 然后用计算结果替换这两个元素。 先入栈的那个元素是计算的左值。
 * 具体细节查看gmh说明。
 * */
public class Arith {
	/** 运算操作所用到的栈 */
	Stack s = null;
	/** 运算操作的左值 */
	BigInteger left = null;
	/** 运算操作的右值 */
	BigInteger right = null;

	/** 构造函数 */
	public Arith(Stack s) {
		this.s = s;
	}

	/** 获取操作数 */
	private void getNum() {
		right = s.pop();
		left = s.pop();
		//right = s.peek();
		//left = s.deeper();
	}

	/** 加 */
	public void add() {
		getNum();
		s.push(left.add(right));
		// s.dup();
		//s.coverTops(left.add(right));
	}

	/** 减 */
	public void sub() {
		getNum();
		s.push(left.subtract(right));
		// s.dup();
		//s.coverTops(left.subtract(right));
	}

	/** 乘 */
	public void mul() {
		getNum();
		s.push(left.multiply(right));
		// s.dup();
		//s.coverTops(left.multiply(right));
	}

	/** 除 */
	public void div() {
		getNum();
		s.push(left.divide(right));
		// s.dup();
		//s.coverTops(left.divide(right));
	}

	/** 取模 */
	public void mod() {
		getNum();
		s.push(left.mod(right));
		// s.dup();
		//s.coverTops(left.mod(right));
	}
}
