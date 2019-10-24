package com.biteforcemc;


import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ingotConverter extends JavaPlugin implements CommandExecutor {


    public void onEnable() {
        getLogger().info("Ingot Converter enabled!");
    }

    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("block") && player.hasPermission("biteforcemc.ingotconverter.convert")) {
            block(player, Material.IRON_INGOT, Material.IRON_BLOCK);
            block(player, Material.GOLD_NUGGET, Material.GOLD_INGOT);
            block(player, Material.GOLD_INGOT, Material.GOLD_BLOCK);
            block(player, Material.DIAMOND, Material.DIAMOND_BLOCK);
            block(player, Material.EMERALD, Material.EMERALD_BLOCK);
            block(player, Material.REDSTONE, Material.REDSTONE_BLOCK);
            block(player, Material.COAL, Material.COAL_BLOCK);
            blockDye(player);
            player.sendMessage(ChatColor.GREEN + "You have converted all your ingots to blocks");
        }
        return false;
    }

    private void block(Player p, Material m, Material block) {
        int amt = 0;
        for(ItemStack i : p.getInventory().getContents()) {
            if(i != null && i.getType() == m) {
                amt += i.getAmount();
            }
        }
        if(amt > 0) {
            p.getInventory().remove(m);
            if(amt % 9 != 0) {
                p.getInventory().addItem(new ItemStack(m, amt % 9));
            }
            if(amt / 9 != 0) {
                p.getInventory().addItem(new ItemStack(block, amt/9));
            }
        }
    }

    void blockDye(Player p) {
        int amt = 0;
        for(ItemStack i : p.getInventory())
            if(i != null && i.getType().equals(Material.INK_SACK) && i.getDurability() == 4)
                amt += i.getAmount();
        if(amt > 0) {
            p.getInventory().removeItem(new ItemStack(Material.INK_SACK, amt, (short) 4));
            if(amt % 9 != 0)
                p.getInventory().addItem(new ItemStack(Material.INK_SACK, amt % 9, (short) 4));
            if(amt / 9 != 0)
                p.getInventory().addItem(new ItemStack(Material.LAPIS_BLOCK, amt / 9));
        }
    }
}
