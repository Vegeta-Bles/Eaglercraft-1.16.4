/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class MusicDiscItem
extends Item {
    private static final Map<SoundEvent, MusicDiscItem> MUSIC_DISCS = Maps.newHashMap();
    private final int comparatorOutput;
    private final SoundEvent sound;

    protected MusicDiscItem(int comparatorOutput, SoundEvent sound, Item.Settings settings) {
        super(settings);
        this.comparatorOutput = comparatorOutput;
        this.sound = sound;
        MUSIC_DISCS.put(this.sound, this);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockState _snowman2 = world.getBlockState(_snowman = context.getBlockPos());
        if (!_snowman2.isOf(Blocks.JUKEBOX) || _snowman2.get(JukeboxBlock.HAS_RECORD).booleanValue()) {
            return ActionResult.PASS;
        }
        ItemStack _snowman3 = context.getStack();
        if (!world.isClient) {
            ((JukeboxBlock)Blocks.JUKEBOX).setRecord(world, _snowman, _snowman2, _snowman3);
            world.syncWorldEvent(null, 1010, _snowman, Item.getRawId(this));
            _snowman3.decrement(1);
            PlayerEntity playerEntity = context.getPlayer();
            if (playerEntity != null) {
                playerEntity.incrementStat(Stats.PLAY_RECORD);
            }
        }
        return ActionResult.success(world.isClient);
    }

    public int getComparatorOutput() {
        return this.comparatorOutput;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(this.getDescription().formatted(Formatting.GRAY));
    }

    public MutableText getDescription() {
        return new TranslatableText(this.getTranslationKey() + ".desc");
    }

    @Nullable
    public static MusicDiscItem bySound(SoundEvent sound) {
        return MUSIC_DISCS.get(sound);
    }

    public SoundEvent getSound() {
        return this.sound;
    }
}

