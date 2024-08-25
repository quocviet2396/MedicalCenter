package com.medicalcenter.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medicalcenter.entities.Medicine;
import com.medicalcenter.entities.User;
@Repository
public interface MedicineRepository extends  SearchRepository<Medicine, Long>  {
	
    Medicine getMedicineById(Long id);
    

	@Query("SELECT u FROM  Medicine u WHERE CONCAT(u.id, ' ', u.genericName, ' ', u.brandName, ' ', u.measurementUnit) LIKE %?1%")
	public Page<Medicine> findAll(@Param("searchText") String searchText, Pageable pageable);
    
    void deleteById(Long id);

	void save(Optional<Medicine> medicine);
	
	
	@Query("SELECT m FROM Medicine m WHERE m.genericName LIKE %:searchText% OR m.brandName LIKE %:searchText%")
    List<Medicine> searchMedicinesByKeyword(@Param("searchText") String searchText);
 	

}
