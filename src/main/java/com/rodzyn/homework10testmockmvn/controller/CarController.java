package com.rodzyn.homework10testmockmvn.controller;

import com.rodzyn.homework10testmockmvn.model.Car;
import com.rodzyn.homework10testmockmvn.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController{

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping
    public ResponseEntity<Car> getCars(){
        return new ResponseEntity(carService.getCars(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") int id){
        Optional<Car> first = carService.getCars().stream().filter(car -> car.getId() == id).findFirst();
        if(first.isPresent()){
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/color")
    public ResponseEntity<Car> getCarByColor(@RequestParam String color){
        List<Car> colorCar = carService.getCars().stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());
        if(colorCar.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(colorCar, HttpStatus.OK);
    }

    @GetMapping("/mark/{mark}")
    public ResponseEntity<Car> getCarByMark(@PathVariable String mark){
        List<Car> colorCar = carService.getCars().stream().filter(car -> car.getMark().equals(mark)).collect(Collectors.toList());
        if(colorCar.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(colorCar, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car){
        boolean add = carService.getCars().add(car);
        if(add){
            return new ResponseEntity(add, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> modCar(@PathVariable("id") int id, @RequestBody Car newCar){
        Optional<Car> first = carService.getCars().stream().filter(car -> car.getId() == 2).findFirst();
        if(first.isPresent()){
            carService.removeCar(first.get());
            carService.addCar(newCar);
            return new ResponseEntity<>(newCar, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> removeCar(@PathVariable long id){
        Optional<Car> first = carService.getCars().stream().filter(car -> car.getId() == id).findFirst();
        if(first.isPresent()){
            carService.removeCar(first.get());
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}