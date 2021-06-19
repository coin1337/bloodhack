package dev.lors.bloodhack.module.BloodModules.render;

import dev.lors.bloodhack.event.events.RenderWorldEvent;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.utils.RenderUtil;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class ChunkOverlay extends Module {
   @EventHandler
   Listener<RenderWorldEvent> event = new Listener((e) -> {
      RenderUtil.drawRect(10.0D, 10.0D, 10.0D, 10.0D, -1);
   }, new Predicate[0]);

   public ChunkOverlay() {
      super("ChunkOverlay", Category.RENDER);
   }
}
