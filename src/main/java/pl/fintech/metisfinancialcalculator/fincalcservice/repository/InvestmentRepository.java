package pl.fintech.metisfinancialcalculator.fincalcservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
}
