package dev.lors.bloodhack.module.BloodModules.combat;

import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;

public class AutoTotem extends Module {
   public AutoTotem() {
      super("AutoTotem", Category.COMBAT);
   }

   public void onUpdate() {
      if (this.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.OFFHAND).func_77973_b() != Items.field_190929_cY) {
         int slot = this.getItemSlot();
         if (slot != -1) {
            this.mc.field_71442_b.func_187098_a(this.mc.field_71439_g.field_71069_bz.field_75152_c, slot, 0, ClickType.PICKUP, this.mc.field_71439_g);
            this.mc.field_71442_b.func_187098_a(this.mc.field_71439_g.field_71069_bz.field_75152_c, 45, 0, ClickType.PICKUP, this.mc.field_71439_g);
            this.mc.field_71442_b.func_187098_a(this.mc.field_71439_g.field_71069_bz.field_75152_c, slot, 0, ClickType.PICKUP, this.mc.field_71439_g);
            this.mc.field_71442_b.func_78765_e();
         }

      }
   }

   private int getItemSlot() {
      for(int i = 0; i < 36; ++i) {
         Item item = this.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
         if (item == Items.field_190929_cY) {
            if (i < 9) {
               i += 36;
            }

            return i;
         }
      }

      return -1;
   }
}
