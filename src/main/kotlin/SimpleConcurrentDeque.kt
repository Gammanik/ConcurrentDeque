class SimpleConcurrentDeque<T>: ConcurrentDeque<T> {
    override val isEmpty: Boolean
        get() { return size == 0 }

    override var size: Int = 0

    private var head = Node(null)
    private var tail = Node(null)

    init {
        tail.prev = head
        head.next = tail
    }

    override fun clear() {
        size = 0
        head.next = tail
        tail.prev = head
    }

    override fun addFirst(value: T) {
        val newNode = Node(value)

        val tmp = head.next
        tmp?.prev = newNode

        head.next = newNode
        newNode.prev = head
        newNode.next = tmp
        size += 1
    }

    override fun addLast(value: T) {
        val newNode = Node(value)

        val tmp = tail.prev
        tmp?.next = newNode

        tail.prev = newNode
        newNode.prev = tmp
        newNode.next = tail

        size += 1
    }

    override fun peekFirst(): T? {
        return head.next?.value
    }

    override fun peekLast(): T? {
        return tail.prev?.value
    }

    override fun pollFirst(): T? {
        val value = head.next?.value
        head.next = head.next?.next

        if (!isEmpty) { size -= 1 }
        return value
    }

    override fun pollLast(): T? {
        val value = tail.prev?.value
        tail.prev = tail.prev?.prev

        if (!isEmpty) { size -= 1 }
        return value
    }

    override fun contains(value: T): Boolean {
        var tmp = head.next

        while (tmp?.next != null) {
            if (tmp.value == value) {
                return true
            }
            tmp = tmp.next
        }

        return false
    }


    inner class Node(val value: T?) {
        var prev: Node? = null
        var next: Node? = null
    }

    override fun toString(): String {
        var tmp = head.next
        val arr = ArrayList<String>()
        while (tmp?.next != null) {
            arr.add(tmp.value.toString())
            tmp = tmp.next
        }
        return arr.joinToString(", ", "{", "}")
    }

    override fun equals(other: Any?): Boolean {
        if (other !is SimpleConcurrentDeque<*>) {
            return false
        }

        if (size != other.size) {
            return false
        }

        var tmpThis = head.next
        var tmpOther = other.head.next

        while (tmpThis?.next != null) {
            if (tmpThis != tmpOther) {
                return false
            }
            tmpThis = tmpThis.next
            tmpOther = tmpOther.next
        }

        return true
    }

    override fun hashCode(): Int {
        var tmp = head.next
        var hashCode = 1

        while (tmp?.next != null) {
            tmp = tmp.next
            hashCode = 31 * hashCode + (tmp?.hashCode() ?: 0)
        }

        return hashCode
    }
}
