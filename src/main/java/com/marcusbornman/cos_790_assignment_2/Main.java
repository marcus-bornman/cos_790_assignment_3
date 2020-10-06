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

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws ParseException, IOException {
		// Set up command line options
		Options options = new Options();
		options.addRequiredOption("s", "searchType", true, "The search type for the hyper-heuristic. Possible values are 'singlepoint' or 'multipoint'.");
		options.addRequiredOption("p", "problemFile", true, "The problem instance file for the examination timetabling problem.");
		options.addRequiredOption("r", "randomSeed", true, "The seed used to generate random values.");
		options.addOption("g", "geneticParamFile", true, "The genetic parameters file required if 'search' is 'multipoint'");
		CommandLine cl = new DefaultParser().parse(options, args);

		// Set up problem and initial solution
		ExamTimetablingProblem problem = ExamTimetablingProblem.fromFile(cl.getOptionValue("p"));
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

		solution = search.execute();
		System.out.println(solution.softConstraintViolations() * (solution.distanceToFeasibility() + 1));
	}
}
