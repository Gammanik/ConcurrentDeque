public interface ConcurrentDeque<T> {
    /** Checks if this deque is empty. */
    public val isEmpty: Boolean
    /** The number of elements in this deque. */
    public val size: Int
    /** Removes all elements from this deque. */
    public fun clear()
    /** Pushes [value] to the front of this deque. */
    public fun addFirst(value: T)
    /** Pushes [value] to the back of this deque. */
    public fun addLast(value: T)
    /** Returns the first element in this deque, or null if it is empty. */
    public fun peekFirst(): T?
    /** Returns the last element in this deque, or null if it is empty. */
    public fun peekLast(): T?
    /**
     * Removes the first element from this deque and returns it.
     * Returns null if the deque is empty.
     */
    public fun pollFirst(): T?
    /**
     * Removes the last element from this deque and returns it.
     * Returns null if the deque is empty.
     */
    public fun pollLast(): T?

    /** Checks if this deque contains the given value. */
    public fun contains(value: T): Boolean
}