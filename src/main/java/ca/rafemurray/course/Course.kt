package ca.rafemurray.course

import ca.rafemurray.ElytraRacing
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.UUIDUtil
import net.minecraft.resources.Identifier
import net.minecraft.world.level.block.state.BlockState
import java.util.*

/**
 * This class represents an instance of a racecourse
 */
class Course // Only used for serialization. We don't need to mark as dirty since the data is coming straight from storage
private constructor(
    @JvmField val id: UUID,
    var startPos: BlockPos?,
    var finishPos: BlockPos?,
    private val checkPoints: MutableList<BlockState>
) {
    private fun setDirty() {
        CourseChangedCallback.EVENT.invoker().onCourseChanged(this)
    }

    constructor() : this(UUID.randomUUID(), null, null, ArrayList<BlockState>()) {
        setDirty()
    }

    val immutableCheckPoints: List<BlockState>
        get() = java.util.List.copyOf(checkPoints)

    fun setStart(startPos: BlockPos?) {
        this.startPos = startPos
        setDirty()
    }

    fun setFinish(finishPos: BlockPos?) {
        this.finishPos = finishPos
        setDirty()
    }

    // TODO: check that these are of type Checkpoint
    fun addCheckpoint(checkpoint: BlockState) {
        checkPoints.add(checkpoint)
        setDirty()
    }

    val isValid: Boolean
        get() = (finishPos != null && startPos != null)

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Course

        return id == other.id
    }

    companion object {
        @JvmField
        val COURSE_ID_IDENTIFIER: Identifier = Identifier.fromNamespaceAndPath(
            ElytraRacing.MOD_ID, "course_id"
        )
        @JvmField
        val IDENTIFIER: Identifier = Identifier.fromNamespaceAndPath(ElytraRacing.MOD_ID, "course")

        @JvmField
        val CODEC: Codec<Course> = RecordCodecBuilder.create { instance: RecordCodecBuilder.Instance<Course> ->
            instance.group(
                UUIDUtil.CODEC.fieldOf("id").forGetter { obj: Course -> obj.id },
                BlockPos.CODEC.fieldOf("start").forGetter { obj: Course -> obj.startPos },
                BlockPos.CODEC.fieldOf("finish").forGetter { obj: Course -> obj.finishPos },
                BlockState.CODEC.listOf().fieldOf("checkPoints")
                    .forGetter { obj: Course -> obj.immutableCheckPoints }
            ).apply(
                instance
            ) { id: UUID, startPos: BlockPos?, finishPos: BlockPos?, checkPoints: MutableList<BlockState> ->
                Course(
                    id,
                    startPos,
                    finishPos,
                    checkPoints
                )
            }
        }
    }
}
