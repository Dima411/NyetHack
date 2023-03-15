import java.nio.file.WatchEvent.Modifier
import kotlin.random.Random
import kotlin.random.nextInt

var narrationModifier: (String) -> String = {it}

inline fun narrate (
    message: String,
    modifier: (String) -> String = {narrationModifier(it)}
) {
    println(modifier(message))
}

fun changeNarratorMood() {
    val mood: String
    val modifier: (String) -> String
    when (Random.nextInt(1..8)) {
        1 -> {
            mood = "loud"
            modifier = { message ->
                val numExclamationPoints = 3
                message.uppercase() + "!".repeat(numExclamationPoints)
            }
        }

        2 -> {
            mood = "tired"
            modifier = { message ->
                message.lowercase().replace(" ", "... ")
            }
        }

        3 -> {
            mood = "unsure"
            modifier = { message -> "$message?" }
        }

        4 -> {
            mood = "lazily"
            modifier = {messege -> messege.substring(0, 26) + "... And so on"}

        }

        5 -> {
            mood = "mysterious"
            modifier = {message ->
                message.replace(Regex("[a-z]")) { replacedResult ->
                    when (replacedResult.value) {
                        "a" -> "3"
                        "i" -> "$"
                        "r" -> "#"
                        "s" -> "5"
                        "o" -> "*"
                        "t" -> "9"
                        else -> replacedResult.value
                    }
                }
            }
        }

        6 -> {
            mood = "poetic"
            modifier = {message -> message.replace(",", ", Adventure awaits\n")}
        }

        7 -> {
            var narrationsGive = 0
            mood = "Like sending an itemized bill"
            modifier = {message ->
                narrationsGive++
                "$message.\n(I have narrated $narrationsGive things)"
            }
        }

        else -> {
            mood = "proffessional"
            modifier = { message ->
                "$message."
            }
        }
    }

    narrationModifier = modifier
    narrate("The narrator begins to feel $mood")
}