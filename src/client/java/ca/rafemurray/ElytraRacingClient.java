package ca.rafemurray;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ElytraRacingClient implements ClientModInitializer {
	// TODO: add a timer to the screen for player whose course has started (might need a boolean field for if the race is active)
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BOOSTER, RenderLayer.getTranslucent());
	}
}