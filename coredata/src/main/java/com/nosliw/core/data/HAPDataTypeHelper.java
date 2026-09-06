package com.nosliw.core.data;

import java.util.Set;

/**
 * This is helper class that provide methods that related with data type and operation information
 */
public interface HAPDataTypeHelper {

	/**
	 * 
	 * @param dataTypeInfo
	 * @param name
	 * @return
	 */
	HAPDataTypeOperation getOperationInfoByName(HAPDataTypeId dataTypeInfo, String name);

	
	/**
	 * List all data types between from and to
	 * This means that each data type should be able to convert to "From" and also can be converted from "To"
	 * @param from
	 * @param to
	 * @return
	 */
	Set<HAPDataTypeId> getAllDataTypeInRange(HAPDataTypeId from, HAPDataTypeId to);
	

	/**
	 * Find the root data type (all the parent data type which don't have parent data type)
	 * @param dataTypeId
	 * @return
	 */
	Set<HAPDataTypeId> getRootDataTypeId(HAPDataTypeId dataTypeId);

	/**
	 * Find the root data type relationship (all the parent data type which don't have parent data type)
	 * @param dataTypeId
	 * @return
	 */
	Set<HAPRelationship> getRootDataTypeRelationship(HAPDataTypeId dataTypeId);

	
	
}
