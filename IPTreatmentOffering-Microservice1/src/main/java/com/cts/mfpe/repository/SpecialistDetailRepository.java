package com.cts.mfpe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.SpecialistDetail;

@Repository
public interface SpecialistDetailRepository extends JpaRepository<SpecialistDetail, Integer> {
	
	public List<SpecialistDetail> findByAreaOfExpertise(AilmentCategory ailment);

}
