

import controllers.CarAPI
import ie.setu.utils.readNextInt
import ie.setu.utils.readNextLine
import io.github.oshai.kotlinlogging.KotlinLogging
import models.Car
import kotlin.system.exitProcess


private val logger = KotlinLogging.logger {}
private val carAPI = CarAPI()

fun main() {
    runMenu()
}

fun mainMenu(): Int {
    print(""" 
         > ----------------------------------
         > |        CAR SERVICE APP         |
         > ----------------------------------
         > | CAR MENU                       |
         > |   1) Add a Car                 |
         > |   2) List all Cars             |
         > |   3) Update a Car              |
         > |   4) Delete a Car              |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
    return readNextInt(" > ==>>")
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addCar()
            2  -> listCars()
            3  -> updateCar()
            4  -> deleteCar()
            0  -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun addCar(){
    val carMake = readNextLine("Enter the make for the car: ")
    val carEngine = readNextInt("Enter the size of the engine:  ")
    val carPrice = readNextLine("Enter a price for the car: ")
    val isAdded = carAPI.add(Car(carMake, carEngine, carPrice, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listCars(){
   println(carAPI.listAllCars())
}

fun updateCar(){
   logger.info { "updateCar() function invoked"}
}

fun deleteCar(){
    logger.info { "deleteCar() function invoked" }
}

fun exitApp(){
    println("Exiting...bye")
    exitProcess(0)
}

