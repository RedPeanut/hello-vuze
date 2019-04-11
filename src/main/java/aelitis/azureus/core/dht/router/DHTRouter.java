package aelitis.azureus.core.dht.router;

public interface DHTRouter {
	/**
	 * Adds a contact to the router. The contact is not known to be alive (e.g.
	 * we've been returned the contact by someone but we've not either got a reply
	 * from it, nor has it invoked us.
	 * @param nodeId
	 * @param attachment
	 * @return
	 */
	public void contactKnown(
		byte[]						nodeId,
		DHTRouterContactAttachment	attachment,
		boolean						force);
}
