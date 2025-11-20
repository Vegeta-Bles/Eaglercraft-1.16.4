package net.minecraft.client.render.debug;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VillageDebugRenderer implements DebugRenderer.Renderer {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MinecraftClient client;
   private final Map<BlockPos, VillageDebugRenderer.PointOfInterest> pointsOfInterest = Maps.newHashMap();
   private final Map<UUID, VillageDebugRenderer.Brain> brains = Maps.newHashMap();
   @Nullable
   private UUID targetedEntity;

   public VillageDebugRenderer(MinecraftClient _snowman) {
      this.client = _snowman;
   }

   @Override
   public void clear() {
      this.pointsOfInterest.clear();
      this.brains.clear();
      this.targetedEntity = null;
   }

   public void addPointOfInterest(VillageDebugRenderer.PointOfInterest _snowman) {
      this.pointsOfInterest.put(_snowman.pos, _snowman);
   }

   public void removePointOfInterest(BlockPos _snowman) {
      this.pointsOfInterest.remove(_snowman);
   }

   public void setFreeTicketCount(BlockPos pos, int freeTicketCount) {
      VillageDebugRenderer.PointOfInterest _snowman = this.pointsOfInterest.get(pos);
      if (_snowman == null) {
         LOGGER.warn("Strange, setFreeTicketCount was called for an unknown POI: " + pos);
      } else {
         _snowman.freeTicketCount = freeTicketCount;
      }
   }

   public void addBrain(VillageDebugRenderer.Brain brain) {
      this.brains.put(brain.uuid, brain);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      this.method_24805();
      this.method_23135(cameraX, cameraY, cameraZ);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
      if (!this.client.player.isSpectator()) {
         this.updateTargetedEntity();
      }
   }

   private void method_24805() {
      this.brains.entrySet().removeIf(_snowman -> {
         Entity _snowmanx = this.client.world.getEntityById(_snowman.getValue().field_18924);
         return _snowmanx == null || _snowmanx.removed;
      });
   }

   private void method_23135(double _snowman, double _snowman, double _snowman) {
      BlockPos _snowmanxxx = new BlockPos(_snowman, _snowman, _snowman);
      this.brains.values().forEach(_snowmanxxxx -> {
         if (this.isClose(_snowmanxxxx)) {
            this.drawBrain(_snowmanxxxx, _snowman, _snowman, _snowman);
         }
      });

      for (BlockPos _snowmanxxxx : this.pointsOfInterest.keySet()) {
         if (_snowmanxxx.isWithinDistance(_snowmanxxxx, 30.0)) {
            drawPointOfInterest(_snowmanxxxx);
         }
      }

      this.pointsOfInterest.values().forEach(_snowmanxxxxx -> {
         if (_snowman.isWithinDistance(_snowmanxxxxx.pos, 30.0)) {
            this.drawPointOfInterestInfo(_snowmanxxxxx);
         }
      });
      this.getGhostPointsOfInterest().forEach((_snowmanxxxxx, _snowmanxxxxxx) -> {
         if (_snowman.isWithinDistance(_snowmanxxxxx, 30.0)) {
            this.drawGhostPointOfInterest(_snowmanxxxxx, _snowmanxxxxxx);
         }
      });
   }

   private static void drawPointOfInterest(BlockPos pos) {
      float _snowman = 0.05F;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      DebugRenderer.drawBox(pos, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
   }

   private void drawGhostPointOfInterest(BlockPos pos, List<String> brains) {
      float _snowman = 0.05F;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      DebugRenderer.drawBox(pos, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
      drawString("" + brains, pos, 0, -256);
      drawString("Ghost POI", pos, 1, -65536);
   }

   private void drawPointOfInterestInfo(VillageDebugRenderer.PointOfInterest pointOfInterest) {
      int _snowman = 0;
      Set<String> _snowmanx = this.getVillagerNames(pointOfInterest);
      if (_snowmanx.size() < 4) {
         drawString("Owners: " + _snowmanx, pointOfInterest, _snowman, -256);
      } else {
         drawString("" + _snowmanx.size() + " ticket holders", pointOfInterest, _snowman, -256);
      }

      _snowman++;
      Set<String> _snowmanxx = this.method_29385(pointOfInterest);
      if (_snowmanxx.size() < 4) {
         drawString("Candidates: " + _snowmanxx, pointOfInterest, _snowman, -23296);
      } else {
         drawString("" + _snowmanxx.size() + " potential owners", pointOfInterest, _snowman, -23296);
      }

      drawString("Free tickets: " + pointOfInterest.freeTicketCount, pointOfInterest, ++_snowman, -256);
      drawString(pointOfInterest.field_18932, pointOfInterest, ++_snowman, -1);
   }

   private void drawPath(VillageDebugRenderer.Brain brain, double cameraX, double cameraY, double cameraZ) {
      if (brain.path != null) {
         PathfindingDebugRenderer.drawPath(brain.path, 0.5F, false, false, cameraX, cameraY, cameraZ);
      }
   }

   private void drawBrain(VillageDebugRenderer.Brain brain, double cameraX, double cameraY, double cameraZ) {
      boolean _snowman = this.isTargeted(brain);
      int _snowmanx = 0;
      drawString(brain.pos, _snowmanx, brain.field_19328, -1, 0.03F);
      _snowmanx++;
      if (_snowman) {
         drawString(brain.pos, _snowmanx, brain.profession + " " + brain.xp + " xp", -1, 0.02F);
         _snowmanx++;
      }

      if (_snowman) {
         int _snowmanxx = brain.field_22406 < brain.field_22407 ? -23296 : -1;
         drawString(brain.pos, _snowmanx, "health: " + String.format("%.1f", brain.field_22406) + " / " + String.format("%.1f", brain.field_22407), _snowmanxx, 0.02F);
         _snowmanx++;
      }

      if (_snowman && !brain.field_19372.equals("")) {
         drawString(brain.pos, _snowmanx, brain.field_19372, -98404, 0.02F);
         _snowmanx++;
      }

      if (_snowman) {
         for (String _snowmanxx : brain.field_18928) {
            drawString(brain.pos, _snowmanx, _snowmanxx, -16711681, 0.02F);
            _snowmanx++;
         }
      }

      if (_snowman) {
         for (String _snowmanxx : brain.field_18927) {
            drawString(brain.pos, _snowmanx, _snowmanxx, -16711936, 0.02F);
            _snowmanx++;
         }
      }

      if (brain.wantsGolem) {
         drawString(brain.pos, _snowmanx, "Wants Golem", -23296, 0.02F);
         _snowmanx++;
      }

      if (_snowman) {
         for (String _snowmanxx : brain.field_19375) {
            if (_snowmanxx.startsWith(brain.field_19328)) {
               drawString(brain.pos, _snowmanx, _snowmanxx, -1, 0.02F);
            } else {
               drawString(brain.pos, _snowmanx, _snowmanxx, -23296, 0.02F);
            }

            _snowmanx++;
         }
      }

      if (_snowman) {
         for (String _snowmanxx : Lists.reverse(brain.field_19374)) {
            drawString(brain.pos, _snowmanx, _snowmanxx, -3355444, 0.02F);
            _snowmanx++;
         }
      }

      if (_snowman) {
         this.drawPath(brain, cameraX, cameraY, cameraZ);
      }
   }

   private static void drawString(String string, VillageDebugRenderer.PointOfInterest pointOfInterest, int offsetY, int color) {
      BlockPos _snowman = pointOfInterest.pos;
      drawString(string, _snowman, offsetY, color);
   }

   private static void drawString(String string, BlockPos pos, int offsetY, int color) {
      double _snowman = 1.3;
      double _snowmanx = 0.2;
      double _snowmanxx = (double)pos.getX() + 0.5;
      double _snowmanxxx = (double)pos.getY() + 1.3 + (double)offsetY * 0.2;
      double _snowmanxxxx = (double)pos.getZ() + 0.5;
      DebugRenderer.drawString(string, _snowmanxx, _snowmanxxx, _snowmanxxxx, color, 0.02F, true, 0.0F, true);
   }

   private static void drawString(Position pos, int offsetY, String string, int color, float size) {
      double _snowman = 2.4;
      double _snowmanx = 0.25;
      BlockPos _snowmanxx = new BlockPos(pos);
      double _snowmanxxx = (double)_snowmanxx.getX() + 0.5;
      double _snowmanxxxx = pos.getY() + 2.4 + (double)offsetY * 0.25;
      double _snowmanxxxxx = (double)_snowmanxx.getZ() + 0.5;
      float _snowmanxxxxxx = 0.5F;
      DebugRenderer.drawString(string, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, color, size, false, 0.5F, true);
   }

   private Set<String> getVillagerNames(VillageDebugRenderer.PointOfInterest pointOfInterest) {
      return this.getBrains(pointOfInterest.pos).stream().map(NameGenerator::name).collect(Collectors.toSet());
   }

   private Set<String> method_29385(VillageDebugRenderer.PointOfInterest _snowman) {
      return this.method_29386(_snowman.pos).stream().map(NameGenerator::name).collect(Collectors.toSet());
   }

   private boolean isTargeted(VillageDebugRenderer.Brain brain) {
      return Objects.equals(this.targetedEntity, brain.uuid);
   }

   private boolean isClose(VillageDebugRenderer.Brain brain) {
      PlayerEntity _snowman = this.client.player;
      BlockPos _snowmanx = new BlockPos(_snowman.getX(), brain.pos.getY(), _snowman.getZ());
      BlockPos _snowmanxx = new BlockPos(brain.pos);
      return _snowmanx.isWithinDistance(_snowmanxx, 30.0);
   }

   private Collection<UUID> getBrains(BlockPos pointOfInterest) {
      return this.brains
         .values()
         .stream()
         .filter(_snowmanx -> _snowmanx.isPointOfInterest(pointOfInterest))
         .map(VillageDebugRenderer.Brain::getUuid)
         .collect(Collectors.toSet());
   }

   private Collection<UUID> method_29386(BlockPos _snowman) {
      return this.brains.values().stream().filter(_snowmanxx -> _snowmanxx.method_29388(_snowman)).map(VillageDebugRenderer.Brain::getUuid).collect(Collectors.toSet());
   }

   private Map<BlockPos, List<String>> getGhostPointsOfInterest() {
      Map<BlockPos, List<String>> _snowman = Maps.newHashMap();

      for (VillageDebugRenderer.Brain _snowmanx : this.brains.values()) {
         for (BlockPos _snowmanxx : Iterables.concat(_snowmanx.pointsOfInterest, _snowmanx.field_25287)) {
            if (!this.pointsOfInterest.containsKey(_snowmanxx)) {
               _snowman.computeIfAbsent(_snowmanxx, _snowmanxxx -> Lists.newArrayList()).add(_snowmanx.field_19328);
            }
         }
      }

      return _snowman;
   }

   private void updateTargetedEntity() {
      DebugRenderer.getTargetedEntity(this.client.getCameraEntity(), 8).ifPresent(_snowman -> this.targetedEntity = _snowman.getUuid());
   }

   public static class Brain {
      public final UUID uuid;
      public final int field_18924;
      public final String field_19328;
      public final String profession;
      public final int xp;
      public final float field_22406;
      public final float field_22407;
      public final Position pos;
      public final String field_19372;
      public final Path path;
      public final boolean wantsGolem;
      public final List<String> field_18927 = Lists.newArrayList();
      public final List<String> field_18928 = Lists.newArrayList();
      public final List<String> field_19374 = Lists.newArrayList();
      public final List<String> field_19375 = Lists.newArrayList();
      public final Set<BlockPos> pointsOfInterest = Sets.newHashSet();
      public final Set<BlockPos> field_25287 = Sets.newHashSet();

      public Brain(UUID _snowman, int _snowman, String _snowman, String profession, int xp, float _snowman, float _snowman, Position _snowman, String _snowman, @Nullable Path _snowman, boolean _snowman) {
         this.uuid = _snowman;
         this.field_18924 = _snowman;
         this.field_19328 = _snowman;
         this.profession = profession;
         this.xp = xp;
         this.field_22406 = _snowman;
         this.field_22407 = _snowman;
         this.pos = _snowman;
         this.field_19372 = _snowman;
         this.path = _snowman;
         this.wantsGolem = _snowman;
      }

      private boolean isPointOfInterest(BlockPos _snowman) {
         return this.pointsOfInterest.stream().anyMatch(_snowman::equals);
      }

      private boolean method_29388(BlockPos _snowman) {
         return this.field_25287.contains(_snowman);
      }

      public UUID getUuid() {
         return this.uuid;
      }
   }

   public static class PointOfInterest {
      public final BlockPos pos;
      public String field_18932;
      public int freeTicketCount;

      public PointOfInterest(BlockPos _snowman, String _snowman, int _snowman) {
         this.pos = _snowman;
         this.field_18932 = _snowman;
         this.freeTicketCount = _snowman;
      }
   }
}
