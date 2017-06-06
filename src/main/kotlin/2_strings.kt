/***********************************************************
 * Created by Anartz Mugika on 29/05/2017.
 */

fun main(args: Array<String>) {
    //If use 'var', variable is mutable (let in typescript)
    //If use 'val' not mutable = constant
    val name = "Anartz"

    val _name = name.replace("A","");

    //Print name value
    printValue("My name is " + name)

    //Print float value with two decimals
    val pi = 3.14159265358979323
    var s = "pi = %.2f".format(pi)
    printValue(s);

    var value1 = 1
    var value2 = "2"
    var value3 = 3.0
    var value4 = 4.0

    //Use java string forma
    printValue(java.lang.String.format("%d, %s, %6f : %3f", value1, value2, value3, value4))

    //Use kotlin
    printValue("%d, %s, %6f : %3f".format(value1, value2, value3, value4))

    //String templates
    val i = 10
    var str = "i = $i" // evaluates to "i = 10"
    printValue(str)

    s = "abc"
    str = "$s.length is ${s.length}" // evaluates to "abc.length is 3"

    printValue(str)

    //Literal string always inside"{'string'}
    val price = """
        ${'$'}9.99
        """
    printValue(price)
}

fun printValue(x: String) {
    println(x)
}