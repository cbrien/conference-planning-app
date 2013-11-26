package com.prodyna.pac.conference.web.util;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

import com.prodyna.pac.conference.web.beans.ConferenceViewBean;

@RequestScoped
public class BeanProvider {

	@Produces
	FacesContext facesContext = FacesContext.getCurrentInstance();

	public static ConferenceViewBean getConferenceViewBean() {
		return getViewScopedBean(ConferenceViewBean.class, "conferenceView");
	}

	private static ConferenceViewBean getViewScopedBean(
			Class<ConferenceViewBean> clazz, String name) {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		VariableMapper variableMapper = elContext.getVariableMapper();
		ValueExpression variable = variableMapper.resolveVariable("#{" + name
				+ "}");
		Object value = variable.getValue(elContext);

		return clazz.cast(value);
	}

}
