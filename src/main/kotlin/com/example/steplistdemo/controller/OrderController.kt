package com.example.steplistdemo.controller

import com.example.steplistdemo.dto.OrderRequest
import com.example.steplistdemo.model.BaseResponse
import com.example.steplistdemo.model.ResponseCode
import com.example.steplistdemo.utilities.StepUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST API layer for order processing.
 *
 * The controller does not contain business logic directly. It only orchestrates
 * the step list configured in application.properties.
 */
@RestController // Marks this class as a REST controller; return values are written to the HTTP response body
@RequestMapping("/api/orders") // Base URL path for all endpoints in this controller
class OrderController {

    /**
     * Processes an order by executing each business step in configured order.
     *
     * Flow:
     * 1. Read steps from [StepUtils.processOrderStepList]
     * 2. Execute each [ExecuteBusiness] step
     * 3. Stop and return if any step returns a non-success response code
     * 4. Return the final step response on success
     *
     * [@PostMapping] maps HTTP POST requests to this method.
     * [@RequestBody] deserializes JSON request body into [OrderRequest].
     */
    @PostMapping("/process")
    fun processOrder(@RequestBody request: OrderRequest): ResponseEntity<BaseResponse> {
        var baseResponse = BaseResponse()
        // Stores each step's output so later steps can read previous results
        val stepResponses: HashMap<String, BaseResponse> = hashMapOf()

        StepUtils.processOrderStepList.forEach { (stepName, business) ->
            baseResponse = business.execute(request, stepResponses)
            stepResponses[stepName] = baseResponse

            // Fail-fast: do not run remaining steps after a failed step
            if (baseResponse.responseCode != ResponseCode.SUCCESS_CODE) {
                return ResponseEntity.ok(baseResponse)
            }
        }

        // ResponseEntity wraps status + body; ok() returns HTTP 200
        return ResponseEntity.ok(baseResponse)
    }
}
