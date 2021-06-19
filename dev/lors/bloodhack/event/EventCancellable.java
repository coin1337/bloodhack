package dev.lors.bloodhack.event;

import me.zero.alpine.type.Cancellable;
import net.minecraft.client.Minecraft;

public class EventCancellable extends Cancellable {
   private final EventCancellable.Era era_switch;
   private final float partial_ticks;

   public EventCancellable() {
      this.era_switch = EventCancellable.Era.EVENT_PRE;
      this.partial_ticks = Minecraft.func_71410_x().func_184121_ak();
   }

   public EventCancellable.Era get_era() {
      return this.era_switch;
   }

   public float get_partial_ticks() {
      return this.partial_ticks;
   }

   public static enum Era {
      EVENT_PRE,
      EVENT_PERI,
      EVENT_POST;
   }
}
