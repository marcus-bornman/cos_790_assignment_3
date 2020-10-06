package com.marcusbornman.cos_790_assignment_2;

import com.marcusbornman.cos_790_assignment_2.search.HeuristicSearch;
import com.marcusbornman.cos_790_assignment_2.search.multipoint.MultiPointSearch;
import com.marcusbornman.cos_790_assignment_2.search.singlepoint.SinglePointSearch;
import com.marcusbornman.cos_790_assignment_2.tools.InitialSolutionBuilder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import uk.ac.qub.cs.itc2007.ExamTimetablingProblem;
import uk.ac.qub.cs.itc2007.ExamTimetablingSolution;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
	public static void main(String[] args) throws ParseException, IOException {
		// Set up command line options
		Options options = new Options();
		options.addRequiredOption("s", "searchType", true, "The search type for the hyper-heuristic. Possible values are 'singlepoint' or 'multipoint'.");
		options.addRequiredOption("p", "problemFile", true, "The problem instance file for the examination timetabling problem.");
		options.addRequiredOption("r", "randomSeed", true, "The seed used to generate random values.");
		options.addRequiredOption("o", "outputFile", true, "The folder to write the results of the experiment to.");
		options.addOption("g", "geneticParamFile", true, "The genetic parameters file required if 'search' is 'multipoint'");
		CommandLine cl = new DefaultParser().parse(options, args);

		// Set up problem and initial solution
		System.out.println("Reading problem from " + cl.getOptionValue("p"));
		ExamTimetablingProblem problem = ExamTimetablingProblem.fromFile(cl.getOptionValue("p"));
		System.out.println("Building Initial Solution");
		InitialSolutionBuilder initialSolutionBuilder = new InitialSolutionBuilder(problem, Long.parseLong(cl.getOptionValue("r")));
		ExamTimetablingSolution solution = initialSolutionBuilder.generateInitialSolution();

		// Improve the solution by applying selective perturbative hyper-heuristics
		HeuristicSearch search;
		if (cl.getOptionValue("s").equals("singlepoint")) {
			search = new SinglePointSearch(problem, solution, Long.parseLong(cl.getOptionValue("r")));
		} else if (cl.getOptionValue("s").equals("multipoint")) {
			search = new MultiPointSearch(problem, solution, Long.parseLong(cl.getOptionValue("r")), cl.getOptionValue("g"));
		} else {
			throw new IllegalArgumentException(cl.getOptionValue("s") + " is not a valid search type. The available search types are 'singlepoint' and 'multipoint'.");
		}

		System.out.println("Performing search - " + search.toString());
		runExperiment(search, cl.getOptionValue("o"));
		System.out.println("Search complete. See " + cl.getOptionValue("o") + " for experiment results.");
	}

	private static void runExperiment(HeuristicSearch search, String outputFile) throws IOException {
		LocalDateTime before = LocalDateTime.now();
		ExamTimetablingSolution solution = search.execute();
		LocalDateTime after = LocalDateTime.now();
		Duration runtime = Duration.between(before, after);

		List<String> lines = Arrays.asList(
				"=========================================================",
				"Experimental Result",
				"=========================================================",
				"Search: " + search.toString(),
				"Runtime (in milliseconds): " + runtime.toMillis(),
				"Distance to Feasibility: " + solution.distanceToFeasibility(),
				"Soft Constraint Violations: " + solution.softConstraintViolations(),
				"Objective Value: " + (solution.softConstraintViolations() * (solution.distanceToFeasibility() + 1)),
				"",
				"=========================================================",
				"Solution",
				"=========================================================",
				solution.toString()
		);

		//noinspection ResultOfMethodCallIgnored
		new File(outputFile).getParentFile().mkdirs();// to create file if it doesn't exist
		Files.write(Paths.get(outputFile), lines, StandardCharsets.UTF_8);
	}
}
