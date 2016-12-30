package com.facundoprecentado.repository;

import com.facundoprecentado.domain.Partner;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PartnerRepository extends CrudRepository<Partner, Long> {
    List<Partner> findById(Long id);
    Partner findByEmail(String email);
    List<Partner> findByCompanyName(String companyName);
}
