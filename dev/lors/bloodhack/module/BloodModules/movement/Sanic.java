package dev.lors.bloodhack.module.BloodModules.movement;

import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;

public class Sanic extends Module {
   int waitCounter;
   int forward = 1;
   Value<Boolean> jump = new Value("Jump", false);

   public Sanic() {
      super("Sanic", Category.MOVEMENT);
   }

   public void onUpdate() {
      if (this.mc.field_71439_g != null) {
         boolean boost = Math.abs(this.mc.field_71439_g.field_70759_as - this.mc.field_71439_g.field_70177_z) < 90.0F;
         if (this.mc.field_71439_g.field_191988_bg != 0.0F) {
            if (!this.mc.field_71439_g.func_70051_ag()) {
               this.mc.field_71439_g.func_70031_b(true);
            }

            float yaw = this.mc.field_71439_g.field_70177_z;
            if (this.mc.field_71439_g.field_191988_bg > 0.0F) {
               if (this.mc.field_71439_g.field_71158_b.field_78902_a != 0.0F) {
                  yaw += this.mc.field_71439_g.field_71158_b.field_78902_a > 0.0F ? -45.0F : 45.0F;
               }

               this.forward = 1;
               this.mc.field_71439_g.field_191988_bg = 1.0F;
               this.mc.field_71439_g.field_70702_br = 0.0F;
            } else if (this.mc.field_71439_g.field_191988_bg < 0.0F) {
               if (this.mc.field_71439_g.field_71158_b.field_78902_a != 0.0F) {
                  yaw += this.mc.field_71439_g.field_71158_b.field_78902_a > 0.0F ? 45.0F : -45.0F;
               }

               this.forward = -1;
               this.mc.field_71439_g.field_191988_bg = -1.0F;
               this.mc.field_71439_g.field_70702_br = 0.0F;
            }

            if (this.mc.field_71439_g.field_70122_E) {
               this.mc.field_71439_g.func_70637_d(false);
               if (this.waitCounter < 1) {
                  ++this.waitCounter;
                  return;
               }

               this.waitCounter = 0;
               float f = (float)Math.toRadians((double)yaw);
               EntityPlayerSP var10000;
               if ((Boolean)this.jump.value) {
                  this.mc.field_71439_g.field_70181_x = 0.405D;
                  var10000 = this.mc.field_71439_g;
                  var10000.field_70159_w -= (double)(MathHelper.func_76126_a(f) * 0.2F) * (double)this.forward;
                  var10000 = this.mc.field_71439_g;
                  var10000.field_70179_y += (double)(MathHelper.func_76134_b(f) * 0.2F) * (double)this.forward;
               } else if (this.mc.field_71474_y.field_74314_A.func_151468_f()) {
                  this.mc.field_71439_g.field_70181_x = 0.405D;
                  var10000 = this.mc.field_71439_g;
                  var10000.field_70159_w -= (double)(MathHelper.func_76126_a(f) * 0.2F) * (double)this.forward;
                  var10000 = this.mc.field_71439_g;
                  var10000.field_70179_y += (double)(MathHelper.func_76134_b(f) * 0.2F) * (double)this.forward;
               }
            } else {
               if (this.waitCounter < 1) {
                  ++this.waitCounter;
                  return;
               }

               this.waitCounter = 0;
               double currentSpeed = Math.sqrt(this.mc.field_71439_g.field_70159_w * this.mc.field_71439_g.field_70159_w + this.mc.field_71439_g.field_70179_y * this.mc.field_71439_g.field_70179_y);
               double speed = boost ? 1.0064D : 1.001D;
               if (this.mc.field_71439_g.field_70181_x < 0.0D) {
                  speed = 1.0D;
               }

               double direction = Math.toRadians((double)yaw);
               this.mc.field_71439_g.field_70159_w = -Math.sin(direction) * speed * currentSpeed * (double)this.forward;
               this.mc.field_71439_g.field_70179_y = Math.cos(direction) * speed * currentSpeed * (double)this.forward;
            }
         }
      }

   }
}
