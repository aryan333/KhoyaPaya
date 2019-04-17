package com.javatechie.spring.orm.api.controller.web;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.Session;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.javatechie.spring.orm.api.dto.ProductFoundDTO;
import com.javatechie.spring.orm.api.dto.ProductTypeDTO;
import com.javatechie.spring.orm.api.dto.StateDTO;
import com.javatechie.spring.orm.api.dto.UserDto;
import com.javatechie.spring.orm.api.entity.ProductFoundEntity;
import com.javatechie.spring.orm.api.model.ProductFound;
import com.javatechie.spring.orm.api.model.ProductType;
import com.javatechie.spring.orm.api.model.State;
import com.javatechie.spring.orm.api.model.User;
import com.javatechie.spring.orm.api.service.PersonService;

import ch.qos.logback.classic.Logger;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	PersonService service;

	@Autowired
	MessageSource message;

	String errorMessage = "";

	@RequestMapping("welcome")

	public ModelAndView home() {

		System.out.println("Come in");
		return new ModelAndView("home");
	}

	@RequestMapping("/register")

	public String register(ModelMap map) {

		System.out.println("Register in");
		map.addAttribute("paramsNotValid", errorMessage);
		errorMessage = "";
		return "register";
	}

	@RequestMapping("/dashboard")

	public ModelAndView dashboard(HttpSession session) {
		
		System.out.println("on Dashboard");

		System.out.println("yaha pai"+session.getAttribute("User"));

		User user = (User) session.getAttribute("User");
		System.out.println("Mobile pai"+user.getMobile());
		User user1=new User();
		UserDto dto=service.getUserData(user.getMobile());
		BeanUtils.copyProperties(dto, user1);
		session.setAttribute("User",user1);
		System.out.println(user1.getName());
		return new ModelAndView("dashboard").addObject("data", user1);
	}

	@PostMapping("/addUser")

	public String addUser(@Valid @ModelAttribute("User") User user, BindingResult bindingResult, ModelMap map,HttpSession session)

			throws Exception {

		if (bindingResult.hasErrors()) {

			FieldError error = bindingResult.getFieldError();
			errorMessage = message.getMessage(error, null);
			// map.addAttribute("paramsNotValid",s);
			return "redirect:/user/register";
		}
        
		session.setAttribute("User", user);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		service.signUp(userDto);
		return "redirect:/user/dashboard";
	}

	@RequestMapping("/loginform")

	public String loginform(ModelMap map) {

		map.addAttribute("paramsNotValid", errorMessage);
		errorMessage = "";
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@Valid @ModelAttribute("User") User user, BindingResult bindingResult,
			HttpSession session) {

		if (bindingResult.hasErrors()) {

			System.out.println("loginform");
			FieldError error = bindingResult.getFieldError();
			errorMessage = message.getMessage(error, null);
			// map.addAttribute("paramsNotValid",s);
			return "redirect:/user/loginform";
		}

		session.setAttribute("User", user);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		String str = service.login(user.getMobile(), user.getPassword());

		System.out.println(str);
		if (str.equalsIgnoreCase("success")) {
			return "redirect:/user/dashboard";
		}

		else {

			return "redirect:/user/loginform";
		}

	}
	
	@GetMapping("/loginpage")
	public String getLoginPage() {
		return "login";
	}
	
	@PostMapping("/processlogin")
	public ModelAndView validateUser() {
		return new ModelAndView("dashboard");
	}
	
	@RequestMapping(path="/formdata")
	public ModelAndView khoyaform(HttpSession session){
		
		User user=(User) session.getAttribute("User");
		List<ProductTypeDTO> productdto=service.getProductType();
		List<StateDTO> statedto=service.getState();
		for(StateDTO dto2:statedto){
		System.out.println("in Controller"+dto2.getStateName());
		}
		
		return new ModelAndView("khoyaform").addObject("data",user).addObject("stateData",statedto).addObject("productTypedata",productdto);
		
	}
	
	@GetMapping(path="/statedata")
	public @ResponseBody Map<String, List<StateDTO>> stateData(){
		
		List<StateDTO> stateDTOs=service.getState();
		Map<String,List<StateDTO>> response=new HashMap<>();
		response.put("statedata", stateDTOs);
		return response;
		
	}
	
	@PostMapping(path="/productfound")
	public @ResponseBody Map<String,Object> productFound(ProductFound productFound,HttpSession session){
		
		Map<String,Object> response=new HashMap<>();
		
		 User user1=(User) session.getAttribute("User");
		 productFound.setUser(user1);
		
		 ProductFoundDTO productFoundDTO=new ProductFoundDTO();
		 ProductTypeDTO productTypeDTO=new ProductTypeDTO();
		 StateDTO stateDTO=new StateDTO();
		 UserDto userDto=new UserDto();
		 
		 State state=productFound.getState();
		 BeanUtils.copyProperties(state, stateDTO);
		 
		 User user=productFound.getUser();
		 BeanUtils.copyProperties(user, userDto);
		 
		 
		 ProductType productType=productFound.getProductType();
		 BeanUtils.copyProperties(productType, productTypeDTO);
		 
		 BeanUtils.copyProperties(productFound, productFoundDTO);
		 productFoundDTO.setProductTypeDTO(productTypeDTO);
		 productFoundDTO.setStateDTO(stateDTO);
		 productFoundDTO.setUserDto(userDto);
		 
		try{
		 List<ProductFoundDTO> productFoundlist=service.saveProductFound(productFoundDTO);
		 response.put("response",HttpStatus.OK);
		 return response; 
		}catch(Exception e){
			
			response.put("response", HttpStatus.BAD_REQUEST);
			return response;
		}
		
	}
	
	@GetMapping(path="/productfoundlist")
	public @ResponseBody List<ProductFound> productfoundList(){
		
		List<ProductFound> productfoundlist=service.productFoundList();
		return productfoundlist;
		
	}
	
	
	@RequestMapping(path="/masterlocation")
	public ModelAndView productMaster() {
		
		return new ModelAndView("masterLocation");
	}
	
	
}
