package com.results;

import com.Runner;
import com.classes.Vehicle;
import org.apache.commons.math3.stat.StatUtils;

import java.util.*;

public class ResultsCollection {

    private HashMap<String, Double> results;

    public ResultsCollection() {
        results = new HashMap<>();
        results.put("#EVs", (double) Runner.NUMBER_EVS); // add the other params of Runner
        results.put("station_capacity", (double) Runner.STATION_CAPACITY);
        results.put("minutes_step", (double) Runner.MINUTES_PER_STEP);
        results.put("#iterations", (double) Runner.NUMBER_ITERATIONS);
    }

    public void addWaitingTimeResults(String algoName, List<Vehicle> vehicles) {
        double[] waitingTimes = new double[vehicles.size()];
        for(int i = 0; i < vehicles.size(); i++) {
            waitingTimes[i] = vehicles.get(i).getWaitingTime();
        }

        double avgWaitingTime = StatUtils.mean(waitingTimes);

        avgWaitingTime *= Runner.MINUTES_PER_STEP / Runner.NUMBER_ITERATIONS;

        String key = algoName + "_waiting_time";

        results.put(key, (results.getOrDefault(key, 0.0) + avgWaitingTime));
    }

    public void addTardinessResults(String algoName, List<Vehicle> vehicles) {
        double[] delays = new double[vehicles.size()];
        for(int i = 0; i < vehicles.size(); i++) {
            delays[i] = vehicles.get(i).getTardiness();
        }

        double maxTardiness = StatUtils.max(delays);
        double avgTardiness = StatUtils.mean(delays);

        String key = algoName + "_max_tardiness";

        maxTardiness *= Runner.MINUTES_PER_STEP;

        results.put(key, Math.max(maxTardiness, results.getOrDefault(key, 0.0)));

        key = algoName + "_avg_tardiness";

        avgTardiness *= Runner.MINUTES_PER_STEP / Runner.NUMBER_ITERATIONS;

        results.put(key, results.getOrDefault(key, 0.0) + avgTardiness);
    }

    @Override
    public String toString() {
        return toString("\t");
    }

    public String toString(String divider) {

        StringBuffer sb = new StringBuffer();

        for(String key : results.keySet()) {
            sb.append(key);
            sb.append(divider);
        }

        sb.append("\n");

        for(String key : results.keySet()) {
            sb.append(results.get(key));
            sb.append(divider);
        }

        sb.append("\n");
        return sb.toString();

//        Set<String> descriptionHeadings = new HashSet<String>();
////        Set<String> statsHeadings = new FastSet<String>();
//
//        descriptionHeadings.addAll(results.keySet());
//        for (Entry<StatisticsDescription, Map<String, Statistics>> entry : stats
//                .entrySet()) {
//            descriptionHeadings.addAll(entry.getKey().getKeys());
//            statsHeadings.addAll(entry.getValue().keySet());
//        }
//
//        String[] annotationHeads = new String[descriptionHeadings.size()];
//        int pointer = 0;
//        for (String heading : descriptionHeadings) {
//            annotationHeads[pointer++] = heading;
//        }
//        pointer = 0;
//        String[] statsHeads = new String[statsHeadings.size() * 2];
//        for (String heading : statsHeadings) {
//            statsHeads[pointer++] = heading;
//            statsHeads[pointer++] = heading;
//        }
//        Arrays.sort(annotationHeads);
//        Arrays.sort(statsHeads);
//        for (int i = 1; i < statsHeads.length; i = i + 2) {
//            statsHeads[i] = statsHeads[i - 1] + " St.Error";
//        }
//
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < annotationHeads.length; i++) {
//            sb.append(annotationHeads[i]);
//            sb.append(divider);
//        }
//        for (int i = 0; i < statsHeads.length; i++) {
//            sb.append(statsHeads[i]);
//            if (i < statsHeads.length - 1) {
//                sb.append(divider);
//            } else {
//                sb.append("\n");
//            }
//        }
//
//        for (Entry<StatisticsDescription, Map<String, Statistics>> entry : stats
//                .entrySet()) {
//
//            if (entry.getValue().size() == 0) {
//                continue;
//            }
//
//            StatisticsDescription description = new StatisticsDescription();
//            description.add(overallContext);
//            description.add(entry.getKey());
//
//            for (String h : annotationHeads) {
//                String value = description.getValue(h);
//                if (value == null) {
//                    sb.append(StatisticsDescription.skipString);
//                } else {
//                    sb.append(value);
//                }
//                sb.append(divider);
//            }
//
//            for (int i = 0; i < statsHeads.length; i = i + 2) {
//                String h = statsHeads[i];
//                Statistics stats = entry.getValue().get(h);
//                if (stats == null) {
//                    sb.append(StatisticsDescription.skipString);
//                    sb.append(divider);
//                    sb.append(StatisticsDescription.skipString);
//                    sb.append(divider);
//                } else {
//                    sb.append(stats.getMean());
//					/*if (Double.isNaN(stats.getMean())) {
//						System.out.println("NAN");
//					}
//*/
//                    sb.append(divider);
//                    if (stats.getSamples() > 1) {
//                        sb.append(stats.getStandardError());
//						/*if (Double.isNaN(stats.getStandardError())) {
//							System.out.println("NAN");
//						}
//*/
//                        sb.append(divider);
//                    } else {
//                        sb.append(StatisticsDescription.skipString);
//                        sb.append(divider);
//                    }
//                }
//            }
//            sb.append('\n');
//
//        }
//        return sb.toString();
    }
}
