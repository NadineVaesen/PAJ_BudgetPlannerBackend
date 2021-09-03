package be.pxl.paj.budgetplanner.rest;

import be.pxl.paj.budgetplanner.dao.AccountRepository;
import be.pxl.paj.budgetplanner.dto.AccountCreateResource;
import be.pxl.paj.budgetplanner.dto.AccountDTO;
import be.pxl.paj.budgetplanner.dto.PaymentCreateResource;
import be.pxl.paj.budgetplanner.service.AccountService;
import be.pxl.paj.budgetplanner.upload.BudgetPlannerImporter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final BudgetPlannerImporter budgetPlannerImporter;
    private AccountService accountService;

    public AccountController(BudgetPlannerImporter budgetPlannerImporter, AccountService accountService) {
        this.budgetPlannerImporter = budgetPlannerImporter;
        this.accountService = accountService;
    }

    @PostMapping("/upload")
    @Async
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file){

        if (file.isEmpty()){
            return ResponseEntity.badRequest().body("Please upload a CSV file.");
        }
        if (!file.getName().endsWith(".csv")){
            return ResponseEntity.badRequest().body("Please upload a CSV file");
        }

        budgetPlannerImporter.readCsv(file);
        return ResponseEntity.ok().body("Upload succeeded");

    }

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody AccountCreateResource accountCreateResource){
        return accountService.createAccount(accountCreateResource);
    }

    @GetMapping
    public List<AccountDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PostMapping("/{id}")
    public RequestEntity<String> addPayment(@PathVariable Long id, @RequestBody PaymentCreateResource paymentCreateResource){
        return accountService.addPayment(id, paymentCreateResource);
    }

}
