package dev.lors.bloodhack.command.commands;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.command.Command;
import dev.lors.bloodhack.managers.MessageManager;
import dev.lors.bloodhack.module.Module;
import java.util.Iterator;
import net.minecraft.util.text.TextFormatting;

public class Toggle extends Command {
   public Toggle() {
      super("Toggle", "Toggles a module.");
   }

   public void onCommand(String[] args) {
      if (args.length > 1) {
         try {
            Iterator var2 = BloodHack.moduleManager.getModules().iterator();

            while(var2.hasNext()) {
               Module m = (Module)var2.next();
               if (m.getName().equalsIgnoreCase(args[1])) {
                  m.toggle();
                  if (m.isToggled()) {
                     MessageManager.sendMessagePrefix(TextFormatting.AQUA + m.getName() + TextFormatting.WHITE + " is now " + TextFormatting.GREEN + "ON");
                  } else {
                     MessageManager.sendMessagePrefix(TextFormatting.AQUA + m.getName() + TextFormatting.WHITE + " is now " + TextFormatting.RED + "OFF");
                  }
               }
            }
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }
}
