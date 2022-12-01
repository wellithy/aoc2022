fun main() {
    fun List<String>.toElves(): List<Elf> = mutableListOf<Elf>().also {
        var start = 0
        forEachIndexed { index, line ->
            if (line.isEmpty()) {
                it += Elf(subList(start, index))
                start = index + 1
            }
        }
        it += Elf(subList(start, size))
    }

    fun part1(input: List<String>): Int = input.toElves().maxOf(Elf::calories)

    fun part2(input: List<String>): Int = input
        .toElves()
        .mapTo(mutableListOf(), Elf::calories)
        .let {
            it.sortDescending()
            it.subList(0, 3).sum()
        }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    check(part1(input) == 71471)

    check(part2(testInput) == 45000)
    println(part2(input))
}

class Elf(list: List<String>) {
    val calories: Int = list.sumOf(String::toInt)
}