package dev.lors.bloodhack.module.BloodModules.hud;

import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.utils.ColourUtils;
import java.util.Calendar;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Welcomer extends Module {
   Value<Float> x = new Value("X Position", 2.0F, 0.0F, 1000.0F);
   Value<Float> y = new Value("Y Position", 350.0F, 0.0F, 1000.0F);

   public Welcomer() {
      super("Welcomer", Category.HUD);
   }

   private String WelcomeMessages() {
      int timeOfDay = Calendar.getInstance().get(11);
      if (timeOfDay < 12) {
         return "Good Morning, ";
      } else if (timeOfDay < 16) {
         return "Good Afternoon, ";
      } else {
         return timeOfDay < 21 ? "Good Evening, " : "Good Night, ";
      }
   }

   @SubscribeEvent
   public void onRenderGameOverlay(RenderGameOverlayEvent event) {
      if (this.mc.field_71439_g != null && this.mc.field_71441_e != null && event.getType() == ElementType.ALL) {
         this.mc.field_71466_p.func_175063_a(this.WelcomeMessages() + this.mc.func_110432_I().func_111285_a(), (Float)this.x.value, (Float)this.y.value, ColourUtils.genRainbow());
      }

   }
}
