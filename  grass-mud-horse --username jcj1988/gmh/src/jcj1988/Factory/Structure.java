package jcj1988.Factory;

import java.math.BigInteger;

import jcj1988.io.IOable;
import jcj1988.oprate.Arith;
import jcj1988.oprate.Flow;
import jcj1988.oprate.Heap;
import jcj1988.oprate.IO;
import jcj1988.oprate.Oprateable;
import jcj1988.oprate.Stack;

public class Structure implements Factoryable<FacArg> {
	public static IOable std = null;
	public static Stack s = new Stack();
	public static Heap h = null;
	public static Flow f = null;
	public static Arith a = new Arith(s);
	public static IO io = null;

	public void init(FacArg o) {
		std = o.stdio;
		h = new Heap(o.heapMax, s);
		io = new IO(o.stdio, s, h);
		f = new Flow(o.raf);
	}

	@Override
	public Oprateable Factory(String s) {
		OpCode r = null;
		try {
			r=(OpCode) OpCode.class.getDeclaredField(s).get(this);
		} catch (Exception e) {
			e.printStackTrace();
			//std.error(e);
		}
		//早先的方法
		// OpCode[] oc = OpCode.values();
		// for (int i = 0; i < oc.length; i++) {
		// if (oc[i].name().equals(s)) {
		// r = oc[i];
		// break;
		// }
		// }
		return r;
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

	private enum OpCode implements Oprateable {
		// Stack Manipulation
		S_POP(false) {
			public void excute() {
				s.pop();
			}
		},
		S_PUSH(true) {
			public void excute(BigInteger b) {
				s.push(b);
			}
		},
		S_DUP(false) {
			public void excute() {
				s.dup();
			}
		},
		S_COPY(true) {
			public void excute(BigInteger b) {
				s.copy(b.intValue());
			}
		},
		S_SWAP(false) {
			public void excute() {
				s.swap();
			}
		},
		S_SLIDE(true) {
			public void excute(BigInteger b) {
				s.slid(b.intValue());
			}
		},
		// Arithmetic
		A_ADD(false) {
			public void excute() {
				a.add();
			}
		},
		A_SUB(false) {
			public void excute() {
				a.sub();
			}
		},
		A_MUL(false) {
			public void excute() {
				a.mul();
			}
		},
		A_DIV(false) {
			public void excute() {
				a.div();
			}
		},
		A_MOD(false) {
			public void excute() {
				a.mod();
			}
		},
		// Heap Access
		H_PUT(false) {
			public void excute() {
				h.put();
			}
		},
		H_GET(false) {
			public void excute() {
				h.get();
			}
		},
		// Flow Control
		F_MARK(true) {
			public void excute(BigInteger b) {
				f.mark(b);
			}
		},
		F_CALL(true) {
			public void excute(BigInteger b) {
				f.call(b);
			}
		},
		F_JMP(true) {
			public void excute(BigInteger b) {
				f.jmp(b);
			}
		},
		F_JZ(true) {
			public void excute(BigInteger b) {
				f.jz(b);
			}
		},
		F_JNEG(true) {
			public void excute(BigInteger b) {
				f.jneg(b);
			}
		},
		F_RET(false) {
			public void excute() {
				f.ret();
			}
		},
		F_END(false) {
			public void excute() {
				f.end();
			}
		},
		// I/O
		I_INT(false) {
			public void excute() {
				io.getInt();
			}
		},
		I_CHR(false) {
			public void excute() {
				io.getChar();
			}
		},
		O_INT(false) {
			public void excute() {
				io.putInt();
			}
		},
		O_CHR(false) {
			public void excute() {
				io.putChar();
			}
		},
		// EOF
		X_EOF(false) {
			public void excute() {
				// do nothing
				//System.out.println("OK!");//测试用
			}
		};
		public boolean needArg;

		public boolean isNeedArg() {
			return needArg;
		}

		private OpCode(boolean needArg) {
			this.needArg = needArg;
		}

		public void excute() {
			// do nothing
		}

		public void excute(BigInteger b) {
			// do nothing
		}
	}

	public static void main(String args[]) {
		// new OpCode(true);
		// OpCode2.A_ADD.excute();
		// Oprater o = new Oprater(100, null, null);
		// o.X_EOF.excute();
		Structure s = new Structure();
		// s.Factory("X_EOF").excute();
		Oprateable e = s.Factory("X_EOF");
		e.excute();
	}
}
