package ca.rafemurray.item

import ca.rafemurray.ElytraRacing
import ca.rafemurray.block.ModBlocks
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import java.util.function.Function

object ModItems {
    // Make and register a custom item group for the mod (creative inventory tab)
    private fun <T : Item?> register(name: String, itemFactory: Function<Item.Properties, T>, settings: Item.Properties): T {
        // Create the item key.
        val itemKey = ResourceKey.create(
            Registries.ITEM, Identifier.fromNamespaceAndPath(
                ElytraRacing.MOD_ID, name
            )
        )

        // Create the item instance.
        val item = itemFactory.apply(settings.setId(itemKey))

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item)

        return item
    }

    private val CUSTOM_CREATIVE_TAB_KEY: ResourceKey<CreativeModeTab> = ResourceKey.create(
        BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(ElytraRacing.MOD_ID, "creative_tab")
    )
    private val CUSTOM_CREATIVE_TAB: CreativeModeTab = FabricItemGroup.builder()
        .icon { ItemStack(Items.ELYTRA) }
        .title(Component.translatable("itemGroup.elytra-racing"))
        .displayItems { params: ItemDisplayParameters?, output: CreativeModeTab.Output ->
            output.accept(Items.ELYTRA)
            output.accept(COURSE_MARKER)
            output.accept(ModBlocks.BOOSTER)
            output.accept(ModBlocks.START)
            output.accept(ModBlocks.FINISH)
        }
        .build()

    private val COURSE_MARKER: Item = register(
        "course_marker",
        { settings: Item.Properties -> CourseMarkerItem(settings) }, Item.Properties()
    )

    fun initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, CUSTOM_CREATIVE_TAB)
    }
}
