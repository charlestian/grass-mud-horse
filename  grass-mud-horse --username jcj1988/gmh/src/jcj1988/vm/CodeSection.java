package jcj1988.vm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import jcj1988.cfg.VMarg;
import jcj1988.io.IOable;
import jcj1988.manage.Manager;

/**
 * 虚拟机代码容器，负责保存整个gmh代码以及运行过程中的一些参数。
 * 由于属于参数传输容器类，可能日后改动较大，只要遵循可改不可删，只增不减的原则可以保证改动有最少的牵连
 * */
public class CodeSection {
	private String filename;
	private BufferedReader reader;
	private VMarg vmarg;
	private IOable io;
	private Manager mng;

	// Integer[] lineNums;
	// Integer[] colNums;

	public Manager getMng() {
		return mng;
	}

	public CodeSection(String file, IOable io, VMarg vmarg,Manager mng) {
		this.io = io;
		this.vmarg = vmarg;
		this.mng=mng;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			io.error(e);
		}
	}

	public String getFilename() {
		return filename;
	}

	public VMarg getVmarg() {
		return vmarg;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public IOable getIo() {
		return io;
	}	
}
