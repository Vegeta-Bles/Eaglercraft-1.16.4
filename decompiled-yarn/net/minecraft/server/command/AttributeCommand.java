package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.UUID;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.UuidArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

public class AttributeCommand {
   private static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (_snowman, _snowmanx) -> CommandSource.suggestIdentifiers(
         Registry.ATTRIBUTE.getIds(), _snowmanx
      );
   private static final DynamicCommandExceptionType ENTITY_FAILED_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.attribute.failed.entity", _snowman)
   );
   private static final Dynamic2CommandExceptionType NO_ATTRIBUTE_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("commands.attribute.failed.no_attribute", _snowman, _snowmanx)
   );
   private static final Dynamic3CommandExceptionType NO_MODIFIER_EXCEPTION = new Dynamic3CommandExceptionType(
      (_snowman, _snowmanx, _snowmanxx) -> new TranslatableText("commands.attribute.failed.no_modifier", _snowmanx, _snowman, _snowmanxx)
   );
   private static final Dynamic3CommandExceptionType MODIFIER_ALREADY_PRESENT_EXCEPTION = new Dynamic3CommandExceptionType(
      (_snowman, _snowmanx, _snowmanxx) -> new TranslatableText("commands.attribute.failed.modifier_already_present", _snowmanxx, _snowmanx, _snowman)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("attribute").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("target", EntityArgumentType.entity())
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("attribute", IdentifierArgumentType.identifier())
                              .suggests(SUGGESTION_PROVIDER)
                              .then(
                                 ((LiteralArgumentBuilder)CommandManager.literal("get")
                                       .executes(
                                          _snowman -> executeValueGet(
                                                (ServerCommandSource)_snowman.getSource(),
                                                EntityArgumentType.getEntity(_snowman, "target"),
                                                IdentifierArgumentType.method_27575(_snowman, "attribute"),
                                                1.0
                                             )
                                       ))
                                    .then(
                                       CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                          .executes(
                                             _snowman -> executeValueGet(
                                                   (ServerCommandSource)_snowman.getSource(),
                                                   EntityArgumentType.getEntity(_snowman, "target"),
                                                   IdentifierArgumentType.method_27575(_snowman, "attribute"),
                                                   DoubleArgumentType.getDouble(_snowman, "scale")
                                                )
                                          )
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)CommandManager.literal("base")
                                    .then(
                                       CommandManager.literal("set")
                                          .then(
                                             CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                                .executes(
                                                   _snowman -> executeBaseValueSet(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         EntityArgumentType.getEntity(_snowman, "target"),
                                                         IdentifierArgumentType.method_27575(_snowman, "attribute"),
                                                         DoubleArgumentType.getDouble(_snowman, "value")
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)CommandManager.literal("get")
                                          .executes(
                                             _snowman -> executeBaseValueGet(
                                                   (ServerCommandSource)_snowman.getSource(),
                                                   EntityArgumentType.getEntity(_snowman, "target"),
                                                   IdentifierArgumentType.method_27575(_snowman, "attribute"),
                                                   1.0
                                                )
                                          ))
                                       .then(
                                          CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                             .executes(
                                                _snowman -> executeBaseValueGet(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      EntityArgumentType.getEntity(_snowman, "target"),
                                                      IdentifierArgumentType.method_27575(_snowman, "attribute"),
                                                      DoubleArgumentType.getDouble(_snowman, "scale")
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           ((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("modifier")
                                    .then(
                                       CommandManager.literal("add")
                                          .then(
                                             CommandManager.argument("uuid", UuidArgumentType.uuid())
                                                .then(
                                                   CommandManager.argument("name", StringArgumentType.string())
                                                      .then(
                                                         ((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                                                     "value", DoubleArgumentType.doubleArg()
                                                                  )
                                                                  .then(
                                                                     CommandManager.literal("add")
                                                                        .executes(
                                                                           _snowman -> executeModifierAdd(
                                                                                 (ServerCommandSource)_snowman.getSource(),
                                                                                 EntityArgumentType.getEntity(_snowman, "target"),
                                                                                 IdentifierArgumentType.method_27575(_snowman, "attribute"),
                                                                                 UuidArgumentType.getUuid(_snowman, "uuid"),
                                                                                 StringArgumentType.getString(_snowman, "name"),
                                                                                 DoubleArgumentType.getDouble(_snowman, "value"),
                                                                                 EntityAttributeModifier.Operation.ADDITION
                                                                              )
                                                                        )
                                                                  ))
                                                               .then(
                                                                  CommandManager.literal("multiply")
                                                                     .executes(
                                                                        _snowman -> executeModifierAdd(
                                                                              (ServerCommandSource)_snowman.getSource(),
                                                                              EntityArgumentType.getEntity(_snowman, "target"),
                                                                              IdentifierArgumentType.method_27575(_snowman, "attribute"),
                                                                              UuidArgumentType.getUuid(_snowman, "uuid"),
                                                                              StringArgumentType.getString(_snowman, "name"),
                                                                              DoubleArgumentType.getDouble(_snowman, "value"),
                                                                              EntityAttributeModifier.Operation.MULTIPLY_TOTAL
                                                                           )
                                                                     )
                                                               ))
                                                            .then(
                                                               CommandManager.literal("multiply_base")
                                                                  .executes(
                                                                     _snowman -> executeModifierAdd(
                                                                           (ServerCommandSource)_snowman.getSource(),
                                                                           EntityArgumentType.getEntity(_snowman, "target"),
                                                                           IdentifierArgumentType.method_27575(_snowman, "attribute"),
                                                                           UuidArgumentType.getUuid(_snowman, "uuid"),
                                                                           StringArgumentType.getString(_snowman, "name"),
                                                                           DoubleArgumentType.getDouble(_snowman, "value"),
                                                                           EntityAttributeModifier.Operation.MULTIPLY_BASE
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("remove")
                                       .then(
                                          CommandManager.argument("uuid", UuidArgumentType.uuid())
                                             .executes(
                                                _snowman -> executeModifierRemove(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      EntityArgumentType.getEntity(_snowman, "target"),
                                                      IdentifierArgumentType.method_27575(_snowman, "attribute"),
                                                      UuidArgumentType.getUuid(_snowman, "uuid")
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("value")
                                    .then(
                                       CommandManager.literal("get")
                                          .then(
                                             ((RequiredArgumentBuilder)CommandManager.argument("uuid", UuidArgumentType.uuid())
                                                   .executes(
                                                      _snowman -> executeModifierValueGet(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            EntityArgumentType.getEntity(_snowman, "target"),
                                                            IdentifierArgumentType.method_27575(_snowman, "attribute"),
                                                            UuidArgumentType.getUuid(_snowman, "uuid"),
                                                            1.0
                                                         )
                                                   ))
                                                .then(
                                                   CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                                      .executes(
                                                         _snowman -> executeModifierValueGet(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               EntityArgumentType.getEntity(_snowman, "target"),
                                                               IdentifierArgumentType.method_27575(_snowman, "attribute"),
                                                               UuidArgumentType.getUuid(_snowman, "uuid"),
                                                               DoubleArgumentType.getDouble(_snowman, "scale")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static EntityAttributeInstance getAttributeInstance(Entity entity, EntityAttribute attribute) throws CommandSyntaxException {
      EntityAttributeInstance _snowman = getLivingEntity(entity).getAttributes().getCustomInstance(attribute);
      if (_snowman == null) {
         throw NO_ATTRIBUTE_EXCEPTION.create(entity.getName(), new TranslatableText(attribute.getTranslationKey()));
      } else {
         return _snowman;
      }
   }

   private static LivingEntity getLivingEntity(Entity entity) throws CommandSyntaxException {
      if (!(entity instanceof LivingEntity)) {
         throw ENTITY_FAILED_EXCEPTION.create(entity.getName());
      } else {
         return (LivingEntity)entity;
      }
   }

   private static LivingEntity getLivingEntityWithAttribute(Entity entity, EntityAttribute attribute) throws CommandSyntaxException {
      LivingEntity _snowman = getLivingEntity(entity);
      if (!_snowman.getAttributes().hasAttribute(attribute)) {
         throw NO_ATTRIBUTE_EXCEPTION.create(entity.getName(), new TranslatableText(attribute.getTranslationKey()));
      } else {
         return _snowman;
      }
   }

   private static int executeValueGet(ServerCommandSource source, Entity target, EntityAttribute attribute, double multiplier) throws CommandSyntaxException {
      LivingEntity _snowman = getLivingEntityWithAttribute(target, attribute);
      double _snowmanx = _snowman.getAttributeValue(attribute);
      source.sendFeedback(
         new TranslatableText("commands.attribute.value.get.success", new TranslatableText(attribute.getTranslationKey()), target.getName(), _snowmanx), false
      );
      return (int)(_snowmanx * multiplier);
   }

   private static int executeBaseValueGet(ServerCommandSource source, Entity target, EntityAttribute attribute, double multiplier) throws CommandSyntaxException {
      LivingEntity _snowman = getLivingEntityWithAttribute(target, attribute);
      double _snowmanx = _snowman.getAttributeBaseValue(attribute);
      source.sendFeedback(
         new TranslatableText("commands.attribute.base_value.get.success", new TranslatableText(attribute.getTranslationKey()), target.getName(), _snowmanx), false
      );
      return (int)(_snowmanx * multiplier);
   }

   private static int executeModifierValueGet(ServerCommandSource source, Entity target, EntityAttribute attribute, UUID uuid, double multiplier) throws CommandSyntaxException {
      LivingEntity _snowman = getLivingEntityWithAttribute(target, attribute);
      AttributeContainer _snowmanx = _snowman.getAttributes();
      if (!_snowmanx.hasModifierForAttribute(attribute, uuid)) {
         throw NO_MODIFIER_EXCEPTION.create(target.getName(), new TranslatableText(attribute.getTranslationKey()), uuid);
      } else {
         double _snowmanxx = _snowmanx.getModifierValue(attribute, uuid);
         source.sendFeedback(
            new TranslatableText(
               "commands.attribute.modifier.value.get.success", uuid, new TranslatableText(attribute.getTranslationKey()), target.getName(), _snowmanxx
            ),
            false
         );
         return (int)(_snowmanxx * multiplier);
      }
   }

   private static int executeBaseValueSet(ServerCommandSource source, Entity target, EntityAttribute attribute, double value) throws CommandSyntaxException {
      getAttributeInstance(target, attribute).setBaseValue(value);
      source.sendFeedback(
         new TranslatableText("commands.attribute.base_value.set.success", new TranslatableText(attribute.getTranslationKey()), target.getName(), value), false
      );
      return 1;
   }

   private static int executeModifierAdd(
      ServerCommandSource source, Entity target, EntityAttribute attribute, UUID uuid, String name, double value, EntityAttributeModifier.Operation operation
   ) throws CommandSyntaxException {
      EntityAttributeInstance _snowman = getAttributeInstance(target, attribute);
      EntityAttributeModifier _snowmanx = new EntityAttributeModifier(uuid, name, value, operation);
      if (_snowman.hasModifier(_snowmanx)) {
         throw MODIFIER_ALREADY_PRESENT_EXCEPTION.create(target.getName(), new TranslatableText(attribute.getTranslationKey()), uuid);
      } else {
         _snowman.addPersistentModifier(_snowmanx);
         source.sendFeedback(
            new TranslatableText("commands.attribute.modifier.add.success", uuid, new TranslatableText(attribute.getTranslationKey()), target.getName()), false
         );
         return 1;
      }
   }

   private static int executeModifierRemove(ServerCommandSource source, Entity target, EntityAttribute attribute, UUID uuid) throws CommandSyntaxException {
      EntityAttributeInstance _snowman = getAttributeInstance(target, attribute);
      if (_snowman.tryRemoveModifier(uuid)) {
         source.sendFeedback(
            new TranslatableText("commands.attribute.modifier.remove.success", uuid, new TranslatableText(attribute.getTranslationKey()), target.getName()),
            false
         );
         return 1;
      } else {
         throw NO_MODIFIER_EXCEPTION.create(target.getName(), new TranslatableText(attribute.getTranslationKey()), uuid);
      }
   }
}
