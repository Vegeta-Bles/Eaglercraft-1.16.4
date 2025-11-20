/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.block.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Stainable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ContainerLock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.BeaconScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Heightmap;

public class BeaconBlockEntity
extends BlockEntity
implements NamedScreenHandlerFactory,
Tickable {
    public static final StatusEffect[][] EFFECTS_BY_LEVEL = new StatusEffect[][]{{StatusEffects.SPEED, StatusEffects.HASTE}, {StatusEffects.RESISTANCE, StatusEffects.JUMP_BOOST}, {StatusEffects.STRENGTH}, {StatusEffects.REGENERATION}};
    private static final Set<StatusEffect> EFFECTS = Arrays.stream(EFFECTS_BY_LEVEL).flatMap(Arrays::stream).collect(Collectors.toSet());
    private List<BeamSegment> beamSegments = Lists.newArrayList();
    private List<BeamSegment> field_19178 = Lists.newArrayList();
    private int level;
    private int field_19179 = -1;
    @Nullable
    private StatusEffect primary;
    @Nullable
    private StatusEffect secondary;
    @Nullable
    private Text customName;
    private ContainerLock lock = ContainerLock.EMPTY;
    private final PropertyDelegate propertyDelegate = new PropertyDelegate(this){
        final /* synthetic */ BeaconBlockEntity field_17379;
        {
            this.field_17379 = beaconBlockEntity;
        }

        public int get(int index) {
            switch (index) {
                case 0: {
                    return BeaconBlockEntity.method_17490(this.field_17379);
                }
                case 1: {
                    return StatusEffect.getRawId(BeaconBlockEntity.method_17494(this.field_17379));
                }
                case 2: {
                    return StatusEffect.getRawId(BeaconBlockEntity.method_17496(this.field_17379));
                }
            }
            return 0;
        }

        public void set(int index, int value) {
            switch (index) {
                case 0: {
                    BeaconBlockEntity.method_17491(this.field_17379, value);
                    break;
                }
                case 1: {
                    if (!this.field_17379.world.isClient && !BeaconBlockEntity.method_20294(this.field_17379).isEmpty()) {
                        this.field_17379.playSound(SoundEvents.BLOCK_BEACON_POWER_SELECT);
                    }
                    BeaconBlockEntity.method_17492(this.field_17379, BeaconBlockEntity.method_17493(value));
                    break;
                }
                case 2: {
                    BeaconBlockEntity.method_17495(this.field_17379, BeaconBlockEntity.method_17493(value));
                }
            }
        }

        public int size() {
            return 3;
        }
    };

    public BeaconBlockEntity() {
        super(BlockEntityType.BEACON);
    }

    @Override
    public void tick() {
        int n;
        BlockPos _snowman2;
        int n2 = this.pos.getX();
        _snowman = this.pos.getY();
        _snowman = this.pos.getZ();
        if (this.field_19179 < _snowman) {
            _snowman2 = this.pos;
            this.field_19178 = Lists.newArrayList();
            this.field_19179 = _snowman2.getY() - 1;
        } else {
            _snowman2 = new BlockPos(n2, this.field_19179 + 1, _snowman);
        }
        BeamSegment _snowman6 = this.field_19178.isEmpty() ? null : this.field_19178.get(this.field_19178.size() - 1);
        int _snowman3 = this.world.getTopY(Heightmap.Type.WORLD_SURFACE, n2, _snowman);
        for (n = 0; n < 10 && _snowman2.getY() <= _snowman3; ++n) {
            block18: {
                Block _snowman4;
                block16: {
                    float[] _snowman5;
                    block17: {
                        BlockState blockState = this.world.getBlockState(_snowman2);
                        _snowman4 = blockState.getBlock();
                        if (!(_snowman4 instanceof Stainable)) break block16;
                        _snowman5 = ((Stainable)((Object)_snowman4)).getColor().getColorComponents();
                        if (this.field_19178.size() > 1) break block17;
                        _snowman6 = new BeamSegment(_snowman5);
                        this.field_19178.add(_snowman6);
                        break block18;
                    }
                    if (_snowman6 == null) break block18;
                    if (Arrays.equals(_snowman5, _snowman6.color)) {
                        _snowman6.increaseHeight();
                    } else {
                        _snowman6 = new BeamSegment(new float[]{(_snowman6.color[0] + _snowman5[0]) / 2.0f, (_snowman6.color[1] + _snowman5[1]) / 2.0f, (_snowman6.color[2] + _snowman5[2]) / 2.0f});
                        this.field_19178.add(_snowman6);
                    }
                    break block18;
                }
                if (_snowman6 != null && (blockState.getOpacity(this.world, _snowman2) < 15 || _snowman4 == Blocks.BEDROCK)) {
                    _snowman6.increaseHeight();
                } else {
                    this.field_19178.clear();
                    this.field_19179 = _snowman3;
                    break;
                }
            }
            _snowman2 = _snowman2.up();
            ++this.field_19179;
        }
        n = this.level;
        if (this.world.getTime() % 80L == 0L) {
            if (!this.beamSegments.isEmpty()) {
                this.updateLevel(n2, _snowman, _snowman);
            }
            if (this.level > 0 && !this.beamSegments.isEmpty()) {
                this.applyPlayerEffects();
                this.playSound(SoundEvents.BLOCK_BEACON_AMBIENT);
            }
        }
        if (this.field_19179 >= _snowman3) {
            this.field_19179 = -1;
            boolean bl = n > 0;
            this.beamSegments = this.field_19178;
            if (!this.world.isClient) {
                boolean bl2 = _snowman = this.level > 0;
                if (!bl && _snowman) {
                    this.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE);
                    for (ServerPlayerEntity serverPlayerEntity : this.world.getNonSpectatingEntities(ServerPlayerEntity.class, new Box(n2, _snowman, _snowman, n2, _snowman - 4, _snowman).expand(10.0, 5.0, 10.0))) {
                        Criteria.CONSTRUCT_BEACON.trigger(serverPlayerEntity, this);
                    }
                } else if (bl && !_snowman) {
                    this.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE);
                }
            }
        }
    }

    private void updateLevel(int x, int y, int z) {
        this.level = 0;
        int n = 1;
        while (n <= 4 && (_snowman = y - n) >= 0) {
            boolean bl = true;
            block1: for (int i = x - n; i <= x + n && bl; ++i) {
                for (_snowman = z - n; _snowman <= z + n; ++_snowman) {
                    if (this.world.getBlockState(new BlockPos(i, _snowman, _snowman)).isIn(BlockTags.BEACON_BASE_BLOCKS)) continue;
                    bl = false;
                    continue block1;
                }
            }
            if (!bl) break;
            this.level = n++;
        }
    }

    @Override
    public void markRemoved() {
        this.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE);
        super.markRemoved();
    }

    private void applyPlayerEffects() {
        if (this.world.isClient || this.primary == null) {
            return;
        }
        double d = this.level * 10 + 10;
        int _snowman2 = 0;
        if (this.level >= 4 && this.primary == this.secondary) {
            _snowman2 = 1;
        }
        int _snowman3 = (9 + this.level * 2) * 20;
        Box _snowman4 = new Box(this.pos).expand(d).stretch(0.0, this.world.getHeight(), 0.0);
        List<PlayerEntity> _snowman5 = this.world.getNonSpectatingEntities(PlayerEntity.class, _snowman4);
        for (PlayerEntity playerEntity : _snowman5) {
            playerEntity.addStatusEffect(new StatusEffectInstance(this.primary, _snowman3, _snowman2, true, true));
        }
        if (this.level >= 4 && this.primary != this.secondary && this.secondary != null) {
            for (PlayerEntity playerEntity : _snowman5) {
                playerEntity.addStatusEffect(new StatusEffectInstance(this.secondary, _snowman3, 0, true, true));
            }
        }
    }

    public void playSound(SoundEvent soundEvent) {
        this.world.playSound(null, this.pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public List<BeamSegment> getBeamSegments() {
        return this.level == 0 ? ImmutableList.of() : this.beamSegments;
    }

    public int getLevel() {
        return this.level;
    }

    @Override
    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 3, this.toInitialChunkDataTag());
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        return this.toTag(new CompoundTag());
    }

    @Override
    public double getSquaredRenderDistance() {
        return 256.0;
    }

    @Nullable
    private static StatusEffect getPotionEffectById(int id) {
        StatusEffect statusEffect = StatusEffect.byRawId(id);
        return EFFECTS.contains(statusEffect) ? statusEffect : null;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.primary = BeaconBlockEntity.getPotionEffectById(tag.getInt("Primary"));
        this.secondary = BeaconBlockEntity.getPotionEffectById(tag.getInt("Secondary"));
        if (tag.contains("CustomName", 8)) {
            this.customName = Text.Serializer.fromJson(tag.getString("CustomName"));
        }
        this.lock = ContainerLock.fromTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putInt("Primary", StatusEffect.getRawId(this.primary));
        tag.putInt("Secondary", StatusEffect.getRawId(this.secondary));
        tag.putInt("Levels", this.level);
        if (this.customName != null) {
            tag.putString("CustomName", Text.Serializer.toJson(this.customName));
        }
        this.lock.toTag(tag);
        return tag;
    }

    public void setCustomName(@Nullable Text text) {
        this.customName = text;
    }

    @Override
    @Nullable
    public ScreenHandler createMenu(int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if (LockableContainerBlockEntity.checkUnlocked(playerEntity, this.lock, this.getDisplayName())) {
            return new BeaconScreenHandler(n, playerInventory, this.propertyDelegate, ScreenHandlerContext.create(this.world, this.getPos()));
        }
        return null;
    }

    @Override
    public Text getDisplayName() {
        return this.customName != null ? this.customName : new TranslatableText("container.beacon");
    }

    static /* synthetic */ int method_17490(BeaconBlockEntity beaconBlockEntity) {
        return beaconBlockEntity.level;
    }

    static /* synthetic */ StatusEffect method_17494(BeaconBlockEntity beaconBlockEntity) {
        return beaconBlockEntity.primary;
    }

    static /* synthetic */ StatusEffect method_17496(BeaconBlockEntity beaconBlockEntity) {
        return beaconBlockEntity.secondary;
    }

    static /* synthetic */ int method_17491(BeaconBlockEntity beaconBlockEntity, int n) {
        beaconBlockEntity.level = n;
        return beaconBlockEntity.level;
    }

    static /* synthetic */ List method_20294(BeaconBlockEntity beaconBlockEntity) {
        return beaconBlockEntity.beamSegments;
    }

    static /* synthetic */ StatusEffect method_17492(BeaconBlockEntity beaconBlockEntity, StatusEffect statusEffect) {
        beaconBlockEntity.primary = statusEffect;
        return beaconBlockEntity.primary;
    }

    static /* synthetic */ StatusEffect method_17493(int n) {
        return BeaconBlockEntity.getPotionEffectById(n);
    }

    static /* synthetic */ StatusEffect method_17495(BeaconBlockEntity beaconBlockEntity, StatusEffect statusEffect) {
        beaconBlockEntity.secondary = statusEffect;
        return beaconBlockEntity.secondary;
    }

    public static class BeamSegment {
        private final float[] color;
        private int height;

        public BeamSegment(float[] color) {
            this.color = color;
            this.height = 1;
        }

        protected void increaseHeight() {
            ++this.height;
        }

        public float[] getColor() {
            return this.color;
        }

        public int getHeight() {
            return this.height;
        }
    }
}

