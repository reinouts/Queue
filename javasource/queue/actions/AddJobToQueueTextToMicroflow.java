// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package queue.actions;

import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import queue.helpers.ExponentialBackoffCalculator;
import queue.helpers.JobToQueueAdder;
import queue.helpers.JobValidator;
import queue.helpers.MicroflowValidator;
import queue.helpers.TimeUnitConverter;
import queue.proxies.constants.Constants;
import queue.repositories.ScheduledJobRepository;
import queue.utilities.CoreUtility;
import queue.repositories.ConstantsRepository;
import queue.repositories.JobRepository;
import queue.repositories.MicroflowRepository;
import queue.repositories.QueueRepository;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class AddJobToQueueTextToMicroflow extends CustomJavaAction<java.lang.Boolean>
{
	private IMendixObject __job;
	private queue.proxies.Job job;

	public AddJobToQueueTextToMicroflow(IContext context, IMendixObject job)
	{
		super(context);
		this.__job = job;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.job = __job == null ? null : queue.proxies.Job.initialize(getContext(), __job);

		// BEGIN USER CODE
		ILogNode logger = Core.getLogger(Constants.getLOGNODE());
		MicroflowRepository microflowRepository = new MicroflowRepository();
		MicroflowValidator microflowValidator = new MicroflowValidator(microflowRepository);
		JobValidator jobValidator = new JobValidator(logger, microflowValidator);
		ExponentialBackoffCalculator exponentialBackoffCalculator = new ExponentialBackoffCalculator();
		TimeUnitConverter timeUnitConverter = new TimeUnitConverter();
		ConstantsRepository constantsRepository = new ConstantsRepository();
		CoreUtility coreUtility = new CoreUtility();
		
		JobToQueueAdder adder = new JobToQueueAdder(jobValidator, exponentialBackoffCalculator, timeUnitConverter, constantsRepository, coreUtility, microflowRepository);
		ScheduledJobRepository scheduledJobRepository = ScheduledJobRepository.getInstance();
		QueueRepository queueRepository = QueueRepository.getInstance();
		JobRepository jobRepository = new JobRepository();
		
		adder.add(this.context(), logger, queueRepository, jobRepository, scheduledJobRepository, job);
		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "AddJobToQueueTextToMicroflow";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
