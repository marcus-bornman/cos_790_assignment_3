package timetabling;

import genalg.GenAlg;
import initialsoln.InitialSoln;
import timetabling.evohyp.HeuristicApplier;
import timetabling.utils.ProblemReader;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		String filePath = "problems/exam_comp_set1.exam";

		try {
			long seed = System.currentTimeMillis();
			String heuristics = HeuristicApplier.SUPPORTED_HEURISTICS;
			GenAlg genAlg = new GenAlg(seed, heuristics);
			genAlg.setPopulationSize(100);
			genAlg.setTournamentSize(10);
			genAlg.setNoOfGenerations(100);
			genAlg.setMutationRate(0.1);
			genAlg.setCrossoverRate(0.1);
			genAlg.setInitialMaxLength(10);
			genAlg.setOffspringMaxLength(50);
			genAlg.setMutationLength(5);
			genAlg.setProblem(ProblemReader.problemFromFile(filePath));

			InitialSoln solution = genAlg.evolve();

			System.out.println("Best Solution");
			System.out.println("--------------");
			System.out.println("Fitness: " + solution.getFitness());
			System.out.println("Heuristic combination: " + solution.getHeuCom());
			System.out.println("Solution: \n" + solution);
		} catch (IOException e) {
			System.out.println("File at path `" + filePath + "` could not be found.");
		}
	}
}
