package com.pss.poc.web.bean;

import java.util.ArrayList;

import com.pss.innoms.ws.model.InnomsChallengeModel;
import com.pss.innoms.ws.model.InnomsClaimModel;
import com.pss.innoms.ws.model.InnomsIdeaModel;
import com.pss.innoms.ws.model.InnomsSolutionModel;

public class FileUploadFactory {
	
	
	public static ArrayList<FileUploadBean> load()
	{
		 ArrayList<FileUploadBean> fd = new ArrayList<FileUploadBean>();
		
		FileUploadBean bean=new FileUploadBean();
		bean.setFileName("sss");
		fd.add(bean);
		return fd;
	}
	
 
	
	public static ArrayList<InnomsIdeaModel> loadIdea()
	{
		 ArrayList<InnomsIdeaModel> fd = new ArrayList<InnomsIdeaModel>();
			
		 InnomsIdeaModel bean=new InnomsIdeaModel();
		bean.setIdeaTitle("Idea");
		fd.add(bean);
		return fd;
	}
	
	public static ArrayList<InnomsChallengeModel> loadChallenge()
	{
		 ArrayList<InnomsChallengeModel> fd = new ArrayList<InnomsChallengeModel>();
		
		 InnomsChallengeModel bean=new InnomsChallengeModel();
		bean.setChallengeTitle("Challenge");
		fd.add(bean);
		return fd;
	}
	
	public static ArrayList<InnomsSolutionModel> loadSolution()
	{
		 ArrayList<InnomsSolutionModel> fd = new ArrayList<InnomsSolutionModel>();
		
		 InnomsSolutionModel bean=new InnomsSolutionModel();
		bean.setSolutionTitle("Solution");
		fd.add(bean);
		return fd;
	}
	
	public static ArrayList<InnomsClaimModel> loadClaims()
	{
		 ArrayList<InnomsClaimModel> fd = new ArrayList<InnomsClaimModel>();
		
		 InnomsClaimModel bean=new InnomsClaimModel();
		bean.setClaimDesc("Claims");
		fd.add(bean);
		return fd;
	}

}
