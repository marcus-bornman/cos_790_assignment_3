package com.marcusbornman.cos_790_assignment_3;

import com.marcusbornman.cos_790_assignment_3.evohyp.EvohypProblem;
import com.marcusbornman.cos_790_assignment_3.evohyp.EvohypSolution;
import com.marcusbornman.cos_790_assignment_3.utils.NodeAttributes;
import distrgenprog.DistrGenProg;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.moeaframework.problem.tsplib.TSPInstance;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws ParseException, IOException {
		// Set up command line options
		Options options = new Options();
		options.addRequiredOption("p", "problemFile", true, "The problem instance file for the travelling salesman problem.");
		options.addRequiredOption("g", "geneticParamFile", true, "The genetic parameters file.");
		options.addRequiredOption("o", "outputFile", true, "The file to write the results of the experiment to.");
		options.addRequiredOption("n", "numRuns", true, "The number of test runs to execute.");
		CommandLine cl = new DefaultParser().parse(options, args);

		// Get command line option values
		String problemFile = cl.getOptionValue("p");
		String parameterFile = cl.getOptionValue("g");
		String outputFile = cl.getOptionValue("o");
		int numRuns = Integer.parseInt(cl.getOptionValue("n"));

		// Run the experiment
		System.out.println("Starting Algorithm Execution");
		System.out.println("Problem File: " + problemFile);
		System.out.println("Genetic Parameter File: " + parameterFile);
		System.out.println("Number of Runs: " + numRuns);
		System.out.println("Please be patient. This may take a while.");
		runExperiment(problemFile, parameterFile, outputFile, numRuns);
		System.out.println("Search complete. See " + outputFile + " for experiment results.");
	}

	private static void runExperiment(String problemFile, String parameterFile, String outputFile, int numRuns) throws IOException {
		//noinspection ResultOfMethodCallIgnored
		new File(outputFile).getParentFile().mkdirs();// to create file if it doesn't exist

		EvohypProblem problem = new EvohypProblem(new TSPInstance(new File(problemFile)));
		problem.setAttribs(NodeAttributes.supportedAttributes);
		List<Duration> runtimes = new ArrayList<>();
		List<EvohypSolution> solutions = new ArrayList<>();
		for (int i = 1; i <= numRuns; i++) {
			DistrGenProg distrGenProg = new DistrGenProg(i, NodeAttributes.supportedAttributes, 1, 4);
			distrGenProg.setParameters(parameterFile);
			distrGenProg.setProblem(problem);

			LocalDateTime before = LocalDateTime.now();
			EvohypSolution solution = (EvohypSolution) distrGenProg.evolve();
			LocalDateTime after = LocalDateTime.now();
			Duration runtime = Duration.between(before, after);

			runtimes.add(runtime);
			solutions.add(solution);
		}

		writeResultsToFile(solutions, runtimes, outputFile);
	}

	private static void writeResultsToFile(List<EvohypSolution> solutions, List<Duration> runtimes, String outputFile) throws IOException {
		List<String> lines = new ArrayList<>();
		for (int i = 0; i < solutions.size(); i++) {
			lines.add("=========================================================");
			lines.add("Run " + (i + 1) + " result");
			lines.add("---------------------------------------------------------");
			lines.add("Seed: " + (i + 1));
			lines.add("Runtime (in milliseconds): " + runtimes.get(i).toMillis());
			lines.add("Distance/Fitness: " + solutions.get(i).getFitness());
			lines.add("Solution/Tour: " + solutions.get(i).getSoln().toString());
			lines.add("=========================================================");
			lines.add("");
		}

		lines.add("=========================================================");
		lines.add("Summary over " + solutions.size() + " runs");
		lines.add("---------------------------------------------------------");
		lines.add("Avg. Runtime (in milliseconds): " + runtimes.stream().mapToDouble(Duration::toMillis).average().orElseThrow());
		lines.add("Avg. Distance/Fitness: " + solutions.stream().mapToDouble(EvohypSolution::getFitness).average().orElseThrow());
		lines.add("Best Distance/Fitness: " + solutions.stream().mapToDouble(EvohypSolution::getFitness).min().orElseThrow());
		lines.add("=========================================================");

		Files.write(Paths.get(outputFile), lines, StandardCharsets.UTF_8);
	}
}
