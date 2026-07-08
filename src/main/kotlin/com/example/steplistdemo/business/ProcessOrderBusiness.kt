package com.example.steplistdemo.business

import com.example.steplistdemo.dto.OrderRequest
import com.example.steplistdemo.model.BaseResponse
import com.example.steplistdemo.model.ResponseCode

/**
 * Second step in the order-processing pipeline.
 *
 * Simulates core order-processing logic after validation passes.
 * Registered in application.properties under the name "processOrder".
 */
class ProcessOrderBusiness : ExecuteBusiness {

    /**
     * Processes the order and returns intermediate processing data.
     *
     * In a real application this step would call services, databases, or external APIs.
     */
    override fun execute(
        request: Any?,
        response: HashMap<String, BaseResponse>?,
    ): BaseResponse {
        val orderRequest = request as OrderRequest

        println("In ProcessOrderBusiness")

        return BaseResponse(
            responseCode = ResponseCode.SUCCESS_CODE,
            responseMessage = ResponseCode.SUCCESS_CODE_DESC,
            data = mapOf(
                "orderId" to orderRequest.orderId,
                "status" to "PROCESSED",
                "amount" to orderRequest.amount,
                "customerName" to orderRequest.customerName,
            ),
        )
    }
}
