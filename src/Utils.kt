import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
val srcRoot = Path("src").resolve("txt")
fun readInput(name: String) = srcRoot.resolve("$name.txt").readLines()

fun ByteArray.hex(): String = BigInteger(1, this).toString(16)
/**
 * Converts string to md5 hash.
 */
fun ByteArray.md5(): ByteArray = MessageDigest.getInstance("MD5").digest(this)
fun String.md5(): String = toByteArray().md5().hex()
