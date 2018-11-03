package com.app.pie_data.controller;

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
import com.app.pie_data.entity.PieDataVo;

@Controller
@RequestMapping("pieDataInfo")
public class PieDataController {
	@RequestMapping("getData")
	@ResponseBody
	public void getData(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		try {
			PrintWriter out = response.getWriter();
			List <PieDataVo> lists = getData();
			String msgctx="";
			String msgcomment="";
			if (null!= lists) {
				for(PieDataVo vo:lists) {
					msgcomment +=  vo.getComment()+",";
					msgctx  += vo.getCtx()+",";
				}
			}
//			if(!"".equals(msgcomment)) {
//				msgcomment = msgcomment.substring(0,msgcomment.length()-1);
//			}
//			if(!"".equals(msgctx)) {
//				msgctx = msgctx.substring(0,msgctx.length()-1);
//			}
			String msg = msgctx+"@"+msgcomment;
			out.print(msg);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public List<PieDataVo> getData(){
    	StringBuffer strSql= new StringBuffer();
    	strSql.append("SELECT comment, count(comment) as  ctx from b_category ");
    	strSql.append(" INNER JOIN b_project ON b_category.id=b_project.categoryId GROUP BY comment");
    	Connection conn = DbManager.getDbManager().getConn();
    		PreparedStatement st = null;
    		ResultSet rs = null;
    		List<PieDataVo> lists = new ArrayList<PieDataVo>();
    		try {
    			st = conn.prepareStatement(strSql.toString());
    			rs = st.executeQuery();
    			while(rs.next()) {
    				PieDataVo vo= new PieDataVo();
    				vo.setCtx(rs.getInt("ctx")+"");
    				vo.setComment(rs.getString("comment")); 
    				lists.add(vo);
    		     } 
    		}catch (SQLException e) {
    			e.printStackTrace();
    		}
    		finally{
    			DbManager.getDbManager().close(conn, st, rs);
    		}
    		return lists;
    }	
    }