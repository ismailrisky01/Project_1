package tech.jayamakmur.jmdlibrary

import org.junit.Test

import org.junit.Assert.*
import tech.jayamakmur.jmdlibrary.util.MyRecyclerAdapter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @MyRecyclerAdapter
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}