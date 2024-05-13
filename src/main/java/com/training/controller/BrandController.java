package com.training.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.training.entity.BrandEntity;
import com.training.model.ResponseDataModel;
import com.training.service.IBrandService;

@Controller
@RequestMapping(value = { "/brand" })
public class BrandController {

	@Autowired
	IBrandService brandService;

//	@GetMapping
//	public String initPage(Model model, @RequestParam(required = false) Integer page) {
//		model.addAttribute("responseData", brandService.findAllWithPager(page == null ? 1 : page));
//		return "brand-index";
//	}
//	
	@GetMapping
	public String initPage(Model model) {
		return "brand-index";
	}
	
	@GetMapping("/api/findAll/{pageNumber}")
	@ResponseBody
	public ResponseDataModel findAllWithPagerApi(@PathVariable("pageNumber") int pageNumber) {
		return brandService.findAllWithPagerApi(pageNumber);
	}
	@GetMapping("/api/findAll")
	@ResponseBody
	public ResponseDataModel findBrandByIdApi(@RequestParam("id") Long brandId) {
		return brandService.findBrandByIdApi(brandId);
	}
	
	@PostMapping(value="/api/add")
	@ResponseBody
	public ResponseDataModel addApi(@ModelAttribute BrandEntity brandEntity) {
		return brandService.addApi(brandEntity);
	}

	@PostMapping(value ="/api/update")
	@ResponseBody
	public ResponseDataModel updateApi(@ModelAttribute BrandEntity brandEntity) {
		return brandService.updateApi(brandEntity);
	}

	@DeleteMapping(value ="/api/delete/{brandId}")
	@ResponseBody
	public ResponseDataModel deleteApi(@PathVariable("brandId") Long brandId) {
		return brandService.deleteApi(brandId);
	}
	
	@GetMapping(value = { "/api/search/{keyword}/{pageNumber}" })
	@ResponseBody
	public ResponseDataModel searchApi(@PathVariable("keyword") String keyword,
			@PathVariable("pageNumber") int pageNumber) {
		return brandService.search(pageNumber, keyword);
	}
}