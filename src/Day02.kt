package day02

import util.*
import day02.Result.*
import day02.Hand.*
import day02.Symbol.*

enum class Hand(val score: Int, val symbol: String) {
    Rock(1, "A"),
    Paper(2, "B"),
    Scissors(3, "C"),
    ;

    val defeats: Hand
        get() = when (this) {
            Rock -> Scissors
            Paper -> Rock
            Scissors -> Paper
        }

    fun result(other: Hand): Result = when (other) {
        defeats -> Won
        this -> Draw
        else -> Lost
    }

    companion object {
        val hands = values().associateBy(Hand::symbol)
        fun of(symbol: String): Hand = hands.getValue(symbol)
        val defeated = values().associateBy(Hand::defeats)
    }

    val defeatedBy: Hand get() = defeated.getValue(this)

}

enum class Symbol {
    X, Y, Z;

    companion object {
        val symbols = values().associateBy(Symbol::name)
        fun of(symbol: String): Symbol = symbols.getValue(symbol)
    }
}

enum class Result(val score: Int) {
    Lost(0),
    Draw(3),
    Won(6)
}

fun Sequence<String>.parse(): Sequence<Pair<Hand, Symbol>> = sequence {
    forEach { line ->
        yield(line.split(" ").let { Hand.of(it[0]) to Symbol.of(it[1]) })
    }
}

fun Hand.score(other: Hand): Int = score + result(other).score
val Symbol.strategy1: Hand
    get() = when (this) {
        X -> Rock
        Y -> Paper
        Z -> Scissors
    }

fun part1(input: Sequence<String>): Int = input.parse().map { (other, self) -> self.strategy1.score(other) }.sum()


val Symbol.strategy2: Result
    get() = when (this) {
        X -> Lost
        Y -> Draw
        Z -> Won
    }

fun Result.of(other: Hand): Hand = when (this) {
    Lost -> other.defeats
    Draw -> other
    Won -> other.defeatedBy
}

fun Result.score(other: Hand): Int = score + of(other).score

fun part2(input: Sequence<String>): Int = input.parse().map { (other, self) -> self.strategy2.score(other) }.sum()

fun main() {

    test(::part1, 15)
    go(::part1, 11449)

    test(::part2, 12)
    go(::part2, 13187)

}
