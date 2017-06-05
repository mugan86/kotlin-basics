package poo

/**
 * Created by amugica on 05/06/2017.
 */

fun main(args: Array<String>) {
    val materials = listOf("Brick", "Wood", "Glass", "Stone")
    val house = House(name = "Wally Manor", materials = materials, rooms = 8)
    val zoo = Monument(name= "BCN Zoo", high = -1, visitorsPerYear = 10239393)

    println("${house.info()}) wich is a house that is made of ${house.materials} and has ${house.rooms} rooms")
    house.materials.addAll((listOf("Marble", "Gold")))
    println("New house materials: ${house.materials}")

    println("Zoo: ${zoo.info()}")
}