package ca.rafemurray.block.entity

import ca.rafemurray.ElytraRacing
import ca.rafemurray.block.ModBlocks
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

object ModBlockEntities {
    val FINISH_BLOCK_ENTITY: BlockEntityType<FinishBlockEntity> = register(
        "finish",
        { pos: BlockPos, state: BlockState ->
            FinishBlockEntity(
                pos, state
            )
        }, ModBlocks.FINISH
    )
    val START_BLOCK_ENTITY: BlockEntityType<StartBlockEntity> = register(
        "start",
        { pos: BlockPos, state: BlockState ->
            StartBlockEntity(
                pos, state
            )
        }, ModBlocks.START
    )

    val CHECKPOINT_BLOCK_ENTITY: BlockEntityType<CheckpointBlockEntity> = register(
        "checkpoint",
        { pos: BlockPos, state: BlockState ->
            CheckpointBlockEntity(pos, state)
        }, ModBlocks.CHECKPOINT
    )

    private fun <T : BlockEntity> register(
        name: String,
        entityFactory: FabricBlockEntityTypeBuilder.Factory<out T>,
        vararg blocks: Block?
    ): BlockEntityType<T> {
        val id = Identifier.fromNamespaceAndPath(ElytraRacing.MOD_ID, name)
        return Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            id,
            FabricBlockEntityTypeBuilder.create(entityFactory, *blocks).build()
        )
    }

    fun initialize() {}
}
