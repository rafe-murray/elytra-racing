package ca.rafemurray.course

import ca.rafemurray.ElytraRacing
import com.mojang.serialization.Codec
import net.minecraft.core.UUIDUtil
import net.minecraft.resources.Identifier
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.saveddata.SavedData
import net.minecraft.world.level.saveddata.SavedDataType
import java.util.*
import java.util.function.Function
import java.util.function.Supplier

class Courses : SavedData {
    private val courseMap: MutableMap<UUID?, Course>
    private val map: Map<UUID?, Course>
        get() {
            return courseMap
        }

    private constructor() {
        courseMap = HashMap()
        instance = this
    }

    private constructor(map: Map<UUID?, Course>) {
        courseMap = HashMap(map)
        instance = this
    }

    fun getCourse(uuid: UUID?): Course? {
        return courseMap.get(uuid)
    }

    fun addOrUpdateCourse(course: Course) {
        courseMap[course.id] = course
        setDirty()
    }

    companion object {
        @JvmStatic
        lateinit var instance: Courses
            private set

        // NOTE: we need to use UUIDUtil.STRING_CODEC so that the keys are serialized as strings so that saving to NBT works properly
        val CODEC: Codec<Courses> =
            Codec.unboundedMap(UUIDUtil.STRING_CODEC, Course.CODEC).xmap(
                { map: Map<UUID?, Course> -> Courses(map) },
                { obj: Courses -> obj.map }
            )

        val IDENTIFIER: Identifier = Identifier.fromNamespaceAndPath(ElytraRacing.MOD_ID, "courses")


        private val TYPE: SavedDataType<Courses> = SavedDataType(
            "courses_saved_data",
            { Courses() },
            CODEC,
            null
        )

        @JvmStatic
        fun create(server: MinecraftServer): Courses {
            instance = server.overworld().dataStorage.computeIfAbsent(TYPE)
            return instance
        }
    }
}
