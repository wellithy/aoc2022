import java.util.Comparator
import java.util.PriorityQueue

fun main() {
    fun List<String>.toCalories(): List<Int> = mutableListOf<Int>().also { calories ->
        var calory = 0
        forEach {
            if (it.isEmpty()) {
                calories += calory
                calory = 0
            } else
                calory += it.toInt()
        }
        calories += calory
    }

    fun part1(input: List<String>): Int = input.toCalories().max()

    fun part2(input: List<String>): Int = input
        .toCalories()
        .sortedDescending()
        .subList(0, 3).sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    check(part1(input) == 71471)

    check(part2(testInput) == 45000)
    check(part2(input) == 211189)
}
