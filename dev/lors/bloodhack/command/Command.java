package dev.lors.bloodhack.command;

public class Command {
   public String usage;
   private final String command;

   public Command(String name, String usage) {
      this.command = name;
      this.usage = usage;
   }

   public static void sendClientMessage(String s) {
   }

   public void onCommand(String[] args) {
   }

   public String getCommand() {
      return this.command;
   }
}
