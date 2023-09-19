package com.dukebarrelswap;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("duke-barrel-swap")
public interface DukeBarrelSwapConfig extends Config
{
	@ConfigItem(
			keyName = "enableSwap",
			name = "Enable Swap",
			description = "Whether or not to enable the barrel menu swap"
	)
	default boolean enableSwap()
	{
		return true;
	}
}
