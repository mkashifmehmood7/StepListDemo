package com.example.steplistdemo.utilities

import com.example.steplistdemo.business.ExecuteBusiness

/**
 * In-memory registry for configured business step lists.
 *
 * [object] creates a Kotlin singleton: one shared instance for the whole application.
 * [PostBootConfigurations] populates these maps at startup; controllers read from them at runtime.
 */
object StepUtils {

    /**
     * Ordered map of step name -> business implementation for order processing.
     *
     * [linkedMapOf] preserves insertion order, which matches the order defined
     * in the processOrderSteps property in application.properties.
     */
    val processOrderStepList: MutableMap<String, ExecuteBusiness> = linkedMapOf()
}
