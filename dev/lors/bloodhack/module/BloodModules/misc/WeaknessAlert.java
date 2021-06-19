package dev.lors.bloodhack.module.BloodModules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lors.bloodhack.managers.MessageManager;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import net.minecraft.init.MobEffects;

public class WeaknessAlert extends Module {
   private boolean hasAnnounced = false;

   public WeaknessAlert() {
      super("WeaknessAlert", Category.MISC);
   }

   public void onUpdate() {
      if (this.mc.field_71441_e != null && this.mc.field_71439_g != null) {
         if (this.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t) && !this.hasAnnounced) {
            this.hasAnnounced = true;
            MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "WeaknessDetect" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Hey" + ChatFormatting.GRAY + ", " + ChatFormatting.AQUA + this.mc.func_110432_I().func_111285_a() + ChatFormatting.GRAY + "," + ChatFormatting.WHITE + " unlucky move mate" + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + "you now have " + ChatFormatting.RED + "weakness");
         }

         if (!this.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t) && this.hasAnnounced) {
            this.hasAnnounced = false;
            MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + "WeaknessDetect" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Phew" + ChatFormatting.GRAY + ", " + ChatFormatting.AQUA + this.mc.func_110432_I().func_111285_a() + ChatFormatting.GRAY + "," + ChatFormatting.WHITE + " that was close" + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + "you no longer have " + ChatFormatting.RED + "weakness");
         }
      }

   }
}
