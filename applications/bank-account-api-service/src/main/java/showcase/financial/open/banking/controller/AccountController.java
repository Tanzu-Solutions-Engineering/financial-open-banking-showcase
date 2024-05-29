package showcase.financial.open.banking.controller;

import com.vmware.financial.open.banking.account.service.AccountService;
import com.vmware.financial.open.banking.domain.account.BankAccount;
import com.vmware.financial.open.banking.domain.account.BankAccountCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/obp/v4.0.0/")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("banks/{bankId}/accounts")
    ResponseEntity<BankAccountCreateDto> createAccount(@PathVariable("bankId")String bankId , @RequestBody BankAccountCreateDto account)  {
        return ResponseEntity.ok(accountService.createAccount(bankId,account));
    }


    @GetMapping("banks/{bankId}/accounts/{accountId}/account")
    ResponseEntity<BankAccount> getAccountById(@PathVariable("bankId")String bankId, @PathVariable("accountId")String accountId) {
        var account = accountService.findAccountById(bankId,accountId);
        if(account.isPresent())
            return ResponseEntity.ok(account.get());

        return ResponseEntity.notFound().build();
    }
}
