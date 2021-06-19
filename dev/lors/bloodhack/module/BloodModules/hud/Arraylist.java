package dev.lors.bloodhack.module.BloodModules.hud;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.utils.ColourUtils;
import java.awt.Color;
import java.util.Iterator;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Arraylist extends Module {
   public Arraylist() {
      super("Arraylist", Category.HUD);
   }

   public int GenRainbow() {
      float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
      int rgb = Color.HSBtoRGB(hue[0], 1.0F, 1.0F);
      int red = rgb >> 16 & 255;
      int green = rgb >> 8 & 255;
      int blue = rgb & 255;
      int color = ColourUtils.toRGBA(red, green, blue, 255);
      return color;
   }

   @SubscribeEvent
   public void onRenderGameOverlay(RenderGameOverlayEvent event) {
      if (this.mc.field_71439_g != null && this.mc.field_71441_e != null && event.getType() == ElementType.ALL) {
         float currY = (float)(this.mc.field_71466_p.field_78288_b + 2);
         Iterator var3 = BloodHack.moduleManager.getModules().iterator();

         while(var3.hasNext()) {
            Module m = (Module)var3.next();
            if (m.isToggled()) {
               this.mc.field_71466_p.func_175063_a("《" + m.getName() + "》", 2.0F, currY, this.GenRainbow());
               currY += (float)this.mc.field_71466_p.field_78288_b;
            }
         }
      }

   }
}
