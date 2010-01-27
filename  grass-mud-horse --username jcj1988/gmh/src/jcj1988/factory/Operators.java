package jcj1988.factory;

import java.math.BigInteger;

import jcj1988.io.IOable;
import jcj1988.oprate.Arith;
import jcj1988.oprate.Flow;
import jcj1988.oprate.Heap;
import jcj1988.oprate.IO;
import jcj1988.oprate.Operatable;
import jcj1988.oprate.Stack;
import jcj1988.oprate.OpCode;
import jcj1988.vm.CodeSection;

/**
 * 虚拟机操作类工厂的一种实现方式，是通过类族和静态对象的方式实现的
 * */
public class Operators implements Factoryable {
	IOable stdio = null;
	Stack s =null;
	Arith a = null;
	Heap h = null;
	Flow f = null;
	IO io = null;

	@Override
	public Operatable Factory(String s) {
		Operatable r = null;
		try {
			r = (Operatable) Operators.class.getDeclaredField(s).get(this);
		} catch (Exception e) {
			e.printStackTrace();
			// stdio.error(e);
		}
		return r;
	}

	public final OpCode S_POP = new OpCode("S_POP", false) {
		@Override
		public void execute() {
			s.pop();
		}
	};
	public final OpCode S_PUSH = new OpCode("S_PUSH", true) {
		@Override
		public void execute(BigInteger b) {
			s.push(b);
		}
	};
	public final OpCode S_DUP = new OpCode("S_DUP", false) {
		@Override
		public void execute() {
			s.dup();
		}
	};
	public final OpCode S_COPY = new OpCode("S_COPY", true) {
		@Override
		public void execute(BigInteger b) {
			s.copy(b.intValue());
		}
	};
	public final OpCode S_SWAP = new OpCode("S_SWAP", false) {
		@Override
		public void execute() {
			s.swap();
		}
	};
	public final OpCode S_SLIDE = new OpCode("S_SLIDE", true) {
		@Override
		public void execute(BigInteger b) {
			s.slid(b.intValue());
		}
	};
	// Arithmetic
	public final OpCode A_ADD = new OpCode("A_ADD", false) {
		@Override
		public void execute() {
			a.add();
		}
	};
	public final OpCode A_SUB = new OpCode("A_SUB", false) {
		@Override
		public void execute() {
			a.sub();
		}
	};
	public final OpCode A_MUL = new OpCode("A_MAL", false) {
		@Override
		public void execute() {
			a.mul();
		}
	};
	public final OpCode A_DIV = new OpCode("A_DV", false) {
		@Override
		public void execute() {
			a.div();
		}
	};
	public final OpCode A_MOD = new OpCode("A_MOD", false) {
		@Override
		public void execute() {
			a.mod();
		}
	};
	// Heap Access
	public final OpCode H_PUT = new OpCode("H_PUT", false) {
		@Override
		public void execute() {
			h.put();
		}
	};
	public final OpCode H_GET = new OpCode("H_GET", false) {
		@Override
		public void execute() {
			h.get();
		}
	};
	// Flow Control
	public final OpCode F_MARK = new OpCode("F_MARK", true) {
		@Override
		public void execute(BigInteger b) {
			f.mark(b);
		}

		@Override
		public boolean isMarkOpr() {
			return true;
		}
	};
	public final OpCode F_CALL = new OpCode("F_CALL", true) {
		@Override
		public void execute(BigInteger b) {
			f.call(b);
		}
	};
	public final OpCode F_JMP = new OpCode("F_JMP", true) {
		@Override
		public void execute(BigInteger b) {
			f.jmp(b);
		}
	};
	public final OpCode F_JZ = new OpCode("F_JZ", true) {
		@Override
		public void execute(BigInteger b) {
			f.jz(b);
		}
	};
	public final OpCode F_JNEG = new OpCode("F_JNEG", true) {
		@Override
		public void execute(BigInteger b) {
			f.jneg(b);
		}
	};
	public final OpCode F_RET = new OpCode("F_RET", false) {
		@Override
		public void execute() {
			f.ret();
		}
	};
	public final OpCode F_END = new OpCode("F_END", false) {
		@Override
		public void execute() {
			f.end();
		}
	};
	// I/O
	public final OpCode I_INT = new OpCode("F_INT", false) {
		@Override
		public void execute() {
			io.getInt();
		}
	};
	public final OpCode I_CHR = new OpCode("I_CHR", false) {
		@Override
		public void execute() {
			io.getChar();
		}
	};
	public final OpCode O_INT = new OpCode("O_INT", false) {
		@Override
		public void execute() {
			io.putInt();
		}
	};
	public final OpCode O_CHR = new OpCode("O_CHR", false) {
		@Override
		public void execute() {
			io.putChar();
		}
	};
	// EOF
	public final OpCode X_EOF = new OpCode("X_EOF", false) {
		@Override
		public void execute() {
			// do nothing
			// System.out.println("OK!");//测试用
		}
	};

	public Operators(CodeSection cs) {
		this.stdio = cs.getIo();
		s= new Stack(stdio);
		a= new Arith(s);
		h = new Heap(cs.getVmarg().getHeapMax(), s);
		io = new IO(stdio, s, h);
		f = new Flow(cs.getMng(), s);
	}

	public Stack getStack() {
		return s;
	}

	public Heap getHeap() {
		return h;
	}

	public Flow getFlow() {
		return f;
	}

	public Arith getArith() {
		return a;
	}

	public IO getIo() {
		return io;
	}

	public static void main(String args[]) {
		new Operators(null).Factory("X_EOF").execute();
	}
}
