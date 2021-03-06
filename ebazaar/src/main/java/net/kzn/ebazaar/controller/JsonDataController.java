package net.kzn.ebazaar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kzn.bazaarbackend.dao.ProductDAO;
import net.kzn.bazaarbackend.dto.Product;

@Controller
@RequestMapping("/json/data")
public class JsonDataController {
	
	
	@Autowired
	private ProductDAO productDAO;
	
	
	@RequestMapping("/all/products")
	@ResponseBody
	public List<Product> getAllProducts() {
		
		return productDAO.listActiveProducts();
		
	}
	
	@RequestMapping("/category/{id}/products")
	@ResponseBody
	public List<Product> getAllProductsByCategory(@PathVariable int id) {
		
		return productDAO.listActiveProductsByCategory(id);
		
	}
	

}
