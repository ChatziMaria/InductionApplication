package gr.knowledge.induction.web.rest;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.dto.BonusDTO;
import gr.knowledge.induction.service.BonusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bonus")
public class BonusController {

    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @PostMapping
    public ResponseEntity<BonusDTO> createBonus(@RequestBody BonusDTO bonus){
        BonusDTO createdBonus = bonusService.createBonus(bonus);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBonus);
    }

    @PutMapping
    public ResponseEntity<BonusDTO> updateBonus(@PathVariable Long id , @RequestBody BonusDTO bonus){
        BonusDTO updatedBonus = bonusService.updateBonus(id,bonus);
        return ResponseEntity.ok(updatedBonus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBonus(@PathVariable Long id){
        bonusService.deleteBonus(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BonusDTO>> getAllBonus(){
        List<BonusDTO> gotAllBonus = bonusService.getAllBonus();
        return ResponseEntity.ok(gotAllBonus);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<BonusDTO>  getBonusById(@PathVariable Long id){
        BonusDTO bonus = bonusService.getBonusById(id);
        return ResponseEntity.ok(bonus);
    }

    @GetMapping("/bonusCalculation")
    public ResponseEntity<BigDecimal> bonusCalculation(@RequestParam String season,@RequestParam BigDecimal salary ){
        BigDecimal bonus = bonusService.bonusCalculation(season,salary);
                return ResponseEntity.ok(bonus);
    }

    @PostMapping("/bonusesForCompany")
    public ResponseEntity<List<BonusDTO>> bonusesForCompany(@RequestParam Long companyId, String season){
        List<BonusDTO> bonuses = bonusService.bonusesForCompany(companyId,season);
        return ResponseEntity.status(HttpStatus.CREATED).body(bonuses);
    }
}
