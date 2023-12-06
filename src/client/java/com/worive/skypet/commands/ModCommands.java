package com.worive.skypet.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;

public class ModCommands {
    public static void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    ClientCommandManager.literal("sitall")
                            .executes(context -> {
                                sitNearbyPets(context.getSource());
                                return 1;
                            })
            );
        });
    }

    private static void sitNearbyPets(FabricClientCommandSource source) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world == null || mc.player == null) {
            source.sendError(Text.literal("Error: World or player is null"));
            return;
        }

        double interactionRadius = 5.0; // Set this to your desired interaction radius
        Box interactionBox = new Box(mc.player.getPos(), mc.player.getPos()).expand(interactionRadius);

        mc.world.getEntitiesByClass(TameableEntity.class, interactionBox, tameable -> tameable.isOwner(mc.player)).forEach(tameable -> {
            if (tameable.isOwner(mc.player)) {
                if (tameable.isSitting()) {
                    return;
                }

                mc.interactionManager.interactEntity(mc.player, tameable, Hand.MAIN_HAND);
            }
        });

        source.sendFeedback(Text.literal("Toggled sitting for nearby pets"));
    }
}