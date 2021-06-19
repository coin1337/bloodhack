package dev.lors.bloodhack.module.BloodModules.player;

import com.mojang.authlib.GameProfile;
import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FakePlayer extends Module {
   Value<String> name = new Value("Name", "", "Popbob");
   private EntityOtherPlayerMP _fakePlayer;

   public FakePlayer() {
      super("FakePlayer", Category.PLAYER);
   }

   public void onEnable() {
      super.onEnable();
      this._fakePlayer = null;
      if (this.mc.field_71441_e == null) {
         this.toggle();
      } else {
         this._fakePlayer = new EntityOtherPlayerMP(this.mc.field_71441_e, new GameProfile(this.mc.field_71439_g.func_110124_au(), (String)this.name.value));
         this.mc.field_71441_e.func_73027_a(this._fakePlayer.func_145782_y(), this._fakePlayer);
         this._fakePlayer.func_184595_k(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v);
      }
   }

   public void onDisable() {
      if (this.mc.field_71441_e != null) {
         this.mc.field_71441_e.func_72900_e(this._fakePlayer);
      }

   }
}
