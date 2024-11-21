package controllers

import models.Car
import kotlin.text.get

class CarAPI {
    private var cars = ArrayList<Car>()
}

fun add(car: Car): Boolean {
    return cars.add(car)
}

fun listAllCars(): String {
    return if (cars.isEmpty()) {
        "No Cars Stored"
    } else {
        var listOfCars = ""
        for(i in cars.indices) {
            listOfCars += "${i}: ${cars[i]} \n"
        }
        listOfCars
    }
}