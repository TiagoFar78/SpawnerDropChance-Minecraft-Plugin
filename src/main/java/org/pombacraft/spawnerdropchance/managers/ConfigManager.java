package org.pombacraft.spawnerdropchance.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.pombacraft.spawnerdropchance.SpawnerDropChance;

public class ConfigManager {
	
	private static ConfigManager instance = new ConfigManager();
	
	public static ConfigManager getInstance() {
		return instance;
	}
	
	private double _dropChance;
	
	private ConfigManager() {
		YamlConfiguration config = SpawnerDropChance.getYamlConfiguration();
		
		_dropChance = config.getDouble("DropChance");
	}

	public double getDropChance() {
		return _dropChance;
	}
}
