package com.cts.portal.controller;

//import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cts.portal.exception.IPTreatmentPackageNotFoundException;
import com.cts.portal.feign.IPTreatmentOfferingClient;
import com.cts.portal.model.AilmentCategory;
import com.cts.portal.model.FormInputsGetByPackageName;
import com.cts.portal.model.IPTreatmentPackage;
import com.cts.portal.model.PackageDetail;
import com.cts.portal.model.SpecialistDetail;

import feign.FeignException;

@Controller
@RequestMapping("/portal")
public class IpTreatmentOfferingController {

	private static int Myid;
	private static AilmentCategory ailment;
	@Autowired
	private IPTreatmentOfferingClient client;

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/specialists")
	public ModelAndView showSpecialistPage(HttpServletRequest request) throws Exception {
		
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * get the list of specialists using feign client of IPOfferingMicroservice
		 */
		System.out.println("Inside /specialists");
		//System.out.println("Token "+(String) request.getSession().getAttribute("Authorization"));
		List<SpecialistDetail> specialists = client
				.getAllSpecialist((String) request.getSession().getAttribute("Authorization"));
		ModelAndView model = new ModelAndView("user-view-list-of-specialist-page");
		model.addObject("specialists", specialists);
		return model;
	}

	/**
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/ipTreatmentPackages")
	public ModelAndView showIPTreatmentPackages(Model model, HttpServletRequest request) throws Exception {
		System.out.println("Inside IP Treatment Packages");
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		List<IPTreatmentPackage> packageDetails = client
				.getAllIPTreatmentPackage((String) request.getSession().getAttribute("Authorization"));
		ModelAndView modelAndView = new ModelAndView("user-view-package-detail-page");
		modelAndView.addObject("ipTreatmentPackagekageName", packageDetails);
		return modelAndView;
	}

	/**
	 * @param formInputsGetByPackageName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/ipTreatmentPackageByName")
	public ModelAndView showIPTreatmentPackageByName2(
			@ModelAttribute("formInputsGetByPackageName") FormInputsGetByPackageName formInputsGetByPackageName,
			HttpServletRequest request) throws Exception {
		
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, 
		 * then allow access to view
		 */
		ModelAndView model = new ModelAndView("user-package-detail-by-name-page");
		if (formInputsGetByPackageName != null && formInputsGetByPackageName.getAilment() != null
				&& formInputsGetByPackageName.getPackageName() != null) {
			try {
				/*
				 * get the package details by Name 
				 * using feign client of IPOfferingMicroservice
				 */
				IPTreatmentPackage ipTreatmentPackagekageName = client.getIPTreatmentPackageByName(
						formInputsGetByPackageName.getAilment(),
						formInputsGetByPackageName.getPackageName(),
						(String) request.getSession().getAttribute("Authorization"));
				model.addObject("ipTreatmentPackagekageName", ipTreatmentPackagekageName);
			} catch (IPTreatmentPackageNotFoundException e) {
				model.addObject("error", e.getMessage());
			}
		}
		return model;
	}
	
	/*@GetMapping("/showaddSpecialists")
	public String showAddSpecialist(@ModelAttribute("specialistDetails") SpecialistDetail specialistDetails) {
		return "admin-add-specialist";
	}*/
	
	@GetMapping("/addSpecialist")

	public String addSpecialists(@ModelAttribute("specialistDetails") SpecialistDetail specialistDetail,Model model,HttpServletRequest request) throws Exception {
		
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			return "error-page401";
		}
		
		if(specialistDetail != null && specialistDetail.getName() != null && specialistDetail.getAreaOfExpertise()!=null) {
			
			try {
				model.addAttribute("check", 1);
				client.addNewSpecialistDetail( (String) request.getSession().getAttribute("Authorization"), specialistDetail);
				
			}
			catch(FeignException exx) {
				exx.printStackTrace();
				model.addAttribute("error","Connection exception. Try Again!");
			}
			catch(Exception e) {
				e.printStackTrace();
				model.addAttribute("error","My Exception");
			}
		}
		else {
			model.addAttribute("check", 0);
		}
		return "admin-add-specialist";
	}
	
	@GetMapping("/showDeleteSpecialist")
	public ModelAndView showDeleteMapping(HttpServletRequest request) throws Exception {
		
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * get the list of specialists using feign client of IPOfferingMicroservice
		 */
		System.out.println("Inside /specialists");
		List<SpecialistDetail> specialists = client
				.getAllSpecialist((String) request.getSession().getAttribute("Authorization"));
		ModelAndView model = new ModelAndView("admin-delete-specialist-page");
		model.addObject("specialists", specialists);
		return model;
	}
	
	@GetMapping("/deleteSpecialist")
	public ModelAndView deleteSpecialist(HttpServletRequest request,@RequestParam int specialistId) throws Exception{
		System.out.println("In Delete Mapping");
		
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		ModelAndView model = new ModelAndView("admin-delete-specialist-page");
		try {
			System.out.println("In Delete Mapping");
			client.deleteSpecialist(specialistId, (String) request.getSession().getAttribute("Authorization"));
			List<SpecialistDetail> specialists = client
					.getAllSpecialist((String) request.getSession().getAttribute("Authorization"));
			//ModelAndView model = new ModelAndView("admin-delete-specialist-page");
			model.addObject("specialists", specialists);

		}
		catch(FeignException exx) {
			exx.printStackTrace();
			model.addObject("error","Connection exception. Try Again!");
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addObject("error","My Exception");
		}
		
		/*List<SpecialistDetail> specialists = client
				.getAllSpecialist((String) request.getSession().getAttribute("Authorization"));
		ModelAndView model = new ModelAndView("admin-delete-specialist-page");
		model.addObject("specialists", specialists);*/
		return model;
	}
	
	@GetMapping("/showSpecialistByExpertise")
	public String showSpecialistByExpertise(@ModelAttribute("specialist") SpecialistDetail specialistDetail) {
		return "admin-show-specialist-by-expertise";
	}
	
	@GetMapping("/specialistByExpertise")
	public ModelAndView specialistByExpertise(HttpServletRequest request,@ModelAttribute("specialist") SpecialistDetail specialistDetail) throws Exception{
		
		
		System.out.println("In SpecialistByExpertise");
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		ModelAndView model=new ModelAndView();
		try {
			//String ailment=request.getParameter("ailment");
			List<SpecialistDetail> list=client.getAllSpecialistByAilmentCategory(specialistDetail.getAreaOfExpertise(), (String) request.getSession().getAttribute("Authorization"));
			model.setViewName("user-view-list-of-specialist-page");
			model.addObject("specialists", list);
		}
		catch(FeignException exx) {
			exx.printStackTrace();
			model.addObject("error","Connection exception. Try Again!");
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addObject("error","My Exception");
		}
		
		return model;
	}
	
	@GetMapping("/showUpdatePackage")
	public String showUpdatePackage(@ModelAttribute("formInputsGetByPackageName") FormInputsGetByPackageName formInputsGetByPackageName) {
		return "admin-show-update-package-detail-by-name-page";
	}
	
	@GetMapping("/updatePackage")
	public ModelAndView updatePackage(@ModelAttribute("formInputsGetByPackageName") FormInputsGetByPackageName formInputsGetByPackageName,
			@ModelAttribute("packageDetails") PackageDetail packageDetail,
			HttpServletRequest request) throws Exception {
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, 
		 * then allow access to view
		 */
		ModelAndView model = new ModelAndView("admin-update-package-by-name");
		if (formInputsGetByPackageName != null && formInputsGetByPackageName.getAilment() != null
				&& formInputsGetByPackageName.getPackageName() != null) {
			try {
				/*
				 * get the package details by Name 
				 * using feign client of IPOfferingMicroservice
				 */
				ailment=formInputsGetByPackageName.getAilment();
				IPTreatmentPackage ipTreatmentPackagekageName = client.getIPTreatmentPackageByName(
						ailment,
						formInputsGetByPackageName.getPackageName(),
						(String) request.getSession().getAttribute("Authorization"));
				Myid=ipTreatmentPackagekageName.getTreatmentPackageId();
				//System.out.println("Package Name "+packageDetail.getTreatmentPackageName());
					packageDetail.setTestDetails(ipTreatmentPackagekageName.getPackageDetail().getTestDetails());
					packageDetail.setCost(ipTreatmentPackagekageName.getPackageDetail().getCost());
					packageDetail.setTreatmentDuration(ipTreatmentPackagekageName.getPackageDetail().getTreatmentDuration());
					packageDetail.setTreatmentPackageName(ipTreatmentPackagekageName.getPackageDetail().getTreatmentPackageName());
					model.addObject("AilmentCategory",ailment);
					//model.addObject("Package Name", formInputsGetByPackageName.getPackageName());
			
			} 
			catch (IPTreatmentPackageNotFoundException e) {
				model.addObject("error", e.getMessage());
			}
			catch(FeignException exx) {
				exx.printStackTrace();
				model.addObject("error","Connection exception. Try Again!");
			}
		}
		else {
			try{
				client.updatePackage(Myid,(String) request.getSession().getAttribute("Authorization") , packageDetail);
				packageDetail=null;
				model.addObject("AilmentCategory",ailment);
				//model.setViewName("user-view-package-detail-page");
			}
			catch (IPTreatmentPackageNotFoundException e) {
				model.addObject("error", e.getMessage());
			}
			catch(FeignException exx) {
				exx.printStackTrace();
				model.addObject("error","Connection exception. Try Again!");
			}
			
			
		}
		return model;
	}
	
	@ModelAttribute("ailmentList")
	public Set<String> populateAilmentEnumList() {
		return EnumSet.allOf(com.cts.portal.model.AilmentCategory.class).stream().map(a -> a.name())
				.collect(Collectors.toSet());

	}

	@ModelAttribute("packageList")
	public List<String> populatePackageList() {
		return Arrays.asList("Package 1", "Package 2");

	}
}