package me.everton.jpvp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.json.JSONArray;
import org.json.JSONObject;

public class UUID extends Thread {
	@SuppressWarnings("unused")
	public static String get(String name) {
		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if (p.getName().equalsIgnoreCase(name)) {
				return p.getUniqueId().toString().replace("-", "");
			}
		}

		try {
			JSONObject uuidJson = new JSONObject(
					readUrl("https://api.mojang.com/users/profiles/minecraft/"
							+ name));
			JSONArray jsonArray = new JSONArray(
					readUrl("https://api.mojang.com/user/profiles/"
							+ uuidJson.getString("id") + "/names"));
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject explrObject = jsonArray.getJSONObject(i);
				if (i != 0) {
					builder.append(", " + explrObject.get("name"));
				} else {
					builder.append(explrObject.get("name"));
				}
				return uuidJson.getString("id");
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	static String result = null;

	public static String readUrl(String urlString) {
		Thread t = new Thread() {
			public void run() {
				BufferedReader reader = null;
				try {
					URL url = new URL(urlString);
					reader = new BufferedReader(new InputStreamReader(
							url.openStream()));
					StringBuffer buffer = new StringBuffer();
					char[] chars = new char['?'];
					int read;
					while ((read = reader.read(chars)) != -1) {
						buffer.append(chars, 0, read);
					}
					result = buffer.toString();
				} catch (IOException e) {
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
						}
					}
				}
			}
		};
		t.run();

		return result;
	}
}
