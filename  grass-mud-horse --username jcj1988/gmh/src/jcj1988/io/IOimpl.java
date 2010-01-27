package jcj1988.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
//import java.io.PrintWriter;

public class IOimpl implements IOable {

	//PrintWriter out = null;
	PrintStream out=null;
	BufferedReader in = null;
	Integer i;

	IOimpl(PrintStream ps, InputStream is) {
		out = ps;
		in = new BufferedReader(new InputStreamReader(is));
	}

	@Override
	public void error(Exception e) {
		out.println(e + ":" + e.getMessage());
	}

	@Override
	public String getString() {
		String s;
		try {
			s = in.readLine();
		} catch (Exception e) {
			// e.printStackTrace();
			s = null;
			error(e);
		}
		return s;
	}

	@Override
	public int getChar() {
		try {
			i = in.read();
		} catch (Exception e) {
			// e.printStackTrace();
			i = 0;
			error(e);
		}
		return i;
	}

	@Override
	public int getInt() {
		try {
			i = Integer.parseInt(in.readLine());
		} catch (Exception e) {
			// e.printStackTrace();
			i = 0;
			error(e);
		}
		return i;
	}

	@Override
	public void putChar(int i) {
		out.append((char) i);
	}

	@Override
	public void print(String s) {
		out.print(s);
	}

	@Override
	public void println(String s) {
		out.println(s);
	}

	@Override
	public void putInt(int i) {
		out.print(i);
	}

	@Override
	public void close() {
		try {
			in.close();
		} catch (Exception e) {
			// e.printStackTrace();
			error(e);
		}
		out.close();
	}

	@Override
	public void flush() {
		out.flush();
	}
}
