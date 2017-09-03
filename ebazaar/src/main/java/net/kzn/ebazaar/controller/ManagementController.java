package net.kzn.ebazaar.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.kzn.bazaarbackend.dao.CategoryDAO;
import net.kzn.bazaarbackend.dao.ProductDAO;
import net.kzn.bazaarbackend.dto.Category;
import net.kzn.bazaarbackend.dto.Product;
import net.kzn.ebazaar.util.FileUploadUtility;
import net.kzn.ebazaar.validator.ProductValidator;

@Controller
@RequestMapping("/manage")
public class ManagementController {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

	@RequestMapping(value="/products", method=RequestMethod.GET)
	public ModelAndView showManageProducts(@RequestParam(name="operation", required=false) String operation) {
		
		ModelAndView mv = new ModelAndView("page");
		
		mv.addObject("userClickManageProducts", true);
		mv.addObject("title", "Manage Products");
		Product nProduct = new Product();
		
		// set few fields
		nProduct.setSupplierId(1);
		nProduct.setActive(true);
		
		mv.addObject("product", nProduct);
		
		if(operation!=null) {
			
			if(operation.equals("product")) {
				mv.addObject("message", "Product Submitted Successfully!");
			}
		}
		
		return mv;
		
	}
	
	//handling product submission
	@RequestMapping(value="/products", method=RequestMethod.POST)
	public String handleProductSubmission(@Valid @ModelAttribute("product")Product mProduct, BindingResult results, Model model,
	HttpServletRequest request) {
		
	new ProductValidator().validate(mProduct, results);	
		
		
		
		
		// check if there are any errors
	if(results.hasErrors()) {
	
		model.addAttribute("userClickManageProducts", true);
		model.addAttribute("title", "Manage Products");
		model.addAttribute("message", "Validation failed for Product Submission!");
		
		return "page";
	}	
		
		logger.info(mProduct.toString());
		
		// Create a new product method
		productDAO.add(mProduct);
		
		if(!mProduct.getFile().getOriginalFilename().equals("")) {
			FileUploadUtility.uploadFile(request, mProduct.getFile(), mProduct.getCode());
		}
		
		
		return "redirect:/manage/products?operation=product";		
	}
	
	//Return Categories for all request mapping
	@ModelAttribute("categories")
	public List<Category> getCategories() {	
	return categoryDAO.list();
		
	}
	
	
	
}
