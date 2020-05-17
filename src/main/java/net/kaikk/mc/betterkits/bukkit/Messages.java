package net.kaikk.mc.betterkits.bukkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.kaikk.mc.betterkits.common.CommonUtils;

public class Messages {
	private static Map<String, String> messages = new HashMap<String, String>();
	
	public static void load(JavaPlugin instance) {
		try {
			CommonUtils.extractResource("/betterkits/messages.yml", new File(instance.getDataFolder(), "messages.yml"), false);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		@SuppressWarnings("deprecation")
		FileConfiguration defaultMessages = YamlConfiguration.loadConfiguration(new InputStreamReader(CommonUtils.getResourceAsStream("/betterkits/messages.yml")));
		FileConfiguration sMessages = YamlConfiguration.loadConfiguration(new File(instance.getDataFolder(), "messages.yml"));
		
		messages.clear();
		for (String key : defaultMessages.getKeys(false)) {
			messages.put(key, ChatColor.translateAlternateColorCodes('&', defaultMessages.getString(key)));
		}
		for (String key : sMessages.getKeys(false)) {
			messages.put(key, ChatColor.translateAlternateColorCodes('&', sMessages.getString(key)));
		}
	}
	public static String get(String id) {
		String m = messages.get(id);
		if (m == null) {
			return ChatColor.RED + "Couldn't find the message id \""+id+"\"!";
		}
		return m;
	}
	
	public static String get(String id, String... replacements) {
		String m = messages.get(id);
		if (m == null) {
			return ChatColor.RED + "Couldn't find the message id \""+id+"\"!";
		}
		for (int i = 0; i<replacements.length; i++) {
			m = m.replace("%"+replacements[i], replacements[++i]);
		}
		
		return m;
	}
}
