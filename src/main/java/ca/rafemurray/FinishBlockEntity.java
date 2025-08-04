package ca.rafemurray;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class FinishBlockEntity extends BlockEntity {
    private Course course;
    public FinishBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FINISH_BLOCK_ENTITY, pos, state);
        this.course = null;
    }
    // TODO: make this a blockEntity and register it

    public void setCourse(Course course) {
        this.course = course;
        markDirty();
    }
    public void onSteppedOn(Entity entity) {
        if (entity instanceof PlayerEntity && course != null && course.isValid()) {
            ElytraRacingPlayerEntity player = (ElytraRacingPlayerEntity) entity;
            if (course.equals(player.getCourse())) {
                player.finishCourse();
            }
        }
    }
}
