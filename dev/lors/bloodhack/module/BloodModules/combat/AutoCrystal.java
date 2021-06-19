package dev.lors.bloodhack.module.BloodModules.combat;

import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.utils.EntityUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;

public class AutoCrystal extends Module {
   private static final Minecraft mc = Minecraft.func_71410_x();
   private static boolean togglePitch = false;
   private static double yaw;
   private static double pitch;
   private static boolean isSpoofingAngles;
   public boolean isActive = false;
   Value<Boolean> place = new Value("Place", true);
   Value<Integer> range = new Value("Range", 5, 0, 10);
   Value<Integer> delay = new Value("Delay", 2, 0, 20);
   private BlockPos render;
   private Entity renderEnt;
   private final long systemTime = -1L;
   private boolean switchCooldown = false;
   private final boolean isAttacking = false;
   private final int oldSlot = -1;
   private int newSlot;
   private long placeSystemTime;
   private long breakSystemTime;
   private long chatSystemTime;
   private long multiPlaceSystemTime;
   private long antiStuckSystemTime;
   private int placements;

   public AutoCrystal() {
      super("AutoCrystal", Category.COMBAT);
   }

   public static BlockPos getPlayerPos() {
      return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
   }

   public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
      float doubleExplosionSize = 12.0F;
      double distancedsize = entity.func_70011_f(posX, posY, posZ) / 12.0D;
      Vec3d vec3d = new Vec3d(posX, posY, posZ);
      double blockDensity = (double)entity.field_70170_p.func_72842_a(vec3d, entity.func_174813_aQ());
      double v = (1.0D - distancedsize) * blockDensity;
      float damage = (float)((int)((v * v + v) / 2.0D * 7.0D * 12.0D + 1.0D));
      double finald = 1.0D;
      if (entity instanceof EntityLivingBase) {
         finald = (double)getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion(mc.field_71441_e, (Entity)null, posX, posY, posZ, 6.0F, false, true));
      }

      return (float)finald;
   }

   public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
      if (entity instanceof EntityPlayer) {
         EntityPlayer ep = (EntityPlayer)entity;
         DamageSource ds = DamageSource.func_94539_a(explosion);
         damage = CombatRules.func_189427_a(damage, (float)ep.func_70658_aO(), (float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
         int k = EnchantmentHelper.func_77508_a(ep.func_184193_aE(), ds);
         float f = MathHelper.func_76131_a((float)k, 0.0F, 20.0F);
         damage *= 1.0F - f / 25.0F;
         if (entity.func_70644_a(Potion.func_188412_a(11))) {
            damage -= damage / 4.0F;
         }

         return damage;
      } else {
         damage = CombatRules.func_189427_a(damage, (float)entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
         return damage;
      }
   }

   private static float getDamageMultiplied(float damage) {
      int diff = mc.field_71441_e.func_175659_aa().func_151525_a();
      return damage * (diff == 0 ? 0.0F : (diff == 2 ? 1.0F : (diff == 1 ? 0.5F : 1.5F)));
   }

   public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
      return calculateDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, entity);
   }

   public static boolean canBlockBeSeen(BlockPos blockPos) {
      return mc.field_71441_e.func_147447_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p()), false, true, false) == null;
   }

   private static void setYawAndPitch(float yaw1, float pitch1) {
      yaw = (double)yaw1;
      pitch = (double)pitch1;
   }

   private static void resetRotation() {
      yaw = (double)mc.field_71439_g.field_70177_z;
      pitch = (double)mc.field_71439_g.field_70125_A;
   }

   public void setup() {
      this.placeSystemTime = -1L;
      this.breakSystemTime = -1L;
      this.chatSystemTime = -1L;
      this.multiPlaceSystemTime = -1L;
      this.antiStuckSystemTime = -1L;
      this.switchCooldown = false;
      this.placements = 0;
   }

   public void onUpdate() {
      EntityEnderCrystal crystal = (EntityEnderCrystal)mc.field_71441_e.field_72996_f.stream().filter((entity) -> {
         return entity instanceof EntityEnderCrystal;
      }).map((entity) -> {
         return entity;
      }).min(Comparator.comparing((c) -> {
         return mc.field_71439_g.func_70032_d(c);
      })).orElse((Object)null);
      if (crystal != null && mc.field_71439_g.func_70032_d(crystal) <= 5.0F) {
         if (System.nanoTime() / 1000000L - this.breakSystemTime >= 320L) {
            this.lookAtPacket(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, mc.field_71439_g);
            mc.field_71442_b.func_78764_a(mc.field_71439_g, crystal);
            mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            this.breakSystemTime = System.nanoTime() / 1000000L;
         }

         if (System.nanoTime() / 1000000L - this.antiStuckSystemTime <= 700L) {
            return;
         }
      } else {
         resetRotation();
      }

      int crystalSlot = mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP ? mc.field_71439_g.field_71071_by.field_70461_c : -1;
      if (crystalSlot == -1) {
         for(int l = 0; l < 9; ++l) {
            if (mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_185158_cP) {
               crystalSlot = l;
               break;
            }
         }
      }

      boolean offhand = false;
      if (mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) {
         offhand = true;
      } else if (crystalSlot == -1) {
         return;
      }

      Entity ent = null;
      Entity lastTarget = null;
      BlockPos finalPos = null;
      List<BlockPos> blocks = this.findCrystalBlocks();
      List<Entity> entities = new ArrayList();
      entities.addAll((Collection)mc.field_71441_e.field_73010_i.stream().filter((entityPlayer) -> {
         return true;
      }).collect(Collectors.toList()));
      double damage = 0.5D;
      Iterator var11 = entities.iterator();

      label132:
      while(true) {
         Entity entity2;
         do {
            do {
               do {
                  if (!var11.hasNext()) {
                     if (damage == 0.5D) {
                        this.render = null;
                        this.renderEnt = null;
                        resetRotation();
                        return;
                     }

                     this.render = finalPos;
                     this.renderEnt = ent;
                     if (!offhand && mc.field_71439_g.field_71071_by.field_70461_c != crystalSlot) {
                        mc.field_71439_g.field_71071_by.field_70461_c = crystalSlot;
                        resetRotation();
                        this.switchCooldown = true;
                        return;
                     }

                     this.lookAtPacket((double)finalPos.func_177958_n() + 0.5D, (double)finalPos.func_177956_o() - 0.5D, (double)finalPos.func_177952_p() + 0.5D, mc.field_71439_g);
                     RayTraceResult result = mc.field_71441_e.func_72933_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d((double)finalPos.func_177958_n() + 0.5D, (double)finalPos.func_177956_o() - 0.5D, (double)finalPos.func_177952_p() + 0.5D));
                     EnumFacing f;
                     if (result != null && result.field_178784_b != null) {
                        f = result.field_178784_b;
                     } else {
                        f = EnumFacing.UP;
                     }

                     if (this.switchCooldown) {
                        this.switchCooldown = false;
                        return;
                     }

                     if (System.nanoTime() / 1000000L - this.placeSystemTime >= (long)(Integer)this.delay.value) {
                        mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItemOnBlock(finalPos, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
                        ++this.placements;
                        this.antiStuckSystemTime = System.nanoTime() / 1000000L;
                        this.placeSystemTime = System.nanoTime() / 1000000L;
                     }

                     EntityPlayerSP player;
                     if (togglePitch) {
                        player = mc.field_71439_g;
                        player.field_70125_A += 4.0E-4F;
                        togglePitch = false;
                     } else {
                        player = mc.field_71439_g;
                        player.field_70125_A -= 4.0E-4F;
                        togglePitch = true;
                     }

                     return;
                  }

                  entity2 = (Entity)var11.next();
               } while(entity2 == mc.field_71439_g);
            } while(((EntityLivingBase)entity2).func_110143_aJ() <= 0.0F);
         } while(mc.field_71439_g.func_70068_e(entity2) > 25.0D);

         Iterator var13 = blocks.iterator();

         while(true) {
            BlockPos blockPos;
            double d;
            do {
               double b;
               do {
                  do {
                     if (!var13.hasNext()) {
                        continue label132;
                     }

                     blockPos = (BlockPos)var13.next();
                  } while(!canBlockBeSeen(blockPos) && mc.field_71439_g.func_174818_b(blockPos) > 25.0D);

                  b = entity2.func_174818_b(blockPos);
               } while(b > 56.2D);

               d = (double)calculateDamage((double)blockPos.func_177958_n() + 0.5D, (double)(blockPos.func_177956_o() + 1), (double)blockPos.func_177952_p() + 0.5D, entity2);
            } while(d < 7.0D && ((EntityLivingBase)entity2).func_110143_aJ() + ((EntityLivingBase)entity2).func_110139_bj() > 20.0F);

            if (!(d <= damage)) {
               double self = (double)calculateDamage((double)blockPos.func_177958_n() + 0.5D, (double)(blockPos.func_177956_o() + 1), (double)blockPos.func_177952_p() + 0.5D, mc.field_71439_g);
               if (!((double)(mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj()) - self <= 7.0D) && !(self > d)) {
                  damage = d;
                  finalPos = blockPos;
                  ent = entity2;
               }
            }
         }
      }
   }

   private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
      double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
      setYawAndPitch((float)v[0], (float)v[1]);
   }

   private boolean canPlaceCrystal(BlockPos blockPos) {
      BlockPos boost = blockPos.func_177982_a(0, 1, 0);
      BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
      return (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150357_h || mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150343_Z) && mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(boost2).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost)).isEmpty() && mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
   }

   private List<BlockPos> findCrystalBlocks() {
      NonNullList positions = NonNullList.func_191196_a();
      positions.addAll((Collection)this.getSphere(getPlayerPos(), (float)(Integer)this.range.value, (Integer)this.range.value, false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
      return positions;
   }

   public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
      List<BlockPos> circleblocks = new ArrayList();
      int cx = loc.func_177958_n();
      int cy = loc.func_177956_o();
      int cz = loc.func_177952_p();

      for(int x = cx - (int)r; (float)x <= (float)cx + r; ++x) {
         for(int z = cz - (int)r; (float)z <= (float)cz + r; ++z) {
            for(int y = sphere ? cy - (int)r : cy; (float)y < (sphere ? (float)cy + r : (float)(cy + h)); ++y) {
               double dist = (double)((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0));
               if (dist < (double)(r * r) && (!hollow || dist >= (double)((r - 1.0F) * (r - 1.0F)))) {
                  BlockPos l = new BlockPos(x, y + plus_y, z);
                  circleblocks.add(l);
               }
            }
         }
      }

      return circleblocks;
   }
}
