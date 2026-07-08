package com.example.steplistdemo.business

import com.example.steplistdemo.dto.OrderRequest
import com.example.steplistdemo.model.BaseResponse
import com.example.steplistdemo.model.ResponseCode

/**
 * Final step in the order-processing pipeline.
 *
 * Builds the API response using data from earlier steps.
 * Registered in application.properties under the name "buildResponse".
 */
class BuildResponseBusiness : ExecuteBusiness {

    /**
     * Composes the final response for the client.
     *
     * Reads the output of the "processOrder" step from [response] to include
     * processing results in the final payload.
     */
    override fun execute(
        request: Any?,
        response: HashMap<String, BaseResponse>?,
    ): BaseResponse {
        println("In BuildResponseBusiness")

        val orderRequest = request as OrderRequest
        // Look up the result of a previous step by the step name defined in application.properties
        val processStep = response?.get("processOrder")

        return BaseResponse(
            responseCode = ResponseCode.SUCCESS_CODE,
            responseMessage = "Order completed successfully",
            data = mapOf(
                "orderId" to orderRequest.orderId,
                "customerName" to orderRequest.customerName,
                "amount" to orderRequest.amount,
                "processingResult" to processStep?.data,
            ),
        )
    }
}
