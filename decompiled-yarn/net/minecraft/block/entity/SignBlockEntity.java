package net.minecraft.block.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class SignBlockEntity extends BlockEntity {
   private final Text[] text = new Text[]{LiteralText.EMPTY, LiteralText.EMPTY, LiteralText.EMPTY, LiteralText.EMPTY};
   private boolean editable = true;
   private PlayerEntity editor;
   private final OrderedText[] textBeingEdited = new OrderedText[4];
   private DyeColor textColor = DyeColor.BLACK;

   public SignBlockEntity() {
      super(BlockEntityType.SIGN);
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         String _snowmanx = Text.Serializer.toJson(this.text[_snowman]);
         tag.putString("Text" + (_snowman + 1), _snowmanx);
      }

      tag.putString("Color", this.textColor.getName());
      return tag;
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      this.editable = false;
      super.fromTag(state, tag);
      this.textColor = DyeColor.byName(tag.getString("Color"), DyeColor.BLACK);

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         String _snowmanx = tag.getString("Text" + (_snowman + 1));
         Text _snowmanxx = Text.Serializer.fromJson(_snowmanx.isEmpty() ? "\"\"" : _snowmanx);
         if (this.world instanceof ServerWorld) {
            try {
               this.text[_snowman] = Texts.parse(this.getCommandSource(null), _snowmanxx, null, 0);
            } catch (CommandSyntaxException var7) {
               this.text[_snowman] = _snowmanxx;
            }
         } else {
            this.text[_snowman] = _snowmanxx;
         }

         this.textBeingEdited[_snowman] = null;
      }
   }

   public Text getTextOnRow(int row) {
      return this.text[row];
   }

   public void setTextOnRow(int row, Text text) {
      this.text[row] = text;
      this.textBeingEdited[row] = null;
   }

   @Nullable
   public OrderedText getTextBeingEditedOnRow(int row, Function<Text, OrderedText> _snowman) {
      if (this.textBeingEdited[row] == null && this.text[row] != null) {
         this.textBeingEdited[row] = _snowman.apply(this.text[row]);
      }

      return this.textBeingEdited[row];
   }

   @Nullable
   @Override
   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      return new BlockEntityUpdateS2CPacket(this.pos, 9, this.toInitialChunkDataTag());
   }

   @Override
   public CompoundTag toInitialChunkDataTag() {
      return this.toTag(new CompoundTag());
   }

   @Override
   public boolean copyItemDataRequiresOperator() {
      return true;
   }

   public boolean isEditable() {
      return this.editable;
   }

   public void setEditable(boolean _snowman) {
      this.editable = _snowman;
      if (!_snowman) {
         this.editor = null;
      }
   }

   public void setEditor(PlayerEntity player) {
      this.editor = player;
   }

   public PlayerEntity getEditor() {
      return this.editor;
   }

   public boolean onActivate(PlayerEntity player) {
      for (Text _snowman : this.text) {
         Style _snowmanx = _snowman == null ? null : _snowman.getStyle();
         if (_snowmanx != null && _snowmanx.getClickEvent() != null) {
            ClickEvent _snowmanxx = _snowmanx.getClickEvent();
            if (_snowmanxx.getAction() == ClickEvent.Action.RUN_COMMAND) {
               player.getServer().getCommandManager().execute(this.getCommandSource((ServerPlayerEntity)player), _snowmanxx.getValue());
            }
         }
      }

      return true;
   }

   public ServerCommandSource getCommandSource(@Nullable ServerPlayerEntity player) {
      String _snowman = player == null ? "Sign" : player.getName().getString();
      Text _snowmanx = (Text)(player == null ? new LiteralText("Sign") : player.getDisplayName());
      return new ServerCommandSource(
         CommandOutput.DUMMY, Vec3d.ofCenter(this.pos), Vec2f.ZERO, (ServerWorld)this.world, 2, _snowman, _snowmanx, this.world.getServer(), player
      );
   }

   public DyeColor getTextColor() {
      return this.textColor;
   }

   public boolean setTextColor(DyeColor value) {
      if (value != this.getTextColor()) {
         this.textColor = value;
         this.markDirty();
         this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
         return true;
      } else {
         return false;
      }
   }
}
