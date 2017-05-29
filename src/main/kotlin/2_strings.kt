/**
 * Created by Anartz Mugika on 29/05/2017.
 */

fun main(args: Array<String>) {
    val name = "Anartz"

    //Print name value
    println("My name is " + name)

    //Print float value with two decimals
    val pi = 3.14159265358979323
    val s = "pi = %.2f".format(pi)
    println(s);

    var value1 = 1
    var value2 = "2"
    var value3 = 3.0
    var value4 = 4.0

    //Use java string forma
    println(java.lang.String.format("%d, %s, %6f : %3f", value1, value2, value3, value4))

    //Use kotlin
    println("%d, %s, %6f : %3f".format(value1, value2, value3, value4))
}
