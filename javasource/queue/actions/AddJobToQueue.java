// This file was generated by Mendix Modeler.
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
import queue.helpers.JobValidator;
import queue.helpers.MicroflowValidator;
import queue.helpers.TimeUnitConverter;
import queue.proxies.ENU_JobStatus;
import queue.proxies.constants.Constants;
import queue.repositories.QueueRepository;
import queue.usecases.QueueHandler;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class AddJobToQueue extends CustomJavaAction<java.lang.Boolean>
{
	private IMendixObject __JobParameter1;
	private queue.proxies.Job JobParameter1;

	public AddJobToQueue(IContext context, IMendixObject JobParameter1)
	{
		super(context);
		this.__JobParameter1 = JobParameter1;
	}

	@Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.JobParameter1 = __JobParameter1 == null ? null : queue.proxies.Job.initialize(getContext(), __JobParameter1);

		// BEGIN USER CODE
		ILogNode logger = Core.getLogger(Constants.getLOGNODE());
		MicroflowValidator microflowValidator = new MicroflowValidator();
		JobValidator jobValidator = new JobValidator(logger, microflowValidator);
		boolean valid = jobValidator.isValid(JobParameter1);
		
		if (valid == false) {
			return false;
		}
		
		
		JobParameter1.setStatus(ENU_JobStatus.Queued);
		
		try {
			JobParameter1.commit(this.context());
		} catch (Exception e) {
			logger.error("Could not commit job.");
			return false;
		}
		
		QueueRepository
			.getQueue(JobParameter1.getQueue())
			.schedule(
					new QueueHandler(__JobParameter1.getId()), 
					JobParameter1.getDelay(), 
					TimeUnitConverter.getTimeUnit(JobParameter1.getDelayUnit().getCaption())
					);
		
		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public java.lang.String toString()
	{
		return "AddJobToQueue";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}