package com.saifintex.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.saifintex.common.AbstractBase;
import com.saifintex.domain.DisputeReasons;
import com.saifintex.domain.FiscalYear;
import com.saifintex.domain.ItemCategory;
import com.saifintex.domain.Relations;
import com.saifintex.domain.RewardPoints;
import com.saifintex.domain.Roles;
import com.saifintex.dto.DisputeReasonsDTO;
import com.saifintex.dto.FiscalYearDTO;
import com.saifintex.dto.ItemCategoryDto;
import com.saifintex.dto.RelationsDTO;
import com.saifintex.dto.RewardPointsDTO;
import com.saifintex.dto.RolesDTO;
import com.saifintex.services.DisputeReasonsService;
import com.saifintex.services.FiscalYearService;
import com.saifintex.services.ItemCategoryService;
import com.saifintex.services.RelationsService;
import com.saifintex.services.RewardPointsService;
import com.saifintex.services.RolesService;

@Controller
@RequestMapping("/admin")

public class MastertableController extends AbstractBase {

	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private RelationsService relationsService;

	@Autowired
	private DisputeReasonsService disputeReasonsService;

	@Autowired
	private ItemCategoryService ItemCategoryService;

	@Autowired
	private FiscalYearService fiscalYearService;
	
	@Autowired
	private RewardPointsService rewardPointsService;

	@RequestMapping(value = "/mastertable")
	public ModelAndView viewMasterTable() {
		List<Roles> roleList = getRoles();
		ModelAndView model = new ModelAndView("master-table");
		model.addObject("roles", roleList);
		return model;
	}

