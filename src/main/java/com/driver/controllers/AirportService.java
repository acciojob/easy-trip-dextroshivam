package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {

    @Autowired
    AirportRepository airportRepository;

    public void addAirport(Airport airport) {
        airportRepository.addAirport(airport);
    }

    public void addFlight(Flight flight) {
        airportRepository.addFlight(flight);
    }

    public void addPassenger(Passenger passenger) {
        airportRepository.addPassenger(passenger);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
            return airportRepository.bookATicket(flightId,passengerId);
    }

    public String getLargestAirportName() {

        return airportRepository.getLargestAirportName();
    }

    public int calculateFlightFare(Integer flightId) {
        return airportRepository.calculateFlightFare(flightId);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        return airportRepository.getAirportNameFromFlightId(flightId);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {

        return airportRepository.getNumberOfPeopleOn(date,airportName);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {

        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {

        return airportRepository.cancelATicket(flightId,passengerId);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {

        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }
}
