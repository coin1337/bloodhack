package dev.lors.bloodhack.command.commands;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.command.Command;

public class Save extends Command {
   public Save() {
      super("save", "Saves all settings.");
   }

   public void onCommand(String[] args) {
      BloodHack.configManager.save();
   }
}
