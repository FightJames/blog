package com.techapp.james.stickyrecyclerview

import com.techapp.james.stickyrecyclerview.dataStructure.ConcreteData
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit concreteData, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testData() {
        //concreteData key set
        var data = ConcreteData()
        var title = "abcdefg"
        var item = "123456"
        title.forEach { t -> item.forEach { i -> data.insertOrUpdate(t.toString(), i.toString()) } }
        assertEquals("b", data.getItem(7))
    }
}
