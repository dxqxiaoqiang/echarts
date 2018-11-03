package com.app.pie_data.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.pie_data.entity.PieDataVo;
import com.ssc.system.dao.BaseDao;
@Service
public class PieDataService {
	@Autowired
	  private BaseDao baseDao;  
	  public List<PieDataVo> getDataList(){
		  List<PieDataVo> lists = new ArrayList<PieDataVo>();
		 
		  return lists;
		  
	  }
}
