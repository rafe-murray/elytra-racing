package ca.rafemurray;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FinishBlock extends BlockWithEntity {
    protected FinishBlock(Settings settings) {
        super(settings);
    }

    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(FinishBlock::new);
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FinishBlockEntity(pos, state);
    }
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if ((world.getBlockEntity(pos) instanceof FinishBlockEntity finishBlockEntity)) {
            finishBlockEntity.onSteppedOn(entity);
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}
