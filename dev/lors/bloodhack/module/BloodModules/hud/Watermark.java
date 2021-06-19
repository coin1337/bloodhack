package dev.lors.bloodhack.module.BloodModules.hud;

import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.utils.ColourUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Watermark extends Module {
   public Watermark() {
      super("Watermark", Category.HUD);
   }

   @SubscribeEvent
   public void onRenderGameOverlay(RenderGameOverlayEvent event) {
      if (this.mc.field_71439_g != null && this.mc.field_71441_e != null && event.getType() == ElementType.ALL) {
         this.mc.field_71466_p.func_175063_a("Blood Hack b1.1", 2.0F, 2.0F, ColourUtils.genRainbow());
      }

   }
}
