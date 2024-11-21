package ie.setu

import ie.setu.utils.readIntNotNull
import ie.setu.utils.readNextInt
import java.lang.System.exit

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
    println("You chose Add Car")
}

fun listCars(){
    println("You chose List Cars")
}

fun updateCar(){
    println("You chose Update Car")
}

fun deleteCar(){
    println("You chose Delete Car")
}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}

fun main() {
    runMenu()
}