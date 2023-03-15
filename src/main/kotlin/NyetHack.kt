var heroName: String = ""

fun main() {
    //changeNarratorMood()
    heroName = promptHeroName()
    narrate("$heroName, ${createTitle(heroName)}, heads to the town square.")
    visiteTavern()
}

private fun promptHeroName(): String {
    narrate("A hero enters the town of Kronstadt. What is their name?")
    { message ->
        //Виведення повідомлення жовтим кольором
        "\u001b[33;1m$message\u001b[0m"
    }
    /*val inpute = readLine()
    require(heroName != null && heroName.isNotEmpty()) {
        "The hero must have a name."
    }
    return input */
    println("Madrigal")
    return "Madrigal"
}

private fun createTitle(name: String): String {
    return when {
        name.all {it.isDigit()} -> "The Identifiable"
        name.none {it.isLetter()} -> "The Witness Protection Member"
        name.count {it.lowercase() in "aeiou"} > 4 -> "The master of vowels"
        name.all {it.isUpperCase()} -> "The outstanding"
        name.length > 10 -> "The spacious"
        name.equals(name.reversed(), ignoreCase = true) -> "The carrier of the palindrome"
        else -> "The Renowned Hero"
    }
}