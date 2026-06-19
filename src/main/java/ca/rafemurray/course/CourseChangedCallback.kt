package ca.rafemurray.course

import ca.rafemurray.course.CourseChangedCallback
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.world.InteractionResult
import java.util.function.Function

fun interface CourseChangedCallback {
    fun onCourseChanged(course: Course?): InteractionResult?

    companion object {
        @JvmField
        val EVENT: Event<CourseChangedCallback> = EventFactory.createArrayBacked(
            CourseChangedCallback::class.java
        ) { listeners ->
            CourseChangedCallback { course: Course? ->
                for (listener: CourseChangedCallback? in listeners) {
                    val result: InteractionResult? = listener?.onCourseChanged(course)

                    if (result !== InteractionResult.PASS) {
                        return@CourseChangedCallback result
                    }
                }
                InteractionResult.PASS
            }
        }

    }
}
