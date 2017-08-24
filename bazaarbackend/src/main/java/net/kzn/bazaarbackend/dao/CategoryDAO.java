package net.kzn.bazaarbackend.dao;

import java.util.List;

import net.kzn.bazaarbackend.dto.Category;

public interface CategoryDAO {

	
	List<Category> list();
	Category get(int id);
	
	
}
