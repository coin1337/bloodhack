package dev.lors.bloodhack;

import dev.lors.bloodhack.capes.CapeUtil;
import dev.lors.bloodhack.clickgui.ClickGUI;
import dev.lors.bloodhack.command.CommandManager;
import dev.lors.bloodhack.event.Adapter;
import dev.lors.bloodhack.managers.ConfigManager;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.module.ModuleManager;
import dev.lors.bloodhack.util.font.CFontRenderer;
import java.util.Iterator;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

@Mod(
   name = "Blood Hack",
   modid = "bloodhack",
   version = "b1.1"
)
public class BloodHack {
   public static final String name = "Blood Hack";
   public static final String version = "b1.1";
   public static final String appid = "764915024422240296";
   public static final EventBus EVENT_BUS = new EventManager();
   public static String prefix = "*";
   public static ConfigManager configManager;
   public static ModuleManager moduleManager;
   public static ClickGUI gui;
   public static CFontRenderer fontRenderer;
   @Instance
   private static BloodHack INSTANCE;
   protected Minecraft mc2;

   public BloodHack() {
      INSTANCE = this;
   }

   public static BloodHack getInstance() {
      return INSTANCE;
   }

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      Display.setTitle("Blood Hack b1.1");
   }

   @EventHandler
   public void Init(FMLInitializationEvent event) throws IllegalAccessException {
      moduleManager = new ModuleManager();
      configManager = new ConfigManager();
      gui = new ClickGUI();
      CommandManager.init();
      MinecraftForge.EVENT_BUS.register(new CommandManager());
      MinecraftForge.EVENT_BUS.register(this);
      MinecraftForge.EVENT_BUS.register(new Adapter());
      CapeUtil.startAnimationLoop();
   }

   @SubscribeEvent
   public void onKeyPress(KeyInputEvent event) {
      Iterator var2 = moduleManager.getModules().iterator();

      while(var2.hasNext()) {
         Module m = (Module)var2.next();
         if (Keyboard.isKeyDown(m.getKey())) {
            m.toggle();
         }
      }

   }
}
