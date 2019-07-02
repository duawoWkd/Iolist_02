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
	public boolean updatePRO() {
		while (true) {
			System.out.println("===================================");
			System.out.println("상품정보 변경");
			System.out.println("-----------------------------------");
			System.out.print("상품코드 입력 >>");
			String strPcode = scan.nextLine();
			ProductVO vo = proDao.findByCode(strPcode);
			if (vo == null) {
				System.out.println("상품정보가 없습니다.");
				continue;
			}
			System.out.printf("상품이름 입력 %s>>", vo.getP_name());
			String strName = scan.nextLine();
			if (strName.isEmpty())
				strName = vo.getP_name();

			System.out.printf("매입금액 입력 %d>>", vo.getP_iprice());
			String strIprice = scan.nextLine();
			int intIprice = 0;
			if (strIprice.isEmpty())
				intIprice = vo.getP_iprice();
			else
				intIprice = Integer.valueOf(strIprice);
			System.out.printf("매출금액 입력 %d>>", vo.getP_oprice());
			String strOprice = scan.nextLine();
			int intOprice = 0;
			if (strOprice.isEmpty())
				intOprice = vo.getP_oprice();
			else
				intOprice = Integer.valueOf(intOprice);

			vo.setP_code(strPcode);
			vo.setP_name(strName);
			vo.setP_iprice(intIprice);
			vo.setP_oprice(intIprice);

			if (proDao.update(vo) > 0) {
				System.out.println("업데이트 완료");
				return true;
			} else {
				System.out.println("업데이트 실패");
				return false;
			}
		}

	}
	
	public boolean deletePRO() {
		System.out.print("삭제할 거래내역 >> ");
		String strPcode = scan.nextLine();
	
		ProductVO vo = proDao.findByCode(strPcode);
		System.out.println(vo);
		System.out.println("정말 삭제할까요? (YES)");
		String confirm = scan.nextLine();
		if(confirm.equals("YES")) {
			if(proDao.delete(strPcode) > 0) {
				System.out.println("삭제되었습니다");
				return true;
			}
			else return false;
		}
		return false;
	}
	
	}




