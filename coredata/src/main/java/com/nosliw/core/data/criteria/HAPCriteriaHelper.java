package com.nosliw.core.data.criteria;

import java.util.Map;
import java.util.Set;

import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPDataTypeId;
import com.nosliw.core.data.HAPRelationship;
import com.nosliw.core.data.matcher.HAPMatcher;
import com.nosliw.core.data.matcher.HAPMatchers;

public interface HAPCriteriaHelper {

	
	Set<HAPDataTypeCriteriaId> normalizeCriteria(Set<HAPDataTypeCriteriaId> dataTypeCriteriaIds);
	
	/**
	 * Build all possible relationship in order to convert the data type from "from" criteria to "to" criteria  
	 * @param from
	 * @param to
	 * @return
	 */
	HAPMatchers buildMatchers(HAPDataTypeCriteria from, HAPDataTypeCriteria to);
	
	/**
	 * Process all expression in criteria in order to figure out solid criteria for each expression criteria
	 * @param criteria
	 * @param parms parms used in expression 
	 */
	void processExpressionCriteria(HAPDataTypeCriteria criteria, Map<String, HAPData> parms);

	/**
	 * Do "And" operation between criteria1 and criteria2 
	 * @param criteria1
	 * @param criteria2
	 * @return
	 */
	HAPDataTypeCriteria and(HAPDataTypeCriteria criteria1, HAPDataTypeCriteria criteria2);

	
	/**
	 * Find criteria that can convert to both criteria
	 * @param expectCriteria
	 * @param criteria
	 * @return
	 */
	HAPDataTypeCriteria merge(HAPDataTypeCriteria criteria1, HAPDataTypeCriteria criteria2);
	
	
	/**
	 * Loose criteria so that all the data type that can be converted to this criteria are included
	 * @param criteria
	 * @return
	 */
	HAPDataTypeCriteria looseCriteria(HAPDataTypeCriteria criteria);

	/**
	 * Whether source criteria is convertable to target criteria
	 * This means that all data type in criteria1 is also part of criteria2
	 * @param criteria1
	 * @param criteria2
	 * @return
	 */
	HAPMatchers convertable(HAPDataTypeCriteria sourceCriteria, HAPDataTypeCriteria targetCriteria);

	/**
	 * Whether source dataType is convertable to target dataType
	 * This means that dataType1 can be converted to dataType2
	 * @param criteria1
	 * @param criteria2
	 * @return
	 */
	HAPRelationship convertable(HAPDataTypeId sourceDataTypeId, HAPDataTypeId targetDataTypeId);
	
	/**
	 * 
	 * @param sourceCriteria
	 * @param targetCriteria
	 * @return
	 */
	HAPMatcher convertableIdCriteria(HAPDataTypeCriteriaId sourceCriteria, HAPDataTypeCriteriaId targetCriteria);
	
}
