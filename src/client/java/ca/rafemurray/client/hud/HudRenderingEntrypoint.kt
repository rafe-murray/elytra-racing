package ca.rafemurray.client.hud

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements
import net.minecraft.client.DeltaTracker
import net.minecraft.client.gui.GuiGraphics

object HudRenderingEntrypoint {
    fun initialize() {
        HudElementRegistry.attachElementAfter(
            VanillaHudElements.MISC_OVERLAYS, TimerWidget.IDENTIFIER
        ) { guiGraphics: GuiGraphics, deltaTracker: DeltaTracker -> TimerWidget.render(guiGraphics, deltaTracker) }
    }
}
