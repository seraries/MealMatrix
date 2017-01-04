/* This class makes a food object with a given type (e.g. "fruits"), name 
 * (e.g., "oranges"), and serving size (e.g., "1/2 cup").
 *
 * @version v.14 1-3-2017
 * @author Sarah Richardson
 */

package com.richardsonprogramming.matrix.model;

public class Food {
	private String type;
	private String name;
	private String servingSize; 

	public Food (String type, String name, String servingSize) {
		this.type = type;
		this.name = name;
		this.servingSize = servingSize;
	}

	// constructor with default serving size for user-added foods
	public Food (String type, String name){
		this.type = type;
		this.name = name;
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
