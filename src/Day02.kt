import Result.*
import Hand.*
import Symbol.*

internal enum class Hand { Rock, Paper, Scissors }
internal enum class Symbol { X, Y, Z }
internal enum class Result { Lost, Draw, Won }

internal val Result.score: Int
    get() = when (this) {
        Lost -> 0
        Draw -> 3
        Won -> 6
    }

internal val Hand.score: Int
    get() = when (this) {
        Rock -> 1
        Paper -> 2
        Scissors -> 3
    }

internal val Hand.defeats: Hand
    get() = when (this) {
        Rock -> Scissors
        Paper -> Rock
        Scissors -> Paper
    }

internal fun Hand.result(other: Hand): Result = when (other) {
    defeats -> Won
    this -> Draw
    else -> Lost
}

internal val defeated = Hand.values().associateBy(Hand::defeats)

internal val Hand.defeatedBy: Hand get() = defeated.getValue(this)
internal fun Result.of(other: Hand): Hand = when (this) {
    Lost -> other.defeats
    Draw -> other
    Won -> other.defeatedBy
}

internal val Symbol.strategy1: Hand
    get() = when (this) {
        X -> Rock
        Y -> Paper
        Z -> Scissors
    }

internal val Symbol.strategy2: Result
    get() = when (this) {
        X -> Lost
        Y -> Draw
        Z -> Won
    }

fun main() {
    val shapes = mapOf("A" to Rock, "B" to Paper, "C" to Scissors)
    val symbols = Symbol.values().associateBy(Symbol::name)


    fun Sequence<String>.parse(): Sequence<Pair<Hand, Symbol>> = sequence {
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
