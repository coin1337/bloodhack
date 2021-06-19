package dev.lors.bloodhack.managers;

import dev.lors.bloodhack.module.Module;
import java.util.function.Predicate;

public class Value<T> {
   public String name;
   public String[] alias;
   public String desc;
   public Module module;
   public Predicate<T> showIf;
   public T value;
   public T min;
   public T max;

   public Value(String name, String[] alias, String desc) {
      this.name = name;
      this.alias = alias;
      this.desc = desc;
   }

   public Value(String name, String[] alias, String desc, T value) {
      this(name, alias, desc);
      this.value = value;
   }

   public Value(String name, String[] alias, String desc, T value, T min, T max) {
      this(name, alias, desc, value);
      this.min = min;
      this.max = max;
   }

   public Value(String name, String desc) {
      this.name = name;
      this.alias = new String[0];
      this.desc = desc;
   }

   public Value(String name, String desc, T value) {
      this(name, new String[0], desc);
      this.value = value;
   }

   public Value(String name, String desc, T value, T min, T max) {
      this(name, new String[0], desc, value);
      this.min = min;
      this.max = max;
   }

   public Value(String name) {
      this.name = name;
      this.alias = new String[0];
      this.desc = "";
   }

   public Value(String name, T value) {
      this(name, new String[0], "");
      this.value = value;
   }

   public Value(String name, T value, T min, T max) {
      this(name, new String[0], "", value);
      this.min = min;
      this.max = max;
   }

   public Value(String name, T value, T min, T max, Predicate<T> showIf) {
      this(name, new String[0], "", value);
      this.min = min;
      this.max = max;
      this.showIf = showIf;
   }

   public Value(String name, T value, Predicate<T> showIf) {
      this(name, new String[0], "", value);
      this.showIf = showIf;
   }

   public <T> T clamp(T value, T min, T max) {
      return ((Comparable)value).compareTo(min) < 0 ? min : (((Comparable)value).compareTo(max) > 0 ? max : value);
   }

   public T getValue() {
      return this.value;
   }

   public void setValue(T value) {
      this.value = value;
   }

   public String getMeta() {
      return this.value != null ? this.value.toString() : this.name;
   }

   public boolean isVisible() {
      return this.showIf == null ? true : this.showIf.test(this.getValue());
   }
}
