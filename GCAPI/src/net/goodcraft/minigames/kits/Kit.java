package net.goodcraft.minigames.kits;

import net.goodcraft.Main;
import net.goodcraft.api.ActionBar;
import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public abstract class Kit implements Listener {

    public static ArrayList<Kit> kits = new ArrayList<>();
    private String name;
    private ItemStack selector;
    private ItemStack[] items;
    private List<String> description;
    private ArrayList<UUID> using;
    private boolean isVip = false;
    private int price;
    private HashMap<UUID, Long> cooldown = new HashMap<>();

    public Kit(String nome, Material kitSelector, ItemStack[] kitItems, List<String> desc) {
        name = nome;

        ItemStack i = Item.item(kitSelector, 1, "§3" + nome, desc.toArray(new String[desc.size()]));
        ;
        ItemMeta im = i.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES);
        i.setItemMeta(im);
        selector = i;

        items = kitItems;
        description = desc;
        using = new ArrayList<>();

        Bukkit.getPluginManager().registerEvents(this, Main.minigame.getPlugin());
        kits.add(this);
    }

    public Kit(String nome, Material kitSelector, int type, ItemStack[] kitItems, List<String> desc) {
        name = nome;

        ItemStack i = Item.item(kitSelector, 1, "§3" + nome, desc.toArray(new String[desc.size()]), type);
        ItemMeta im = i.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES);
        i.setItemMeta(im);
        selector = i;

        items = kitItems;
        description = desc;
        using = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(this, Main.minigame.getPlugin());
        kits.add(this);
    }

    public Kit(String nome, PotionType type, ItemStack[] kitItems, List<String> desc) {
        name = nome;

        ItemStack i = Item.item(type, 1, "§3" + nome, desc.toArray(new String[desc.size()]), false, false, 1);
        ItemMeta im = i.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES);
        i.setItemMeta(im);
        selector = i;

        items = kitItems;
        description = desc;
        using = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(this, Main.minigame.getPlugin());
        kits.add(this);
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip() {
        this.isVip = true;
    }

    public String toString() {
        return name;
    }

    public ItemStack getKitSelectorItem() {
        return selector;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] item) {
        items = item;
    }

    public List<String> getDesc() {
        return description;
    }

    public void addPlayer(Player p) {
        using.add(p.getUniqueId());
    }

    public void addItems(Player p){
        String[] nomes = new String[]{"HELMET", "CHESTPLATE", "LEGGINGS", "BOOTS"};
        ArrayList<String> armor = new ArrayList<>();
        armor.addAll(Arrays.asList(nomes));

        if (getItems() != null) {
            loop1:
            for (ItemStack item : getItems()) {
                if (item != null) {
//                    for(String st : armor){
//                        if(item.getType().name().contains(st)){
//                            if(st.equalsIgnoreCase("HELMET")){
//                                p.getInventory().setHelmet(item);
//                            } else if(st.equalsIgnoreCase("CHESTPLATE")){
//                                p.getInventory().setChestplate(item);
//                            } else if(st.equalsIgnoreCase("LEGGINGS")){
//                                p.getInventory().setLeggings(item);
//                            } else if(st.equalsIgnoreCase("BOOTS")){
//                                p.getInventory().setBoots(item);
//                            }
//                            continue loop1;
//                        }
//                    }
                    p.getInventory().addItem(item);
                }
            }
        }
    }

    protected boolean hasAbility(Player p) {
        return using.contains(p.getUniqueId());
    }

    public void removePlayer(Player p) {
        using.remove(p.getUniqueId());
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for (Player o : Bukkit.getOnlinePlayers()) {
            if (!using.contains(o.getUniqueId())) {
                continue;
            }
            players.add(o);
        }
        return players;
    }

    protected void addCooldown(final Player p, final int seconds) {
        long cooldownLength = System.currentTimeMillis() + seconds * 1000;
        cooldown.remove(p.getUniqueId());
        cooldown.put(p.getUniqueId(), cooldownLength);
        new BukkitRunnable() {
            int i = 0;

            public void run() {
                if (!using.contains(p.getUniqueId())) {
                    cooldown.remove(p.getUniqueId());
                    cancel();
                    return;
                } else if (i == seconds) {
                    ActionBar.INFO.send(p, "O cooldown acabou!");
                    p.playSound(p.getLocation(), Sound.CLICK, 1f, 1f);
                    cooldown.remove(p.getUniqueId());
                    cancel();
                    return;
                } else if (!p.isOnline()) {
                    cooldown.remove(p.getUniqueId());
                    cancel();
                    return;
                } else if (!cooldown.containsKey(p.getUniqueId())) {
                    cancel();
                    return;
                }
                i++;
            }
        }.runTaskTimer(Main.minigame.getPlugin(), 0, 20);
    }

    protected String getCooldown(Player p) {
        long cooldownLength = (cooldown.get(p.getUniqueId()));
        long left = (cooldownLength - System.currentTimeMillis()) / 1000L;
        return left + "";
    }

    protected boolean hasCooldown(Player p) {
        return cooldown.containsKey(p.getUniqueId());
    }

    protected void removeCooldown(Player p) {
        cooldown.remove(p.getUniqueId());
    }

    protected void mensagemcooldown(Player p) {
        Message.INFO.send(p, "Você precisa esperar mais " + getCooldown(p) + " segundos!");
    }

    public void onStartGame() {

    }
}
