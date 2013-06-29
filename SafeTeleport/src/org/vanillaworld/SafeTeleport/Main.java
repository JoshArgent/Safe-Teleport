package org.vanillaworld.SafeTeleport;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	private void PlayerTeleport(PlayerTeleportEvent event)
	{
		Player p = event.getPlayer();
		if(normalTeleport(p))
		{
			// This teleport has been processed and allowed to happen.
			// Set metadata to false for the next teleport the player does.
			setNormalTeleport(p, false);
		}
		else
		{
			// Teleport has not been made safe
			// Perform safe guards..
			event.setCancelled(true);
			SafeTeleport.safeTeleport(p, event.getTo());
			setNormalTeleport(p, true);
		}
	}
	
	private boolean normalTeleport(Player p)
	{
		if(p.hasMetadata("safeteleport"))
		{
			MetadataValue value = p.getMetadata("safeteleport").get(0);
			return value.asBoolean();
		}
		return false;
	}
	
	private void setNormalTeleport(Player p, boolean value)
	{
		p.setMetadata("safeteleport", new FixedMetadataValue(this, value));
	}

}
