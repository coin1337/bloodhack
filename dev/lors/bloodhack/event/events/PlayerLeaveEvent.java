package dev.lors.bloodhack.event.events;

import dev.lors.bloodhack.event.Event;

public class PlayerLeaveEvent extends Event {
   private final String name;

   public PlayerLeaveEvent(String n) {
      this.name = n;
   }

   public String getName() {
      return this.name;
   }
}
