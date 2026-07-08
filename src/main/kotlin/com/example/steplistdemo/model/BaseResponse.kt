package com.example.steplistdemo.model

/**
 * Standard API response wrapper used by all business steps and controllers.
 *
 * [var] fields are mutable so each step can update response values during pipeline execution.
 */
data class BaseResponse(
    var responseCode: String? = null,
    var responseMessage: String? = null,
    var data: Any? = null,
)
