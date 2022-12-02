import Shape.*
import Symbol.*
import Result.*

private enum class Symbol { X, Y, Z }
private enum class Result { Lost, Draw, Won }
private enum class Shape { Rock, Paper, Scissors }

private val Result.score: Int
    get() = when (this) {
        Lost -> 0
        Draw -> 3
        Won -> 6
    }


fun main() {
    val shapes = mapOf("A" to Rock, "B" to Paper, "C" to Scissors)
    val symbols = Symbol.values().associateBy(Symbol::name)


    fun Sequence<String>.parse(): Sequence<Pair<Shape, Symbol>> = sequence {
        forEach { line ->
            yield(line.split(" ").let { shapes.getValue(it[0]) to symbols.getValue(it[1]) })
        }
    }

    fun Shape.score(): Int = when (this) {
        Rock -> 1
        Paper -> 2
        Scissors -> 3
    }

    val defeats = mapOf(Rock to Scissors, Paper to Rock, Scissors to Paper)
    val defeatedBy = defeats.asSequence().map { it.value to it.key }.toMap()

    fun Shape.result(other: Shape): Result = when {
        this == other -> Draw
        this == defeatedBy[other] -> Won
        else -> Lost
    }

    fun part1(input: Sequence<String>): Int {
        val strategy = mapOf(Y to Paper, X to Rock, Z to Scissors)
        return input.parse().map { (other, self) ->
            strategy.getValue(self).run { score() + result(other).score }
        }.sum()
    }


    fun Shape.toHave(result: Result): Shape = when (result) {
        Draw -> this
        Lost -> defeats.getValue(this)
        else -> defeatedBy.getValue(this)
    }


    fun part2(input: Sequence<String>): Int {
        val strategy = mapOf(Y to Draw, X to Lost, Z to Won)
        return input.parse().map { (other, self) ->
            strategy.getValue(self).run { score + other.toHave(this).score() }
        }.sum()
    }

    test(::part1, 15)
    go(::part1, 11449)

    test(::part2, 12)
    go(::part2, 13187)


}
