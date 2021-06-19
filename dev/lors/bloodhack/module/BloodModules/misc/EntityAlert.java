package dev.lors.bloodhack.module.BloodModules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lors.bloodhack.managers.MessageManager;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import java.util.Iterator;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.init.SoundEvents;

public class EntityAlert extends Module {
   private int donkeyDelay;
   private int llamaDelay;
   private int muleDelay;

   public EntityAlert() {
      super("EntityAlert", Category.MISC);
   }

   public void onUpdate() {
      if (this.mc.field_71441_e != null && this.mc.field_71439_g != null) {
         ++this.donkeyDelay;
         ++this.llamaDelay;
         ++this.muleDelay;
         Iterator var1 = this.mc.field_71441_e.func_72910_y().iterator();

         while(true) {
            while(var1.hasNext()) {
               Entity entity = (Entity)var1.next();
               if (entity instanceof EntityDonkey && this.donkeyDelay >= 100) {
                  MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.BLUE + "EntityAlert" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Found a " + ChatFormatting.AQUA + "donkey " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.field_70142_S) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70137_T) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70136_U) + ChatFormatting.GRAY + "]");
                  this.mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_194007_a(SoundEvents.field_187604_bf, 1.0F, 1.0F));
                  this.donkeyDelay = -750;
               } else if (entity instanceof EntityLlama && this.llamaDelay >= 100) {
                  MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.BLUE + "EntityAlert" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Found a " + ChatFormatting.AQUA + "llama " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.field_70142_S) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70137_T) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70136_U) + ChatFormatting.GRAY + "]");
                  this.mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_194007_a(SoundEvents.field_187604_bf, 1.0F, 1.0F));
                  this.llamaDelay = -750;
               } else if (entity instanceof EntityMule && this.muleDelay >= 100) {
                  MessageManager.sendMessagePrefix(ChatFormatting.GRAY + "[" + ChatFormatting.BLUE + "EntityAlert" + ChatFormatting.GRAY + "] " + ChatFormatting.WHITE + "Found a " + ChatFormatting.AQUA + "mule " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.field_70142_S) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70137_T) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.field_70136_U) + ChatFormatting.GRAY + "]");
                  this.mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_194007_a(SoundEvents.field_187604_bf, 1.0F, 1.0F));
                  this.muleDelay = -750;
               }
            }

            return;
         }
      }
   }
}
