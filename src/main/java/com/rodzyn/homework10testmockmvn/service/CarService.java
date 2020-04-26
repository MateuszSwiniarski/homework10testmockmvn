package com.rodzyn.homework10testmockmvn.service;

import com.rodzyn.homework10testmockmvn.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> getCars();
    Optional<Car> getCarById(Long id);
    List<Car> getCarByColor(String color);
    List<Car> getCarByMark(String mark);
    boolean addCar(Car car);
    Optional<Car> modCar(Car car);
    boolean removeCar(Car car);
}
