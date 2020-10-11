package pl.fintech.metisfinancialcalculator.fincalcservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;

@Repository
public interface ResultRespotiory extends JpaRepository<Result, Long> {
}
