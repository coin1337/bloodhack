package dev.lors.bloodhack.clickgui.comps;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.module.Category;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.module.BloodModules.hud.ClickGUI;
import dev.lors.bloodhack.utils.ColourUtils;
import dev.lors.bloodhack.utils.RenderUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Frame extends Component {
   List<Component> components = new ArrayList();
   int offsetX;
   int offsetY;
   int offset;
   boolean dragging;
   ClickGUI clickGUI;
   private final Category category;
   private boolean expanded;

   public Frame(Category category, int x, int y) {
      super(x, y, 70, 27);
      this.clickGUI = (ClickGUI)BloodHack.moduleManager.getModuleByName("ClickGUI");
      this.category = category;
      this.x = x;
      this.y = y;
      this.offsetY = 13;
      int count = this.offsetY;

      for(Iterator var5 = BloodHack.moduleManager.getModulesByCategory(category).iterator(); var5.hasNext(); count += this.offsetY) {
         Module module = (Module)var5.next();
         Component comp = new ModuleComponent(module, x, count, this.width, y + count + this.height);
         comp.parent = this;
         this.components.add(comp);
      }

   }

   public void render(int mouseX, int mouseY) {
      if (Mouse.getEventDWheel() > 0) {
         --this.y;
      } else if (Mouse.getEventDWheel() < 0) {
         ++this.y;
      }

      if (this.dragging) {
         this.x = mouseX - this.dx;
         this.y = mouseY - this.dy;
      }

      int color = ColourUtils.toRGBA((Integer)this.clickGUI.r.value, (Integer)this.clickGUI.g.value, (Integer)this.clickGUI.b.value, (Integer)this.clickGUI.a.value);
      int bgColor = (Boolean)this.clickGUI.rainbow.value ? -872415232 : ColourUtils.toRGBA((Integer)this.clickGUI.br.value, (Integer)this.clickGUI.bg.value, (Integer)this.clickGUI.bb.value, (Integer)this.clickGUI.ba.value);
      GL11.glPushMatrix();
      RenderUtil.drawSexyRect((double)this.x, (double)(this.y + this.offsetY), (double)(this.x + this.width + 32), (double)(this.y + this.height), (Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : bgColor, bgColor);
      if (mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y + this.offsetY && mouseY < this.y + this.height) {
         RenderUtil.drawRect((double)this.x, (double)(this.y + this.offsetY), (double)(this.x + this.width + 32), (double)(this.y + this.height), (Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : color);
      }

      this.fr.func_175063_a(this.category.name(), (float)(this.x + 5), (float)(this.y + 16), -1);
      Iterator var5;
      Component item;
      if (this.expanded) {
         var5 = this.components.iterator();

         while(var5.hasNext()) {
            item = (Component)var5.next();
            item.render(mouseX, mouseY);
         }
      } else {
         for(var5 = this.components.iterator(); var5.hasNext(); ((ModuleComponent)item).parExpand = false) {
            item = (Component)var5.next();
         }
      }

      GL11.glPopMatrix();
      int count = this.offsetY;

      for(Iterator var9 = this.components.iterator(); var9.hasNext(); count += this.offsetY) {
         Component item = (Component)var9.next();
         if (item instanceof ModuleComponent) {
            item.offsetY = this.offsetY + 1;
            item.x = this.x;
            item.y = this.y + count;
            item.height = this.height;
            item.width = this.width + 32;
            count += ((ModuleComponent)item).offset;
         }
      }

   }

   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
      if (mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y - this.offsetY && mouseY < this.y + this.height) {
         if (mouseButton == 1) {
            this.expanded = !this.expanded;
         }

         if (mouseButton == 0) {
            this.dragging = true;
         }

         this.dx = mouseX - this.x;
         this.dy = mouseY - this.y;
      }

      if (this.expanded) {
         Iterator var4 = this.components.iterator();

         while(var4.hasNext()) {
            Component item = (Component)var4.next();
            item.mouseClick(mouseX, mouseY, mouseButton);
         }
      }

   }

   public void mouseRelease(int mouseX, int mouseY, int state) {
      if (this.dragging) {
         this.dragging = false;
      }

      Iterator var4 = this.components.iterator();

      while(var4.hasNext()) {
         Component item = (Component)var4.next();
         item.mouseRelease(mouseX, mouseY, state);
      }

   }

   public void mouseClickMove(int mouseX, int mouseY, int mouseButton, float timeSinceLastCLick) {
      Iterator var5 = this.components.iterator();

      while(var5.hasNext()) {
         Component item = (Component)var5.next();
         item.mouseClickMove(mouseX, mouseY, mouseButton, timeSinceLastCLick);
      }

   }

   public void mouseMove(int mouseX, int mouseY) {
      Iterator var3 = this.components.iterator();

      while(var3.hasNext()) {
         Component item = (Component)var3.next();
         item.mouseMove(mouseX, mouseY);
      }

   }

   public void keyTyped(char typedChar, int keyCode) throws IOException {
      Iterator var3 = this.components.iterator();

      while(var3.hasNext()) {
         Component component = (Component)var3.next();
         component.keyTyped(typedChar, keyCode);
      }

   }
}
