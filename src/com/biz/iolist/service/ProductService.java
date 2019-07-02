package com.biz.iolist.service;

import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.biz.iolist.config.DBConnection;
import com.biz.iolist.dao.ProductDao;
import com.biz.iolist.model.ProductVO;

/* 
 * 상품정보의 등록, 수정, 삭제 method
 */
public class ProductService {

	SqlSession sqlSession = null;
	ProductDao proDao = null;
	Scanner scan = null;

	public ProductService () {
		sqlSession = DBConnection.getSqlSessionFactory().openSession(true);
		proDao = (ProductDao) sqlSession.getMapper(ProductDao.class);
		scan = new Scanner(System.in);
	}
	
	public void viewProduct() {
		System.out.println("=====================================");
		System.out.println("우리동네 제일마트 상품 정보 ");
		System.out.println("-------------------------------------");
		System.out.println("상품코드\t상품이름\t매입단가\t매출단가\n");
		
		List<ProductVO> proList = proDao.selectAll();
			for(ProductVO vo : proList) {
				
				System.out.print(vo.getP_code()+ "\t");
				System.out.print(vo.getP_name()+ "\t");
				System.out.print(vo.getP_iprice()+ "\t");
				System.out.print(vo.getP_oprice()+ "\n");
			}
		
	}
	public boolean inserPRO() {
		System.out.print("상품코드 >> ");
		String strPcode = scan.nextLine();
		
		System.out.print("상품이름 >> ");
		String strName = scan.nextLine();
		
		int intIprice = 0;
		while(true) {
			System.out.print("매입금액 >> ");
			String strIprice = scan.nextLine();	
			try {
				intIprice = Integer.valueOf(strIprice);
			} catch (NumberFormatException e) {
				System.out.println("매입금액은 숫자로만 입력가능");
				continue;
			}
			break;
		}
		int intOprice = 0;
		while(true) {
			System.out.print("매출금액 >> ");
			String strOprice = scan.nextLine();
			try {
				intOprice = Integer.valueOf(strOprice);
			} catch (NumberFormatException e) {
				System.out.println("매출금액은 숫자로만 입력가능");
				continue;
			}
			break;
		}
		ProductVO vo = new ProductVO(strPcode,strName,intIprice,intOprice);
		
		if(proDao.insert(vo) > 0) return true;
		else return false;
		
	}
	public void updatePRO() {
		System.out.println("==============================");
		System.out.println("상품정보수정");
		System.out.println("------------------------------");
		System.out.print("수정할 상품번호 >> ");
		String strId = scan.nextLine();
		
		long io_seq = Long.valueOf(strId);
		ProductVO vo = proDao.findByCode();
		if(vo == null) {
			System.out.println("상품정보 없음");
			
		}
		
		System.out.printf("상품코드 %s>> ", vo.getP_code();
		String strCcode = scan.nextLine();
		if(strCcode.isEmpty()) strCcode = vo.getP_code();
		
		
			
		
	}
	
	public boolean deletePRO() {
		System.out.print("삭제할 거래내역 >> ");
		String strId = scan.nextLine();
		Long io_seq = Long.valueOf(strId);
		
		ProductVO vo = proDao.findByCode(io_seq);
		System.out.println();
		System.out.println("정말 삭제할까요? (YES)");
		String confirm = scan.nextLine();
		if(confirm.equals("YES")) {
			if(proDao.findByCode(p_code) > 0) return true;
			else return false;
		}
		return false;
	}
	}




