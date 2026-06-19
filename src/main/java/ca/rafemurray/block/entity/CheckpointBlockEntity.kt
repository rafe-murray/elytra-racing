package ca.rafemurray.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class CheckpointBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(ModBlockEntities.CHECKPOINT_BLOCK_ENTITY,
    blockPos, blockState
) {
    val courseId : UUID? = null
}