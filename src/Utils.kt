package util

import java.nio.file.Path
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.io.path.Path
import kotlin.io.path.useLines

fun <T> go(block: (Sequence<String>) -> T, expected: T) =
    go(block, expected, textRoot.resolve("${callerName()}.txt"))

fun <T> test(block: (Sequence<String>) -> T, expected: T) =
    go(block, expected, textRoot.resolve("${callerName()}_test.txt"))

fun <T> Sequence<T>.top(n: Int): PriorityQueue<T> = PriorityQueue<T>(n + 1).also { pq ->
    forEach {
        pq += it
        if (pq.size > n) pq.poll()
    }
}


fun <T> ArrayDeque<T>.removeFirst(count: Int): List<T> = mutableListOf<T>().also { list ->
    repeat(count) {
        list += removeFirst()
    }
}

fun <T> ArrayDeque<T>.addFirst(list: List<T>, order:Boolean) =
    (if(order) list.asReversed() else list).forEach(::addFirst)


private fun <T> go(block: (Sequence<String>) -> T?, expected: T?, path: Path): T? =
    path.useLines(block = block).also { result ->
        if (expected != result) error("Result=<$result> while Expected=<$expected>")
    }

private val textRoot = Path(".txt")
private fun callerName(): String = Throwable().stackTrace[3].className.run { substring(6, length - 2) }
