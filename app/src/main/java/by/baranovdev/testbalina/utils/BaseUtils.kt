package by.baranovdev.testbalina.utils

object BaseUtils {
    fun Boolean.ifTrue(l: () -> Unit): Boolean {
        if (this) {
            l.invoke()
        }
        return this
    }

    fun Boolean.ifFalse(l: () -> Unit): Boolean {
        if (this) {
            l.invoke()
        }
        return this
    }

    fun <T> T?.doIfNotNull(l: () -> T): T? {
        if (this == null) {
            l.invoke()
        }
        return this
    }

    fun <T> T?.isNotNull(): Boolean = this != null

}