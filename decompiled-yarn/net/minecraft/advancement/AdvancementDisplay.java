package net.minecraft.advancement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class AdvancementDisplay {
   private final Text title;
   private final Text description;
   private final ItemStack icon;
   private final Identifier background;
   private final AdvancementFrame frame;
   private final boolean showToast;
   private final boolean announceToChat;
   private final boolean hidden;
   private float xPos;
   private float yPos;

   public AdvancementDisplay(
      ItemStack icon,
      Text title,
      Text description,
      @Nullable Identifier background,
      AdvancementFrame frame,
      boolean showToast,
      boolean announceToChat,
      boolean hidden
   ) {
      this.title = title;
      this.description = description;
      this.icon = icon;
      this.background = background;
      this.frame = frame;
      this.showToast = showToast;
      this.announceToChat = announceToChat;
      this.hidden = hidden;
   }

   public void setPosition(float xPos, float yPos) {
      this.xPos = xPos;
      this.yPos = yPos;
   }

   public Text getTitle() {
      return this.title;
   }

   public Text getDescription() {
      return this.description;
   }

   public ItemStack getIcon() {
      return this.icon;
   }

   @Nullable
   public Identifier getBackground() {
      return this.background;
   }

   public AdvancementFrame getFrame() {
      return this.frame;
   }

   public float getX() {
      return this.xPos;
   }

   public float getY() {
      return this.yPos;
   }

   public boolean shouldShowToast() {
      return this.showToast;
   }

   public boolean shouldAnnounceToChat() {
      return this.announceToChat;
   }

   public boolean isHidden() {
      return this.hidden;
   }

   public static AdvancementDisplay fromJson(JsonObject obj) {
      Text _snowman = Text.Serializer.fromJson(obj.get("title"));
      Text _snowmanx = Text.Serializer.fromJson(obj.get("description"));
      if (_snowman != null && _snowmanx != null) {
         ItemStack _snowmanxx = iconFromJson(JsonHelper.getObject(obj, "icon"));
         Identifier _snowmanxxx = obj.has("background") ? new Identifier(JsonHelper.getString(obj, "background")) : null;
         AdvancementFrame _snowmanxxxx = obj.has("frame") ? AdvancementFrame.forName(JsonHelper.getString(obj, "frame")) : AdvancementFrame.TASK;
         boolean _snowmanxxxxx = JsonHelper.getBoolean(obj, "show_toast", true);
         boolean _snowmanxxxxxx = JsonHelper.getBoolean(obj, "announce_to_chat", true);
         boolean _snowmanxxxxxxx = JsonHelper.getBoolean(obj, "hidden", false);
         return new AdvancementDisplay(_snowmanxx, _snowman, _snowmanx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
      } else {
         throw new JsonSyntaxException("Both title and description must be set");
      }
   }

   private static ItemStack iconFromJson(JsonObject json) {
      if (!json.has("item")) {
         throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
      } else {
         Item _snowman = JsonHelper.getItem(json, "item");
         if (json.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            ItemStack _snowmanx = new ItemStack(_snowman);
            if (json.has("nbt")) {
               try {
                  CompoundTag _snowmanxx = StringNbtReader.parse(JsonHelper.asString(json.get("nbt"), "nbt"));
                  _snowmanx.setTag(_snowmanxx);
               } catch (CommandSyntaxException var4) {
                  throw new JsonSyntaxException("Invalid nbt tag: " + var4.getMessage());
               }
            }

            return _snowmanx;
         }
      }
   }

   public void toPacket(PacketByteBuf buf) {
      buf.writeText(this.title);
      buf.writeText(this.description);
      buf.writeItemStack(this.icon);
      buf.writeEnumConstant(this.frame);
      int _snowman = 0;
      if (this.background != null) {
         _snowman |= 1;
      }

      if (this.showToast) {
         _snowman |= 2;
      }

      if (this.hidden) {
         _snowman |= 4;
      }

      buf.writeInt(_snowman);
      if (this.background != null) {
         buf.writeIdentifier(this.background);
      }

      buf.writeFloat(this.xPos);
      buf.writeFloat(this.yPos);
   }

   public static AdvancementDisplay fromPacket(PacketByteBuf buf) {
      Text _snowman = buf.readText();
      Text _snowmanx = buf.readText();
      ItemStack _snowmanxx = buf.readItemStack();
      AdvancementFrame _snowmanxxx = buf.readEnumConstant(AdvancementFrame.class);
      int _snowmanxxxx = buf.readInt();
      Identifier _snowmanxxxxx = (_snowmanxxxx & 1) != 0 ? buf.readIdentifier() : null;
      boolean _snowmanxxxxxx = (_snowmanxxxx & 2) != 0;
      boolean _snowmanxxxxxxx = (_snowmanxxxx & 4) != 0;
      AdvancementDisplay _snowmanxxxxxxxx = new AdvancementDisplay(_snowmanxx, _snowman, _snowmanx, _snowmanxxxxx, _snowmanxxx, _snowmanxxxxxx, false, _snowmanxxxxxxx);
      _snowmanxxxxxxxx.setPosition(buf.readFloat(), buf.readFloat());
      return _snowmanxxxxxxxx;
   }

   public JsonElement toJson() {
      JsonObject _snowman = new JsonObject();
      _snowman.add("icon", this.iconToJson());
      _snowman.add("title", Text.Serializer.toJsonTree(this.title));
      _snowman.add("description", Text.Serializer.toJsonTree(this.description));
      _snowman.addProperty("frame", this.frame.getId());
      _snowman.addProperty("show_toast", this.showToast);
      _snowman.addProperty("announce_to_chat", this.announceToChat);
      _snowman.addProperty("hidden", this.hidden);
      if (this.background != null) {
         _snowman.addProperty("background", this.background.toString());
      }

      return _snowman;
   }

   private JsonObject iconToJson() {
      JsonObject _snowman = new JsonObject();
      _snowman.addProperty("item", Registry.ITEM.getId(this.icon.getItem()).toString());
      if (this.icon.hasTag()) {
         _snowman.addProperty("nbt", this.icon.getTag().toString());
      }

      return _snowman;
   }
}
