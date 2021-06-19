package dev.lors.bloodhack.util;

import dev.lors.bloodhack.util.font.CFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;

public class Wrapper {
   static final Minecraft mc = Minecraft.func_71410_x();
   private static CFontRenderer fontRenderer;

   public static Minecraft GetMC() {
      return mc;
   }

   public static EntityPlayerSP GetPlayer() {
      return mc.field_71439_g;
   }

   public static int getKey(String keyname) {
      return Keyboard.getKeyIndex(keyname.toUpperCase());
   }

   public static CFontRenderer getFontRenderer() {
      return fontRenderer;
   }
}
