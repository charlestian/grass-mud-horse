package analyse;

import java.io.RandomAccessFile;

import jcj1988.io.IOable;
import jcj1988.oprate.Oprateable;
import jcj1988.vm.TriNode;

public class SrcAnalyse implements Analiseable{
	private RandomAccessFile raf = null;
	private TriNode<Oprateable> tree;
	private IOable io;

	public SrcAnalyse(RandomAccessFile raf, TriNode<Oprateable> tree, IOable io) {
		this.raf = raf;
		this.tree = tree;
		this.io = io;
	}

	public RandomAccessFile getRaf() {
		return raf;
	}

	public void setRaf(RandomAccessFile raf) {
		this.raf = raf;
	}

	public TriNode<Oprateable> getTree() {
		return tree;
	}

	public void setTree(TriNode<Oprateable> tree) {
		this.tree = tree;
	}

	public IOable getIo() {
		return io;
	}

	public void setIo(IOable io) {
		this.io = io;
	}
}
