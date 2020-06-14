class SimpleConcurrentDeque<T>: ConcurrentDeque<T> {
    override val isEmpty: Boolean
        @Synchronized get() { return size == 0 }

    @Volatile override var size: Int = 0
    @Volatile private var head = Node(null)
    @Volatile private var tail = Node(null)

    init {
        synchronized(this) {
            tail.prev = head
            head.next = tail
        }
    }

    @Synchronized override fun clear() {
        size = 0
        head.next = tail
        tail.prev = head
    }

    @Synchronized override fun addFirst(value: T) {
        val newNode = Node(value)

        val tmp = head.next
        tmp?.prev = newNode

        head.next = newNode
        newNode.prev = head
        newNode.next = tmp
        size += 1
    }

    @Synchronized override fun addLast(value: T) {
        val newNode = Node(value)
        val tmp = tail.prev
        tmp?.next = newNode

        tail.prev = newNode
        newNode.prev = tmp
        newNode.next = tail

        size += 1
    }

    @Synchronized override fun peekFirst(): T? {
        return head.next?.value
    }

    @Synchronized override fun peekLast(): T? {
        return tail.prev?.value
    }

    @Synchronized override fun pollFirst(): T? {
        if (isEmpty) return null

        val firstNode = head.next
        head.next = firstNode?.next
        firstNode?.next?.prev = head
        size -= 1
        return firstNode?.value
    }

    @Synchronized override fun pollLast(): T? {
        if (isEmpty) return null

        val lastNode = tail.prev
        tail.prev = lastNode?.prev
        lastNode?.prev?.next = tail
        size -= 1
        return lastNode?.value
    }

    @Synchronized override fun contains(value: T): Boolean {
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
        @Volatile var prev: Node? = null
        @Volatile var next: Node? = null
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

    // todo: Use Fine-Grained or Lazy synchronization
    //  i.e. lock only one current nodes not the whole list
    @Synchronized override fun equals(other: Any?): Boolean {
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
