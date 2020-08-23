package timetabling;

import timetabling.utils.ProblemReader;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		String filePath = "problems/exam_comp_set1.exam";

		try {
			Problem problem = ProblemReader.problemFromFile(filePath);
			System.out.println("Success, WHOOP WHOOP!");
		} catch (IOException e) {
			System.out.println("File at path `" + filePath + "` could not be found.");
		}
	}
}
