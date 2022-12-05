package day05

import util.*

@JvmInline
value class Crate(val mark: Char)

@JvmInline
value class Stack(private val crates: ArrayDeque<Crate> = ArrayDeque()) {
    fun addLast(crate: Crate) = crates.addLast(crate)

    fun move(other: Stack, count: Int, order: Boolean) =
        other.crates.addFirst(crates.removeFirst(count), order)

    fun top(): Crate = crates.first()

}

@JvmInline
value class Supplies(private val stacks: Map<Int, Stack>) {
    fun execute(count: Int, from: Int, to: Int, order: Boolean) =
        stacks.getValue(from).move(stacks.getValue(to), count, order)

    fun top(): String =
        generateSequence(1) { it + 1 }.take(stacks.size).map { stacks.getValue(it).top().mark }.joinToString("")

}

class Solver(private val order: Boolean) {
    private companion object {
        const val CRATE_DELIMITER: Char = '['
        val MOVE_COMMAND: Regex = Regex("""move (\d+) from (\d+) to (\d+)""")
        const val CRATE_WIDTH: Int = "[X] ".length
        fun String.crate(id: Int): Crate? =
            if (get(id * CRATE_WIDTH) != CRATE_DELIMITER) null
            else Crate(get(id * CRATE_WIDTH + 1))

        fun MutableMap<Int, Stack>.addCrates(line: String) {
            val max = line.length + 1
            require(max % CRATE_WIDTH == 0)
            repeat(max / CRATE_WIDTH) { id ->
                line.crate(id)?.let {
                    computeIfAbsent(id + 1) { _ -> Stack() }.addLast(it)
                }
            }
        }
    }

    fun solve(lines: Sequence<String>): String {
        val map = mutableMapOf<Int, Stack>()// this is kind of cheating :-)
        val supplies = Supplies(map)
        lines.forEach {
            if (CRATE_DELIMITER in it) map.addCrates(it)
            else MOVE_COMMAND.matchEntire(it)?.run {
                destructured.toList().map(String::toInt).let { (count, from, to) ->
                    supplies.execute(count, from, to, order)
                }
            }
        }
        return supplies.top()
    }

}

fun part1(input: Sequence<String>): String = Solver(false).solve(input)
fun part2(input: Sequence<String>): String = Solver(true).solve(input)


fun main() {
    test(::part1, "CMZ")
    go(::part1, "JDTMRWCQJ")

    test(::part2, "MCD")
    go(::part2, "VHJDDCWRD")
}

