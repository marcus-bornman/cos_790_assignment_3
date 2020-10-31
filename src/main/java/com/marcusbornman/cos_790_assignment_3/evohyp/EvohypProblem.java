package com.marcusbornman.cos_790_assignment_3.evohyp;

import com.marcusbornman.cos_790_assignment_3.utils.NodeAttributes;
import distrgenprog.Evaluator;
import distrgenprog.Node;
import org.apache.commons.lang3.StringUtils;
import org.moeaframework.problem.tsplib.TSPInstance;
import org.moeaframework.problem.tsplib.Tour;
import problem.Problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvohypProblem extends Problem {
	private String attributes;

	public final TSPInstance tspInstance;

	/**
	 * @param tspInstance - the symmetric or asymmetric travelling salesman problem instance.
	 */
	public EvohypProblem(TSPInstance tspInstance) {
		this.attributes = "";
		this.tspInstance = tspInstance;
	}

	@Override
	public EvohypSolution evaluate(Object heuristic) {
		if (StringUtils.isEmpty(attributes)) throw new IllegalArgumentException("Attributes cannot be null or empty.");

		// Calculate the priorities for each node in tspInstance
		Map<Integer, Double> nodeToPriorityMap = new HashMap<>();
		for (int i = 0; i < tspInstance.getDistanceTable().listNodes().length; i++) {
			int nodeId = tspInstance.getDistanceTable().listNodes()[i];
			NodeAttributes nodeAttributes = new NodeAttributes(tspInstance, nodeId);
			Evaluator evaluator = new Evaluator(attributes, nodeAttributes.getAttributes(attributes));
			nodeToPriorityMap.put(nodeId, evaluator.eval((Node) heuristic));
		}

		// Build a sorted list of the nodes according to priorities calculated above and instantiate a tour from the list
		List<Integer> tourList = new ArrayList<>();
		nodeToPriorityMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(e -> tourList.add(e.getKey()));
		Tour tour = new Tour();
		tour.fromArray(tourList.stream().mapToInt(i -> i).toArray());

		return new EvohypSolution(tspInstance, tour);
	}

	@Override
	public void setAttribs(String attributes) {
		this.attributes = attributes;
	}
}
