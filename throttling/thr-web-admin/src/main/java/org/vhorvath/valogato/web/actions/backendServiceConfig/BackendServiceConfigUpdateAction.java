package org.vhorvath.valogato.web.actions.backendServiceConfig;


import java.util.List;

import org.vhorvath.valogato.common.beans.configuration.backendservice.BackendServiceBean;
import org.vhorvath.valogato.common.constants.ThrConstants;
import org.vhorvath.valogato.common.dao.highlevel.configuration.backendservice.BackendServiceConfigDAOFactory;
import org.vhorvath.valogato.common.exception.ThrottlingConfigurationException;
import org.vhorvath.valogato.common.utils.ThrottlingStorage;
import org.vhorvath.valogato.web.actions.ThrottlingActionSupport;
import org.vhorvath.valogato.web.beans.backendservice.FeatureParamWebBean;
import org.vhorvath.valogato.web.beans.backendservice.FeatureWebBean;
import org.vhorvath.valogato.web.beans.backendservice.SimulatedServiceWebBean;
import org.vhorvath.valogato.web.utils.WebBeanUtils;

import com.opensymphony.xwork2.util.Element;


public class BackendServiceConfigUpdateAction extends ThrottlingActionSupport {

	
	private static final long serialVersionUID = -3877323651562882037L;

