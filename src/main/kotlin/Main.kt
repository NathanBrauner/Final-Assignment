

import controllers.CarAPI
import io.github.oshai.kotlinlogging.KotlinLogging
import models.Car
import persistence.JSONSerializer
import utils.readNextInt
import utils.readNextLine
import java.io.File
import kotlin.system.exitProcess


private val logger = KotlinLogging.logger {}
//private val carAPI = CarAPI(XMLSerializer(File("cars.xml")))
private val carAPI = CarAPI(JSONSerializer(File("cars.json")))

fun main() {
    runMenu()
    listAllCars()
    listSoldCars()
    listAvailableCars()
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
         > |   5) Search a Car              |
         > |   6) Book a Service            |
         > -------------------------------- |
         > |   20) Save Cars                |
         > |   21) Load Cars                |         
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
            5 -> searchCars()
            6 -> bookService()
            20 -> save()
            21 -> load()
            0  -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun addCar(){
    val carMake = readNextLine("Enter the make for the car: ")
    val carModel = readNextLine("Enter the model for the car: ")
    val carEngine = readNextInt("Enter the size of the engine:  ")
    val carPrice = readNextLine("Enter a price for the car: ")
    val dateOfService = readNextLine("Enter the date of service:")
    val carYear = readNextInt("Enter the year of the car: ")
    val isAdded = carAPI.add(Car(carMake, carModel, carEngine, carPrice, dateOfService, carYear.toString(), false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun bookService(){
    val carMake = readNextLine("Enter the make of the car: ")
    val carModel = readNextLine("Enter the model for the car: ")
    val carEngine = readNextInt("Enter the engine size of the car: ")
    val carPrice = readNextLine("Enter the price of the car: ")
    val dateOfService = readNextLine("Enter the date for your service: ")
    val carYear = readNextInt("Enter the year of the car: ")
    val isAdded = carAPI.add(Car(carMake, carModel, carEngine, carPrice, dateOfService, carYear.toString(), true))

    if (isAdded) {
        println("Your service is booked, thank you!!")
    } else {
        println("Your booking has been unsuccessful")
    }
}

fun listCars() {

    if (carAPI.numberOfCars() > 0) {
        val option = readNextInt(
            """
                 > --------------------------------
                  > |   1) View ALL cars           |
                  > |   2) View SOLD cars          |
                  > |   3) View AVAILABLE cars     |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllCars()
            2 -> listSoldCars()
            3 -> listAvailableCars()
            else -> println("Invalid Number Entered: $option")
        }
    } else {
        println("Option Invalid - No Cars Stored")
    }
}

fun listAllCars() = {
    println(carAPI.listAllCars())
}
fun listAvailableCars() {
    println(carAPI.listAvailableCars())
}
fun listSoldCars() {
    println(carAPI.listSoldCars())
}



fun updateCar(){
   //logger.info { "updateCar() function invoked"}
    listCars()
    if (carAPI.numberOfCars() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the car to Update: ")
        if (carAPI.isValidIndex(indexToUpdate)) {
            val carMake = readNextLine("Enter the make for the car: ")
            val carModel = readNextLine("Enter the model for the car: ")
            val carEngine = readNextInt("Enter the size of the engine: ")
            val carPrice = readNextLine("Enter the price of the car: ")
            val dateOfService = readNextLine("Enter the date for your service: ")
            val carYear = readNextInt("Enter the Year of the car: ")

            if (carAPI.updateCar(indexToUpdate, Car(carMake, carModel, carEngine, carPrice, dateOfService,
                    carYear.toString(), false))){
                println("Update was successful")
            } else {
                println("Update has Failed")
            }
        } else {
            println("There are no cars for this index number")
        }
    }
}

fun deleteCar(){
    listCars()
    if(carAPI.numberOfCars() > 0) {
        val indexToDelete = readNextInt("Enter the index of the car to delete: ")
        val carToDelete = carAPI.deleteCar(indexToDelete)
        if (carToDelete != null) {
            println("Delete Successful! Deleted Car: $carToDelete")
        } else {
            println("Delete Unsuccessful")
        }
    }
}

fun searchCars() {
    val searchMake = readNextLine("Enter the make of the car: ")
    val searchResults = carAPI.searchByMake(searchMake)
    if (searchResults.isEmpty()) {
        println("No cars found")
    } else {
        println(searchResults)
    }
}

private fun Unit.isEmpty(): Boolean {
    TODO("Not yet implemented")
}


fun save () {
    try {
        carAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        carAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp(){
    println("Exiting...bye")
    exitProcess(0)
}

