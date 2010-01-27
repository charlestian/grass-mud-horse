package jcj1988.io;

/**
 * 输入输出接口，方便移植 （主要是指命令界面与图形界面间的移植）
 * */
public interface IOable {
	/**
	 * 输入函数（获得一个整数）
	 * **/
	public int getInt();

	/**
	 * 输入函数（获得一个字符）
	 * */
	public int getChar();

	/**
	 * 输入函数（获得一个字符串）
	 * **/
	public String getString();

	/**
	 * 输出函数(不带换行)
	 * **/
	public void print(String s);

	/**
	 * 输出函数(带换行)
	 * **/
	public void println(String s);

	/**
	 * 输出函数（输出一个字符）
	 * */
	public void putChar(int i);
	
	/**
	 * 输出函数（输出一个数字）
	 * */
	public void putInt(int i);
	/**
	 * 刷新流
	 * */
	public void flush();
	/**
	 * 错误输出函数
	 * **/
	public void error(Exception e);

	/**
	 * 关闭输入输出流
	 * **/
	public void close();
}
