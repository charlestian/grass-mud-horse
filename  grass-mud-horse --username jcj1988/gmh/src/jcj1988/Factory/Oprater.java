package jcj1988.Factory;

import java.math.BigInteger;

import jcj1988.io.IOable;
import jcj1988.oprate.Arith;
import jcj1988.oprate.Flow;
import jcj1988.oprate.Heap;
import jcj1988.oprate.IO;
import jcj1988.oprate.Oprateable;
import jcj1988.oprate.Stack;

public class Oprater implements Factoryable<FacArg> {
	IOable stdio = null;
	final Stack s = new Stack();
	final Arith a = new Arith(s);
	Heap h = null;
	Flow f = null;
	IO io = null;

	@Override
	public Oprateable Factory(String s) {
		Oprateable r = null;
		try {
			r = (Oprateable) Oprater.class.getDeclaredField(s).get(this);
		} catch (Exception e) {
			e.printStackTrace();
			//stdio.error(e);
		}
		return r;
	}

	public final OpCode S_POP = new OpCode(false) {
		public void excute() {
			s.pop();
		}
	};
	public final OpCode S_PUSH = new OpCode(true) {
		public void excute(BigInteger b) {
			s.push(b);
		}
	};
	public final OpCode S_DUP = new OpCode(false) {
		public void excute() {
			s.dup();
		}
	};
	public final OpCode S_COPY = new OpCode(true) {
		public void excute(BigInteger b) {
			s.copy(b.intValue());
		}
	};
	public final OpCode S_SWAP = new OpCode(false) {
		public void excute() {
			s.swap();
		}
	};
	public final OpCode S_SLIDE = new OpCode(true) {
		public void excute(BigInteger b) {
			s.slid(b.intValue());
		}
	};
	// Arithmetic
	public final OpCode A_ADD = new OpCode(false) {
		public void excute() {
			a.add();
		}
	};
	public final OpCode A_SUB = new OpCode(false) {
		public void excute() {
			a.sub();
		}
	};
	public final OpCode A_MUL = new OpCode(false) {
		public void excute() {
			a.mul();
		}
	};
	public final OpCode A_DIV = new OpCode(false) {
		public void excute() {
			a.div();
		}
	};
	public final OpCode A_MOD = new OpCode(false) {
		public void excute() {
			a.mod();
		}
	};
	// Heap Access
	public final OpCode H_PUT = new OpCode(false) {
		public void excute() {
			h.put();
		}
	};
	public final OpCode H_GET = new OpCode(false) {
		public void excute() {
			h.get();
		}
	};
	// Flow Control
	public final OpCode F_MARK = new OpCode(true) {
		public void excute(BigInteger b) {
			f.mark(b);
		}
	};
	public final OpCode F_CALL = new OpCode(true) {
		public void excute(BigInteger b) {
			f.call(b);
		}
	};
	public final OpCode F_JMP = new OpCode(true) {
		public void excute(BigInteger b) {
			f.jmp(b);
		}
	};
	public final OpCode F_JZ = new OpCode(true) {
		public void excute(BigInteger b) {
			f.jz(b);
		}
	};
	public final OpCode F_JNEG = new OpCode(true) {
		public void excute(BigInteger b) {
			f.jneg(b);
		}
	};
	public final OpCode F_RET = new OpCode(false) {
		public void excute() {
			f.ret();
		}
	};
	public final OpCode F_END = new OpCode(false) {
		public void excute() {
			f.end();
		}
	};
	// I/O
	public final OpCode I_INT = new OpCode(false) {
		public void excute() {
			io.getInt();
		}
	};
	public final OpCode I_CHR = new OpCode(false) {
		public void excute() {
			io.getChar();
		}
	};
	public final OpCode O_INT = new OpCode(false) {
		public void excute() {
			io.putInt();
		}
	};
	public final OpCode O_CHR = new OpCode(false) {
		public void excute() {
			io.putChar();
		}
	};
	// EOF
	public final OpCode X_EOF = new OpCode(false) {
		public void excute() {
			// do nothing
			//System.out.println("OK!");//≤‚ ‘”√
		}
	};

	public void init(FacArg o){
		this.stdio = o.stdio;
		h = new Heap(o.heapMax, s);
		io = new IO(o.stdio, s, h);
		f = new Flow(o.raf);
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
		new Oprater().Factory("X_EOF").excute();
	}
}
