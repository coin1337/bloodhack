package dev.lors.bloodhack.clickgui.comps;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.managers.Value;
import dev.lors.bloodhack.module.BloodModules.hud.ClickGUI;
import dev.lors.bloodhack.utils.ColourUtils;
import dev.lors.bloodhack.utils.RenderUtil;
import java.io.IOException;
import java.util.Iterator;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class ValueComponent extends Component {
   public Value value;
   public int offset;
   public boolean slider;
   boolean hasSlider;
   float sliderWidth;
   boolean listening;
   String typeCache = "";
   ClickGUI clickGUI;

   public ValueComponent(Value value, int x, int y, int width, int height) {
      super(x, y, width, height);
      this.clickGUI = (ClickGUI)BloodHack.moduleManager.getModuleByName("ClickGUI");
      this.width = Math.max(this.fr.func_78256_a(value.name + ": " + value.getMeta()) + 10, width);
      this.value = value;
      if (value.getValue() instanceof Number && !(value.getValue() instanceof Enum)) {
         this.flags |= this.FLAG_SLIDER;
      }

   }

   public void render(int mouseX, int mouseY) {
      int bgColor = (Boolean)this.clickGUI.rainbow.value ? -872415232 : ColourUtils.toRGBA((Integer)this.clickGUI.br.value, (Integer)this.clickGUI.bg.value, (Integer)this.clickGUI.bb.value, (Integer)this.clickGUI.ba.value);
      int color = ColourUtils.toRGBA((Integer)this.clickGUI.r.value, (Integer)this.clickGUI.g.value, (Integer)this.clickGUI.b.value, (Integer)this.clickGUI.a.value);
      RenderUtil.drawSexyRect((double)this.x, (double)(this.y + this.offsetY), (double)(this.x + this.width), (double)(this.y + this.height), (Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : bgColor, bgColor);
      if (this.value.value instanceof Boolean && (Boolean)this.value.value) {
         Gui.func_73734_a(this.x, this.y + this.offsetY, this.x + this.width, this.y + this.height, (Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : color);
      }

      if (this.value.value instanceof Enum) {
         Gui.func_73734_a(this.x, this.y + this.offsetY, this.x + this.width, this.y + this.height, (Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : color);
      }

      if (this.hasFlag(this.FLAG_SLIDER)) {
         this.drawSlider(color, bgColor);
      }

      if (mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y + this.offsetY && mouseY < this.y + this.height) {
         if (!this.value.desc.equals("")) {
            ScaledResolution sr = new ScaledResolution(this.mc);
            this.fr.func_175063_a("Description: " + this.value.desc, 0.0F, (float)(sr.func_78328_b() - this.fr.field_78288_b), -1);
         }

         int[] col = ColourUtils.toRGBAArray((Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : color);
         int finalCol = ColourUtils.toRGBA(col[0], col[1], col[2], Math.max(0, col[3] - 50));
         RenderUtil.drawRect((double)this.x, (double)(this.y + this.offsetY), (double)(this.x + this.width), (double)(this.y + this.height), finalCol);
      }

      if (this.value.value instanceof String) {
         this.fr.func_175063_a(this.listening ? this.typeCache + "_" : this.value.name + ": " + this.value.getMeta(), (float)(this.x + 5), (float)(this.y + 16), -1);
      } else {
         this.fr.func_175063_a(this.value.value instanceof Boolean ? this.value.name : this.value.name + ": " + this.value.getMeta(), (float)(this.x + 5), (float)(this.y + 16), -1);
      }

      if (this.expanded) {
         Iterator var8 = this.components.iterator();

         while(var8.hasNext()) {
            Component item = (Component)var8.next();
            item.render(mouseX, mouseY);
         }
      }

      super.render(mouseX, mouseY);
   }

   public void drawSlider(int color, int bgColor) {
      float scale = this.calculateXPos(this.value);
      Gui.func_73734_a(this.x, this.y + this.offsetY, (int)((float)this.x + (scale > (float)this.width ? (float)this.width : scale)), this.y + this.height, (Boolean)this.clickGUI.rainbow.value ? ColourUtils.genRainbow() : color);
   }

   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
      if (this.value.isVisible() && mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y + this.offsetY && mouseY < this.y + this.height && mouseButton == 0) {
         this.handleMouseClick();
      }

   }

   private void handleMouseClick() {
      if (this.value.value instanceof Boolean) {
         this.value.setValue(!(Boolean)this.value.value);
      }

      if (this.value.value instanceof Enum) {
         Enum val = (Enum)this.value.value;
         this.value.setValue(this.getNextEnum(this.getEnumIndex(val), val));
      }

      if (this.value.value instanceof String) {
         this.listening = !this.listening;
      }

      if (this.hasFlag(this.FLAG_SLIDER)) {
         this.slider = !this.slider;
      }

   }

   public int getEnumIndex(Enum enu) {
      int index = 0;
      Enum[] var3 = (Enum[])enu.getClass().getEnumConstants();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Enum en = var3[var5];
         if (enu == en) {
            break;
         }

         ++index;
      }

      return index;
   }

   public Enum getNextEnum(int index, Enum enu) {
      Enum[] array = (Enum[])enu.getClass().getEnumConstants();
      return index + 1 >= array.length ? array[0] : array[index + 1];
   }

   public float calculateXPos(Value val) {
      float minX = (float)this.x;
      float maxX = (float)(this.x + this.width);
      if (val.max == null) {
         return minX;
      } else {
         Number l_Val = (Number)val.value;
         Number l_Max = (Number)val.max;
         return (float)Math.round(maxX - minX) * (l_Val.floatValue() / l_Max.floatValue());
      }
   }

   public void mouseRelease(int mouseX, int mouseY, int state) {
      if (this.value.isVisible() && this.slider) {
         this.slider = false;
      }

   }

   public void mouseClickMove(int mouseX, int mouseY, int r, float e) {
      if (this.parent.expanded && this.slider) {
         int difference = ((Number)this.value.max).intValue() - ((Number)this.value.min).intValue();
         float percent = ((float)mouseX - (float)this.x) / ((float)this.width + 7.4F);
         if (this.value.getValue() instanceof Double) {
            double result = (Double)this.value.min + (double)((float)difference * percent);
            this.value.setValue((double)Math.round(10.0D * result) / 10.0D);
         } else if (this.value.getValue() instanceof Float) {
            float result = (Float)this.value.min + (float)difference * percent;
            this.value.setValue((float)Math.round(10.0F * result) / 10.0F);
         } else if (this.value.getValue() instanceof Integer) {
            this.value.setValue((Integer)this.value.min + (int)((float)difference * percent));
         } else if (this.value.getValue() instanceof Long) {
            this.value.setValue((Long)this.value.min + (long)((float)difference * percent));
         }

         if (this.value.value instanceof Integer) {
            if (((Number)this.value.value).intValue() <= ((Number)this.value.min).intValue()) {
               this.value.value = this.value.min;
            }

            if (((Number)this.value.value).intValue() >= ((Number)this.value.max).intValue()) {
               this.value.value = this.value.max;
            }
         }

         if (this.value.value instanceof Float) {
            if (((Number)this.value.value).floatValue() <= ((Number)this.value.min).floatValue()) {
               this.value.value = this.value.min;
            }

            if (((Number)this.value.value).floatValue() >= ((Number)this.value.max).floatValue()) {
               this.value.value = this.value.max;
            }
         }

         if (this.value.value instanceof Double) {
            if (((Number)this.value.value).doubleValue() <= ((Number)this.value.min).doubleValue()) {
               this.value.value = this.value.min;
            }

            if (((Number)this.value.value).doubleValue() >= ((Number)this.value.max).doubleValue()) {
               this.value.value = this.value.max;
            }
         }

         if (this.value.value instanceof Long) {
            if (((Number)this.value.value).longValue() <= ((Number)this.value.min).longValue()) {
               this.value.value = this.value.min;
            }

            if (((Number)this.value.value).longValue() >= ((Number)this.value.max).longValue()) {
               this.value.value = this.value.max;
            }
         }
      }

   }

   public void keyTyped(char typedChar, int keyCode) throws IOException {
      if (this.listening) {
         if (keyCode == 14 && this.typeCache.length() > 0) {
            this.typeCache = this.typeCache.substring(0, this.typeCache.length() - 1);
            return;
         }

         if (keyCode == 28) {
            this.listening = !this.listening;
            this.value.setValue(this.typeCache);
            return;
         }

         if (keyCode == 42 || keyCode == 54 || keyCode == 157 || keyCode == 29 || keyCode == 0) {
            return;
         }

         this.typeCache = this.typeCache + typedChar;
      }

   }
}
