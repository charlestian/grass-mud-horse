package jcj1988.vm;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.ArrayList;

import jcj1988.analyze.Analyzable;
import jcj1988.analyze.AnalyzeImpl;
import jcj1988.cfg.Config;
import jcj1988.cfg.VMarg;
import jcj1988.factory.Factoryable;
import jcj1988.factory.OpEnum;
import jcj1988.factory.Operators;
import jcj1988.io.IOable;
import jcj1988.io.StdIO;
import jcj1988.io.ToFile;
import jcj1988.manage.CmpMng;
import jcj1988.manage.DebugMng;
import jcj1988.manage.Manager;
import jcj1988.manage.RunMng;
import jcj1988.oprate.Executable;
import jcj1988.oprate.Operatable;
import jcj1988.oprate.Operator;

public class GMH implements Executable {
	public IOable io = null;
	public Config cfg = null;
	Reader reader = null;
	public VMarg vmarg = null;
	CodeSection cs = null;
	public Factoryable fct = null;
	// public TriNode<Operatable> tree = null;
	Analyzable alz = null;
	Manager mng = null;

	public GMH(String xml, String src, Mode mode) {
		io = new StdIO();
		switch (mode) {
		case RUN:
			mng = new RunMng();
			break;
		case CMP:
			mng = new CmpMng(io);
			break;
		case DBG:
			mng = new DebugMng(io);
			break;
		}
		cfg = new Config(xml);
		vmarg = cfg.getVMarg();
		cs = new CodeSection(src, io, vmarg, mng);
		fct = new OpEnum(cs);// OpEnum(cs);Operators(cs);
		alz = new AnalyzeImpl(cs, cfg.buildCmdTree(fct), mng);
	}

	@Override
	public void execute() {
		alz.analyse();
		mng.execute();
		io.close();
	}

	enum Mode {
		RUN, CMP, DBG;
	}

	public static void main(String[] args) {
		if (args.length > 0)
			new GMH("config.xml", args[0], Mode.RUN).execute();
		else System.out.println("请加命令行参数，gmh文本文件名，如：\nGMH hworld.gmh\n");
	}
}
