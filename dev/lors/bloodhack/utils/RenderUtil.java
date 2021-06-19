package dev.lors.bloodhack.utils;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
   public static void drawSexyRect(double posX, double posY, double posX2, double posY2, int col1, int col2) {
      drawRect(posX, posY, posX2, posY2, col2);
      float alpha = (float)(col1 >> 24 & 255) / 255.0F;
      float red = (float)(col1 >> 16 & 255) / 255.0F;
      float green = (float)(col1 >> 8 & 255) / 255.0F;
      float blue = (float)(col1 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glLineWidth(3.0F);
      GL11.glBegin(1);
      GL11.glVertex2d(posX, posY);
      GL11.glVertex2d(posX, posY2);
      GL11.glVertex2d(posX2, posY2);
      GL11.glVertex2d(posX2, posY);
      GL11.glVertex2d(posX, posY);
      GL11.glVertex2d(posX2, posY);
      GL11.glVertex2d(posX, posY2);
      GL11.glVertex2d(posX2, posY2);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void drawRect(double par0, double par1, double par2, double par3, int par4) {
      double var5;
      if (par0 < par2) {
         var5 = par0;
         par0 = par2;
         par2 = var5;
      }

      if (par1 < par3) {
         var5 = par1;
         par1 = par3;
         par3 = var5;
      }

      float var10 = (float)(par4 >> 24 & 255) / 255.0F;
      float var6 = (float)(par4 >> 16 & 255) / 255.0F;
      float var7 = (float)(par4 >> 8 & 255) / 255.0F;
      float var8 = (float)(par4 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      GL11.glColor4f(var6, var7, var8, var10);
      GL11.glBegin(7);
      GL11.glVertex3d(par0, par3, 0.0D);
      GL11.glVertex3d(par2, par3, 0.0D);
      GL11.glVertex3d(par2, par1, 0.0D);
      GL11.glVertex3d(par0, par1, 0.0D);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawBoundingBox(AxisAlignedBB bb, float width, float red, float green, float blue, float alpha) {
      GlStateManager.func_179094_E();
      BloodHackTessellator tessellator = BloodHackTessellator.INSTANCE;
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, 0.0F).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, 0.0F).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
      bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179121_F();
   }
}
