package cn.icybear.GrassMudHorse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import javax.script.ScriptException;

public class AlpacaVM {
    Deque<Integer> stack = new LinkedList<Integer>();
    ArrayList<Integer> heap = new ArrayList<Integer>();

    Reader reader;
    public String fileName = "<Unknown>";

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public AlpacaVM(Reader reader) {
	this.reader = reader;
    }

    public int next() throws IOException, ScriptException {
	OpCode code = readCode();
	int arg = 0;
	if (code.needArg)
	    arg = readArg();
	System.out.print(code.name());
	if (code.needArg) {
	    System.out.print(" " + arg);
	}
	System.out.println();
	return code == OpCode.X_EOF ? -1 : 0;
    }

    private int readArg() throws ScriptException, IOException {
	int ret = 0;
	int count = 1;
	int sign = 0;
	int val = readNext();
	switch (val) {
	case '²Ý':
	    sign = 1;
	    break;
	case 'Äà':
	    sign = -1;
	    break;
	default:
	    throwUnexpectedChar(val, new int[] { '²Ý', 'Äà' });
	}
	while ((val = readNext()) != 'Âí') {
	    if (++count >= 31)
		throwUnexpectedChar(val, createExpected('Âí'));
	    switch (val) {
	    case '²Ý':
		ret <<= 1;
		break;
	    case 'Äà':
		ret <<= 1;
		ret += 1;
		break;
	    default:
		throwUnexpectedChar(val, createExpected('²Ý', 'Äà', 'Âí'));
	    }
	}

	return sign * ret;
    }

    private OpCode readCode() throws IOException, ScriptException {
	int imp = readNext();
	int[] expected = createExpected();
	outer: switch (imp) {
	case -1:
	    return OpCode.X_EOF;
	case 'Ð·':
	    // End
	    return OpCode.F_END;
	case '²Ý':
	    // Stack
	    imp = readNext();
	    switch (imp) {
	    case '²Ý':
		// Push
		return OpCode.S_PUSH;
	    case 'Äà':
		imp = readNext();
		switch (imp) {
		case '²Ý':
		    // Copy
		    return OpCode.S_COPY;
		case 'Âí':
		    // Slide
		    return OpCode.S_SLIDE;
		}
		expected = createExpected('²Ý', 'Âí');
		break outer;
	    case 'Âí':
		imp = readNext();
		switch (imp) {
		case '²Ý':
		    // Dup
		    return OpCode.S_DUP;
		case 'Äà':
		    // Swap
		    return OpCode.S_SWAP;
		case 'Âí':
		    // Pop
		    return OpCode.S_POP;
		}
	    }
	    expected = createExpected('²Ý', 'Äà', 'Âí');
	    break outer;
	case 'Âí':
	    // Flow Control
	    imp = readNext();
	    switch (imp) {
	    case '²Ý':
		imp = readNext();
		switch (imp) {
		case '²Ý':
		    // Mark
		    return OpCode.F_MARK;
		case 'Äà':
		    // Call
		    return OpCode.F_CALL;
		case 'Âí':
		    // Jump
		    return OpCode.F_JMP;
		}
		expected = createExpected('²Ý', 'Äà', 'Âí');
		break outer;
	    case 'Äà':
		imp = readNext();
		switch (imp) {
		case '²Ý':
		    // Jump if Zero
		    return OpCode.F_JZ;
		case 'Äà':
		    // Jump if Negative
		    return OpCode.F_JNEG;
		case 'Âí':
		    // Return
		    return OpCode.F_RET;
		}
		expected = createExpected('²Ý', 'Äà', 'Âí');
		break outer;
	    case 'Âí':
		imp = readNext();
		switch (imp) {
		case 'Âí':
		    // End
		    return OpCode.F_END;
		}
		expected = createExpected('Âí');
		break outer;
	    }
	    expected = createExpected('²Ý', 'Äà', 'Âí');
	    break outer;
	case 'Äà':
	    imp = readNext();
	    switch (imp) {
	    case '²Ý':
		// Arithmetic
		imp = readNext();
		switch (imp) {
		case '²Ý':
		    imp = readNext();
		    switch (imp) {
		    case '²Ý':
			// Addition
			return OpCode.A_ADD;
		    case 'Äà':
			// Subtraction
			return OpCode.A_SUB;
		    case 'Âí':
			// Multiplication
			return OpCode.A_MUL;
		    }
		    expected = createExpected('²Ý', 'Äà', 'Âí');
		    break outer;
		case 'Äà':
		    imp = readNext();
		    switch (imp) {
		    case '²Ý':
			// Division
			return OpCode.A_DIV;
		    case 'Äà':
			// Modulo
			return OpCode.A_MOD;
		    }
		    expected = createExpected('²Ý', 'Äà');
		    break outer;
		}
		expected = createExpected('²Ý', 'Äà');
		break outer;
	    case 'Äà':
		// Heap access
		imp = readNext();
		switch (imp) {
		case '²Ý':
		    // Store
		    return OpCode.H_PUT;
		case 'Äà':
		    // Retrieve
		    return OpCode.H_GET;
		}
		expected = createExpected('²Ý', 'Äà');
		break outer;
	    case 'Âí':
		// IO Control
		imp = readNext();
		switch (imp) {
		case '²Ý':
		    imp = readNext();
		    switch (imp) {
		    case '²Ý':
			// Output the character at the top of the stack
			return OpCode.O_CHR;
		    case 'Äà':
			// Output the number at the top of the stack
			return OpCode.O_INT;
		    }
		    expected = createExpected('²Ý', 'Äà');
		    break outer;
		case 'Äà':
		    imp = readNext();
		    switch (imp) {
		    case '²Ý':
			// Read a character and place it in the location given
			// by the top of the stack
			return OpCode.I_CHR;
		    case 'Äà':
			// Read a number and place it in the location given by
			// the top of the stack
			return OpCode.I_INT;
		    }
		    expected = createExpected('²Ý', 'Äà');
		    break outer;
		}
		expected = createExpected('²Ý', 'Äà');
		break outer;
	    }
	    expected = createExpected('²Ý', 'Äà', 'Âí', 'Ð·', -1);
	}
	throwUnexpectedChar(imp, expected);
	return null;
    }

