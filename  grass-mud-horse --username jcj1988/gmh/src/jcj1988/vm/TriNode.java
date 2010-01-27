package jcj1988.vm;

/**
 * �������ڵ�
 * */
public class TriNode<T> implements Showable{
	/** ���� */
	TriNode<T> left;
	/** �к��� */
	TriNode<T> middle;
	/** �Һ��� */
	TriNode<T> right;
	/** �ڵ������Ԫ�� */
	T elem;

	public TriNode(T o) {
		elem = o;
	}

	/** ȡ�ýڵ��Ԫ�� */
	public T getElem() {
		return elem;
	}

	/** ���ýڵ��Ԫ�� */
	public void setElem(T elem) {
		this.elem = elem;
	}

	/** ������� */
	public TriNode<T> getLeft() {
		return left;
	}

	/** �������� */
	public void setLeft(TriNode<T> left) {
		this.left = left;
	}

	/** ����к��� */
	public TriNode<T> getMiddle() {
		return middle;
	}

	/** �����к��� */
	public void setMiddle(TriNode<T> middle) {
		this.middle = middle;
	}

	/** ����Һ��� */
	public TriNode<T> getRight() {
		return right;
	}

	/** �����Һ��� */
	public void setRight(TriNode<T> right) {
		this.right = right;
	}

	/** �����ã���ʾ�������ṹ */
	public void show() {
		show(this);
	}

	/** �����ã���ʾ�������ṹ �����ⲻ�ɼ�*/
	private void show(TriNode<?> t) {
		if (t.elem != null) {
			System.out.println(" " + t.elem);
		} else {
			if (t.left != null) {
				System.out.print("L");
				show(t.left);
			}
			if (t.middle != null) {
				System.out.print("M");
				show(t.middle);
			}
			if (t.right != null) {
				System.out.print("R");
				show(t.right);
			}
		}

	}
}
