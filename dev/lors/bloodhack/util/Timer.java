package dev.lors.bloodhack.util;

public final class Timer {
   private long time = -1L;

   public boolean passed(double ms) {
      return (double)(System.currentTimeMillis() - this.time) >= ms;
   }

   public void reset() {
      this.time = System.currentTimeMillis();
   }

   public long getTime() {
      return this.time;
   }

   public void setTime(long time) {
      this.time = time;
   }
}
