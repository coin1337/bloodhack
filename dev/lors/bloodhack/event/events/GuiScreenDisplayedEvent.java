package dev.lors.bloodhack.event.events;

import dev.lors.bloodhack.event.Event;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenDisplayedEvent extends Event {
   private final GuiScreen guiScreen;

   public GuiScreenDisplayedEvent(GuiScreen screen) {
      this.guiScreen = screen;
   }

   public GuiScreen getScreen() {
      return this.guiScreen;
   }
}
