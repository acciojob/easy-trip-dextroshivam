package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {

    HashMap<String,Airport> airportMap=new HashMap<>();
    HashMap<Integer,Flight> flightMap=new HashMap<>();
    HashMap<Integer,Passenger> passengerMap=new HashMap<>();

    HashMap<Integer, List<Integer>> flightToPassenger=new HashMap<>();

    HashMap<Integer,List<Integer>> passengerToFlight=new HashMap<>();



    public void addAirport(Airport airport) {
        airportMap.put(airport.getAirportName(),airport);
    }

    public void addFlight(Flight flight) {
        flightToPassenger.put(flight.getFlightId(),new ArrayList<>());
        flightMap.put(flight.getFlightId(),flight);
    }

    public void addPassenger(Passenger passenger) {
        passengerToFlight.put(passenger.getPassengerId(),new ArrayList<>());
        passengerMap.put(passenger.getPassengerId(),passenger);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"

        int maxCapacity=flightMap.get(flightId).getMaxCapacity();
        int curSize=flightToPassenger.get(flightId).size();

        if(curSize<=maxCapacity-1){
            flightToPassenger.get(flightId).add(passengerId);
            passengerToFlight.get(passengerId).add(flightId);
            return "SUCCESS";
        }else {
            return "FAILURE";
        }
    }

    public String getLargestAirportName() {

        //Largest airport is in terms of terminals. 3 terminal airport is larger than 2 terminal airport
        //Incase of a tie return the Lexicographically smallest airportName

        HashMap<Integer,String> terminalMap=new HashMap<>();
        terminalMap.put(0,"");
        for(String name:airportMap.keySet()){
            Airport curAirport=airportMap.get(name);
            int lastVal=(Integer) terminalMap.keySet().toArray()[0];
            if(curAirport.getNoOfTerminals()>lastVal){
                terminalMap.clear();
                terminalMap.put(curAirport.getNoOfTerminals(),name);
            }else if(curAirport.getNoOfTerminals()==lastVal){
                terminalMap.put(curAirport.getNoOfTerminals(),name);
            }
        }
        List<String> airportName=new ArrayList<>();
        for(int id:terminalMap.keySet()){
            airportName.add(terminalMap.get(id));
        }
        Collections.sort(airportName);
        return airportName.get(0);

    }

    public int calculateFlightFare(Integer flightId) {
        int currentNoPassenger=flightToPassenger.get(flightId).size();
        return 3000+currentNoPassenger*50;
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        //We need to get the starting airportName from where the flight will be taking off (Hint think of City variable if that can be of some use)
        //return null incase the flightId is invalid or you are not able to find the airportName
        if(!flightMap.containsKey(flightId)) return  null;
        else{
            return flightMap.get(flightId).getFromCity().name();
        }

    }

    public int getNumberOfPeopleOn(Date date, String airportName) {

        //Calculate the total number of people who have flights on that day on a particular airport
        //This includes both the people who have come for a flight and who have landed on an airport after their flight
        int count=0;
        for(Integer id:flightMap.keySet()){
            Flight flight=flightMap.get(id);
            if(flight.getFlightDate()==date && (flight.getToCity().name()==airportName || flight.getFromCity().name()==airportName)){
                count++;
            }
        }
        return count;
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return passengerToFlight.get(passengerId).size();
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        int revenue=0;
        int noOfPassenger=flightToPassenger.get(flightId).size();
        for(int i=0;i<noOfPassenger;i++){
            revenue+=3000+i*50;
        }
        return revenue;
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {


        //If the passenger has not booked a ticket for that flight or the flightId is invalid or in any other failure case
        // then return a "FAILURE" message
        // Otherwise return a "SUCCESS" message
        // and also cancel the ticket that passenger had booked earlier on the given flightId

        if(!flightMap.containsKey(flightId)) return "FAILURE";
        else if(!passengerMap.containsKey(passengerId)) return "FAILURE";
        else {
            if(!flightToPassenger.containsKey(flightId)) return "FAILURE";
            else if(!passengerToFlight.containsKey(passengerId)) return "FAILURE";
            else {
                flightToPassenger.get(flightId).clear();
                passengerToFlight.get(passengerId).remove(flightId);
                return "SUCCESS";
            }
        }
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double minDuration=Integer.MAX_VALUE;
        for(Integer id:flightMap.keySet()){
            Flight flight=flightMap.get(id);
            if(flight.getDuration()<minDuration){
                minDuration=flight.getDuration();
            }
        }
        return minDuration;
    }
}











