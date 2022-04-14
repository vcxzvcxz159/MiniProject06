package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.impl.ProductDaoImpl;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService{
	
	/// Field
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao prodDAO;
	
	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}
	
	public void setProdDAO(ProductDao prodDAO) {
		this.prodDAO = prodDAO;
	}
	
	public PurchaseServiceImpl() {
		System.out.println("::" + getClass() + "default Constructor...");
	}
	
	/// Method
	@Override
	public int addPurchase(Purchase purchase) throws Exception {
		Product product = prodDAO.findProduct(purchase.getPurchaseProd().getProdNo());
		
		// 수량 변경
		product.setQuantity(product.getQuantity() - purchase.getQuantity());
		prodDAO.updateProduct(product);
		
		return purchaseDao.insertPurchase(purchase);
	}

	@Override
	public Purchase getPurchase(int transNo) throws Exception {		
		return purchaseDao.findPurchase(transNo);
	}
	
	@Override
	public int updatePurchase(Purchase purchase)throws Exception {
		Purchase oldPurchase = new Purchase();
		oldPurchase = purchaseDao.findPurchase(purchase.getTranNo());
		int quantityDif = purchase.getQuantity() -  oldPurchase.getQuantity();
		
		//수량 변경
		Product product = prodDAO.findProduct(purchase.getPurchaseProd().getProdNo());
		product.setQuantity(product.getQuantity() - quantityDif);
		prodDAO.updateProduct(product);
		return purchaseDao.updatePurchase(purchase);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId)throws Exception {
		return purchaseDao.getPurchaseList(search, buyerId);
	}

	@Override
	public Map<String, Object> getSaleList(Search search)throws Exception {
		return purchaseDao.getSaleList(search);
	}



	@Override
	public void UpdateTranCode(Purchase purchase)throws Exception {
		
	}
	
}
