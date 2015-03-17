package parser;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class testDateParser {

	@Test
	public void test() {
		DateParser dp = new DateParser();
		Date output = dp.getDate(null);
		System.out.println(output);
	}

}
