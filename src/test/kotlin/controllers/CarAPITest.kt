package controllers

import models.Car
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File

class CarAPITest {

    private var serviceCar: Car? = null
    private var testDrive: Car? = null
    private var repairCar: Car? = null
    private var testCar: Car? = null
    private var cleanCar: Car? = null
    private var availableCars: CarAPI? = CarAPI(XMLSerializer(File("cars.xml")))
    private var soldCars: CarAPI? = CarAPI(XMLSerializer(File("cars.xml")))

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
            val carsString = availableCars!!.listAllCars().lowercase()
            assertTrue(carsString.contains("service Car"))
            assertTrue(carsString.contains("test Drive"))
            assertTrue(carsString.contains("repair Car"))
            assertTrue(carsString.contains("test Car"))
            assertTrue(carsString.contains("clean Car"))
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

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesnt crash app`() {

            val storingCars = CarAPI(XMLSerializer(File("cars.xml")))
            storingCars.store()

            val loadedCars = CarAPI(XMLSerializer(File("cars.xml")))
            loadedCars.load()

            assertEquals(0, storingCars.numberOfCars())
            assertEquals(0, loadedCars.numberOfCars())
            assertEquals(storingCars.numberOfCars(), loadedCars.numberOfCars())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesnt loose data`() {

            val storingCars = CarAPI(XMLSerializer(File("cars.xml")))
            storingCars.add(testCar!!)
            storingCars.add(cleanCar!!)
            storingCars.add(testDrive!!)
            storingCars.store()

            val loadedCars = CarAPI(XMLSerializer(File("cars.xml")))
            loadedCars.load()

            assertEquals(3, storingCars.numberOfCars())
            assertEquals(3, loadedCars.numberOfCars())
            assertEquals(storingCars.numberOfCars(), loadedCars.numberOfCars())
            assertEquals(storingCars.findCar(0), loadedCars.findCar(0))
            assertEquals(storingCars.findCar(1), loadedCars.findCar(1))
            assertEquals(storingCars.findCar(2), loadedCars.findCar(2))
        }
    }


    @Test
    fun `saving and loading an empty collection in JSON doesn't crash app`() {
        // Saving an empty cars.json file.
        val storingCars = CarAPI(JSONSerializer(File("cars.json")))
        storingCars.store()

        //Loading the empty cars.json file into a new object
        val loadedCars = CarAPI(JSONSerializer(File("cars.json")))
        loadedCars.load()

        //Comparing the source of the cars (storingCars) with the json loaded cars (loadedCars)
        assertEquals(0, storingCars.numberOfCars())
        assertEquals(0, loadedCars.numberOfCars())
        assertEquals(storingCars.numberOfCars(), loadedCars.numberOfCars())
    }

    @Test
    fun `saving and loading an loaded collection in JSON doesn't loose data`() {
        // Storing 3 cars to the cars.json file.
        val storingCars = CarAPI(JSONSerializer(File("cars.json")))
        storingCars.add(testCar!!)
        storingCars.add(cleanCar!!)
        storingCars.add(testDrive!!)
        storingCars.store()

        //Loading cars.json into a different collection
        val loadedCars = CarAPI(JSONSerializer(File("cars.json")))
        loadedCars.load()

        //Comparing the source of the cars (storingCars) with the json loaded cars (loadedCars)
        assertEquals(3, storingCars.numberOfCars())
        assertEquals(3, loadedCars.numberOfCars())
        assertEquals(storingCars.numberOfCars(), loadedCars.numberOfCars())
        assertEquals(storingCars.findCar(0), loadedCars.findCar(0))
        assertEquals(storingCars.findCar(1), loadedCars.findCar(1))
        assertEquals(storingCars.findCar(2), loadedCars.findCar(2))
    }
}





