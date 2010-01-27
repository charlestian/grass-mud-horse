package jcj1988.io;

/**
 * ��������ӿڣ�������ֲ ����Ҫ��ָ���������ͼ�ν�������ֲ��
 * */
public interface IOable {
	/**
	 * ���뺯�������һ��������
	 * **/
	public int getInt();

	/**
	 * ���뺯�������һ���ַ���
	 * */
	public int getChar();

	/**
	 * ���뺯�������һ���ַ�����
	 * **/
	public String getString();

	/**
	 * �������(��������)
	 * **/
	public void print(String s);

	/**
	 * �������(������)
	 * **/
	public void println(String s);

	/**
	 * ������������һ���ַ���
	 * */
	public void putChar(int i);
	
	/**
	 * ������������һ�����֣�
	 * */
	public void putInt(int i);
	/**
	 * ˢ����
	 * */
	public void flush();
	/**
	 * �����������
	 * **/
	public void error(Exception e);

	/**
	 * �ر����������
	 * **/
	public void close();
}
