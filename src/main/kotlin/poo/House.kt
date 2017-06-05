package poo

/**
 * Created by amugica on 05/06/2017.
 */

open class House (name: String, var rooms: Int) : Building(name) {
    val materials = mutableListOf<String>()
    constructor(name: String, rooms: Int, materials: List<String>) : this(name = name, rooms = rooms) {
        this.materials.addAll(materials)
    }
}