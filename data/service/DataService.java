package com.app.data.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.data.entity.DataVO;
import com.ssc.system.dao.BaseDao;
@Service
public class DataService {
	  
  @Autowired
  private BaseDao baseDao;
  
  
  public List<DataVO> getDataList(){
	  List<DataVO> lists = new ArrayList<DataVO>();
	  
	  
	  
	  return lists;
	  
  }
  

}
