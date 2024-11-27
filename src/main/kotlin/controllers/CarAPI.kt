package controllers

import models.Car
import kotlin.text.get

class CarAPI {
    private var cars = ArrayList<Car>()


fun add(car: Car): Boolean {

    return cars.add(car)
}

fun listAllCars(): String {
    return if (cars.isEmpty) {
        "No Cars Stored"
    } else {
        var listOfCars = ""
        for (i in cars.indices) {
            listOfCars += "${i}: ${cars[i]} \n"
        }
        listOfCars
    }
}

    fun numberOfCars(): Int{
        return cars.size
    }

    fun findCar(index: Int): Car? {
        return if (isValidListIndex(index, cars)) {
            cars[index]
        } else null
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }


fun deleteNote(indexToDelete: Int): Car? {
    return if (isValidListIndex(indexToDelete, cars)) {
        cars.removeAt(indexToDelete)
    } else null
}

fun updateCar(indexToUpdate: Int, car: Car?): Boolean {
    val foundCar = findCar(indexToUpdate)

    if ((foundCar != null) && (car != null)) {
        foundCar.carMake = car.carMake
        foundCar.carEngine = car.carEngine
        foundCar.carPrice = car.carPrice
        return true
    }

    return false
}



