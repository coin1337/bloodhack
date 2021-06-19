package dev.lors.bloodhack.event.events;

import dev.lors.bloodhack.event.Event;
import net.minecraft.entity.Entity;

public class EventEntityRemoved extends Event {
   private final Entity entity;

   public EventEntityRemoved(Entity entity) {
      this.entity = entity;
   }

   public Entity get_entity() {
      return this.entity;
   }
}
