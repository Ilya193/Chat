package ru.kraz.chat.domain

interface EventWrapper<T> {
    fun getContentOrNot(callback: (T) -> Unit)

    open class Single<T>(protected var data: T) : EventWrapper<T> {
        protected var protectionEnabled = false

        override fun getContentOrNot(callback: (T) -> Unit) {
            if (!protectionEnabled) {
                protectionEnabled = true
                callback(data)
            }
        }
    }

    open class State<T>(data: T) : Single<T>(data) {
        fun setState(value: Boolean) {
            protectionEnabled = value
        }
    }

    class Change<T>(data: T) : State<T>(data) {
        fun setValue(value: T) {
            setState(false)
            data = value
        }
    }
}