package com.github.enk0ded.simpleeffects;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

/**
 *
 * @author ENK0DED
 */
public class Utils {
    private static final Map<String, Effect> effectMap;

    static {
        effectMap = new HashMap<String, Effect>();

        for (Effect effect : EnumSet.allOf(Effect.class)) {
            effectMap.put(effect.name().toLowerCase(), effect);
        }
    }

    public static Effect getEffect(final String effectName) {
        return effectMap.get(effectName.toLowerCase());
    }

    public static Location correctLocation(Location loc) {
        loc.setX((double) Math.floor(loc.getX()) + 0.5);
        loc.setY((double) Math.floor(loc.getY()));
        loc.setZ((double) Math.floor(loc.getZ()) + 0.5);

        return loc;
    }

    public static boolean parseEffects(CommandSender sender, Location loc, String effects) {
        int effectCounter = 0;
        Effect effect;

        String[] effectsSplit = effects.split(",");

        for (String effectString : effectsSplit) {
            String[] effectSplit = effectString.split(":", 2);
            effect = getEffect(effectSplit[0]);

            if (effectSplit.length > 1) {
                if (effect != null) {
                    loc.getWorld().playEffect(Utils.correctLocation(loc), effect, Integer.parseInt(effectSplit[1]), 128);
                    effectCounter++;
                    sender.sendMessage(ChatColor.GREEN + "The effect \"" + ChatColor.GOLD + effect.name() + ChatColor.GREEN + "\" was played at the specified location!");
                } else {
                    sender.sendMessage(ChatColor.RED + "The effect \"" + ChatColor.GOLD + effectString + ChatColor.RED + "\" does not exist!");
                }
            } else if (effectSplit.length == 1) {
                if (getEffect(effectSplit[0]) != null) {
                    loc.getWorld().playEffect(Utils.correctLocation(loc), effect, 128);
                    effectCounter++;
                    sender.sendMessage(ChatColor.GREEN + "The effect \"" + ChatColor.GOLD + effect.name() + ChatColor.GREEN + "\" was played at the specified location!");
                } else {
                    sender.sendMessage(ChatColor.RED + "The effect \"" + ChatColor.GOLD + effectString + ChatColor.RED + "\" does not exist!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "The effect \"" + ChatColor.GOLD + effectString + ChatColor.RED + "\" does not exist!");
            }
        }

        if (effectCounter > 0) {
            return true;
        }

        return false;
    }

    public static String getMessage(String[] args) {
        StringBuilder chatMessage = new StringBuilder();

        for (String arg: args) chatMessage.append(arg).append(" ");

        return chatMessage.toString();
    }
}