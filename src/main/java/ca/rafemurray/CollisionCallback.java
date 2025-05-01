package ca.rafemurray;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface CollisionCallback {
    ActionResult interact(PlayerEntity player, Block block);
    Event<CollisionCallback> EVENT = EventFactory.createArrayBacked(CollisionCallback.class,
            (listeners) -> (player, block) -> {
                for (CollisionCallback listener : listeners) {
                    ActionResult result = listener.interact(player, block);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });
}
