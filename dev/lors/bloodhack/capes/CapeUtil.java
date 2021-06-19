package dev.lors.bloodhack.capes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class CapeUtil {
   static final ArrayList<String> final_uuid_list = get_uuids();
   static ArrayList<ResourceLocation> capeTexts = new ArrayList();
   static Iterator<ResourceLocation> iterator;
   public static ResourceLocation capeTexture;
   static int currentCape = 0;
   private static Thread thread;

   public static ArrayList<String> get_uuids() {
      try {
         URL url = new URL("https://pastebin.com/ZzBm5hT4");
         BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
         ArrayList uuid_list = new ArrayList();

         String s;
         while((s = reader.readLine()) != null) {
            uuid_list.add(s);
         }

         return uuid_list;
      } catch (Exception var4) {
         return null;
      }
   }

   public static void startAnimationLoop() {
      thread = new Thread(() -> {
         while(true) {
            nextCapeTex();

            try {
               Thread.sleep(0L);
            } catch (InterruptedException var1) {
               var1.printStackTrace();
            }
         }
      });
      thread.start();
   }

   public static void stopAnimationLoop() {
      thread.stop();
   }

   private static void nextCapeTex() {
      ++currentCape;
      if (currentCape == capeTexts.size()) {
         currentCape = 0;
      }

      capeTexture = (ResourceLocation)capeTexts.get(currentCape);
   }

   public static boolean is_uuid_valid(UUID uuid) {
      Iterator var1 = ((ArrayList)Objects.requireNonNull(final_uuid_list)).iterator();

      String u;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         u = (String)var1.next();
      } while(!u.equals(uuid.toString()));

      return true;
   }

   public static void setCape(DynamicTexture texture) {
      capeTexture = Minecraft.func_71410_x().func_110434_K().func_110578_a("bloodhack/capes", texture);
   }

   public static ResourceLocation getCape() {
      return capeTexture;
   }

   static {
      try {
         capeTexts.add(Minecraft.func_71410_x().func_110434_K().func_110578_a("bloodhack/capes", new DynamicTexture(ImageIO.read(new URL("https://i.imgur.com/W1T3gFG.png")))));
         capeTexts.add(Minecraft.func_71410_x().func_110434_K().func_110578_a("bloodhack/capes", new DynamicTexture(ImageIO.read(new URL("https://i.imgur.com/P0plzkt.png")))));
         capeTexts.add(Minecraft.func_71410_x().func_110434_K().func_110578_a("bloodhack/capes", new DynamicTexture(ImageIO.read(new URL("https://i.imgur.com/iAEjyYs.png")))));
      } catch (Exception var1) {
      }

      iterator = capeTexts.iterator();
   }
}
