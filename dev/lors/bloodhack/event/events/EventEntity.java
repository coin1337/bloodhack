package dev.lors.bloodhack.event.events;

import dev.lors.bloodhack.event.EventCancellable;
import net.minecraft.entity.Entity;

public class EventEntity extends EventCancellable {
   private final Entity entity;

   public EventEntity(Entity entity) {
      this.entity = entity;
   }

   public Entity get_entity() {
      return this.entity;
   }

   public static class WurstplusEventColision extends EventEntity {
      private double x;
      private double y;
      private double z;

      public WurstplusEventColision(Entity entity, double x, double y, double z) {
         super(entity);
         this.x = x;
         this.y = y;
         this.z = z;
      }

      public double get_x() {
         return this.x;
      }

      public void set_x(double x) {
         this.x = x;
      }

      public double get_y() {
         return this.y;
      }

      public void set_y(double y) {
         this.y = y;
      }

      public double get_z() {
         return this.z;
      }

      public void set_z(double x) {
         this.z = this.z;
      }
   }
}
