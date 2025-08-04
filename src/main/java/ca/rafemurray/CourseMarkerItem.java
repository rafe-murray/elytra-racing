package ca.rafemurray;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CourseMarkerItem extends Item {
    private final Course course;
    public CourseMarkerItem(Settings settings) {
        super(settings);
        this.course = new Course();
    }
    //TODO: update the course fields for the start and finish blocks

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        if (world.getBlockEntity(pos) instanceof StartBlockEntity startBlockEntity){
            course.setStart(startBlockEntity);
            startBlockEntity.setCourse(course);
            return ActionResult.SUCCESS;
        }
        if (world.getBlockEntity(pos) instanceof FinishBlockEntity finishBlockEntity) {
            course.setFinish(finishBlockEntity);
            finishBlockEntity.setCourse(course);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
