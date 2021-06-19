package dev.lors.bloodhack.clickgui.comps;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.module.BloodModules.hud.ClickGUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class Component {
   public int FLAG_SLIDER = 105;
   public int FLAG_ENUM = 1056;
   public int FLAG_BOOLEAN = 4919;
   ClickGUI clickGUI;
   FontRenderer fr;
   Minecraft mc;
   List<Component> components;
   Component parent;
   int x;
   int y;
   int dx;
   int dy;
   int width;
   int height;
   int offsetX;
   int offsetY;
   boolean dragging;
   boolean expanded;
   int flags;

   public Component(int x, int y, int width, int height) {
      this.clickGUI = (ClickGUI)BloodHack.moduleManager.getModuleByName("ClickGUI");
      this.fr = Minecraft.func_71410_x().field_71466_p;
      this.mc = Minecraft.func_71410_x();
      this.components = new ArrayList();
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public void render(int mouseX, int mouseY) {
   }

   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
      if (!(this instanceof Frame) && this.expanded) {
         Iterator var4 = this.components.iterator();

         while(var4.hasNext()) {
            Component item = (Component)var4.next();
            item.mouseClick(mouseX, mouseY, mouseButton);
         }
      }

   }

   public void mouseRelease(int mouseX, int mouseY, int state) {
      if (!(this instanceof Frame) && this.expanded) {
         Iterator var4 = this.components.iterator();

         while(var4.hasNext()) {
            Component item = (Component)var4.next();
            item.mouseRelease(mouseX, mouseY, state);
         }
      }

   }

   public void mouseClickMove(int mouseX, int mouseY, int mouseButton, float timeSinceLastCLick) {
      if (!(this instanceof Frame) && this.expanded) {
         Iterator var5 = this.components.iterator();

         while(var5.hasNext()) {
            Component item = (Component)var5.next();
            item.mouseClickMove(mouseX, mouseY, mouseButton, timeSinceLastCLick);
         }
      }

   }

   public void mouseMove(int mouseX, int mouseY) {
      if (!(this instanceof Frame) && this.expanded) {
         Iterator var3 = this.components.iterator();

         while(var3.hasNext()) {
            Component item = (Component)var3.next();
            item.mouseMove(mouseX, mouseY);
         }
      }

   }

   public boolean hasFlag(int flag) {
      return (this.flags & flag) != 0;
   }

   public void keyTyped(char typedChar, int keyCode) throws IOException {
   }
}