    private int[] createExpected(int... arg) {
	return arg;
    }

    int lineNum = 1;
    int colNum = 1;

    int readNext() throws IOException {
	boolean lastRiver = false;
	while (true) {
	    int ret = reader.read();
	    colNum++;
	    switch (ret) {
	    case '²Ý':
	    case 'Äà':
	    case 'Âí':
	    case -1:
		System.out.print((char) ret);
		lastRiver = false;
		return ret;
	    case 'Ð·':
		if (lastRiver) {
		    System.out.print((char) ret);
		    return ret;
		} else
		    continue;
	    case 'ºÓ':
		System.out.print((char) ret);
		lastRiver = true;
		continue;
	    case '\n':
		lineNum++;
		colNum = 1;
	    }
	}
    }

    protected void throwUnexpectedChar(int ch, int... arg) throws ScriptException {
	StringBuilder exp = new StringBuilder();
	if (arg.length != 0) {
	    exp.append("Expecting: ");
	    for (int c : arg) {
		exp.append('<');
		switch (c) {
		case '\n':
		    exp.append("EOL");
		    break;
		case -1:
		    exp.append("EOF");
		    break;
		default:
		    exp.append((char) c);
		}
		exp.append('>');
		exp.append(' ');
	    }
	}
	if (ch == -1) {
	    throwScriptException("Unexpected char: <EOF> %s", exp);
	} else if (ch == '\n') {
	    throwScriptException("Unexpected char: <EOL> %s", exp);
	} else {
	    throwScriptException("Unexpected char: <%c> %s", ch, exp);
	}
    }

    protected void throwScriptException(String fmt, Object... arg) throws ScriptException {
	throw new ScriptException(String.format(fmt, arg), fileName, lineNum, colNum);
    }

    /**
     * @param args
     * @throws IOException
     * @throws ScriptException
     */
    public static void main(String[] args) throws IOException, ScriptException {
	BufferedReader reader = new BufferedReader(new FileReader("test.gmh"));
	AlpacaVM vm = new AlpacaVM(reader);
	while (vm.next() != -1) {
	}
    }

}
