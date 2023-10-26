package net.goodcraft.api;

import com.google.common.collect.Iterators;
import net.goodcraft.Main;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Utils {

    /*
        Retorna o DisplayName de um Item.
     */
    public static String getDisplayName(ItemStack is) {
        if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
            return is.getItemMeta().getDisplayName();
        }
        return null;
    }

    /*
        Retorna uma localização(X, Y e Z) em forma de String.
     */
    public static String getXYZ(Location fromToGet) {
        String xyz = "X " + fromToGet.getBlockX() + ", Y " + fromToGet.getBlockY() + ", Z " + fromToGet.getBlockZ();
        return xyz;
    }

    /*
        Pega uma frase de um array de Strings.
     */
    public static String getSentence(String[] args) {
        String sentence = "";
        for (int i = 0; i < args.length; i++) {
            sentence += args[i] + (i == (args.length - 1) ? "" : " ");
        }
        return sentence;
    }

    /*
        Pega uma frase de um array de Strings, com um índice inicial.
     */
    public static String getSentence(String[] args, int inicial) {
        String sentence = "";
        for (int i = inicial; i < args.length; i++) {
            sentence += args[i] + (i == (args.length - 1) ? "" : " ");
        }
        return sentence;
    }

    /*
        Envia uma mensagem para todos os jogadores.
     */
    public static void broadcast(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

    /*
        Envia uma mensagem para todos os jogadores de determinado rank.
     */
    public static void broadcast(String message, Rank rank) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!Rank.has(p.getUniqueId(), rank)) {
                continue;
            }
            p.sendMessage(message);
        }
    }

    /*
        Limpa o chat do jogador.
     */
    public static void clearChat(Player p) {
        for (int i = 0; i < 90; i++) {
            p.sendMessage(" ");
        }
    }

    /*
        Envia uma mensagem de recurso destinado apenas para VIPs.
     */
    public static void onlyVip(Player p) {
        Message.INFO.send(p, "Este recurso é destinado apenas para VIPs! Compre um em good-craft.net");
    }

    /*
        Esconde um jogador para todos os outros.
     */
    public static void hideForAll(Player p) {
        for (Player oP : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(oP);
        }
    }

    /*
        Esconde um jogador para todos os outros, exceto os que pertencem ao rank.
     */
    public static void hideForAllExcept(Player p, Rank rank) {
        for (Player oP : Bukkit.getOnlinePlayers()) {
            if ((Rank.has(oP.getUniqueId(), rank))) {
                continue;
            }
            oP.hidePlayer(p);
        }
    }

    /*
        Mostra um jogador para todos.
     */
    public static void showForAll(Player p) {
        for (Player oP : Bukkit.getOnlinePlayers()) {
            oP.showPlayer(p);
        }
    }

    /*
        Mostra um jogador para todos os jogadores de determinado rank.
     */
    public static void showForAll(Player p, Rank rank) {
        for (Player oP : Bukkit.getOnlinePlayers()) {
            if ((!Rank.has(oP.getUniqueId(), rank))) {
                continue;
            }
            oP.showPlayer(p);
        }
    }

    /*
        Esconde todos os jogadores para um jogador.
     */
    public static void hideAllFor(Player p) {
        for (Player oP : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(oP);
        }
    }

    /*
        Esconde todos os jogadores de determinado rank para um jogador.
     */
    public static void hideAllFor(Player p, Rank rank) {
        for (Player oP : Bukkit.getOnlinePlayers()) {
            if ((!Rank.has(oP.getUniqueId(), rank))) {
                continue;
            }
            p.hidePlayer(oP);
        }
    }

    /*
        Mostra todos os jogadores para um jogador.
     */
    public static void showAllFor(Player p) {
        for (Player oP : Bukkit.getOnlinePlayers()) {
            if (AdminAPI.admins.containsKey(oP.getName())) continue;
            p.showPlayer(oP);
        }
    }

    /*
        Mostra todos os jogadores de determinado rank para um jogador.
     */
    public static void showAllFor(Player p, Rank rank) {
        for (Player oP : Bukkit.getOnlinePlayers()) {
            if ((!Rank.has(oP.getUniqueId(), rank))) {
                continue;
            }
            p.showPlayer(oP);
        }
    }

    /*
        Transforma um número com apenas um digito em um com dois.
     */
    public static String doisDigitos(int number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    /*
        Transforma uma Integer de segundos em String no formato mm:ss.
     */
    public static String secondsToString(int seconds) {
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    /*
        Transforma uma Integer de segundos em String no formato "mm minutos e ss segundos".
     */
    public static String secondsToSentence(int seconds) {
        if (seconds >= 86400) {
            int d = (int) TimeUnit.SECONDS.toDays(seconds);
            int h = (int) (TimeUnit.SECONDS.toHours(seconds) - (d * 24));
            int m = (int) (TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS
                    .toHours(seconds) * 60));
            int s = (int) (TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS
                    .toMinutes(seconds) * 60));
            return doisDigitos(d) + " dia(s), " + doisDigitos(h) + " hora(s), "
                    + doisDigitos(m) + " minuto(s) e " + doisDigitos(s)
                    + " segundo(s)";
        } else if (seconds >= 3600) {
            int h = (int) TimeUnit.SECONDS.toHours(seconds);
            int m = (int) (TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS
                    .toHours(seconds) * 60));
            int s = (int) (TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS
                    .toMinutes(seconds) * 60));

            return doisDigitos(h) + " hora(s), " + doisDigitos(m)
                    + " minuto(s) e " + doisDigitos(s) + " segundo(s)";
        } else {
            int m = (int) TimeUnit.SECONDS.toMinutes(seconds);
            int s = seconds - (m * 60);
            if ((m == 0) && (s != 0)) {
                if (s > 1) {
                    return doisDigitos(s) + " segundos";
                } else {
                    return doisDigitos(s) + " segundo";
                }
            } else if ((m != 0) && (s == 0)) {
                if (m > 1) {
                    return doisDigitos(m) + " minutos";
                } else {
                    return doisDigitos(m) + " minuto";
                }
            } else {
                if ((s > 1) && (m > 1)) {
                    return doisDigitos(m) + " minutos e " + s + " segundos";
                } else if ((s > 1) && (!(m > 1))) {
                    return doisDigitos(m) + " minuto e " + s + " segundos";
                } else if ((m > 1) && (!(s > 1))) {
                    return doisDigitos(m) + " minutos e " + s + " segundo";
                }
            }
            return doisDigitos(s) + " minutos e " + s + " segundos";
        }
    }

    public static void registerCommands(File f) {
        try {
            try (JarInputStream is = new JarInputStream(new FileInputStream(f))) {
                for (JarEntry entry; (entry = is.getNextJarEntry()) != null; ) {
                    if (!entry.getName().endsWith(".class")) {
                        continue;
                    }
                    if (entry.getName().contains("Comando.class")) {
                        continue;
                    }
                    String path = entry.getName().replace("/", ".").replace(".class", "");
                    if(path.contains("GameMode")){
                        continue;
                    }
                    Class cl = Class.forName(path);
                    if (cl.getSuperclass() == null) {
                        continue;
                    }

                    if (cl.getSuperclass().equals(Comando.class)) {
                        if(f.getName().contains("API")){
                            if (Main.minigame != null) {
                                if (entry.getName().contains("minigames")) {
                                    Minigame mg = Main.minigame;
                                    if (!mg.hasOption(Option.HAS_KITS) && entry.getName().contains("kit")) {
                                        continue;
                                    }
                                }
                            } else {
                                if(entry.getName().contains("minigames")){
                                    continue;
                                }
                            }
                        }

                        Object o = cl.newInstance();
                        Method md = cl.getMethod("register");
                        md.invoke(o);
                    }
                }
            }
        } catch (IOException | InstantiationException | ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void firework(final Location loc, Color l) {
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.clearEffects();
        Random r = new Random();

        int rt = r.nextInt(3) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1) type = FireworkEffect.Type.BALL_LARGE;
        if (rt == 2) type = FireworkEffect.Type.BURST;
        if (rt == 3) type = FireworkEffect.Type.CREEPER;
        if (rt == 4) type = FireworkEffect.Type.STAR;
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).
                withColor(l).withColor(l).withFade(l).with(type).trail(false).build();
        fwm.addEffect(effect);
        fwm.setPower(0);
        fw.setFireworkMeta(fwm);
        final List<Location> blocks = circle(loc, 10, 1, true, false, 0);
        new BukkitRunnable() {
            int maxIterations = 2;
            int iterations = 0;

            public void run() {
                iterations = 0;
                while (!blocks.isEmpty() && iterations++ < maxIterations) {
                    final Firework fw = (Firework) loc.getWorld().spawnEntity(blocks.get(0), EntityType.FIREWORK);
                    FireworkMeta meta = fw.getFireworkMeta();
                    meta.addEffect(FireworkEffect.builder().withColor(Color.RED).
                            withColor(Color.WHITE).withFade(Color.WHITE).build());
                    fw.setFireworkMeta(meta);
                    fw.detonate();
                    blocks.remove(0);
                }
                if (blocks.isEmpty()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getPlugin(), 0, 2);
    }

    private static List<Location> circle(Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        int i = 0;
        for (int x = cx - r; x <= cx + r; x++)
            for (int z = cz - r; z <= cz + r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        if (i++ < 2) {
                            continue;
                        } else {
                            i = 0;
                        }
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
        return circleblocks;
    }

    @SuppressWarnings("deprecation")
    public static void changeChestState(Location loc, boolean open) {
        byte dataByte = (open) ? (byte) 1 : 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playNote(loc, (byte) 1, dataByte);
            BlockPosition position = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            PacketPlayOutBlockAction blockActionPacket = new PacketPlayOutBlockAction(position, net.minecraft.server.v1_8_R3.Block.
                    getById(loc.getBlock().getTypeId()), (byte) 1, dataByte);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(blockActionPacket);
        }
    }

    private static boolean blockOnTop(World world, BlockPosition position) {
        return world.getType(position.up()).getBlock().isOccluding();
    }

    private static boolean ocelotOnTop(World world, BlockPosition position) {
        Iterator var3 = world.a(EntityOcelot.class,
                new AxisAlignedBB((double) position.getX(), (double) (position.getY() + 1),
                        (double) position.getZ(), (double) (position.getX() + 1),
                        (double) (position.getY() + 2), (double) (position.getZ() + 1))).iterator();

        EntityOcelot var5;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            Entity var4 = (Entity) var3.next();
            var5 = (EntityOcelot) var4;
        } while (!var5.isSitting());

        return true;
    }

    private static boolean topBlocking(World world, BlockPosition position) {
        return blockOnTop(world, position) || ocelotOnTop(world, position);
    }

    public static boolean activateChest(Player p, boolean anyChest, boolean silentChest, int x, int y, int z) {
        BlockPosition position = new BlockPosition(x, y, z);
        EntityPlayer player = ((CraftPlayer) p).getHandle();
        net.minecraft.server.v1_8_R3.World world = player.world;
        if (world.isClientSide) {
            return true;
        }

        BlockChest chest = (BlockChest) (((BlockChest) world.getType(position).getBlock()).b == 1 ?
                Block.getByName("trapped_chest") : Block.getByName("chest"));

        TileEntity tileEntity = world.getTileEntity(position);
        if (!(tileEntity instanceof TileEntityChest)) {
            return true;
        }

        ITileInventory tileInventory = (ITileInventory) tileEntity;
        if (!anyChest && topBlocking(world, position)) {
            return true;
        }

        for (EnumDirection direction : EnumDirectionList.HORIZONTAL) {
            BlockPosition side = position.shift(direction);
            Block block = world.getType(side).getBlock();
            if (block == chest) {
                if (!anyChest && topBlocking(world, side)) {
                    return true;
                }

                TileEntity sideTileEntity = world.getTileEntity(side);
                if (sideTileEntity instanceof TileEntityChest) {
                    if (direction != EnumDirection.WEST && direction != EnumDirection.NORTH) {
                        tileInventory = new InventoryLargeChest("container.chestDouble", tileInventory, (TileEntityChest) sideTileEntity);
                    } else {
                        tileInventory = new InventoryLargeChest("container.chestDouble", (TileEntityChest) sideTileEntity, tileInventory);
                    }
                }
            }
        }

        boolean returnValue = true;
        if (silentChest) {
            tileInventory = new SilentInventory(tileInventory);
            returnValue = false;
        }

        player.openContainer(tileInventory);

        return returnValue;
    }

    public static org.bukkit.entity.Entity getNearestEntityInSight(Player player, int range) {
        List<org.bukkit.entity.Entity> entities = player.getNearbyEntities(range, range, range); //Get the entities within range
        Iterator<org.bukkit.entity.Entity> iterator = entities.iterator(); //Create an iterator
        while (iterator.hasNext()) {
            org.bukkit.entity.Entity next = iterator.next(); //Get the next entity in the iterator
            if (!(next instanceof LivingEntity) || next == player) { //If the entity is not a living entity or the player itself, remove it from the list
                iterator.remove();
            }
        }
        List<org.bukkit.block.Block> sight = player.getLineOfSight((Set<org.bukkit.Material>) null, range); //Get the blocks in the player's line of sight (the Set is null to not ignore any blocks)
        for (org.bukkit.block.Block block : sight) { //For each block in the list
            if (block.getType() != org.bukkit.Material.AIR) { //If the block is not air -> obstruction reached, exit loop/seach
                break;
            }
            Location low = block.getLocation();
            Location high = low.clone().add(1, 1, 1);
            AxisAlignedBB blockBoundingBox = AxisAlignedBB.a(low.getX(), low.getY(), low.getZ(), high.getX(), high.getY(), high.getZ());
            for (org.bukkit.entity.Entity entity : entities) {
                if (entity.getLocation().distance(player.getEyeLocation()) <= range && ((CraftEntity) entity).getHandle().getBoundingBox().b(blockBoundingBox)) {
                    return entity;
                }
            }
        }
        return null;
    }

    public enum EnumDirectionList implements Iterable<EnumDirection> {
        HORIZONTAL(EnumDirection.EnumDirectionLimit.HORIZONTAL),
        VERTICAL(EnumDirection.EnumDirectionLimit.VERTICAL);

        private final EnumDirection.EnumDirectionLimit list;

        private EnumDirectionList(EnumDirection.EnumDirectionLimit list) {
            this.list = list;
        }

        @Override
        public Iterator<EnumDirection> iterator() {
            return Iterators.forArray(list.a());
        }
    }

    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                deleteDir(new File(dir, children[i]));
            }
        }
        dir.delete();
    }

}
