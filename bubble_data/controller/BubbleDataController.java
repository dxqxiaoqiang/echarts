package com.app.bubble_data.controller;
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

import com.app.bubble_data.entity.BubbleDataVo;
import com.app.pie_data.entity.DbManager;
@Controller
@RequestMapping("bubbledatainfo")
public class BubbleDataController {
   @RequestMapping("getData")
   @ResponseBody
   public void getData(HttpServletRequest request, HttpServletResponse response) {
		// 使客户浏览器区分数据，在这里是html
		response.setContentType("text/html;charset=GBK");
		try {
			PrintWriter out = response.getWriter();
			List<BubbleDataVo> lists = getData();
			String msgctx = "";
			String msgmonth = "";
			String msgkeyword = "";
			String titles = "";
			
			if (lists != null) {
				for (BubbleDataVo vo : lists) {
					
				    //[[月份,总量,关键词，title]
				    //[[40000,81.8,'Australia',2015],[43294,81.7,'China',2015],[13334,76.9,'China',2015]]
					msgmonth += vo.getMonth() + "#";
					msgctx += vo.getCtx() + "#";
					msgkeyword += vo.getKeyword() + "#";
					titles += vo.getTitle() + "#";
					
				}
				if(msgmonth.length()>0) {
					msgmonth = msgmonth.substring(0, msgmonth.length()-1);
					msgctx = msgctx.substring(0, msgctx.length()-1);
					msgkeyword = msgkeyword.substring(0, msgkeyword.length()-1);
					titles = titles.substring(0, titles.length()-1);
				}
				
				String msg =  msgmonth + "@" + msgctx + "@" + msgkeyword + "@" + titles;
				out.print(msg);
		}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

}

private List<BubbleDataVo> getData() {
	StringBuffer strsql = new StringBuffer();
	strsql.append("select \"关键词统计\" as title,\"区块链\" as keyword, month(create_time) month, count(keyword)  as ctx from b_project ");
	strsql.append("where keyword like '%区块链%' ");
	strsql.append("GROUP BY month(create_time) ");
//	strsql.append("");
	strsql.append(" UNION select \"关键词统计\" as title,\"大数据\" as keyword, month(create_time) month, count(keyword)  as ctx from b_project ");
	strsql.append("where keyword like '%大数据%' ");
	strsql.append("GROUP BY month(create_time) ");
	strsql.append("UNION select \"关键词统计\" as title,\"人工智能\" as keyword, month(create_time) month, count(keyword)  as ctx from b_project ");
	strsql.append("where keyword like '%人工智能%'");
	strsql.append("GROUP BY month(create_time) ");
	Connection conn = DbManager.getDbManager().getConn();
	PreparedStatement st = null;
	ResultSet rs = null;
	List<BubbleDataVo> lists = new ArrayList<BubbleDataVo>();
	try {
		st = conn.prepareStatement(strsql.toString());
		rs = st.executeQuery();
		while(rs.next()) {
			BubbleDataVo vo= new BubbleDataVo();
			vo.setCtx(rs.getInt("ctx")+"");
			vo.setMonth(rs.getString("month"));
			vo.setKeyword(rs.getString("keyword"));
			vo.setTitle(rs.getString("title"));
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
