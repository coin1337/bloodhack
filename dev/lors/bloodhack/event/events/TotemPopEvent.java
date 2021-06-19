package dev.lors.bloodhack.event.events;

import dev.lors.bloodhack.event.Event;
import net.minecraft.entity.Entity;

public class TotemPopEvent extends Event {
   private final Entity entity;

   public TotemPopEvent(Entity entity) {
      this.entity = entity;
   }

   public Entity getEntity() {
      return this.entity;
   }
}
