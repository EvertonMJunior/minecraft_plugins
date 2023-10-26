package me.everton.esw;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.server.v1_8_R1.DataWatcher;
import net.minecraft.server.v1_8_R1.EnumGamemode;
import net.minecraft.server.v1_8_R1.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R1.PlayerInfoData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.mojang.authlib.GameProfile;
public class NPC {
   
   private DataWatcher watcher;
   private Material chestplate;
   private Material leggings;
   private Location location;
   private Material inHand;
   private Material helmet;
   private Material boots;
   private String tablist;
   private int entityID;
   private String name;
   private UUID uuid;
   
   /*
    * NPC, a class for spawning fake players in the 1.8
   Copyright (C) [Summerfeeling]
   Dieses Programm ist freie Software. Sie können es unter den Bedingungen der GNU General Public License, wie von der Free Software Foundation veröffentlicht, weitergeben und/oder modifizieren, entweder gemäß Version 3 der Lizenz oder (nach Ihrer Option) jeder späteren Version.
   Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es Ihnen von Nutzen sein wird, aber OHNE IRGENDEINE GARANTIE, sogar ohne die implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FÜR EINEN BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
   Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem Programm erhalten haben. Falls nicht, siehe <http://www.gnu.org/licenses/>.
    */
   
   public NPC(String name, String tablist, UUID uuid, int entityID, Location location, Material inHand) {
      this.location = location;
      this.tablist = tablist;
      this.name = name;
      this.uuid = uuid;
      this.entityID = entityID;
      this.inHand = inHand;
      this.watcher = new DataWatcher(null);
      watcher.a(6, (float) 20);
   }
   
   public NPC(String name, Location location) {
      this(name, name, UUID.randomUUID(), new Random().nextInt(10000), location, Material.AIR);
   }
   
   public NPC(String name, Location location, Material inHand) {
      this(name, name, UUID.randomUUID(), new Random().nextInt(10000), location, inHand);
   }
   
   public NPC(String name, String tablist, Location location) {
      this(name, tablist, UUID.randomUUID(), new Random().nextInt(10000), location, Material.AIR);
   }
   
   public NPC(String name, String tablist, Location location, Material inHand) {
      this(name, tablist, UUID.randomUUID(), new Random().nextInt(10000), location, inHand);
   }
   
