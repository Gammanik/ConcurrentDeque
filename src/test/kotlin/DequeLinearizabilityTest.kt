import org.jetbrains.kotlinx.lincheck.LinChecker
import org.jetbrains.kotlinx.lincheck.LoggingLevel
import org.jetbrains.kotlinx.lincheck.annotations.Operation
import org.jetbrains.kotlinx.lincheck.annotations.Validate
import org.jetbrains.kotlinx.lincheck.strategy.stress.StressCTest
import org.jetbrains.kotlinx.lincheck.strategy.stress.StressOptions
import org.jetbrains.kotlinx.lincheck.verifier.VerifierState
import org.junit.Before
import org.junit.Test

//import org.jetbrains.kotlinx.lincheck.strategy.stress.*


@StressCTest
//@Param(name = "key", gen = IntGen.class, conf = "1:5")
class DequeLinearizabilityTest: VerifierState() {
    var dq: ConcurrentDeque<Int> = SimpleConcurrentDeque()

    @Test
    fun runTests() {
        val opts = StressOptions()
                .iterations(1)
                .threads(4)
                .logLevel(LoggingLevel.INFO)

        LinChecker.check(DequeLinearizabilityTest::class.java, opts)
    }

    // make sure to add "-parameters" compilerArgument to javac
    @Operation
    fun addFirst(value: Int) {
        return dq.addFirst(value)
    }

    @Operation
    fun getSize(): Int {
        return dq.size
    }

    @Operation
    fun addLast(value: Int) {
        return dq.addLast(value)
    }

    @Operation
    fun pollFirst(): Int? {
        return dq.pollFirst()
    }

    @Operation
    fun pollLast(): Int? {
        return dq.pollLast()
    }

    @Operation
    fun contains(value: Int): Boolean {
        return dq.contains(value)
    }

    override fun extractState(): Any {
        return dq
    }
}