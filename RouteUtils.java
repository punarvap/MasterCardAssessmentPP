package com.mastercard.routing.helpers;


import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

@Component
public class RouteUtils {
    /**
     *
     * @param location
     * @return
     *
     * Method to read the File and Return the Content as List
     * Eash line is a element in List
     */
    public List<String> readFile(String location) {

        List<String> fileData = new ArrayList<String>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    location));
            String line = reader.readLine();
            while (line != null) {
                fileData.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {

        }
        return fileData;
    }

    /**
     *
     * @param dualCities
     * @return
     *
     * the List received after Reading the File will be provided the Parameter
     * to get the Set of distinct cities
     */
    public LinkedHashSet<String> getDistinctCities(List<String> dualCities) {

        LinkedHashSet<String> cities = new LinkedHashSet<String>();

        for (String line : dualCities) {

            for (String city : line.split(","))
                cities.add(city);
        }

        return cities;
    }

    /**
     * Allocating Number for Each City
     * @param cities
     * @return
     */
    public LinkedHashMap<String,Integer> allocatingIdentifiers(LinkedHashSet<String> cities){
        LinkedHashMap<String,Integer> citiesWithNumbers = new LinkedHashMap<String,Integer>();
        int count = 0;

        for (String city:cities ) {
            citiesWithNumbers.put(city,count);
            count++;
        }
        return citiesWithNumbers;
    }

    /**
     *
     * @param adj
     * @param s
     * @param dest
     * @param v
     * @return
     *
     * Finding  the Route
     *
     */
    public  boolean  printShortestDistance(
            ArrayList<ArrayList<Integer>> adj,
            int s, int dest, int v) {
        // predecessor[i] array stores predecessor of
        // i and distance array stores distance of i
        // from s
        int pred[] = new int[v];
        int dist[] = new int[v];

        if (BFS(adj, s, dest, v, pred, dist) == false) {
            System.out.println("Given source and destination" +
                    "are not connected");
            return false;
        }

       return true;
    }

    /**
     *
     * @param adj
     * @param i
     * @param j
     *
     * for distinguishing the routes
     *
     */
    public  void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j) {
        adj.get(i).add(j);
        adj.get(j).add(i);
    }


    /**
     *
     * @param adj
     * @param src
     * @param dest
     * @param v
     * @param pred
     * @param dist
     * @return
     *
     * Search Algorithm
     */
    private  boolean BFS(ArrayList<ArrayList<Integer>> adj, int src,
                               int dest, int v, int pred[], int dist[])
    {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean visited[] = new boolean[v];
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        visited[src] = true;
        dist[src] = 0;
        queue.add(src);

        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));
                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        return false;
    }
}
