UberHardcore
============

Name TBC.

Experimental bukkit mod/plugin, modifies Minecraft.

Compatability: Java 7+, Bukkit 1.8 (created against 1.8, 1.8.3 and 1.8.8)

# Notice

Whenever the plugin is loaded/unloaded it will remove all entities in the affected list below. If you do /reload all
the affected entities will be killed and will respawn under regular rules. This also applies on server start/stop. This
is to ensure that no invalid/invisible custom mobs are walking around causing havoc.

AFFECTED: Zombie, Skeleton, Spider, Sheep, Chicken  
UNAFFECTED: Rabbit, Creeper, Ghast

# Changes:

### Zombie

- walk slower
- when agroed move faster
- leap at target
- dont need sight to agro
- longer agro range
- don't attack villagers
- immune to fire
- 4 base damage instead of 3
- 30 health instead of 20

### Skeleton

- wither skeletons spawn at 30% chance, use a bow (flame arrows automatically)
- shoot double speed at max range (regular speed at close range)
- more accurate (still crappy at max range, basically never hit)
- longer attack + agro range

### Spider

- Attack no matter the light level
- Explodes webs (as falling sand entities) and shows blood particles on death
- 50% base movement speed buff, makes about the same speed as a sprint jumping player on flat land

### Sheep

- Float around like derpy clouds, because why not

### Chicken

- Ranged mob that shoot eggs that do damage (half heart base)
- Chance for eggs to spawn babies is a flat 1/32 chance and only 1 can spawn

### Creeper

- Explodes a short time after it's death (gives particle effect warning) does not trigger if the creeper dies by exploding

### Rabbit

- 1% chance to spawn as a killer rabbit

### Ghast

- Now can spawn in ALL biomes

## Known problems

Zombies riding chickens *shouldnt* work, as well as Skeleton jockeys. Any invalid non-custom mobs spawned are cancelled
meaning there will be no invalid invisible mobs walking around.