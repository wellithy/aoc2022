import java.nio.file.Path
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.useLines

fun <T> go(block: (Sequence<String>) -> T?, expected: T?): T? =
    go(block, expected, textRoot.resolve("${callerName()}.txt"))

fun <T> test(block: (Sequence<String>) -> T?, expected: T?): T? =
    go(block, expected, textRoot.resolve("${callerName()}_test.txt"))

fun <T> Sequence<T>.top(n: Int): PriorityQueue<T> = PriorityQueue<T>(n + 1).also { pq ->
    forEach {
        pq += it
        if (pq.size > n) pq.poll()
    }
}

private fun <T> go(block: (Sequence<String>) -> T?, expected: T?, path: Path): T? =
    path.useLines(block = block).also { result ->
        if (expected != result) error("Result=<$result> while Expected=<$expected>")
    }

private val textRoot = Path(".txt")
private fun callerName(): String = Throwable().stackTrace[3].className.run { substring(0, length - 2) }
