package ca.rafemurray;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.function.Function;

public class ModItems {
    // Make and register a custom item group for the mod (creative inventory tab)
    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(ElytraRacing.MOD_ID, "item_group"));
    public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.ELYTRA))
            .displayName(Text.translatable("itemGroup.elytra-racing"))
            .build();
    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ElytraRacing.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static final Item TEST = register("test", Item::new, new Item.Settings());
    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY)
                .register((itemGroup) -> {
                    itemGroup.add(ModItems.TEST);
                    itemGroup.add(Items.ELYTRA);
                });
    }
}
