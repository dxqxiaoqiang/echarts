package com.app.data.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.data.entity.DataVO;
import com.app.pie_data.entity.DbManager;

/**
 * 首页报表数据
 * 
 * @author dxq
 *
 */
@Controller
@RequestMapping("dataInfo")
public class DataController {

	
	
	//桌面总数和剩余数
	@RequestMapping("getData")
	@ResponseBody
	public void getData(HttpServletRequest request, HttpServletResponse response) {
		// 使客户浏览器区分数据，在这里是html
		response.setContentType("text/html;charset=GBK");
		try {
			PrintWriter out = response.getWriter();
			List<DataVO> lists = getData();
			String msgJnc = "";
			String msgJnm = "";
			
			String msgQnc = "";
			String msgQnm = "";
			
			if(null!=lists) {
				for (DataVO vo : lists) {
					if("1".equals(vo.getFalg())) {
						msgJnc+=vo.getCts()+",";
						msgJnm+=vo.getMonth()+",";
					}else {
						msgQnc+=vo.getCts()+",";
						msgQnm+=vo.getMonth()+",";
					}
				}
			}
			Date day=new Date();  
//			设置日期格式：年
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy");
			String msg = msgJnc+"@"+msgJnm+"&"+msgQnc+"@"+msgQnm+"#"+df.format(day);
			
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public  List<DataVO> getData() {
		StringBuffer strSql = new StringBuffer();
		
		strSql.append(" select f.dateval month,f.cts,f.flag from (");
		strSql.append(" select k.dateval,ifnull(m.cts,0) cts,'1' flag from mydate k");
		strSql.append(" left join (");
		strSql.append(" select count(f.datem) cts ,f.datem from ( ");
		strSql.append(" select h.id,h.title,h.name,DATE_FORMAT(h.create_time,'%Y-%m') datem from   b_project h where h.create_time is not null");
		strSql.append(" and DATE_FORMAT(h.create_time,'%Y')=DATE_FORMAT(NOW(),'%Y')");
		strSql.append(" ) f  where 1=1  group by f.datem order by datem");
		strSql.append(" ) m on m.datem=k.dateval");
		strSql.append(" where DATE_FORMAT(STR_TO_DATE(k.dateval,'%Y'),'%Y')=DATE_FORMAT(NOW(),'%Y') ");
		strSql.append(" union ");
		strSql.append(" select k.dateval,ifnull(m.cts,0) cts,'2' flag   from mydate k");
		strSql.append(" left join (");
		strSql.append(" select count(f.datem) cts ,f.datem from ( ");
		strSql.append(" select h.id,h.title,h.name,DATE_FORMAT(h.create_time,'%Y-%m') datem from   b_project h where h.create_time is not null");
		strSql.append(" and DATE_FORMAT(h.create_time,'%Y')=DATE_FORMAT(date_add(NOW(), interval -1 year),'%Y')");
		strSql.append(" ) f  where 1=1  group by f.datem order by datem");
		strSql.append(" ) m on m.datem=k.dateval");
		strSql.append(" where DATE_FORMAT(STR_TO_DATE(k.dateval,'%Y'),'%Y')=DATE_FORMAT(date_add(NOW(), interval -1 year),'%Y')");
		strSql.append(" ) f  order by f.dateval");
//		连接数据库
	Connection conn = DbManager.getDbManager().getConn();
//	申明一个类型为PreparedStatement 的变量
		PreparedStatement st = null;
//		定义出这个ResultSet的对象rs
		ResultSet rs = null;
		List<DataVO> lists = new ArrayList<DataVO>();
		try {
//			预编译的 SQL 语句的对象
			st = conn.prepareStatement(strSql.toString());
			rs = st.executeQuery();
			while(rs.next()){
				DataVO vo = new DataVO();
				vo.setCts(rs.getInt("cts")+"");
				vo.setMonth(rs.getString("month"));
				vo.setFalg(rs.getString("flag"));
				lists.add(vo);
			}
			return lists;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DbManager.getDbManager().close(conn, st, rs);
		}
		return lists;
	}

}
