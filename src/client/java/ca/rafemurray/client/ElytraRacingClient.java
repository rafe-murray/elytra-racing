package ca.rafemurray.client;

import ca.rafemurray.block.ModBlocks;
import ca.rafemurray.client.hud.HudRenderingEntrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class ElytraRacingClient implements ClientModInitializer {
	// TODO: add a timer to the screen for player whose course has started (might need a boolean field for if the race is active)
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BlockRenderLayerMap.putBlock(ModBlocks.BOOSTER, ChunkSectionLayer.TRANSLUCENT);
		HudRenderingEntrypoint.initialize();

		// TODO: implement a text rendering widget using bufferbuilder, upload (at
		// least) one font file, and use that to render the timer on the HUD
	}
}