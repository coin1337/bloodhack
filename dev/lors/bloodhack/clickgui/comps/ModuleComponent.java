package dev.lors.bloodhack.clickgui.comps;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.module.BloodModules.hud.ClickGUI;
import dev.lors.bloodhack.utils.ColourUtils;
import dev.lors.bloodhack.utils.RenderUtil;
import java.io.IOException;
import java.util.Iterator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ModuleComponent extends Component {
   public Module module;
   public int offset;
   public boolean parExpand;
   ClickGUI clickGUI;

   public ModuleComponent(Module module, int x, int y, int width, int height) {
      super(x, y, width, height);
      this.clickGUI = (ClickGUI)BloodHack.moduleManager.getModuleByName("ClickGUI");
      this.width = Math.max(this.fr.func_78256_a(module.getName()) + 10, width);
      int count = this.offsetY;

      for(Iterator var7 = module.values.iterator(); var7.hasNext(); count += this.offsetY) {
         Value value = (Value)var7.next();
         ValueComponent comp = new ValueComponent(value, x + 1, count, width, y + count + height);
         comp.parent = this;
         this.components.add(comp);
      }

      ValueComponentKeybind keybind = new ValueComponentKeybind(module, x + 1, count + this.offsetY, width, y + count + height);
      keybind.parent = this;
      this.components.add(keybind);
      this.module = module;
   }

   public void render(int mouseX, int mouseY) {
      this.parExpand = true;
      int color = ColourUtils.toRGBA((Integer)this.clickGUI.r.value, (Integer)this.clickGUI.g.value, (Integer)this.clickGUI.b.value, (Integer)this.clickGUI.a.value);
      int bgColor = (Boolean)this.clickGUI.rainbow.value ? -872415232 : ColourUtils.toRGBA((Integer)this.clickGUI.br.value, (Integer)this.clickGUI.bg.value, (Integer)this.clickGUI.bb.value, (Integer)this.clickGUI.ba.value);
      color = (Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : color;
      GL11.glPushMatrix();
      RenderUtil.drawSexyRect((double)this.x, (double)(this.y + this.offsetY), (double)(this.x + this.width), (double)(this.y + this.height), (Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : bgColor, this.module.isToggled() ? color : bgColor);
      if (mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y + this.offsetY && mouseY < this.y + this.height) {
         RenderUtil.drawRect((double)this.x, (double)(this.y + this.offsetY), (double)(this.x + this.width), (double)(this.y + this.height), (Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : color);
      }

      this.fr.func_175063_a(this.module.getName(), (float)(this.x + 5), (float)(this.y + 16), -1);
      GL11.glPopMatrix();
      if (this.expanded) {
         int count = this.offsetY;
         Iterator var6 = this.components.iterator();

         while(true) {
            while(var6.hasNext()) {
               Component item = (Component)var6.next();
               item.offsetY = this.offsetY - 1;
               item.x = this.x;
               item.y = this.y + count;
               item.height = this.height;
               if (item instanceof ValueComponent && ((ValueComponent)item).value.isVisible()) {
                  ValueComponent value = (ValueComponent)item;
                  if (value.value.value instanceof String) {
                     item.width = Math.max(this.width, this.fr.func_78256_a(value.listening ? value.typeCache + "_" : value.value.name + ": " + value.value.getMeta()));
                  } else {
                     item.width = Math.max(this.fr.func_78256_a(value.value.value instanceof Boolean ? value.value.name : value.value.name + ": " + value.value.getMeta() + 10), this.width);
                  }

                  count += this.offsetY;
                  item.render(mouseX, mouseY);
               } else if (item instanceof ValueComponentKeybind) {
                  ValueComponentKeybind value = (ValueComponentKeybind)item;
                  if (value.module != null) {
                     item.width = Math.max(this.fr.func_78256_a(value.listening ? "Listening..." : "Bind: " + Keyboard.getKeyName(this.module.getKey())), this.width);
                  }

                  count += this.offsetY;
                  item.render(mouseX, mouseY);
               }
            }

            this.offset = count - this.offsetY;
            break;
         }
      } else {
         this.offset = 0;
      }

      super.render(mouseX, mouseY);
   }

   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
      if (mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y + this.offsetY && mouseY < this.y + this.height) {
         if (mouseButton == 0) {
            this.module.toggle();
         }

         if (mouseButton == 1) {
            this.expanded = !this.expanded;
         }
      }

      if (this.expanded) {
         Iterator var4 = this.components.iterator();

         while(var4.hasNext()) {
            Component item = (Component)var4.next();
            item.mouseClick(mouseX, mouseY, mouseButton);
         }
      }

   }

   public void keyTyped(char typedChar, int keyCode) throws IOException {
      if (this.expanded) {
         Iterator var3 = this.components.iterator();

         while(var3.hasNext()) {
            Component item = (Component)var3.next();
            item.keyTyped(typedChar, keyCode);
         }
      }

   }
}
