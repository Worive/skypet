package com.worive.skypet;

import com.worive.skypet.commands.ModCommands;
import net.fabricmc.api.ClientModInitializer;

public class SkyPetClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModCommands.registerCommands();
	}
}