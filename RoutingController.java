package com.mastercard.routing.controller;


import com.mastercard.routing.helpers.RouteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@RestController
public class RoutingController {


    @Autowired
    RouteUtils utils;

    @Value("${location}")
    private String fileLocation;

    /**
     *
     * @param src
     * @param dst
     * @return YES/NO
     *
     *Endpoint with Get method to find the Route between the Cities
     */
    @GetMapping(value = "/connected")
    public String isConnected(@RequestParam("origin") String src, @RequestParam("destination") String dst) {
        String route = "NO";
        //Loading the data from file
        List<String> fileData = utils.readFile(fileLocation);
        //Getting the Unique Values
        LinkedHashSet<String> distinctCities = utils.getDistinctCities(fileData);

        //Proving the Identifier to Each City a Number
        LinkedHashMap<String, Integer> numberedCities = utils.allocatingIdentifiers(distinctCities);

        //Number of cities
        int v = distinctCities.size();

        //Storting per city based Connections
        ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(v);

        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<Integer>());
        }


        //Generating a Graph with the Connected Cities
        for (String line1 : fileData) {
            String data[] = line1.split(",");
            utils.addEdge(adj, numberedCities.get(data[0]), numberedCities.get(data[1]));
        }
        int srcNumber = 0;
        int dstNumber = 0;
        boolean check = true;
        //getting the Given Number for the Provided Source
        if(numberedCities.containsKey(src)) {
            srcNumber  = numberedCities.get(src);
        }else{
            check = false;
        }

        if(numberedCities.containsKey(dst)) {
            dstNumber = numberedCities.get(dst);
        }else{
            check = false;
        }



        //Checking the Route for Given source and Destination
        if(check) {
           check  = utils.printShortestDistance(adj, srcNumber, dstNumber, v);
        }
        if (check) {
            route = "YES";
        }

        return route;
    }

}
