package dev.lors.bloodhack.module.BloodModules.render;

import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;

public class Fullbright extends Module {
   public Fullbright() {
      super("Fullbright", Category.RENDER);
   }

   public void onEnable() {
      this.mc.field_71474_y.field_74333_Y = 100.0F;
   }

   public void onDisable() {
      this.mc.field_71474_y.field_74333_Y = 1.0F;
   }
}
