package ca.rafemurray.block.entity;

import ca.rafemurray.ElytraRacing;
import ca.rafemurray.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final BlockEntityType<FinishBlockEntity> FINISH_BLOCK_ENTITY =
            register("finish", FinishBlockEntity::new, ModBlocks.FINISH);
    public static final BlockEntityType<StartBlockEntity> START_BLOCK_ENTITY =
            register("start", StartBlockEntity::new, ModBlocks.START);
    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(ElytraRacing.MOD_ID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
    public static void initialize() {}
}
