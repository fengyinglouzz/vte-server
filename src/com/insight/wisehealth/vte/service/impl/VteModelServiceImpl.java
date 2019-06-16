package com.insight.wisehealth.vte.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.core.util.StringUtil;
import com.insight.core.util.ToolUtil;
import com.insight.wisehealth.vte.dao.TbVteModelDao;
import com.insight.wisehealth.vte.persistence.TbVteModel;
import com.insight.wisehealth.vte.pojo.VteModelTreePojo;
import com.insight.wisehealth.vte.service.VteModelService;


/**
 * 
 * 描述:模块表服务
 * 
 * Copyright © 2016 Insight.ltd All rights reserved
 * 
 * @author 王珠珠
 * @version 1.0.0
 */
@Service
public class VteModelServiceImpl  implements VteModelService{
	@Autowired
	TbVteModelDao vteModelMapper;
	@Override
	public TbVteModel saveVteModel(Map map) throws Exception{
		TbVteModel tbVteModel = new TbVteModel();
		tbVteModel = (TbVteModel) ToolUtil.converMapToObject(map,TbVteModel.class);
		if (null == tbVteModel.getModelId()) {
			//根据modelFatherCode获取当前模块编码
			String modelFatherCode = (String)map.get("modelFatherCode");
			String modelCode = gainModelCode(modelFatherCode);
			tbVteModel.setModelCode(modelCode);
			vteModelMapper.insert(tbVteModel);
		} else {
			vteModelMapper.updateByFormMap(map);
		}
		return tbVteModel;
	}
	
	@Override
	public void delVteModel(Map map) throws Exception {
		String ids = (String) map.get("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			TbVteModel tbVteModel = new TbVteModel();
			tbVteModel.setModelId(Integer.parseInt(id[i]));
			vteModelMapper.deleteByPrimaryKey(tbVteModel);
		}
	}

	@Override
	public List queryVteModelList(Map map) throws Exception {
		List list = vteModelMapper.queryAllVteModel(map);
		return list;
	}

	@Override
	public int countVteModelList(Map map) throws Exception {
		int count = (int)vteModelMapper.countAllVteModel(map);
		return count;
	}

	@Override
	public List queryAllVteModelNp(Map map) throws Exception{
		List list = new ArrayList();
		list = vteModelMapper.queryAllVteModelNP( map);
		return list;
	}
	
	@Override
	public TbVteModel queryVteModelInfo(Map map) throws Exception{
		TbVteModel tbVteModel =	(TbVteModel)vteModelMapper.queryVteModelInfo(map);
		return tbVteModel;
	}
	
	@Override
	public List<VteModelTreePojo> queryVteModelTreePojo(Map map)throws Exception{
		List<VteModelTreePojo> list = vteModelMapper.queryAllModelByRole(map);
		List<VteModelTreePojo>  vteModelTreePojoList = new ArrayList();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					VteModelTreePojo  tbVteModel = list.get(i);
					String modelCode = tbVteModel.getModelCode();
					String[] modelCs = modelCode.split("-");
					if(modelCs.length==1){
						tbVteModel.setChildrenList(new ArrayList());
						vteModelTreePojoList.add(tbVteModel);
					}else{
						List<VteModelTreePojo> cList = vteModelTreePojoList;
						StringBuffer codes = new StringBuffer();
						for(int j=0;j<modelCs.length-1;j++){
							if(j!=0){
								codes.append("-");
							}
								codes.append(modelCs[j]);
							VteModelTreePojo pPojo = findVteModelTreePojoFormList(cList, codes.toString());
							cList = pPojo.getChildrenList();
						}
						cList.add(tbVteModel);
					}
				}
			}
		return vteModelTreePojoList;
	}
	
	private  VteModelTreePojo findVteModelTreePojoFormList(List<VteModelTreePojo> vteModelTreePojoList,String modelCode){
		VteModelTreePojo vteModelTreePojo = new VteModelTreePojo();
		vteModelTreePojo.setModelCode(modelCode);
		if(vteModelTreePojoList!=null&&vteModelTreePojoList.size()>0){
			for(int i=0;i<vteModelTreePojoList.size();i++){
				VteModelTreePojo vteModelTree= vteModelTreePojoList.get(i);
				if(vteModelTree.getModelCode().equals(modelCode)){
					if(vteModelTree.getChildrenList()==null){
						vteModelTree.setChildrenList(new ArrayList());
					}
					return vteModelTree;
				}
			}
		}
		vteModelTreePojo.setChildrenList(new ArrayList());
		return  vteModelTreePojo;
	}
	
	//////////////////////////////私有方法///////////////////////////////////////////////////////////////
	
	private String gainModelCode(String modelFatherCode){
		String modelCode = new String();
		if(StringUtil.isEmpty(modelFatherCode)){
			modelFatherCode = "";
		}else{
			modelFatherCode = modelFatherCode+"-";
		}
		String regexpStrng =  "^"+modelFatherCode+"[0-9]{1,5}$";
		Map map = new HashMap();
		map.put("regexpStrng", regexpStrng);
		String maxmodelCode = vteModelMapper.queryMaxCodeByPaCode(map);
		int lastnum = 10;
		if(!StringUtil.isEmpty(maxmodelCode)){
			String lastcode = maxmodelCode.substring(maxmodelCode.lastIndexOf("-"));
			lastnum = Integer.valueOf(lastcode);
		}
		lastnum ++;
		if(lastnum<10){
			modelCode = modelFatherCode+"-00"+lastnum;
		}else if(lastnum<100){
			modelCode = modelFatherCode+"-0"+lastnum;
		}else{
			modelCode = modelFatherCode+"-"+lastnum;
		}
		return modelCode;
	}

	/**
	 * 添加分组数据私有方法
	 * 模块表
	 * @param list
	 * @param deleteVteModelIds 需要删除的数据
	 * @param pid 父节点id
	 * @return
	 * @throws Exception
	 */
	private void saveVteModelByGroup (List list,String deleteVteModelIds,Integer pid) throws Exception {
		//根据deleteBasicPastOperationId 删除信息
		if(!StringUtil.isEmpty(deleteVteModelIds)){
			String deleteVteModelId[] =  deleteVteModelIds.split(",");
			for(int i=0;i<deleteVteModelId.length;i++){
				if(!StringUtil.isEmpty(deleteVteModelId[i])){
					TbVteModel tbVteModel = new TbVteModel();
					tbVteModel.setModelId(Integer.parseInt(deleteVteModelId[i]));
					vteModelMapper.deleteByPrimaryKey(tbVteModel);
				}
			}
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				TbVteModel tbVteModel
				= (TbVteModel) ToolUtil.converMapToObject(map,TbVteModel.class);
				if (null == tbVteModel.getModelId()) {
					//TODO 设置Pid 
					vteModelMapper.insertSelective(tbVteModel);
				} else {
					vteModelMapper.updateByFormMap(map);
				}
			}
		}
	}
}
