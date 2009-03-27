package cn.icybear.GrassMudHorse;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;

import javax.script.ScriptException;

/**
 * JOT£¨Just Out of Time£©±àÒëÆ÷£¬ÓÃÓÚ½âÎö£¬ÑéÖ¤²ÝÄàÂíÓïÎÄ¼þÊÇ·ñºÏ·¨¡£
 * ÁíÍâ±¾ÀàµÄmain·½·¨¿ÉÒÔ°É²ÝÄàÂíÓïÎÄ¼þ±àÒë³ÉÎ±»ã±à´úÂë£¬¸úÊÊÒËÔÄ¶Á¡£
 * 
 * @author Bearice
 * 
 */
public class JOTCompiler {

    Reader reader;
    String fileName = "<Unknown>";
    int lineNum = 1;
    int colNum = 1;
    int lastCodeLine;
    int lastCodeCol;

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public JOTCompiler(Reader reader) {
	this.reader = reader;
    }

    public GMHCodeSection compile() throws IOException, ScriptException {
	LinkedList<OpCode> codes = new LinkedList<OpCode>();
	LinkedList<BigInteger> args = new LinkedList<BigInteger>();
	HashMap<BigInteger, Integer> labels = new HashMap<BigInteger, Integer>();
	LinkedList<Integer> lineNums = new LinkedList<Integer>();
	LinkedList<Integer> colNums = new LinkedList<Integer>();
	LinkedList<Integer> labelCheckPoints = new LinkedList<Integer>();
	OpCode code;
	while ((code = readCode()) != OpCode.X_EOF) {
	    BigInteger arg = null;
	    switch (code) {
	    case F_CALL:
	    case F_JMP:
	    case F_JNEG:
	    case F_JZ:
		labelCheckPoints.add(codes.size());
		arg = readUnsignedArg();
		break;
	    case F_MARK:
		arg = readUnsignedArg();
		if (labels.containsKey(arg)) {
		    int i = labels.get(arg);
		    throwScriptException(lastCodeLine, lastCodeCol, fileName,
			    "Duplicated label found: [%s] against defined at line %d, column %d", arg, lineNums.get(i),
			    colNums.get(i));
		} else
		    labels.put(arg, codes.size());
		break;
	    default:
		if (code.needArg)
		    arg = readArg();
	    }
	    codes.add(code);
	    args.add(arg);
	    lineNums.add(lastCodeLine);
	    colNums.add(lastCodeCol);
	}
	GMHCodeSection ret = new GMHCodeSection();
	ret.codes = codes.toArray(new OpCode[0]);
	ret.args = args.toArray(new BigInteger[0]);
	ret.colNums = colNums.toArray(new Integer[0]);
	ret.lineNums = lineNums.toArray(new Integer[0]);
	ret.labels = labels;
	ret.fileName = fileName;
	for (int idx : labelCheckPoints) {
	    if (!labels.containsKey(ret.args[idx]))
		throwScriptException(ret.lineNums[idx], ret.colNums[idx], fileName, "Missing label: %s", ret.args[idx]);
	    else
		ret.args[idx] = BigInteger.valueOf(labels.get(ret.args[idx]));
	}
	return ret;
    }

    public int decompileNext() throws IOException, ScriptException {
	System.out.printf("%d:%d ", lineNum, colNum);
	OpCode code = readCode();
	BigInteger arg = null;
	switch (code) {
	case F_CALL:
	case F_JMP:
	case F_JNEG:
	case F_JZ:
	case F_MARK:
	    arg = readUnsignedArg();
	    break;
	default:
	    if (code.needArg)
		arg = readArg();
	}
	System.out.printf("%n%s", code);
	if (arg != null) {
	    System.out.print(" " + arg);
	}
	System.out.println();
	return code == OpCode.X_EOF ? -1 : 0;
    }

    private BigInteger readUnsignedArg() throws ScriptException, IOException {
	BigInteger ret = null;
	int val;
	while ((val = readNext()) != 'Âí') {
	    if (ret == null)
		ret = BigInteger.ZERO;
	    switch (val) {
	    case '²Ý':
		ret = ret.shiftLeft(1);
		break;
	    case 'Äà':
		ret = ret.shiftLeft(1).add(BigInteger.ONE);
		break;
	    default:
		throwUnexpectedChar(val, createExpected('²Ý', 'Äà', 'Âí'));
	    }
	}
	if (ret == null) {
	    throwUnexpectedChar(val, createExpected('²Ý', 'Äà'));
	}
	return ret;
    }

    private BigInteger readArg() throws ScriptException, IOException {

	// int count = 1;
	BigInteger sign = BigInteger.ZERO;
	int val = readNext();
	switch (val) {
	case '²Ý':
	    sign = BigInteger.ONE;
	    break;
	case 'Äà':
	    sign = BigInteger.ONE.negate();
	    break;
	default:
	    throwUnexpectedChar(val, new int[] { '²Ý', 'Äà' });
	}
	BigInteger ret = BigInteger.ZERO;
	while ((val = readNext()) != 'Âí') {
	    switch (val) {
	    case '²Ý':
		ret = ret.shiftLeft(1);
		break;
	    case 'Äà':
		ret = ret.shiftLeft(1).add(BigInteger.ONE);
		break;
	    default:
		throwUnexpectedChar(val, createExpected('²Ý', 'Äà', 'Âí'));
	    }
	}
	return ret.multiply(sign);
    }

    private OpCode readCode() throws IOException, ScriptException {
	lastCodeLine = lineNum;
	lastCodeCol = colNum;
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

    static boolean readEcho = false;

    int readNext() throws IOException {
	boolean lastRiver = false;
	while (true) {
	    int ret = reader.read();
	    colNum++;
	    switch (ret) {
	    case '²Ý':
	    case 'Äà':
	    case 'Âí':
		if (readEcho)
		    System.out.print((char) ret);
	    case -1:
		return ret;
	    case 'Ð·':
		if (lastRiver) {
		    if (readEcho)
			System.out.print((char) ret);
		    return ret;
		} else
		    continue;
	    case 'ºÓ':
		if (readEcho)
		    System.out.print((char) ret);
		lastRiver = true;
		continue;
	    case '\n':
		lineNum++;
		colNum = 1;
	    }
	    lastRiver = false;
	}
    }

    protected void throwUnexpectedChar(int ch, int... arg) throws ScriptException {
	StringBuilder exp = new StringBuilder();
	if (arg.length != 0) {
	    exp.append(" Expecting: ");
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
	    throwScriptException("Unexpected char: <EOF>%s", exp);
	} else if (ch == '\n') {
	    throwScriptException("Unexpected char: <EOL>%s", exp);
	} else {
	    throwScriptException("Unexpected char: <%c>%s", ch, exp);
	}
    }

    protected void throwScriptException(int line, int col, String file, String fmt, Object... arg)
	    throws ScriptException {
	throw new ScriptException(String.format(fmt, arg), file, line, col);
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
	if (args.length < 1) {
	    System.out.println("Usage: JOTCompiler <gmh_file> [output]");
	    return;
	}
	String file = args[0];
	if (args.length >= 2) {
	    System.setOut(new PrintStream(new FileOutputStream(args[1])));
	}
	readEcho = true;
	BufferedReader reader = new BufferedReader(new FileReader(file));
	JOTCompiler compiler = new JOTCompiler(reader);
	compiler.setFileName(file);
	while (compiler.decompileNext() != -1) {
	}
    }
}
