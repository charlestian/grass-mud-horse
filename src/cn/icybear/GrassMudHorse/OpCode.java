package cn.icybear.GrassMudHorse;

public enum OpCode {
    // Stack Manipulation
    S_POP(false), S_PUSH(true), S_DUP(false), S_COPY(true), S_SWAP(false), S_SLIDE(true),
    // Arithmetic
    A_ADD(false), A_SUB(false), A_MUL(false), A_DIV(false), A_MOD(false),
    // Heap Access
    H_PUT(false), H_GET(false),
    // Flow Control
    F_MARK(true), F_CALL(true), F_JMP(true), F_JZ(true), F_JNEG(true), F_RET(false), F_END(false),
    // I/O
    I_INT(false), I_CHR(false), O_INT(false), O_CHR(false),
    // EOF
    X_EOF(false);
    public final boolean needArg;

    private OpCode(boolean needArg) {
	this.needArg = needArg;
    }
}
