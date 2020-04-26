package com.rodzyn.homework10testmockmvn.service;

import com.rodzyn.homework10testmockmvn.model.Car;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CarServiceImpTest {

    @MockBean
    private CarService carService;

    @Before
    private void init(){
        given(carService.getCars()).willReturn(prepareMockData());
    }

    public List<Car> prepareMockData(){
        List<Car> carList = new ArrayList<>();
        carList.add(new Car(1L, "audi", "A5", "black"));
        carList.add(new Car(2L, "porsche", "911", "red"));
        carList.add(new Car(3L, "bmw", "i8", "red"));
        carList.add(new Car(4L, "Kia", "Optima", "blue"));
        carList.add(new Car(5L, "Fiat", "Tipo", "white"));
        return carList;
    }

    @Test
    void should_get_cars(){
        //given
        List<Car> carList = prepareMockData();
        given(carService.getCars()).willReturn(carList);
        //when
        List<Car> cars = carService.getCars();
        //then
        assertThat(cars, Matchers.hasSize(5));
    }

//    @Test
//    void getCars() {
//        //give
//        CarService carService = mock(CarService.class);
//        given(carService.getCars()).willReturn(prepareMockData());
//        //when
//        List<Car> cars = carService.getCars();
//        //then
//        assertThat(cars, Matchers.hasSize(5));
//    }

    @Test
    void should_get_car_by_id(){
        //give
        given(carService.getCarById(1l)).willReturn(java.util.Optional.of(new Car(1L, "audi", "A5", "black")));
        //when
        long carId = carService.getCarById(1l).get().getId();
        //then
        assertEquals(1l, carId);
        assertNotEquals(3l, carId);
    }

//    @Test
//    void getCarById() {
//        //give
//        CarService carService = mock(CarService.class);
//        given(carService.getCarById(1l)).willReturn(java.util.Optional.of(new Car(1L, "audi", "A5", "black")));
//        //when
//        long carId = carService.getCarById(1l).get().getId();
//        //then
//        assertEquals(1l, carId);
//        assertNotEquals(3l, carId);
//    }

    @Test
    void should_get_car_by_color() {
        //give
        given(carService.getCarById(1l)).willReturn(java.util.Optional.of(new Car(1L, "audi", "A5", "black")));
        //when
        String carColor = carService.getCarById(1l).get().getColor();
        //then
        assertEquals("black", carColor);
        assertNotEquals("white", carColor);
    }

//    @Test
//    void getCarByColor() {
//        //give
//        CarService carService = mock(CarService.class);
//        given(carService.getCarById(1l)).willReturn(java.util.Optional.of(new Car(1L, "audi", "A5", "black")));
//        //when
//        String carColor = carService.getCarById(1l).get().getColor();
//        //then
//        assertEquals("black", carColor);
//        assertNotEquals("white", carColor);
//    }

    @Test
    void should_get_car_by_mark() {
        //give
        given(carService.getCarById(1l)).willReturn(java.util.Optional.of(new Car(1L, "audi", "A5", "black")));
        //when
        String carMark = carService.getCarById(1l).get().getMark();
        //then
        assertEquals("audi", carMark);
        assertNotEquals("bmw", carMark);
    }

//    @Test
//    void getCarByMark() {
//        //give
//        CarService carService = mock(CarService.class);
//        given(carService.getCarById(1l)).willReturn(java.util.Optional.of(new Car(1L, "audi", "A5", "black")));
//        //when
//        String carMark = carService.getCarById(1l).get().getMark();
//        //then
//        assertEquals("audi", carMark);
//        assertNotEquals("bmw", carMark);
//    }

    @Test
    void should_add_car() {
        //give
        given(carService.getCars()).willReturn(prepareMockData());
        List<Car> carList = carService.getCars();
        Car car = new Car(6l, "Nissan", "Sunny", "blac");
        //when
        carList.add(car);
        //then
        assertEquals(6, carList.size());
        assertThat(carList, Matchers.hasSize(6));
        assertEquals("Nissan", carList.get(6-1).getMark());
    }

    @Test
    void should_mod_car() {
        //give
        given(carService.getCars()).willReturn(prepareMockData());
        List<Car> carList = carService.getCars();
        Car newCar = new Car(2L, "Fiat", "126p", "red");
        //when
        Optional<Car> first = carList.stream().filter(car -> car.getId() == newCar.getId()).findFirst();
        if(first.isPresent()) {
            carList.remove(first.get());
            carList.add(newCar);
        }
        //then
        Optional<Car> updateCar = carList.stream().filter(car -> car.getId() == 2).findFirst();
        assertEquals(newCar.getId(), updateCar.get().getId());
        assertEquals(newCar.getMark(), updateCar.get().getMark());
        assertEquals(newCar.getModel(), updateCar.get().getModel());
        assertEquals(newCar.getColor(), updateCar.get().getColor());

    }

    @Test
    void should_remove_car() {
        //give
        given(carService.getCars()).willReturn(prepareMockData());
        List<Car> carList = carService.getCars();
        Car updateCar = new Car(3L, "bmw", "i8", "red");
        //when
        Optional<Car> first = carList.stream().filter(car -> car.getId() == updateCar.getId()).findFirst();
        if(first.isPresent()) {
            carList.remove(first.get());
        }
        //then
        assertEquals(4, carList.size());
        assertThat(carList, Matchers.hasSize(4));
    }
}