package com.cts.portal.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.portal.exception.AilmentCategoryNotFoundException;
//import com.cts.mfpe.exception.AilmentCategoryNotFoundException;
import com.cts.portal.exception.AuthorizationException;
import com.cts.portal.exception.IPTreatmentPackageNotFoundException;
import com.cts.portal.exception.SpecialistDetailNotFoundException;
import com.cts.portal.model.AilmentCategory;
import com.cts.portal.model.IPTreatmentPackage;
import com.cts.portal.model.PackageDetail;
import com.cts.portal.model.SpecialistDetail;

//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@FeignClient(name = "IPTreatmentOffering-service", url = "${ipoffering.URL}")
public interface IPTreatmentOfferingClient {
    
	@GetMapping("/ipTreatmentPackages")
	public List<IPTreatmentPackage> getAllIPTreatmentPackage(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException;
	
	@GetMapping("/ipTreatmentPackageByName/{ailment}/{packageName}")
	public IPTreatmentPackage getIPTreatmentPackageByName(
			@ApiParam(name = "ailment", value = "ailment of the package") @PathVariable AilmentCategory ailment,
			@ApiParam(name = "packageName", value = "name of the package") @PathVariable String packageName,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, IPTreatmentPackageNotFoundException;
	
	@GetMapping("/specialists")
	public List<SpecialistDetail> getAllSpecialist(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException;
	
//	http://localhost:8100/ipTreatmentOffering/addSpecialist
	@PostMapping("/addSpecialist")
	public ResponseEntity<String> addNewSpecialistDetail(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody SpecialistDetail specialistDetail) throws AuthorizationException;

	@DeleteMapping("/deleteSpecialist/{id}")

	public ResponseEntity<String> deleteSpecialist(
			@ApiParam(name = "specialistId", value = "id of specialist") @PathVariable int id,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)throws AuthorizationException,SpecialistDetailNotFoundException;

	@GetMapping("/specialistsByExpertsise/{ailment}")
	public List<SpecialistDetail> getAllSpecialistByAilmentCategory(
			@ApiParam(name = "ailment", value = "are of expertise") @PathVariable String ailment,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
					throws AuthorizationException, SpecialistDetailNotFoundException,AilmentCategoryNotFoundException;
	
	@PutMapping("/updatePackage/{id}")
	public ResponseEntity<String> updatePackage(
			@ApiParam(name = "IPTreatmentPackage id", value = "Id of IPTreatment Package") @PathVariable int id,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody PackageDetail packageDetail) throws IPTreatmentPackageNotFoundException, AuthorizationException;
}