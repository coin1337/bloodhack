package dev.lors.bloodhack.module.BloodModules.chat;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatSuffix extends Module {
   public ChatSuffix() {
      super("ChatSuffix", Category.CHAT);
   }

   @SubscribeEvent
   public void onChat(ClientChatEvent event) {
      String suffix = "  | ʙʟᴏᴏᴅʜᴀᴄᴋ";
      if (!event.getMessage().startsWith("/")) {
         if (!event.getMessage().startsWith(BloodHack.prefix)) {
            event.setMessage(event.getMessage() + suffix);
         }
      }
   }
}
