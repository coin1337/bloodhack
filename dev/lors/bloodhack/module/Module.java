package dev.lors.bloodhack.module;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.managers.Value;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Module {
   public boolean toggled;
   public List<Value> values = new ArrayList();
   protected Minecraft mc = Minecraft.func_71410_x();
   protected int tickDelay;
   boolean drawn;
   private String name;
   private String displayName;
   private Category category;
   private Integer key;

   public Module(String name, Category category) {
      this.name = name;
      this.category = category;
      this.toggled = false;
      this.key = 0;
      this.drawn = true;
      this.setup();
   }

   public void registerSettings() {
      this.selfSettings();
   }

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
      BloodHack.EVENT_BUS.subscribe(this);
   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
      BloodHack.EVENT_BUS.unsubscribe(this);
   }

   @SubscribeEvent
   public void gameTickEvent(TickEvent event) {
      if (this.isToggled()) {
         this.onUpdate();
      }

   }

   public void onUpdate() {
   }

   public void selfSettings() {
   }

   public void onToggle() {
   }

   public void toggle() {
      this.toggled = !this.toggled;
      this.onToggle();
      if (this.toggled) {
         this.onEnable();
      } else {
         this.onDisable();
      }

   }

   public Integer getKey() {
      return this.key;
   }

   public void setKey(Integer key) {
      this.key = key;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Category getCategory() {
      return this.category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }

   public boolean isToggled() {
      return this.toggled;
   }

   public String getDisplayName() {
      return this.displayName == null ? this.name : this.displayName;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public void setDrawn(boolean d) {
      this.drawn = d;
   }

   public void setup() {
   }
}
