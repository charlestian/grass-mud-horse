package jcj1988.Factory;

import java.math.BigInteger;

import jcj1988.oprate.Oprateable;

public class Compiler implements Factoryable<FacArg> {
	public void init(FacArg o) {
	}

	@Override
	public Oprateable Factory(String s) {
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

	private enum OpCode implements Oprateable {
		// Stack Manipulation
		S_POP(false) {
			public void excute() {
				System.out.println("S_POP");
			}
		},
		S_PUSH(true) {
			public void excute(BigInteger b) {
				System.out.println("S_PUSH " + b);
			}
		},
		S_DUP(false) {
			public void excute() {
				System.out.println("S_DUP");
			}
		},
		S_COPY(true) {
			public void excute(BigInteger b) {
				System.out.println("S_COPY " + b);
			}
		},
		S_SWAP(false) {
			public void excute() {
				System.out.println("S_SWAP");
			}
		},
		S_SLIDE(true) {
			public void excute(BigInteger b) {
				System.out.println("S_SLIDE " + b);
			}
		},
		// Arithmetic
		A_ADD(false) {
			public void excute() {
				System.out.println("A_ADD");
			}
		},
		A_SUB(false) {
			public void excute() {
				System.out.println("A_SUB");
			}
		},
		A_MUL(false) {
			public void excute() {
				System.out.println("A_MUL");
			}
		},
		A_DIV(false) {
			public void excute() {
				System.out.println("A_DIV");
			}
		},
		A_MOD(false) {
			public void excute() {
				System.out.println("A_MOD");
			}
		},
		// Heap Access
		H_PUT(false) {
			public void excute() {
				System.out.println("H_PUT");
			}
		},
		H_GET(false) {
			public void excute() {
				System.out.println("H_GET");
			}
		},
		// Flow Control
		F_MARK(true) {
			public void excute(BigInteger b) {
				System.out.println("F_MARK " + b);
			}
		},
		F_CALL(true) {
			public void excute(BigInteger b) {
				System.out.println("F_CALL " + b);
			}
		},
		F_JMP(true) {
			public void excute(BigInteger b) {
				System.out.println("F_JMP " + b);
			}
		},
		F_JZ(true) {
			public void excute(BigInteger b) {
				System.out.println("F_JZ " + b);
			}
		},
		F_JNEG(true) {
			public void excute(BigInteger b) {
				System.out.println("F_JNEG " + b);
			}
		},
		F_RET(false) {
			public void excute() {
				System.out.println("F_RET");
			}
		},
		F_END(false) {
			public void excute() {
				System.out.println("F_END");
			}
		},
		// I/O
		I_INT(false) {
			public void excute() {
				System.out.println("I_INT");
			}
		},
		I_CHR(false) {
			public void excute() {
				System.out.println("I_CHR");
			}
		},
		O_INT(false) {
			public void excute() {
				System.out.println("O_INT");
			}
		},
		O_CHR(false) {
			public void excute() {
				System.out.println("O_CHR");
			}
		},
		// EOF
		X_EOF(false) {
			public void excute() {
				System.out.println("X_EOF");
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
		// Factoryable s = new Compiler();
		// s.Factory("X_EOF").excute();
		// Oprateable e = s.Factory("O_INT");
		// e.excute();
	}
}