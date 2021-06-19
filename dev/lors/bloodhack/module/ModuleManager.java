package dev.lors.bloodhack.module;

import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.BloodModules.chat.AutoSuicide;
import dev.lors.bloodhack.module.BloodModules.chat.ChatSuffix;
import dev.lors.bloodhack.module.BloodModules.chat.PrefixChat;
import dev.lors.bloodhack.module.BloodModules.chat.TotemPopAnnouncer;
import dev.lors.bloodhack.module.BloodModules.combat.AutoArmor;
import dev.lors.bloodhack.module.BloodModules.combat.AutoCrystal;
import dev.lors.bloodhack.module.BloodModules.combat.AutoTotem;
import dev.lors.bloodhack.module.BloodModules.combat.BedAura;
import dev.lors.bloodhack.module.BloodModules.combat.BetterAC;
import dev.lors.bloodhack.module.BloodModules.combat.BowSpam;
import dev.lors.bloodhack.module.BloodModules.hud.Arraylist;
import dev.lors.bloodhack.module.BloodModules.hud.ClickGUI;
import dev.lors.bloodhack.module.BloodModules.hud.Watermark;
import dev.lors.bloodhack.module.BloodModules.hud.Welcomer;
import dev.lors.bloodhack.module.BloodModules.misc.DiscordRPC;
import dev.lors.bloodhack.module.BloodModules.misc.EntityAlert;
import dev.lors.bloodhack.module.BloodModules.misc.WeaknessAlert;
import dev.lors.bloodhack.module.BloodModules.movement.HoleTP;
import dev.lors.bloodhack.module.BloodModules.movement.Sanic;
import dev.lors.bloodhack.module.BloodModules.movement.Sprint;
import dev.lors.bloodhack.module.BloodModules.player.FakePlayer;
import dev.lors.bloodhack.module.BloodModules.render.ChunkOverlay;
import dev.lors.bloodhack.module.BloodModules.render.CustomCape;
import dev.lors.bloodhack.module.BloodModules.render.Fullbright;
import dev.lors.bloodhack.module.BloodModules.render.HoleESP;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModuleManager {
   private final ArrayList<Module> modules = new ArrayList();

   public ModuleManager() throws IllegalAccessException {
      this.modules.add(new BedAura());
      this.modules.add(new BetterAC());
      this.modules.add(new AutoArmor());
      this.modules.add(new AutoCrystal());
      this.modules.add(new AutoTotem());
      this.modules.add(new BowSpam());
      this.modules.add(new Sanic());
      this.modules.add(new HoleTP());
      this.modules.add(new Sprint());
      this.modules.add(new Fullbright());
      this.modules.add(new ChunkOverlay());
      this.modules.add(new CustomCape());
      this.modules.add(new HoleESP());
      this.modules.add(new AutoSuicide());
      this.modules.add(new PrefixChat());
      this.modules.add(new ChatSuffix());
      this.modules.add(new TotemPopAnnouncer());
      this.modules.add(new DiscordRPC());
      this.modules.add(new WeaknessAlert());
      this.modules.add(new EntityAlert());
      this.modules.add(new Watermark());
      this.modules.add(new Arraylist());
      this.modules.add(new ClickGUI());
      this.modules.add(new Welcomer());
      this.modules.add(new FakePlayer());
      Iterator var1 = this.modules.iterator();

      while(var1.hasNext()) {
         Module module = (Module)var1.next();
         Field[] var3 = module.getClass().getDeclaredFields();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            if (Value.class.isAssignableFrom(field.getType())) {
               if (!field.isAccessible()) {
                  field.setAccessible(true);
               }

               Value val = (Value)field.get(module);
               val.module = module;
               module.values.add(val);
            }
         }
      }

   }

   public ArrayList<Module> getModules() {
      return this.modules;
   }

   public Module getModuleByName(String name) {
      return (Module)this.modules.stream().filter((module) -> {
         return module.getName().equalsIgnoreCase(name);
      }).findFirst().orElse((Object)null);
   }

   public List<Module> getModulesByCategory(Category category) {
      List<Module> list = new ArrayList();
      Iterator var3 = this.modules.iterator();

      while(var3.hasNext()) {
         Module module = (Module)var3.next();
         if (module.getCategory().equals(category)) {
            list.add(module);
         }
      }

      return list;
   }
}
