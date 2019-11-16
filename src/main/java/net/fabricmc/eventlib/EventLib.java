package net.fabricmc.eventlib;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventLib implements ModInitializer {
	public static Logger LOGGER= LogManager.getLogger();
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		//ClientTickCallback.EVENT.register((client)->{client.keyboard.setClipboard(null);});
		LOGGER.info("EventLib is loaded!");
	}
}
