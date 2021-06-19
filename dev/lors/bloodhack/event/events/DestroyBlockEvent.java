package dev.lors.bloodhack.event.events;

import dev.lors.bloodhack.event.Event;
import net.minecraft.util.math.BlockPos;

public class DestroyBlockEvent extends Event {
   BlockPos pos;

   public DestroyBlockEvent(BlockPos blockPos) {
      this.pos = blockPos;
   }

   public BlockPos getBlockPos() {
      return this.pos;
   }
}