	private String nameBackendService;
	private String maxLoading;
	private String averageResponseTime;
	private List<FeatureWebBean> features;
	@Element(value = FeatureParamWebBean.class) // only if its values should be changed at submit...
	private List<FeatureParamWebBean> featureParams;
	private String selectedFeature;
	private String submitType;
	private List<SimulatedServiceWebBean> simulatedServiceList;
	private String buttonName;

	
	public String execute() throws ThrottlingConfigurationException {
		String action = null;

		try {
			// clicking on the cancel button
			if (buttonName != null && buttonName.equals("Cancel")) {
				action = "LIST";
			}
			// clicking on the save button
			else if (submitType != null && submitType.equals("Save")) {
				// save
				BackendServiceConfigDAOFactory.getDAO().put(nameBackendService, updateBackendServiceBean());
				action = "LIST";
			} else {
				BackendServiceBean backendServiceBean = BackendServiceConfigDAOFactory.getDAO().getBackendService(nameBackendService);
				features = WebBeanUtils.loadFeatures();
				simulatedServiceList = WebBeanUtils.getSimulatedServiceList(backendServiceBean);
	
				// immediately after the list
				if (submitType == null) {
					maxLoading = backendServiceBean.getMaxLoading().toString();
					averageResponseTime = backendServiceBean.getAverageResponseTime().toString();
					selectedFeature = backendServiceBean.getFeature().getName();
					action = "UPDATE";
				}
				
				// choosing a different feature
				else if (submitType.equals("changedFeature")) {
					action = "UPDATE";
				}
				
				else {
					action = "LIST";
				}
				
				featureParams = WebBeanUtils.getFeatureParams(selectedFeature, backendServiceBean.getFeature().getParams());
			}
		} finally {
			ThrottlingStorage.removeCache();
		}
		
		return action;
	}

	
	public void validate() {
		if (buttonName != null && buttonName.equals("Save")) {
			// check maxLoading
			checkInteger(this, maxLoading, "maxLoading", "Max Loading", 0, 99999);
			// check averageResponseTime
			checkInteger(this, averageResponseTime, "averageResponseTime", "Average Response Time", 0, 20*60*1000);

			try {
				BackendServiceBean backendServiceBean = BackendServiceConfigDAOFactory.getDAO().getBackendService(nameBackendService);
				features = WebBeanUtils.loadFeatures();
				simulatedServiceList = WebBeanUtils.getSimulatedServiceList(backendServiceBean);
				List<FeatureParamWebBean> tmpFeatureParams = WebBeanUtils.getFeatureParams(selectedFeature, backendServiceBean.getFeature().getParams());
				if (featureParams != null) {
					for(FeatureParamWebBean param : featureParams) {
						// the sleeping period must be bigger than 1000 millisec and less than 30 sec
						if (param.getName().equals(ThrConstants.FeatureParam.period.toString())) {
							checkInteger(this, param.getValue(), ThrConstants.FeatureParam.period.getName(), ThrConstants.FeatureParam.period.getTitle(), 
									1000, 30000);
						}
						// the max number of waiting reqs must be bigger than 50 and less than 100000
						if (param.getName().equals(ThrConstants.FeatureParam.maxNumberOfWaitingReqs.toString())) {
							checkInteger(this, param.getValue(), ThrConstants.FeatureParam.maxNumberOfWaitingReqs.getName(), 
									ThrConstants.FeatureParam.maxNumberOfWaitingReqs.getTitle(), 50, 100000);
						}
						// the waiting Req List Max Size must be bigger than 10 and less than 1000
						if (param.getName().equals(ThrConstants.FeatureParam.waitingReqListMaxSize.toString())) {
							checkInteger(this, param.getValue(), ThrConstants.FeatureParam.waitingReqListMaxSize.getName(), 
									ThrConstants.FeatureParam.waitingReqListMaxSize.getTitle(), 10, 1000);
						}
						// the strategy must be in the set (fast,maintiningFreeSlots,registeringRequestsIndividually)
						if (param.getName().equals(ThrConstants.FeatureParam.strategy.toString())) {
							checkPossibleValues(this, param.getValue(), ThrConstants.FeatureParam.strategy.getName(), 
									ThrConstants.FeatureParam.strategy.getTitle(), ThrConstants.FeatureParamValue.fast.toString(),
									ThrConstants.FeatureParamValue.maintiningFreeSlots.toString(), 
									ThrConstants.FeatureParamValue.registeringRequestsIndividually.toString());
						}
						// the endpoints must have value
						if (param.getName().equals(ThrConstants.FeatureParam.endpoints.toString())) {
							checkRequired(this, param.getValue(), ThrConstants.FeatureParam.endpoints.getName(), ThrConstants.FeatureParam.endpoints.getTitle());
						}
						// refilling the titles (the titles don't come back in the HTTP req so they should be empty if there is a validation error)
						for(FeatureParamWebBean tmpParam : tmpFeatureParams) {
							if (param.getName().equals(tmpParam.getName())) {
								param.setTitle(tmpParam.getTitle());
								break;
							}
						}
					}
				}
			} catch(ThrottlingConfigurationException e) {
				LOGGER.error("ThrottlingConfigurationException has happened!", e);
			}
		}
	}
	
	
	private BackendServiceBean updateBackendServiceBean() throws ThrottlingConfigurationException {
		BackendServiceBean backendServiceBean = BackendServiceConfigDAOFactory.getDAO().getBackendService(nameBackendService);
		backendServiceBean.setAverageResponseTime(Integer.parseInt(averageResponseTime));
		backendServiceBean.setMaxLoading(Integer.parseInt(maxLoading));
		backendServiceBean.setFeature(WebBeanUtils.convertFeatureWebBeanToBean(selectedFeature, featureParams));
		return backendServiceBean;
	}

	
	public String getNameBackendService() {
		return nameBackendService;
	}

	public void setNameBackendService(String nameBackendService) {
		this.nameBackendService = nameBackendService;
	}

	public String getMaxLoading() {
		return maxLoading;
	}

	public void setMaxLoading(String maxLoading) {
		this.maxLoading = maxLoading;
	}

	public String getAverageResponseTime() {
		return averageResponseTime;
	}

	public void setAverageResponseTime(String averageResponseTime) {
		this.averageResponseTime = averageResponseTime;
	}

	public String getSelectedFeature() {
		return selectedFeature;
	}

	public void setSelectedFeature(String selectedFeature) {
		this.selectedFeature = selectedFeature;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	public List<FeatureWebBean> getFeatures() {
		return features;
	}

	public void setFeatures(List<FeatureWebBean> features) {
		this.features = features;
	}

	public List<FeatureParamWebBean> getFeatureParams() {
		return featureParams;
	}

	public void setFeatureParams(List<FeatureParamWebBean> featureParams) {
		this.featureParams = featureParams;
	}

	public List<SimulatedServiceWebBean> getSimulatedServiceList() {
		return simulatedServiceList;
	}

	public void setSimulatedServiceList(List<SimulatedServiceWebBean> simulatedServiceList) {
		this.simulatedServiceList = simulatedServiceList;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

}