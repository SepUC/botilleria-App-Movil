package com.example.botilleriaapp

import org.junit.jupiter.api.Test
import io.kotest.matchers.shouldBe

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `addition is correct`() {
        (2 + 2) shouldBe 4
    }
    
    @Test
    fun `subtraction is correct`() {
        (5 - 3) shouldBe 2
    }
    
    @Test
    fun `multiplication is correct`() {
        (3 * 4) shouldBe 12
    }
}
