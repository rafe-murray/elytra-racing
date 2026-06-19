package ca.rafemurray.block

import ca.rafemurray.block.entity.StartBlockEntity
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class StartBlock(settings: Properties) : BaseEntityBlock(settings) {
    override fun codec(): MapCodec<out BaseEntityBlock> {
        return simpleCodec { settings: Properties -> StartBlock(settings) }
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return StartBlockEntity(pos, state)
    }

    // TODO: consider two cases: the start block is broken while a player is in the course, and the player steps on another start block while in a course
    // TODO: add an item to register blocks with a particular course - Idea: maybe have a limited number of courses and use the wool colours
    override fun stepOn(level: Level, pos: BlockPos, state: BlockState, entity: Entity) {
        val blockEntity = level.getBlockEntity(pos);
        if ((blockEntity is StartBlockEntity)) {
            blockEntity.stepOn(entity)
        }
        super.stepOn(level, pos, state, entity)
    }
}
