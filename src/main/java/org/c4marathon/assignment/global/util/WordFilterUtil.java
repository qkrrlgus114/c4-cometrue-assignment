package org.c4marathon.assignment.global.util;

import java.util.HashSet;
import java.util.Set;

public class WordFilterUtil {

	private static final Set<String> wordFilter = new HashSet<>(Set.of("씨발", "개새끼"));

	public static boolean containsBannedWord(String input){

		for(String word : wordFilter){
			if(input.toLowerCase().contains(word)){
				return true;
			}
		}

		return false;
	}
}
