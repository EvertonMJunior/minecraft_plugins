package com.mojang.api.profiles;

public interface ProfileRepository {
	public Profile[] findProfilesByCriteria(final ProfileCriteria... criteria);
}
