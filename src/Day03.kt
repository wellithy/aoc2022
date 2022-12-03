internal typealias Item = Char
internal typealias Rucksack = String
internal typealias Group = List<String>

internal fun Item.priority(): Int =
    when (this) {
        in 'a'..'z' -> code - 'a'.code + 1
        in 'A'..'Z' -> code - 'A'.code + 27
        else -> error("")
    }

internal fun Rucksack.common(): Item =
    (substring(0, length / 2).toSet() intersect substring(length / 2).toSet()).single()

internal fun Group.badge(): Item =
    (get(0).toSet() intersect (get(1).toSet() intersect get(2).toSet())).single()

fun main() {

    fun part1(input: Sequence<String>): Int =
        input.map(Rucksack::common).sumOf(Item::priority)

    fun part2(input: Sequence<String>): Int =
        input.chunked(3).map(Group::badge).sumOf(Item::priority)

    test(::part1, 157)
    go(::part1, 8252)

    test(::part2, 70)
    go(::part2, 2828)
}

