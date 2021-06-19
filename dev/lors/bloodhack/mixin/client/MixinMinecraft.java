package dev.lors.bloodhack.mixin.client;

import club.minnced.discord.rpc.DiscordRPC;
import dev.lors.bloodhack.BloodHack;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public class MixinMinecraft {
   @Inject(
      method = {"shutdown"},
      at = {@At("HEAD")}
   )
   public void shutdown(CallbackInfo ci) {
      BloodHack.configManager.save();
      DiscordRPC.INSTANCE.Discord_Shutdown();
   }

   @Inject(
      method = {"crashed"},
      at = {@At("HEAD")}
   )
   public void crashed(CrashReport crash, CallbackInfo ci) {
      BloodHack.configManager.save();
      DiscordRPC.INSTANCE.Discord_Shutdown();
   }
}
