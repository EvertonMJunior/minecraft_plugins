package net.goodcraft.kitpvp.kits.habilidades;

import net.goodcraft.api.ActionBar;
import net.goodcraft.api.SecondsEvent;
import net.goodcraft.kitpvp.Main;
import net.goodcraft.minigames.eventos.DamageEvent;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class Madman extends Kit {
    public static HashMap<UUID, Integer> afetados = new HashMap<>();
    public static HashMap<UUID, UUID> afetadosAutor = new HashMap<>();

    public Madman() {
        super("Madman", Material.DIAMOND_SWORD,
                new ItemStack[]{null},
                Arrays.asList("§7Quanto mais jogadores estiverem",
                        "§7em sua volta, mais dano você dará",
                        "§7em seus adversários, sendo assim um",
                        "§7ótimo kit contra times!"));
        setPrice(4250);
        setVip();
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void madMan(SecondsEvent e) {
        for (Player on : Bukkit.getOnlinePlayers()) {
            if (hasAbility(on)) {
                ArrayList<UUID> pP = new ArrayList<>();
                for (Entity ent : on.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
                    if (ent instanceof Player) {
                        Player near = (Player) ent;
                        if (Main.getMg().isPlayer(near.getUniqueId())) {
                            pP.add(near.getUniqueId());
                        }
                    }
                }

                if (pP.size() > 0) {
                    for (UUID pN : pP) {
                        Player p = Bukkit.getPlayer(pN);
                        if (p != null) {
                            if (afetados.containsKey(p.getUniqueId())) {
                                afetados.put(p.getUniqueId(),
                                        afetados.get(p.getUniqueId()) + 3);
                            } else {
                                afetados.put(p.getUniqueId(), 3);
                            }
                            afetadosAutor.put(p.getUniqueId(), on.getUniqueId());
                        }
                    }
                }
            }


            if (afetados.containsKey(on.getUniqueId())) {
                Player au = Bukkit.getPlayer(afetadosAutor.get(on
                        .getUniqueId()));
                int value = afetados.get(on.getUniqueId()) - 3;

                if (afetados.get(on.getUniqueId()) <= 0) {
                    afetados.remove(on.getUniqueId());
                    afetadosAutor.remove(on.getUniqueId());
                    return;
                }

                if (!Main.getMg().isPlayer(on.getUniqueId())) {
                    afetados.remove(on.getUniqueId());
                    afetadosAutor.remove(on.getUniqueId());
                    return;
                }

                if (au != null) {
                    if (!au.getNearbyEntities(10.0D, 10.0D, 10.0D).contains(
                            (Entity) on)) {
                        afetados.put(on.getUniqueId(), value);
                    }
                    if (!Main.getMg().isPlayer(au.getUniqueId())) {
                        afetados.put(on.getUniqueId(), value);
                    }
                    if (!hasAbility(au)) {
                        afetados.put(on.getUniqueId(), value);
                    }
                } else if (au == null) {
                    afetados.put(on.getUniqueId(), value);
                }
                ActionBar.INFO.send(on, "Efeito do Madman: " + afetados.get(on.getUniqueId()) + "%");
            }
        }
    }

    @EventHandler
    public void madmanDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        Player en = (Player) e.getEntity();
        Player p = (Player) e.getDamager();

        if (!hasAbility(p)) return;

        DamageEvent.nerfDamage(p, e);

        double dano = e.getDamage();

        if (Madman.afetados.containsKey(en.getUniqueId())) {
            dano += Madman.afetados.get(en.getUniqueId()) / 50;
        }

        e.setDamage(dano);
    }
}