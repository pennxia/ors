package com.lsc.ors.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lsc.ors.beans.OutpatientLogCharacters;
import com.lsc.ors.debug.ConsoleOutput;

public class DataMiner {

	public static int[][] associationOf2Character(
			OutpatientLogCharacters[] list,
			String[] rightCharacterClasses, int rightCharacterType, 
			String[] leftCharacterClasses, int leftCharacterType){
		//initiate result data
		int[][] result = new int[rightCharacterClasses.length + 1][leftCharacterClasses.length + 1];
		for (int i = 0; i < rightCharacterClasses.length; i++) {
			for (int j = 0; j < leftCharacterClasses.length; j++) {
				result[i][j] = 0;
			}
		}
		
		//prepare key map
		Map<String, Integer> rightClassIndexes = new HashMap<String, Integer>();
		Map<String, Integer> leftClassIndexes = new HashMap<String, Integer>();
		String[] split = null;
		for (int i = 0; i < rightCharacterClasses.length; i++) {
			rightClassIndexes.put(rightCharacterClasses[i], i);
		}
		for (int i = 0; i < leftCharacterClasses.length; i++) {
			leftClassIndexes.put(leftCharacterClasses[i], i);
		}
		int rightDivider = getDivider(rightCharacterClasses, rightCharacterType);
		int leftDivider = getDivider(leftCharacterClasses, leftCharacterType);
		ConsoleOutput.pop("DataMiner.associationOf2Characters", "left divider:" + leftDivider);
		int leftMax = getMaxIndex(leftCharacterClasses, leftCharacterType);
		int rightMax = getMaxIndex(rightCharacterClasses, rightCharacterType);
		
		//generate frequency map
		OutpatientLogCharacters olc = null;
		String rightCrctr = null;
		String leftCrctr = null;
		Integer rightCID = null;
		Integer leftCID = null;
		for (int i = 0; i < list.length; i++) {
			olc = list[i];
			//right character
			rightCrctr = olc.get(rightCharacterType);
			rightCID = getClassIndex(rightClassIndexes, rightCharacterType, rightCrctr, rightDivider, rightMax);
			
			//left character can repeatedly present in one transaction
			leftCrctr = olc.get(leftCharacterType);
			if(leftCrctr == null){
				leftCrctr = "null";
			}
			split = leftCrctr.split(",");
			for (int j = 0; j < split.length; j++) {
				leftCID = getClassIndex(leftClassIndexes, leftCharacterType, leftCrctr, leftDivider, leftMax);
				
				if(rightCID != null && leftCID != null){
					result[rightCID][leftCID] ++;
				} else{
					ConsoleOutput.pop("DataMiner.associationOf2Character", "null index");
				}
				if(leftCID != null) result[rightCharacterClasses.length][leftCID] ++;
			}
			if(rightCID != null) result[rightCID][leftCharacterClasses.length] ++;
		}
		result[rightCharacterClasses.length][leftCharacterClasses.length] = list.length;
		return result;
	}
	private static int getDivider(String[] classes, int type){
		String[] split = classes[0].split("-");
		switch (type) {
		case OutpatientLogCharacters.INDEX_RECEPTION:
		case OutpatientLogCharacters.INDEX_REGISTRATION:
			if(split.length <= 1)
				split = classes[1].split("-");
			String[] subSplit = split[1].split(":");
			int minutesAmount = Integer.parseInt(subSplit[0]) * 60 + Integer.parseInt(subSplit[1]);
			subSplit = split[0].split(":");
			minutesAmount -= (Integer.parseInt(subSplit[0]) * 60 + Integer.parseInt(subSplit[1]));
			return minutesAmount;
		case OutpatientLogCharacters.INDEX_PATIENT_AGE:
		case OutpatientLogCharacters.INDEX_WAIT:
			if(split.length > 1) return Integer.parseInt(split[1]) - Integer.parseInt(split[0]);
		default:
			return 1;
		}
	}
	private static int getMaxIndex(String[] classes, int type){
		switch (type) {
		case OutpatientLogCharacters.INDEX_RECEPTION:
		case OutpatientLogCharacters.INDEX_REGISTRATION:
		case OutpatientLogCharacters.INDEX_PATIENT_AGE:
		case OutpatientLogCharacters.INDEX_WAIT:
			String[] split = null;
			for (int i = 0; i < classes.length; i++) {
				split = classes[i].split("-");
				if(split.length == 1) return i;
			}
		default:
			return classes.length - 1;
		}
	}
	private static Integer getClassIndex(Map<String, Integer> keyMap, int type, String value, int divider, int maxIndex){
		if(value == null) return null;
		switch (type) {
		case OutpatientLogCharacters.INDEX_DIAGNOSES:
		case OutpatientLogCharacters.INDEX_DOCTOR:
		case OutpatientLogCharacters.INDEX_FURTHER_CONSULTATION:
		case OutpatientLogCharacters.INDEX_PATIENT_GENDER:
			return keyMap.get(value);
		case OutpatientLogCharacters.INDEX_RECEPTION:
		case OutpatientLogCharacters.INDEX_REGISTRATION:
			String[] timeSplit = value.split(" ");
			timeSplit = timeSplit[1].split(":");
			int minutesAmount = Integer.parseInt(timeSplit[0]) * 60 + Integer.parseInt(timeSplit[1]);

			int quotient = minutesAmount / divider;
			minutesAmount = quotient * divider;
			String timeKey = "" + (minutesAmount / 60) + ":" + (minutesAmount % 60) + "-" +
					((minutesAmount + divider) / 60) + ":" +((minutesAmount +divider) % 60);
			
			Integer timeIndex = keyMap.get(timeKey);
			if(timeIndex == null){
				return maxIndex;
			}
			return timeIndex;
		case OutpatientLogCharacters.INDEX_PATIENT_AGE:
		case OutpatientLogCharacters.INDEX_WAIT:
			int number = Integer.parseInt(value);
			int q = number / divider;
			String numberKey = "" + (q * divider) + "-" + ((q + 1) * divider);
			Integer numberIndex = keyMap.get(numberKey);
			if(numberIndex == null){
				return maxIndex;
			}
			return numberIndex;
		default:
			break;
		}
		return null;
	}
}
