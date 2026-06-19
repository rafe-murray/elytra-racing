package ca.rafemurray.block

import ca.rafemurray.block.entity.CheckpointBlockEntity
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class CheckpointBlock(settings: Properties) : BaseEntityBlock(settings) {
    override fun codec(): MapCodec<out BaseEntityBlock> {
        return simpleCodec { settings: Properties -> StartBlock(settings) }
    }

    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity {
        return CheckpointBlockEntity(blockPos, blockState)
    }

    companion object {
        val CHECKPOINT_BLOCK = ModBlocks.register(
            "checkpoint",
            { settings: Properties ->
                CheckpointBlock(settings)
            }, Properties.of(), true
        )
    }
}