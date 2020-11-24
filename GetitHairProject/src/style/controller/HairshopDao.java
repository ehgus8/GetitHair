package style.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;

public class HairshopDao {

	public ArrayList<Hairshop> selectHairshop(Connection conn) {
		PreparedStatement ps = null;
		ResultSet rset = null;
		String query = "select shop_no,shop_name,shop_addr,shop_open,shop_close,shop_rate,shop_img,shop_likes from hairshop";
		ArrayList<Hairshop> list = new ArrayList<Hairshop>();
		try {
			ps = conn.prepareStatement(query);
			rset = ps.executeQuery();
			while(rset.next()) {
				Hairshop h = new Hairshop();
				h.setShopNo(rset.getInt("shop_no"));
				h.setShopName(rset.getString("shop_name"));
				h.setShopAddr(rset.getString("shop_addr"));
				h.setShopOpen(rset.getString("shop_open"));
				h.setShopClose(rset.getString("shop_close"));
				h.setShopRate(rset.getInt("shop_rate"));
				h.setShopImg(rset.getString("shop_img"));
				h.setShopLikes(rset.getInt("shop_likes"));
				list.add(h);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(ps);
		}
		return list;
	}

	public Hairshop selectOneHairshop(Connection conn,int shopNo) {
		PreparedStatement ps = null;
		ResultSet rset = null;
		String query = "select * from hairshop where shop_no=?";
		Hairshop hs = new Hairshop();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, shopNo);
			rset = ps.executeQuery();
			if(rset.next()) {
				hs.setShopNo(rset.getInt("shop_no"));
				hs.setShopCompNo(rset.getString("shop_comp_no"));
				hs.setShopName(rset.getString("shop_name"));
				hs.setShopAddr(rset.getString("shop_addr"));
				hs.setShopPhone(rset.getString("shop_phone"));
				hs.setShopOpen(rset.getString("shop_open"));
				hs.setShopClose(rset.getString("shop_close"));
				hs.setShopHoliday(rset.getString("shop_holiday"));
				hs.setShopRate(rset.getInt("shop_rate"));
				hs.setShopImg(rset.getString("shop_img"));
				hs.setShopLikes(rset.getInt("shop_likes"));
				hs.setAddrDetail(rset.getString("addr_detail"));
				hs.setAddrPostcode(rset.getString("addr_postcode"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(ps);
		}
		return hs;
	}

}
