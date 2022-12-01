fun main() {

    fun part1(input: List<String>): Int = 1

    fun part2(input: List<String>): Int = 1

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1)

    val input = readInput("Day02")
    println(part1(input))

    check(part2(testInput) == 1)
    println(part2(input))
}
