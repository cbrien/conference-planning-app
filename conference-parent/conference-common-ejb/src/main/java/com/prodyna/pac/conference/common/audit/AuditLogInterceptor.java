package com.prodyna.pac.conference.common.audit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

@Audited
@Interceptor
public class AuditLogInterceptor {

	@Inject
	@AuditLog
	Logger logger;

	@Inject
	EntityManager em;

	@AroundInvoke
	public Object auditChanges(InvocationContext context) throws Exception {
		Object[] parameters = context.getParameters();
		if (parameters.length == 0 || parameters[0] == null) {
			logger.debug("no parameters to audit or inoked with null "
					+ context.getMethod());
			return context.proceed();
		}

		Audited audited = context.getMethod().getAnnotation(Audited.class);
		Object paramObject = parameters[0];
		Object originalObject = null;
		boolean auditable = false;
		try {
			Field emField = paramObject.getClass().getDeclaredField(
					audited.value());
			emField.setAccessible(true);
			Object id = emField.get(paramObject);
			if (id != null
					&& (originalObject = em.find(paramObject.getClass(), id)) != null) {
				em.detach(originalObject);
			}
			auditable = true;
		} catch (Exception e) {
			logger.debug("could not audit method call: " + context.getMethod());
		}

		Object result = context.proceed();

		if (auditable) {
			String classname = paramObject.getClass().getName();
			if (originalObject == null) {
				String attributes = getObjectAttributes(paramObject);
				logger.info(classname + " has been created: " + attributes);
			} else {
				List<ObjectChange> changes = getChanges(originalObject,
						paramObject);
				if (changes.size() > 0) {
					logger.info(classname + " has been changed: "
							+ Arrays.toString(changes.toArray()));
				}
			}
		}

		return result;
	}

	private String getObjectAttributes(Object created) {
		StringBuilder sb = new StringBuilder("[");
		Field[] fields = created.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				Object value = field.get(created);
				if (sb.length() > 1) {
					sb.append(", ");
				}
				sb.append("Field: \"").append(field.getName());
				sb.append("\" value: \"").append(value).append("\"");
			} catch (Exception e) {
				// skip field
			}
		}
		return sb.append("]").toString();
	}

	private List<ObjectChange> getChanges(Object original, Object modified) {
		List<ObjectChange> objectChanges = new ArrayList<ObjectChange>();
		Field[] fields = modified.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				Object originalValue = field.get(original);
				Object modifiedValue = field.get(modified);
				if ((originalValue != null || modifiedValue != null)
						&& (originalValue == null || !originalValue
								.equals(modifiedValue))) {
					String name = field.getName();
					objectChanges.add(new ObjectChange(name, originalValue,
							modifiedValue));
				}
			} catch (Exception e) {
				// skip field
			}
		}
		return objectChanges;
	}

	final class ObjectChange {

		private String name;
		private Object original;
		private Object modified;

		public ObjectChange(String name, Object original, Object modified) {
			this.name = name;
			this.original = original;
			this.modified = modified;
		}

		@Override
		public String toString() {
			return "Field: \"" + name + "\" from: \"" + original + "\" to: \""
					+ modified + "\"";
		}
	}
}
