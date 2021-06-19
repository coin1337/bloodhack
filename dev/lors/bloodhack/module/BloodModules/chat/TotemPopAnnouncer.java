package dev.lors.bloodhack.module.BloodModules.chat;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.event.events.PacketEvent;
import dev.lors.bloodhack.event.events.TotemPopEvent;
import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.managers.friends.Friends;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.common.MinecraftForge;

public class TotemPopAnnouncer extends Module {
   @EventHandler
   public Listener<TotemPopEvent> listListener;
   @EventHandler
   public Listener<PacketEvent.Receive> popListener;
   Value<Boolean> greenText = new Value("Use Greentext", false);
   private HashMap<String, Integer> playerList = new HashMap();
   private boolean isDead = false;

   public TotemPopAnnouncer() {
      super("TotemPopCounter", Category.MISC);
      int[] popCounter = new int[]{0};
      this.listListener = new Listener((event) -> {
         if (this.playerList == null) {
            this.playerList = new HashMap();
         }

         if (this.playerList.get(event.getEntity().func_70005_c_()) == null) {
            this.playerList.put(event.getEntity().func_70005_c_(), 1);
            if (event.getEntity().func_70005_c_() != this.mc.field_71439_g.func_70005_c_() && !Friends.isFriend(event.getEntity().func_70005_c_())) {
               if ((Boolean)this.greenText.value) {
                  this.mc.field_71439_g.func_71165_d(">" + event.getEntity().func_70005_c_() + " popped 1 totem");
               } else {
                  this.mc.field_71439_g.func_71165_d(event.getEntity().func_70005_c_() + " popped 1 totem");
               }
            }
         } else if (this.playerList.get(event.getEntity().func_70005_c_()) != null) {
            popCounter[0] = (Integer)this.playerList.get(event.getEntity().func_70005_c_());
            int var10002 = popCounter[0]++;
            this.playerList.put(event.getEntity().func_70005_c_(), popCounter[0]);
            if (event.getEntity().func_70005_c_() != this.mc.field_71439_g.func_70005_c_() && !Friends.isFriend(event.getEntity().func_70005_c_())) {
               if ((Boolean)this.greenText.value) {
                  this.mc.field_71439_g.func_71165_d(">" + event.getEntity().func_70005_c_() + " popped " + popCounter[0] + " totems");
               } else {
                  this.mc.field_71439_g.func_71165_d(event.getEntity().func_70005_c_() + " popped " + popCounter[0] + " totems");
               }
            }
         }

      }, new Predicate[0]);
      SPacketEntityStatus[] packet = new SPacketEntityStatus[1];
      Entity[] entity = new Entity[1];
      this.popListener = new Listener((event) -> {
         if (this.mc.field_71439_g != null && event.getPacket() instanceof SPacketEntityStatus) {
            packet[0] = (SPacketEntityStatus)event.getPacket();
            if (packet[0].func_149160_c() == 35) {
               entity[0] = packet[0].func_149161_a(this.mc.field_71441_e);
               if (this.selfCheck(entity[0].func_70005_c_())) {
                  BloodHack.EVENT_BUS.post(new TotemPopEvent(entity[0]));
               }
            }
         }

      }, new Predicate[0]);
   }

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
   }

   public void onUpdate() {
      if (!this.isDead && 0.0F >= this.mc.field_71439_g.func_110143_aJ()) {
         this.isDead = true;
         this.playerList.clear();
      } else {
         if (this.isDead && 0.0F < this.mc.field_71439_g.func_110143_aJ()) {
            this.isDead = false;
         }

         Iterator var1 = this.mc.field_71441_e.field_73010_i.iterator();

         while(var1.hasNext()) {
            EntityPlayer player = (EntityPlayer)var1.next();
            if (0.0F >= player.func_110143_aJ() && this.selfCheck(player.func_70005_c_()) && this.playerList.containsKey(player.func_70005_c_())) {
               if (player.func_70005_c_() != this.mc.field_71439_g.func_70005_c_() && !Friends.isFriend(player.func_70005_c_())) {
                  if ((Boolean)this.greenText.value) {
                     this.mc.field_71439_g.func_71165_d(">" + player.func_70005_c_() + " died after popping " + this.playerList.get(player.func_70005_c_()) + " totems");
                  } else {
                     this.mc.field_71439_g.func_71165_d(player.func_70005_c_() + " died after popping " + this.playerList.get(player.func_70005_c_()) + " totems");
                  }
               }

               this.playerList.remove(player.func_70005_c_(), this.playerList.get(player.func_70005_c_()));
            }
         }

      }
   }

   private boolean selfCheck(String name) {
      return !this.isDead && (name.equalsIgnoreCase(this.mc.field_71439_g.func_70005_c_()) || !name.equalsIgnoreCase(this.mc.field_71439_g.func_70005_c_()));
   }
}
