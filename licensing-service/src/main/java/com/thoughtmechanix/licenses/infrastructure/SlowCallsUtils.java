package com.thoughtmechanix.licenses.infrastructure;

import java.util.Random;

public class SlowCallsUtils {
	
	private static void sleep() {
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void randomRunSlow() {
		Random random = new Random();
		
		int randomInt = random.nextInt((3 - 1) + 1) + 1;
		
		if (randomInt == 3) {
			sleep();
		}
	}

}
