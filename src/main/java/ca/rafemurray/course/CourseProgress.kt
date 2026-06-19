package ca.rafemurray.course

import ca.rafemurray.DurationUtils
import ca.rafemurray.ElytraRacing
import com.mojang.serialization.Codec
import net.minecraft.resources.Identifier
import java.time.Duration
import java.time.Instant
import java.util.function.Function

class CourseProgress {
    constructor() {
        savedProgress = Duration.ZERO
        startTime = Instant.now()
    }

    // Amount of time spent until this was last saved/loaded from disk
    private var savedProgress: Duration

    // Time the player started the course. If we are resuming from a save this is when the Player entity was loaded
    private var startTime: Instant?

    fun pause() {
        checkNotNull(startTime) { "Course progress has not started" }
        savedProgress = savedProgress.plus(Duration.between(startTime, Instant.now()))
        startTime = null
    }

    fun resume() {
        check(startTime == null) { "Course progress has not been paused." }
        startTime = Instant.now()
    }

    val elapsedTime: Duration
        get() {
            if (startTime == null) {
                return savedProgress
            }
            return savedProgress.plus(Duration.between(startTime, Instant.now()))
        }

    private constructor(savedProgress: Duration) {
        this.savedProgress = savedProgress
        this.startTime = Instant.now()
    }

    companion object {
        @JvmField
        val IDENTIFIER: Identifier = Identifier.fromNamespaceAndPath(ElytraRacing.MOD_ID, "course_progress")

        @JvmField
        val CODEC: Codec<CourseProgress> = DurationUtils.CODEC.xmap(
            Function { savedProgress: Duration -> CourseProgress(savedProgress) },
            Function { obj: CourseProgress -> obj.elapsedTime }
        )
    }
}
