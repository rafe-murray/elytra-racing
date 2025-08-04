package ca.rafemurray;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class StartBlockEntity extends BlockEntity {
    private Course course;
    public StartBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.START_BLOCK_ENTITY, pos, state);
        this.course = null;
    }

    public void setCourse(Course course) {
        this.course = course;
        markDirty();
    }
    public void onSteppedOn(Entity entity) {
        if (entity instanceof PlayerEntity && course != null && course.isValid()) {
            ElytraRacingPlayerEntity player = (ElytraRacingPlayerEntity) entity;
            player.startCourse(course);
        }
    }
}
