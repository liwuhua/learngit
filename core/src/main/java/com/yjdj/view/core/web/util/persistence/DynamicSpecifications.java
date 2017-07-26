package com.yjdj.view.core.web.util.persistence;

import com.google.common.collect.Lists;
import com.yjdj.view.core.util.Exceptions;
import com.yjdj.view.core.util.ServletUtils;
import com.yjdj.view.core.web.SecurityConstants;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.servlet.ServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DynamicSpecifications {
	private static final Logger logger = LoggerFactory.getLogger(DynamicSpecifications.class);
	
	private static final String SHORT_DATE = "yyyy-MM-dd";
	private static final String LONG_DATE = "yyyy-MM-dd mm:HH:ss";
	private static final String TIME = "mm:HH:ss";
	
	public static Collection<SearchFilter> genSearchFilter(ServletRequest request) {
		if (request != null) {
			Map<String, Object> searchParams = ServletUtils.getParametersStartingWith(request, SecurityConstants.SEARCH_PREFIX);
			Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
			return filters.values();
		}
		return new HashSet<SearchFilter>(0);
	}
	
	public static <T> Specification<T> bySearchFilter(final Class<T> entityClazz, SearchFilter...searchFilters) {
		return bySearchFilter(null, entityClazz, searchFilters);
	}
	
	public static <T> Specification<T> bySearchFilter(ServletRequest request, final Class<T> entityClazz, SearchFilter...searchFilters) {
		Collection<SearchFilter> filters = genSearchFilter(request);
		Set<SearchFilter> set = new HashSet<SearchFilter>(filters);
		for (SearchFilter searchFilter : searchFilters) {
			set.add(searchFilter);
		}
		return bySearchFilter(entityClazz, set);
	}

	public static <T> Specification<T> bySearchFilter(final Class<T> entityClazz, final Collection<SearchFilter> filters) {
		return new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (filters != null && !filters.isEmpty()) {
					List<Predicate> predicates = Lists.newArrayList();
					for (SearchFilter filter : filters) {
						// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
						String[] names = StringUtils.split(filter.getFieldName(), ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						// 自动进行enum和date的转换。
						Class clazz = expression.getJavaType();
						if (Date.class.isAssignableFrom(clazz) && !filter.getValue().getClass().equals(clazz)) {
							filter.setValue(convert2Date((String)filter.getValue()));
						} else if (Enum.class.isAssignableFrom(clazz) && !filter.getValue().getClass().equals(clazz)) {
							filter.setValue(convert2Enum(clazz, (String)filter.getValue()));
						}
						
						// logic operator
						switch (filter.getOperator()) {
						case EQ:
							predicates.add(builder.equal(expression, filter.getValue()));
							break;
						case LIKE:
							predicates.add(builder.like(expression, "%" + filter.getValue() + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) filter.getValue()));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) filter.getValue()));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.getValue()));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.getValue()));
							break;
						}
					}

					// 将所有条件用 and 联合起来
					if (predicates.size() > 0) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
	}
	
	private static Date convert2Date(String dateString) {
		SimpleDateFormat sFormat = new SimpleDateFormat(SHORT_DATE);
		try {
			return sFormat.parse(dateString);
		} catch (ParseException e) {
			try {
				return sFormat.parse(LONG_DATE);
			} catch (ParseException e1) {
				try {
					return sFormat.parse(TIME);
				} catch (ParseException e2) {
					logger.error("Convert time is error! The dateString is " + dateString + "." + Exceptions.getStackTraceAsString(e2));
				}
			}
		}

		return null;
	}
		
	
	private static <E extends Enum<E>> E convert2Enum(Class<E> enumClass, String enumString) {
		return EnumUtils.getEnum(enumClass, enumString);
	}
}
