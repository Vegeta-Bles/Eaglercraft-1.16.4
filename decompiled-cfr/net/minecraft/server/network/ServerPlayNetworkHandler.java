/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.primitives.Doubles
 *  com.google.common.primitives.Floats
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.suggestion.Suggestions
 *  io.netty.util.concurrent.Future
 *  io.netty.util.concurrent.GenericFutureListener
 *  it.unimi.dsi.fastutil.ints.Int2ShortMap
 *  it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.network;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
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
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
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
import net.minecraft.world.CommandBlockExecutor;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPlayNetworkHandler
implements ServerPlayPacketListener {
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
        TextStream textStream = player.getTextStream();
        if (textStream != null) {
            textStream.onConnect();
        }
    }

    public void tick() {
        this.syncWithPlayerPosition();
        this.player.prevX = this.player.getX();
        this.player.prevY = this.player.getY();
        this.player.prevZ = this.player.getZ();
        this.player.playerTick();
        this.player.updatePositionAndAngles(this.lastTickX, this.lastTickY, this.lastTickZ, this.player.yaw, this.player.pitch);
        ++this.ticks;
        this.lastTickMovePacketsCount = this.movePacketsCount;
        if (this.floating && !this.player.isSleeping()) {
            if (++this.floatingTicks > 80) {
                LOGGER.warn("{} was kicked for floating too long!", (Object)this.player.getName().getString());
                this.disconnect(new TranslatableText("multiplayer.disconnect.flying"));
                return;
            }
        } else {
            this.floating = false;
            this.floatingTicks = 0;
        }
        this.topmostRiddenEntity = this.player.getRootVehicle();
        if (this.topmostRiddenEntity == this.player || this.topmostRiddenEntity.getPrimaryPassenger() != this.player) {
            this.topmostRiddenEntity = null;
            this.ridingEntity = false;
            this.vehicleFloatingTicks = 0;
        } else {
            this.lastTickRiddenX = this.topmostRiddenEntity.getX();
            this.lastTickRiddenY = this.topmostRiddenEntity.getY();
            this.lastTickRiddenZ = this.topmostRiddenEntity.getZ();
            this.updatedRiddenX = this.topmostRiddenEntity.getX();
            this.updatedRiddenY = this.topmostRiddenEntity.getY();
            this.updatedRiddenZ = this.topmostRiddenEntity.getZ();
            if (this.ridingEntity && this.player.getRootVehicle().getPrimaryPassenger() == this.player) {
                if (++this.vehicleFloatingTicks > 80) {
                    LOGGER.warn("{} was kicked for floating a vehicle too long!", (Object)this.player.getName().getString());
                    this.disconnect(new TranslatableText("multiplayer.disconnect.flying"));
                    return;
                }
            } else {
                this.ridingEntity = false;
                this.vehicleFloatingTicks = 0;
            }
        }
        this.server.getProfiler().push("keepAlive");
        long l = Util.getMeasuringTimeMs();
        if (l - this.lastKeepAliveTime >= 15000L) {
            if (this.waitingForKeepAlive) {
                this.disconnect(new TranslatableText("disconnect.timeout"));
            } else {
                this.waitingForKeepAlive = true;
                this.lastKeepAliveTime = l;
                this.keepAliveId = l;
                this.sendPacket(new KeepAliveS2CPacket(this.keepAliveId));
            }
        }
        this.server.getProfiler().pop();
        if (this.messageCooldown > 0) {
            --this.messageCooldown;
        }
        if (this.creativeItemDropThreshold > 0) {
            --this.creativeItemDropThreshold;
        }
        if (this.player.getLastActionTime() > 0L && this.server.getPlayerIdleTimeout() > 0 && Util.getMeasuringTimeMs() - this.player.getLastActionTime() > (long)(this.server.getPlayerIdleTimeout() * 1000 * 60)) {
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
        this.connection.send(new DisconnectS2CPacket(reason), (GenericFutureListener<? extends Future<? super Void>>)((GenericFutureListener)future -> this.connection.disconnect(reason)));
        this.connection.disableAutoRead();
        this.server.submitAndJoin(this.connection::handleDisconnection);
    }

    private <T> void filterText(T text, Consumer<T> consumer, BiFunction<TextStream, T, CompletableFuture<Optional<T>>> backingFilterer) {
        MinecraftServer minecraftServer = this.player.getServerWorld().getServer();
        Consumer<Object> _snowman2 = object -> {
            if (this.getConnection().isOpen()) {
                consumer.accept(object);
            } else {
                LOGGER.debug("Ignoring packet due to disconnection");
            }
        };
        TextStream _snowman3 = this.player.getTextStream();
        if (_snowman3 != null) {
            backingFilterer.apply(_snowman3, (TextStream)text).thenAcceptAsync(optional -> optional.ifPresent(_snowman2), (Executor)minecraftServer);
        } else {
            minecraftServer.execute(() -> _snowman2.accept(text));
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
        if (!(Doubles.isFinite((double)packet.getX(0.0)) && Doubles.isFinite((double)packet.getY(0.0)) && Doubles.isFinite((double)packet.getZ(0.0)) && Floats.isFinite((float)packet.getPitch(0.0f)) && Floats.isFinite((float)packet.getYaw(0.0f)))) {
            return true;
        }
        return Math.abs(packet.getX(0.0)) > 3.0E7 || Math.abs(packet.getY(0.0)) > 3.0E7 || Math.abs(packet.getZ(0.0)) > 3.0E7;
    }

    private static boolean validateVehicleMove(VehicleMoveC2SPacket packet) {
        return !Doubles.isFinite((double)packet.getX()) || !Doubles.isFinite((double)packet.getY()) || !Doubles.isFinite((double)packet.getZ()) || !Floats.isFinite((float)packet.getPitch()) || !Floats.isFinite((float)packet.getYaw());
    }

    @Override
    public void onVehicleMove(VehicleMoveC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (ServerPlayNetworkHandler.validateVehicleMove(packet)) {
            this.disconnect(new TranslatableText("multiplayer.disconnect.invalid_vehicle_movement"));
            return;
        }
        Entity entity = this.player.getRootVehicle();
        if (entity != this.player && entity.getPrimaryPassenger() == this.player && entity == this.topmostRiddenEntity) {
            ServerWorld serverWorld = this.player.getServerWorld();
            double _snowman2 = entity.getX();
            double _snowman3 = entity.getY();
            double _snowman4 = entity.getZ();
            double _snowman5 = packet.getX();
            double _snowman6 = packet.getY();
            double _snowman7 = packet.getZ();
            float _snowman8 = packet.getYaw();
            float _snowman9 = packet.getPitch();
            double _snowman10 = _snowman5 - this.lastTickRiddenX;
            double _snowman11 = _snowman6 - this.lastTickRiddenY;
            double _snowman12 = _snowman7 - this.lastTickRiddenZ;
            double _snowman13 = _snowman10 * _snowman10 + _snowman11 * _snowman11 + _snowman12 * _snowman12;
            double _snowman14 = entity.getVelocity().lengthSquared();
            if (_snowman13 - _snowman14 > 100.0 && !this.isHost()) {
                LOGGER.warn("{} (vehicle of {}) moved too quickly! {},{},{}", (Object)entity.getName().getString(), (Object)this.player.getName().getString(), (Object)_snowman10, (Object)_snowman11, (Object)_snowman12);
                this.connection.send(new VehicleMoveS2CPacket(entity));
                return;
            }
            boolean _snowman15 = serverWorld.isSpaceEmpty(entity, entity.getBoundingBox().contract(0.0625));
            _snowman10 = _snowman5 - this.updatedRiddenX;
            _snowman11 = _snowman6 - this.updatedRiddenY - 1.0E-6;
            _snowman12 = _snowman7 - this.updatedRiddenZ;
            entity.move(MovementType.PLAYER, new Vec3d(_snowman10, _snowman11, _snowman12));
            double _snowman16 = _snowman11;
            _snowman10 = _snowman5 - entity.getX();
            _snowman11 = _snowman6 - entity.getY();
            if (_snowman11 > -0.5 || _snowman11 < 0.5) {
                _snowman11 = 0.0;
            }
            _snowman12 = _snowman7 - entity.getZ();
            _snowman13 = _snowman10 * _snowman10 + _snowman11 * _snowman11 + _snowman12 * _snowman12;
            boolean _snowman17 = false;
            if (_snowman13 > 0.0625) {
                _snowman17 = true;
                LOGGER.warn("{} (vehicle of {}) moved wrongly! {}", (Object)entity.getName().getString(), (Object)this.player.getName().getString(), (Object)Math.sqrt(_snowman13));
            }
            entity.updatePositionAndAngles(_snowman5, _snowman6, _snowman7, _snowman8, _snowman9);
            boolean _snowman18 = serverWorld.isSpaceEmpty(entity, entity.getBoundingBox().contract(0.0625));
            if (_snowman15 && (_snowman17 || !_snowman18)) {
                entity.updatePositionAndAngles(_snowman2, _snowman3, _snowman4, _snowman8, _snowman9);
                this.connection.send(new VehicleMoveS2CPacket(entity));
                return;
            }
            this.player.getServerWorld().getChunkManager().updateCameraPosition(this.player);
            this.player.increaseTravelMotionStats(this.player.getX() - _snowman2, this.player.getY() - _snowman3, this.player.getZ() - _snowman4);
            this.ridingEntity = _snowman16 >= -0.03125 && !this.server.isFlightEnabled() && this.method_29780(entity);
            this.updatedRiddenX = entity.getX();
            this.updatedRiddenY = entity.getY();
            this.updatedRiddenZ = entity.getZ();
        }
    }

    private boolean method_29780(Entity entity) {
        return entity.world.method_29546(entity.getBoundingBox().expand(0.0625).stretch(0.0, -0.55, 0.0)).allMatch(AbstractBlock.AbstractBlockState::isAir);
    }

    @Override
    public void onTeleportConfirm(TeleportConfirmC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (packet.getTeleportId() == this.requestedTeleportId) {
            this.player.updatePositionAndAngles(this.requestedTeleportPos.x, this.requestedTeleportPos.y, this.requestedTeleportPos.z, this.player.yaw, this.player.pitch);
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
            Identifier identifier = packet.getTabToOpen();
            Advancement _snowman2 = this.server.getAdvancementLoader().get(identifier);
            if (_snowman2 != null) {
                this.player.getAdvancementTracker().setDisplayTab(_snowman2);
            }
        }
    }

    @Override
    public void onRequestCommandCompletions(RequestCommandCompletionsC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        StringReader stringReader = new StringReader(packet.getPartialCommand());
        if (stringReader.canRead() && stringReader.peek() == '/') {
            stringReader.skip();
        }
        ParseResults _snowman2 = this.server.getCommandManager().getDispatcher().parse(stringReader, (Object)this.player.getCommandSource());
        this.server.getCommandManager().getDispatcher().getCompletionSuggestions(_snowman2).thenAccept(suggestions -> this.connection.send(new CommandSuggestionsS2CPacket(packet.getCompletionId(), (Suggestions)suggestions)));
    }

    @Override
    public void onUpdateCommandBlock(UpdateCommandBlockC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (!this.server.areCommandBlocksEnabled()) {
            this.player.sendSystemMessage(new TranslatableText("advMode.notEnabled"), Util.NIL_UUID);
            return;
        }
        if (!this.player.isCreativeLevelTwoOp()) {
            this.player.sendSystemMessage(new TranslatableText("advMode.notAllowed"), Util.NIL_UUID);
            return;
        }
        CommandBlockExecutor commandBlockExecutor = null;
        CommandBlockBlockEntity _snowman2 = null;
        BlockPos _snowman3 = packet.getBlockPos();
        BlockEntity _snowman4 = this.player.world.getBlockEntity(_snowman3);
        if (_snowman4 instanceof CommandBlockBlockEntity) {
            _snowman2 = (CommandBlockBlockEntity)_snowman4;
            commandBlockExecutor = _snowman2.getCommandExecutor();
        }
        String _snowman5 = packet.getCommand();
        boolean _snowman6 = packet.shouldTrackOutput();
        if (commandBlockExecutor != null) {
            CommandBlockBlockEntity.Type type = _snowman2.getCommandBlockType();
            Direction _snowman7 = this.player.world.getBlockState(_snowman3).get(CommandBlock.FACING);
            switch (packet.getType()) {
                case SEQUENCE: {
                    BlockState blockState = Blocks.CHAIN_COMMAND_BLOCK.getDefaultState();
                    this.player.world.setBlockState(_snowman3, (BlockState)((BlockState)blockState.with(CommandBlock.FACING, _snowman7)).with(CommandBlock.CONDITIONAL, packet.isConditional()), 2);
                    break;
                }
                case AUTO: {
                    BlockState blockState = Blocks.REPEATING_COMMAND_BLOCK.getDefaultState();
                    this.player.world.setBlockState(_snowman3, (BlockState)((BlockState)blockState.with(CommandBlock.FACING, _snowman7)).with(CommandBlock.CONDITIONAL, packet.isConditional()), 2);
                    break;
                }
                default: {
                    BlockState blockState = Blocks.COMMAND_BLOCK.getDefaultState();
                    this.player.world.setBlockState(_snowman3, (BlockState)((BlockState)blockState.with(CommandBlock.FACING, _snowman7)).with(CommandBlock.CONDITIONAL, packet.isConditional()), 2);
                }
            }
            _snowman4.cancelRemoval();
            this.player.world.setBlockEntity(_snowman3, _snowman4);
            commandBlockExecutor.setCommand(_snowman5);
            commandBlockExecutor.shouldTrackOutput(_snowman6);
            if (!_snowman6) {
                commandBlockExecutor.setLastOutput(null);
            }
            _snowman2.setAuto(packet.isAlwaysActive());
            if (type != packet.getType()) {
                _snowman2.method_23359();
            }
            commandBlockExecutor.markDirty();
            if (!ChatUtil.isEmpty(_snowman5)) {
                this.player.sendSystemMessage(new TranslatableText("advMode.setCommand.success", _snowman5), Util.NIL_UUID);
            }
        }
    }

    @Override
    public void onUpdateCommandBlockMinecart(UpdateCommandBlockMinecartC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (!this.server.areCommandBlocksEnabled()) {
            this.player.sendSystemMessage(new TranslatableText("advMode.notEnabled"), Util.NIL_UUID);
            return;
        }
        if (!this.player.isCreativeLevelTwoOp()) {
            this.player.sendSystemMessage(new TranslatableText("advMode.notAllowed"), Util.NIL_UUID);
            return;
        }
        CommandBlockExecutor commandBlockExecutor = packet.getMinecartCommandExecutor(this.player.world);
        if (commandBlockExecutor != null) {
            commandBlockExecutor.setCommand(packet.getCommand());
            commandBlockExecutor.shouldTrackOutput(packet.shouldTrackOutput());
            if (!packet.shouldTrackOutput()) {
                commandBlockExecutor.setLastOutput(null);
            }
            commandBlockExecutor.markDirty();
            this.player.sendSystemMessage(new TranslatableText("advMode.setCommand.success", packet.getCommand()), Util.NIL_UUID);
        }
    }

    @Override
    public void onPickFromInventory(PickFromInventoryC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        this.player.inventory.swapSlotWithHotbar(packet.getSlot());
        this.player.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, this.player.inventory.selectedSlot, this.player.inventory.getStack(this.player.inventory.selectedSlot)));
        this.player.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, packet.getSlot(), this.player.inventory.getStack(packet.getSlot())));
        this.player.networkHandler.sendPacket(new HeldItemChangeS2CPacket(this.player.inventory.selectedSlot));
    }

    @Override
    public void onRenameItem(RenameItemC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (this.player.currentScreenHandler instanceof AnvilScreenHandler) {
            AnvilScreenHandler anvilScreenHandler = (AnvilScreenHandler)this.player.currentScreenHandler;
            String _snowman2 = SharedConstants.stripInvalidChars(packet.getName());
            if (_snowman2.length() <= 35) {
                anvilScreenHandler.setNewItemName(_snowman2);
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
        if (!this.player.isCreativeLevelTwoOp()) {
            return;
        }
        BlockPos blockPos = packet.getPos();
        BlockState _snowman2 = this.player.world.getBlockState(blockPos);
        BlockEntity _snowman3 = this.player.world.getBlockEntity(blockPos);
        if (_snowman3 instanceof StructureBlockBlockEntity) {
            StructureBlockBlockEntity structureBlockBlockEntity = (StructureBlockBlockEntity)_snowman3;
            structureBlockBlockEntity.setMode(packet.getMode());
            structureBlockBlockEntity.setStructureName(packet.getStructureName());
            structureBlockBlockEntity.setOffset(packet.getOffset());
            structureBlockBlockEntity.setSize(packet.getSize());
            structureBlockBlockEntity.setMirror(packet.getMirror());
            structureBlockBlockEntity.setRotation(packet.getRotation());
            structureBlockBlockEntity.setMetadata(packet.getMetadata());
            structureBlockBlockEntity.setIgnoreEntities(packet.getIgnoreEntities());
            structureBlockBlockEntity.setShowAir(packet.shouldShowAir());
            structureBlockBlockEntity.setShowBoundingBox(packet.shouldShowBoundingBox());
            structureBlockBlockEntity.setIntegrity(packet.getIntegrity());
            structureBlockBlockEntity.setSeed(packet.getSeed());
            if (structureBlockBlockEntity.hasStructureName()) {
                String string = structureBlockBlockEntity.getStructureName();
                if (packet.getAction() == StructureBlockBlockEntity.Action.SAVE_AREA) {
                    if (structureBlockBlockEntity.saveStructure()) {
                        this.player.sendMessage(new TranslatableText("structure_block.save_success", string), false);
                    } else {
                        this.player.sendMessage(new TranslatableText("structure_block.save_failure", string), false);
                    }
                } else if (packet.getAction() == StructureBlockBlockEntity.Action.LOAD_AREA) {
                    if (!structureBlockBlockEntity.isStructureAvailable()) {
                        this.player.sendMessage(new TranslatableText("structure_block.load_not_found", string), false);
                    } else if (structureBlockBlockEntity.loadStructure(this.player.getServerWorld())) {
                        this.player.sendMessage(new TranslatableText("structure_block.load_success", string), false);
                    } else {
                        this.player.sendMessage(new TranslatableText("structure_block.load_prepare", string), false);
                    }
                } else if (packet.getAction() == StructureBlockBlockEntity.Action.SCAN_AREA) {
                    if (structureBlockBlockEntity.detectStructureSize()) {
                        this.player.sendMessage(new TranslatableText("structure_block.size_success", string), false);
                    } else {
                        this.player.sendMessage(new TranslatableText("structure_block.size_failure"), false);
                    }
                }
            } else {
                this.player.sendMessage(new TranslatableText("structure_block.invalid_structure_name", packet.getStructureName()), false);
            }
            structureBlockBlockEntity.markDirty();
            this.player.world.updateListeners(blockPos, _snowman2, _snowman2, 3);
        }
    }

    @Override
    public void onJigsawUpdate(UpdateJigsawC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (!this.player.isCreativeLevelTwoOp()) {
            return;
        }
        BlockPos blockPos = packet.getPos();
        BlockState _snowman2 = this.player.world.getBlockState(blockPos);
        BlockEntity _snowman3 = this.player.world.getBlockEntity(blockPos);
        if (_snowman3 instanceof JigsawBlockEntity) {
            JigsawBlockEntity jigsawBlockEntity = (JigsawBlockEntity)_snowman3;
            jigsawBlockEntity.setAttachmentType(packet.getAttachmentType());
            jigsawBlockEntity.setTargetPool(packet.getTargetPool());
            jigsawBlockEntity.setPool(packet.getPool());
            jigsawBlockEntity.setFinalState(packet.getFinalState());
            jigsawBlockEntity.setJoint(packet.getJointType());
            jigsawBlockEntity.markDirty();
            this.player.world.updateListeners(blockPos, _snowman2, _snowman2, 3);
        }
    }

    @Override
    public void onJigsawGenerating(JigsawGeneratingC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (!this.player.isCreativeLevelTwoOp()) {
            return;
        }
        BlockPos blockPos = packet.getPos();
        BlockEntity _snowman2 = this.player.world.getBlockEntity(blockPos);
        if (_snowman2 instanceof JigsawBlockEntity) {
            JigsawBlockEntity jigsawBlockEntity = (JigsawBlockEntity)_snowman2;
            jigsawBlockEntity.generate(this.player.getServerWorld(), packet.getMaxDepth(), packet.shouldKeepJigsaws());
        }
    }

    @Override
    public void onMerchantTradeSelect(SelectMerchantTradeC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        int n = packet.getTradeId();
        ScreenHandler _snowman2 = this.player.currentScreenHandler;
        if (_snowman2 instanceof MerchantScreenHandler) {
            MerchantScreenHandler merchantScreenHandler = (MerchantScreenHandler)_snowman2;
            merchantScreenHandler.setRecipeIndex(n);
            merchantScreenHandler.switchTo(n);
        }
    }

    @Override
    public void onBookUpdate(BookUpdateC2SPacket packet) {
        int n;
        ItemStack itemStack = packet.getBook();
        if (itemStack.getItem() != Items.WRITABLE_BOOK) {
            return;
        }
        CompoundTag _snowman2 = itemStack.getTag();
        if (!WritableBookItem.isValid(_snowman2)) {
            return;
        }
        ArrayList _snowman3 = Lists.newArrayList();
        boolean _snowman4 = packet.wasSigned();
        if (_snowman4) {
            _snowman3.add(_snowman2.getString("title"));
        }
        ListTag _snowman5 = _snowman2.getList("pages", 8);
        for (n = 0; n < _snowman5.size(); ++n) {
            _snowman3.add(_snowman5.getString(n));
        }
        n = packet.getSlot();
        if (!PlayerInventory.isValidHotbarIndex(n) && n != 40) {
            return;
        }
        this.filterTexts(_snowman3, _snowman4 ? list -> this.method_31276((String)list.get(0), list.subList(1, list.size()), n) : list -> this.method_31278((List<String>)list, n));
    }

    private void method_31278(List<String> list, int n) {
        ItemStack itemStack = this.player.inventory.getStack(n);
        if (itemStack.getItem() != Items.WRITABLE_BOOK) {
            return;
        }
        ListTag _snowman2 = new ListTag();
        list.stream().map(StringTag::of).forEach(_snowman2::add);
        itemStack.putSubTag("pages", _snowman2);
    }

    private void method_31276(String string, List<String> list, int n) {
        ItemStack itemStack = this.player.inventory.getStack(n);
        if (itemStack.getItem() != Items.WRITABLE_BOOK) {
            return;
        }
        _snowman = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag _snowman2 = itemStack.getTag();
        if (_snowman2 != null) {
            _snowman.setTag(_snowman2.copy());
        }
        _snowman.putSubTag("author", StringTag.of(this.player.getName().getString()));
        _snowman.putSubTag("title", StringTag.of(string));
        ListTag _snowman3 = new ListTag();
        for (String string2 : list) {
            LiteralText literalText = new LiteralText(string2);
            String _snowman4 = Text.Serializer.toJson(literalText);
            _snowman3.add(StringTag.of(_snowman4));
        }
        _snowman.putSubTag("pages", _snowman3);
        this.player.inventory.setStack(n, _snowman);
    }

    @Override
    public void onQueryEntityNbt(QueryEntityNbtC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (!this.player.hasPermissionLevel(2)) {
            return;
        }
        Entity entity = this.player.getServerWorld().getEntityById(packet.getEntityId());
        if (entity != null) {
            CompoundTag compoundTag = entity.toTag(new CompoundTag());
            this.player.networkHandler.sendPacket(new TagQueryResponseS2CPacket(packet.getTransactionId(), compoundTag));
        }
    }

    @Override
    public void onQueryBlockNbt(QueryBlockNbtC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (!this.player.hasPermissionLevel(2)) {
            return;
        }
        BlockEntity blockEntity = this.player.getServerWorld().getBlockEntity(packet.getPos());
        CompoundTag _snowman2 = blockEntity != null ? blockEntity.toTag(new CompoundTag()) : null;
        this.player.networkHandler.sendPacket(new TagQueryResponseS2CPacket(packet.getTransactionId(), _snowman2));
    }

    @Override
    public void onPlayerMove(PlayerMoveC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (ServerPlayNetworkHandler.validatePlayerMove(packet)) {
            this.disconnect(new TranslatableText("multiplayer.disconnect.invalid_player_movement"));
            return;
        }
        ServerWorld serverWorld = this.player.getServerWorld();
        if (this.player.notInAnyWorld) {
            return;
        }
        if (this.ticks == 0) {
            this.syncWithPlayerPosition();
        }
        if (this.requestedTeleportPos != null) {
            if (this.ticks - this.teleportRequestTick > 20) {
                this.teleportRequestTick = this.ticks;
                this.requestTeleport(this.requestedTeleportPos.x, this.requestedTeleportPos.y, this.requestedTeleportPos.z, this.player.yaw, this.player.pitch);
            }
            return;
        }
        this.teleportRequestTick = this.ticks;
        if (this.player.hasVehicle()) {
            this.player.updatePositionAndAngles(this.player.getX(), this.player.getY(), this.player.getZ(), packet.getYaw(this.player.yaw), packet.getPitch(this.player.pitch));
            this.player.getServerWorld().getChunkManager().updateCameraPosition(this.player);
            return;
        }
        double _snowman2 = this.player.getX();
        double _snowman3 = this.player.getY();
        double _snowman4 = this.player.getZ();
        double _snowman5 = this.player.getY();
        double _snowman6 = packet.getX(this.player.getX());
        double _snowman7 = packet.getY(this.player.getY());
        double _snowman8 = packet.getZ(this.player.getZ());
        float _snowman9 = packet.getYaw(this.player.yaw);
        float _snowman10 = packet.getPitch(this.player.pitch);
        double _snowman11 = _snowman6 - this.lastTickX;
        double _snowman12 = _snowman7 - this.lastTickY;
        double _snowman13 = _snowman8 - this.lastTickZ;
        double _snowman14 = this.player.getVelocity().lengthSquared();
        double _snowman15 = _snowman11 * _snowman11 + _snowman12 * _snowman12 + _snowman13 * _snowman13;
        if (this.player.isSleeping()) {
            if (_snowman15 > 1.0) {
                this.requestTeleport(this.player.getX(), this.player.getY(), this.player.getZ(), packet.getYaw(this.player.yaw), packet.getPitch(this.player.pitch));
            }
            return;
        }
        ++this.movePacketsCount;
        int _snowman16 = this.movePacketsCount - this.lastTickMovePacketsCount;
        if (_snowman16 > 5) {
            LOGGER.debug("{} is sending move packets too frequently ({} packets since last tick)", (Object)this.player.getName().getString(), (Object)_snowman16);
            _snowman16 = 1;
        }
        if (!(this.player.isInTeleportationState() || this.player.getServerWorld().getGameRules().getBoolean(GameRules.DISABLE_ELYTRA_MOVEMENT_CHECK) && this.player.isFallFlying())) {
            float f = _snowman = this.player.isFallFlying() ? 300.0f : 100.0f;
            if (_snowman15 - _snowman14 > (double)(_snowman * (float)_snowman16) && !this.isHost()) {
                LOGGER.warn("{} moved too quickly! {},{},{}", (Object)this.player.getName().getString(), (Object)_snowman11, (Object)_snowman12, (Object)_snowman13);
                this.requestTeleport(this.player.getX(), this.player.getY(), this.player.getZ(), this.player.yaw, this.player.pitch);
                return;
            }
        }
        Box _snowman17 = this.player.getBoundingBox();
        _snowman11 = _snowman6 - this.updatedX;
        _snowman12 = _snowman7 - this.updatedY;
        _snowman13 = _snowman8 - this.updatedZ;
        boolean bl = _snowman = _snowman12 > 0.0;
        if (this.player.isOnGround() && !packet.isOnGround() && _snowman) {
            this.player.jump();
        }
        this.player.move(MovementType.PLAYER, new Vec3d(_snowman11, _snowman12, _snowman13));
        double _snowman18 = _snowman12;
        _snowman11 = _snowman6 - this.player.getX();
        _snowman12 = _snowman7 - this.player.getY();
        if (_snowman12 > -0.5 || _snowman12 < 0.5) {
            _snowman12 = 0.0;
        }
        _snowman13 = _snowman8 - this.player.getZ();
        _snowman15 = _snowman11 * _snowman11 + _snowman12 * _snowman12 + _snowman13 * _snowman13;
        boolean _snowman19 = false;
        if (!this.player.isInTeleportationState() && _snowman15 > 0.0625 && !this.player.isSleeping() && !this.player.interactionManager.isCreative() && this.player.interactionManager.getGameMode() != GameMode.SPECTATOR) {
            _snowman19 = true;
            LOGGER.warn("{} moved wrongly!", (Object)this.player.getName().getString());
        }
        this.player.updatePositionAndAngles(_snowman6, _snowman7, _snowman8, _snowman9, _snowman10);
        if (!this.player.noClip && !this.player.isSleeping() && (_snowman19 && serverWorld.isSpaceEmpty(this.player, _snowman17) || this.isPlayerNotCollidingWithBlocks(serverWorld, _snowman17))) {
            this.requestTeleport(_snowman2, _snowman3, _snowman4, _snowman9, _snowman10);
            return;
        }
        this.floating = _snowman18 >= -0.03125 && this.player.interactionManager.getGameMode() != GameMode.SPECTATOR && !this.server.isFlightEnabled() && !this.player.abilities.allowFlying && !this.player.hasStatusEffect(StatusEffects.LEVITATION) && !this.player.isFallFlying() && this.method_29780(this.player);
        this.player.getServerWorld().getChunkManager().updateCameraPosition(this.player);
        this.player.handleFall(this.player.getY() - _snowman5, packet.isOnGround());
        this.player.setOnGround(packet.isOnGround());
        if (_snowman) {
            this.player.fallDistance = 0.0f;
        }
        this.player.increaseTravelMotionStats(this.player.getX() - _snowman2, this.player.getY() - _snowman3, this.player.getZ() - _snowman4);
        this.updatedX = this.player.getX();
        this.updatedY = this.player.getY();
        this.updatedZ = this.player.getZ();
    }

    private boolean isPlayerNotCollidingWithBlocks(WorldView worldView, Box box) {
        Stream<VoxelShape> stream = worldView.getCollisions(this.player, this.player.getBoundingBox().contract(1.0E-5f), entity -> true);
        VoxelShape _snowman2 = VoxelShapes.cuboid(box.contract(1.0E-5f));
        return stream.anyMatch(voxelShape2 -> !VoxelShapes.matchesAnywhere(voxelShape2, _snowman2, BooleanBiFunction.AND));
    }

    public void requestTeleport(double x, double y, double z, float yaw, float pitch) {
        this.teleportRequest(x, y, z, yaw, pitch, Collections.emptySet());
    }

    public void teleportRequest(double x, double y, double z, float yaw, float pitch, Set<PlayerPositionLookS2CPacket.Flag> set) {
        double d = set.contains((Object)PlayerPositionLookS2CPacket.Flag.X) ? this.player.getX() : 0.0;
        _snowman = set.contains((Object)PlayerPositionLookS2CPacket.Flag.Y) ? this.player.getY() : 0.0;
        _snowman = set.contains((Object)PlayerPositionLookS2CPacket.Flag.Z) ? this.player.getZ() : 0.0;
        float _snowman2 = set.contains((Object)PlayerPositionLookS2CPacket.Flag.Y_ROT) ? this.player.yaw : 0.0f;
        float _snowman3 = set.contains((Object)PlayerPositionLookS2CPacket.Flag.X_ROT) ? this.player.pitch : 0.0f;
        this.requestedTeleportPos = new Vec3d(x, y, z);
        if (++this.requestedTeleportId == Integer.MAX_VALUE) {
            this.requestedTeleportId = 0;
        }
        this.teleportRequestTick = this.ticks;
        this.player.updatePositionAndAngles(x, y, z, yaw, pitch);
        this.player.networkHandler.sendPacket(new PlayerPositionLookS2CPacket(x - d, y - _snowman, z - _snowman, yaw - _snowman2, pitch - _snowman3, set, this.requestedTeleportId));
    }

    @Override
    public void onPlayerAction(PlayerActionC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        BlockPos blockPos = packet.getPos();
        this.player.updateLastActionTime();
        PlayerActionC2SPacket.Action _snowman2 = packet.getAction();
        switch (_snowman2) {
            case SWAP_ITEM_WITH_OFFHAND: {
                if (!this.player.isSpectator()) {
                    ItemStack itemStack = this.player.getStackInHand(Hand.OFF_HAND);
                    this.player.setStackInHand(Hand.OFF_HAND, this.player.getStackInHand(Hand.MAIN_HAND));
                    this.player.setStackInHand(Hand.MAIN_HAND, itemStack);
                    this.player.clearActiveItem();
                }
                return;
            }
            case DROP_ITEM: {
                if (!this.player.isSpectator()) {
                    this.player.dropSelectedItem(false);
                }
                return;
            }
            case DROP_ALL_ITEMS: {
                if (!this.player.isSpectator()) {
                    this.player.dropSelectedItem(true);
                }
                return;
            }
            case RELEASE_USE_ITEM: {
                this.player.stopUsingItem();
                return;
            }
            case START_DESTROY_BLOCK: 
            case ABORT_DESTROY_BLOCK: 
            case STOP_DESTROY_BLOCK: {
                this.player.interactionManager.processBlockBreakingAction(blockPos, _snowman2, packet.getDirection(), this.server.getWorldHeight());
                return;
            }
        }
        throw new IllegalArgumentException("Invalid player action");
    }

    private static boolean canPlace(ServerPlayerEntity player, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        Item item = stack.getItem();
        return (item instanceof BlockItem || item instanceof BucketItem) && !player.getItemCooldownManager().isCoolingDown(item);
    }

    @Override
    public void onPlayerInteractBlock(PlayerInteractBlockC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        ServerWorld serverWorld = this.player.getServerWorld();
        Hand _snowman2 = packet.getHand();
        ItemStack _snowman3 = this.player.getStackInHand(_snowman2);
        BlockHitResult _snowman4 = packet.getBlockHitResult();
        BlockPos _snowman5 = _snowman4.getBlockPos();
        Direction _snowman6 = _snowman4.getSide();
        this.player.updateLastActionTime();
        if (_snowman5.getY() < this.server.getWorldHeight()) {
            if (this.requestedTeleportPos == null && this.player.squaredDistanceTo((double)_snowman5.getX() + 0.5, (double)_snowman5.getY() + 0.5, (double)_snowman5.getZ() + 0.5) < 64.0 && serverWorld.canPlayerModifyAt(this.player, _snowman5)) {
                ActionResult actionResult = this.player.interactionManager.interactBlock(this.player, serverWorld, _snowman3, _snowman2, _snowman4);
                if (_snowman6 == Direction.UP && !actionResult.isAccepted() && _snowman5.getY() >= this.server.getWorldHeight() - 1 && ServerPlayNetworkHandler.canPlace(this.player, _snowman3)) {
                    MutableText mutableText = new TranslatableText("build.tooHigh", this.server.getWorldHeight()).formatted(Formatting.RED);
                    this.player.networkHandler.sendPacket(new GameMessageS2CPacket(mutableText, MessageType.GAME_INFO, Util.NIL_UUID));
                } else if (actionResult.shouldSwingHand()) {
                    this.player.swingHand(_snowman2, true);
                }
            }
        } else {
            MutableText mutableText = new TranslatableText("build.tooHigh", this.server.getWorldHeight()).formatted(Formatting.RED);
            this.player.networkHandler.sendPacket(new GameMessageS2CPacket(mutableText, MessageType.GAME_INFO, Util.NIL_UUID));
        }
        this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(serverWorld, _snowman5));
        this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(serverWorld, _snowman5.offset(_snowman6)));
    }

    @Override
    public void onPlayerInteractItem(PlayerInteractItemC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        ServerWorld serverWorld = this.player.getServerWorld();
        Hand _snowman2 = packet.getHand();
        ItemStack _snowman3 = this.player.getStackInHand(_snowman2);
        this.player.updateLastActionTime();
        if (_snowman3.isEmpty()) {
            return;
        }
        ActionResult _snowman4 = this.player.interactionManager.interactItem(this.player, serverWorld, _snowman3, _snowman2);
        if (_snowman4.shouldSwingHand()) {
            this.player.swingHand(_snowman2, true);
        }
    }

    @Override
    public void onSpectatorTeleport(SpectatorTeleportC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (this.player.isSpectator()) {
            for (ServerWorld serverWorld : this.server.getWorlds()) {
                Entity entity = packet.getTarget(serverWorld);
                if (entity == null) continue;
                this.player.teleport(serverWorld, entity.getX(), entity.getY(), entity.getZ(), entity.yaw, entity.pitch);
                return;
            }
        }
    }

    @Override
    public void onResourcePackStatus(ResourcePackStatusC2SPacket packet) {
    }

    @Override
    public void onBoatPaddleState(BoatPaddleStateC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        Entity entity = this.player.getVehicle();
        if (entity instanceof BoatEntity) {
            ((BoatEntity)entity).setPaddleMovings(packet.isLeftPaddling(), packet.isRightPaddling());
        }
    }

    @Override
    public void onDisconnected(Text reason) {
        LOGGER.info("{} lost connection: {}", (Object)this.player.getName().getString(), (Object)reason.getString());
        this.server.forcePlayerSampleUpdate();
        this.server.getPlayerManager().broadcastChatMessage(new TranslatableText("multiplayer.player.left", this.player.getDisplayName()).formatted(Formatting.YELLOW), MessageType.SYSTEM, Util.NIL_UUID);
        this.player.onDisconnect();
        this.server.getPlayerManager().remove(this.player);
        TextStream textStream = this.player.getTextStream();
        if (textStream != null) {
            textStream.onDisconnect();
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
        Object _snowman2;
        if (packet instanceof GameMessageS2CPacket) {
            GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packet;
            _snowman2 = this.player.getClientChatVisibility();
            if (_snowman2 == ChatVisibility.HIDDEN && gameMessageS2CPacket.getLocation() != MessageType.GAME_INFO) {
                return;
            }
            if (_snowman2 == ChatVisibility.SYSTEM && !gameMessageS2CPacket.isNonChat()) {
                return;
            }
        }
        try {
            this.connection.send(packet, listener);
        }
        catch (Throwable throwable) {
            _snowman2 = CrashReport.create(throwable, "Sending packet");
            CrashReportSection crashReportSection = ((CrashReport)_snowman2).addElement("Packet being sent");
            crashReportSection.add("Packet class", () -> packet.getClass().getCanonicalName());
            throw new CrashException((CrashReport)_snowman2);
        }
    }

    @Override
    public void onUpdateSelectedSlot(UpdateSelectedSlotC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (packet.getSelectedSlot() < 0 || packet.getSelectedSlot() >= PlayerInventory.getHotbarSize()) {
            LOGGER.warn("{} tried to set an invalid carried item", (Object)this.player.getName().getString());
            return;
        }
        if (this.player.inventory.selectedSlot != packet.getSelectedSlot() && this.player.getActiveHand() == Hand.MAIN_HAND) {
            this.player.clearActiveItem();
        }
        this.player.inventory.selectedSlot = packet.getSelectedSlot();
        this.player.updateLastActionTime();
    }

    @Override
    public void onGameMessage(ChatMessageC2SPacket packet) {
        String string = StringUtils.normalizeSpace((String)packet.getChatMessage());
        if (string.startsWith("/")) {
            NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
            this.method_31286(string);
        } else {
            this.filterText(string, this::method_31286);
        }
    }

    private void method_31286(String string2) {
        String string2;
        if (this.player.getClientChatVisibility() == ChatVisibility.HIDDEN) {
            this.sendPacket(new GameMessageS2CPacket(new TranslatableText("chat.cannotSend").formatted(Formatting.RED), MessageType.SYSTEM, Util.NIL_UUID));
            return;
        }
        this.player.updateLastActionTime();
        for (int i = 0; i < string2.length(); ++i) {
            if (SharedConstants.isValidChar(string2.charAt(i))) continue;
            this.disconnect(new TranslatableText("multiplayer.disconnect.illegal_characters"));
            return;
        }
        if (string2.startsWith("/")) {
            this.executeCommand(string2);
        } else {
            TranslatableText translatableText = new TranslatableText("chat.type.text", this.player.getDisplayName(), string2);
            this.server.getPlayerManager().broadcastChatMessage(translatableText, MessageType.CHAT, this.player.getUuid());
        }
        this.messageCooldown += 20;
        if (this.messageCooldown > 200 && !this.server.getPlayerManager().isOperator(this.player.getGameProfile())) {
            this.disconnect(new TranslatableText("disconnect.spam"));
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
            case PRESS_SHIFT_KEY: {
                this.player.setSneaking(true);
                break;
            }
            case RELEASE_SHIFT_KEY: {
                this.player.setSneaking(false);
                break;
            }
            case START_SPRINTING: {
                this.player.setSprinting(true);
                break;
            }
            case STOP_SPRINTING: {
                this.player.setSprinting(false);
                break;
            }
            case STOP_SLEEPING: {
                if (!this.player.isSleeping()) break;
                this.player.wakeUp(false, true);
                this.requestedTeleportPos = this.player.getPos();
                break;
            }
            case START_RIDING_JUMP: {
                if (!(this.player.getVehicle() instanceof JumpingMount)) break;
                JumpingMount jumpingMount = (JumpingMount)((Object)this.player.getVehicle());
                int _snowman2 = packet.getMountJumpHeight();
                if (!jumpingMount.canJump() || _snowman2 <= 0) break;
                jumpingMount.startJumping(_snowman2);
                break;
            }
            case STOP_RIDING_JUMP: {
                if (!(this.player.getVehicle() instanceof JumpingMount)) break;
                _snowman = (JumpingMount)((Object)this.player.getVehicle());
                _snowman.stopJumping();
                break;
            }
            case OPEN_INVENTORY: {
                if (!(this.player.getVehicle() instanceof HorseBaseEntity)) break;
                ((HorseBaseEntity)this.player.getVehicle()).openInventory(this.player);
                break;
            }
            case START_FALL_FLYING: {
                if (this.player.checkFallFlying()) break;
                this.player.stopFallFlying();
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid client command!");
            }
        }
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        ServerWorld serverWorld = this.player.getServerWorld();
        Entity _snowman2 = packet.getEntity(serverWorld);
        this.player.updateLastActionTime();
        this.player.setSneaking(packet.isPlayerSneaking());
        if (_snowman2 != null) {
            double d = 36.0;
            if (this.player.squaredDistanceTo(_snowman2) < 36.0) {
                Hand hand = packet.getHand();
                ItemStack _snowman3 = hand != null ? this.player.getStackInHand(hand).copy() : ItemStack.EMPTY;
                Optional<Object> _snowman4 = Optional.empty();
                if (packet.getType() == PlayerInteractEntityC2SPacket.InteractionType.INTERACT) {
                    _snowman4 = Optional.of(this.player.interact(_snowman2, hand));
                } else if (packet.getType() == PlayerInteractEntityC2SPacket.InteractionType.INTERACT_AT) {
                    _snowman4 = Optional.of(_snowman2.interactAt(this.player, packet.getHitPosition(), hand));
                } else if (packet.getType() == PlayerInteractEntityC2SPacket.InteractionType.ATTACK) {
                    if (_snowman2 instanceof ItemEntity || _snowman2 instanceof ExperienceOrbEntity || _snowman2 instanceof PersistentProjectileEntity || _snowman2 == this.player) {
                        this.disconnect(new TranslatableText("multiplayer.disconnect.invalid_entity_attacked"));
                        LOGGER.warn("Player {} tried to attack an invalid entity", (Object)this.player.getName().getString());
                        return;
                    }
                    this.player.attack(_snowman2);
                }
                if (_snowman4.isPresent() && ((ActionResult)((Object)_snowman4.get())).isAccepted()) {
                    Criteria.PLAYER_INTERACTED_WITH_ENTITY.test(this.player, _snowman3, _snowman2);
                    if (((ActionResult)((Object)_snowman4.get())).shouldSwingHand()) {
                        this.player.swingHand(hand, true);
                    }
                }
            }
        }
    }

    @Override
    public void onClientStatus(ClientStatusC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        this.player.updateLastActionTime();
        ClientStatusC2SPacket.Mode mode = packet.getMode();
        switch (mode) {
            case PERFORM_RESPAWN: {
                if (this.player.notInAnyWorld) {
                    this.player.notInAnyWorld = false;
                    this.player = this.server.getPlayerManager().respawnPlayer(this.player, true);
                    Criteria.CHANGED_DIMENSION.trigger(this.player, World.END, World.OVERWORLD);
                    break;
                }
                if (this.player.getHealth() > 0.0f) {
                    return;
                }
                this.player = this.server.getPlayerManager().respawnPlayer(this.player, false);
                if (!this.server.isHardcore()) break;
                this.player.setGameMode(GameMode.SPECTATOR);
                this.player.getServerWorld().getGameRules().get(GameRules.SPECTATORS_GENERATE_CHUNKS).set(false, this.server);
                break;
            }
            case REQUEST_STATS: {
                this.player.getStatHandler().sendStats(this.player);
            }
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
                DefaultedList<ItemStack> defaultedList = DefaultedList.of();
                for (int i = 0; i < this.player.currentScreenHandler.slots.size(); ++i) {
                    defaultedList.add(this.player.currentScreenHandler.slots.get(i).getStack());
                }
                this.player.onHandlerRegistered(this.player.currentScreenHandler, defaultedList);
            } else {
                ItemStack itemStack = this.player.currentScreenHandler.onSlotClick(packet.getSlot(), packet.getClickData(), packet.getActionType(), this.player);
                if (ItemStack.areEqual(packet.getStack(), itemStack)) {
                    this.player.networkHandler.sendPacket(new ConfirmScreenActionS2CPacket(packet.getSyncId(), packet.getActionId(), true));
                    this.player.skipPacketSlotUpdates = true;
                    this.player.currentScreenHandler.sendContentUpdates();
                    this.player.updateCursorStack();
                    this.player.skipPacketSlotUpdates = false;
                } else {
                    this.transactions.put(this.player.currentScreenHandler.syncId, packet.getActionId());
                    this.player.networkHandler.sendPacket(new ConfirmScreenActionS2CPacket(packet.getSyncId(), packet.getActionId(), false));
                    this.player.currentScreenHandler.setPlayerRestriction(this.player, false);
                    DefaultedList<ItemStack> defaultedList = DefaultedList.of();
                    for (int i = 0; i < this.player.currentScreenHandler.slots.size(); ++i) {
                        ItemStack itemStack2 = this.player.currentScreenHandler.slots.get(i).getStack();
                        defaultedList.add(itemStack2.isEmpty() ? ItemStack.EMPTY : itemStack2);
                    }
                    this.player.onHandlerRegistered(this.player.currentScreenHandler, defaultedList);
                }
            }
        }
    }

    @Override
    public void onCraftRequest(CraftRequestC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        this.player.updateLastActionTime();
        if (this.player.isSpectator() || this.player.currentScreenHandler.syncId != packet.getSyncId() || !this.player.currentScreenHandler.isNotRestricted(this.player) || !(this.player.currentScreenHandler instanceof AbstractRecipeScreenHandler)) {
            return;
        }
        this.server.getRecipeManager().get(packet.getRecipe()).ifPresent(recipe -> ((AbstractRecipeScreenHandler)this.player.currentScreenHandler).fillInputSlots(packet.shouldCraftAll(), (Recipe<?>)recipe, this.player));
    }

    @Override
    public void onButtonClick(ButtonClickC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        this.player.updateLastActionTime();
        if (this.player.currentScreenHandler.syncId == packet.getSyncId() && this.player.currentScreenHandler.isNotRestricted(this.player) && !this.player.isSpectator()) {
            this.player.currentScreenHandler.onButtonClick(this.player, packet.getButtonId());
            this.player.currentScreenHandler.sendContentUpdates();
        }
    }

    @Override
    public void onCreativeInventoryAction(CreativeInventoryActionC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (this.player.interactionManager.isCreative()) {
            boolean bl = packet.getSlot() < 0;
            ItemStack _snowman2 = packet.getItemStack();
            CompoundTag _snowman3 = _snowman2.getSubTag("BlockEntityTag");
            if (!_snowman2.isEmpty() && _snowman3 != null && _snowman3.contains("x") && _snowman3.contains("y") && _snowman3.contains("z") && (_snowman = this.player.world.getBlockEntity(_snowman = new BlockPos(_snowman3.getInt("x"), _snowman3.getInt("y"), _snowman3.getInt("z")))) != null) {
                CompoundTag compoundTag = _snowman.toTag(new CompoundTag());
                compoundTag.remove("x");
                compoundTag.remove("y");
                compoundTag.remove("z");
                _snowman2.putSubTag("BlockEntityTag", compoundTag);
            }
            boolean bl2 = packet.getSlot() >= 1 && packet.getSlot() <= 45;
            boolean bl3 = _snowman = _snowman2.isEmpty() || _snowman2.getDamage() >= 0 && _snowman2.getCount() <= 64 && !_snowman2.isEmpty();
            if (bl2 && _snowman) {
                if (_snowman2.isEmpty()) {
                    this.player.playerScreenHandler.setStackInSlot(packet.getSlot(), ItemStack.EMPTY);
                } else {
                    this.player.playerScreenHandler.setStackInSlot(packet.getSlot(), _snowman2);
                }
                this.player.playerScreenHandler.setPlayerRestriction(this.player, true);
                this.player.playerScreenHandler.sendContentUpdates();
            } else if (bl && _snowman && this.creativeItemDropThreshold < 200) {
                this.creativeItemDropThreshold += 20;
                this.player.dropItem(_snowman2, true);
            }
        }
    }

    @Override
    public void onConfirmScreenAction(ConfirmScreenActionC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        int n = this.player.currentScreenHandler.syncId;
        if (n == packet.getSyncId() && this.transactions.getOrDefault(n, (short)(packet.getActionId() + 1)) == packet.getActionId() && !this.player.currentScreenHandler.isNotRestricted(this.player) && !this.player.isSpectator()) {
            this.player.currentScreenHandler.setPlayerRestriction(this.player, true);
        }
    }

    @Override
    public void onSignUpdate(UpdateSignC2SPacket packet) {
        List<String> list2 = Stream.of(packet.getText()).map(Formatting::strip).collect(Collectors.toList());
        this.filterTexts(list2, list -> this.method_31282(packet, (List<String>)list));
    }

    private void method_31282(UpdateSignC2SPacket updateSignC2SPacket, List<String> list) {
        this.player.updateLastActionTime();
        ServerWorld serverWorld = this.player.getServerWorld();
        BlockPos _snowman2 = updateSignC2SPacket.getPos();
        if (serverWorld.isChunkLoaded(_snowman2)) {
            BlockState blockState = serverWorld.getBlockState(_snowman2);
            BlockEntity _snowman3 = serverWorld.getBlockEntity(_snowman2);
            if (!(_snowman3 instanceof SignBlockEntity)) {
                return;
            }
            SignBlockEntity _snowman4 = (SignBlockEntity)_snowman3;
            if (!_snowman4.isEditable() || _snowman4.getEditor() != this.player) {
                LOGGER.warn("Player {} just tried to change non-editable sign", (Object)this.player.getName().getString());
                return;
            }
            for (int i = 0; i < list.size(); ++i) {
                _snowman4.setTextOnRow(i, new LiteralText(list.get(i)));
            }
            _snowman4.markDirty();
            serverWorld.updateListeners(_snowman2, blockState, blockState, 3);
        }
    }

    @Override
    public void onKeepAlive(KeepAliveC2SPacket packet) {
        if (this.waitingForKeepAlive && packet.getId() == this.keepAliveId) {
            int n = (int)(Util.getMeasuringTimeMs() - this.lastKeepAliveTime);
            this.player.pingMilliseconds = (this.player.pingMilliseconds * 3 + n) / 4;
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
        if (!this.player.hasPermissionLevel(2) && !this.isHost()) {
            return;
        }
        this.server.setDifficulty(packet.getDifficulty(), false);
    }

    @Override
    public void onUpdateDifficultyLock(UpdateDifficultyLockC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (!this.player.hasPermissionLevel(2) && !this.isHost()) {
            return;
        }
        this.server.setDifficultyLocked(packet.isDifficultyLocked());
    }
}

