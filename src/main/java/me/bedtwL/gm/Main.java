package me.bedtwL.gm;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Main extends JavaPlugin implements CommandExecutor, TabCompleter {

    private FileConfiguration config;
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        config = getConfig();
        registerCommand("gmc");
        registerCommand("gms");
        registerCommand("gma");
        registerCommand("gmsp");
        registerCommand("gmr");
        getLogger().info("Hello! short gamemode command is working!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Damn! short gamemode command is died!");
    }
    private void registerCommand(String name) {
        PluginCommand cmd = getCommand(name);
        if (cmd != null) {
            cmd.setExecutor(this);
            cmd.setTabCompleter(this);
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = label.toLowerCase();

        if (cmd.equals("gmr")) {
            String perm = config.getString("permissions.gmr", "gamemode.reload");
            if (!sender.hasPermission(perm)) {
                sender.sendMessage(config.getString("messages.no-permission"));
                return true;
            }

            reloadConfig();
            config = getConfig();
            sender.sendMessage(config.getString("messages.config-reloaded"));
            return true;
        }

        GameMode mode;
        switch (cmd) {
            case "gmc": mode = GameMode.CREATIVE; break;
            case "gms": mode = GameMode.SURVIVAL; break;
            case "gma": mode = GameMode.ADVENTURE; break;
            case "gmsp": mode = GameMode.SPECTATOR; break;
            default: return true;
        }

        String perm = config.getString("permissions." + cmd, "gamemode." + cmd);
        if (!sender.hasPermission(perm)) {
            sender.sendMessage(config.getString("messages.no-permission"));
            return true;
        }

        Player target;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(config.getString("messages.not-a-player"));
                return true;
            }
            target = (Player) sender;
        } else {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(config.getString("messages.player-not-found"));
                return true;
            }
        }

        target.setGameMode(mode);
        String gamemodeName = mode.name().toLowerCase();

        String msgSelf = config.getString("messages.gamemode-changed-self", "")
                .replace("%gamemode%", gamemodeName);
        String msgOther = config.getString("messages.gamemode-changed-other", "")
                .replace("%target%", target.getName())
                .replace("%gamemode%", gamemodeName);

        if (!target.equals(sender)) {
            sender.sendMessage(msgOther);
        }
        target.sendMessage(msgSelf);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1 && Arrays.asList("gmc", "gms", "gma", "gmsp").contains(alias.toLowerCase())) {
            List<String> names = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    names.add(p.getName());
                }
            }
            return names;
        }
        return Collections.emptyList();
    }
}
