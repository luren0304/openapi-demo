package com.excel.demo;

public class test {

	
	public static void main(String[] args) {
		String path="/deposits/one/bypath/D1";
		String rule = ".*/bypath/(.*)";
		System.out.println(path.matches(rule));
		System.out.println(path.matches("/+.*(/.*)"));
		//path="/exchangeRate";
		//System.out.println(path.matches("/+.*(/.*)"));
	}
}
