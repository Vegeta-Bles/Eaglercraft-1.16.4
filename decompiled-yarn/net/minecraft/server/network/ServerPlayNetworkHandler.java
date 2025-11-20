package net.minecraft.server.network;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CommandBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.client.options.ChatVisibility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WritableBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.MessageType;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.c2s.play.AdvancementTabC2SPacket;
import net.minecraft.network.packet.c2s.play.BoatPaddleStateC2SPacket;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import net.minecraft.network.packet.c2s.play.ButtonClickC2SPacket;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientSettingsC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.ConfirmScreenActionC2SPacket;
import net.minecraft.network.packet.c2s.play.CraftRequestC2SPacket;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.JigsawGeneratingC2SPacket;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PickFromInventoryC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.QueryBlockNbtC2SPacket;
import net.minecraft.network.packet.c2s.play.QueryEntityNbtC2SPacket;
import net.minecraft.network.packet.c2s.play.RecipeBookDataC2SPacket;
import net.minecraft.network.packet.c2s.play.RecipeCategoryOptionsC2SPacket;
import net.minecraft.network.packet.c2s.play.RenameItemC2SPacket;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;
import net.minecraft.network.packet.c2s.play.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.network.packet.c2s.play.SpectatorTeleportC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateBeaconC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateCommandBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateCommandBlockMinecartC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateDifficultyC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateDifficultyLockC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateJigsawC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateStructureBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import net.minecraft.network.packet.s2c.play.ConfirmScreenActionS2CPacket;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.HeldItemChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.TagQueryResponseS2CPacket;
import net.minecraft.network.packet.s2c.play.VehicleMoveS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.BeaconScreenHandler;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.thread.ThreadExecutor;
import net.minecraft.world.CommandBlockExecutor;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPlayNetworkHandler implements ServerPlayPacketListener {
   private static final Logger LOGGER = LogManager.getLogger();
   public final ClientConnection connection;
   private final MinecraftServer server;
   public ServerPlayerEntity player;
   private int ticks;
   private long lastKeepAliveTime;
   private boolean waitingForKeepAlive;
   private long keepAliveId;
   private int messageCooldown;
   private int creativeItemDropThreshold;
   private final Int2ShortMap transactions = new Int2ShortOpenHashMap();
   private double lastTickX;
   private double lastTickY;
   private double lastTickZ;
   private double updatedX;
   private double updatedY;
   private double updatedZ;
   private Entity topmostRiddenEntity;
   private double lastTickRiddenX;
   private double lastTickRiddenY;
   private double lastTickRiddenZ;
   private double updatedRiddenX;
   private double updatedRiddenY;
   private double updatedRiddenZ;
   private Vec3d requestedTeleportPos;
   private int requestedTeleportId;
   private int teleportRequestTick;
   private boolean floating;
   private int floatingTicks;
   private boolean ridingEntity;
   private int vehicleFloatingTicks;
   private int movePacketsCount;
   private int lastTickMovePacketsCount;

   public ServerPlayNetworkHandler(MinecraftServer server, ClientConnection connection, ServerPlayerEntity player) {
      this.server = server;
      this.connection = connection;
      connection.setPacketListener(this);
      this.player = player;
      player.networkHandler = this;
      TextStream _snowman = player.getTextStream();
      if (_snowman != null) {
         _snowman.onConnect();
      }
   }

   public void tick() {
      this.syncWithPlayerPosition();
      this.player.prevX = this.player.getX();
      this.player.prevY = this.player.getY();
      this.player.prevZ = this.player.getZ();
      this.player.playerTick();
      this.player.updatePositionAndAngles(this.lastTickX, this.lastTickY, this.lastTickZ, this.player.yaw, this.player.pitch);
      this.ticks++;
      this.lastTickMovePacketsCount = this.movePacketsCount;
      if (this.floating && !this.player.isSleeping()) {
         if (++this.floatingTicks > 80) {
            LOGGER.warn("{} was kicked for floating too long!", this.player.getName().getString());
            this.disconnect(new TranslatableText("multiplayer.disconnect.flying"));
            return;
         }
      } else {
         this.floating = false;
         this.floatingTicks = 0;
      }

      this.topmostRiddenEntity = this.player.getRootVehicle();
      if (this.topmostRiddenEntity != this.player && this.topmostRiddenEntity.getPrimaryPassenger() == this.player) {
         this.lastTickRiddenX = this.topmostRiddenEntity.getX();
         this.lastTickRiddenY = this.topmostRiddenEntity.getY();
         this.lastTickRiddenZ = this.topmostRiddenEntity.getZ();
         this.updatedRiddenX = this.topmostRiddenEntity.getX();
         this.updatedRiddenY = this.topmostRiddenEntity.getY();
         this.updatedRiddenZ = this.topmostRiddenEntity.getZ();
         if (this.ridingEntity && this.player.getRootVehicle().getPrimaryPassenger() == this.player) {
            if (++this.vehicleFloatingTicks > 80) {
               LOGGER.warn("{} was kicked for floating a vehicle too long!", this.player.getName().getString());
               this.disconnect(new TranslatableText("multiplayer.disconnect.flying"));
               return;
            }
         } else {
            this.ridingEntity = false;
            this.vehicleFloatingTicks = 0;
         }
      } else {
         this.topmostRiddenEntity = null;
         this.ridingEntity = false;
         this.vehicleFloatingTicks = 0;
      }

      this.server.getProfiler().push("keepAlive");
      long _snowman = Util.getMeasuringTimeMs();
      if (_snowman - this.lastKeepAliveTime >= 15000L) {
         if (this.waitingForKeepAlive) {
            this.disconnect(new TranslatableText("disconnect.timeout"));
         } else {
            this.waitingForKeepAlive = true;
            this.lastKeepAliveTime = _snowman;
            this.keepAliveId = _snowman;
            this.sendPacket(new KeepAliveS2CPacket(this.keepAliveId));
         }
      }

      this.server.getProfiler().pop();
      if (this.messageCooldown > 0) {
         this.messageCooldown--;
      }

      if (this.creativeItemDropThreshold > 0) {
         this.creativeItemDropThreshold--;
      }

      if (this.player.getLastActionTime() > 0L
         && this.server.getPlayerIdleTimeout() > 0
         && Util.getMeasuringTimeMs() - this.player.getLastActionTime() > (long)(this.server.getPlayerIdleTimeout() * 1000 * 60)) {
         this.disconnect(new TranslatableText("multiplayer.disconnect.idling"));
      }
   }

   public void syncWithPlayerPosition() {
      this.lastTickX = this.player.getX();
      this.lastTickY = this.player.getY();
      this.lastTickZ = this.player.getZ();
      this.updatedX = this.player.getX();
      this.updatedY = this.player.getY();
      this.updatedZ = this.player.getZ();
   }

   @Override
   public ClientConnection getConnection() {
      return this.connection;
   }

   private boolean isHost() {
      return this.server.isHost(this.player.getGameProfile());
   }

   public void disconnect(Text reason) {
      this.connection.send(new DisconnectS2CPacket(reason), _snowmanx -> this.connection.disconnect(reason));
      this.connection.disableAutoRead();
      this.server.submitAndJoin(this.connection::handleDisconnection);
   }

   private <T> void filterText(T text, Consumer<T> consumer, BiFunction<TextStream, T, CompletableFuture<Optional<T>>> backingFilterer) {
      ThreadExecutor<?> _snowman = this.player.getServerWorld().getServer();
      Consumer<T> _snowmanx = _snowmanxx -> {
         if (this.getConnection().isOpen()) {
            consumer.accept(_snowmanxx);
         } else {
            LOGGER.debug("Ignoring packet due to disconnection");
         }
      };
      TextStream _snowmanxx = this.player.getTextStream();
      if (_snowmanxx != null) {
         backingFilterer.apply(_snowmanxx, text).thenAcceptAsync(_snowmanxxx -> _snowmanxxx.ifPresent(_snowman), _snowman);
      } else {
         _snowman.execute(() -> _snowman.accept(text));
      }
   }

   private void filterText(String text, Consumer<String> consumer) {
      this.filterText(text, consumer, TextStream::filterText);
   }

   private void filterTexts(List<String> texts, Consumer<List<String>> consumer) {
      this.filterText(texts, consumer, TextStream::filterTexts);
   }

   @Override
   public void onPlayerInput(PlayerInputC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.method_14218(packet.getSideways(), packet.getForward(), packet.isJumping(), packet.isSneaking());
   }

   private static boolean validatePlayerMove(PlayerMoveC2SPacket packet) {
      return Doubles.isFinite(packet.getX(0.0))
            && Doubles.isFinite(packet.getY(0.0))
            && Doubles.isFinite(packet.getZ(0.0))
            && Floats.isFinite(packet.getPitch(0.0F))
            && Floats.isFinite(packet.getYaw(0.0F))
         ? Math.abs(packet.getX(0.0)) > 3.0E7 || Math.abs(packet.getY(0.0)) > 3.0E7 || Math.abs(packet.getZ(0.0)) > 3.0E7
         : true;
   }

   private static boolean validateVehicleMove(VehicleMoveC2SPacket packet) {
      return !Doubles.isFinite(packet.getX())
         || !Doubles.isFinite(packet.getY())
         || !Doubles.isFinite(packet.getZ())
         || !Floats.isFinite(packet.getPitch())
         || !Floats.isFinite(packet.getYaw());
   }

   @Override
   public void onVehicleMove(VehicleMoveC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (validateVehicleMove(packet)) {
         this.disconnect(new TranslatableText("multiplayer.disconnect.invalid_vehicle_movement"));
      } else {
         Entity _snowman = this.player.getRootVehicle();
         if (_snowman != this.player && _snowman.getPrimaryPassenger() == this.player && _snowman == this.topmostRiddenEntity) {
            ServerWorld _snowmanx = this.player.getServerWorld();
            double _snowmanxx = _snowman.getX();
            double _snowmanxxx = _snowman.getY();
            double _snowmanxxxx = _snowman.getZ();
            double _snowmanxxxxx = packet.getX();
            double _snowmanxxxxxx = packet.getY();
            double _snowmanxxxxxxx = packet.getZ();
            float _snowmanxxxxxxxx = packet.getYaw();
            float _snowmanxxxxxxxxx = packet.getPitch();
            double _snowmanxxxxxxxxxx = _snowmanxxxxx - this.lastTickRiddenX;
            double _snowmanxxxxxxxxxxx = _snowmanxxxxxx - this.lastTickRiddenY;
            double _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - this.lastTickRiddenZ;
            double _snowmanxxxxxxxxxxxxx = _snowman.getVelocity().lengthSquared();
            double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx > 100.0 && !this.isHost()) {
               LOGGER.warn(
                  "{} (vehicle of {}) moved too quickly! {},{},{}",
                  _snowman.getName().getString(),
                  this.player.getName().getString(),
                  _snowmanxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxx
               );
               this.connection.send(new VehicleMoveS2CPacket(_snowman));
               return;
            }

            boolean _snowmanxxxxxxxxxxxxxxx = _snowmanx.isSpaceEmpty(_snowman, _snowman.getBoundingBox().contract(0.0625));
            _snowmanxxxxxxxxxx = _snowmanxxxxx - this.updatedRiddenX;
            _snowmanxxxxxxxxxxx = _snowmanxxxxxx - this.updatedRiddenY - 1.0E-6;
            _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - this.updatedRiddenZ;
            _snowman.move(MovementType.PLAYER, new Vec3d(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx));
            _snowmanxxxxxxxxxx = _snowmanxxxxx - _snowman.getX();
            _snowmanxxxxxxxxxxx = _snowmanxxxxxx - _snowman.getY();
            if (_snowmanxxxxxxxxxxx > -0.5 || _snowmanxxxxxxxxxxx < 0.5) {
               _snowmanxxxxxxxxxxx = 0.0;
            }

            _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - _snowman.getZ();
            _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
            boolean _snowmanxxxxxxxxxxxxxxxx = false;
            if (_snowmanxxxxxxxxxxxxxx > 0.0625) {
               _snowmanxxxxxxxxxxxxxxxx = true;
               LOGGER.warn("{} (vehicle of {}) moved wrongly! {}", _snowman.getName().getString(), this.player.getName().getString(), Math.sqrt(_snowmanxxxxxxxxxxxxxx));
            }

            _snowman.updatePositionAndAngles(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            boolean _snowmanxxxxxxxxxxxxxxxxx = _snowmanx.isSpaceEmpty(_snowman, _snowman.getBoundingBox().contract(0.0625));
            if (_snowmanxxxxxxxxxxxxxxx && (_snowmanxxxxxxxxxxxxxxxx || !_snowmanxxxxxxxxxxxxxxxxx)) {
               _snowman.updatePositionAndAngles(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
               this.connection.send(new VehicleMoveS2CPacket(_snowman));
               return;
            }

            this.player.getServerWorld().getChunkManager().updateCameraPosition(this.player);
            this.player.increaseTravelMotionStats(this.player.getX() - _snowmanxx, this.player.getY() - _snowmanxxx, this.player.getZ() - _snowmanxxxx);
            this.ridingEntity = _snowmanxxxxxxxxxxx >= -0.03125 && !this.server.isFlightEnabled() && this.method_29780(_snowman);
            this.updatedRiddenX = _snowman.getX();
            this.updatedRiddenY = _snowman.getY();
            this.updatedRiddenZ = _snowman.getZ();
         }
      }
   }

   private boolean method_29780(Entity _snowman) {
      return _snowman.world.method_29546(_snowman.getBoundingBox().expand(0.0625).stretch(0.0, -0.55, 0.0)).allMatch(AbstractBlock.AbstractBlockState::isAir);
   }

   @Override
   public void onTeleportConfirm(TeleportConfirmC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (packet.getTeleportId() == this.requestedTeleportId) {
         this.player
            .updatePositionAndAngles(this.requestedTeleportPos.x, this.requestedTeleportPos.y, this.requestedTeleportPos.z, this.player.yaw, this.player.pitch);
         this.updatedX = this.requestedTeleportPos.x;
         this.updatedY = this.requestedTeleportPos.y;
         this.updatedZ = this.requestedTeleportPos.z;
         if (this.player.isInTeleportationState()) {
            this.player.onTeleportationDone();
         }

         this.requestedTeleportPos = null;
      }
   }

   @Override
   public void onRecipeBookData(RecipeBookDataC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.server.getRecipeManager().get(packet.getRecipeId()).ifPresent(this.player.getRecipeBook()::onRecipeDisplayed);
   }

   @Override
   public void onRecipeCategoryOptions(RecipeCategoryOptionsC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.getRecipeBook().setCategoryOptions(packet.getCategory(), packet.isGuiOpen(), packet.isFilteringCraftable());
   }

   @Override
   public void onAdvancementTab(AdvancementTabC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (packet.getAction() == AdvancementTabC2SPacket.Action.OPENED_TAB) {
         Identifier _snowman = packet.getTabToOpen();
         Advancement _snowmanx = this.server.getAdvancementLoader().get(_snowman);
         if (_snowmanx != null) {
            this.player.getAdvancementTracker().setDisplayTab(_snowmanx);
         }
      }
   }

   @Override
   public void onRequestCommandCompletions(RequestCommandCompletionsC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      StringReader _snowman = new StringReader(packet.getPartialCommand());
      if (_snowman.canRead() && _snowman.peek() == '/') {
         _snowman.skip();
      }

      ParseResults<ServerCommandSource> _snowmanx = this.server.getCommandManager().getDispatcher().parse(_snowman, this.player.getCommandSource());
      this.server
         .getCommandManager()
         .getDispatcher()
         .getCompletionSuggestions(_snowmanx)
         .thenAccept(_snowmanxx -> this.connection.send(new CommandSuggestionsS2CPacket(packet.getCompletionId(), _snowmanxx)));
   }

   @Override
   public void onUpdateCommandBlock(UpdateCommandBlockC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (!this.server.areCommandBlocksEnabled()) {
         this.player.sendSystemMessage(new TranslatableText("advMode.notEnabled"), Util.NIL_UUID);
      } else if (!this.player.isCreativeLevelTwoOp()) {
         this.player.sendSystemMessage(new TranslatableText("advMode.notAllowed"), Util.NIL_UUID);
      } else {
         CommandBlockExecutor _snowman = null;
         CommandBlockBlockEntity _snowmanx = null;
         BlockPos _snowmanxx = packet.getBlockPos();
         BlockEntity _snowmanxxx = this.player.world.getBlockEntity(_snowmanxx);
         if (_snowmanxxx instanceof CommandBlockBlockEntity) {
            _snowmanx = (CommandBlockBlockEntity)_snowmanxxx;
            _snowman = _snowmanx.getCommandExecutor();
         }

         String _snowmanxxxx = packet.getCommand();
         boolean _snowmanxxxxx = packet.shouldTrackOutput();
         if (_snowman != null) {
            CommandBlockBlockEntity.Type _snowmanxxxxxx = _snowmanx.getCommandBlockType();
            Direction _snowmanxxxxxxx = this.player.world.getBlockState(_snowmanxx).get(CommandBlock.FACING);
            switch (packet.getType()) {
               case SEQUENCE:
                  BlockState _snowmanxxxxxxxx = Blocks.CHAIN_COMMAND_BLOCK.getDefaultState();
                  this.player
                     .world
                     .setBlockState(
                        _snowmanxx, _snowmanxxxxxxxx.with(CommandBlock.FACING, _snowmanxxxxxxx).with(CommandBlock.CONDITIONAL, Boolean.valueOf(packet.isConditional())), 2
                     );
                  break;
               case AUTO:
                  BlockState _snowmanxxxxxxxxx = Blocks.REPEATING_COMMAND_BLOCK.getDefaultState();
                  this.player
                     .world
                     .setBlockState(
                        _snowmanxx, _snowmanxxxxxxxxx.with(CommandBlock.FACING, _snowmanxxxxxxx).with(CommandBlock.CONDITIONAL, Boolean.valueOf(packet.isConditional())), 2
                     );
                  break;
               case REDSTONE:
               default:
                  BlockState _snowmanxxxxxxxxxx = Blocks.COMMAND_BLOCK.getDefaultState();
                  this.player
                     .world
                     .setBlockState(
                        _snowmanxx, _snowmanxxxxxxxxxx.with(CommandBlock.FACING, _snowmanxxxxxxx).with(CommandBlock.CONDITIONAL, Boolean.valueOf(packet.isConditional())), 2
                     );
            }

            _snowmanxxx.cancelRemoval();
            this.player.world.setBlockEntity(_snowmanxx, _snowmanxxx);
            _snowman.setCommand(_snowmanxxxx);
            _snowman.shouldTrackOutput(_snowmanxxxxx);
            if (!_snowmanxxxxx) {
               _snowman.setLastOutput(null);
            }

            _snowmanx.setAuto(packet.isAlwaysActive());
            if (_snowmanxxxxxx != packet.getType()) {
               _snowmanx.method_23359();
            }

            _snowman.markDirty();
            if (!ChatUtil.isEmpty(_snowmanxxxx)) {
               this.player.sendSystemMessage(new TranslatableText("advMode.setCommand.success", _snowmanxxxx), Util.NIL_UUID);
            }
         }
      }
   }

   @Override
   public void onUpdateCommandBlockMinecart(UpdateCommandBlockMinecartC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (!this.server.areCommandBlocksEnabled()) {
         this.player.sendSystemMessage(new TranslatableText("advMode.notEnabled"), Util.NIL_UUID);
      } else if (!this.player.isCreativeLevelTwoOp()) {
         this.player.sendSystemMessage(new TranslatableText("advMode.notAllowed"), Util.NIL_UUID);
      } else {
         CommandBlockExecutor _snowman = packet.getMinecartCommandExecutor(this.player.world);
         if (_snowman != null) {
            _snowman.setCommand(packet.getCommand());
            _snowman.shouldTrackOutput(packet.shouldTrackOutput());
            if (!packet.shouldTrackOutput()) {
               _snowman.setLastOutput(null);
            }

            _snowman.markDirty();
            this.player.sendSystemMessage(new TranslatableText("advMode.setCommand.success", packet.getCommand()), Util.NIL_UUID);
         }
      }
   }

   @Override
   public void onPickFromInventory(PickFromInventoryC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.inventory.swapSlotWithHotbar(packet.getSlot());
      this.player
         .networkHandler
         .sendPacket(
            new ScreenHandlerSlotUpdateS2CPacket(-2, this.player.inventory.selectedSlot, this.player.inventory.getStack(this.player.inventory.selectedSlot))
         );
      this.player.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, packet.getSlot(), this.player.inventory.getStack(packet.getSlot())));
      this.player.networkHandler.sendPacket(new HeldItemChangeS2CPacket(this.player.inventory.selectedSlot));
   }

   @Override
   public void onRenameItem(RenameItemC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (this.player.currentScreenHandler instanceof AnvilScreenHandler) {
         AnvilScreenHandler _snowman = (AnvilScreenHandler)this.player.currentScreenHandler;
         String _snowmanx = SharedConstants.stripInvalidChars(packet.getName());
         if (_snowmanx.length() <= 35) {
            _snowman.setNewItemName(_snowmanx);
         }
      }
   }

   @Override
   public void onUpdateBeacon(UpdateBeaconC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (this.player.currentScreenHandler instanceof BeaconScreenHandler) {
         ((BeaconScreenHandler)this.player.currentScreenHandler).setEffects(packet.getPrimaryEffectId(), packet.getSecondaryEffectId());
      }
   }

   @Override
   public void onStructureBlockUpdate(UpdateStructureBlockC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (this.player.isCreativeLevelTwoOp()) {
         BlockPos _snowman = packet.getPos();
         BlockState _snowmanx = this.player.world.getBlockState(_snowman);
         BlockEntity _snowmanxx = this.player.world.getBlockEntity(_snowman);
         if (_snowmanxx instanceof StructureBlockBlockEntity) {
            StructureBlockBlockEntity _snowmanxxx = (StructureBlockBlockEntity)_snowmanxx;
            _snowmanxxx.setMode(packet.getMode());
            _snowmanxxx.setStructureName(packet.getStructureName());
            _snowmanxxx.setOffset(packet.getOffset());
            _snowmanxxx.setSize(packet.getSize());
            _snowmanxxx.setMirror(packet.getMirror());
            _snowmanxxx.setRotation(packet.getRotation());
            _snowmanxxx.setMetadata(packet.getMetadata());
            _snowmanxxx.setIgnoreEntities(packet.getIgnoreEntities());
            _snowmanxxx.setShowAir(packet.shouldShowAir());
            _snowmanxxx.setShowBoundingBox(packet.shouldShowBoundingBox());
            _snowmanxxx.setIntegrity(packet.getIntegrity());
            _snowmanxxx.setSeed(packet.getSeed());
            if (_snowmanxxx.hasStructureName()) {
               String _snowmanxxxx = _snowmanxxx.getStructureName();
               if (packet.getAction() == StructureBlockBlockEntity.Action.SAVE_AREA) {
                  if (_snowmanxxx.saveStructure()) {
                     this.player.sendMessage(new TranslatableText("structure_block.save_success", _snowmanxxxx), false);
                  } else {
                     this.player.sendMessage(new TranslatableText("structure_block.save_failure", _snowmanxxxx), false);
                  }
               } else if (packet.getAction() == StructureBlockBlockEntity.Action.LOAD_AREA) {
                  if (!_snowmanxxx.isStructureAvailable()) {
                     this.player.sendMessage(new TranslatableText("structure_block.load_not_found", _snowmanxxxx), false);
                  } else if (_snowmanxxx.loadStructure(this.player.getServerWorld())) {
                     this.player.sendMessage(new TranslatableText("structure_block.load_success", _snowmanxxxx), false);
                  } else {
                     this.player.sendMessage(new TranslatableText("structure_block.load_prepare", _snowmanxxxx), false);
                  }
               } else if (packet.getAction() == StructureBlockBlockEntity.Action.SCAN_AREA) {
                  if (_snowmanxxx.detectStructureSize()) {
                     this.player.sendMessage(new TranslatableText("structure_block.size_success", _snowmanxxxx), false);
                  } else {
                     this.player.sendMessage(new TranslatableText("structure_block.size_failure"), false);
                  }
               }
            } else {
               this.player.sendMessage(new TranslatableText("structure_block.invalid_structure_name", packet.getStructureName()), false);
            }

            _snowmanxxx.markDirty();
            this.player.world.updateListeners(_snowman, _snowmanx, _snowmanx, 3);
         }
      }
   }

   @Override
   public void onJigsawUpdate(UpdateJigsawC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (this.player.isCreativeLevelTwoOp()) {
         BlockPos _snowman = packet.getPos();
         BlockState _snowmanx = this.player.world.getBlockState(_snowman);
         BlockEntity _snowmanxx = this.player.world.getBlockEntity(_snowman);
         if (_snowmanxx instanceof JigsawBlockEntity) {
            JigsawBlockEntity _snowmanxxx = (JigsawBlockEntity)_snowmanxx;
            _snowmanxxx.setAttachmentType(packet.getAttachmentType());
            _snowmanxxx.setTargetPool(packet.getTargetPool());
            _snowmanxxx.setPool(packet.getPool());
            _snowmanxxx.setFinalState(packet.getFinalState());
            _snowmanxxx.setJoint(packet.getJointType());
            _snowmanxxx.markDirty();
            this.player.world.updateListeners(_snowman, _snowmanx, _snowmanx, 3);
         }
      }
   }

   @Override
   public void onJigsawGenerating(JigsawGeneratingC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (this.player.isCreativeLevelTwoOp()) {
         BlockPos _snowman = packet.getPos();
         BlockEntity _snowmanx = this.player.world.getBlockEntity(_snowman);
         if (_snowmanx instanceof JigsawBlockEntity) {
            JigsawBlockEntity _snowmanxx = (JigsawBlockEntity)_snowmanx;
            _snowmanxx.generate(this.player.getServerWorld(), packet.getMaxDepth(), packet.shouldKeepJigsaws());
         }
      }
   }

   @Override
   public void onMerchantTradeSelect(SelectMerchantTradeC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      int _snowman = packet.getTradeId();
      ScreenHandler _snowmanx = this.player.currentScreenHandler;
      if (_snowmanx instanceof MerchantScreenHandler) {
         MerchantScreenHandler _snowmanxx = (MerchantScreenHandler)_snowmanx;
         _snowmanxx.setRecipeIndex(_snowman);
         _snowmanxx.switchTo(_snowman);
      }
   }

   @Override
   public void onBookUpdate(BookUpdateC2SPacket packet) {
      ItemStack _snowman = packet.getBook();
      if (_snowman.getItem() == Items.WRITABLE_BOOK) {
         CompoundTag _snowmanx = _snowman.getTag();
         if (WritableBookItem.isValid(_snowmanx)) {
            List<String> _snowmanxx = Lists.newArrayList();
            boolean _snowmanxxx = packet.wasSigned();
            if (_snowmanxxx) {
               _snowmanxx.add(_snowmanx.getString("title"));
            }

            ListTag _snowmanxxxx = _snowmanx.getList("pages", 8);

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
               _snowmanxx.add(_snowmanxxxx.getString(_snowmanxxxxx));
            }

            int _snowmanxxxxx = packet.getSlot();
            if (PlayerInventory.isValidHotbarIndex(_snowmanxxxxx) || _snowmanxxxxx == 40) {
               this.filterTexts(
                  _snowmanxx, _snowmanxxx ? _snowmanxxxxxx -> this.method_31276(_snowmanxxxxxx.get(0), _snowmanxxxxxx.subList(1, _snowmanxxxxxx.size()), _snowman) : _snowmanxxxxxx -> this.method_31278(_snowmanxxxxxx, _snowman)
               );
            }
         }
      }
   }

   private void method_31278(List<String> _snowman, int _snowman) {
      ItemStack _snowmanxx = this.player.inventory.getStack(_snowman);
      if (_snowmanxx.getItem() == Items.WRITABLE_BOOK) {
         ListTag _snowmanxxx = new ListTag();
         _snowman.stream().map(StringTag::of).forEach(_snowmanxxx::add);
         _snowmanxx.putSubTag("pages", _snowmanxxx);
      }
   }

   private void method_31276(String _snowman, List<String> _snowman, int _snowman) {
      ItemStack _snowmanxxx = this.player.inventory.getStack(_snowman);
      if (_snowmanxxx.getItem() == Items.WRITABLE_BOOK) {
         ItemStack _snowmanxxxx = new ItemStack(Items.WRITTEN_BOOK);
         CompoundTag _snowmanxxxxx = _snowmanxxx.getTag();
         if (_snowmanxxxxx != null) {
            _snowmanxxxx.setTag(_snowmanxxxxx.copy());
         }

         _snowmanxxxx.putSubTag("author", StringTag.of(this.player.getName().getString()));
         _snowmanxxxx.putSubTag("title", StringTag.of(_snowman));
         ListTag _snowmanxxxxxx = new ListTag();

         for (String _snowmanxxxxxxx : _snowman) {
            Text _snowmanxxxxxxxx = new LiteralText(_snowmanxxxxxxx);
            String _snowmanxxxxxxxxx = Text.Serializer.toJson(_snowmanxxxxxxxx);
            _snowmanxxxxxx.add(StringTag.of(_snowmanxxxxxxxxx));
         }

         _snowmanxxxx.putSubTag("pages", _snowmanxxxxxx);
         this.player.inventory.setStack(_snowman, _snowmanxxxx);
      }
   }

   @Override
   public void onQueryEntityNbt(QueryEntityNbtC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (this.player.hasPermissionLevel(2)) {
         Entity _snowman = this.player.getServerWorld().getEntityById(packet.getEntityId());
         if (_snowman != null) {
            CompoundTag _snowmanx = _snowman.toTag(new CompoundTag());
            this.player.networkHandler.sendPacket(new TagQueryResponseS2CPacket(packet.getTransactionId(), _snowmanx));
         }
      }
   }

   @Override
   public void onQueryBlockNbt(QueryBlockNbtC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (this.player.hasPermissionLevel(2)) {
         BlockEntity _snowman = this.player.getServerWorld().getBlockEntity(packet.getPos());
         CompoundTag _snowmanx = _snowman != null ? _snowman.toTag(new CompoundTag()) : null;
         this.player.networkHandler.sendPacket(new TagQueryResponseS2CPacket(packet.getTransactionId(), _snowmanx));
      }
   }

   @Override
   public void onPlayerMove(PlayerMoveC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (validatePlayerMove(packet)) {
         this.disconnect(new TranslatableText("multiplayer.disconnect.invalid_player_movement"));
      } else {
         ServerWorld _snowman = this.player.getServerWorld();
         if (!this.player.notInAnyWorld) {
            if (this.ticks == 0) {
               this.syncWithPlayerPosition();
            }

            if (this.requestedTeleportPos != null) {
               if (this.ticks - this.teleportRequestTick > 20) {
                  this.teleportRequestTick = this.ticks;
                  this.requestTeleport(
                     this.requestedTeleportPos.x, this.requestedTeleportPos.y, this.requestedTeleportPos.z, this.player.yaw, this.player.pitch
                  );
               }
            } else {
               this.teleportRequestTick = this.ticks;
               if (this.player.hasVehicle()) {
                  this.player
                     .updatePositionAndAngles(
                        this.player.getX(), this.player.getY(), this.player.getZ(), packet.getYaw(this.player.yaw), packet.getPitch(this.player.pitch)
                     );
                  this.player.getServerWorld().getChunkManager().updateCameraPosition(this.player);
               } else {
                  double _snowmanx = this.player.getX();
                  double _snowmanxx = this.player.getY();
                  double _snowmanxxx = this.player.getZ();
                  double _snowmanxxxx = this.player.getY();
                  double _snowmanxxxxx = packet.getX(this.player.getX());
                  double _snowmanxxxxxx = packet.getY(this.player.getY());
                  double _snowmanxxxxxxx = packet.getZ(this.player.getZ());
                  float _snowmanxxxxxxxx = packet.getYaw(this.player.yaw);
                  float _snowmanxxxxxxxxx = packet.getPitch(this.player.pitch);
                  double _snowmanxxxxxxxxxx = _snowmanxxxxx - this.lastTickX;
                  double _snowmanxxxxxxxxxxx = _snowmanxxxxxx - this.lastTickY;
                  double _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - this.lastTickZ;
                  double _snowmanxxxxxxxxxxxxx = this.player.getVelocity().lengthSquared();
                  double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
                  if (this.player.isSleeping()) {
                     if (_snowmanxxxxxxxxxxxxxx > 1.0) {
                        this.requestTeleport(
                           this.player.getX(), this.player.getY(), this.player.getZ(), packet.getYaw(this.player.yaw), packet.getPitch(this.player.pitch)
                        );
                     }
                  } else {
                     this.movePacketsCount++;
                     int _snowmanxxxxxxxxxxxxxxx = this.movePacketsCount - this.lastTickMovePacketsCount;
                     if (_snowmanxxxxxxxxxxxxxxx > 5) {
                        LOGGER.debug(
                           "{} is sending move packets too frequently ({} packets since last tick)", this.player.getName().getString(), _snowmanxxxxxxxxxxxxxxx
                        );
                        _snowmanxxxxxxxxxxxxxxx = 1;
                     }

                     if (!this.player.isInTeleportationState()
                        && (!this.player.getServerWorld().getGameRules().getBoolean(GameRules.DISABLE_ELYTRA_MOVEMENT_CHECK) || !this.player.isFallFlying())) {
                        float _snowmanxxxxxxxxxxxxxxxx = this.player.isFallFlying() ? 300.0F : 100.0F;
                        if (_snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx > (double)(_snowmanxxxxxxxxxxxxxxxx * (float)_snowmanxxxxxxxxxxxxxxx) && !this.isHost()) {
                           LOGGER.warn("{} moved too quickly! {},{},{}", this.player.getName().getString(), _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
                           this.requestTeleport(this.player.getX(), this.player.getY(), this.player.getZ(), this.player.yaw, this.player.pitch);
                           return;
                        }
                     }

                     Box _snowmanxxxxxxxxxxxxxxxx = this.player.getBoundingBox();
                     _snowmanxxxxxxxxxx = _snowmanxxxxx - this.updatedX;
                     _snowmanxxxxxxxxxxx = _snowmanxxxxxx - this.updatedY;
                     _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - this.updatedZ;
                     boolean _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx > 0.0;
                     if (this.player.isOnGround() && !packet.isOnGround() && _snowmanxxxxxxxxxxxxxxxxx) {
                        this.player.jump();
                     }

                     this.player.move(MovementType.PLAYER, new Vec3d(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx));
                     _snowmanxxxxxxxxxx = _snowmanxxxxx - this.player.getX();
                     _snowmanxxxxxxxxxxx = _snowmanxxxxxx - this.player.getY();
                     if (_snowmanxxxxxxxxxxx > -0.5 || _snowmanxxxxxxxxxxx < 0.5) {
                        _snowmanxxxxxxxxxxx = 0.0;
                     }

                     _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx - this.player.getZ();
                     _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
                     boolean _snowmanxxxxxxxxxxxxxxxxxx = false;
                     if (!this.player.isInTeleportationState()
                        && _snowmanxxxxxxxxxxxxxx > 0.0625
                        && !this.player.isSleeping()
                        && !this.player.interactionManager.isCreative()
                        && this.player.interactionManager.getGameMode() != GameMode.SPECTATOR) {
                        _snowmanxxxxxxxxxxxxxxxxxx = true;
                        LOGGER.warn("{} moved wrongly!", this.player.getName().getString());
                     }

                     this.player.updatePositionAndAngles(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
                     if (this.player.noClip
                        || this.player.isSleeping()
                        || (!_snowmanxxxxxxxxxxxxxxxxxx || !_snowman.isSpaceEmpty(this.player, _snowmanxxxxxxxxxxxxxxxx))
                           && !this.isPlayerNotCollidingWithBlocks(_snowman, _snowmanxxxxxxxxxxxxxxxx)) {
                        this.floating = _snowmanxxxxxxxxxxx >= -0.03125
                           && this.player.interactionManager.getGameMode() != GameMode.SPECTATOR
                           && !this.server.isFlightEnabled()
                           && !this.player.abilities.allowFlying
                           && !this.player.hasStatusEffect(StatusEffects.LEVITATION)
                           && !this.player.isFallFlying()
                           && this.method_29780(this.player);
                        this.player.getServerWorld().getChunkManager().updateCameraPosition(this.player);
                        this.player.handleFall(this.player.getY() - _snowmanxxxx, packet.isOnGround());
                        this.player.setOnGround(packet.isOnGround());
                        if (_snowmanxxxxxxxxxxxxxxxxx) {
                           this.player.fallDistance = 0.0F;
                        }

                        this.player.increaseTravelMotionStats(this.player.getX() - _snowmanx, this.player.getY() - _snowmanxx, this.player.getZ() - _snowmanxxx);
                        this.updatedX = this.player.getX();
                        this.updatedY = this.player.getY();
                        this.updatedZ = this.player.getZ();
                     } else {
                        this.requestTeleport(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
                     }
                  }
               }
            }
         }
      }
   }

   private boolean isPlayerNotCollidingWithBlocks(WorldView _snowman, Box _snowman) {
      Stream<VoxelShape> _snowmanxx = _snowman.getCollisions(this.player, this.player.getBoundingBox().contract(1.0E-5F), _snowmanxxx -> true);
      VoxelShape _snowmanxxx = VoxelShapes.cuboid(_snowman.contract(1.0E-5F));
      return _snowmanxx.anyMatch(_snowmanxxxx -> !VoxelShapes.matchesAnywhere(_snowmanxxxx, _snowman, BooleanBiFunction.AND));
   }

   public void requestTeleport(double x, double y, double z, float yaw, float pitch) {
      this.teleportRequest(x, y, z, yaw, pitch, Collections.emptySet());
   }

   public void teleportRequest(double x, double y, double z, float yaw, float pitch, Set<PlayerPositionLookS2CPacket.Flag> _snowman) {
      double _snowmanx = _snowman.contains(PlayerPositionLookS2CPacket.Flag.X) ? this.player.getX() : 0.0;
      double _snowmanxx = _snowman.contains(PlayerPositionLookS2CPacket.Flag.Y) ? this.player.getY() : 0.0;
      double _snowmanxxx = _snowman.contains(PlayerPositionLookS2CPacket.Flag.Z) ? this.player.getZ() : 0.0;
      float _snowmanxxxx = _snowman.contains(PlayerPositionLookS2CPacket.Flag.Y_ROT) ? this.player.yaw : 0.0F;
      float _snowmanxxxxx = _snowman.contains(PlayerPositionLookS2CPacket.Flag.X_ROT) ? this.player.pitch : 0.0F;
      this.requestedTeleportPos = new Vec3d(x, y, z);
      if (++this.requestedTeleportId == Integer.MAX_VALUE) {
         this.requestedTeleportId = 0;
      }

      this.teleportRequestTick = this.ticks;
      this.player.updatePositionAndAngles(x, y, z, yaw, pitch);
      this.player
         .networkHandler
         .sendPacket(new PlayerPositionLookS2CPacket(x - _snowmanx, y - _snowmanxx, z - _snowmanxxx, yaw - _snowmanxxxx, pitch - _snowmanxxxxx, _snowman, this.requestedTeleportId));
   }

   @Override
   public void onPlayerAction(PlayerActionC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      BlockPos _snowman = packet.getPos();
      this.player.updateLastActionTime();
      PlayerActionC2SPacket.Action _snowmanx = packet.getAction();
      switch (_snowmanx) {
         case SWAP_ITEM_WITH_OFFHAND:
            if (!this.player.isSpectator()) {
               ItemStack _snowmanxx = this.player.getStackInHand(Hand.OFF_HAND);
               this.player.setStackInHand(Hand.OFF_HAND, this.player.getStackInHand(Hand.MAIN_HAND));
               this.player.setStackInHand(Hand.MAIN_HAND, _snowmanxx);
               this.player.clearActiveItem();
            }

            return;
         case DROP_ITEM:
            if (!this.player.isSpectator()) {
               this.player.dropSelectedItem(false);
            }

            return;
         case DROP_ALL_ITEMS:
            if (!this.player.isSpectator()) {
               this.player.dropSelectedItem(true);
            }

            return;
         case RELEASE_USE_ITEM:
            this.player.stopUsingItem();
            return;
         case START_DESTROY_BLOCK:
         case ABORT_DESTROY_BLOCK:
         case STOP_DESTROY_BLOCK:
            this.player.interactionManager.processBlockBreakingAction(_snowman, _snowmanx, packet.getDirection(), this.server.getWorldHeight());
            return;
         default:
            throw new IllegalArgumentException("Invalid player action");
      }
   }

   private static boolean canPlace(ServerPlayerEntity player, ItemStack stack) {
      if (stack.isEmpty()) {
         return false;
      } else {
         Item _snowman = stack.getItem();
         return (_snowman instanceof BlockItem || _snowman instanceof BucketItem) && !player.getItemCooldownManager().isCoolingDown(_snowman);
      }
   }

   @Override
   public void onPlayerInteractBlock(PlayerInteractBlockC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      ServerWorld _snowman = this.player.getServerWorld();
      Hand _snowmanx = packet.getHand();
      ItemStack _snowmanxx = this.player.getStackInHand(_snowmanx);
      BlockHitResult _snowmanxxx = packet.getBlockHitResult();
      BlockPos _snowmanxxxx = _snowmanxxx.getBlockPos();
      Direction _snowmanxxxxx = _snowmanxxx.getSide();
      this.player.updateLastActionTime();
      if (_snowmanxxxx.getY() < this.server.getWorldHeight()) {
         if (this.requestedTeleportPos == null
            && this.player.squaredDistanceTo((double)_snowmanxxxx.getX() + 0.5, (double)_snowmanxxxx.getY() + 0.5, (double)_snowmanxxxx.getZ() + 0.5) < 64.0
            && _snowman.canPlayerModifyAt(this.player, _snowmanxxxx)) {
            ActionResult _snowmanxxxxxx = this.player.interactionManager.interactBlock(this.player, _snowman, _snowmanxx, _snowmanx, _snowmanxxx);
            if (_snowmanxxxxx == Direction.UP && !_snowmanxxxxxx.isAccepted() && _snowmanxxxx.getY() >= this.server.getWorldHeight() - 1 && canPlace(this.player, _snowmanxx)) {
               Text _snowmanxxxxxxx = new TranslatableText("build.tooHigh", this.server.getWorldHeight()).formatted(Formatting.RED);
               this.player.networkHandler.sendPacket(new GameMessageS2CPacket(_snowmanxxxxxxx, MessageType.GAME_INFO, Util.NIL_UUID));
            } else if (_snowmanxxxxxx.shouldSwingHand()) {
               this.player.swingHand(_snowmanx, true);
            }
         }
      } else {
         Text _snowmanxxxxxx = new TranslatableText("build.tooHigh", this.server.getWorldHeight()).formatted(Formatting.RED);
         this.player.networkHandler.sendPacket(new GameMessageS2CPacket(_snowmanxxxxxx, MessageType.GAME_INFO, Util.NIL_UUID));
      }

      this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(_snowman, _snowmanxxxx));
      this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(_snowman, _snowmanxxxx.offset(_snowmanxxxxx)));
   }

   @Override
   public void onPlayerInteractItem(PlayerInteractItemC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      ServerWorld _snowman = this.player.getServerWorld();
      Hand _snowmanx = packet.getHand();
      ItemStack _snowmanxx = this.player.getStackInHand(_snowmanx);
      this.player.updateLastActionTime();
      if (!_snowmanxx.isEmpty()) {
         ActionResult _snowmanxxx = this.player.interactionManager.interactItem(this.player, _snowman, _snowmanxx, _snowmanx);
         if (_snowmanxxx.shouldSwingHand()) {
            this.player.swingHand(_snowmanx, true);
         }
      }
   }

   @Override
   public void onSpectatorTeleport(SpectatorTeleportC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (this.player.isSpectator()) {
         for (ServerWorld _snowman : this.server.getWorlds()) {
            Entity _snowmanx = packet.getTarget(_snowman);
            if (_snowmanx != null) {
               this.player.teleport(_snowman, _snowmanx.getX(), _snowmanx.getY(), _snowmanx.getZ(), _snowmanx.yaw, _snowmanx.pitch);
               return;
            }
         }
      }
   }

   @Override
   public void onResourcePackStatus(ResourcePackStatusC2SPacket packet) {
   }

   @Override
   public void onBoatPaddleState(BoatPaddleStateC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      Entity _snowman = this.player.getVehicle();
      if (_snowman instanceof BoatEntity) {
         ((BoatEntity)_snowman).setPaddleMovings(packet.isLeftPaddling(), packet.isRightPaddling());
      }
   }

   @Override
   public void onDisconnected(Text reason) {
      LOGGER.info("{} lost connection: {}", this.player.getName().getString(), reason.getString());
      this.server.forcePlayerSampleUpdate();
      this.server
         .getPlayerManager()
         .broadcastChatMessage(
            new TranslatableText("multiplayer.player.left", this.player.getDisplayName()).formatted(Formatting.YELLOW), MessageType.SYSTEM, Util.NIL_UUID
         );
      this.player.onDisconnect();
      this.server.getPlayerManager().remove(this.player);
      TextStream _snowman = this.player.getTextStream();
      if (_snowman != null) {
         _snowman.onDisconnect();
      }

      if (this.isHost()) {
         LOGGER.info("Stopping singleplayer server as player logged out");
         this.server.stop(false);
      }
   }

   public void sendPacket(Packet<?> packet) {
      this.sendPacket(packet, null);
   }

   public void sendPacket(Packet<?> packet, @Nullable GenericFutureListener<? extends Future<? super Void>> listener) {
      if (packet instanceof GameMessageS2CPacket) {
         GameMessageS2CPacket _snowman = (GameMessageS2CPacket)packet;
         ChatVisibility _snowmanx = this.player.getClientChatVisibility();
         if (_snowmanx == ChatVisibility.HIDDEN && _snowman.getLocation() != MessageType.GAME_INFO) {
            return;
         }

         if (_snowmanx == ChatVisibility.SYSTEM && !_snowman.isNonChat()) {
            return;
         }
      }

      try {
         this.connection.send(packet, listener);
      } catch (Throwable var6) {
         CrashReport _snowmanxx = CrashReport.create(var6, "Sending packet");
         CrashReportSection _snowmanxxx = _snowmanxx.addElement("Packet being sent");
         _snowmanxxx.add("Packet class", () -> packet.getClass().getCanonicalName());
         throw new CrashException(_snowmanxx);
      }
   }

   @Override
   public void onUpdateSelectedSlot(UpdateSelectedSlotC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (packet.getSelectedSlot() >= 0 && packet.getSelectedSlot() < PlayerInventory.getHotbarSize()) {
         if (this.player.inventory.selectedSlot != packet.getSelectedSlot() && this.player.getActiveHand() == Hand.MAIN_HAND) {
            this.player.clearActiveItem();
         }

         this.player.inventory.selectedSlot = packet.getSelectedSlot();
         this.player.updateLastActionTime();
      } else {
         LOGGER.warn("{} tried to set an invalid carried item", this.player.getName().getString());
      }
   }

   @Override
   public void onGameMessage(ChatMessageC2SPacket packet) {
      String _snowman = StringUtils.normalizeSpace(packet.getChatMessage());
      if (_snowman.startsWith("/")) {
         NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
         this.method_31286(_snowman);
      } else {
         this.filterText(_snowman, this::method_31286);
      }
   }

   private void method_31286(String _snowman) {
      if (this.player.getClientChatVisibility() == ChatVisibility.HIDDEN) {
         this.sendPacket(new GameMessageS2CPacket(new TranslatableText("chat.cannotSend").formatted(Formatting.RED), MessageType.SYSTEM, Util.NIL_UUID));
      } else {
         this.player.updateLastActionTime();

         for (int _snowmanx = 0; _snowmanx < _snowman.length(); _snowmanx++) {
            if (!SharedConstants.isValidChar(_snowman.charAt(_snowmanx))) {
               this.disconnect(new TranslatableText("multiplayer.disconnect.illegal_characters"));
               return;
            }
         }

         if (_snowman.startsWith("/")) {
            this.executeCommand(_snowman);
         } else {
            Text _snowmanxx = new TranslatableText("chat.type.text", this.player.getDisplayName(), _snowman);
            this.server.getPlayerManager().broadcastChatMessage(_snowmanxx, MessageType.CHAT, this.player.getUuid());
         }

         this.messageCooldown += 20;
         if (this.messageCooldown > 200 && !this.server.getPlayerManager().isOperator(this.player.getGameProfile())) {
            this.disconnect(new TranslatableText("disconnect.spam"));
         }
      }
   }

   private void executeCommand(String input) {
      this.server.getCommandManager().execute(this.player.getCommandSource(), input);
   }

   @Override
   public void onHandSwing(HandSwingC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.updateLastActionTime();
      this.player.swingHand(packet.getHand());
   }

   @Override
   public void onClientCommand(ClientCommandC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.updateLastActionTime();
      switch (packet.getMode()) {
         case PRESS_SHIFT_KEY:
            this.player.setSneaking(true);
            break;
         case RELEASE_SHIFT_KEY:
            this.player.setSneaking(false);
            break;
         case START_SPRINTING:
            this.player.setSprinting(true);
            break;
         case STOP_SPRINTING:
            this.player.setSprinting(false);
            break;
         case STOP_SLEEPING:
            if (this.player.isSleeping()) {
               this.player.wakeUp(false, true);
               this.requestedTeleportPos = this.player.getPos();
            }
            break;
         case START_RIDING_JUMP:
            if (this.player.getVehicle() instanceof JumpingMount) {
               JumpingMount _snowman = (JumpingMount)this.player.getVehicle();
               int _snowmanx = packet.getMountJumpHeight();
               if (_snowman.canJump() && _snowmanx > 0) {
                  _snowman.startJumping(_snowmanx);
               }
            }
            break;
         case STOP_RIDING_JUMP:
            if (this.player.getVehicle() instanceof JumpingMount) {
               JumpingMount _snowman = (JumpingMount)this.player.getVehicle();
               _snowman.stopJumping();
            }
            break;
         case OPEN_INVENTORY:
            if (this.player.getVehicle() instanceof HorseBaseEntity) {
               ((HorseBaseEntity)this.player.getVehicle()).openInventory(this.player);
            }
            break;
         case START_FALL_FLYING:
            if (!this.player.checkFallFlying()) {
               this.player.stopFallFlying();
            }
            break;
         default:
            throw new IllegalArgumentException("Invalid client command!");
      }
   }

   @Override
   public void onPlayerInteractEntity(PlayerInteractEntityC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      ServerWorld _snowman = this.player.getServerWorld();
      Entity _snowmanx = packet.getEntity(_snowman);
      this.player.updateLastActionTime();
      this.player.setSneaking(packet.isPlayerSneaking());
      if (_snowmanx != null) {
         double _snowmanxx = 36.0;
         if (this.player.squaredDistanceTo(_snowmanx) < 36.0) {
            Hand _snowmanxxx = packet.getHand();
            ItemStack _snowmanxxxx = _snowmanxxx != null ? this.player.getStackInHand(_snowmanxxx).copy() : ItemStack.EMPTY;
            Optional<ActionResult> _snowmanxxxxx = Optional.empty();
            if (packet.getType() == PlayerInteractEntityC2SPacket.InteractionType.INTERACT) {
               _snowmanxxxxx = Optional.of(this.player.interact(_snowmanx, _snowmanxxx));
            } else if (packet.getType() == PlayerInteractEntityC2SPacket.InteractionType.INTERACT_AT) {
               _snowmanxxxxx = Optional.of(_snowmanx.interactAt(this.player, packet.getHitPosition(), _snowmanxxx));
            } else if (packet.getType() == PlayerInteractEntityC2SPacket.InteractionType.ATTACK) {
               if (_snowmanx instanceof ItemEntity || _snowmanx instanceof ExperienceOrbEntity || _snowmanx instanceof PersistentProjectileEntity || _snowmanx == this.player) {
                  this.disconnect(new TranslatableText("multiplayer.disconnect.invalid_entity_attacked"));
                  LOGGER.warn("Player {} tried to attack an invalid entity", this.player.getName().getString());
                  return;
               }

               this.player.attack(_snowmanx);
            }

            if (_snowmanxxxxx.isPresent() && _snowmanxxxxx.get().isAccepted()) {
               Criteria.PLAYER_INTERACTED_WITH_ENTITY.test(this.player, _snowmanxxxx, _snowmanx);
               if (_snowmanxxxxx.get().shouldSwingHand()) {
                  this.player.swingHand(_snowmanxxx, true);
               }
            }
         }
      }
   }

   @Override
   public void onClientStatus(ClientStatusC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.updateLastActionTime();
      ClientStatusC2SPacket.Mode _snowman = packet.getMode();
      switch (_snowman) {
         case PERFORM_RESPAWN:
            if (this.player.notInAnyWorld) {
               this.player.notInAnyWorld = false;
               this.player = this.server.getPlayerManager().respawnPlayer(this.player, true);
               Criteria.CHANGED_DIMENSION.trigger(this.player, World.END, World.OVERWORLD);
            } else {
               if (this.player.getHealth() > 0.0F) {
                  return;
               }

               this.player = this.server.getPlayerManager().respawnPlayer(this.player, false);
               if (this.server.isHardcore()) {
                  this.player.setGameMode(GameMode.SPECTATOR);
                  this.player.getServerWorld().getGameRules().get(GameRules.SPECTATORS_GENERATE_CHUNKS).set(false, this.server);
               }
            }
            break;
         case REQUEST_STATS:
            this.player.getStatHandler().sendStats(this.player);
      }
   }

   @Override
   public void onCloseHandledScreen(CloseHandledScreenC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.closeScreenHandler();
   }

   @Override
   public void onClickSlot(ClickSlotC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.updateLastActionTime();
      if (this.player.currentScreenHandler.syncId == packet.getSyncId() && this.player.currentScreenHandler.isNotRestricted(this.player)) {
         if (this.player.isSpectator()) {
            DefaultedList<ItemStack> _snowman = DefaultedList.of();

            for (int _snowmanx = 0; _snowmanx < this.player.currentScreenHandler.slots.size(); _snowmanx++) {
               _snowman.add(this.player.currentScreenHandler.slots.get(_snowmanx).getStack());
            }

            this.player.onHandlerRegistered(this.player.currentScreenHandler, _snowman);
         } else {
            ItemStack _snowman = this.player.currentScreenHandler.onSlotClick(packet.getSlot(), packet.getClickData(), packet.getActionType(), this.player);
            if (ItemStack.areEqual(packet.getStack(), _snowman)) {
               this.player.networkHandler.sendPacket(new ConfirmScreenActionS2CPacket(packet.getSyncId(), packet.getActionId(), true));
               this.player.skipPacketSlotUpdates = true;
               this.player.currentScreenHandler.sendContentUpdates();
               this.player.updateCursorStack();
               this.player.skipPacketSlotUpdates = false;
            } else {
               this.transactions.put(this.player.currentScreenHandler.syncId, packet.getActionId());
               this.player.networkHandler.sendPacket(new ConfirmScreenActionS2CPacket(packet.getSyncId(), packet.getActionId(), false));
               this.player.currentScreenHandler.setPlayerRestriction(this.player, false);
               DefaultedList<ItemStack> _snowmanx = DefaultedList.of();

               for (int _snowmanxx = 0; _snowmanxx < this.player.currentScreenHandler.slots.size(); _snowmanxx++) {
                  ItemStack _snowmanxxx = this.player.currentScreenHandler.slots.get(_snowmanxx).getStack();
                  _snowmanx.add(_snowmanxxx.isEmpty() ? ItemStack.EMPTY : _snowmanxxx);
               }

               this.player.onHandlerRegistered(this.player.currentScreenHandler, _snowmanx);
            }
         }
      }
   }

   @Override
   public void onCraftRequest(CraftRequestC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.updateLastActionTime();
      if (!this.player.isSpectator()
         && this.player.currentScreenHandler.syncId == packet.getSyncId()
         && this.player.currentScreenHandler.isNotRestricted(this.player)
         && this.player.currentScreenHandler instanceof AbstractRecipeScreenHandler) {
         this.server
            .getRecipeManager()
            .get(packet.getRecipe())
            .ifPresent(
               _snowmanx -> ((AbstractRecipeScreenHandler)this.player.currentScreenHandler).fillInputSlots(packet.shouldCraftAll(), (Recipe<?>)_snowmanx, this.player)
            );
      }
   }

   @Override
   public void onButtonClick(ButtonClickC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.updateLastActionTime();
      if (this.player.currentScreenHandler.syncId == packet.getSyncId()
         && this.player.currentScreenHandler.isNotRestricted(this.player)
         && !this.player.isSpectator()) {
         this.player.currentScreenHandler.onButtonClick(this.player, packet.getButtonId());
         this.player.currentScreenHandler.sendContentUpdates();
      }
   }

   @Override
   public void onCreativeInventoryAction(CreativeInventoryActionC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (this.player.interactionManager.isCreative()) {
         boolean _snowman = packet.getSlot() < 0;
         ItemStack _snowmanx = packet.getItemStack();
         CompoundTag _snowmanxx = _snowmanx.getSubTag("BlockEntityTag");
         if (!_snowmanx.isEmpty() && _snowmanxx != null && _snowmanxx.contains("x") && _snowmanxx.contains("y") && _snowmanxx.contains("z")) {
            BlockPos _snowmanxxx = new BlockPos(_snowmanxx.getInt("x"), _snowmanxx.getInt("y"), _snowmanxx.getInt("z"));
            BlockEntity _snowmanxxxx = this.player.world.getBlockEntity(_snowmanxxx);
            if (_snowmanxxxx != null) {
               CompoundTag _snowmanxxxxx = _snowmanxxxx.toTag(new CompoundTag());
               _snowmanxxxxx.remove("x");
               _snowmanxxxxx.remove("y");
               _snowmanxxxxx.remove("z");
               _snowmanx.putSubTag("BlockEntityTag", _snowmanxxxxx);
            }
         }

         boolean _snowmanxxx = packet.getSlot() >= 1 && packet.getSlot() <= 45;
         boolean _snowmanxxxx = _snowmanx.isEmpty() || _snowmanx.getDamage() >= 0 && _snowmanx.getCount() <= 64 && !_snowmanx.isEmpty();
         if (_snowmanxxx && _snowmanxxxx) {
            if (_snowmanx.isEmpty()) {
               this.player.playerScreenHandler.setStackInSlot(packet.getSlot(), ItemStack.EMPTY);
            } else {
               this.player.playerScreenHandler.setStackInSlot(packet.getSlot(), _snowmanx);
            }

            this.player.playerScreenHandler.setPlayerRestriction(this.player, true);
            this.player.playerScreenHandler.sendContentUpdates();
         } else if (_snowman && _snowmanxxxx && this.creativeItemDropThreshold < 200) {
            this.creativeItemDropThreshold += 20;
            this.player.dropItem(_snowmanx, true);
         }
      }
   }

   @Override
   public void onConfirmScreenAction(ConfirmScreenActionC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      int _snowman = this.player.currentScreenHandler.syncId;
      if (_snowman == packet.getSyncId()
         && this.transactions.getOrDefault(_snowman, (short)(packet.getActionId() + 1)) == packet.getActionId()
         && !this.player.currentScreenHandler.isNotRestricted(this.player)
         && !this.player.isSpectator()) {
         this.player.currentScreenHandler.setPlayerRestriction(this.player, true);
      }
   }

   @Override
   public void onSignUpdate(UpdateSignC2SPacket packet) {
      List<String> _snowman = Stream.of(packet.getText()).map(Formatting::strip).collect(Collectors.toList());
      this.filterTexts(_snowman, _snowmanx -> this.method_31282(packet, _snowmanx));
   }

   private void method_31282(UpdateSignC2SPacket _snowman, List<String> _snowman) {
      this.player.updateLastActionTime();
      ServerWorld _snowmanxx = this.player.getServerWorld();
      BlockPos _snowmanxxx = _snowman.getPos();
      if (_snowmanxx.isChunkLoaded(_snowmanxxx)) {
         BlockState _snowmanxxxx = _snowmanxx.getBlockState(_snowmanxxx);
         BlockEntity _snowmanxxxxx = _snowmanxx.getBlockEntity(_snowmanxxx);
         if (!(_snowmanxxxxx instanceof SignBlockEntity)) {
            return;
         }

         SignBlockEntity _snowmanxxxxxx = (SignBlockEntity)_snowmanxxxxx;
         if (!_snowmanxxxxxx.isEditable() || _snowmanxxxxxx.getEditor() != this.player) {
            LOGGER.warn("Player {} just tried to change non-editable sign", this.player.getName().getString());
            return;
         }

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowman.size(); _snowmanxxxxxxx++) {
            _snowmanxxxxxx.setTextOnRow(_snowmanxxxxxxx, new LiteralText(_snowman.get(_snowmanxxxxxxx)));
         }

         _snowmanxxxxxx.markDirty();
         _snowmanxx.updateListeners(_snowmanxxx, _snowmanxxxx, _snowmanxxxx, 3);
      }
   }

   @Override
   public void onKeepAlive(KeepAliveC2SPacket packet) {
      if (this.waitingForKeepAlive && packet.getId() == this.keepAliveId) {
         int _snowman = (int)(Util.getMeasuringTimeMs() - this.lastKeepAliveTime);
         this.player.pingMilliseconds = (this.player.pingMilliseconds * 3 + _snowman) / 4;
         this.waitingForKeepAlive = false;
      } else if (!this.isHost()) {
         this.disconnect(new TranslatableText("disconnect.timeout"));
      }
   }

   @Override
   public void onPlayerAbilities(UpdatePlayerAbilitiesC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.abilities.flying = packet.isFlying() && this.player.abilities.allowFlying;
   }

   @Override
   public void onClientSettings(ClientSettingsC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      this.player.setClientSettings(packet);
   }

   @Override
   public void onCustomPayload(CustomPayloadC2SPacket packet) {
   }

   @Override
   public void onUpdateDifficulty(UpdateDifficultyC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (this.player.hasPermissionLevel(2) || this.isHost()) {
         this.server.setDifficulty(packet.getDifficulty(), false);
      }
   }

   @Override
   public void onUpdateDifficultyLock(UpdateDifficultyLockC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
      if (this.player.hasPermissionLevel(2) || this.isHost()) {
         this.server.setDifficultyLocked(packet.isDifficultyLocked());
      }
   }
}
