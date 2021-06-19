package dev.lors.bloodhack.module.BloodModules.render;

import dev.lors.bloodhack.event.events.RenderWorldEvent;
import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.utils.CrystalUtil;
import dev.lors.bloodhack.utils.RenderUtil;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class HoleESP extends Module {
   private final BlockPos[] surroundOffset = new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};
   Value<Integer> rangeS = new Value("Range", 7, 0, 9);
   Value<Integer> r = new Value("Red", 255, 0, 255);
   Value<Integer> g = new Value("Green", 255, 0, 255);
   Value<Integer> b = new Value("Blue", 255, 0, 255);
   Value<Integer> a = new Value("Alpha", 255, 0, 255);
   private ConcurrentHashMap<BlockPos, Boolean> safeHoles = new ConcurrentHashMap();
   @EventHandler
   Listener<RenderWorldEvent> eventListener = new Listener((e) -> {
      if (this.mc.field_71439_g != null && this.safeHoles != null) {
         try {
            if (this.safeHoles.isEmpty()) {
               return;
            }

            Iterator var2 = this.safeHoles.keySet().iterator();

            while(var2.hasNext()) {
               BlockPos pos = (BlockPos)var2.next();
               RenderUtil.drawBoundingBox(new AxisAlignedBB(pos), 2.0F, (float)(Integer)this.r.value, (float)(Integer)this.g.value, (float)(Integer)this.b.value, (float)(Integer)this.a.value);
            }
         } catch (Exception var4) {
         }

      }
   }, new Predicate[0]);

   public HoleESP() {
      super("HoleESP", Category.RENDER);
   }

   public void onUpdate() {
      if (this.mc.field_71439_g != null || this.mc.field_71441_e != null) {
         try {
            int range = (int)Math.ceil((double)(Integer)this.rangeS.value);
            List<BlockPos> blockPosList = CrystalUtil.getSphere(this.getPlayerPos(), (float)range, range, false, true, 0);
            Iterator var3 = blockPosList.iterator();

            while(true) {
               BlockPos pos;
               do {
                  do {
                     do {
                        if (!var3.hasNext()) {
                           return;
                        }

                        pos = (BlockPos)var3.next();
                     } while(!this.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a));
                  } while(!this.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a));
               } while(!this.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a));

               boolean isSafe = true;
               boolean isBedrock = true;
               BlockPos[] var7 = this.surroundOffset;
               int var8 = var7.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  BlockPos offset = var7[var9];
                  Block block = this.mc.field_71441_e.func_180495_p(pos.func_177971_a(offset)).func_177230_c();
                  if (block != Blocks.field_150357_h) {
                     isBedrock = false;
                  }

                  if (block != Blocks.field_150357_h && block != Blocks.field_150343_Z && block != Blocks.field_150477_bB && block != Blocks.field_150467_bQ) {
                     isSafe = false;
                     break;
                  }
               }

               if (isSafe) {
                  this.safeHoles.put(pos, isBedrock);
               }
            }
         } catch (Exception var12) {
         }
      }
   }

   public BlockPos getPlayerPos() {
      return this.mc.field_71439_g.func_180425_c();
   }
}
