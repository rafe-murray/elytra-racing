package ca.rafemurray.block

import ca.rafemurray.block.entity.FinishBlockEntity
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class FinishBlock (settings: Properties) : BaseEntityBlock(settings) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return FinishBlockEntity(pos, state)
    }

    override fun stepOn(level: Level, pos: BlockPos, state: BlockState, entity: Entity) {
        val blockEntity = level.getBlockEntity(pos);
        if ((blockEntity is FinishBlockEntity)) {
            blockEntity.stepOn(entity)
        }
        super.stepOn(level, pos, state, entity)
    }

    override fun codec(): MapCodec<out BaseEntityBlock> {
        return simpleCodec { settings: Properties -> FinishBlock(settings) }
    }
}
