package dev.lors.bloodhack.module.BloodModules.hud;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;

public class ClickGUI extends Module {
   public Value<Boolean> rainbow = new Value("Rainbow", true);
   public Value<Integer> r = new Value("Red", 204, 0, 255);
   public Value<Integer> g = new Value("Green", 0, 0, 255);
   public Value<Integer> b = new Value("Blue", 0, 0, 255);
   public Value<Integer> a = new Value("Alpha", 255, 0, 255);
   public Value<Integer> br = new Value("Background Red", 204, 0, 255);
   public Value<Integer> bg = new Value("Background Green", 0, 0, 255);
   public Value<Integer> bb = new Value("Background Blue", 0, 0, 255);
   public Value<Integer> ba = new Value("Background Alpha", 255, 0, 255);

   public ClickGUI() {
      super("ClickGUI", Category.HUD);
   }

   public void onEnable() {
      if (this.mc.field_71439_g != null && this.mc.field_71441_e != null) {
         this.mc.func_147108_a(BloodHack.gui);
         this.toggle();
      }

   }
}
