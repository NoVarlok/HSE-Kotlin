import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import kotlin.system.exitProcess

fun calculateScore(src: Set<String>, checkIn: Set<String>): Int {
    var score = 0
    src.forEach {
        if (checkIn.contains(it)) {
            score += it.length
        }
    }
    return score
}

fun main() = runBlocking {
    val endSymbol = "!END"
    val minLength = 25
    val filepath = "words.txt"
    val outputFile = "output.txt"
    val words: MutableSet<String> = mutableSetOf()
    try {
        FileReader(filepath).use { reader ->
            reader.forEachLine {
                words.add(it)
            }
        }
    } catch (e: FileNotFoundException) {
        println("Unable to open $filepath")
        exitProcess(0)
    }
    val roots = words.filter { it.length >= minLength }
    if (roots.isEmpty()) {
        println("File must contains at least 1 word of length >= $minLength")
        exitProcess(0)
    }
    val rootWord = roots.random()

    println("Make up as many words as possible from the letters of the word \"$rootWord\" and enter them (each on a new line)")
    println("Print \"$endSymbol\" if you entered all your words")

    val usersWords: MutableSet<String> = mutableSetOf()
    while (true) {
        val userWord = readLine()
        if (userWord == null || userWord == endSymbol) {
            break
        }
        usersWords.add(userWord.toLowerCase())
    }
    val scoreAwait = async { calculateScore(usersWords, words) }
    try {
        val outFile = FileWriter(outputFile)
        outFile.use { writer ->
            usersWords.forEach { writer.write(it + '\n') }
        }
    } catch (e: Exception) {
        println("Unable to write words to the $outputFile")
    }
    val score = scoreAwait.await()
    println("Your score is $score")
}