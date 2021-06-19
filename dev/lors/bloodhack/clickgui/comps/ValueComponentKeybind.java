package dev.lors.bloodhack.clickgui.comps;

import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.utils.ColourUtils;
import dev.lors.bloodhack.utils.RenderUtil;
import java.io.IOException;
import java.util.Iterator;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class ValueComponentKeybind extends Component {
   Module module;
   boolean listening;

   public ValueComponentKeybind(Module module, int x, int y, int width, int height) {
      super(x, y, width, height);
      this.module = module;
   }

   public void render(int mouseX, int mouseY) {
      int bgColor = (Boolean)this.clickGUI.rainbow.value ? -872415232 : ColourUtils.toRGBA((Integer)this.clickGUI.br.value, (Integer)this.clickGUI.bg.value, (Integer)this.clickGUI.bb.value, (Integer)this.clickGUI.ba.value);
      int color = ColourUtils.toRGBA((Integer)this.clickGUI.r.value, (Integer)this.clickGUI.g.value, (Integer)this.clickGUI.b.value, (Integer)this.clickGUI.a.value);
      RenderUtil.drawSexyRect((double)this.x, (double)(this.y + this.offsetY), (double)(this.x + this.width), (double)(this.y + this.height), (Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : bgColor, bgColor);
      Gui.func_73734_a(this.x, this.y + this.offsetY, this.x + this.width, this.y + this.height, (Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : color);
      if (mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y + this.offsetY && mouseY < this.y + this.height) {
         int[] col = ColourUtils.toRGBAArray(ColourUtils.genRainbow());
         int finalCol = ColourUtils.toRGBA(col[0], col[1], col[2], Math.max(0, col[3] - 10));
         Gui.func_73734_a(this.x, this.y + this.offsetY, this.x + this.width, this.y + this.height, (Boolean)this.clickGUI.rainbow.value ? finalCol : color);
      }

      this.fr.func_175063_a(this.listening ? "Listening..." : "Bind: " + Keyboard.getKeyName(this.module.getKey()), (float)(this.x + 5), (float)(this.y + 16), -1);
      if (this.expanded) {
         Iterator var7 = this.components.iterator();

         while(var7.hasNext()) {
            Component item = (Component)var7.next();
            item.render(mouseX, mouseY);
         }
      }

      super.render(mouseX, mouseY);
   }

   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
      if (mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y + this.offsetY && mouseY < this.y + this.height && mouseButton == 0) {
         this.listening = true;
      }

   }

   public void keyTyped(char typedChar, int keyCode) throws IOException {
      if (this.listening) {
         this.module.setKey(keyCode);
         this.listening = false;
      }

   }
}
