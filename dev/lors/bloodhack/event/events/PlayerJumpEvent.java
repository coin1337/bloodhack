package dev.lors.bloodhack.event.events;

import dev.lors.bloodhack.event.Event;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerJumpEvent extends Event {
   EntityPlayer player;

   public PlayerJumpEvent(EntityPlayer player) {
      this.player = player;
   }
}
