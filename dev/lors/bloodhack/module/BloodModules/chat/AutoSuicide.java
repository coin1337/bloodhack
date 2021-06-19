package dev.lors.bloodhack.module.BloodModules.chat;

import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;

public class AutoSuicide extends Module {
   public AutoSuicide() {
      super("AutoSuicide", Category.CHAT);
   }

   public void onEnable() {
      this.mc.field_71439_g.func_71165_d("/kill");
      this.toggle();
   }
}
