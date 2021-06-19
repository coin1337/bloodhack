package dev.lors.bloodhack.utils;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class BlockUtils {
   private static final Minecraft mc = Minecraft.func_71410_x();
   public static List<Block> emptyBlocks;
   public static List<Block> rightclickableBlocks;

   public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck, float height) {
      return !shouldCheck || mc.field_71441_e.func_147447_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d((double)pos.func_177958_n(), (double)((float)pos.func_177956_o() + height), (double)pos.func_177952_p()), false, true, false) == null;
   }

   public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck) {
      return rayTracePlaceCheck(pos, shouldCheck, 1.0F);
   }

   public static boolean canSeeBlock(BlockPos p_Pos) {
      return mc.field_71439_g != null && mc.field_71441_e.func_147447_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d((double)p_Pos.func_177958_n(), (double)p_Pos.func_177956_o(), (double)p_Pos.func_177952_p()), false, true, false) == null;
   }

   public static void placeCrystalOnBlock(BlockPos pos, EnumHand hand) {
      RayTraceResult result = mc.field_71441_e.func_72933_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() - 0.5D, (double)pos.func_177952_p() + 0.5D));
      EnumFacing facing = result != null && result.field_178784_b != null ? result.field_178784_b : EnumFacing.UP;
      mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0F, 0.0F, 0.0F));
   }
}
