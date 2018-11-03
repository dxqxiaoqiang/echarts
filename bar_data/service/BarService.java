package com.bar_data.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bar_data.entity.BarDataVo;
import com.ssc.system.dao.BaseDao;
@Service
public class BarService {
	@Autowired
	  private BaseDao baseDao;
	  
	  
	  public List<BarDataVo> getDataList(){
		  List<BarDataVo> lists = new ArrayList<BarDataVo>();
		  
		  
		  
		  return lists;
		  
	  }
}
