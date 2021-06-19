package dev.lors.bloodhack.module.BloodModules.combat;

import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.math.BlockPos;

public class BowSpam extends Module {
   public BowSpam() {
      super("BowSpam", Category.COMBAT);
   }

   public void onUpdate() {
      if (this.mc.field_71439_g != null && this.mc.field_71441_e != null && this.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBow && this.mc.field_71439_g.func_184587_cr() && this.mc.field_71439_g.func_184612_cw() >= 3) {
         this.mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, this.mc.field_71439_g.func_174811_aO()));
         this.mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItem(this.mc.field_71439_g.func_184600_cs()));
         this.mc.field_71439_g.func_184597_cx();
      }

   }
}