	@RequestMapping(value = "/mastertable/roles")
	public @ResponseBody ResponseEntity<?> viewRolesMasterTable() {
		List<Roles> roleList = getRoles();
		Map<String, List<Roles>> responseMap = new HashMap<String, List<Roles>>();
		responseMap.put("data", roleList);
		return new ResponseEntity<Map<String, List<Roles>>>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/mastertable/disputeReasons")
	public @ResponseBody ResponseEntity<?> viewDisputeReasonTable() {
		List<DisputeReasonsDTO> list = disputeReasonsService.findAll();
		List<DisputeReasons> disputeReasonslist = (List<DisputeReasons>) (List<?>) copyList(list, DisputeReasons.class);
		Map<String, List<DisputeReasons>> responseMap = new HashMap<String, List<DisputeReasons>>();
		responseMap.put("data", disputeReasonslist);
		return new ResponseEntity<Map<String, List<DisputeReasons>>>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/mastertable/itemCategory")
	public @ResponseBody ResponseEntity<?> viewItemCategoryTable() {
		List<ItemCategoryDto> list = ItemCategoryService.getItemCategoryList();
		List<ItemCategory> itemCategorylist = (List<ItemCategory>) (List<?>) copyList(list, ItemCategory.class);
		Map<String, List<ItemCategory>> responseMap = new HashMap<String, List<ItemCategory>>();
		responseMap.put("data", itemCategorylist);
		return new ResponseEntity<Map<String, List<ItemCategory>>>(responseMap, HttpStatus.OK);

	}

	@RequestMapping(value = "/mastertable/fiscalYear")
	public @ResponseBody ResponseEntity<?> viewFicalYearTable() {
		List<FiscalYearDTO> list = fiscalYearService.findAll();
		List<FiscalYear> fiscalYearlist = (List<FiscalYear>) (List<?>) copyList(list, FiscalYear.class);
		Map<String, List<FiscalYear>> responseMap = new HashMap<String, List<FiscalYear>>();
		responseMap.put("data", fiscalYearlist);
		return new ResponseEntity<Map<String, List<FiscalYear>>>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/mastertable/relations")
	public @ResponseBody ResponseEntity<?> viewRelationsMasterTable() {		
		List<RelationsDTO> list = relationsService.findAll();
		@SuppressWarnings("unchecked")
		List<Relations> relationsList = (List<Relations>) (List<?>) copyList(list, Relations.class);
		Map<String, List<Relations>> responseMap = new HashMap<String, List<Relations>>();
		responseMap.put("data", relationsList);
		return new ResponseEntity<Map<String, List<Relations>>>(responseMap, HttpStatus.OK);
	}

	private List<Roles> getRoles() {
		List<RolesDTO> list = rolesService.findAll();
		@SuppressWarnings("unchecked")
		List<Roles> roleList = (List<Roles>) (List<?>) copyList(list, Roles.class);
		return roleList;
	}
	
	@RequestMapping(value = "/mastertable/rewardPoints")
	public @ResponseBody ResponseEntity<?> viewRewardPointsMasterTable() {		
		List<RewardPointsDTO> rewardPointsDTOs = rewardPointsService.getAll();
		@SuppressWarnings("unchecked")
		List<RewardPoints> rewardPointsList = (List<RewardPoints>) (List<?>) copyList(rewardPointsDTOs, RewardPoints.class);
		Map<String, List<RewardPoints>> responseMap = new HashMap<String, List<RewardPoints>>();
		responseMap.put("data", rewardPointsList);
		return new ResponseEntity<Map<String, List<RewardPoints>>>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/mastertable/addRole",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, Roles>> addRoleHandler(@RequestBody Roles roles) {		
		RolesDTO dto = new RolesDTO();
		BeanUtils.copyProperties(roles, dto);		
		rolesService.addRole(dto, getWebUser());
		BeanUtils.copyProperties(rolesService.addRole(dto, getWebUser()), roles);
		Map<String, Roles> responseMap = new HashMap<String, Roles>();
		responseMap.put("data", roles);
		return new ResponseEntity<Map<String, Roles>>(responseMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mastertable/addRewardPoint",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, RewardPoints>> addRewardPointHandler(@RequestBody RewardPoints rewardPoints) {		
		RewardPointsDTO dto = new RewardPointsDTO();
		BeanUtils.copyProperties(rewardPoints, dto);		
		BeanUtils.copyProperties(rewardPointsService.addRewardPoint(dto, getWebUser()), rewardPoints);
		Map<String, RewardPoints> responseMap = new HashMap<String, RewardPoints>();
		responseMap.put("data", rewardPoints);
		return new ResponseEntity<Map<String, RewardPoints>>(responseMap,HttpStatus.OK);
	}

	@RequestMapping(value = "/mastertable/addDisputeReason",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, DisputeReasons>> addDisputeReasonHandler(@RequestBody DisputeReasons disputeReasons) {		
		DisputeReasonsDTO dto = new DisputeReasonsDTO();
		BeanUtils.copyProperties(disputeReasons, dto);		
		BeanUtils.copyProperties(disputeReasonsService.addDisputeReason(dto, getWebUser()), disputeReasons);
		Map<String, DisputeReasons> responseMap = new HashMap<String, DisputeReasons>();
		responseMap.put("data", disputeReasons);
		return new ResponseEntity<Map<String, DisputeReasons>>(responseMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mastertable/addItemCategory",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, ItemCategory>> addItemCategoryHandler(@RequestBody ItemCategory itemCategory) {		
		ItemCategoryDto dto = new ItemCategoryDto();
		BeanUtils.copyProperties(itemCategory, dto);		
		BeanUtils.copyProperties(ItemCategoryService.addItemCategory(dto, getWebUser()),itemCategory);
		Map<String, ItemCategory> responseMap = new HashMap<String, ItemCategory>();
		responseMap.put("data", itemCategory);
		return new ResponseEntity<Map<String, ItemCategory>>(responseMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mastertable/addFiscalYear",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, FiscalYear>> addFiscalYearHandler(@RequestBody FiscalYear fiscalYear) {		
		FiscalYearDTO dto = new FiscalYearDTO();
		BeanUtils.copyProperties(fiscalYear, dto);		
		BeanUtils.copyProperties(fiscalYearService.addFiscalYear(dto, getWebUser()),fiscalYear);
		Map<String, FiscalYear> responseMap = new HashMap<String, FiscalYear>();
		responseMap.put("data", fiscalYear);
		return new ResponseEntity<Map<String, FiscalYear>>(responseMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mastertable/addRelation",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, Relations>> addRelationHandler(@RequestBody Relations relations) {		
		RelationsDTO dto = new RelationsDTO();
		BeanUtils.copyProperties(relations, dto);		
		BeanUtils.copyProperties(relationsService.addRelation(dto, getWebUser()),relations);
		Map<String, Relations> responseMap = new HashMap<String, Relations>();
		responseMap.put("data", relations);
		return new ResponseEntity<Map<String, Relations>>(responseMap,HttpStatus.OK);
	}
}