   @SuppressWarnings("deprecation")
   public void spawn() {
      try{
         PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
         this.addToTablist();
         
         this.setValue(packet, "a", this.entityID);
         this.setValue(packet, "b", this.uuid);
         this.setValue(packet, "c", this.toFixedPoint(this.location.getX()));
         this.setValue(packet, "d", this.toFixedPoint(this.location.getY()));
         this.setValue(packet, "e", this.toFixedPoint(this.location.getZ()));
         this.setValue(packet, "f", this.toPackedByte(this.location.getYaw()));
         this.setValue(packet, "g", this.toPackedByte(this.location.getPitch()));
         this.setValue(packet, "h", this.inHand == null ? 0 : this.inHand.getId());
         this.setValue(packet, "i", this.watcher);
         
         for(Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
         }
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   @SuppressWarnings("deprecation")
   public void despawn() {
      PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[]{this.entityID});
      this.removeFromTablist();
      for(Player online : Bukkit.getOnlinePlayers()) {
         ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
      }
   }
   
   @SuppressWarnings("deprecation")
   public void changePlayerlistName(String name) {
      try{
         PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
         PlayerInfoData data = new PlayerInfoData(packet, new GameProfile(this.uuid, this.name), 0, EnumGamemode.NOT_SET, CraftChatMessage.fromString(name)[0]);
         @SuppressWarnings("unchecked") List<PlayerInfoData> players = (List<PlayerInfoData>) this.getValue(packet, "b");
         players.add(data);
         
         this.setValue(packet, "a", EnumPlayerInfoAction.UPDATE_DISPLAY_NAME);
         this.setValue(packet, "b", players);
         this.tablist = name;
         
         for(Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
         }
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   @SuppressWarnings("deprecation")
   private void addToTablist() {
      try {
         PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
         PlayerInfoData data = new PlayerInfoData(packet, new GameProfile(this.uuid, this.name), 0, EnumGamemode.NOT_SET, CraftChatMessage.fromString(this.tablist)[0]);
         @SuppressWarnings("unchecked") List<PlayerInfoData> players = (List<PlayerInfoData>) this.getValue(packet, "b");
         players.add(data);
         
         this.setValue(packet, "a", EnumPlayerInfoAction.ADD_PLAYER);
         this.setValue(packet, "b", players);
         
         for(Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   @SuppressWarnings("deprecation")
   private void removeFromTablist() {
      try{
         PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
         PlayerInfoData data = new PlayerInfoData(packet, new GameProfile(this.uuid, this.name), 0, EnumGamemode.NOT_SET, CraftChatMessage.fromString(this.tablist)[0]);
         @SuppressWarnings("unchecked") List<PlayerInfoData> players = (List<PlayerInfoData>) this.getValue(packet, "b");
         players.add(data);
         
         this.setValue(packet, "a", EnumPlayerInfoAction.REMOVE_PLAYER);
         this.setValue(packet, "b", players);
         
         for(Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
         }
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   @SuppressWarnings("deprecation")
   public void teleport(Location location) {
      try{
         PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
         
         this.setValue(packet, "a", this.entityID);
         this.setValue(packet, "b", this.toFixedPoint(location.getX()));
         this.setValue(packet, "c", this.toFixedPoint(location.getY()));
         this.setValue(packet, "d", this.toFixedPoint(location.getZ()));
         this.setValue(packet, "e", this.toPackedByte(location.getYaw()));
         this.setValue(packet, "f", this.toPackedByte(location.getPitch()));
         this.setValue(packet, "g", this.location.getBlock().getType() == Material.AIR ? false : true);
         this.location = location;
         
         for(Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
         }
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   @SuppressWarnings("deprecation")
   public void setItemInHand(Material material) {
      try{
         PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
         
         this.setValue(packet, "a", this.entityID);
         this.setValue(packet, "b", 0);
         this.setValue(packet, "c", material == Material.AIR || material == null ? CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)) : CraftItemStack.asNMSCopy(new ItemStack(material)));
         this.inHand = material;
         
         for(Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
         }
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   public Material getItemInHand() {
      return this.inHand;
   }
   
   @SuppressWarnings("deprecation")
   public void setHelmet(Material material) {
      try{
         PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
         
         this.setValue(packet, "a", this.entityID);
         this.setValue(packet, "b", 4);
         this.setValue(packet, "c", material == Material.AIR || material == null ? CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)) : CraftItemStack.asNMSCopy(new ItemStack(material)));
         this.helmet = material;
         
         for(Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
         }
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   public Material getHelmet() {
      return this.helmet;
   }
   
   @SuppressWarnings("deprecation")
   public void setChestplate(Material material) {
      try{
         PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
         
         this.setValue(packet, "a", this.entityID);
         this.setValue(packet, "b", 3);
         this.setValue(packet, "c", material == Material.AIR || material == null ? CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)) : CraftItemStack.asNMSCopy(new ItemStack(material)));
         this.chestplate = material;
         
         for(Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
         }
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   public Material getChestplate() {
      return this.chestplate;
   }
   
   @SuppressWarnings("deprecation")
   public void setLeggings(Material material) {
      try{
         PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
         
         this.setValue(packet, "a", this.entityID);
         this.setValue(packet, "b", 2);
         this.setValue(packet, "c", material == Material.AIR || material == null ? CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)) : CraftItemStack.asNMSCopy(new ItemStack(material)));
         this.leggings = material;
         
         for(Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
         }
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   public Material getLeggings() {
      return this.leggings;
   }
   
   @SuppressWarnings("deprecation")
   public void setBoots(Material material) {
      try{
         PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
         
         this.setValue(packet, "a", this.entityID);
         this.setValue(packet, "b", 1);
         this.setValue(packet, "c", material == Material.AIR || material == null ? CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)) : CraftItemStack.asNMSCopy(new ItemStack(material)));
         this.boots = material;
         
         for(Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
         }
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   public Material getBoots() {
      return this.boots;
   }
   
   public int getEntityID() {
      return this.entityID;
   }
   
   public UUID getUUID() {
      return this.uuid;
   }
   
   public Location getLocation() {
      return this.location;
   }
   
   public String getName() {
      return this.name;
   }
   
   public String getPlayerlistName() {
      return this.tablist;
   }
   
   private void setValue(Object instance, String field, Object value) throws Exception {
      Field f = instance.getClass().getDeclaredField(field);
      f.setAccessible(true);
      f.set(instance, value);
   }
   
   private Object getValue(Object instance, String field) throws Exception {
      Field f = instance.getClass().getDeclaredField(field);
      f.setAccessible(true);
      return f.get(instance);
   }
   
   private int toFixedPoint(double d) {
      return (int) (d * 32.0);
   }
   
   private byte toPackedByte(float f) {
      return (byte) ((int) (f * 256.0F / 360.0F));
   }
   
}