package models

data class Car(
    var carMake: String,
    var carModel: String,
    var carEngine: Int,
    var carPrice: String,
    var carYear: String,
    var dateOfService: String,
    var isCarAvailable: Boolean
){
}