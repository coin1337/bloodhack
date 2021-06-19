package dev.lors.bloodhack.module.BloodModules.movement;

import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;

public class Sprint extends Module {
   public Sprint() {
      super("Sprint", Category.MOVEMENT);
   }

   public void onUpdate() {
      try {
         if (this.mc.field_71474_y.field_74351_w.func_151470_d() && !this.mc.field_71439_g.field_70123_F && !this.mc.field_71439_g.func_70051_ag()) {
            this.mc.field_71439_g.func_70031_b(true);
         }
      } catch (Exception var2) {
      }

   }
}
