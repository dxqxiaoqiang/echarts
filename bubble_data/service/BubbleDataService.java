package com.app.bubble_data.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bubble_data.entity.BubbleDataVo;
import com.ssc.system.dao.BaseDao;
@Service
public class BubbleDataService {
	@Autowired
    BaseDao baseDao;
	public List<BubbleDataVo> getData(){
		  List<BubbleDataVo> lists = new ArrayList<BubbleDataVo>();
		  
		  
		  
		  return lists;
		  
	}
}
