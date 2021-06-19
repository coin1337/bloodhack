package dev.lors.bloodhack.utils;

import dev.lors.bloodhack.BloodHack;
import net.minecraft.client.Minecraft;

public class FontUtils {
   private static final Minecraft mc = Minecraft.func_71410_x();

   public static float drawStringWithShadow(boolean customFont, String text, int x, int y, int color) {
      return customFont ? BloodHack.fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color) : (float)mc.field_71466_p.func_175063_a(text, (float)x, (float)y, color);
   }

   public static float drawString(boolean customFont, String text, int x, int y, int color, boolean shadow) {
      return customFont ? BloodHack.fontRenderer.drawString(text, (double)x, (double)y, color, shadow) : (float)mc.field_71466_p.func_175065_a(text, (float)x, (float)y, color, false);
   }

   public static float drawFloatedStringWithShadow(boolean customFont, String text, int x, float y, int color) {
      return customFont ? BloodHack.fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color) : (float)mc.field_71466_p.func_175063_a(text, (float)x, y, color);
   }

   public static float drawFloatedString(boolean customFont, String text, int x, float y, int color, boolean shadow) {
      return customFont ? BloodHack.fontRenderer.drawString(text, (double)x, (double)y, color, shadow) : (float)mc.field_71466_p.func_175065_a(text, (float)x, y, color, shadow);
   }

   public static int getStringWidth(boolean customFont, String str) {
      return customFont ? BloodHack.fontRenderer.getStringWidth(str) : mc.field_71466_p.func_78256_a(str);
   }

   public static int getFontHeight(boolean customFont) {
      return customFont ? BloodHack.fontRenderer.getHeight() : mc.field_71466_p.field_78288_b;
   }
}
