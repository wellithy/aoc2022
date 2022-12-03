package day02

import util.*
import day02.Result.*
import day02.Hand.*
import day02.Symbol.*

enum class Hand { Rock, Paper, Scissors }
enum class Symbol { X, Y, Z }
enum class Result { Lost, Draw, Won }

val Result.score: Int
    get() = when (this) {
        Lost -> 0
        Draw -> 3
        Won -> 6
    }

val Hand.score: Int
    get() = when (this) {
        Rock -> 1
        Paper -> 2
        Scissors -> 3
    }

val Hand.defeats: Hand
    get() = when (this) {
        Rock -> Scissors
        Paper -> Rock
        Scissors -> Paper
    }

fun Hand.result(other: Hand): Result = when (other) {
    defeats -> Won
    this -> Draw
    else -> Lost
}

val defeated = Hand.values().associateBy(Hand::defeats)

val Hand.defeatedBy: Hand get() = defeated.getValue(this)
fun Result.of(other: Hand): Hand = when (this) {
    Lost -> other.defeats
    Draw -> other
    Won -> other.defeatedBy
}

val Symbol.strategy1: Hand
    get() = when (this) {
        X -> Rock
        Y -> Paper
        Z -> Scissors
    }

val Symbol.strategy2: Result
    get() = when (this) {
        X -> Lost
        Y -> Draw
        Z -> Won
    }
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

fun main() {

    test(::part1, 15)
    go(::part1, 11449)

    test(::part2, 12)
    go(::part2, 13187)

}
