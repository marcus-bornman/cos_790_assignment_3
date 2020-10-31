package com.marcusbornman.cos_790_assignment_3.utils;

import org.moeaframework.problem.tsplib.TSPInstance;

/**
 * A utility class to calculate the values for attributes of a node in a particular TSP.
 */
public class NodeAttributes {
	public static final String supportedAttributes = "ACF";

	private final TSPInstance tspInstance;

	private final int nodeId;

	private final Double avgDistanceToOtherNodes;

	private final Double distanceToClosestNode;

	private final Double distanceToFurthestNode;


	/**
	 * @param tspInstance the TSP instance of which the node is a part.
	 * @param nodeId      the node for which to calculate attribute values.
	 */
	public NodeAttributes(TSPInstance tspInstance, int nodeId) {
		this.tspInstance = tspInstance;
		this.nodeId = nodeId;
		this.avgDistanceToOtherNodes = calcAvgDistanceToOtherNodes();
		this.distanceToClosestNode = calcDistanceToClosestNode();
		this.distanceToFurthestNode = calcDistanceToFurthestNode();
	}

	public double[] getAttributes(String attributes) {
		double[] attributeValues = new double[attributes.length()];
		for (int j = 0; j < attributes.length(); j++) {
			attributeValues[j] = getAttribute(attributes.charAt(j));
		}

		return attributeValues;
	}

	private double getAttribute(char attribute) {
		switch (attribute) {
			case 'A':
				return avgDistanceToOtherNodes;
			case 'C':
				return distanceToClosestNode;
			case 'F':
				return distanceToFurthestNode;
			default:
				throw new IllegalArgumentException(attribute + " is not a supported attribute. Supported Attributes are " + supportedAttributes + ".");
		}
	}

	private double calcAvgDistanceToOtherNodes() {
		double totalDistance = 0;
		int[] neighborIds = tspInstance.getDistanceTable().getNeighborsOf(nodeId);
		for (int neighborId : neighborIds) {
			totalDistance += tspInstance.getDistanceTable().getDistanceBetween(nodeId, neighborId);
		}

		return totalDistance / neighborIds.length;
	}

	private double calcDistanceToClosestNode() {
		double distanceToClosestNode = Double.MAX_VALUE;
		int[] neighborIds = tspInstance.getDistanceTable().getNeighborsOf(nodeId);
		for (int neighborId : neighborIds) {
			double distance = tspInstance.getDistanceTable().getDistanceBetween(nodeId, neighborId);
			if (distance < distanceToClosestNode) distanceToClosestNode = distance;
		}

		return distanceToClosestNode;
	}

	private double calcDistanceToFurthestNode() {
		double distanceToFurthestNode = Double.MIN_VALUE;
		int[] neighborIds = tspInstance.getDistanceTable().getNeighborsOf(nodeId);
		for (int neighborId : neighborIds) {
			double distance = tspInstance.getDistanceTable().getDistanceBetween(nodeId, neighborId);
			if (distance > distanceToFurthestNode) distanceToFurthestNode = distance;
		}

		return distanceToFurthestNode;
	}
}
