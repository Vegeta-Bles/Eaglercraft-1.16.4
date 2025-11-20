package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;

public class ZombieEntityRenderer extends ZombieBaseEntityRenderer<ZombieEntity, ZombieEntityModel<ZombieEntity>> {
   public ZombieEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new ZombieEntityModel<>(0.0F, false), new ZombieEntityModel<>(0.5F, true), new ZombieEntityModel<>(1.0F, true));
   }
}
