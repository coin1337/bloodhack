package dev.lors.bloodhack.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class Messages {
   private static final EntityPlayerSP player;
   public static String prefix;

   public static void sendRawMessage(String message) {
      player.func_145747_a(new TextComponentString(message));
   }

   public static void sendMessagePrefix(String message) {
      sendRawMessage(prefix + " " + message);
   }

   static {
      player = Minecraft.func_71410_x().field_71439_g;
      prefix = TextFormatting.BLUE + "[" + TextFormatting.GOLD + "BloodHack" + TextFormatting.BLUE + "]" + TextFormatting.WHITE;
   }
}
