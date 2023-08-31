package telran.employees.dto;

import java.io.Serializable;

public record ReadFromTo<T extends Serializable>(T from, T to) implements Serializable {
	
	

}
