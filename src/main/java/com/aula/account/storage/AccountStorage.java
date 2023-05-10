package com.aula.account.storage;

import com.aula.account.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountStorage {

    private final Map<UUID, Account> map = new HashMap<>();

    public void save(Account account) {
        map.put(account.getPlayer().getUniqueId(), account);
    }

    public Account read(UUID uuid) {
        return map.get(uuid);
    }

    public void delete(UUID uuid) {
        map.remove(uuid);
    }
}
