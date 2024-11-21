package ie.setu

import ie.setu.utils.readIntNotNull
import ie.setu.utils.readNextInt
import io.github.oshai.kotlinlogging.KotlinLogging
import java.lang.System.exit

private val logger = KotlinLogging.logger {}

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
   logger.info { "addCar() function invoked" }
}

fun listCars(){
   logger.info { "listCars() function invoked" }
}

fun updateCar(){
   logger.info { "updateCar() function invoked"}
}

fun deleteCar(){
    logger.info { "deleteCar() function invoked" }
}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}

fun main() {
    runMenu()
}