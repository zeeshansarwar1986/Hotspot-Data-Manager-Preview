package com.example.hotspotdatamanager

import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun dataCalculation_isCorrect() {
        val mockDataUsedBytes = 1048576L // 1 MB
        val inMB = mockDataUsedBytes / (1024 * 1024)
        assertEquals(1L, inMB)
    }
}
