package controllers

import models.Car
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CarAPITest {

    private var serviceCar: Car? = null
    private var testDrive: Car? = null
    private var repairCar: Car? = null
    private var testCar: Car? = null
    private var cleanCar: Car? = null
    private var availableCars: CarAPI? = CarAPI()
    private var soldCars: CarAPI? = CarAPI()

    //The method ran before each test
    @BeforeEach
    fun setup() {
        serviceCar = Car("Servicing Car", 1, "125.00", false)
        testDrive = Car("Test Driving Car Before Purchase", 2, "10.00", true)
        repairCar = Car("Repair Car", 3, "50.00", true)
        testCar = Car("Testing Car", 6, "80.00", true)
        cleanCar = Car("Cleaning Car", 4, "20.00", true)

        // adding 5 different notes to the car API
        availableCars!!.add(serviceCar!!)
        availableCars!!.add(testDrive!!)
        availableCars!!.add(repairCar!!)
        availableCars!!.add(testCar!!)
        availableCars!!.add(cleanCar!!)
    }

    //The method ran after each test
    @AfterEach
    fun tearDown() {
        serviceCar = null
        testDrive = null
        repairCar = null
        testCar = null
        cleanCar = null
        availableCars = null
        soldCars = null
    }

    @Nested
    inner class AddCars {

        @Test
        fun `adding a Car to an available list adds to ArrayList`() {
            val newCar = Car("Checking Tyre Pressure", 2, "25.00", true)
            assertEquals(5, availableCars!!.numberOfCars())
            assertTrue(availableCars!!.add(newCar))
            assertEquals(6, availableCars!!.numberOfCars())
            assertEquals(newCar, availableCars!!.findCar(availableCars!!.numberOfCars() - 1))
        }

        @Test
        fun `adding a Car to an sold list adds to ArrayList`() {
            val newCar = Car("Checking Tyre Pressure", 2, "25.00", true)
            assertEquals(0, soldCars!!.numberOfCars())
            assertTrue(soldCars!!.add(newCar))
            assertEquals(1, soldCars!!.numberOfCars())
            assertEquals(newCar, soldCars!!.findCar(soldCars!!.numberOfCars() - 1))
        }
    }

    @Nested
    inner class ListCars {

        @Test
        fun `listAllCars returns No Cars Stored message when ArrayList is empty`() {
            assertEquals(0, soldCars!!.numberOfCars())
            assertTrue(soldCars!!.listAllCars().lowercase().contains("no cars"))
        }

        @Test
        fun `listAllCars returns Cars when ArrayList has cars stored`() {
            assertEquals(5, availableCars!!.numberOfCars())
            val carsString = availableCars!!.listAllCars().lowercase
            assertTrue(carsString.contains("Service Car"))
            assertTrue(carsString.contains("Test Drive"))
            assertTrue(carsString.contains("Repair Car"))
            assertTrue(carsString.contains("Test Car"))
            assertTrue(carsString.contains("Clean Car"))
        }
    }

    @Nested
    inner class DeleteCars {

        @Test
        fun `deleting a Car that does not exist, returns null`() {
            // checks whether the result of the method is null
            assertNull(soldCars!!.deleteCar(0))
            assertNull(availableCars!!.deleteCar(-1))
            assertNull(availableCars!!.deleteCar(5))
        }

        @Test
        fun `deleting a car that exists delete and returns deleted object`() {
            //checks the actual value matches the expected value
            assertEquals(5, availableCars!!.numberOfCars())
            assertEquals(cleanCar, availableCars!!.deleteCar(4)) // deleting car at index 4 returns cleanCar
            assertEquals(4, availableCars!!.numberOfCars())// number of cars is now 4 after deleting
            assertEquals(serviceCar, availableCars!!.deleteCar(0))// deleting car at index 0 returns serviceCar
            assertEquals(3, availableCars!!.numberOfCars())// number of cars is now 3 after deleting
        }
    }
}




