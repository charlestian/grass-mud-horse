package jcj1988.analyze;

import java.io.Reader;
import java.math.BigInteger;

import jcj1988.cfg.VMarg;
import jcj1988.io.IOable;
import jcj1988.manage.Manager;
import jcj1988.oprate.Operatable;
import jcj1988.vm.CodeSection;
import jcj1988.vm.TriNode;

public class AnalyzeImpl implements Analyzable {
	Manager mng = null;

	private TriNode<Operatable> tree;
	private VMarg vmarg = null;

	private IOable io;
	private Reader reader;
	private int S;
	private int T;
	private int L;

	public AnalyzeImpl(CodeSection cs, TriNode<Operatable> tree,
			Manager mng) {
		this.mng = mng;
		this.tree = tree;

		io = cs.getIo();
		reader = cs.getReader();
		vmarg = cs.getVmarg();
		S = vmarg.getS();
		T = vmarg.getT();
		L = vmarg.getL();
		// Analyze();
	}

	private BigInteger getArg() {
		boolean negate = true;// 是否为负
		int c;
		BigInteger r = BigInteger.ZERO;
		try {
			while ((c = reader.read()) != -1) {
				if (c == S) {
					negate = false;
					break;
				} else if (c == T) {
					negate = true;
					break;
				} else if (c == L) {
					return BigInteger.ZERO;
				}
			}
			while ((c = reader.read()) != -1) {
				if (c == S) {
					r = r.shiftLeft(1);
				} else if (c == T) {
					r = r.shiftLeft(1).add(BigInteger.ONE);
				} else if (c == L) {
					break;
				}
			}
			if (negate)
				r = r.negate();
		} catch (Exception e) {
			// e.printStackTrace();
			io.error(e);
		}
		return r;
	}

	public void analyse() {
		TriNode<Operatable> root = tree;
		Operatable elem = null;
		int c;
		try {
			while ((c = reader.read()) != -1) {
				// System.out.print((char)c);
				if (c == S) {
					root = root.getLeft();
				} else if (c == T)
					root = root.getMiddle();
				else if (c == L)
					root = root.getRight();
				if ((elem = root.getElem()) != null) {// 没有子节点了
					if (elem.isNeedArg()) {
						mng.manage(elem, getArg());
					} else {
						mng.manage(elem);
					}
					root = tree;
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			io.error(e);
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				// e.printStackTrace();
				io.error(e);
			}
		}
	}

	public TriNode<Operatable> getTree() {
		return tree;
	}

	public void setTree(TriNode<Operatable> tree) {
		this.tree = tree;
	}
}
