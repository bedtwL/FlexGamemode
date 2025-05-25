## 📄 FlexGamemode Plugin

Spigot: https://www.spigotmc.org/resources/flexgamemode.125369/

This Spigot plugin allows players and administrators to change game modes easily using the following commands:

* `/gmc [player]` — Set Creative mode
* `/gms [player]` — Set Survival mode
* `/gma [player]` — Set Adventure mode
* `/gmsp [player]` — Set Spectator mode
* `/gmr` — Reload plugin configuration

Feel free to request/make a push request for adding features.

All commands support:

* Optional target player
* Customizable permissions
* Fully configurable messages

---

## ⚙️ Configuration (`config.yml`)

### 🔐 `permissions` section

Customize the required permission node for each command:

```yaml
permissions:
  gmc: "bedtwl.gm.gmc"
  gms: "bedtwl.gm.gms"
  gma: "bedtwl.gm.gma"
  gmsp: "bedtwl.gm.gmsp"
  gmr: "bedtwl.gm.reload"
```

> ⚠️ Make sure to grant these permissions using a permissions plugin like LuckPerms or PermissionsEx.

---

### 💬 `messages` section

Customize all messages sent to the player. You can use placeholders like:

* `%gamemode%` — the name of the gamemode (e.g., `creative`)
* `%target%` — the name of the target player

```yaml
messages:
  no-permission: "§cYou don't have permission to use this command."
  player-not-found: "§cPlayer not found."
  not-a-player: "§cOnly players can use this command without arguments."
  gamemode-changed-self: "§aYour gamemode has been set to %gamemode%."
  gamemode-changed-other: "§aYou set %target%'s gamemode to %gamemode%."
  config-reloaded: "§aConfiguration reloaded successfully."
```

You can use Minecraft color codes with `§` (e.g., `§a` for green, `§c` for red), but `&` is not supported

---

## 🔄 Reloading the Config

You can reload the config file without restarting the server using:

```
/gmr
```

> This requires the permission defined in `permissions.gmr`.

---

## 🧪 Examples

### Change your own gamemode to creative:

```
/gmc
```

### Change another player's gamemode to spectator:

```
/gmsp Notch
```
