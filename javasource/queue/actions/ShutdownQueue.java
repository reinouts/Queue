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
import queue.helpers.QueueController;
import queue.proxies.constants.Constants;
import queue.repositories.QueueRepository;

/**
 * Graceful shutdown waits for all pending jobs that have been submitted. No additional jobs can be added to the queue.
 * 
 * If graceful shutdown is set to false, all running and pending tasks will be terminated.
 * 
 * AwaitTermination waits until the queue has been shut down or the timeout period has been exceeded.
 */
public class ShutdownQueue extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String name;
	private java.lang.Boolean gracefully;
	private java.lang.Boolean awaitTermination;
	private java.lang.Long terminationTimeout;

	public ShutdownQueue(IContext context, java.lang.String name, java.lang.Boolean gracefully, java.lang.Boolean awaitTermination, java.lang.Long terminationTimeout)
	{
		super(context);
		this.name = name;
		this.gracefully = gracefully;
		this.awaitTermination = awaitTermination;
		this.terminationTimeout = terminationTimeout;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		// BEGIN USER CODE
		ILogNode logger = Core.getLogger(Constants.getLOGNODE());
		QueueController queueController = new QueueController(logger);
		QueueRepository queueRepository = QueueRepository.getInstance();
		return queueController.shutdown(queueRepository, name, gracefully, awaitTermination, terminationTimeout.intValue());
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "ShutdownQueue";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
