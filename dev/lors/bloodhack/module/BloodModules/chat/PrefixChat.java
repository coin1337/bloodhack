package dev.lors.bloodhack.module.BloodModules.chat;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;

public class PrefixChat extends Module {
   public PrefixChat() {
      super("PrefixChat", Category.CHAT);
   }

   @SubscribeEvent
   public void onKeyPress(KeyInputEvent event) {
      if (this.mc.field_71462_r == null && Keyboard.isKeyDown(13)) {
         this.mc.func_147108_a(new GuiChat(BloodHack.prefix));
      }

   }
}
