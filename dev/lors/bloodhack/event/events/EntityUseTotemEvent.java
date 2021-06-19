package dev.lors.bloodhack.event.events;

import dev.lors.bloodhack.event.Event;
import net.minecraft.entity.Entity;

public class EntityUseTotemEvent extends Event {
   private final Entity entity;

   public EntityUseTotemEvent(Entity entity) {
      this.entity = entity;
   }

   public Entity getEntity() {
      return this.entity;
   }
}
