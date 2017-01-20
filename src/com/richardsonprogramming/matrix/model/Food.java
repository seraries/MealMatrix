/* This class makes a food object with a given type (e.g. "fruits"), name 
 * (e.g., "oranges"), serving size (e.g., "1/2 cup"), and default value ("yes")
 *
 * @version v.15 1-15-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.model;

public class Food {
	private String type;
	private String name;
	private String servingSize; 
	private String isDefault;  

	// constructor used with FoodMatrix.java-
	public Food (String type, String name, String servingSize, String isDefault) {
		this.type = type;
		this.name = name;
		this.servingSize = servingSize;
		this.isDefault = isDefault;
	}

	// constructor used with FillFoodDB.java--fills database with 
	// default foods, therefore assigns "yes" to "isDefault"
	public Food (String type, String name, String servingSize) {
		this.type = type;
		this.name = name;
		this.servingSize = servingSize;
		this.isDefault = "yes";
	}

	// constructor with default serving size for user-added foods and
	// since these are not the default foods in db, assign "no" to "isDefault"
	public Food (String type, String name){
		this.type = type;
		this.name = name;
		this.isDefault = "no";
		if (type == "veggies" || type == "fruits") {
			this.servingSize = "1/2 cup";
		} else if (type == "proteins") {
			this.servingSize = "4 oz";
		} else if (type == "fats") {
			this.servingSize = "1 tbsp";
		} else {
			servingSize = "1 tsp";
			} // must be a seasoning
	}

	public String getType(){
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public String getIsDefault() {
		return this.isDefault;
	}
	public String getServingSize() {
		return this.servingSize;
	}

	// TO DO: add functionality for user to adjust serving size of foods
	public void setServingSize (String servingSize) {
		this.servingSize = servingSize;
	}

	@Override
	public String toString() {
		String dataString = getName();
		return dataString;
	}
}
