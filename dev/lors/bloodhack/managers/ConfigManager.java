package dev.lors.bloodhack.managers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.module.Module;
import dev.lors.bloodhack.util.EnumConverter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.stream.Collectors;
import org.lwjgl.input.Keyboard;

public class ConfigManager {
   public File BloodHack = new File("BloodHack");
   public File Settings;

   public ConfigManager() {
      if (!this.BloodHack.exists()) {
         this.BloodHack.mkdirs();
      }

      this.Settings = new File("BloodHack" + File.separator + "Settings");
      if (!this.Settings.exists()) {
         this.Settings.mkdirs();
      }

      this.load();
   }

   public void save() {
      try {
         Iterator var1 = dev.lors.bloodhack.BloodHack.moduleManager.getModules().iterator();

         while(var1.hasNext()) {
            Module module = (Module)var1.next();
            File moduleFile = new File(this.BloodHack.getAbsolutePath(), "Settings/" + module.getCategory() + "/" + module.getName() + ".json");
            moduleFile.getParentFile().mkdirs();
            if (!moduleFile.exists()) {
               moduleFile.createNewFile();
            }

            JsonObject object = new JsonObject();
            object.addProperty("bind", Keyboard.getKeyName(module.getKey()));
            object.addProperty("enabled", module.isToggled());
            Iterator var5 = module.values.iterator();

            while(var5.hasNext()) {
               Value value = (Value)var5.next();
               if (value.value instanceof Boolean) {
                  object.addProperty(value.name, (Boolean)value.value);
               }

               if (value.value instanceof Number) {
                  object.addProperty(value.name, (Number)value.value);
               }

               if (value.value instanceof String) {
                  object.addProperty(value.name, (String)value.value);
               }

               if (value.value instanceof Enum) {
                  object.addProperty(value.name, ((Enum)value.value).name());
               }
            }

            FileWriter fileWriter = new FileWriter(moduleFile);
            fileWriter.write(object.toString());
            fileWriter.flush();
            fileWriter.close();
         }
      } catch (Exception var7) {
      }

   }

   public void load() {
      Iterator var1 = dev.lors.bloodhack.BloodHack.moduleManager.getModules().iterator();

      while(var1.hasNext()) {
         Module module = (Module)var1.next();

         try {
            File moduleFile = new File(this.BloodHack.getAbsolutePath(), "Settings/" + module.getCategory() + "/" + module.getName() + ".json");
            moduleFile.getParentFile().mkdirs();
            if (!moduleFile.exists()) {
               moduleFile.createNewFile();
            }

            String content = (String)Files.readAllLines(moduleFile.toPath()).stream().collect(Collectors.joining());
            JsonObject object = (new JsonParser()).parse(content).getAsJsonObject();
            int bind = Keyboard.getKeyIndex(object.get("bind").getAsString());
            module.setKey(bind);
            module.toggled = object.get("enabled").getAsBoolean();
            Iterator var7 = module.values.iterator();

            while(var7.hasNext()) {
               Value value = (Value)var7.next();
               JsonElement element = object.get(value.name);
               if (value.value instanceof Boolean) {
                  value.value = element.getAsBoolean();
               }

               if (value.value instanceof Number) {
                  if (value.value instanceof Integer) {
                     value.value = element.getAsNumber().intValue();
                  }

                  if (value.value instanceof Double) {
                     value.value = element.getAsNumber().doubleValue();
                  }

                  if (value.value instanceof Float) {
                     value.value = element.getAsNumber().floatValue();
                  }

                  if (value.value instanceof Long) {
                     value.value = element.getAsNumber().longValue();
                  }

                  if (value.value instanceof Byte) {
                     value.value = element.getAsNumber().byteValue();
                  }

                  if (value.value instanceof Short) {
                     value.value = element.getAsNumber().shortValue();
                  }
               }

               if (value.value instanceof String) {
                  value.value = element.getAsString();
               }

               if (value.value instanceof Enum) {
                  EnumConverter converter = new EnumConverter(((Enum)value.getValue()).getClass());
                  value.value = converter.doBackward(element);
               }
            }
         } catch (Exception var11) {
         }
      }

   }
}
