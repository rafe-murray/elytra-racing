package ca.rafemurray;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {
    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean registerItem) {
        RegistryKey<Block> blockKey = getBlockKey(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        if (registerItem) {
            RegistryKey<Item> itemKey = getItemKey(name);
            BlockItem item = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, item);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Item> getItemKey(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ElytraRacing.MOD_ID, name));
    }

    private static RegistryKey<Block> getBlockKey(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ElytraRacing.MOD_ID, name));
    }

    public static final Block BOOSTER = register("booster", BoosterBlock::new, AbstractBlock.Settings.create().noCollision(),true);
    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
            itemGroup.add(ModBlocks.BOOSTER.asItem());
        });
    }


}
