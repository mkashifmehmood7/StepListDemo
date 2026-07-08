package com.example.steplistdemo.dto

/**
 * Data Transfer Object (DTO) for incoming order API requests.
 *
 * [data class] auto-generates equals(), hashCode(), toString(), and copy().
 * Nullable fields allow Jackson/Spring to deserialize partial JSON payloads.
 */
data class OrderRequest(
    val orderId: String? = null,
    val amount: Double? = null,
    val customerName: String? = null,
)
