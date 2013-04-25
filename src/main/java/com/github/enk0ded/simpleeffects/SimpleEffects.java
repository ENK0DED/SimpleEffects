package com.github.enk0ded.simpleeffects;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author ENK0DED
 */
public class SimpleEffects extends JavaPlugin {
    /**
     * Logger for debugging.
     */
    public static final Logger logger = Logger.getLogger("Minecraft.SimpleEffects");
    private static SimpleEffects instance;

    @Override
    public void onEnable() {
        super.onEnable();
        this.initializePlugin();
    }

    @Override
    public void onDisable() {
        this.finalizePlugin();
        super.onDisable();
    }

    public static  SimpleEffects getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("playeffect")) {
            if (sender instanceof BlockCommandSender || sender.hasPermission("simpleeffects.playeffect")) {
                Location loc = null;

                if (args.length == 1) {
                    if (sender instanceof BlockCommandSender) {
                        Block block = ((BlockCommandSender) sender).getBlock();
                        loc = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ());
                    } else if (sender instanceof Player) {
                        Player player = (Player) sender;
                        loc = player.getLocation();
                    }
                } else if (args.length == 4) {
                    World world = null;
                    Integer x = null, y = null, z = null;

                    if (sender instanceof BlockCommandSender) {
                        world = ((BlockCommandSender) sender).getBlock().getWorld();
                    } else if (sender instanceof Player) {
                        world = ((Player) sender).getWorld();
                    }

                    if (world != null) {
                        try {
                            x = Integer.parseInt(args[1]);
                            y = Integer.parseInt(args[2]);
                            z = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "Error: One or more of your position arguments could not be read as an integer!");
                            return false;
                        } finally {
                            loc = new Location(world, x, y, z);
                        }
                    }
                }

                if (loc != null) {
                    return Utils.parseEffects(sender, loc, args[0]);
                }
            }
        }

        return false;
    }

    public void initializePlugin() {
        instance = this;
    }

    public void finalizePlugin() {}
}