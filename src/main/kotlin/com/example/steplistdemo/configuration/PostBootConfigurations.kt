package com.example.steplistdemo.configuration

import com.example.steplistdemo.business.ExecuteBusiness
import com.example.steplistdemo.utilities.StepUtils
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.ObjectMapper
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

/**
 * Loads business step definitions from application.properties after Spring Boot starts.
 *
 * This is the bridge between configuration and runtime execution:
 * property JSON -> reflection -> [StepUtils] step maps used by controllers.
 */
@Component // Registers this class as a Spring-managed bean
class PostBootConfigurations(
    // Environment gives access to values from application.properties and other config sources
    private val environment: Environment,
) {

    /**
     * Runs once the application is fully started and ready to serve requests.
     *
     * [@EventListener] listens for [ApplicationReadyEvent], which fires after the context
     * is initialized and the app is ready (safer than loading steps too early).
     */
    @EventListener(ApplicationReadyEvent::class)
    fun prepareStepsList() {
        // Each call links one properties key to one step-list map in StepUtils
        registerSteps("processOrderSteps", StepUtils.processOrderStepList)
    }

    /**
     * Parses a JSON step list from a property and registers each business class.
     *
     * Expected property format:
     * [{"name":"stepName","class":"fully.qualified.ClassName"}, ...]
     *
     * @param propertyName Key in application.properties (for example "processOrderSteps")
     * @param stepList     Mutable map that will hold step-name -> business instance
     */
    private fun registerSteps(
        propertyName: String,
        stepList: MutableMap<String, ExecuteBusiness>,
    ) {
        val propertyValue = environment.getProperty(propertyName)
        if (propertyValue.isNullOrBlank()) {
            println("Property '$propertyName' is missing or empty. Skipping step loading.")
            return
        }

        try {
            val objectMapper = ObjectMapper()
            // Convert JSON array from properties into a list of maps
            val steps: List<Map<String, String>> =
                objectMapper.readValue(
                    propertyValue,
                    object : TypeReference<List<Map<String, String>>>() {},
                )

            for (step in steps) {
                val name = step["name"]
                val className = step["class"]
                if (name == null || className == null) {
                    println("Skipping invalid step entry in '$propertyName': $step")
                    continue
                }
                if (stepList.containsKey(name)) {
                    println("Step '$name' is already registered. Skipping.")
                    continue
                }

                // Load class by name and create a new instance (no Spring DI on step classes here)
                val clazz = Class.forName(className)
                val instance = clazz.getDeclaredConstructor().newInstance() as ExecuteBusiness
                stepList[name] = instance
                println("Registered business step: $name -> $className")
            }
        } catch (e: Exception) {
            println("Error parsing or registering steps for '$propertyName': ${e.message}")
        }
    }
}
