import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test

class SingleThreatedDequeTest {
    var dq: ConcurrentDeque<Int> = SimpleConcurrentDeque()

    @Before
    fun setUp() {
        dq = SimpleConcurrentDeque()
    }

    @Test
    fun shouldAddFirst() {
        assertTrue(dq.isEmpty)
        assertEquals(2,2)

        dq.addFirst(1)
        assertEquals(dq.peekFirst(), 1)
        assertFalse(dq.isEmpty)

        dq.addFirst(2)
        assertFalse(dq.isEmpty)
        assertEquals(dq.peekFirst(), 2)
    }

    @Test
    fun shouldAddLast() {
        dq.addLast(1)
        assertEquals(dq.peekFirst(), 1)
        assertEquals(dq.peekLast(), 1)
        assertTrue(dq.contains(1))

        dq.addLast(2)
        assertEquals(dq.peekFirst(), 1)
        assertEquals(dq.peekLast(), 2)
        assertTrue(dq.contains(1))
        assertTrue(dq.contains(2))


        dq.addLast(3)
        assertEquals(dq.peekFirst(), 1)
        assertEquals(dq.peekLast(), 3)
        assertTrue(dq.contains(1))
        assertTrue(dq.contains(2))
        assertTrue(dq.contains(3))

        dq.addFirst(0)
        dq.addFirst(-1)
    }

    @Test
    fun shouldPollBeginning() {
        assertNull(dq.pollFirst())

        dq.addFirst(1)
        assertFalse(dq.isEmpty)
        assertEquals(dq.pollFirst(), 1)
        assertTrue(dq.isEmpty)

        dq.addLast(2)
        assertEquals(dq.pollFirst(), 2)
        assertTrue(dq.isEmpty)
        assertNull(dq.pollFirst())


        dq.addFirst(4)
        dq.addFirst(3)
        assertEquals(dq.pollFirst(), 3)
        assertEquals(dq.peekFirst(), 4)
        assertEquals(dq.pollFirst(), 4)
        assertNull(dq.pollFirst())
    }

    @Test
    fun shouldPollLast() {
        assertNull(dq.pollFirst())
        dq.addFirst(1)
        assertEquals(dq.pollLast(), 1)
    }

    @Test
    fun shouldReturnNullIfEmpty() {
        dq.addLast(1)
        assertEquals(dq.pollFirst(), 1)
        assertNull(dq.pollLast())
    }

    @Test
    fun lastFirstTest() {
        dq.addLast(-6)
        assertEquals(dq.pollFirst(), -6)
        dq.addLast(1)
        assertEquals(dq.pollFirst(), 1)

        dq.addFirst(-6)
        assertEquals(dq.pollLast(), -6)
        dq.addLast(-5)
        assertEquals(dq.pollLast(), -5)
    }

    @Test
    fun clearTest() {
        dq.addLast(-6)
        dq.addLast(1)
        dq.addFirst(-1)
        dq.clear()

        assertNull(dq.pollFirst())
        assertNull(dq.pollLast())
        assertTrue(dq.isEmpty)
    }
}