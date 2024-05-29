package showcase.financial.open.banking.controller;

import com.vmware.financial.open.banking.account.service.AccountService;
import com.vmware.financial.open.banking.domain.account.BankAccount;
import com.vmware.financial.open.banking.domain.account.BankAccountCreateDto;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    private BankAccount account  = JavaBeanGeneratorCreator.of(BankAccount.class).create();

    @Mock
    private BankAccountCreateDto accountCreateDto;

    @Mock
    private AccountService accountService;

    private AccountController subject;

    @BeforeEach
    public void setUp() {
        accountCreateDto = JavaBeanGeneratorCreator.of(BankAccountCreateDto.class).create();
        subject = new AccountController(accountService);
    }



    @Test
    public void create() {
        var bankId = "expected";
        var response = subject.createAccount(bankId,accountCreateDto);
        Mockito.verify(accountService).createAccount(anyString(),any());
    }

    @Test
    public void getAccountById_foundAccount() {

        when(accountService.findAccountById(anyString(),anyString())).thenReturn(Optional.of(account));

        var bankId = "expected";
        var actualHttpEntity = subject.getAccountById(bankId,account.getId());
        assertEquals(HttpStatus.OK, actualHttpEntity.getStatusCode() );
        assertEquals(account,actualHttpEntity.getBody());
    }

    @Test
    public void getAccountById_notFoundAccount() {


            //onGeneric { findAccountById(anyString(),anyString())} doReturn Optional.empty()        }
        when(accountService.findAccountById(anyString(),anyString())).thenReturn(Optional.empty());

        subject = new AccountController(accountService);

        var bankId = "expected";

        var actualHttpEntity = subject.getAccountById(bankId,account.getId());
        assertEquals(actualHttpEntity.getStatusCode() ,HttpStatus.NOT_FOUND);
    }


}