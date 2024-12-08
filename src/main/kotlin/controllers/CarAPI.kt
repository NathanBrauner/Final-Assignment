package controllers

import models.Car
import persistence.Serializer

/**
 * A controller class for managing car-related operations.
 *
 * This class provides functionality to manage a collection of cars and handles
 * serialization and deserialization using a specified [Serializer].
 *
 * @property serializer The serializer used to save and load car data.
 * @constructor Creates a [CarAPI] instance with a specified [serializerType].
 * @param serializerType The serializer instance to be used for data persistence.
 */


class CarAPI(serializerType: Serializer) {

    // The serializer used for persisting car data.
    private var serializer: Serializer = serializerType
    // A list of cars managed by this API.
    private var cars = ArrayList<Car>()




    /**
     * Adds a new car to the collection.
     *
     * This function takes a [Car] object and adds it to the list of managed cars.
     *
     * @param car The [Car] object to be added to the collection.
     * @return `true` if the car was successfully added, `false` otherwise.
     */
    fun add(car: Car): Boolean {

            return cars.add(car)
        }

    /**
     * Lists all cars in the collection as a formatted string.
     *
     * This function returns a string representation of all the cars in the collection.
     * Each car is displayed with its index and details. If the collection is empty,
     * it returns a message indicating that no cars are stored.
     *
     * @return A string representation of all cars or a message if no cars are stored.
     */
        fun listAllCars(): String =
            if (cars.isEmpty()) "No Cars Stored"
            else cars.joinToString (separator = "\n") { car ->
                cars.indexOf(car).toString() + ": " + car.toString()
            }

    /**
     * Counts the total number of cars available in the collection.
     *
     * This function iterates through the cars and counts those marked as available.
     *
     * @return The total count of cars that are available.
     */
    fun numberOfCars(): Int = cars.count { car: Car -> car.isCarAvailable}

    /**
     * Counts the total number of cars that have been sold.
     *
     * This function iterates through the cars and counts those that are not available.
     *
     * @return The total count of cars that have been sold.
     */
       fun listSoldCars(): Int = cars.count { car: Car -> car.isCarAvailable}

    /**
     * Counts the total number of cars currently available for sale.
     *
     * This function iterates through the cars and counts those marked as available.
     *
     * @return The total count of cars available for sale.
     */
        fun listAvailableCars(): Int = cars.count { car: Car -> car.isCarAvailable}

    /**
     * Finds a car in the collection by its index.
     *
     * This function retrieves a car from the collection if the provided index is valid.
     * If the index is out of bounds, it returns `null`.
     *
     * @param index The index of the car to be retrieved.
     * @return The [Car] object at the specified index, or `null` if the index is invalid.
     */
        fun findCar(index: Int): Car? {
            return if (isValidListIndex(index, cars)) {
                cars[index]
            } else null
        }

    /**
     * Checks if the given index is valid for a specified list.
     *
     * This function ensures the index is within the bounds of the list (greater than or equal to 0
     * and less than the size of the list).
     *
     * @param index The index to check.
     * @param list The list against which the index is validated.
     * @return `true` if the index is valid, `false` otherwise.
     */
        fun isValidListIndex(index: Int, list: List<Any>): Boolean {
            return (index >= 0 && index < list.size)
        }

    /**
     * Checks if the given index is valid for the cars collection.
     *
     * This function uses [isValidListIndex] to validate the index specifically for the `cars` list.
     *
     * @param index The index to check.
     * @return `true` if the index is valid for the cars collection, `false` otherwise.
     */
    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, cars);
    }

    /**
     * Deletes a car from the collection by its index.
     *
     * This function removes a car from the collection if the provided index is valid.
     * If the index is invalid, it returns `null`.
     *
     * @param indexToDelete The index of the car to be deleted.
     * @return The [Car] object that was removed, or `null` if the index was invalid.
     */
        fun deleteCar(indexToDelete: Int): Car? {
            return if (isValidListIndex(indexToDelete, cars)) {
                cars.removeAt(indexToDelete)
            } else null
        }

    fun searchByMake(searchMake: String) {

    }

    /**
     * Updates the details of a car in the collection at the specified index.
     *
     * This function replaces the details of an existing car at the given index with the
     * details of the provided [car] object. If the index is valid and the new car is not null,
     * the update is applied.
     *
     * @param indexToUpdate The index of the car to be updated.
     * @param car The new [Car] object containing updated values for the car's details.
     * @return `true` if the car was successfully updated, `false` otherwise.
     */
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

    /**
     * Loads the list of cars from the persistent storage.
     *
     * This function retrieves the saved list of cars from the serializer and
     * assigns it to the `cars` collection. It may throw an exception if
     * the loading process encounters an error.
     *
     * @throws Exception If an error occurs during the loading process.
     */
                @Throws(Exception::class)
                fun load() {
                    cars = serializer.read() as java.util.ArrayList<Car>
                }

    /**
     * Saves the current list of cars to persistent storage.
     *
     * This function stores the current state of the `cars` collection using
     * the specified [serializer]. It may throw an exception if saving
     * the data encounters an error.
     *
     * @throws Exception If an error occurs during the saving process.
     */
                @Throws(Exception::class)
                fun store() {
                    serializer.write(cars)
                }


    /**
     * Formats the list of cars into a string representation.
     *
     * This function takes a list of cars and returns a formatted string,
     * with each car's index and details shown on a new line.
     *
     * @param carsToFormat The list of [Car] objects to be formatted.
     * @return A formatted string representing the list of cars.
     */
                private fun formatListString(carsToFormat : List<Car>) : String =
                    carsToFormat
                        .joinToString (separator = "\n") { car ->
                            cars.indexOf(car).toString() + ": " + car.toString() }

            }




