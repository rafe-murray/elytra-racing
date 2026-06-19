package ca.rafemurray.block

import ca.rafemurray.ElytraRacing
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import java.util.function.Function

object ModBlocks {
    fun register(
        name: String,
        blockFactory: Function<BlockBehaviour.Properties, Block>,
        settings: BlockBehaviour.Properties,
        shouldRegisterItem: Boolean
    ): Block {
        // Create a registry key for the block
        val blockKey = keyOfBlock(name)
        // Create the block instance
        val block = blockFactory.apply(settings.setId(blockKey))

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            val itemKey = keyOfItem(name)

            val blockItem = BlockItem(block, Item.Properties().setId(itemKey).useBlockDescriptionPrefix())
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem)
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block)
    }

    private fun keyOfBlock(name: String): ResourceKey<Block> {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ElytraRacing.MOD_ID, name))
    }

    private fun keyOfItem(name: String): ResourceKey<Item> {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ElytraRacing.MOD_ID, name))
    }

    val BOOSTER: Block = register(
        "booster",
        { properties: BlockBehaviour.Properties -> BoosterBlock(properties) },
        BlockBehaviour.Properties.of().noCollision(),
        true
    )

    val START: Block = register(
        "start_block",
        { settings: BlockBehaviour.Properties ->
            StartBlock(settings)
        }, BlockBehaviour.Properties.of(), true
    )

    val FINISH: Block = register(
        "finish_block",
        { settings: BlockBehaviour.Properties -> FinishBlock(settings) }, BlockBehaviour.Properties.of(), true
    )

    val CHECKPOINT: Block = register(
        "checkpoint_block",
        { settings: BlockBehaviour.Properties -> CheckpointBlock(settings) }, BlockBehaviour.Properties.of(), true
    )

    fun initialize() {}
}
