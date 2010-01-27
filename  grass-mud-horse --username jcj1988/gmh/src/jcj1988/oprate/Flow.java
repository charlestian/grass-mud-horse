package jcj1988.oprate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;

import jcj1988.manage.Manager;
import jcj1988.manage.RunMng;

/**
 * gmh�е������ƣ���gmh�еĲ���һ�£�����ϸ�ڲ鿴gmh˵���� ��ʱ��δʵ�֡�����
 * */
public class Flow {
	RunMng mng = null;
	private ArrayList<Callable> opr = null;
	java.util.Stack<Integer> callStack = new java.util.Stack<Integer>();
	Stack opStack = null;
	private Hashtable<BigInteger, Integer> labels = new Hashtable<BigInteger, Integer>();

	public Flow(Manager mng, Stack s) {
		this.mng = (RunMng)mng;
		this.opr=this.mng.getOpr();
		this.opStack=s;
	}

	public void mark(BigInteger b) {
		labels.put(b, mng.getI());
	}

	public void call(BigInteger b) {
		callStack.push(mng.getI());
		mng.setI(labels.get(b));
	}

	public void jmp(BigInteger b) {
		mng.setI(labels.get(b));
	}

	public void jz(BigInteger b) {
		if (opStack.pop().signum() == 0) {
			mng.setI(labels.get(b));
		}
	}

	public void jneg(BigInteger b) {
		if (opStack.pop().signum() == -1) {
			mng.setI(labels.get(b));
		}
	}

	public void ret() {
		mng.setI(callStack.pop());
	}

	public void end() {
		mng.setI(opr.size());
	}
}
