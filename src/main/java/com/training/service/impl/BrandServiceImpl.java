package com.training.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.training.common.constant.Constants;
import com.training.common.util.FileHelper;
import com.training.dao.IBrandDao;
import com.training.entity.BrandEntity;
import com.training.model.PagerModel;
import com.training.model.ResponseDataModel;
import com.training.service.IBrandService;

@Service
@Transactional
public class BrandServiceImpl implements IBrandService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Value("${parent.folder.images.brand}")
	private String brandLogoFolderPath;

	@Autowired
	IBrandDao brandDao;

	@Override
	public BrandEntity add(BrandEntity brandEntity) {

		try {
			String imagePath = FileHelper.addNewFile(brandLogoFolderPath, brandEntity.getLogoFiles());
			brandEntity.setLogo(imagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return brandDao.saveAndFlush(brandEntity);
	}

	@Override
	public BrandEntity update(BrandEntity brandEntity) {

		try {
			if (brandEntity.getLogoFiles()[0].getSize() > 0) {

				String imagePath = FileHelper.editFile(brandLogoFolderPath, brandEntity.getLogoFiles(),
						brandEntity.getLogo());
				brandEntity.setLogo(imagePath);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return brandDao.saveAndFlush(brandEntity);
	}

	@Override
	public ResponseDataModel delete(Long brandId) {

		BrandEntity brandEntity = brandDao.findByBrandId(brandId);
		if (brandEntity != null) {
			brandDao.deleteById(brandId);
			brandDao.flush();

			try {
				// Remove logo of brand from store folder
				FileHelper.deleteFile(brandEntity.getLogo());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public BrandEntity findByBrandId(Long brandId) {
		return brandDao.findByBrandId(brandId);
	}

	@Override
	public List<BrandEntity> getAll() {
		return brandDao.findAll(Sort.by(Sort.Direction.DESC, "brandId"));
	}


	@Override
	public Map<String, Object> findAllWithPager(int pageNumber) {
		Map<String, Object> responseMap = new HashMap<>();
		Sort sortInfo = Sort.by(Sort.Direction.DESC, "brandId");
		Pageable pageable = PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sortInfo);
		Page<BrandEntity> brandEntitiesPage = brandDao.findAll(pageable);
		responseMap.put("brandsList", brandEntitiesPage.getContent());
		responseMap.put("paginationInfo", new PagerModel(pageNumber, brandEntitiesPage.getTotalPages()));
		return responseMap;
	}

	@Override
	public ResponseDataModel addApi(BrandEntity brandEntity) {
		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		
		try {
			if (findByBrandName(brandEntity.getBrandName()) != null) {
				responseMsg = "Brand Name is duplicated";
				responseCode = Constants.RESULT_CD_DUPL;
		} else {
				MultipartFile[] logoFiles = brandEntity.getLogoFiles();
				if (logoFiles != null && logoFiles[0].getSize() > 0) {

					String imagePath = FileHelper.addNewFile(brandLogoFolderPath, logoFiles);
					brandEntity.setLogo(imagePath);
				}
				brandDao.saveAndFlush(brandEntity);
				responseMsg = "Brand is added successfully";
				responseCode = Constants.RESULT_CD_SUCCESS;
			}
		} catch (IOException e) {
			responseMsg = "Error when adding brand";
			LOGGER.error("Error when get all brand: ", e);
		}
		return new ResponseDataModel(responseCode, responseMsg);
	}

	@Override
	public ResponseDataModel findAllWithPagerApi(int pageNumber) {
		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		Map<String, Object> responseMap = new HashMap<>();
		try {
			Sort sortInfo = Sort.by(Sort.Direction.ASC, "brandName");
			Pageable pageable = PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sortInfo);
			Page<BrandEntity> brandEntitiesPage = brandDao.findAll(pageable);
			responseMap.put("brandsList", brandEntitiesPage.getContent());
			responseMap.put("paginationInfo", new PagerModel(pageNumber, brandEntitiesPage.getTotalPages()));
			responseMap.put("totalItem", brandEntitiesPage.getTotalElements());
			responseCode = Constants.RESULT_CD_SUCCESS;
		} catch (Exception e) {
			responseMsg = e.getMessage();
			LOGGER.error("Error when get all brand: ", e);
		}
		return new ResponseDataModel(responseCode, responseMsg, responseMap);
	}

	@Override
	public ResponseDataModel findBrandByIdApi(Long brandId) {

		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		BrandEntity brandEntity = null;
		try {
			brandEntity = brandDao.findByBrandId(brandId);
			if (brandEntity != null) {
				responseCode = Constants.RESULT_CD_SUCCESS;
			}
		} catch (Exception e) {
			responseMsg = "Error when finding brand by ID";
			LOGGER.error("Error when finding brand by ID: ", e);
		}
		return new ResponseDataModel(responseCode, responseMsg, brandEntity);
	}

	@Override
	public ResponseDataModel updateApi(BrandEntity brandEntity) {
		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		try {
			BrandEntity duplicatedBrand = brandDao.findByBrandNameAndBrandIdNot(brandEntity.getBrandName(),
					brandEntity.getBrandId());
			if (duplicatedBrand != null) {
				responseMsg = "Brand Name is duplicated";
				responseCode = Constants.RESULT_CD_DUPL;
			} else {
				MultipartFile[] logoFiles = brandEntity.getLogoFiles();
				if (logoFiles != null && logoFiles[0].getSize() > 0) {
					String imagePath = FileHelper.editFile(brandLogoFolderPath, logoFiles, brandEntity.getLogo());
					brandEntity.setLogo(imagePath);
				}
				brandDao.saveAndFlush(brandEntity);
				responseMsg = "Brand is updated successfully";
				responseCode = Constants.RESULT_CD_SUCCESS;
			}
		} catch (Exception e) {
			responseMsg = "Error when updating brand";
			LOGGER.error("Errorr when updating brand: ", e);
		}
		return new ResponseDataModel(responseCode, responseMsg);
	}

	@Override
	public ResponseDataModel deleteApi(Long brandId) {
		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		BrandEntity brandEntity = brandDao.findByBrandId(brandId);
		try {
			if (brandEntity != null) {
				brandDao.deleteById(brandId);
				brandDao.flush();
				FileHelper.deleteFile(brandEntity.getLogo());
				responseMsg = "Brand is deleted successfully";
				responseCode = Constants.RESULT_CD_SUCCESS;
			}
		} catch (Exception e) {
			responseMsg = "Error when deleting brand";
			LOGGER.error("Error when delete brand: ", e);
		}
		return new ResponseDataModel(responseCode, responseMsg);
	
	}
	
	@Override
	public BrandEntity findByBrandName(String brandName) {
		// TODO Auto-generated method stub
		return brandDao.findByBrandName(brandName);
	}

	@Override
	public ResponseDataModel search(int pageNumber, String keyword) {
		int responseCode = Constants.RESULT_CD_FAIL;
		String responseMsg = StringUtils.EMPTY;
		
		//List<BrandEntity> listBrand = brandDao.findByBrandNameLike("%" + keyword + "%" );
		Map<String, Object> rpMap = new HashMap<>();
		try {
			Sort sort = Sort.by(Direction.ASC, "brandName");
			Pageable pageable = PageRequest.of(pageNumber -1, Constants.PAGE_SIZE,sort);
			Page<BrandEntity> brandEntitiesPage = brandDao.findByBrandNameLike("%" + keyword + "%",pageable);
			rpMap.put("brandsList", brandEntitiesPage.getContent());
			rpMap.put("paginationInfo", new PagerModel(pageNumber, brandEntitiesPage.getTotalPages()));
			rpMap.put("totalItem", brandEntitiesPage.getTotalElements());
			responseCode = Constants.RESULT_CD_SUCCESS;
			responseMsg = "ResultSet has " + brandEntitiesPage.getTotalElements() + " products";
		} catch (Exception e) {
			responseMsg = e.getMessage();
			LOGGER.error("Error when get all product: ", e);
		}
		return new ResponseDataModel(responseCode, responseMsg, rpMap);
	}
}