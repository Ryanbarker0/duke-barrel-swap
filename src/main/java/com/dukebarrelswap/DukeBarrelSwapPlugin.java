package com.dukebarrelswap;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.apache.commons.lang3.ArrayUtils;

@Slf4j
@PluginDescriptor(
		name = "Duke Barrel Swap",
		description = "Swaps Fill & Check for Duke Sucellus Fermentation Barrels when uncrushed mushrooms are in inventory"
)
public class DukeBarrelSwapPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private DukeBarrelSwapConfig config;

	public static final int DUKE_SUCELLUS_REGION_ID = 12132;
	public static final int UNCRUSHED_MUSHROOM_ID = 28341;
	public static final int CRUSHED_MUSHROOM_ID = 28342;


	@Override
	protected void startUp() throws Exception
	{
	}

	@Override
	protected void shutDown() throws Exception
	{
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
		}
	}

	@Subscribe
	public void onMenuEntryAdded(final MenuEntryAdded event)
	{
		if (isInDukeRoom()) {
			final ItemContainer inv = client.getItemContainer(InventoryID.INVENTORY);

			if (inv == null) {
				return;
			}

			final boolean hasUncrushedMushroom = containsAny(inv);

			if (!hasUncrushedMushroom) {
				return;
			}

			if (config.enableSwap()) {
				final boolean barrelSwap = isFillBarrel(event.getMenuEntry());

				if (barrelSwap) {
					event.getMenuEntry().setDeprioritized(true);
				}
			}
		}
	}

	@Provides
	DukeBarrelSwapConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DukeBarrelSwapConfig.class);
	}

	public boolean isInDukeRoom()
	{
		return ArrayUtils.contains(client.getMapRegions(), DUKE_SUCELLUS_REGION_ID);
	}

	private static boolean containsAny(final ItemContainer itemContainer)
	{
		for (final Item item : itemContainer.getItems())
		{
			if (UNCRUSHED_MUSHROOM_ID == item.getId())
			{
				return true;
			}
		}

		return false;
	}

	private static boolean isFillBarrel(final MenuEntry menuEntry)
	{
		return menuEntry.getOption().equals("Fill") && menuEntry.getTarget().contains("Fermentation Vat");
	}
}
