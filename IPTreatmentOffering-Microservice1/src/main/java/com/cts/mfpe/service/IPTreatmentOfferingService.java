package com.cts.mfpe.service;

import java.util.List;

import com.cts.mfpe.exception.AilmentCategoryNotFoundException;
import com.cts.mfpe.exception.IPTreatmentPackageNotFoundException;
import com.cts.mfpe.exception.SpecialistDetailNotFoundException;
import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;
import com.cts.mfpe.model.PackageDetail;
import com.cts.mfpe.model.SpecialistDetail;

public interface IPTreatmentOfferingService {
	
	List<IPTreatmentPackage> findAllIPTreatmentPackages();
	IPTreatmentPackage findIPTreatmentPackageByName(AilmentCategory ailment, String packageName) throws IPTreatmentPackageNotFoundException;
	List<SpecialistDetail> findAllSpecialists();
	List<SpecialistDetail> findAllSpecialistsByAilmentCategory(String ailment) throws SpecialistDetailNotFoundException,AilmentCategoryNotFoundException;
	SpecialistDetail addSpecialistDetail(SpecialistDetail specialistDetail);
	SpecialistDetail deleteSpecialistDetailById(int specialistId) throws SpecialistDetailNotFoundException;
	IPTreatmentPackage updateIpTreatmentPackage(int ipTreatmentPackageId,PackageDetail packageDetail) throws IPTreatmentPackageNotFoundException;

}
