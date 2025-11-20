package net.minecraft.command.argument;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class MessageArgumentType implements ArgumentType<MessageArgumentType.MessageFormat> {
   private static final Collection<String> EXAMPLES = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");

   public MessageArgumentType() {
   }

   public static MessageArgumentType message() {
      return new MessageArgumentType();
   }

   public static Text getMessage(CommandContext<ServerCommandSource> command, String name) throws CommandSyntaxException {
      return ((MessageArgumentType.MessageFormat)command.getArgument(name, MessageArgumentType.MessageFormat.class))
         .format((ServerCommandSource)command.getSource(), ((ServerCommandSource)command.getSource()).hasPermissionLevel(2));
   }

   public MessageArgumentType.MessageFormat parse(StringReader _snowman) throws CommandSyntaxException {
      return MessageArgumentType.MessageFormat.parse(_snowman, true);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public static class MessageFormat {
      private final String contents;
      private final MessageArgumentType.MessageSelector[] selectors;

      public MessageFormat(String _snowman, MessageArgumentType.MessageSelector[] _snowman) {
         this.contents = _snowman;
         this.selectors = _snowman;
      }

      public Text format(ServerCommandSource _snowman, boolean _snowman) throws CommandSyntaxException {
         if (this.selectors.length != 0 && _snowman) {
            MutableText _snowmanxx = new LiteralText(this.contents.substring(0, this.selectors[0].getStart()));
            int _snowmanxxx = this.selectors[0].getStart();

            for (MessageArgumentType.MessageSelector _snowmanxxxx : this.selectors) {
               Text _snowmanxxxxx = _snowmanxxxx.format(_snowman);
               if (_snowmanxxx < _snowmanxxxx.getStart()) {
                  _snowmanxx.append(this.contents.substring(_snowmanxxx, _snowmanxxxx.getStart()));
               }

               if (_snowmanxxxxx != null) {
                  _snowmanxx.append(_snowmanxxxxx);
               }

               _snowmanxxx = _snowmanxxxx.getEnd();
            }

            if (_snowmanxxx < this.contents.length()) {
               _snowmanxx.append(this.contents.substring(_snowmanxxx, this.contents.length()));
            }

            return _snowmanxx;
         } else {
            return new LiteralText(this.contents);
         }
      }

      public static MessageArgumentType.MessageFormat parse(StringReader _snowman, boolean _snowman) throws CommandSyntaxException {
         String _snowmanxx = _snowman.getString().substring(_snowman.getCursor(), _snowman.getTotalLength());
         if (!_snowman) {
            _snowman.setCursor(_snowman.getTotalLength());
            return new MessageArgumentType.MessageFormat(_snowmanxx, new MessageArgumentType.MessageSelector[0]);
         } else {
            List<MessageArgumentType.MessageSelector> _snowmanxxx = Lists.newArrayList();
            int _snowmanxxxx = _snowman.getCursor();

            while (true) {
               int _snowmanxxxxx;
               EntitySelector _snowmanxxxxxx;
               while (true) {
                  if (!_snowman.canRead()) {
                     return new MessageArgumentType.MessageFormat(_snowmanxx, _snowmanxxx.toArray(new MessageArgumentType.MessageSelector[_snowmanxxx.size()]));
                  }

                  if (_snowman.peek() == '@') {
                     _snowmanxxxxx = _snowman.getCursor();

                     try {
                        EntitySelectorReader _snowmanxxxxxxx = new EntitySelectorReader(_snowman);
                        _snowmanxxxxxx = _snowmanxxxxxxx.read();
                        break;
                     } catch (CommandSyntaxException var8) {
                        if (var8.getType() != EntitySelectorReader.MISSING_EXCEPTION && var8.getType() != EntitySelectorReader.UNKNOWN_SELECTOR_EXCEPTION) {
                           throw var8;
                        }

                        _snowman.setCursor(_snowmanxxxxx + 1);
                     }
                  } else {
                     _snowman.skip();
                  }
               }

               _snowmanxxx.add(new MessageArgumentType.MessageSelector(_snowmanxxxxx - _snowmanxxxx, _snowman.getCursor() - _snowmanxxxx, _snowmanxxxxxx));
            }
         }
      }
   }

   public static class MessageSelector {
      private final int start;
      private final int end;
      private final EntitySelector selector;

      public MessageSelector(int _snowman, int _snowman, EntitySelector _snowman) {
         this.start = _snowman;
         this.end = _snowman;
         this.selector = _snowman;
      }

      public int getStart() {
         return this.start;
      }

      public int getEnd() {
         return this.end;
      }

      @Nullable
      public Text format(ServerCommandSource _snowman) throws CommandSyntaxException {
         return EntitySelector.getNames(this.selector.getEntities(_snowman));
      }
   }
}
