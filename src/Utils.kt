import java.nio.file.Path
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.useLines

const val TEST = "test"
fun <T> go(block: (Sequence<String>) -> T, expected: T?, extension: String = "txt"): T =
    callerName().textPath(extension).useLines(block = block).also { result ->
        if (expected != result) error("Result=$result while Expected=$expected")
    }

fun <T> Sequence<T>.top(n: Int): PriorityQueue<T> = PriorityQueue<T>(n + 1).also { pq ->
    forEach {
        pq.add(it)
        if (pq.size > n) pq.poll()
    }
}


private val textRoot = Path("src").resolve("txt")
private fun String.textPath(extension: String): Path = textRoot.resolve("$this.$extension")
private fun callerName(): String = Exception().stackTrace[3].className.run { substring(0, length - 2) }
