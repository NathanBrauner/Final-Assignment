package controllers

/**
 * Unit tests for the [CarAPI] class, focusing on car management operations.
 *
 * This test class includes multiple unit tests that cover functionality such as adding,
 * deleting, listing cars, and saving/loading car data in both XML and JSON formats.
 * The [CarAPI] instance is tested with different operations to ensure correctness of
 * serialization and deserialization processes as well as the integrity of the data.
 */
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

    // Test cars used in the test cases.
    private var serviceCar: Car? = null
    private var testDrive: Car? = null
    private var repairCar: Car? = null
    private var testCar: Car? = null
    private var cleanCar: Car? = null
    // CarAPI instances for testing, one for available cars and another for sold cars.
    private var availableCars: CarAPI? = CarAPI(XMLSerializer(File("cars.xml")))
    private var soldCars: CarAPI? = CarAPI(XMLSerializer(File("cars.xml")))

    /**
     * Set up the test resources before each test method.
     *
     * This method is executed before each test to initialize cars and add them to
     * the [availableCars] collection. It ensures the test setup is consistent for each test.
     */
    @BeforeEach
    fun setup() {
        // Initialize cars with test data
        serviceCar = Car("Servicing Car", "Toyota Yaris", 1, "125.00", 2010.toString(), "2024-12-06", false)
        testDrive = Car("Test Driving Car Before Purchase", "Ford Focus", 2, "10.00",
            2014.toString(), "2024-12-10", true)
        repairCar = Car("Repair Car", "Volkswagen GTI" , 3, "50.00", 2020.toString(), "2024-12-20", true)
        testCar = Car("Testing Car", "Nissan Micra", 6, "80.00", 2005.toString(), "2024-12-28",true)
        cleanCar = Car("Cleaning Car", "Ford Fiesta",  4, "20.00", 2014.toString(), "2024-12-29", true)

        // adding 5 different cars to the available cars collection
        availableCars!!.add(serviceCar!!)
        availableCars!!.add(testDrive!!)
        availableCars!!.add(repairCar!!)
        availableCars!!.add(testCar!!)
        availableCars!!.add(cleanCar!!)
    }

    /**
     * Clean up test resources after each test method.
     *
     * This method is executed after each test to clear any test-specific resources,
     * ensuring a clean state for subsequent tests.
     */
    @AfterEach
    fun tearDown() {
        // Clear references to test cars and collections
        serviceCar = null
        testDrive = null
        repairCar = null
        testCar = null
        cleanCar = null
        availableCars = null
        soldCars = null
    }

    // Nested class for tests related to adding cars.
    @Nested
    inner class AddCars {

        /**
         * Test for adding a car to the available cars collection.
         *
         * This test ensures that adding a new car to the [availableCars] collection
         * successfully increases the number of cars, and the added car can be retrieved
         * by its index.
         */
        @Test
        fun `adding a Car to an available list adds to ArrayList`() {
            val newCar = Car("Checking Tyre Pressure", "Toyota Corolla", 2, "25.00", 2012.toString(), "2024-12-29", true)
            assertEquals(5, availableCars!!.numberOfCars())
            assertTrue(availableCars!!.add(newCar))
            assertEquals(6, availableCars!!.numberOfCars())
            assertEquals(newCar, availableCars!!.findCar(availableCars!!.numberOfCars() - 1))
        }

        /**
         * Test for adding a car to the sold cars collection.
         *
         * This test ensures that adding a new car to the [soldCars] collection
         * successfully increases the number of cars in that collection as well.
         */
        @Test
        fun `adding a Car to an sold list adds to ArrayList`() {
            val newCar = Car("Checking Tyre Pressure", "Toyota Corolla", 2, "25.00", 2012.toString(), "2024-12-16", true)
            assertEquals(0, soldCars!!.numberOfCars())
            assertTrue(soldCars!!.add(newCar))
            assertEquals(1, soldCars!!.numberOfCars())
            assertEquals(newCar, soldCars!!.findCar(soldCars!!.numberOfCars() - 1))
        }
    }

    // Nested class for tests related to listing cars.
    @Nested
    inner class ListCars {

        /**
         * Test for listing cars when the collection is empty.
         *
         * This test ensures that when no cars are stored in the [soldCars] collection,
         * a message indicating that no cars are available is returned.
         */
        @Test
        fun `listAllCars returns No Cars Stored message when ArrayList is empty`() {
            assertEquals(0, soldCars!!.numberOfCars())
            assertTrue(soldCars!!.listAllCars().lowercase().contains("no cars"))
        }

        /**
         * Test for listing cars when the collection has cars stored.
         *
         * This test checks if all cars in the [availableCars] collection are listed
         * correctly by checking the car names and ensuring they are included in the output.
         */
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

    // Nested class for tests related to deleting cars.
    @Nested
    inner class DeleteCars {

        /**
         * Test for attempting to delete a car that does not exist.
         *
         * This test verifies that when an invalid index is provided, the method returns `null`.
         */
        @Test
        fun `deleting a Car that does not exist, returns null`() {
            // checks whether the result of the method is null
            assertNull(soldCars!!.deleteCar(0))
            assertNull(availableCars!!.deleteCar(-1))
            assertNull(availableCars!!.deleteCar(5))
        }

        /**
         * Test for successfully deleting a car that exists in the collection.
         *
         * This test ensures that when a valid car is deleted, it is correctly removed
         * from the collection and the number of cars is updated accordingly.
         */
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

    // Nested class for persistence tests (saving/loading).
    @Nested
    inner class PersistenceTests {

        /**
         * Test for saving and loading an empty collection using XML format.
         *
         * This test ensures that when an empty collection is saved and loaded, the
         * application does not crash, and the loaded collection remains empty.
         */
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

        /**
         * Test for saving and loading a collection using XML format without losing data.
         *
         * This test verifies that after saving and loading a collection of cars, the
         * cars are retained correctly in the loaded collection.
         */
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

    /**
     * Test for saving and loading an empty collection using JSON format.
     *
     * This test ensures that when an empty collection is saved and loaded, the
     * application does not crash, and the loaded collection remains empty.
     */
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

    /**
     * Test for saving and loading a collection using JSON format without losing data.
     *
     * This test verifies that after saving and loading a collection of cars, the
     * cars are retained correctly in the loaded collection.
     */
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





