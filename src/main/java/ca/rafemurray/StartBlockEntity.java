package ca.rafemurray;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class StartBlockEntity extends BlockEntity {
    private UUID courseId;
    private Course course;

    public StartBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.START_BLOCK_ENTITY, pos, state);
        this.courseId = null;
        this.course = null;
    }

    public void setCourse(Course course) {
        this.courseId = course.getId();
        this.course = course;
        markDirty();
    }
    public void onSteppedOn(Entity entity) {
        if (entity instanceof PlayerEntity && course != null && course.isValid()) {
            ElytraRacingPlayerEntity player = (ElytraRacingPlayerEntity) entity;
            player.startCourse(course);
        }
    }

//    @Override
//    protected void readNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registries) {
//
//    }
}
