package dev.lors.bloodhack.clickgui;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.clickgui.comps.Frame;
import dev.lors.bloodhack.module.Category;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen {
   ArrayList<Frame> frames = new ArrayList();
   dev.lors.bloodhack.module.BloodModules.hud.ClickGUI clickGUI;

   public ClickGUI() {
      this.clickGUI = (dev.lors.bloodhack.module.BloodModules.hud.ClickGUI)BloodHack.moduleManager.getModuleByName("ClickGUI");
      int count = 0;
      Category[] var2 = Category.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Category category = var2[var4];
         this.frames.add(new Frame(category, 28 + count, 0));
         count += 110;
      }

   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      Iterator var4 = this.frames.iterator();

      while(var4.hasNext()) {
         Frame frame = (Frame)var4.next();
         frame.render(mouseX, mouseY);
         frame.mouseMove(mouseX, mouseY);
      }

      super.func_73863_a(mouseX, mouseY, partialTicks);
   }

   protected void func_146286_b(int mouseX, int mouseY, int state) {
      Iterator var4 = this.frames.iterator();

      while(var4.hasNext()) {
         Frame frame = (Frame)var4.next();
         frame.mouseRelease(mouseX, mouseY, state);
      }

      super.func_146286_b(mouseX, mouseY, state);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
      Iterator var4 = this.frames.iterator();

      while(var4.hasNext()) {
         Frame frame = (Frame)var4.next();
         frame.mouseClick(mouseX, mouseY, mouseButton);
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   protected void func_146273_a(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
      Iterator var6 = this.frames.iterator();

      while(var6.hasNext()) {
         Frame frame = (Frame)var6.next();
         frame.mouseClickMove(mouseX, mouseY, clickedMouseButton, (float)timeSinceLastClick);
      }

      super.func_146273_a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
   }

   protected void func_73869_a(char typedChar, int keyCode) throws IOException {
      Iterator var3 = this.frames.iterator();

      while(var3.hasNext()) {
         Frame frame = (Frame)var3.next();
         frame.keyTyped(typedChar, keyCode);
      }

      super.func_73869_a(typedChar, keyCode);
   }
}
