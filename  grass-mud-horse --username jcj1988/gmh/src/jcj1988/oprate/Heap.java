package jcj1988.oprate;

import java.math.BigInteger;

/**
 * gmh中的堆，与gmh中的操作一致，具体细节查看gmh说明。
 * 堆操作命令根据栈上的地址来存储或读取元素。要存储一个元素入堆，先要把元素地址压栈，然后运行存储命令
 * 。要读取一个元素入栈，先要把元素地址压栈，然后运行读取命令，这样就把那个元素读出并放到了栈顶。
 * 考虑到堆的容量问题，计划要用哈希表类来重写此类
 * */
public class Heap {
	/** 堆数据结构 */
	BigInteger[] heap = null;
	/** 堆操作的辅助栈 */
	Stack s = null;

	/** 构造函数 **/
	public Heap(int heapMax, Stack s) {
		this.s = s;
		heap = new BigInteger[heapMax];
	}

	/** 非gmh操作，为相关操作预留。设置栈中地址adr的元素为num */
	void set(int adr, BigInteger num) {
		heap[adr] = num;
	}

	/** 非gmh操作，为相关操作预留。获取栈中地址adr的元素的值 */
	BigInteger get(int adr) {
		return heap[adr];
	}

	/** 存储栈顶元素 */
	public void put() {
		//heap[s.deeper().intValue()] = s.peek();
		heap[s.deeper().intValue()] = s.pop();
	}

	/** 获取堆元素到栈顶 */
	public void get() {
		//s.push(heap[s.peek().intValue()]);
		s.push(heap[s.pop().intValue()]);
	}
}
