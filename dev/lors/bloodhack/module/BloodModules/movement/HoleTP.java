package dev.lors.bloodhack.module.BloodModules.movement;

import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;

public class HoleTP extends Module {
   public HoleTP() {
      super("HoleTP", Category.MOVEMENT);
   }

   public void onUpdate() {
      if (this.mc.field_71439_g.field_70122_E) {
         --this.mc.field_71439_g.field_70181_x;
      }

   }
}
