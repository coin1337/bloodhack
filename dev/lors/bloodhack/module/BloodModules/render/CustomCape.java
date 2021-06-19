package dev.lors.bloodhack.module.BloodModules.render;

import dev.lors.bloodhack.capes.CapeUtil;
import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.util.Messages;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class CustomCape extends Module {
   Value<CustomCape.Cape> cape;
   Value<String> url;

   public CustomCape() {
      super("CustomCape", Category.RENDER);
      this.cape = new Value("Cape", CustomCape.Cape.BloodCape);
      this.url = new Value("CapeURL", "", (c) -> {
         return this.cape.value == CustomCape.Cape.CustomCape;
      });
   }

   public void onEnable() {
      if (this.cape.value == CustomCape.Cape.BloodDrop) {
         if (CapeUtil.is_uuid_valid(this.mc.field_71439_g.func_110124_au())) {
            CapeUtil.startAnimationLoop();
         } else {
            Messages.sendMessagePrefix("You are not on allowed to have these capes!");
         }
      } else if (this.cape.value == CustomCape.Cape.BloodCape) {
         if (CapeUtil.is_uuid_valid(this.mc.field_71439_g.func_110124_au())) {
            try {
               CapeUtil.setCape(new DynamicTexture(ImageIO.read(new URL("https://i.imgur.com/EkcQlck.png"))));
            } catch (IOException var3) {
               var3.printStackTrace();
            }
         } else {
            Messages.sendMessagePrefix("You are not on allowed to have these capes!");
         }
      } else if (this.cape.value == CustomCape.Cape.CustomCape) {
         try {
            CapeUtil.setCape(new DynamicTexture(ImageIO.read(new URL(new String(((String)this.url.value).toString().getBytes(), StandardCharsets.UTF_8)))));
         } catch (IOException var2) {
            Messages.sendMessagePrefix("An error happened trying to load the cape");
            var2.printStackTrace();
         }
      }

   }

   public void onDisable() {
      CapeUtil.stopAnimationLoop();
      CapeUtil.capeTexture = null;
   }

   static enum Cape {
      BloodCape,
      BloodDrop,
      CustomCape;
   }
}
