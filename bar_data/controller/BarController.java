package com.bar_data.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.app.pie_data.entity.DbManager;
import com.bar_data.entity.BarDataVo;
@Controller
@RequestMapping("bardatainfo")
public class BarController {
	@RequestMapping("getData")
	@ResponseBody
	public void getData(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		try {
			PrintWriter out = response.getWriter();
			List<BarDataVo> lists = getData();
			String msgctx = "";
			String msgmonth = "";
			
			String msgpass = "";
			String msgnopass= "";
			
			if(null!=lists) {
				for (BarDataVo vo : lists) {
					
					msgctx+=vo.getCtx()+",";
					msgmonth+=vo.getMonth()+",";
					msgpass+= vo.getPass()+",";
					msgnopass+= vo.getNopass();
				}
			}
			String msg = msgctx+"@"+msgmonth+"@"+msgpass+"@"+msgnopass;
			
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
}
}
public  List<BarDataVo> getData() {
	StringBuffer strSql = new StringBuffer();
        strSql.append("SELECT MONTH(create_time) month,IFNULL((SUM(CASE status WHEN 1 THEN 1  END)),0) pass,IFNULL((SUM(CASE status WHEN 2 THEN 1 END)),0) nopass, COUNT(*) ctx FROM b_project ");
        strSql.append("GROUP BY MONTH(create_time) ORDER BY month;");
//        strSql.append("GROUP BY MONTH(create_time) ORDER BY month;");
        
       Connection conn = DbManager.getDbManager().getConn();
       StringBuffer srtsql = new StringBuffer();
		PreparedStatement st = null;

		ResultSet rs = null;
		List<BarDataVo> lists = new ArrayList<BarDataVo>();
		try {

			st = conn.prepareStatement(strSql.toString());
			rs = st.executeQuery();
			while(rs.next()){
				BarDataVo vo = new BarDataVo();
				vo.setCtx(rs.getString("ctx"));
				vo.setMonth(rs.getString("month"));
				vo.setNopass(rs.getString("nopass"));
				vo.setPass(rs.getString("pass"));
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