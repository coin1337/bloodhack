package dev.lors.bloodhack.mixin.client;

import dev.lors.bloodhack.capes.CapeUtil;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AbstractClientPlayer.class})
public class MixinAbstractClientPlayer {
   boolean hasDoneit = false;

   @Inject(
      method = {"getLocationCape"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void getLocationCape(CallbackInfoReturnable<ResourceLocation> cir) {
      cir.setReturnValue(CapeUtil.getCape());
   }
}
