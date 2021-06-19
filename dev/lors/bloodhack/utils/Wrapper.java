package dev.lors.bloodhack.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class Wrapper {
   static final Minecraft mc = Minecraft.func_71410_x();
   private static FontRenderer fontRenderer;

   public static Minecraft GetMC() {
      return mc;
   }

   public static EntityPlayerSP GetPlayer() {
      return mc.field_71439_g;
   }

   public static Minecraft getMinecraft() {
      return Minecraft.func_71410_x();
   }

   public static EntityPlayerSP getPlayer() {
      return getMinecraft().field_71439_g;
   }

   public static World getWorld() {
      return getMinecraft().field_71441_e;
   }

   public static int getKey(String keyname) {
      return Keyboard.getKeyIndex(keyname.toUpperCase());
   }
}
