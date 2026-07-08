package com.example.steplistdemo

import com.example.steplistdemo.dto.OrderRequest
import com.example.steplistdemo.model.ResponseCode
import com.example.steplistdemo.utilities.StepUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

/**
 * Integration tests for the step-list demo application.
 *
 * [@SpringBootTest] starts the full Spring application context for testing.
 */
@SpringBootTest
class StepListDemoApplicationTests {

    /**
     * Verifies that Spring context starts without errors (beans, config, wiring).
     *
     * [@Test] marks this method as a JUnit test case.
     */
    @Test
    fun contextLoads() {
    }

    /**
     * Verifies [PostBootConfigurations] loaded all three steps from application.properties.
     */
    @Test
    fun processOrderStepsAreLoadedFromProperties() {
        assertEquals(3, StepUtils.processOrderStepList.size)
        assertTrue(StepUtils.processOrderStepList.containsKey("validateRequest"))
        assertTrue(StepUtils.processOrderStepList.containsKey("processOrder"))
        assertTrue(StepUtils.processOrderStepList.containsKey("buildResponse"))
    }

    /**
     * Verifies validation step returns failure for invalid input.
     */
    @Test
    fun stepListStopsOnValidationFailure() {
        val stepResponses = hashMapOf<String, com.example.steplistdemo.model.BaseResponse>()
        val response = StepUtils.processOrderStepList["validateRequest"]!!
            .execute(OrderRequest(orderId = "", amount = 10.0, customerName = "Test"), stepResponses)

        assertEquals(ResponseCode.FAIL_CODE, response.responseCode)
    }
}
