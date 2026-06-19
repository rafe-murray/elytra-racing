package ca.rafemurray.course

import ca.rafemurray.DurationUtils
import ca.rafemurray.ElytraRacing
import com.mojang.serialization.Codec
import net.minecraft.core.UUIDUtil
import net.minecraft.resources.Identifier
import java.time.Duration
import java.util.*
import java.util.function.Function

class CourseRecords {
    private val records: MutableMap<UUID, Duration>

    constructor() {
        this.records = HashMap()
    }

    fun putIfFaster(uuid: UUID, duration: Duration): Duration? {
        val result: Duration? = records.putIfAbsent(uuid, duration)
        if (result != null) {
            return result
        }

        // Time is faster than record
        if (duration.compareTo(records.get(uuid)) < 0) {
            return records.put(uuid, duration)
        }
        return null
    }

    private constructor(map: Map<UUID, Duration>) {
        this.records = HashMap(map)
    }

    private val map: Map<UUID, Duration>
        get() {
            return this.records
        }

    companion object {
        @JvmField
        val IDENTIFIER: Identifier = Identifier.fromNamespaceAndPath(ElytraRacing.MOD_ID, "course_records")

        @JvmField
        val CODEC: Codec<CourseRecords> = Codec.unboundedMap(UUIDUtil.CODEC, DurationUtils.CODEC).xmap(
            Function { map: Map<UUID, Duration> -> CourseRecords(map) },
            Function { obj: CourseRecords -> obj.map }
        )
    }
}
