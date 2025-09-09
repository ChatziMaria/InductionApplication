package gr.knowledge.induction.web.rest;

import gr.knowledge.induction.domain.Bonus;
import gr.knowledge.induction.dto.BonusDTO;
import gr.knowledge.induction.enums.Seasons;
import gr.knowledge.induction.service.BonusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller για τη διαχείριση μπόνους.
 * Παρέχει endpoints για δημιουργία, ενημέρωση, διαγραφή, ανάκτηση μπόνους
 * και υπολογισμό μπόνους με βάση τη σεζόν και τον μισθό.
 */
@RestController
@RequestMapping("/api/bonus")
public class BonusController {

    private final BonusService bonusService;

    /**
     * Κατασκευαστής για την έγχυση του BonusService.
     *
     * @param bonusService η υπηρεσία που διαχειρίζεται τα μπόνους
     */
    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    /**
     * Δημιουργεί ένα νέο μπόνους.
     *
     * @param bonus το αντικείμενο DTO που περιγράφει το μπόνους
     * @return ResponseEntity με το δημιουργημένο αντικείμενο BonusDTO και HTTP status 201 (Created)
     */
    @PostMapping("/create")
    public ResponseEntity<BonusDTO> createBonus(@RequestBody BonusDTO bonus){
        BonusDTO createdBonus = bonusService.createBonus(bonus);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBonus);
    }

    /**
     * Ενημερώνει ένα υπάρχον μπόνους με βάση το ID.
     *
     * @param id    το ID του μπόνους που θα ενημερωθεί
     * @param bonus το αντικείμενο DTO με τις ενημερωμένες πληροφορίες
     * @return ResponseEntity με το ενημερωμένο αντικείμενο BonusDTO
     */
    @PutMapping("/update")
    public ResponseEntity<BonusDTO> updateBonus(@PathVariable Long id , @RequestBody BonusDTO bonus){
        BonusDTO updatedBonus = bonusService.updateBonus(id,bonus);
        return ResponseEntity.ok(updatedBonus);
    }

    /**
     * Διαγράφει ένα μπόνους με βάση το ID.
     *
     * @param id το ID του μπόνους που θα διαγραφεί
     * @return ResponseEntity με HTTP status 204 (No Content)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBonus(@PathVariable Long id){
        bonusService.deleteBonus(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Επιστρέφει όλα τα μπόνους.
     *
     * @return ResponseEntity με λίστα αντικειμένων BonusDTO
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<BonusDTO>> getAllBonus(){
        List<BonusDTO> gotAllBonus = bonusService.getAllBonus();
        return ResponseEntity.ok(gotAllBonus);
    }

    /**
     * Επιστρέφει ένα μπόνους με βάση το ID.
     *
     * @param id το ID του μπόνους
     * @return ResponseEntity με το αντικείμενο BonusDTO
     */
    @GetMapping("/getById/{id}")
    public  ResponseEntity<BonusDTO>  getBonusById(@PathVariable Long id){
        BonusDTO bonus = bonusService.getBonusById(id);
        return ResponseEntity.ok(bonus);
    }

    /**
     * Υπολογίζει το μπόνους ενός εργαζομένου με βάση τη σεζόν και τον μισθό.
     *
     * @param season η σεζόν για τον υπολογισμό του μπόνους
     * @param salary ο μισθός του εργαζομένου
     * @return ResponseEntity με το ποσό του μπόνους ως BigDecimal
     */
    @GetMapping("/bonusCalculation")
    public ResponseEntity<BigDecimal> bonusCalculation(@RequestParam Seasons season, @RequestParam BigDecimal salary ){
        BigDecimal bonus = bonusService.bonusCalculation(season,salary);
                return ResponseEntity.ok(bonus);
    }

    /**
     * Επιστρέφει όλα τα μπόνους για μια εταιρεία για συγκεκριμένη σεζόν.
     *
     * @param companyId το ID της εταιρείας
     * @param season    η σεζόν για τον υπολογισμό των μπόνους
     * @return ResponseEntity με λίστα αντικειμένων BonusDTO και HTTP status 201 (Created)
     */
    @PostMapping("/bonusesForCompany")
    public ResponseEntity<List<BonusDTO>> bonusesForCompany(@RequestParam Long companyId, Seasons season){
        List<BonusDTO> bonuses = bonusService.bonusesForCompany(companyId,season);
        return ResponseEntity.status(HttpStatus.CREATED).body(bonuses);
    }
}
