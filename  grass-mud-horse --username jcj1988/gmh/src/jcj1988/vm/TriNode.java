package jcj1988.vm;

/**
 * 三叉树节点
 * */
public class TriNode<T> implements Showable{
	/** 左孩子 */
	TriNode<T> left;
	/** 中孩子 */
	TriNode<T> middle;
	/** 右孩子 */
	TriNode<T> right;
	/** 节点的数据元素 */
	T elem;

	public TriNode(T o) {
		elem = o;
	}

	/** 取得节点的元素 */
	public T getElem() {
		return elem;
	}

	/** 设置节点的元素 */
	public void setElem(T elem) {
		this.elem = elem;
	}

	/** 获得左孩子 */
	public TriNode<T> getLeft() {
		return left;
	}

	/** 设置左孩子 */
	public void setLeft(TriNode<T> left) {
		this.left = left;
	}

	/** 获得中孩子 */
	public TriNode<T> getMiddle() {
		return middle;
	}

	/** 设置中孩子 */
	public void setMiddle(TriNode<T> middle) {
		this.middle = middle;
	}

	/** 获得右孩子 */
	public TriNode<T> getRight() {
		return right;
	}

	/** 设置右孩子 */
	public void setRight(TriNode<T> right) {
		this.right = right;
	}

	/** 测试用，显示整个树结构 */
	public void show() {
		show(this);
	}

	/** 测试用，显示整个树结构 ，对外不可见*/
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
