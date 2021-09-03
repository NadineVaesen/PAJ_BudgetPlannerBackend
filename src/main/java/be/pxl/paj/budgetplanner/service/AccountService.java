package be.pxl.paj.budgetplanner.service;

import be.pxl.paj.budgetplanner.dao.AccountRepository;
import be.pxl.paj.budgetplanner.dao.CategoryRepository;
import be.pxl.paj.budgetplanner.dao.PaymentRepository;
import be.pxl.paj.budgetplanner.dto.AccountCreateResource;
import be.pxl.paj.budgetplanner.dto.AccountDTO;
import be.pxl.paj.budgetplanner.dto.PaymentCreateResource;
import be.pxl.paj.budgetplanner.entity.Account;
import be.pxl.paj.budgetplanner.entity.Payment;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;

    public AccountService(AccountRepository accountRepository, PaymentRepository paymentRepository, CategoryRepository categoryRepository) {
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<String> createAccount(AccountCreateResource accountCreateResource) {
        Account account = mapResourceToAccount(accountCreateResource);
        if (accountRepository.findAccountByIban(account.getIban()).getIban().equals(account.getIban())) {
            return ResponseEntity.badRequest().body("There already exists an account with iban [" + account.getIban() + "]");
        } else {
            accountRepository.save(account);
            return ResponseEntity.ok().body("Account successfully created");
        }
    }

    private Account mapResourceToAccount(AccountCreateResource accountCreateResource) {
        Account account = new Account();
        account.setIban(accountCreateResource.getIban());
        account.setFirstName(accountCreateResource.getFirstName());
        account.setName(accountCreateResource.getName());
        return account;
    }


    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream().map(this::mapAccountToAccountDTO).collect(Collectors.toList());
    }

    private AccountDTO  mapAccountToAccountDTO(Account account) {
        return new AccountDTO(account);
    }

    public RequestEntity<String> addPayment(Long id, PaymentCreateResource paymentCreateResource) {
        Payment payment = mapToPayment(id, paymentCreateResource);
        paymentRepository.save()
    }

    private Payment mapToPayment(Long id, PaymentCreateResource paymentCreateResource) throws AccountNotFoundException {
        Payment payment = new Payment();
        payment.setAccount(accountRepository.findById(id).orElseThrow(AccountNotFoundException::new));
        payment.setCategory(categoryRepository.findCategoryByName(paymentCreateResource.getCategory()));
        payment.setDate(paymentCreateResource.getDate());
        payment.setDetail(paymentCreateResource.getDetail());
        payment.setName();
    }
}
