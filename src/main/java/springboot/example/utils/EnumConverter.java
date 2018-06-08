package springboot.example.utils;

import springboot.example.models.TaskStatus;

public class EnumConverter
{
	public static TaskStatus convert(String status){
				
		try{
			return TaskStatus.valueOf(status);
		}
		catch(Exception ex){
			return null;
		}
	}
}
