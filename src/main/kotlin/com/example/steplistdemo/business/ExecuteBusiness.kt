package com.example.steplistdemo.business

import com.example.steplistdemo.model.BaseResponse

/**
 * Contract for every business step in the step-list pipeline.
 *
 * Each implementation represents one unit of work (validation, processing, response building, etc.).
 * Steps are registered at startup from [application.properties] and executed in order by the controller.
 */
interface ExecuteBusiness {

    /**
     * Runs a single business step.
     *
     * @param request  Incoming API request object (cast to the expected DTO inside each step)
     * @param response Map of step-name -> response from previously executed steps in the same request
     * @return [BaseResponse] with a response code; non-success codes stop the remaining steps
     */
    fun execute(request: Any?, response: HashMap<String, BaseResponse>?): BaseResponse
}
