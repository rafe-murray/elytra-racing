package ca.rafemurray.client.hud;

import ca.rafemurray.ElytraRacing;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;

public class TimerWidget {
    public static final Identifier IDENTIFIER = Identifier.fromNamespaceAndPath(ElytraRacing.MOD_ID, "timer");

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        guiGraphics.textRenderer().accept(
            0,
            0,
            FormattedCharSequence.forward(
                "test",
                Style.EMPTY.withColor(TextColor.fromRgb(0xff0000))
            )
        );
    }
}
