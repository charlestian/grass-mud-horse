package cn.icybear.GrassMudHorse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 一个转换Whitespace(.ws)到GrassMudHorse（.gmh）的工具。
 * 
 * @author Bearice
 * 
 */
public class WS2GMH {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
	if (args.length < 1) {
	    System.out.println("Usage WS2GMH <ws_file> [gsh_file]");
	    return;
	}
	String wsf = args[0];
	String gmhf = args.length > 2 ? args[2] : wsf.replaceFirst("\\.ws$", ".gmh");
	BufferedReader reader = new BufferedReader(new FileReader(wsf));
	BufferedWriter writer = new BufferedWriter(new FileWriter(gmhf, false));
	int ch;
	while ((ch = reader.read()) != -1) {
	    switch (ch) {
	    case ' ':
		// writer.append("[Space]");
		ch = '草';
		break;
	    case '\t':
		// writer.append("[Tab]");
		ch = '泥';
		break;
	    case '\n':
		// writer.append("[LF]\n");
		writer.append('马');
		break;
	    }
	    writer.append((char) ch);
	}
	reader.close();
	writer.close();
    }

}
