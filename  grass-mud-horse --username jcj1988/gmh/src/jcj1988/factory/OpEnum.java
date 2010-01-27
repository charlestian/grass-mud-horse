package jcj1988.factory;

import java.math.BigInteger;

import jcj1988.io.IOable;
import jcj1988.oprate.Arith;
import jcj1988.oprate.Flow;
import jcj1988.oprate.Heap;
import jcj1988.oprate.IO;
import jcj1988.oprate.Operatable;
import jcj1988.oprate.Stack;
import jcj1988.vm.CodeSection;

/**
 * 虚拟机操作类工厂的另一种实现方式，是通过继承了操作接口的枚举类来实现的
 * */
public class OpEnum implements Factoryable {
	public static IOable std = null;
	public static Stack s = null;
	public static Heap h = null;
	public static Flow f = null;
	public static Arith a = null;
	public static IO io = null;

	public OpEnum(CodeSection cs) {
		std = cs.getIo();
		s= new Stack(std);
		a= new Arith(s);
		h = new Heap(cs.getVmarg().getHeapMax(), s);
		io = new IO(std, s, h);
		f = new Flow(cs.getMng(), s);
	}

	@Override
	public Operatable Factory(String s) {
		OpCode r = null;
		try {
			r = (OpCode) OpCode.class.getDeclaredField(s).get(this);
		} catch (Exception e) {
			e.printStackTrace();
			// std.error(e);
		}
		// 早先的方法
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

	private enum OpCode implements Operatable {
		// Stack Manipulation
		S_POP(false) {
			@Override
			public void execute() {
				s.pop();
			}
		},
		S_PUSH(true) {
			@Override
			public void execute(BigInteger b) {
				s.push(b);
			}
		},
		S_DUP(false) {
			@Override
			public void execute() {
				s.dup();
			}
		},
		S_COPY(true) {
			@Override
			public void execute(BigInteger b) {
				s.copy(b.intValue());
			}
		},
		S_SWAP(false) {
			@Override
			public void execute() {
				s.swap();
			}
		},
		S_SLIDE(true) {
			@Override
			public void execute(BigInteger b) {
				s.slid(b.intValue());
			}
		},
		// Arithmetic
		A_ADD(false) {
			@Override
			public void execute() {
				a.add();
			}
		},
		A_SUB(false) {
			@Override
			public void execute() {
				a.sub();
			}
		},
		A_MUL(false) {
			@Override
			public void execute() {
				a.mul();
			}
		},
		A_DIV(false) {
			@Override
			public void execute() {
				a.div();
			}
		},
		A_MOD(false) {
			@Override
			public void execute() {
				a.mod();
			}
		},
		// Heap Access
		H_PUT(false) {
			@Override
			public void execute() {
				h.put();
			}
		},
		H_GET(false) {
			@Override
			public void execute() {
				h.get();
			}
		},
		// Flow Control
		F_MARK(true) {
			@Override
			public void execute(BigInteger b) {
				f.mark(b);
			}

			@Override
			public boolean isMarkOpr() {
				return true;
			}
		},
		F_CALL(true) {
			@Override
			public void execute(BigInteger b) {
				f.call(b);
			}
		},
		F_JMP(true) {
			@Override
			public void execute(BigInteger b) {
				f.jmp(b);
			}
		},
		F_JZ(true) {
			@Override
			public void execute(BigInteger b) {
				f.jz(b);
			}
		},
		F_JNEG(true) {
			@Override
			public void execute(BigInteger b) {
				f.jneg(b);
			}
		},
		F_RET(false) {
			@Override
			public void execute() {
				f.ret();
			}
		},
		F_END(false) {
			@Override
			public void execute() {
				f.end();
			}
		},
		// I/O
		I_INT(false) {
			@Override
			public void execute() {
				io.getInt();
			}
		},
		I_CHR(false) {
			@Override
			public void execute() {
				io.getChar();
			}
		},
		O_INT(false) {
			@Override
			public void execute() {
				io.putInt();
			}
		},
		O_CHR(false) {
			@Override
			public void execute() {
				io.putChar();
			}
		},
		// EOF
		X_EOF(false) {
			@Override
			public void execute() {
				// do nothing
				// System.out.println("OK!");//测试用
			}
		};
		public boolean needArg;

		// public String name;

		@Override
		public boolean isNeedArg() {
			return needArg;
		}

		private OpCode(boolean needArg) {
			this.needArg = needArg;
		}

		@Override
		public boolean isMarkOpr() {
			return false;
		}

		@Override
		public String getName() {
			return name();
		}

		@Override
		public void execute() {
			// do nothing
		}

		@Override
		public void execute(BigInteger b) {
			// do nothing
		}

	}

	public static void main(String args[]) {
		// new OpCode(true);
		// OpCode2.A_ADD.execute();
		// Oprater o = new Oprater(100, null, null);
		// o.X_EOF.execute();
		OpEnum s = new OpEnum(null);
		// s.Factory("X_EOF").execute();
		Operatable e = s.Factory("X_EOF");
		e.execute();
	}
}
