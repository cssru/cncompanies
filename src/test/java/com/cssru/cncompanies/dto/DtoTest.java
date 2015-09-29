package com.cssru.cncompanies.dto;

import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.ProvidedService;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DtoTest {

    @Test
    public void testDtoMapFrom() throws Exception {
        Account account = new Account();
        account.setId(555L);
        account.setHuman(new Human());
        account.setLogin("testLogin");
        account.setEmail("test@email.com");
        account.setLocked(true);
        account.setEnabled(true);
        account.setCreated(new Date(1234567));
        account.setProvidedServices(new HashSet<ProvidedService>());
        account.setMoney(12345);
        account.setVersion(54321L);

        AccountDto accountDto = new AccountDto();
        accountDto.mapFrom(account);

        assertEquals(account.getId(), accountDto.getId());
        assertNull(accountDto.getHuman());
        assertEquals(account.getLogin(), accountDto.getLogin());
        assertEquals(account.getLocked(), accountDto.getLocked());
        assertEquals(account.getEmail(), accountDto.getEmail());
        assertTrue(accountDto.getLocked());
        assertTrue(accountDto.getEnabled());
        assertEquals(account.getCreated(), accountDto.getCreated());
        assertNull(accountDto.getProvidedServices());
        assertEquals(account.getMoney(), accountDto.getMoney());
        assertEquals(account.getVersion(), accountDto.getVersion());

        ChangePasswordDto cpDto = new ChangePasswordDto();
        account.setPassword("testPassword");
        cpDto.mapFrom(account);
        assertEquals(account.getLogin(), cpDto.getLoginName());
        assertEquals(account.getPassword(), cpDto.getNewPassword());
        assertNull(cpDto.getOldPassword());
        assertNull(cpDto.getNewPassword2());

    }
}
