fun main() {

    fun part1(input: Sequence<String>): Int = 1

    fun part2(input: Sequence<String>): Int = 2

    go(::part1, 1, TEST)
    go(::part1, 1)

    go(::part2, 2, TEST)
    go(::part2, 2)
}
