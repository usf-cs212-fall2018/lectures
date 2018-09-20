import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class StreamDemo {

	public static void print(String element) {
		System.out.print(element);
		System.out.print(" ");
	}

	public static void main(String[] args) throws IOException {

		/*
		 * https://en.wikipedia.org/wiki/List_of_computing_and_IT_abbreviations
		 * API - Application Program Interface
		 * BLOB - Binary Large OBject
		 * CPU - Central Processing Unit
		 * DBMS - DataBase Management System
		 * EOF - End Of File
		 * FIFO - First In First Out
		 * GUI - Fraphical User Interface
		 * HDD - HardDisk Drive
		 * IDE - Integrated Development Environment
		 * JSON - JavaScript Object Notation
		 * OOP - Object Oriented Programming
		 * URI - Uniform Resource Identifier
		 * WYSIWYG - What You See Is What You Get
		 */

		ArrayList<String> acronyms = new ArrayList<>();
		Collections.addAll(acronyms, "IDE", "CPU", "URI", "HDD", "GUI", "API", "OOP", "EOF");
		Collections.addAll(acronyms, "DBMS", "BLOB", "FIFO", "JSON");
		Collections.addAll(acronyms, "WYSIWYG");

		int i = 0;

		System.out.print(++i + ": ");
		// TODO
		System.out.println();

		// TODO

	}

}
