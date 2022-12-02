fun main() {

    fun Sequence<String>.parse(): Sequence<Pair<Shape, Symbol>> = sequence {
        forEach { line ->
            yield(line.split(" ").let { Shape.lookup(it[0]) to Symbol.lookup(it[1]) })
        }
    }

    fun part1(input: Sequence<String>): Int {
        val strategy = mapOf(Symbol.Y to Shape.Paper, Symbol.X to Shape.Rock, Symbol.Z to Shape.Scissors)
        return input.parse().map { (other, self) ->
            strategy.getValue(self).run { score + result(other).score }
        }.sum()
    }

    fun part2(input: Sequence<String>): Int {
        val strategy = mapOf(Symbol.Y to Result.Draw, Symbol.X to Result.Lost, Symbol.Z to Result.Won)
        return input.parse().map { (other, self) ->
            strategy.getValue(self).run { score + other.toHave(this).score }
        }.sum()
    }

    test(::part1, 15)
    go(::part1, 11449)

    test(::part2, 12)
    go(::part2, 13187)

}


private enum class Symbol {
    X, Y, Z;

    companion object {
        val names = values().associateBy(Symbol::name)
        fun lookup(str: String): Symbol = names.getValue(str)
    }
}

private enum class Result(val score: Int) {
    Lost(0), Draw(3), Won(6)
}

private enum class Shape(val score: Int, val sym: String) {
    Rock(1, "A"),
    Paper(2, "B"),
    Scissors(3, "C"),
    ;

    companion object {
        val names = values().associateBy(Shape::sym)
        fun lookup(str: String): Shape = names.getValue(str)

        val defatedBy = mapOf(Scissors to Rock, Rock to Paper, Paper to Scissors)

        val defates = defatedBy.asSequence().map { it.value to it.key }.toMap()
    }

    fun toHave(result: Result): Shape = when (result) {
        Result.Draw -> this
        Result.Lost -> defates.getValue(this)
        else -> defatedBy.getValue(this)
    }

    fun result(other: Shape): Result = when {
        this == other -> Result.Draw
        this == defatedBy[other] -> Result.Won
        else -> Result.Lost
    }

}

