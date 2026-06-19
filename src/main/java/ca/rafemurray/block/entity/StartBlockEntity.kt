package ca.rafemurray.block.entity

import ca.rafemurray.ElytraRacingPlayer
import ca.rafemurray.course.Course
import ca.rafemurray.course.Courses.Companion.instance
import net.minecraft.core.BlockPos
import net.minecraft.core.UUIDUtil
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.ValueInput
import net.minecraft.world.level.storage.ValueOutput
import java.util.*

class StartBlockEntity(pos: BlockPos, state: BlockState) :
    BlockEntity(ModBlockEntities.START_BLOCK_ENTITY, pos, state) {
    private var courseId: UUID? = null

    fun setCourse(course: Course) {
        this.courseId = course.id
        setChanged()
    }

    fun stepOn(entity: Entity) {
        if (entity is ElytraRacingPlayer && courseId != null) {
            val course = instance.getCourse(courseId)
            if (course == null || !course.isValid || course === entity.course) {
                return
            }
            entity.startCourse(course)
        }
    }

    override fun loadAdditional(input: ValueInput) {
        val optionalCourseId = input.read(
            Course.COURSE_ID_IDENTIFIER.toString(),
            UUIDUtil.CODEC
        )
        courseId = optionalCourseId.orElse(null)
        super.loadAdditional(input)
    }


    override fun saveAdditional(out: ValueOutput) {
        out.storeNullable(
            Course.COURSE_ID_IDENTIFIER.toString(),
            UUIDUtil.CODEC,
            courseId
        )
        super.saveAdditional(out)
    }
}
