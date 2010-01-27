package jcj1988.oprate;

import java.math.BigInteger;

/**
 * gmh�еĶѣ���gmh�еĲ���һ�£�����ϸ�ڲ鿴gmh˵����
 * �Ѳ����������ջ�ϵĵ�ַ���洢���ȡԪ�ء�Ҫ�洢һ��Ԫ����ѣ���Ҫ��Ԫ�ص�ַѹջ��Ȼ�����д洢����
 * ��Ҫ��ȡһ��Ԫ����ջ����Ҫ��Ԫ�ص�ַѹջ��Ȼ�����ж�ȡ��������Ͱ��Ǹ�Ԫ�ض������ŵ���ջ����
 * ���ǵ��ѵ��������⣬�ƻ�Ҫ�ù�ϣ��������д����
 * */
public class Heap {
	/** �����ݽṹ */
	BigInteger[] heap = null;
	/** �Ѳ����ĸ���ջ */
	Stack s = null;

	/** ���캯�� **/
	public Heap(int heapMax, Stack s) {
		this.s = s;
		heap = new BigInteger[heapMax];
	}

	/** ��gmh������Ϊ��ز���Ԥ��������ջ�е�ַadr��Ԫ��Ϊnum */
	void set(int adr, BigInteger num) {
		heap[adr] = num;
	}

	/** ��gmh������Ϊ��ز���Ԥ������ȡջ�е�ַadr��Ԫ�ص�ֵ */
	BigInteger get(int adr) {
		return heap[adr];
	}

	/** �洢ջ��Ԫ�� */
	public void put() {
		//heap[s.deeper().intValue()] = s.peek();
		heap[s.deeper().intValue()] = s.pop();
	}

	/** ��ȡ��Ԫ�ص�ջ�� */
	public void get() {
		//s.push(heap[s.peek().intValue()]);
		s.push(heap[s.pop().intValue()]);
	}
}
