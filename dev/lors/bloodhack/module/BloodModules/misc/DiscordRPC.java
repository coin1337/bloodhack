package dev.lors.bloodhack.module.BloodModules.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;

public class DiscordRPC extends Module {
   DiscordRichPresence presence = new DiscordRichPresence();
   club.minnced.discord.rpc.DiscordRPC rpc;
   Value<Long> updateDelay;
   boolean connected;

   public DiscordRPC() {
      super("DiscordRPC", Category.MISC);
      this.rpc = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
      this.updateDelay = new Value("UpdateDelay", 1L, 0L, 10L);
   }

   public void onEnable() {
      this.start();
   }

   public void onDisable() {
      this.end();
   }

   private void start() {
      if (!this.connected) {
         System.out.println("DiscordRPC started");
         this.connected = true;
         this.rpc.Discord_Initialize("767603853574144003", new DiscordEventHandlers(), true, "");
         this.presence.startTimestamp = System.currentTimeMillis() / 1000L;
         this.presence.largeImageKey = "blood_group_picture";
         this.presence.smallImageKey = "blood";
         (new Thread(this::setRpcWithDelay, "Discord-RPC-Callback-Handler")).start();
         System.out.println("Discord RPC initialised successfully");
      }
   }

   void end() {
      if (this.connected) {
         System.out.println("DiscordRPC stopped");
         this.connected = false;
         this.rpc.Discord_Shutdown();
      }
   }

   private void setRpcWithDelay() {
      while(!Thread.currentThread().isInterrupted() && this.connected) {
         try {
            String details = "In the menus";
            String state = "taking a shit";
            if (this.mc.func_71387_A()) {
               details = "Singleplayer - " + this.mc.func_71401_C().func_71221_J();
            } else if (this.mc.field_71462_r instanceof GuiMultiplayer) {
               details = "Multiplayer Menu";
            } else if (this.mc.func_147104_D() != null) {
               details = "On " + this.mc.func_147104_D().field_78845_b.toLowerCase();
            } else if (this.mc.field_71462_r instanceof GuiWorldSelection) {
               details = "Singleplayer Menu";
            }

            this.presence.details = details;
            this.presence.state = state;
            this.rpc.Discord_UpdatePresence(this.presence);
         } catch (Exception var4) {
            var4.printStackTrace();
         }

         try {
            Thread.sleep((Long)this.updateDelay.value * 1000L);
         } catch (InterruptedException var3) {
            var3.printStackTrace();
         }
      }

   }
}
