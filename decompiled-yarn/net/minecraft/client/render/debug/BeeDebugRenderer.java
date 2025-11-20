package net.minecraft.client.render.debug;

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
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;

public class BeeDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;
   private final Map<BlockPos, BeeDebugRenderer.Hive> hives = Maps.newHashMap();
   private final Map<UUID, BeeDebugRenderer.Bee> bees = Maps.newHashMap();
   private UUID targetedEntity;

   public BeeDebugRenderer(MinecraftClient client) {
      this.client = client;
   }

   @Override
   public void clear() {
      this.hives.clear();
      this.bees.clear();
      this.targetedEntity = null;
   }

   public void addHive(BeeDebugRenderer.Hive hive) {
      this.hives.put(hive.pos, hive);
   }

   public void addBee(BeeDebugRenderer.Bee bee) {
      this.bees.put(bee.uuid, bee);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      this.removeOutdatedHives();
      this.removeInvalidBees();
      this.render();
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
      if (!this.client.player.isSpectator()) {
         this.updateTargetedEntity();
      }
   }

   private void removeInvalidBees() {
      this.bees.entrySet().removeIf(_snowman -> this.client.world.getEntityById(_snowman.getValue().entityId) == null);
   }

   private void removeOutdatedHives() {
      long _snowman = this.client.world.getTime() - 20L;
      this.hives.entrySet().removeIf(_snowmanx -> _snowmanx.getValue().time < _snowman);
   }

   private void render() {
      BlockPos _snowman = this.getCameraPos().getBlockPos();
      this.bees.values().forEach(_snowmanx -> {
         if (this.isInRange(_snowmanx)) {
            this.drawBee(_snowmanx);
         }
      });
      this.drawFlowers();

      for (BlockPos _snowmanx : this.hives.keySet()) {
         if (_snowman.isWithinDistance(_snowmanx, 30.0)) {
            drawHive(_snowmanx);
         }
      }

      Map<BlockPos, Set<UUID>> _snowmanxx = this.getBlacklistingBees();
      this.hives.values().forEach(_snowmanxxx -> {
         if (_snowman.isWithinDistance(_snowmanxxx.pos, 30.0)) {
            Set<UUID> _snowmanxxxx = _snowman.get(_snowmanxxx.pos);
            this.drawHiveInfo(_snowmanxxx, (Collection<UUID>)(_snowmanxxxx == null ? Sets.newHashSet() : _snowmanxxxx));
         }
      });
      this.getBeesByHive().forEach((_snowmanxxx, _snowmanxxxx) -> {
         if (_snowman.isWithinDistance(_snowmanxxx, 30.0)) {
            this.drawHiveBees(_snowmanxxx, (List<String>)_snowmanxxxx);
         }
      });
   }

   private Map<BlockPos, Set<UUID>> getBlacklistingBees() {
      Map<BlockPos, Set<UUID>> _snowman = Maps.newHashMap();
      this.bees.values().forEach(_snowmanx -> _snowmanx.blacklist.forEach(_snowmanxxx -> _snowman.computeIfAbsent(_snowmanxxx, _snowmanxxxxx -> Sets.newHashSet()).add(_snowmanx.getUuid())));
      return _snowman;
   }

   private void drawFlowers() {
      Map<BlockPos, Set<UUID>> _snowman = Maps.newHashMap();
      this.bees
         .values()
         .stream()
         .filter(BeeDebugRenderer.Bee::hasFlower)
         .forEach(_snowmanx -> _snowman.computeIfAbsent(_snowmanx.flower, _snowmanxx -> Sets.newHashSet()).add(_snowmanx.getUuid()));
      _snowman.entrySet().forEach(_snowmanx -> {
         BlockPos _snowmanx = _snowmanx.getKey();
         Set<UUID> _snowmanxx = _snowmanx.getValue();
         Set<String> _snowmanxxx = _snowmanxx.stream().map(NameGenerator::name).collect(Collectors.toSet());
         int _snowmanxxxx = 1;
         drawString(_snowmanxxx.toString(), _snowmanx, _snowmanxxxx++, -256);
         drawString("Flower", _snowmanx, _snowmanxxxx++, -1);
         float _snowmanxxxxx = 0.05F;
         drawBox(_snowmanx, 0.05F, 0.8F, 0.8F, 0.0F, 0.3F);
      });
   }

   private static String toString(Collection<UUID> bees) {
      if (bees.isEmpty()) {
         return "-";
      } else {
         return bees.size() > 3 ? "" + bees.size() + " bees" : bees.stream().map(NameGenerator::name).collect(Collectors.toSet()).toString();
      }
   }

   private static void drawHive(BlockPos pos) {
      float _snowman = 0.05F;
      drawBox(pos, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
   }

   private void drawHiveBees(BlockPos pos, List<String> bees) {
      float _snowman = 0.05F;
      drawBox(pos, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
      drawString("" + bees, pos, 0, -256);
      drawString("Ghost Hive", pos, 1, -65536);
   }

   private static void drawBox(BlockPos pos, float expand, float red, float green, float blue, float alpha) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      DebugRenderer.drawBox(pos, expand, red, green, blue, alpha);
   }

   private void drawHiveInfo(BeeDebugRenderer.Hive hive, Collection<UUID> blacklistingBees) {
      int _snowman = 0;
      if (!blacklistingBees.isEmpty()) {
         drawString("Blacklisted by " + toString(blacklistingBees), hive, _snowman++, -65536);
      }

      drawString("Out: " + toString(this.getBeesForHive(hive.pos)), hive, _snowman++, -3355444);
      if (hive.beeCount == 0) {
         drawString("In: -", hive, _snowman++, -256);
      } else if (hive.beeCount == 1) {
         drawString("In: 1 bee", hive, _snowman++, -256);
      } else {
         drawString("In: " + hive.beeCount + " bees", hive, _snowman++, -256);
      }

      drawString("Honey: " + hive.honeyLevel, hive, _snowman++, -23296);
      drawString(hive.label + (hive.sedated ? " (sedated)" : ""), hive, _snowman++, -1);
   }

   private void drawPath(BeeDebugRenderer.Bee bee) {
      if (bee.path != null) {
         PathfindingDebugRenderer.drawPath(
            bee.path, 0.5F, false, false, this.getCameraPos().getPos().getX(), this.getCameraPos().getPos().getY(), this.getCameraPos().getPos().getZ()
         );
      }
   }

   private void drawBee(BeeDebugRenderer.Bee bee) {
      boolean _snowman = this.isTargeted(bee);
      int _snowmanx = 0;
      drawString(bee.position, _snowmanx++, bee.toString(), -1, 0.03F);
      if (bee.hive == null) {
         drawString(bee.position, _snowmanx++, "No hive", -98404, 0.02F);
      } else {
         drawString(bee.position, _snowmanx++, "Hive: " + this.getPositionString(bee, bee.hive), -256, 0.02F);
      }

      if (bee.flower == null) {
         drawString(bee.position, _snowmanx++, "No flower", -98404, 0.02F);
      } else {
         drawString(bee.position, _snowmanx++, "Flower: " + this.getPositionString(bee, bee.flower), -256, 0.02F);
      }

      for (String _snowmanxx : bee.labels) {
         drawString(bee.position, _snowmanx++, _snowmanxx, -16711936, 0.02F);
      }

      if (_snowman) {
         this.drawPath(bee);
      }

      if (bee.travelTicks > 0) {
         int _snowmanxx = bee.travelTicks < 600 ? -3355444 : -23296;
         drawString(bee.position, _snowmanx++, "Travelling: " + bee.travelTicks + " ticks", _snowmanxx, 0.02F);
      }
   }

   private static void drawString(String string, BeeDebugRenderer.Hive hive, int line, int color) {
      BlockPos _snowman = hive.pos;
      drawString(string, _snowman, line, color);
   }

   private static void drawString(String string, BlockPos pos, int line, int color) {
      double _snowman = 1.3;
      double _snowmanx = 0.2;
      double _snowmanxx = (double)pos.getX() + 0.5;
      double _snowmanxxx = (double)pos.getY() + 1.3 + (double)line * 0.2;
      double _snowmanxxxx = (double)pos.getZ() + 0.5;
      DebugRenderer.drawString(string, _snowmanxx, _snowmanxxx, _snowmanxxxx, color, 0.02F, true, 0.0F, true);
   }

   private static void drawString(Position pos, int line, String string, int color, float size) {
      double _snowman = 2.4;
      double _snowmanx = 0.25;
      BlockPos _snowmanxx = new BlockPos(pos);
      double _snowmanxxx = (double)_snowmanxx.getX() + 0.5;
      double _snowmanxxxx = pos.getY() + 2.4 + (double)line * 0.25;
      double _snowmanxxxxx = (double)_snowmanxx.getZ() + 0.5;
      float _snowmanxxxxxx = 0.5F;
      DebugRenderer.drawString(string, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, color, size, false, 0.5F, true);
   }

   private Camera getCameraPos() {
      return this.client.gameRenderer.getCamera();
   }

   private String getPositionString(BeeDebugRenderer.Bee bee, BlockPos pos) {
      float _snowman = MathHelper.sqrt(pos.getSquaredDistance(bee.position.getX(), bee.position.getY(), bee.position.getZ(), true));
      double _snowmanx = (double)Math.round(_snowman * 10.0F) / 10.0;
      return pos.toShortString() + " (dist " + _snowmanx + ")";
   }

   private boolean isTargeted(BeeDebugRenderer.Bee bee) {
      return Objects.equals(this.targetedEntity, bee.uuid);
   }

   private boolean isInRange(BeeDebugRenderer.Bee bee) {
      PlayerEntity _snowman = this.client.player;
      BlockPos _snowmanx = new BlockPos(_snowman.getX(), bee.position.getY(), _snowman.getZ());
      BlockPos _snowmanxx = new BlockPos(bee.position);
      return _snowmanx.isWithinDistance(_snowmanxx, 30.0);
   }

   private Collection<UUID> getBeesForHive(BlockPos hivePos) {
      return this.bees.values().stream().filter(_snowmanx -> _snowmanx.isHiveAt(hivePos)).map(BeeDebugRenderer.Bee::getUuid).collect(Collectors.toSet());
   }

   private Map<BlockPos, List<String>> getBeesByHive() {
      Map<BlockPos, List<String>> _snowman = Maps.newHashMap();

      for (BeeDebugRenderer.Bee _snowmanx : this.bees.values()) {
         if (_snowmanx.hive != null && !this.hives.containsKey(_snowmanx.hive)) {
            _snowman.computeIfAbsent(_snowmanx.hive, _snowmanxx -> Lists.newArrayList()).add(_snowmanx.getName());
         }
      }

      return _snowman;
   }

   private void updateTargetedEntity() {
      DebugRenderer.getTargetedEntity(this.client.getCameraEntity(), 8).ifPresent(_snowman -> this.targetedEntity = _snowman.getUuid());
   }

   public static class Bee {
      public final UUID uuid;
      public final int entityId;
      public final Position position;
      @Nullable
      public final Path path;
      @Nullable
      public final BlockPos hive;
      @Nullable
      public final BlockPos flower;
      public final int travelTicks;
      public final List<String> labels = Lists.newArrayList();
      public final Set<BlockPos> blacklist = Sets.newHashSet();

      public Bee(UUID uuid, int entityId, Position position, Path path, BlockPos hive, BlockPos flower, int travelTicks) {
         this.uuid = uuid;
         this.entityId = entityId;
         this.position = position;
         this.path = path;
         this.hive = hive;
         this.flower = flower;
         this.travelTicks = travelTicks;
      }

      public boolean isHiveAt(BlockPos pos) {
         return this.hive != null && this.hive.equals(pos);
      }

      public UUID getUuid() {
         return this.uuid;
      }

      public String getName() {
         return NameGenerator.name(this.uuid);
      }

      @Override
      public String toString() {
         return this.getName();
      }

      public boolean hasFlower() {
         return this.flower != null;
      }
   }

   public static class Hive {
      public final BlockPos pos;
      public final String label;
      public final int beeCount;
      public final int honeyLevel;
      public final boolean sedated;
      public final long time;

      public Hive(BlockPos pos, String label, int beeCount, int honeyLevel, boolean sedated, long time) {
         this.pos = pos;
         this.label = label;
         this.beeCount = beeCount;
         this.honeyLevel = honeyLevel;
         this.sedated = sedated;
         this.time = time;
      }
   }
}
