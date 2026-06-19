package ca.rafemurray

import ca.rafemurray.block.ModBlocks
import ca.rafemurray.block.entity.ModBlockEntities
import ca.rafemurray.item.ModItems
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ElytraRacing : ModInitializer {
    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        ModItems.initialize()
        ModBlocks.initialize()
        ModBlockEntities.initialize()
        GlobalSavedData.initialize()
        LOGGER.info("Initialized Elytra Racing")
    }

    companion object {
        const val MOD_ID: String = "elytra-racing"

        // This logger is used to write text to the console and the log file.
        // It is considered best practice to use your mod id as the logger's name.
        // That way, it's clear which mod wrote info, warnings, and errors.
		@JvmField
		val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)
    }
}