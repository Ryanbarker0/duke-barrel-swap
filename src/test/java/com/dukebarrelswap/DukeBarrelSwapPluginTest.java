package com.dukebarrelswap;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DukeBarrelSwapPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(DukeBarrelSwapPlugin.class);
		RuneLite.main(args);
	}
}