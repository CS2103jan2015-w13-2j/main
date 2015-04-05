package parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testDateParser {
	
	private static final String EXCEPTION_NULLPOINTER = "The command is null";
	
	private Date output;
	private Boolean booleanOutput;
	private DateParser dp;
	private SimpleDateFormat sdf;
	private String dateString;
	
	@Before
	public void initDateParser(){
		dp = new DateParser();
	}
	
	@Test
	public void testNullInput() {
		try{
			output = dp.getDate(null);
			fail("No exception thrown.");
		}catch(Exception ex){
			assertTrue(ex instanceof NullPointerException);
			assertTrue(ex.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
	}
	
	@Test
	public void testEmptyInput() {
		try {
			output = dp.getDate("");
			assertEquals(null, output);
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void testFlatYear() {
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
	}
	
	@Test
	public void testMonthIllegal() {
		try {
			dateString = "2018-13-29";
			output = dp.getDate(dateString);
			fail("No exception thrown.");
		} catch (Exception ex) {
			assertTrue(ex instanceof IOException);
			assertTrue(ex.getMessage().contains("the date format you entered is incorrect"));
		}
	}
	
	@Test
	public void testDayIllegal() {
		try {
			dateString = "2018-5-32";
			output = dp.getDate(dateString);
			fail("No exception thrown.");
		} catch (Exception ex) {
			assertTrue(ex instanceof IOException);
			assertTrue(ex.getMessage().contains("the date format you entered is incorrect"));
		}
	}
	
	@Test
	public void testDateOnly() {
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
	}
	
	@Test
	public void testDateAndTime() {
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
	}
	
	@Test
	public void testMinuteIllegal() {
		try {
			dateString = "2015-4-25 13:60";
			output = dp.getDate(dateString);
			fail("no exception thrown");
		} catch (Exception e) {
			assertTrue(e instanceof IOException);
			assertTrue(e.getMessage().contains("the date format you entered is incorrect"));
		}
	}
	
	@Test
	public void testIsSameDay() {
		try {
			Date d1 = dp.getDate("2015-4-25 13:40");
			Date d2 = dp.getDate("2015-4-25 12:00");
			booleanOutput = dp.isSameDay(d1, d2);
			assertEquals(true, booleanOutput);
			d1 = dp.getDate("2015-4-26 13:30");
			d2 = dp.getDate("2015-4-25 12:00");
			booleanOutput = dp.isSameDay(d1, d2);
			assertEquals(false, booleanOutput);
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void cleanUp() {
		dp = null;
	}
}
