package dev.lors.bloodhack.module.BloodModules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.managers.friends.Friends;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.util.Messages;
import dev.lors.bloodhack.utils.BloodHackTessellator;
import dev.lors.bloodhack.utils.ColourUtils;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BedAura extends Module {
   Value<Integer> range = new Value("Range", 7, 0, 9);
   Value<Integer> placedelay = new Value("Place Delay", 15, 8, 20);
   Value<Boolean> announceUsage = new Value("Announce Usage", true);
   Value<Boolean> placeesp = new Value("Place ESP", true);
   private int playerHotbarSlot = -1;
   private int lastHotbarSlot = -1;
   private EntityPlayer closestTarget;
   private String lastTickTargetName;
   private int bedSlot = -1;
   private BlockPos placeTarget;
   private float rotVar;
   private int blocksPlaced;
   private double diffXZ;
   private boolean firstRun;
   private boolean nowTop = false;

   public BedAura() {
      super("ECME BedAura", Category.COMBAT);
   }

   public static boolean isLiving(Entity e) {
      return e instanceof EntityLivingBase;
   }

   public void setup() {
   }

   public void onEnable() {
      if (this.mc.field_71439_g == null) {
         this.toggle();
      } else {
         MinecraftForge.EVENT_BUS.register(this);
         this.firstRun = true;
         this.blocksPlaced = 0;
         this.playerHotbarSlot = this.mc.field_71439_g.field_71071_by.field_70461_c;
         this.lastHotbarSlot = -1;
      }
   }

   public void onDisable() {
      if (this.mc.field_71439_g != null) {
         MinecraftForge.EVENT_BUS.unregister(this);
         if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            this.mc.field_71439_g.field_71071_by.field_70461_c = this.playerHotbarSlot;
         }

         this.playerHotbarSlot = -1;
         this.lastHotbarSlot = -1;
         if ((Boolean)this.announceUsage.value) {
            Messages.sendMessagePrefix(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "BedAura" + TextFormatting.BLUE + "]" + ChatFormatting.RED.toString() + " Disabled" + ChatFormatting.RESET.toString() + "!");
         }

         this.blocksPlaced = 0;
      }
   }

   public void onUpdate() {
      if (this.mc.field_71439_g != null) {
         if (this.mc.field_71439_g.field_71093_bK == 0) {
            Messages.sendMessagePrefix("You are in the overworld!");
            this.toggle();
         }

         try {
            this.findClosestTarget();
         } catch (NullPointerException var10) {
         }

         if (this.closestTarget == null && this.mc.field_71439_g.field_71093_bK != 0 && this.firstRun) {
            this.firstRun = false;
            if ((Boolean)this.announceUsage.value) {
               Messages.sendMessagePrefix(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "BedAura" + TextFormatting.BLUE + "]" + TextFormatting.WHITE + " enabled, " + TextFormatting.WHITE + "waiting for target.");
            }
         }

         if (this.firstRun && this.closestTarget != null && this.mc.field_71439_g.field_71093_bK != 0) {
            this.firstRun = false;
            this.lastTickTargetName = this.closestTarget.func_70005_c_();
            if ((Boolean)this.announceUsage.value) {
               Messages.sendMessagePrefix(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "BedAura" + TextFormatting.BLUE + "]" + TextFormatting.WHITE + " enabled" + TextFormatting.WHITE + ", target: " + ChatFormatting.BLUE.toString() + this.lastTickTargetName);
            }
         }

         if (this.closestTarget != null && this.lastTickTargetName != null && !this.lastTickTargetName.equals(this.closestTarget.func_70005_c_())) {
            this.lastTickTargetName = this.closestTarget.func_70005_c_();
            if ((Boolean)this.announceUsage.value) {
               Messages.sendMessagePrefix(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "BedAura" + TextFormatting.BLUE + "]" + TextFormatting.WHITE + " New target: " + ChatFormatting.BLUE.toString() + this.lastTickTargetName);
            }
         }

         try {
            this.diffXZ = this.mc.field_71439_g.func_174791_d().func_72438_d(this.closestTarget.func_174791_d());
         } catch (NullPointerException var9) {
         }

         try {
            if (this.closestTarget != null) {
               this.nowTop = false;
               this.rotVar = 90.0F;
               BlockPos block1 = this.placeTarget;
               if (!this.canPlaceBed(block1)) {
                  this.rotVar = -90.0F;
                  this.nowTop = false;
               }

               BlockPos block2 = this.placeTarget;
               if (!this.canPlaceBed(block2)) {
                  this.rotVar = 180.0F;
                  this.nowTop = false;
               }

               BlockPos block3 = this.placeTarget;
               if (!this.canPlaceBed(block3)) {
                  this.rotVar = 0.0F;
                  this.nowTop = false;
               }

               BlockPos block4 = this.placeTarget;
               if (!this.canPlaceBed(block4)) {
                  this.rotVar = 0.0F;
                  this.nowTop = true;
               }

               BlockPos blockt1 = this.placeTarget;
               if (this.nowTop && !this.canPlaceBed(blockt1)) {
                  this.rotVar = -90.0F;
               }

               BlockPos blockt2 = this.placeTarget;
               if (this.nowTop && !this.canPlaceBed(blockt2)) {
                  this.rotVar = 180.0F;
               }

               BlockPos blockt3 = this.placeTarget;
               if (this.nowTop && !this.canPlaceBed(blockt3)) {
                  this.rotVar = 90.0F;
               }
            }

            this.mc.field_71441_e.field_147482_g.stream().filter((e) -> {
               return e instanceof TileEntityBed;
            }).filter((e) -> {
               return this.mc.field_71439_g.func_70011_f((double)e.func_174877_v().func_177958_n(), (double)e.func_174877_v().func_177956_o(), (double)e.func_174877_v().func_177952_p()) <= (double)(Integer)this.range.value;
            }).sorted(Comparator.comparing((e) -> {
               return this.mc.field_71439_g.func_70011_f((double)e.func_174877_v().func_177958_n(), (double)e.func_174877_v().func_177956_o(), (double)e.func_174877_v().func_177952_p());
            })).forEach((bed) -> {
               if (this.mc.field_71439_g.field_71093_bK != 0) {
                  this.mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItemOnBlock(bed.func_174877_v(), EnumFacing.UP, EnumHand.OFF_HAND, 0.0F, 0.0F, 0.0F));
               }

            });
            if (this.mc.field_71439_g.field_70173_aa % (Integer)this.placedelay.value == 0 && this.closestTarget != null) {
               this.findBeds();
               ++this.mc.field_71439_g.field_70173_aa;
               this.doDaMagic();
            }
         } catch (NullPointerException var8) {
            var8.printStackTrace();
         }

      }
   }

   private void doDaMagic() {
      if (this.diffXZ <= (double)(Integer)this.range.value) {
         for(int i = 0; i < 9 && this.bedSlot == -1; ++i) {
            ItemStack stack = this.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() instanceof ItemBed) {
               this.bedSlot = i;
               if (i != -1) {
                  this.mc.field_71439_g.field_71071_by.field_70461_c = this.bedSlot;
               }
               break;
            }
         }

         this.bedSlot = -1;
         if (this.blocksPlaced == 0 && this.mc.field_71439_g.field_71071_by.func_70301_a(this.mc.field_71439_g.field_71071_by.field_70461_c).func_77973_b() instanceof ItemBed) {
            this.mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(this.mc.field_71439_g, Action.START_SNEAKING));
            this.mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(this.rotVar, 0.0F, this.mc.field_71439_g.field_70122_E));
            this.placeBlock(new BlockPos(this.placeTarget), EnumFacing.DOWN);
            this.mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(this.mc.field_71439_g, Action.STOP_SNEAKING));
            this.blocksPlaced = 1;
            this.nowTop = false;
         }

         this.blocksPlaced = 0;
      }

   }

   private void findBeds() {
      if ((this.mc.field_71462_r == null || !(this.mc.field_71462_r instanceof GuiContainer)) && this.mc.field_71439_g.field_71071_by.func_70301_a(0).func_77973_b() != Items.field_151104_aV) {
         for(int i = 9; i < 36; ++i) {
            if (this.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_151104_aV) {
               this.mc.field_71442_b.func_187098_a(this.mc.field_71439_g.field_71069_bz.field_75152_c, i, 0, ClickType.SWAP, this.mc.field_71439_g);
               break;
            }
         }
      }

   }

   private boolean canPlaceBed(BlockPos pos) {
      return (this.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150350_a || this.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150324_C) && this.mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(pos)).isEmpty();
   }

   private void findClosestTarget() {
      List<EntityPlayer> playerList = this.mc.field_71441_e.field_73010_i;
      this.closestTarget = null;
      Iterator var2 = playerList.iterator();

      while(var2.hasNext()) {
         EntityPlayer target = (EntityPlayer)var2.next();
         if (target != this.mc.field_71439_g && !Friends.isFriend(target.func_70005_c_()) && isLiving(target) && !(target.func_110143_aJ() <= 0.0F)) {
            if (this.closestTarget == null) {
               this.closestTarget = target;
            } else if (this.mc.field_71439_g.func_70032_d(target) < this.mc.field_71439_g.func_70032_d(this.closestTarget)) {
               this.closestTarget = target;
            }
         }
      }

   }

   private void placeBlock(BlockPos pos, EnumFacing side) {
      BlockPos neighbour = pos.func_177972_a(side);
      EnumFacing opposite = side.func_176734_d();
      Vec3d hitVec = (new Vec3d(neighbour)).func_178787_e((new Vec3d(opposite.func_176730_m())).func_186678_a(0.5D));
      this.mc.field_71442_b.func_187099_a(this.mc.field_71439_g, this.mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
   }

   @SubscribeEvent
   public void render(RenderWorldLastEvent event) {
      if (this.placeTarget != null && this.mc.field_71441_e != null && this.closestTarget != null) {
         if ((Boolean)this.placeesp.value) {
            try {
               float posx = (float)this.placeTarget.func_177958_n();
               float posy = (float)this.placeTarget.func_177956_o();
               float posz = (float)this.placeTarget.func_177952_p();
               BloodHackTessellator.prepare("lines");
               BloodHackTessellator.draw_cube_line(posx, posy, posz, ColourUtils.genRainbow(), "all");
               if (this.rotVar == 90.0F) {
                  BloodHackTessellator.draw_cube_line(posx - 1.0F, posy, posz, ColourUtils.genRainbow(), "all");
               }

               if (this.rotVar == 0.0F) {
                  BloodHackTessellator.draw_cube_line(posx, posy, posz + 1.0F, ColourUtils.genRainbow(), "all");
               }

               if (this.rotVar == -90.0F) {
                  BloodHackTessellator.draw_cube_line(posx + 1.0F, posy, posz, ColourUtils.genRainbow(), "all");
               }

               if (this.rotVar == 180.0F) {
                  BloodHackTessellator.draw_cube_line(posx, posy, posz - 1.0F, ColourUtils.genRainbow(), "all");
               }
            } catch (Exception var5) {
            }

            BloodHackTessellator.release();
         }

      }
   }
}
