package edu.kis.powp.command;

import java.util.List;

import edu.kis.powp.jobs2d.Job2dDriver;

public class ComplexCommand implements DriverCommand {
	private List<DriverCommand> listOfCommand;

	public ComplexCommand(List<DriverCommand> list) {
		listOfCommand = list;
	}

	@Override 
	public void execute(Job2dDriver job2dDriver) {
<<<<<<< HEAD
		for (DriverCommand command : listOfCommand)
			command.execute(job2dDriver);
=======
		for (DriverCommand item : listOfCommand)
			item.execute(job2dDriver);
>>>>>>> branch 'master' of https://github.com/baksior/powp_jobs2d_project
	}
}