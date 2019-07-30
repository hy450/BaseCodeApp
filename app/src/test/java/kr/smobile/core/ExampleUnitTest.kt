package kr.smobile.core

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        assertEquals(true, isNeedRefresh(1564486666L,1564487280L) )
    }

    private fun isNeedRefresh(timems : Long, comp: Long) : Boolean {
        val infoDate = Calendar.getInstance()
        infoDate.timeInMillis = timems

        //3시간이 지난 데이터의 경우 다시 가져온다.
        val boundary = Calendar.getInstance()
        boundary.timeInMillis = comp
        boundary.add(Calendar.HOUR_OF_DAY,-3)

        println(infoDate.time)
        println(boundary.time)

        return infoDate.before(boundary)
    }
}
