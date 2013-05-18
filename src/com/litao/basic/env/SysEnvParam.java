package com.litao.basic.env;

import java.util.Map;
import java.util.Properties;

public class SysEnvParam {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("*** get system environment variable ***");
		Map<String, String> envMap = System.getenv();
		for(String key : envMap.keySet()) {
			String value = envMap.get(key);
			System.out.println(String.format("%s: %s", key, value));
		}
		
		// ���Ի�ȡ-Dָ���Ĳ���
		System.out.println("\n*** get system property ***");
		Properties properties = System.getProperties();
		for(Object key : properties.keySet()) {
			Object value = properties.get(key);
			System.out.println(String.format("%s: %s", key, value));
		}
		
		while(true) {
			Thread.sleep(60 * 1000);
		}
	}
}
