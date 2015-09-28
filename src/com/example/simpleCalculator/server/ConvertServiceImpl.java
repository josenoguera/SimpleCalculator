package com.example.simpleCalculator.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.example.simpleCalculator.client.ConvertService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ConvertServiceImpl extends RemoteServiceServlet implements ConvertService {

	private static final long serialVersionUID = 4276360013759733550L;
	private static final PersistenceManagerFactory PMF =
		      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	public String convertToBinary(String decimal) throws IllegalStateException {
		Conversion conv = new Conversion(decimal);

		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(conv);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			pm.close();
		}

		return conv.getBinary();
	}
	
	public String[] getHistory() throws IllegalArgumentException {
		PersistenceManager pm = getPersistenceManager();
		List<String> history = new ArrayList<String>();
		try {
			Query q = pm.newQuery(Conversion.class, "");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("timestamp");
			
			@SuppressWarnings("unchecked")
			List<Conversion> list = (List<Conversion>) q.execute();
			for (Conversion conv : list) {
				history.add(String.format("%s: %s -> %s", conv.getTimestamp(), conv.getDecimal(), conv.getBinary()));
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			pm.close();
		}

		return (String[]) history.toArray(new String[0]);
	}
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
}
