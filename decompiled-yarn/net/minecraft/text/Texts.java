package net.minecraft.text;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;

public class Texts {
   public static MutableText setStyleIfAbsent(MutableText text, Style style) {
      if (style.isEmpty()) {
         return text;
      } else {
         Style _snowman = text.getStyle();
         if (_snowman.isEmpty()) {
            return text.setStyle(style);
         } else {
            return _snowman.equals(style) ? text : text.setStyle(_snowman.withParent(style));
         }
      }
   }

   public static MutableText parse(@Nullable ServerCommandSource source, Text text, @Nullable Entity sender, int depth) throws CommandSyntaxException {
      if (depth > 100) {
         return text.shallowCopy();
      } else {
         MutableText _snowman = text instanceof ParsableText ? ((ParsableText)text).parse(source, sender, depth + 1) : text.copy();

         for (Text _snowmanx : text.getSiblings()) {
            _snowman.append(parse(source, _snowmanx, sender, depth + 1));
         }

         return _snowman.fillStyle(method_27663(source, text.getStyle(), sender, depth));
      }
   }

   private static Style method_27663(@Nullable ServerCommandSource _snowman, Style _snowman, @Nullable Entity _snowman, int _snowman) throws CommandSyntaxException {
      HoverEvent _snowmanxxxx = _snowman.getHoverEvent();
      if (_snowmanxxxx != null) {
         Text _snowmanxxxxx = _snowmanxxxx.getValue(HoverEvent.Action.SHOW_TEXT);
         if (_snowmanxxxxx != null) {
            HoverEvent _snowmanxxxxxx = new HoverEvent(HoverEvent.Action.SHOW_TEXT, parse(_snowman, _snowmanxxxxx, _snowman, _snowman + 1));
            return _snowman.withHoverEvent(_snowmanxxxxxx);
         }
      }

      return _snowman;
   }

   public static Text toText(GameProfile profile) {
      if (profile.getName() != null) {
         return new LiteralText(profile.getName());
      } else {
         return profile.getId() != null ? new LiteralText(profile.getId().toString()) : new LiteralText("(unknown)");
      }
   }

   public static Text joinOrdered(Collection<String> strings) {
      return joinOrdered(strings, string -> new LiteralText(string).formatted(Formatting.GREEN));
   }

   public static <T extends Comparable<T>> Text joinOrdered(Collection<T> elements, Function<T, Text> transformer) {
      if (elements.isEmpty()) {
         return LiteralText.EMPTY;
      } else if (elements.size() == 1) {
         return transformer.apply(elements.iterator().next());
      } else {
         List<T> _snowman = Lists.newArrayList(elements);
         _snowman.sort(Comparable::compareTo);
         return join(_snowman, transformer);
      }
   }

   public static <T> MutableText join(Collection<T> elements, Function<T, Text> transformer) {
      if (elements.isEmpty()) {
         return new LiteralText("");
      } else if (elements.size() == 1) {
         return transformer.apply(elements.iterator().next()).shallowCopy();
      } else {
         MutableText _snowman = new LiteralText("");
         boolean _snowmanx = true;

         for (T _snowmanxx : elements) {
            if (!_snowmanx) {
               _snowman.append(new LiteralText(", ").formatted(Formatting.GRAY));
            }

            _snowman.append(transformer.apply(_snowmanxx));
            _snowmanx = false;
         }

         return _snowman;
      }
   }

   public static MutableText bracketed(Text text) {
      return new TranslatableText("chat.square_brackets", text);
   }

   public static Text toText(Message message) {
      return (Text)(message instanceof Text ? (Text)message : new LiteralText(message.getString()));
   }
}
