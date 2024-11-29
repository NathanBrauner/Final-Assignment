package controllers





class CarAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var cars = ArrayList<Car>()





    fun add(car: Car): Boolean {

            return cars.add(car)
        }

        fun listAllCars(): String =
            if (cars.isEmpty()) "No Cars Stored"
            else cars.joinToString (separator = "\n") { car ->
                cars.indexOf(car).toString() + ": " + car.toString()
            }

       fun numberOfSoldCars(): Int = cars.count { car: Car -> car.isCarAvailable}

        fun numberOfAvailableCars(): Int = cars.count { car: Car -> car.isCarAvailable}

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

                @Throws(Exception::class)
                fun load() {
                    cars = serializer.read() as ArrayList<Car>
                }

                @Throws(Exception::class)
                fun store() {
                    serializer.write(cars)
                }


                private fun formatListString(carsToFormat : List<Car>) : String =
                    carsToFormat
                        .joinToString (separator = "\n") { car ->
                            cars.indexOf(car).toString() + ": " + car.toString() }

            }




