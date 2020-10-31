package com.marcusbornman.cos_790_assignment_3;

import org.moeaframework.problem.tsplib.TSPInstance;

import java.io.File;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		TSPInstance problem = new TSPInstance(new File("./data/tsp/pcb442.tsp"));
	}
}
