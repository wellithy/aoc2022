import Result.*
import Shape.*
import Symbol.*

private enum class Shape { Rock, Paper, Scissors }
private enum class Symbol { X, Y, Z }
private enum class Result { Lost, Draw, Won }

private val Result.score: Int
    get() = when (this) {
        Lost -> 0
        Draw -> 3
        Won -> 6
    }

private val Shape.score: Int
    get() = when (this) {
        Rock -> 1
        Paper -> 2
        Scissors -> 3
    }

private val Shape.defeats: Shape
    get() = when (this) {
        Rock -> Scissors
        Paper -> Rock
        Scissors -> Paper
    }

private fun Shape.result(other: Shape): Result = when (this) {
    other -> Draw
    other.defeats -> Lost
    else -> Won
}

private val defeatedBy = Shape.values().associateBy(Shape::defeats)
private fun Result.of(other: Shape): Shape = when (this) {
    Draw -> other
    Lost -> other.defeats
    Won -> defeatedBy.getValue(other)
}

private val Symbol.strategy1: Shape
    get() = when (this) {
        X -> Rock
        Y -> Paper
        Z -> Scissors
    }

private val Symbol.strategy2: Result
    get() = when (this) {
        X -> Lost
        Y -> Draw
        Z -> Won
    }

fun main() {
    val shapes = mapOf("A" to Rock, "B" to Paper, "C" to Scissors)
    val symbols = Symbol.values().associateBy(Symbol::name)


    fun Sequence<String>.parse(): Sequence<Pair<Shape, Symbol>> = sequence {
        forEach { line ->
            yield(line.split(" ").let { shapes.getValue(it[0]) to symbols.getValue(it[1]) })
        }
    }

    fun part1(input: Sequence<String>): Int = input.parse().map { (other, self) ->
        self.strategy1.run { score + result(other).score }
    }.sum()


    fun part2(input: Sequence<String>): Int = input.parse().map { (other, self) ->
        self.strategy2.run { score + of(other).score }
    }.sum()

    test(::part1, 15)
    go(::part1, 11449)

    test(::part2, 12)
    go(::part2, 13187)

}
