package dev.lors.bloodhack.event;

import dev.lors.bloodhack.BloodHack;
import dev.lors.bloodhack.event.events.RenderWorldEvent;
import dev.lors.bloodhack.event.events.TickEvent;
import dev.lors.bloodhack.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.Post;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Start;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.world.ChunkEvent.Load;
import net.minecraftforge.event.world.ChunkEvent.Unload;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Adapter {
   Minecraft mc = Minecraft.func_71410_x();

   @SubscribeEvent
   public void onRender(RenderWorldLastEvent event) {
      if (!event.isCanceled()) {
         RenderUtil.drawBoundingBox(this.mc.field_71439_g.func_174813_aQ(), 1.0F, 255.0F, 255.0F, 255.0F, 255.0F);
         BloodHack.EVENT_BUS.post(new RenderWorldEvent());
      }
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      if (this.mc.field_71439_g != null) {
         BloodHack.EVENT_BUS.post(new TickEvent());
      }
   }

   @SubscribeEvent
   public void onEntitySpawn(EntityJoinWorldEvent event) {
      if (!event.isCanceled()) {
         BloodHack.EVENT_BUS.post(event);
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onPlayerDrawn(Pre event) {
      BloodHack.EVENT_BUS.post(event);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onPlayerDrawn(Post event) {
      BloodHack.EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void onChunkLoaded(Load event) {
      BloodHack.EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void onEventMouse(MouseInputEvent event) {
      BloodHack.EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void onChunkUnLoaded(Unload event) {
      BloodHack.EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void onInputUpdate(InputUpdateEvent event) {
      BloodHack.EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void onLivingEntityUseItemEventTick(Start entityUseItemEvent) {
      BloodHack.EVENT_BUS.post(entityUseItemEvent);
   }

   @SubscribeEvent
   public void onLivingDamageEvent(LivingDamageEvent event) {
      BloodHack.EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void onEntityJoinWorldEvent(EntityJoinWorldEvent entityJoinWorldEvent) {
      BloodHack.EVENT_BUS.post(entityJoinWorldEvent);
   }

   @SubscribeEvent
   public void onPlayerPush(PlayerSPPushOutOfBlocksEvent event) {
      BloodHack.EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void onLeftClickBlock(LeftClickBlock event) {
      BloodHack.EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void onAttackEntity(AttackEntityEvent entityEvent) {
      BloodHack.EVENT_BUS.post(entityEvent);
   }

   @SubscribeEvent
   public void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
      BloodHack.EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void onClientChat(ClientChatReceivedEvent event) {
      BloodHack.EVENT_BUS.post(event);
   }

   @SubscribeEvent
   public void OnWorldChange(WorldEvent p_Event) {
      BloodHack.EVENT_BUS.post(p_Event);
   }
}
