package com.example.steplistdemo.business

import com.example.steplistdemo.dto.OrderRequest
import com.example.steplistdemo.model.BaseResponse
import com.example.steplistdemo.model.ResponseCode

/**
 * First step in the order-processing pipeline.
 *
 * Validates required fields before any business processing happens.
 * Registered in application.properties under the name "validateRequest".
 */
class ValidateRequestBusiness : ExecuteBusiness {

    /**
     * Checks that the request is the correct type and contains valid order data.
     *
     * Returns [ResponseCode.FAIL_CODE] immediately if validation fails so the controller
     * can stop the step list and return the error to the client.
     */
    override fun execute(
        request: Any?,
        response: HashMap<String, BaseResponse>?,
    ): BaseResponse {
        println("In ValidateRequestBusiness")

        // Safe cast: returns failure response if request is not an OrderRequest
        val orderRequest = request as? OrderRequest
            ?: return BaseResponse(ResponseCode.FAIL_CODE, "Invalid request type")

        if (orderRequest.orderId.isNullOrBlank()) {
            return BaseResponse(ResponseCode.FAIL_CODE, "Order ID is required")
        }
        if (orderRequest.amount == null || orderRequest.amount <= 0) {
            return BaseResponse(ResponseCode.FAIL_CODE, "Valid amount is required")
        }
        if (orderRequest.customerName.isNullOrBlank()) {
            return BaseResponse(ResponseCode.FAIL_CODE, "Customer name is required")
        }

        return BaseResponse(
            responseCode = ResponseCode.SUCCESS_CODE,
            responseMessage = ResponseCode.SUCCESS_CODE_DESC,
            data = mapOf("validated" to true),
        )
    }
}
