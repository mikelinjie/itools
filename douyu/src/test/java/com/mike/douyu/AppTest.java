package com.mike.douyu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


/**
 * Unit test for simple App.
 */

public class AppTest {
	String Content = "type@=chatmessage/rescode@=0/sender@=21352606/content@=肉酱会5血/snick@";

	@Test
	public void t1() {
		String regex = "/content@=(.*?)/";
		Pattern p = Pattern.compile(regex);
		Matcher m=p.matcher(Content);
		m.find();
		System.out.println(m.group(1));
	}
}
