package dev.lors.bloodhack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.command.Command;
import dev.lors.bloodhack.managers.MessageManager;
import dev.lors.bloodhack.module.Module;
import java.util.Iterator;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
   public Bind() {
      super("bind", "Binds modules to keys.");
   }

   public void onCommand(String[] args) {
      if (args.length > 2) {
         try {
            Iterator var2 = BloodHack.moduleManager.getModules().iterator();

            while(var2.hasNext()) {
               Module m = (Module)var2.next();
               if (m.getName().equalsIgnoreCase(args[1])) {
                  try {
                     m.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
                     MessageManager.sendMessagePrefix(ChatFormatting.AQUA + m.getName() + ChatFormatting.WHITE + " is now binded to " + ChatFormatting.RED + args[2].toUpperCase() + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + Keyboard.getKeyIndex(args[2].toUpperCase() + "") + ChatFormatting.GRAY + ")");
                  } catch (Exception var5) {
                     MessageManager.sendMessagePrefix(ChatFormatting.RED + m.getName() + ChatFormatting.WHITE + " Something went wrong :(");
                     var5.printStackTrace();
                  }
               }
            }
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }

   }
}
