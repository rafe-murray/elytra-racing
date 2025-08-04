package ca.rafemurray;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StartBlock extends BlockWithEntity {
    protected StartBlock(Settings settings) {
        super(settings);
    }

    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(StartBlock::new);
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StartBlockEntity(pos, state);
    }
    // TODO: consider two cases: the start block is broken while a player is in the course, and the player steps on another start block while in a course
    // TODO: add an item to register blocks with a particular course - Idea: maybe have a limited number of courses and use the wool colours

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if ((world.getBlockEntity(pos) instanceof StartBlockEntity startBlockEntity)) {
            startBlockEntity.onSteppedOn(entity);
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}
