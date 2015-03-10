package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class testTitle {

	@Test
	public void test() {
		Parser p = new Parser();
		p.initParser();
		String output = p.getTitle("add hello");
		assertEquals("hello", output);
		output = p.getTitle("add good night -v home");
		assertEquals("good night", output);
		output = p.getTitle("display");
		System.out.println(output);
	}

}
