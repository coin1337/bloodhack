package dev.lors.bloodhack.module.BloodModules.combat;

import dev.lors.bloodhack.event.events.EventEntityRemoved;
import dev.lors.bloodhack.event.events.PacketEvent;
import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.util.Pair;
import dev.lors.bloodhack.util.Timer;
import dev.lors.bloodhack.utils.BlockUtils;
import dev.lors.bloodhack.utils.BloodHackTessellator;
import dev.lors.bloodhack.utils.CrystalUtil;
import dev.lors.bloodhack.utils.EntityUtil;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

public class BetterAC extends Module {
   private final ConcurrentHashMap<EntityEnderCrystal, Integer> attackedCrystals = new ConcurrentHashMap();
   private final Timer timer = new Timer();
   private final Timer removeVisualTimer = new Timer();
   @EventHandler
   private final Listener<PacketEvent.Receive> receive_listener = new Listener((event) -> {
      if (event.getPacket() instanceof SPacketSoundEffect) {
         SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
         if (packet.func_186977_b() == SoundCategory.BLOCKS && packet.func_186978_a() == SoundEvents.field_187539_bB) {
            Iterator var3 = this.mc.field_71441_e.field_72996_f.iterator();

            while(var3.hasNext()) {
               Entity e = (Entity)var3.next();
               if (e instanceof EntityEnderCrystal && e.func_70011_f(packet.func_149207_d(), packet.func_149211_e(), packet.func_149210_f()) <= 6.0D) {
                  e.func_70106_y();
               }
            }
         }
      }

   }, new Predicate[0]);
   Value<Boolean> placeCA = new Value("Place", true);
   Value<Boolean> breakCA = new Value("Break", true);
   Value<Boolean> autoSwitch = new Value("Auto-Switch", true);
   Value<Boolean> antiWeakness = new Value("Anti-Weakness");
   Value<Boolean> antiSui = new Value("Anti-Suicide", true);
   Value<Boolean> antiStuck = new Value("Anti-Stuck", true);
   Value<Boolean> newServ = new Value("1.13+ Mode", false);
   Value<Boolean> faceplace = new Value("FacePlace Mode", false);
   Value<Integer> placeRange = new Value("Place Range", 6, 1, 7);
   Value<Integer> placeDelay = new Value("Place Delay", 0, 0, 7);
   Value<Integer> breakRange = new Value("Break Range", 8, 1, 7);
   Value<Integer> breakDelay = new Value("Break Delay", 1, 0, 7);
   Value<Integer> wallRange = new Value("Wall Range", 6, 1, 6);
   Value<Integer> minDamagePlace = new Value("Min Damage Place", 8, 0, 7);
   Value<Integer> minDamageBreak = new Value("Min Damage Break", 6, 0, 7);
   Value<Integer> selfDamage = new Value("Max Self Damage", 6, 0, 20);
   Value<Integer> breakAttempts = new Value("Break Attempts", 2, 1, 6);
   Value<Integer> faceplaceHealth = new Value("Faceplace Min Health", 8, 0, 36);
   Value<Integer> r = new Value("Red", 255, 0, 255);
   Value<Integer> g = new Value("Green", 255, 0, 255);
   Value<Integer> b = new Value("Blue", 255, 0, 255);
   private EntityPlayer autoEzTarget = null;
   private BlockPos renderBlockInit;
   private double renderDamageValue;
   private float yaw;
   private float pitch;
   private boolean alreadyAttacking = false;
   private boolean placeTimeoutFlag = false;
   private boolean isRotating;
   private boolean didAnything;
   private boolean outline;
   private boolean solid;
   private int chainStep = 0;
   private int currentChainIndex = 0;
   private int placeTimeout;
   private int breakTimeout;
   private int breakDelayCounter;
   private int placeDelayCounter;
   @EventHandler
   private final Listener<EventEntityRemoved> on_entity_removed = new Listener((event) -> {
      if (event.get_entity() instanceof EntityEnderCrystal) {
         this.attackedCrystals.remove(event.get_entity());
      }

   }, new Predicate[0]);

