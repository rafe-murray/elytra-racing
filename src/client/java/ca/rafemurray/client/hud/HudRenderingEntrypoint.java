package ca.rafemurray.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

public class HudRenderingEntrypoint {
    public static void initialize(){
        HudElementRegistry.attachElementAfter(VanillaHudElements.MISC_OVERLAYS, TimerWidget.IDENTIFIER, TimerWidget::render);
    }
}
