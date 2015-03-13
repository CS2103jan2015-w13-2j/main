package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class testTitle {

	@Test
	public void test() {
		Parser p = new Parser();
		String output = p.getTitle("add hello-sss -v school");
		assertEquals("hello-sss", output);
		output = p.getTitle("add good night -v home");
		assertEquals("good night", output);
		output = p.getTitle("add buy the Y-50 lenovo laptop -v funan");
		assertEquals("buy the Y-50 lenovo laptop", output);
	}

}
