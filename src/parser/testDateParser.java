package parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class testDateParser {

	@Test
	public void test() {
		
		Date output;
		DateParser dp = new DateParser();
		SimpleDateFormat sdf;
		String dateString;
		//test null input
		try{
			output = dp.getDate(null);
			fail("No exception thrown.");
		}catch(Exception ex){
			assertTrue(ex instanceof NullPointerException);
			assertTrue(ex.getMessage().contains("the command cannot be null"));
		}
		
		//test empty input
		try {
			output = dp.getDate("");
			assertEquals(null, output);
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//test date string with illegal date flat/leap
		try {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateString = "2015-2-29";
			output = dp.getDate(dateString);
			dateString = "2015-3-1";
			assertEquals(sdf.parse(dateString), sdf.parse(sdf.format(output)));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateString = "2012-2-29";
			output = dp.getDate(dateString);
			dateString = "2012-2-29";
			assertEquals(sdf.parse(dateString), sdf.parse(sdf.format(output)));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//test date string with illegal date, month illegal
		try {
			dateString = "2018-13-29";
			output = dp.getDate(dateString);
			fail("No exception thrown.");
		} catch (Exception ex) {
			assertTrue(ex instanceof IOException);
			assertTrue(ex.getMessage().contains("the date format you entered is incorrect"));
		}
		
		//test date string with illegal date, day illegal
		try {
			dateString = "2018-5-32";
			output = dp.getDate(dateString);
			fail("No exception thrown.");
		} catch (Exception ex) {
			assertTrue(ex instanceof IOException);
			assertTrue(ex.getMessage().contains("the date format you entered is incorrect"));
		}
		
		//test date only string yyyy-MM-dd
		try {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateString = "2015-4-25";
			output = dp.getDate(dateString);
			assertEquals(sdf.parse(dateString), sdf.parse(sdf.format(output)));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//test date only string yyyy/MM/dd
		try {
			sdf = new SimpleDateFormat("yyyy/MM/dd");
			dateString = "2015/4/25";
			output = dp.getDate(dateString);
			assertEquals(sdf.parse(dateString), sdf.parse(sdf.format(output)));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//test date only string MM/dd/yyyy
		try {
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			dateString = "4/25/2015";
			output = dp.getDate(dateString);
			assertEquals(sdf.parse(dateString), sdf.parse(sdf.format(output)));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//test string with both time and date
		try {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			dateString = "2015-4-25 13:00";
			output = dp.getDate(dateString);
			assertEquals(sdf.parse(dateString), output);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//test date string with illegal time, minute illegal
		try {
			dateString = "2015-4-25 13:60";
			output = dp.getDate(dateString);
			fail("no exception thrown");
		} catch (Exception e) {
			assertTrue(e instanceof IOException);
			assertTrue(e.getMessage().contains("the date format you entered is incorrect"));
		}
	}
}
