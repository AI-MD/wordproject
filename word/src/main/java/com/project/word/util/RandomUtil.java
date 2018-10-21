package com.project.word.util;

import java.util.Random;

public class RandomUtil {
	 private static final Random RANDOM = new Random();

	    public int getNextInt(int x) {
	        return RANDOM.nextInt(x);
	    }
}


