package jcj1988.vm;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;


import jcj1988.Factory.Compiler;
import jcj1988.Factory.FacArg;
import jcj1988.Factory.Factoryable;
import jcj1988.Factory.Structure;
import jcj1988.cfg.Config;
import jcj1988.cfg.VMarg;
import jcj1988.io.IOable;
import jcj1988.io.StdIO;
import jcj1988.oprate.Oprateable;

public class GMH {

	public Config cfg = null;
	public RandomAccessFile raf = null;
	public VMarg vmarg = null;
	public Factoryable<FacArg> fct = new Compiler();//new Compiler();new Structure();//
	public IOable io = new StdIO();
	public TriNode<Oprateable> tree = null;
	private final int S;
	private int T;
	private int L;

	GMH(String xml, String src) {
		cfg = new Config(xml, fct);
		vmarg = cfg.getVMarg();
		tree = cfg.getTree();
		fct.init(new FacArg(vmarg.getHeapMax(), io, raf));
		S = vmarg.getS();
		T = vmarg.getT();
		L = vmarg.getL();
		try {
			raf = new RandomAccessFile(src, "r");
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			io.error(e);
		}
		SrcAnalyse();
	}

	private BigInteger getArg() {
		boolean negate=true;// 是否为负
		int c;
		BigInteger r = BigInteger.ZERO;
		try {
			while ((c = raf.readChar()) != -1) {
				if (c == S){
					negate = false;
					break;
				}else if (c == T){
					negate = true;
					break;
				}else if (c == L){
					negate = false;
					break;
				}
			}
			while ((c = raf.readChar()) != -1) {
				if (c == S) {
					r = r.shiftLeft(1);
				} else if (c == T) {
					r = r.shiftLeft(1).add(BigInteger.ONE);
				}else if (c == L){
					break;
				}
			}
			if(negate)r=r.negate();
		} catch (Exception e) {
			// e.printStackTrace();
			io.error(e);
		}
		return r;
	}

	private void SrcAnalyse() {
		TriNode<Oprateable> root = tree;
		Oprateable elem = null;
		// boolean arg=false;
		int c;
		try {
			while ((c = raf.readChar()) != -1) {
				//System.out.print((char)c);
				if (c == S){
					root = root.getLeft();
				}else if (c == T)
					root = root.getMiddle();
				else if (c == L)
					root = root.getRight();
				if ((elem = root.getElem()) != null) {// 没有子节点了
					if (elem.isNeedArg()){
						//BigInteger bi=getArg();
						//System.out.println(bi);
						elem.excute(getArg());
					}else
						elem.excute();
					root = tree;
				}
			}
		} catch (EOFException e) {
			// e.printStackTrace();
			//io.error(e);
		} catch (Exception e) {
			// e.printStackTrace();
			io.error(e);
		} finally {
			try {
				raf.close();
			} catch (Exception e) {
				// e.printStackTrace();
				io.error(e);
			}
			io.close();
		}
	}

	public static void main(String[] args) {
		new GMH("./bin/jcj1988/cfg/config.xml","E:\\javaSpace\\Grass-Mud-Horse\\hworld.gmh");
		//Integer i=new Integer(3);
		//System.out.println(i.hashCode());
	}
}
