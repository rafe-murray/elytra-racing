package ca.rafemurray.item

import ca.rafemurray.block.entity.FinishBlockEntity
import ca.rafemurray.block.entity.StartBlockEntity
import ca.rafemurray.course.Course
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class CourseMarkerItem(settings: Properties) : Item(settings) {

    private val course: Course = Course()

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val pos = context.clickedPos

        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is StartBlockEntity) {
            course.setStart(pos)
            blockEntity.setCourse(course)
            return InteractionResult.SUCCESS
        }
        if (blockEntity is FinishBlockEntity) {
            course.setFinish(pos)
            blockEntity.setCourse(course)
            return InteractionResult.SUCCESS
        }
        return InteractionResult.PASS
    }
}
