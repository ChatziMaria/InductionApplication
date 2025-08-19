package gr.knowledge.induction.web.rest;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.service.BonusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Bonus> createBonus(@RequestBody Bonus bonus , Long id){
        Bonus createdBonus = bonusService.createBonus(bonus,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBonus);
    }

    @PutMapping
    public ResponseEntity<Bonus> updateBonus(@PathVariable Long id , @RequestBody Bonus bonus){
        Bonus updatedBonus = bonusService.updateBonus(id,bonus);
        return ResponseEntity.ok(updatedBonus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBonus(@PathVariable Long id , @RequestBody Bonus bonus){
        bonusService.deleteBonus(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Bonus>> getAllBonus(@RequestBody Bonus bonus){
        List<Bonus> gotAllBonus = bonusService.getAllBonus();
        return ResponseEntity.ok(gotAllBonus);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<Optional<Bonus>> getBonusById(@PathVariable Long id){
        Optional<Bonus> gotBonusById = bonusService.getBonusById(id);
        return ResponseEntity.ok(gotBonusById);
    }

    @GetMapping("/bonusCalculation")
    public ResponseEntity<Optional<Double>> bonusCalculation(@RequestParam String season,@RequestParam Double salary ){
        Optional<Double> gotBonusCalculation = Optional.of(bonusService.bonusCalculation(season,salary));
                return ResponseEntity.ok(gotBonusCalculation);
    }

    @PostMapping("/bonusesForCompany")
    public ResponseEntity<List<Bonus>> bonusesForCompany(@RequestParam Long companyId, String season){
        List<Bonus> bonuses = bonusService.bonusesForCompany(companyId,season);
        return ResponseEntity.status(HttpStatus.CREATED).body(bonuses);
    }
}