   public BetterAC() {
      super("BetterAutoCrystal", Category.COMBAT);
   }

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
      this.placeTimeout = (Integer)this.placeDelay.value;
      this.breakTimeout = (Integer)this.breakDelay.value;
      this.placeTimeoutFlag = false;
      this.isRotating = false;
      this.autoEzTarget = null;
      this.chainStep = 0;
      this.currentChainIndex = 0;
      this.timer.reset();
      this.removeVisualTimer.reset();
   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
      this.renderBlockInit = null;
      this.autoEzTarget = null;
   }

   public void onUpdate() {
      this.do_ca();
   }

   public void do_ca() {
      this.didAnything = false;
      if (this.mc.field_71439_g != null && this.mc.field_71441_e != null) {
         if (this.removeVisualTimer.passed(1000.0D)) {
            this.removeVisualTimer.reset();
            this.attackedCrystals.clear();
         }

         if (!this.checkPause()) {
            if ((Boolean)this.placeCA.value && this.placeDelayCounter > this.placeTimeout) {
               this.place_crystal();
            }

            if ((Boolean)this.breakCA.value && this.breakDelayCounter > this.breakTimeout) {
               this.break_crystal();
            }

            if (!this.didAnything) {
               this.autoEzTarget = null;
               this.isRotating = false;
            }

            if (this.timer.passed(1000.0D)) {
               this.timer.reset();
               this.chainStep = 0;
            }

            ++this.breakDelayCounter;
            ++this.placeDelayCounter;
         }
      }
   }

   public EntityEnderCrystal get_best_crystal() {
      double best_damage = 0.0D;
      double maximum_damage_self = (double)(Integer)this.selfDamage.value;
      double best_distance = 0.0D;
      EntityEnderCrystal best_crystal = null;

      try {
         Iterator var10 = this.mc.field_71441_e.field_72996_f.iterator();

         label129:
         while(true) {
            EntityEnderCrystal crystal;
            do {
               do {
                  do {
                     Entity c;
                     do {
                        if (!var10.hasNext()) {
                           return best_crystal;
                        }

                        c = (Entity)var10.next();
                     } while(!(c instanceof EntityEnderCrystal));

                     crystal = (EntityEnderCrystal)c;
                  } while(this.mc.field_71439_g.func_70032_d(crystal) > (float)!this.mc.field_71439_g.func_70685_l(crystal) ? (Integer)this.wallRange.value : (Integer)this.breakRange.value);
               } while(crystal.field_70128_L);
            } while(this.attackedCrystals.containsKey(crystal) && (Integer)this.attackedCrystals.get(crystal) > 5 && (Boolean)this.antiStuck.value);

            Iterator var13 = this.mc.field_71441_e.field_73010_i.iterator();

            while(true) {
               EntityPlayer target;
               double target_damage;
               double self_damage;
               do {
                  do {
                     double minimum_damage;
                     do {
                        do {
                           do {
                              Entity player;
                              do {
                                 do {
                                    do {
                                       if (!var13.hasNext()) {
                                          if (this.mc.field_71439_g.func_70068_e(crystal) > best_distance) {
                                             best_distance = this.mc.field_71439_g.func_70068_e(crystal);
                                             best_crystal = crystal;
                                          }
                                          continue label129;
                                       }

                                       player = (Entity)var13.next();
                                    } while(player == this.mc.field_71439_g);
                                 } while(!(player instanceof EntityPlayer));
                              } while(player.func_70032_d(this.mc.field_71439_g) >= 11.0F);

                              target = (EntityPlayer)player;
                           } while(target.field_70128_L);
                        } while(target.func_110143_aJ() <= 0.0F);

                        boolean no_place = false;
                        if ((!(target.func_110143_aJ() < (float)(Integer)this.faceplaceHealth.value) || !(Boolean)this.faceplace.value || no_place) && no_place) {
                           minimum_damage = (double)(Integer)this.minDamageBreak.value;
                        } else {
                           minimum_damage = 2.0D;
                        }

                        target_damage = (double)CrystalUtil.calculateDamage(crystal, target);
                     } while(target_damage < minimum_damage);

                     self_damage = (double)CrystalUtil.calculateDamage(crystal, this.mc.field_71439_g);
                  } while(self_damage > maximum_damage_self);
               } while((Boolean)this.antiSui.value && (double)(this.mc.field_71439_g.func_110143_aJ() + this.mc.field_71439_g.func_110139_bj()) - self_damage <= 0.5D);

               if (target_damage > best_damage) {
                  this.autoEzTarget = target;
                  best_damage = target_damage;
                  best_crystal = crystal;
               }
            }
         }
      } catch (ConcurrentModificationException var21) {
         return best_crystal;
      }
   }

   public BlockPos get_best_block() {
      if (this.get_best_crystal() != null) {
         this.placeTimeoutFlag = true;
         return null;
      } else if (this.placeTimeoutFlag) {
         this.placeTimeoutFlag = false;
         return null;
      } else {
         List<Pair<Double, BlockPos>> damage_blocks = new ArrayList();
         double best_damage = 0.0D;
         double maximum_damage_self = (double)(Integer)this.selfDamage.value;
         BlockPos best_block = null;
         List<BlockPos> blocks = CrystalUtil.possiblePlacePositions((float)(Integer)this.placeRange.value, (Boolean)this.newServ.value, true);
         Iterator var10 = this.mc.field_71441_e.field_73010_i.iterator();

         label123:
         while(var10.hasNext()) {
            Entity player = (Entity)var10.next();
            Iterator var12 = blocks.iterator();

            while(true) {
               BlockPos block;
               EntityPlayer target;
               double target_damage;
               double self_damage;
               do {
                  do {
                     double minimum_damage;
                     do {
                        do {
                           do {
                              do {
                                 do {
                                    do {
                                       do {
                                          do {
                                             if (!var12.hasNext()) {
                                                continue label123;
                                             }

                                             block = (BlockPos)var12.next();
                                          } while(player == this.mc.field_71439_g);
                                       } while(!(player instanceof EntityPlayer));
                                    } while(player.func_70032_d(this.mc.field_71439_g) >= 11.0F);
                                 } while(!BlockUtils.rayTracePlaceCheck(block, false));
                              } while(!BlockUtils.canSeeBlock(block) && this.mc.field_71439_g.func_70011_f((double)block.func_177958_n(), (double)block.func_177956_o(), (double)block.func_177952_p()) > (double)(Integer)this.wallRange.value);

                              target = (EntityPlayer)player;
                           } while(target.field_70128_L);
                        } while(target.func_110143_aJ() <= 0.0F);

                        boolean no_place = (Boolean)this.faceplace.value && this.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151048_u;
                        if ((!(target.func_110143_aJ() < (float)(Integer)this.faceplaceHealth.value) || !(Boolean)this.faceplace.value || no_place) && no_place) {
                           minimum_damage = (double)(Integer)this.minDamagePlace.value;
                        } else {
                           minimum_damage = 2.0D;
                        }

                        target_damage = (double)CrystalUtil.calculateDamage((double)block.func_177958_n() + 0.5D, (double)block.func_177956_o() + 1.0D, (double)block.func_177952_p() + 0.5D, target);
                     } while(target_damage < minimum_damage);

                     self_damage = (double)CrystalUtil.calculateDamage((double)block.func_177958_n() + 0.5D, (double)block.func_177956_o() + 1.0D, (double)block.func_177952_p() + 0.5D, this.mc.field_71439_g);
                  } while(self_damage > maximum_damage_self);
               } while((Boolean)this.antiSui.value && (double)(this.mc.field_71439_g.func_110143_aJ() + this.mc.field_71439_g.func_110139_bj()) - self_damage <= 0.5D);

               if (target_damage > best_damage) {
                  best_damage = target_damage;
                  best_block = block;
                  this.autoEzTarget = target;
               }
            }
         }

         blocks.clear();
         if (this.chainStep == 1) {
            this.currentChainIndex = 3;
         } else if (this.chainStep > 1) {
            --this.currentChainIndex;
         }

         this.renderDamageValue = best_damage;
         this.renderBlockInit = best_block;
         this.sortBestBlocks(damage_blocks);
         return best_block;
      }
   }

   public List<Pair<Double, BlockPos>> sortBestBlocks(List<Pair<Double, BlockPos>> list) {
      List<Pair<Double, BlockPos>> new_list = new ArrayList();
      double damage_cap = 1000.0D;

      for(int i = 0; i < list.size(); ++i) {
         double biggest_dam = 0.0D;
         Pair<Double, BlockPos> best_pair = null;
         Iterator var9 = list.iterator();

         while(var9.hasNext()) {
            Pair<Double, BlockPos> pair = (Pair)var9.next();
            if ((Double)pair.getKey() > biggest_dam && (Double)pair.getKey() < damage_cap) {
               best_pair = pair;
            }
         }

         if (best_pair != null) {
            damage_cap = (Double)best_pair.getKey();
            new_list.add(best_pair);
         }
      }

      return new_list;
   }

   public void place_crystal() {
      BlockPos target_block = this.get_best_block();
      if (target_block != null) {
         this.placeDelayCounter = 0;
         this.alreadyAttacking = false;
         boolean offhand_check = false;
         if (this.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_185158_cP) {
            if (this.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_185158_cP && (Boolean)this.autoSwitch.value) {
               if (this.find_crystals_hotbar() == -1) {
                  return;
               }

               this.mc.field_71439_g.field_71071_by.field_70461_c = this.find_crystals_hotbar();
               return;
            }
         } else {
            offhand_check = true;
         }

         ++this.chainStep;
         this.didAnything = true;
         this.timer.reset();
         BlockUtils.placeCrystalOnBlock(target_block, offhand_check ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
      }
   }

   private int find_crystals_hotbar() {
      for(int i = 0; i < 9; ++i) {
         if (this.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_185158_cP) {
            return i;
         }
      }

      return -1;
   }

   public void break_crystal() {
      EntityEnderCrystal crystal = this.get_best_crystal();
      if (crystal != null) {
         if ((Boolean)this.antiWeakness.value && this.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t)) {
            boolean should_weakness = true;
            if (this.mc.field_71439_g.func_70644_a(MobEffects.field_76420_g) && ((PotionEffect)Objects.requireNonNull(this.mc.field_71439_g.func_70660_b(MobEffects.field_76420_g))).func_76458_c() == 2) {
               should_weakness = false;
            }

            if (should_weakness) {
               if (!this.alreadyAttacking) {
                  this.alreadyAttacking = true;
               }

               int new_slot = -1;

               for(int i = 0; i < 9; ++i) {
                  ItemStack stack = this.mc.field_71439_g.field_71071_by.func_70301_a(i);
                  if (stack.func_77973_b() instanceof ItemSword || stack.func_77973_b() instanceof ItemTool) {
                     new_slot = i;
                     this.mc.field_71442_b.func_78765_e();
                     break;
                  }
               }

               if (new_slot != -1) {
                  this.mc.field_71439_g.field_71071_by.field_70461_c = new_slot;
               }
            }
         }

         this.didAnything = true;

         for(int i = 0; i < (Integer)this.breakAttempts.value; ++i) {
            EntityUtil.attackEntity(crystal, false);
         }

         this.addAttackedCrystal(crystal);
         this.breakDelayCounter = 0;
      }
   }

   private void addAttackedCrystal(EntityEnderCrystal crystal) {
      if (this.attackedCrystals.containsKey(crystal)) {
         int value = (Integer)this.attackedCrystals.get(crystal);
         this.attackedCrystals.put(crystal, value + 1);
      } else {
         this.attackedCrystals.put(crystal, 1);
      }

   }

   public boolean checkPause() {
      if (this.find_crystals_hotbar() == -1 && this.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_185158_cP) {
         return true;
      } else if (this.mc.field_71474_y.field_74312_F.func_151470_d() && this.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemPickaxe) {
         this.renderBlockInit = null;
         return true;
      } else {
         return false;
      }
   }

   public void render_block(BlockPos pos) {
      float h = 1.0F;
      if (this.solid) {
         BloodHackTessellator.prepare("quads");
         BloodHackTessellator.draw_cube(BloodHackTessellator.get_buffer_build(), (float)pos.func_177958_n(), (float)pos.func_177956_o(), (float)pos.func_177952_p(), 1.0F, h, 1.0F, (Integer)this.r.value, (Integer)this.g.value, (Integer)this.b.value, 255, "all");
         BloodHackTessellator.release();
      }

      if (this.outline) {
         BloodHackTessellator.prepare("lines");
         BloodHackTessellator.draw_cube_line(BloodHackTessellator.get_buffer_build(), (float)pos.func_177958_n(), (float)pos.func_177956_o(), (float)pos.func_177952_p(), 1.0F, h, 1.0F, (Integer)this.r.value, (Integer)this.g.value, (Integer)this.b.value, 255, "all");
         BloodHackTessellator.release();
      }

   }
}
