package parser;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class testDateParser {

	@Test
	public void test() {
		DateParser dp = new DateParser();
		Date output = null;
		output = dp.getDate("25-04-2014 11:00 am");
		System.out.println(output);
	}

}
