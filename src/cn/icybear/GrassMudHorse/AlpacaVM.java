package cn.icybear.GrassMudHorse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Deque;
import java.util.LinkedList;

import javax.script.ScriptException;

public class AlpacaVM {
    LinkedList<BigInteger> opStack = new LinkedList<BigInteger>();
    BigInteger[] heap = new BigInteger[65536];
    Deque<Integer> callStack = new LinkedList<Integer>();
    GMHCodeSection codeSection;
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter stdout = new PrintWriter(System.out);
    int esp = 0;

    public AlpacaVM(GMHCodeSection codeSection) {
	this.codeSection = codeSection;
    }

    public void execute() throws ScriptException {
	while (step() > 0) {
	}
    }

    public int step() throws ScriptException {
	try {
	    OpCode code = codeSection.codes[esp];
	    BigInteger op1 = null, op2 = null;
	    int i1 = 0;
	    switch (code) {
	    case A_ADD:
		op2 = opStack.pop();
		op1 = opStack.pop();
		opStack.push(op1.add(op2));
		break;
	    case A_SUB:
		op2 = opStack.pop();
		op1 = opStack.pop();
		opStack.push(op1.subtract(op2));
		break;
	    case A_MUL:
		op2 = opStack.pop();
		op1 = opStack.pop();
		opStack.push(op1.multiply(op2));
		break;
	    case A_DIV:
		op2 = opStack.pop();
		op1 = opStack.pop();
		opStack.push(op1.divide(op2));
		break;
	    case A_MOD:
		op2 = opStack.pop();
		op1 = opStack.pop();
		opStack.push(op1.mod(op2));
		break;
	    case S_PUSH:
		opStack.push(codeSection.args[esp]);
		break;
	    case S_POP:
		opStack.pop();
		break;
	    case S_DUP:
		op1 = opStack.pop();
		opStack.push(op1);
		opStack.push(op1);
		break;
	    case S_SWAP:
		op2 = opStack.pop();
		op1 = opStack.pop();
		opStack.push(op2);
		opStack.push(op1);
		break;
	    case S_COPY:
		i1 = codeSection.args[esp].intValue();
		opStack.push(opStack.get(opStack.size() - i1));
		break;
	    case S_SLIDE:
		i1 = codeSection.args[esp].intValue();
		op1 = opStack.peek();
		for (int i = 0; i < i1; i++)
		    opStack.pop();
		opStack.push(op1);
		break;
	    case H_GET:
		i1 = opStack.pop().intValue();
		if (i1 >= heap.length)
		    throwExecutionException("Illegal heap access: %d", i1);
		op1 = heap[i1];
		if (op1 == null)
		    op1 = BigInteger.ZERO;
		opStack.push(op1);
		break;
	    case H_PUT:
		op1 = opStack.pop();
		i1 = opStack.pop().intValue();
		if (i1 >= heap.length)
		    throwExecutionException("Illegal heap access: %d", i1);
		heap[i1] = op1;
		break;
	    case I_CHR:
		i1 = opStack.pop().intValue();
		if (i1 >= heap.length)
		    throwExecutionException("Illegal heap access: %d", i1);
		try {
		    heap[i1] = BigInteger.valueOf(stdin.read());
		} catch (IOException e) {
		    throwExecutionException("Error while reading char: %s", e);
		}
		break;
	    case I_INT:
		i1 = opStack.pop().intValue();
		if (i1 >= heap.length)
		    throwExecutionException("Illegal heap access: %d", i1);
		try {
		    while (op1 == null) {
			String line = stdin.readLine();
			if (line == null)
			    throwExecutionException("Error while reading integer: End of input.");
			try {
			    op1 = new BigInteger(line);
			} catch (NumberFormatException e) {
			    stdout.println("Not an integer!");
			}
		    }
		    heap[i1] = op1;
		} catch (IOException e) {
		    throwExecutionException("Error while reading: %s", e);
		}
		break;
	    case O_CHR:
		i1 = opStack.pop().intValue();
		stdout.write(i1);
		stdout.flush();
		break;
	    case O_INT:
		op1 = opStack.pop();
		stdout.print(op1);
		stdout.flush();
		break;
	    case F_RET:
		i1 = callStack.pop();
		esp = i1 - 1;
		break;
	    case F_CALL:
		i1 = codeSection.args[esp].intValue();
		callStack.push(esp + 1);
		esp = i1 - 1;
		break;
	    case F_JMP:
		i1 = codeSection.args[esp].intValue();
		esp = i1 - 1;
		break;
	    case F_JZ:
		op1 = opStack.pop();
		if (op1.signum() == 0) {
		    i1 = codeSection.args[esp].intValue();
		    esp = i1 - 1;
		}
		break;
	    case F_JNEG:
		op1 = opStack.pop();
		if (op1.signum() == -1) {
		    i1 = codeSection.args[esp].intValue();
		    esp = i1 - 1;
		}
		break;
	    case F_END:
		esp = -2;
		break;
	    case F_MARK:
		break;
	    default:
		throwExecutionException("Unknown opcode: %s", code);
		break;
	    }
	    esp++;
	    return esp;
	} catch (ScriptException se) {
	    throw se;
	} catch (Exception e) {
	    throwExecutionException(e);
	    return 0;
	}
    }

    private void throwExecutionException(String fmt, Object... arg) throws ScriptException {
	throw new ScriptException(String.format(fmt, arg), codeSection.fileName, codeSection.lineNums[esp],
		codeSection.colNums[esp]);
    }

    private void throwExecutionException(Throwable t) throws ScriptException {
	ScriptException se = new ScriptException("Uncaught exception", codeSection.fileName, codeSection.lineNums[esp],
		codeSection.colNums[esp]);
	se.initCause(t);
	throw se;
    }

    public static void main(String[] args) throws Exception {
	if (args.length < 1) {
	    System.out.println("Usage: AlpacaVM <gmhfile>");
	    return;
	}
	String file = args[0];
	BufferedReader reader = new BufferedReader(new FileReader(file));
	JOTCompiler compiler = new JOTCompiler(reader);
	compiler.setFileName(file);
	GMHCodeSection cs = compiler.compile();
	AlpacaVM vm = new AlpacaVM(cs);
	vm.execute();
    }
}
