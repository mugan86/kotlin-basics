/*******************************************************************
 * Created by Anartz Mugika on 29/05/2017.
 * http://kotlinlang.org/docs/reference/control-flow.html
 */

fun main(args: Array<String>) {
    // Traditional usage
    var max = 132
    val a = 23
    val b = 2394
    if (a < b) max = b

// With else

    if (a > b) {
        max = a
    } else {
        max = b
    }

// As expression

    max = if (a > b) a else b

    //Using blocks

    max = if (a > b) {
        println("Choose a")
        a
    } else {
        println("Choose b")
        b
    }

    // Use when expresion

    when (a) {
        1 -> println("a == 1")
        2 -> println("a == 2")
        else -> { // Note the block
            println("a is neither 1 nor 2")
        }
    }

    when (a) {
        0, 1 -> println("a == 0 or a == 1")
        else -> println("otherwise")
    }

    println(max)
}