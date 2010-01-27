package jcj1988.io;


/**
 * 标准输入输出
 * */
public class StdIO implements IOable {
	IOimpl io=new IOimpl(System.out,System.in);

	public void close() {
		io.close();
	}

	public boolean equals(Object obj) {
		return io.equals(obj);
	}

	public void error(Exception e) {
		io.error(e);
	}

	public int getChar() {
		return io.getChar();
	}

	public int getInt() {
		return io.getInt();
	}

	public String getString() {
		return io.getString();
	}

	public int hashCode() {
		return io.hashCode();
	}

	public void print(String s) {
		io.print(s);
	}

	public void println(String s) {
		io.println(s);
	}

	public void putChar(int i) {
		io.putChar(i);
	}

	public void putInt(int i) {
		io.putInt(i);
	}

	public String toString() {
		return io.toString();
	}

	@Override
	public void flush() {
		io.flush();
	}
}
